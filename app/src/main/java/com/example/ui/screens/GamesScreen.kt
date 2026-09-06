package com.example.ui.screens

import com.zetraquina.app.games.DetetivePalavrasScreen
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.border
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.LocalContext
import com.example.ui.components.SafeAsyncImage
import com.example.util.AppImageLoader
import com.example.util.PreviewConfig
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Brush
import com.example.R
import android.content.Context
import android.graphics.Bitmap
import android.print.PrintAttributes
import android.print.PrintManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.toArgb
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Save
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import com.example.audio.AudioSynthesizer
import androidx.compose.material.icons.filled.Star
import com.example.data.UserProgress
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import com.example.ui.theme.PreviewAppTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.ui.components.FundoApp

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.MascotEmotion
import com.example.ui.components.ZeTraquinaMascot
import com.example.ui.components.MascotFaceCircle
import com.example.ui.components.CustomAmiguinhoMascot
import com.example.ui.theme.KidStarOrange
import com.example.ui.theme.MintGreen
import com.example.ui.components.ScreenHeader
import com.example.ui.theme.SkyBluePrimary
import com.example.ui.theme.SunshineYellow
import com.example.viewmodel.DrawingStroke
import com.example.viewmodel.GamesViewModel
import com.example.viewmodel.MainViewModel

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val emoji: String,
    val explanation: String
)

data class BalloonItem(
    val id: Int,
    val color: Color,
    val label: String,
    val emoji: String,
    var isPopped: Boolean = false
)

data class PianoKeyItem(
    val note: String,
    val label: String,
    val emoji: String,
    val color: Color,
    val speechText: String,
    val frequencyHz: Double = 261.63
)

class GameCardPreviewParameterProvider : PreviewParameterProvider<GameLibraryItem> {
    override val values: Sequence<GameLibraryItem> = sequenceOf(
        GameLibraryItem(
            id = "memory",
            title = "Jogo da Memória",
            description = "Encontra os pares de cartas dos animais!",
            category = "Memória",
            emoji = "🦁",
            accentColor = SkyBluePrimary,
            starReward = "⭐ +5",
            difficulty = "Fácil",
            targetTab = 1
        ),
        GameLibraryItem(
            id = "magic_piano",
            title = "Piano Mágico",
            description = "Toca notas musicais e melodias divertidas!",
            category = "Música",
            emoji = "🎹",
            accentColor = MintGreen,
            starReward = "⭐ +5",
            difficulty = "Livre",
            targetTab = 6
        )
    )
}

class QuizQuestionPreviewParameterProvider : PreviewParameterProvider<QuizQuestion> {
    override val values: Sequence<QuizQuestion> = sequenceOf(
        QuizQuestion(
            question = "Qual é o som que o cão faz?",
            options = listOf("Miau", "Au Au", "Piu Piu", "Muuu"),
            correctIndex = 1,
            emoji = "🐶",
            explanation = "O cão faz Au Au!"
        )
    )
}

@Composable
fun GamesScreen(
    key: String = "",
    mainViewModel: MainViewModel? = null,
    gamesViewModel: GamesViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    modifier: Modifier = Modifier,
    showHeader: Boolean = true,
    selectedYear: Int? = null,
    onGameStateChanged: ((Boolean) -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .testTag("games_screen")
    ) {
            var selectedTab by remember { mutableStateOf(0) }

            LaunchedEffect(selectedTab) {
                onGameStateChanged?.invoke(selectedTab > 0)
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            ) {
                if (selectedTab in 1..6) {
                    // --- Header when playing inline games (Memory, Drawing, Star Counting, Quiz, Balloons, Piano) ---
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White.copy(alpha = 0.85f))
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Surface(
                            onClick = { selectedTab = 0 },
                            shape = RoundedCornerShape(12.dp),
                            color = SunshineYellow,
                            shadowElevation = 2.dp,
                            modifier = Modifier.testTag("btn_back_to_game_library")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Voltar à Biblioteca de Jogos",
                                    tint = Color.Black,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Biblioteca de Jogos 📚",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            val userProgressState = mainViewModel?.userProgress?.collectAsState()
                            val soundEnabled = userProgressState?.value?.soundEnabled ?: true
                            Surface(
                                onClick = { mainViewModel?.toggleSound() },
                                shape = CircleShape,
                                color = if (soundEnabled) Color(0xFFE0E7FF) else Color(0xFFFFEBEE),
                                border = BorderStroke(1.2.dp, if (soundEnabled) Color(0xFF6366F1) else Color(0xFFEF4444)),
                                modifier = Modifier
                                    .size(36.dp)
                                    .testTag("btn_toggle_voice_game_header")
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = if (soundEnabled) Icons.Default.VolumeUp else Icons.Default.VolumeOff,
                                        contentDescription = if (soundEnabled) "Desativar Voz do Zé" else "Ativar Voz do Zé",
                                        tint = if (soundEnabled) Color(0xFF4F46E5) else Color(0xFFDC2626),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }

                            Text(
                                text = "A jogar... 🎮",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF0F172A)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .background(if (selectedTab > 0) Color.White else Color.Transparent)
                ) {
                    AnimatedContent(
                        targetState = selectedTab,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)) +
                                    scaleIn(initialScale = 0.95f, animationSpec = tween(300)) togetherWith
                                    fadeOut(animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing))
                        },
                        label = "GameTabTransition"
                    ) { targetTab ->
                        when (targetTab) {
                            0 -> GameLibraryView(
                                mainViewModel = mainViewModel, 
                                onSelectGame = { gameTab -> selectedTab = gameTab }, 
                                showHeader = showHeader,
                                selectedYear = selectedYear
                            )
                            1 -> MemoryGameView(mainViewModel, gamesViewModel)
                            2 -> DrawingGameView(mainViewModel, gamesViewModel)
                            3 -> StarCountingGameView(mainViewModel, gamesViewModel)
                            4 -> QuizGameView(mainViewModel)
                            5 -> BalloonPopGameView(mainViewModel)
                            6 -> MagicPianoGameView(mainViewModel)
                            7 -> JogoAdivinhaPalavraScreen(mainViewModel) { selectedTab = 0 }
                            8 -> JogoDescubraDiferencasScreen(mainViewModel) { selectedTab = 0 }
                            9 -> JogoCorridaColoridaScreen(mainViewModel) { selectedTab = 0 }
                            10 -> JogoGaloScreen(mainViewModel) { selectedTab = 0 }
                            11 -> JogoSudokuInfantilScreen(mainViewModel) { selectedTab = 0 }
                            12 -> JogoStopZeTraquinaScreen(mainViewModel) { selectedTab = 0 }
                            13 -> JogoSopaDeLetrasScreen(mainViewModel) { selectedTab = 0 }
                            14 -> BubbleTraquinaClassicGameScreen(mainViewModel) { selectedTab = 0 }
                            15 -> JogoSeparacaoCoresScreen(mainViewModel) { selectedTab = 0 }
                            16 -> DetetivePalavrasScreen(mainViewModel) { selectedTab = 0 }
                            17 -> CreativeWorkshopScreen(mainViewModel) { selectedTab = 0 }
                            18 -> UnoGameScreen(mainViewModel) { selectedTab = 0 }
                        }
                    }
                }
            }
        }
    }

data class MemoryGameLevel(val levelNum: Int, val pairCount: Int, val title: String)
data class StarGameLevel(val levelNum: Int, val starCount: Int, val title: String)
data class BalloonGameLevel(val levelNum: Int, val balloonCount: Int, val title: String)

