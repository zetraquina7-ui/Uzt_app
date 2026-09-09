package com.example.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ScreenRotation
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.content.res.Configuration
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.ui.layout.ContentScale
import com.example.ui.components.SafeAsyncImage
import com.example.BuildConfig
import com.example.api.YouTubeRepository
import com.example.data.VideoCacheManager
import com.example.ui.components.FundoApp
import com.example.ui.components.GrelhaVideosComponent
import com.example.ui.components.GrelhaVideosHorizontalCarousel
import com.example.ui.components.ScreenHeader
import com.example.ui.components.ShortsGrid
import com.example.ui.components.VideoPlayer
import com.example.ui.components.parseYouTubePlaylistId
import com.example.ui.components.parseYouTubeVideoId
import com.example.util.CustomVideoStorageHelper
import com.example.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.ui.input.pointer.PointerEventPass

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

data class YouTubeSubCategory(
    val key: String,
    val name: String,
    val emoji: String,
    val accentColor: Color,
    val playlistId: String? = null,
    val playlistList: List<Pair<String, String>> = emptyList(),
    val isCustom: Boolean = false
)

enum class VideoMainSection(val title: String, val emoji: String, val gradient: List<Color>) {
    ZE_TRAQUINA("Zé Traquina", "👦🏻", listOf(Color(0xFFEA580C), Color(0xFFF59E0B))),
    CANTINHO_PT("Cantinho PT", "🇵🇹", listOf(Color(0xFF0284C7), Color(0xFF10B981)))
}
fun getFallbackPlaylistTracks(playlistId: String): List<YouTubeVideoTrack> = emptyList()

private var hasShownRotationHintInSession = false

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
    resultList
}

