package com.example.ui.screens

import androidx.compose.ui.draw.clip
import androidx.annotation.DrawableRes
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import com.example.ui.components.AudioPlayerBarComposable
import com.example.ui.components.HomeVideoPlayerCard
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.ui.graphics.Shadow
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.ui.draw.scale
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas

import androidx.compose.ui.res.painterResource
import androidx.compose.material3.MaterialTheme
import com.example.ui.components.MascotEmotion
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import com.example.ui.components.FundoApp

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.compose.foundation.Canvas
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import com.example.ui.components.SafeAsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.util.AppImageLoader
import com.example.util.PreviewConfig
import com.example.ui.components.ZeTraquinaMascot
import com.example.ui.components.MascotFaceCircle
import android.util.Log
import com.example.ui.navigation.Screen
import com.example.ui.theme.KidStarOrange
import com.example.ui.theme.MintGreen
import com.example.ui.theme.SkyBluePrimary
import com.example.ui.theme.SunshineYellow
import com.example.viewmodel.MainViewModel
import com.example.data.UserProgress
import com.example.ui.components.UniversoBottomNavBar
import com.example.ui.navigation.mainNavScreens
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay



import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.filled.Refresh
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items

import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.MenuBook

data class ZeBalloonTip(
    val category: String,
    val badgeLabel: String,
    val icon: ImageVector,
    val badgeBgColor: Color,
    val badgeTextColor: Color,
    val text: String,
    val answer: String? = null,
    val emotion: MascotEmotion = MascotEmotion.HAPPY
)

data class DailyHighlightData(
    val category: String,
    val symbol: String,
    val emoji: String,
    val title: String,
    val badgeColor: Color,
    val containerColor: Color,
    val testTag: String,
    val targetScreen: Screen,
    val imageRes: Int = R.drawable.ic_3d_educar,
    val primaryColor: Color = Color(0xFF0284C7),
    val darkerColor: Color = Color(0xFF0369A1),
    val deepShadowColor: Color = Color(0xFF0C4A6E)
)

@androidx.media3.common.util.UnstableApi
@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    val userProgress by viewModel.userProgress.collectAsState()
    HomeScreenContent(
        userProgress = userProgress,
        onNavigate = onNavigate,
        onSpeak = { textToSpeak ->
            val cleanText = textToSpeak.replace(Regex("[^A-Za-zÀ-ÖØ-öø-ÿ0-9,?!. ]"), "")
            viewModel.speak(cleanText)
        },
        modifier = modifier
    )
}

