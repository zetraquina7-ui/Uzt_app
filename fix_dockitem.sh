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
                .height(96.dp) // Aumentado um pouco para caber melhor os retângulos
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(24.dp), // Quadrada com cantos arredondados
                    spotColor = Color(0xFF003366).copy(alpha = 0.2f),
                    ambientColor = Color(0xFF003366).copy(alpha = 0.1f)
                )
                .clip(RoundedCornerShape(24.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.45f),
                            Color.White.copy(alpha = 0.25f),
                            Color.White.copy(alpha = 0.35f)
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
                    shape = RoundedCornerShape(24.dp)
                )
        )

        // Icons Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
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
            .padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(56.dp) // Rectângulo de vidro por baixo do ícone
                .scale(scale)
                .shadow(
                    elevation = if (isSelected) 8.dp else 4.dp,
                    shape = RoundedCornerShape(16.dp),
                    spotColor = Color.Black.copy(alpha = 0.2f)
                )
                .clip(RoundedCornerShape(16.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = if (isSelected) {
                            listOf(
                                Color.White.copy(alpha = 0.7f),
                                Color.White.copy(alpha = 0.4f)
                            )
                        } else {
                            listOf(
                                Color.White.copy(alpha = 0.4f),
                                Color.White.copy(alpha = 0.1f)
                            )
                        }
                    )
                )
                .border(
                    width = 1.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.9f),
                            Color.White.copy(alpha = 0.2f)
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(8.dp) // Espaço interno do ícone dentro do vidro
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
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = screen.title,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold,
            color = if (isSelected) Color(0xFF1E293B) else Color(0xFF475569),
            modifier = Modifier.alpha(if (isSelected) 1f else 0.8f)
        )
    }
}
INNER_EOF
cat app/src/main/java/com/example/ui/components/UniversoBottomNavBar.kt | sed -n '194,$p' >> app/src/main/java/com/example/ui/components/UniversoBottomNavBar.kt.tmp
mv app/src/main/java/com/example/ui/components/UniversoBottomNavBar.kt.tmp app/src/main/java/com/example/ui/components/UniversoBottomNavBar.kt
