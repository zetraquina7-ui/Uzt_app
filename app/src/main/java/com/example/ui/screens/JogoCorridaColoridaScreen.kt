package com.example.ui.screens

import com.example.ui.theme.PreviewAppTheme

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.MascotEmotion
import com.example.ui.components.ZeTraquinaMascot
import com.example.ui.theme.MintGreen
import com.example.ui.theme.SkyBluePrimary
import com.example.ui.theme.SunshineYellow
import com.example.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlin.random.Random

data class TargetColorInfo(
    val name: String,
    val color: Color,
    val emoji: String
)

data class FallingColorItem(
    val id: Long,
    val lane: Int, // 0: Left, 1: Center, 2: Right
    val colorInfo: TargetColorInfo,
    var posY: Float // 0.0 (top) to 1.0 (bottom)
)

val availableTargetColors = listOf(
    TargetColorInfo("Vermelho", Color(0xFFE53935), "🔴"),
    TargetColorInfo("Azul", Color(0xFF1E88E5), "🔵"),
    TargetColorInfo("Amarelo", Color(0xFFFFB300), "🟡"),
    TargetColorInfo("Verde", Color(0xFF43A047), "🟢"),
    TargetColorInfo("Roxo", Color(0xFF8E24AA), "🟣"),
    TargetColorInfo("Laranja", Color(0xFFFB8C00), "🟠")
)