// --- 1. Memory Game ---
@Composable
fun MemoryGameView(
    mainViewModel: MainViewModel? = null,
    gamesViewModel: GamesViewModel
) {
    val isPreview = LocalInspectionMode.current
    val userProgress by mainViewModel?.userProgress?.collectAsState() ?: remember { mutableStateOf(UserProgress()) }
    val cards by gamesViewModel.memoryCards.collectAsState()
    val matchedPairs by gamesViewModel.matchedPairs.collectAsState()
    val totalPairs = cards.size / 2

    val memoryLevels = remember {
        listOf(
            MemoryGameLevel(1, 3, "Nível 1 (3 Pares 🐣)"),
            MemoryGameLevel(2, 4, "Nível 2 (4 Pares 🦁)"),
            MemoryGameLevel(3, 6, "Nível 3 (6 Pares 🚀)"),
            MemoryGameLevel(4, 8, "Nível 4 (8 Pares 👑)"),
            MemoryGameLevel(5, 10, "Nível 5 (10 Pares 🏆)")
        )
    }
    var currentLevelIndex by remember { mutableIntStateOf(1) } // 4 pairs default (index 1)
    val currentLevel = memoryLevels[currentLevelIndex]

    LaunchedEffect(currentLevelIndex) {
        gamesViewModel.startNewMemoryGame(
            pairCount = currentLevel.pairCount,
            customEmoji = userProgress.customCharFace.takeIf { userProgress.customCharName.isNotEmpty() },
            customName = userProgress.customCharName.takeIf { it.isNotEmpty() }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Jogo da Memória 🧠✨",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    color = Color(0xFF0F172A)
                )
                Text(
                    text = "Pares encontrados: $matchedPairs / $totalPairs",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = SkyBluePrimary
                )
            }

            Button(
                onClick = {
                    gamesViewModel.startNewMemoryGame(
                        pairCount = currentLevel.pairCount,
                        customEmoji = userProgress.customCharFace.takeIf { userProgress.customCharName.isNotEmpty() },
                        customName = userProgress.customCharName.takeIf { it.isNotEmpty() }
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = SunshineYellow),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reiniciar", tint = Color.Black, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Novo Jogo", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }

        // Level Selector Tabs
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            itemsIndexed(memoryLevels) { idx, lvl ->
                val isSelected = currentLevelIndex == idx
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (isSelected) SkyBluePrimary else Color(0xFFE2E8F0),
                    border = BorderStroke(1.dp, if (isSelected) Color(0xFF0284C7) else Color(0xFFCBD5E1)),
                    modifier = Modifier.clickable {
                        currentLevelIndex = idx
                    }
                ) {
                    Text(
                        text = lvl.title,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                        color = if (isSelected) Color.White else Color(0xFF334155),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                    )
                }
            }
        }

        if (totalPairs > 0 && matchedPairs == totalPairs) {
            LaunchedEffect(matchedPairs) {
                kotlinx.coroutines.delay(2200)
                // Advance to next level automatically if available
                currentLevelIndex = (currentLevelIndex + 1) % memoryLevels.size
            }
            ConfettiEffect()
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MintGreen)
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ZeTraquinaMascot(
                        size = 56.dp,
                        showSpeechBubble = true,
                        emotion = MascotEmotion.CELEBRATING,
                        triggerEmotionKey = matchedPairs,
                        customPhrase = "PARABÉNS! Encontraste todos os pares! A passar ao Nível ${(currentLevelIndex % memoryLevels.size) + 1}... 🎉⭐",
                        onInteract = {}
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        val gridColumns = if (totalPairs >= 8) 4 else if (totalPairs >= 5) 4 else 3
        LazyVerticalGrid(
            columns = GridCells.Fixed(gridColumns),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 20.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(cards, key = { _, card -> card.id }) { index, card ->
                val isRevealed = card.isFlipped || card.isMatched

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.85f)
                        .clickable {
                            if (!card.isFlipped && !card.isMatched) {
                                gamesViewModel.flipMemoryCard(index) {
                                    if (!isPreview) {
                                        mainViewModel?.addStars(5)

                                    }
                                }
                                if (!card.isMatched && !isPreview) {

                                }
                            }
                        }
                        .testTag("memory_card_$index"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = when {
                            card.isMatched -> Color(0xFFDCFCE7) // Soft Mint Green
                            card.isFlipped -> Color(0xFFE0F2FE) // Soft Sky Blue
                            else -> SunshineYellow
                        }
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = if (isRevealed) 2.dp else 4.dp),
                    border = BorderStroke(
                        width = 1.5.dp,
                        color = when {
                            card.isMatched -> MintGreen
                            card.isFlipped -> SkyBluePrimary
                            else -> Color(0xFFF59E0B)
                        }
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isRevealed) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = card.icon, fontSize = 34.sp)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = card.label,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1E293B)
                                )
                            }
                        } else {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "🧩", fontSize = 28.sp)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Zé",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF1E293B)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- 2. Drawing Canvas Game ---
@Composable
fun DrawingGameView(
    mainViewModel: MainViewModel? = null,
    gamesViewModel: GamesViewModel
) {
    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current
    val strokes by gamesViewModel.strokes.collectAsState()
    val selectedColor by gamesViewModel.selectedColor.collectAsState()
    val selectedWidth by gamesViewModel.selectedBrushWidth.collectAsState()

    var currentPath by remember { mutableStateOf<android.graphics.Path?>(null) }

    val colorPalette = listOf(
        Color(0xFFFF4081), // Pink
        Color(0xFF0288D1), // Blue
        Color(0xFFFFC107), // Yellow
        Color(0xFF4CAF50), // Green
        Color(0xFF7E57C2), // Purple
        Color(0xFFFF5722), // Orange
        Color(0xFF000000)  // Black
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                colorPalette.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(color)
                            .clickable {
                                gamesViewModel.setColor(color)
                                if (!isPreview) {

                                }
                            }
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(
                    onClick = {
                        if (!isPreview) {
                            saveDrawing(context, strokes)
                        }
                    },
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Icon(imageVector = Icons.Default.Save, contentDescription = "Guardar", tint = Color(0xFF10B981), modifier = Modifier.size(18.dp))
                }

                IconButton(
                    onClick = {
                        if (!isPreview) {
                            printDrawing(context, strokes)
                        }
                    },
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Icon(imageVector = Icons.Default.Print, contentDescription = "Imprimir", tint = SkyBluePrimary, modifier = Modifier.size(18.dp))
                }

                IconButton(
                    onClick = {
                        gamesViewModel.clearDrawing()
                        if (!isPreview) {

                        }
                    },
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Limpar", tint = Color.Red, modifier = Modifier.size(18.dp))
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Espessura:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            listOf(8f, 16f, 26f).forEach { width ->
                Button(
                    onClick = { gamesViewModel.setBrushWidth(width) },
                    modifier = Modifier.padding(horizontal = 4.dp).height(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedWidth == width) SkyBluePrimary else MaterialTheme.colorScheme.surfaceVariant
                    ),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = if (width == 8f) "Fino" else if (width == 16f) "Médio" else "Grosso",
                        fontSize = 11.sp,
                        color = if (selectedWidth == width) Color.White else Color.DarkGray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(bottom = 12.dp)
                .testTag("drawing_canvas_card"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                currentPath = android.graphics.Path().apply { moveTo(offset.x, offset.y) }
                            },
                            onDrag = { change, _ ->
                                currentPath?.let { path ->
                                    path.lineTo(change.position.x, change.position.y)
                                    gamesViewModel.addStroke(
                                        DrawingStroke(
                                            path = android.graphics.Path(path),
                                            color = selectedColor,
                                            strokeWidth = selectedWidth
                                        )
                                    )
                                }
                            },
                            onDragEnd = {
                                currentPath = null
                            }
                        )
                    }
            ) {
                strokes.forEach { stroke ->
                    drawPath(
                        path = stroke.path.asComposePath(),
                        color = stroke.color,
                        style = Stroke(width = stroke.strokeWidth)
                    )
                }
            }
        }
    }
}

