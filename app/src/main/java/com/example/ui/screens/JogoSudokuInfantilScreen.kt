package com.example.ui.screens

import com.example.ui.theme.PreviewAppTheme
import androidx.compose.ui.graphics.Brush

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
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

data class SudokuShapeOption(
    val id: Int,
    val emoji: String,
    val name: String,
    val color: Color
)

val sudokuShapeOptions = listOf(
    SudokuShapeOption(1, "⭐", "Estrela", Color(0xFFE53935)),
    SudokuShapeOption(2, "🔵", "Círculo", Color(0xFF1E88E5)),
    SudokuShapeOption(3, "🟩", "Quadrado", Color(0xFF43A047)),
    SudokuShapeOption(4, "🔺", "Triângulo", Color(0xFFFFB300))
)

data class SudokuLevel(
    val id: Int,
    val name: String,
    val difficulty: String,
    val initialBoard: Array<IntArray>,
    val solutionBoard: Array<IntArray>
)

val sudokuLevels = listOf(
    // Nível 1 - Muito Fácil (7 Pistas)
    SudokuLevel(
        id = 1,
        name = "Nível 1",
        difficulty = "Muito Fácil ⭐",
        initialBoard = arrayOf(
            intArrayOf(1, 0, 3, 4),
            intArrayOf(3, 4, 0, 2),
            intArrayOf(0, 1, 4, 3),
            intArrayOf(4, 3, 2, 0)
        ),
        solutionBoard = arrayOf(
            intArrayOf(1, 2, 3, 4),
            intArrayOf(3, 4, 1, 2),
            intArrayOf(2, 1, 4, 3),
            intArrayOf(4, 3, 2, 1)
        )
    ),
    // Nível 2 - Fácil (6 Pistas)
    SudokuLevel(
        id = 2,
        name = "Nível 2",
        difficulty = "Fácil ⭐⭐",
        initialBoard = arrayOf(
            intArrayOf(1, 2, 0, 4),
            intArrayOf(0, 4, 1, 0),
            intArrayOf(0, 1, 4, 0),
            intArrayOf(4, 0, 0, 1)
        ),
        solutionBoard = arrayOf(
            intArrayOf(1, 2, 3, 4),
            intArrayOf(3, 4, 1, 2),
            intArrayOf(2, 1, 4, 3),
            intArrayOf(4, 3, 2, 1)
        )
    ),
    // Nível 3 - Médio (5 Pistas)
    SudokuLevel(
        id = 3,
        name = "Nível 3",
        difficulty = "Médio 🌟",
        initialBoard = arrayOf(
            intArrayOf(0, 2, 4, 0),
            intArrayOf(1, 0, 0, 3),
            intArrayOf(4, 0, 0, 2),
            intArrayOf(0, 3, 1, 0)
        ),
        solutionBoard = arrayOf(
            intArrayOf(3, 2, 4, 1),
            intArrayOf(1, 4, 2, 3),
            intArrayOf(4, 1, 3, 2),
            intArrayOf(2, 3, 1, 4)
        )
    ),
    // Nível 4 - Médio (5 Pistas)
    SudokuLevel(
        id = 4,
        name = "Nível 4",
        difficulty = "Médio 🌟🌟",
        initialBoard = arrayOf(
            intArrayOf(4, 0, 2, 0),
            intArrayOf(0, 3, 0, 1),
            intArrayOf(3, 0, 1, 0),
            intArrayOf(0, 2, 0, 4)
        ),
        solutionBoard = arrayOf(
            intArrayOf(4, 1, 2, 3),
            intArrayOf(2, 3, 4, 1),
            intArrayOf(3, 4, 1, 2),
            intArrayOf(1, 2, 3, 4)
        )
    ),
    // Nível 5 - Desafio (4 Pistas)
    SudokuLevel(
        id = 5,
        name = "Nível 5",
        difficulty = "Desafio 🔥",
        initialBoard = arrayOf(
            intArrayOf(1, 0, 0, 2),
            intArrayOf(0, 2, 1, 0),
            intArrayOf(0, 1, 2, 0),
            intArrayOf(2, 0, 0, 1)
        ),
        solutionBoard = arrayOf(
            intArrayOf(1, 4, 3, 2),
            intArrayOf(3, 2, 1, 4),
            intArrayOf(4, 1, 2, 3),
            intArrayOf(2, 3, 4, 1)
        )
    ),
    // Nível 6 - Avançado (4 Pistas)
    SudokuLevel(
        id = 6,
        name = "Nível 6",
        difficulty = "Avançado 🔥🔥",
        initialBoard = arrayOf(
            intArrayOf(0, 4, 1, 0),
            intArrayOf(1, 0, 0, 4),
            intArrayOf(0, 1, 4, 0),
            intArrayOf(4, 0, 0, 1)
        ),
        solutionBoard = arrayOf(
            intArrayOf(2, 4, 1, 3),
            intArrayOf(1, 3, 2, 4),
            intArrayOf(3, 1, 4, 2),
            intArrayOf(4, 2, 3, 1)
        )
    ),
    // Nível 7 - Mestre (3 Pistas)
    SudokuLevel(
        id = 7,
        name = "Nível 7",
        difficulty = "Mestre 🏆",
        initialBoard = arrayOf(
            intArrayOf(3, 0, 0, 0),
            intArrayOf(0, 4, 0, 1),
            intArrayOf(0, 3, 4, 0),
            intArrayOf(0, 0, 0, 3)
        ),
        solutionBoard = arrayOf(
            intArrayOf(3, 1, 2, 4),
            intArrayOf(2, 4, 3, 1),
            intArrayOf(1, 3, 4, 2),
            intArrayOf(4, 2, 1, 3)
        )
    ),
    // Nível 8 - Zé Traquina (3 Pistas)
    SudokuLevel(
        id = 8,
        name = "Nível 8",
        difficulty = "Super Zé 👑",
        initialBoard = arrayOf(
            intArrayOf(4, 0, 0, 2),
            intArrayOf(0, 2, 0, 0),
            intArrayOf(0, 0, 3, 0),
            intArrayOf(3, 0, 0, 4)
        ),
        solutionBoard = arrayOf(
            intArrayOf(4, 3, 1, 2),
            intArrayOf(1, 2, 4, 3),
            intArrayOf(2, 4, 3, 1),
            intArrayOf(3, 1, 2, 4)
        )
    ),
    // Nível 9 - Fácil/Médio (6 Pistas)
    SudokuLevel(
        id = 9,
        name = "Nível 9",
        difficulty = "Aventura 🌟",
        initialBoard = arrayOf(
            intArrayOf(2, 0, 4, 3),
            intArrayOf(4, 0, 0, 1),
            intArrayOf(1, 0, 3, 0),
            intArrayOf(0, 4, 1, 2)
        ),
        solutionBoard = arrayOf(
            intArrayOf(2, 1, 4, 3),
            intArrayOf(4, 3, 2, 1),
            intArrayOf(1, 2, 3, 4),
            intArrayOf(3, 4, 1, 2)
        )
    ),
    // Nível 10 - Médio (5 Pistas)
    SudokuLevel(
        id = 10,
        name = "Nível 10",
        difficulty = "Desafio do Zé 💥",
        initialBoard = arrayOf(
            intArrayOf(0, 4, 1, 0),
            intArrayOf(1, 0, 3, 4),
            intArrayOf(2, 1, 0, 0),
            intArrayOf(0, 3, 0, 1)
        ),
        solutionBoard = arrayOf(
            intArrayOf(3, 4, 1, 2),
            intArrayOf(1, 2, 3, 4),
            intArrayOf(2, 1, 4, 3),
            intArrayOf(4, 3, 2, 1)
        )
    ),
    // Nível 11 - Difícil (4 Pistas)
    SudokuLevel(
        id = 11,
        name = "Nível 11",
        difficulty = "Brilhante 💎",
        initialBoard = arrayOf(
            intArrayOf(1, 0, 0, 2),
            intArrayOf(0, 2, 1, 0),
            intArrayOf(2, 0, 0, 1),
            intArrayOf(0, 1, 0, 4)
        ),
        solutionBoard = arrayOf(
            intArrayOf(1, 3, 4, 2),
            intArrayOf(4, 2, 1, 3),
            intArrayOf(2, 4, 3, 1),
            intArrayOf(3, 1, 2, 4)
        )
    ),
    // Nível 12 - Especial (4 Pistas)
    SudokuLevel(
        id = 12,
        name = "Nível 12",
        difficulty = "Mágico 🔮",
        initialBoard = arrayOf(
            intArrayOf(4, 0, 3, 0),
            intArrayOf(0, 1, 0, 4),
            intArrayOf(1, 0, 4, 0),
            intArrayOf(0, 4, 0, 3)
        ),
        solutionBoard = arrayOf(
            intArrayOf(4, 2, 3, 1),
            intArrayOf(3, 1, 2, 4),
            intArrayOf(1, 3, 4, 2),
            intArrayOf(2, 4, 1, 3)
        )
    ),
    // Nível 13 - Mestre (3 Pistas)
    SudokuLevel(
        id = 13,
        name = "Nível 13",
        difficulty = "Mestre 👑",
        initialBoard = arrayOf(
            intArrayOf(3, 0, 0, 0),
            intArrayOf(0, 2, 1, 0),
            intArrayOf(0, 3, 2, 0),
            intArrayOf(0, 0, 0, 1)
        ),
        solutionBoard = arrayOf(
            intArrayOf(3, 1, 4, 2),
            intArrayOf(4, 2, 1, 3),
            intArrayOf(1, 3, 2, 4),
            intArrayOf(2, 4, 3, 1)
        )
    ),
    // Nível 14 - Campeão (3 Pistas)
    SudokuLevel(
        id = 14,
        name = "Nível 14",
        difficulty = "Campeão do Zé 🏆",
        initialBoard = arrayOf(
            intArrayOf(0, 4, 0, 1),
            intArrayOf(3, 0, 0, 0),
            intArrayOf(0, 0, 1, 3),
            intArrayOf(1, 0, 0, 0)
        ),
        solutionBoard = arrayOf(
            intArrayOf(2, 4, 3, 1),
            intArrayOf(3, 1, 2, 4),
            intArrayOf(4, 2, 1, 3),
            intArrayOf(1, 3, 4, 2)
        )
    ),
    // Nível 15 - Lendário
    SudokuLevel(
        id = 15,
        name = "Nível 15",
        difficulty = "Lendário 🌟",
        initialBoard = arrayOf(
            intArrayOf(0, 3, 0, 1),
            intArrayOf(4, 0, 2, 0),
            intArrayOf(0, 4, 0, 2),
            intArrayOf(1, 0, 3, 0)
        ),
        solutionBoard = arrayOf(
            intArrayOf(2, 3, 4, 1),
            intArrayOf(4, 1, 2, 3),
            intArrayOf(3, 4, 1, 2),
            intArrayOf(1, 2, 3, 4)
        )
    ),
    // Nível 16 - Grão-Mestre Zé Traquina
    SudokuLevel(
        id = 16,
        name = "Nível 16",
        difficulty = "Grão-Mestre 👑✨",
        initialBoard = arrayOf(
            intArrayOf(0, 0, 3, 2),
            intArrayOf(2, 0, 0, 4),
            intArrayOf(3, 0, 0, 1),
            intArrayOf(1, 2, 0, 0)
        ),
        solutionBoard = arrayOf(
            intArrayOf(4, 1, 3, 2),
            intArrayOf(2, 3, 1, 4),
            intArrayOf(3, 4, 2, 1),
            intArrayOf(1, 2, 4, 3)
        )
    )
)

