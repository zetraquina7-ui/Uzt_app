package com.example.ui.components

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import com.example.ui.components.ZeAudioPermissionDialog
import com.example.ui.components.ZeListeningPulseIndicator
import com.example.util.AudioPermissionHelper
import com.example.util.SpeechRecognitionHelper
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.viewmodel.MainViewModel
import com.example.viewmodel.ChatViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicNone
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.PlayerView
import androidx.media3.ui.AspectRatioFrameLayout
import com.example.R
import com.example.util.PreviewConfig
import com.example.viewmodel.LiveAvatarState
import com.example.audio.ZeTraquinaPuckTTSService
import kotlinx.coroutines.launch

private val VIDEO_URL_IDLE = com.example.util.ZeAvatarCacheManager.VIDEO_URL_IDLE
private val VIDEO_URL_LISTENING = com.example.util.ZeAvatarCacheManager.VIDEO_URL_LISTENING
private val VIDEO_URL_TALKING = com.example.util.ZeAvatarCacheManager.VIDEO_URL_TALKING

@androidx.media3.common.util.UnstableApi
@Composable
fun ZeLiveAvatarComponent(
    mainViewModel: MainViewModel,
    chatViewModel: ChatViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val isLoading by chatViewModel.isLoading.collectAsState()
    
    var isWaitingForSpeech by remember { mutableStateOf(false) }

    val avatarViewModel: com.example.viewmodel.ZeLiveAvatarViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val avatarState by avatarViewModel.avatarState.collectAsState()
    val currentVideoUrl by avatarViewModel.currentVideoUrl.collectAsState()
    val voiceSessionState by avatarViewModel.voiceSessionState.collectAsState()

    var inputText by remember { mutableStateOf("") }
    val isListeningVoice by mainViewModel.isListening.collectAsStateWithLifecycle()
    val isSpeaking by mainViewModel.isSpeaking.collectAsStateWithLifecycle()
    val isGeneratingAudio by mainViewModel.isGeneratingAudio.collectAsStateWithLifecycle()
    var isThinking by remember { mutableStateOf(false) }
    val speechErrorMessageState by mainViewModel.voiceError.collectAsStateWithLifecycle()
    var speechErrorMessage by remember { mutableStateOf<String?>(null) }
    var speechVolumeLevel by remember { mutableStateOf(0f) }
    var lastResponseText by remember { mutableStateOf("Olá amiguinho! Sou o Zé Traquina! Podes falar comigo pelo microfone ou escrever o que quiseres aprender ou brincar hoje! 🎒✨") }

    val isPreviewMode = LocalInspectionMode.current || PreviewConfig.isInPreview()
    var hasHeardSpeech by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        onDispose {
            mainViewModel.releaseSpeechRecognizer()
            mainViewModel.stopSpeaking()
            speechVolumeLevel = 0f
        }
    }

    // Optimized state transition logic with debouncing and synchronization delay
    val targetAvatarState = remember(isSpeaking, isLoading, isListeningVoice, inputText, isWaitingForSpeech, isThinking, isGeneratingAudio) {
        when {
            // Priority 1: Video changes to TALKING ONLY when audio is actively playing through the speaker! (Issue 7)
            isSpeaking -> LiveAvatarState.TALKING
            
            // Priority 2: Waiting for AI response or audio synthesis (PENSAR state - Issue 7)
            isThinking || isGeneratingAudio || isWaitingForSpeech || isLoading -> LiveAvatarState.THINKING
            
            // Priority 3: If listening or has partial input text
            isListeningVoice || inputText.isNotBlank() -> LiveAvatarState.LISTENING
            
            // Priority 4: Idle
            else -> LiveAvatarState.IDLE
        }
    }

    LaunchedEffect(mainViewModel.rmsValue) {
        mainViewModel.rmsValue.collect { rmsdB ->
            speechVolumeLevel = ((rmsdB + 2f) / 12f).coerceIn(0f, 1f)
        }
    }

    LaunchedEffect(mainViewModel.voiceError) {
        mainViewModel.voiceError.collect { error ->
            if (error != null) {
                speechErrorMessage = error
                avatarViewModel.setAvatarState(LiveAvatarState.IDLE)
            }
        }
    }

    // Function to trigger message sending and AI reply + TTS flow
    val handleSendQuestion: (String) -> Unit = { queryText ->
        val query = queryText.trim()
        if (query.isNotBlank()) {
            speechErrorMessage = null
            mainViewModel.clearVoiceResult()
            Log.i("VOICE_DEBUG", "[VOICE DEBUG 6] ChatViewModel.sendMessage chamado com query: '$query'")
            
            // Clear input text immediately so it doesn't get stuck in LISTENING state
            inputText = ""
            
            // Show thinking state in the text bubble
            lastResponseText = "A preparar a resposta... ⏳"
            
            try {
                mainViewModel.releaseSpeechRecognizer()
            } catch (_: Exception) {}

            mainViewModel.playClickSound()
            // State "A Pensar" enquanto aguarda o Gemini e a geração do TTS (Issue 7)
            isThinking = true
            avatarViewModel.setAvatarState(LiveAvatarState.THINKING)

            chatViewModel.sendMessage(query) { reply ->
                Log.i("VOICE_DEBUG", "[VOICE DEBUG 9] Resposta enviada para a UI (callback recebido): '$reply'")
                
                // Update text bubble to indicate we are now waiting for the audio synthesis
                lastResponseText = "Quase lá... a preparar a voz! 🗣️"
                
                mainViewModel.addStars(3)
                
                // Mantém Thinking até que o áudio realmente comece a tocar
                isThinking = false
                isWaitingForSpeech = true
                avatarViewModel.setAvatarState(LiveAvatarState.THINKING)
                
                mainViewModel.speak(
                    text = reply,
                    onPlaybackStarted = {
                        // Only show the actual response text when the audio starts playing
                        lastResponseText = reply
                        isWaitingForSpeech = false
                        avatarViewModel.setAvatarState(LiveAvatarState.TALKING)
                    },
                    onComplete = {
                        if (lastResponseText != reply) {
                            lastResponseText = reply
                        }
                        isWaitingForSpeech = false
                    }
                )
            }
        }
    }

    // Voice recognition permission launcher and rationale dialog state
    var showPermissionRationaleDialog by remember { mutableStateOf(false) }

    fun toggleVoiceInputInternal() {
        speechErrorMessage = null
        if (mainViewModel.isListening.value) {
            mainViewModel.stopListening()
            speechVolumeLevel = 0f
            avatarViewModel.setAvatarState(LiveAvatarState.IDLE)
        } else {
            if (isSpeaking) {
                mainViewModel.stopSpeaking()
            }

            inputText = ""

            mainViewModel.startListening(
                onResult = { transcription ->
                    coroutineScope.launch(Dispatchers.Main) {
                        speechErrorMessage = null
                        if (transcription.isNotBlank()) {
                            inputText = transcription
                            handleSendQuestion(transcription)
                        } else {
                            speechErrorMessage = "Não percebi bem. Podes repetir? 🎤"
                            avatarViewModel.setAvatarState(LiveAvatarState.IDLE)
                        }
                        speechVolumeLevel = 0f
                    }
                },
                onPartial = { partial ->
                    coroutineScope.launch(Dispatchers.Main) {
                        if (partial.isNotBlank()) {
                            inputText = partial
                        }
                    }
                }
            )
            
            avatarViewModel.setAvatarState(LiveAvatarState.LISTENING)
            mainViewModel.playClickSound()
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(context, "Microfone autorizado! Fala com o Zé! 🎤", Toast.LENGTH_SHORT).show()
            avatarViewModel.onUserConfirmedVoice(true)
        } else {
            Toast.makeText(context, "Precisamos do microfone para ouvires o Zé! 🎤", Toast.LENGTH_SHORT).show()
            avatarViewModel.onUserConfirmedVoice(false)
        }
    }

    val toggleVoiceInput = {
        speechErrorMessage = null
        if (isListeningVoice) {
            toggleVoiceInputInternal()
        } else {
            // Verifica se a permissão RECORD_AUDIO já foi concedida (Issue 5)
            if (com.example.util.AudioPermissionHelper.hasAudioPermission(context)) {
                // Se já tem permissão, ignora o diálogo de confirmação e inicia
                avatarViewModel.onUserConfirmedVoice(true)
            } else {
                avatarViewModel.requestVoiceConfirmation()
            }
        }
    }

    LaunchedEffect(voiceSessionState) {
        if (voiceSessionState == com.example.viewmodel.VoiceSessionState.INITIALIZING_SPEECH_RECOGNIZER) {
            if (com.example.util.AudioPermissionHelper.hasAudioPermission(context)) {
                toggleVoiceInputInternal()
                avatarViewModel.onRecognizerReady()
            } else {
                avatarViewModel.onUserConfirmedVoice(false)
            }
        }
    }

    // Infinite transitions for pulsing animations
    val infiniteTransition = rememberInfiniteTransition(label = "live_avatar_anim")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_alpha"
    )

    // Border color depending on state
    val stateBorderColor by animateColorAsState(
        targetValue = when (avatarState) {
            LiveAvatarState.IDLE -> Color(0xFF6366F1)
            LiveAvatarState.LISTENING -> Color(0xFF10B981)
            LiveAvatarState.THINKING -> Color(0xFFFBBF24) // Amber for thinking
            LiveAvatarState.TALKING -> Color(0xFFEC4899)
        },
        animationSpec = tween(400),
        label = "border_color"
    )



    // Force sync the state to the ViewModel whenever targetAvatarState changes
    LaunchedEffect(targetAvatarState) {
        avatarViewModel.setAvatarState(targetAvatarState)
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .imePadding()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ==========================================
            // 1. Central Live Avatar Video Player Container
            // Uses Column weight and Modifier.aspectRatio(1f) to prevent distortion across all device sizes
            // ==========================================
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.2f)
                    .padding(vertical = 2.dp),
                contentAlignment = Alignment.Center
            ) {
                val videoCardSize = minOf(maxWidth, maxHeight)
                Card(
                    modifier = Modifier
                        .size(videoCardSize)
                        .aspectRatio(1f)
                        .shadow(
                            elevation = if (avatarState != LiveAvatarState.IDLE) 10.dp else 5.dp,
                            shape = RoundedCornerShape(24.dp),
                            spotColor = stateBorderColor.copy(alpha = glowAlpha)
                        )
                        .testTag("live_avatar_video_card"),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
                    border = BorderStroke(2.dp, stateBorderColor)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        // Background subtle radiant glow behind character
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = listOf(
                                            stateBorderColor.copy(alpha = 0.25f),
                                            Color.Transparent
                                        )
                                    )
                                )
                        )

                        // The continuous state-driven video player
                        LiveAvatarVideoRenderer(
                            videoUrl = currentVideoUrl,
                            avatarState = avatarState,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(24.dp))
                        )

                        // Interactive badge overlay in top-right (Discreet LIVE/STATUS indicator)
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color.Black.copy(alpha = 0.50f),
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 7.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val badgeColor = when {
                                    avatarState == LiveAvatarState.TALKING -> Color(0xFFEC4899)
                                    avatarState == LiveAvatarState.LISTENING -> Color(0xFF10B981)
                                    else -> Color(0xFFEF4444)
                                }
                                val badgeText = when {
                                    avatarState == LiveAvatarState.TALKING -> "FALANDO..."
                                    avatarState == LiveAvatarState.LISTENING -> "A OUVIR..."
                                    else -> "AO VIVO"
                                }
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .background(badgeColor, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = badgeText,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }

                        // Microphone status overlay inside the video area, absolute bottom-centered
                        ZeListeningPulseIndicator(
                            isListening = isListeningVoice,
                            volumeLevel = speechVolumeLevel,
                            spokenTextPreview = inputText,
                            onStopListening = { toggleVoiceInput() },
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(start = 8.dp, end = 8.dp, bottom = 10.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // ==========================================
            // 2. Speech / Subtitle Bubble Card
            // Uses weight to adapt responsively, with internal scroll for long responses
            // ==========================================
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.96f)
                    .weight(0.8f)
                    .padding(vertical = 2.dp)
                    .shadow(3.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
                border = BorderStroke(1.2.dp, Color(0xFFE2E8F0))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "💬", fontSize = 13.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Zé Traquina",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF4338CA)
                            )
                        }

                        if (isSpeaking) {
                            IconButton(
                                onClick = {
                                    mainViewModel.stopSpeaking()
                                },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Stop,
                                    contentDescription = "Parar Áudio",
                                    tint = Color(0xFFEF4444),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        } else if (lastResponseText.isNotBlank()) {
                            IconButton(
                                onClick = {
                                    mainViewModel.speak(
                                        text = lastResponseText,
                                        onPlaybackStarted = {
                                            avatarViewModel.setAvatarState(LiveAvatarState.TALKING)
                                        }
                                    )
                                },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VolumeUp,
                                    contentDescription = "Repetir Voz",
                                    tint = Color(0xFF6366F1),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = lastResponseText,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1E293B),
                        textAlign = TextAlign.Center
                    )
                }
            }

            AnimatedVisibility(
                visible = !isListeningVoice && speechErrorMessage != null,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(0.96f)
                        .padding(vertical = 2.dp)
                        .testTag("speech_feedback_banner"),
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFFFEF2F2),
                    border = BorderStroke(1.dp, Color(0xFFFCA5A5))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = "⚠️", fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = speechErrorMessage ?: "",
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFFDC2626),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            TextButton(
                                onClick = {
                                    speechErrorMessage = null
                                    toggleVoiceInput()
                                },
                                contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
                                modifier = Modifier.height(28.dp)
                            ) {
                                Text(
                                    text = "Tentar de novo 🔄",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFDC2626)
                                )
                            }
                            IconButton(
                                onClick = { speechErrorMessage = null },
                                modifier = Modifier.size(20.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Fechar erro",
                                    tint = Color(0xFFDC2626),
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                }
            }

            // ==========================================
            // 3. Sticky Bottom Input Bar
            // ==========================================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .background(Color.White, RoundedCornerShape(50))
                    .border(
                        width = 1.5.dp,
                        brush = Brush.horizontalGradient(
                            listOf(Color(0xFF6366F1), Color(0xFFEC4899), Color(0xFFF59E0B))
                        ),
                        shape = RoundedCornerShape(50)
                    )
                    .padding(horizontal = 4.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Microphone Action Button
                val micBgColor = when {
                    isListeningVoice -> Color(0xFFEF4444)
                    isLoading -> Color(0xFFF59E0B)
                    speechErrorMessage != null -> Color(0xFFDC2626)
                    else -> Color(0xFF4F46E5)
                }

                val micIcon = when {
                    isListeningVoice -> Icons.Default.Mic
                    isLoading -> Icons.Default.Refresh
                    speechErrorMessage != null -> Icons.Default.Refresh
                    else -> Icons.Default.MicNone
                }

                IconButton(
                    onClick = {
                        if (isLoading) return@IconButton
                        if (speechErrorMessage != null) {
                            speechErrorMessage = null
                        }
                        toggleVoiceInput()
                    },
                    enabled = !isLoading,
                    modifier = Modifier
                        .size(36.dp)
                        .background(micBgColor, CircleShape)
                        .testTag("live_avatar_mic_btn")
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = micIcon,
                            contentDescription = "Falar ao Microfone",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(4.dp))

                // Text Input Field
                TextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = {
                        Text(
                            text = "Pergunta ao Zé...",
                            fontSize = 12.sp,
                            color = Color(0xFF94A3B8),
                            fontWeight = FontWeight.Medium
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("live_avatar_text_input"),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color(0xFF0F172A),
                        unfocusedTextColor = Color(0xFF0F172A)
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(4.dp))

                // Send Button
                val canSend = inputText.isNotBlank()
                IconButton(
                    onClick = { handleSendQuestion(inputText) },
                    enabled = canSend,
                    modifier = Modifier
                        .size(36.dp)
                        .shadow(if (canSend) 3.dp else 0.dp, CircleShape)
                        .background(
                            if (canSend) {
                                Brush.linearGradient(listOf(Color(0xFF10B981), Color(0xFF059669)))
                            } else {
                                Brush.linearGradient(listOf(Color(0xFFE2E8F0), Color(0xFFCBD5E1)))
                            },
                            CircleShape
                        )
                        .testTag("live_avatar_send_btn")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Enviar Pergunta",
                        tint = if (canSend) Color.White else Color(0xFF94A3B8),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            
            Text(
                text = "O Zé Traquina é um personagem/avatar digital criado com IA.",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)
            )
        }
    }

    // Purely State-Driven Permission & Confirmation Dialog
    if (voiceSessionState == com.example.viewmodel.VoiceSessionState.AWAITING_USER_CONFIRMATION) {
        ZeAudioPermissionDialog(
            onDismissRequest = {
                avatarViewModel.resetVoiceSession()
            },
            onConfirmPermission = {
                if (com.example.util.AudioPermissionHelper.hasAudioPermission(context)) {
                    avatarViewModel.onUserConfirmedVoice(true)
                } else {
                    permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                }
            }
        )
    }
}

