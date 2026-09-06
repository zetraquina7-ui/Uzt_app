package com.example.ui.components

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import com.example.ui.components.ZeAudioPermissionDialog
import com.example.ui.components.ZeListeningPulseIndicator
import com.example.viewmodel.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.util.AudioPermissionHelper
import com.example.util.SpeechRecognitionHelper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.util.AppImageLoader
import com.example.ui.theme.KidStarOrange
import com.example.ui.theme.MintGreen
import com.example.ui.theme.SkyBluePrimary
import com.example.ui.theme.SunshineYellow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

/**
 * Emotional states for Zé Traquina character mascot
 */
enum class MascotEmotion {
    HAPPY,          // Standard happy smile
    CELEBRATING,    // Quiz completion / victory celebration
    EXCITED,        // Gaining stars / progression
    PROUD,          // Level up or high achievement
    THINKING,       // Quiz in progress / reflection
    IDLE,           // Idle state
    TALKING         // Talking state
}

data class VoiceCommandItem(
    val icon: String,
    val text: String,
    val response: String,
    val emotion: MascotEmotion
)

val defaultVoiceCommands = listOf(
    VoiceCommandItem("👋", "Olá Zé!", "Olá amiguinho! Que bom ter-te no Universo Zé Traquina! 🧢✨", MascotEmotion.HAPPY),
    VoiceCommandItem("🦘", "Dá um salto!", "Boing! Boing! Saltinho bem alto! Apanhei uma estrela! 🦘⭐", MascotEmotion.EXCITED),
    VoiceCommandItem("🎵", "Canta uma música!", "Traquina, Traquina, a cantarola! A aprender e a jogar na escola! 🎶🎤", MascotEmotion.CELEBRATING),
    VoiceCommandItem("😂", "Conta uma piada!", "O que diz uma impressora para a outra? Essa folha é tua ou é impressão minha? Ahahah! 📄😂", MascotEmotion.EXCITED),
    VoiceCommandItem("🎮", "Que jogo queres jogar?", "Adoro o Sudoku Infantil e o Detetive de Palavras! Vamos a isto? 🕹️🌟", MascotEmotion.THINKING),
    VoiceCommandItem("😄", "Rir!", "Hihihi! Ahahah! Fizeram-me cócegas! Que gargalhada boa! 😄🎉", MascotEmotion.CELEBRATING),
    VoiceCommandItem("👋", "Tchau Zé!", "Até logo, campeão! Volte sempre a brincar comigo! 👋💙", MascotEmotion.HAPPY)
)

