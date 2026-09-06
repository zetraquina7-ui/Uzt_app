package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.draw.clip
import androidx.compose.ui.zIndex
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.input.pointer.pointerInput
import com.example.ui.components.AtlasNavigationTab
import com.example.ui.components.EducationalAtlasTemplate
import com.example.ui.components.AdaptiveContentScreen
import com.example.ui.components.LocalAdaptiveSizing
import com.example.viewmodel.MainViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.layout.ContentScale
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.absoluteValue

/**
 * Tab types for educational modules to enforce Panoramic Overview as the FIRST Tab.
 */
enum class TopicTabType {
    PANORAMIC,
    GRID,
    CATEGORIES,
    QUIZ
}

data class TopicTabItem(
    val type: TopicTabType,
    val title: String,
    val icon: ImageVector,
    val count: Int? = null
)

/**
 * Reusable base layout / screen for all educational topics in the "Aprender" section.
 * Built upon EducationalAtlasTemplate, enforcing that the FIRST top button/tab is the
 * Immersive Panoramic Interactive View (matching the Interactive World Map in Atlas do Zé Traquina).
 */
@Composable
fun EducationalTopicScreen(
    topicData: EducationalTopicData,
    viewModel: MainViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentTab by remember { mutableStateOf(0) } // 0 = Panorâmica, 1 = Lista/Grid, 2 = Categorias, 3 = Quiz
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryName by remember { mutableStateOf("Todos") }
    var selectedItem by remember { mutableStateOf<EducationalItem?>(null) }

    // Quiz Mode State
    var currentQuizIndex by remember { mutableStateOf(0) }
    var selectedQuizAnswer by remember { mutableStateOf("") }
    var quizAnswered by remember { mutableStateOf<Boolean?>(null) }
    var quizScore by remember { mutableStateOf(0) }

    val currentQuestion = remember(currentQuizIndex, topicData.quizQuestions) {
        if (topicData.quizQuestions.isNotEmpty()) {
            topicData.quizQuestions[currentQuizIndex % topicData.quizQuestions.size]
        } else null
    }

    // Filter items
    val filteredItems = remember(topicData.items, selectedCategoryName, searchQuery) {
        topicData.items.filter { item ->
            val matchCategory = selectedCategoryName.equals("Todos", ignoreCase = true) ||
                    selectedCategoryName.equals("Todas", ignoreCase = true) ||
                    item.category.contains(selectedCategoryName, ignoreCase = true)

            val matchSearch = searchQuery.isBlank() ||
                    item.name.contains(searchQuery, ignoreCase = true) ||
                    item.subtitle.contains(searchQuery, ignoreCase = true) ||
                    item.fact.contains(searchQuery, ignoreCase = true) ||
                    item.syllables.contains(searchQuery, ignoreCase = true) ||
                    item.category.contains(searchQuery, ignoreCase = true)

            matchCategory && matchSearch
        }
    }

    // Tab definitions: Primeira aba é SEMPRE "Início" com a ilustração/animação limpa!
    val tabs = remember(topicData) {
        val list = mutableListOf<TopicTabItem>()

        val listTabTitle = when (topicData.id) {
            "sistema_solar" -> "Planetas"
            "animais" -> "Animais"
            "corpo_humano" -> "Corpo"
            "matematica" -> "Desafios"
            "portugal" -> "Símbolos"
            "dinossauros" -> "Dinossauros"
            "alfabeto" -> "ABC"
            "vogais" -> "Vogais"
            "numeros" -> "Números"
            "frutas" -> "Frutas"
            "profissoes" -> "Profissões"
            "emocoes" -> "Emoções"
            "dias_semana" -> "Dias"
            "meses_ano" -> "Meses"
            "transportes" -> "Veículos"
            "instrumentos" -> "Sons"
            "historias" -> "Histórias"
            "palavras_novas", "dicionario" -> "Palavras"
            "saude_higiene" -> "Hábitos"
            "cores_formas" -> "Cores"
            "natureza" -> "Natureza"
            else -> "Lista"
        }

        // 1. PRIMEIRO BOTÃO (DEFAULT): VISTA INÍCIO (ILUSTRAÇÃO / ANIMAÇÃO LIMPA)
        list.add(
            TopicTabItem(
                type = TopicTabType.PANORAMIC,
                title = "Início",
                icon = Icons.Default.Home
            )
        )

        // 2. SEGUNDO BOTÃO: LISTA DE CARTÕES COM PESQUISA E FILTROS
        list.add(
            TopicTabItem(
                type = TopicTabType.GRID,
                title = listTabTitle,
                icon = Icons.Default.ViewList
            )
        )

        // 3. TERCEIRO BOTÃO = CATEGORIAS
        if (topicData.categories.size > 1) {
            list.add(
                TopicTabItem(
                    type = TopicTabType.CATEGORIES,
                    title = "Categorias",
                    icon = Icons.Default.Category
                )
            )
        }

        // 4. QUARTO BOTÃO = QUIZ
        if (topicData.quizQuestions.isNotEmpty()) {
            list.add(
                TopicTabItem(
                    type = TopicTabType.QUIZ,
                    title = "Quiz",
                    icon = Icons.Default.EmojiEvents
                )
            )
        }

        list
    }

    val currentTabItem = tabs.getOrNull(currentTab) ?: tabs.first()
    val currentTabType = currentTabItem.type

    // Map to AtlasNavigationTab for EducationalAtlasTemplate
    val atlasTabs = remember(tabs) {
        tabs.map { tab ->
            AtlasNavigationTab(
                title = tab.title,
                icon = tab.icon,
                count = tab.count
            )
        }
    }

    LaunchedEffect(topicData.id) {
        viewModel.speak("Olá amiguinho! Sou o Zé Traquina! Bem-vindo ao ecrã de ${topicData.title}!")
    }

    AdaptiveContentScreen {
        Box(modifier = Modifier.fillMaxSize()) {
            EducationalAtlasTemplate(
        title = topicData.title,
        subtitle = topicData.subtitle,
        viewModel = viewModel,
        onBack = onBack,
        modifier = modifier.testTag("educational_topic_${topicData.id}"),
        accentColor = topicData.accentColor,
        tabs = atlasTabs,
        selectedTabIndex = currentTab,
        onTabSelected = { index ->
            currentTab = index
            val tabItem = tabs.getOrNull(index) ?: tabs.first()
            when (tabItem.type) {
                TopicTabType.PANORAMIC -> viewModel.speak("Início de ${topicData.title}")
                TopicTabType.GRID -> viewModel.speak("Aqui podes pesquisar e ver todos os cartões!")
                TopicTabType.CATEGORIES -> viewModel.speak("Aqui podes escolher uma categoria específica!")
                TopicTabType.QUIZ -> {
                    selectedQuizAnswer = ""
                    quizAnswered = null
                    viewModel.speak("Vamos jogar ao Quiz do ${topicData.title}!")
                }
            }
        },
        searchQuery = if (currentTabType == TopicTabType.GRID) searchQuery else null,
        onSearchQueryChange = if (currentTabType == TopicTabType.GRID) { { searchQuery = it } } else null,
        searchPlaceholder = topicData.searchPlaceholder,
        categoryFilters = emptyList(),
        selectedCategoryName = selectedCategoryName,
        onCategorySelected = {
            selectedCategoryName = it
            viewModel.speak("A mostrar $it")
        },
        showSearchAndFilters = (currentTabType == TopicTabType.GRID)
    ) {
        // ==========================================
        // MAIN BODY CONTENT ACCORDING TO TAB TYPE
        // ==========================================
        when (currentTabType) {
            TopicTabType.PANORAMIC -> {
                // =========================================================================
                // TAB 0 (INÍCIO): VISTA SIMPLIFICADA, LIMPA E SEM INTERATIVIDADE
                // =========================================================================
                CleanInicioView(
                    topicData = topicData,
                    viewModel = viewModel
                )
            }

            TopicTabType.GRID -> {
                // =========================================================================
                // TAB 1: GRELHA DE CARTÕES DETALHADOS COM PESQUISA E FILTROS
                // =========================================================================
                if (filteredItems.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "🔍", fontSize = 40.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Nenhum elemento encontrado",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = Color(0xFF1E293B)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Experimenta pesquisar com outra palavra ou mudar o filtro!",
                                    fontSize = 13.sp,
                                    color = Color(0xFF64748B),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                } else {
                    val itemsPerPage = if (topicData.id == "dicionario") 6 else 6
                    val pages = filteredItems.chunked(itemsPerPage)
                    val pagerState = rememberPagerState(pageCount = { pages.size })
                    val coroutineScope = rememberCoroutineScope()
                    
                    Column(modifier = Modifier.fillMaxSize()) {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.weight(1f).fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            pageSpacing = 16.dp
                        ) { page ->
                            val pageItems = pages[page]
                            // Smooth transition animation when swiping between pages
                            val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                            val pageScale = 1f - (0.12f * pageOffset.coerceIn(0f, 1f))
                            val pageAlpha = 1f - (0.4f * pageOffset.coerceIn(0f, 1f))
                            val pageRotationY = if (pagerState.currentPage > page) -6f * pageOffset.coerceIn(0f, 1f) else 6f * pageOffset.coerceIn(0f, 1f)

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .graphicsLayer {
                                        scaleX = pageScale
                                        scaleY = pageScale
                                        alpha = pageAlpha
                                        rotationY = pageRotationY
                                        cameraDistance = 12f * density
                                    }
                            ) {
                                val columns = if (topicData.id == "dicionario") 2 else 3
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(columns),
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(pageItems) { item ->
                                        if (topicData.id == "dicionario") {
                                            DictionaryWordCard(
                                                item = item,
                                                onClick = {
                                                    selectedItem = item
                                                    val speech = if (item.speechText.isNotBlank()) item.speechText else "${item.name}: ${item.fact}"
                                                    viewModel.speak(speech)
                                                    viewModel.addStars(2)
                                                }
                                            )
                                        } else {
                                            AtlasItemCard(
                                                item = item,
                                                onClick = {
                                                    selectedItem = item
                                                    val speech = if (item.speechText.isNotBlank()) item.speechText else "${item.name}. ${item.fact}"
                                                    viewModel.speak(speech)
                                                    viewModel.addStars(2)
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        
                        // Page Indicators / Controls
                        if (pages.size > 1) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = {
                                        if (pagerState.currentPage > 0) {
                                            coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                                        }
                                    },
                                    enabled = pagerState.currentPage > 0,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(Color.White.copy(alpha = if (pagerState.currentPage > 0) 0.9f else 0.4f), CircleShape)
                                ) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Anterior", tint = topicData.accentColor)
                                }
                                
                                Spacer(modifier = Modifier.width(16.dp))
                                
                                Text(
                                    text = "${pagerState.currentPage + 1} / ${pages.size}",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                                
                                Spacer(modifier = Modifier.width(16.dp))
                                
                                IconButton(
                                    onClick = {
                                        if (pagerState.currentPage < pages.size - 1) {
                                            coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                                        }
                                    },
                                    enabled = pagerState.currentPage < pages.size - 1,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(Color.White.copy(alpha = if (pagerState.currentPage < pages.size - 1) 0.9f else 0.4f), CircleShape)
                                ) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Seguinte", tint = topicData.accentColor)
                                }
                            }
                        }
                    }
                }
            }

            TopicTabType.CATEGORIES -> {
                // =========================================================================
                // TAB 2: LISTA DE CATEGORIAS
                // =========================================================================
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        Text(
                            text = "Escolhe uma categoria para explorar:",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }

                    items(topicData.categories) { category ->
                        val count = if (category.name.equals("Todos", ignoreCase = true) || category.name.equals("Todas", ignoreCase = true)) {
                            topicData.items.size
                        } else {
                            topicData.items.count { it.category.contains(category.name, ignoreCase = true) }
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedCategoryName = category.name
                                    searchQuery = ""
                                    val gridIndex = tabs.indexOfFirst { it.type == TopicTabType.GRID }
                                    if (gridIndex >= 0) currentTab = gridIndex
                                    viewModel.speak("Categoria ${category.name} selecionada!")
                                },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.3f)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        shape = CircleShape,
                                        color = category.color.copy(alpha = 0.15f),
                                        modifier = Modifier.size(46.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(text = category.emoji, fontSize = 24.sp)
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = category.name,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = Color(0xFF0F172A)
                                        )
                                        Text(
                                            text = "$count elementos para aprender",
                                            fontSize = 12.sp,
                                            color = Color(0xFF64748B)
                                        )
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = topicData.accentColor,
                                    shadowElevation = 2.dp
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Ver",
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        )
                                        Icon(
                                            imageVector = Icons.Default.ChevronRight,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            TopicTabType.QUIZ -> {
                // =========================================================================
                // TAB 3: SECÇÃO DE QUIZ INTERATIVO (IDENTICO AO ATLAS WORLD QUIZ)
                // =========================================================================
                if (currentQuestion != null) {
                    val sizing = LocalAdaptiveSizing.current
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(sizing.scalePadding(12.dp))
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(sizing.scalePadding(12.dp))
                    ) {
                        // 1. Score Header Card
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("🏆", fontSize = 24.sp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text("Pontuação do Quiz", fontSize = 12.sp, color = Color.Gray)
                                        Text(
                                            "$quizScore Acertos",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Black,
                                            color = topicData.accentColor
                                        )
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = Color(0xFFE8F5E9)
                                ) {
                                    Text(
                                        text = "+5 ⭐ por acerto",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF2E7D32),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }

                        // 2. Question Card
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(22.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(sizing.scalePadding(16.dp)),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = currentQuestion.emoji,
                                    fontSize = sizing.scaleFont(52.sp)
                                )
                                Spacer(modifier = Modifier.height(sizing.scalePadding(6.dp)))
                                Text(
                                    text = "Quiz do Zé Traquina",
                                    fontSize = sizing.scaleFont(12.sp),
                                    color = Color.Gray,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = currentQuestion.question,
                                    fontSize = sizing.scaleFont(16.sp),
                                    fontWeight = FontWeight.Black,
                                    color = topicData.accentColor,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(sizing.scalePadding(4.dp)))
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = Color(0xFFF0F4F8)
                                ) {
                                    Text(
                                        text = "Pergunta ${(currentQuizIndex % topicData.quizQuestions.size) + 1} de ${topicData.quizQuestions.size}",
                                        fontSize = sizing.scaleFont(11.sp),
                                        color = Color.DarkGray,
                                        modifier = Modifier.padding(
                                            horizontal = sizing.scalePadding(8.dp),
                                            vertical = sizing.scalePadding(4.dp)
                                        )
                                    )
                                }
                            }
                        }

                        // 3. Options List
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            currentQuestion.options.forEach { option ->
                                val isSelected = selectedQuizAnswer == option
                                val isCorrect = option.equals(currentQuestion.correctAnswer, ignoreCase = true)
                                val buttonColor = when {
                                    quizAnswered == null -> Color.White
                                    isCorrect -> Color(0xFFC8E6C9)
                                    isSelected && !isCorrect -> Color(0xFFFFCDD2)
                                    else -> Color.White
                                }
                                val borderColor = when {
                                    quizAnswered == null -> topicData.accentColor.copy(alpha = 0.3f)
                                    isCorrect -> Color(0xFF2E7D32)
                                    isSelected && !isCorrect -> Color(0xFFC62828)
                                    else -> Color.LightGray.copy(alpha = 0.4f)
                                }

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(enabled = quizAnswered == null) {
                                            selectedQuizAnswer = option
                                            val correct = option.equals(currentQuestion.correctAnswer, ignoreCase = true)
                                            quizAnswered = correct
                                            if (correct) {
                                                quizScore++
                                                viewModel.addStars(5)
                                                viewModel.speak("Certo! ${currentQuestion.explanation}")
                                            } else {
                                                viewModel.speak("A resposta certa era ${currentQuestion.correctAnswer}.")
                                            }
                                        },
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = buttonColor),
                                    border = BorderStroke(1.5.dp, borderColor)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = option,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp,
                                            color = Color(0xFF0F172A),
                                            modifier = Modifier.weight(1f)
                                        )

                                        if (quizAnswered != null) {
                                            if (isCorrect) {
                                                Text("✅", fontSize = 20.sp)
                                            } else if (isSelected) {
                                                Text("❌", fontSize = 20.sp)
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // Next Question Button
                        if (quizAnswered != null) {
                            Button(
                                onClick = {
                                    currentQuizIndex++
                                    selectedQuizAnswer = ""
                                    quizAnswered = null
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = topicData.accentColor),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp)
                            ) {
                                Text(
                                    "Próxima Pergunta ➡️",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Detail Dialog (Matching Atlas Country Detail Modal)
    selectedItem?.let { item ->
        AtlasDetailDialog(
            item = item,
            accentColor = topicData.accentColor,
            onDismiss = { selectedItem = null },
            onSpeak = {
                val speech = if (item.speechText.isNotBlank()) item.speechText else "${item.name}. ${item.fact}"
                viewModel.speak(speech)
            }
        )
    }
}
}
}

// =========================================================================================
// CLEAN "INÍCIO" VIEW (VISTA SIMPLIFICADA, LIMPA E SEM INTERATIVIDADE)
// =========================================================================================

/**
 * Clean, simplified "Início" view showing ONLY the main topic illustration or animation,
 * occupying all available vertical and horizontal screen space without cropping, distortion, or vertical scrolling.
 * Interactivity is completely disabled on this tab as per specification.
 */
@Composable
private fun CleanInicioView(
    topicData: EducationalTopicData,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = androidx.compose.ui.platform.LocalContext.current

    // Map of all official high-resolution posters for each topic to avoid secondary helper components
    val officialPosterUrl = remember(topicData.id) {
        when (topicData.id.lowercase().trim()) {
            "alfabeto" -> "https://files.catbox.moe/udfo3a.png"
            "numeros" -> "https://files.catbox.moe/so9awi.png"
            "vogais" -> "https://files.catbox.moe/bn9oir.png"
            "animais" -> "https://files.catbox.moe/mp6m9e.jpg"
            "natureza" -> "https://files.catbox.moe/xf507z.webp"
            "corpo_humano" -> "https://files.catbox.moe/82lzbj.jpg"
            "portugal", "historias" -> "https://files.catbox.moe/zf4c71.png"
            "dinossauros" -> "https://files.catbox.moe/mxbe80.jpg"
            "matematica" -> "https://files.catbox.moe/jgkz7i.png"
            "cores_formas" -> "https://files.catbox.moe/sxxmx4.webp"
            "frutas" -> "https://files.catbox.moe/6smgux.jpg"
            "saude_higiene", "saude" -> "https://files.catbox.moe/a8ahhg.png"
            "profissoes" -> "https://files.catbox.moe/pxnyml.jpg"
            "transportes" -> "https://files.catbox.moe/il2z4u.jpg"
            "instrumentos" -> "https://files.catbox.moe/9rbtlx.jpg"
            "emocoes" -> "https://files.catbox.moe/m9s9i5.jpg"
            "dias_semana" -> "https://files.catbox.moe/2dpgp6.jpg"
            "meses_ano" -> "https://files.catbox.moe/jjnzkb.jpg"
            "dicionario", "palavras_novas", "livro_magico" -> "https://files.catbox.moe/cqm5mc.jpg"
            else -> null
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (topicData.id.lowercase().trim() == "sistema_solar") {
            // System Orbit Animation without speed buttons or clickable hotspots
            PanoramicSolarSystemCanvas(
                topicData = topicData,
                activeItem = null,
                onSelectItem = {},
                isCleanView = true,
                modifier = Modifier.fillMaxSize()
            )
        } else if (officialPosterUrl != null) {
            // Load the official poster image directly on the screen background, scaling to fit the full available area without cropping or scrolling
            coil.compose.AsyncImage(
                model = coil.request.ImageRequest.Builder(context)
                    .data(officialPosterUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Pôster de ${topicData.title}",
                contentScale = androidx.compose.ui.layout.ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // General fallback view for general topics
            PanoramicGeneralTopicCanvas(
                topicData = topicData,
                activeItem = null,
                onSelectItem = {},
                modifier = Modifier.fillMaxSize()
            )
        }

        // Transparent touch blocker consuming all touch gestures to guarantee zero interactivity in "Início" view
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { /* Touch events disabled on Início tab */ }
                }
        )
    }
}

// =========================================================================================
// PANORAMIC INTERACTIVE TOPIC VIEW (VISTA PANORÂMICA E MAPA INTERATIVO PRINCIPAL)
// =========================================================================================

/**
 * PanoramicInteractiveTopicView renders a large, immersive visual representation
 * for each topic (like the Interactive World Map in the Atlas do Zé Traquina).
 */
@Composable
fun PanoramicInteractiveTopicView(
    topicData: EducationalTopicData,
    onSelectItem: (EducationalItem) -> Unit,
    onSwitchToGrid: () -> Unit,
    viewModel: MainViewModel
) {
    var activeItem by remember { mutableStateOf<EducationalItem?>(null) }
    var soundEffectBadge by remember { mutableStateOf<String?>(null) }
    var challengeTarget by remember(topicData.id) { mutableStateOf(topicData.items.shuffled().firstOrNull()) }
    var challengeCompleted by remember { mutableStateOf(false) }
    var comboCount by remember { mutableIntStateOf(0) }
    var showSuperComboCelebration by remember { mutableStateOf(false) }
    var isCanvasDarkMode by remember { mutableStateOf(topicData.id == "sistema_solar") }

    val soundEffectsMap = mapOf(
        "sol" to "☀️ SHINE!", "terra" to "🌍 VROOM!", "marte" to "🪐 PLIM!", "saturno" to "💫 SWOOSH!", "jupiter" to "🌟 BOOM!",
        "leao" to "🦁 ROAAR!", "golfinho" to "🐬 GLUB-GLUB!", "cao" to "🐶 AU-AU!", "gato" to "🐱 MIAU!", "elefante" to "🐘 TRUUU!", "macaco" to "🐒 UH-UH-AH-AH!",
        "coracao" to "🫀 BUMP-BUMP!", "cerebro" to "🧠 PLINK!", "pulmoes" to "🫁 WHOOSH!", "ossos" to "🦴 CLACK!",
        "trex" to "🦖 ROAAAR!", "triceratops" to "🦕 STOMP!", "lisboa" to "🏰 TCHIM!", "porto" to "🍷 TRIM-TRIM!", "acores" to "🌋 WHAM!",
        "bombeiro" to "🚒 NII-NOO!", "medico" to "🩺 TUM-TUM!", "policia" to "🚨 WUU-WUU!", "astronauta" to "🚀 VROOM!", "cozinheiro" to "🍳 NHAM-NHAM!", "professor" to "📚 TRIM-TRIM!", "piloto" to "✈️ WHOOSH!",
        "carro" to "🚗 BRRRUM!", "aviao" to "✈️ WHOOSH!", "comboio" to "🚂 CHUU-CHUU!", "barco" to "🚢 TUU-TUU!", "bicicleta" to "🚲 TRING-TRING!",
        "piano" to "🎹 PLINK-PLONK!", "guitarra" to "🎸 TCHIM!", "bateria" to "🥁 BUM-BUM-TCHÁ!", "flauta" to "🎶 TUU-TUU!", "violino" to "🎻 ZING-ZING!",
        "feliz" to "😄 YEAH!", "triste" to "😢 SNIF!", "zangado" to "😠 GRRR!", "calmo" to "🧘 OHM...", "surpreendido" to "😲 UAU!"
    )

    LaunchedEffect(challengeTarget) {
        if (challengeTarget != null) {
            if (topicData.id == "dinossauros") {
                val correctName = when (challengeTarget?.id) {
                    "dino_trex" -> "T-Rex"
                    "dino_triceratops" -> "Triceratops"
                    "dino_braquio" -> "Braquiossauro"
                    "dino_ptero" -> "Pterodáctilo"
                    else -> challengeTarget?.name ?: ""
                }
                viewModel.speak("O Zé Traquina tem um desafio! Onde está o $correctName? Consegues encontrá-lo?")
            } else if (topicData.id == "saude_higiene" || topicData.id == "saude") {
                viewModel.speak("O Zé Traquina tem um desafio! Consegues encontrar o hábito de ${challengeTarget?.name}?")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 12.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // CHALLENGE PANEL FROM ZÉ TRAQUINA AT THE TOP
        if (false) {
            val correctName = if (topicData.id == "dinossauros") {
                when (challengeTarget?.id) {
                    "dino_trex" -> "T-Rex"
                    "dino_triceratops" -> "Triceratops"
                    "dino_braquio" -> "Braquiossauro"
                    "dino_ptero" -> "Pterodáctilo"
                    else -> challengeTarget?.name ?: ""
                }
            } else {
                challengeTarget?.name ?: ""
            }
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (challengeCompleted) Color(0xFFDCFCE7) else Color(0xFFFEF3C7)
                ),
                border = BorderStroke(
                    width = 2.dp,
                    color = if (challengeCompleted) Color(0xFF22C55E) else Color(0xFFF59E0B)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(text = if (challengeCompleted) "🎉" else "🤠", fontSize = 32.sp)
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (challengeCompleted) "Desafio Concluído! 🌟" else "Desafio do Zé Traquina! 🎯",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (challengeCompleted) Color(0xFF15803D) else Color(0xFFB45309)
                        )
                        Text(
                            text = if (challengeCompleted) {
                                "Parabéns! Encontraste $correctName! Ganhaste +5 estrelas! ⭐"
                            } else {
                                if (topicData.id == "dinossauros") "Onde está o $correctName? Consegues encontrá-lo no desfile?" else "Consegues encontrar o hábito de $correctName? Toca nele!"
                            },
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black,
                            color = if (challengeCompleted) Color(0xFF166534) else Color(0xFF78350F)
                        )
                    }
                    if (!challengeCompleted) {
                        Button(
                            onClick = {
                                if (topicData.id == "dinossauros") {
                                    viewModel.speak("Onde está o $correctName? Procura bem no desfile de dinossauros!")
                                } else {
                                    viewModel.speak("Consegues encontrar o hábito de $correctName? Toca no cartão!")
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD97706)),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text("Repetir 🔊", fontSize = 10.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Button(
                            onClick = {
                                val validTargets = topicData.items.filter { it.id != "dino_fossil" }
                                challengeTarget = if (validTargets.isNotEmpty()) validTargets.shuffled().first() else topicData.items.shuffled().first()
                                challengeCompleted = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16A34A)),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text("Outro 🔁", fontSize = 10.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
        // SUPER COMBO CELEBRATION BANNER
        if (showSuperComboCelebration) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF08A)),
                border = BorderStroke(2.dp, Color(0xFFEAB308)),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("💥", fontSize = 32.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("SUPER EXPLORADOR! 🔥 COMBO x3!", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color(0xFF854D0E))
                            Text("Ganhaste BÓNUS de +10 ESTRELAS! 🌟🌟🌟", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFA16207))
                        }
                    }
                    Button(
                        onClick = { showSuperComboCelebration = false },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCA8A04)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Uau! 🚀", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }

        // TOPIC CUSTOM CANVAS / MAP CARD
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            border = BorderStroke(1.5.dp, topicData.accentColor.copy(alpha = 0.35f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Header of Panoramic Card with Interactive Tools
                if (topicData.id !in listOf("corpo_humano", "animais", "frutas", "matematica", "portugal", "alfabeto", "vogais", "dinossauros", "saude_higiene", "numeros", "profissoes", "transportes", "instrumentos", "emocoes", "dias_semana", "meses_ano")) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(topicData.iconEmoji, fontSize = 28.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = topicData.title,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF0F172A)
                                )
                                Text(
                                    text = topicData.subtitle,
                                    fontSize = 11.5.sp,
                                    color = Color(0xFF64748B)
                                )
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            // DAY / NIGHT THEME TOGGLE
                            Surface(
                                modifier = Modifier.clickable { isCanvasDarkMode = !isCanvasDarkMode },
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFF0284C7)
                            ) {
                                Text(
                                    text = if (isCanvasDarkMode) "☀️ Dia" else "🌙 Noite",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                }

                // DYNAMIC PANORAMIC VISUAL CANVAS BASED ON TOPIC ID
                val handleSelect: (EducationalItem) -> Unit = { item ->
                    activeItem = item
                    onSelectItem(item)

                    // Combo increment logic
                    comboCount++
                    if (comboCount >= 3) {
                        comboCount = 0
                        showSuperComboCelebration = true
                        viewModel.addStars(10)
                        viewModel.speak("Combo Triplo! Fantástico! Ganhaste bónus de 10 estrelas!")
                    }

                    // Find matching sound badge or generate fun default badge
                    val key = soundEffectsMap.keys.find { item.id.contains(it, ignoreCase = true) }
                    soundEffectBadge = soundEffectsMap[key] ?: "✨ ${item.emoji} PLIM!"

                    // Check if hit challenge target
                    if (challengeTarget?.id == item.id && !challengeCompleted) {
                        challengeCompleted = true
                        viewModel.addStars(5)
                        viewModel.speak("Fantástico! Encontraste o ${item.name}! Ganhaste mais 5 estrelas!")
                    } else {
                        viewModel.addStars(2)
                        val msg = if (item.speechText.isNotBlank()) item.speechText else "Isto é o ${item.name}! ${item.fact}"
                        viewModel.speak(msg)
                    }
                }

                // QUADRADO CENTRAL: IMAGEM INTERATIVA COM HOTSPOTS E SELETOR
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    when (topicData.id) {
                        "sistema_solar" -> PanoramicSolarSystemCanvas(topicData = topicData, activeItem = activeItem, onSelectItem = handleSelect)
                        "animais" -> PanoramicAnimalsCanvas(topicData = topicData, activeItem = activeItem, onSelectItem = handleSelect, viewModel = viewModel)
                        "corpo_humano" -> PanoramicHumanBodyCanvas(topicData = topicData, activeItem = activeItem, onSelectItem = handleSelect, viewModel = viewModel)
                        "portugal" -> PanoramicPortugalCanvas(topicData = topicData, activeItem = activeItem, onSelectItem = handleSelect, viewModel = viewModel)
                        "dinossauros" -> PanoramicDinosaursCanvas(topicData = topicData, activeItem = activeItem, onSelectItem = handleSelect, viewModel = viewModel)
                        "profissoes" -> PanoramicProfessionsCanvas(topicData = topicData, activeItem = activeItem, onSelectItem = handleSelect, viewModel = viewModel)
                        "transportes" -> PanoramicTransportCanvas(topicData = topicData, activeItem = activeItem, onSelectItem = handleSelect, viewModel = viewModel)
                        "instrumentos" -> PanoramicInstrumentsCanvas(topicData = topicData, activeItem = activeItem, onSelectItem = handleSelect, viewModel = viewModel)
                        "emocoes" -> PanoramicEmotionsCanvas(topicData = topicData, activeItem = activeItem, onSelectItem = handleSelect, viewModel = viewModel)
                        "dias_semana" -> PanoramicDiasSemanaCanvas(topicData = topicData, activeItem = activeItem, onSelectItem = handleSelect, viewModel = viewModel)
                        "meses_ano" -> PanoramicMesesAnoCanvas(topicData = topicData, activeItem = activeItem, onSelectItem = handleSelect, viewModel = viewModel)
                        "alfabeto" -> PanoramicAlphabetGardenCanvas(topicData = topicData, activeItem = activeItem, onSelectItem = handleSelect, viewModel = viewModel)
                        "numeros" -> PanoramicNumbersGardenCanvas(topicData = topicData, activeItem = activeItem, onSelectItem = handleSelect, viewModel = viewModel)
                        "vogais" -> PanoramicVowelsCanvas(topicData = topicData, activeItem = activeItem, onSelectItem = handleSelect, viewModel = viewModel)
                        "matematica", "cores_formas" -> PanoramicMathematicsPanoramicCanvas(topicData = topicData, activeItem = activeItem, onSelectItem = handleSelect, viewModel = viewModel)
                        "frutas" -> PanoramicFruitsCanvas(topicData = topicData, activeItem = activeItem, onSelectItem = handleSelect, viewModel = viewModel)
                        "saude_higiene" -> SaudeHigienePanoramicCanvas(topicData = topicData, activeItem = activeItem, onSelectItem = handleSelect, viewModel = viewModel)
                        else -> PanoramicGeneralTopicCanvas(topicData = topicData, activeItem = activeItem, onSelectItem = handleSelect)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // CARD INFORMATIVO DO ITEM SELECIONADO OU GUIA INICIAL (DIRETAMENTE POR BAIXO DA IMAGEM)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (activeItem != null) Color(0xFFFFFBEB) else Color(0xFFF8FAFC)
                    ),
                    border = BorderStroke(
                        width = 1.5.dp,
                        color = if (activeItem != null) Color(0xFFF59E0B) else topicData.accentColor.copy(alpha = 0.3f)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        if (activeItem != null) {
                            val item = activeItem!!
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(item.emoji, fontSize = 28.sp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = item.name,
                                                fontSize = 17.sp,
                                                fontWeight = FontWeight.Black,
                                                color = Color(0xFF0F172A)
                                            )
                                            if (item.syllables.isNotBlank()) {
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Surface(
                                                    shape = RoundedCornerShape(8.dp),
                                                    color = topicData.accentColor.copy(alpha = 0.15f)
                                                ) {
                                                    Text(
                                                        text = item.syllables,
                                                        fontSize = 10.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = topicData.accentColor,
                                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                    )
                                                }
                                            }
                                        }
                                        Text(
                                            text = item.subtitle.ifBlank { item.category },
                                            fontSize = 11.5.sp,
                                            color = Color(0xFF64748B),
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }

                                IconButton(
                                    onClick = { activeItem = null },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Fechar",
                                        tint = Color.Gray
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = if (item.fact.isNotBlank()) item.fact else item.speechText,
                                fontSize = 12.5.sp,
                                color = Color(0xFF334155),
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(
                                    onClick = {
                                        val speech = if (item.speechText.isNotBlank()) item.speechText else "${item.name}. ${item.fact}"
                                        viewModel.speak(speech)
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD97706)),
                                    shape = RoundedCornerShape(10.dp),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text("🔊 Ouvir Som e Pronúncia", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                }

                                Button(
                                    onClick = { onSelectItem(item) },
                                    colors = ButtonDefaults.buttonColors(containerColor = topicData.accentColor),
                                    shape = RoundedCornerShape(10.dp),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text("📜 Ver Cartão Completo", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                }
                            }
                        } else {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text("🤠", fontSize = 26.sp)
                                Column {
                                    Text(
                                        text = "Zé Traquina diz:",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = topicData.accentColor
                                    )
                                    Text(
                                        text = "Toca num desenho da imagem ou num cartão abaixo para aprender!",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1E293B)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // SELETOR RÁPIDO DE ELEMENTOS EM CHIPS (ROLA HORIZONTALMENTE)
                Column {
                    Text(
                        text = "🌟 Todos os Elementos (Toca para ver o cartão):",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF334155)
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        contentPadding = PaddingValues(horizontal = 2.dp)
                    ) {
                        items(topicData.items) { item ->
                            val isSelected = activeItem?.id == item.id
                            Surface(
                                modifier = Modifier.clickable {
                                    activeItem = item
                                    val speech = if (item.speechText.isNotBlank()) item.speechText else "${item.name}. ${item.fact}"
                                    viewModel.speak(speech)
                                    viewModel.addStars(2)
                                },
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) topicData.accentColor else Color(0xFFF1F5F9),
                                border = BorderStroke(
                                    width = if (isSelected) 1.5.dp else 1.dp,
                                    color = if (isSelected) topicData.accentColor else Color(0xFFCBD5E1)
                                )
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(item.emoji, fontSize = 16.sp)
                                    Text(
                                        text = item.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        color = if (isSelected) Color.White else Color(0xFF0F172A)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // INTERACTIVE KIDS SOUNDBOARD STRIP
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val soundButtons = when (topicData.id) {
                        "alfabeto", "vogais" -> listOf(
                            "✨ Magia" to "Plim! Fizeste magia no Alfabeto!",
                            "🔊 Ouvir" to "Escuta com atenção a voz das letras mágicas!",
                            "✍️ Escrever" to "Desenha a letra no ar com o teu dedinho!",
                            "⭐ Estrela" to "Brilha, brilha, Estrelinha do Alfabeto!"
                        )
                        "sistema_solar" -> listOf(
                            "✨ Magia" to "Plim! Fizeste magia espacial!",
                            "🚀 Descolar" to "3, 2, 1... Descolar rumo às estrelas!",
                            "⭐ Estrela" to "Brilha, brilha, estrelinha!",
                            "🦁 Som" to "Roaaar! Os sons do universo!"
                        )
                        else -> listOf(
                            "✨ Magia" to "Plim! Fizeste magia da aprendizagem!",
                            "🚀 Descolar" to "3, 2, 1... Descolar rumo ao saber!",
                            "⭐ Estrela" to "Brilha, brilha, Estrela do Zé Traquina!",
                            "🦁 Som" to "Roaaar! Os sons do saber!"
                        )
                    }

                    soundButtons.forEach { (label, speech) ->
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    soundEffectBadge = label
                                    viewModel.addStars(1)
                                    viewModel.speak(speech)
                                },
                            shape = RoundedCornerShape(10.dp),
                            color = topicData.accentColor.copy(alpha = 0.08f),
                            border = BorderStroke(1.dp, topicData.accentColor.copy(alpha = 0.25f))
                        ) {
                            Text(
                                text = label,
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.Bold,
                                color = topicData.accentColor,
                                modifier = Modifier.padding(vertical = 6.dp),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // BOTTOM ACTION TOOLBAR
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = {
                            val msg = if (activeItem != null) {
                                "O ${activeItem?.name} é incrível! Toca noutros desenhos para aprender e jogar!"
                            } else {
                                "Estás no mapa interativo do ${topicData.title}! Toca nos elementos para descobrir curiosidades e ganhar estrelas!"
                            }
                            viewModel.speak(msg)
                        },
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, topicData.accentColor.copy(alpha = 0.5f))
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Ouvir Zé",
                            modifier = Modifier.size(16.dp),
                            tint = topicData.accentColor
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Ouvir Zé 🔊",
                            color = topicData.accentColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    Button(
                        onClick = onSwitchToGrid,
                        colors = ButtonDefaults.buttonColors(containerColor = topicData.accentColor),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Ver Lista em Cartões ➡️",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

// =========================================================================================
// CUSTOM PANORAMIC CANVASES FOR SPECIFIC TOPICS
// =========================================================================================

/**
 * 🌌 SOLAR SYSTEM ORBIT PANORAMIC CANVAS
 */
@Composable
private fun PanoramicSolarSystemCanvas(
    topicData: EducationalTopicData,
    activeItem: EducationalItem?,
    onSelectItem: (EducationalItem) -> Unit,
    isCleanView: Boolean = false,
    modifier: Modifier = Modifier
) {
    var isOrbitPaused by remember { mutableStateOf(false) }
    var speedMultiplier by remember { mutableFloatStateOf(1f) }

    val infiniteTransition = rememberInfiniteTransition(label = "solar_orbits")
    val orbitRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween((25000 / speedMultiplier).toInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "planet_orbit_rotation"
    )

    // Sun pulse animation
    val sunScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.12f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sun_pulse"
    )

    val containerModifier = if (isCleanView) {
        modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF030712), Color(0xFF0F172A), Color(0xFF1E1B4B))
                ),
                shape = RoundedCornerShape(18.dp)
            )
            .clip(RoundedCornerShape(18.dp))
    } else {
        modifier
            .fillMaxWidth()
            .aspectRatio(1.30f)
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF030712), Color(0xFF0F172A), Color(0xFF1E1B4B))
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
    }

    Box(
        modifier = containerModifier,
        contentAlignment = Alignment.Center
    ) {
        // Orbit rings background
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val r1 = 45.dp.toPx()
            val r2 = 75.dp.toPx()
            val r3 = 105.dp.toPx()
            val r4 = 135.dp.toPx()
            val radii = listOf(r1, r2, r3, r4)
            radii.forEach { r ->
                drawCircle(
                    color = Color.White.copy(alpha = 0.18f),
                    radius = r,
                    center = center,
                    style = Stroke(width = 1.2.dp.toPx())
                )
            }
        }

        // Orbit speed / pause controls top right
        if (!isCleanView) {
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Surface(
                    modifier = Modifier.clickable { isOrbitPaused = !isOrbitPaused },
                    shape = RoundedCornerShape(10.dp),
                    color = Color.White.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = if (isOrbitPaused) "▶️ Rodar" else "⏸️ Parar",
                        fontSize = 11.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Surface(
                    modifier = Modifier.clickable {
                        speedMultiplier = if (speedMultiplier >= 2f) 0.5f else speedMultiplier + 0.5f
                    },
                    shape = RoundedCornerShape(10.dp),
                    color = Color.White.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = "⚡ ${speedMultiplier}x",
                        fontSize = 11.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // Central Sun
        val sunItem = topicData.items.find { it.id.contains("sol", ignoreCase = true) } ?: topicData.items.firstOrNull()
        val isSunActive = activeItem?.id == sunItem?.id
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable { sunItem?.let { onSelectItem(it) } }
        ) {
            Surface(
                modifier = Modifier
                    .size((68 * (if (isSunActive) 1.2f else sunScale)).dp),
                shape = CircleShape,
                color = Color(0xFFFFD54F),
                shadowElevation = 12.dp,
                border = BorderStroke(if (isSunActive) 3.5.dp else 2.dp, if (isSunActive) Color.White else Color(0xFFFF6F00))
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("☀️", fontSize = 34.sp)
                }
            }
            if (isSunActive) {
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFFEF3C7),
                    border = BorderStroke(1.dp, Color(0xFFF59E0B))
                ) {
                    Text(
                        text = "Sol ☀️",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF92400E),
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }

        // Orbiting Planets
        val planets = topicData.items.filter { !it.id.contains("sol", ignoreCase = true) }
        val effectiveRotation = if (isOrbitPaused) 0f else orbitRotation

        planets.forEachIndexed { index, planet ->
            val angleDeg = (effectiveRotation + (index * (360f / planets.size))) % 360f
            val angleRad = Math.toRadians(angleDeg.toDouble())
            val orbitRadius = (50 + (index % 4) * 30).dp.value
            val isSelected = activeItem?.id == planet.id

            Box(
                modifier = Modifier
                    .graphicsLayer {
                        translationX = (orbitRadius * cos(angleRad) * 2.5).toFloat()
                        translationY = (orbitRadius * sin(angleRad) * 1.8).toFloat()
                        scaleX = if (isSelected) 1.3f else 1f
                        scaleY = if (isSelected) 1.3f else 1f
                    }
                    .clickable { onSelectItem(planet) }
            ) {
                if (isSelected) {
                    // Revealed Name Pill when Tapped
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFFEF3C7),
                        shadowElevation = 12.dp,
                        border = BorderStroke(2.5.dp, Color(0xFFF59E0B))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(planet.emoji, fontSize = 22.sp)
                            Text(
                                planet.name,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF0F172A)
                            )
                        }
                    }
                } else {
                    // Clean Planet Sphere without text label by default
                    Surface(
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.9f),
                        shadowElevation = 6.dp,
                        border = BorderStroke(1.5.dp, Color(planet.colorHex))
                    ) {
                        Box(
                            modifier = Modifier.size(36.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(planet.emoji, fontSize = 20.sp)
                        }
                    }
                }
            }
        }
    }
}

/**
 * 🦁 ANIMALS PANORAMIC CANVAS (Ecrã 'Descobre os amigos da natureza!')
 */
@Composable
private fun PanoramicAnimalsCanvas(
    topicData: EducationalTopicData,
    activeItem: EducationalItem?,
    onSelectItem: (EducationalItem) -> Unit,
    viewModel: MainViewModel
) {
    // Official Animals Image URL
    val officialAnimalsImageUrl = "https://files.catbox.moe/mp6m9e.jpg"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.30f),
        contentAlignment = Alignment.Center
    ) {
        // Official Animals Image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(officialAnimalsImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Animais Oficial",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )

        // Hotspot Overlay (using a grid to cover the animals in the image)
        Column(modifier = Modifier.fillMaxSize()) {
            topicData.items.chunked(3).forEach { rowItems ->
                Row(modifier = Modifier.weight(1f)) {
                    rowItems.forEach { item ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable {
                                    viewModel.speak("${item.name}!")
                                    onSelectItem(item)
                                }
                                .testTag("animal_hotspot_${item.id}")
                        )
                    }
                    repeat(3 - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

/**
 * 👀 HUMAN BODY PANORAMIC CANVAS (Ecrã Oficial 'Corpo Humano')
 */
@Composable
private fun PanoramicHumanBodyCanvas(
    topicData: EducationalTopicData,
    activeItem: EducationalItem?,
    onSelectItem: (EducationalItem) -> Unit,
    viewModel: MainViewModel? = null
) {
    // Official Human Body Image URL
    val officialBodyImageUrl = "https://files.catbox.moe/82lzbj.jpg"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.30f),
        contentAlignment = Alignment.Center
    ) {
        // Official Human Body Image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(officialBodyImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Corpo Humano Oficial",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )

        // Hotspot Overlay (5 rows x 3 columns = 15 cards matching image)
        Column(modifier = Modifier.fillMaxSize()) {
            topicData.items.chunked(3).forEach { rowItems ->
                Row(modifier = Modifier.weight(1f)) {
                    rowItems.forEach { item ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable {
                                    viewModel?.speak("${item.name}!")
                                    onSelectItem(item)
                                }
                                .testTag("body_hotspot_${item.id}")
                        )
                    }
                    repeat(3 - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

/**
 * 🇵🇹 PORTUGAL MAP PANORAMIC CANVAS (Clean Interactive Official Image Map)
 */
@Composable
private fun PanoramicPortugalCanvas(
    topicData: EducationalTopicData,
    activeItem: EducationalItem?,
    onSelectItem: (EducationalItem) -> Unit,
    viewModel: MainViewModel? = null
) {
    val ptNorte = remember {
        EducationalItem(
            id = "pt_norte",
            name = "Norte de Portugal",
            category = "Regiões",
            emoji = "🌉",
            subtitle = "Porto, Barcelos e Guimarães",
            fact = "O Norte de Portugal é famoso pelo Rio Douro, a Ponte D. Luís no Porto, o Castelo de Guimarães e o Galo de Barcelos!",
            details = mapOf("Capital Regional" to "Porto", "Monumentos" to "Ponte D. Luís, Castelo de Guimarães", "Símbolo" to "Galo de Barcelos", "Rio" to "Douro"),
            speechText = "No Norte fica a linda cidade do Porto, o Castelo de Guimarães e o Galo de Barcelos!",
            syllables = "NOR • TE",
            badge = "NORTE 🌉",
            colorHex = 0xFF0284C7
        )
    }

    val ptCentro = remember {
        EducationalItem(
            id = "pt_centro",
            name = "Centro de Portugal",
            category = "Regiões",
            emoji = "🖼️",
            subtitle = "Azulejos, Aveiro e Serra da Estrela",
            fact = "O Centro de Portugal tem os barcos moliceiros de Aveiro, os bonitos azulejos azuis e brancos e a neve na Serra da Estrela!",
            details = mapOf("Cidade Histórica" to "Coimbra", "Litoral" to "Aveiro e Moliceiros", "Montanha" to "Serra da Estrela (Neve)", "Arte" to "Azulejo Português"),
            speechText = "O Centro tem os barcos de Aveiro, azulejos maravilhosos e neve na Serra da Estrela!",
            syllables = "CEN • TRO",
            badge = "CENTRO 🏔️",
            colorHex = 0xFF3F51B5
        )
    }

    val ptLisboa = remember {
        EducationalItem(
            id = "pt_lisboa",
            name = "Lisboa (Capital)",
            category = "Regiões",
            emoji = "🏰",
            subtitle = "Torre de Belém e Elétrico 28",
            fact = "Lisboa é a alegre capital de Portugal! Tem a histórica Torre de Belém junto ao Rio Tejo, o elétrico 28 e os deliciosos pastéis de nata!",
            details = mapOf("Estatuto" to "Capital de Portugal", "Monumentos" to "Torre de Belém, Mosteiro dos Jerónimos", "Transporte" to "Elétrico 28 Amarelo", "Doce" to "Pastel de Nata"),
            speechText = "Lisboa é a capital de Portugal! Podes andar no elétrico amarelo e comer pastéis de nata estaladiços!",
            syllables = "LIS • BOA",
            badge = "LISBOA 🚋",
            colorHex = 0xFFF59E0B
        )
    }

    val ptAlentejo = remember {
        EducationalItem(
            id = "pt_alentejo",
            name = "Alentejo",
            category = "Regiões",
            emoji = "🌾",
            subtitle = "Campos Dourados e Sobreiros",
            fact = "O Alentejo tem campos vastos de trigo e árvores de sobreiro de onde vem a cortiça, além de cantos e guitarras tradicionais!",
            details = mapOf("Paisagem" to "Campos Dourados e Planícies", "Cidades" to "Évora, Beja e Elvas", "Música" to "Cante Alentejano e Fado", "Natureza" to "Árvores de Cortiça"),
            speechText = "O Alentejo tem grandes campos dourados, sobreiros e aldeias brancas de encantar!",
            syllables = "A • LEN • TE • JO",
            badge = "ALENTEJO 🌾",
            colorHex = 0xFF84CC16
        )
    }

    val ptAlgarve = remember {
        EducationalItem(
            id = "pt_algarve",
            name = "Algarve",
            category = "Regiões",
            emoji = "🏖️",
            subtitle = "Praias Douradas e Gruta de Benagil",
            fact = "O Algarve é mundialmente famoso pelas suas praias de areia dourada, mar quente e grutas marinhas misteriosas como a de Benagil!",
            details = mapOf("Região" to "Sul de Portugal", "Clima" to "Muito Sol e Mar Quente", "Destaque" to "Gruta de Benagil", "Diversão" to "Castelos de Areia"),
            speechText = "O Algarve fica no sul de Portugal, cheio de praias fantásticas, grutas e muito sol!",
            syllables = "AL • GAR • VE",
            badge = "ALGARVE ☀️",
            colorHex = 0xFFF97316
        )
    }

    val ptAcores = remember {
        EducationalItem(
            id = "pt_acores",
            name = "Açores (Arquipélago)",
            category = "Regiões",
            emoji = "🐋",
            subtitle = "Ilhas Vulcânicas, Lagoas e Baleias",
            fact = "Os Açores são 9 ilhas vulcânicas maravilhosas no meio do oceano Atlântico, com lagoas de duas cores, vulcões e muitas baleias!",
            details = mapOf("Nº de Ilhas" to "9 Ilhas Verdes", "Destaque" to "Lagoa das Sete Cidades", "Fauna" to "Baleias e Golfinhos", "Origem" to "Vulcânica"),
            speechText = "Os Açores são nove ilhas vulcânicas no meio do mar, com lagoas verdes e baleias a nadar!",
            syllables = "A • ÇO • RES",
            badge = "AÇORES 🌋",
            colorHex = 0xFF06B6D4
        )
    }

    val ptMadeira = remember {
        EducationalItem(
            id = "pt_madeira",
            name = "Madeira (Arquipélago)",
            category = "Regiões",
            emoji = "🌺",
            subtitle = "Jardim do Atlântico e Floresta Laurissilva",
            fact = "A Madeira é uma ilha-jardim repleta de flores exóticas, montanhas verdes, a milenar Floresta Laurissilva e os carros de cesto!",
            details = mapOf("Capital" to "Funchal", "Natureza" to "Floresta Laurissilva", "Tradição" to "Carros de Cesto do Monte", "Clima" to "Primavera todo o ano"),
            speechText = "A Madeira é o Jardim do Atlântico, com florestas mágicas e flores super coloridas!",
            syllables = "MA • DEI • RA",
            badge = "MADEIRA 💐",
            colorHex = 0xFF10B981
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.30f)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val xRatio = offset.x / size.width
                    val yRatio = offset.y / size.height

                    val selectedRegion = if (xRatio < 0.42f) {
                        if (yRatio < 0.50f) ptAcores else ptMadeira
                    } else {
                        when {
                            yRatio < 0.28f -> ptNorte
                            yRatio < 0.48f -> ptCentro
                            yRatio < 0.60f -> ptLisboa
                            yRatio < 0.82f -> ptAlentejo
                            else -> ptAlgarve
                        }
                    }
                    onSelectItem(selectedRegion)
                }
            }
            .testTag("portugal_official_map_canvas"),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://files.catbox.moe/zf4c71.png")
                .crossfade(true)
                .build(),
            contentDescription = "Mapa Oficial de Portugal",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}

/**
 * 🦖 DINOSAURS JURASSIC CANVAS (Caminhada Dinâmica Pré-Histórica - Desfile Horizontal)
 */
@Composable
private fun PanoramicDinosaursCanvas(
    topicData: EducationalTopicData,
    activeItem: EducationalItem?,
    onSelectItem: (EducationalItem) -> Unit,
    viewModel: MainViewModel
) {
    val officialDinosaursImageUrl = "https://files.catbox.moe/mxbe80.jpg"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.30f),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(officialDinosaursImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Dinossauros Oficial",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )

        Column(modifier = Modifier.fillMaxSize()) {
            topicData.items.chunked(3).forEach { rowItems ->
                Row(modifier = Modifier.weight(1f)) {
                    rowItems.forEach { item ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable {
                                    viewModel.speak("${item.name}!")
                                    onSelectItem(item)
                                }
                                .testTag("dinosaurs_hotspot_${item.id}")
                        )
                    }
                    repeat(3 - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

/**
 * 🔤 ALPHABET & NUMBERS BOARD CANVAS
 */
/**
 * 🧮 MATEMÁTICA PANORAMIC CANVAS (Ecrã 'Aprender, praticar e divertir!')
 */
@Composable
private fun PanoramicMathematicsPanoramicCanvas(
    topicData: EducationalTopicData,
    activeItem: EducationalItem?,
    onSelectItem: (EducationalItem) -> Unit,
    viewModel: MainViewModel
) {
    // Official Mathematics Image URL
    val officialMathematicsImageUrl = "https://files.catbox.moe/jgkz7i.png"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.30f),
        contentAlignment = Alignment.Center
    ) {
        // Official Mathematics Image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(officialMathematicsImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Matemática Oficial",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )

        // Hotspot Overlay
        Column(modifier = Modifier.fillMaxSize()) {
            topicData.items.chunked(3).forEach { rowItems ->
                Row(modifier = Modifier.weight(1f)) {
                    rowItems.forEach { item ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable {
                                    viewModel.speak("${item.name}!")
                                    onSelectItem(item)
                                }
                                .testTag("math_hotspot_${item.id}")
                        )
                    }
                    repeat(3 - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}



/**
 * 🍎 FRUTAS PANORAMIC CANVAS (Ecrã 'Descobre os sabores da natureza!')
 */
@Composable
private fun PanoramicFruitsCanvas(
    topicData: EducationalTopicData,
    activeItem: EducationalItem?,
    onSelectItem: (EducationalItem) -> Unit,
    viewModel: MainViewModel
) {
    // Official Fruits Image URL
    val officialFruitsImageUrl = "https://files.catbox.moe/6smgux.jpg"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.30f),
        contentAlignment = Alignment.Center
    ) {
        // Official Fruits Image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(officialFruitsImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Frutas Oficial",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )

        // Hotspot Overlay
        Column(modifier = Modifier.fillMaxSize()) {
            topicData.items.chunked(3).forEach { rowItems ->
                Row(modifier = Modifier.weight(1f)) {
                    rowItems.forEach { item ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable {
                                    viewModel.speak("${item.name}!")
                                    onSelectItem(item)
                                }
                                .testTag("fruit_hotspot_${item.id}")
                        )
                    }
                    repeat(3 - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}



/**
 * 🚰 SAÚDE E HIGIENE PANORAMIC CANVAS (Ecrã 'Pequenas atitudes, grandes benefícios!')
 */
@Composable
private fun SaudeHigienePanoramicCanvas(
    topicData: EducationalTopicData,
    activeItem: EducationalItem?,
    onSelectItem: (EducationalItem) -> Unit,
    viewModel: MainViewModel
) {
    // Official Health & Hygiene Image URL
    val officialHealthImageUrl = "https://files.catbox.moe/a8ahhg.png"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.30f),
        contentAlignment = Alignment.Center
    ) {
        // Official Health Image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(officialHealthImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Saúde e Higiene Oficial",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )

        // Hotspot Overlay (Assuming 3 columns to cover the items)
        Column(modifier = Modifier.fillMaxSize()) {
            topicData.items.chunked(3).forEach { rowItems ->
                Row(modifier = Modifier.weight(1f)) {
                    rowItems.forEach { item ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable {
                                    viewModel.speak("${item.name}!")
                                    onSelectItem(item)
                                }
                                .testTag("health_hotspot_${item.id}")
                        )
                    }
                    repeat(3 - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

/**
 * 🎨 GENERAL PANORAMIC TOPIC CANVAS
 */
@Composable
private fun PanoramicGeneralTopicCanvas(
    topicData: EducationalTopicData,
    activeItem: EducationalItem?,
    onSelectItem: (EducationalItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "general_motion")
    val generalFloat by infiniteTransition.animateFloat(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(1300, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "general_item_float"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(14.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(topicData.iconEmoji, fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "✨ Pôster Ilustrado de ${topicData.title}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF0F172A)
                        )
                        Text(
                            text = "Explora os elementos com ilustrações e cores vivas!",
                            fontSize = 11.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                topicData.items.chunked(3).take(3).forEach { rowItems ->
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowItems.forEach { item ->
                            val isSelected = activeItem?.id == item.id
                            val itemColor = Color(item.colorHex)
                            val index = topicData.items.indexOf(item)
                            val itemFloat = if (isSelected) 0f else (if (index % 2 == 0) generalFloat else -generalFloat)

                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .graphicsLayer {
                                        translationY = itemFloat
                                        if (isSelected) {
                                            scaleX = 1.08f
                                            scaleY = 1.08f
                                        }
                                    }
                                    .clickable { onSelectItem(item) },
                                shape = RoundedCornerShape(14.dp),
                                color = if (isSelected) topicData.accentColor.copy(alpha = 0.18f) else itemColor.copy(alpha = 0.10f),
                                border = BorderStroke(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) topicData.accentColor else itemColor.copy(alpha = 0.35f)
                                ),
                                shadowElevation = if (isSelected) 6.dp else 2.dp
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(6.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = item.emoji,
                                        fontSize = if (isSelected) 28.sp else 24.sp
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = item.name,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color(0xFF0F172A),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        textAlign = TextAlign.Center
                                    )
                                    if (item.badge.isNotBlank()) {
                                        Text(
                                            text = item.badge,
                                            fontSize = 9.sp,
                                            color = itemColor,
                                            fontWeight = FontWeight.Bold,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
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

/**
 * 👨‍🚒 PROFESSIONS TOWN SQUARE PANORAMIC CANVAS
 */
@Composable
private fun PanoramicProfessionsCanvas(
    topicData: EducationalTopicData,
    activeItem: EducationalItem?,
    onSelectItem: (EducationalItem) -> Unit,
    viewModel: MainViewModel
) {
    // Official Professions Image URL
    val officialProfessionsImageUrl = "https://files.catbox.moe/pxnyml.jpg"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.30f),
        contentAlignment = Alignment.Center
    ) {
        // Official Professions Image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(officialProfessionsImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Profissões Oficial",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )

        // Hotspot Overlay
        Column(modifier = Modifier.fillMaxSize()) {
            topicData.items.chunked(3).forEach { rowItems ->
                Row(modifier = Modifier.weight(1f)) {
                    rowItems.forEach { item ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable {
                                    viewModel.speak("${item.name}!")
                                    onSelectItem(item)
                                }
                                .testTag("prof_hotspot_${item.id}")
                        )
                    }
                    repeat(3 - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

/**
 * 🚀 TRANSPORT VEHICLES SKY & ROAD PANORAMIC CANVAS
 */
@Composable
private fun PanoramicTransportCanvas(
    topicData: EducationalTopicData,
    activeItem: EducationalItem?,
    onSelectItem: (EducationalItem) -> Unit,
    viewModel: MainViewModel
) {
    val officialTransportImageUrl = "https://files.catbox.moe/il2z4u.jpg"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.30f),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(officialTransportImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Transportes Oficial",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )

        Column(modifier = Modifier.fillMaxSize()) {
            topicData.items.chunked(3).forEach { rowItems ->
                Row(modifier = Modifier.weight(1f)) {
                    rowItems.forEach { item ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable {
                                    viewModel.speak("${item.name}!")
                                    onSelectItem(item)
                                }
                                .testTag("transport_hotspot_${item.id}")
                        )
                    }
                    repeat(3 - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

/**
 * 🎹 MUSICAL INSTRUMENTS STAGE PANORAMIC CANVAS
 */
@Composable
private fun PanoramicInstrumentsCanvas(
    topicData: EducationalTopicData,
    activeItem: EducationalItem?,
    onSelectItem: (EducationalItem) -> Unit,
    viewModel: MainViewModel
) {
    val officialInstrumentsImageUrl = "https://files.catbox.moe/9rbtlx.jpg"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.30f),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(officialInstrumentsImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Instrumentos Oficial",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )

        Column(modifier = Modifier.fillMaxSize()) {
            topicData.items.chunked(3).forEach { rowItems ->
                Row(modifier = Modifier.weight(1f)) {
                    rowItems.forEach { item ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable {
                                    viewModel.speak("${item.name}!")
                                    onSelectItem(item)
                                }
                                .testTag("instruments_hotspot_${item.id}")
                        )
                    }
                    repeat(3 - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

/**
 * 😄 EMOTIONS GARDEN PANORAMIC CANVAS
 */
@Composable
private fun PanoramicEmotionsCanvas(
    topicData: EducationalTopicData,
    activeItem: EducationalItem?,
    onSelectItem: (EducationalItem) -> Unit,
    viewModel: MainViewModel
) {
    val officialEmotionsImageUrl = "https://files.catbox.moe/m9s9i5.jpg"
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.30f),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(officialEmotionsImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Emoções Oficial",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
        Column(modifier = Modifier.fillMaxSize()) {
            topicData.items.chunked(3).forEach { rowItems ->
                Row(modifier = Modifier.weight(1f)) {
                    rowItems.forEach { item ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable {
                                    viewModel.speak("${item.name}!")
                                    onSelectItem(item)
                                }
                                .testTag("em_hotspot_${item.id}")
                        )
                    }
                    repeat(3 - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

/**
 * 🔤 ALPHABET GARDEN PANORAMIC CANVAS (Ecrã 'Alfabeto Mágico')
 * Refactored to be a paginated horizontal carousel.
 */
@Composable
private fun PanoramicAlphabetGardenCanvas(
    topicData: EducationalTopicData,
    activeItem: EducationalItem?,
    onSelectItem: (EducationalItem) -> Unit,
    viewModel: MainViewModel
) {
    val itemsPerPage = 9
    val pages = topicData.items.chunked(itemsPerPage)
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()
    var showDetailDialog by remember { mutableStateOf<EducationalItem?>(null) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Horizontal Pager for letters with smooth swipe transition animation
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f).fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            pageSpacing = 16.dp
        ) { page ->
            val pageItems = pages[page]
            // Smooth transition animation when swiping between pages
            val pageOffset = ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
            val pageScale = 1f - (0.12f * pageOffset.coerceIn(0f, 1f))
            val pageAlpha = 1f - (0.4f * pageOffset.coerceIn(0f, 1f))
            val pageRotationY = if (pagerState.currentPage > page) -6f * pageOffset.coerceIn(0f, 1f) else 6f * pageOffset.coerceIn(0f, 1f)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = pageScale
                        scaleY = pageScale
                        alpha = pageAlpha
                        rotationY = pageRotationY
                        cameraDistance = 12f * density
                    }
            ) {
                // 3x3 Grid (3 columns x 3 rows = 9 items total per page)
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(pageItems) { item ->
                        AlphabetLetterCard(
                            item = item,
                            onClick = {
                                showDetailDialog = item
                            }
                        )
                    }
                }
            }
        }

        // Navigation Controls
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    if (pagerState.currentPage > 0) {
                        coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                    }
                },
                enabled = pagerState.currentPage > 0,
                modifier = Modifier.size(48.dp).background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Página Anterior")
            }

            Text(text = "Página ${pagerState.currentPage + 1} / ${pages.size}", fontWeight = FontWeight.Bold)

            IconButton(
                onClick = {
                    if (pagerState.currentPage < pages.size - 1) {
                        coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                    }
                },
                enabled = pagerState.currentPage < pages.size - 1,
                modifier = Modifier.size(48.dp).background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Página Seguinte")
            }
        }
    }

    // Detail Dialog
    showDetailDialog?.let { item ->
        AlertDialog(
            onDismissRequest = { showDetailDialog = null },
            title = { 
                Text(
                    text = "${item.id.replace("letra_", "").uppercase()} - ${item.name}",
                    style = MaterialTheme.typography.headlineSmall
                ) 
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = item.emoji, fontSize = 64.sp)
                    Text(
                        text = "Esta é a letra ${item.id.replace("letra_", "").uppercase()}. \n\n${item.name} começa com esta letra! ${item.fact}",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showDetailDialog = null }) { Text("Fechar") }
            }
        )
    }
}

@Composable
fun AlphabetLetterCard(item: EducationalItem, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.93f else 1f,
        label = "alphabet_card_scale"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isPressed) 1.dp else 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.25f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = item.id.replace("letra_", "").uppercase(),
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.primary
            )
            Text(text = item.emoji, fontSize = 24.sp)
            Text(
                text = item.name,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * 🔠 Animated Interactive Alphabet Letter Chip with bouncy Pop & Scale spring physics on tap
 */
@Composable
fun AlphabetLetterChip(
    letter: String,
    emoji: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    primaryColor: Color = Color(0xFF3F51B5),
    secondaryColor: Color = Color(0xFFEEF2FF),
    borderColor: Color = Color(0xFFC7D2FE)
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Bouncy spring pop & scale animation
    val scale by animateFloatAsState(
        targetValue = when {
            isPressed -> 0.88f
            isSelected -> 1.15f
            else -> 1.0f
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "alphabet_chip_scale"
    )

    val emojiScale by animateFloatAsState(
        targetValue = if (isSelected) 1.30f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "alphabet_chip_emoji_scale"
    )

    Surface(
        onClick = onClick,
        interactionSource = interactionSource,
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) primaryColor else secondaryColor,
        border = BorderStroke(
            if (isSelected) 2.dp else 1.5.dp,
            if (isSelected) primaryColor else borderColor
        ),
        shadowElevation = if (isSelected) 6.dp else if (isPressed) 0.dp else 1.5.dp,
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = letter,
                fontSize = 15.sp,
                fontWeight = FontWeight.Black,
                color = if (isSelected) Color.White else Color(0xFF1E3A8A)
            )
            Text(
                text = emoji,
                fontSize = 14.sp,
                modifier = Modifier.graphicsLayer {
                    scaleX = emojiScale
                    scaleY = emojiScale
                }
            )
        }
    }
}

/**
 * 🔢 NUMBERS GARDEN PANORAMIC CANVAS (Ecrã 'Números do Zé')
 */
@Composable
private fun PanoramicNumbersGardenCanvas(
    topicData: EducationalTopicData,
    activeItem: EducationalItem?,
    onSelectItem: (EducationalItem) -> Unit,
    viewModel: MainViewModel
) {
    // Official Numbers Image URL
    val officialNumbersImageUrl = "https://files.catbox.moe/so9awi.png"

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.30f),
            contentAlignment = Alignment.Center
        ) {
            // Official Numbers Image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(officialNumbersImageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Números Oficial",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )

            // Hotspot Overlay
            Column(modifier = Modifier.fillMaxSize()) {
                topicData.items.take(10).chunked(2).forEach { rowItems ->
                    Row(modifier = Modifier.weight(1f)) {
                        rowItems.forEach { item ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .clickable {
                                        viewModel.speak("${item.name}!")
                                        onSelectItem(item)
                                    }
                                    .testTag("number_hotspot_${item.id}")
                            )
                        }
                        repeat(2 - rowItems.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }

        // Quick Interactive Numbers Strip: Tap any number to open its magic window
        Text(
            text = "🔢 Toca num número para abrir a janela mágica:",
            fontSize = 12.5.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xFF1E293B),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp, vertical = 2.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            contentPadding = PaddingValues(horizontal = 2.dp, vertical = 2.dp)
        ) {
            items(topicData.items.take(20)) { item ->
                val isSelected = activeItem?.id == item.id
                val numBadge = item.badge.ifBlank { item.id.replace("num_", "") }
                NumberItemChip(
                    numBadge = numBadge,
                    emoji = item.emoji,
                    isSelected = isSelected,
                    onClick = {
                        viewModel.speak("${item.name}!")
                        onSelectItem(item)
                    }
                )
            }
        }
    }
}

/**
 * 🔢 Animated Interactive Number Chip with bouncy Pop & Scale spring physics on tap
 */
@Composable
fun NumberItemChip(
    numBadge: String,
    emoji: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = when {
            isPressed -> 0.88f
            isSelected -> 1.15f
            else -> 1.0f
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "number_chip_scale"
    )

    val emojiScale by animateFloatAsState(
        targetValue = if (isSelected) 1.30f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "number_chip_emoji_scale"
    )

    Surface(
        onClick = onClick,
        interactionSource = interactionSource,
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) Color(0xFF2563EB) else Color(0xFFEFF6FF),
        border = BorderStroke(
            if (isSelected) 2.dp else 1.5.dp,
            if (isSelected) Color(0xFF2563EB) else Color(0xFFBFDBFE)
        ),
        shadowElevation = if (isSelected) 6.dp else if (isPressed) 0.dp else 1.5.dp,
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = numBadge,
                fontSize = 15.sp,
                fontWeight = FontWeight.Black,
                color = if (isSelected) Color.White else Color(0xFF1D4ED8)
            )
            Text(
                text = emoji,
                fontSize = 14.sp,
                modifier = Modifier.graphicsLayer {
                    scaleX = emojiScale
                    scaleY = emojiScale
                }
            )
        }
    }
}

@Composable
private fun AlphabetGridLetterCard(
    letterChar: String,
    item: EducationalItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val letterColor = Color(item.colorHex)
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.88f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "grid_letter_card_scale"
    )

    Surface(
        modifier = modifier
            .height(80.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clickable(
                onClick = onClick,
                interactionSource = interactionSource,
                indication = null
            )
            .testTag("alphabet_letter_card_${item.id}"),
        shape = RoundedCornerShape(14.dp),
        color = letterColor,
        border = BorderStroke(2.dp, Color.White),
        shadowElevation = if (isPressed) 1.dp else 4.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = letterChar,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    style = LocalTextStyle.current.copy(
                        shadow = androidx.compose.ui.graphics.Shadow(
                            color = Color.Black.copy(alpha = 0.3f),
                            offset = Offset(1.5f, 1.5f),
                            blurRadius = 3f
                        )
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = item.emoji,
                    fontSize = 20.sp
                )
            }
        }
    }
}

/**
 * 🔠 VOWELS PANORAMIC CANVAS (Ecrã 'Vogais Mágicas' - Official Image with Hotspots)
 */
@Composable
private fun PanoramicVowelsCanvas(
    topicData: EducationalTopicData,
    activeItem: EducationalItem?,
    onSelectItem: (EducationalItem) -> Unit,
    viewModel: MainViewModel
) {
    // Official Vowels Image URL
    val officialVowelsImageUrl = "https://files.catbox.moe/m2nqfm.jpg"

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Official Vowels Poster Container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.30f),
            contentAlignment = Alignment.Center
        ) {
            // Official Vowels Image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(officialVowelsImageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Vogais Oficial",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )

            // Hotspot Overlay (5 main horizontal areas for A, E, I, O, U)
            Row(modifier = Modifier.fillMaxSize()) {
                topicData.items.take(5).forEach { item ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable {
                                val vowelLetter = item.badge.ifBlank { item.id.replace("v_", "").uppercase() }
                                val speech = if (item.speechText.isNotBlank()) item.speechText else "Vogal $vowelLetter!"
                                viewModel.speak(speech)
                                onSelectItem(item)
                            }
                            .testTag("vowel_hotspot_${item.id}")
                    )
                }
            }
        }

        // Quick Interactive 5-Vowels Strip: Tap any vowel to open its magic window!
        Text(
            text = "🔤 Toca numa vogal para abrir a janela mágica:",
            fontSize = 12.5.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xFF1E293B),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp, vertical = 2.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            topicData.items.take(5).forEach { item ->
                val isSelected = activeItem?.id == item.id
                val vowelLetter = item.badge.ifBlank { item.id.replace("v_", "").uppercase() }
                VowelLetterChip(
                    letter = vowelLetter,
                    emoji = item.emoji,
                    isSelected = isSelected,
                    onClick = {
                        val speech = if (item.speechText.isNotBlank()) item.speechText else "Vogal $vowelLetter!"
                        viewModel.speak(speech)
                        onSelectItem(item)
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

/**
 * 🔤 Animated Interactive Vowel Letter Chip with bouncy Pop & Scale spring physics on tap
 */
@Composable
fun VowelLetterChip(
    letter: String,
    emoji: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = when {
            isPressed -> 0.88f
            isSelected -> 1.15f
            else -> 1.0f
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "vowel_chip_scale"
    )

    val emojiScale by animateFloatAsState(
        targetValue = if (isSelected) 1.30f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "vowel_chip_emoji_scale"
    )

    Surface(
        onClick = onClick,
        interactionSource = interactionSource,
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) Color(0xFF00897B) else Color(0xFFE0F2F1),
        border = BorderStroke(
            if (isSelected) 2.dp else 1.5.dp,
            if (isSelected) Color(0xFF00897B) else Color(0xFF80CBC4)
        ),
        shadowElevation = if (isSelected) 6.dp else if (isPressed) 0.dp else 1.5.dp,
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
    ) {
        Column(
            modifier = Modifier.padding(vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = letter,
                fontSize = 17.sp,
                fontWeight = FontWeight.Black,
                color = if (isSelected) Color.White else Color(0xFF004D40)
            )
            Text(
                text = emoji,
                fontSize = 13.sp,
                modifier = Modifier.graphicsLayer {
                    scaleX = emojiScale
                    scaleY = emojiScale
                }
            )
        }
    }
}

@Composable
private fun VowelCard(
    vowelLetter: String,
    item: EducationalItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cardColor = Color(item.colorHex)

    // Breathing idle animation for extra visual polish and kid-friendly engagement
    val infiniteTransition = rememberInfiniteTransition(label = "vowel_motion_${item.id}")
    val pulsePeriod = remember(item.id) { 1600 + (item.id.hashCode() % 350).let { if (it < 0) -it else it } }
    val scalePulse by infiniteTransition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = pulsePeriod, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Surface(
        modifier = modifier
            .height(145.dp) // Perfect height for Column layout containing Badge and details
            .graphicsLayer {
                scaleX = scalePulse
                scaleY = scalePulse
            }
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
            .testTag("vowel_card_${item.id}"),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        border = BorderStroke(2.5.dp, cardColor),
        shadowElevation = 3.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Circular Vowel Badge
            Surface(
                modifier = Modifier.size(46.dp),
                shape = CircleShape,
                color = cardColor
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = vowelLetter,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        style = LocalTextStyle.current.copy(
                            shadow = androidx.compose.ui.graphics.Shadow(
                                color = Color.Black.copy(alpha = 0.25f),
                                offset = Offset(1f, 1f),
                                blurRadius = 2f
                            )
                        )
                    )
                }
            }

            // Text and illustration
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = item.emoji,
                        fontSize = 18.sp
                    )
                    Text(
                        text = item.name,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF1E293B),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = item.subtitle,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF64748B),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

// =========================================================================================
// HELPER COMPONENTS
// =========================================================================================

/**
 * Detail Modal Dialog matching Atlas Country Modal
 */
@Composable
fun AtlasDetailDialog(
    item: EducationalItem,
    accentColor: Color,
    onDismiss: () -> Unit,
    onSpeak: () -> Unit
) {
    val isLetter = item.details.containsKey("Letra") || item.details.containsKey("Maiúscula") || item.id.length <= 3

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Fechar", color = Color.White, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            Button(
                onClick = onSpeak,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                    contentDescription = "Ouvir",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Ouvir Zé", color = Color.White, fontWeight = FontWeight.Bold)
            }
        },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (isLetter) {
                    Surface(
                        shape = CircleShape,
                        color = accentColor.copy(alpha = 0.15f),
                        border = BorderStroke(2.dp, accentColor),
                        modifier = Modifier.size(54.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = item.id.uppercase(),
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Black,
                                color = accentColor
                            )
                        }
                    }
                } else {
                    Text(item.emoji, fontSize = 38.sp)
                }

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = if (isLetter) "Letra ${item.id.uppercase()}" else item.name,
                            fontWeight = FontWeight.Black,
                            fontSize = 20.sp,
                            color = Color(0xFF0F172A)
                        )
                        if (isLetter) {
                            Text(item.emoji, fontSize = 20.sp)
                        }
                    }
                    Text(
                        text = if (isLetter) "${item.name} (${item.badge.ifBlank { item.category }})" else item.badge.ifBlank { item.category },
                        fontSize = 13.sp,
                        color = accentColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Syllable Division Banner (if available)
                if (item.syllables.isNotBlank()) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFFEEF2FF),
                        border = BorderStroke(1.5.dp, Color(0xFF818CF8)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Divisão Silábica 📖",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4338CA)
                            )
                            Text(
                                text = item.syllables,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF1E1B4B),
                                letterSpacing = 1.sp
                            )
                        }
                    }
                }

                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        item.details.forEach { (k, v) ->
                            Row {
                                Text("$k: ", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.Gray)
                                Text(v, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color.Black)
                            }
                        }
                    }
                }

                if (item.fact.isNotBlank()) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Color(0xFFFFD54F))
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text("💡", fontSize = 18.sp)
                            Column {
                                Text("Sabias que?", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFFE65100))
                                Text(item.fact, fontSize = 12.sp, lineHeight = 16.sp, color = Color(0xFF3E2723))
                            }
                        }
                    }
                }
            }
        },
        shape = RoundedCornerShape(24.dp),
        containerColor = Color.White
    )
}

/**
 * Item Card optimized for 3x2 Grid with horizontal text orientation
 */
@Composable
fun AtlasItemCard(
    item: EducationalItem,
    onClick: () -> Unit
) {
    val sizing = LocalAdaptiveSizing.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.93f else 1f,
        label = "atlas_card_scale"
    )
    val accentColor = Color(item.colorHex)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isPressed) 1.dp else 4.dp),
        border = BorderStroke(1.5.dp, accentColor.copy(alpha = 0.35f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = sizing.scalePadding(8.dp),
                    vertical = sizing.scalePadding(10.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(sizing.scalePadding(4.dp))
        ) {
            // Emoji badge container
            Box(
                modifier = Modifier
                    .size(sizing.scaleSize(42.dp))
                    .background(accentColor.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.emoji,
                    fontSize = sizing.scaleFont(22.sp)
                )
            }

            // Title - horizontal readable text
            Text(
                text = item.name,
                fontWeight = FontWeight.Black,
                fontSize = sizing.scaleFont(13.sp),
                color = Color(0xFF0F172A),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )

            // Subtitle or Badge horizontal
            val subText = if (item.subtitle.isNotBlank()) item.subtitle else (if (item.badge.isNotBlank()) item.badge else item.category)
            if (subText.isNotBlank()) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = accentColor.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = subText,
                        fontSize = sizing.scaleFont(10.sp),
                        fontWeight = FontWeight.Bold,
                        color = accentColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(
                            horizontal = sizing.scalePadding(6.dp),
                            vertical = sizing.scalePadding(2.dp)
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun DictionaryWordCard(
    item: EducationalItem,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        label = "dic_card_scale"
    )
    val accentColor = Color(item.colorHex)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 140.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isPressed) 1.dp else 4.dp),
        border = BorderStroke(1.5.dp, accentColor.copy(alpha = 0.4f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(accentColor.copy(alpha = 0.15f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = item.emoji, fontSize = 20.sp)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.name,
                        fontWeight = FontWeight.Black,
                        fontSize = 15.sp,
                        color = Color(0xFF0F172A),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (item.subtitle.isNotBlank()) {
                        Text(
                            text = item.subtitle,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = accentColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Text(
                text = item.fact,
                fontSize = 12.sp,
                color = Color(0xFF475569),
                lineHeight = 16.sp
            )
        }
    }
}

/**
 * 🎨 UNIVERSAL INTERACTIVE TOPIC BOARD / POSTER CARD
 * Apresenta o pôster ilustrado completo em grelha com imagens/emojis coloridos e texto em destaque.
 */
@Composable
fun InteractiveTopicBoardCard(
    topicData: EducationalTopicData,
    accentColor: Color,
    activeItemId: String? = null,
    onItemClick: (EducationalItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = topicData.items
    val isAlphabet = topicData.id.lowercase() in listOf("alfabeto", "vogais")
    val isNumbers = topicData.id.lowercase() == "numeros"

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isAlphabet || isNumbers) Color.Transparent else Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isAlphabet || isNumbers) 0.dp else 5.dp
        ),
        border = if (isAlphabet || isNumbers) null else BorderStroke(1.5.dp, accentColor.copy(alpha = 0.25f))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header inside poster card
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = accentColor.copy(alpha = 0.12f),
                        modifier = Modifier.size(38.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(topicData.iconEmoji, fontSize = 20.sp)
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = when {
                                topicData.id.lowercase() == "alfabeto" -> "Quadro Interativo A-Z"
                                topicData.id.lowercase() == "vogais" -> "Quadro das Vogais"
                                topicData.id.lowercase() == "numeros" -> "Quadro dos Números 1 a 10"
                                else -> "Pôster Ilustrado de ${topicData.title}"
                            },
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF0F172A),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Toca para ouvir a voz do Zé e ganhar estrelas!",
                            fontSize = 11.sp,
                            color = Color(0xFF64748B),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = accentColor.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = "${items.size} ${if (isAlphabet) "Letras" else if (isNumbers) "Números" else "Elementos"}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = accentColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Grid items
            val columns = when {
                topicData.id.lowercase() == "alfabeto" -> 6
                topicData.id.lowercase() == "vogais" -> 5
                topicData.id.lowercase() == "numeros" -> 5
                items.size <= 8 -> 4
                else -> 4
            }

            val chunked = items.chunked(columns)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                chunked.forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowItems.forEach { item ->
                            val isSelected = activeItemId == item.id
                            InteractivePosterItem(
                                item = item,
                                isSelected = isSelected,
                                isAlphabet = isAlphabet,
                                isNumbers = isNumbers,
                                accentColor = accentColor,
                                onClick = { onItemClick(item) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                        repeat(columns - rowItems.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InteractivePosterItem(
    item: EducationalItem,
    isSelected: Boolean,
    isAlphabet: Boolean,
    isNumbers: Boolean,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val itemColor = Color(item.colorHex)
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Bouncy Spring Pop & Scale Animation on tap and selection
    val scale by animateFloatAsState(
        targetValue = when {
            isPressed -> 0.88f
            isSelected -> 1.12f
            else -> 1.0f
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "poster_item_scale"
    )

    val emojiScale by animateFloatAsState(
        targetValue = if (isSelected) 1.25f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "poster_item_emoji_scale"
    )

    Surface(
        modifier = modifier
            .aspectRatio(if (isAlphabet || isNumbers) 1f else 0.88f)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .testTag("poster_item_${item.id}"),
        shape = RoundedCornerShape(14.dp),
        color = if (isSelected) accentColor.copy(alpha = 0.18f) else itemColor.copy(alpha = 0.10f),
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) accentColor else itemColor.copy(alpha = 0.35f)
        ),
        shadowElevation = if (isSelected) 6.dp else if (isPressed) 0.dp else 1.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (isAlphabet) {
                val displayLetter = item.id.replace("letra_", "").replace("v_", "").uppercase()
                Text(
                    text = displayLetter,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Black,
                    color = itemColor
                )
                Text(
                    text = item.emoji,
                    fontSize = 12.sp,
                    modifier = Modifier.graphicsLayer {
                        scaleX = emojiScale
                        scaleY = emojiScale
                    }
                )
                Text(
                    text = item.name,
                    fontSize = 8.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            } else if (isNumbers) {
                val displayNum = item.id.replace("num_", "")
                Text(
                    text = displayNum,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Black,
                    color = itemColor
                )
                Text(
                    text = item.emoji,
                    fontSize = 12.sp,
                    modifier = Modifier.graphicsLayer {
                        scaleX = emojiScale
                        scaleY = emojiScale
                    }
                )
                Text(
                    text = item.name,
                    fontSize = 8.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            } else {
                Text(
                    text = item.emoji,
                    fontSize = 20.sp,
                    modifier = Modifier.graphicsLayer {
                        scaleX = emojiScale
                        scaleY = emojiScale
                    }
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = item.name,
                    fontSize = 9.5.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF0F172A),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
                if (item.badge.isNotBlank()) {
                    Text(
                        text = item.badge,
                        fontSize = 7.5.sp,
                        color = itemColor,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

/**
 * Interactive A-Z Soundboard Card (Backward compatibility wrapper)
 */
@Composable
fun InteractiveAlphabetBoardCard(
    items: List<EducationalItem>,
    accentColor: Color,
    onLetterClick: (EducationalItem) -> Unit
) {
    val topic = LearningTopicRepository.getTopicById("alfabeto")
        ?: EducationalTopicData(
            id = "alfabeto",
            title = "Alfabeto",
            subtitle = "Aprende as letras de A a Z",
            iconEmoji = "🔤",
            accentColor = accentColor,
            headerGradient = listOf(accentColor, accentColor.copy(alpha = 0.8f)),
            items = items,
            categories = emptyList(),
            quizQuestions = emptyList()
        )
    InteractiveTopicBoardCard(
        topicData = topic,
        accentColor = accentColor,
        onItemClick = onLetterClick
    )
}

@Composable
private fun PanoramicDiasSemanaCanvas(
    topicData: EducationalTopicData,
    activeItem: EducationalItem?,
    onSelectItem: (EducationalItem) -> Unit,
    viewModel: MainViewModel
) {
    val officialDiasImageUrl = "https://files.catbox.moe/2dpgp6.jpg"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.30f),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(officialDiasImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Dias da Semana Oficial",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )

        Column(modifier = Modifier.fillMaxSize()) {
            topicData.items.forEach { item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable {
                            viewModel.speak("${item.name}!")
                            onSelectItem(item)
                        }
                        .testTag("dias_hotspot_${item.id}")
                )
            }
        }
    }
}

@Composable
private fun PanoramicMesesAnoCanvas(
    topicData: EducationalTopicData,
    activeItem: EducationalItem?,
    onSelectItem: (EducationalItem) -> Unit,
    viewModel: MainViewModel
) {
    val officialMesesImageUrl = "https://files.catbox.moe/jjnzkb.jpg"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.30f),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(officialMesesImageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Meses do Ano Oficial",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )

        Column(modifier = Modifier.fillMaxSize()) {
            topicData.items.chunked(3).forEach { rowItems ->
                Row(modifier = Modifier.weight(1f)) {
                    rowItems.forEach { item ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable {
                                    viewModel.speak("${item.name}!")
                                    onSelectItem(item)
                                }
                                .testTag("meses_hotspot_${item.id}")
                        )
                    }
                    repeat(3 - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