@androidx.media3.common.util.UnstableApi
@Composable
fun HomeScreenContent(
    userProgress: UserProgress = UserProgress(),
    onNavigate: (Screen) -> Unit = {},
    onSpeak: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var isAudioPlaying by remember { mutableStateOf(false) }

    // Dynamic calculation of day of the week for daily rotating highlights
    val dayOfWeek = remember {
        val calendar = java.util.Calendar.getInstance()
        calendar.get(java.util.Calendar.DAY_OF_WEEK) - 1 // 0 = Sunday, 1 = Monday, ..., 6 = Saturday
    }

    val dayNames = remember {
        listOf("Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sábado")
    }
    val todayName = dayNames[dayOfWeek % dayNames.size]

    val dailyHighlightPairs = remember {
        listOf(
            // 0: Domingo (Aprender & Jogos)
            DailyHighlightData("Aprender", "📚", "✨", "Escola Mágica & Saberes", Color(0xFF4F46E5), Color(0xFFEEF2FF), "home_highlight_learn", Screen.Learn, R.drawable.img_3d_icon_educar_1787589385019, Color(0xFF4F46E5), Color(0xFF4338CA), Color(0xFF3730A3)) to
            DailyHighlightData("Jogos", "🎮", "🕹️", "Parquinho de Jogos", Color(0xFF16A34A), Color(0xFFF0FDF4), "home_highlight_games", Screen.Games, R.drawable.img_3d_icon_historias_1787589400744, Color(0xFF16A34A), Color(0xFF15803D), Color(0xFF166534)),

            // 1: Segunda-feira (Fichas & Vídeos)
            DailyHighlightData("Fichas de Trabalho", "📝", "✏️", "Educar - Fichas", Color(0xFFE11D48), Color(0xFFFFF1F2), "home_highlight_fichas", Screen.Educar, R.drawable.img_3d_icon_educar_1787589385019, Color(0xFFE11D48), Color(0xFFBE123C), Color(0xFF9F1239)) to
            DailyHighlightData("Vídeos do Zé", "▶️", "📺", "Assistir ao Zé Traquina", Color(0xFFEA580C), Color(0xFFFFEDD5), "home_highlight_videos", Screen.Media, R.drawable.img_3d_icon_videos_1787589414811, Color(0xFFEA580C), Color(0xFFC2410C), Color(0xFF9A3412)),

            // 2: Terça-feira (Jogos & Aprender)
            DailyHighlightData("Jogos do Zé", "🧩", "🧠", "Desafios & Memória", Color(0xFF16A34A), Color(0xFFF0FDF4), "home_highlight_games", Screen.Games, R.drawable.img_3d_icon_historias_1787589400744, Color(0xFF16A34A), Color(0xFF15803D), Color(0xFF166534)) to
            DailyHighlightData("Mundo Mágico", "🪐", "🚀", "Sistema Solar & Animais", Color(0xFF4F46E5), Color(0xFFEEF2FF), "home_highlight_learn", Screen.Learn, R.drawable.img_3d_icon_educar_1787589385019, Color(0xFF4F46E5), Color(0xFF4338CA), Color(0xFF3730A3)),

            // 3: Quarta-feira (Vídeos & Fichas)
            DailyHighlightData("Vídeos & Canal", "🎬", "🍿", "Canal do Zé Traquina", Color(0xFFEA580C), Color(0xFFFFEDD5), "home_highlight_videos", Screen.Media, R.drawable.img_3d_icon_videos_1787589414811, Color(0xFFEA580C), Color(0xFFC2410C), Color(0xFF9A3412)) to
            DailyHighlightData("Fichas de Estudo", "📝", "📊", "Exercícios de Português & Mat", Color(0xFFE11D48), Color(0xFFFFF1F2), "home_highlight_fichas", Screen.Educar, R.drawable.img_3d_icon_educar_1787589385019, Color(0xFFE11D48), Color(0xFFBE123C), Color(0xFF9F1239)),

            // 4: Quinta-feira (Aprender & Vídeos)
            DailyHighlightData("Descobrir & Aprender", "💡", "🔬", "Alfabeto, Números & Frutas", Color(0xFF4F46E5), Color(0xFFEEF2FF), "home_highlight_learn", Screen.Learn, R.drawable.img_3d_icon_educar_1787589385019, Color(0xFF4F46E5), Color(0xFF4338CA), Color(0xFF3730A3)) to
            DailyHighlightData("Vídeos Mágicos", "▶️", "🌟", "Clipes & Desenhos do Zé", Color(0xFFEA580C), Color(0xFFFFEDD5), "home_highlight_videos", Screen.Media, R.drawable.img_3d_icon_videos_1787589414811, Color(0xFFEA580C), Color(0xFFC2410C), Color(0xFF9A3412)),

            // 5: Sexta-feira (Fichas & Jogos)
            DailyHighlightData("Fichas Práticas", "📝", "🎨", "Atividades & Pinturas", Color(0xFFE11D48), Color(0xFFFFF1F2), "home_highlight_fichas", Screen.Educar, R.drawable.img_3d_icon_educar_1787589385019, Color(0xFFE11D48), Color(0xFFBE123C), Color(0xFF9F1239)) to
            DailyHighlightData("Jogos de Tabuleiro", "🎲", "🎯", "Xadrez, Sudoku & Galo", Color(0xFF16A34A), Color(0xFFF0FDF4), "home_highlight_games", Screen.Games, R.drawable.img_3d_icon_historias_1787589400744, Color(0xFF16A34A), Color(0xFF15803D), Color(0xFF166534)),

            // 6: Sábado (Jogos & Vídeos)
            DailyHighlightData("Jogos do Fim de Semana", "🎮", "🏆", "Diversão em Família", Color(0xFF16A34A), Color(0xFFF0FDF4), "home_highlight_games", Screen.Games, R.drawable.img_3d_icon_historias_1787589400744, Color(0xFF16A34A), Color(0xFF15803D), Color(0xFF166534)) to
            DailyHighlightData("Vídeos em Família", "▶️", "🍿", "Cinema do Zé Traquina", Color(0xFFEA580C), Color(0xFFFFEDD5), "home_highlight_videos", Screen.Media, R.drawable.img_3d_icon_videos_1787589414811, Color(0xFFEA580C), Color(0xFFC2410C), Color(0xFF9A3412))
        )
    }

    val (highlight1, highlight2) = remember(dayOfWeek) {
        dailyHighlightPairs[dayOfWeek % dailyHighlightPairs.size]
    }

    // Rich categorized full-time Zé Traquina balloon tips
    val allZeTips = remember {
        listOf(
            // --- MENSAGEM PRINCIPAL DO ZÉ ---
            ZeBalloonTip(
                category = "Mensagem",
                badgeLabel = "MENSAGEM DO ZÉ 🌟",
                icon = Icons.Default.Star,
                badgeBgColor = Color(0xFFE0F2FE),
                badgeTextColor = Color(0xFF0288D1),
                text = "Olá amiguinhos!\nO que vamos aprender hoje? 😊✨",
                emotion = MascotEmotion.HAPPY
            ),
            // --- RUBRICA 1: DICAS DO ZÉ ---
            ZeBalloonTip(
                category = "Dicas",
                badgeLabel = "DICA DO ZÉ 💡",
                icon = Icons.Default.Lightbulb,
                badgeBgColor = Color(0xFFFFECE0),
                badgeTextColor = Color(0xFFE65100),
                text = "Lava bem as mãos com água e sabão antes de comer para afastar os micróbios! 🧼",
                emotion = MascotEmotion.HAPPY
            ),
            // --- RUBRICA 2: TRAVA-LÍNGUAS ---
            ZeBalloonTip(
                category = "Trava-Línguas",
                badgeLabel = "TRAVA-LÍNGUAS 👅",
                icon = Icons.Default.RecordVoiceOver,
                badgeBgColor = Color(0xFFFCE4EC),
                badgeTextColor = Color(0xFFC2185B),
                text = "Tenta dizer sem te trancar: O tempo perguntou ao tempo quanto tempo o tempo tem! ⏱️🤪",
                answer = "O tempo respondeu ao tempo que o tempo tem tanto tempo quanto o tempo tem! ⌛",
                emotion = MascotEmotion.EXCITED
            ),
            // --- RUBRICA 3: SABIAS QUE? ---
            ZeBalloonTip(
                category = "Sabias que?",
                badgeLabel = "SABIAS QUE? ❓",
                icon = Icons.Default.AutoAwesome,
                badgeBgColor = Color(0xFFE0F2FE),
                badgeTextColor = Color(0xFF0288D1),
                text = "Sabias que as borboletas provam a comida usando as suas patinhas? 🦋🦶",
                emotion = MascotEmotion.EXCITED
            ),
            // --- RUBRICA 4: CANTIGA DO DIA ---
            ZeBalloonTip(
                category = "Cantigas",
                badgeLabel = "CANTIGA DO DIA 🎶",
                icon = Icons.Default.MusicNote,
                badgeBgColor = Color(0xFFF3E8FF),
                badgeTextColor = Color(0xFF7B1FA2),
                text = "Cantiga do Zé: 'Atirei o pau ao gato-to, mas o gato-to não morreu-reu'! Canta comigo! 🐱🎶",
                answer = "Dona Chica-ca admirou-se-se do berro que o gato deu: Miau! 🐾",
                emotion = MascotEmotion.CELEBRATING
            ),
            // --- RUBRICA 5: ADIVINHAS DO ZÉ ---
            ZeBalloonTip(
                category = "Adivinhas",
                badgeLabel = "ADIVINHA DO ZÉ 🧩",
                icon = Icons.Default.SmartToy,
                badgeBgColor = Color(0xFFFFF3E0),
                badgeTextColor = Color(0xFFE65100),
                text = "Tem capas mas não é herói, tem folhas mas não é árvore. O que é?",
                answer = "O Livro! 📖✨",
                emotion = MascotEmotion.THINKING
            ),
            // --- RUBRICA 6: MISSÃO DE BONDADE ---
            ZeBalloonTip(
                category = "Missão",
                badgeLabel = "MISSÃO DE BONDADE 💕",
                icon = Icons.Default.VolunteerActivism,
                badgeBgColor = Color(0xFFFFEBEE),
                badgeTextColor = Color(0xFFD32F2F),
                text = "Missão de Hoje: Dá um sorriso radiante e dize 'Bom dia' a alguém da tua família! ☀️😊",
                answer = "Espalhar alegria e amor é a melhor missão de todas! ⭐",
                emotion = MascotEmotion.PROUD
            ),
            // --- RUBRICA 7: ANEDOTA DO ZÉ ---
            ZeBalloonTip(
                category = "Anedotas",
                badgeLabel = "ANEDOTA DO ZÉ 😄",
                icon = Icons.Default.Face,
                badgeBgColor = Color(0xFFFFF8E1),
                badgeTextColor = Color(0xFFF57F17),
                text = "O que diz um zero para o oito?",
                answer = "Que cinto bonito! ⭕8️⃣",
                emotion = MascotEmotion.CELEBRATING
            ),
            // --- RUBRICA 8: CURIOSIDADE ESPACIAL ---
            ZeBalloonTip(
                category = "Espaço",
                badgeLabel = "CURIOSIDADE ESPACIAL 🚀",
                icon = Icons.Default.RocketLaunch,
                badgeBgColor = Color(0xFFE8EAF6),
                badgeTextColor = Color(0xFF283593),
                text = "Curiosidade Espacial: No Espaço não existe som! É o lugar mais silencioso do Universo! 🌌✨",
                emotion = MascotEmotion.EXCITED
            ),
            // --- RUBRICA 9: DESAFIOS DO ZÉ ---
            ZeBalloonTip(
                category = "Desafios",
                badgeLabel = "DESAFIO DO ZÉ 🏆",
                icon = Icons.Default.Favorite,
                badgeBgColor = Color(0xFFE8F5E9),
                badgeTextColor = Color(0xFF2E7D32),
                text = "Consegues dar 5 pulinhos no mesmo pé sem perder o equilíbrio? Experimenta! 🦘⭐",
                emotion = MascotEmotion.CELEBRATING
            ),
            // --- RUBRICA 10: CONSELHOS DO ZÉ ---
            ZeBalloonTip(
                category = "Conselhos",
                badgeLabel = "CONSELHO DO ZÉ 🌟",
                icon = Icons.Default.Star,
                badgeBgColor = Color(0xFFF3E8FF),
                badgeTextColor = Color(0xFF7C4DFF),
                text = "Partilha os teus brinquedos com os teus amigos! Brincar juntos é muito mais divertido! 🤝❤️",
                emotion = MascotEmotion.PROUD
            ),
            // --- ROUND 2: TRAVA-LÍNGUAS 2 ---
            ZeBalloonTip(
                category = "Trava-Línguas",
                badgeLabel = "TRAVA-LÍNGUAS 👅",
                icon = Icons.Default.RecordVoiceOver,
                badgeBgColor = Color(0xFFFCE4EC),
                badgeTextColor = Color(0xFFC2185B),
                text = "Trava-Língua: A aranha arranha a rã, a rã arranha a aranha! 🕷️🐸",
                answer = "Conseguiste dizer super rápido sem enganar? Fantástico! 👏",
                emotion = MascotEmotion.HAPPY
            ),
            // --- ROUND 2: PIADAS DO ZÉ ---
            ZeBalloonTip(
                category = "Piadas",
                badgeLabel = "PIADA DO ZÉ 🤪",
                icon = Icons.Default.Celebration,
                badgeBgColor = Color(0xFFFFFDE7),
                badgeTextColor = Color(0xFFF57F17),
                text = "O que é que o tomate foi fazer ao banco?",
                answer = "Tirar extrato de tomate! 🍅🏦",
                emotion = MascotEmotion.CELEBRATING
            ),
            // --- ROUND 2: SABIAS QUE 2 ---
            ZeBalloonTip(
                category = "Sabias que?",
                badgeLabel = "SABIAS QUE? ❓",
                icon = Icons.Default.AutoAwesome,
                badgeBgColor = Color(0xFFE0F2FE),
                badgeTextColor = Color(0xFF0288D1),
                text = "Sabias que os golfinhos dormem com um olho aberto para vigiar o oceano? 🐬👁️",
                emotion = MascotEmotion.THINKING
            ),
            // --- ROUND 2: ADIVINHA 2 ---
            ZeBalloonTip(
                category = "Adivinhas",
                badgeLabel = "ADIVINHA DO ZÉ 🧩",
                icon = Icons.Default.SmartToy,
                badgeBgColor = Color(0xFFFFF3E0),
                badgeTextColor = Color(0xFFE65100),
                text = "Qual é a coisa qual é ela que cai em pé e corre deitada?",
                answer = "A Chuva! 🌧️💧",
                emotion = MascotEmotion.EXCITED
            )
        )
    }

    var currentTipIndex by remember { mutableIntStateOf(0) }

    val activeTip = remember(currentTipIndex, allZeTips) {
        if (allZeTips.isNotEmpty()) {
            allZeTips[currentTipIndex % allZeTips.size]
        } else {
            null
        }
    }

    // Continuous auto-rotation for the full-time balloon tips, riddles, and jokes
    LaunchedEffect(allZeTips.size) {
        if (allZeTips.isNotEmpty()) {
            while (true) {
                delay(7000L) // Rotates every 7 seconds automatically
                currentTipIndex = (currentTipIndex + 1) % allZeTips.size
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 12.dp, vertical = 4.dp)
                .testTag("home_screen"),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Header Image ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                val context = androidx.compose.ui.platform.LocalContext.current
                val headerImgReq = androidx.compose.runtime.remember {
                    AppImageLoader.buildHeaderRequest(
                        context = context,
                        data = "https://i.imgur.com/ul5vhpV.png",
                        placeholderRes = R.drawable.bg_menu_inicial
                    )
                }
                SafeAsyncImage(
                    model = headerImgReq,
                    contentDescription = "Cabeçalho Menu Inicial",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )

                AudioPlayerBarComposable(
                    isPlayingState = isAudioPlaying,
                    onPlayStateChanged = { isAudioPlaying = it },
                    url = "https://files.catbox.moe/r850fc.mp3",
                    modifier = Modifier
                        .align(androidx.compose.ui.Alignment.BottomEnd)
                        .padding(6.dp)
                        .width(190.dp)
                )
            }

            // --- 3. Vídeo Principal do Zé Traquina ---
            HomeVideoPlayerCard(
                videoUrl = "https://files.catbox.moe/cxlun5.mp4",
                starsCount = userProgress.starsCount
            )

            // --- 4. Destaques do Dia (Changes Daily based on Day of Week) ---
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .offset(y = 4.dp)
                            .zIndex(2f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFFDE68A),
                            border = BorderStroke(1.5.dp, Color(0xFFB45309)),
                            shadowElevation = 3.dp
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "🪵", fontSize = 11.sp)
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = "Destaques de $todayName 🌟",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF451A03),
                                    maxLines = 2,
                                    lineHeight = 12.sp
                                )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .offset(y = 4.dp)
                            .zIndex(2f),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFFCD34D),
                            border = BorderStroke(1.5.dp, Color(0xFFB45309)),
                            shadowElevation = 3.dp
                        ) {
                            Text(
                                text = "Muda Diariamente! 🗓️",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF451A03),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                                maxLines = 1
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    HighlightCard(
                        category = highlight1.category,
                        symbol = highlight1.symbol,
                        emoji = highlight1.emoji,
                        title = highlight1.title,
                        badgeColor = highlight1.badgeColor,
                        containerColor = highlight1.containerColor,
                        testTag = highlight1.testTag,
                        imageRes = highlight1.imageRes,
                        primaryColor = highlight1.primaryColor,
                        darkerColor = highlight1.darkerColor,
                        deepShadowColor = highlight1.deepShadowColor,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(highlight1.targetScreen) }
                    )

                    HighlightCard(
                        category = highlight2.category,
                        symbol = highlight2.symbol,
                        emoji = highlight2.emoji,
                        title = highlight2.title,
                        badgeColor = highlight2.badgeColor,
                        containerColor = highlight2.containerColor,
                        testTag = highlight2.testTag,
                        imageRes = highlight2.imageRes,
                        primaryColor = highlight2.primaryColor,
                        darkerColor = highlight2.darkerColor,
                        deepShadowColor = highlight2.deepShadowColor,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(highlight2.targetScreen) }
                    )
                }
            }

            // --- 5. Full-Time Zé Traquina Speech Balloon (3D Glassmorphism Panel) ---
            if (activeTip != null) {
                FullTimeZeBalloonCard(
                    activeTip = activeTip,
                    onNextTip = {
                        if (allZeTips.isNotEmpty()) {
                            currentTipIndex = (currentTipIndex + 1) % allZeTips.size
                        }
                    },
                    onSpeak = { textToSpeak ->
                        val cleanText = textToSpeak.replace(Regex("[^A-Za-zÀ-ÖØ-öø-ÿ0-9,?!. ]"), "")
                        onSpeak(cleanText)
                    }
                )
            }

            Spacer(modifier = Modifier.height(3.dp))
        }
    }
}