@Composable
fun JogoCorridaColoridaScreen(
    mainViewModel: MainViewModel? = null,
    onBack: () -> Unit
) {
    val isPreview = LocalInspectionMode.current

    var score by remember { mutableIntStateOf(0) }
    var lives by remember { mutableIntStateOf(3) }
    var currentLane by remember { mutableIntStateOf(1) } // 0=Left, 1=Center, 2=Right
    var targetColor by remember { mutableStateOf(availableTargetColors[0]) }
    var isPlaying by remember { mutableStateOf(false) }
    var isGameOver by remember { mutableStateOf(false) }
    var roadmapOffset by remember { mutableFloatStateOf(0f) }

    val fallingItems = remember { mutableStateListOf<FallingColorItem>() }
    var itemIdCounter by remember { mutableLongStateOf(0L) }

    // Start or Restart game logic
    fun startGame() {
        score = 0
        lives = 3
        currentLane = 1
        targetColor = availableTargetColors.random()
        fallingItems.clear()
        isPlaying = true
        isGameOver = false
    }

    // Function to change target color dynamically
    fun switchTargetColor() {
        val nextColors = availableTargetColors.filter { it.name != targetColor.name }
        targetColor = nextColors.random()
    }

    // Main Game Loop (Updates roadmap, spawns and moves items down)
    LaunchedEffect(isPlaying) {
        if (!isPlaying) return@LaunchedEffect

        var spawnTimer = 0
        while (isPlaying && !isGameOver) {
            delay(30) // ~33 FPS tick

            // Move road animated lines
            roadmapOffset = (roadmapOffset + 0.03f) % 1.0f

            // Spawn new item every ~1.2s (40 ticks)
            spawnTimer++
            if (spawnTimer >= 38) {
                spawnTimer = 0
                val randomLane = Random.nextInt(3)
                // 50% chance to spawn target color item
                val itemColor = if (Random.nextBoolean()) {
                    targetColor
                } else {
                    availableTargetColors.random()
                }
                fallingItems.add(
                    FallingColorItem(
                        id = itemIdCounter++,
                        lane = randomLane,
                        colorInfo = itemColor,
                        posY = 0.0f
                    )
                )
            }

            // Move falling items down
            val iterator = fallingItems.iterator()
            val carYPosition = 0.82f // Player's car is situated around Y=82%
            val collisionThreshold = 0.08f

            while (iterator.hasNext()) {
                val item = iterator.next()
                item.posY += 0.022f // Fall speed

                // Check collision with player car
                if (item.posY in (carYPosition - collisionThreshold)..(carYPosition + collisionThreshold) &&
                    item.lane == currentLane
                ) {
                    if (item.colorInfo.name == targetColor.name) {
                        // Correct color collected!
                        score += 10
                        if (!isPreview) {
                            mainViewModel?.addStars(1)
                            mainViewModel?.playSound(R.raw.ze_traquina_e_fixe)
                        }
                        // Change color target every 40 points
                        if (score > 0 && score % 40 == 0) {
                            switchTargetColor()
                        }
                    } else {
                        // Wrong color collected!
                        score = (score - 5).coerceAtLeast(0)
                        lives--
                        if (lives <= 0) {
                            isPlaying = false
                            isGameOver = true
                        } else {
                        }
                    }
                    iterator.remove()
                } else if (item.posY > 1.05f) {
                    // Item fell off screen
                    iterator.remove()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- Header Zone (Top Bar with Back & Score) ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(containerColor = SunshineYellow),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar",
                    tint = Color.Black,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Voltar", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }

            // Score Display & Visible Hint Button
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = SunshineYellow),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                    modifier = Modifier.padding(end = 6.dp)
                ) {
                    Text("💡 Dica", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black)
                }

                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color.Black.copy(alpha = 0.5f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFD600),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Pontos: $score",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "❤️".repeat(lives),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // --- Target Color Display Banner (tvTargetColor) ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = targetColor.color),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = targetColor.emoji, fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Apanha a Cor: ${targetColor.name.uppercase()}",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                        Text(
                            text = "Guia o carro para a faixa certa!",
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }

                if (!isPlaying && !isGameOver) {
                    Button(
                        onClick = { startGame() },
                        colors = ButtonDefaults.buttonColors(containerColor = SunshineYellow),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Iniciar 🏎️", fontWeight = FontWeight.ExtraBold, color = Color.Black)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // --- MAIN ROAD GAME CONTAINER (3 Vertical Lanes) ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 4.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF262626)), // Dark asphalt background
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val laneWidth = maxWidth / 3

                // Asphalt road texture and dashed vertical lane lines
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height

                    // Draw 2 dashed white lane divider lines
                    val dashPathEffect = PathEffect.dashPathEffect(floatArrayOf(30f, 25f), roadmapOffset * 55f)
                    val line1X = w / 3f
                    val line2X = (w / 3f) * 2f

                    drawLine(
                        color = Color.White.copy(alpha = 0.7f),
                        start = Offset(line1X, 0f),
                        end = Offset(line1X, h),
                        strokeWidth = 6f,
                        pathEffect = dashPathEffect
                    )

                    drawLine(
                        color = Color.White.copy(alpha = 0.7f),
                        start = Offset(line2X, 0f),
                        end = Offset(line2X, h),
                        strokeWidth = 6f,
                        pathEffect = dashPathEffect
                    )

                    // Side red/white warning curb borders
                    drawRect(
                        color = Color(0xFFE53935),
                        topLeft = Offset(0f, 0f),
                        size = Size(10f, h)
                    )
                    drawRect(
                        color = Color(0xFFE53935),
                        topLeft = Offset(w - 10f, 0f),
                        size = Size(10f, h)
                    )
                }

                // Render Falling Color Items
                fallingItems.forEach { item ->
                    val itemX = laneWidth * item.lane + (laneWidth / 2) - 24.dp
                    val itemY = maxHeight * item.posY

                    Box(
                        modifier = Modifier
                            .offset(x = itemX, y = itemY)
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(item.colorInfo.color)
                            .border(3.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = item.colorInfo.emoji, fontSize = 22.sp)
                    }
                }

                // Player Car (ivPlayerCar) positioned in active lane
                val carX = laneWidth * currentLane + (laneWidth / 2) - 36.dp
                val carY = maxHeight * 0.82f

                Box(
                    modifier = Modifier
                        .offset(x = carX, y = carY)
                        .size(width = 72.dp, height = 72.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(SunshineYellow)
                        .border(3.dp, Color.White, RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "🏎️", fontSize = 32.sp)
                        Text(
                            text = "Zé",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.Black
                        )
                    }
                }

                // Overlay Controls (Left half & Right half invisible click zones + buttons)
                Row(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable {
                                if (isPlaying && currentLane > 0) currentLane--
                            }
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable {
                                if (isPlaying && currentLane < 2) currentLane++
                            }
                    )
                }

                // Start Overlay prompt if not started or Game Over
                if (!isPlaying) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.75f))
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            ZeTraquinaMascot(
                                size = 70.dp,
                                showSpeechBubble = false,
                                emotion = if (isGameOver) MascotEmotion.THINKING else MascotEmotion.HAPPY
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            if (isGameOver) {
                                Text(
                                    text = "Fim de Corrida! 🏁",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Fizeste $score pontos na Corrida Colorida!",
                                    fontSize = 14.sp,
                                    color = SunshineYellow,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            } else {
                                Text(
                                    text = "A Corrida Colorida 🏎️🎨",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Muda de faixa e apanha os itens da cor pedida!",
                                    fontSize = 13.sp,
                                    color = Color.LightGray,
                                    textAlign = TextAlign.Center
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Button(
                                onClick = { startGame() },
                                colors = ButtonDefaults.buttonColors(containerColor = SunshineYellow),
                                shape = RoundedCornerShape(16.dp),
                                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                            ) {
                                Icon(
                                    imageVector = if (isGameOver) Icons.Default.Refresh else Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (isGameOver && score >= 30) "🚀 PRÓXIMO NÍVEL (MAIS DIFÍCIL!)" else if (isGameOver) "JOGAR DE NOVO 🔄" else "INICIAR CORRIDA 🏎️",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // --- Bottom Touch Directional Buttons (btnLeft & btnRight) ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    if (isPlaying && currentLane > 0) currentLane--
                },
                enabled = isPlaying && currentLane > 0,
                modifier = Modifier
                    .weight(1f)
                    .height(54.dp)
                    .padding(end = 6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("⬅️ FAIXA ESQUERDA", fontSize = 14.sp, fontWeight = FontWeight.Black)
            }

            Button(
                onClick = {
                    if (isPlaying && currentLane < 2) currentLane++
                },
                enabled = isPlaying && currentLane < 2,
                modifier = Modifier
                    .weight(1f)
                    .height(54.dp)
                    .padding(start = 6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("FAIXA DIREITA ➡️", fontSize = 14.sp, fontWeight = FontWeight.Black)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JogoCorridaColoridaPreview(
    @androidx.compose.ui.tooling.preview.PreviewParameter(com.example.ui.util.GamePreviewParameterProvider::class) mockData: com.example.ui.util.GamePreviewMockData
) {
    PreviewAppTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF1F5F9))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Preview - ${mockData.title} | Status: ${mockData.statusMessage}",
                    fontSize = 12.sp,
                    color = Color(0xFF64748B),
                    fontWeight = FontWeight.Bold
                )
            }
            JogoCorridaColoridaScreen(mainViewModel = null, onBack = {})
        }
    }
}
