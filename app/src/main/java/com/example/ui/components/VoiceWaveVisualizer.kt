package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.sin

/**
 * VoiceWaveVisualizer renders real-time audio amplitude bars to provide visual feedback
 * when the microphone is capturing audio, improving the interactive experience for children.
 *
 * @param isListening True if the microphone is actively capturing voice, triggering high energy animations.
 * @param modifier Custom modifiers for the visualizer.
 * @param barCount The number of animated bars to render.
 * @param barWidth The width of each individual bar.
 * @param barSpacing The space between adjacent bars.
 * @param maxBarHeight The maximum height a bar can reach.
 */
@Composable
fun VoiceWaveVisualizer(
    isListening: Boolean,
    modifier: Modifier = Modifier,
    barCount: Int = 12,
    barWidth: Dp = 6.dp,
    barSpacing: Dp = 4.dp,
    maxBarHeight: Dp = 70.dp
) {
    // Infinite transition to drive the wavy rhythmic motion
    val infiniteTransition = rememberInfiniteTransition(label = "VoiceWaveVisualizer_Transition")

    // Animations for individual bars to create a wave-like flowing motion
    val animFractions = List(barCount) { index ->
        val phaseDelay = index * 150 // staggered start for wave pattern
        val durationMillis = if (isListening) 400 + (index % 3) * 100 else 1200 + (index % 2) * 200
        
        infiniteTransition.animateFloat(
            initialValue = 0.1f,
            targetValue = 1.0f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = durationMillis,
                    delayMillis = phaseDelay,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "bar_$index"
        )
    }

    // Colors aligned with the playful "Universo Zé Traquina" palette
    val gradientColors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary
    )

    // Calculate total required width to center the bars nicely
    val totalWidth = (barWidth * barCount) + (barSpacing * (barCount - 1))

    Box(
        modifier = modifier
            .testTag("voice_wave_visualizer")
            .size(width = totalWidth, height = maxBarHeight),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val widthPx = barWidth.toPx()
            val spacingPx = barSpacing.toPx()
            val maxHeightPx = maxBarHeight.toPx()
            val centerY = size.height / 2f

            for (i in 0 until barCount) {
                // Dynamically calculate height fraction
                val animationValue = animFractions[i].value
                
                // When idle, waves are small, calm, and soothing. When listening, they bounce dynamically!
                val heightMultiplier = if (isListening) {
                    0.2f + 0.8f * animationValue
                } else {
                    // Micro-rhythmic movement even when idle to show it is alive
                    0.1f + 0.15f * sin((animationValue * Math.PI).toFloat())
                }

                val barHeight = maxHeightPx * heightMultiplier
                val left = i * (widthPx + spacingPx)
                val top = centerY - (barHeight / 2f)

                // Create a beautiful vibrant vertical gradient for each bar
                val brush = Brush.verticalGradient(
                    colors = gradientColors,
                    startY = top,
                    endY = top + barHeight
                )

                // Draw each bar as a beautiful smooth rounded capsule
                drawRoundRect(
                    brush = brush,
                    topLeft = Offset(x = left, y = top),
                    size = Size(width = widthPx, height = barHeight),
                    cornerRadius = CornerRadius(x = widthPx / 2f, y = widthPx / 2f)
                )
            }
        }
    }
}
