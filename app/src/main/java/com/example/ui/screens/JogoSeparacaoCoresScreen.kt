package com.example.ui.screens

import android.graphics.RectF
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.MascotEmotion
import com.example.ui.components.ZeTraquinaMascot
import com.example.ui.theme.MintGreen
import com.example.ui.theme.SkyBluePrimary
import com.example.ui.theme.SunshineYellow
import com.example.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.random.Random

// --- Color Types ---
enum class TargetColorType(
    val displayName: String,
    val color: Color,
    val containerBg: Color,
    val emoji: String,
    val description: String
) {
    RED(
        displayName = "Balde Vermelho",
        color = Color(0xFFE53935),
        containerBg = Color(0xFFFFEBEE),
        emoji = "🍎",
        description = "Balde Vermelho"
    ),
    BLUE(
        displayName = "Balde Azul",
        color = Color(0xFF1E88E5),
        containerBg = Color(0xFFE3F2FD),
        emoji = "🐳",
        description = "Balde Azul"
    ),
    YELLOW(
        displayName = "Balde Amarelo",
        color = Color(0xFFFFB300),
        containerBg = Color(0xFFFFFDE7),
        emoji = "🍌",
        description = "Balde Amarelo"
    )
}

// --- Item Model ---
data class ColorGameItem(
    val id: String,
    val name: String,
    val colorType: TargetColorType,
    val emoji: String
)

