package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ui.screens.YouTubeVideoTrack

/**
 * Room Entity for caching YouTube video metadata locally.
 * Preserves category/playlist associations, metadata attributes, and order.
 */
@Entity(tableName = "cached_videos")
data class CachedVideoEntity(
    @PrimaryKey
    val id: String,
    val playlistOrCategoryKey: String,
    val title: String,
    val videoId: String?,
    val thumbnailUrl: String?,
    val duration: String,
    val emoji: String,
    val publishedDate: String,
    val isShort: Boolean,
    val isDownloaded: Boolean = false,
    val localFilePath: String? = null,
    val cachedAt: Long = System.currentTimeMillis(),
    val orderIndex: Int = 0
) {
    fun toYouTubeVideoTrack(): YouTubeVideoTrack {
        return YouTubeVideoTrack(
            id = videoId ?: id,
            title = title,
            videoId = videoId,
            thumbnailUrl = thumbnailUrl,
            duration = duration,
            emoji = emoji,
            publishedDate = publishedDate,
            isShort = isShort
        )
    }

    companion object {
        fun fromYouTubeVideoTrack(
            track: YouTubeVideoTrack,
            categoryKey: String,
            orderIndex: Int,
            timestamp: Long = System.currentTimeMillis(),
            isDownloaded: Boolean = false,
            localFilePath: String? = null
        ): CachedVideoEntity {
            val uniqueId = "${categoryKey}_${track.videoId ?: track.id.ifBlank { orderIndex.toString() }}"
            return CachedVideoEntity(
                id = uniqueId,
                playlistOrCategoryKey = categoryKey,
                title = track.title,
                videoId = track.videoId ?: track.id,
                thumbnailUrl = track.thumbnailUrl,
                duration = track.duration,
                emoji = track.emoji,
                publishedDate = track.publishedDate,
                isShort = track.isShort,
                isDownloaded = isDownloaded,
                localFilePath = localFilePath,
                cachedAt = timestamp,
                orderIndex = orderIndex
            )
        }
    }
}