@Composable
fun VideosScreen(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = context as? android.app.Activity
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val scope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        activity?.requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_SENSOR
        
        onDispose {
            activity?.requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    // Dialog for adding YouTube URLs
        var showAddVideoDialog by remember { mutableStateOf(false) }
        var inputVideoUrl by remember { mutableStateOf("") }
        var inputVideoTitle by remember { mutableStateOf("") }

        var selectedSection by remember { mutableStateOf(VideoMainSection.ZE_TRAQUINA) }

        val zeTraquinaSubCategories = remember {
            listOf(
                YouTubeSubCategory("ze_musicas", "Músicas", "🎵", Color(0xFFE91E63), playlistId = "PLHz1Xt0IaQWM"),
                YouTubeSubCategory("ze_educativo", "Educativo", "📖", Color(0xFF10B981), playlistId = "PLT7ZV5QsDKA4"),
                YouTubeSubCategory("ze_historias", "Histórias", "📚", Color(0xFF7C3AED), playlistId = "PLZjPDfJ2Av4c"),
                YouTubeSubCategory("ze_diversao", "Diversão", "🪁", Color(0xFF06B6D4), playlistId = "PLHXyMYX6Yxxc"),
                YouTubeSubCategory("ze_shorts", "Shorts ⚡", "⚡", Color(0xFFEC4899), playlistId = "ze_shorts_auto")
            )
        }

        val cantinhoPtSubCategories = remember {
            listOf(
                YouTubeSubCategory(
                    key = "pt_musicas",
                    name = "Músicas",
                    emoji = "🎵",
                    accentColor = Color(0xFFE91E63),
                    playlistId = "PLSsrAc3exDx0"
                ),
                YouTubeSubCategory(
                    key = "pt_aprender",
                    name = "Aprender",
                    emoji = "🧠",
                    accentColor = Color(0xFFEA580C),
                    playlistId = "PLAHA0KA1fztw"
                ),
                YouTubeSubCategory(
                    key = "pt_brincar",
                    name = "Brincar",
                    emoji = "🪁",
                    accentColor = Color(0xFF06B6D4),
                    playlistId = "PLWywOKJSEg6E"
                ),
                YouTubeSubCategory(
                    key = "pt_historias",
                    name = "Histórias",
                    emoji = "📚",
                    accentColor = Color(0xFF8B5CF6),
                    playlistId = "PLTHYqcQWhhUI"
                )
            )
        }

        var selectedZeSub by remember { mutableStateOf(zeTraquinaSubCategories[0]) }
        var selectedPtSub by remember { mutableStateOf(cantinhoPtSubCategories[0]) }

        val activeSubCategory = if (selectedSection == VideoMainSection.ZE_TRAQUINA) selectedZeSub else selectedPtSub
        val activeKey = activeSubCategory.key

        var selectedVideoId by remember { mutableStateOf<String?>(null) }
        var refreshTrigger by remember { mutableIntStateOf(0) }

        var categoryTracksMap by remember {
            mutableStateOf<Map<String, List<YouTubeVideoTrack>>>(emptyMap())
        }
        var isLoadingTracks by remember { mutableStateOf(false) }
        var apiErrorMessage by remember { mutableStateOf<String?>(null) }

        LaunchedEffect(selectedSection, activeSubCategory, refreshTrigger) {
            if (activeSubCategory.isCustom || activeKey == "ze_meus_videos") {
                val customVideos = CustomVideoStorageHelper.loadCustomVideos(context)
                categoryTracksMap = categoryTracksMap + (activeKey to customVideos)
                isLoadingTracks = false
                return@LaunchedEffect
            }

            val existing = categoryTracksMap[activeKey] ?: emptyList()
            val roomList = VideoCacheManager.loadFromRoomDatabase(context, activeKey)
            val hasRealCache = roomList.isNotEmpty()
            if (hasRealCache) {
                categoryTracksMap = categoryTracksMap + (activeKey to roomList)
            }

            val isExpired = VideoCacheManager.isCacheExpired(context, activeKey)
            val isForcedRefresh = refreshTrigger > 0

            if (hasRealCache && !isExpired && !isForcedRefresh) {
                isLoadingTracks = false
                return@LaunchedEffect
            }

            if (!hasRealCache) {
                isLoadingTracks = true
            }

            try {
                var errorReport: String? = null
                val freshTracks = withContext(Dispatchers.IO) {
                    val apiKey = try {
                        val key = BuildConfig.YOUTUBE_API_KEY
                        if (key.isNotBlank() && key != "DEFAULT_KEY") key else YOUTUBE_API_KEY
                    } catch (e: Throwable) { YOUTUBE_API_KEY }

                    val repo = YouTubeRepository(apiKey)
                    when {
                        // 1. Zé Traquina Shorts dedicated channel fetch
                        activeKey == "ze_shorts" -> {
                            val (uploadsId, _) = repo.resolveUploadsPlaylistAndChannelId("zetraquina")
                            var videos = if (!uploadsId.isNullOrBlank()) {
                                repo.fetchPlaylistVideos(uploadsId, maxResults = 50)
                            } else emptyList()
                            if (videos.isEmpty()) {
                                videos = repo.searchVideos("Zé Traquina shorts", maxResults = 30)
                            }
                            videos.filter { it.isShort || it.title.contains("short", ignoreCase = true) }.ifEmpty { videos }
                        }
                        // 2. Specific YouTube Playlist ID (Músicas, Educativo, Histórias, Diversão)
                        activeSubCategory.playlistId != null && activeSubCategory.playlistId.isNotBlank() -> {
                            fetchYouTubePlaylistItems(activeSubCategory.playlistId, apiKey, activeSubCategory.name)
                        }
                        // 3. Multi-playlist subcategory
                        activeSubCategory.playlistList.isNotEmpty() -> {
                            coroutineScope {
                                val playlistDeferreds = activeSubCategory.playlistList.map { (pid, name) ->
                                    async(Dispatchers.IO) {
                                        try {
                                            fetchYouTubePlaylistItems(pid, apiKey, name)
                                        } catch (e: Exception) {
                                            emptyList()
                                        }
                                    }
                                }
                                val combined = mutableListOf<YouTubeVideoTrack>()
                                playlistDeferreds.awaitAll().forEach { list ->
                                    for (item in list) {
                                        if (!item.videoId.isNullOrBlank() && combined.none { it.videoId == item.videoId }) {
                                            combined.add(item)
                                        }
                                    }
                                }
                                combined.distinctBy { it.videoId }
                            }
                        }
                        else -> emptyList()
                    }
                }
                apiErrorMessage = errorReport
                if (freshTracks.isNotEmpty()) {
                    categoryTracksMap = categoryTracksMap + (activeKey to freshTracks)
                    VideoCacheManager.syncFreshTracks(context, activeKey, freshTracks)
                } else if (hasRealCache) {
                    // Keep real cached tracks
                } else {
                    categoryTracksMap = categoryTracksMap + (activeKey to emptyList())
                }
            } catch (e: Exception) {
                Log.e("VideosScreen", "Error loading tracks for $activeKey", e)
            } finally {
                isLoadingTracks = false
            }
        }

        val activeTracks = categoryTracksMap[activeKey] ?: emptyList()
        val activeVideoId = selectedVideoId ?: activeTracks.firstOrNull()?.videoId

        // --- DIALOG: ADICIONAR VÍDEO YOUTUBE ---
        if (showAddVideoDialog) {
            val detectedId = remember(inputVideoUrl) { parseYouTubeVideoId(inputVideoUrl) }
            val detectedPlaylistId = remember(inputVideoUrl) { parseYouTubePlaylistId(inputVideoUrl) }
            val isValid = detectedId != null || detectedPlaylistId != null

            AlertDialog(
                onDismissRequest = { showAddVideoDialog = false },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🔗 Adicionar Vídeo YouTube", fontWeight = FontWeight.Bold, fontSize = 17.sp)
                    }
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "Cola qualquer link do YouTube (Vídeo, Short, Playlist ou ID de 11 caracteres) para tocar na app:",
                            fontSize = 12.sp,
                            color = Color(0xFF475569)
                        )

                        OutlinedTextField(
                            value = inputVideoUrl,
                            onValueChange = { inputVideoUrl = it },
                            label = { Text("URL ou ID do YouTube") },
                            placeholder = { Text("Ex: https://youtu.be/wOnvZxQ-Iio") },
                            singleLine = true,
                            trailingIcon = {
                                if (inputVideoUrl.isNotEmpty()) {
                                    IconButton(onClick = { inputVideoUrl = "" }) {
                                        Icon(Icons.Default.Clear, contentDescription = "Limpar")
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = inputVideoTitle,
                            onValueChange = { inputVideoTitle = it },
                            label = { Text("Título do Vídeo (Opcional)") },
                            placeholder = { Text("Ex: Minha Música Favorita") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Status da Deteção
                        if (inputVideoUrl.isNotBlank()) {
                            if (detectedId != null) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFFDCFCE7),
                                    border = BorderStroke(1.dp, Color(0xFF22C55E))
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF16A34A), modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Vídeo reconhecido: ID $detectedId", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF15803D))
                                    }
                                }
                            } else if (detectedPlaylistId != null) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFFDCFCE7),
                                    border = BorderStroke(1.dp, Color(0xFF22C55E))
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF16A34A), modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Playlist reconhecida: ID $detectedPlaylistId", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF15803D))
                                    }
                                }
                            } else {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFFFEF3C7),
                                    border = BorderStroke(1.dp, Color(0xFFF59E0B))
                                ) {
                                    Text(
                                        text = "⚠️ Introduz um link válido do YouTube",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFB45309),
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                    )
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val vid = detectedId ?: detectedPlaylistId
                            if (vid != null) {
                                val isShort = inputVideoUrl.contains("/shorts/")
                                val savedTrack = CustomVideoStorageHelper.saveCustomVideo(
                                    context = context,
                                    videoId = vid,
                                    title = inputVideoTitle.ifBlank { "Vídeo YouTube ($vid)" },
                                    isShort = isShort
                                )

                                val updatedCustomList = CustomVideoStorageHelper.loadCustomVideos(context)
                                categoryTracksMap = categoryTracksMap + ("ze_meus_videos" to updatedCustomList)

                                // Select custom tab and play newly added video
                                selectedSection = VideoMainSection.ZE_TRAQUINA
                                selectedZeSub = zeTraquinaSubCategories.first { it.key == "ze_meus_videos" }
                                selectedVideoId = savedTrack.videoId

                                Toast.makeText(context, "Vídeo adicionado e a reproduzir! 🎬", Toast.LENGTH_SHORT).show()
                                showAddVideoDialog = false
                                inputVideoUrl = ""
                                inputVideoTitle = ""
                            }
                        },
                        enabled = isValid,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEA580C))
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Guardar & Reproduzir")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAddVideoDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        var showRotationHint by remember { mutableStateOf(!hasShownRotationHintInSession && !isLandscape) }

        LaunchedEffect(isLandscape) {
            if (isLandscape) {
                hasShownRotationHintInSession = true
                showRotationHint = false
            }
        }

        LaunchedEffect(showRotationHint) {
            if (showRotationHint) {
                delay(4500)
                showRotationHint = false
                hasShownRotationHintInSession = true
            }
        }

        if (isLandscape) {
            LandscapeImmersiveVideoPlayer(
                selectedSection = selectedSection,
                onSectionSelected = { newSection ->
                    if (selectedSection != newSection) {
                        selectedSection = newSection
                        selectedVideoId = null
                    }
                },
                activeSubCategory = activeSubCategory,
                subCategories = if (selectedSection == VideoMainSection.ZE_TRAQUINA) zeTraquinaSubCategories else cantinhoPtSubCategories,
                onSubCategorySelected = { newSub ->
                    if (selectedSection == VideoMainSection.ZE_TRAQUINA) {
                        selectedZeSub = newSub
                    } else {
                        selectedPtSub = newSub
                    }
                    selectedVideoId = null
                },
                categoryTracksMap = categoryTracksMap,
                activeVideoId = activeVideoId,
                onVideoSelected = { track ->
                    selectedVideoId = track.videoId ?: track.id
                }
            )
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                ) {
                // --- Header ---
                ScreenHeader(
                    title = if (selectedSection == VideoMainSection.ZE_TRAQUINA) "Vídeos e Músicas" else "Videos e músicas",
                    subtitle = if (selectedSection == VideoMainSection.ZE_TRAQUINA) "Canal Oficial @zetraquina" else "YouTube kids  🇵🇹",
                    icon = "🎬",
                    gradientColors = selectedSection.gradient,
                    modifier = Modifier.fillMaxWidth()
                )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                VideoMainSection.values().forEach { section ->
                    val isSelected = selectedSection == section
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(32.dp)
                            .clickable {
                                if (selectedSection != section) {
                                    selectedSection = section
                                    selectedVideoId = null
                                }
                            },
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) Color.Transparent else Color.White.copy(alpha = 0.85f)
                        ),
                        border = BorderStroke(
                            width = if (isSelected) 1.5.dp else 0.5.dp,
                            color = if (isSelected) section.gradient.first() else Color.LightGray.copy(alpha = 0.4f)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 3.dp else 1.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .then(
                                    if (isSelected) Modifier.background(
                                        Brush.horizontalGradient(section.gradient)
                                    ) else Modifier
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = section.emoji,
                                    fontSize = 13.sp,
                                    modifier = Modifier.padding(end = 4.dp)
                                )
                                Text(
                                    text = section.title,
                                    fontSize = 10.5.sp,
                                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold,
                                    color = if (isSelected) Color.White else Color(0xFF334155)
                                )
                            }
                        }
                    }
                }
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
                    .aspectRatio(16f / 10f)
            ) {
                VideoPlayer(
                    youtubeId = activeVideoId,
                    playlistId = if (selectedVideoId == null && selectedSection == VideoMainSection.ZE_TRAQUINA && activeSubCategory.playlistId?.startsWith("ze_") != true && !activeSubCategory.isCustom) activeSubCategory.playlistId else null,
                    modifier = Modifier.fillMaxSize(),
                    onVideoEnded = {
                        val currentIndex = activeTracks.indexOfFirst { (it.videoId ?: it.id) == activeVideoId }
                        val nextTrack = if (currentIndex != -1 && currentIndex < activeTracks.size - 1) {
                            activeTracks[currentIndex + 1]
                        } else {
                            activeTracks.firstOrNull()
                        }
                        if (nextTrack != null) {
                            selectedVideoId = nextTrack.videoId ?: nextTrack.id
                        }
                    }
                )

                // Visual Rotation Hint for children
                RotateDeviceHintOverlay(
                    visible = showRotationHint,
                    onDismiss = {
                        showRotationHint = false
                        hasShownRotationHintInSession = true
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 8.dp, bottom = 8.dp)
                )
            }

            // --- SUB-MENUS UNDER PLAYER ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val currentSubCategories = if (selectedSection == VideoMainSection.ZE_TRAQUINA) {
                    zeTraquinaSubCategories
                } else {
                    cantinhoPtSubCategories
                }

                LazyRow(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(currentSubCategories.size) { index ->
                        val subCategory = currentSubCategories[index]
                        val isSelected = activeSubCategory.key == subCategory.key

                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                if (selectedSection == VideoMainSection.ZE_TRAQUINA) {
                                    selectedZeSub = subCategory
                                } else {
                                    selectedPtSub = subCategory
                                }
                                selectedVideoId = null
                            },
                            label = {
                                Text(
                                    text = subCategory.emoji,
                                    fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                                    fontSize = 13.sp,
                                    letterSpacing = (-0.2).sp,
                                    maxLines = 1
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = subCategory.accentColor,
                                selectedLabelColor = Color.White,
                                containerColor = Color.White.copy(alpha = 0.85f),
                                labelColor = Color(0xFF475569)
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = Color.Transparent,
                                selectedBorderColor = subCategory.accentColor,
                                borderWidth = 0.dp,
                                selectedBorderWidth = 0.dp
                            ),
                            shape = RoundedCornerShape(6.dp)
                        )
                    }
                }

                IconButton(
                    onClick = { refreshTrigger++ },
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Atualizar Vídeos",
                        tint = Color(0xFF475569),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // --- VIDEO CONTENT GRID ---
            if (activeKey == "ze_shorts") {
                ShortsGrid(
                    shorts = activeTracks,
                    selectedVideoId = activeVideoId,
                    isLoading = isLoadingTracks,
                    onShortSelected = { track ->
                        selectedVideoId = track.videoId ?: track.id
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            } else {
                GrelhaVideosComponent(
                    videos = activeTracks,
                    selectedVideoId = activeVideoId,
                    isLoading = isLoadingTracks,
                    accentColor = activeSubCategory.accentColor,
                    onVideoSelected = { track ->
                        selectedVideoId = track.videoId ?: track.id
                    },
                    onDeleteVideo = if (activeSubCategory.isCustom || activeKey == "ze_meus_videos") {
                        { track ->
                            val vid = track.videoId ?: track.id
                            CustomVideoStorageHelper.deleteCustomVideo(context, vid)
                            val updated = CustomVideoStorageHelper.loadCustomVideos(context)
                            categoryTracksMap = categoryTracksMap + (activeKey to updated)
                            if (selectedVideoId == vid) {
                                selectedVideoId = updated.firstOrNull()?.videoId
                            }
                        }
                    } else null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    errorMessage = apiErrorMessage,
                    onRetry = { refreshTrigger++ }
                )
            }
        }

        // Floating Action Button removed by user request
        /*
        if (activeKey != "ze_historias") {
            androidx.compose.material3.FloatingActionButton(
            onClick = { showAddVideoDialog = true },
            containerColor = Color(0xFFEA580C),
            contentColor = Color.White,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .size(56.dp)
                .testTag("add_video_button")
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Adicionar Vídeo",
                modifier = Modifier.size(28.dp)
            )
        }
    }
    */
}
}
}

