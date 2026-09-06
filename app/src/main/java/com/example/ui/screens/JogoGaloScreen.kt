package com.example.ui.screens

import com.example.ui.theme.PreviewAppTheme

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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

enum class PlayerSymbol(val symbol: String, val color: Color, val label: String) {
    X("❌", Color(0xFFE53935), "Jogador 1 (❌)"),
    O("⭕", Color(0xFF1E88E5), "Jogador 2 / Zé Traquina (⭕)")
}

enum class GameMode {
    VS_AI,
    VS_FRIEND
}

@Composable
fun JogoGaloScreen(
    mainViewModel: MainViewModel? = null,
    onBack: () -> Unit
) {
    val isPreview = LocalInspectionMode.current

    var board by remember { mutableStateOf(Array(9) { "" }) }
    var isXTurn by remember { mutableStateOf(true) }
    var winner by remember { mutableStateOf<String?>(null) } // "X", "O", or "DRAW"
    var winningIndices by remember { mutableStateOf<List<Int>>(emptyList()) }

    var gameMode by remember { mutableStateOf(GameMode.VS_AI) }
    var scoreX by remember { mutableIntStateOf(0) }
    var scoreO by remember { mutableIntStateOf(0) }
    var scoreDraws by remember { mutableIntStateOf(0) }

    var isAiThinking by remember { mutableStateOf(false) }

    // Check winner helper
    fun checkWinner(currentBoard: Array<String>): Pair<String?, List<Int>> {
        val winPatterns = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8), // Rows
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8), // Cols
            listOf(0, 4, 8), listOf(2, 4, 6)                  // Diagonals
        )

        for (pattern in winPatterns) {
            val (a, b, c) = pattern
            if (currentBoard[a].isNotEmpty() &&
                currentBoard[a] == currentBoard[b] &&
                currentBoard[a] == currentBoard[c]
            ) {
                return Pair(currentBoard[a], pattern)
            }
        }

        if (currentBoard.all { it.isNotEmpty() }) {
            return Pair("DRAW", emptyList())
        }

        return Pair(null, emptyList())
    }

    // Reset current board
    fun resetBoard() {
        board = Array(9) { "" }
        isXTurn = true
        winner = null
        winningIndices = emptyList()
        isAiThinking = false
    }

    // AI logic (plays as "O")
    suspend fun makeAiMove() {
        if (winner != null || isXTurn) return
        isAiThinking = true
        delay(600) // Realistic delay

        val emptyIndices = board.indices.filter { board[it].isEmpty() }
        if (emptyIndices.isEmpty()) {
            isAiThinking = false
            return
        }

        // 1. Check if AI can win immediately
        var chosenIndex = -1
        for (index in emptyIndices) {
            val testBoard = board.copyOf()
            testBoard[index] = "O"
            if (checkWinner(testBoard).first == "O") {
                chosenIndex = index
                break
            }
        }

        // 2. Check if AI needs to block player X from winning
        if (chosenIndex == -1) {
            for (index in emptyIndices) {
                val testBoard = board.copyOf()
                testBoard[index] = "X"
                if (checkWinner(testBoard).first == "X") {
                    chosenIndex = index
                    break
                }
            }
        }

        // 3. Take center if available
        if (chosenIndex == -1 && board[4].isEmpty()) {
            chosenIndex = 4
        }

        // 4. Random available spot
        if (chosenIndex == -1) {
            chosenIndex = emptyIndices.random()
        }

        val newBoard = board.copyOf()
        newBoard[chosenIndex] = "O"
        board = newBoard

        val (winResult, winLines) = checkWinner(newBoard)
        if (winResult != null) {
            winner = winResult
            winningIndices = winLines
            if (winResult == "O") {
                scoreO++
            } else if (winResult == "DRAW") {
                scoreDraws++
            }
        } else {
            isXTurn = true
        }
        isAiThinking = false
    }

    // Player tap handler
    fun handleCellClick(index: Int) {
        if (board[index].isNotEmpty() || winner != null || isAiThinking) return

        val currentPlayerSymbol = if (isXTurn) "X" else "O"
        val newBoard = board.copyOf()
        newBoard[index] = currentPlayerSymbol
        board = newBoard

        val (winResult, winLines) = checkWinner(newBoard)
        if (winResult != null) {
            winner = winResult
            winningIndices = winLines
            if (winResult == "X") {
                scoreX++
                if (!isPreview) {
                    mainViewModel?.addStars(5)
                    mainViewModel?.playSound(R.raw.ze_traquina_e_fixe)
                }
            } else if (winResult == "O") {
                scoreO++
            } else if (winResult == "DRAW") {
                scoreDraws++
                if (!isPreview) {
                    mainViewModel?.addStars(1)
                }
            }
        } else {
            isXTurn = !isXTurn
        }
    }

    // Trigger AI move if VS_AI mode and it's O's turn
    LaunchedEffect(isXTurn, winner, gameMode) {
        if (gameMode == GameMode.VS_AI && !isXTurn && winner == null) {
            makeAiMove()
        }
    }

    LaunchedEffect(winner) {
        if (winner != null) {
            kotlinx.coroutines.delay(2200)
            resetBoard()
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        // --- Top Container with White Background ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
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
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
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
                        text = "Jogo do Galo ❌⭕",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF0F172A)
                    )

                    IconButton(
                        onClick = { resetBoard() },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Reiniciar Tabuleiro",
                            tint = SkyBluePrimary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // --- Game Mode Selector ---
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        onClick = {
                            gameMode = GameMode.VS_AI
                            resetBoard()
                        },
                        shape = RoundedCornerShape(16.dp),
                        color = if (gameMode == GameMode.VS_AI) SkyBluePrimary else Color(0xFFF1F5F9),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.SmartToy,
                                contentDescription = null,
                                tint = if (gameMode == GameMode.VS_AI) Color.White else Color(0xFF475569),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "vs Zé Traquina 🤖",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (gameMode == GameMode.VS_AI) Color.White else Color(0xFF475569)
                            )
                        }
                    }

                    Surface(
                        onClick = {
                            gameMode = GameMode.VS_FRIEND
                            resetBoard()
                        },
                        shape = RoundedCornerShape(16.dp),
                        color = if (gameMode == GameMode.VS_FRIEND) SkyBluePrimary else Color(0xFFF1F5F9),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = if (gameMode == GameMode.VS_FRIEND) Color.White else Color(0xFF475569),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "2 Jogadores 👥",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (gameMode == GameMode.VS_FRIEND) Color.White else Color(0xFF475569)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // --- Scoreboard ---
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Player X Score
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "❌ Jogador 1", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PlayerSymbol.X.color)
                            Text(text = "$scoreX", fontSize = 20.sp, fontWeight = FontWeight.Black, color = Color(0xFF0F172A))
                        }

                        // Draws Score
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Empates 🤝", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                            Text(text = "$scoreDraws", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = Color.Gray)
                        }

                        // Player O Score
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = if (gameMode == GameMode.VS_AI) "🤖 Zé Traquina" else "⭕ Jogador 2",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = PlayerSymbol.O.color
                            )
                            Text(text = "$scoreO", fontSize = 20.sp, fontWeight = FontWeight.Black, color = Color(0xFF0F172A))
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // --- Turn Indicator Banner ---
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = when (winner) {
                "X" -> MintGreen
                "O" -> SunshineYellow
                "DRAW" -> Color.LightGray
                else -> if (isXTurn) PlayerSymbol.X.color.copy(alpha = 0.15f) else PlayerSymbol.O.color.copy(alpha = 0.15f)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = when {
                        winner == "X" -> "������ VITORIA DO JOGADOR 1 (❌)!"
                        winner == "O" -> if (gameMode == GameMode.VS_AI) "🤖 ZÉ TRAQUINA VENCEU!" else "🎉 VITÓRIA DO JOGADOR 2 (⭕)!"
                        winner == "DRAW" -> "🤝 EMPATE PERFECTO!"
                        isAiThinking -> "🤖 Zé Traquina a pensar..."
                        isXTurn -> "Vez do Jogador 1 (❌)"
                        else -> if (gameMode == GameMode.VS_AI) "Vez do Zé Traquina (⭕)" else "Vez do Jogador 2 (⭕)"
                    },
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = when (winner) {
                        "X" -> Color(0xFF166534)
                        "O" -> Color.Black
                        "DRAW" -> Color.DarkGray
                        else -> if (isXTurn) PlayerSymbol.X.color else PlayerSymbol.O.color
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- 3x3 TIC-TAC-TOE BOARD GRID ---
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
                .border(4.dp, SkyBluePrimary, RoundedCornerShape(24.dp))
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (row in 0..2) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (col in 0..2) {
                            val index = row * 3 + col
                            val symbol = board[index]
                            val isWinningCell = winningIndices.contains(index)

                            GaloCell(
                                symbol = symbol,
                                isWinningCell = isWinningCell,
                                isEnabled = winner == null && symbol.isEmpty() && !isAiThinking,
                                onClick = { handleCellClick(index) },
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Mascot & Reset Controls ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ZeTraquinaMascot(
                size = 56.dp,
                showSpeechBubble = false,
                emotion = when (winner) {
                    "X" -> MascotEmotion.CELEBRATING
                    "O" -> MascotEmotion.CELEBRATING
                    "DRAW" -> MascotEmotion.HAPPY
                    else -> if (isAiThinking) MascotEmotion.THINKING else MascotEmotion.HAPPY
                },
                onInteract = {}
            )

            Button(
                onClick = { resetBoard() },
                colors = ButtonDefaults.buttonColors(containerColor = SunshineYellow),
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "NOVO JOGO 🔄",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )
            }
        }
        }

        if (winner == "X" || winner == "O") {
            ConfettiEffect()
        }
    }
}

@Composable
private fun GaloCell(
    symbol: String,
    isWinningCell: Boolean,
    isEnabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1.0f,
        animationSpec = tween(durationMillis = 100),
        label = "cell_scale"
    )

    Card(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clickable(
                enabled = isEnabled,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isWinningCell -> MintGreen
                symbol.isNotEmpty() -> MaterialTheme.colorScheme.surface
                else -> MaterialTheme.colorScheme.surface
            }
        ),
        border = BorderStroke(
            width = if (isWinningCell) 3.dp else 2.dp,
            color = if (isWinningCell) Color(0xFF166534) else SkyBluePrimary.copy(alpha = 0.4f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (symbol.isNotEmpty()) 4.dp else 2.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (symbol.isNotEmpty()) {
                Text(
                    text = symbol,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JogoGaloPreview(
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
            JogoGaloScreen(mainViewModel = null, onBack = {})
        }
    }
}
