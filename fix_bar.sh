cat << 'INNER_EOF' > app/src/main/java/com/example/ui/components/UniversoBottomNavBar.kt
package com.example.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.R
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
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .testTag("bottom_nav_bar"),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Pill Glass Background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
                .shadow(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(percent = 50),
                    spotColor = Color(0xFF00FFCC).copy(alpha = 0.4f),
                    ambientColor = Color(0xFF00FFCC).copy(alpha = 0.2f)
                )
                .clip(RoundedCornerShape(percent = 50))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF001A24).copy(alpha = 0.6f),
                            Color(0xFF06222F).copy(alpha = 0.75f)
                        )
                    )
                )
                .border(
                    width = 2.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFCCFFFF).copy(alpha = 0.9f),
                            Color(0xFF4AA0A9).copy(alpha = 0.4f),
                            Color(0xFF86E0DF).copy(alpha = 0.9f)
                        )
                    ),
                    shape = RoundedCornerShape(percent = 50)
                )
                .drawBehind {
                    // Top highlight inner reflection
                    val highlightWidth = (size.width - 48f).coerceAtLeast(0f)
                    drawRoundRect(
                        color = Color.White.copy(alpha = 0.2f),
                        topLeft = Offset(24f, 4f),
                        size = Size(highlightWidth, 12f),
                        cornerRadius = CornerRadius(6f, 6f)
                    )
                }
        )

        // Icons and Center Avatar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(76.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left items (first 3)
            val leftScreens = screens.take(3)
            // Right items (last 3)
            val rightScreens = screens.drop(3).take(3)

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.weight(1f)
            ) {
                leftScreens.forEach { screen ->
                    DockItem(
                        screen = screen,
                        isSelected = currentScreen.route == screen.route,
                        onClick = { onScreenSelected(screen) }
                    )
                }
            }

            // Center Avatar
            Box(
                modifier = Modifier
                    .size(92.dp) // Make it quite large to pop out
                    .offset(y = (-10).dp) // Pop out slightly from the top
                    .shadow(24.dp, CircleShape, spotColor = Color(0xFF00FFCC).copy(alpha = 0.6f))
                    .clip(CircleShape)
                    .border(
                        width = 3.dp,
                        brush = Brush.linearGradient(
                            listOf(Color(0xFFE0FFFF), Color(0xFF339598), Color(0xFF86E0DF))
                        ),
                        shape = CircleShape
                    )
                    .background(Color(0xFF003344)) // Base color in case image is small
                    .clickable { /* Opcional: Ação para perfil/avatar */ },
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = R.drawable.img_app_icon_1785061192574,
                    contentDescription = "Avatar do Zé Traquina",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().padding(2.dp).clip(CircleShape)
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.weight(1f)
            ) {
                rightScreens.forEach { screen ->
                    DockItem(
                        screen = screen,
                        isSelected = currentScreen.route == screen.route,
                        onClick = { onScreenSelected(screen) }
                    )
                }
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
        targetValue = if (isPressed) 0.9f else if (isSelected) 1.15f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "nav_scale"
    )

    // Select specific icons for exact match with the image if possible, otherwise use screen.icon
    val iconVector: ImageVector = when (screen) {
        Screen.Home -> Icons.Default.Home
        Screen.Learn -> Icons.Default.School
        Screen.Games -> Icons.Default.SportsEsports
        Screen.Media -> Icons.Default.OndemandVideo
        Screen.Chat -> Icons.Default.Face
        Screen.More -> Icons.Default.Apps // 3x3 grid representation
        else -> screen.icon
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .testTag("nav_item_${screen.route}")
            .padding(2.dp)
            .scale(scale)
    ) {
        Icon(
            imageVector = iconVector,
            contentDescription = screen.title,
            tint = if (isSelected) Color.White else Color.White.copy(alpha = 0.8f),
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.height(2.dp))
        
        Text(
            text = screen.title,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold,
            color = if (isSelected) Color.White else Color.White.copy(alpha = 0.8f),
            maxLines = 1,
            softWrap = false
        )
    }
}
INNER_EOF
