package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

// --- Modelos de Dados ---

enum class UnoColor(val color: Color, val displayName: String) {
    RED(Color(0xFFFF4B4B), "Vermelho"),
    BLUE(Color(0xFF4B89FF), "Azul"),
    GREEN(Color(0xFF4BFF89), "Verde"),
    YELLOW(Color(0xFFFFD94B), "Amarelo"),
    WILD(Color(0xFF333333), "Especial")
}

enum class UnoValue(val symbol: String) {
    ZERO("0"), ONE("1"), TWO("2"), THREE("3"), FOUR("4"),
    FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"),
    SKIP("⊘"), REVERSE("⇄"), DRAW_TWO("+2"),
    WILD("★"), WILD_DRAW_FOUR("+4")
}

data class UnoCard(
    val id: Int,
    val color: UnoColor,
    val value: UnoValue,
    val chosenColor: UnoColor? = null
) {
    fun canPlayOn(topCard: UnoCard): Boolean {
        if (this.color == UnoColor.WILD) return true
        if (topCard.color == UnoColor.WILD) {
            return topCard.chosenColor == null || this.color == topCard.chosenColor
        }
        return this.color == topCard.color || this.value == topCard.value
    }
}

// --- Lógica do Jogo ---

class UnoGameEngine {
    private val deck = mutableListOf<UnoCard>()
    private var nextId = 0
    var playerHand = mutableStateListOf<UnoCard>()
    var aiHand = mutableStateListOf<UnoCard>()
    var discardPile = mutableStateListOf<UnoCard>()
    var isPlayerTurn by mutableStateOf(true)
    var gameStatus by mutableStateOf("Tua vez! Escolhe uma carta.")
    var showColorPicker by mutableStateOf<UnoCard?>(null)
    var gameOver by mutableStateOf<String?>(null)

    init {
        setupGame()
    }

    fun setupGame() {
        deck.clear()
        nextId = 0
        playerHand.clear()
        aiHand.clear()
        discardPile.clear()
        gameOver = null
        isPlayerTurn = true

        UnoColor.values().forEach { color ->
            if (color != UnoColor.WILD) {
                UnoValue.values().forEach { value ->
                    if (value != UnoValue.WILD && value != UnoValue.WILD_DRAW_FOUR) {
                        deck.add(UnoCard(id = nextId++, color = color, value = value))
                        if (value != UnoValue.ZERO) deck.add(UnoCard(id = nextId++, color = color, value = value))
                    }
                }
            }
        }
        repeat(4) {
            deck.add(UnoCard(id = nextId++, color = UnoColor.WILD, value = UnoValue.WILD))
            deck.add(UnoCard(id = nextId++, color = UnoColor.WILD, value = UnoValue.WILD_DRAW_FOUR))
        }
        deck.shuffle()

        repeat(7) {
            if (deck.isNotEmpty()) playerHand.add(deck.removeAt(0))
            if (deck.isNotEmpty()) aiHand.add(deck.removeAt(0))
        }

        val firstCard = deck.first { it.color != UnoColor.WILD && it.value.symbol.toIntOrNull() != null }
        deck.remove(firstCard)
        discardPile.add(firstCard)
    }

    fun drawCard(isPlayer: Boolean) {
        if (deck.isEmpty()) {
            val top = discardPile.last()
            val rest = discardPile.dropLast(1).shuffled()
            discardPile.clear()
            discardPile.add(top)
            deck.addAll(rest)
        }
        if (deck.isNotEmpty()) {
            val card = deck.removeAt(0)
            if (isPlayer) playerHand.add(card) else aiHand.add(card)
        }
    }

    fun playCard(card: UnoCard, isPlayer: Boolean, chosenColor: UnoColor? = null) {
        val finalCard = if (chosenColor != null) card.copy(chosenColor = chosenColor) else card
        discardPile.add(finalCard)
        if (isPlayer) playerHand.remove(card) else aiHand.remove(card)

        if (playerHand.isEmpty()) { gameOver = "Ganhaste!"; return }
        if (aiHand.isEmpty()) { gameOver = "O Zé ganhou!"; return }

        handleCardEffect(finalCard, isPlayer)
    }

