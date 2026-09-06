package com.example.ui.screens

import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ui.components.FundoApp
import com.example.ui.components.EducationalItemCard
import com.example.ui.components.ScreenHeader
import com.example.ui.theme.SunshineYellow
import com.example.viewmodel.MainViewModel

data class LearnCategoryMeta(
    val id: Int,
    val title: String,
    val emoji: String,
    val countText: String,
    val description: String,
    val badge: String,
    val bgColor: Long,
    val accentColor: Long,
    val topicKey: String,
    val previewChips: List<String>? = null,
    val imageUrl: String? = null
)

@Composable
fun LearnScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
    showHeader: Boolean = true,
    htmlUrl: String? = null,
    selectedYear: Int? = null,
    onTopicStateChanged: ((Boolean) -> Unit)? = null
) {
    // If HTML url is provided directly
    if (!htmlUrl.isNullOrBlank()) {
        Box(modifier = modifier.fillMaxSize()) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    WebView(context).apply { com.example.util.EmulatorUtils.optimizeWebViewForEmulator(this); 
                        settings.apply {
                            javaScriptEnabled = true
                            domStorageEnabled = true
                            allowFileAccess = true
                            allowContentAccess = true
                            mediaPlaybackRequiresUserGesture = false
                            useWideViewPort = true
                            loadWithOverviewMode = true
                            builtInZoomControls = false
                            displayZoomControls = false
                        }
                        webChromeClient = WebChromeClient()
                        webViewClient = object : WebViewClient() {
                            override fun onReceivedError(
                                view: WebView?,
                                request: WebResourceRequest?,
                                error: WebResourceError?
                            ) {
                                super.onReceivedError(view, request, error)
                            }

                            override fun onRenderProcessGone(
                                view: WebView?,
                                detail: android.webkit.RenderProcessGoneDetail?
                            ): Boolean {
                                try {
                                    (view?.parent as? android.view.ViewGroup)?.removeView(view)
                                    view?.destroy()
                                } catch (_: Exception) {}
                                return true
                            }
                        }
                        loadUrl(htmlUrl)
                    }
                }
            )
        }
        return
    }

    var selectedCategoryIndex by remember { mutableStateOf<Int?>(null) }

        LaunchedEffect(selectedCategoryIndex) {
            onTopicStateChanged?.invoke(selectedCategoryIndex != null)
        }

        // Category Metadata matching all pedagogical topics with identical rich structure
        val categoryMetas = remember {
            listOf(
                LearnCategoryMeta(0, "Abecedário", "🔤", "26 Letras", "Letras de A a Z, divisão silábica e palavras ilustradas", "POPULAR 🔥", 0xFFFFF8E1, 0xFFFFB300, "alfabeto", listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"), "https://files.catbox.moe/udfo3a.png"),
                LearnCategoryMeta(1, "Vogais", "🔠", "5 Vogais", "A, E, I, O, U com fonética e pronúncia em Português", "ESSENCIAL ⭐", 0xFFE0F7FA, 0xFF00ACC1, "vogais", listOf("A", "E", "I", "O", "U")),
                LearnCategoryMeta(2, "Números", "🔢", "10 Números", "Contar de 1 a 10 com dedinhos, escrita e quantidades", "FÁCIL 🎈", 0xFFFFF3E0, 0xFFFB8C00, "numeros", listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")),
                LearnCategoryMeta(18, "Sistema Solar", "🪐", "9 Corpos", "Sol, Planetas, Lua, Astronautas e o Universo", "CIÊNCIA 🚀", 0xFFEDE7F6, 0xFF673AB7, "sistema_solar", listOf("☀️ Sol", "🌍 Terra", "🌐 Urano", "🪐 Saturno", "🚀 Foguete")),
                LearnCategoryMeta(19, "Dinossauros", "🦖", "6 Espécies", "T-Rex, Triceratops, Fósseis e a Pré-História", "FASCINANTE 🦕", 0xFFE8F5E9, 0xFF2E7D32, "dinossauros", listOf("🦖 T-Rex", "🦕 Diplodoco", "🐊 Triceratops", "🥚 Fóssil", "🌋 Vulcão")),
                LearnCategoryMeta(21, "Portugal", "🏰", "15 Símbolos", "Castelos, Torre de Belém, Galo de Barcelos e Tradições", "TRADIÇÃO 🇵🇹", 0xFFFFEBEE, 0xFFC62828, "portugal", listOf("🏰 Belém", "🇵🇹 Bandeira", "🐓 Galo", "⛵ Caravela", "🧀 Queijo")),
                LearnCategoryMeta(22, "Saúde e Higiene", "💧", "6 Hábitos", "Lavar os dentes, mãos, alimentação saudável e desporto", "SAUDÁVEL 🍏", 0xFFE0F2F1, 0xFF00695C, "saude_higiene", listOf("🦷 Dentes", "🪥 Escova", "🍎 Maçã", "🧼 Sabão", "🏃 Desporto")),
                LearnCategoryMeta(23, "Matemática", "➕", "5 Desafios", "Somas fáceis, subtrações, dobro e números pares", "DESAFIO 🧠", 0xFFFFF8E1, 0xFFE65100, "matematica", listOf("1+1", "2+2", "5-2", "2x2", "10-5")),
                LearnCategoryMeta(24, "Mapa do Mundo", "🌍", "51 Países", "Atlas do Zé com mapa interativo, bandeiras e capitais", "ATLAS 🗺️", 0xFFE3F2FD, 0xFF1976D2, "mapa_mundo", listOf("🇵🇹 Portugal", "🇪🇸 Espanha", "🇫🇷 França", "🇧🇷 Brasil", "🇦🇴 Angola", "🇯🇵 Japão")),
                LearnCategoryMeta(5, "Animais", "🦁", "21 Animais", "Sons reais, habitats, características e curiosidades", "PREFERIDO 🐾", 0xFFE8F5E9, 0xFF4CAF50, "animais", listOf("🦁 Leão", "🐘 Elefante", "🦒 Girafa", "🐬 Golfinho", "🦅 Águia", "🐶 Cão")),
                LearnCategoryMeta(6, "Frutas", "🍎", "18 Frutas", "Frutas deliciosas, sabores, vitaminas e cores", "VITAMINAS 🍓", 0xFFFBE9E7, 0xFFFF5722, "frutas", listOf("🍎 Maçã", "🍌 Banana", "🍓 Morango", "🍊 Laranja", "🍇 Uva", "🍉 Melancia")),
                LearnCategoryMeta(7, "Corpo Humano", "👀", "16 Partes", "Sentidos, órgãos, ossos e higiene pessoal", "DESCOBERTA 💡", 0xFFE1F5FE, 0xFF0288D1, "corpo_humano", listOf("❤️ Coração", "🧠 Cérebro", "👀 Olhos", "🦴 Ossos", "🦷 Dentes", "👂 Ouvidos")),
                LearnCategoryMeta(8, "Profissões", "👨‍⚕️", "8 Heróis", "Bombeiros, médicos, professores e ofícios", "FUTURO 🚀", 0xFFF3E5F5, 0xFF8E24AA, "profissoes", listOf("👨‍🚒 Bombeiro", "👩‍⚕️ Médica", "👨‍🏫 Professor", "👮 Polícia", "🧑‍🍳 Cozinheiro")),
                LearnCategoryMeta(9, "Emoções", "😊", "8 Emoções", "Alegria, calma, coragem e inteligência emocional", "AFETO ❤️", 0xFFFFF8E1, 0xFFFF8F00, "emocoes", listOf("😊 Alegria", "😌 Calma", "🦁 Coragem", "❤️ Amor", "😮 Surpresa")),
                LearnCategoryMeta(10, "Dias da Semana", "📅", "7 Dias", "Segunda a Domingo e organização da rotina diária", "ROTINA 🗓️", 0xFFEFEBE9, 0xFF6D4C41, "dias_semana", listOf("Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom")),
                LearnCategoryMeta(11, "Meses do Ano", "🗓️", "12 Meses", "As 4 estações, aniversários e o calendário anual", "CALENDÁRIO ⏳", 0xFFFFEBEE, 0xFFD81B60, "meses_ano", listOf("Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez")),
                LearnCategoryMeta(12, "Transportes", "🚗", "8 Veículos", "Carros, aviões, navios, terra, mar e ar", "VIAGEM ✈️", 0xFFFFF59D, 0xFFFBC02D, "transportes", listOf("🚗 Carro", "✈️ Avião", "🚢 Navio", "🚀 Foguete", "🚂 Comboio")),
                LearnCategoryMeta(13, "Instrumentos", "🎸", "8 Sons", "Guitarra, piano, tambor e ritmo musical", "MÚSICA 🎼", 0xFFECEFF1, 0xFF546E7A, "instrumentos", listOf("🎸 Guitarra", "🎹 Piano", "🥁 Tambor", "🎷 Saxofone", "🎻 Violino")),
                LearnCategoryMeta(25, "Cores e Formas", "🎨", "8 Formas", "Cores primárias, círculo, quadrado, triângulo e figuras", "CRIATIVO 🎨", 0xFFFFF8E1, 0xFFFFB300, "cores_formas", listOf("🔴 Vermelho", "🔵 Azul", "🟡 Amarelo", "🟢 Verde", "🔺 Triângulo", "⬛ Quadrado")),
                LearnCategoryMeta(27, "Natureza", "🌱", "4 Elementos", "Árvores, rios, montanhas e cuidados com o planeta", "ECOLÓGICO 🌳", 0xFFE8F5E9, 0xFF43A047, "natureza", listOf("🌲 Árvore", "🌊 Rio", "🌻 Girassol", "🌿 Folha", "🌈 Arco-Íris")),
                LearnCategoryMeta(16, "Dicionário", "📖", "A a Z", "Dicionário visual com mais de 50 termos ilustrados", "NOVO ✨", 0xFFE8F5E9, 0xFF43A047, "dicionario", listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"))
            )
        }

        val filteredCategoryMetas = remember(selectedYear, categoryMetas) {
            when (selectedYear) {
                1 -> categoryMetas.filter { it.id in listOf(0, 1, 2, 10, 11, 25) }
                2 -> categoryMetas.filter { it.id in listOf(7, 5, 6, 9, 13, 27) }
                3 -> categoryMetas.filter { it.id in listOf(23, 18, 19, 12, 8) }
                4 -> categoryMetas.filter { it.id in listOf(24, 21, 22, 16) }
                else -> categoryMetas
            }
        }

        // BackHandler for physical device back button
        BackHandler(enabled = selectedCategoryIndex != null) {
            selectedCategoryIndex = null
        }

        AnimatedContent(
            targetState = selectedCategoryIndex,
            transitionSpec = {
                if (targetState != null) {
                    (slideInHorizontally { width -> width } + fadeIn(animationSpec = tween(400))) togetherWith
                            (slideOutHorizontally { width -> -width } + fadeOut(animationSpec = tween(400)))
                } else {
                    (slideInHorizontally { width -> -width } + fadeIn(animationSpec = tween(400))) togetherWith
                            (slideOutHorizontally { width -> width } + fadeOut(animationSpec = tween(400)))
                }
            },
            label = "learn_screen_navigation_transition",
            modifier = modifier.fillMaxSize()
        ) { targetIndex ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .testTag("learn_screen")
            ) {
                if (targetIndex == null) {
                    // ==================== 1. MAIN CATEGORIES HUB ====================
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 12.dp, end = 12.dp, top = 6.dp, bottom = 6.dp)
                    ) {
                        if (showHeader) {
                            ScreenHeader(
                                title = "Escola Mágica",
                                subtitle = "Aprende e Descobre com o Zé Traquina",
                                icon = "📚",
                                gradientColors = listOf(Color(0xFF4F46E5), Color(0xFF9333EA))
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        // Grid of Category Cards
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(bottom = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        ) {
                            itemsIndexed(filteredCategoryMetas) { _, cat ->
                                EducationalItemCard(
                                    title = cat.title,
                                    subtitle = cat.description,
                                    emoji = cat.emoji,
                                    colorHex = cat.accentColor,
                                    badge = cat.badge,
                                    countText = cat.countText,
                                    previewChips = cat.previewChips,
                                    imageUrl = cat.imageUrl,
                                    onClick = {
                                        selectedCategoryIndex = cat.id
                                        viewModel.speak("A abrir ${cat.title}")
                                    },
                                    testTag = "category_card_${cat.id}",
                                    subtitleMaxLines = 2,
                                    subtitleMinHeight = 30
                                )
                            }
                        }
                    }
                } else if (targetIndex == 24) {
                    // ==================== 1. ATLAS DO ZÉ TRAQUINA (100% PRESERVADO E INTACTO) ====================
                    WorldMapScreen(viewModel = viewModel, onBack = { selectedCategoryIndex = null })
                } else if (targetIndex == 5) {
                    // ==================== 2. MÓDULO ANIMAIS ====================
                    AnimaisScreen(viewModel = viewModel, onBack = { selectedCategoryIndex = null })
                } else if (targetIndex == 18) {
                    // ==================== 3. MÓDULO SISTEMA SOLAR ====================
                    SistemaSolarScreen(viewModel = viewModel, onBack = { selectedCategoryIndex = null })
                } else if (targetIndex == 7) {
                    // ==================== 4. MÓDULO CORPO HUMANO ====================
                    CorpoHumanoScreen(viewModel = viewModel, onBack = { selectedCategoryIndex = null })
                } else if (targetIndex == 23) {
                    // ==================== 5. MÓDULO MATEMÁTICA ====================
                    MatematicaScreen(viewModel = viewModel, onBack = { selectedCategoryIndex = null })
                } else if (targetIndex == 21) {
                    // ==================== 6. MÓDULO PORTUGAL ====================
                    PortugalScreen(viewModel = viewModel, onBack = { selectedCategoryIndex = null })
                } else {
                    // ==================== 7. MÓDULOS COM O TEMPLATE EDUCATIVO (ABECEDÁRIO, VOGAIS, NÚMEROS, ETC.) ====================
                    val selectedMeta = categoryMetas.firstOrNull { it.id == targetIndex }
                    val topicKey = selectedMeta?.topicKey ?: "animais"

                    val topicData = LearningTopicRepository.getTopicById(topicKey)
                    if (topicData != null) {
                        EducationalTopicScreen(
                            topicData = topicData,
                            viewModel = viewModel,
                            onBack = { selectedCategoryIndex = null }
                        )
                    } else {
                        LearningTopicRepository.getTopicById("animais")?.let { fallback ->
                            EducationalTopicScreen(
                                topicData = fallback,
                                viewModel = viewModel,
                                onBack = { selectedCategoryIndex = null }
                            )
                        }
                    }
                }
            }
        }
}
