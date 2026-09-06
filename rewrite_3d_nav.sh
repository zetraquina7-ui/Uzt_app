cat << 'INNER_EOF' > app/src/main/java/com/example/ui/components/UniversoBottomNavBar.kt
package com.example.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.ui.navigation.Screen

@Composable
fun UniversoBottomNavBar(
    screens: List<Screen>,
    currentScreen: Screen,
    onScreenSelected: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .zIndex(9999f)
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .testTag("bottom_nav_bar"),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Liquid Glass Background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(86.dp)
                .shadow(
                    elevation = 24.dp,
                    shape = RoundedCornerShape(28.dp),
                    spotColor = Color(0xFF003366).copy(alpha = 0.15f),
                    ambientColor = Color(0xFF003366).copy(alpha = 0.1f)
                )
                .clip(RoundedCornerShape(28.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.85f),
                            Color.White.copy(alpha = 0.6f),
                            Color.White.copy(alpha = 0.75f)
                        )
                    )
                )
                .border(
                    width = 1.5.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White,
                            Color.White.copy(alpha = 0.3f),
                            Color.White.copy(alpha = 0.7f)
                        )
                    ),
                    shape = RoundedCornerShape(28.dp)
                )
        )

        // Icons Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp, start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            screens.forEach { screen ->
                val isSelected = currentScreen.route == screen.route
                DockItem(
                    screen = screen,
                    isSelected = isSelected,
                    onClick = { onScreenSelected(screen) }
                )
            }
        }
    }
}

@Composable
private fun DockItem(
    screen: Screen,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.25f else if (isSelected) 1.2f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "nav_scale"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .testTag("nav_item_${screen.route}")
            .padding(4.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(54.dp)
                .scale(scale)
        ) {
            when (screen) {
                Screen.Home -> Icon3DHome(Modifier.fillMaxWidth())
                Screen.Learn -> Icon3DBook(Modifier.fillMaxWidth())
                Screen.Games -> Icon3DGame(Modifier.fillMaxWidth())
                Screen.Media -> Icon3DMedia(Modifier.fillMaxWidth())
                Screen.Chat -> Icon3DChat(Modifier.fillMaxWidth())
                Screen.More -> Icon3DMore(Modifier.fillMaxWidth())
                else -> Icon3DMore(Modifier.fillMaxWidth())
            }
        }
        
        Spacer(modifier = Modifier.height(2.dp))
        
        Text(
            text = screen.title,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold,
            color = if (isSelected) Color(0xFF1E293B) else Color(0xFF64748B),
            modifier = Modifier.alpha(if (isSelected) 1f else 0.8f)
        )
        
        if (isSelected) {
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .size(width = 16.dp, height = 4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(Color(0xFF3B82F6), Color(0xFF8B5CF6))
                        )
                    )
            )
        } else {
            Spacer(modifier = Modifier.height(6.dp)) // Maintain layout height
        }
    }
}

@Composable
fun Icon3DHome(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.drawWithCache {
        val w = size.width
        val h = size.height
        
        val roofShadowPath = Path().apply {
            moveTo(w * 0.15f, h * 0.55f)
            lineTo(w * 0.5f, h * 0.15f)
            lineTo(w * 0.85f, h * 0.55f)
            lineTo(w * 0.5f, h * 0.95f)
            close()
        }
        
        val roofPath = Path().apply {
            moveTo(w * 0.1f, h * 0.45f)
            lineTo(w * 0.5f, h * 0.1f)
            lineTo(w * 0.9f, h * 0.45f)
            lineTo(w * 0.5f, h * 0.85f)
            close()
        }
        
        val baseShadowPath = Path().apply {
            moveTo(w * 0.25f, h * 0.5f)
            lineTo(w * 0.75f, h * 0.5f)
            lineTo(w * 0.75f, h * 0.95f)
            lineTo(w * 0.25f, h * 0.95f)
            close()
        }
        
        val basePath = Path().apply {
            moveTo(w * 0.25f, h * 0.45f)
            lineTo(w * 0.75f, h * 0.45f)
            lineTo(w * 0.75f, h * 0.85f)
            lineTo(w * 0.25f, h * 0.85f)
            close()
        }

        onDrawBehind {
            // Base shadow
            drawPath(baseShadowPath, Color(0xFF1E3A8A))
            // Base front
            drawPath(basePath, Brush.linearGradient(listOf(Color(0xFF60A5FA), Color(0xFF2563EB))))
            
            // Door
            drawRoundRect(
                color = Color(0xFFEFF6FF),
                topLeft = Offset(w * 0.4f, h * 0.6f),
                size = Size(w * 0.2f, h * 0.25f),
                cornerRadius = CornerRadius(w * 0.05f)
            )
            
            // Roof shadow
            drawPath(roofShadowPath, Color(0xFF7F1D1D))
            // Roof front
            drawPath(roofPath, Brush.linearGradient(listOf(Color(0xFFF87171), Color(0xFFDC2626))))
        }
    })
}