    private fun handleCardEffect(card: UnoCard, wasPlayer: Boolean) {
        var skipNext = false
        var nextDraw = 0

        when (card.value) {
            UnoValue.SKIP, UnoValue.REVERSE -> skipNext = true
            UnoValue.DRAW_TWO -> nextDraw = 2
            UnoValue.WILD_DRAW_FOUR -> nextDraw = 4
            else -> {}
        }

        if (nextDraw > 0) {
            repeat(nextDraw) { drawCard(!wasPlayer) }
            skipNext = true
        }

        if (!skipNext) isPlayerTurn = !wasPlayer
        gameStatus = if (isPlayerTurn) "Tua vez! Joga uma carta." else "O Zé está a pensar..."
    }

    suspend fun aiPlay() {
        if (gameOver != null) return
        delay(2000)
        val top = discardPile.last()
        val playable = aiHand.filter { it.canPlayOn(top) }

        if (playable.isNotEmpty()) {
            val toPlay = playable.random()
            if (toPlay.color == UnoColor.WILD) {
                val bestColor = aiHand.filter { it.color != UnoColor.WILD }.groupBy { it.color }.maxByOrNull { it.value.size }?.key ?: UnoColor.RED
                playCard(toPlay, false, bestColor)
            } else {
                playCard(toPlay, false)
            }
        } else {
            gameStatus = "O Zé não tinha carta e comprou uma!"
            drawCard(false)
            delay(1000)
            val newPlayable = aiHand.last().takeIf { it.canPlayOn(discardPile.last()) }
            if (newPlayable != null) playCard(newPlayable, false) else isPlayerTurn = true
            gameStatus = "Tua vez!"
        }
    }
}

// --- Componentes UI ---