private fun printDrawing(context: Context, strokes: List<DrawingStroke>) {
    val bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888)
    val androidCanvas = android.graphics.Canvas(bitmap)
    androidCanvas.drawColor(android.graphics.Color.WHITE)

    val paint = android.graphics.Paint().apply {
        isAntiAlias = true
        style = android.graphics.Paint.Style.STROKE
        strokeCap = android.graphics.Paint.Cap.ROUND
        strokeJoin = android.graphics.Paint.Join.ROUND
    }

    strokes.forEach { stroke ->
        paint.color = stroke.color.toArgb()
        paint.strokeWidth = stroke.strokeWidth * 2f
        try {
            androidCanvas.drawPath(stroke.path, paint)
        } catch (_: Throwable) {}
    }

    val outputStream = java.io.ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    val base64Image = android.util.Base64.encodeToString(outputStream.toByteArray(), android.util.Base64.DEFAULT)

    val html = """
        <html>
        <head>
            <style>
                @media print { @page { size: A4; margin: 2cm; } }
                body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; text-align: center; padding: 20px; color: #333; }
                h1 { color: #E11D48; }
                .header { display: flex; justify-content: space-between; align-items: flex-end; border-bottom: 2px solid #eee; padding-bottom: 10px; margin-bottom: 20px; text-align: left; }
                .header-field { border-bottom: 1px solid #ccc; min-width: 200px; display: inline-block; }
                .canvas-container { border: 3px solid #E11D48; border-radius: 16px; display: inline-block; padding: 10px; background: #fff; box-shadow: 0 4px 12px rgba(0,0,0,0.1); }
                img { max-width: 100%; height: auto; display: block; border-radius: 8px; }
            </style>
        </head>
        <body>
            <div class='header'>
                <div>
                    <h1>Atelier Pintura 🎨 - Zé Traquina</h1>
                    <p style='margin: 5px 0 0 0; color: #666; font-weight: bold;'>Universo Educativo Zé Traquina</p>
                </div>
                <div>
                    <div>Nome: <span class='header-field'></span></div>
                    <div style='margin-top: 8px;'>Data: <span class='header-field'>____/____/________</span></div>
                </div>
            </div>
            <div class='canvas-container'>
                <img src='data:image/png;base64,$base64Image' />
            </div>
        </body>
        </html>
    """.trimIndent()

    val webView = WebView(context)
    com.example.util.EmulatorUtils.optimizeWebViewForEmulator(webView)
    webView.webViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView, url: String) {
            val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
            val printAdapter = view.createPrintDocumentAdapter("Atelier_Pintura")
            printManager.print("Atelier_Pintura", printAdapter, PrintAttributes.Builder().build())
        }

        override fun onRenderProcessGone(view: WebView?, detail: android.webkit.RenderProcessGoneDetail?): Boolean {
            try {
                view?.destroy()
            } catch (_: Exception) {}
            return true
        }
    }
    webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
}

private fun saveDrawing(context: Context, strokes: List<DrawingStroke>) {
    val bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888)
    val androidCanvas = android.graphics.Canvas(bitmap)
    androidCanvas.drawColor(android.graphics.Color.WHITE)

    val paint = android.graphics.Paint().apply {
        isAntiAlias = true
        style = android.graphics.Paint.Style.STROKE
        strokeCap = android.graphics.Paint.Cap.ROUND
        strokeJoin = android.graphics.Paint.Join.ROUND
    }

    strokes.forEach { stroke ->
        paint.color = stroke.color.toArgb()
        paint.strokeWidth = stroke.strokeWidth * 2f
        try {
            androidCanvas.drawPath(stroke.path, paint)
        } catch (_: Throwable) {}
    }

    try {
        val filename = "ZeTraquina_Pintura_${System.currentTimeMillis()}.png"
        var fos: java.io.OutputStream? = null
        var imageUri: android.net.Uri? = null

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            val values = android.content.ContentValues().apply {
                put(android.provider.MediaStore.Images.Media.DISPLAY_NAME, filename)
                put(android.provider.MediaStore.Images.Media.MIME_TYPE, "image/png")
                put(android.provider.MediaStore.Images.Media.RELATIVE_PATH, android.os.Environment.DIRECTORY_PICTURES + "/Zé Traquina")
            }
            val contentResolver = context.contentResolver
            imageUri = contentResolver.insert(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            imageUri?.let {
                fos = contentResolver.openOutputStream(it)
            }
        } else {
            val imagesDir = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_PICTURES)
            val appDir = java.io.File(imagesDir, "Zé Traquina")
            if (!appDir.exists()) appDir.mkdirs()
            val image = java.io.File(appDir, filename)
            fos = java.io.FileOutputStream(image)
        }

        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            android.widget.Toast.makeText(context, "Desenho guardado nas Imagens! 💾", android.widget.Toast.LENGTH_SHORT).show()
        } ?: run {
            android.widget.Toast.makeText(context, "Erro ao guardar desenho", android.widget.Toast.LENGTH_SHORT).show()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        android.widget.Toast.makeText(context, "Erro ao guardar: ${e.localizedMessage}", android.widget.Toast.LENGTH_SHORT).show()
    }
}

