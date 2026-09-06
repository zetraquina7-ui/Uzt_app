package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.*
import com.example.util.AppImageLoader
import com.example.util.PreviewConfig

@Composable
fun ZeMascotCustomizedView(
    customization: ZeCustomization,
    modifier: Modifier = Modifier,
    mascotModel: Int = R.drawable.img_ze_ai_mascot,
    showAuraGlow: Boolean = true,
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
    enableFloatingMotion: Boolean = true
) {
    val context = LocalContext.current
    val isPreviewMode = PreviewConfig.isInPreview() || androidx.compose.ui.platform.LocalInspectionMode.current

    val mascotImageRequest = remember(mascotModel, context, isPreviewMode) {
        try {
            AppImageLoader.buildMascotRequest(context, mascotModel, isPreviewMode = isPreviewMode)
        } catch (e: Throwable) {
            AppImageLoader.buildMascotRequest(context, R.drawable.img_ze_ai_mascot, isPreviewMode = isPreviewMode)
        }
    }

    // Aura Glow & Gentle Floating Motion Animation
    val auraPulseScale: Float
    val auraAlpha: Float
    val floatOffsetY: Float
    val floatRotation: Float

    if (true) {
        auraPulseScale = 1.0f
        auraAlpha = 0.5f
        floatOffsetY = 0f
        floatRotation = 0f
    } else {
        val infiniteTransition = rememberInfiniteTransition(label = "auraTransition")
        val animPulseScale by infiniteTransition.animateFloat(
            initialValue = 0.95f,
            targetValue = 1.08f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "auraPulseScale"
        )

        val animAlpha by infiniteTransition.animateFloat(
            initialValue = 0.35f,
            targetValue = 0.65f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "auraAlpha"
        )

        val animOffsetY by infiniteTransition.animateFloat(
            initialValue = -7f,
            targetValue = 7f,
            animationSpec = infiniteRepeatable(
                animation = tween(2200, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "floatOffsetY"
        )

        val animRotation by infiniteTransition.animateFloat(
            initialValue = -2.0f,
            targetValue = 2.0f,
            animationSpec = infiniteRepeatable(
                animation = tween(2800, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "floatRotation"
        )

        auraPulseScale = animPulseScale
        auraAlpha = animAlpha
        floatOffsetY = animOffsetY
        floatRotation = animRotation
    }

    val rootModifier = if (enableFloatingMotion) {
        modifier.graphicsLayer {
            translationY = floatOffsetY
            rotationZ = floatRotation
        }
    } else {
        modifier
    }

    Box(
        modifier = rootModifier,
        contentAlignment = alignment
    ) {
        // 1. Back Aura Glow & Cape Layer
        if (showAuraGlow) {
            Box(
                modifier = Modifier
                    .fillMaxSize(0.85f)
                    .align(alignment)
                    .graphicsLayer {
                        scaleX = auraPulseScale
                        scaleY = auraPulseScale
                        alpha = auraAlpha
                    }
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                customization.aura.color.copy(alpha = 0.85f),
                                customization.aura.color.copy(alpha = 0.35f),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
            )
        }

        // Back Cape / Magic Aura for Superhero & Wizard outfits
        OutfitCapeOverlay(
            outfit = customization.outfit,
            modifier = Modifier.fillMaxSize()
        )

        // 2. Base Mascot Image
        SafeAsyncImage(
            model = mascotImageRequest,
            contentDescription = "Mascote Zé Traquina Personalizado",
            contentScale = contentScale,
            alignment = alignment,
            modifier = Modifier.fillMaxSize()
        )

        // Calculate positions relative to top/center/left
        val isLeftAligned = alignment == Alignment.BottomStart || alignment == Alignment.CenterStart
        val horizAlignment = if (isLeftAligned) Alignment.TopStart else Alignment.TopCenter
        val horizOffset = if (isLeftAligned) 20.dp else 0.dp

        // 3. Outfit Chest / Body Costume Overlay
        OutfitChestOverlay(
            outfit = customization.outfit,
            modifier = Modifier
                .align(horizAlignment)
                .padding(top = 125.dp)
                .offset(x = horizOffset)
        )

        // 4. Glasses / Eye Mask Overlay
        GlassesOverlay(
            glasses = customization.glasses,
            modifier = Modifier
                .align(horizAlignment)
                .padding(top = 62.dp)
                .offset(x = horizOffset)
        )

        // 5. Hat Overlay on top of head
        HatOverlay(
            hat = customization.hat,
            modifier = Modifier
                .align(horizAlignment)
                .padding(top = 2.dp)
                .offset(x = horizOffset)
        )
    }
}

@Composable
private fun OutfitCapeOverlay(
    outfit: OutfitOption,
    modifier: Modifier = Modifier
) {
    if (outfit == OutfitOption.SUPERHERO) {
        // Red Superhero Cape behind Zé
        Box(
            modifier = modifier
                .padding(top = 60.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(Color(0xFFE53935), Color(0xFFB71C1C))
                    ),
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp, bottomStart = 60.dp, bottomEnd = 60.dp)
                )
                .border(2.dp, Color(0xFFFFD54F), RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp, bottomStart = 60.dp, bottomEnd = 60.dp))
        )
    } else if (outfit == OutfitOption.WIZARD) {
        // Purple Magic Aura stars behind Zé
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Text("✨ 🔮 ✨", fontSize = 32.sp, modifier = Modifier.padding(bottom = 80.dp))
        }
    } else if (outfit == OutfitOption.ASTRONAUT) {
        // Space Orbit ring
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Text("🪐 🚀 🌌", fontSize = 22.sp, modifier = Modifier.padding(bottom = 120.dp))
        }
    }
}

