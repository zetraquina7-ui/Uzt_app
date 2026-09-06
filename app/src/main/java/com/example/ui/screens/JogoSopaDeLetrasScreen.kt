package com.example.ui.screens

import com.example.ui.theme.PreviewAppTheme

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
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
import kotlinx.coroutines.launch
import kotlin.random.Random

data class SopaLevel(
    val levelNumber: Int,
    val title: String,
    val subtitle: String,
    val gridSize: Int,
    val color: Color,
    val words: List<String>
)

val sopaLevels = listOf(
    SopaLevel(
        levelNumber = 1,
        title = "Nível 1 (6x6)",
        subtitle = "Palavras Fáceis",
        gridSize = 6,
        color = Color(0xFF4CAF50),
        words = listOf("GATO", "BOLA", "CASA", "PATO")
    ),
    SopaLevel(
        levelNumber = 2,
        title = "Nível 2 (6x6)",
        subtitle = "Novos Desafios",
        gridSize = 6,
        color = Color(0xFF8BC34A),
        words = listOf("RATO", "LUA", "SOL", "PEIXE")
    ),
    SopaLevel(
        levelNumber = 3,
        title = "Nível 3 (8x8)",
        subtitle = "Nível Médio",
        gridSize = 8,
        color = Color(0xFFFF9800),
        words = listOf("ZEBRA", "CARRO", "LIVRO", "FLOR", "MESA")
    ),
    SopaLevel(
        levelNumber = 4,
        title = "Nível 4 (8x8)",
        subtitle = "Aventura Média",
        gridSize = 8,
        color = Color(0xFFFF5722),
        words = listOf("ESCOLA", "AMIGO", "JOGO", "AGUA", "FESTA")
    ),
    SopaLevel(
        levelNumber = 5,
        title = "Nível 5 (10x10)",
        subtitle = "Nível Avançado",
        gridSize = 10,
        color = Color(0xFFE91E63),
        words = listOf("AVIAO", "ESTRELA", "JANELA", "BOTAO", "LIVRO")
    )
)

data class PlacedWordInfo(
    val word: String,
    val cells: List<Pair<Int, Int>>
)