// --- 3. Star Counting Game ---
@Composable
fun StarCountingGameView(
    mainViewModel: MainViewModel? = null,
    gamesViewModel: GamesViewModel
) {
    val userProgress by mainViewModel?.userProgress?.collectAsState() ?: remember { mutableStateOf(UserProgress()) }
    val isPreview = LocalInspectionMode.current
    val targetCount by gamesViewModel.starTargetCount.collectAsState()
    val userTappedStars by gamesViewModel.userTappedStars.collectAsState()

    val starLevels = remember {
        listOf(
            StarGameLevel(1, 3, "Nível 1 (3 ⭐)"),
            StarGameLevel(2, 5, "Nível 2 (5 ⭐)"),
            StarGameLevel(3, 7, "Nível 3 (7 ⭐)"),
            StarGameLevel(4, 9, "Nível 4 (9 ⭐)"),
            StarGameLevel(5, 12, "Nível 5 (12 ⭐)"),
            StarGameLevel(6, 15, "Nível 6 (15 ⭐)")
        )
    }
    var currentLevelIdx by remember { mutableIntStateOf(0) }

    LaunchedEffect(currentLevelIdx) {
        gamesViewModel.startStarCounterLevel(starLevels[currentLevelIdx].starCount)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Toca em $targetCount estrelinhas! ⭐",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Progresso: $userTappedStars / $targetCount",
                fontSize = 14.sp,
                color = SkyBluePrimary,
                fontWeight = FontWeight.Bold
            )

            // Star Level Chips
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                itemsIndexed(starLevels) { idx, lvl ->
                    val isSelected = currentLevelIdx == idx
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) SunshineYellow else Color(0xFFE2E8F0),
                        border = BorderStroke(1.dp, if (isSelected) Color(0xFFD97706) else Color(0xFFCBD5E1)),
                        modifier = Modifier.clickable {
                            currentLevelIdx = idx
                        }
                    ) {
                        Text(
                            text = lvl.title,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                            color = if (isSelected) Color(0xFF78350F) else Color(0xFF334155),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                        )
                    }
                }
            }

            if (userTappedStars >= targetCount && targetCount > 0) {
                LaunchedEffect(userTappedStars) {
                    kotlinx.coroutines.delay(2000)
                    currentLevelIdx = (currentLevelIdx + 1) % starLevels.size
                }
                ConfettiEffect()
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center) {
                    ZeTraquinaMascot(
                        size = 56.dp,
                        showSpeechBubble = true,
                        emotion = MascotEmotion.CELEBRATING,
                        triggerEmotionKey = userTappedStars,
                        customPhrase = "PARABÉNS! Contaste todas as $targetCount estrelas! A passar ao Nível ${(currentLevelIdx % starLevels.size) + 1}... ⭐🌟",
                        onInteract = {}
                    )
                    if (userProgress.customCharName.isNotEmpty()) {
                        Spacer(modifier = Modifier.width(12.dp))
                        CustomAmiguinhoMascot(
                            size = 48.dp,
                            shapeName = userProgress.customCharShape,
                            color = Color(userProgress.customCharColor),
                            faceEmoji = userProgress.customCharFace
                        )
                    }
                }
            }
        }

        val starCols = if (targetCount > 9) 4 else 3
        LazyVerticalGrid(
            columns = GridCells.Fixed(starCols),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(targetCount) { index ->
                val isTapped = index < userTappedStars

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clickable {
                            if (index == userTappedStars) {
                                gamesViewModel.tapStar {
                                    if (!isPreview) {
                                        mainViewModel?.addStars(5)
                                    }
                                }
                            }
                        },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isTapped) MintGreen else SunshineYellow
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Estrela",
                            tint = if (isTapped) Color.White else KidStarOrange,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

// --- 4. Quiz Divertido ---
@Composable
fun QuizGameView(
    mainViewModel: MainViewModel?
) {
    val isPreview = LocalInspectionMode.current
    val context = LocalContext.current
    val questions = remember {
        listOf(
            QuizQuestion(
                question = "Qual animal diz 'Miau Miau'?",
                options = listOf("🐶 Cão", "🐱 Gato", "🦁 Leão", "🐮 Vaca"),
                correctIndex = 1,
                emoji = "🐱",
                explanation = "O gato é um felino muito fofinho que adora brincar!"
            ),
            QuizQuestion(
                question = "Onde vivem os peixinhos?",
                options = listOf("🌲 Na floresta", "🌊 Na água", "☁️ No céu", "🏠 Em cima da mesa"),
                correctIndex = 1,
                emoji = "🐟",
                explanation = "Os peixinhos nadam felizes na água do mar e dos rios!"
            ),
            QuizQuestion(
                question = "Qual é a cor do sol brilhante?",
                options = listOf("🔵 Azul", "🟡 Amarelo", "🟢 Verde", "🟣 Roxo"),
                correctIndex = 1,
                emoji = "☀️",
                explanation = "O sol brilha com uma cor amarela radiante no céu!"
            ),
            QuizQuestion(
                question = "O que comem os macacos?",
                options = listOf("🍌 Bananas", "🧱 Tijolos", "🚗 Carros", "📚 Livros"),
                correctIndex = 0,
                emoji = "🐒",
                explanation = "Os macacos adoram trepar árvores e comer bananas docinhas!"
            ),
            QuizQuestion(
                question = "Quem produz o leite gostoso?",
                options = listOf("🐔 Galinha", "🐮 Vaca", "🐸 Sapo", "🐝 Abelha"),
                correctIndex = 1,
                emoji = "🐮",
                explanation = "A vaca dá um leite muito saboroso e nutritivo!"
            ),
            QuizQuestion(
                question = "Qual destes voa alto no céu?",
                options = listOf("🐘 Elefante", "🦅 Pássaro", "🐢 Tartaruga", "🐖 Porco"),
                correctIndex = 1,
                emoji = "🦅",
                explanation = "Os pássaros têm asas e voam livres pelo céu!"
            ),
            QuizQuestion(
                question = "Quantas cores tem o arco-íris maravilhoso?",
                options = listOf("🌈 3 cores", "🌈 5 cores", "🌈 7 cores", "🌈 10 cores"),
                correctIndex = 2,
                emoji = "🌈",
                explanation = "O arco-íris tem 7 cores lindas: vermelho, laranja, amarelo, verde, azul, anil e violeta!"
            ),
            QuizQuestion(
                question = "Qual é o meio de transporte que viaja pelos carris e faz 'Chu-Chu'?",
                options = listOf("🚗 Carro", "🚂 Comboio", "✈️ Avião", "🚲 Bicicleta"),
                correctIndex = 1,
                emoji = "🚂",
                explanation = "O comboio anda nos carris e leva muitas pessoas a passear!"
            ),
            QuizQuestion(
                question = "O que recolhem as abelhas das flores para fazer mel docinho?",
                options = listOf("🌸 Pólen e Néctar", "🧱 Pedras", "🍃 Folhas", "💧 Água"),
                correctIndex = 0,
                emoji = "🐝",
                explanation = "As abelhas visitam as flores para recolher néctar e fazer o mel que tanto gostamos!"
            ),
            QuizQuestion(
                question = "Qual é a maior estrela do nosso Sistema Solar?",
                options = listOf("🌙 Lua", "🌍 Terra", "☀️ Sol", "⭐ Outra Estrela"),
                correctIndex = 2,
                emoji = "☀️",
                explanation = "O Sol é uma estrela gigante que nos dá luz e calor todos os dias!"
            ),
            QuizQuestion(
                question = "Quantos dias tem uma semana inteira?",
                options = listOf("📅 5 dias", "📅 7 dias", "📅 10 dias", "📅 30 dias"),
                correctIndex = 1,
                emoji = "📅",
                explanation = "Uma semana tem 7 dias: segunda, terça, quarta, quinta, sexta, sábado e domingo!"
            ),
            QuizQuestion(
                question = "Qual deste frutos tem a casca vermelha e uma coroa verde?",
                options = listOf("🍌 Banana", "🍇 Uva", "🍓 Morango", "🍊 Laranja"),
                correctIndex = 2,
                emoji = "🍓",
                explanation = "O morango é vermelho, docinho e tem folhas verdes no topo que parecem uma coroa!"
            ),
            QuizQuestion(
                question = "Qual é o animal conhecido por ser o 'Rei da Selva'?",
                options = listOf("🦁 Leão", "🐯 Tigre", "🐻 Urso", "🐘 Elefante"),
                correctIndex = 0,
                emoji = "🦁",
                explanation = "O leão é chamado o Rei da Selva por ser muito forte e ter uma grande juba!"
            ),
            QuizQuestion(
                question = "Onde é que os peixes respiram debaixo de água?",
                options = listOf("👃 Narinas", "🫁 Pulmões", "🐟 Guelras/Brânquias", "👂 Ouvidos"),
                correctIndex = 2,
                emoji = "🐟",
                explanation = "Os peixes usam as guelras para respirar retirando o oxigénio da água!"
            ),
            QuizQuestion(
                question = "Qual é o animal terrestre mais alto do mundo?",
                options = listOf("🐘 Elefante", "🦒 Girafa", "🦕 Dinossauro", "🦁 Leão"),
                correctIndex = 1,
                emoji = "🦒",
                explanation = "A girafa tem um pescoço super comprido para conseguir comer as folhas no topo das árvores!"
            ),
            QuizQuestion(
                question = "Qual é a cor que resulta da mistura do Azul com o Amarelo?",
                options = listOf("🟢 Verde", "🟣 Roxo", "🟠 Laranja", "🔴 Vermelho"),
                correctIndex = 0,
                emoji = "🎨",
                explanation = "Misturando azul e amarelo, criamos a maravilhosa cor verde!"
            ),
            QuizQuestion(
                question = "Quantas estações tem o ano?",
                options = listOf("❄️ 2 estações", "🌸 4 estações", "☀️ 6 estações", "🍂 12 estações"),
                correctIndex = 1,
                emoji = "🌸",
                explanation = "O ano tem 4 estações: Primavera, Verão, Outono e Inverno!"
            ),
            QuizQuestion(
                question = "Qual é o planeta em que nós vivemos?",
                options = listOf("🪐 Saturno", "🔴 Marte", "🌍 Terra", "🌕 Lua"),
                correctIndex = 2,
                emoji = "🌍",
                explanation = "Vivemos no lindo Planeta Terra, o planeta azul cheio de vida e oceanos!"
            ),
            QuizQuestion(
                question = "Que animal carrega a sua própria casinha às costas?",
                options = listOf("🐢 Tartaruga", "🐰 Coelho", "🦊 Raposa", "🦆 Pato"),
                correctIndex = 0,
                emoji = "🐢",
                explanation = "A tartaruga tem uma carapaça protetora que funciona como a sua casinha!"
            ),
            QuizQuestion(
                question = "Quantos dedos temos nas duas mãos juntas?",
                options = listOf("🖐️ 5 dedos", "🖐️🖐️ 10 dedos", "🖐️ 15 dedos", "🖐️ 20 dedos"),
                correctIndex = 1,
                emoji = "🖐️",
                explanation = "Temos 5 dedos em cada mão, fazendo 10 dedos no total!"
            ),
            QuizQuestion(
                question = "Qual é o instrumento musical que tem teclas pretas e brancas?",
                options = listOf("🎸 Guitarra", "🎹 Piano", "🥁 Tambor", "🎺 Trompete"),
                correctIndex = 1,
                emoji = "🎹",
                explanation = "O piano tem teclas pretas e brancas que tocam notas mágicas e harmoniosas!"
            ),
            QuizQuestion(
                question = "Qual é o animal marinho mais inteligente e brincalhão?",
                options = listOf("🐬 Golfinho", "🦀 Caranguejo", "🐙 Polvo", "🐡 Baiacu"),
                correctIndex = 0,
                emoji = "🐬",
                explanation = "O golfinho adora dar saltos no mar e comunicar com os seus amigos!"
            ),
            QuizQuestion(
                question = "De que cor fica a relva e as folhas no Verão?",
                options = listOf("🟢 Verde", "🟣 Roxo", "🔵 Azul", "⚫ Preto"),
                correctIndex = 0,
                emoji = "🌱",
                explanation = "A clorofila dá a cor verde brilhante às folhas e à relva!"
            ),
            QuizQuestion(
                question = "Qual é o veículo que voa no espaço e visita a Lua?",
                options = listOf("🚀 Foguetão", "🚗 Carro", "⛵ Barco", "🚜 Trator"),
                correctIndex = 0,
                emoji = "🚀",
                explanation = "Os astronautas usam foguetões espaciais para explorar o universo e a Lua!"
            ),
            QuizQuestion(
                question = "Quantos lados tem um triângulo?",
                options = listOf("🔺 3 lados", "🔲 4 lados", "⭐ 5 lados", "🛑 6 lados"),
                correctIndex = 0,
                emoji = "🔺",
                explanation = "Um triângulo tem 3 lados e 3 cantos bem definidos!"
            )
        )
    }

    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var isCorrect by remember { mutableStateOf<Boolean?>(null) }
    var correctCount by remember { mutableIntStateOf(0) }

    val q = questions[currentQuestionIndex]

    // Voice narration when question loads
    LaunchedEffect(currentQuestionIndex) {
        if (!isPreview) {
            mainViewModel?.speak("Pergunta número ${currentQuestionIndex + 1}: ${q.question}") {}
        }
    }

    // Auto next question transition
    LaunchedEffect(isCorrect, currentQuestionIndex) {
        if (isCorrect == true) {
            if (!isPreview) {
                mainViewModel?.speak("Excelente! Acertaste! ${q.explanation}") {}
            }
            kotlinx.coroutines.delay(3500)
            selectedOption = null
            isCorrect = null
            currentQuestionIndex = (currentQuestionIndex + 1) % questions.size
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFEFF6FF), Color(0xFFDBEAFE))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header Info & Progress Bar
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Nível ${currentQuestionIndex + 1} de ${questions.size}",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1E3A8A)
                        )
                        Text(
                            text = "Super Quiz do Zé 👦🏻✨",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF475569)
                        )
                    }

                    // Score Indicator
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFFCD34D),
                        border = BorderStroke(1.5.dp, Color(0xFFF59E0B)),
                        shadowElevation = 2.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Estrelas",
                                tint = Color(0xFFD97706),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Acertos: $correctCount",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF78350F)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Fun Progress Bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE2E8F0))
                ) {
                    val progressFraction = (currentQuestionIndex + 1).toFloat() / questions.size
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progressFraction)
                            .fillMaxHeight()
                            .clip(CircleShape)
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(Color(0xFF3B82F6), Color(0xFF60A5FA))
                                )
                            )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Card with Question
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.5.dp, Color(0xFF93C5FD), RoundedCornerShape(24.dp)),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Speaker Button to repeat reading
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(
                                onClick = {
                                    if (!isPreview) {
                                        mainViewModel?.speak(q.question) {}
                                    }
                                },
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(Color(0xFFEFF6FF), CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VolumeUp,
                                    contentDescription = "Ouvir",
                                    tint = Color(0xFF2563EB),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Text(
                            text = q.emoji,
                            fontSize = 64.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = q.question,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF1E293B),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            lineHeight = 24.sp
                        )
                    }
                }
            }

            // Message Banners
            if (isCorrect == true) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0xFFDCFCE7),
                    border = BorderStroke(2.dp, Color(0xFF22C55E)),
                    shadowElevation = 4.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🎉 BRILHANTE! ACERTASTE! 🌟",
                            color = Color(0xFF15803D),
                            fontWeight = FontWeight.Black,
                            fontSize = 17.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = q.explanation,
                            color = Color(0xFF166534),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            } else if (isCorrect == false) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0xFFFEE2E2),
                    border = BorderStroke(2.dp, Color(0xFFEF4444)),
                    shadowElevation = 4.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🌈 Quase! Tenta de novo! 🙈",
                            color = Color(0xFF991B1B),
                            fontWeight = FontWeight.Black,
                            fontSize = 15.sp
                        )
                        Text(
                            text = "Tu consegues amiguinho, pensa com atenção! 🎨",
                            color = Color(0xFFB91C1C),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }

            // Options list
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                q.options.forEachIndexed { optIndex, optionText ->
                    val isSelected = selectedOption == optIndex
                    val isCorrectChoice = optIndex == q.correctIndex

                    val (buttonColor, borderColor, textColor) = when {
                        isCorrect == true && isSelected -> Triple(Color(0xFFDCFCE7), Color(0xFF22C55E), Color(0xFF15803D))
                        isCorrect == false && isSelected -> Triple(Color(0xFFFEE2E2), Color(0xFFEF4444), Color(0xFF991B1B))
                        else -> Triple(Color.White, Color(0xFFCBD5E1), Color(0xFF334155))
                    }

                    Button(
                        onClick = {
                            if (isCorrect != true) {
                                selectedOption = optIndex
                                val correct = optIndex == q.correctIndex
                                isCorrect = correct
                                if (!isPreview) {
                                    if (correct) {
                                        correctCount++
                                        mainViewModel?.addStars(5)
                                        mainViewModel?.playSound(R.raw.ze_traquina_e_fixe)
                                    } else {
                                        // Play wrong buzzer or sound if any
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .border(
                                width = if (isSelected) 3.dp else 1.5.dp,
                                color = borderColor,
                                shape = RoundedCornerShape(18.dp)
                            ),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = optionText,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Black,
                                color = textColor
                            )
                            if (isSelected) {
                                Text(
                                    text = if (isCorrectChoice) "✅" else "❌",
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(70.dp))
        }

        // Confetti effect overlay when answer is correct!
        if (isCorrect == true) {
            ConfettiEffect()
        }
    }
}

// --- 5. Balloon Pop Game ---
@Composable
fun BalloonPopGameView(
    mainViewModel: MainViewModel?
) {
    val isPreview = LocalInspectionMode.current

    val balloonLevels = remember {
        listOf(
            BalloonGameLevel(1, 4, "Nível 1 (4 🎈)"),
            BalloonGameLevel(2, 6, "Nível 2 (6 🎈)"),
            BalloonGameLevel(3, 8, "Nível 3 (8 🎈)"),
            BalloonGameLevel(4, 10, "Nível 4 (10 🎈)"),
            BalloonGameLevel(5, 12, "Nível 5 (12 🎈)")
        )
    }
    var currentLevelIdx by remember { mutableIntStateOf(1) } // 6 balloons default

    val allPossibleBalloons = remember {
        listOf(
            BalloonItem(1, Color(0xFFFF4081), "Vermelho", "🎈"),
            BalloonItem(2, Color(0xFF00B0FF), "Azul", "🎈"),
            BalloonItem(3, Color(0xFFFFD54F), "Amarelo", "🎈"),
            BalloonItem(4, Color(0xFF66BB6A), "Verde", "🎈"),
            BalloonItem(5, Color(0xFFAB47BC), "Roxo", "🎈"),
            BalloonItem(6, Color(0xFFFF7043), "Laranja", "🎈"),
            BalloonItem(7, Color(0xFF00E676), "Lima", "🎈"),
            BalloonItem(8, Color(0xFFFF1744), "Rubi", "🎈"),
            BalloonItem(9, Color(0xFF651FFF), "Índigo", "🎈"),
            BalloonItem(10, Color(0xFFFFD700), "Dourado", "🎈"),
            BalloonItem(11, Color(0xFF00E5FF), "Ciano", "🎈"),
            BalloonItem(12, Color(0xFFFF4081), "Rosa Choque", "🎈")
        )
    }

    var balloons by remember(currentLevelIdx) {
        val count = balloonLevels[currentLevelIdx].balloonCount
        mutableStateOf(allPossibleBalloons.take(count).map { it.copy(isPopped = false) })
    }

    var poppedCount by remember(currentLevelIdx) { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Estoura os Balões Mágicos! 🎈",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Balões rebentados: $poppedCount / ${balloons.size}",
                fontSize = 14.sp,
                color = SkyBluePrimary,
                fontWeight = FontWeight.Bold
            )

            // Level Selector Chips
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                itemsIndexed(balloonLevels) { idx, lvl ->
                    val isSelected = currentLevelIdx == idx
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) Color(0xFFFF4081) else Color(0xFFE2E8F0),
                        border = BorderStroke(1.dp, if (isSelected) Color(0xFFE11D48) else Color(0xFFCBD5E1)),
                        modifier = Modifier.clickable {
                            currentLevelIdx = idx
                        }
                    ) {
                        Text(
                            text = lvl.title,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                            color = if (isSelected) Color.White else Color(0xFF334155),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                        )
                    }
                }
            }

            if (poppedCount == balloons.size && balloons.isNotEmpty()) {
                LaunchedEffect(poppedCount) {
                    kotlinx.coroutines.delay(2200)
                    currentLevelIdx = (currentLevelIdx + 1) % balloonLevels.size
                }
                ConfettiEffect()
                Spacer(modifier = Modifier.height(4.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MintGreen)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🎉 PARABÉNS! Rebentaste todos os balões! 🎈🌟",
                            fontWeight = FontWeight.Black,
                            fontSize = 15.sp,
                            color = Color(0xFF0F172A)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "A passar ao Nível ${(currentLevelIdx % balloonLevels.size) + 1}... 🎈🚀",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF166534)
                        )
                    }
                }
            }
        }

        val balloonCols = 4
        LazyVerticalGrid(
            columns = GridCells.Fixed(balloonCols),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp)
        ) {
            items(balloons) { balloon ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.0f)
                        .clickable {
                            if (!balloon.isPopped) {
                                balloons = balloons.map { if (it.id == balloon.id) it.copy(isPopped = true) else it }
                                poppedCount++
                                if (!isPreview) {
                                    mainViewModel?.addStars(3)
                                }
                            }
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (balloon.isPopped) MaterialTheme.colorScheme.surfaceVariant else balloon.color
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (balloon.isPopped) {
                            Text(text = "✨", fontSize = 32.sp)
                        } else {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = balloon.emoji, fontSize = 36.sp)
                                Text(
                                    text = balloon.label,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(70.dp))
    }
}