/**
 * Top Glass Header Component displaying clean white "Universo educativo",
 * playful vibrant 3D "Zé Traquina", and a 3D Glass Star Counter.
 */
@Composable
fun HomeTopHeader(
    starsCount: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 3D Glass Star Counter Pill
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White.copy(alpha = 0.90f),
            border = BorderStroke(1.5.dp, SunshineYellow),
            shadowElevation = 6.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "⭐",
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "$starsCount",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFFD81B60)
                )
            }
        }
    }
}

/**
 * FullTimeZeBalloonCard: Um balão de mensagem em retângulo arredondado
 * com fundo estilo madeira/glass (fusão de tons de madeira nobre com vidro fosco/frosted glass)
 * e a bola da foto do Zé Traquina integrada no interior.
 */
@Composable
fun FullTimeZeBalloonCard(
    activeTip: ZeBalloonTip,
    onNextTip: () -> Unit,
    onSpeak: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isAnswerRevealed by remember(activeTip) { mutableStateOf(false) }

    val pulseBorderWidth = 2.5f

    // Gradiente dinâmico estático na alocação
    val animatedBorderBrush = remember(activeTip) {
        Brush.linearGradient(
            colors = listOf(
                activeTip.badgeBgColor,
                Color.White.copy(alpha = 0.9f),
                activeTip.badgeTextColor
            ),
            start = Offset(0f, 0f),
            end = Offset(1000f, 1000f)
        )
    }

    val animatedAvatarBorderBrush = remember(activeTip) {
        Brush.linearGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.9f),
                activeTip.badgeTextColor,
                activeTip.badgeBgColor
            ),
            start = Offset(0f, 0f),
            end = Offset(200f, 200f)
        )
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = activeTip.badgeTextColor.copy(alpha = 0.50f),
                ambientColor = Color.Black.copy(alpha = 0.30f)
            )
            .testTag("ze_balloon_card"),
        shape = RoundedCornerShape(20.dp),
        color = activeTip.badgeTextColor,
        border = BorderStroke(
            width = pulseBorderWidth.dp,
            brush = animatedBorderBrush
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                // Fundo com a cor dinâmica do tema
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            activeTip.badgeTextColor.copy(alpha = 0.8f),
                            activeTip.badgeTextColor,
                            activeTip.badgeTextColor
                        )
                    )
                )
        ) {
            // Efeito Frosted Glass Sheen de Topo (Reflexo vítreo curvo)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.35f),
                                Color.White.copy(alpha = 0.08f),
                                Color.Transparent
                            )
                        )
                    )
            )

            // Efeito de escurecimento na base (Sombra interna inferior) para dar profundidade
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.15f),
                                Color.Black.copy(alpha = 0.40f)
                            )
                        )
                    )
            )

            // Difusão de Luz Branca/Cristalina Interna (Glow)
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.15f),
                                Color.Transparent
                            ),
                            center = Offset(180f, 60f),
                            radius = 400f
                        )
                    )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // 1. Bola da foto do Zé Traquina integrada no balão (Madeira + Borda Dourada/Vidro Animada)
                Box(
                    modifier = Modifier
                        .size(62.dp)
                        .shadow(
                            elevation = 6.dp,
                            shape = CircleShape,
                            spotColor = Color(0xFF451A03).copy(alpha = 0.60f),
                            ambientColor = Color.Black.copy(alpha = 0.30f)
                        )
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFFFEF3C7),
                                    Color(0xFFFDE68A),
                                    Color(0xFFD97706)
                                )
                            ),
                            shape = CircleShape
                        )
                        .border(
                            BorderStroke(
                                width = 2.5.dp,
                                brush = animatedAvatarBorderBrush
                            ),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    MascotFaceCircle(
                        size = 56.dp
                    )
                }

                // 2. Conteúdo da mensagem do balão
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Top Bar do balão: Categoria / Badge e Ações
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Badge da mensagem estilo Dinâmico/Glass
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = activeTip.badgeBgColor,
                            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)),
                            shadowElevation = 2.dp
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = activeTip.icon,
                                    contentDescription = null,
                                    tint = activeTip.badgeTextColor,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = activeTip.badgeLabel,
                                    fontSize = 9.5.sp,
                                    fontWeight = FontWeight.Black,
                                    color = activeTip.badgeTextColor
                                )
                            }
                        }

                        // Botões de Ouvir e Próxima Mensagem
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            IconButton(
                                onClick = {
                                    val speechText = if (activeTip.answer != null) {
                                        if (isAnswerRevealed) "${activeTip.text} A resposta é: ${activeTip.answer}" else activeTip.text
                                    } else activeTip.text
                                    onSpeak(speechText)
                                },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                    contentDescription = "Ouvir a Mensagem do Zé",
                                    tint = Color(0xFFFFFBEB),
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            IconButton(
                                onClick = {
                                    isAnswerRevealed = false
                                    onNextTip()
                                },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Nova Mensagem",
                                    tint = Color(0xFFFFFBEB),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }

                    // Texto da Mensagem perfeitamente legível com alto contraste sobre madeira/vidro
                    Text(
                        text = activeTip.text,
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFFDF0),
                        lineHeight = 17.sp,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.7f),
                                offset = Offset(0f, 1f),
                                blurRadius = 2f
                            )
                        )
                    )

                    // Área interativa de resposta para Adivinhas / Piadas / Trava-Línguas
                    if (activeTip.answer != null) {
                        Spacer(modifier = Modifier.height(2.dp))
                        if (isAnswerRevealed) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = activeTip.badgeBgColor,
                                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)),
                                shadowElevation = 2.dp
                            ) {
                                Text(
                                    text = "👉 Resposta: ${activeTip.answer}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = activeTip.badgeTextColor,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        } else {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color.Black.copy(alpha = 0.2f),
                                shadowElevation = 0.dp,
                                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f)),
                                modifier = Modifier
                                    .clickable {
                                        isAnswerRevealed = true
                                        val speechText = "${activeTip.text} A resposta é: ${activeTip.answer}"
                                        onSpeak(speechText)
                                    }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.Help,
                                        contentDescription = "Ver Resposta",
                                        tint = Color.White,
                                        modifier = Modifier.size(11.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "VER RESPOSTA",
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
        }
    }
}