// List of fun items categorized by color
val ALL_COLOR_ITEMS = listOf(
    // Vermelho (Red)
    ColorGameItem("red_1", "Maçã", TargetColorType.RED, "🍎"),
    ColorGameItem("red_2", "Morango", TargetColorType.RED, "🍓"),
    ColorGameItem("red_3", "Carro", TargetColorType.RED, "🚗"),
    ColorGameItem("red_4", "Balão", TargetColorType.RED, "🎈"),
    ColorGameItem("red_5", "Tomate", TargetColorType.RED, "🍅"),
    ColorGameItem("red_6", "Coração", TargetColorType.RED, "❤️"),

    // Azul (Blue)
    ColorGameItem("blue_1", "Baleia", TargetColorType.BLUE, "🐳"),
    ColorGameItem("blue_2", "Gota d'Água", TargetColorType.BLUE, "💧"),
    ColorGameItem("blue_3", "Estrela Azul", TargetColorType.BLUE, "🔷"),
    ColorGameItem("blue_4", "Barco", TargetColorType.BLUE, "⛵"),
    ColorGameItem("blue_5", "Mirtilo", TargetColorType.BLUE, "🫐"),
    ColorGameItem("blue_6", "Peixinho Azul", TargetColorType.BLUE, "🐟"),

    // Amarelo (Yellow)
    ColorGameItem("yellow_1", "Banana", TargetColorType.YELLOW, "🍌"),
    ColorGameItem("yellow_2", "Sol Mágico", TargetColorType.YELLOW, "☀️"),
    ColorGameItem("yellow_3", "Patinho", TargetColorType.YELLOW, "🐥"),
    ColorGameItem("yellow_4", "Estrela", TargetColorType.YELLOW, "⭐"),
    ColorGameItem("yellow_5", "Limão", TargetColorType.YELLOW, "🍋"),
    ColorGameItem("yellow_6", "Queijo", TargetColorType.YELLOW, "🧀")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JogoSeparacaoCoresScreen(
    mainViewModel: MainViewModel? = null,
    onBack: () -> Unit
) {
    val isPreview = LocalInspectionMode.current
    val coroutineScope = rememberCoroutineScope()

    // Game state
    var currentLevel by remember { mutableIntStateOf(1) }
    var score by remember { mutableIntStateOf(0) }
    var totalSortedInLevel by remember { mutableIntStateOf(0) }
    var showLevelCompleteDialog by remember { mutableStateOf(false) }

    // List of active items for the current level
    var remainingItems by remember { mutableStateOf<List<ColorGameItem>>(emptyList()) }
    // Items sorted per bucket
    var sortedRedItems by remember { mutableStateOf<List<ColorGameItem>>(emptyList()) }
    var sortedBlueItems by remember { mutableStateOf<List<ColorGameItem>>(emptyList()) }
    var sortedYellowItems by remember { mutableStateOf<List<ColorGameItem>>(emptyList()) }

    // Currently selected item (for tap-to-select fallback mode)
    var selectedItem by remember { mutableStateOf<ColorGameItem?>(null) }

    // Active feedback message & sparkles animation state
    var feedbackMessage by remember { mutableStateOf("Arrasta ou toca num objeto para o colocar no balde da cor certa!") }
    var mascotEmotion by remember { mutableStateOf(MascotEmotion.HAPPY) }
    var showCelebrationEffects by remember { mutableStateOf(false) }

    // Bucket bounds tracking for Drag and Drop collision
    val bucketBounds = remember { mutableStateMapOf<TargetColorType, androidx.compose.ui.geometry.Rect>() }

    // Bouncing animation states for buckets on correct drop
    var animatingBucket by remember { mutableStateOf<TargetColorType?>(null) }

    // Function to generate items for a level
    fun loadLevel(level: Int) {
        val itemsPerColor = when (level) {
            1 -> 2 // 6 items total
            2 -> 3 // 9 items total
            else -> 4 // 12 items total
        }

        val redList = ALL_COLOR_ITEMS.filter { it.colorType == TargetColorType.RED }.shuffled().take(itemsPerColor)
        val blueList = ALL_COLOR_ITEMS.filter { it.colorType == TargetColorType.BLUE }.shuffled().take(itemsPerColor)
        val yellowList = ALL_COLOR_ITEMS.filter { it.colorType == TargetColorType.YELLOW }.shuffled().take(itemsPerColor)

        remainingItems = (redList + blueList + yellowList).shuffled()
        sortedRedItems = emptyList()
        sortedBlueItems = emptyList()
        sortedYellowItems = emptyList()
        totalSortedInLevel = 0
        selectedItem = null
        showLevelCompleteDialog = false
        feedbackMessage = "Nível $level: Separa as cores! Arrasta os objetos para os seus baldes! 🎨"
        mascotEmotion = MascotEmotion.HAPPY

        if (!isPreview) {
        }
    }

    // Initialize level 1 on start
    LaunchedEffect(Unit) {
        loadLevel(1)
    }

    // Helper: Handle when an item is placed into a bucket
    fun attemptPlaceItemInBucket(item: ColorGameItem, targetBucket: TargetColorType) {
        if (item.colorType == targetBucket) {
            // Correct match!
            remainingItems = remainingItems.filter { it.id != item.id }
            when (targetBucket) {
                TargetColorType.RED -> sortedRedItems = sortedRedItems + item
                TargetColorType.BLUE -> sortedBlueItems = sortedBlueItems + item
                TargetColorType.YELLOW -> sortedYellowItems = sortedYellowItems + item
            }
            score += 10
            totalSortedInLevel++
            selectedItem = null
            animatingBucket = targetBucket

            // Feedback
            feedbackMessage = "Muito bem! ${item.name} é de cor ${targetBucket.displayName}! ✨"
            mascotEmotion = MascotEmotion.CELEBRATING
            showCelebrationEffects = true

            if (!isPreview) {
                mainViewModel?.addStars(5)
            }

            coroutineScope.launch {
                delay(1200)
                animatingBucket = null
                showCelebrationEffects = false
                mascotEmotion = MascotEmotion.HAPPY

                // Check if level finished
                if (remainingItems.isEmpty()) {
                    showLevelCompleteDialog = true
                    if (!isPreview) {
                        mainViewModel?.addStars(15)
                    }
                }
            }
        } else {
            // Wrong match
            feedbackMessage = "Ops! A ${item.name} não é ${targetBucket.displayName}. É ${item.colorType.displayName}! Tenta de novo! 😊"
            mascotEmotion = MascotEmotion.THINKING

            if (!isPreview) {
            }

            coroutineScope.launch {
                delay(2000)
                mascotEmotion = MascotEmotion.HAPPY
            }
        }
    }

    LaunchedEffect(showLevelCompleteDialog) {
        if (showLevelCompleteDialog) {
            kotlinx.coroutines.delay(2200)
            currentLevel++
            loadLevel(currentLevel)
        }
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Separação de Cores 🎨",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            color = Color(0xFF0F172A)
                        )
                        Text(
                            text = "Nível $currentLevel • Aprende as cores!",
                            fontSize = 11.sp,
                            color = Color(0xFF64748B),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color(0xFF0F172A)
                        )
                    }
                },
                actions = {
                    // Star counter pill
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = SunshineYellow,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "$score ⭐",
                                fontWeight = FontWeight.Black,
                                fontSize = 13.sp,
                                color = Color.Black
                            )
                        }
                    }

                    // Visible Hint Button
                    Button(
                        onClick = {
                            val firstRemaining = remainingItems.firstOrNull()
                            if (firstRemaining != null) {
                                feedbackMessage = "Dica: A ${firstRemaining.name} deve ir para o balde ${firstRemaining.colorType.displayName}!"
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SunshineYellow),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                        modifier = Modifier.padding(end = 4.dp)
                    ) {
                        Text("💡 Dica", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black)
                    }

                    // Reset level button
                    IconButton(onClick = { loadLevel(currentLevel) }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Reiniciar Nível",
                            tint = Color(0xFF0F172A)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // --- 1. Mascot Guidance Banner ---
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    border = BorderStroke(1.dp, SkyBluePrimary.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ZeTraquinaMascot(
                            size = 42.dp,
                            showSpeechBubble = false,
                            emotion = mascotEmotion,
                            onInteract = {}
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = feedbackMessage,
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E293B),
                                lineHeight = 15.sp,
                                maxLines = 2,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                            )
                            Text(
                                text = "Dica: Toca no objeto e depois no balde da mesma cor! 💡",
                                fontSize = 9.5.sp,
                                color = SkyBluePrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // --- 2. Objects Rack (Items to be sorted) ---
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    border = BorderStroke(1.5.dp, Color(0xFFE2E8F0))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Bancada de Objetos 📦 (${remainingItems.size} restantes)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF334155)
                            )

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MintGreen.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = "Arrasta ou Toca 👆",
                                    fontSize = 9.5.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2E7D32),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        if (remainingItems.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("🎉", fontSize = 36.sp)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "Todos os objetos foram organizados!",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MintGreen
                                    )
                                }
                            }
                        } else {
                            val rows = remainingItems.chunked(3)
                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                rows.forEach { rowItems ->
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f)
                                    ) {
                                        rowItems.forEach { item ->
                                            Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                                                DraggableColorItemCard(
                                                    item = item,
                                                    isSelected = selectedItem?.id == item.id,
                                                    onSelect = {
                                                        selectedItem = if (selectedItem?.id == item.id) null else item
                                                    },
                                                    onDragDropToBucket = { dropPositionWindow ->
                                                        var matchedBucket: TargetColorType? = null
                                                        for ((type, bounds) in bucketBounds) {
                                                            if (bounds.contains(dropPositionWindow)) {
                                                                matchedBucket = type
                                                                break
                                                            }
                                                        }
                                                        if (matchedBucket != null) {
                                                            attemptPlaceItemInBucket(item, matchedBucket)
                                                        }
                                                    }
                                                )
                                            }
                                        }
                                        repeat(3 - rowItems.size) {
                                            Spacer(modifier = Modifier.weight(1f))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // --- 3. Color Containers / Buckets (Drop Targets) ---
                Text(
                    text = "Recipientes das Cores 🪣",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF334155),
                    modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(115.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    TargetColorType.entries.forEach { colorType ->
                        val sortedList = when (colorType) {
                            TargetColorType.RED -> sortedRedItems
                            TargetColorType.BLUE -> sortedBlueItems
                            TargetColorType.YELLOW -> sortedYellowItems
                        }

                        val isAnimating = animatingBucket == colorType
                        val isHighlighted = selectedItem?.colorType == colorType

                        ColorBucketContainer(
                            colorType = colorType,
                            sortedItems = sortedList,
                            isAnimating = isAnimating,
                            isHighlighted = isHighlighted,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .onGloballyPositioned { coordinates ->
                                    bucketBounds[colorType] = coordinates.boundsInWindow()
                                }
                                .clickable {
                                    // Tap to drop if an item was selected
                                    val currentSelected = selectedItem
                                    if (currentSelected != null) {
                                        attemptPlaceItemInBucket(currentSelected, colorType)
                                    }
                                }
                        )
                    }
                }
            }

            // --- Celebration Particle Overlay ---
            if (showCelebrationEffects || showLevelCompleteDialog) {
                CelebrationParticleOverlay()
                ConfettiEffect()
            }

            // --- Level Completion Dialog ---
            if (showLevelCompleteDialog) {
                LevelCompletionDialog(
                    level = currentLevel,
                    score = score,
                    onNextLevel = {
                        currentLevel++
                        loadLevel(currentLevel)
                    },
                    onRestart = {
                        loadLevel(currentLevel)
                    }
                )
            }
        }
    }
}

