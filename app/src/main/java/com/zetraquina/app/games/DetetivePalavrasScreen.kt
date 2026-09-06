package com.zetraquina.app.games

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.screens.ConfettiEffect
import com.example.ui.theme.MintGreen
import com.example.ui.theme.SkyBluePrimary
import com.example.ui.theme.SunshineYellow
import com.example.viewmodel.MainViewModel
import com.example.ui.components.ZeTraquinaMascot
import com.example.ui.components.MascotEmotion
import com.example.ui.components.AdaptiveContentScreen
import com.example.ui.components.LocalAdaptiveSizing
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

data class MysteryWord(
    val id: Int,
    val word: String,
    val emoji: String,
    val hintCategory: String,
    val options: List<String>
)

val defaultMysteryWords = listOf(
    MysteryWord(
        id = 1,
        word = "GATO",
        emoji = "🐱",
        hintCategory = "É um animal fofinho de estimação que faz miau e gosta de leite!",
        options = listOf("GATO", "PATO", "RATO", "SAPO")
    ),
    MysteryWord(
        id = 2,
        word = "BOLO",
        emoji = "🎂",
        hintCategory = "É um doce delicioso que comemos nas festas de aniversário!",
        options = listOf("BOLO", "BOLA", "BOTA", "COLA")
    ),
    MysteryWord(
        id = 3,
        word = "SAPO",
        emoji = "🐸",
        hintCategory = "É um anfíbio verde muito brincalhão que dá pulos na lagoa!",
        options = listOf("SAPO", "SOPA", "SACO", "SECO")
    ),
    MysteryWord(
        id = 4,
        word = "BOLA",
        emoji = "⚽",
        hintCategory = "É um brinquedo redondo usado para jogar futebol e brincar no parque!",
        options = listOf("BOLA", "BOLO", "BOTA", "RODA")
    ),
    MysteryWord(
        id = 5,
        word = "CASACO",
        emoji = "🧥",
        hintCategory = "É uma roupa quentinha que vestimos no inverno quando faz frio!",
        options = listOf("CASACO", "MACACO", "SAPATO", "BONECO")
    ),
    MysteryWord(
        id = 6,
        word = "PATO",
        emoji = "🦆",
        hintCategory = "É uma ave aquática engraçada que nada na lagoa e faz 'quack quack'!",
        options = listOf("PATO", "GATO", "RATO", "PRATO")
    ),
    MysteryWord(
        id = 7,
        word = "CARRO",
        emoji = "🚗",
        hintCategory = "É um meio de transporte com 4 rodas que anda na estrada com a família!",
        options = listOf("CARRO", "BARCO", "CORRO", "TREM")
    ),
    MysteryWord(
        id = 8,
        word = "MACACO",
        emoji = "🐒",
        hintCategory = "É um animal traquina que adora saltar em árvores e comer bananas!",
        options = listOf("MACACO", "CASACO", "MALUCO", "CAVALO")
    ),
    MysteryWord(
        id = 9,
        word = "VACA",
        emoji = "🐮",
        hintCategory = "É um animal grande da quinta que produz um leite gostoso e nutritivo!",
        options = listOf("VACA", "MALA", "FACA", "PACA")
    ),
    MysteryWord(
        id = 10,
        word = "LÁPIS",
        emoji = "✏️",
        hintCategory = "É um objeto escolar essencial que usas para escrever e fazer desenhos bonitos!",
        options = listOf("LÁPIS", "LIVRO", "RÉGUA", "PAPEL")
    ),
    MysteryWord(
        id = 11,
        word = "LEÃO",
        emoji = "🦁",
        hintCategory = "É o valente rei da selva com uma juba enorme e um rugido bem forte!",
        options = listOf("LEÃO", "CÃO", "PEÃO", "GATO")
    ),
    MysteryWord(
        id = 12,
        word = "PEIXE",
        emoji = "🐟",
        hintCategory = "Vive na água a nadar feliz com as suas barbatanas brilhantes!",
        options = listOf("PEIXE", "PATO", "POLVO", "SAPO")
    ),
    MysteryWord(
        id = 13,
        word = "FOGUETÃO",
        emoji = "🚀",
        hintCategory = "Voa veloz até ao espaço sideral para explorar as estrelas e os planetas!",
        options = listOf("FOGUETÃO", "AVIÃO", "BALÃO", "CAMIÃO")
    ),
    MysteryWord(
        id = 14,
        word = "BORBOLETA",
        emoji = "🦋",
        hintCategory = "Tem asas com padrões coloridos e voa suavemente entre as flores do jardim!",
        options = listOf("BORBOLETA", "JOANINHA", "ABELHA", "FORMIGA")
    ),
    MysteryWord(
        id = 15,
        word = "MORANGO",
        emoji = "🍓",
        hintCategory = "É uma fruta vermelha pequenina, doce e com sementinhas por fora!",
        options = listOf("MORANGO", "BANANA", "LARANJA", "CEREJA")
    ),
    MysteryWord(
        id = 16,
        word = "GIRAFA",
        emoji = "🦒",
        hintCategory = "É o animal mais alto da savana, com um pescoço bem comprido e manchas castanhas!",
        options = listOf("GIRAFA", "ZEBRA", "ELEFANTE", "LEOPARDO")
    ),
    MysteryWord(
        id = 17,
        word = "PIANO",
        emoji = "🎹",
        hintCategory = "Instrumento musical majestoso com teclas pretas e brancas que tocam lindas melodias!",
        options = listOf("PIANO", "GUITARRA", "TAMBOR", "FLAUTA")
    ),
    MysteryWord(
        id = 18,
        word = "DINOSSAURO",
        emoji = "🦖",
        hintCategory = "Criatura gigante do passado que viveu na Terra há milhões de anos!",
        options = listOf("DINOSSAURO", "DRAGÃO", "TUBARÃO", "CROCODILO")
    ),
    MysteryWord(
        id = 19,
        word = "SOL",
        emoji = "☀️",
        hintCategory = "Estrela brilhante que ilumina as nossas manhãs e aquece o nosso planeta!",
        options = listOf("SOL", "LUA", "CÉU", "MAR")
    ),
    MysteryWord(
        id = 20,
        word = "LIVRO",
        emoji = "📚",
        hintCategory = "Tem muitas páginas cheias de histórias mágicas e aventuras para leres!",
        options = listOf("LIVRO", "LÁPIS", "CADERNO", "ESTOJO")
    )
)