/**
 * High-performance video renderer with preview fallback and graceful error handling.
 * Renders the talking avatar video cropped seamlessly to a 1:1 square without black bars,
 * anchored to the top so that the character's head, face and cap are never cut off.
 */
@androidx.media3.common.util.UnstableApi
@Composable
private fun LiveAvatarVideoRenderer(
    videoUrl: String,
    avatarState: LiveAvatarState = LiveAvatarState.IDLE,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(24.dp)),
        contentAlignment = Alignment.TopCenter
    ) {
        // Fallback / Poster mascot background image (always visible so screen is never blank/black)
        Image(
            painter = painterResource(id = R.drawable.ze_mascot_green_polo),
            contentDescription = "Mascote Zé Traquina",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter
        )

        val isInspection = LocalInspectionMode.current
        if (!isInspection) {
            LiveAvatarPlayer(
                videoUrl = videoUrl,
                avatarState = avatarState,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@androidx.media3.common.util.UnstableApi
@Composable
private fun LiveAvatarPlayer(
    videoUrl: String,
    avatarState: LiveAvatarState = LiveAvatarState.IDLE,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var videoRatio by remember { mutableStateOf(9f / 16f) }
    var isPlayerError by remember(videoUrl) { mutableStateOf(false) }
    
    val exoPlayer = remember {
        try {
            val httpDataSourceFactory = androidx.media3.datasource.DefaultHttpDataSource.Factory()
                .setUserAgent("Mozilla/5.0 (Linux; Android 13) AppleWebKit/537.36")
                .setConnectTimeoutMs(8000)
                .setReadTimeoutMs(8000)

            val dataSourceFactory = androidx.media3.datasource.DefaultDataSource.Factory(
                context.applicationContext,
                httpDataSourceFactory
            )

            val mediaSourceFactory = androidx.media3.exoplayer.source.DefaultMediaSourceFactory(context.applicationContext)
                .setDataSourceFactory(dataSourceFactory)
                
            val renderersFactory = com.example.util.ExoPlayerHelper.createRenderersFactory(
                context = context,
                isVideoOnly = true
            )

            val loadControl = androidx.media3.exoplayer.DefaultLoadControl.Builder()
                .setBufferDurationsMs(
                    1_500,
                    5_000,
                    200,
                    400
                )
                .setPrioritizeTimeOverSizeThresholds(true)
                .setBackBuffer(
                    0,
                    false
                )
                .build()

            androidx.media3.exoplayer.ExoPlayer.Builder(context.applicationContext, renderersFactory)
                .setMediaSourceFactory(mediaSourceFactory)
                .setLoadControl(loadControl)
                .build().apply {
                    repeatMode = androidx.media3.common.Player.REPEAT_MODE_ONE
                    volume = 0f
                    playWhenReady = true
                    
                    // CRITICAL: Disable audio track decoding completely to prevent AudioTrack crashes 
                    // when the microphone (AudioRecord) is simultaneously active, especially on emulators.
                    trackSelectionParameters = trackSelectionParameters.buildUpon()
                        .setTrackTypeDisabled(androidx.media3.common.C.TRACK_TYPE_AUDIO, true)
                        .build()
                        
                    val player = this
                    addListener(object : androidx.media3.common.Player.Listener {
                        override fun onVideoSizeChanged(videoSize: androidx.media3.common.VideoSize) {
                            if (videoSize.width > 0 && videoSize.height > 0) {
                                videoRatio = videoSize.width.toFloat() / videoSize.height.toFloat()
                            }
                        }
                        override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                            Log.w("LiveAvatarPlayer", "ExoPlayer playback error for avatar video: ${error.message}")
                            try {
                                player.stop()
                                player.clearMediaItems()
                            } catch (_: Throwable) {}
                            if (videoUrl.isNotBlank()) {
                                com.example.util.MediaFailureTracker.addFailedUrl(videoUrl)
                            }
                            isPlayerError = true
                        }
                    })
                }
        } catch (e: Throwable) {
            Log.e("LiveAvatarPlayer", "Error creating ExoPlayer instance", e)
            null
        }
    }

    DisposableEffect(exoPlayer) {
        onDispose {
            try {
                exoPlayer?.stop()
                exoPlayer?.release()
            } catch (e: Throwable) {
                Log.e("LiveAvatarPlayer", "Error releasing ExoPlayer", e)
            }
        }
    }

    LaunchedEffect(videoUrl, exoPlayer) {
        val player = exoPlayer ?: return@LaunchedEffect
        if (videoUrl.isNotEmpty()) {
            val isCached = com.example.util.ZeAvatarCacheManager.isCached(context, videoUrl)
            if (!isCached && com.example.util.MediaFailureTracker.isUrlFailed(videoUrl)) {
                Log.w("LiveAvatarPlayer", "Skipping failed URL: $videoUrl")
                return@LaunchedEffect
            }
            try {
                val resolvedUri = com.example.util.ZeAvatarCacheManager.getMediaUri(context, videoUrl)
                
                if (com.example.util.EmulatorUtils.isEmulator && videoUrl == com.example.util.ZeAvatarCacheManager.VIDEO_URL_LISTENING) {
                    Log.w("LiveAvatarPlayer", "Skipping video switch to LISTENING on emulator to prevent codec crash.")
                    return@LaunchedEffect
                }

                val mediaItem = androidx.media3.common.MediaItem.Builder()
                    .setUri(resolvedUri)
                    .build()
                if (player.currentMediaItem?.localConfiguration?.uri != mediaItem.localConfiguration?.uri) {
                    player.setMediaItem(mediaItem)
                    player.prepare()
                    player.play()
                } else {
                    player.play()
                }
            } catch (e: Throwable) {
                Log.e("LiveAvatarPlayer", "Error loading media item", e)
                com.example.util.MediaFailureTracker.addFailedUrl(videoUrl)
            }
        }
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(24.dp)),
        contentAlignment = Alignment.TopCenter
    ) {
        val containerWidth = maxWidth
        val containerHeight = maxHeight
        val calculatedHeight = if (videoRatio > 0f) containerWidth / videoRatio else containerHeight
        val finalHeight = maxOf(containerHeight, calculatedHeight)

        if (exoPlayer != null) {
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(finalHeight)
                    .align(Alignment.TopCenter),
                factory = { ctx ->
                    try {
                        (android.view.LayoutInflater.from(ctx).inflate(R.layout.exo_texture_player, null) as androidx.media3.ui.PlayerView).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            useController = false
                            resizeMode = androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_FILL
                            player = exoPlayer
                            setKeepContentOnPlayerReset(true)
                            setShutterBackgroundColor(android.graphics.Color.TRANSPARENT)
                            setBackgroundColor(android.graphics.Color.TRANSPARENT)
                        }
                    } catch (e: Throwable) {
                        Log.e("LiveAvatarPlayer", "Failed inflating PlayerView", e)
                        try {
                            (android.view.LayoutInflater.from(ctx).inflate(R.layout.exo_texture_player, null) as androidx.media3.ui.PlayerView).apply {
                                useController = false
                                player = exoPlayer
                            }
                        } catch (e2: Throwable) {
                            androidx.media3.ui.PlayerView(ctx).apply {
                                useController = false
                                player = exoPlayer
                            }
                        }
                    }
                },
                update = { view ->
                    try {
                        if (view.player != exoPlayer) {
                            view.player = exoPlayer
                        }
                    } catch (_: Throwable) {}
                },
                onRelease = { view ->
                    try {
                        view.player = null
                    } catch (_: Throwable) {}
                }
            )
        }

        if (exoPlayer == null ||
            isPlayerError ||
            (!com.example.util.ZeAvatarCacheManager.isCached(context, videoUrl) && com.example.util.MediaFailureTracker.isUrlFailed(videoUrl))) {
            // Fallback mascot image if video fails or is unsupported
            androidx.compose.foundation.Image(
                painter = androidx.compose.ui.res.painterResource(id = R.drawable.ze_mascot_green_polo),
                contentDescription = "Avatar Zé Traquina",
                modifier = Modifier.fillMaxSize().background(Color(0xFFE0F7FA))
            )
        }
    }
}
