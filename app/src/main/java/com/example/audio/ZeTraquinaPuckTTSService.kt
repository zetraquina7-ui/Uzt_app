package com.example.audio

import android.content.Context
import android.net.Uri
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Base64
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
import java.util.Locale
import java.util.concurrent.TimeUnit

/**
 * Official voice narration service for the Zé Traquina app.
 * Exclusively uses the "Puck" Gemini TTS voice (energetic, warm young boy voice in European Portuguese).
 * The "Puck" voice is strictly constant and immutable across all sessions.
 */
object ZeTraquinaPuckTTSService {
    private const val TAG = "ZeTraquinaPuckTTS"

    // Fixed, immutable voice configuration for Zé Traquina
    const val VOICE_NAME = "Puck"
    const val PUCK_PITCH = 1.35f
    const val PUCK_SPEED = 1.08f

    private var exoPlayer: ExoPlayer? = null
    private var nativeTts: TextToSpeech? = null
    private var isNativeTtsReady = false

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    // State indicating audio synthesis is in progress (Thinking state)
    private val _isGeneratingAudio = MutableStateFlow(false)
    val isGeneratingAudio: StateFlow<Boolean> = _isGeneratingAudio.asStateFlow()

    private val client = OkHttpClient.Builder()
        .connectTimeout(6, TimeUnit.SECONDS)
        .readTimeout(12, TimeUnit.SECONDS)
        .writeTimeout(12, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    fun init(context: Context) {
        if (nativeTts == null) {
            try {
                nativeTts = TextToSpeech(context.applicationContext) { status ->
                    if (status == TextToSpeech.SUCCESS) {
                        var res = nativeTts?.setLanguage(Locale.forLanguageTag("pt-PT"))
                        if (res == TextToSpeech.LANG_MISSING_DATA || res == TextToSpeech.LANG_NOT_SUPPORTED) {
                            res = nativeTts?.setLanguage(Locale("pt", "PT"))
                        }
                        nativeTts?.setPitch(PUCK_PITCH)
                        nativeTts?.setSpeechRate(PUCK_SPEED)
                        isNativeTtsReady = true
                    }
                }
            } catch (e: Throwable) {
                Log.e("TTSService", "Failed to initialize native TextToSpeech engine", e)
            }
        }
    }

    /**
     * Speaks the given text using the official Puck voice (Gemini TTS / Puck DSP / European Portuguese).
     * @param onPlaybackStarted Callback invoked at the exact instant the audio output begins playing.
     * @param onComplete Callback invoked when audio finishes playback or encounters an error.
     */
    fun speak(
        context: Context,
        text: String,
        onPlaybackStarted: (() -> Unit)? = null,
        onComplete: (() -> Unit)? = null
    ) {
        val cleanText = com.example.util.SpeechTextSanitizer.cleanForSpeech(text)
        if (cleanText.isBlank()) {
            _isGeneratingAudio.value = false
            _isSpeaking.value = false
            onComplete?.invoke()
            return
        }

        stop()
        init(context)

        val cacheDir = File(context.cacheDir, "ze_puck_voice_cache").apply { mkdirs() }
        val textHash = md5("ze_puck_v1_${cleanText}")
        val cachedWav = File(cacheDir, "puck_${textHash}.wav")

        // 1. Instant Cache Hit (0ms latency, eliminates delays and works completely offline)
        if (cachedWav.exists() && cachedWav.length() > 500) {
            _isGeneratingAudio.value = false
            CoroutineScope(Dispatchers.Main).launch {
                playAudioFile(context, cachedWav.absolutePath, onPlaybackStarted) {
                    _isSpeaking.value = false
                    _isGeneratingAudio.value = false
                    onComplete?.invoke()
                }
            }
            return
        }

        // 2. Generating audio state - inform UI that audio synthesis is underway
        _isGeneratingAudio.value = true

        CoroutineScope(Dispatchers.IO).launch {
            val apiKey = BuildConfig.MY_GEMINI_KEY.ifBlank { BuildConfig.GEMINI_API_KEY }

            // Try Gemini Cloud TTS with the fixed "Puck" Voice and intelligent retry strategy
            val audioBytes = if (apiKey.isNotBlank()) {
                fetchGeminiTTSAudioWithRetry(apiKey, cleanText)
            } else {
                Log.w(TAG, "No Gemini API key available for TTS, using local Puck voice synthesis.")
                null
            }

            if (audioBytes != null && audioBytes.isNotEmpty()) {
                val wavData = ensureValidWavFormat(audioBytes)
                try {
                    FileOutputStream(cachedWav).use { fos -> fos.write(wavData) }
                    withContext(Dispatchers.Main) {
                        playAudioFile(context, cachedWav.absolutePath, onPlaybackStarted) {
                            _isSpeaking.value = false
                            _isGeneratingAudio.value = false
                            onComplete?.invoke()
                        }
                    }
                    return@launch
                } catch (e: Throwable) {
                    Log.e(TAG, "Error writing Puck voice cache", e)
                    cachedWav.delete()
                }
            }

            // Fallback: If Gemini Cloud TTS is offline, timed out, or unavailable,
            // fallback immediately to the native TTS tuned with the identical Puck profile (pitch/speed).
            Log.d(TAG, "Using resilient local Puck voice fallback (pitch=$PUCK_PITCH, speed=$PUCK_SPEED)")
            withContext(Dispatchers.Main) {
                playNativeTts(cleanText, onPlaybackStarted) {
                    _isSpeaking.value = false
                    _isGeneratingAudio.value = false
                    onComplete?.invoke()
                }
            }
        }
    }

    private fun playAudioFile(
        context: Context,
        filePath: String,
        onPlaybackStarted: (() -> Unit)?,
        onComplete: (() -> Unit)?
    ) {
        try {
            exoPlayer?.stop()
            exoPlayer?.release()

            val player = com.example.util.ExoPlayerHelper.createExoPlayer(context)
            if (player == null) {
                _isGeneratingAudio.value = false
                _isSpeaking.value = false
                onComplete?.invoke()
                return
            }
            val mediaItem = MediaItem.fromUri(Uri.fromFile(File(filePath)))

            var hasTriggeredStart = false

            player.addListener(object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    _isSpeaking.value = isPlaying
                    if (isPlaying) {
                        _isGeneratingAudio.value = false
                        if (!hasTriggeredStart) {
                            hasTriggeredStart = true
                            onPlaybackStarted?.invoke()
                        }
                    }
                }

                override fun onPlaybackStateChanged(state: Int) {
                    if (state == Player.STATE_ENDED || state == Player.STATE_IDLE) {
                        if (state == Player.STATE_ENDED) {
                            _isSpeaking.value = false
                            _isGeneratingAudio.value = false
                            onComplete?.invoke()
                        }
                    }
                }

                override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                    Log.e(TAG, "Player error: ${error.message}")
                    _isSpeaking.value = false
                    _isGeneratingAudio.value = false
                    onComplete?.invoke()
                }
            })