@Composable
fun UnoGameScreen(mainViewModel: MainViewModel? = null, onBack: () -> Unit) {
    val engine = remember { UnoGameEngine() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(engine.isPlayerTurn) {
        if (!engine.isPlayerTurn && engine.gameOver == null) engine.aiPlay()
    }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFE0F2FE))) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack, modifier = Modifier.size(36.dp).background(Color.White, CircleShape)) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar", tint = Color(0xFF0369A1), modifier = Modifier.size(20.dp))
                }
                Text("UNO Zé Traquina 🃏", color = Color(0xFF0369A1), fontWeight = FontWeight.Black, fontSize = 18.sp)
                IconButton(onClick = { engine.setupGame() }, modifier = Modifier.size(36.dp).background(Color.White, CircleShape)) {
                    Icon(Icons.Default.Refresh, "Reiniciar", tint = Color(0xFF0369A1), modifier = Modifier.size(20.dp))
                }
            }

            // Game Area (AI + Center Table + Status)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                // AI Section
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier.size(54.dp).background(Color.White, CircleShape).border(3.dp, Color(0xFF0369A1), CircleShape), contentAlignment = Alignment.Center) {
                        Text("🤖", fontSize = 28.sp)
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text("Zé Traquina: ${engine.aiHand.size} cartas", fontWeight = FontWeight.Bold, color = Color(0xFF0369A1), fontSize = 12.sp)
                }

                // Center Table
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Deck
                    Card(
                        modifier = Modifier.size(72.dp, 105.dp).clickable(enabled = engine.isPlayerTurn) {
                            if (engine.isPlayerTurn && engine.gameOver == null) {
                                engine.drawCard(true)
                                engine.isPlayerTurn = false
                            }
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF0369A1)),
                        elevation = CardDefaults.cardElevation(6.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("PEDIR\nCARTA", color = Color.White, textAlign = TextAlign.Center, fontWeight = FontWeight.Black, fontSize = 13.sp)
                        }
                    }

                    // Discard Pile
                    if (engine.discardPile.isNotEmpty()) {
                        UnoCardView(card = engine.discardPile.last(), isLarge = true)
                    }
                }

                // Status Message
                Surface(
                    color = Color.White.copy(alpha = 0.95f),
                    shape = RoundedCornerShape(16.dp),
                    shadowElevation = 3.dp
                ) {
                    Text(
                        text = engine.gameStatus,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp,
                        color = Color(0xFF0369A1)
                    )
                }
            }

            // Player Hand
            Surface(
                modifier = Modifier.fillMaxWidth().height(150.dp),
                color = Color.White,
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                shadowElevation = 12.dp
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Tuas Cartas (${engine.playerHand.size})", modifier = Modifier.padding(top = 8.dp), fontWeight = FontWeight.Black, color = Color(0xFF0369A1), fontSize = 13.sp)
                    LazyRow(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        items(engine.playerHand, key = { it.id }) { card ->
                            val canPlay = card.canPlayOn(engine.discardPile.last()) && engine.isPlayerTurn
                            UnoCardView(
                                card = card,
                                isEnabled = canPlay,
                                isPlayable = canPlay,
                                onClick = {
                                    if (canPlay) {
                                        if (card.color == UnoColor.WILD) engine.showColorPicker = card
                                        else engine.playCard(card, true)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }


        // Popups
        if (engine.showColorPicker != null) {
            Dialog(onDismissRequest = { engine.showColorPicker = null }) {
                Surface(shape = RoundedCornerShape(30.dp), color = Color.White) {
                    Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Escolhe a cor!", fontWeight = FontWeight.Black, fontSize = 22.sp, color = Color(0xFF0369A1))
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                            UnoColor.values().filter { it != UnoColor.WILD }.forEach { unoColor ->
                                Box(
                                    modifier = Modifier.size(60.dp).background(unoColor.color, CircleShape)
                                        .clickable { engine.playCard(engine.showColorPicker!!, true, unoColor); engine.showColorPicker = null }
                                        .border(4.dp, Color.White, CircleShape)
                                )
                            }
                        }
                    }
                }
            }
        }

        if (engine.gameOver != null) {
            Dialog(onDismissRequest = {}) {
                Surface(shape = RoundedCornerShape(40.dp), color = Color.White) {
                    Column(modifier = Modifier.padding(40.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(engine.gameOver!!, fontWeight = FontWeight.Black, fontSize = 30.sp, color = Color(0xFF0369A1), textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier.height(30.dp))
                        Button(
                            onClick = { engine.setupGame() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0EA5E9)),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text("Jogar de novo!", modifier = Modifier.padding(8.dp), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UnoCardView(card: UnoCard, isLarge: Boolean = false, isEnabled: Boolean = true, isPlayable: Boolean = false, onClick: (() -> Unit)? = null) {
    val displayColor = if (card.color == UnoColor.WILD) (card.chosenColor?.color ?: Color(0xFF333333)) else card.color.color
    
    val infiniteTransition = rememberInfiniteTransition(label = "playable_glow")
    val scale by animateFloatAsState(if (isPlayable) 1.15f else 1.0f, label = "scale")
    val elevation by animateDpAsState(if (isPlayable) 12.dp else 4.dp, label = "elevation")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f, targetValue = 0.8f,
        animationSpec = infiniteRepeatable(tween(800, easing = LinearEasing), RepeatMode.Reverse), label = "glow"
    )

    Surface(
        modifier = Modifier
            .size(if (isLarge) 100.dp else 85.dp, if (isLarge) 150.dp else 130.dp)
            .scale(scale)
            .alpha(if (isEnabled || isLarge) 1.0f else 0.5f)
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .shadow(elevation, RoundedCornerShape(12.dp)),
        color = displayColor,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(if (isPlayable) 6.dp else 4.dp, if (isPlayable) Color.White.copy(alpha = glowAlpha) else Color.White)
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            Text(card.value.symbol, color = Color.White, fontWeight = FontWeight.Black, fontSize = if (isLarge) 24.sp else 18.sp, modifier = Modifier.align(Alignment.TopStart))
            Box(modifier = Modifier.size(if (isLarge) 60.dp else 45.dp).background(Color.White, CircleShape).align(Alignment.Center), contentAlignment = Alignment.Center) {
                Text(card.value.symbol, color = displayColor, fontWeight = FontWeight.Black, fontSize = if (isLarge) 36.sp else 28.sp)
            }
            Text(card.value.symbol, color = Color.White, fontWeight = FontWeight.Black, fontSize = if (isLarge) 24.sp else 18.sp, modifier = Modifier.align(Alignment.BottomEnd).rotate(180f))
        }
    }
}
