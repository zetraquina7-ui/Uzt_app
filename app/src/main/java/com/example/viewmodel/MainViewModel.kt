package com.example.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.AdaptiveAnalysisResult
import com.example.api.GeminiAdaptiveLearningService
import com.example.audio.MiniMaxStorytellingService
import com.example.audio.StoryPlaybackState
import com.example.audio.TTSManager
import com.example.audio.SoundManager
import com.example.data.AppDatabase
import com.example.data.AppRepository
import com.example.data.CompletedItem
import com.example.data.UserPreferences
import com.example.data.UserProgress
import com.example.ui.navigation.Screen
import com.example.api.ZeTraquinaChatRepository
import com.example.audio.ZeTraquinaPuckTTSService
import com.example.util.SpeechRecognitionHelper
import android.speech.SpeechRecognizer
import android.speech.RecognizerIntent
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.pm.PackageManager
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "DEBUG_APP"

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TAG, "Uncaught CoroutineException in MainViewModel", throwable)
    }

    private val repository by lazy {
        try {
            Log.d(TAG, "Initializing AppRepository...")
            AppRepository(AppDatabase.getDatabase(application).progressDao())
        } catch (e: Throwable) {
            Log.e(TAG, "Error initializing AppRepository", e)
            null
        }
    }

    val ttsManager by lazy {
        try {
            Log.d(TAG, "Initializing TTSManager...")
            TTSManager(application)
        } catch (e: Throwable) {
            Log.e(TAG, "Error initializing TTSManager", e)
            null
        }
    }

    val soundManager by lazy {
        try {
            Log.d(TAG, "Initializing SoundManager...")
            SoundManager(application)
        } catch (e: Throwable) {
            Log.e(TAG, "Error initializing SoundManager", e)
            null
        }
    }

    val storytellingService by lazy {
        try {
            Log.d(TAG, "Initializing MiniMaxStorytellingService...")
            MiniMaxStorytellingService(application)
        } catch (e: Throwable) {
            Log.e(TAG, "Error initializing MiniMaxStorytellingService", e)
            null
        }
    }

    private val chatRepository by lazy { ZeTraquinaChatRepository() }
    private val userPreferences = UserPreferences(application)

    val showRateAppDialog = combine(
        userPreferences.sessionCount,
        userPreferences.dialogShown
    ) { count, shown ->
        count >= 5 && !shown
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun onRateAppDismissed() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            userPreferences.setDialogShown()
        }
    }

    var currentScreen by mutableStateOf<Screen>(Screen.Home)

    fun navigateTo(screen: Screen) {
        try {
            Log.d(TAG, "Navigating to screen: ${screen.route}")
            stopSpeaking()
            stopListening()
            currentScreen = screen
        } catch (e: Throwable) {
            Log.e(TAG, "Error during navigation to ${screen.route}", e)
            currentScreen = Screen.Home
        }
    }

    suspend fun getGeminiResponse(query: String): String {
        return try {
            val dao = AppDatabase.getDatabase(getApplication()).zeAICacheDao()
            chatRepository.sendMessage(query, emptyList(), dao)
        } catch (e: Throwable) {
            Log.e(TAG, "Error getting Gemini response", e)
            "Ups! O Zé Traquina perdeu o fio à meada... Podes repetir, amiguinho? 😅"
        }
    }

    val storyPlaybackState: StateFlow<StoryPlaybackState> =
        storytellingService?.playbackState ?: MutableStateFlow<StoryPlaybackState>(StoryPlaybackState.Idle).asStateFlow()

    private val _userProgress = MutableStateFlow(UserProgress())
    val userProgress: StateFlow<UserProgress> = _userProgress.asStateFlow()

    private val _completedItems = MutableStateFlow<List<CompletedItem>>(emptyList())
    val completedItems: StateFlow<List<CompletedItem>> = _completedItems.asStateFlow()

    private val _isTimeLimitReached = MutableStateFlow(false)
    val isTimeLimitReached: StateFlow<Boolean> = _isTimeLimitReached.asStateFlow()

    private val adaptiveLearningService by lazy { GeminiAdaptiveLearningService() }
    private val _adaptiveAnalysisState = MutableStateFlow<AdaptiveAnalysisResult?>(null)
    val adaptiveAnalysisState: StateFlow<AdaptiveAnalysisResult?> = _adaptiveAnalysisState.asStateFlow()

    private val _isAnalyzingPerformance = MutableStateFlow(false)
    val isAnalyzingPerformance: StateFlow<Boolean> = _isAnalyzingPerformance.asStateFlow()

    init {
        Log.d(TAG, "MainViewModel init started")
        
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                Log.d(TAG, "Loading UserProgress flow from repository...")
                var initialized = false
                repository?.userProgress?.collect { progress ->
                    if (progress != null) {
                        _userProgress.value = progress
                        initialized = true
                    } else if (!initialized) {
                        initialized = true
                        val defaultProg = UserProgress()
                        repository?.updateProgress(defaultProg)
                        _userProgress.value = defaultProg
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable) {
                Log.e(TAG, "Error loading UserProgress from database", e)
            }
        }

        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                Log.d(TAG, "Loading CompletedItems flow from repository...")
                var seeded = false
                repository?.completedItems?.collect { list ->
                    _completedItems.value = list
                    if (list.isEmpty() && !seeded) {
                        seeded = true
                        val now = System.currentTimeMillis()
                        val dayMs = 86400000L
                        val initialSamples = listOf(
                            CompletedItem(id = "sample_1", category = "Jogos", title = "Jogo da Memória dos Animais", timestamp = now - (dayMs * 0.1).toLong()),
                            CompletedItem(id = "sample_2", category = "Aprender", title = "Alfabeto Zé Traquina: Letra A", timestamp = now - (dayMs * 0.3).toLong()),
                            CompletedItem(id = "sample_3", category = "Chat", title = "Conversa sobre os Dinossauros com ZéAI", timestamp = now - (dayMs * 0.8).toLong()),
                            CompletedItem(id = "sample_4", category = "Vídeos", title = "A Dança das Cores do Zé Traquina", timestamp = now - (dayMs * 1.2).toLong()),
                            CompletedItem(id = "sample_5", category = "Jogos", title = "Quebra-Cabeça da Quinta", timestamp = now - (dayMs * 1.8).toLong()),
                            CompletedItem(id = "sample_6", category = "Aprender", title = "Contar Números 1 a 10", timestamp = now - (dayMs * 2.5).toLong()),
                            CompletedItem(id = "sample_7", category = "Jogos", title = "Jogo das Formas Geométricas", timestamp = now - (dayMs * 3.2).toLong()),
                            CompletedItem(id = "sample_8", category = "Vídeos", title = "A Canção do Alfabeto Divertido", timestamp = now - (dayMs * 4.1).toLong()),
                            CompletedItem(id = "sample_9", category = "Aprender", title = "Descobrir as Cores Primárias", timestamp = now - (dayMs * 5.0).toLong()),
                            CompletedItem(id = "sample_10", category = "Chat", title = "Adivinhas de Animaizinhos", timestamp = now - (dayMs * 6.0).toLong())
                        )
                        repository?.seedSampleItemsIfEmpty(initialSamples)
                    }
                    if (_adaptiveAnalysisState.value == null && list.isNotEmpty()) {
                        analyzePerformanceWithGemini()
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Throwable) {
                Log.e(TAG, "Error loading CompletedItems from database", e)
            }
        }
    }

    fun analyzePerformanceWithGemini() {
        if (_isAnalyzingPerformance.value) return
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _isAnalyzingPerformance.value = true
            try {
                val result = adaptiveLearningService.analyzePerformance(
                    userProgress = _userProgress.value,
                    completedItems = _completedItems.value
                )
                _adaptiveAnalysisState.value = result
            } catch (e: Throwable) {
                Log.e(TAG, "Error performing Gemini adaptive analysis", e)
            } finally {
                _isAnalyzingPerformance.value = false
            }
        }
    }

    fun recordActivityCompletion(category: String, title: String) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                val id = "activity_${System.currentTimeMillis()}"
                repository?.recordCompletion(id, category, title)
                addStars(5)
            } catch (e: Throwable) {
                Log.e(TAG, "Error recording activity completion", e)
            }
        }
    }

    fun clearActivityHistory() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                repository?.clearHistory()
            } catch (e: Throwable) {
                Log.e(TAG, "Error clearing activity history", e)
            }
        }
    }

    fun addStars(amount: Int) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                val current = _userProgress.value
                val updated = current.copy(starsCount = current.starsCount + amount)
                _userProgress.value = updated
                repository?.updateProgress(updated)
            } catch (e: Throwable) {
                Log.e(TAG, "Error adding stars", e)
            }
        }
    }

    fun recordGamePlayed() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                val current = _userProgress.value
                val updated = current.copy(
                    totalGamesPlayed = current.totalGamesPlayed + 1,
                    starsCount = current.starsCount + 5
                )
                _userProgress.value = updated
                repository?.updateProgress(updated)
            } catch (e: Throwable) {
                Log.e(TAG, "Error recording game played", e)
            }
        }
    }

    fun toggleSound() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                val current = _userProgress.value.soundEnabled
                val updated = _userProgress.value.copy(soundEnabled = !current)
                _userProgress.value = updated
                repository?.updateProgress(updated)
                if (current) {
                    stopSpeaking()
                }
            } catch (e: Throwable) {
                Log.e(TAG, "Error toggling sound", e)
            }
        }
    }

    fun setSoundEnabled(enabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                val updated = _userProgress.value.copy(soundEnabled = enabled)
                _userProgress.value = updated
                repository?.updateProgress(updated)
                if (!enabled) {
                    stopSpeaking()
                }
            } catch (e: Throwable) {
                Log.e(TAG, "Error setting sound enabled", e)
            }
        }
    }

    fun updateParentSettings(childName: String, ageGroup: String, timeLimitMinutes: Int, soundEnabled: Boolean) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                val updated = _userProgress.value.copy(
                    childName = childName,
                    ageGroup = ageGroup,
                    timeLimitMinutes = timeLimitMinutes,
                    soundEnabled = soundEnabled
                )
                _userProgress.value = updated
                repository?.updateProgress(updated)
            } catch (e: Throwable) {
                Log.e(TAG, "Error updating parent settings", e)
            }
        }
    }

    fun saveCustomCharacter(name: String, shape: String, color: Long, face: String) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                val updated = _userProgress.value.copy(
                    customCharName = name,
                    customCharShape = shape,
                    customCharColor = color,
                    customCharFace = face
                )
                _userProgress.value = updated
                repository?.updateProgress(updated)
            } catch (e: Throwable) {
                Log.e(TAG, "Error saving custom character", e)
            }
        }
    }

    fun saveCustomAvatar(
        name: String,
        skinTone: String,
        hairStyle: String,
        hairColor: Long,
        clothingStyle: String,
        clothingColor: Long,
        accessory: String,
        expression: String,
        background: String
    ) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                val updated = _userProgress.value.copy(
                    customCharName = name,
                    avatarSkinTone = skinTone,
                    avatarHairStyle = hairStyle,
                    avatarHairColor = hairColor,
                    avatarClothingStyle = clothingStyle,
                    avatarClothingColor = clothingColor,
                    avatarAccessory = accessory,
                    avatarExpression = expression,
                    avatarBackground = background,
                    starsCount = _userProgress.value.starsCount + 5
                )
                _userProgress.value = updated
                repository?.updateProgress(updated)
            } catch (e: Throwable) {
                Log.e(TAG, "Error saving custom avatar", e)
            }
        }
    }

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    // State indicating audio synthesis is in progress
    val isGeneratingAudio: StateFlow<Boolean> = ZeTraquinaPuckTTSService.isGeneratingAudio

    // --- Native Speech Recognition State ---
    private var speechRecognizer: SpeechRecognizer? = null
    private val _isListening = MutableStateFlow(false)
    val isListening: StateFlow<Boolean> = _isListening.asStateFlow()

    private val _voiceResult = MutableStateFlow("")
    val voiceResult: StateFlow<String> = _voiceResult.asStateFlow()

    private val _voiceError = MutableStateFlow<String?>(null)
    val voiceError: StateFlow<String?> = _voiceError.asStateFlow()

    private val _rmsValue = MutableStateFlow(0f)
    val rmsValue: StateFlow<Float> = _rmsValue.asStateFlow()

    private var onSpeechFinalResult: ((String) -> Unit)? = null
    private var onSpeechPartialResult: ((String) -> Unit)? = null

    init {
        viewModelScope.launch {
            ZeTraquinaPuckTTSService.isSpeaking.collect { speaking ->
                _isSpeaking.value = speaking
            }
        }
    }

    /**
     * Inicia a captura de voz para interagir com o Zé Traquina.
     * Garante a limpeza de instâncias anteriores e verificação de suporte.
     */
    fun startListening(
        onResult: ((String) -> Unit)? = null,
        onPartial: ((String) -> Unit)? = null
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            stopSpeaking()
            if (!SpeechRecognitionHelper.isSpeechRecognitionSupported(getApplication())) {
                _voiceError.value = "O reconhecimento de voz não é suportado neste dispositivo. 🎤"
                return@launch
            }

            if (ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                _voiceError.value = "Permissão de áudio necessária! 🎤"
                return@launch
            }

            // Limpeza agressiva antes de iniciar nova sessão para evitar ERROR_CLIENT (5)
            // Garante que o listener é corretamente destruído/recriado entre tentativas
            if (speechRecognizer != null) {
                SpeechRecognitionHelper.safeDestroy(speechRecognizer)
                speechRecognizer = null
                delay(150) // Aumentado para garantir libertação de recursos do sistema
            }

            if (_isListening.value) {
                _isListening.value = false
                delay(100)
            }

            onSpeechFinalResult = onResult
            onSpeechPartialResult = onPartial
            _voiceError.value = null
            _voiceResult.value = ""
            
            try {
                speechRecognizer = SpeechRecognitionHelper.createSafeRecognizer(getApplication())
                if (speechRecognizer == null) {
                    _voiceError.value = "Não foi possível iniciar o serviço de voz. Tenta novamente! 📱"
                    return@launch
                }

                val intent = SpeechRecognitionHelper.createSpeechIntent(getApplication())
                
                val listener = SpeechRecognitionHelper.createListener(
                    context = getApplication(),
                    onReady = { 
                        _isListening.value = true
                        _voiceError.value = null
                        _voiceResult.value = "Estou a ouvir... Fala agora! 🎙️"
                        Log.d(TAG, "Speech ready") 
                    },
                    onFinalResult = { result, _ ->
                        _isListening.value = false
                        if (result.isNotBlank()) {
                            _voiceResult.value = result
                            onSpeechFinalResult?.invoke(result)
                        } else {
                            _voiceError.value = "Não percebi bem. Podes repetir? 🎤"
                        }
                        viewModelScope.launch(Dispatchers.Main) {
                            releaseSpeechRecognizer()
                        }
                    },
                    onError = { details ->
                        _isListening.value = false
                        _voiceError.value = details.userMessage
                        if (details.code == SpeechRecognizer.ERROR_SPEECH_TIMEOUT || details.code == SpeechRecognizer.ERROR_NO_MATCH) {
                            Log.d(TAG, "Speech info: ${details.name} - ${details.code}")
                        } else {
                            Log.w(TAG, "Speech warning: ${details.name} - ${details.code}")
                        }
                        viewModelScope.launch(Dispatchers.Main) {
                            releaseSpeechRecognizer()
                        }
                    },
                    onPartialResult = { partial ->
                        if (partial.isNotBlank()) {
                            _voiceResult.value = partial
                            onSpeechPartialResult?.invoke(partial)
                        }
                    },
                    onRms = { volume ->
                        _rmsValue.value = volume
                    }
                )

                speechRecognizer?.setRecognitionListener(listener)
                _voiceResult.value = "A preparar o microfone... 🎙️"
                speechRecognizer?.startListening(intent)
            } catch (e: Exception) {
                Log.e(TAG, "Error starting speech recognition", e)
                _voiceError.value = "Erro ao iniciar o microfone. 🎤"
                _isListening.value = false
                releaseSpeechRecognizer()
            }
        }
    }

    /**
     * Completely frees and releases the native SpeechRecognizer instance and hardware audio recorder,
     * preventing any resource conflicts with Text-To-Speech (TTS) audio synthesis.
     */
    fun releaseSpeechRecognizer() {
        try {
            if (speechRecognizer != null) {
                SpeechRecognitionHelper.safeCancel(speechRecognizer)
                SpeechRecognitionHelper.safeDestroy(speechRecognizer)
                speechRecognizer = null
            }
        } catch (e: Throwable) {
            Log.w(TAG, "Error destroying speechRecognizer: ${e.message}")
            speechRecognizer = null
        } finally {
            _isListening.value = false
            _rmsValue.value = 0f
        }
    }

    fun clearVoiceResult() {
        _voiceResult.value = ""
        _voiceError.value = null
    }

    fun handleRecognizedCommand(text: String) {
        releaseSpeechRecognizer()
        viewModelScope.launch {
            val lower = text.lowercase(java.util.Locale.ROOT)
            _voiceResult.value = "Disseste: \"$text\""
            
            when {
                lower.contains("jogos") || lower.contains("jogar") || lower.contains("brincar") -> {
                    navigateTo(Screen.Games)
                    speak("A abrir o Parquinho dos Jogos!")
                }
                lower.contains("aprender") || lower.contains("escola") || lower.contains("aula") -> {
                    navigateTo(Screen.Learn)
                    speak("A abrir a Escola Mágica!")
                }
                lower.contains("música") || lower.contains("canta") || lower.contains("canção") -> {
                    navigateTo(Screen.Music)
                    speak("A abrir o Cantinho da Música!")
                }
                lower.contains("vídeo") || lower.contains("ver") || lower.contains("televisão") -> {
                    navigateTo(Screen.Media)
                    speak("A abrir o Cinema do Zé Traquina!")
                }
                lower.contains("chat") || lower.contains("conversa") || lower.contains("falar") -> {
                    navigateTo(Screen.Chat)
                    speak("A abrir o Chat com o Zé Traquina!")
                }
                lower.contains("estatísticas") || lower.contains("progresso") || lower.contains("estrelas") -> {
                    navigateTo(Screen.Stats)
                    speak("A abrir as tuas Estatísticas!")
                }
                lower.contains("piada") || lower.contains("engraçado") -> {
                    playBubbleSound()
                    speak("O que diz o zero para o oito? Que cinto tão apertado! Ahahah! 😆")
                }
                lower.contains("rir") || lower.contains("gargalhada") || lower.contains("cócegas") -> {
                    playBubbleSound()
                    speak("Hihihi! Ahahah! Muitas gargalhadas para nos divertirmos! 😄🎉")
                }
                lower.contains("tchau") || lower.contains("adeus") || lower.contains("até logo") -> {
                    playBubbleSound()
                    speak("Até já amiguinho! Um abraço do Zé Traquina! 👋💙")
                }
                lower.contains("salto") || lower.contains("pula") || lower.contains("dança") -> {
                    playVictorySound()
                    speak("Boing! Boing! Saltinho bem alto do Zé Traquina! 🦘")
                }
                lower.contains("olá") || lower.contains("oi") || lower.contains("bom dia") -> {
                    playBubbleSound()
                    speak("Olá amiguinho! É um prazer brincar contigo! 💙")
                }
                else -> {
                    playBubbleSound()
                    val response = getGeminiResponse(text)
                    _voiceResult.value = response
                    speak(response)
                }
            }
            delay(4000)
            _voiceResult.value = ""
        }
    }

    fun stopListening() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                if (speechRecognizer != null) {
                    // Use safeStop to tell the recognizer to stop listening and process the audio,
                    // rather than destroying it, which abandons the text.
                    SpeechRecognitionHelper.safeStop(speechRecognizer)
                }
                _isListening.value = false
            } catch (e: Throwable) {
                releaseSpeechRecognizer()
            }
        }
    }

    fun cancelListening() {
        viewModelScope.launch(Dispatchers.Main) {
            releaseSpeechRecognizer()
        }
    }

    fun speak(
        text: String,
        onPlaybackStarted: (() -> Unit)? = null,
        onComplete: (() -> Unit)? = null
    ) {
        // Guarantee speech recognition and microphone locks are released before audio playback begins
        releaseSpeechRecognizer()
        try {
            val cleanText = com.example.util.SpeechTextSanitizer.cleanForSpeech(text)
            if (cleanText.isBlank()) {
                onComplete?.invoke()
                return
            }
            if (_userProgress.value.soundEnabled) {
                // Use the high-quality Puck Voice service instead of generic ttsManager
                viewModelScope.launch {
                    ZeTraquinaPuckTTSService.speak(
                        context = getApplication(),
                        text = cleanText,
                        onPlaybackStarted = onPlaybackStarted,
                        onComplete = onComplete
                    )
                }
            } else {
                onComplete?.invoke()
            }
        } catch (e: Throwable) {
            Log.e(TAG, "Error in speak", e)
            onComplete?.invoke()
        }
    }

    fun stopSpeaking() {
        try {
            ZeTraquinaPuckTTSService.stop()
            ttsManager?.stop()
        } catch (e: Throwable) {
            Log.e(TAG, "Error in stopSpeaking", e)
        }
    }

    fun playSound(resId: Int) {
        try {
            if (_userProgress.value.soundEnabled) {
                soundManager?.playSound(resId)
            }
        } catch (e: Throwable) {
            Log.e(TAG, "Error playing sound resId: $resId", e)
        }
    }

    fun playClickSound() {
        try {
            if (_userProgress.value.soundEnabled) {
                soundManager?.playClick()
            }
        } catch (e: Throwable) {
            Log.e(TAG, "Error playing click sound", e)
        }
    }

    fun playVictorySound() {
        try {
            if (_userProgress.value.soundEnabled) {
                soundManager?.playVictory()
            }
        } catch (e: Throwable) {
            Log.e(TAG, "Error playing victory sound", e)
        }
    }

    fun playErrorSound() {
        try {
            if (_userProgress.value.soundEnabled) {
                soundManager?.playError()
            }
        } catch (e: Throwable) {
            Log.e(TAG, "Error playing error sound", e)
        }
    }

    fun playStarSound() {
        try {
            if (_userProgress.value.soundEnabled) {
                soundManager?.playStar()
            }
        } catch (e: Throwable) {
            Log.e(TAG, "Error playing star sound", e)
        }
    }

    fun playBubbleSound() {
        try {
            soundManager?.playBubble()
        } catch (e: Throwable) {
            Log.e(TAG, "Error playing bubble sound", e)
        }
    }

    fun playBounceSound() {
        try {
            soundManager?.playBounce()
        } catch (e: Throwable) {
            Log.e(TAG, "Error playing bounce sound", e)
        }
    }

    fun playComboSound() {
        try {
            soundManager?.playCombo()
        } catch (e: Throwable) {
            Log.e(TAG, "Error playing combo sound", e)
        }
    }

    fun startAmbientMusic(volume: Float = 0.30f) {
        try {
            soundManager?.startAmbientMusic(volume)
        } catch (e: Throwable) {
            Log.e(TAG, "Error starting ambient music", e)
        }
    }

    fun stopAmbientMusic() {
        try {
            soundManager?.stopAmbientMusic()
        } catch (e: Throwable) {
            Log.e(TAG, "Error stopping ambient music", e)
        }
    }

    fun tellStory(title: String, storyContent: String) {
        try {
            if (!_userProgress.value.soundEnabled) return

            val prefs = getApplication<Application>().getSharedPreferences("ze_traquina_prefs", android.content.Context.MODE_PRIVATE)
            val apiKey = prefs.getString("minimax_api_key", "") ?: ""
            val groupId = prefs.getString("minimax_group_id", "412800306253115401")?.ifBlank { "412800306253115401" } ?: "412800306253115401"
            val voiceId = prefs.getString("minimax_voice_id", "moss_audio_042825f2-9a3c-11f0-a95b-4a1f35de8f62")?.ifBlank { "moss_audio_042825f2-9a3c-11f0-a95b-4a1f35de8f62" } ?: "moss_audio_042825f2-9a3c-11f0-a95b-4a1f35de8f62"

            storytellingService?.tellStory(
                title = title,
                storyContent = storyContent,
                apiKey = apiKey,
                groupId = groupId,
                voiceId = voiceId,
                onFallbackNativeTTS = { text ->
                    speak(text)
                }
            )
        } catch (e: Throwable) {
            Log.e(TAG, "Error telling story", e)
        }
    }

    fun stopStory() {
        try {
            storytellingService?.stopStory()
            ttsManager?.stop()
        } catch (e: Throwable) {
            Log.e(TAG, "Error stopping story", e)
        }
    }

    override fun onCleared() {
        super.onCleared()
        try {
            SpeechRecognitionHelper.safeDestroy(speechRecognizer)
            storytellingService?.stopStory()
            ttsManager?.shutdown()
            soundManager?.release()
        } catch (e: Throwable) {
            Log.e(TAG, "Error onCleared MainViewModel", e)
        }
    }
}