@Composable
fun JogoSopaDeLetrasScreen(
    mainViewModel: MainViewModel? = null,
    onBack: () -> Unit
) {
    val isPreview = LocalInspectionMode.current
    val coroutineScope = rememberCoroutineScope()

    var gameStarted by remember { mutableStateOf(false) }
    var currentLevelIndex by remember { mutableIntStateOf(0) }
    var gridSize by remember { mutableIntStateOf(6) }
    var gridLetters by remember { mutableStateOf<List<List<Char>>>(emptyList()) }
    var wordList by remember { mutableStateOf<List<String>>(emptyList()) }
    var placedWordsInfo by remember { mutableStateOf<List<PlacedWordInfo>>(emptyList()) }
    var foundWords by remember { mutableStateOf<Set<String>>(emptySet()) }
    var foundCells by remember { mutableStateOf<Set<Pair<Int, Int>>>(emptySet()) }

    // Selection state for tapping letter cells
    val selectedCells = remember { mutableStateListOf<Pair<Int, Int>>() }
    var statusMessage by remember { mutableStateOf("Toca nas letras para encontrar as palavras!") }
    var currentLevelConfig by remember { mutableStateOf(sopaLevels[0]) }

    // Generate puzzle grid for a given level
    fun loadLevel(index: Int) {
        val safeIndex = index % sopaLevels.size
        currentLevelIndex = safeIndex
        val level = sopaLevels[safeIndex]
        currentLevelConfig = level
        gridSize = level.gridSize
        wordList = level.words
        foundWords = emptySet()
        foundCells = emptySet()
        selectedCells.clear()
        statusMessage = "${level.title}: Toca nas letras para encontrar as palavras!"

        val size = level.gridSize
        val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val matrix = Array(size) { CharArray(size) { ' ' } }
        val placedList = mutableListOf<PlacedWordInfo>()

        // Place words guaranteed in matrix (Horizontal or Vertical) with collision check
        for (word in level.words) {
            var placed = false
            var attempts = 0
            var wordCells = listOf<Pair<Int, Int>>()

            while (!placed && attempts < 100) {
                attempts++
                val isVertical = Random.nextBoolean()
                if (isVertical) {
                    val startRow = Random.nextInt(size - word.length + 1)
                    val startCol = Random.nextInt(size)
                    var canFit = true
                    val tempCells = mutableListOf<Pair<Int, Int>>()
                    for (i in word.indices) {
                        val current = matrix[startRow + i][startCol]
                        if (current != ' ' && current != word[i]) {
                            canFit = false
                            break
                        }
                        tempCells.add(Pair(startRow + i, startCol))
                    }
                    if (canFit) {
                        for (i in word.indices) {
                            matrix[startRow + i][startCol] = word[i]
                        }
                        wordCells = tempCells
                        placed = true
                    }
                } else {
                    val startRow = Random.nextInt(size)
                    val startCol = Random.nextInt(size - word.length + 1)
                    var canFit = true
                    val tempCells = mutableListOf<Pair<Int, Int>>()
                    for (i in word.indices) {
                        val current = matrix[startRow][startCol + i]
                        if (current != ' ' && current != word[i]) {
                            canFit = false
                            break
                        }
                        tempCells.add(Pair(startRow, startCol + i))
                    }
                    if (canFit) {
                        for (i in word.indices) {
                            matrix[startRow][startCol + i] = word[i]
                        }
                        wordCells = tempCells
                        placed = true
                    }
                }
            }
            if (!placed) {
                val tempCells = mutableListOf<Pair<Int, Int>>()
                for (i in word.indices) {
                    if (i < size) {
                        matrix[0][i] = word[i]
                        tempCells.add(Pair(0, i))
                    }
                }
                wordCells = tempCells
            }
            placedList.add(PlacedWordInfo(word = word, cells = wordCells))
        }
        placedWordsInfo = placedList

        // Fill remaining spaces with random uppercase letters
        for (r in 0 until size) {
            for (c in 0 until size) {
                if (matrix[r][c] == ' ') {
                    matrix[r][c] = alphabet.random()
                }
            }
        }

        // Convert to List<List<Char>> for Compose state
        val listGrid = mutableListOf<List<Char>>()
        for (r in 0 until size) {
            val rowList = mutableListOf<Char>()
            for (c in 0 until size) {
                rowList.add(matrix[r][c])
            }
            listGrid.add(rowList)
        }
        gridLetters = listGrid

        if (!isPreview) {
        }
    }

    fun startGame() {
        gameStarted = true
        loadLevel(0)
    }

    // Check if selected cells form any word automatically
    fun evaluateSelection() {
        if (selectedCells.isEmpty()) return

        val formedWord = selectedCells.joinToString("") { (r, c) ->
            gridLetters[r][c].toString()
        }

        val reversedWord = selectedCells.reversed().joinToString("") { (r, c) ->
            gridLetters[r][c].toString()
        }

        val matchedInfo = placedWordsInfo.find { 
            !foundWords.contains(it.word) && (it.word == formedWord || it.word == reversedWord)
        }

        if (matchedInfo != null) {
            foundWords = foundWords + matchedInfo.word
            foundCells = foundCells + matchedInfo.cells
            selectedCells.clear()
            statusMessage = "Boa! Encontraste \"${matchedInfo.word}\"!"

            if (!isPreview) {
                mainViewModel?.addStars(3)
                mainViewModel?.playSound(R.raw.ze_traquina_e_fixe)
            }

            if (foundWords.size == wordList.size) {
                statusMessage = "🎉 Nível ${currentLevelConfig.levelNumber} Concluído! A avançar..."
                if (!isPreview) {
                    mainViewModel?.addStars(10)
                }

                // Automatically advance to next level after 2 seconds
                coroutineScope.launch {
                    delay(2000)
                    loadLevel(currentLevelIndex + 1)
                }
            }
        } else {
            val maxLen = wordList.maxOfOrNull { it.length } ?: 6
            val isPrefix = placedWordsInfo.any { info ->
                !foundWords.contains(info.word) && (info.word.startsWith(formedWord) || info.word.reversed().startsWith(formedWord))
            }
            
            if (!isPrefix || selectedCells.size > maxLen) {
                statusMessage = "Tenta outra vez!"
                selectedCells.clear()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        // --- Top Bar ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    if (gameStarted) {
                        gameStarted = false
                    } else {
                        onBack()
                    }
                },
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

            Text(
                text = "Sopa de Letras 🥣🔤",
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground
            )

            if (gameStarted) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(
                        onClick = {
                            val unfoundInfo = placedWordsInfo.find { !foundWords.contains(it.word) }
                            if (unfoundInfo != null && unfoundInfo.cells.isNotEmpty()) {
                                val firstCell = unfoundInfo.cells.first()
                                if (!selectedCells.contains(firstCell)) {
                                    selectedCells.add(firstCell)
                                }
                                statusMessage = "Dica: A palavra \"${unfoundInfo.word}\" começa na letra ${gridLetters[firstCell.first][firstCell.second]}!"
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SunshineYellow),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                        modifier = Modifier.padding(end = 4.dp)
                    ) {
                        Text("💡 Dica", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black)
                    }

                    IconButton(
                        onClick = { loadLevel(currentLevelIndex) },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Reiniciar Nível",
                            tint = SkyBluePrimary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            } else {
                Spacer(modifier = Modifier.size(36.dp))
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // --- SCREEN 1: START MENU ---
        if (!gameStarted) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ZeTraquinaMascot(
                    size = 90.dp,
                    showSpeechBubble = false,
                    emotion = MascotEmotion.HAPPY
                )
                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Aventura Sopa de Letras",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Encontra as palavras! Cada nível fica mais difícil.",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { startGame() },
                    colors = ButtonDefaults.buttonColors(containerColor = MintGreen),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 28.dp, vertical = 14.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        tint = Color(0xFF166534),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "JOGAR AVENTURA 🚀",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF166534)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Level selection pills
                Text(
                    text = "Ou escolhe um nível específico:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally)
                ) {
                    sopaLevels.forEach { lvl ->
                        OutlinedButton(
                            onClick = {
                                gameStarted = true
                                loadLevel(lvl.levelNumber - 1)
                            },
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(2.dp, lvl.color),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = lvl.color),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Nível ${lvl.levelNumber}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        } else {
            // --- SCREEN 2: ACTIVE GAME & PROGRESSIVE LEVELS ---
            // Level badge & Status Message
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = currentLevelConfig.color.copy(alpha = 0.2f),
                    border = BorderStroke(1.dp, currentLevelConfig.color)
                ) {
                    Text(
                        text = "⭐ ${currentLevelConfig.title}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = currentLevelConfig.color,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Text(
                    text = "Progresso: ${foundWords.size}/${wordList.size}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = if (foundWords.size == wordList.size) MintGreen else SkyBluePrimary.copy(alpha = 0.15f)
            ) {
                Text(
                    text = statusMessage,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (foundWords.size == wordList.size) Color(0xFF166534) else MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Selected Word Preview
            val currentSelectedWord = selectedCells.joinToString("") { (r, c) ->
                gridLetters.getOrNull(r)?.getOrNull(c)?.toString() ?: ""
            }
            if (currentSelectedWord.isNotEmpty()) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = SunshineYellow.copy(alpha = 0.3f),
                    border = BorderStroke(1.dp, SunshineYellow)
                ) {
                    Text(
                        text = "A Selecionar: $currentSelectedWord",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }

            // Grid Board
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .aspectRatio(1f)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = BorderStroke(3.dp, currentLevelConfig.color),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(6.dp),
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    for (r in 0 until gridSize) {
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            for (c in 0 until gridSize) {
                                val letter = gridLetters.getOrNull(r)?.getOrNull(c) ?: ' '
                                val pos = Pair(r, c)
                                val isSelected = selectedCells.contains(pos)
                                val isFoundCell = foundCells.contains(pos)

                                val bgColor = when {
                                    isFoundCell -> MintGreen
                                    isSelected -> SunshineYellow
                                    else -> Color.White
                                }

                                val borderColor = when {
                                    isFoundCell -> Color(0xFF166534)
                                    isSelected -> Color.Black
                                    else -> Color.LightGray
                                }

                                Card(
                                    onClick = {
                                        if (!isFoundCell && foundWords.size < wordList.size) {
                                            if (selectedCells.contains(pos)) {
                                                selectedCells.remove(pos)
                                            } else {
                                                selectedCells.add(pos)
                                                evaluateSelection()
                                            }
                                        }
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    shape = RoundedCornerShape(6.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = bgColor
                                    ),
                                    border = BorderStroke(
                                        width = if (isSelected || isFoundCell) 2.dp else 1.dp,
                                        color = borderColor
                                    )
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = letter.toString(),
                                            fontSize = if (gridSize <= 6) 18.sp else if (gridSize <= 8) 14.sp else 12.sp,
                                            fontWeight = FontWeight.Black,
                                            color = if (isFoundCell) Color(0xFF166534) else if (isSelected) Color.Black else Color.DarkGray
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Words to find list header
            Text(
                text = "Palavras para encontrar:",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Words pills row / wrap
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                wordList.forEach { word ->
                    val isFound = foundWords.contains(word)
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isFound) MintGreen else MaterialTheme.colorScheme.surfaceVariant,
                        border = BorderStroke(1.dp, if (isFound) Color(0xFF166534) else Color.LightGray)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (isFound) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = Color(0xFF166534),
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                            }
                            Text(
                                text = word,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                textDecoration = if (isFound) TextDecoration.LineThrough else TextDecoration.None,
                                color = if (isFound) Color(0xFF166534) else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Bottom Controls & Mascot
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ZeTraquinaMascot(
                    size = 50.dp,
                    showSpeechBubble = false,
                    emotion = if (foundWords.size == wordList.size) MascotEmotion.CELEBRATING else MascotEmotion.HAPPY,
                    onInteract = {}
                )

                if (foundWords.size == wordList.size) {
                    Button(
                        onClick = { loadLevel(currentLevelIndex + 1) },
                        colors = ButtonDefaults.buttonColors(containerColor = SunshineYellow),
                        shape = RoundedCornerShape(14.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "🚀 PRÓXIMO NÍVEL (NÍVEL ${currentLevelConfig.levelNumber + 1} - MAIS DIFÍCIL!)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.Black
                        )
                    }
                } else {
                    Button(
                        onClick = { selectedCells.clear() },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Limpar ❌",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        if (foundWords.size == wordList.size && wordList.isNotEmpty()) {
            ConfettiEffect()
        }
    }
}
}

@Preview(showBackground = true)
@Composable
fun JogoSopaDeLetrasPreview(
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
            JogoSopaDeLetrasScreen(mainViewModel = null, onBack = {})
        }
    }
}
