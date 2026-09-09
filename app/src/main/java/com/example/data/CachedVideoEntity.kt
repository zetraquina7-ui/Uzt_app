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
        val realVideoId = when {
            !videoId.isNullOrBlank() && !videoId.contains(":") && videoId.length in 8..15 -> videoId
            !id.contains(":") && id.substringAfterLast("_").length in 8..15 -> id.substringAfterLast("_")
            !thumbnailUrl.isNullOrBlank() && !thumbnailUrl.startsWith("http") && thumbnailUrl.length in 8..15 -> thumbnailUrl
            else -> videoId ?: id
        }
        val realDuration = when {
            duration.isNotBlank() && duration != "Vídeo" && duration != "Short" -> duration
            !videoId.isNullOrBlank() && videoId.contains(":") -> videoId
            else -> if (isShort) "Short" else "Vídeo"
        }
        val realThumb = when {
            !thumbnailUrl.isNullOrBlank() && thumbnailUrl.startsWith("http") -> thumbnailUrl
            realVideoId.isNotBlank() && !realVideoId.contains(":") -> "https://i.ytimg.com/vi/$realVideoId/hqdefault.jpg"
            else -> "https://i.ytimg.com/vi/wOnvZxQ-Iio/hqdefault.jpg"
        }

        return YouTubeVideoTrack(
            id = realVideoId,
            title = title,
            videoId = realVideoId,
            thumbnailUrl = realThumb,
            duration = realDuration,
            emoji = emoji,
            publishedDate = publishedDate,
            isShort = isShort,
            remoteThumbnailUrl = realThumb
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
            val vid = track.cleanVideoId
            val uniqueId = "${categoryKey}_${vid.ifBlank { orderIndex.toString() }}"
            return CachedVideoEntity(
                id = uniqueId,
                playlistOrCategoryKey = categoryKey,
                title = track.cleanTitle,
                videoId = vid,
                thumbnailUrl = track.cleanThumbnailUrl,
                duration = track.cleanDuration,
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