@Composable
fun HighlightCard(
    category: String,
    symbol: String,
    emoji: String,
    title: String,
    badgeColor: Color,
    containerColor: Color,
    testTag: String,
    @DrawableRes imageRes: Int,
    primaryColor: Color,
    darkerColor: Color,
    deepShadowColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "highlight_card_scale"
    )

    val cardShape = RoundedCornerShape(16.dp)

    Surface(
        modifier = modifier
            .heightIn(min = 60.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .shadow(
                elevation = if (isPressed) 1.dp else 2.dp,
                shape = cardShape,
                spotColor = Color.Black.copy(alpha = 0.1f)
            )
            .testTag(testTag),
        shape = cardShape,
        color = Color(0xFFFFFDF9),
        border = BorderStroke(
            width = 1.dp,
            color = Color(0xFFE2E8F0)
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Subtle tactile dot/stripe texture overlay
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(cardShape)
            ) {
                val stripeColor = Color(0xFF94A3B8).copy(alpha = 0.08f)
                val step = 14.dp.toPx()
                var x = -size.height
                while (x < size.width + size.height) {
                    drawLine(
                        color = stripeColor,
                        start = Offset(x, 0f),
                        end = Offset(x + size.height, size.height),
                        strokeWidth = 2f
                    )
                    x += step
                }
            }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Compact 3D icon box
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .shadow(1.dp, RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .border(
                        width = 1.dp,
                        color = badgeColor.copy(alpha = 0.35f),
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                if (imageRes != 0) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = category,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(10.dp))
                    )
                } else {
                    Text(
                        text = symbol,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = badgeColor,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = category.uppercase(),
                    fontSize = 8.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = title,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B),
                    maxLines = 2,
                    lineHeight = 12.sp
                )
            }
          }
        }
    }
}

