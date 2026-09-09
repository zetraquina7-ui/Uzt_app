package com.example.util

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.annotation.OptIn
import androidx.annotation.RawRes
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.mediacodec.MediaCodecSelector
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.extractor.DefaultExtractorsFactory
import java.io.File

@OptIn(UnstableApi::class)
object ExoPlayerHelper {

    fun createExtractorsFactory(): DefaultExtractorsFactory {
        return DefaultExtractorsFactory()
            .setConstantBitrateSeekingEnabled(true)
    }

    fun createMediaSourceFactory(context: Context): DefaultMediaSourceFactory {
        val httpDataSourceFactory = DefaultHttpDataSource.Factory()
            .setUserAgent("Mozilla/5.0 (Linux; Android 10; Mobile) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36")
            .setConnectTimeoutMs(15000)
            .setReadTimeoutMs(15000)
            .setAllowCrossProtocolRedirects(true)

        val dataSourceFactory = DefaultDataSource.Factory(context.applicationContext, httpDataSourceFactory)
        return DefaultMediaSourceFactory(dataSourceFactory, createExtractorsFactory())
    }

    fun getRawResourceUri(context: Context, @RawRes rawResId: Int): Uri {
        return RawResourceDataSource.buildRawResourceUri(rawResId)
    }

    fun getRawResourceAsFileUri(context: Context, @RawRes rawResId: Int, fileName: String): Uri {
        val cacheFile = File(context.cacheDir, fileName)
        var shouldCopy = !cacheFile.exists() || cacheFile.length() <= 0L
        if (!shouldCopy) {
            try {
                context.resources.openRawResourceFd(rawResId).use { afd ->
                    val actualLength = afd.length
                    if (actualLength > 0L && actualLength != cacheFile.length()) {
                        Log.w("ExoPlayerHelper", "Cache file length mismatch. Expected: $actualLength, Actual cache: ${cacheFile.length()}. Re-copying.")
                        shouldCopy = true
                    }
                }
            } catch (e: Exception) {
                Log.w("ExoPlayerHelper", "Failed to verify size via openRawResourceFd. Checking cache size.", e)
                if (cacheFile.length() <= 0L) {
                    shouldCopy = true
                }
            }
        }

        if (shouldCopy) {
            try {
                if (cacheFile.exists()) {
                    cacheFile.delete()
                }
                context.resources.openRawResource(rawResId).use { inputStream ->
                    cacheFile.outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                Log.d("ExoPlayerHelper", "Copied raw resource to cache: $fileName (${cacheFile.length()} bytes)")
            } catch (e: Exception) {
                Log.e("ExoPlayerHelper", "Failed to copy raw resource to cache: $fileName", e)
                try {
                    cacheFile.delete()
                } catch (de: Exception) {}
                return getRawResourceUri(context, rawResId)
            }
        } else {
            Log.d("ExoPlayerHelper", "Using existing cached raw resource: $fileName (${cacheFile.length()} bytes)")
        }
        return Uri.fromFile(cacheFile)
    }

    open class SafeRenderersFactory(
        context: Context,
        private val isAudioOnly: Boolean = false,
        private val isVideoOnly: Boolean = false
    ) : DefaultRenderersFactory(context.applicationContext) {

        override fun buildAudioRenderers(
            context: Context,
            extensionRendererMode: Int,
            mediaCodecSelector: MediaCodecSelector,
            enableDecoderFallback: Boolean,
            audioSink: androidx.media3.exoplayer.audio.AudioSink,
            eventHandler: android.os.Handler,
            eventListener: androidx.media3.exoplayer.audio.AudioRendererEventListener,
            out: java.util.ArrayList<androidx.media3.exoplayer.Renderer>
        ) {
            if (!isVideoOnly) {
                super.buildAudioRenderers(
                    context,
                    extensionRendererMode,
                    mediaCodecSelector,
                    enableDecoderFallback,
                    audioSink,
                    eventHandler,
                    eventListener,
                    out
                )
            }
        }

        override fun buildVideoRenderers(
            context: Context,
            extensionRendererMode: Int,
            mediaCodecSelector: MediaCodecSelector,
            enableDecoderFallback: Boolean,
            eventHandler: android.os.Handler,
            eventListener: androidx.media3.exoplayer.video.VideoRendererEventListener,
            allowedVideoJoiningTimeMs: Long,
            out: java.util.ArrayList<androidx.media3.exoplayer.Renderer>
        ) {
            if (!isAudioOnly) {
                super.buildVideoRenderers(
                    context,
                    extensionRendererMode,
                    mediaCodecSelector,
                    enableDecoderFallback,
                    eventHandler,
                    eventListener,
                    allowedVideoJoiningTimeMs,
                    out
                )
            }
        }
    }

    private val resilientCodecSelector = object : MediaCodecSelector {
        override fun getDecoderInfos(
            mimeType: String,
            requiresSecureDecoder: Boolean,
            requiresTunnelingDecoder: Boolean
        ): MutableList<androidx.media3.exoplayer.mediacodec.MediaCodecInfo> {
            if (EmulatorUtils.isEmulator) {
                try {
                    val softwareDecoders = mutableListOf<androidx.media3.exoplayer.mediacodec.MediaCodecInfo>()
                    val codecList = android.media.MediaCodecList(android.media.MediaCodecList.REGULAR_CODECS)
                    val clazz = androidx.media3.exoplayer.mediacodec.MediaCodecInfo::class.java
                    val constructor = clazz.declaredConstructors.firstOrNull()
                    if (constructor != null) {
                        constructor.isAccessible = true
                        for (info in codecList.codecInfos) {
                            if (info.isEncoder) continue
                            val name = info.name.lowercase()
                            val isSoftware = name.startsWith("c2.android.") || name.startsWith("omx.google.") || 
                                (android.os.Build.VERSION.SDK_INT >= 29 && info.isSoftwareOnly)
                            if (isSoftware) {
                                for (type in info.supportedTypes) {
                                    if (type.equals(mimeType, ignoreCase = true)) {
                                        try {
                                            // Instantiate via reflection matching constructor parameters
                                            val instance = constructor.newInstance(
                                                info.name,
                                                mimeType,
                                                mimeType,
                                                null,
                                                false,
                                                true,
                                                false,
                                                false,
                                                requiresSecureDecoder,
                                                requiresTunnelingDecoder
                                            ) as? androidx.media3.exoplayer.mediacodec.MediaCodecInfo
                                            if (instance != null) {
                                                softwareDecoders.add(instance)
                                            }
                                        } catch (ignored: Throwable) {}
                                    }
                                }
                            }
                        }
                    }
                    if (softwareDecoders.isNotEmpty()) {
                        return softwareDecoders
                    }
                } catch (e: Throwable) {
                    Log.w("ExoPlayerHelper", "Error creating software decoders via reflection on emulator: ${e.message}")
                }
            }

            val decoders = try {
                MediaCodecSelector.DEFAULT.getDecoderInfos(
                    mimeType,
                    requiresSecureDecoder,
                    requiresTunnelingDecoder
                )
            } catch (e: Throwable) {
                Log.w("ExoPlayerHelper", "Failed getting decoder infos for $mimeType: ${e.message}")
                emptyList()
            }
            
            val safeDecoders = decoders.filterNot { decoder ->
                val name = decoder.name.lowercase()
                name.contains("goldfish") || name.contains("ranchu") || name.contains("vbox")
            }

            val targetList = if (safeDecoders.isNotEmpty()) safeDecoders else decoders

            val (softwareDecoders, hwDecoders) = targetList.partition {
                it.softwareOnly || it.name.startsWith("c2.android.") || it.name.startsWith("OMX.google.")
            }

            val ordered = if (mimeType.startsWith("audio/") || EmulatorUtils.isEmulator) {
                (softwareDecoders + hwDecoders).toMutableList()
            } else {
                (hwDecoders + softwareDecoders).toMutableList()
            }
            return if (ordered.isNotEmpty()) ordered else decoders.toMutableList()
        }
    }

    fun createRenderersFactory(
        context: Context,
        extensionMode: Int = DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF,
        isAudioOnly: Boolean = false,
        isVideoOnly: Boolean = false
    ): DefaultRenderersFactory {
        return try {
            SafeRenderersFactory(context.applicationContext, isAudioOnly = isAudioOnly, isVideoOnly = isVideoOnly).apply {
                setEnableDecoderFallback(true)
                setExtensionRendererMode(extensionMode)
                setMediaCodecSelector(resilientCodecSelector)
            }
        } catch (e: Throwable) {
            Log.w("ExoPlayerHelper", "Fallback to default renderers factory: ${e.message}")
            DefaultRenderersFactory(context.applicationContext)
        }
    }

    fun createLoadControl(): androidx.media3.exoplayer.LoadControl {
        return androidx.media3.exoplayer.DefaultLoadControl.Builder()
            .setBufferDurationsMs(
                /* minBufferMs = */ 30_000,
                /* maxBufferMs = */ 60_000,
                /* bufferForPlaybackMs = */ 200,
                /* bufferForPlaybackAfterRebufferMs = */ 400
            )
            .setPrioritizeTimeOverSizeThresholds(true)
            .build()
    }

    fun createExoPlayer(
        context: Context,
        isAudioOnly: Boolean = true,
        renderersFactory: DefaultRenderersFactory = createRenderersFactory(context, isAudioOnly = isAudioOnly)
    ): ExoPlayer? {
        return try {
            ExoPlayer.Builder(context.applicationContext, renderersFactory)
                .setMediaSourceFactory(createMediaSourceFactory(context))
                .setLoadControl(createLoadControl())
                .build()
        } catch (e: Throwable) {
            Log.e("ExoPlayerHelper", "Failed to create ExoPlayer with custom factory", e)
            try {
                ExoPlayer.Builder(context.applicationContext)
                    .setLoadControl(createLoadControl())
                    .build()
            } catch (e2: Throwable) {
                Log.e("ExoPlayerHelper", "Failed fallback ExoPlayer creation", e2)
                null
            }
        }
    }
}
