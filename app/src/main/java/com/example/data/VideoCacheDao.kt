package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

/**
 * Room Data Access Object (DAO) for local caching of video metadata.
 */
@Dao
interface VideoCacheDao {
    @Query("SELECT * FROM cached_videos WHERE playlistOrCategoryKey = :key ORDER BY orderIndex ASC")
    fun getVideosByKeyFlow(key: String): Flow<List<CachedVideoEntity>>

    @Query("SELECT * FROM cached_videos WHERE playlistOrCategoryKey = :key ORDER BY orderIndex ASC")
    suspend fun getVideosByKey(key: String): List<CachedVideoEntity>

    @Query("SELECT MAX(cachedAt) FROM cached_videos WHERE playlistOrCategoryKey = :key")
    suspend fun getLatestTimestampByKey(key: String): Long?

    @Query("SELECT COUNT(*) FROM cached_videos WHERE playlistOrCategoryKey = :key")
    suspend fun countVideosByKey(key: String): Int

    @Query("UPDATE cached_videos SET isDownloaded = :isDownloaded, localFilePath = :path WHERE id = :videoId")
    suspend fun updateDownloadStatus(videoId: String, isDownloaded: Boolean, path: String?)

    @Query("SELECT * FROM cached_videos WHERE isDownloaded = 1")
    fun getDownloadedVideos(): Flow<List<CachedVideoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videos: List<CachedVideoEntity>)

    @Query("DELETE FROM cached_videos WHERE playlistOrCategoryKey = :key")
    suspend fun deleteVideosByKey(key: String)

    @Query("DELETE FROM cached_videos WHERE playlistOrCategoryKey = :key AND isDownloaded = 0")
    suspend fun deleteNonDownloadedByKey(key: String)

    @Query("DELETE FROM cached_videos WHERE cachedAt < :thresholdTimestamp AND isDownloaded = 0")
    suspend fun deleteExpiredVideos(thresholdTimestamp: Long): Int

    @Query("DELETE FROM cached_videos WHERE playlistOrCategoryKey = :key AND isDownloaded = 0 AND (videoId IS NULL OR videoId NOT IN (:validVideoIds))")
    suspend fun deleteMissingVideos(key: String, validVideoIds: List<String>): Int

    @Query("DELETE FROM cached_videos")
    suspend fun clearAll()

    /**
     * Atomically synchronizes a playlist's cached tracks:
     * Removes non-downloaded stale/missing videos and inserts fresh valid items.
     */
    @Transaction
    suspend fun syncPlaylistTracks(
        key: String,
        newVideos: List<CachedVideoEntity>
    ) {
        deleteNonDownloadedByKey(key)
        if (newVideos.isNotEmpty()) {
            insertVideos(newVideos)
        }
    }
}

