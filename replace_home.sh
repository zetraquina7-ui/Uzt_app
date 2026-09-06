cat << 'INNER_EOF' > app/src/main/java/com/example/ui/components/UniversoBottomNavBar.kt.tmp
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
import androidx.compose.foundation.layout.fillMaxSize
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
                    elevation = 16.dp,
                    shape = RoundedCornerShape(28.dp),
                    spotColor = Color(0xFF003366).copy(alpha = 0.2f),
                    ambientColor = Color(0xFF003366).copy(alpha = 0.1f)
                )
                .clip(RoundedCornerShape(28.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.35f),
                            Color.White.copy(alpha = 0.15f),
                            Color.White.copy(alpha = 0.25f)
                        )
                    )
                )
                .border(
                    width = 1.5.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.9f),
                            Color.White.copy(alpha = 0.3f),
                            Color.White.copy(alpha = 0.6f)
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
                Screen.Home -> Icon3DHome(Modifier.fillMaxSize())
                Screen.Learn -> Icon3DBook(Modifier.fillMaxSize())
                Screen.Games -> Icon3DGame(Modifier.fillMaxSize())
                Screen.Media -> Icon3DMedia(Modifier.fillMaxSize())
                Screen.Chat -> Icon3DChat(Modifier.fillMaxSize())
                Screen.More -> Icon3DMore(Modifier.fillMaxSize())
                else -> Icon3DMore(Modifier.fillMaxSize())
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
        
        val roofPath = Path().apply {
            moveTo(w * 0.1f, h * 0.45f)
            lineTo(w * 0.5f, h * 0.1f)
            lineTo(w * 0.9f, h * 0.45f)
            close()
        }
        
        val roofHighlightPath = Path().apply {
            moveTo(w * 0.1f, h * 0.45f)
            lineTo(w * 0.5f, h * 0.1f)
            lineTo(w * 0.5f, h * 0.15f)
            lineTo(w * 0.18f, h * 0.43f)
            close()
        }

        val basePath = Path().apply {
            moveTo(w * 0.2f, h * 0.45f)
            lineTo(w * 0.8f, h * 0.45f)
            lineTo(w * 0.8f, h * 0.9f)
            lineTo(w * 0.2f, h * 0.9f)
            close()
        }

        onDrawBehind {
            // Base shadow to create depth
            drawPath(basePath, Color(0xFF1E3A8A).copy(alpha = 0.5f))
            
            // Base front
            drawPath(basePath, Brush.linearGradient(listOf(Color(0xFF60A5FA), Color(0xFF2563EB))))
            
            // Door
            drawRoundRect(
                color = Color(0xFFEFF6FF),
                topLeft = Offset(w * 0.4f, h * 0.6f),
                size = Size(w * 0.2f, h * 0.3f),
                cornerRadius = CornerRadius(w * 0.05f)
            )
            
            // Windows
            drawRoundRect(
                color = Color(0xFF93C5FD),
                topLeft = Offset(w * 0.25f, h * 0.55f),
                size = Size(w * 0.12f, h * 0.12f),
                cornerRadius = CornerRadius(w * 0.02f)
            )
            drawRoundRect(
                color = Color(0xFF93C5FD),
                topLeft = Offset(w * 0.63f, h * 0.55f),
                size = Size(w * 0.12f, h * 0.12f),
                cornerRadius = CornerRadius(w * 0.02f)
            )
            
            // Roof shadow underneath
            drawPath(roofPath, Color(0xFF7F1D1D).copy(alpha = 0.3f))
            
            // Roof front
            drawPath(roofPath, Brush.linearGradient(listOf(Color(0xFFF87171), Color(0xFFDC2626))))
            
            // Roof highlight/3d rim
            drawPath(roofHighlightPath, Color(0xFFFCA5A5))
        }
    })
}

@Composable
fun Icon3DBook(modifier: Modifier = Modifier) {
INNER_EOF
cat app/src/main/java/com/example/ui/components/UniversoBottomNavBar.kt | sed -n '255,$p' >> app/src/main/java/com/example/ui/components/UniversoBottomNavBar.kt.tmp
mv app/src/main/java/com/example/ui/components/UniversoBottomNavBar.kt.tmp app/src/main/java/com/example/ui/components/UniversoBottomNavBar.kt
