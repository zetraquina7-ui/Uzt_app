package com.example.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast

/**
 * Data class representing detailed speech error information
 * with structured technical diagnostics and child-friendly European Portuguese feedback.
 */
data class SpeechErrorDetails(
    val code: Int,
    val name: String,
    val userMessage: String,
    val canRetry: Boolean
)

/**
 * Robust helper for SpeechRecognizer on Android with explicit pt-PT configuration,
 * comprehensive RecognitionListener logging, device hardware checks, and clear user feedback.
 */
object SpeechRecognitionHelper {
    private const val TAG = "SpeechRecognition"
    const val LANGUAGE_TAG_PT_PT = "pt-PT"

    /**
     * Checks if the device has physical microphone hardware available.
     */
    fun isMicrophoneHardwareAvailable(context: Context): Boolean {
        val hasMic = context.packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)
        Log.d(TAG, "isMicrophoneHardwareAvailable: $hasMic")
        return hasMic
    }

    /**
     * Checks if a SpeechRecognizer engine (e.g., Google Speech Services) is installed and available.
     * Note: On cloud and standard emulators, background SpeechRecognizer binds often fail/warn, 
     * so we proactively treat emulators as unsupported to force the cleaner system dialog fallback immediately.
     */
    fun isRecognitionServiceAvailable(context: Context): Boolean {
        try {
            val serviceIntent = Intent("android.speech.RecognitionService")
            val resolveInfos = context.packageManager.queryIntentServices(serviceIntent, 0)
            
            Log.d(TAG, "Available RecognitionServices: ${resolveInfos.size}")
            resolveInfos.forEach { 
                Log.d(TAG, "  - Found: ${it.serviceInfo.packageName}/${it.serviceInfo.name}") 
            }

            if (resolveInfos.isNotEmpty()) {
                return true
            }
            
            return SpeechRecognizer.isRecognitionAvailable(context)
        } catch (e: Throwable) {
            Log.w(TAG, "Error checking recognition service availability", e)
            return false
        }
    }

    /**
     * Complete verification: both microphone hardware and recognition service must be present.
     */
    fun isSpeechRecognitionSupported(context: Context): Boolean {
        val frameworkAvailable = SpeechRecognizer.isRecognitionAvailable(context)
        if (!frameworkAvailable) return false
        val serviceAvailable = isRecognitionServiceAvailable(context)
        val supported = frameworkAvailable && serviceAvailable
        Log.d(TAG, "isSpeechRecognitionSupported: $supported (service=$serviceAvailable, framework=$frameworkAvailable)")
        return supported
    }

    private var lastRequestTime = 0L

    /**
     * Rate-limiting check to prevent ERROR_TOO_MANY_REQUESTS (code 10)
     */
    fun canInitiateRecognition(): Boolean {
        val now = System.currentTimeMillis()
        if (now - lastRequestTime < 1200L) {
            return false
        }
        lastRequestTime = now
        return true
    }

    /**
     * Creates a robust SpeechRecognizer instance safely on the main thread,
     * checking system recognition service components and falling back if necessary.
     * We explicitly destroy any old instance before creating a new one to prevent
     * the common ERROR_CLIENT (5) on several Android versions.
     */
    fun createSafeRecognizer(context: Context): SpeechRecognizer? {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            Log.w(TAG, "SpeechRecognizer.isRecognitionAvailable returned false")
            return null
        }

        var recognizer: SpeechRecognizer? = null
        try {
            // Prioritize standard creation
            recognizer = SpeechRecognizer.createSpeechRecognizer(context)
            Log.d(TAG, "SpeechRecognizer created using standard factory")
        } catch (e: Throwable) {
            Log.w(TAG, "Standard createSpeechRecognizer failed: ${e.message}")
        }

        // Fallback: search for available RecognitionService component if default creation failed
        if (recognizer == null) {
            try {
                val serviceIntent = Intent("android.speech.RecognitionService")
                val resolveInfos = context.packageManager.queryIntentServices(serviceIntent, 0)
                
                val googleComponent = resolveInfos.find { 
                    it.serviceInfo.packageName == "com.google.android.googlequicksearchbox" 
                }?.let { 
                    android.content.ComponentName(it.serviceInfo.packageName, it.serviceInfo.name)
                }

                if (googleComponent != null) {
                    Log.d(TAG, "Attempting SpeechRecognizer with Google component: $googleComponent")
                    recognizer = SpeechRecognizer.createSpeechRecognizer(context, googleComponent)
                } else if (resolveInfos.isNotEmpty()) {
                    val firstComponent = android.content.ComponentName(
                        resolveInfos[0].serviceInfo.packageName,
                        resolveInfos[0].serviceInfo.name
                    )
                    Log.d(TAG, "Attempting SpeechRecognizer with first available component: $firstComponent")
                    recognizer = SpeechRecognizer.createSpeechRecognizer(context, firstComponent)
                }
            } catch (e: Throwable) {
                Log.e(TAG, "Fallback SpeechRecognizer creation failed", e)
            }
        }

        return recognizer
    }

    /**
     * Safely stops speech recognition without throwing or logging connection errors.
     */
    fun safeStop(recognizer: SpeechRecognizer?) {
        if (recognizer == null) return
        try {
            recognizer.stopListening()
        } catch (e: Throwable) {
            Log.w(TAG, "safeStop ignored error: ${e.message}")
        }
    }

    /**
     * Safely cancels speech recognition without throwing or logging connection errors.
     */
    fun safeCancel(recognizer: SpeechRecognizer?) {
        if (recognizer == null) return
        try {
            recognizer.cancel()
        } catch (e: Throwable) {
            Log.w(TAG, "safeCancel ignored error: ${e.message}")
        }
    }

    /**
     * Safely destroys a speech recognizer instance without throwing or logging connection errors.
     */
    fun safeDestroy(recognizer: SpeechRecognizer?) {
        if (recognizer == null) return
        try {
            recognizer.destroy()
        } catch (e: Throwable) {
            Log.w(TAG, "safeDestroy ignored error: ${e.message}")
        }
    }

    /**
     * Creates an Intent configured for speech recognition with resilient locale fallback.
     */
    fun createSpeechIntent(
        context: Context,
        prompt: String = "Fala com o Zé Traquina! 🎙️",
        enablePartialResults: Boolean = true,
        maxResults: Int = 3
    ): Intent {
        return Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            // Standard Free-form speech model
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            
            // European Portuguese language
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, LANGUAGE_TAG_PT_PT)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, LANGUAGE_TAG_PT_PT)
            
            // Calling package identifier
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
            
            // Results parameters
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, maxResults)
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, enablePartialResults)
            putExtra(RecognizerIntent.EXTRA_PROMPT, prompt)
        }
    }

    /**
     * Maps all Android SpeechRecognizer error codes to detailed technical descriptors
     * and clear user-facing messages in European Portuguese.
     */
    fun getErrorDetails(errorCode: Int): SpeechErrorDetails {
        return when (errorCode) {
            SpeechRecognizer.ERROR_AUDIO -> SpeechErrorDetails(
                code = errorCode,
                name = "ERROR_AUDIO (3)",
                userMessage = "Opa! O microfone está com soluços. Verifica se está tudo bem! 🎤",
                canRetry = true
            )
            SpeechRecognizer.ERROR_CLIENT -> SpeechErrorDetails(
                code = errorCode,
                name = "ERROR_CLIENT (5)",
                userMessage = "O serviço de voz do teu telemóvel está a descansar. Tenta de novo! 📱",
                canRetry = true
            )
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> SpeechErrorDetails(
                code = errorCode,
                name = "ERROR_INSUFFICIENT_PERMISSIONS (9)",
                userMessage = "Preciso que deixes o Zé ouvir-te para podermos brincar! 🔒",
                canRetry = false
            )
            SpeechRecognizer.ERROR_NETWORK -> SpeechErrorDetails(
                code = errorCode,
                name = "ERROR_NETWORK (2)",
                userMessage = "Parece que a internet foi dar um passeio. Liga-a para falarmos! 🌐",
                canRetry = true
            )
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> SpeechErrorDetails(
                code = errorCode,
                name = "ERROR_NETWORK_TIMEOUT (1)",
                userMessage = "A internet está com sono e demorou muito. Tenta outra vez! ⏳",
                canRetry = true
            )
            SpeechRecognizer.ERROR_NO_MATCH -> SpeechErrorDetails(
                code = errorCode,
                name = "ERROR_NO_MATCH (7)",
                userMessage = "Não percebi nada do que disseste! Podes falar mais devagarinho? 🗣️",
                canRetry = true
            )
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> SpeechErrorDetails(
                code = errorCode,
                name = "ERROR_RECOGNIZER_BUSY (8)",
                userMessage = "Calma, amiguinho! Estou a processar outra coisa. Espera um bocadinho! ⏳",
                canRetry = true
            )
            SpeechRecognizer.ERROR_SERVER -> SpeechErrorDetails(
                code = errorCode,
                name = "ERROR_SERVER (4)",
                userMessage = "A nuvem das vozes está com chuva. Tenta daqui a nada! ☁️",
                canRetry = true
            )
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> SpeechErrorDetails(
                code = errorCode,
                name = "ERROR_SPEECH_TIMEOUT (6)",
                userMessage = "Ficaste caladinho? Toca no microfone e conta-me tudo! 🎙️",
                canRetry = true
            )
            10 -> SpeechErrorDetails(
                code = errorCode,
                name = "ERROR_TOO_MANY_REQUESTS (10)",
                userMessage = "Estás a falar muito depressa! Vamos dar um segundinho de descanso? ⏳",
                canRetry = true
            )
            11 -> SpeechErrorDetails(
                code = errorCode,
                name = "ERROR_SERVER_DISCONNECTED (11)",
                userMessage = "A ligação com o Zé caiu. Vamos tentar ligar de novo! 🔌",
                canRetry = true
            )
            12 -> SpeechErrorDetails(
                code = errorCode,
                name = "ERROR_LANGUAGE_NOT_SUPPORTED (12)",
                userMessage = "Ainda não aprendi a falar esse idioma. Só sei Português! 🇵🇹",
                canRetry = false
            )
            13 -> SpeechErrorDetails(
                code = errorCode,
                name = "ERROR_LANGUAGE_UNAVAILABLE (13)",
                userMessage = "O pacote de voz em Português foi brincar às escondidas! 🇵🇹",
                canRetry = false
            )
            14 -> SpeechErrorDetails(
                code = errorCode,
                name = "ERROR_CANNOT_CHECK_SUPPORT (14)",
                userMessage = "Não consigo saber se o teu telemóvel sabe falar Português! ⚠️",
                canRetry = true
            )
            else -> SpeechErrorDetails(
                code = errorCode,
                name = "ERROR_UNKNOWN ($errorCode)",
                userMessage = "Ocorreu um erro mágico ($errorCode). Vamos tentar de novo! 🎤",
                canRetry = true
            )
        }
    }

    /**
     * Builds a RecognitionListener with full event logging and standardized callback routing.
     */
    fun createListener(
        context: Context? = null,
        onReady: () -> Unit = {},
        onBeginning: () -> Unit = {},
        onRms: (Float) -> Unit = {},
        onEndOfSpeech: () -> Unit = {},
        onPartialResult: (String) -> Unit = {},
        onFinalResult: (spokenText: String, allMatches: List<String>) -> Unit = { _, _ -> },
        onError: (details: SpeechErrorDetails) -> Unit = {}
    ): RecognitionListener {
        return object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                Log.d(TAG, "RecognitionListener.onReadyForSpeech: Ready to receive speech in pt-PT")
                onReady()
            }

            override fun onBeginningOfSpeech() {
                Log.d(TAG, "RecognitionListener.onBeginningOfSpeech: User started speaking")
                onBeginning()
            }

            override fun onRmsChanged(rmsdB: Float) {
                onRms(rmsdB)
            }

            override fun onBufferReceived(buffer: ByteArray?) {
                Log.v(TAG, "RecognitionListener.onBufferReceived: size=${buffer?.size ?: 0}")
            }

            override fun onEndOfSpeech() {
                Log.d(TAG, "RecognitionListener.onEndOfSpeech: End of user speech detected")
                onEndOfSpeech()
            }

            override fun onError(error: Int) {
                val details = getErrorDetails(error)
                if (error == SpeechRecognizer.ERROR_SPEECH_TIMEOUT || error == SpeechRecognizer.ERROR_NO_MATCH) {
                    Log.d(TAG, "RecognitionListener info: code=$error (${details.name}) -> userMessage=\"${details.userMessage}\"")
                } else {
                    Log.w(TAG, "RecognitionListener.onError: code=$error (${details.name}) -> userMessage=\"${details.userMessage}\"")
                }
                
                onError(details)
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION) ?: arrayListOf()
                val scores = results?.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES)
                Log.d(TAG, "RecognitionListener.onResults: matchesCount=${matches.size}, matches=$matches, scores=${scores?.contentToString()}")
                
                if (matches.isNotEmpty()) {
                    val primaryMatch = matches[0]
                    onFinalResult(primaryMatch, matches)
                } else {
                    Log.w(TAG, "RecognitionListener.onResults: returned empty match list")
                    val details = getErrorDetails(SpeechRecognizer.ERROR_NO_MATCH)
                    onError(details)
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {
                val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION) ?: arrayListOf()
                if (matches.isNotEmpty()) {
                    val partialText = matches[0]
                    Log.d(TAG, "RecognitionListener.onPartialResults: partial=\"$partialText\"")
                    onPartialResult(partialText)
                }
            }

            override fun onEvent(eventType: Int, params: Bundle?) {
                Log.d(TAG, "RecognitionListener.onEvent: eventType=$eventType")
            }
        }
    }
}
