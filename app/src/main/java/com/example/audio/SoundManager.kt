package com.example.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.media.MediaPlayer
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.PI
import kotlin.math.sin

/**
 * SoundManager provides low-latency synthesized sounds (Pops, Clicks, Fanfares, Bounces)
 * generated purely mathematically via AudioTrack (zero external files required),
 * as well as robust raw sound playback via Android MediaPlayer.
 */
class SoundManager(private val context: Context) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val cachedPcmTracks = ConcurrentHashMap<String, ByteArray>()
    
    private var ambientPlayer: MediaPlayer? = null
    private var effectsPlayer: MediaPlayer? = null

    /**
     * Crisp, satisfying pop sound for UI interactions, button taps, and card flips
     */
    fun playPop() {
        playPcmSound("POP") { generatePopPcm(900f, 400f, 45) }
    }

    fun playClick() = playPop()
    fun playBubble() = playPop()
    fun playStar() = playVictory()
    fun playError() = playIncorrect()

    /**
     * Bright cheerful chime for correct answers and rewarded actions
     */
    fun playVictory() {
        playPcmSound("VICTORY") { generateArpeggioPcm(listOf(523.25f, 659.25f, 783.99f, 1046.50f), 80) }
    }

    /**
     * Soft, non-punitive boop for incorrect matches or invalid moves
     */
    fun playIncorrect() {
        playPcmSound("INCORRECT") { generateIncorrectPcm() }
    }

    /**
     * Playful bouncing sound for mascot taps and animations
     */
    fun playBounce() {
        playPcmSound("BOUNCE") { generatePopPcm(800f, 350f, 60) }
    }

    /**
     * Triumphant combo match fanfare
     */
    fun playCombo() {
        playPcmSound("COMBO") { generateArpeggioPcm(listOf(440f, 554.37f, 659.25f, 880f, 1108.73f), 65) }
    }

    /**
     * Starts playing soft background ambient music in a loop
     */
    fun startAmbientMusic(volume: Float = 0.35f) {
        scope.launch(Dispatchers.Main) {
            try {
                if (ambientPlayer?.isPlaying == true) return@launch
                try {
                    ambientPlayer?.stop()
                    ambientPlayer?.release()
                } catch (_: Throwable) {}
                ambientPlayer = null

                val rawId = context.resources.getIdentifier("ze_traquina_e_fixe", "raw", context.packageName)
                if (rawId != 0) {
                    val player = MediaPlayer.create(context, rawId)
                    if (player != null) {
                        player.isLooping = true
                        player.setVolume(volume, volume)
                        player.start()
                        ambientPlayer = player
                    }
                }
            } catch (e: Throwable) {
                Log.w("SoundManager", "Error starting ambient music: ${e.message}")
            }
        }
    }

    /**
     * Stops the background ambient music
     */
    fun stopAmbientMusic() {
        scope.launch(Dispatchers.Main) {
            try {
                ambientPlayer?.let { p ->
                    try { p.stop() } catch (_: Throwable) {}
                    try { p.release() } catch (_: Throwable) {}
                }
                ambientPlayer = null
            } catch (e: Throwable) {
                Log.e("SoundManager", "Error stopping ambient music", e)
            }
        }
    }

    /**
     * Asynchronously plays raw audio resources using MediaPlayer.
     * Falls back to synthesized victory sound if raw resource is missing.
     */
    fun playSound(resId: Int) {
        scope.launch(Dispatchers.Main) {
            try {
                effectsPlayer?.let { p ->
                    try { p.stop() } catch (_: Throwable) {}
                    try { p.release() } catch (_: Throwable) {}
                }
                effectsPlayer = null
                if (resId != 0) {
                    val player = MediaPlayer.create(context, resId)
                    if (player != null) {
                        player.setOnCompletionListener { mp ->
                            try { mp.release() } catch (_: Throwable) {}
                            if (effectsPlayer == mp) effectsPlayer = null
                        }
                        player.setOnErrorListener { mp, _, _ ->
                            try { mp.release() } catch (_: Throwable) {}
                            if (effectsPlayer == mp) effectsPlayer = null
                            playVictory()
                            true
                        }
                        player.start()
                        effectsPlayer = player
                    } else {
                        playVictory()
                    }
                } else {
                    playVictory()
                }
            } catch (e: Throwable) {
                Log.w("SoundManager", "Error playing sound: ${e.message}")
                playVictory()
            }
        }
    }

    private fun playPcmSound(key: String, generator: () -> ByteArray) {
        scope.launch {
            try {
                val pcmData = cachedPcmTracks[key] ?: generator().also { cachedPcmTracks[key] = it }
                playAudioTrackPcm(pcmData)
            } catch (e: Throwable) {
                Log.e("SoundManager", "Error playing PCM sound: $key", e)
            }
        }
    }

    private suspend fun playAudioTrackPcm(pcmData: ByteArray) {
        val sampleRate = 22050
        val bufferSize = AudioTrack.getMinBufferSize(
            sampleRate,
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        ).coerceAtLeast(pcmData.size)

        var audioTrack: AudioTrack? = null
        try {
            audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(bufferSize)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            audioTrack.write(pcmData, 0, pcmData.size)
            audioTrack.play()

            // Wait duration of playback before release
            val durationMs = (pcmData.size.toDouble() / (sampleRate * 2) * 1000).toLong() + 30
            kotlinx.coroutines.delay(durationMs)
        } catch (e: Throwable) {
            Log.e("SoundManager", "Error playing AudioTrack PCM", e)
        } finally {
            try {
                audioTrack?.stop()
                audioTrack?.release()
            } catch (_: Throwable) {}
        }
    }

    private fun generatePopPcm(startFreq: Float, endFreq: Float, durationMs: Int): ByteArray {
        val sampleRate = 22050
        val totalSamples = (sampleRate * (durationMs / 1000.0)).toInt().coerceAtLeast(1)
        val pcm = ByteArray(totalSamples * 2)

        for (i in 0 until totalSamples) {
            val progress = i.toFloat() / totalSamples
            val currentFreq = startFreq + (endFreq - startFreq) * progress
            val amplitude = (1.0f - progress) * 0.9f
            val sampleVal = (sin(2.0 * PI * currentFreq * i / sampleRate) * amplitude * 32767).toInt().coerceIn(-32768, 32767)

            pcm[i * 2] = (sampleVal and 0xFF).toByte()
            pcm[i * 2 + 1] = ((sampleVal shr 8) and 0xFF).toByte()
        }
        return pcm
    }

    private fun generateArpeggioPcm(frequencies: List<Float>, noteDurationMs: Int): ByteArray {
        val sampleRate = 22050
        val noteSamples = (sampleRate * (noteDurationMs / 1000.0)).toInt()
        val totalSamples = noteSamples * frequencies.size
        val pcm = ByteArray(totalSamples * 2)

        var pcmIndex = 0
        for (freq in frequencies) {
            for (i in 0 until noteSamples) {
                val progress = i.toFloat() / noteSamples
                val envelope = when {
                    progress < 0.1f -> progress / 0.1f
                    else -> 1.0f - (progress - 0.1f) / 0.9f
                } * 0.85f

                val sampleVal = (sin(2.0 * PI * freq * i / sampleRate) * envelope * 32767).toInt().coerceIn(-32768, 32767)
                pcm[pcmIndex++] = (sampleVal and 0xFF).toByte()
                pcm[pcmIndex++] = ((sampleVal shr 8) and 0xFF).toByte()
            }
        }
        return pcm
    }

    private fun generateIncorrectPcm(): ByteArray {
        val sampleRate = 22050
        val durationMs = 180
        val totalSamples = (sampleRate * (durationMs / 1000.0)).toInt()
        val pcm = ByteArray(totalSamples * 2)

        for (i in 0 until totalSamples) {
            val progress = i.toFloat() / totalSamples
            val freq = 220.0f - (progress * 60.0f)
            val envelope = (1.0f - progress) * 0.7f
            val sampleVal = (sin(2.0 * PI * freq * i / sampleRate) * envelope * 32767).toInt().coerceIn(-32768, 32767)

            pcm[i * 2] = (sampleVal and 0xFF).toByte()
            pcm[i * 2 + 1] = ((sampleVal shr 8) and 0xFF).toByte()
        }
        return pcm
    }

    fun release() {
        stopAmbientMusic()
        try {
            effectsPlayer?.stop()
            effectsPlayer?.release()
            effectsPlayer = null
        } catch (_: Throwable) {}
        scope.cancel()
    }
}
