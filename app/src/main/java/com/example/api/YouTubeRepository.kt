package com.example.api

import android.util.Log
import com.example.BuildConfig
import com.example.data.CanalRecomendado
import com.example.data.VideoModel
import com.example.ui.screens.YouTubeVideoTrack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

data class YouTubeApiResult(
    val videos: List<YouTubeVideoTrack>,
    val errorMessage: String? = null,
    val resolvedChannelId: String? = null
)

class YouTubeRepository(private val customApiKey: String? = null) {

    val effectiveApiKey: String
        get() {
            val key = customApiKey?.takeIf { it.isNotBlank() && it != "DEFAULT_KEY" }
                ?: try { BuildConfig.YOUTUBE_API_KEY.takeIf { it.isNotBlank() && it != "DEFAULT_KEY" } } catch (e: Throwable) { null }
                ?: "AIzaSyBP6gYBEy9W2p0FIVObmRKScBkIJRqgTUE"
            return key.trim().trim('"', '\'')
        }

    private val unavailableTitles = setOf(
        "private video",
        "deleted video",
        "this video is unavailable",
        "this video is private",
        "video unavailable",
        "vídeo indisponível",
        "vídeo privado",
        "vídeo eliminado",
        "vídeo removido"
    )

    private fun isVideoValidAndAvailable(item: JSONObject): Boolean {
        // 1. Check status object if provided by API (privacyStatus must be 'public')
        val status = item.optJSONObject("status")
        if (status != null) {
            val privacyStatus = status.optString("privacyStatus", "").trim()
            if (privacyStatus.isNotEmpty() && !privacyStatus.equals("public", ignoreCase = true)) {
                return false
            }
            val uploadStatus = status.optString("uploadStatus", "").trim()
            if (uploadStatus.equals("deleted", ignoreCase = true) ||
                uploadStatus.equals("failed", ignoreCase = true) ||
                uploadStatus.equals("rejected", ignoreCase = true)) {
                return false
            }
        }

        // 2. Check snippet
        val snippet = item.optJSONObject("snippet") ?: return false
        val resourceId = snippet.optJSONObject("resourceId")
        val videoId = resourceId?.optString("videoId", "")?.trim() ?: ""
        if (videoId.isBlank() || videoId.equals("null", ignoreCase = true)) return false

        // 3. Check title (must not be empty, null, or a known YouTube placeholder for unavailable/deleted videos)
        val title = snippet.optString("title", "").trim()
        if (title.isBlank() || title.equals("null", ignoreCase = true)) return false
        if (unavailableTitles.contains(title.lowercase())) return false

        // 4. Check thumbnails (empty/null thumbnails is the definitive indicator of unavailable/private/deleted videos in YouTube API)
        val thumbnails = snippet.optJSONObject("thumbnails")
        if (thumbnails == null || thumbnails.length() == 0) return false

        val hasAnyValidThumb = listOf("maxres", "standard", "high", "medium", "default").any { key ->
            val thumbObj = thumbnails.optJSONObject(key)
            val thumbUrl = thumbObj?.optString("url", "")?.trim() ?: ""
            thumbUrl.isNotBlank() && !thumbUrl.equals("null", ignoreCase = true)
        }
        if (!hasAnyValidThumb) return false

        return true
    }

