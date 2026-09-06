cat << 'INNER_EOF' > app/src/main/java/com/example/ui/components/UniversoBottomNavBar.kt
package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.OndemandVideo
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.SportsEsports
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
            .shadow(elevation = 8.dp, spotColor = Color.Black.copy(alpha = 0.1f))
            .background(Color.White)
            .navigationBarsPadding(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
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

    val itemColor = remember(screen) {
        when (screen) {
            Screen.Home -> Color(0xFF1976D2) // Azul
            Screen.Learn -> Color(0xFF388E3C) // Verde escuro
            Screen.Games -> Color(0xFF4CAF50) // Verde
            Screen.Media -> Color(0xFFF57C00) // Laranja
            Screen.Chat -> Color(0xFFE91E63) // Rosa
            Screen.More -> Color(0xFF757575) // Cinza
            else -> Color(0xFF1976D2)
        }
    }

    val iconVector = if (isSelected) {
        when (screen) {
            Screen.Home -> Icons.Filled.Home
            Screen.Learn -> Icons.Filled.School
            Screen.Games -> Icons.Filled.SportsEsports
            Screen.Media -> Icons.Filled.OndemandVideo
            Screen.Chat -> Icons.Filled.Face
            Screen.More -> Icons.Filled.MoreHoriz
            else -> Icons.Filled.Home
        }
    } else {
        when (screen) {
            Screen.Home -> Icons.Outlined.Home
            Screen.Learn -> Icons.Outlined.School
            Screen.Games -> Icons.Outlined.SportsEsports
            Screen.Media -> Icons.Outlined.OndemandVideo
            Screen.Chat -> Icons.Outlined.Face
            Screen.More -> Icons.Outlined.MoreHoriz
            else -> Icons.Outlined.Home
        }
    }

    val pillColor by animateColorAsState(
        targetValue = if (isSelected) itemColor.copy(alpha = 0.15f) else Color.Transparent,
        label = "pill_color"
    )

    val contentColor by animateColorAsState(
        targetValue = if (isSelected) itemColor else Color.DarkGray,
        label = "content_color"
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .testTag("nav_item_${screen.route}"),
        contentAlignment = Alignment.Center
    ) {
        // O utilizador pediu uma pill por trás do ícone + texto:
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(pillColor)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Icon(
                imageVector = iconVector,
                contentDescription = screen.title,
                tint = contentColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = screen.title,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = contentColor
            )
        }
    }
}
INNER_EOF
