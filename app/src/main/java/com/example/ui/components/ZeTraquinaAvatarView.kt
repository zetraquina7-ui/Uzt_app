package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.R

@Composable
fun ZeTraquinaAvatarView(
    skinTone: String,
    hairStyle: String,
    hairColor: Long,
    clothingStyle: String,
    clothingColor: Long,
    accessory: String,
    expression: String,
    background: String,
    modifier: Modifier = Modifier,
    size: Dp = 180.dp,
    animated: Boolean = true,
    useRealPhoto3dModel: Boolean = false
) {
    if (useRealPhoto3dModel) {
        Box(
            modifier = modifier
                .size(size)
                .clip(RoundedCornerShape(32.dp))
                .border(3.dp, Color(0xFFF59E0B), RoundedCornerShape(32.dp))
                .shadow(8.dp, RoundedCornerShape(32.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = R.drawable.ze_ai_photo_user),
                contentDescription = "Avatar Zé Traquina",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        return
    }

    val bounceOffset = 0f
    val blinkAlpha = 1f

    // Background Gradient based on selected background theme
    val bgColors = when (background) {
        "street_calcada" -> listOf(Color(0xFFFEF3C7), Color(0xFFCBD5E1), Color(0xFF64748B)) // Cobblestone warmth
        "room_ze" -> listOf(Color(0xFFFEF3C7), Color(0xFFFDE68A), Color(0xFFF59E0B))
        "park" -> listOf(Color(0xFFDCFCE7), Color(0xFF86EFAC), Color(0xFF22C55E))
        "space" -> listOf(Color(0xFF1E1B4B), Color(0xFF312E81), Color(0xFF4C1D95))
        "pirate_island" -> listOf(Color(0xFFE0F2FE), Color(0xFFBAE6FD), Color(0xFF38BDF8))
        "music_stage" -> listOf(Color(0xFFFCE7F3), Color(0xFFF472B6), Color(0xFFDB2777))
        else -> listOf(Color(0xFFEFF6FF), Color(0xFFBFDBFE), Color(0xFF60A5FA))
    }

    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(32.dp))
            .background(Brush.radialGradient(bgColors))
            .border(3.dp, Color.White.copy(alpha = 0.8f), RoundedCornerShape(32.dp))
            .shadow(6.dp, RoundedCornerShape(32.dp)),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .offset(y = bounceOffset.dp)
        ) {
            val w = this.size.width
            val h = this.size.height

            // 1. Draw Skin Colors
            val skinPaintColor = getSkinColor(skinTone)
            val hairPaintColor = Color(hairColor)
            val clothesPaintColor = Color(clothingColor)

            // 2. Draw Shoulders & Clothing Body
            drawClothing(
                clothingStyle = clothingStyle,
                color = clothesPaintColor,
                w = w,
                h = h
            )

            // 3. Draw Neck & Head
            val headCenterX = w / 2f
            val headCenterY = h * 0.44f
            val headRadius = w * 0.28f

            // Neck (with subtle shadow)
            drawRect(
                color = skinPaintColor.copy(alpha = 0.8f),
                topLeft = Offset(headCenterX - headRadius * 0.35f, headCenterY + headRadius * 0.5f),
                size = Size(headRadius * 0.7f, headRadius * 0.6f)
            )

            // Ears
            drawCircle(skinPaintColor, headRadius * 0.2f, Offset(headCenterX - headRadius * 0.95f, headCenterY + 2f))
            drawCircle(skinPaintColor, headRadius * 0.2f, Offset(headCenterX + headRadius * 0.95f, headCenterY + 2f))

            // Head Base (Flat Cartoon Style)
            drawCircle(skinPaintColor, headRadius, Offset(headCenterX, headCenterY))
            
            // Subtle Shadow on Head
            drawCircle(
                color = Color.Black.copy(alpha = 0.05f),
                radius = headRadius,
                center = Offset(headCenterX + 4f, headCenterY + 4f)
            )

            // Cheeks (Blush) - more prominent
            val blushColor = Color(0xFFFFB7C5).copy(alpha = 0.5f)
            drawCircle(blushColor, headRadius * 0.2f, Offset(headCenterX - headRadius * 0.55f, headCenterY + headRadius * 0.25f))
            drawCircle(blushColor, headRadius * 0.2f, Offset(headCenterX + headRadius * 0.55f, headCenterY + headRadius * 0.25f))

            // Freckles (Sardinhas no Rosto)
            val freckleColor = Color(0xFF8B4513).copy(alpha = 0.4f)
            drawCircle(freckleColor, 3f, Offset(headCenterX - headRadius * 0.35f, headCenterY + headRadius * 0.12f))
            drawCircle(freckleColor, 3f, Offset(headCenterX + headRadius * 0.35f, headCenterY + headRadius * 0.12f))
            drawCircle(freckleColor, 2f, Offset(headCenterX, headCenterY + headRadius * 0.2f))

            // 4. Draw Hair (Back & Front layers)
            drawHair(
                style = hairStyle,
                color = hairPaintColor,
                centerX = headCenterX,
                centerY = headCenterY,
                radius = headRadius
            )

            // 5. Draw Facial Expression
            drawFaceExpression(
                expression = expression,
                centerX = headCenterX,
                centerY = headCenterY,
                radius = headRadius,
                blinkFactor = blinkAlpha
            )

            // 6. Draw Accessories (Hat, Vintage Cap, Glasses, Headphones, Ribbon)
            drawAccessory(
                accessory = accessory,
                centerX = headCenterX,
                centerY = headCenterY,
                radius = headRadius
            )
        }
    }
}

