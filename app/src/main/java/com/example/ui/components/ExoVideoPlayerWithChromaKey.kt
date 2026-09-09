package com.example.ui.components

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import android.os.Build
import android.util.Log
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.PlayerView
import com.example.R
import com.example.util.PreviewConfig

/**
 * A video player that uses AGSL shader to remove chroma key (red) in real-time.
 * Requires API 33+ for RuntimeShader and RenderEffect.
 */
@androidx.media3.common.util.UnstableApi
@Composable
fun ExoVideoPlayerWithChromaKey(
    videoUrl: String,
    modifier: Modifier = Modifier,
    onError: (String) -> Unit = {}
) {
    val isEmulator = remember { com.example.util.EmulatorUtils.isEmulator } 
    val _ignored = remember {
        val finger = android.os.Build.FINGERPRINT ?: ""
        val model = android.os.Build.MODEL ?: ""
        val prod = android.os.Build.PRODUCT ?: ""
        finger.contains("generic") ||
                model.contains("google_sdk") ||
                model.contains("Emulator") ||
                model.contains("Android SDK built for x86") ||
                prod.contains("sdk_gphone") ||
                prod.contains("google_sdk")
    }

    if (LocalInspectionMode.current || PreviewConfig.isInPreview()) {
        Box(
            modifier = modifier
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ze_mascot_green_polo),
                contentDescription = "Preview Zé Traquina",
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = "Preview Video",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
        return
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !isEmulator) {
        ChromaKeyExoVideoPlayer(videoUrl, modifier, onError)
    } else {
        StandardExoVideoPlayer(videoUrl, modifier, onError)
    }
}