// --- Draggable & Selectable Item Component ---
@Composable
fun DraggableColorItemCard(
    item: ColorGameItem,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onDragDropToBucket: (Offset) -> Unit
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }
    var itemWindowPos by remember { mutableStateOf(Offset.Zero) }

    // Pulsing scale animation if selected
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(66.dp)
            .onGloballyPositioned { coordinates ->
                itemWindowPos = coordinates.boundsInWindow().center
            }
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .graphicsLayer {
                scaleX = if (isDragging) 1.25f else if (isSelected) pulseScale else 1.0f
                scaleY = if (isDragging) 1.25f else if (isSelected) pulseScale else 1.0f
                shadowElevation = if (isDragging) 16.dp.toPx() else 4.dp.toPx()
            }
            .pointerInput(item.id) {
                detectDragGestures(
                    onDragStart = {
                        isDragging = true
                    },
                    onDragEnd = {
                        isDragging = false
                        // Calculate final drop center in window coordinates
                        val dropPos = itemWindowPos + Offset(offsetX, offsetY)
                        onDragDropToBucket(dropPos)
                        // Reset drag offset back to rack
                        offsetX = 0f
                        offsetY = 0f
                    },
                    onDragCancel = {
                        isDragging = false
                        offsetX = 0f
                        offsetY = 0f
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                )
            }
            .clickable { onSelect() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) item.colorType.containerBg else Color(0xFFF8FAFC)
        ),
        border = BorderStroke(
            width = if (isSelected) 3.dp else 1.5.dp,
            color = if (isSelected) item.colorType.color else Color(0xFFCBD5E1)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = item.emoji,
                fontSize = 26.sp
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = item.name,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B),
                textAlign = TextAlign.Center
            )
        }
    }
}