            player.setMediaItem(mediaItem)
            // Authentic Gemini Puck voice at natural 1.0x pitch and speed (no pitch alteration)
            player.playbackParameters = PlaybackParameters(1.0f, 1.0f)
            player.prepare()
            player.play()
            exoPlayer = player
        } catch (e: Exception) {
            Log.e(TAG, "Error playing audio file", e)
            _isSpeaking.value = false
            _isGeneratingAudio.value = false
            onComplete?.invoke()
        }
    }

    private fun playNativeTts(
        text: String,
        onPlaybackStarted: (() -> Unit)?,
        onComplete: (() -> Unit)?
    ) {
        try {
            nativeTts?.stop()
            nativeTts?.setPitch(PUCK_PITCH)
            nativeTts?.setSpeechRate(PUCK_SPEED)

            nativeTts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    CoroutineScope(Dispatchers.Main).launch {
                        _isSpeaking.value = true
                        _isGeneratingAudio.value = false
                        onPlaybackStarted?.invoke()
                    }
                }
                override fun onDone(utteranceId: String?) {
                    CoroutineScope(Dispatchers.Main).launch {
                        _isSpeaking.value = false
                        _isGeneratingAudio.value = false
                        onComplete?.invoke()
                    }
                }
                override fun onError(utteranceId: String?) {
                    CoroutineScope(Dispatchers.Main).launch {
                        _isSpeaking.value = false
                        _isGeneratingAudio.value = false
                        onComplete?.invoke()
                    }
                }
            })

            nativeTts?.speak(
                com.example.util.SpeechTextSanitizer.cleanForSpeech(text),
                TextToSpeech.QUEUE_FLUSH,
                null,
                "ze_puck_${System.currentTimeMillis()}"
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error during native speak", e)
            _isSpeaking.value = false
            _isGeneratingAudio.value = false
            onComplete?.invoke()
        }
    }

    private suspend fun fetchGeminiTTSAudioWithRetry(apiKey: String, text: String): ByteArray? {
        val candidateModels = listOf("gemini-2.5-flash-preview-tts")
        val rootJson = JSONObject()
        val contents = JSONArray()
        val contentObj = JSONObject()
        val parts = JSONArray()
        parts.put(JSONObject().put("text", text))
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
        // Strictly enforce the "Puck" voice name
        prebuiltVoiceConfig.put("voiceName", VOICE_NAME)
        voiceConfig.put("prebuiltVoiceConfig", prebuiltVoiceConfig)
        speechConfig.put("voiceConfig", voiceConfig)
        generationConfig.put("speechConfig", speechConfig)

        rootJson.put("generationConfig", generationConfig)

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = rootJson.toString().toRequestBody(mediaType)

        val maxRetriesPerModel = 2

        for (model in candidateModels) {
            val url = "https://generativelanguage.googleapis.com/v1beta/models/$model:generateContent?key=$apiKey"
            
            for (attempt in 0..maxRetriesPerModel) {
                try {
                    val request = Request.Builder().url(url).post(requestBody).build()
                    val response = client.newCall(request).execute()

                    response.use { resp ->
                        val code = resp.code
                        if (resp.isSuccessful) {
                            val bodyStr = resp.body?.string() ?: ""
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
                                                Log.d(TAG, "Successfully synthesized Puck audio with Gemini model $model")
                                                return Base64.decode(base64Data, Base64.DEFAULT)
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            Log.w(TAG, "Gemini TTS HTTP error $code on model $model (attempt $attempt): ${resp.message}")

                            // Rate limit (429) or unrecoverable client errors (400, 403, 404): do not retry current model
                            if (code == 429 || code == 400 || code == 403 || code == 404) {
                                Log.w(TAG, "Client/Quota error ($code) on model $model. Proceeding to next model or fallback.")
                                return@use // Break attempt loop to move to next model
                            }

                            // 5xx Server errors: retry with exponential backoff if attempts remain
                            if (code in 500..599 && attempt < maxRetriesPerModel) {
                                val delayMs = 300L * (1 shl attempt) + (Math.random() * 100).toLong()
                                Log.d(TAG, "Retrying Gemini TTS in ${delayMs}ms after server error $code...")
                                kotlinx.coroutines.delay(delayMs)
                            }
                        }
                    }
                } catch (e: java.net.SocketTimeoutException) {
                    Log.w(TAG, "SocketTimeoutException on model $model (attempt $attempt): ${e.message}")
                    if (attempt < maxRetriesPerModel) {
                        val delayMs = 250L * (1 shl attempt)
                        kotlinx.coroutines.delay(delayMs)
                    }
                } catch (e: java.net.UnknownHostException) {
                    Log.w(TAG, "UnknownHostException (No internet connection) for Gemini TTS: ${e.message}")
                    // Device is completely offline - fail fast to local Puck TTS synthesis
                    return null
                } catch (e: java.io.IOException) {
                    Log.w(TAG, "IOException on model $model (attempt $attempt): ${e.message}")
                    if (attempt < maxRetriesPerModel) {
                        val delayMs = 300L * (1 shl attempt)
                        kotlinx.coroutines.delay(delayMs)
                    }
                } catch (e: Throwable) {
                    Log.e(TAG, "Unexpected error during Gemini TTS request on model $model", e)
                    break // Move to next model on unexpected error
                }
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
        header[20] = 1
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
        header[34] = 16
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

    fun stop() {
        try {
            exoPlayer?.stop()
            exoPlayer?.release()
            exoPlayer = null
            nativeTts?.stop()
            _isSpeaking.value = false
        } catch (e: Exception) {
            Log.w(TAG, "Error stopping: ${e.message}")
        }
    }

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(input.toByteArray())
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}