/**
 * Interactive Zé Traquina mascot character component with:
 * - Touch & Tap interactions (spring jumps, wiggles, particles, TTS voice responses)
 * - Android SpeechRecognizer integration for live voice commands spoken by children
 * - Quick-tap voice command chips for fallback/preview
 * - Emotional animations & dynamic speech bubbles
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ZeTraquinaMascot(
    modifier: Modifier = Modifier,
    size: Dp = 80.dp,
    showSpeechBubble: Boolean = true,
    customPhrase: String? = null,
    emotion: MascotEmotion = MascotEmotion.HAPPY,
    triggerEmotionKey: Any? = null,
    enableVoiceCommand: Boolean = false,
    onInteract: (() -> Unit)? = null,
    mainViewModel: MainViewModel = viewModel()
) {
    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current
    val coroutineScope = rememberCoroutineScope()

    // Interactive & active emotion state
    var activeEmotion by remember { mutableStateOf(emotion) }
    var isInteracting by remember { mutableStateOf(false) }
    var interactionCount by remember { mutableStateOf(0) }
    var showParticles by remember { mutableStateOf(false) }
    var phraseIndex by remember { mutableStateOf(0) }
    var voiceDialogOpen by remember { mutableStateOf(false) }
    
    // Use MainViewModel states
    val isListeningVoice by mainViewModel.isListening.collectAsStateWithLifecycle()
    val recognizedSpeechText by mainViewModel.voiceResult.collectAsStateWithLifecycle()
    val voiceError by mainViewModel.voiceError.collectAsStateWithLifecycle()
    val isSpeaking by mainViewModel.isSpeaking.collectAsStateWithLifecycle()
    
    var showPermissionRationaleDialog by remember { mutableStateOf(false) }

    fun speakMascot(text: String) {
        mainViewModel.speak(text)
    }

    // Sync external emotion parameter or trigger key changes
    LaunchedEffect(emotion, triggerEmotionKey) {
        if (triggerEmotionKey != null && triggerEmotionKey != false) {
            activeEmotion = MascotEmotion.CELEBRATING
            showParticles = true
            isInteracting = true
            speakMascot("Viva! Fantástico trabalho amiguinho!")
            delay(3500)
            showParticles = false
            isInteracting = false
            activeEmotion = emotion
        } else {
            activeEmotion = emotion
        }
    }

    val defaultPhrases = remember {
        listOf(
            "Olá amiguinho! Vamos brincar e aprender hoje? 🧢✨",
            "Fizeste-me cócegas! És um super campeão! 😄⭐",
            "Dá um salto comigo! 1, 2, 3, pular! 🦘🚀",
            "Adoro a Escola Mágica e o Parquinho dos Jogos! 🎨🎮",
            "Sorri e diverte-te no Universo Zé Traquina! 🎉💙"
        )
    }

    val emotionPhrase by remember {
        derivedStateOf {
            if (recognizedSpeechText.isNotBlank()) recognizedSpeechText else when (activeEmotion) {
                MascotEmotion.CELEBRATING -> "FANTÁSTICO! Acertaste em cheio! PARABÉNS! 🎉🏆✨"
                MascotEmotion.EXCITED -> "UAU! Ganhaste mais estrelas reluzentes! ⭐🚀"
                MascotEmotion.PROUD -> "Estou super orgulhoso de ti, campeão! 👑🌟"
                MascotEmotion.THINKING -> "Hummm... pensa bem! Tu consegues acertar! 💭🧠"
                MascotEmotion.TALKING -> "O Zé Traquina está a falar contigo! 🗣️"
                MascotEmotion.IDLE -> "Zzz... 😴"
                MascotEmotion.HAPPY -> defaultPhrases[phraseIndex % defaultPhrases.size]
            }
        }
    }

    val currentSpeech by remember {
        derivedStateOf { customPhrase ?: emotionPhrase }
    }

    // Continuous animation for active emotions
    val pulseScale: Float
    val wobbleRotation: Float

    if (true) {
        pulseScale = 1.0f
        wobbleRotation = 0f
    } else {
        val infiniteTransition = rememberInfiniteTransition(label = "infiniteMascot")

        val animPulse by infiniteTransition.animateFloat(
            initialValue = 1.0f,
            targetValue = when (activeEmotion) {
                MascotEmotion.CELEBRATING -> 1.15f
                MascotEmotion.EXCITED -> 1.10f
                MascotEmotion.PROUD -> 1.06f
                MascotEmotion.THINKING -> 1.03f
                MascotEmotion.TALKING -> 1.08f
                MascotEmotion.IDLE -> 1.0f
                MascotEmotion.HAPPY -> 1.02f
            },
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = if (activeEmotion == MascotEmotion.CELEBRATING) 350 else 900,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "pulseScale"
        )

        val animWobble by infiniteTransition.animateFloat(
            initialValue = when (activeEmotion) {
                MascotEmotion.CELEBRATING -> -12f
                MascotEmotion.THINKING -> -8f
                else -> -3f
            },
            targetValue = when (activeEmotion) {
                MascotEmotion.CELEBRATING -> 12f
                MascotEmotion.THINKING -> 8f
                else -> 3f
            },
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = if (activeEmotion == MascotEmotion.CELEBRATING) 300 else 1200,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "wobbleRotation"
        )

        pulseScale = animPulse
        wobbleRotation = animWobble
    }

    // Drag physics state
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    var isDragging by remember { mutableStateOf(false) }

    val interactiveScale by animateFloatAsState(
        targetValue = if (isDragging) 1.28f else if (isInteracting) 1.28f else pulseScale,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "mascotScale"
    )

    val currentRotation by animateFloatAsState(
        targetValue = if (isDragging) {
            (offsetX.value / 10f).coerceIn(-30f, 30f)
        } else if (isInteracting) {
            if (interactionCount % 2 == 0) -18f else 18f
        } else {
            wobbleRotation
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "mascotRotation"
    )

    val animatedOffsetY by animateFloatAsState(
        targetValue = if (isInteracting) -16f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "mascotOffsetY"
    )

    val interactiveOffsetY = if (isDragging || offsetX.isRunning || offsetY.isRunning) {
        offsetY.value
    } else {
        animatedOffsetY
    }

    val interactiveOffsetX = offsetX.value

    val borderBrush by remember {
        derivedStateOf {
            when (activeEmotion) {
                MascotEmotion.CELEBRATING -> Brush.sweepGradient(
                    listOf(SunshineYellow, Color(0xFFFF4081), MintGreen, SkyBluePrimary, SunshineYellow)
                )
                MascotEmotion.EXCITED -> Brush.linearGradient(
                    listOf(Color(0xFFFF9800), SunshineYellow, Color(0xFFFF4081))
                )
                MascotEmotion.PROUD -> Brush.linearGradient(
                    listOf(SunshineYellow, KidStarOrange, SunshineYellow)
                )
                MascotEmotion.THINKING -> Brush.linearGradient(
                    listOf(SkyBluePrimary, Color(0xFFB39DDB), SkyBluePrimary)
                )
                MascotEmotion.TALKING -> Brush.linearGradient(
                    listOf(MintGreen, SkyBluePrimary, MintGreen)
                )
                MascotEmotion.IDLE -> Brush.linearGradient(
                    listOf(Color.LightGray, Color.Gray, Color.LightGray)
                )
                MascotEmotion.HAPPY -> Brush.sweepGradient(
                    listOf(SunshineYellow, Color(0xFFFF9800), SkyBluePrimary, SunshineYellow)
                )
            }
        }
    }

    val backgroundBrush by remember {
        derivedStateOf {
            Brush.radialGradient(
                colors = listOf(
                    when (activeEmotion) {
                        MascotEmotion.CELEBRATING -> SunshineYellow
                        MascotEmotion.EXCITED -> Color(0xFFFF80AB)
                        MascotEmotion.PROUD -> SunshineYellow
                        MascotEmotion.THINKING -> SkyBluePrimary.copy(alpha = 0.6f)
                        MascotEmotion.TALKING -> MintGreen
                        MascotEmotion.IDLE -> Color.LightGray
                        MascotEmotion.HAPPY -> SunshineYellow
                    },
                    SkyBluePrimary.copy(alpha = 0.8f)
                )
            )
        }
    }

    val mascotImageRequest = remember(context, isPreview) {
        try {
            AppImageLoader.buildMascotRequest(
                context = context,
                data = com.example.R.drawable.img_ze_traquina_original,
                isPreviewMode = isPreview
            )
        } catch (e: Throwable) {
            AppImageLoader.buildMascotRequest(
                context = context,
                data = com.example.R.drawable.img_ze_traquina_original,
                isPreviewMode = isPreview
            )
        }
    }

    val badgeIcon by remember {
        derivedStateOf {
            when (activeEmotion) {
                MascotEmotion.CELEBRATING -> "🏆"
                MascotEmotion.EXCITED -> "⚡"
                MascotEmotion.PROUD -> "👑"
                MascotEmotion.THINKING -> "💭"
                MascotEmotion.TALKING -> "🗣️"
                MascotEmotion.IDLE -> "💤"
                MascotEmotion.HAPPY -> "🧢"
            }
        }
    }

    val isParticlesVisible by remember {
        derivedStateOf {
            showParticles || activeEmotion == MascotEmotion.CELEBRATING || activeEmotion == MascotEmotion.EXCITED
        }
    }

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    // Watch for voice results to trigger mascot animations
    LaunchedEffect(recognizedSpeechText) {
        if (recognizedSpeechText.isNotBlank()) {
            val lower = recognizedSpeechText.lowercase(Locale.ROOT)
            when {
                lower.contains("viva") || lower.contains("parabéns") || lower.contains("fantástico") || lower.contains("canta") -> {
                    activeEmotion = MascotEmotion.CELEBRATING
                    showParticles = true
                }
                lower.contains("uau") || lower.contains("ganhaste") || lower.contains("salto") || lower.contains("estrela") -> {
                    activeEmotion = MascotEmotion.EXCITED
                    showParticles = true
                }
                lower.contains("orgulhoso") || lower.contains("campeão") -> {
                    activeEmotion = MascotEmotion.PROUD
                }
                lower.contains("hummm") || lower.contains("pensa") || lower.contains("jogo") -> {
                    activeEmotion = MascotEmotion.THINKING
                }
                else -> {
                    if (isSpeaking) {
                        activeEmotion = MascotEmotion.TALKING
                    }
                }
            }
            
            if (activeEmotion != MascotEmotion.HAPPY) {
                coroutineScope.launch {
                    delay(4000)
                    activeEmotion = MascotEmotion.HAPPY
                    showParticles = false
                }
            }
        }
    }

    fun startListening() {
        AudioPermissionHelper.checkAndRequestAudioPermission(
            context = context,
            onPermissionGranted = {
                mainViewModel.startListening { spoken ->
                    mainViewModel.handleRecognizedCommand(spoken)
                }
            },
            onShowRationale = {
                showPermissionRationaleDialog = true
            }
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startListening()
        }
    }

    androidx.compose.animation.AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(500))
    ) {
        Row(
            modifier = modifier.testTag("ze_traquina_mascot"),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Mascot Container with Coil AsyncImage and Particle Overlay
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = interactiveScale
                        scaleY = interactiveScale
                        rotationZ = currentRotation
                        translationY = interactiveOffsetY
                        translationX = interactiveOffsetX
                    }
                    .clip(CircleShape)
                    .background(backgroundBrush)
                    .border(
                        width = if (activeEmotion == MascotEmotion.CELEBRATING) 4.dp else 3.dp,
                        brush = borderBrush,
                        shape = CircleShape
                    )
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = {
                                isDragging = true
                                activeEmotion = MascotEmotion.EXCITED
                                showParticles = true
                            },
                            onDragEnd = {
                                isDragging = false
                                activeEmotion = emotion
                                showParticles = false
                                coroutineScope.launch {
                                    launch { offsetX.animateTo(0f, spring(dampingRatio = 0.5f, stiffness = Spring.StiffnessLow)) }
                                    launch { offsetY.animateTo(0f, spring(dampingRatio = 0.5f, stiffness = Spring.StiffnessLow)) }
                                }
                            },
                            onDragCancel = {
                                isDragging = false
                                activeEmotion = emotion
                                showParticles = false
                                coroutineScope.launch {
                                    launch { offsetX.animateTo(0f, spring(dampingRatio = 0.5f, stiffness = Spring.StiffnessLow)) }
                                    launch { offsetY.animateTo(0f, spring(dampingRatio = 0.5f, stiffness = Spring.StiffnessLow)) }
                                }
                            }
                        ) { change, dragAmount ->
                            change.consume()
                            coroutineScope.launch {
                                offsetX.snapTo(offsetX.value + dragAmount.x)
                                offsetY.snapTo(offsetY.value + dragAmount.y)
                            }
                        }
                    }
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        interactionCount++
                        isInteracting = true
                        showParticles = true
                        phraseIndex++

                        if (activeEmotion == MascotEmotion.HAPPY) {
                            activeEmotion = MascotEmotion.EXCITED
                        }

                        val nextPhrase = customPhrase ?: defaultPhrases[phraseIndex % defaultPhrases.size]
                        speakMascot(nextPhrase)

                        onInteract?.invoke()

                        coroutineScope.launch {
                            delay(300)
                            isInteracting = false
                            delay(2000)
                            showParticles = false
                            if (activeEmotion == MascotEmotion.EXCITED && emotion == MascotEmotion.HAPPY) {
                                activeEmotion = MascotEmotion.HAPPY
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                // Local image asset displayed via cached Coil AsyncImage
                AsyncImage(
                    model = mascotImageRequest,
                    contentDescription = "Mascote Zé Traquina",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(size)
                        .clip(CircleShape)
                )

                // Emotion Overlay Badge Icon
                badgeIcon?.let { badge ->
                    Surface(
                        shape = CircleShape,
                        color = SunshineYellow,
                        shadowElevation = 4.dp,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 2.dp, y = (-2).dp)
                    ) {
                        Text(
                            text = badge,
                            fontSize = (size.value * 0.28f).sp,
                            modifier = Modifier.padding(2.dp)
                        )
                    }
                }

                // Floating Particles Animation
                androidx.compose.animation.AnimatedVisibility(
                    visible = isParticlesVisible,
                    enter = fadeIn() + scaleIn(animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)),
                    exit = fadeOut() + scaleOut()
                ) {
                    Box(modifier = Modifier.size(size)) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = KidStarOrange,
                            modifier = Modifier
                                .size(22.dp)
                                .align(Alignment.TopStart)
                                .offset(x = (-6).dp, y = (-6).dp)
                        )
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = SunshineYellow,
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.TopEnd)
                                .offset(x = 6.dp, y = 6.dp)
                        )
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = Color(0xFFFF4081),
                            modifier = Modifier
                                .size(18.dp)
                                .align(Alignment.BottomEnd)
                                .offset(x = 6.dp, y = 6.dp)
                        )
                        if (activeEmotion == MascotEmotion.CELEBRATING) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = null,
                                tint = SunshineYellow,
                                modifier = Modifier
                                    .size(20.dp)
                                    .align(Alignment.BottomStart)
                                    .offset(x = (-6).dp, y = 6.dp)
                            )
                        }
                    }
                }
            }

            if (showSpeechBubble) {
                Spacer(modifier = Modifier.width(10.dp))

                // Speech Bubble with Voice Mic Button
                androidx.compose.animation.AnimatedVisibility(
                    visible = true,
                    enter = scaleIn(animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                ) {
                    Surface(
                        shape = RoundedCornerShape(topStart = 4.dp, topEnd = 16.dp, bottomEnd = 16.dp, bottomStart = 16.dp),
                        color = when (activeEmotion) {
                            MascotEmotion.CELEBRATING -> SunshineYellow
                            MascotEmotion.EXCITED -> MintGreen
                            MascotEmotion.PROUD -> Color(0xFFFFF3E0)
                            else -> MaterialTheme.colorScheme.surfaceVariant
                        },
                        tonalElevation = 6.dp,
                        shadowElevation = 4.dp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth(0.95f)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "Zé Traquina",
                                        fontWeight = FontWeight.Black,
                                        fontSize = 13.sp,
                                        color = if (activeEmotion == MascotEmotion.CELEBRATING) Color.Black else MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = when (activeEmotion) {
                                            MascotEmotion.CELEBRATING -> "🎉🥳"
                                            MascotEmotion.EXCITED -> "🚀⭐"
                                            MascotEmotion.PROUD -> "👑🏆"
                                            MascotEmotion.THINKING -> "💭🤔"
                                            MascotEmotion.TALKING -> "🗣️💬"
                                            MascotEmotion.IDLE -> "😴💤"
                                            MascotEmotion.HAPPY -> "🧢👦"
                                        },
                                        fontSize = 12.sp
                                    )
                                }

                                if (enableVoiceCommand) {
                                    IconButton(
                                        onClick = {
                                            voiceDialogOpen = true
                                            speakMascot("Olá! Vamos conversar por voz! O que queres dizer?")
                                        },
                                        modifier = Modifier.size(26.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Mic,
                                            contentDescription = "Falar por voz com o Zé Traquina",
                                            tint = Color(0xFF0284C7),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(top = 2.dp)
                            ) {
                                Text(
                                    text = currentSpeech,
                                    fontSize = 12.sp,
                                    fontWeight = if (activeEmotion == MascotEmotion.CELEBRATING) FontWeight.ExtraBold else FontWeight.Medium,
                                    color = if (activeEmotion == MascotEmotion.CELEBRATING) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                IconButton(
                                    onClick = { speakMascot(currentSpeech) },
                                    modifier = Modifier.size(22.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                        contentDescription = "Ouvir Zé Traquina",
                                        tint = SkyBluePrimary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // --- INTERACTIVE VOICE COMMAND DIALOG ---
    if (voiceDialogOpen) {
        Dialog(onDismissRequest = { voiceDialogOpen = false }) {
            Surface(
                shape = RoundedCornerShape(28.dp),
                color = Color(0xFF0F172A), // Dark starry slate background
                border = BorderStroke(2.dp, SunshineYellow),
                shadowElevation = 16.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Header Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "🗣️", fontSize = 24.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Conversa com o Zé!",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = SunshineYellow
                            )
                        }
                        IconButton(
                            onClick = { voiceDialogOpen = false },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.15f))
                                .size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Fechar Conversa",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Animated Zé Mascot Preview
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    listOf(SunshineYellow, SkyBluePrimary)
                                )
                            )
                            .border(3.dp, SunshineYellow, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = mascotImageRequest,
                            contentDescription = "Zé Traquina Mascot",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(96.dp)
                                .clip(CircleShape)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Dynamic Voice Status text
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFF1E293B),
                        border = BorderStroke(1.dp, SkyBluePrimary.copy(alpha = 0.6f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (recognizedSpeechText.isNotEmpty()) recognizedSpeechText else "Clica no microfone ou escolhe um comando abaixo!",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(14.dp)
                        )
                    }

                    // Pulse Listening Indicator when active
                    ZeListeningPulseIndicator(
                        isListening = isListeningVoice,
                        spokenTextPreview = recognizedSpeechText,
                        onStopListening = {
                            mainViewModel.cancelListening()
                        },
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Big Microphone Action Button
                    Button(
                        onClick = {
                            if (isListeningVoice) {
                                mainViewModel.cancelListening()
                            } else {
                                AudioPermissionHelper.checkAndRequestAudioPermission(
                                    context = context,
                                    onPermissionGranted = {
                                        startListening()
                                    },
                                    onShowRationale = {
                                        showPermissionRationaleDialog = true
                                    }
                                )
                            }
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isListeningVoice) Color(0xFFEF4444) else SkyBluePrimary
                        ),
                        modifier = Modifier.size(64.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                    ) {
                        Icon(
                            imageVector = if (isListeningVoice) Icons.Default.MicOff else Icons.Default.Mic,
                            contentDescription = "Microfone Zé Traquina",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Text(
                        text = if (isListeningVoice) "A ouvir a tua voz... (Toca para parar)" else "Toca para Falar 🎙️",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.padding(top = 6.dp)
                    )

                    Spacer(modifier = Modifier.height(18.dp))
                }
            }
        }
    }

    if (showPermissionRationaleDialog) {
        ZeAudioPermissionDialog(
            onDismissRequest = {
                showPermissionRationaleDialog = false
            },
            onConfirmPermission = {
                showPermissionRationaleDialog = false
                permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }
        )
    }
}

/**
 * A simplified, circular Mascot face avatar specifically for compact spaces like the tips balloon.
 */
@Composable
fun MascotFaceCircle(
    modifier: Modifier = Modifier,
    size: Dp = 80.dp,
    imageUrl: String = "https://i.imgur.com/O2uwUho.png",
    isPreview: Boolean = false
) {
    val context = LocalContext.current
    val request = remember(context, isPreview, imageUrl) {
        AppImageLoader.buildMascotRequest(
            context = context,
            data = imageUrl,
            placeholderRes = com.example.R.drawable.img_ze_traquina_original,
            isPreviewMode = isPreview
        )
    }

    Surface(
        modifier = modifier
            .size(size)
            .shadow(elevation = 4.dp, shape = CircleShape),
        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(2.dp, SkyBluePrimary.copy(alpha = 0.5f))
    ) {
        SafeAsyncImage(
            model = request,
            contentDescription = "Zé Traquina Face",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