@Composable
private fun HatOverlay(
    hat: HatOption,
    modifier: Modifier = Modifier
) {
    if (hat == HatOption.NONE || hat == HatOption.BERET) return

    val (emoji, bgGradient, borderCol, labelText) = when (hat) {
        HatOption.BERET -> Quadruple("👨‍🎨", listOf(Color(0xFF2E7D32), Color(0xFF1B5E20)), Color.White, "")
        HatOption.PARTY -> Quadruple("🥳", listOf(Color(0xFFFF4081), Color(0xFFFFD54F)), Color.White, "FESTA")
        HatOption.WIZARD -> Quadruple("🧙‍♂️", listOf(Color(0xFF7C4DFF), Color(0xFF00E5FF)), Color(0xFFFFD54F), "MAGIA")
        HatOption.CROWN -> Quadruple("👑", listOf(Color(0xFFFFD700), Color(0xFFFFA000)), Color.White, "COROA")
        HatOption.CAP -> Quadruple("🧢", listOf(Color(0xFF29B6F6), Color(0xFF0288D1)), Color.White, "BONÉ")
        HatOption.BUNNY -> Quadruple("🐰", listOf(Color(0xFFF8BBD0), Color(0xFFF48FB1)), Color.White, "COELHO")
        HatOption.PIRATE -> Quadruple("🏴‍☠️", listOf(Color(0xFF37474F), Color(0xFF212121)), Color(0xFFFFD54F), "PIRATA")
        else -> Quadruple("🎩", listOf(Color.DarkGray, Color.Black), Color.White, "")
    }

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color.Transparent,
        modifier = modifier.shadow(10.dp, RoundedCornerShape(20.dp))
    ) {
        Row(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(bgGradient),
                    shape = RoundedCornerShape(20.dp)
                )
                .border(2.dp, borderCol, RoundedCornerShape(20.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(emoji, fontSize = 28.sp)
            if (labelText.isNotEmpty()) {
                Spacer(Modifier.width(4.dp))
                Text(
                    text = labelText,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun GlassesOverlay(
    glasses: GlassesOption,
    modifier: Modifier = Modifier
) {
    if (glasses == GlassesOption.NONE) return

    val (emoji, bgCol, borderCol, tagText) = when (glasses) {
        GlassesOption.READING -> Quadruple("👓", Color(0xEE1E88E5), Color.White, "SÁBIO")
        GlassesOption.SUNGLASSES -> Quadruple("🕶️", Color(0xEE212121), Color(0xFFFFD54F), "COOL")
        GlassesOption.STAR -> Quadruple("🌟", Color(0xEEFFB300), Color.White, "ESTRELA")
        GlassesOption.HERO_MASK -> Quadruple("🦸‍♂️", Color(0xEE8E24AA), Color(0xFF00E5FF), "HERÓI")
        GlassesOption.GLASSES_3D -> Quadruple("🕶️✨", Color(0xEED81B60), Color.White, "3D")
        else -> Quadruple("👓", Color.Black, Color.White, "")
    }

    Surface(
        shape = RoundedCornerShape(18.dp),
        color = bgCol,
        border = BorderStroke(1.8.dp, borderCol),
        shadowElevation = 8.dp,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(emoji, fontSize = 24.sp)
            Spacer(Modifier.width(4.dp))
            Text(
                tagText,
                fontSize = 10.5.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
        }
    }
}

@Composable
private fun OutfitChestOverlay(
    outfit: OutfitOption,
    modifier: Modifier = Modifier
) {
    if (outfit == OutfitOption.POLO_GREEN) return

    val (emoji, title, gradient) = when (outfit) {
        OutfitOption.ASTRONAUT -> Triple("👨‍🚀", "ASTRONAUTA", listOf(Color(0xFF0288D1), Color(0xFF0D47A1)))
        OutfitOption.WIZARD -> Triple("🧙‍♂️", "FEITICEIRO", listOf(Color(0xFF673AB7), Color(0xFF311B92)))
        OutfitOption.SUPERHERO -> Triple("🦸‍♂️", "SUPER HERÓI", listOf(Color(0xFFD32F2F), Color(0xFFB71C1C)))
        OutfitOption.EXPLORER -> Triple("🏕️", "EXPLORADOR", listOf(Color(0xFF388E3C), Color(0xFF1B5E20)))
        OutfitOption.PAINTER -> Triple("🎨", "ARTISTA", listOf(Color(0xFFFF9800), Color(0xFFF57C00)))
        else -> Triple("👕", "POLO", listOf(Color(0xFF4CAF50), Color(0xFF2E7D32)))
    }

    Surface(
        shape = RoundedCornerShape(18.dp),
        color = Color.Transparent,
        modifier = modifier.shadow(8.dp, RoundedCornerShape(18.dp))
    ) {
        Row(
            modifier = Modifier
                .background(Brush.horizontalGradient(gradient), RoundedCornerShape(18.dp))
                .border(1.8.dp, Color.White, RoundedCornerShape(18.dp))
                .padding(horizontal = 12.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(emoji, fontSize = 22.sp)
            Spacer(Modifier.width(6.dp))
            Text(
                title,
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                color = Color.White
            )
        }
    }
}

private data class Quadruple<A, B, C, D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
)