@Composable
fun Icon3DBook(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.drawWithCache {
        val w = size.width
        val h = size.height
        
        // Book thickness (pages)
        val pagesPath = Path().apply {
            moveTo(w * 0.15f, h * 0.85f)
            lineTo(w * 0.85f, h * 0.85f)
            lineTo(w * 0.9f, h * 0.75f)
            lineTo(w * 0.2f, h * 0.75f)
            close()
        }
        
        // Back Cover
        val backCoverPath = Path().apply {
            moveTo(w * 0.1f, h * 0.85f)
            lineTo(w * 0.9f, h * 0.85f)
            lineTo(w * 0.9f, h * 0.9f)
            lineTo(w * 0.1f, h * 0.9f)
            close()
        }
        
        // Front Cover
        val frontCoverPath = Path().apply {
            moveTo(w * 0.1f, h * 0.15f)
            lineTo(w * 0.8f, h * 0.15f)
            lineTo(w * 0.9f, h * 0.75f)
            lineTo(w * 0.2f, h * 0.75f)
            close()
        }
        
        // Spine
        val spinePath = Path().apply {
            moveTo(w * 0.1f, h * 0.15f)
            lineTo(w * 0.2f, h * 0.75f)
            lineTo(w * 0.1f, h * 0.9f)
            lineTo(w * 0.0f, h * 0.3f)
            close()
        }
        
        // Title / badge on cover
        val badgePath = Path().apply {
            moveTo(w * 0.3f, h * 0.3f)
            lineTo(w * 0.5f, h * 0.3f)
            lineTo(w * 0.55f, h * 0.5f)
            lineTo(w * 0.35f, h * 0.5f)
            close()
        }
        
        onDrawBehind {
            drawPath(pagesPath, Color(0xFFF1F5F9))
            drawLine(Color(0xFFCBD5E1), Offset(w * 0.18f, h * 0.8f), Offset(w * 0.88f, h * 0.8f), strokeWidth = 2f)
            drawPath(backCoverPath, Color(0xFF064E3B))
            drawPath(frontCoverPath, Brush.linearGradient(listOf(Color(0xFF34D399), Color(0xFF059669))))
            drawPath(spinePath, Brush.linearGradient(listOf(Color(0xFF10B981), Color(0xFF047857))))
            drawPath(badgePath, Color(0xFFFBBF24))
        }
    })
}