@Composable
fun QuickMenuCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    highlightColor: Color,
    primaryColor: Color,
    darkerColor: Color,
    deepShadowColor: Color,
    testTag: String,
    modifier: Modifier = Modifier,
    @DrawableRes imageRes: Int? = null,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // 3D Physical press displacement animation
    val pressOffsetY by animateDpAsState(
        targetValue = if (isPressed) 3.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "quick_card_offset"
    )
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "quick_card_scale"
    )

    val cardCornerRadius = 14.dp
    val cardShape = RoundedCornerShape(cardCornerRadius)

    // Master 3D Physical Button Assembly (Increased height for full text visibility)
    Box(
        modifier = modifier
            .height(58.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .shadow(
                elevation = if (isPressed) 1.dp else 4.dp,
                shape = cardShape,
                spotColor = deepShadowColor.copy(alpha = 0.85f),
                ambientColor = Color.Black.copy(alpha = 0.35f)
            )
            // Fixed bottom 3D extruded pedestal / foundation
            .background(
                color = deepShadowColor,
                shape = cardShape
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .testTag(testTag)
    ) {
        // Lower beveled side accent for deep 3D realism
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            darkerColor.copy(alpha = 0.5f),
                            deepShadowColor
                        )
                    ),
                    shape = RoundedCornerShape(
                        bottomStart = cardCornerRadius,
                        bottomEnd = cardCornerRadius
                    )
                )
        )

        // 3D Raised Push-Button Cap (Moves downward when pressed)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .offset(y = pressOffsetY)
                // 3D Front Face Gradient with Rich Volume & Color Saturation
                .background(
                    brush = Brush.verticalGradient(
                        colorStops = arrayOf(
                            0.0f to highlightColor,
                            0.30f to primaryColor,
                            1.0f to darkerColor
                        )
                    ),
                    shape = cardShape
                )
                // 3D Top Rim-Light / Specular Edge Reflection
                .border(
                    BorderStroke(
                        width = 1.dp,
                        brush = Brush.verticalGradient(
                            colorStops = arrayOf(
                                0.0f to Color.White.copy(alpha = 0.90f),
                                0.35f to Color.White.copy(alpha = 0.30f),
                                1.0f to Color.Black.copy(alpha = 0.20f)
                            )
                        )
                    ),
                    shape = cardShape
                )
        ) {
            // Subtle Texture Overlay (diagonal textured stripes for tactile feel)
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(cardShape)
            ) {
                val stripeColor = Color.White.copy(alpha = 0.08f)
                val step = 12.dp.toPx()
                var x = -size.height
                while (x < size.width + size.height) {
                    drawLine(
                        color = stripeColor,
                        start = Offset(x, 0f),
                        end = Offset(x + size.height, size.height),
                        strokeWidth = 2.5f
                    )
                    x += step
                }
            }

            // Glass Sheen Arc / Curvature Gloss Highlight (Top Half)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.44f)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.40f),
                                Color.White.copy(alpha = 0.0f)
                            )
                        ),
                        shape = RoundedCornerShape(
                            topStart = cardCornerRadius,
                            topEnd = cardCornerRadius,
                            bottomStart = 6.dp,
                            bottomEnd = 6.dp
                        )
                    )
            )

            // Content Row (3D Icon on side, text on side)
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // 3D Colorful Icon Container (Floating Glossy Medallion)
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        // Deep drop shadow behind orb
                        .shadow(
                            elevation = 2.dp,
                            shape = CircleShape,
                            spotColor = deepShadowColor.copy(alpha = 0.85f),
                            ambientColor = Color.Black.copy(alpha = 0.40f)
                        )
                        // 3D Sphere Surface Shading
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color.White,
                                    Color.White.copy(alpha = 0.96f),
                                    highlightColor.copy(alpha = 0.40f)
                                ),
                                center = Offset(10f, 10f),
                                radius = 40f
                            ),
                            shape = CircleShape
                        )
                        // 3D Rim bevel for orb
                        .border(
                            BorderStroke(
                                width = 1.5.dp,
                                brush = Brush.verticalGradient(
                                    listOf(
                                        Color.White,
                                        Color.White.copy(alpha = 0.90f),
                                        darkerColor.copy(alpha = 0.35f)
                                    )
                                )
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (imageRes != null) {
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                        )
                    } else {
                        Icon(
                            imageVector = icon,
                            contentDescription = title,
                            tint = primaryColor,
                            modifier = Modifier
                                .size(22.dp)
                                .graphicsLayer {
                                    shadowElevation = 1f
                                }
                        )
                    }
                }

                // Text Column (Left-aligned next to icon)
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    // Title: 3D Embossed Heading with Deep Shadow
                    Text(
                        text = title.uppercase(),
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 12.sp,
                        letterSpacing = 0.2.sp,
                        maxLines = 1,
                        style = TextStyle(
                            shadow = Shadow(
                                color = deepShadowColor.copy(alpha = 0.95f),
                                offset = Offset(0f, 1f),
                                blurRadius = 1f
                            )
                        )
                    )

                    Spacer(modifier = Modifier.height(1.dp))

                    // Subtitle: Crisp High-Contrast White with soft outline shadow
                    Text(
                        text = subtitle,
                        color = Color.White.copy(alpha = 0.98f),
                        fontSize = 8.5.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        lineHeight = 10.sp,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.7f),
                                offset = Offset(0f, 1f),
                                blurRadius = 1f
                            )
                        )
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// COMPOSE PREVIEWS FOR VISUAL EDITOR / TOOLING
// -------------------------------------------------------------