// --- Bucket Container Component ---
@Composable
fun ColorBucketContainer(
    colorType: TargetColorType,
    sortedItems: List<ColorGameItem>,
    isAnimating: Boolean,
    isHighlighted: Boolean,
    modifier: Modifier = Modifier
) {
    val scaleAnim by animateFloatAsState(
        targetValue = if (isAnimating) 1.12f else if (isHighlighted) 1.05f else 1.0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "bucketScale"
    )

    Card(
        modifier = modifier
            .scale(scaleAnim)
            .shadow(
                elevation = if (isHighlighted || isAnimating) 8.dp else 2.dp,
                shape = RoundedCornerShape(18.dp)
            ),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = colorType.containerBg),
        border = BorderStroke(
            width = if (isHighlighted || isAnimating) 3.dp else 1.5.dp,
            color = colorType.color
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header Tag
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = colorType.color
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = colorType.emoji,
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = colorType.displayName,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                }
            }

            // Bucket Graphic Face Area
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(colorType.color.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (isAnimating) "🤩" else "🪣",
                        fontSize = 24.sp
                    )
                }
            }

            // Sorted items counter & mini emojis
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (sortedItems.isEmpty()) {
                    Text(
                        text = "Arrasta para aqui!",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorType.color,
                        textAlign = TextAlign.Center
                    )
                } else {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 2.dp)
                    ) {
                        items(sortedItems) { item ->
                            Text(text = item.emoji, fontSize = 12.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(1.dp))

                Surface(
                    shape = CircleShape,
                    color = colorType.color,
                    modifier = Modifier.size(18.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "${sortedItems.size}",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

// --- Confetti / Star Sparkles Particle Overlay ---
@Composable
fun CelebrationParticleOverlay() {
    val infiniteTransition = rememberInfiniteTransition(label = "particles")
    val alphaAnim by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val random = Random(42)
        for (i in 0..25) {
            val x = random.nextFloat() * size.width
            val y = random.nextFloat() * size.height
            val radius = random.nextFloat() * 12f + 6f
            val colors = listOf(Color(0xFFFFD700), Color(0xFFFF4081), Color(0xFF00E676), Color(0xFF00E5FF))
            val color = colors[i % colors.size]

            drawCircle(
                color = color.copy(alpha = alphaAnim),
                radius = radius,
                center = Offset(x, y)
            )
        }
    }
}

// --- Level Completion Dialog ---
@Composable
fun LevelCompletionDialog(
    level: Int,
    score: Int,
    onNextLevel: () -> Unit,
    onRestart: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            Button(
                onClick = onNextLevel,
                colors = ButtonDefaults.buttonColors(containerColor = MintGreen),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "🚀 PRÓXIMO NÍVEL (NÍVEL ${level + 1} - MAIS DIFÍCIL!)",
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onRestart,
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "Repetir Nível 🔄",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )
            }
        },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("🏆 ", fontSize = 24.sp)
                Text(
                    text = "Nível $level Concluído!",
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp,
                    color = Color(0xFF0F172A)
                )
            }
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                ZeTraquinaMascot(
                    size = 72.dp,
                    showSpeechBubble = false,
                    emotion = MascotEmotion.CELEBRATING
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Excelente trabalho! Aprendeste a separar os objetos por Vermelho, Azul e Amarelo! 🎨✨",
                    fontSize = 12.sp,
                    color = Color(0xFF475569),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = SunshineYellow.copy(alpha = 0.3f),
                    border = BorderStroke(1.dp, SunshineYellow)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("⭐ Pontuação Total: ", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text("$score Estrelas", fontWeight = FontWeight.Black, fontSize = 13.sp, color = Color.Black)
                    }
                }
            }
        },
        shape = RoundedCornerShape(26.dp),
        containerColor = Color.White
    )
}