@Composable
fun JogoSudokuInfantilScreen(
    mainViewModel: MainViewModel? = null,
    onBack: () -> Unit
) {
    val isPreview = LocalInspectionMode.current

    var currentLevelIndex by remember { mutableIntStateOf(0) }
    val level = sudokuLevels[currentLevelIndex]

    // Track completed levels
    val completedLevels = remember { mutableStateListOf<Int>() }

    // User board state (4x4 array of shape IDs 1-4, 0 = empty)
    var userBoard by remember(currentLevelIndex) {
        mutableStateOf(Array(4) { row -> level.initialBoard[row].clone() })
    }

    var selectedShapeId by remember { mutableIntStateOf(1) }
    var isWon by remember { mutableStateOf(false) }
    var hasConflict by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf("Toca nos quadrados vazios para colocar a forma escolhida!") }

    // Helper function to check 4x4 Sudoku validity & completeness
    fun checkBoardValidity(board: Array<IntArray>): Pair<Boolean, Boolean> {
        var isFilled = true
        for (r in 0..3) {
            for (c in 0..3) {
                if (board[r][c] == 0) isFilled = false
            }
        }

        // Check rows
        for (r in 0..3) {
            val seen = mutableSetOf<Int>()
            for (c in 0..3) {
                val valAt = board[r][c]
                if (valAt != 0) {
                    if (seen.contains(valAt)) return Pair(false, false)
                    seen.add(valAt)
                }
            }
        }

        // Check cols
        for (c in 0..3) {
            val seen = mutableSetOf<Int>()
            for (r in 0..3) {
                val valAt = board[r][c]
                if (valAt != 0) {
                    if (seen.contains(valAt)) return Pair(false, false)
                    seen.add(valAt)
                }
            }
        }

        // Check 2x2 blocks
        val blocks = listOf(
            listOf(0 to 0, 0 to 1, 1 to 0, 1 to 1),
            listOf(0 to 2, 0 to 3, 1 to 2, 1 to 3),
            listOf(2 to 0, 2 to 1, 3 to 0, 3 to 1),
            listOf(2 to 2, 2 to 3, 3 to 2, 3 to 3)
        )

        for (block in blocks) {
            val seen = mutableSetOf<Int>()
            for ((r, c) in block) {
                val valAt = board[r][c]
                if (valAt != 0) {
                    if (seen.contains(valAt)) return Pair(false, false)
                    seen.add(valAt)
                }
            }
        }

        return Pair(true, isFilled)
    }

    fun handleCellClick(row: Int, col: Int) {
        if (level.initialBoard[row][col] != 0 || isWon) return

        val newBoard = Array(4) { r -> userBoard[r].clone() }
        // Toggle off if already matches selectedShapeId
        if (newBoard[row][col] == selectedShapeId) {
            newBoard[row][col] = 0
        } else {
            newBoard[row][col] = selectedShapeId
        }
        userBoard = newBoard

        val (isValid, isFilled) = checkBoardValidity(newBoard)
        hasConflict = !isValid

        if (!isValid) {
            statusMessage = "⚠️ Repetido! Não pode haver formas iguais na mesma linha, coluna ou bloco 2x2!"
            if (!isPreview) {
                mainViewModel?.speak("Atenção amiguinho, forma repetida!") {}
            }
        } else if (isFilled) {
            isWon = true
            if (!completedLevels.contains(level.id)) {
                completedLevels.add(level.id)
            }
            statusMessage = "🎉 FANTÁSTICO! Resolveste o ${level.name}! ⭐ +5 Estrelas!"
            if (!isPreview) {
                mainViewModel?.addStars(5)
                mainViewModel?.speak("Espetacular! Resolveste o sudoku! Parabéns!") {}
                mainViewModel?.playSound(R.raw.ze_traquina_e_fixe)
            }
        } else {
            statusMessage = "Muito bem! Continua a preencher os quadrados!"
        }
    }

    fun resetCurrentGame() {
        userBoard = Array(4) { row -> level.initialBoard[row].clone() }
        isWon = false
        hasConflict = false
        statusMessage = "Toca num quadrado vazio para colocar a forma escolhida!"
    }

    LaunchedEffect(currentLevelIndex) {
        if (!isPreview) {
            mainViewModel?.speak("Iniciando ${level.name}. Nível de dificuldade ${level.difficulty}!") {}
        }
    }

    LaunchedEffect(isWon, currentLevelIndex) {
        if (isWon) {
            kotlinx.coroutines.delay(2200)
            isWon = false
            currentLevelIndex = (currentLevelIndex + 1) % sudokuLevels.size
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFECFDF5), Color(0xFFD1FAE5)) // Minty-green garden theme
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        // --- Top Navigation Header ---
        Row(
            modifier = Modifier.fillMaxWidth(),
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

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Sudoku Infantil 🧩",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF065F46)
                )
                Text(
                    text = level.difficulty,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF047857)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = {
                        if (!isWon) {
                            var filled = false
                            for (r in 0..3) {
                                for (c in 0..3) {
                                    if (userBoard[r][c] == 0) {
                                        val newBoard = Array(4) { row -> userBoard[row].clone() }
                                        newBoard[r][c] = level.solutionBoard[r][c]
                                        userBoard = newBoard
                                        filled = true
                                        break
                                    }
                                }
                                if (filled) break
                            }
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
                    onClick = { resetCurrentGame() },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reiniciar",
                        tint = Color(0xFF059669),
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // --- Level Selector Bar (Nível 1..14) ---
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            contentPadding = PaddingValues(horizontal = 2.dp, vertical = 4.dp)
        ) {
            itemsIndexed(sudokuLevels) { index, item ->
                val isSelected = currentLevelIndex == index
                val isCompleted = completedLevels.contains(item.id)

                Surface(
                    onClick = {
                        currentLevelIndex = index
                        resetCurrentGame()
                    },
                    shape = RoundedCornerShape(14.dp),
                    color = when {
                        isSelected -> Color(0xFF059669)
                        isCompleted -> Color(0xFFA7F3D0)
                        else -> Color.White
                    },
                    border = BorderStroke(
                        width = if (isSelected) 2.dp else 1.dp,
                        color = if (isSelected) SunshineYellow else Color(0xFFCBD5E1)
                    ),
                    shadowElevation = if (isSelected) 3.dp else 1.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isCompleted) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Concluído",
                                tint = Color(0xFF047857),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                        Text(
                            text = item.name,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else Color(0xFF334155)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // --- Message Status Banner (tvSudokuMessage) ---
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            color = when {
                isWon -> Color(0xFFD1FAE5)
                hasConflict -> Color(0xFFFEE2E2)
                else -> Color.White
            },
            border = BorderStroke(
                1.5.dp,
                when {
                    isWon -> Color(0xFF059669)
                    hasConflict -> Color(0xFFEF4444)
                    else -> Color(0xFFCBD5E1)
                }
            ),
            shadowElevation = 2.dp
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = statusMessage,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = when {
                        isWon -> Color(0xFF047857)
                        hasConflict -> Color(0xFFB91C1C)
                        else -> Color(0xFF334155)
                    },
                    textAlign = TextAlign.Center
                )
                if (isWon && currentLevelIndex < sudokuLevels.size - 1) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Button(
                        onClick = {
                            currentLevelIndex++
                            resetCurrentGame()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SunshineYellow),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "🚀 PRÓXIMO NÍVEL (NÍVEL ${currentLevelIndex + 2} - MAIS DIFÍCIL!)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // --- 4x4 SUDOKU BOARD WITH HIGH-VISIBILITY 2x2 BLOCKS ---
        // 4 distinct 2x2 Block Cards separated by 10.dp space so children immediately perceive the blocks!
        Card(
            modifier = Modifier
                .fillMaxWidth(0.60f)
                .aspectRatio(1f),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(3.dp, Color(0xFF059669)),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp) // Gap between top 2x2 blocks and bottom 2x2 blocks
            ) {
                // Top Half: 2 Blocks (Top-Left & Top-Right)
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp) // Gap between Left 2x2 block and Right 2x2 block
                ) {
                    // Block 0: Top-Left (rows 0..1, cols 0..1)
                    SudokuBlock2x2(
                        startRow = 0, startCol = 0,
                        userBoard = userBoard, initialBoard = level.initialBoard,
                        isWon = isWon,
                        blockBgColor = Color(0xFFFFFBEB), // Soft warm yellow block
                        blockBorderColor = Color(0xFFF59E0B),
                        onCellClick = { r, c -> handleCellClick(r, c) },
                        modifier = Modifier.weight(1f)
                    )

                    // Block 1: Top-Right (rows 0..1, cols 2..3)
                    SudokuBlock2x2(
                        startRow = 0, startCol = 2,
                        userBoard = userBoard, initialBoard = level.initialBoard,
                        isWon = isWon,
                        blockBgColor = Color(0xFFEFF6FF), // Soft sky blue block
                        blockBorderColor = Color(0xFF3B82F6),
                        onCellClick = { r, c -> handleCellClick(r, c) },
                        modifier = Modifier.weight(1f)
                    )
                }

                // Bottom Half: 2 Blocks (Bottom-Left & Bottom-Right)
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Block 2: Bottom-Left (rows 2..3, cols 0..1)
                    SudokuBlock2x2(
                        startRow = 2, startCol = 0,
                        userBoard = userBoard, initialBoard = level.initialBoard,
                        isWon = isWon,
                        blockBgColor = Color(0xFFEFF6FF), // Soft sky blue block
                        blockBorderColor = Color(0xFF3B82F6),
                        onCellClick = { r, c -> handleCellClick(r, c) },
                        modifier = Modifier.weight(1f)
                    )

                    // Block 3: Bottom-Right (rows 2..3, cols 2..3)
                    SudokuBlock2x2(
                        startRow = 2, startCol = 2,
                        userBoard = userBoard, initialBoard = level.initialBoard,
                        isWon = isWon,
                        blockBgColor = Color(0xFFFFFBEB), // Soft warm yellow block
                        blockBorderColor = Color(0xFFF59E0B),
                        onCellClick = { r, c -> handleCellClick(r, c) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // --- Bottom Selection Bar with 4 Shape Options ---
        Text(
            text = "Escolhe uma forma e toca no quadrado onde a queres colocar:",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF047857)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            sudokuShapeOptions.forEach { shape ->
                val isSelected = selectedShapeId == shape.id

                Card(
                    onClick = { selectedShapeId = shape.id },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) shape.color else Color.White
                    ),
                    border = BorderStroke(
                        if (isSelected) 3.dp else 1.dp,
                        if (isSelected) SunshineYellow else Color(0xFFE2E8F0)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 6.dp else 2.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 6.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = shape.emoji, fontSize = 24.sp)
                        Text(
                            text = shape.name,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = if (isSelected) Color.White else Color(0xFF475569)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // --- Bottom Action Controls & Mascot ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ZeTraquinaMascot(
                size = 50.dp,
                showSpeechBubble = false,
                emotion = if (isWon) MascotEmotion.CELEBRATING else MascotEmotion.HAPPY,
                onInteract = {}
            )

            if (isWon) {
                Button(
                    onClick = {
                        currentLevelIndex = (currentLevelIndex + 1) % sudokuLevels.size
                        resetCurrentGame()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SunshineYellow),
                    shape = RoundedCornerShape(14.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "PRÓXIMO NÍVEL ➡️",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.Black
                    )
                }
            } else {
                Button(
                    onClick = { resetCurrentGame() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFCBD5E1)),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        tint = Color(0xFF475569),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Limpar 🔄",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF475569)
                    )
                }
            }
        }
        }

        if (isWon) {
            ConfettiEffect()
        }
    }
}

