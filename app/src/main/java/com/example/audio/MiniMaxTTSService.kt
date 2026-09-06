package com.example.audio

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import android.net.Uri
import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

object MiniMaxTTSService {
    private var exoPlayer: ExoPlayer? = null

    fun speakMiniMax(
        context: Context,
        text: String,
        apiKey: String,
        groupId: String,
        voiceId: String,
        onFallback: () -> Unit,
        onComplete: (() -> Unit)? = null
    ) {
        val cleanText = com.example.util.SpeechTextSanitizer.cleanForSpeech(text)
        if (cleanText.isBlank() || apiKey.isBlank() || voiceId.isBlank()) {
            onFallback()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val urlString = "https://api.minimax.chat/v1/text_to_speech"
                val url = URL(urlString)
                val conn = url.openConnection() as HttpURLConnection
                try {
                    conn.requestMethod = "POST"
                    conn.setRequestProperty("Content-Type", "application/json")
                    conn.setRequestProperty("Authorization", "Bearer $apiKey")
                    conn.connectTimeout = 8000
                    conn.readTimeout = 8000
                    conn.doOutput = true

                    val payload = JSONObject().apply {
                        put("model", "speech-01-turbo")
                        put("text", cleanText)
                        put("group_id", groupId)
                        put("voice_setting", JSONObject().apply {
                            put("voice_id", voiceId)
                        })
                    }

                    conn.outputStream.use { os ->
                        os.write(payload.toString().toByteArray(Charsets.UTF_8))
                    }

                    val responseCode = conn.responseCode
                    if (responseCode == 200) {
                        val responseBytes = conn.inputStream.readBytes()

                        if (responseBytes.size > 200) {
                            val tempAudioFile = File(context.cacheDir, "minimax_tts_temp.mp3")
                            FileOutputStream(tempAudioFile).use { fos ->
                                fos.write(responseBytes)
                            }

                            withContext(Dispatchers.Main) {
                                playAudioFile(context, tempAudioFile.absolutePath, onComplete)
                            }
                            return@launch
                        } else {
                            Log.e("MiniMaxTTS", "Response bytes too small: ${responseBytes.size}")
                        }
                    } else {
                        val errorStream = conn.errorStream?.bufferedReader()?.use { it.readText() } ?: "No error stream"
                        Log.e("MiniMaxTTS", "MiniMax HTTP Error $responseCode: $errorStream")
                    }
                } finally {
                    conn.disconnect()
                }
            } catch (e: Exception) {
                Log.e("MiniMaxTTS", "Failed to synthesize speech via MiniMax.io API", e)
                withContext(Dispatchers.Main) {
                    onFallback()
                }
            }
        }
    }

    private fun playAudioFile(context: Context, filePath: String, onComplete: (() -> Unit)? = null) {
        try {
            exoPlayer?.stop()
            exoPlayer?.release()

            val file = File(filePath)
            if (!file.exists() || file.length() <= 44) {
                Log.w("MiniMaxTTS", "Audio file is invalid or empty: $filePath")
                onComplete?.invoke()
                return
            }

            val player = com.example.util.ExoPlayerHelper.createExoPlayer(context)
            if (player == null) {
                onComplete?.invoke()
                return
            }
            val mediaItem = MediaItem.fromUri(Uri.fromFile(file))
            player.setMediaItem(mediaItem)
            
            player.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    if (state == Player.STATE_ENDED) {
                        onComplete?.invoke()
                    }
                }

                override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                    Log.e("MiniMaxTTS", "ExoPlayer playback error for MiniMax audio: ${error.message}", error)
                    try {
                        exoPlayer?.stop()
                        exoPlayer?.clearMediaItems()
                        exoPlayer?.release()
                    } catch (_: Throwable) {}
                    exoPlayer = null
                    onComplete?.invoke()
                }
            })

            player.prepare()
            player.play()
            exoPlayer = player
        } catch (e: Exception) {
            Log.e("MiniMaxTTS", "Error playing generated MiniMax audio file", e)
            onComplete?.invoke()
        }
    }

    fun stop() {
        try {
            exoPlayer?.stop()
            exoPlayer?.release()
            exoPlayer = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
