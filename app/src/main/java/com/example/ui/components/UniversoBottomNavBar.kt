package com.example.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.R
import com.example.ui.navigation.Screen
import com.example.ui.navigation.mainNavScreens

/**
 * Immutable state representation for an individual bottom navigation tab.
 * Ensures Jetpack Compose can skip recompositions when tab properties don't change.
 */
@Immutable
data class NavTabItemState(
    val screen: Screen,
    val isSelected: Boolean,
    val isChatTab: Boolean,
    val label: String,
    val bgColor: Color,
    val activeBgColor: Color,
    val darkShadowColor: Color,
    val textColor: Color,
    val primaryColor: Color,
    val icon: ImageVector
)

/**
 * State holder for bottom navigation bar, isolating color, icon and selection logic.
 */
@Stable
class UniversoNavBarState(
    val tabs: List<NavTabItemState>
)

/**
 * Creates and remembers a [UniversoNavBarState] based on screens and active route.
 */
@Composable
fun rememberUniversoNavBarState(
    screens: List<Screen> = mainNavScreens,
    activeRoute: String
): UniversoNavBarState {
    return remember(screens, activeRoute) {
        val tabStates = screens.map { screen ->
            val isSelected = activeRoute == screen.route
            val isChatTab = screen == Screen.Chat || screen.route == "chat"
            val style = resolveTabTheme(screen)

            NavTabItemState(
                screen = screen,
                isSelected = isSelected,
                isChatTab = isChatTab,
                label = screen.title,
                bgColor = style.bgColor,
                activeBgColor = style.activeBgColor,
                darkShadowColor = style.darkShadowColor,
                textColor = style.textColor,
                primaryColor = style.primaryColor,
                icon = style.icon
            )
        }
        UniversoNavBarState(tabs = tabStates)
    }
}

private data class TabTheme(
    val bgColor: Color,
    val activeBgColor: Color,
    val darkShadowColor: Color,
    val textColor: Color,
    val primaryColor: Color,
    val icon: ImageVector
)

private fun resolveTabTheme(screen: Screen): TabTheme {
    return when (screen.route) {
        Screen.Home.route, "inicio" -> TabTheme(
            bgColor = Color(0xFFFF9800),
            activeBgColor = Color(0xFFFFB74D),
            darkShadowColor = Color(0xFFB26A00),
            textColor = Color.White,
            primaryColor = Color(0xFFE65100),
            icon = Icons.Default.Home
        )
        Screen.Educar.route, "educar", "aprender" -> TabTheme(
            bgColor = Color(0xFF4CAF50),
            activeBgColor = Color(0xFF66BB6A),
            darkShadowColor = Color(0xFF2E7D32),
            textColor = Color.White,
            primaryColor = Color(0xFF1B5E20),
            icon = Icons.Default.School
        )
        Screen.Chat.route, "chat" -> TabTheme(
            bgColor = Color(0xFFFFEB3B),
            activeBgColor = Color(0xFFFFF176),
            darkShadowColor = Color(0xFFF57F17),
            textColor = Color(0xFF3E2723),
            primaryColor = Color(0xFFE65100),
            icon = Icons.Default.Home
        )
        Screen.Media.route, "videos" -> TabTheme(
            bgColor = Color(0xFFF44336),
            activeBgColor = Color(0xFFEF5350),
            darkShadowColor = Color(0xFFB71C1C),
            textColor = Color.White,
            primaryColor = Color(0xFFC62828),
            icon = Icons.Default.PlayArrow
        )
        Screen.More.route, "mais" -> TabTheme(
            bgColor = Color(0xFF03A9F4),
            activeBgColor = Color(0xFF29B6F6),
            darkShadowColor = Color(0xFF01579B),
            textColor = Color.White,
            primaryColor = Color(0xFF0277BD),
            icon = Icons.Default.MoreHoriz
        )
        else -> TabTheme(
            bgColor = Color(0xFF03A9F4),
            activeBgColor = Color(0xFF29B6F6),
            darkShadowColor = Color(0xFF01579B),
            textColor = Color.White,
            primaryColor = Color(0xFF0277BD),
            icon = screen.icon
        )
    }
}

@Composable
fun UniversoBottomNavBar(
    screens: List<Screen> = mainNavScreens,
    activeRoute: String,
    onScreenSelected: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    val navBarState = rememberUniversoNavBarState(screens = screens, activeRoute = activeRoute)
    UniversoBottomNavBarContent(
        state = navBarState,
        onScreenSelected = onScreenSelected,
        modifier = modifier
    )
}