@Composable
private fun LandscapeImmersiveVideoPlayer(
    selectedSection: VideoMainSection,
    onSectionSelected: (VideoMainSection) -> Unit,
    activeSubCategory: YouTubeSubCategory,
    subCategories: List<YouTubeSubCategory>,
    onSubCategorySelected: (YouTubeSubCategory) -> Unit,
    categoryTracksMap: Map<String, List<YouTubeVideoTrack>>,
    activeVideoId: String?,
    onVideoSelected: (YouTubeVideoTrack) -> Unit
) {
    var mostrarMenu by remember { mutableStateOf(false) }
    var resetTimerTrigger by remember { mutableStateOf(0) }

    // Control visibility of system bars in landscape
    val context = LocalContext.current
    val window = (context as? android.app.Activity)?.window
    val view = androidx.compose.ui.platform.LocalView.current
    
    DisposableEffect(Unit) {
        val windowInsetsController = window?.let {
            androidx.core.view.WindowCompat.getInsetsController(it, view)
        }
        windowInsetsController?.systemBarsBehavior = androidx.core.view.WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController?.hide(androidx.core.view.WindowInsetsCompat.Type.systemBars())
        
        onDispose {
            windowInsetsController?.show(androidx.core.view.WindowInsetsCompat.Type.systemBars())
        }
    }

    // 5-second inactivity auto-hide
    LaunchedEffect(mostrarMenu, resetTimerTrigger) {
        if (mostrarMenu) {
            delay(5000)
            mostrarMenu = false
        }
    }

    // Only display tracks for the currently selected subcategory playlist (strictly separated)
    val currentTracks = categoryTracksMap[activeSubCategory.key] ?: emptyList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
    ) {
        if (mostrarMenu) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    // Reset the inactivity timer when touching or interacting with the top menus
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                awaitPointerEvent(androidx.compose.ui.input.pointer.PointerEventPass.Initial)
                                resetTimerTrigger++
                            }
                        }
                    },
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Top: Section Toggles (Tabs Zé Traquina and Cantinho PT)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
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
                                .height(32.dp)
                                .clickable { 
                                    onSectionSelected(section)
                                    resetTimerTrigger++
                                }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${section.emoji} ${section.title}",
                                    textAlign = TextAlign.Center,
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                // Sub-category Pills (Playlists)
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(subCategories.size) { index ->
                        val subCategory = subCategories[index]
                        val isSelected = activeSubCategory.key == subCategory.key
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) subCategory.accentColor else Color.White.copy(alpha = 0.15f),
                            border = BorderStroke(
                                width = if (isSelected) 1.5.dp else 0.5.dp,
                                color = if (isSelected) Color.White else Color.White.copy(alpha = 0.25f)
                            ),
                            modifier = Modifier
                                .height(28.dp)
                                .clickable {
                                    onSubCategorySelected(subCategory)
                                    resetTimerTrigger++
                                }
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            ) {
                                Text(
                                    text = "${subCategory.emoji} ${subCategory.name}",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }

        // Player (adjusts size and style based on state)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(if (mostrarMenu) 1.2f else 1f)
                .padding(
                    horizontal = if (mostrarMenu) 48.dp else 0.dp,
                    vertical = if (mostrarMenu) 6.dp else 0.dp
                )
        ) {
            Surface(
                shape = if (mostrarMenu) RoundedCornerShape(12.dp) else RoundedCornerShape(0.dp),
                border = if (mostrarMenu) BorderStroke(2.dp, Color.White.copy(alpha = 0.3f)) else null,
                color = Color.Black,
                modifier = Modifier.fillMaxSize()
            ) {
                VideoPlayer(
                    youtubeId = activeVideoId,
                    playlistId = null,
                    modifier = Modifier.fillMaxSize(),
                    onVideoEnded = {
                        val currentIndex = currentTracks.indexOfFirst { (it.videoId ?: it.id) == activeVideoId }
                        val nextTrack = if (currentIndex != -1 && currentIndex < currentTracks.size - 1) {
                            currentTracks[currentIndex + 1]
                        } else {
                            currentTracks.firstOrNull()
                        }
                        if (nextTrack != null) {
                            onVideoSelected(nextTrack)
                        }
                    }
                )
            }

            // Clickable overlay on the player:
            // 1. If in fullscreen, clicking minimizes the player and displays menus.
            // 2. If already minimized, clicking the player returns it back to fullscreen.
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .clickable {
                        mostrarMenu = !mostrarMenu
                    }
            )
        }

        if (mostrarMenu) {
            // Bottom: Horizontal thumbnails carousel of active tracks for selected playlist
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    // Reset the inactivity timer when touching or scrolling the bottom cards list
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                awaitPointerEvent(androidx.compose.ui.input.pointer.PointerEventPass.Initial)
                                resetTimerTrigger++
                            }
                        }
                    }
            ) {
                GrelhaVideosHorizontalCarousel(
                    videos = currentTracks,
                    selectedVideoId = activeVideoId,
                    isLoading = false,
                    accentColor = activeSubCategory.accentColor,
                    onVideoSelected = { track ->
                        onVideoSelected(track)
                        resetTimerTrigger++
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

/**
 * Pista visual discreta de rotação para crianças no ecrã de Vídeos
 */
@Composable
fun RotateDeviceHintOverlay(
    visible: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(400)) + scaleIn(initialScale = 0.85f),
        exit = fadeOut(animationSpec = tween(400)) + scaleOut(targetScale = 0.85f),
        modifier = modifier
    ) {
        val infiniteTransition = rememberInfiniteTransition(label = "rotation_hint_anim")
        val rotationAngle by infiniteTransition.animateFloat(
            initialValue = -12f,
            targetValue = 12f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 650, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "phone_tilt"
        )
        val pulseScale by infiniteTransition.animateFloat(
            initialValue = 0.97f,
            targetValue = 1.03f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 650, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "badge_pulse"
        )

        Surface(
            shape = RoundedCornerShape(18.dp),
            color = Color(0xEB0F172A),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.22f)),
            shadowElevation = 5.dp,
            modifier = Modifier
                .graphicsLayer {
                    scaleX = pulseScale
                    scaleY = pulseScale
                }
                .clickable { onDismiss() }
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ScreenRotation,
                    contentDescription = "Rodar para ecrã inteiro",
                    tint = Color(0xFFFBBF24),
                    modifier = Modifier
                        .size(16.dp)
                        .graphicsLayer {
                            rotationZ = rotationAngle
                        }
                )
                Text(
                    text = "Vira o ecrã 📱",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

