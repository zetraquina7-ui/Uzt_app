package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomAmiguinhoMascot(
    size: Dp = 60.dp,
    shapeName: String = "CIRCLE",
    color: Color = Color.Cyan,
    faceEmoji: String = "😊",
    isAnimating: Boolean = true
) {
    val isPreview = androidx.compose.ui.platform.LocalInspectionMode.current || com.example.util.PreviewConfig.isInPreview()

    val bounce: Float
    val rotation: Float

    if (isPreview) {
        bounce = 0f
        rotation = 0f
    } else {
        val infiniteTransition = rememberInfiniteTransition(label = "amiguinho")

        val animBounce by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = if (isAnimating) -8f else 0f,
            animationSpec = infiniteRepeatable(
                animation = tween(800, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "bounce"
        )

        val animRotation by infiniteTransition.animateFloat(
            initialValue = -3f,
            targetValue = 3f,
            animationSpec = infiniteRepeatable(
                animation = tween(1200, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "rotation"
        )

        bounce = animBounce
        rotation = animRotation
    }

    Box(
        modifier = Modifier
            .offset(y = bounce.dp)
            .rotate(rotation)
            .size(size)
            .clip(
                when(shapeName) {
                    "CIRCLE" -> CircleShape
                    "SQUARE" -> RoundedCornerShape(size * 0.2f)
                    else -> RoundedCornerShape(size * 0.15f)
                }
            )
            .background(color)
            .border(
                (size.value * 0.05f).dp, 
                Color.White.copy(alpha = 0.4f),
                when(shapeName) {
                    "CIRCLE" -> CircleShape
                    "SQUARE" -> RoundedCornerShape(size * 0.2f)
                    else -> RoundedCornerShape(size * 0.15f)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = faceEmoji,
            fontSize = (size.value * 0.45f).sp
        )
        
        if (shapeName == "STAR") {
            Text("⭐", fontSize = (size.value * 1.1f).sp, color = color.copy(alpha = 0.2f))
        }
    }
}