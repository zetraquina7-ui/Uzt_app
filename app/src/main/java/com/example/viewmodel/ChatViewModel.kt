package com.example.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.ZeTraquinaChatRepository
import com.example.data.AppDatabase
import com.example.data.ZeAICacheDao
import com.example.data.ZeAICacheRepository
import com.example.data.ZeAILocalKnowledgeBase
import com.example.data.ZeChatHistoryDao
import com.example.data.ZeChatHistoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

data class ChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val text: String,
    val isFromUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

enum class ZeAITab {
    CONVERSAR,
    HISTORICO
}

class ChatViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "ChatViewModel"
    private val repository by lazy { ZeTraquinaChatRepository() }
    private val cacheRepository by lazy { ZeAICacheRepository(application) }
    private var historyDao: ZeChatHistoryDao? = null

    private val _selectedTab = MutableStateFlow(ZeAITab.CONVERSAR)
    val selectedTab: StateFlow<ZeAITab> = _selectedTab.asStateFlow()

    private val _persistentHistory = MutableStateFlow<List<ZeChatHistoryEntity>>(emptyList())
    val persistentHistory: StateFlow<List<ZeChatHistoryEntity>> = _persistentHistory.asStateFlow()

    private val _messages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                text = "Olá amiguinhos!\nO que vamos aprender hoje?",
                isFromUser = false
            )
        )
    )
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                cacheRepository.initializeIfNeeded()
            } catch (e: Exception) {
                Log.e(TAG, "Error initializing cache repository", e)
            }

            try {
                val dao = AppDatabase.getDatabase(application).zeChatHistoryDao()
                historyDao = dao
                dao.getAllHistory()
                    .flowOn(Dispatchers.IO)
                    .catch { e ->
                        Log.e(TAG, "Error collecting history Flow", e)
                    }
                    .collect { list ->
                        _persistentHistory.value = list
                    }
            } catch (e: Exception) {
                Log.e(TAG, "Error initializing ZeChatHistoryDao in background", e)
            }
        }
    }

    fun selectTab(tab: ZeAITab) {
        _selectedTab.value = tab
    }

    /**
     * Records a completed single-request voice interaction directly without making
     * any secondary network calls to the Gemini API, preserving quota.
     */
    fun recordSingleRequestConversation(userText: String, aiReply: String) {
        val query = userText.ifBlank { "Áudio do amiguinho 🎤" }
        val userMessage = ChatMessage(text = query, isFromUser = true)
        val mascotMessage = ChatMessage(text = aiReply, isFromUser = false)
        
        // EXPLICIT CHECKPOINT: Voice transcription and reply insertion into messages list state
        Log.i(TAG, "[CHECKPOINT ESTADO DA MENSAGEM] Inserindo transcrição de voz recebida e resposta no estado (_messages): '$query' -> '$aiReply'")
        
        _messages.value = _messages.value + userMessage + mascotMessage
        _isLoading.value = false

        viewModelScope.launch(Dispatchers.IO) {
            try {
                cacheRepository.cacheResponse(query, aiReply)
                historyDao?.insertHistory(
                    ZeChatHistoryEntity(
                        question = query,
                        answer = aiReply,
                        timestamp = System.currentTimeMillis()
                    )
                )
            } catch (e: Exception) {
                Log.e(TAG, "Error persisting single-request conversation", e)
            }
        }
    }

    fun sendMessage(userText: String, onResponseReceived: (String) -> Unit) {
        Log.i("VOICE_DEBUG", "[VOICE DEBUG CHATVIEWMODEL] ChatViewModel.sendMessage() recebido com texto: '$userText'")
        if (userText.isBlank()) return

        val trimmedQuery = userText.trim()
        val userMessage = ChatMessage(text = trimmedQuery, isFromUser = true)
        _messages.value = _messages.value + userMessage
        _isLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val history = _messages.value
                .drop(1) // exclude initial greeting
                .chunked(2)
                .mapNotNull { pair ->
                    if (pair.size == 2 && pair[0].isFromUser && !pair[1].isFromUser) {
                        Pair(pair[0].text, pair[1].text)
                    } else null
                }

            var finalResponse: String
            try {
                Log.d(TAG, "Attempting to send message to repository: '$trimmedQuery'")
                Log.i("VOICE_DEBUG", "[VOICE DEBUG REPOSITORY] ZeTraquinaChatRepository.sendMessage() chamado com query: '$trimmedQuery'")
                val dao = AppDatabase.getDatabase(getApplication()).zeAICacheDao()
                val onlineResponse = repository.sendMessage(trimmedQuery, history, dao)

                if (onlineResponse.isBlank()) {
                    Log.w(TAG, "Online response was blank for query '$trimmedQuery', resolving from cache")
                    finalResponse = cacheRepository.getOfflineResponse(trimmedQuery)
                } else {
                    finalResponse = onlineResponse
                    Log.d(TAG, "Received successful online response: '$finalResponse'")
                    // Save successful response to local cache for future offline usage
                    cacheRepository.cacheResponse(trimmedQuery, finalResponse)
                }
                Log.i("VOICE_DEBUG", "[VOICE DEBUG GEMINI SUCCESS] Resposta do Gemini/API recebida: '$finalResponse'")
            } catch (e: Exception) {
                Log.e("VOICE_DEBUG", "[VOICE DEBUG GEMINI ERROR] Gemini/API devolveu ERRO real:", e)
                Log.e(TAG, "Chat repository request failed for '$trimmedQuery': ${e.message}", e)
                finalResponse = cacheRepository.getOfflineResponse(trimmedQuery)
                Log.i("VOICE_DEBUG", "[VOICE DEBUG GEMINI FALLBACK] Resposta obtida via Cache local/offline (devido a erro): '$finalResponse'")
            }

            Log.i("VOICE_DEBUG", "[VOICE DEBUG UI RESPONSE] Enviando resposta para a UI: '$finalResponse'")
            val mascotMessage = ChatMessage(text = finalResponse, isFromUser = false)
            _messages.value = _messages.value + mascotMessage
            _isLoading.value = false

            // Content Safety Check for saving into History:
            val isBlocked = finalResponse.contains("não posso responder", ignoreCase = true) ||
                    finalResponse.contains("Não posso ajudar com esse assunto", ignoreCase = true)

            if (!isBlocked && trimmedQuery.isNotBlank() && finalResponse.isNotBlank()) {
                try {
                    historyDao?.insertHistory(
                        ZeChatHistoryEntity(
                            question = trimmedQuery,
                            answer = finalResponse,
                            timestamp = System.currentTimeMillis()
                        )
                    )
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to persist chat history entry", e)
                }
            }

            launch(Dispatchers.Main) {
                onResponseReceived(finalResponse)
            }
        }
    }

    fun deleteConversationItem(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                historyDao?.deleteById(id)
            } catch (e: Exception) {
                Log.e(TAG, "Error deleting history item: $id", e)
            }
        }
    }

    fun clearAllHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                historyDao?.deleteAll()
            } catch (e: Exception) {
                Log.e(TAG, "Error clearing chat history", e)
            }
            _messages.value = listOf(
                ChatMessage(
                    text = "Olá amiguinhos!\nO que vamos aprender hoje?",
                    isFromUser = false
                )
            )
        }
    }
}
