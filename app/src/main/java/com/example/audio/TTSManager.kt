package com.example.audio

import android.content.Context
import android.util.Log
import com.example.util.SpeechTextSanitizer

class TTSManager(private val context: Context) {

    init {
        try {
            ZeTraquinaPuckTTSService.init(context)
        } catch (e: Throwable) {
            Log.e("TTSManager", "Error initializing ZeTraquinaPuckTTSService", e)
        }
    }

    /**
     * Speaks text using the official Zé Traquina "Puck" voice (Gemini TTS / European Portuguese).
     * Emojis and symbols are automatically stripped so they are never spoken aloud.
     */
    fun speak(
        text: String,
        onPlaybackStarted: (() -> Unit)? = null,
        onComplete: (() -> Unit)? = null
    ) {
        val clean = SpeechTextSanitizer.cleanForSpeech(text)
        if (clean.isBlank()) {
            onComplete?.invoke()
            return
        }

        try {
            ZeTraquinaPuckTTSService.speak(
                context = context,
                text = clean,
                onPlaybackStarted = onPlaybackStarted,
                onComplete = onComplete
            )
        } catch (e: Throwable) {
            Log.e("TTSManager", "Error speaking with Puck voice", e)
            onComplete?.invoke()
        }
    }

    fun stop() {
        try {
            ZeTraquinaPuckTTSService.stop()
        } catch (e: Throwable) {
            Log.e("TTSManager", "Error stopping TTS", e)
        }
    }

    fun shutdown() {
        try {
            ZeTraquinaPuckTTSService.stop()
        } catch (e: Throwable) {
            Log.e("TTSManager", "Error shutting down TTS", e)
        }
    }
}