@androidx.media3.common.util.UnstableApi
@Preview(name = "Dashboard - Menu Inicial Padrão", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun HomeScreenDashboardPreview_Default() {
    FundoApp(drawImage = true) {
        HomeScreenContent(
            userProgress = UserProgress(
                starsCount = 25,
                streakDays = 3,
                totalGamesPlayed = 8
            )
        )
    }
}

@androidx.media3.common.util.UnstableApi
@Preview(name = "Dashboard - Com Progresso Elevado", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun HomeScreenDashboardPreview_WithProgress() {
    FundoApp(drawImage = true) {
        HomeScreenContent(
            userProgress = UserProgress(
                starsCount = 150,
                streakDays = 14,
                totalGamesPlayed = 42
            )
        )
    }
}

@androidx.media3.common.util.UnstableApi
@Preview(name = "Dashboard + Barra de Navegação Inferior", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun HomeScreenWithBottomNavPreview_FullLayout() {
    FundoApp(drawImage = true) {
        androidx.compose.material3.Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                UniversoBottomNavBar(
                    screens = mainNavScreens,
                    activeRoute = Screen.Home.route,
                    onScreenSelected = {}
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                HomeScreenContent(
                    userProgress = UserProgress(
                        starsCount = 75,
                        streakDays = 5,
                        totalGamesPlayed = 19
                    )
                )
            }
        }
    }
}