@Composable
fun DetetivePalavrasScreen(
    mainViewModel: MainViewModel? = null,
    onBack: () -> Unit
) {
    val isPreview = LocalInspectionMode.current
    val context = LocalContext.current
    var currentLevelIndex by remember { mutableIntStateOf(0) }
    var solvedCount by remember { mutableIntStateOf(0) }
    var revealedHints by remember { mutableStateOf(setOf<Int>()) }
    var wrongOptionSelected by remember { mutableStateOf<String?>(null) }
    var isCorrect by remember { mutableStateOf<Boolean?>(null) }
    var showErrorCard by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val mysteries = defaultMysteryWords
    val currentMystery = mysteries[currentLevelIndex % mysteries.size]

    fun speakText(text: String) {
        if (!isPreview) {
            mainViewModel?.speak(text)
        }
    }

    // Read new case on level switch
    LaunchedEffect(currentLevelIndex) {
        if (!isPreview) {
            delay(500)
            speakText("Caso Secreto número ${currentLevelIndex + 1}. Qual é a palavra secreta?")
        }
    }

    // Loving encouraging messages for wrong attempts
    val encouragingMessages = remember {
        listOf(
            "Quase lá, Detetive! 🔍 Tenta outra vez, examina as tuas pistas!",
            "Não faz mal! Um bom detetive nunca desiste! Examina bem as letras! 💡",
            "Tenta mais uma vez! Lê as pistas com atenção, tu consegues! 🚀",
            "Foi por pouco! Escolhe outra palavra, o mistério está quase resolvido! 🕵️"
        )
    }

    // Auto next level on correct answer after 2.2 seconds delay
    LaunchedEffect(isCorrect, currentLevelIndex) {
        if (isCorrect == true) {
            if (!isPreview) {
                mainViewModel?.addStars(5)
                speakText("Excelente trabalho, Super Detetive! Descobriste a palavra ${currentMystery.word}!")
            }
            delay(2200)
            isCorrect = null
            showErrorCard = false
            wrongOptionSelected = null
            revealedHints = emptySet()
            currentLevelIndex = (currentLevelIndex + 1) % mysteries.size
        }
    }

    // Helper to format hidden word representation
    val formattedHiddenWord = remember(currentMystery, revealedHints, isCorrect) {
        val word = currentMystery.word
        if (isCorrect == true) {
            word.toCharArray().joinToString(" ")
        } else {
            word.toCharArray().mapIndexed { index, char ->
                if (index == 0 && revealedHints.contains(2)) {
                    char.toString()
                } else {
                    "_"
                }
            }.joinToString(" ")
        }
    }

    AdaptiveContentScreen {
        val sizing = LocalAdaptiveSizing.current

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF0F172A), // Dark detective navy top
                            Color(0xFF1E293B),
                            Color(0xFF334155)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = sizing.scalePadding(12.dp),
                        vertical = sizing.scalePadding(6.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // --- TOP BAR (Back button, Title & Level, Scoreboard) ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .size(sizing.scaleSize(38.dp))
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.15f))
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar ao Menu de Jogos",
                            tint = Color.White,
                            modifier = Modifier.size(sizing.scaleSize(20.dp))
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Detetive de Palavras 🔍",
                            fontSize = sizing.scaleFont(16.sp),
                            fontWeight = FontWeight.Black,
                            color = SunshineYellow
                        )
                        Text(
                            text = "Mistério ${currentLevelIndex + 1} de ${mysteries.size}",
                            fontSize = sizing.scaleFont(11.sp),
                            fontWeight = FontWeight.Bold,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFF59E0B)
                    ) {
                        Row(
                            modifier = Modifier.padding(
                                horizontal = sizing.scalePadding(8.dp),
                                vertical = sizing.scalePadding(4.dp)
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🏆 $solvedCount",
                                fontSize = sizing.scaleFont(11.sp),
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                        }
                    }
                }

            // --- DETECTIVE MYSTERY BOARD (Flexibly Weighted) ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
                    .padding(vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                    border = BorderStroke(2.dp, SunshineYellow.copy(alpha = 0.6f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = "🕵️", fontSize = 26.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "CASO SECRETO ${currentLevelIndex + 1}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = SkyBluePrimary,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            IconButton(
                                onClick = {
                                    speakText("Caso Secreto número ${currentLevelIndex + 1}. Descobre a palavra de ${currentMystery.word.length} letras!")
                                },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                    contentDescription = "Ouvir Caso",
                                    tint = SunshineYellow,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        // Hidden Word Card Display
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = Color(0xFF0284C7).copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, SkyBluePrimary),
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = formattedHiddenWord,
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White,
                                    letterSpacing = 4.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            // --- PISTAS GRADUAIS SECTION (3 Hint Buttons) ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "🔎 Pistas do Zé Traquina:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = SunshineYellow
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Pista 1 (Visual/Categoria)
                    Button(
                        onClick = {
                            revealedHints = revealedHints + 1
                            speakText("Pista 1: ${currentMystery.hintCategory}")
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (revealedHints.contains(1)) SunshineYellow else Color(0xFF334155)
                        ),
                        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = if (revealedHints.contains(1)) Color.Black else SunshineYellow,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "Pista 1 💡",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (revealedHints.contains(1)) Color.Black else Color.White
                            )
                        }
                    }

                    // Pista 2 (Letra Inicial)
                    Button(
                        onClick = {
                            revealedHints = revealedHints + 2
                            speakText("Pista 2: A palavra começa pela letra ${currentMystery.word.first()}!")
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (revealedHints.contains(2)) MintGreen else Color(0xFF334155)
                        ),
                        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "🔤 Pista 2",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (revealedHints.contains(2)) Color.Black else Color.White
                            )
                        }
                    }

                    // Pista 3 (Tamanho)
                    Button(
                        onClick = {
                            revealedHints = revealedHints + 3
                            speakText("Pista 3: A palavra tem ${currentMystery.word.length} letras!")
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (revealedHints.contains(3)) SkyBluePrimary else Color(0xFF334155)
                        ),
                        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "📏 Pista 3",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (revealedHints.contains(3)) Color.Black else Color.White
                            )
                        }
                    }
                }

                // Display revealed hints content cards with voice play buttons
                if (revealedHints.contains(1)) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = SunshineYellow.copy(alpha = 0.2f),
                        border = BorderStroke(1.dp, SunshineYellow),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "💡 Categoria: ${currentMystery.hintCategory}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SunshineYellow,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = { speakText("Pista 1: ${currentMystery.hintCategory}") },
                                modifier = Modifier.size(20.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                    contentDescription = "Ouvir Pista 1",
                                    tint = SunshineYellow,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                }

                if (revealedHints.contains(2)) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MintGreen.copy(alpha = 0.2f),
                        border = BorderStroke(1.dp, MintGreen),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "🔤 Começa com a letra '${currentMystery.word.first()}'!",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = MintGreen,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = { speakText("Pista 2: A palavra começa pela letra ${currentMystery.word.first()}!") },
                                modifier = Modifier.size(20.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                    contentDescription = "Ouvir Pista 2",
                                    tint = MintGreen,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                }

                if (revealedHints.contains(3)) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = SkyBluePrimary.copy(alpha = 0.2f),
                        border = BorderStroke(1.dp, SkyBluePrimary),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "📏 A palavra tem ${currentMystery.word.length} letras!",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SkyBluePrimary,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = { speakText("Pista 3: A palavra tem ${currentMystery.word.length} letras!") },
                                modifier = Modifier.size(20.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                    contentDescription = "Ouvir Pista 3",
                                    tint = SkyBluePrimary,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                }
            }

            // --- FEEDBACK MESSAGES & CARDS (Erro / Vitória) ---
            if (isCorrect == true) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFF16A34A),
                    shadowElevation = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "🎉 EXCELENTE TRABALHO, SUPER DETETIVE! 🌟",
                                color = SunshineYellow,
                                fontWeight = FontWeight.Black,
                                fontSize = 13.sp,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            IconButton(
                                onClick = { speakText("Excelente trabalho, Super Detetive! Descobriste a palavra ${currentMystery.word}!") },
                                modifier = Modifier.size(20.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                    contentDescription = "Ouvir Parabéns",
                                    tint = SunshineYellow,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                        Text(
                            text = "Descobriste '${currentMystery.word}' ${currentMystery.emoji}! A avançar... 🚀",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else if (showErrorCard) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFFDC2626),
                    shadowElevation = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = errorMessage,
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                        Button(
                            onClick = {
                                showErrorCard = false
                                wrongOptionSelected = null
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SunshineYellow),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "De novo 🚀",
                                color = Color.Black,
                                fontWeight = FontWeight.Black,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }

            // --- OPTIONS GRID (4 Word Options) ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Qual é a palavra secreta?",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                val options = currentMystery.options
                val row1 = options.take(2)
                val row2 = options.drop(2)

                listOf(row1, row2).forEach { rowOptions ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowOptions.forEach { option ->
                            val isWrong = wrongOptionSelected == option
                            val isRight = isCorrect == true && option == currentMystery.word

                            val buttonColor = when {
                                isRight -> MintGreen
                                isWrong -> Color(0xFFEF4444)
                                else -> Color(0xFF334155)
                            }

                            Button(
                                onClick = {
                                    if (isCorrect != true) {
                                        if (option == currentMystery.word) {
                                            isCorrect = true
                                            solvedCount++
                                            showErrorCard = false
                                            wrongOptionSelected = null
                                            speakText("Excelente trabalho, Super Detetive! Descobriste a palavra $option!")
                                        } else {
                                            wrongOptionSelected = option
                                            isCorrect = false
                                            val msg = encouragingMessages.random()
                                            errorMessage = msg
                                            showErrorCard = true
                                            speakText(msg)
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                                border = BorderStroke(
                                    2.dp,
                                    if (isRight) Color.White else if (isWrong) Color.Red else SkyBluePrimary.copy(alpha = 0.5f)
                                ),
                                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                            ) {
                                Text(
                                    text = option,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }

            // --- CELEBRATION CONFETTI OVERLAY ---
            if (isCorrect == true) {
                ConfettiEffect()
            }
        }
    }
}
}