    suspend fun fetchPlaylistVideos(playlistId: String, maxResults: Int = 30): List<YouTubeVideoTrack> = withContext(Dispatchers.IO) {
        val tracks = mutableListOf<YouTubeVideoTrack>()
        val endpoint = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet,status&playlistId=$playlistId&maxResults=$maxResults&key=$effectiveApiKey"
        var conn: HttpURLConnection? = null
        try {
            val url = URL(endpoint)
            conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.connectTimeout = 4000
            conn.readTimeout = 4000

            if (conn.responseCode == 200) {
                val responseText = conn.inputStream.bufferedReader().use { it.readText() }
                val json = JSONObject(responseText)
                val items = json.optJSONArray("items")
                if (items != null) {
                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        if (!isVideoValidAndAvailable(item)) continue

                        val snippet = item.getJSONObject("snippet")

                        val resourceId = snippet.getJSONObject("resourceId")
                        val videoId = resourceId.getString("videoId").trim()
                        val title = snippet.getString("title").trim()
                        
                        val thumbnails = snippet.getJSONObject("thumbnails")
                        val thumbUrl = thumbnails.optJSONObject("high")?.optString("url")
                            ?: thumbnails.optJSONObject("medium")?.optString("url")
                            ?: thumbnails.optJSONObject("standard")?.optString("url")
                            ?: thumbnails.optJSONObject("default")?.optString("url")
                            ?: "https://img.youtube.com/vi/$videoId/hqdefault.jpg"

                        tracks.add(
                            YouTubeVideoTrack(
                                id = videoId,
                                title = title,
                                videoId = videoId,
                                thumbnailUrl = thumbUrl,
                                duration = "Vídeo",
                                emoji = "🎵"
                            )
                        )
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("YouTubeRepo", "Error fetching playlist $playlistId", e)
        } finally {
            conn?.disconnect()
        }
        tracks
    }

    private fun sanitizeChannelIdentifier(identifier: String): String {
        return identifier.trim().removePrefix("@")
    }

    suspend fun resolveUploadsPlaylistAndChannelId(identifier: String): Pair<String?, String?> = withContext(Dispatchers.IO) {
        val clean = sanitizeChannelIdentifier(identifier)
        if (clean.startsWith("UC") && clean.length == 24) {
            val uploadsId = "UU" + clean.substring(2)
            return@withContext Pair(uploadsId, clean)
        }
        val handleWithAtEndpoint = "https://www.googleapis.com/youtube/v3/channels?part=contentDetails,id,snippet&forHandle=%40$clean&key=$effectiveApiKey"
        val handleAtResult = fetchChannelDetailsFromUrl(handleWithAtEndpoint)
        if (handleAtResult.first != null) {
            return@withContext handleAtResult
        }
        val handleEndpoint = "https://www.googleapis.com/youtube/v3/channels?part=contentDetails,id,snippet&forHandle=$clean&key=$effectiveApiKey"
        val handleResult = fetchChannelDetailsFromUrl(handleEndpoint)
        if (handleResult.first != null) {
            return@withContext handleResult
        }
        val userEndpoint = "https://www.googleapis.com/youtube/v3/channels?part=contentDetails,id,snippet&forUsername=$clean&key=$effectiveApiKey"
        val userResult = fetchChannelDetailsFromUrl(userEndpoint)
        if (userResult.first != null) {
            return@withContext userResult
        }
        try {
            val searchChannelEndpoint = "https://www.googleapis.com/youtube/v3/search?part=snippet&type=channel&q=$clean&maxResults=1&key=$effectiveApiKey"
            val conn = URL(searchChannelEndpoint).openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.connectTimeout = 4000
            conn.readTimeout = 4000
            if (conn.responseCode == 200) {
                val responseText = conn.inputStream.bufferedReader().use { it.readText() }
                val json = JSONObject(responseText)
                val items = json.optJSONArray("items")
                if (items != null && items.length() > 0) {
                    val firstItem = items.getJSONObject(0)
                    val chId = firstItem.optJSONObject("id")?.optString("channelId")
                    if (!chId.isNullOrBlank() && chId.startsWith("UC")) {
                        val uploads = "UU" + chId.substring(2)
                        return@withContext Pair(uploads, chId)
                    }
                }
            }
            conn.disconnect()
        } catch (_: Exception) {}
        Pair(null, null)
    }

    suspend fun searchVideos(query: String, maxResults: Int = 30): List<YouTubeVideoTrack> = withContext(Dispatchers.IO) {
        val tracks = mutableListOf<YouTubeVideoTrack>()
        var conn: HttpURLConnection? = null
        try {
            val encodedQuery = java.net.URLEncoder.encode(query, "UTF-8")
            val endpoint = "https://www.googleapis.com/youtube/v3/search?part=snippet&type=video&q=$encodedQuery&maxResults=$maxResults&key=$effectiveApiKey"
            val url = URL(endpoint)
            conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.connectTimeout = 5000
            conn.readTimeout = 5000

            if (conn.responseCode == 200) {
                val responseText = conn.inputStream.bufferedReader().use { it.readText() }
                val json = JSONObject(responseText)
                val items = json.optJSONArray("items")
                if (items != null) {
                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val idObj = item.optJSONObject("id")
                        val videoId = idObj?.optString("videoId", "")?.trim() ?: ""
                        if (videoId.isBlank()) continue

                        val snippet = item.optJSONObject("snippet") ?: continue
                        val title = snippet.optString("title", "").trim()
                        if (title.isBlank() || unavailableTitles.contains(title.lowercase())) continue

                        val thumbnails = snippet.optJSONObject("thumbnails")
                        val thumbUrl = thumbnails?.optJSONObject("high")?.optString("url")
                            ?: thumbnails?.optJSONObject("medium")?.optString("url")
                            ?: thumbnails?.optJSONObject("standard")?.optString("url")
                            ?: thumbnails?.optJSONObject("default")?.optString("url")
                            ?: "https://img.youtube.com/vi/$videoId/hqdefault.jpg"

                        tracks.add(
                            YouTubeVideoTrack(
                                id = videoId,
                                title = title,
                                videoId = videoId,
                                thumbnailUrl = thumbUrl,
                                duration = "Vídeo",
                                emoji = "🎵",
                                isShort = title.contains("short", ignoreCase = true)
                            )
                        )
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("YouTubeRepo", "Error searching videos for query '$query'", e)
        } finally {
            conn?.disconnect()
        }
        tracks
    }

    private suspend fun fetchChannelDetailsFromUrl(endpoint: String): Pair<String?, String?> = withContext(Dispatchers.IO) {
        var conn: HttpURLConnection? = null
        try {
            val url = URL(endpoint)
            conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.connectTimeout = 4000
            conn.readTimeout = 4000
            if (conn.responseCode == 200) {
                val responseText = conn.inputStream.bufferedReader().use { it.readText() }
                val json = JSONObject(responseText)
                val items = json.optJSONArray("items")
                if (items != null && items.length() > 0) {
                    val item = items.getJSONObject(0)
                    val id = item.optString("id")
                    val contentDetails = item.optJSONObject("contentDetails")
                    val relatedPlaylists = contentDetails?.optJSONObject("relatedPlaylists")
                    val uploadsId = relatedPlaylists?.optString("uploads")
                    if (!uploadsId.isNullOrBlank() && !id.isNullOrBlank()) {
                        return@withContext Pair(uploadsId, id)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("YouTubeRepo", "Error fetching channel details", e)
        } finally {
            conn?.disconnect()
        }
        Pair(null, null)
    }

    suspend fun fetchChannelShortsAndVideos(channelIdentifier: String, maxResults: Int = 30): YouTubeApiResult = withContext(Dispatchers.IO) {
        val (uploadsId, channelId) = resolveUploadsPlaylistAndChannelId(channelIdentifier)
        
        if (uploadsId == null) {
            return@withContext YouTubeApiResult(videos = emptyList(), errorMessage = "Canal não encontrado", resolvedChannelId = null)
        }

        val videos = fetchPlaylistVideos(uploadsId, maxResults)
        val shorts = videos.filter { it.isShort || it.title.contains("#short", ignoreCase = true) || it.title.contains("#shorts", ignoreCase = true) }
        
        if (shorts.isNotEmpty()) {
            YouTubeApiResult(videos = shorts, resolvedChannelId = channelId)
        } else {
            YouTubeApiResult(videos = emptyList(), resolvedChannelId = channelId)
        }
    }

    fun getFallbackShorts(): List<YouTubeVideoTrack> = emptyList()

    suspend fun carregarVideosDoCanal(canal: CanalRecomendado, maxResultados: Int = 6): List<VideoModel> = withContext(Dispatchers.IO) {
        val playlistId = canal.channelId.replaceFirst("UC", "UU")
        val endpoint = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet,status&playlistId=$playlistId&maxResults=$maxResultados&key=$effectiveApiKey"
        val videos = mutableListOf<VideoModel>()
        var connection: HttpURLConnection? = null
        try {
            val url = URL(endpoint)
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 4000
            connection.readTimeout = 4000
            if (connection.responseCode == 200) {
                val responseText = connection.inputStream.bufferedReader().use { it.readText() }
                val json = JSONObject(responseText)
                val items = json.optJSONArray("items") ?: return@withContext emptyList()
                for (i in 0 until items.length()) {
                    val item = items.getJSONObject(i)
                    if (!isVideoValidAndAvailable(item)) continue

                    val snippet = item.getJSONObject("snippet")
                    val resourceId = snippet.getJSONObject("resourceId")
                    val videoId = resourceId.getString("videoId").trim()
                    val titulo = snippet.getString("title").trim()
                    
                    val thumbnails = snippet.getJSONObject("thumbnails")
                    val thumbnailUrl = thumbnails.optJSONObject("high")?.optString("url")
                        ?: thumbnails.optJSONObject("medium")?.optString("url")
                        ?: thumbnails.optJSONObject("standard")?.optString("url")
                        ?: thumbnails.optJSONObject("default")?.optString("url")
                        ?: "https://img.youtube.com/vi/$videoId/hqdefault.jpg"

                    videos.add(
                        VideoModel(
                            id = videoId,
                            titulo = titulo,
                            nomeCanal = canal.nomeCanal,
                            urlThumbnail = thumbnailUrl,
                            categoria = canal.categoria
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("YouTubeRepo", "Error carregarVideosDoCanal", e)
        } finally {
            connection?.disconnect()
        }
        videos
    }
}