@Composable
fun Icon3DGame(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.drawWithCache {
        val w = size.width
        val h = size.height
        
        // Shadow/Depth of controller
        val shadowPath = Path().apply {
            moveTo(w * 0.15f, h * 0.35f)
            quadraticTo(w * 0.5f, h * 0.25f, w * 0.85f, h * 0.35f)
            quadraticTo(w * 1.0f, h * 0.5f, w * 0.85f, h * 0.75f)
            quadraticTo(w * 0.75f, h * 0.85f, w * 0.65f, h * 0.75f)
            lineTo(w * 0.5f, h * 0.65f)
            lineTo(w * 0.35f, h * 0.75f)
            quadraticTo(w * 0.25f, h * 0.85f, w * 0.15f, h * 0.75f)
            quadraticTo(w * 0.0f, h * 0.5f, w * 0.15f, h * 0.35f)
            close()
        }

        // Front of controller
        val frontPath = Path().apply {
            moveTo(w * 0.15f, h * 0.25f)
            quadraticTo(w * 0.5f, h * 0.15f, w * 0.85f, h * 0.25f)
            quadraticTo(w * 1.0f, h * 0.4f, w * 0.85f, h * 0.65f)
            quadraticTo(w * 0.75f, h * 0.75f, w * 0.65f, h * 0.65f)
            lineTo(w * 0.5f, h * 0.55f)
            lineTo(w * 0.35f, h * 0.65f)
            quadraticTo(w * 0.25f, h * 0.75f, w * 0.15f, h * 0.65f)
            quadraticTo(w * 0.0f, h * 0.4f, w * 0.15f, h * 0.25f)
            close()
        }

        onDrawBehind {
            drawPath(shadowPath, Color(0xFF4C1D95))
            drawPath(frontPath, Brush.linearGradient(listOf(Color(0xFFA78BFA), Color(0xFF7C3AED))))
            
            // D-pad (Left)
            val dpadColor = Color(0xFF1E1B4B)
            drawRoundRect(dpadColor, topLeft = Offset(w * 0.22f, h * 0.4f), size = Size(w * 0.06f, h * 0.2f), cornerRadius = CornerRadius(4f))
            drawRoundRect(dpadColor, topLeft = Offset(w * 0.15f, h * 0.47f), size = Size(w * 0.2f, h * 0.06f), cornerRadius = CornerRadius(4f))
            
            // Action Buttons (Right)
            val btnColors = listOf(Color(0xFFF43F5E), Color(0xFFFBBF24), Color(0xFF10B981), Color(0xFF3B82F6))
            drawCircle(btnColors[0], radius = w * 0.04f, center = Offset(w * 0.75f, h * 0.4f)) // Top
            drawCircle(btnColors[1], radius = w * 0.04f, center = Offset(w * 0.83f, h * 0.48f)) // Right
            drawCircle(btnColors[2], radius = w * 0.04f, center = Offset(w * 0.75f, h * 0.56f)) // Bottom
            drawCircle(btnColors[3], radius = w * 0.04f, center = Offset(w * 0.67f, h * 0.48f)) // Left
        }
    })
}

@Composable
fun Icon3DMedia(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.drawWithCache {
        val w = size.width
        val h = size.height
        
        // TV Box shadow/side
        val tvSidePath = Path().apply {
            moveTo(w * 0.1f, h * 0.25f)
            lineTo(w * 0.9f, h * 0.25f)
            lineTo(w * 0.9f, h * 0.75f)
            lineTo(w * 0.85f, h * 0.85f)
            lineTo(w * 0.05f, h * 0.85f)
            lineTo(w * 0.1f, h * 0.75f)
            close()
        }
        
        // TV Front Box
        val tvFrontPath = Path().apply {
            moveTo(w * 0.1f, h * 0.25f)
            lineTo(w * 0.9f, h * 0.25f)
            lineTo(w * 0.9f, h * 0.75f)
            lineTo(w * 0.1f, h * 0.75f)
            close()
        }
        
        // Screen
        val screenPath = Path().apply {
            moveTo(w * 0.15f, h * 0.3f)
            lineTo(w * 0.85f, h * 0.3f)
            lineTo(w * 0.85f, h * 0.7f)
            lineTo(w * 0.15f, h * 0.7f)
            close()
        }
        
        // Play button on screen
        val playBtnPath = Path().apply {
            moveTo(w * 0.4f, h * 0.4f)
            lineTo(w * 0.65f, h * 0.5f)
            lineTo(w * 0.4f, h * 0.6f)
            close()
        }
        
        onDrawBehind {
            drawPath(tvSidePath, Color(0xFF9A3412))
            drawPath(tvFrontPath, Brush.linearGradient(listOf(Color(0xFFFB923C), Color(0xFFEA580C))))
            drawPath(screenPath, Brush.linearGradient(listOf(Color(0xFF334155), Color(0xFF0F172A))))
            drawPath(playBtnPath, Color(0xFFF1F5F9))
            
            // Antenna
            drawLine(Color(0xFF94A3B8), Offset(w * 0.5f, h * 0.25f), Offset(w * 0.3f, h * 0.05f), strokeWidth = 5f)
            drawLine(Color(0xFF94A3B8), Offset(w * 0.5f, h * 0.25f), Offset(w * 0.7f, h * 0.05f), strokeWidth = 5f)
            drawCircle(Color(0xFFEF4444), radius = 5f, center = Offset(w * 0.3f, h * 0.05f))
            drawCircle(Color(0xFFEF4444), radius = 5f, center = Offset(w * 0.7f, h * 0.05f))
        }
    })
}

