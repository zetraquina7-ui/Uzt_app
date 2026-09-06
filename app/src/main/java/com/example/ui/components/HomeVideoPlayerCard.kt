package com.example.ui.components

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.PlayerView
import com.example.R
import com.example.ui.theme.SunshineYellow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

/**
 * Singleton cache for downloaded video chunks so playback never stutters on replay.
 */
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
object VideoPlayerCacheHolder {
    private var simpleCache: SimpleCache? = null

    @Synchronized
    fun getCache(context: Context): SimpleCache {
        if (simpleCache == null) {
            val cacheDir = File(context.cacheDir, "ze_media3_video_cache")
            val evictor = LeastRecentlyUsedCacheEvictor(100 * 1024 * 1024L) // 100MB
            val databaseProvider = StandaloneDatabaseProvider(context.applicationContext)
            simpleCache = SimpleCache(cacheDir, evictor, databaseProvider)
        }
        return simpleCache!!
    }
}

@androidx.media3.common.util.UnstableApi
@Composable
fun HomeVideoPlayerCard(
    videoUrl: String = "https://files.catbox.moe/cxlun5.mp4",
    starsCount: Int = 0,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var autoplayFeito by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }
    var isMuted by remember { mutableStateOf(false) }
    var isBuffering by remember { mutableStateOf(false) }
    var hasError by remember { mutableStateOf(false) }
    var showControls by remember { mutableStateOf(true) }
    var isEnded by remember { mutableStateOf(false) }
    var currentPositionMs by remember { mutableLongStateOf(0L) }
    var totalDurationMs by remember { mutableLongStateOf(0L) }
    var retryAttempts by remember { mutableIntStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    val failedUrls = remember { mutableSetOf<String>() }

    val exoPlayerState = remember { mutableStateOf<ExoPlayer?>(null) }
    val appContext = context.applicationContext

    LaunchedEffect(Unit) {
        if (!autoplayFeito) {
            isPlaying = true
            autoplayFeito = true
        }
    }

    LaunchedEffect(isPlaying, exoPlayerState.value) {
        val player = exoPlayerState.value ?: return@LaunchedEffect
        if (isPlaying) {
            if (!player.isPlaying) {
                player.play()
            }
        } else {
            if (player.isPlaying) {
                player.pause()
            }
        }
    }

    // Auto hide controls after 3.5 seconds of inactivity when opened
    LaunchedEffect(showControls, isPlaying, isEnded) {
        if (showControls && isPlaying && !isEnded) {
            delay(3500)
            showControls = false
        }
    }

    val isYouTubeVideo = remember(videoUrl) {
        videoUrl.contains("youtube") || videoUrl.contains("youtu.be")
    }

    DisposableEffect(appContext, isYouTubeVideo, videoUrl) {
        if (isYouTubeVideo) {
            exoPlayerState.value = null
            onDispose { }
        } else {
            val player = try {
                val httpDataSourceFactory = DefaultHttpDataSource.Factory()
                    .setUserAgent("Mozilla/5.0 (Linux; Android 14; Mobile) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Mobile Safari/537.36")
                    .setConnectTimeoutMs(15_000)
                    .setReadTimeoutMs(15_000)
                    .setAllowCrossProtocolRedirects(true)

                val dataSourceFactory = DefaultDataSource.Factory(appContext, httpDataSourceFactory)
                val renderersFactory = com.example.util.ExoPlayerHelper.createRenderersFactory(appContext)

                ExoPlayer.Builder(appContext, renderersFactory)
                    .setMediaSourceFactory(
                        DefaultMediaSourceFactory(dataSourceFactory, com.example.util.ExoPlayerHelper.createExtractorsFactory())
                    )
                    .build().apply {
                        val audioAttributes = AudioAttributes.Builder()
                            .setUsage(C.USAGE_MEDIA)
                            .setContentType(C.AUDIO_CONTENT_TYPE_MOVIE)
                            .build()
                        setAudioAttributes(audioAttributes, false)
                        repeatMode = Player.REPEAT_MODE_OFF
                        volume = 1f
                        addListener(object : Player.Listener {
                            override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                                Log.w("HomeVideoPlayerCard", "ExoPlayer playback error: ${error.message}")
                                hasError = true
                                isBuffering = false
                            }
                            override fun onPlaybackStateChanged(playbackState: Int) {
                                if (playbackState == Player.STATE_BUFFERING) {
                                    isBuffering = true
                                } else if (playbackState == Player.STATE_READY) {
                                    isBuffering = false
                                    hasError = false
                                }
                            }
                        })
                    }
            } catch (e: Throwable) {
                Log.e("HomeVideoPlayerCard", "Error creating ExoPlayer", e)
                null
            }

            exoPlayerState.value = player

            onDispose {
                try {
                    player?.stop()
                    player?.clearMediaItems()
                    player?.release()
                } catch (e: Throwable) {
                    Log.e("HomeVideoPlayerCard", "Error releasing ExoPlayer", e)
                }
                exoPlayerState.value = null
            }
        }
    }

    LaunchedEffect(videoUrl, exoPlayerState.value, isYouTubeVideo) {
        if (isYouTubeVideo) return@LaunchedEffect
        val player = exoPlayerState.value ?: return@LaunchedEffect
        
        val mediaUri = if (com.example.util.ZeAvatarCacheManager.isCached(context, videoUrl)) {
            Uri.fromFile(com.example.util.ZeAvatarCacheManager.getCacheFile(context, videoUrl))
        } else {
            com.example.util.ZeAvatarCacheManager.preloadVideo(context, videoUrl)
            Uri.parse(videoUrl)
        }
        
        try {
            val mediaItem = MediaItem.Builder()
                .setUri(mediaUri)
                .build()
            player.setMediaItem(mediaItem)
            player.repeatMode = Player.REPEAT_MODE_OFF
            if (isPlaying) {
                player.prepare()
                player.playWhenReady = true
                player.play()
            } else {
                isBuffering = false
            }
        } catch (e: Throwable) {
            Log.e("HomeVideoPlayerCard", "Error setting media item", e)
            try {
                // Fallback to direct remote URL
                val fallbackItem = MediaItem.fromUri(Uri.parse(videoUrl))
                player.setMediaItem(fallbackItem)
                player.prepare()
                player.play()
            } catch (e2: Throwable) {
                com.example.util.MediaFailureTracker.addFailedUrl(videoUrl)
                hasError = true
                isBuffering = false
            }
        }
    }

    // Lifecycle Observer to safely pause when app is in background
    DisposableEffect(lifecycleOwner, exoPlayerState.value) {
        val player = exoPlayerState.value
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> player?.pause()
                Lifecycle.Event.ON_RESUME -> if (isPlaying) player?.play()
                Lifecycle.Event.ON_STOP -> player?.pause()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // Attach Player Listener
    DisposableEffect(exoPlayerState.value) {
        val player = exoPlayerState.value ?: return@DisposableEffect onDispose {}
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_BUFFERING -> {
                        isBuffering = true
                        hasError = false
                        isEnded = false
                    }
                    Player.STATE_READY -> {
                        isBuffering = false
                        hasError = false
                        isEnded = false
                        totalDurationMs = player.duration.coerceAtLeast(0L)
                        if (player.playWhenReady) {
                            player.play()
                        }
                    }
                    Player.STATE_ENDED -> {
                        isBuffering = false
                        isEnded = true
                        showControls = true
                        isPlaying = false
                        player.pause()
                    }
                    Player.STATE_IDLE -> {
                        isEnded = false
                    }
                }
            }

            override fun onIsPlayingChanged(playing: Boolean) {
                isPlaying = playing || player.playWhenReady
            }

            override fun onPlayerError(error: PlaybackException) {
                Log.e("HomeVideoPlayerCard", "Player error for URL: $videoUrl - ${error.message}", error)
                if (videoUrl.isNotBlank()) {
                    com.example.util.MediaFailureTracker.addFailedUrl(videoUrl)
                }

                var cause: Throwable? = error
                var isUnrecognizedFormat = false
                while (cause != null) {
                    if (cause is androidx.media3.exoplayer.source.UnrecognizedInputFormatException ||
                        cause.message?.contains("None of the available extractors") == true) {
                        isUnrecognizedFormat = true
                        break
                    }
                    cause = cause.cause
                }

                // Immediately stop if format is unrecognized or URL failed
                if (isUnrecognizedFormat || error.errorCode == PlaybackException.ERROR_CODE_PARSING_CONTAINER_UNSUPPORTED) {
                    isBuffering = false
                    hasError = true
                    try {
                        player.stop()
                        player.clearMediaItems()
                    } catch (_: Throwable) {}
                    return
                }
                
                if (retryAttempts < 2) {
                    retryAttempts++
                    coroutineScope.launch {
                        delay(1500)
                        try {
                            val fallbackUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
                            player.setMediaItem(MediaItem.fromUri(Uri.parse(fallbackUrl)))
                            player.prepare()
                            player.play()
                        } catch (e: Throwable) {
                            Log.w("HomeVideoPlayerCard", "Error during auto-retry with fallback", e)
                            hasError = true
                            isBuffering = false
                        }
                    }
                } else {
                    isBuffering = false
                    hasError = true
                }
            }
        }

        player.addListener(listener)
        onDispose {
            player.removeListener(listener)
        }
    }

    // Sync position
    LaunchedEffect(exoPlayerState.value, isPlaying) {
        val player = exoPlayerState.value
        while (isPlaying && player != null) {
            try {
                currentPositionMs = player.currentPosition.coerceAtLeast(0L)
                totalDurationMs = player.duration.coerceAtLeast(0L)
            } catch (_: Throwable) {}
            delay(500)
        }
    }

    // Main Card Layout
    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("home_video_section"),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Section Header bar with title and star count
        Row(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .padding(horizontal = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color.White.copy(alpha = 0.90f),
                border = BorderStroke(1.dp, Color.White),
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🎬 Vídeo do Zé Traquina",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF0F172A)
                    )
                }
            }

            // 3D Glass Star Points Rectangle
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color.White.copy(alpha = 0.95f),
                border = BorderStroke(1.2.dp, SunshineYellow),
                shadowElevation = 3.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$starsCount",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFFD81B60)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = SunshineYellow,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }

        // Video Player Container - Compact, aesthetic, centered on screen
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .height(180.dp)
                .shadow(5.dp, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            color = Color.Black,
            border = BorderStroke(
                1.6.dp,
                Brush.linearGradient(
                    listOf(Color(0xFF38BDF8), Color(0xFFFFD700), Color(0xFFFF6E40))
                )
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .then(
                        if (!isYouTubeVideo) {
                            Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                showControls = !showControls
                            }
                        } else Modifier
                    )
            ) {
                if (isYouTubeVideo) {
                    YouTubePlayerComposable(
                        videoUrl = if (videoUrl.isNotBlank()) videoUrl else "https://www.youtube.com/watch?v=wOnvZxQ-Iio",
                        modifier = Modifier.fillMaxSize()
                    )
                } else if (exoPlayerState.value != null && !hasError) {
                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = { ctx ->
                            (android.view.LayoutInflater.from(ctx).inflate(R.layout.exo_texture_player, null) as PlayerView).apply {
                                player = exoPlayerState.value
                                useController = false
                                setShowBuffering(PlayerView.SHOW_BUFFERING_NEVER)
                                setShutterBackgroundColor(android.graphics.Color.TRANSPARENT)
                                layoutParams = FrameLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT
                                )
                            }
                        },
                        update = { playerView ->
                            if (playerView.player != exoPlayerState.value) {
                                playerView.player = exoPlayerState.value
                            }
                        }
                    )
                }

                // Buffering Spinner for ExoPlayer
                if (!isYouTubeVideo && isBuffering && !hasError) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.35f)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = SunshineYellow,
                            strokeWidth = 3.dp,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }

                // Error Overlay for ExoPlayer
                if (!isYouTubeVideo && hasError) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.75f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Não foi possível carregar o vídeo",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Button(
                                onClick = {
                                    retryAttempts = 0
                                    hasError = false
                                    isBuffering = true
                                    exoPlayerState.value?.prepare()
                                    exoPlayerState.value?.play()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    Icons.Default.Refresh,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Tentar Novamente", fontSize = 11.sp)
                            }
                        }
                    }
                }

                // Custom Controls Overlay
                androidx.compose.animation.AnimatedVisibility(
                    visible = (showControls || isEnded) && !hasError && !isYouTubeVideo,
                    enter = fadeIn(),
                    exit = fadeOut(),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                if (isEnded) {
                                    exoPlayerState.value?.seekTo(0)
                                    exoPlayerState.value?.play()
                                    isEnded = false
                                } else {
                                    showControls = false
                                }
                            }
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.45f),
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.75f)
                                    )
                                )
                            )
                    ) {
                        // Center Play/Pause Button
                        IconButton(
                            onClick = {
                                if (isEnded) {
                                    exoPlayerState.value?.seekTo(0)
                                    exoPlayerState.value?.play()
                                    isEnded = false
                                } else if (isPlaying) {
                                    exoPlayerState.value?.pause()
                                } else {
                                    if (exoPlayerState.value?.playbackState == Player.STATE_IDLE) {
                                        exoPlayerState.value?.prepare()
                                    }
                                    exoPlayerState.value?.play()
                                }
                                showControls = true
                            },
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(48.dp)
                                .background(Color.Black.copy(alpha = 0.60f), CircleShape)
                                .border(1.5.dp, Color.White, CircleShape)
                        ) {
                            Icon(
                                imageVector = if (isEnded) Icons.Default.Replay else if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (isEnded) "Recomeçar" else if (isPlaying) "Pausar" else "Reproduzir",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        // Bottom Controls Bar
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Mute / Unmute Button
                            IconButton(
                                onClick = {
                                    val newMuted = !isMuted
                                    isMuted = newMuted
                                    exoPlayerState.value?.volume = if (newMuted) 0f else 1f
                                },
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(Color.White.copy(alpha = 0.25f), CircleShape)
                            ) {
                                Icon(
                                    imageVector = if (isMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                                    contentDescription = if (isMuted) "Ativar Som" else "Desativar Som",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }

                            // Time Progress Indicator
                            val currentSec = (currentPositionMs / 1000).toInt()
                            val totalSec = (totalDurationMs / 1000).toInt()
                            val curFormatted = String.format("%02d:%02d", currentSec / 60, currentSec % 60)
                            val totFormatted = String.format("%02d:%02d", totalSec / 60, totalSec % 60)

                            Text(
                                text = "$curFormatted / $totFormatted",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            // Replay Button
                            IconButton(
                                onClick = {
                                    exoPlayerState.value?.seekTo(0)
                                    exoPlayerState.value?.play()
                                },
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(Color.White.copy(alpha = 0.25f), CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Replay,
                                    contentDescription = "Recomeçar",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
