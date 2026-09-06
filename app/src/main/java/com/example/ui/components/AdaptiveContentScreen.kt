package com.example.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Sizing configuration properties used to scale UI dynamically
 * based on the real available screen height of the device.
 */
data class AdaptiveSizing(
    val screenHeightDp: Int,
    val screenWidthDp: Int,
    val isSmallScreen: Boolean, // height < 700dp (small phones)
    val isMediumScreen: Boolean, // 700dp <= height <= 900dp (standard phones)
    val isLargeScreen: Boolean, // height > 900dp (large screens, foldables, tablets)
    val spacingMultiplier: Float, // Adjust padding/spacing (e.g. 0.65f, 1.0f, 1.3f)
    val fontScale: Float // Adjust font sizes dynamically (e.g. 0.85f, 1.0f, 1.2f)
) {
    /**
     * Scale padding/spacing Dp value.
     */
    fun scalePadding(base: Dp): Dp {
        return base * spacingMultiplier
    }

    /**
     * Scale a dimension Dp value (like image width/height).
     */
    fun scaleSize(base: Dp): Dp {
        // We cap the scaling on small screens so elements don't shrink excessively
        val multiplier = if (isSmallScreen) maxOf(0.7f, spacingMultiplier) else spacingMultiplier
        return base * multiplier
    }

    /**
     * Scale text font size safely.
     */
    fun scaleFont(base: TextUnit): TextUnit {
        return if (base.isSp) {
            val minFontScale = if (isSmallScreen) 0.82f else 1.0f
            val finalScale = maxOf(minFontScale, fontScale)
            (base.value * finalScale).sp
        } else {
            base
        }
    }
}

/**
 * CompositionLocal to expose adaptive sizing to child composables.
 */
val LocalAdaptiveSizing = staticCompositionLocalOf {
    AdaptiveSizing(
        screenHeightDp = 800,
        screenWidthDp = 360,
        isSmallScreen = false,
        isMediumScreen = true,
        isLargeScreen = false,
        spacingMultiplier = 1.0f,
        fontScale = 1.0f
    )
}

/**
 * A highly adaptive wrapper that measures screen dimensions and sets up CompositionLocal
 * scaling constraints so that inner games and education screens can fit perfectly inside the viewport.
 */
@Composable
fun AdaptiveContentScreen(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val configuration = LocalConfiguration.current
    val heightDp = configuration.screenHeightDp
    val widthDp = configuration.screenWidthDp

    // Define three screen profiles as requested
    val isSmall = heightDp < 700
    val isLarge = heightDp >= 950
    val isMedium = !isSmall && !isLarge

    val spacingMultiplier = when {
        isSmall -> 0.65f
        isLarge -> 1.25f
        else -> 1.0f
    }

    val fontScale = when {
        isSmall -> 0.85f
        isLarge -> 1.2f
        else -> 1.0f
    }

    val sizing = AdaptiveSizing(
        screenHeightDp = heightDp,
        screenWidthDp = widthDp,
        isSmallScreen = isSmall,
        isMediumScreen = isMedium,
        isLargeScreen = isLarge,
        spacingMultiplier = spacingMultiplier,
        fontScale = fontScale
    )

    CompositionLocalProvider(LocalAdaptiveSizing provides sizing) {
        BoxWithConstraints(
            modifier = modifier.fillMaxSize()
        ) {
            content()
        }
    }
}
