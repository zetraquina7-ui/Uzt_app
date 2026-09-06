package com.example.data

import android.content.Context
import android.util.Log
import com.example.ui.screens.YouTubeVideoTrack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap

/**
 * High-performance Multi-Tier Video Cache Manager powered by Room Database
 * - Tier 1: Ultra-fast In-Memory RAM Cache (Instant 0ms retrieval on tab switching)
 * - Tier 2: Persistent Local Room Database Cache (Zero unnecessary network calls, offline recovery)
 * - Tier 3: Stale-While-Revalidate pattern with TTL cache expiration management (12h TTL)
 */
object VideoCacheManager {
    private const val TAG = "VideoCacheManager"
    private const val CACHE_EXPIRATION_MS = 12 * 60 * 60 * 1000L // 12 hours TTL

    // L1: In-Memory RAM Cache
    private val memoryCache = ConcurrentHashMap<String, List<YouTubeVideoTrack>>()
    private val memoryTimestamp = ConcurrentHashMap<String, Long>()

    private fun getDao(context: Context): VideoCacheDao {
        return AppDatabase.getDatabase(context).videoCacheDao()
    }

    /**
     * Observes reactive Flow of cached tracks directly from Room Database.
     */
    fun observeCachedTracks(context: Context, playlistOrCategoryKey: String): Flow<List<YouTubeVideoTrack>> {
        return getDao(context).getVideosByKeyFlow(playlistOrCategoryKey).map { entities ->
            entities.map { it.toYouTubeVideoTrack() }
        }
    }

    /**
     * Periodically cleans up expired video metadata from Room Database and memory cache.
     * Keeps downloaded videos intact.
     */
    suspend fun cleanupExpiredVideos(
        context: Context,
        maxAgeMs: Long = CACHE_EXPIRATION_MS
    ): Int = withContext(Dispatchers.IO) {
        try {
            val threshold = System.currentTimeMillis() - maxAgeMs
            val deletedCount = getDao(context).deleteExpiredVideos(threshold)
            
            // Clean up stale L1 memory cache keys
            val now = System.currentTimeMillis()
            val expiredKeys = memoryTimestamp.filter { (now - it.value) > maxAgeMs }.keys
            expiredKeys.forEach { key ->
                memoryCache.remove(key)
                memoryTimestamp.remove(key)
            }
            
            if (deletedCount > 0) {
                Log.d(TAG, "Cleaned up $deletedCount expired video cache entries from Room.")
            }
            return@withContext deletedCount
        } catch (e: Exception) {
            Log.e(TAG, "Error cleaning up expired videos", e)
            0
        }
    }

    /**
     * Gets instantly available cached tracks from L1 Memory (or empty list if not yet loaded).
     */
    fun getInstantCachedTracks(context: Context, playlistId: String): List<YouTubeVideoTrack> {
        // 1. Check L1 Memory Cache
        return memoryCache[playlistId] ?: emptyList()
    }

    /**
     * Loads tracks directly from Room Database (L2 persistent cache).
     * Performs a fast opportunistic cleanup of expired records first.
     */
    suspend fun loadFromRoomDatabase(context: Context, playlistOrCategoryKey: String): List<YouTubeVideoTrack> = withContext(Dispatchers.IO) {
        try {
            cleanupExpiredVideos(context)
            val entities = getDao(context).getVideosByKey(playlistOrCategoryKey)
            if (entities.isNotEmpty()) {
                val tracks = entities.map { it.toYouTubeVideoTrack() }
                memoryCache[playlistOrCategoryKey] = tracks
                val latestTime = entities.maxOfOrNull { it.cachedAt } ?: System.currentTimeMillis()
                memoryTimestamp[playlistOrCategoryKey] = latestTime
                return@withContext tracks
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error querying Room database for $playlistOrCategoryKey", e)
        }
        emptyList()
    }

    /**
     * Checks if the cache for a playlist / category is stale or expired.
     */
    suspend fun isCacheExpired(context: Context, playlistOrCategoryKey: String): Boolean = withContext(Dispatchers.IO) {
        val now = System.currentTimeMillis()
        
        // Check L1 memory timestamp first
        val memTime = memoryTimestamp[playlistOrCategoryKey]
        if (memTime != null && memTime > 0L) {
            return@withContext (now - memTime) > CACHE_EXPIRATION_MS
        }

        // Check Room database timestamp
        try {
            val roomTimestamp = getDao(context).getLatestTimestampByKey(playlistOrCategoryKey)
            if (roomTimestamp != null && roomTimestamp > 0L) {
                memoryTimestamp[playlistOrCategoryKey] = roomTimestamp
                return@withContext (now - roomTimestamp) > CACHE_EXPIRATION_MS
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error checking Room timestamp for $playlistOrCategoryKey", e)
        }

        true // No cache or expired
    }

    /**
     * Synchronizes fresh playlist tracks from the API with Room Database.
     * Automatically purges videos that no longer exist in the playlist / channel and replaces with fresh valid items.
     */
    suspend fun syncFreshTracks(
        context: Context,
        playlistOrCategoryKey: String,
        freshTracks: List<YouTubeVideoTrack>
    ) = withContext(Dispatchers.IO) {
        val now = System.currentTimeMillis()

        // 1. Update L1 Memory Cache
        memoryCache[playlistOrCategoryKey] = freshTracks
        memoryTimestamp[playlistOrCategoryKey] = now

        // 2. Persist to Room Database atomically removing orphan/missing videos
        try {
            val dao = getDao(context)
            val entities = freshTracks.mapIndexed { index, track ->
                CachedVideoEntity.fromYouTubeVideoTrack(
                    track = track,
                    categoryKey = playlistOrCategoryKey,
                    orderIndex = index,
                    timestamp = now
                )
            }
            dao.syncPlaylistTracks(playlistOrCategoryKey, entities)
            Log.d(TAG, "Successfully synced ${entities.size} videos in Room for key: $playlistOrCategoryKey")
        } catch (e: Exception) {
            Log.e(TAG, "Error syncing videos to Room for $playlistOrCategoryKey", e)
        }
    }

    /**
     * Saves tracks to both Room Database (L2) and Memory (L1) caches.
     */
    suspend fun saveTracksToCache(
        context: Context,
        playlistOrCategoryKey: String,
        tracks: List<YouTubeVideoTrack>
    ) = withContext(Dispatchers.IO) {
        syncFreshTracks(context, playlistOrCategoryKey, tracks)
    }

    /**
     * Clears cache for all playlists or a specific category from Room and Memory.
     */
    suspend fun clearCache(context: Context, playlistOrCategoryKey: String? = null) = withContext(Dispatchers.IO) {
        try {
            val dao = getDao(context)
            if (playlistOrCategoryKey != null) {
                memoryCache.remove(playlistOrCategoryKey)
                memoryTimestamp.remove(playlistOrCategoryKey)
                dao.deleteVideosByKey(playlistOrCategoryKey)
            } else {
                memoryCache.clear()
                memoryTimestamp.clear()
                dao.clearAll()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error clearing Room video cache", e)
        }
    }
}
