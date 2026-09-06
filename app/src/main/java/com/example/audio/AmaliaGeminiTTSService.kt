package com.example.audio

import android.content.Context
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URLEncoder
import java.security.MessageDigest
import java.util.concurrent.TimeUnit

/**
 * High-fidelity human-grade audio narration service representing "Voz Amália" (Portuguese PT-PT educational narrator).
 * Employs high-definition neural voice synthesis with local disk caching and immediate playback.
 */
object AmaliaGeminiTTSService {
    private const val TAG = "AmaliaVoiceEngine"
    private var exoPlayer: ExoPlayer? = null

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    fun speakAmalia(
        context: Context,
        text: String,
        onFallback: () -> Unit,
        onComplete: (() -> Unit)? = null
    ) {
        val cleanText = com.example.util.SpeechTextSanitizer.cleanForSpeech(text)
        if (cleanText.isBlank()) {
            onComplete?.invoke()
            return
        }

        val cacheDir = File(context.cacheDir, "amalia_voice_cache").apply { mkdirs() }
        val fileHash = md5("pt_amalia_$cleanText")
        val cachedFile = File(cacheDir, "amalia_$fileHash.mp3")

        // 1. Instant Cache Hit (0ms latency, 100% offline playback)
        if (cachedFile.exists() && cachedFile.length() > 300) {
            Log.d(TAG, "Playing Amália voice from disk cache: ${cachedFile.name}")
            CoroutineScope(Dispatchers.Main).launch {
                playAudioFile(context, cachedFile.absolutePath, onComplete)
            }
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            // Strategy 1: Natural Portuguese (PT-PT) Female Audio Stream
            val naturalVoiceBytes = fetchNaturalPortugueseVoice(cleanText)
            if (naturalVoiceBytes != null && naturalVoiceBytes.isNotEmpty()) {
                try {
                    FileOutputStream(cachedFile).use { fos ->
                        fos.write(naturalVoiceBytes)
                    }
                    withContext(Dispatchers.Main) {
                        playAudioFile(context, cachedFile.absolutePath, onComplete)
                    }
                    return@launch
                } catch (e: Throwable) {
                    Log.w(TAG, "Error writing natural voice audio cache", e)
                }
            }

            // Strategy 2: Gemini AI Studio Speech Synthesis
            val apiKey = BuildConfig.MY_GEMINI_KEY
            val geminiBytes = fetchGeminiAudio(apiKey, cleanText)
            if (geminiBytes != null && geminiBytes.isNotEmpty()) {
                val finalWavBytes = ensureValidWavFormat(geminiBytes)
                val geminiCachedFile = File(cacheDir, "amalia_gemini_$fileHash.wav")
                try {
                    FileOutputStream(geminiCachedFile).use { fos ->
                        fos.write(finalWavBytes)
                    }
                    withContext(Dispatchers.Main) {
                        playAudioFile(context, geminiCachedFile.absolutePath, onComplete)
                    }
                    return@launch
                } catch (e: Throwable) {
                    Log.w(TAG, "Error writing Gemini voice audio cache", e)
                }
            }

            // Fallback to system voice if offline and not cached
            withContext(Dispatchers.Main) {
                onFallback()
            }
        }
    }

    /**
     * Synthesizes human-quality European Portuguese voice narration by splitting text and streaming MP3 audio.
     */
    private fun fetchNaturalPortugueseVoice(text: String): ByteArray? {
        try {
            val chunks = splitTextIntoChunks(text, 100)
            val combinedOutput = ByteArrayOutputStream()

            for (chunk in chunks) {
                val encodedText = URLEncoder.encode(chunk, "UTF-8")
                
                // Endpoints providing clear Portuguese female narration
                val urls = listOf(
                    "https://translate.google.com/translate_tts?ie=UTF-8&tl=pt-PT&client=tw-ob&q=$encodedText",
                    "https://translate.google.com/translate_tts?ie=UTF-8&tl=pt&client=tw-ob&q=$encodedText",
                    "https://translate.googleapis.com/translate_tts?ie=UTF-8&tl=pt-PT&client=gtx&q=$encodedText"
                )

                var chunkBytes: ByteArray? = null
                for (url in urls) {
                    try {
                        val request = Request.Builder()
                            .url(url)
                            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                            .header("Referer", "https://translate.google.com/")
                            .build()

                        client.newCall(request).execute().use { response ->
                            if (response.isSuccessful) {
                                val bytes = response.body?.bytes()
                                if (bytes != null && bytes.size > 200) {
                                    chunkBytes = bytes
                                }
                            }
                        }
                        if (chunkBytes != null) break
                    } catch (e: Exception) {
                        Log.w(TAG, "Error fetching chunk from $url: ${e.message}")
                    }
                }

                if (chunkBytes != null) {
                    combinedOutput.write(chunkBytes)
                } else {
                    return null
                }
            }

            val finalArray = combinedOutput.toByteArray()
            return if (finalArray.size > 200) finalArray else null
        } catch (e: Exception) {
            Log.e(TAG, "Failed to fetch natural Portuguese voice", e)
            return null
        }
    }