@Composable
fun UniversoBottomNavBarContent(
    state: UniversoNavBarState,
    onScreenSelected: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp)
            .padding(horizontal = 8.dp, vertical = 2.dp)
            .testTag("universo_bottom_nav_bar")
    ) {
        // --- 3D DEEP SHADOW & AMBIENT GLOW LAYER ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .align(Alignment.BottomCenter)
                .offset(y = 4.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.25f),
                    shape = RoundedCornerShape(26.dp)
                )
        )

        // --- 3D BASE CHASSIS CONTAINER ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(66.dp)
                .align(Alignment.BottomCenter)
                .shadow(
                    elevation = 14.dp,
                    shape = RoundedCornerShape(24.dp),
                    clip = false,
                    ambientColor = Color(0x66000000),
                    spotColor = Color(0x99000000)
                )
                .clip(RoundedCornerShape(24.dp))
                .border(
                    BorderStroke(
                        width = 2.5.dp,
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.White.copy(alpha = 0.95f),
                                Color.White.copy(alpha = 0.65f),
                                Color(0x33000000),
                                Color(0x77000000)
                            )
                        )
                    ),
                    shape = RoundedCornerShape(24.dp)
                )
        ) {
            // Segmented 3D background capsules
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                state.tabs.forEachIndexed { index, tab ->
                    val gradient = Brush.verticalGradient(
                        colors = if (tab.isSelected) {
                            listOf(
                                tab.activeBgColor.copy(alpha = 0.98f),
                                tab.activeBgColor,
                                tab.bgColor,
                                tab.darkShadowColor
                            )
                        } else {
                            listOf(
                                tab.bgColor.copy(alpha = 0.92f),
                                tab.bgColor,
                                tab.darkShadowColor.copy(alpha = 0.85f)
                            )
                        }
                    )

                    Box(
                        modifier = Modifier
                            .weight(if (tab.isChatTab) 1.25f else 1f)
                            .fillMaxHeight()
                            .background(gradient)
                    ) {
                        // 3D Top Bevel Specular Reflection
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .align(Alignment.TopCenter)
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            Color.White.copy(alpha = if (tab.isSelected) 0.80f else 0.45f),
                                            Color.White.copy(alpha = 0.0f)
                                        )
                                    )
                                )
                        )

                        // 3D Glass / Glossy Upper Half Reflection
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.48f)
                                .align(Alignment.TopCenter)
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            Color.White.copy(alpha = 0.28f),
                                            Color.White.copy(alpha = 0.04f)
                                        )
                                    )
                                )
                        )

                        // 3D Bottom Edge Inset Shadow
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(5.dp)
                                .align(Alignment.BottomCenter)
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.40f)
                                        )
                                    )
                                )
                        )

                        // 3D Segment Separator Line (except last item)
                        if (index < state.tabs.size - 1) {
                            Box(
                                modifier = Modifier
                                    .width(1.5.dp)
                                    .fillMaxHeight()
                                    .align(Alignment.CenterEnd)
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(
                                                Color.White.copy(alpha = 0.4f),
                                                Color.Black.copy(alpha = 0.25f)
                                            )
                                        )
                                    )
                            )
                        }
                    }
                }
            }
        }

        // --- FOREGROUND LAYER WITH PROMINENT 3D ANIMATED ICONS AND LABELS ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            state.tabs.forEach { tab ->
                if (tab.isChatTab) {
                    ChatTabItem(
                        tab = tab,
                        onTabSelected = { onScreenSelected(Screen.Chat) }
                    )
                } else {
                    RegularTabItem(
                        tab = tab,
                        onTabSelected = { onScreenSelected(tab.screen) }
                    )
                }
            }
        }
    }
}

