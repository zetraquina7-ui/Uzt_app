import re

content = """package com.example.ui.screens

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ScreenRotation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.BuildConfig
import com.example.api.YouTubeRepository
import com.example.data.VideoCacheManager
import com.example.ui.components.GrelhaVideosComponent
import com.example.ui.components.VideoPlayer
import com.example.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

const val YOUTUBE_API_KEY = "AIzaSyBP6gYBEy9W2p0FIVObmRKScBkIJRqgTUE"

data class YouTubeVideoTrack(
    val id: String,
    val title: String,
    val videoId: String? = null,
    val thumbnailUrl: String? = null,
    val duration: String = "Vídeo",
    val emoji: String = "🎵",
    val publishedDate: String = "",
    val isShort: Boolean = false
)

enum class VideoMainSection(val title: String, val emoji: String, val gradient: List<Color>) {
    ZE_TRAQUINA("Zé Traquina", "👦🏻", listOf(Color(0xFFEA580C), Color(0xFFF59E0B))),
    CANTINHO_PT("Cantinho PT", "🇵🇹", listOf(Color(0xFF0284C7), Color(0xFF10B981)))
}

fun getFallbackPlaylistTracks(playlistId: String): List<YouTubeVideoTrack> {
    // Basic fallback for robust offline start
    return listOf(
        YouTubeVideoTrack("wOnvZxQ-Iio", "Sou de Viana", "wOnvZxQ-Iio", "https://i3.ytimg.com/vi/wOnvZxQ-Iio/hqdefault.jpg", "Vídeo", "🎵"),
        YouTubeVideoTrack("jYYvwC3L2kI", "Férias de verão", "jYYvwC3L2kI", "https://i3.ytimg.com/vi/jYYvwC3L2kI/hqdefault.jpg", "Vídeo", "🎵"),
        YouTubeVideoTrack("fe4HmhQRCUg", "Um mundo melhor #zetraquina", "fe4HmhQRCUg", "https://i3.ytimg.com/vi/fe4HmhQRCUg/hqdefault.jpg", "Vídeo", "🎵")
    )
}

suspend fun fetchYouTubePlaylistItems(
    playlistId: String,
    apiKey: String,
    categoryName: String = "Vídeo"
): List<YouTubeVideoTrack> = withContext(Dispatchers.IO) {
    val resultList = mutableListOf<YouTubeVideoTrack>()
    try {
        val repo = YouTubeRepository(apiKey)
        val fetchedVideos = repo.fetchPlaylistVideos(playlistId, maxResults = 30)
        if (fetchedVideos.isNotEmpty()) {
            resultList.addAll(fetchedVideos)
        }
    } catch (e: Exception) {
        Log.e("VideosScreen", "Error fetching YouTube playlist: $playlistId", e)
    }

    if (resultList.isEmpty()) {
        resultList.addAll(getFallbackPlaylistTracks(playlistId))
    }
    resultList
}

@Composable
fun VideosScreen(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = context as? android.app.Activity

    DisposableEffect(Unit) {
        activity?.requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_SENSOR
        onDispose {
            activity?.requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    var selectedSection by remember { mutableStateOf(VideoMainSection.ZE_TRAQUINA) }
    val activeKey = if (selectedSection == VideoMainSection.ZE_TRAQUINA) "ze_traquina_all" else "cantinho_pt_all"

    var selectedVideoId by remember { mutableStateOf<String?>(null) }
    var refreshTrigger by remember { mutableIntStateOf(0) }

    var categoryTracksMap by remember { mutableStateOf<Map<String, List<YouTubeVideoTrack>>>(emptyMap()) }
    var isLoadingTracks by remember { mutableStateOf(false) }
    var apiErrorMessage by remember { mutableStateOf<String?>(null) }

    var isFullscreen by remember { mutableStateOf(true) }
    var lastInteractionTime by remember { mutableStateOf(System.currentTimeMillis()) }

    // 6. Timeout automático
    LaunchedEffect(isFullscreen, lastInteractionTime) {
        if (!isFullscreen) {
            delay(5000)
            isFullscreen = true
        }
    }

    LaunchedEffect(activeKey, refreshTrigger) {
        val roomList = VideoCacheManager.loadFromRoomDatabase(context, activeKey)
        if (roomList.isNotEmpty()) {
            categoryTracksMap = categoryTracksMap + (activeKey to roomList)
        }

        val isExpired = VideoCacheManager.isCacheExpired(context, activeKey)
        if (roomList.isNotEmpty() && !isExpired && refreshTrigger == 0) {
            isLoadingTracks = false
            return@LaunchedEffect
        }

        isLoadingTracks = true
        try {
            val apiKey = try {
                val key = BuildConfig.YOUTUBE_API_KEY
                if (key.isNotBlank() && key != "DEFAULT_KEY") key else YOUTUBE_API_KEY
            } catch (e: Throwable) { YOUTUBE_API_KEY }

            val playlists = if (activeKey == "ze_traquina_all") {
                listOf("PLHz1Xt0IaQWM", "PLT7ZV5QsDKA4", "PLHXyMYX6Yxxc", "ze_shorts_auto")
            } else {
                listOf("PLWQVAYRzFnXMmKTjW1L_ofLE22ctWytot", "PL6Nz8kJ8yTb9mgVi1XdLNBEaSjkQoQkMW", 
                       "PLUNTULW6QqtVXhrW6HRAHzkFGELfIZ-cy", "PL6Nz8kJ8yTb_2O_sVP5cLGq-5LmvblTIb", 
                       "PLXpqaMB-MlNsha4jilmX1Lrh6FPkGTLJu", "PLAXFI56mD-HQ", 
                       "PLE0G9h6NddcDzleKVxnMjrWPBcCNWiUAt", "PL7fbHLFM41KeS4tWdu3h_2FjUNC-CfUXk")
            }

            val combined = mutableListOf<YouTubeVideoTrack>()
            val repo = YouTubeRepository(apiKey)
            
            if (activeKey == "ze_traquina_all") {
                try {
                    val shortsResult = repo.fetchChannelShortsAndVideos("zetraquina", maxResults = 20)
                    combined.addAll(shortsResult.videos)
                } catch (e: Exception) {}
            }

            coroutineScope {
                val deferreds = playlists.filter { it != "ze_shorts_auto" }.map { pid ->
                    async(Dispatchers.IO) {
                        try {
                            fetchYouTubePlaylistItems(pid, apiKey, "Vídeos")
                        } catch (e: Exception) { emptyList() }
                    }
                }
                deferreds.awaitAll().forEach { list ->
                    for (item in list) {
                        if (!item.videoId.isNullOrBlank() && combined.none { it.videoId == item.videoId }) {
                            combined.add(item)
                        }
                    }
                }
            }

            if (combined.isNotEmpty()) {
                val distinct = combined.distinctBy { it.videoId }.shuffled()
                categoryTracksMap = categoryTracksMap + (activeKey to distinct)
                VideoCacheManager.saveTracksToCache(context, activeKey, distinct)
            } else {
                val fallbackList = getFallbackPlaylistTracks(activeKey)
                categoryTracksMap = categoryTracksMap + (activeKey to fallbackList.distinctBy { it.videoId }.shuffled())
            }
        } catch (e: Exception) {
            Log.e("VideosScreen", "Error loading tracks for $activeKey", e)
        } finally {
            isLoadingTracks = false
        }
    }

    val activeTracks = categoryTracksMap[activeKey] ?: emptyList()
    
    // 2. Reprodução aleatória ao entrar ou trocar de separador
    LaunchedEffect(activeKey, activeTracks) {
        if (activeTracks.isNotEmpty() && selectedVideoId == null) {
            selectedVideoId = activeTracks.random().videoId
        }
    }

    val activeVideoId = selectedVideoId ?: activeTracks.firstOrNull()?.videoId

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        awaitPointerEvent(PointerEventPass.Initial)
                        // Reset timeout upon any interaction
                        lastInteractionTime = System.currentTimeMillis()
                    }
                }
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Player Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(if (isFullscreen) 1f else 0.4f)
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                val event = awaitPointerEvent(PointerEventPass.Initial)
                                if (event.type == androidx.compose.ui.input.pointer.PointerEventType.Release) {
                                    if (isFullscreen) {
                                        // 4. Shrink player on touch when fullscreen
                                        isFullscreen = false
                                    } else {
                                        // 7. CORREÇÃO DE BUG: touch when shrunk expands to fullscreen
                                        isFullscreen = true
                                    }
                                }
                            }
                        }
                    }
            ) {
                VideoPlayer(
                    youtubeId = activeVideoId,
                    playlistId = null, // Play single random video, autoplay next relies on UI if needed, or exo loop
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Tabs and Grid (Visible only when not fullscreen)
            if (!isFullscreen) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.6f)
                        .background(Color(0xFF0F172A))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    // Tabs
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp, top = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        VideoMainSection.values().forEach { section ->
                            val isSelected = selectedSection == section
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) section.gradient.first() else Color.Black.copy(alpha = 0.65f),
                                border = BorderStroke(
                                    width = if (isSelected) 1.5.dp else 0.8.dp,
                                    color = if (isSelected) Color.White else Color.White.copy(alpha = 0.3f)
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(40.dp)
                                    .clickable {
                                        if (selectedSection != section) {
                                            selectedSection = section
                                            selectedVideoId = null // Trigger random video
                                            isFullscreen = true // 5. Go back to fullscreen IMMEDIATELY
                                        }
                                    }
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "${section.emoji} ${section.title}",
                                        textAlign = TextAlign.Center,
                                        color = Color.White,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    // Content Grid
                    GrelhaVideosComponent(
                        videos = activeTracks,
                        selectedVideoId = activeVideoId,
                        isLoading = isLoadingTracks,
                        accentColor = selectedSection.gradient.first(),
                        onVideoSelected = { track ->
                            selectedVideoId = track.videoId ?: track.id
                            isFullscreen = true // 5. Go back to fullscreen IMMEDIATELY
                        },
                        onDeleteVideo = null,
                        modifier = Modifier.fillMaxSize(),
                        errorMessage = apiErrorMessage,
                        onRetry = { refreshTrigger++ }
                    )
                }
            }
        }
    }
}
"""

with open("app/src/main/java/com/example/ui/screens/VideosScreen.kt", "w") as f:
    f.write(content)
