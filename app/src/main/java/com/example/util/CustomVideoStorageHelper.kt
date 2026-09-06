package com.example.util

import android.content.Context
import android.util.Log
import com.example.ui.screens.YouTubeVideoTrack
import org.json.JSONArray
import org.json.JSONObject

/**
 * Helper to manage custom user-added YouTube video URLs and Playlists.
 * Persists user videos to local storage and integrates with the in-app embedded player.
 */
object CustomVideoStorageHelper {
    private const val TAG = "CustomVideoStorage"
    private const val PREFS_NAME = "custom_youtube_videos_prefs"
    private const val KEY_CUSTOM_VIDEOS_JSON = "saved_custom_youtube_videos"

    /**
     * Loads all user-added custom YouTube videos
     */
    fun loadCustomVideos(context: Context): List<YouTubeVideoTrack> {
        val list = mutableListOf<YouTubeVideoTrack>()
        try {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val json = prefs.getString(KEY_CUSTOM_VIDEOS_JSON, null) ?: return emptyList()
            val array = JSONArray(json)

            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                val id = obj.getString("id")
                val title = obj.getString("title")
                val videoId = obj.optString("videoId", id)
                val thumb = obj.optString("thumbnailUrl", "https://img.youtube.com/vi/$videoId/hqdefault.jpg")
                val duration = obj.optString("duration", "Vídeo")
                val emoji = obj.optString("emoji", "⭐")
                val isShort = obj.optBoolean("isShort", false)

                list.add(
                    YouTubeVideoTrack(
                        id = id,
                        title = title,
                        videoId = videoId,
                        thumbnailUrl = thumb,
                        duration = duration,
                        emoji = emoji,
                        isShort = isShort
                    )
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error loading custom YouTube videos", e)
        }
        return list
    }

    /**
     * Adds or updates a custom video in persistent storage
     */
    fun saveCustomVideo(
        context: Context,
        videoId: String,
        title: String,
        isShort: Boolean = false
    ): YouTubeVideoTrack {
        val current = loadCustomVideos(context).toMutableList()
        current.removeAll { it.videoId == videoId || it.id == videoId }

        val track = YouTubeVideoTrack(
            id = videoId,
            title = title.ifBlank { "Vídeo YouTube ($videoId)" },
            videoId = videoId,
            thumbnailUrl = "https://img.youtube.com/vi/$videoId/hqdefault.jpg",
            duration = if (isShort) "Short" else "Vídeo",
            emoji = if (isShort) "⚡" else "⭐",
            isShort = isShort
        )

        // Insert at beginning of list so latest added shows first
        current.add(0, track)
        saveList(context, current)
        return track
    }

    /**
     * Deletes a custom video by ID
     */
    fun deleteCustomVideo(context: Context, videoId: String): Boolean {
        try {
            val current = loadCustomVideos(context).toMutableList()
            val removed = current.removeAll { it.videoId == videoId || it.id == videoId }
            if (removed) {
                saveList(context, current)
                return true
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting custom video $videoId", e)
        }
        return false
    }

    private fun saveList(context: Context, list: List<YouTubeVideoTrack>) {
        val array = JSONArray()
        for (item in list) {
            val obj = JSONObject()
            obj.put("id", item.id)
            obj.put("title", item.title)
            obj.put("videoId", item.videoId ?: item.id)
            obj.put("thumbnailUrl", item.thumbnailUrl ?: "https://img.youtube.com/vi/${item.videoId}/hqdefault.jpg")
            obj.put("duration", item.duration)
            obj.put("emoji", item.emoji)
            obj.put("isShort", item.isShort)
            array.put(obj)
        }

        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_CUSTOM_VIDEOS_JSON, array.toString())
            .apply()
    }
}