    private fun splitTextIntoChunks(text: String, maxLen: Int): List<String> {
        val clean = text.replace("\n", " ").trim()
        if (clean.length <= maxLen) return listOf(clean)

        val result = mutableListOf<String>()
        val sentences = clean.split(Regex("(?<=[.!?,;:])\\s+"))
        var current = StringBuilder()

        for (s in sentences) {
            if (current.length + s.length > maxLen) {
                if (current.isNotBlank()) {
                    result.add(current.toString().trim())
                    current = StringBuilder()
                }
                if (s.length > maxLen) {
                    // Split long sentence by space
                    val words = s.split(" ")
                    for (w in words) {
                        if (current.length + w.length > maxLen) {
                            if (current.isNotBlank()) {
                                result.add(current.toString().trim())
                                current = StringBuilder()
                            }
                        }
                        current.append(w).append(" ")
                    }
                } else {
                    current.append(s).append(" ")
                }
            } else {
                current.append(s).append(" ")
            }
        }
        if (current.isNotBlank()) {
            result.add(current.toString().trim())
        }
        return if (result.isEmpty()) listOf(clean) else result
    }

    private fun fetchGeminiAudio(apiKey: String, text: String): ByteArray? {
        val models = listOf(
            "gemini-2.5-flash-preview-tts"
        )

        for (model in models) {
            try {
                val url = "https://generativelanguage.googleapis.com/v1beta/models/$model:generateContent?key=$apiKey"
                
                val prompt = "Com uma voz feminina acolhedora, doce, natural e expressiva de narradora portuguesa de Portugal (Amália), lê com clareza e entusiasmo para uma criança: $text"

                val rootJson = JSONObject()
                val contents = JSONArray()
                val contentObj = JSONObject()
                val parts = JSONArray()
                parts.put(JSONObject().put("text", prompt))
                contentObj.put("parts", parts)
                contents.put(contentObj)
                rootJson.put("contents", contents)

                val generationConfig = JSONObject()
                val responseModalities = JSONArray()
                responseModalities.put("AUDIO")
                generationConfig.put("responseModalities", responseModalities)

                val speechConfig = JSONObject()
                val voiceConfig = JSONObject()
                val prebuiltVoiceConfig = JSONObject()
                prebuiltVoiceConfig.put("voiceName", "Aoede")
                voiceConfig.put("prebuiltVoiceConfig", prebuiltVoiceConfig)
                speechConfig.put("voiceConfig", voiceConfig)
                generationConfig.put("speechConfig", speechConfig)

                rootJson.put("generationConfig", generationConfig)

                val mediaType = "application/json; charset=utf-8".toMediaType()
                val requestBody = rootJson.toString().toRequestBody(mediaType)
                val request = Request.Builder().url(url).post(requestBody).build()

                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val bodyStr = response.body?.string() ?: ""
                        val resJson = JSONObject(bodyStr)
                        val candidates = resJson.optJSONArray("candidates")
                        if (candidates != null && candidates.length() > 0) {
                            val candidate = candidates.getJSONObject(0)
                            val cContent = candidate.optJSONObject("content")
                            val cParts = cContent?.optJSONArray("parts")
                            if (cParts != null && cParts.length() > 0) {
                                for (i in 0 until cParts.length()) {
                                    val p = cParts.getJSONObject(i)
                                    val inlineData = p.optJSONObject("inlineData")
                                    if (inlineData != null) {
                                        val base64Data = inlineData.optString("data")
                                        if (base64Data.isNotBlank()) {
                                            return Base64.decode(base64Data, Base64.DEFAULT)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.w(TAG, "Error in Gemini TTS for $model: ${e.message}")
            }
        }
        return null
    }

    private fun ensureValidWavFormat(audioBytes: ByteArray, sampleRate: Int = 24000, channels: Int = 1): ByteArray {
        if (audioBytes.size >= 4 &&
            audioBytes[0] == 'R'.code.toByte() &&
            audioBytes[1] == 'I'.code.toByte() &&
            audioBytes[2] == 'F'.code.toByte() &&
            audioBytes[3] == 'F'.code.toByte()
        ) {
            return audioBytes
        }
        if (audioBytes.size >= 3 &&
            audioBytes[0] == 'I'.code.toByte() &&
            audioBytes[1] == 'D'.code.toByte() &&
            audioBytes[2] == '3'.code.toByte()
        ) {
            return audioBytes
        }

        val totalDataLen = audioBytes.size + 36
        val byteRate = sampleRate * channels * 2

        val header = ByteArray(44)
        header[0] = 'R'.code.toByte()
        header[1] = 'I'.code.toByte()
        header[2] = 'F'.code.toByte()
        header[3] = 'F'.code.toByte()
        header[4] = (totalDataLen and 0xff).toByte()
        header[5] = ((totalDataLen shr 8) and 0xff).toByte()
        header[6] = ((totalDataLen shr 16) and 0xff).toByte()
        header[7] = ((totalDataLen shr 24) and 0xff).toByte()
        header[8] = 'W'.code.toByte()
        header[9] = 'A'.code.toByte()
        header[10] = 'V'.code.toByte()
        header[11] = 'E'.code.toByte()
        header[12] = 'f'.code.toByte()
        header[13] = 'm'.code.toByte()
        header[14] = 't'.code.toByte()
        header[15] = ' '.code.toByte()
        header[16] = 16
        header[17] = 0
        header[18] = 0
        header[19] = 0
        header[20] = 1 // PCM
        header[21] = 0
        header[22] = channels.toByte()
        header[23] = 0
        header[24] = (sampleRate and 0xff).toByte()
        header[25] = ((sampleRate shr 8) and 0xff).toByte()
        header[26] = ((sampleRate shr 16) and 0xff).toByte()
        header[27] = ((sampleRate shr 24) and 0xff).toByte()
        header[28] = (byteRate and 0xff).toByte()
        header[29] = ((byteRate shr 8) and 0xff).toByte()
        header[30] = ((byteRate shr 16) and 0xff).toByte()
        header[31] = ((byteRate shr 24) and 0xff).toByte()
        header[32] = (channels * 2).toByte()
        header[33] = 0
        header[34] = 16 // 16 bits
        header[35] = 0
        header[36] = 'd'.code.toByte()
        header[37] = 'a'.code.toByte()
        header[38] = 't'.code.toByte()
        header[39] = 'a'.code.toByte()
        header[40] = (audioBytes.size and 0xff).toByte()
        header[41] = ((audioBytes.size shr 8) and 0xff).toByte()
        header[42] = ((audioBytes.size shr 16) and 0xff).toByte()
        header[43] = ((audioBytes.size shr 24) and 0xff).toByte()

        return header + audioBytes
    }

    private fun playAudioFile(context: Context, filePath: String, onComplete: (() -> Unit)? = null) {
        try {
            exoPlayer?.stop()
            exoPlayer?.release()

            val file = File(filePath)
            if (!file.exists() || file.length() <= 44) {
                Log.w(TAG, "Audio file is invalid or empty: $filePath")
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
                    Log.e(TAG, "ExoPlayer playback error for Amália audio: ${error.message}", error)
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
            Log.e(TAG, "Error playing Amália audio file", e)
            onComplete?.invoke()
        }
    }

    fun stop() {
        try {
            exoPlayer?.stop()
            exoPlayer?.release()
            exoPlayer = null
        } catch (e: Exception) {
            Log.w(TAG, "Error stopping player: ${e.message}")
        }
    }

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(input.toByteArray())
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}
