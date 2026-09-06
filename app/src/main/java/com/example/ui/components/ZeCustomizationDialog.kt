package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.*
import com.example.ui.theme.KidStarOrange
import com.example.ui.theme.MintGreen
import com.example.ui.theme.SkyBluePrimary
import com.example.ui.theme.SunshineYellow
import com.example.viewmodel.MainViewModel

enum class CustomizationTab(val title: String, val emoji: String) {
    HAT("Chapéus", "🎩"),
    GLASSES("Óculos", "👓"),
    OUTFIT("Roupas", "👕"),
    AURA("Cores", "🎨")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZeCustomizationDialog(
    initialCustomization: ZeCustomization,
    mainViewModel: MainViewModel,
    onDismiss: () -> Unit,
    onSave: (ZeCustomization) -> Unit
) {
    val context = LocalContext.current
    var currentTab by remember { mutableStateOf(CustomizationTab.HAT) }
    var tempCustomization by remember { mutableStateOf(initialCustomization) }

    var isBouncing by remember { mutableStateOf(false) }
    val bounceScale by animateFloatAsState(
        targetValue = if (isBouncing) 1.12f else 1.0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "bounceScale"
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.92f)
                .testTag("ze_customization_dialog"),
            shape = RoundedCornerShape(28.dp),
            color = Color(0xFF101828),
            border = BorderStroke(2.5.dp, Brush.horizontalGradient(listOf(SunshineYellow, Color(0xFF00E5FF)))),
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF00E5FF).copy(alpha = 0.2f),
                            border = BorderStroke(1.5.dp, Color(0xFF00E5FF))
                        ) {
                            Text("🎩", fontSize = 24.sp, modifier = Modifier.padding(6.dp))
                        }
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text(
                                "Camarim do Zé",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                            Text(
                                "Personaliza o teu amiguinho!",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }

                    IconButton(
                        onClick = {
                            mainViewModel.playClickSound()
                            onDismiss()
                        },
                        modifier = Modifier
                            .size(38.dp)
                            .background(Color.White.copy(alpha = 0.15f), CircleShape)
                    ) {
                        Icon(Icons.Default.Close, "Fechar", tint = Color.White)
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Stage Preview Box (Live Mascot)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(22.dp))
                        .background(
                            Brush.verticalGradient(
                                listOf(Color(0xFF1E293B), Color(0xFF0F172A))
                            )
                        )
                        .border(2.dp, tempCustomization.aura.color.copy(alpha = 0.7f), RoundedCornerShape(22.dp))
                        .clickable {
                            mainViewModel.playClickSound()
                            isBouncing = true
                        },
                    contentAlignment = Alignment.Center
                ) {
                    // Starry sparkles background inside stage
                    Text(
                        "✨ 🌟 💫 ✨",
                        fontSize = 18.sp,
                        color = Color.White.copy(0.4f),
                        modifier = Modifier.align(Alignment.TopCenter).padding(top = 8.dp)
                    )

                    ZeMascotCustomizedView(
                        customization = tempCustomization,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .scale(bounceScale),
                        showAuraGlow = true
                    )

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color.Black.copy(0.6f),
                        border = BorderStroke(1.dp, Color.White.copy(0.4f)),
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(10.dp)
                    ) {
                        Text(
                            "Toca para saltar! 🦘",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = SunshineYellow,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                LaunchedEffect(isBouncing) {
                    if (isBouncing) {
                        kotlinx.coroutines.delay(300)
                        isBouncing = false
                    }
                }

                Spacer(Modifier.height(14.dp))

                // Category Selection Tabs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(Color.White.copy(0.08f))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CustomizationTab.values().forEach { tab ->
                        val isSelected = currentTab == tab
                        Surface(
                            onClick = {
                                mainViewModel.playClickSound()
                                currentTab = tab
                            },
                            shape = RoundedCornerShape(14.dp),
                            color = if (isSelected) SunshineYellow else Color.Transparent,
                            modifier = Modifier.weight(1f)
                        ) {
                            Row(
                                modifier = Modifier.padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(tab.emoji, fontSize = 14.sp)
                                Spacer(Modifier.width(4.dp))
                                Text(
                                    tab.title,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                                    color = if (isSelected) Color.Black else Color.White
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Grid of Options based on Current Tab
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    when (currentTab) {
                        CustomizationTab.HAT -> {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(HatOption.values()) { hat ->
                                    val isSelected = tempCustomization.hat == hat
                                    OptionCard(
                                        title = hat.title,
                                        emoji = hat.emoji,
                                        description = hat.description,
                                        isSelected = isSelected,
                                        onClick = {
                                            mainViewModel.playClickSound()
                                            tempCustomization = tempCustomization.copy(hat = hat)
                                        }
                                    )
                                }
                            }
                        }
                        CustomizationTab.GLASSES -> {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(GlassesOption.values()) { glasses ->
                                    val isSelected = tempCustomization.glasses == glasses
                                    OptionCard(
                                        title = glasses.title,
                                        emoji = glasses.emoji,
                                        description = glasses.description,
                                        isSelected = isSelected,
                                        onClick = {
                                            mainViewModel.playClickSound()
                                            tempCustomization = tempCustomization.copy(glasses = glasses)
                                        }
                                    )
                                }
                            }
                        }
                        CustomizationTab.OUTFIT -> {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(OutfitOption.values()) { outfit ->
                                    val isSelected = tempCustomization.outfit == outfit
                                    OptionCard(
                                        title = outfit.title,
                                        emoji = outfit.emoji,
                                        description = outfit.description,
                                        isSelected = isSelected,
                                        onClick = {
                                            mainViewModel.playClickSound()
                                            tempCustomization = tempCustomization.copy(outfit = outfit)
                                        }
                                    )
                                }
                            }
                        }
                        CustomizationTab.AURA -> {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(AuraColorOption.values()) { aura ->
                                    val isSelected = tempCustomization.aura == aura
                                    AuraColorCard(
                                        aura = aura,
                                        isSelected = isSelected,
                                        onClick = {
                                            mainViewModel.playClickSound()
                                            tempCustomization = tempCustomization.copy(aura = aura)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Action Buttons Bottom Bar (Randomize + Save)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Randomize Button
                    Button(
                        onClick = {
                            mainViewModel.playClickSound()
                            tempCustomization = ZeCustomization(
                                hat = HatOption.values().random(),
                                glasses = GlassesOption.values().random(),
                                outfit = OutfitOption.values().random(),
                                aura = AuraColorOption.values().random()
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF334155)),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .weight(0.4f)
                            .height(50.dp)
                    ) {
                        Icon(Icons.Default.Refresh, null, tint = SunshineYellow)
                        Spacer(Modifier.width(4.dp))
                        Text("Baralhar", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    // Save Button
                    Button(
                        onClick = {
                            mainViewModel.playClickSound()
                            mainViewModel.addStars(5)
                            mainViewModel.speak("Uau! Adoro o meu novo visual! Obrigado, amiguinho! 🌟✨") {}
                            tempCustomization.saveToPrefs(context)
                            onSave(tempCustomization)
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00E5FF)),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .weight(0.6f)
                            .height(50.dp)
                    ) {
                        Icon(Icons.Default.Star, null, tint = Color.Black)
                        Spacer(Modifier.width(6.dp))
                        Text("Guardar Visual ✨", fontSize = 13.sp, fontWeight = FontWeight.Black, color = Color.Black)
                    }
                }
            }
        }
    }
}

@Composable
private fun OptionCard(
    title: String,
    emoji: String,
    description: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) Color(0xFF0288D1) else Color.White.copy(0.07f),
        border = BorderStroke(
            if (isSelected) 2.dp else 1.dp,
            if (isSelected) SunshineYellow else Color.White.copy(0.15f)
        ),
        shadowElevation = if (isSelected) 6.dp else 0.dp
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(emoji, fontSize = 26.sp)
            Spacer(Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1
                )
                Text(
                    description,
                    fontSize = 9.5.sp,
                    color = Color.White.copy(0.7f),
                    maxLines = 1
                )
            }
            if (isSelected) {
                Icon(
                    Icons.Default.Check,
                    null,
                    tint = SunshineYellow,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun AuraColorCard(
    aura: AuraColorOption,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) Color(0xFF1E293B) else Color.White.copy(0.07f),
        border = BorderStroke(
            if (isSelected) 2.dp else 1.dp,
            if (isSelected) aura.color else Color.White.copy(0.15f)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(aura.color)
                    .border(2.dp, Color.White, CircleShape)
            )
            Spacer(Modifier.width(10.dp))
            Text(
                aura.title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
            if (isSelected) {
                Icon(
                    Icons.Default.Check,
                    null,
                    tint = aura.color,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
