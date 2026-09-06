package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedRainbowBar(
    modifier: Modifier = Modifier,
    height: Dp = 14.dp
) {
    val isPreview = androidx.compose.ui.platform.LocalInspectionMode.current || com.example.util.PreviewConfig.isInPreview()
    val offsetAnimation = if (isPreview) 0f else {
        val infiniteTransition = rememberInfiniteTransition(label = "RainbowBarTransition")
        val anim by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 3500, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "rainbowOffset"
        )
        anim
    }

    val rainbowColors = listOf(
        Color(0xFFFF3B30), // Vermelho
        Color(0xFFFF9500), // Laranja
        Color(0xFFFFCC00), // Amarelo
        Color(0xFF34C759), // Verde
        Color(0xFF00C7BE), // Verde-água/Ciano
        Color(0xFF007AFF), // Azul
        Color(0xFF5856D6), // Roxo
        Color(0xFFFF2D55), // Rosa
        Color(0xFFFF3B30)  // Vermelho (repetição para fechar o ciclo de forma contínua)
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .background(
                brush = Brush.linearGradient(
                    colors = rainbowColors,
                    start = Offset(offsetAnimation, 0f),
                    end = Offset(offsetAnimation + 1000f, 0f),
                    tileMode = TileMode.Repeated
                )
            )
    )
}