/**
 * 2x2 Block Composable with explicit visual boundaries & high contrast cells
 */
@Composable
private fun SudokuBlock2x2(
    startRow: Int,
    startCol: Int,
    userBoard: Array<IntArray>,
    initialBoard: Array<IntArray>,
    isWon: Boolean,
    blockBgColor: Color,
    blockBorderColor: Color,
    onCellClick: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxHeight(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = blockBgColor),
        border = BorderStroke(2.5.dp, blockBorderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            for (rOffset in 0..1) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    for (cOffset in 0..1) {
                        val row = startRow + rOffset
                        val col = startCol + cOffset
                        val cellValue = userBoard[row][col]
                        val isFixed = initialBoard[row][col] != 0
                        val shape = sudokuShapeOptions.find { it.id == cellValue }

                        SudokuCellSquare(
                            shape = shape,
                            isFixed = isFixed,
                            isWon = isWon,
                            onClick = { onCellClick(row, col) },
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                    }
                }
            }
        }
    }
}

/**
 * Individual Sudoku Cell Square Card
 */
@Composable
private fun SudokuCellSquare(
    shape: SudokuShapeOption?,
    isFixed: Boolean,
    isWon: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(enabled = !isFixed && !isWon, onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isFixed -> Color.White
                shape != null -> shape.color.copy(alpha = 0.15f)
                else -> Color.White
            }
        ),
        border = BorderStroke(
            width = if (isFixed) 2.dp else 1.5.dp,
            color = when {
                isFixed -> Color.DarkGray
                shape != null -> shape.color
                else -> Color(0xFFCBD5E1)
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (shape != null) 3.dp else 1.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (shape != null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = shape.emoji,
                        fontSize = 28.sp,
                        textAlign = TextAlign.Center
                    )
                    if (isFixed) {
                        Text(
                            text = "PIN",
                            fontSize = 7.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.DarkGray
                        )
                    }
                }
            } else {
                // Empty cell placeholder hint
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Vazio",
                    tint = Color(0xFF94A3B8),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JogoSudokuInfantilPreview(
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
            JogoSudokuInfantilScreen(mainViewModel = null, onBack = {})
        }
    }
}