@Composable
fun Icon3DChat(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.drawWithCache {
        val w = size.width
        val h = size.height
        
        // Back bubble (shadow / 3d thickness)
        val bubbleShadowPath = Path().apply {
            moveTo(w * 0.1f, h * 0.25f)
            quadraticTo(w * 0.5f, h * 0.15f, w * 0.9f, h * 0.25f)
            quadraticTo(w * 1.0f, h * 0.5f, w * 0.9f, h * 0.75f)
            quadraticTo(w * 0.5f, h * 0.85f, w * 0.3f, h * 0.75f)
            lineTo(w * 0.1f, h * 0.9f)
            lineTo(w * 0.15f, h * 0.65f)
            quadraticTo(w * 0.0f, h * 0.5f, w * 0.1f, h * 0.25f)
            close()
        }
        
        // Front bubble
        val bubbleFrontPath = Path().apply {
            moveTo(w * 0.1f, h * 0.2f)
            quadraticTo(w * 0.5f, h * 0.1f, w * 0.9f, h * 0.2f)
            quadraticTo(w * 1.0f, h * 0.45f, w * 0.9f, h * 0.7f)
            quadraticTo(w * 0.5f, h * 0.8f, w * 0.3f, h * 0.7f)
            lineTo(w * 0.1f, h * 0.85f)
            lineTo(w * 0.15f, h * 0.6f)
            quadraticTo(w * 0.0f, h * 0.45f, w * 0.1f, h * 0.2f)
            close()
        }
        
        onDrawBehind {
            drawPath(bubbleShadowPath, Color(0xFF86198F))
            drawPath(bubbleFrontPath, Brush.linearGradient(listOf(Color(0xFFE879F9), Color(0xFFC026D3))))
            
            // Dots
            drawCircle(Color.White, radius = w * 0.05f, center = Offset(w * 0.3f, h * 0.45f))
            drawCircle(Color.White, radius = w * 0.05f, center = Offset(w * 0.5f, h * 0.45f))
            drawCircle(Color.White, radius = w * 0.05f, center = Offset(w * 0.7f, h * 0.45f))
        }
    })
}

@Composable
fun Icon3DMore(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.drawWithCache {
        val w = size.width
        val h = size.height
        
        onDrawBehind {
            // Hamburger menu 3D
            val barHeight = h * 0.18f
            val barSpacing = h * 0.28f
            val startY = h * 0.15f
            
            for (i in 0..2) {
                val y = startY + (i * barSpacing)
                
                // Shadow / Bottom depth
                drawRoundRect(
                    color = Color(0xFF0F766E),
                    topLeft = Offset(w * 0.15f, y + barHeight * 0.3f),
                    size = Size(w * 0.7f, barHeight),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(barHeight / 2, barHeight / 2)
                )
                
                // Front surface
                drawRoundRect(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFF2DD4BF), Color(0xFF0D9488))
                    ),
                    topLeft = Offset(w * 0.15f, y),
                    size = Size(w * 0.7f, barHeight),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(barHeight / 2, barHeight / 2)
                )
                
                // Highlight
                drawRoundRect(
                    color = Color.White.copy(alpha = 0.4f),
                    topLeft = Offset(w * 0.2f, y + barHeight * 0.15f),
                    size = Size(w * 0.6f, barHeight * 0.3f),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(barHeight * 0.15f, barHeight * 0.15f)
                )
            }
        }
    })
}
INNER_EOF