// --- 6. Magic Piano, Xylophone & Bells Game ---
@Composable
fun MagicPianoGameView(
    mainViewModel: MainViewModel?
) {
    val isPreview = LocalInspectionMode.current
    val coroutineScope = rememberCoroutineScope()
    var selectedInstrument by remember { mutableStateOf("xylophone") } // "xylophone", "piano", "chime"
    var layoutStyle by remember { mutableStateOf("keyboard") } // "keyboard" (side-by-side), "bars" (compact vertical)

    val pianoKeys = remember(selectedInstrument) {
        val instrumentIcon = when (selectedInstrument) {
            "xylophone" -> "🪵"
            "chime" -> "🔔"
            else -> "🎹"
        }
        listOf(
            PianoKeyItem("Dó", "Dó $instrumentIcon", "🐕", Color(0xFFFF8A80), "Dó", 261.63),
            PianoKeyItem("Ré", "Ré $instrumentIcon", "🐈", Color(0xFFFFD180), "Ré", 293.66),
            PianoKeyItem("Mi", "Mi $instrumentIcon", "🐸", Color(0xFFFFEE58), "Mi", 329.63),
            PianoKeyItem("Fá", "Fá $instrumentIcon", "🦁", Color(0xFFD4E157), "Fá", 349.23),
            PianoKeyItem("Sol", "Sol $instrumentIcon", "🐮", Color(0xFF81D4FA), "Sol", 392.00),
            PianoKeyItem("Lá", "Lá $instrumentIcon", "🐥", Color(0xFFCE93D8), "Lá", 440.00),
            PianoKeyItem("Si", "Si $instrumentIcon", "⭐", Color(0xFFF48FB1), "Si", 493.88),
            PianoKeyItem("Dó²", "Dó² $instrumentIcon", "🚀", Color(0xFFA7F3D0), "Dó Agudo", 523.25)
        )
    }

    var voiceGuideEnabled by remember { mutableStateOf(false) }
    var lastPlayedKey by remember { mutableStateOf<String?>(null) }
    var isPlayingDemoSong by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // --- Top Title & Instrument/Layout Selectors ---
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Xilofone, Piano & Sinos 🎹🪵🔔",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF0F172A)
                    )
                    Text(
                        text = "8 Notas: Dó, Ré, Mi, Fá, Sol, Lá, Si, Dó²",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SkyBluePrimary
                    )
                }

                // Voice toggle & Layout Toggle
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFFF1F5F9),
                        modifier = Modifier.clickable {
                            layoutStyle = if (layoutStyle == "keyboard") "bars" else "keyboard"
                        }
                    ) {
                        Text(
                            text = if (layoutStyle == "keyboard") "🎹 Teclado" else "🪵 Barras",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (voiceGuideEnabled) SunshineYellow else Color(0xFFE2E8F0),
                        modifier = Modifier.clickable { voiceGuideEnabled = !voiceGuideEnabled }
                    ) {
                        Text(
                            text = if (voiceGuideEnabled) "🗣️ Voz ON" else "🗣️ Voz OFF",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E293B),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Instrument Mode Tabs
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                val instruments = listOf(
                    "xylophone" to "Xilofone 🪵",
                    "piano" to "Piano 🎹",
                    "chime" to "Sinos 🔔"
                )
                instruments.forEach { (type, label) ->
                    val isSelected = selectedInstrument == type
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) SkyBluePrimary else Color(0xFFF1F5F9),
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                selectedInstrument = type
                                if (type == "piano") layoutStyle = "keyboard"
                                if (type == "xylophone") layoutStyle = "bars"
                                if (!isPreview) {
                                    AudioSynthesizer.stopMelody()
                                }
                                isPlayingDemoSong = false
                            }
                    ) {
                        Box(
                            modifier = Modifier.padding(vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = label,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else Color(0xFF475569)
                            )
                        }
                    }
                }
            }
        }

        // --- Active Key Indicator Display ---
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = SunshineYellow.copy(alpha = 0.25f)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.MusicNote,
                        contentDescription = null,
                        tint = SkyBluePrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = when {
                            isPlayingDemoSong -> "A tocar Melodia Mágica... 🎶"
                            lastPlayedKey != null -> "Nota tocada: $lastPlayedKey 🎶"
                            else -> "Toca em qualquer nota para ouvir o som!"
                        },
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )
                }

                if (isPlayingDemoSong) {
                    Surface(
                        shape = CircleShape,
                        color = Color.Red.copy(alpha = 0.15f),
                        modifier = Modifier.clickable {
                            if (!isPreview) {
                                AudioSynthesizer.stopMelody()
                            }
                            isPlayingDemoSong = false
                        }
                    ) {
                        Text(
                            text = "Parar ⏹",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }
            }
        }

        // --- ALL 8 MUSICAL KEYS (Fit automatically on screen) ---
        if (layoutStyle == "keyboard") {
            // Horizontal Piano Keyboard Layout (8 keys side-by-side in a Row)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                pianoKeys.forEachIndexed { index, key ->
                    var isPressed by remember { mutableStateOf(false) }
                    val scale by animateFloatAsState(targetValue = if (isPressed) 0.94f else 1.0f)

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .scale(scale)
                            .testTag("key_note_${key.note.lowercase()}")
                            .clickable {
                                isPressed = true
                                lastPlayedKey = "${key.note} (${key.label})"

                                if (!isPreview) {
                                    AudioSynthesizer.playSynthNote(
                                        freqHz = key.frequencyHz,
                                        durationMs = if (selectedInstrument == "xylophone") 400 else 550,
                                        instrumentType = selectedInstrument
                                    )

                                    if (voiceGuideEnabled) {

                                    }

                                    mainViewModel?.addStars(1)
                                }
                                coroutineScope.launch {
                                    delay(200)
                                    isPressed = false
                                }
                            },
                        shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp, topStart = 6.dp, topEnd = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = key.color),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 8.dp, horizontal = 2.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${index + 1}",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Black.copy(alpha = 0.5f)
                            )

                            Text(
                                text = key.emoji,
                                fontSize = 22.sp
                            )

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = key.note,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF0F172A)
                                )
                                Text(
                                    text = "${key.frequencyHz.toInt()}Hz",
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1E293B).copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                }
            }
        } else {
            // Compact Stacked Bars Layout (8 horizontal bars in a Column that fit on screen)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                pianoKeys.forEachIndexed { index, key ->
                    var isPressed by remember { mutableStateOf(false) }
                    val scale by animateFloatAsState(targetValue = if (isPressed) 0.96f else 1.0f)

                    val barFraction = if (selectedInstrument == "xylophone") {
                        1.0f - (index * 0.028f)
                    } else {
                        1.0f
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(barFraction)
                            .weight(1f)
                            .scale(scale)
                            .testTag("key_note_${key.note.lowercase()}")
                            .clickable {
                                isPressed = true
                                lastPlayedKey = "${key.note} (${key.label})"

                                if (!isPreview) {
                                    AudioSynthesizer.playSynthNote(
                                        freqHz = key.frequencyHz,
                                        durationMs = if (selectedInstrument == "xylophone") 400 else 550,
                                        instrumentType = selectedInstrument
                                    )

                                    if (voiceGuideEnabled) {

                                    }

                                    mainViewModel?.addStars(1)
                                }
                                coroutineScope.launch {
                                    delay(200)
                                    isPressed = false
                                }
                            },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = key.color),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = key.emoji, fontSize = 18.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "${key.note} - ${key.label}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF1E293B)
                                )
                            }

                            Text(
                                text = "${key.frequencyHz.toInt()}Hz",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E293B).copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }
        }

        // --- Demo Songs Shortcuts ---
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Melodias Automáticas: 🎵",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(2.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                val demoSongs = listOf(
                    0 to "Férias ☀️",
                    4 to "ABC 🔤",
                    9 to "É Natal 🎄",
                    1 to "Santos 🎈"
                )
                items(demoSongs) { (songIdx, title) ->
                    Button(
                        onClick = {
                            isPlayingDemoSong = true
                            lastPlayedKey = title
                            AudioSynthesizer.playSongMelody(songIdx, false)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE2E8F0)),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(text = title, fontSize = 11.sp, color = Color(0xFF1E293B), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// --- Game Library Component ---
data class GameLibraryItem(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val emoji: String,
    val accentColor: Color,
    val starReward: String,
    val difficulty: String,
    val targetTab: Int,
    val year: Int = 1,
    val levelsCount: String = "Vários Níveis"
)

@Composable
fun GameLibraryView(
    mainViewModel: MainViewModel? = null,
    onSelectGame: (Int) -> Unit,
    showHeader: Boolean = true,
    selectedYear: Int? = null
) {
    val isPreview = LocalInspectionMode.current
    val userProgressState = mainViewModel?.userProgress?.collectAsState()
    val userProgress = userProgressState?.value
    val starsCount = userProgress?.starsCount ?: 0

    var dailyBonusClaimed by remember { mutableStateOf(false) }

    val gamesList = remember {
        listOf(
            GameLibraryItem(
                id = "detetive_palavras",
                title = "Detetive Palavras 🔍",
                description = "Descobre as letras ocultas!",
                category = "Alfabetização",
                emoji = "🕵️",
                accentColor = Color(0xFFF59E0B),
                starReward = "+10 ⭐",
                difficulty = "Fácil",
                targetTab = 16,
                year = 3,
                levelsCount = "20 Níveis 🏆"
            ),
            GameLibraryItem(
                id = "color_sorting",
                title = "Separação Cores 🎨",
                description = "Organiza nos baldes coloridos!",
                category = "Aprendizagem",
                emoji = "🎨",
                accentColor = Color(0xFFE53935),
                starReward = "+10 ⭐",
                difficulty = "Fácil",
                targetTab = 15,
                year = 1,
                levelsCount = "6 Níveis 🎨"
            ),
            GameLibraryItem(
                id = "puzzle_bubble",
                title = "Puzzle Bubble 🫧",
                description = "Lança bolinhas coloridas!",
                category = "Agilidade",
                emoji = "🫧",
                accentColor = Color(0xFF00BCD4),
                starReward = "+15 ⭐",
                difficulty = "Fácil",
                targetTab = 14,
                year = 3,
                levelsCount = "10 Níveis 🫧"
            ),
            GameLibraryItem(
                id = "memory",
                title = "Jogo da Memória 🧠",
                description = "Encontra os pares mágicos!",
                category = "Raciocínio",
                emoji = "🧠",
                accentColor = SkyBluePrimary,
                starReward = "+10 ⭐",
                difficulty = "Fácil",
                targetTab = 1,
                year = 1,
                levelsCount = "5 Níveis 🧠"
            ),
            GameLibraryItem(
                id = "star_counter",
                title = "Contar Estrelas ⭐",
                description = "Toca nas estrelinhas brilhantes!",
                category = "Números",
                emoji = "⭐",
                accentColor = Color(0xFFEAB308),
                starReward = "+5 ⭐",
                difficulty = "Fácil",
                targetTab = 3,
                year = 1,
                levelsCount = "6 Níveis ⭐"
            ),
            GameLibraryItem(
                id = "balloon_pop",
                title = "Estourar Balões 🎈",
                description = "Rebenta balões mágicos coloridos!",
                category = "Cores",
                emoji = "🎈",
                accentColor = Color(0xFFFF4081),
                starReward = "+6 ⭐",
                difficulty = "Fácil",
                targetTab = 5,
                year = 1,
                levelsCount = "5 Níveis 🎈"
            ),
            GameLibraryItem(
                id = "color_race",
                title = "Corrida Colorida 🏎️",
                description = "Acelera e muda de faixa!",
                category = "Agilidade",
                emoji = "🏎️",
                accentColor = Color(0xFFFF9800),
                starReward = "+12 ⭐",
                difficulty = "Médio",
                targetTab = 9,
                year = 4,
                levelsCount = "6 Níveis 🏎️"
            ),
            GameLibraryItem(
                id = "spot_differences",
                title = "Diferenças 🔎",
                description = "Encontra as diferenças!",
                category = "Raciocínio",
                emoji = "🔎",
                accentColor = Color(0xFF4CAF50),
                starReward = "+15 ⭐",
                difficulty = "Médio",
                targetTab = 8,
                year = 4,
                levelsCount = "12 Níveis 🔎"
            ),
            GameLibraryItem(
                id = "magic_piano",
                title = "Piano & Sinos 🎼",
                description = "Toca nas 8 notas musicais!",
                category = "Música",
                emoji = "🎼",
                accentColor = Color(0xFF9C27B0),
                starReward = "+5 ⭐",
                difficulty = "Livre",
                targetTab = 6,
                year = 1,
                levelsCount = "8 Notas & Canções"
            ),
            GameLibraryItem(
                id = "stop_ze_traquina",
                title = "Stop do Zé 🛑",
                description = "Palavras por categorias!",
                category = "Palavras",
                emoji = "🛑",
                accentColor = Color(0xFFE53935),
                starReward = "+10 ⭐",
                difficulty = "Vários",
                targetTab = 12,
                year = 3,
                levelsCount = "10 Níveis 🛑"
            ),
            GameLibraryItem(
                id = "sopa_de_letras",
                title = "Sopa de Letras 🥣",
                description = "Encontra palavras na grelha!",
                category = "Palavras",
                emoji = "🥣",
                accentColor = Color(0xFF2E7D32),
                starReward = "+8 ⭐",
                difficulty = "Vários",
                targetTab = 13,
                year = 3,
                levelsCount = "8 Níveis 🥣"
            ),
            GameLibraryItem(
                id = "painting",
                title = "Atelier Pintura 🎨",
                description = "Pinta desenhos incríveis!",
                category = "Criatividade",
                emoji = "🎨",
                accentColor = MintGreen,
                starReward = "+5 ⭐",
                difficulty = "Livre",
                targetTab = 2,
                year = 2,
                levelsCount = "Livre & Cores 🎨"
            ),
            GameLibraryItem(
                id = "quiz",
                title = "Quiz do Zé 🧩",
                description = "Perguntas super divertidas!",
                category = "Desafios",
                emoji = "🧩",
                accentColor = KidStarOrange,
                starReward = "+12 ⭐",
                difficulty = "Médio",
                targetTab = 4,
                year = 1,
                levelsCount = "26 Níveis 🧩"
            ),
            GameLibraryItem(
                id = "word_guess",
                title = "Adivinha Palavra 📝",
                description = "Completa as palavras!",
                category = "Palavras",
                emoji = "📝",
                accentColor = Color(0xFFD32F2F),
                starReward = "+10 ⭐",
                difficulty = "Médio",
                targetTab = 7,
                year = 4,
                levelsCount = "30 Níveis 📝"
            ),
            GameLibraryItem(
                id = "tic_tac_toe",
                title = "Jogo do Galo ❌⭕",
                description = "Joga 3 em linha!",
                category = "Raciocínio",
                emoji = "❌",
                accentColor = Color(0xFFD81B60),
                starReward = "+5 ⭐",
                difficulty = "Fácil",
                targetTab = 10,
                year = 2,
                levelsCount = "Desafios ❌⭕"
            ),
            GameLibraryItem(
                id = "sudoku_infantil",
                title = "Sudoku 4x4 🧩",
                description = "Preenche com formas!",
                category = "Raciocínio",
                emoji = "🧩",
                accentColor = Color(0xFF8E24AA),
                starReward = "+5 ⭐",
                difficulty = "Médio",
                targetTab = 11,
                year = 2,
                levelsCount = "16 Níveis 🧩"
            )
        )
    }

    val filteredGames = remember(selectedYear, gamesList) {
        if (selectedYear == null) gamesList else gamesList.filter { it.year == selectedYear }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(start = 14.dp, end = 14.dp, top = 8.dp, bottom = 8.dp)
            .testTag("game_library_component")
    ) {
        // --- Sleek Compact Header Bar (Glassmorphic Style) ---
        if (showHeader) {
            ScreenHeader(
                title = "Parquinho dos Jogos",
                subtitle = "Brinca e diverte-te com o Zé!",
                icon = "🎮",
                gradientColors = listOf(Color(0xFF16A34A), Color(0xFFEAB308))
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        // --- 2 Columns Scrollable Grid matching the aesthetic of the LearnScreen menu ---
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(top = 4.dp, bottom = 86.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(filteredGames) { game ->
                GameLibraryCardCompact(
                    game = game,
                    onPlay = {
                        if (!isPreview) {
                            mainViewModel?.addStars(1)
                        }
                        onSelectGame(game.targetTab)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun GameThemedBackground(accentColor: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val path = androidx.compose.ui.graphics.Path()
        path.moveTo(0f, size.height * 0.7f)
        path.quadraticTo(size.width * 0.4f, size.height, size.width, size.height * 0.5f)
        path.lineTo(size.width, 0f)
        path.lineTo(0f, 0f)
        path.close()
        
        drawPath(
            path = path,
            color = accentColor.copy(alpha = 0.2f)
        )
        
        drawCircle(accentColor.copy(alpha = 0.15f), radius = size.width * 0.25f, center = androidx.compose.ui.geometry.Offset(size.width * 0.85f, size.height * 0.25f))
        drawCircle(accentColor.copy(alpha = 0.12f), radius = size.width * 0.12f, center = androidx.compose.ui.geometry.Offset(size.width * 0.15f, size.height * 0.4f))
    }
}

@Composable
fun GameLibraryCardCompact(
    game: GameLibraryItem,
    onPlay: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val cardScale by animateFloatAsState(
        targetValue = if (isPressed) 1.05f else 1.0f,
        label = "card_scale_${game.id}"
    )

    Card(
        modifier = modifier
            .graphicsLayer {
                scaleX = cardScale
                scaleY = cardScale
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onPlay
            )
            .testTag("game_card_${game.id}"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
        border = BorderStroke(
            2.dp,
            if (isPressed) SunshineYellow else game.accentColor.copy(alpha = 0.7f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isPressed) 8.dp else 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(11.dp)
        ) {
            // Top Row: Emoji in a Circle + Levels & Difficulty Badges
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 3.dp,
                    border = BorderStroke(1.5.dp, game.accentColor),
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = game.emoji, fontSize = 24.sp)
                    }
                }

                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF0F172A).copy(alpha = 0.08f),
                        border = BorderStroke(1.dp, Color(0xFF0F172A).copy(alpha = 0.15f))
                    ) {
                        Text(
                            text = game.levelsCount,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF1E293B),
                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = game.accentColor,
                        shadowElevation = 2.dp
                    ) {
                        Text(
                            text = game.difficulty,
                            fontSize = 8.5.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.5.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Game Title
            Text(
                text = game.title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF0F172A),
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(2.dp))

            // Game Description
            Text(
                text = game.description,
                fontSize = 9.5.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF64748B),
                maxLines = 2,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                lineHeight = 12.sp,
                modifier = Modifier.heightIn(min = 24.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Bottom row: Category pill + Star reward pill
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Category Tag
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = game.accentColor.copy(alpha = 0.15f),
                    border = BorderStroke(1.dp, game.accentColor.copy(alpha = 0.3f))
                ) {
                    Text(
                        text = game.category,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = game.accentColor,
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.5.dp)
                    )
                }

                // Reward Tag
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = SunshineYellow.copy(alpha = 0.2f),
                    border = BorderStroke(1.dp, SunshineYellow.copy(alpha = 0.6f))
                ) {
                    Text(
                        text = game.starReward,
                        fontSize = 8.5.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFFB56A00),
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.5.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun GameLibraryCard(
    game: GameLibraryItem,
    onPlay: () -> Unit
) {
    GameLibraryCardCompact(game = game, onPlay = onPlay)
}

// --- PREVIEWS WITH MOCK DATA ---

@Preview(showBackground = true)
@Composable
fun GamesScreenPreview(
    @PreviewParameter(com.example.ui.util.GamePreviewParameterProvider::class) mockData: com.example.ui.util.GamePreviewMockData
) {
    PreviewAppTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE2E8F0))
                    .padding(6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Visualização de Jogo: ${mockData.title} | Nível: ${mockData.level} | Jogador: ${mockData.playerName}",
                    fontSize = 11.sp,
                    color = Color(0xFF475569),
                    fontWeight = FontWeight.Bold
                )
            }
            GamesScreen(mainViewModel = null)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameLibraryCardPreview(
    @PreviewParameter(GameCardPreviewParameterProvider::class) game: GameLibraryItem
) {
    PreviewAppTheme {
        GameLibraryCard(game = game, onPlay = {})
    }
}

@Preview(showBackground = true)
@Composable
fun MemoryGamePreview() {
    PreviewAppTheme {
        MemoryGameView(mainViewModel = null, gamesViewModel = remember { GamesViewModel() })
    }
}

@Preview(showBackground = true)
@Composable
fun DrawingGamePreview() {
    PreviewAppTheme {
        DrawingGameView(mainViewModel = null, gamesViewModel = remember { GamesViewModel() })
    }
}

@Preview(showBackground = true)
@Composable
fun StarCountingGamePreview() {
    PreviewAppTheme {
        StarCountingGameView(mainViewModel = null, gamesViewModel = remember { GamesViewModel() })
    }
}

@Preview(showBackground = true)
@Composable
fun QuizGamePreview() {
    PreviewAppTheme {
        QuizGameView(mainViewModel = null)
    }
}

@Preview(showBackground = true)
@Composable
fun BalloonPopGamePreview() {
    PreviewAppTheme {
        BalloonPopGameView(mainViewModel = null)
    }
}

@Preview(showBackground = true)
@Composable
fun MagicPianoGamePreview() {
    PreviewAppTheme {
        MagicPianoGameView(mainViewModel = null)
    }
}

@Composable
fun GeminiGameDifficultyBanner(
    mainViewModel: MainViewModel? = null,
    onSelectGame: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (mainViewModel == null) return
    val adaptiveAnalysis by mainViewModel.adaptiveAnalysisState.collectAsState()
    val analysis = adaptiveAnalysis ?: return

    val diffColor = when (analysis.recommendedDifficulty.lowercase()) {
        "fácil", "facil" -> Color(0xFF4CAF50)
        "médio", "medio" -> SkyBluePrimary
        "desafiante" -> KidStarOrange
        "avançado", "avancado" -> Color(0xFF9C27B0)
        else -> SkyBluePrimary
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
        border = BorderStroke(1.5.dp, Brush.horizontalGradient(listOf(Color(0xFF00E5FF), Color(0xFF9C27B0)))),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(listOf(Color(0xFF00E5FF), Color(0xFF7C4DFF)))),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "AI",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Dificuldade Ajustada ZéAI:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = diffColor.copy(alpha = 0.15f),
                    border = BorderStroke(1.dp, diffColor)
                ) {
                    Text(
                        text = "🎯 ${analysis.recommendedDifficulty.uppercase()}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = diffColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = analysis.difficultyExplanation,
                fontSize = 11.sp,
                color = Color(0xFF475569),
                lineHeight = 15.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Recomendado para Ti:",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                    Text(
                        text = analysis.suggestedNextActivity,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = SkyBluePrimary
                    )
                }

                Button(
                    onClick = {
                        val targetTab = when {
                            analysis.suggestedNextActivity.contains("Detetive", ignoreCase = true) -> 16
                            analysis.suggestedNextActivity.contains("Sopa", ignoreCase = true) -> 13
                            analysis.suggestedNextActivity.contains("Estrela", ignoreCase = true) -> 3
                            analysis.suggestedNextActivity.contains("Corrida", ignoreCase = true) -> 9
                            analysis.suggestedNextActivity.contains("Sudoku", ignoreCase = true) -> 11
                            else -> 1
                        }
                        onSelectGame(targetTab)
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Jogar", modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Jogar", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
