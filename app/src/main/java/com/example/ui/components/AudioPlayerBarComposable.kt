package com.example.ui.components

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import com.example.R
import com.example.ui.theme.SkyBluePrimary
import com.example.ui.theme.SunshineYellow
import kotlinx.coroutines.delay

@OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun AudioPlayerBarComposable(
    isPlayingState: Boolean = true,
    onPlayStateChanged: (Boolean) -> Unit = {},
    url: String? = null,
    modifier: Modifier = Modifier
) {
    val isPreview = androidx.compose.ui.platform.LocalInspectionMode.current
    if (isPreview) {
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2FE))
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.MusicNote, contentDescription = null, tint = SkyBluePrimary)
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                    Text("Zé Traquina é fixe 🎵", fontWeight = FontWeight.Bold, color = Color(0xFF0F172A), fontSize = 11.sp)
                    Text("Música do Zé Traquina", fontSize = 10.sp, color = Color.Gray)
                }
            }
        }
        return
    }

    val context = LocalContext.current.applicationContext
    var isPlaying by remember(isPlayingState) { mutableStateOf(isPlayingState) }
    var currentProgress by remember { mutableFloatStateOf(0f) }
    var totalDuration by remember { mutableFloatStateOf(100f) }

    var exoPlayer by remember { mutableStateOf<androidx.media3.exoplayer.ExoPlayer?>(null) }

    DisposableEffect(context, url) {
        val player = try {
            val renderersFactory = com.example.util.ExoPlayerHelper.createRenderersFactory(
                context,
                androidx.media3.exoplayer.DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF,
                isAudioOnly = true
            )
            androidx.media3.exoplayer.ExoPlayer.Builder(context, renderersFactory)
                .setMediaSourceFactory(com.example.util.ExoPlayerHelper.createMediaSourceFactory(context))
                .build().apply {
                    repeatMode = androidx.media3.common.Player.REPEAT_MODE_OFF
                    volume = 1.0f
                    setAudioAttributes(androidx.media3.common.AudioAttributes.DEFAULT, true)
                    
                    val mediaItem = if (!url.isNullOrBlank()) {
                        androidx.media3.common.MediaItem.fromUri(Uri.parse(url))
                    } else {
                        val rawUri = androidx.media3.datasource.RawResourceDataSource.buildRawResourceUri(R.raw.ze_traquina_e_fixe)
                        androidx.media3.common.MediaItem.fromUri(rawUri)
                    }

                    // Pure audio bar: disable video decoding completely to save system resources and prevent Codec2 errors
                    trackSelectionParameters = trackSelectionParameters.buildUpon()
                        .setTrackTypeDisabled(androidx.media3.common.C.TRACK_TYPE_VIDEO, true)
                        .build()

                    addListener(object : androidx.media3.common.Player.Listener {
                        override fun onPlaybackStateChanged(playbackState: Int) {
                            Log.d("AudioPlayerBar", "Playback state changed: $playbackState, isPlayingState: $isPlayingState")
                            if (playbackState == androidx.media3.common.Player.STATE_READY && isPlayingState) {
                                playWhenReady = true
                                play()
                            } else if (playbackState == androidx.media3.common.Player.STATE_ENDED) {
                                onPlayStateChanged(false)
                                seekTo(0)
                            }
                        }

                        override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                            Log.e("AudioPlayerBar", "ExoPlayer playback error: ${error.message}", error)
                            onPlayStateChanged(false)
                        }
                    })

                    setMediaItem(mediaItem)
                    if (isPlayingState) {
                        prepare()
                        playWhenReady = true
                        play()
                    }
                }
        } catch (e: Throwable) {
            Log.e("AudioPlayerBar", "Error building ExoPlayer", e)
            null
        }

        exoPlayer = player

        onDispose {
            try {
                player?.stop()
                player?.release()
            } catch (_: Throwable) {}
            exoPlayer = null
        }
    }

    LaunchedEffect(isPlayingState, exoPlayer) {
        isPlaying = isPlayingState
        exoPlayer?.let { player ->
            try {
                if (isPlayingState) {
                    if (player.playbackState == androidx.media3.common.Player.STATE_IDLE) {
                        player.prepare()
                    }
                    player.playWhenReady = true
                    if (!player.isPlaying) {
                        player.play()
                    }
                } else if (player.isPlaying) {
                    player.pause()
                }
            } catch (e: Exception) {
                Log.e("AudioPlayerBar", "Error toggle play state in ExoPlayer", e)
            }
        }
    }

    LaunchedEffect(exoPlayer, isPlayingState) {
        while (isPlayingState) {
            val player = exoPlayer
            if (player != null) {
                try {
                    if (player.isPlaying) {
                        val dur = player.duration.coerceAtLeast(1)
                        totalDuration = dur.toFloat()
                        currentProgress = player.currentPosition.toFloat().coerceIn(0f, totalDuration)
                    }
                } catch (e: Exception) {
                    // Ignore errors during state polling
                }
            }
            delay(500L)
        }
    }

    Card(
        modifier = modifier
            .height(38.dp)
            .testTag("audio_player_bar_card"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left Icon & Title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Surface(
                    shape = CircleShape,
                    color = SkyBluePrimary,
                    modifier = Modifier.size(24.dp)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Icon(
                            imageVector = Icons.Default.MusicNote,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(13.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(6.dp))

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Zé Traquina é fixe 🎵",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF0F172A),
                        maxLines = 1
                    )
                    
                    Spacer(modifier = Modifier.height(2.dp))
                    val progressFraction = (currentProgress / totalDuration).coerceIn(0f, 1f)
                    LinearProgressIndicator(
                        progress = { progressFraction },
                        modifier = Modifier
                            .fillMaxWidth(0.92f)
                            .height(3.dp)
                            .clip(RoundedCornerShape(2.dp)),
                        color = SkyBluePrimary,
                        trackColor = SkyBluePrimary.copy(alpha = 0.2f),
                    )
                }
            }

            Spacer(modifier = Modifier.width(4.dp))

            // Play / Pause Button (Colorful circular)
            Surface(
                modifier = Modifier
                    .size(26.dp)
                    .clip(CircleShape)
                    .clickable {
                        Log.d("AudioPlayerBar", "Play button clicked! Current state: $isPlaying")
                        val newState = !isPlaying
                        isPlaying = newState
                        onPlayStateChanged(newState)
                    },
                shape = CircleShape,
                color = if (isPlaying) SunshineYellow else SkyBluePrimary
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (isPlaying) "Pausar" else "Tocar",
                        tint = if (isPlaying) Color.Black else Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}