private fun getSkinColor(skinTone: String): Color {
    return when (skinTone) {
        "skin_light" -> Color(0xFFFFE0D2)
        "skin_fair" -> Color(0xFFFCD0B1)
        "skin_warm" -> Color(0xFFE8AB75)
        "skin_tan" -> Color(0xFFC68642)
        "skin_deep" -> Color(0xFF8D5524)
        "skin_alien" -> Color(0xFFA7F3D0)
        else -> Color(0xFFFCD0B1)
    }
}

private fun DrawScope.drawClothing(
    clothingStyle: String,
    color: Color,
    w: Float,
    h: Float
) {
    val shoulderTopY = h * 0.65f
    val bodyBottomY = h * 1.05f

    val bodyPath = Path().apply {
        moveTo(w * 0.15f, bodyBottomY)
        lineTo(w * 0.22f, shoulderTopY)
        quadraticTo(w * 0.5f, shoulderTopY - 12f, w * 0.78f, shoulderTopY)
        lineTo(w * 0.85f, bodyBottomY)
        close()
    }

    // Main clothing fill
    drawPath(bodyPath, color)

    // Style Specific details
    when (clothingStyle) {
        "polo_green_vintage" -> {
            // Classic Vintage Polo with folded collar and white buttons (like the photo!)
            val collarColor = color.copy(alpha = 0.85f)
            val leftCollar = Path().apply {
                moveTo(w * 0.5f, shoulderTopY - 4f)
                lineTo(w * 0.36f, shoulderTopY + 18f)
                lineTo(w * 0.44f, shoulderTopY + 22f)
                close()
            }
            val rightCollar = Path().apply {
                moveTo(w * 0.5f, shoulderTopY - 4f)
                lineTo(w * 0.64f, shoulderTopY + 18f)
                lineTo(w * 0.56f, shoulderTopY + 22f)
                close()
            }
            drawPath(leftCollar, Color(0xFF1B5E20))
            drawPath(rightCollar, Color(0xFF1B5E20))

            // Button placket in center
            drawRect(
                color = Color(0xFF1B5E20),
                topLeft = Offset(w * 0.47f, shoulderTopY + 4f),
                size = Size(w * 0.06f, h * 0.15f)
            )
            // Two White buttons
            drawCircle(Color.White, 3f, Offset(w * 0.5f, shoulderTopY + 18f))
            drawCircle(Color.White, 3f, Offset(w * 0.5f, shoulderTopY + 34f))
        }
        "tshirt_star" -> {
            // Big yellow star on chest
            drawStar(
                centerX = w * 0.5f,
                centerY = h * 0.82f,
                radius = w * 0.08f,
                color = Color(0xFFFDE047)
            )
            // Collar
            drawCircle(
                color = Color.White.copy(alpha = 0.9f),
                radius = w * 0.12f,
                center = Offset(w * 0.5f, shoulderTopY - 4f),
                style = Stroke(width = 6f)
            )
        }
        "hoodie_ze" -> {
            // Hoodie strings and pocket
            val hoodColor = Color.White.copy(alpha = 0.3f)
            drawRoundRect(
                color = hoodColor,
                topLeft = Offset(w * 0.35f, h * 0.82f),
                size = Size(w * 0.30f, h * 0.14f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(14f, 14f)
            )
            // Hoodie drawstrings
            drawLine(
                color = Color.White,
                start = Offset(w * 0.44f, shoulderTopY + 8f),
                end = Offset(w * 0.44f, shoulderTopY + 34f),
                strokeWidth = 4f,
                cap = StrokeCap.Round
            )
            drawLine(
                color = Color.White,
                start = Offset(w * 0.56f, shoulderTopY + 8f),
                end = Offset(w * 0.56f, shoulderTopY + 34f),
                strokeWidth = 4f,
                cap = StrokeCap.Round
            )
        }
        "overalls_traquina" -> {
            // White shirt underneath
            drawPath(bodyPath, Color.White)
            // Jean dungarees
            val overallsColor = Color(0xFF1E40AF)
            val dungareesPath = Path().apply {
                moveTo(w * 0.22f, bodyBottomY)
                lineTo(w * 0.28f, h * 0.74f)
                lineTo(w * 0.72f, h * 0.74f)
                lineTo(w * 0.78f, bodyBottomY)
                close()
            }
            drawPath(dungareesPath, overallsColor)
            // Straps
            drawLine(overallsColor, Offset(w * 0.32f, shoulderTopY), Offset(w * 0.35f, h * 0.75f), strokeWidth = 14f)
            drawLine(overallsColor, Offset(w * 0.68f, shoulderTopY), Offset(w * 0.65f, h * 0.75f), strokeWidth = 14f)
            // Yellow buttons
            drawCircle(Color(0xFFFBBF24), radius = 6f, center = Offset(w * 0.35f, h * 0.75f))
            drawCircle(Color(0xFFFBBF24), radius = 6f, center = Offset(w * 0.65f, h * 0.75f))
        }
        "explorer_jacket" -> {
            // Explorer stripes & zipper
            drawLine(Color(0xFFF59E0B), Offset(w * 0.5f, shoulderTopY), Offset(w * 0.5f, bodyBottomY), strokeWidth = 8f)
            drawCircle(Color(0xFFD97706), radius = 8f, center = Offset(w * 0.35f, h * 0.82f))
            drawCircle(Color(0xFFD97706), radius = 8f, center = Offset(w * 0.65f, h * 0.82f))
        }
        "dress_sparkle" -> {
            // Sparkles
            drawStar(w * 0.36f, h * 0.80f, 7f, Color.White)
            drawStar(w * 0.64f, h * 0.82f, 9f, Color.White)
            drawStar(w * 0.50f, h * 0.88f, 8f, Color.White)
        }
    }
}

private fun DrawScope.drawHair(
    style: String,
    color: Color,
    centerX: Float,
    centerY: Float,
    radius: Float
) {
    when (style) {
        "hair_short" -> {
            // Cute boyish side sweep bangs
            val hairPath = Path().apply {
                moveTo(centerX - radius * 1.05f, centerY)
                quadraticTo(centerX - radius * 0.9f, centerY - radius * 1.25f, centerX, centerY - radius * 1.15f)
                quadraticTo(centerX + radius * 0.9f, centerY - radius * 1.25f, centerX + radius * 1.05f, centerY)
                quadraticTo(centerX + radius * 0.6f, centerY - radius * 0.4f, centerX + radius * 0.2f, centerY - radius * 0.55f)
                quadraticTo(centerX - radius * 0.3f, centerY - radius * 0.65f, centerX - radius * 1.05f, centerY)
                close()
            }
            drawPath(hairPath, color)
        }
        "hair_spiky" -> {
            // Cool spikes
            val spikePath = Path().apply {
                moveTo(centerX - radius * 1.0f, centerY - radius * 0.2f)
                lineTo(centerX - radius * 0.8f, centerY - radius * 1.35f)
                lineTo(centerX - radius * 0.4f, centerY - radius * 1.1f)
                lineTo(centerX, centerY - radius * 1.45f)
                lineTo(centerX + radius * 0.4f, centerY - radius * 1.1f)
                lineTo(centerX + radius * 0.8f, centerY - radius * 1.35f)
                lineTo(centerX + radius * 1.0f, centerY - radius * 0.2f)
                quadraticTo(centerX, centerY - radius * 0.4f, centerX - radius * 1.0f, centerY - radius * 0.2f)
                close()
            }
            drawPath(spikePath, color)
        }
        "hair_curly" -> {
            // Big fluffy cute afro / curly puff circles
            val puffRadius = radius * 0.42f
            drawCircle(color, puffRadius, Offset(centerX - radius * 0.85f, centerY - radius * 0.35f))
            drawCircle(color, puffRadius, Offset(centerX + radius * 0.85f, centerY - radius * 0.35f))
            drawCircle(color, puffRadius * 1.1f, Offset(centerX - radius * 0.55f, centerY - radius * 0.95f))
            drawCircle(color, puffRadius * 1.1f, Offset(centerX + radius * 0.55f, centerY - radius * 0.95f))
            drawCircle(color, puffRadius * 1.15f, Offset(centerX, centerY - radius * 1.1f))
        }
        "hair_ponytail" -> {
            // Hair base
            val hairPath = Path().apply {
                moveTo(centerX - radius * 1.02f, centerY)
                quadraticTo(centerX, centerY - radius * 1.25f, centerX + radius * 1.02f, centerY)
                quadraticTo(centerX, centerY - radius * 0.55f, centerX - radius * 1.02f, centerY)
                close()
            }
            drawPath(hairPath, color)
            // High Ponytail / Top knot
            drawCircle(color, radius * 0.45f, Offset(centerX + radius * 0.85f, centerY - radius * 0.95f))
            drawCircle(Color(0xFFEC4899), radius * 0.16f, Offset(centerX + radius * 0.65f, centerY - radius * 0.78f))
        }
        "hair_wavy" -> {
            // Long wavy hair flowing down both sides
            val leftLock = Path().apply {
                moveTo(centerX - radius * 0.95f, centerY - radius * 0.5f)
                quadraticTo(centerX - radius * 1.25f, centerY + radius * 0.5f, centerX - radius * 0.95f, centerY + radius * 1.1f)
                lineTo(centerX - radius * 0.7f, centerY + radius * 1.1f)
                quadraticTo(centerX - radius * 0.9f, centerY + radius * 0.4f, centerX - radius * 0.65f, centerY)
                close()
            }
            val rightLock = Path().apply {
                moveTo(centerX + radius * 0.95f, centerY - radius * 0.5f)
                quadraticTo(centerX + radius * 1.25f, centerY + radius * 0.5f, centerX + radius * 0.95f, centerY + radius * 1.1f)
                lineTo(centerX + radius * 0.7f, centerY + radius * 1.1f)
                quadraticTo(centerX + radius * 0.9f, centerY + radius * 0.4f, centerX + radius * 0.65f, centerY)
                close()
            }
            drawPath(leftLock, color)
            drawPath(rightLock, color)
            // Top bangs
            drawCircle(color, radius * 0.92f, Offset(centerX, centerY - radius * 0.45f))
        }
        "hair_braids" -> {
            // Braids with colorful beads
            val hairTop = Path().apply {
                moveTo(centerX - radius * 1.02f, centerY)
                quadraticTo(centerX, centerY - radius * 1.25f, centerX + radius * 1.02f, centerY)
                quadraticTo(centerX, centerY - radius * 0.55f, centerX - radius * 1.02f, centerY)
                close()
            }
            drawPath(hairTop, color)
            // Braid beads
            drawCircle(Color(0xFFF59E0B), 7f, Offset(centerX - radius * 0.85f, centerY + radius * 0.6f))
            drawCircle(Color(0xFF3B82F6), 7f, Offset(centerX - radius * 0.85f, centerY + radius * 0.85f))
            drawCircle(Color(0xFF10B981), 7f, Offset(centerX + radius * 0.85f, centerY + radius * 0.6f))
            drawCircle(Color(0xFFEC4899), 7f, Offset(centerX + radius * 0.85f, centerY + radius * 0.85f))
        }
    }
}

private fun DrawScope.drawFaceExpression(
    expression: String,
    centerX: Float,
    centerY: Float,
    radius: Float,
    blinkFactor: Float
) {
    val eyeOffsetX = radius * 0.38f
    val eyeCenterY = centerY - radius * 0.05f
    val eyeRadius = radius * 0.16f

    when (expression) {
        "smile", "smile_freckles" -> {
            // Friendly round eyes with shine
            if (blinkFactor > 0.3f) {
                // Left Eye (Warm Brown)
                drawCircle(Color(0xFF3E2723), eyeRadius, Offset(centerX - eyeOffsetX, eyeCenterY))
                drawCircle(Color.White, eyeRadius * 0.4f, Offset(centerX - eyeOffsetX - 2f, eyeCenterY - 3f))
                // Right Eye (Warm Brown)
                drawCircle(Color(0xFF3E2723), eyeRadius, Offset(centerX + eyeOffsetX, eyeCenterY))
                drawCircle(Color.White, eyeRadius * 0.4f, Offset(centerX + eyeOffsetX - 2f, eyeCenterY - 3f))
            } else {
                // Blinking arc
                drawLine(Color(0xFF1E293B), Offset(centerX - eyeOffsetX - eyeRadius, eyeCenterY), Offset(centerX - eyeOffsetX + eyeRadius, eyeCenterY), strokeWidth = 2f)
                drawLine(Color(0xFF1E293B), Offset(centerX + eyeOffsetX - eyeRadius, eyeCenterY), Offset(centerX + eyeOffsetX + eyeRadius, eyeCenterY), strokeWidth = 2f)
            }

            // Big warm smile showing teeth (like in the photo)
            val mouthPath = Path().apply {
                moveTo(centerX - radius * 0.34f, centerY + radius * 0.30f)
                quadraticTo(centerX, centerY + radius * 0.50f, centerX + radius * 0.34f, centerY + radius * 0.30f)
                close()
            }
            drawPath(mouthPath, Color(0xFFB91C1C).copy(alpha = 0.8f))
            // White teeth row
            drawRoundRect(
                color = Color.White.copy(alpha = 0.9f),
                topLeft = Offset(centerX - radius * 0.22f, centerY + radius * 0.30f),
                size = Size(radius * 0.44f, radius * 0.10f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(2f, 2f)
            )
        }
        "wink" -> {
            // Left Eye open
            drawCircle(Color(0xFF3E2723), eyeRadius, Offset(centerX - eyeOffsetX, eyeCenterY))
            drawCircle(Color.White, eyeRadius * 0.4f, Offset(centerX - eyeOffsetX - 2f, eyeCenterY - 3f))
            // Right Eye winking 😉
            val winkPath = Path().apply {
                moveTo(centerX + eyeOffsetX - eyeRadius, eyeCenterY + 2f)
                quadraticTo(centerX + eyeOffsetX, eyeCenterY - eyeRadius * 0.7f, centerX + eyeOffsetX + eyeRadius, eyeCenterY + 2f)
            }
            drawPath(winkPath, Color(0xFF1E293B), style = Stroke(width = 3f, cap = StrokeCap.Round))

            // Smile
            val mouthPath = Path().apply {
                moveTo(centerX - radius * 0.28f, centerY + radius * 0.34f)
                quadraticTo(centerX, centerY + radius * 0.45f, centerX + radius * 0.28f, centerY + radius * 0.34f)
            }
            drawPath(mouthPath, Color(0xFFB91C1C).copy(alpha = 0.8f), style = Stroke(width = 3f, cap = StrokeCap.Round))
        }
        "playful" -> {
            // Cheeky Traquina Face with sticking out tongue 😜
            drawCircle(Color(0xFF3E2723), eyeRadius * 0.9f, Offset(centerX - eyeOffsetX, eyeCenterY))
            val winkPath = Path().apply {
                moveTo(centerX + eyeOffsetX - eyeRadius, eyeCenterY + 2f)
                quadraticTo(centerX + eyeOffsetX, eyeCenterY - eyeRadius * 0.7f, centerX + eyeOffsetX + eyeRadius, eyeCenterY + 2f)
            }
            drawPath(winkPath, Color(0xFF1E293B), style = Stroke(width = 3f, cap = StrokeCap.Round))

            // Open mouth
            val mouthPath = Path().apply {
                moveTo(centerX - radius * 0.28f, centerY + radius * 0.32f)
                quadraticTo(centerX, centerY + radius * 0.45f, centerX + radius * 0.28f, centerY + radius * 0.32f)
                close()
            }
            drawPath(mouthPath, Color(0xFF881337).copy(alpha = 0.8f))
            // Tongue
            drawCircle(Color(0xFFFB7185), radius * 0.12f, Offset(centerX + 3f, centerY + radius * 0.40f))
        }
        "star_eyes" -> {
            // Star Eyes 🤩
            drawStar(centerX - eyeOffsetX, eyeCenterY, eyeRadius * 1.2f, Color(0xFFF59E0B).copy(alpha = 0.9f))
            drawStar(centerX + eyeOffsetX, eyeCenterY, eyeRadius * 1.2f, Color(0xFFF59E0B).copy(alpha = 0.9f))

            // Excited big open grin
            val mouthPath = Path().apply {
                moveTo(centerX - radius * 0.32f, centerY + radius * 0.32f)
                quadraticTo(centerX, centerY + radius * 0.50f, centerX + radius * 0.32f, centerY + radius * 0.32f)
                close()
            }
            drawPath(mouthPath, Color(0xFFB91C1C).copy(alpha = 0.8f))
            // White teeth
            drawRect(Color.White.copy(alpha = 0.9f), Offset(centerX - radius * 0.2f, centerY + radius * 0.32f), Size(radius * 0.4f, 4f))
        }
        "happy" -> {
            // Closed eyes curved of joy ^_^
            val leftEye = Path().apply {
                moveTo(centerX - eyeOffsetX - eyeRadius, eyeCenterY + 4f)
                quadraticTo(centerX - eyeOffsetX, eyeCenterY - eyeRadius, centerX - eyeOffsetX + eyeRadius, eyeCenterY + 4f)
            }
            val rightEye = Path().apply {
                moveTo(centerX + eyeOffsetX - eyeRadius, eyeCenterY + 4f)
                quadraticTo(centerX + eyeOffsetX, eyeCenterY - eyeRadius, centerX + eyeOffsetX + eyeRadius, eyeCenterY + 4f)
            }
            drawPath(leftEye, Color(0xFF1E293B), style = Stroke(width = 3f, cap = StrokeCap.Round))
            drawPath(rightEye, Color(0xFF1E293B), style = Stroke(width = 6f, cap = StrokeCap.Round))

            val mouthPath = Path().apply {
                moveTo(centerX - radius * 0.26f, centerY + radius * 0.34f)
                quadraticTo(centerX, centerY + radius * 0.45f, centerX + radius * 0.26f, centerY + radius * 0.34f)
            }
            drawPath(mouthPath, Color(0xFFB91C1C).copy(alpha = 0.8f), style = Stroke(width = 3f, cap = StrokeCap.Round))
        }
    }

    // Cute nose dot
    drawCircle(Color(0xFFE2A077).copy(alpha = 0.6f), 2f, Offset(centerX, centerY + radius * 0.16f))
}

private fun DrawScope.drawAccessory(
    accessory: String,
    centerX: Float,
    centerY: Float,
    radius: Float
) {
    when (accessory) {
        "cap_vintage_beret" -> {
            // Classic Vintage Brown Newsboy/Flat Cap Boina (exact match to the photo!)
            val beretColor = Color(0xFF6D4C41)
            val shadowBeret = Color(0xFF4E342E)
            val foldHighlight = Color(0xFF8D6E63)

            // Wide curved beret crown
            val beretCrown = Path().apply {
                moveTo(centerX - radius * 1.30f, centerY - radius * 0.15f)
                quadraticTo(centerX - radius * 1.10f, centerY - radius * 1.45f, centerX, centerY - radius * 1.40f)
                quadraticTo(centerX + radius * 1.10f, centerY - radius * 1.45f, centerX + radius * 1.30f, centerY - radius * 0.15f)
                quadraticTo(centerX, centerY - radius * 0.45f, centerX - radius * 1.30f, centerY - radius * 0.15f)
                close()
            }
            drawPath(beretCrown, beretColor)

            // Texture and fold lines on cap
            drawLine(shadowBeret, Offset(centerX - radius * 0.6f, centerY - radius * 1.30f), Offset(centerX - radius * 0.3f, centerY - radius * 0.45f), strokeWidth = 3f)
            drawLine(shadowBeret, Offset(centerX + radius * 0.6f, centerY - radius * 1.30f), Offset(centerX + radius * 0.3f, centerY - radius * 0.45f), strokeWidth = 3f)
            drawLine(foldHighlight, Offset(centerX, centerY - radius * 1.38f), Offset(centerX, centerY - radius * 0.45f), strokeWidth = 4f)

            // Front brim peak attached
            val frontPeak = Path().apply {
                moveTo(centerX - radius * 0.85f, centerY - radius * 0.28f)
                quadraticTo(centerX, centerY - radius * 0.08f, centerX + radius * 0.85f, centerY - radius * 0.28f)
                quadraticTo(centerX, centerY - radius * 0.36f, centerX - radius * 0.85f, centerY - radius * 0.28f)
                close()
            }
            drawPath(frontPeak, shadowBeret)

            // Top decorative button
            drawCircle(shadowBeret, radius * 0.10f, Offset(centerX, centerY - radius * 1.36f))
        }
        "cap_ze" -> {
            // Official Zé Traquina Blue Baseball Cap with Yellow Visor
            val capColor = Color(0xFF0288D1)
            val visorColor = Color(0xFFFBBF24)

            // Cap dome
            val capDome = Path().apply {
                moveTo(centerX - radius * 1.15f, centerY - radius * 0.35f)
                quadraticTo(centerX - radius * 0.8f, centerY - radius * 1.35f, centerX, centerY - radius * 1.35f)
                quadraticTo(centerX + radius * 0.8f, centerY - radius * 1.35f, centerX + radius * 1.15f, centerY - radius * 0.35f)
                close()
            }
            drawPath(capDome, capColor)

            // Yellow front Visor
            val visor = Path().apply {
                moveTo(centerX - radius * 1.1f, centerY - radius * 0.32f)
                quadraticTo(centerX - radius * 0.2f, centerY - radius * 0.55f, centerX + radius * 1.35f, centerY - radius * 0.22f)
                lineTo(centerX + radius * 1.1f, centerY - radius * 0.12f)
                quadraticTo(centerX, centerY - radius * 0.22f, centerX - radius * 1.1f, centerY - radius * 0.32f)
                close()
            }
            drawPath(visor, visorColor)

            // Button on top
            drawCircle(visorColor, radius * 0.12f, Offset(centerX, centerY - radius * 1.32f))
        }
        "glasses_cool" -> {
            // Dark sunglasses 😎
            val glassesY = centerY - radius * 0.05f
            val gRadius = radius * 0.24f
            val darkGlass = Color(0xFF0F172A)

            drawCircle(darkGlass, gRadius, Offset(centerX - radius * 0.38f, glassesY))
            drawCircle(darkGlass, gRadius, Offset(centerX + radius * 0.38f, glassesY))
            drawLine(darkGlass, Offset(centerX - radius * 0.15f, glassesY), Offset(centerX + radius * 0.15f, glassesY), strokeWidth = 6f)
            // Lens shine
            drawLine(Color.White.copy(alpha = 0.6f), Offset(centerX - radius * 0.46f, glassesY - 8f), Offset(centerX - radius * 0.30f, glassesY + 8f), strokeWidth = 3f)
            drawLine(Color.White.copy(alpha = 0.6f), Offset(centerX + radius * 0.30f, glassesY - 8f), Offset(centerX + radius * 0.46f, glassesY + 8f), strokeWidth = 3f)
        }
        "glasses_smart" -> {
            // Round smart glasses 🤓
            val glassesY = centerY - radius * 0.05f
            val gRadius = radius * 0.22f
            val frameColor = Color(0xFFD97706)

            drawCircle(frameColor, gRadius, Offset(centerX - radius * 0.38f, glassesY), style = Stroke(width = 5f))
            drawCircle(frameColor, gRadius, Offset(centerX + radius * 0.38f, glassesY), style = Stroke(width = 5f))
            drawLine(frameColor, Offset(centerX - radius * 0.16f, glassesY), Offset(centerX + radius * 0.16f, glassesY), strokeWidth = 5f)
        }
        "headphones" -> {
            // DJ Headphones 🎧
            val hpColor = Color(0xFFEC4899)
            // Headband
            val band = Path().apply {
                moveTo(centerX - radius * 1.08f, centerY)
                quadraticTo(centerX, centerY - radius * 1.50f, centerX + radius * 1.08f, centerY)
            }
            drawPath(band, Color(0xFF334155), style = Stroke(width = 10f, cap = StrokeCap.Round))
            // Ear cups
            drawRoundRect(hpColor, Offset(centerX - radius * 1.25f, centerY - radius * 0.25f), Size(radius * 0.35f, radius * 0.55f), androidx.compose.ui.geometry.CornerRadius(16f, 16f))
            drawRoundRect(hpColor, Offset(centerX + radius * 0.90f, centerY - radius * 0.25f), Size(radius * 0.35f, radius * 0.55f), androidx.compose.ui.geometry.CornerRadius(16f, 16f))
        }
        "bow_ribbon" -> {
            // Cute ribbon on top 🎀
            val bowColor = Color(0xFFEF4444)
            val bowX = centerX + radius * 0.65f
            val bowY = centerY - radius * 0.85f

            val leftWing = Path().apply {
                moveTo(bowX, bowY)
                lineTo(bowX - 18f, bowY - 14f)
                lineTo(bowX - 18f, bowY + 14f)
                close()
            }
            val rightWing = Path().apply {
                moveTo(bowX, bowY)
                lineTo(bowX + 18f, bowY - 14f)
                lineTo(bowX + 18f, bowY + 14f)
                close()
            }
            drawPath(leftWing, bowColor)
            drawPath(rightWing, bowColor)
            drawCircle(Color(0xFFFDE047), 7f, Offset(bowX, bowY))
        }
        "star_badge" -> {
            // Golden star badge on chest
            drawStar(centerX - radius * 0.50f, centerY + radius * 0.90f, 14f, Color(0xFFF59E0B))
        }
    }
}

private fun DrawScope.drawStar(
    centerX: Float,
    centerY: Float,
    radius: Float,
    color: Color
) {
    val points = 5
    val path = Path()
    val angleStep = Math.PI / points
    var currentAngle = -Math.PI / 2

    for (i in 0 until points * 2) {
        val r = if (i % 2 == 0) radius else radius * 0.45f
        val x = centerX + (r * Math.cos(currentAngle)).toFloat()
        val y = centerY + (r * Math.sin(currentAngle)).toFloat()
        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        currentAngle += angleStep
    }
    path.close()
    drawPath(path, color)
}
