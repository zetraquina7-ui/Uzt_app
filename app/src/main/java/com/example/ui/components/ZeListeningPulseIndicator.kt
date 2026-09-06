package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Visual listening indicator with multi-layered pulse wave animations,
 * reactive sound-level bars, and real-time feedback that ZéAI is listening to the child.
 */
@Composable
fun ZeListeningPulseIndicator(
    isListening: Boolean,
    modifier: Modifier = Modifier,
    volumeLevel: Float = 0f,
    spokenTextPreview: String = "",
    onStopListening: (() -> Unit)? = null
) {
    AnimatedVisibility(
        visible = isListening,
        enter = fadeIn(animationSpec = spring(stiffness = Spring.StiffnessMediumLow)) +
                scaleIn(initialScale = 0.8f, animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)),
        exit = fadeOut(animationSpec = tween(200)) + scaleOut(targetScale = 0.8f, animationSpec = tween(200)),
        modifier = modifier
    ) {
        val infiniteTransition = rememberInfiniteTransition(label = "listening_pulse_transition")

        // Concentric pulse waves 1, 2, 3
        val pulseScale1 by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.65f,
            animationSpec = infiniteRepeatable(
                animation = tween(1400, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "pulse_scale_1"
        )
        val pulseAlpha1 by infiniteTransition.animateFloat(
            initialValue = 0.7f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = tween(1400, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "pulse_alpha_1"
        )

        val pulseScale2 by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.45f,
            animationSpec = infiniteRepeatable(
                animation = tween(1400, delayMillis = 400, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "pulse_scale_2"
        )
        val pulseAlpha2 by infiniteTransition.animateFloat(
            initialValue = 0.6f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = tween(1400, delayMillis = 400, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "pulse_alpha_2"
        )

        // Subtle core glow pulsing
        val coreGlowScale by infiniteTransition.animateFloat(
            initialValue = 0.95f,
            targetValue = 1.08f,
            animationSpec = infiniteRepeatable(
                animation = tween(800, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "core_glow_scale"
        )

        // Equalizer sound bars dynamic simulation
        val bar1Height by infiniteTransition.animateFloat(
            initialValue = 8f,
            targetValue = 26f,
            animationSpec = infiniteRepeatable(tween(350, easing = LinearEasing), RepeatMode.Reverse),
            label = "bar1"
        )
        val bar2Height by infiniteTransition.animateFloat(
            initialValue = 14f,
            targetValue = 32f,
            animationSpec = infiniteRepeatable(tween(450, delayMillis = 100, easing = LinearEasing), RepeatMode.Reverse),
            label = "bar2"
        )
        val bar3Height by infiniteTransition.animateFloat(
            initialValue = 20f,
            targetValue = 38f,
            animationSpec = infiniteRepeatable(tween(300, delayMillis = 50, easing = LinearEasing), RepeatMode.Reverse),
            label = "bar3"
        )
        val bar4Height by infiniteTransition.animateFloat(
            initialValue = 12f,
            targetValue = 28f,
            animationSpec = infiniteRepeatable(tween(400, delayMillis = 150, easing = LinearEasing), RepeatMode.Reverse),
            label = "bar4"
        )
        val bar5Height by infiniteTransition.animateFloat(
            initialValue = 6f,
            targetValue = 22f,
            animationSpec = infiniteRepeatable(tween(500, delayMillis = 80, easing = LinearEasing), RepeatMode.Reverse),
            label = "bar5"
        )

        val responsiveScale = 1f + (volumeLevel * 0.35f)

        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color(0xFF0F172A).copy(alpha = 0.90f), // bg-slate-900/90
            border = androidx.compose.foundation.BorderStroke(
                1.5.dp,
                Brush.horizontalGradient(
                    listOf(Color(0xFF38BDF8), Color(0xFF818CF8), Color(0xFFF472B6))
                )
            ),
            shadowElevation = 10.dp,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .fillMaxWidth()
                .testTag("ze_listening_pulse_indicator")
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left side: Compact Mic with Pulse + Text
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    // Compact Pulse Ring & Mic
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(48.dp)
                    ) {
                        // Pulse Ring 1
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .scale(pulseScale1)
                                .background(
                                    color = Color(0xFF38BDF8).copy(alpha = pulseAlpha1),
                                    shape = CircleShape
                                )
                        )
                        // Central Glowing Bubble with Icon
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(36.dp)
                                .scale(coreGlowScale * responsiveScale)
                                .shadow(4.dp, CircleShape, spotColor = Color(0xFF38BDF8))
                                .background(
                                    brush = Brush.radialGradient(
                                        listOf(
                                            Color(0xFF0284C7),
                                            Color(0xFF4F46E5),
                                            Color(0xFF7C3AED)
                                        )
                                    ),
                                    shape = CircleShape
                                )
                                .border(1.dp, Color(0xFFBAE6FD), CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Mic,
                                contentDescription = "A Ouvir voz",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Text Info
                    Column {
                        Text(
                            text = "O Zé está a ouvir...",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFFFDE047)
                        )
                        Text(
                            text = if (spokenTextPreview.isNotBlank()) {
                                "\"$spokenTextPreview\""
                            } else {
                                "Fala agora amiguinho! 🎙️"
                            },
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (spokenTextPreview.isNotBlank()) Color.White else Color(0xFFE2E8F0),
                            maxLines = 1,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Real-time voice wave feedback
                    VoiceWaveVisualizer(
                        isListening = isListening,
                        barCount = 8,
                        barWidth = 3.dp,
                        barSpacing = 2.5.dp,
                        maxBarHeight = 26.dp,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }

                // Right side: Stop Button
                if (onStopListening != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        onClick = onStopListening,
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFEF4444).copy(alpha = 0.85f),
                        modifier = Modifier.defaultMinSize(minHeight = 36.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Stop,
                                contentDescription = "Terminar fala",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Terminei",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
