package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.util.ZeAvatarCacheManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class LiveAvatarState {
    IDLE,
    LISTENING,
    THINKING,
    TALKING
}

enum class VoiceSessionState {
    IDLE,
    AWAITING_USER_CONFIRMATION, // Explicit friendly screen/dialog where child must click "Confirm" or "Yes"
    CHECKING_PERMISSION,
    PERMISSION_DENIED,
    INITIALIZING_SPEECH_RECOGNIZER,
    RECORDING,
    PROCESSING,
    COMPLETED,
    ERROR
}

class ZeLiveAvatarViewModel : ViewModel() {

    private val _avatarState = MutableStateFlow(LiveAvatarState.IDLE)
    val avatarState: StateFlow<LiveAvatarState> = _avatarState.asStateFlow()

    private val _currentVideoUrl = MutableStateFlow(ZeAvatarCacheManager.VIDEO_URL_IDLE)
    val currentVideoUrl: StateFlow<String> = _currentVideoUrl.asStateFlow()

    private val _voiceSessionState = MutableStateFlow(VoiceSessionState.IDLE)
    val voiceSessionState: StateFlow<VoiceSessionState> = _voiceSessionState.asStateFlow()

    fun resetVoiceSession() {
        _voiceSessionState.value = VoiceSessionState.IDLE
    }

    fun requestVoiceConfirmation() {
        _voiceSessionState.value = VoiceSessionState.AWAITING_USER_CONFIRMATION
    }

    fun onUserConfirmedVoice(hasPermission: Boolean) {
        if (!hasPermission) {
            _voiceSessionState.value = VoiceSessionState.PERMISSION_DENIED
        } else {
            _voiceSessionState.value = VoiceSessionState.INITIALIZING_SPEECH_RECOGNIZER
        }
    }

    fun onRecognizerReady() {
        _voiceSessionState.value = VoiceSessionState.RECORDING
    }

    fun onProcessingVoice() {
        _voiceSessionState.value = VoiceSessionState.PROCESSING
    }

    fun onVoiceSuccess() {
        _voiceSessionState.value = VoiceSessionState.COMPLETED
    }

    fun onVoiceError() {
        _voiceSessionState.value = VoiceSessionState.ERROR
    }

    /**
     * Updates avatar state and maps it to the appropriate local/remote video URL
     * ensuring fluid switching in Media3 ExoPlayer.
     */
    fun setAvatarState(newState: LiveAvatarState) {
        _avatarState.value = newState
        _currentVideoUrl.value = when (newState) {
            LiveAvatarState.IDLE -> ZeAvatarCacheManager.VIDEO_URL_IDLE
            LiveAvatarState.LISTENING -> ZeAvatarCacheManager.VIDEO_URL_LISTENING
            LiveAvatarState.THINKING -> ZeAvatarCacheManager.VIDEO_URL_IDLE
            LiveAvatarState.TALKING -> ZeAvatarCacheManager.VIDEO_URL_TALKING
        }
    }
}