@Composable
private fun RowScope.ChatTabItem(
    tab: NavTabItemState,
    onTabSelected: () -> Unit
) {
    // Highly expressive 3D bounce scale & elevation for ZÉ AI
    val chatScale by animateFloatAsState(
        targetValue = if (tab.isSelected) 1.35f else 1.02f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "ze_ai_scale"
    )
    val floatOffset by animateDpAsState(
        targetValue = if (tab.isSelected) (-14).dp else (-10).dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "ze_ai_offset"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .weight(1.25f)
            .height(94.dp)
            .padding(bottom = 2.dp)
            .offset(y = floatOffset)
            .scale(chatScale)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onTabSelected
            )
            .testTag("nav_tab_chat")
    ) {
        // 3D Raised Mascot Medallion
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(if (tab.isSelected) 62.dp else 52.dp)
                .shadow(
                    elevation = if (tab.isSelected) 12.dp else 6.dp,
                    shape = CircleShape,
                    ambientColor = Color(0x66000000),
                    spotColor = Color(0xAA000000)
                )
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFFFF9C4),
                            Color(0xFFFFD54F),
                            Color(0xFFFF9800),
                            Color(0xFFE65100)
                        ),
                        center = Offset(20f, 20f),
                        radius = 60f
                    )
                )
                .border(
                    BorderStroke(
                        width = if (tab.isSelected) 3.5.dp else 2.5.dp,
                        brush = Brush.verticalGradient(
                            if (tab.isSelected) {
                                listOf(Color(0xFFFFFDE7), Color(0xFFFFD54F), Color(0xFFFF8F00))
                            } else {
                                listOf(Color.White, Color.White.copy(alpha = 0.85f), Color(0xFFFFA000))
                            }
                        )
                    ),
                    shape = CircleShape
                )
        ) {
            Image(
                painter = rememberAsyncImagePainter("https://i.imgur.com/O2uwUho.png"),
                contentDescription = "ZÉ AI Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // 3D Tactile Badge
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = if (tab.isSelected) Color(0xFFE65100) else Color(0xFFF57F17),
            border = BorderStroke(
                1.5.dp,
                Brush.verticalGradient(
                    listOf(Color.White, Color.White.copy(alpha = 0.8f))
                )
            ),
            shadowElevation = if (tab.isSelected) 6.dp else 3.dp,
            modifier = Modifier.offset(y = (-4).dp)
        ) {
            Text(
                text = "ZÉ AI",
                color = Color.White,
                fontWeight = FontWeight.Black,
                fontSize = if (tab.isSelected) 11.5.sp else 10.5.sp,
                maxLines = 1,
                softWrap = false,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.75f),
                        offset = Offset(0f, 1f),
                        blurRadius = 1f
                    )
                ),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
            )
        }
    }
}

@Composable
private fun RowScope.RegularTabItem(
    tab: NavTabItemState,
    onTabSelected: () -> Unit
) {
    // Significantly larger scale when selected (pops up noticeably bigger)
    val iconScale by animateFloatAsState(
        targetValue = if (tab.isSelected) 1.45f else 1.02f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "icon_scale"
    )
    val offsetY by animateFloatAsState(
        targetValue = if (tab.isSelected) (-12f) else (-6f),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "icon_offset"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .weight(1f)
            .height(94.dp)
            .padding(bottom = 6.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onTabSelected
            )
            .testTag("nav_tab_${tab.screen.route}")
    ) {
        // 3D Raised Icon Sphere / Medallion
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .offset(y = offsetY.dp)
                .scale(iconScale)
                .size(if (tab.isSelected) 46.dp else 34.dp)
                .shadow(
                    elevation = if (tab.isSelected) 10.dp else 3.dp,
                    shape = CircleShape,
                    ambientColor = Color(0x66000000),
                    spotColor = Color(0x99000000)
                )
                .background(
                    Brush.radialGradient(
                        colors = if (tab.isSelected) {
                            listOf(
                                Color.White,
                                Color(0xFFFFFDE7),
                                Color(0xFFFFECB3),
                                Color(0xFFFFD54F)
                            )
                        } else {
                            listOf(
                                Color.White,
                                Color(0xFFF5F5F5),
                                Color(0xFFE0E0E0)
                            )
                        },
                        center = Offset(12f, 12f),
                        radius = 35f
                    ),
                    shape = CircleShape
                )
                .border(
                    width = if (tab.isSelected) 2.5.dp else 1.2.dp,
                    brush = Brush.verticalGradient(
                        if (tab.isSelected) {
                            listOf(Color.White, Color(0xFFFFD54F), Color(0xFFFF8F00))
                        } else {
                            listOf(Color.White, Color.White.copy(alpha = 0.85f), Color(0x44000000))
                        }
                    ),
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = tab.icon,
                contentDescription = tab.label,
                tint = tab.primaryColor,
                modifier = Modifier.size(if (tab.isSelected) 25.dp else 18.dp)
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        // Label with 3D text styling
        Text(
            text = tab.label,
            color = tab.textColor,
            fontWeight = if (tab.isSelected) FontWeight.Black else FontWeight.Bold,
            fontSize = if (tab.isSelected) 11.sp else 9.5.sp,
            maxLines = 1,
            softWrap = false,
            textAlign = TextAlign.Center,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Black.copy(alpha = if (tab.isSelected) 0.85f else 0.50f),
                    offset = Offset(0f, 1f),
                    blurRadius = 1f
                )
            ),
            modifier = Modifier.padding(top = 1.dp)
        )
    }
}