@androidx.media3.common.util.UnstableApi
@Composable
private fun StandardExoVideoPlayer(
    videoUrl: String,
    modifier: Modifier = Modifier,
    onError: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val exoPlayer = remember(videoUrl) {
        if (com.example.util.MediaFailureTracker.isUrlFailed(videoUrl)) return@remember null
        try {
            val httpDataSourceFactory = DefaultHttpDataSource.Factory()
                .setUserAgent("Mozilla/5.0 (Linux; Android 13; Mobile) AppleWebKit/537.36")
                .setAllowCrossProtocolRedirects(true)

            val dataSourceFactory = androidx.media3.datasource.DefaultDataSource.Factory(
                context,
                httpDataSourceFactory
            )

            val mediaSourceFactory = DefaultMediaSourceFactory(context)
                .setDataSourceFactory(dataSourceFactory)

            val renderersFactory = com.example.util.ExoPlayerHelper.createRenderersFactory(context)

            val loadControl = com.example.util.ExoPlayerHelper.createLoadControl()

            val resolvedUri = com.example.util.ZeAvatarCacheManager.getMediaUri(context, videoUrl)

            ExoPlayer.Builder(context, renderersFactory)
                .setMediaSourceFactory(mediaSourceFactory)
                .setLoadControl(loadControl)
                .build().apply {
                    if (!com.example.util.ZeAvatarCacheManager.isCached(context, videoUrl) && com.example.util.MediaFailureTracker.isUrlFailed(resolvedUri.toString())) {
                        Log.w("StandardExoPlayer", "Skipping known failed URL: $resolvedUri")
                        return@apply
                    }
                    val mediaItem = MediaItem.Builder()
                        .setUri(resolvedUri)
                        .build()
                    setMediaItem(mediaItem)
                    repeatMode = Player.REPEAT_MODE_ONE
                    val player = this
                    addListener(object : Player.Listener {
                        override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                            Log.e("StandardExoPlayer", "ExoPlayer error: [${error.errorCodeName}] ${error.message}", error)
                            try {
                                player.stop()
                                player.clearMediaItems()
                            } catch (_: Throwable) {}
                            if (resolvedUri.toString().isNotEmpty()) {
                                com.example.util.MediaFailureTracker.addFailedUrl(resolvedUri.toString())
                            }
                            try {
                                if (videoUrl.isNotEmpty()) {
                                    val cacheFile = com.example.util.ZeAvatarCacheManager.getCacheFile(context, videoUrl)
                                    if (cacheFile.exists()) {
                                        cacheFile.delete()
                                    }
                                    val currentUri = currentMediaItem?.localConfiguration?.uri
                                    if (currentUri != null && currentUri.scheme == "file") {
                                        Log.i("StandardExoPlayer", "Falling back to remote network URL for avatar: $videoUrl")
                                        setMediaItem(MediaItem.fromUri(android.net.Uri.parse(videoUrl)))
                                        prepare()
                                        play()
                                        return
                                    }
                                }
                            } catch (_: Throwable) {}
                            onError("ExoPlayer playback error: ${error.message}")
                        }
                    })
                    prepare()
                    playWhenReady = true
                    volume = 0f
                }
        } catch (e: Exception) {
            onError("Failed to initialize ExoPlayer: ${e.message}")
            null
        }
    }

    if (exoPlayer == null) return

    DisposableEffect(exoPlayer) {
        onDispose {
            try {
                exoPlayer.stop()
                exoPlayer.release()
            } catch (e: Exception) {
                Log.e("StandardExoPlayer", "Error releasing ExoPlayer", e)
            }
        }
    }

    Box(modifier = modifier) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                try {
                    (android.view.LayoutInflater.from(ctx).inflate(R.layout.exo_texture_player, null) as PlayerView).apply {
                        useController = false
                        player = exoPlayer
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }
                } catch (e: Exception) {
                    Log.e("StandardExoPlayer", "Error creating PlayerView: ${e.message}", e)
                    android.view.View(ctx)
                }
            },
            update = { playerView ->
                if (playerView is PlayerView && playerView.player != exoPlayer) {
                    playerView.player = exoPlayer
                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@androidx.media3.common.util.UnstableApi
@Composable
private fun ChromaKeyExoVideoPlayer(
    videoUrl: String,
    modifier: Modifier = Modifier,
    onError: (String) -> Unit = {}
) {
    val context = LocalContext.current
    var hasTextureAvailable by remember { mutableStateOf(false) }

    val exoPlayer = remember(videoUrl) {
        if (com.example.util.MediaFailureTracker.isUrlFailed(videoUrl)) return@remember null
        try {
            val httpDataSourceFactory = DefaultHttpDataSource.Factory()
                .setUserAgent("Mozilla/5.0 (Linux; Android 13; Mobile) AppleWebKit/537.36")
                .setAllowCrossProtocolRedirects(true)

            val mediaSourceFactory = DefaultMediaSourceFactory(context)
                .setDataSourceFactory(httpDataSourceFactory)

            val renderersFactory = com.example.util.ExoPlayerHelper.createRenderersFactory(context)

            val loadControl = com.example.util.ExoPlayerHelper.createLoadControl()

            ExoPlayer.Builder(context, renderersFactory)
                .setMediaSourceFactory(mediaSourceFactory)
                .setLoadControl(loadControl)
                .build().apply {
                    if (!com.example.util.ZeAvatarCacheManager.isCached(context, videoUrl) && com.example.util.MediaFailureTracker.isUrlFailed(videoUrl)) {
                        Log.w("ExoVideoPlayer", "Skipping known failed URL: $videoUrl")
                        return@apply
                    }
                    setMediaItem(MediaItem.fromUri(videoUrl))
                    repeatMode = Player.REPEAT_MODE_ONE
                    val player = this
                    addListener(object : Player.Listener {
                        override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                            Log.e("ExoVideoPlayer", "ExoPlayer error: [${error.errorCodeName}] ${error.message}", error)
                            try {
                                player.stop()
                                player.clearMediaItems()
                            } catch (_: Throwable) {}
                            if (videoUrl.isNotBlank()) {
                                com.example.util.MediaFailureTracker.addFailedUrl(videoUrl)
                            }
                            onError("ExoPlayer playback error: ${error.message}")
                        }
                    })
                    prepare()
                    playWhenReady = true
                    volume = 0f
                }
        } catch (e: Exception) {
            onError("Failed to initialize ExoPlayer: ${e.message}")
            null
        }
    }

    if (exoPlayer == null) return

    val chromaKeyShader = """
        uniform shader uTexture;

        half4 main(float2 fragCoord) {
            half4 color = uTexture.eval(fragCoord);
            
            float r = float(color.r);
            float g = float(color.g);
            float b = float(color.b);
            
            // Calculate luminance to determine background
            float luminance = (r + g + b) / 3.0;
            
            // Adjust smoothstep to make the character less transparent
            // Lowering the first value increases the range of colors treated as "character"
            float alpha = smoothstep(0.01, 0.05, luminance);
            
            return half4(color.rgb * alpha, alpha);
        }
    """.trimIndent()

    val runtimeShader = remember {
        try {
            RuntimeShader(chromaKeyShader)
        } catch (e: Exception) {
            Log.e("ExoVideoPlayer", "Failed to compile RuntimeShader: ${e.message}", e)
            null
        }
    }

    val renderEffect = remember(runtimeShader) {
        if (runtimeShader != null) {
            try {
                RenderEffect.createRuntimeShaderEffect(runtimeShader, "uTexture")
            } catch (e: Exception) {
                Log.e("ExoVideoPlayer", "Failed to create RenderEffect: ${e.message}", e)
                null
            }
        } else null
    }

    DisposableEffect(exoPlayer) {
        onDispose {
            try {
                exoPlayer.stop()
                exoPlayer.release()
            } catch (e: Exception) {
                Log.e("ExoVideoPlayer", "Error releasing ExoPlayer", e)
            }
        }
    }

    Box(modifier = modifier) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    if (renderEffect != null) {
                        try {
                            this.renderEffect = renderEffect.asComposeRenderEffect()
                        } catch (e: Exception) {
                            Log.e("ExoVideoPlayer", "Failed to apply compose render effect", e)
                        }
                    }
                },
            factory = { ctx ->
                try {
                    (android.view.LayoutInflater.from(ctx).inflate(R.layout.exo_texture_player, null) as androidx.media3.ui.PlayerView).apply {
                        useController = false
                        resizeMode = androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_FILL
                        player = exoPlayer
                        setKeepContentOnPlayerReset(true)
                    }
                } catch (e: Exception) {
                    Log.e("ExoVideoPlayer", "Failed to inflate PlayerView", e)
                    androidx.media3.ui.PlayerView(ctx).apply {
                        useController = false
                        resizeMode = androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_FILL
                        player = exoPlayer
                    }
                }
            },
            update = { playerView ->
                if (playerView.player != exoPlayer) {
                    playerView.player = exoPlayer
                }
            },
            onRelease = { playerView ->
                playerView.player = null
            }
        )
    }
}
