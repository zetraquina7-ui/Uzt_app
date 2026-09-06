package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.MascotEmotion
import com.example.ui.components.ZeTraquinaMascot
import com.example.ui.theme.KidStarOrange
import com.example.ui.theme.SkyBluePrimary
import com.example.ui.theme.SunshineYellow
import com.example.viewmodel.MainViewModel

data class BookPage(
    val pageNumber: Int,
    val chapterTitle: String,
    val title: String,
    val subtitle: String,
    val emoji: String,
    val description: String,
    val speechText: String,
    val colorHex: Long,
    val accentHex: Long,
    val interactiveItems: List<BookInteractiveItem> = emptyList()
)

data class BookInteractiveItem(
    val id: String,
    val label: String,
    val emoji: String,
    val speechText: String,
    val funFact: String,
    val colorHex: Long
)

@Composable
fun InteractiveBookScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit
) {
    val bookPages = remember {
        listOf(
            // --- CHAPTER 1: CORES (Pages 1 to 4) ---
            BookPage(
                pageNumber = 1,
                chapterTitle = "🎨 Capítulo 1: O Mundo das Cores",
                title = "Vermelho Energético",
                subtitle = "Cor das Cerejas e Bombeiros",
                emoji = "🔴",
                description = "O vermelho é uma cor muito alegre e cheia de energia! Vê-se nos morangos doces, nos corações e nos carros de bombeiros.",
                speechText = "Capítulo 1. Vermelho. O vermelho é a cor dos morangos e dos bombeiros!",
                colorHex = 0xFFFFCDD2,
                accentHex = 0xFFE53935,
                interactiveItems = listOf(
                    BookInteractiveItem("red_1", "Morango", "🍓", "Morango vermelho e doce", "O morango tem sementinhas por fora!", 0xFFFF8A80),
                    BookInteractiveItem("red_2", "Cereja", "🍒", "Cerejas aos pares", "Cerejas parecem brincos vermelhos!", 0xFFFF5252),
                    BookInteractiveItem("red_3", "Bombeiro", "🚒", "Carro de bombeiro", "Os bombeiros ajudam toda a gente com coragem!", 0xFFEF5350),
                    BookInteractiveItem("red_4", "Coração", "❤️", "Coração cheio de amor", "O coração bate com muito carinho!", 0xFFFF1744)
                )
            ),
            BookPage(
                pageNumber = 2,
                chapterTitle = "🎨 Capítulo 1: O Mundo das Cores",
                title = "Azul do Céu e Mar",
                subtitle = "Cor da Tranquilidade",
                emoji = "🔵",
                description = "O azul faz lembrar o céu limpo num dia de sol e o vasto oceano onde nadam os golfinhos e peixinhos.",
                speechText = "Capítulo 1. Azul. O azul é a cor do céu e do mar profundo!",
                colorHex = 0xFFE1F5FE,
                accentHex = 0xFF0288D1,
                interactiveItems = listOf(
                    BookInteractiveItem("blue_1", "Céu", "☀️", "Céu azul limpo", "O sol brilha forte no céu azul!", 0xFF81D4FA),
                    BookInteractiveItem("blue_2", "Golfinho", "🐬", "Golfinho saltitão", "Os golfinhos são super inteligentes!", 0xFF4FC3F7),
                    BookInteractiveItem("blue_3", "Baleia", "🐳", "Baleia gigante", "A baleia é o maior animal dos oceanos!", 0xFF29B6F6),
                    BookInteractiveItem("blue_4", "Barco", "⛵", "Barco a vela", "O barco navega suavemente nas ondas!", 0xFF03A9F4)
                )
            ),
            BookPage(
                pageNumber = 3,
                chapterTitle = "🎨 Capítulo 1: O Mundo das Cores",
                title = "Amarelo Brilhante",
                subtitle = "Cor do Sol e Girassóis",
                emoji = "🟡",
                description = "O amarelo é quente e luminoso como o sol da manhã, as estrelas douradas e os girassóis no campo.",
                speechText = "Capítulo 1. Amarelo. O amarelo é a cor do sol radiante!",
                colorHex = 0xFFFFF9C4,
                accentHex = 0xFFFBC02D,
                interactiveItems = listOf(
                    BookInteractiveItem("yellow_1", "Sol", "☀️", "Sol quente e brilhante", "O sol aquece a Terra inteira!", 0xFFFFEE58),
                    BookInteractiveItem("yellow_2", "Estrela", "⭐", "Estrela dourada", "As estrelas brilham à noite no céu!", 0xFFFFEE33),
                    BookInteractiveItem("yellow_3", "Banana", "🍌", "Banana amarela", "As bananas são cheias de energia!", 0xFFFFEB3B),
                    BookInteractiveItem("yellow_4", "Pintainho", "🐥", "Pintainho fofinho", "O pintainho faz piu piu para a mamã!", 0xFFFFD54F)
                )
            ),
            BookPage(
                pageNumber = 4,
                chapterTitle = "🎨 Capítulo 1: O Mundo das Cores",
                title = "Verde da Natureza",
                subtitle = "Cor das Plantas e Relva",
                emoji = "🟢",
                description = "O verde é a cor da vida na natureza, das árvores frondosas, dos trevos de quatro folhas e dos sapinhos saltitões.",
                speechText = "Capítulo 1. Verde. O verde é a cor da relva e das árvores!",
                colorHex = 0xFFE8F5E9,
                accentHex = 0xFF43A047,
                interactiveItems = listOf(
                    BookInteractiveItem("green_1", "Árvore", "🌳", "Árvore verde", "As árvores dão-nos sombra e ar puro!", 0xFFA5D6A7),
                    BookInteractiveItem("green_2", "Sapo", "🐸", "Sapo saltitão", "O sapo salta alegremente na lagoa!", 0xFF81C784),
                    BookInteractiveItem("green_3", "Maçã Verde", "🍏", "Maçã verde fresca", "A maçã verde é crocante e sumarenta!", 0xFF66BB6A),
                    BookInteractiveItem("green_4", "Folha", "🍃", "Folha da árvore", "As folhas dançam ao som do vento!", 0xFF4CAF50)
                )
            ),

            // --- CHAPTER 2: FORMAS (Pages 5 to 7) ---
            BookPage(
                pageNumber = 5,
                chapterTitle = "📐 Capítulo 2: Formas Geométricas",
                title = "Círculo Redondinho",
                subtitle = "Como uma Bola ou Roda",
                emoji = "⚪",
                description = "O círculo é perfeitamente redondo, sem cantos nenhuns! Roda facilmente como as rodas dos carros e as bolas de futebol.",
                speechText = "Capítulo 2. Círculo. O círculo é redondo como uma bola!",
                colorHex = 0xFFECEFF1,
                accentHex = 0xFF546E7A,
                interactiveItems = listOf(
                    BookInteractiveItem("shape_c1", "Bola", "⚽", "Bola de futebol", "Com a bola jogamos com os amigos!", 0xFFCFD8DC),
                    BookInteractiveItem("shape_c2", "Roda", "🚗", "Roda do carro", "A roda gira e leva-nos a passear!", 0xFFB0BEC5),
                    BookInteractiveItem("shape_c3", "Relógio", "⏰", "Relógio de parede", "O relógio marca as horas das brincadeiras!", 0xFF90A4AE),
                    BookInteractiveItem("shape_c4", "Lua Cheia", "🌕", "Lua redonda no céu", "A lua ilumina a noite estrelada!", 0xFF78909C)
                )
            ),
            BookPage(
                pageNumber = 6,
                chapterTitle = "📐 Capítulo 2: Formas Geométricas",
                title = "Quadrado com 4 Lados",
                subtitle = "Lados Iguais e Cantos Certos",
                emoji = "⬛",
                description = "O quadrado tem quatro lados exatamente iguais e quatro cantinhos direitinhos, como uma caixa de prendas!",
                speechText = "Capítulo 2. Quadrado. O quadrado tem quatro lados iguais!",
                colorHex = 0xFFFFF3E0,
                accentHex = 0xFFEF6C00,
                interactiveItems = listOf(
                    BookInteractiveItem("shape_q1", "Caixa", "📦", "Caixa de surpresas", "Dentro da caixa há sempre uma prenda!", 0xFFFFE0B2),
                    BookInteractiveItem("shape_q2", "Janela", "🪟", "Janela de casa", "Pela janela vemos o jardim florido!", 0xFFFFCC80),
                    BookInteractiveItem("shape_q3", "Almofada", "🛋️", "Almofada fofinha", "Almofadas macias para descansar!", 0xFFFFB74D),
                    BookInteractiveItem("shape_q4", "Dado", "🎲", "Dado de jogar", "O dado tem números em cada face!", 0xFFFFA726)
                )
            ),
            BookPage(
                pageNumber = 7,
                chapterTitle = "📐 Capítulo 2: Formas Geométricas",
                title = "Triângulo de 3 Pontas",
                subtitle = "Como uma Fatia de Pizza",
                emoji = "🔺",
                description = "O triângulo tem três pontas afiadas e três lados. Parece o telhado de uma cabana ou uma deliciosa fatia de pizza!",
                speechText = "Capítulo 2. Triângulo. O triângulo tem três pontas!",
                colorHex = 0xFFFFEBEE,
                accentHex = 0xFFD32F2F,
                interactiveItems = listOf(
                    BookInteractiveItem("shape_t1", "Pizza", "🍕", "Fatia de pizza", "Uma fatia de pizza quentinha e saborosa!", 0xFFFFCDD2),
                    BookInteractiveItem("shape_t2", "Cabana", "⛺", "Tenda de campismo", "A tenda protege-nos no acampamento!", 0xFFEF9A9A),
                    BookInteractiveItem("shape_t3", "Árvore de Natal", "🎄", "Pinheiro de Natal", "O pinheiro tem a forma de um triângulo!", 0xFFE57373),
                    BookInteractiveItem("shape_t4", "Vela de Barco", "⛵", "Vela do barco", "O vento sopra na vela para navegar!", 0xFFEF5350)
                )
            ),

            // --- CHAPTER 3: NÚMEROS (Pages 8 to 10) ---
            BookPage(
                pageNumber = 8,
                chapterTitle = "🔢 Capítulo 3: Contar de 1 a 5",
                title = "Números 1, 2 e 3",
                subtitle = "Contagem Divertida",
                emoji = "🎈",
                description = "Vamos contar juntos! 1 sol, 2 gatinhos fofinhos e 3 estrelas brilhantes no céu mágico do Zé Traquina.",
                speechText = "Capítulo 3. Números um, dois e três. Vamos contar!",
                colorHex = 0xFFF3E5F5,
                accentHex = 0xFF8E24AA,
                interactiveItems = listOf(
                    BookInteractiveItem("num_1", "1 Sol", "☀️", "Número 1: Um sol", "Tens apenas um nariz no centro da cara!", 0xFFE1BEE7),
                    BookInteractiveItem("num_2", "2 Olhos", "👀", "Número 2: Dois olhos", "Tens dois olhos para ver cores e livros!", 0xFFCE93D8),
                    BookInteractiveItem("num_3", "3 Estrelas", "⭐⭐⭐", "Número 3: Três estrelas", "Três estrelas douradas no céu!", 0xFFBA68C8),
                    BookInteractiveItem("num_p", "Contar", "🔢", "Vamos contar!", "Contar é super divertido e útil todos os dias!", 0xFFAB47BC)
                )
            ),
            BookPage(
                pageNumber = 9,
                chapterTitle = "🔢 Capítulo 3: Contar de 1 a 5",
                title = "Números 4 e 5",
                subtitle = "Dedinhos e Foguetes",
                emoji = "🚀",
                description = "O número 4 são as patas do gatinho. O número 5 são os dedinhos numa mão cheia de energia para brincar!",
                speechText = "Capítulo 3. Números quatro e cinco. Cinco dedinhos na mão!",
                colorHex = 0xFFE0F7FA,
                accentHex = 0xFF00ACC1,
                interactiveItems = listOf(
                    BookInteractiveItem("num_4", "4 Patas", "🐾", "Número 4: Quatro patas", "O cãozinho e o gatinho têm quatro patas!", 0xB280DEEA),
                    BookInteractiveItem("num_5", "5 Dedos", "✋", "Número 5: Cinco dedos", "Cinco dedinhos numa mão para pintar!", 0x80ACC1),
                    BookInteractiveItem("num_f", "5 Foguete", "🚀", "Foguete espacial", "Contagem decrescente: 5, 4, 3, 2, 1, Disparo!", 0x4DD0E1),
                    BookInteractiveItem("num_b", "5 Bolas", "⚽⚽⚽⚽⚽", "Cinco bolas", "Cinco bolas prontas para o jogo no recreio!", 0x26C6DA)
                )
            ),
            BookPage(
                pageNumber = 10,
                chapterTitle = "📖 Conclusão: Campeão da Leitura",
                title = "Parabéns, Leitor Mágico!",
                subtitle = "Completaste o Livro Interativo",
                emoji = "🏆",
                description = "Leste todas as páginas do livro de Cores, Formas e Números! És um verdadeiro campeão da Escola Mágica.",
                speechText = "Parabéns! Completaste o livro interativo e ganhaste dez estrelas mágicas!",
                colorHex = 0xFFFFF8E1,
                accentHex = 0xFFFF8F00,
                interactiveItems = listOf(
                    BookInteractiveItem("rew_1", "Estrelas", "⭐", "Dez estrelas ganhas", "Ganhaste um super bónus de leitura!", 0xFFFFD54F),
                    BookInteractiveItem("rew_2", "Troféu", "🏆", "Troféu de Campeão", "O troféu do saber e da leitura!", 0xFFFFCA28),
                    BookInteractiveItem("rew_3", "Medalha", "🎖️", "Medalha de Honra", "Parabéns por seres tãotão curioso!", 0xFFFFB300),
                    BookInteractiveItem("rew_4", "Sorriso", "😁", "Sorriso feliz", "O Zé Traquina está muito orgulhoso de ti!", 0xFFFFA000)
                )
            )
        )
    }

    var currentPageIndex by remember { mutableIntStateOf(0) }
    val currentPage = bookPages[currentPageIndex]
    var hasRewardedCompletion by remember { mutableStateOf(false) }

    LaunchedEffect(currentPageIndex) {
        viewModel.speak(currentPage.speechText)
        if (currentPageIndex == bookPages.size - 1 && !hasRewardedCompletion) {
            hasRewardedCompletion = true
            viewModel.addStars(10)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .padding(14.dp)
            .testTag("interactive_book_screen")
    ) {
        // --- Top Bar: Back Button, Progress & Title ---
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
                Text("Temas", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.MenuBook,
                    contentDescription = null,
                    tint = SkyBluePrimary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Livro Mágico 📖",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF0F172A)
                )
            }

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = SkyBluePrimary
            ) {
                Text(
                    text = "Pág ${currentPage.pageNumber}/${bookPages.size}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Progress bar
        LinearProgressIndicator(
            progress = { (currentPageIndex + 1).toFloat() / bookPages.size },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = SkyBluePrimary,
            trackColor = Color.LightGray.copy(alpha = 0.4f)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // --- Main Book Page Card ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(currentPage.colorHex)),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            border = BorderStroke(2.dp, Color(currentPage.accentHex).copy(alpha = 0.4f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Chapter & Emoji Header
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(currentPage.accentHex)
                    ) {
                        Text(
                            text = currentPage.chapterTitle,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Big Emoji with bounce effect on tap
                    var isEmojiTapped by remember { mutableStateOf(false) }
                    val emojiScale by animateFloatAsState(
                        targetValue = if (isEmojiTapped) 1.2f else 1.0f,
                        label = "emoji_scale"
                    )

                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .scale(emojiScale)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.8f))
                            .clickable {
                                isEmojiTapped = true
                                viewModel.speak(currentPage.speechText)
                                android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                                    isEmojiTapped = false
                                }, 350)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = currentPage.emoji, fontSize = 42.sp)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = currentPage.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF0F172A),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = currentPage.subtitle,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF334155),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = currentPage.description,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF475569),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Interactive Elements Grid (Cores, Formas, Itens)
                Text(
                    text = "Clica nos itens para interagir: 👇",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0F172A)
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(currentPage.interactiveItems) { item ->
                        var isItemTapped by remember { mutableStateOf(false) }
                        val itemScale by animateFloatAsState(
                            targetValue = if (isItemTapped) 1.08f else 1.0f,
                            label = "item_scale"
                        )

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .scale(itemScale)
                                .clickable {
                                    isItemTapped = true
                                    viewModel.speak("${item.speechText}. Curiosidade: ${item.funFact}")
                                    viewModel.addStars(1)
                                    android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                                        isItemTapped = false
                                    }, 300)
                                }
                                .testTag("book_item_${item.id}"),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(item.colorHex)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                            border = BorderStroke(1.dp, Color(currentPage.accentHex).copy(alpha = 0.3f))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(text = item.emoji, fontSize = 24.sp)
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.label,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF0F172A),
                                        maxLines = 1
                                    )
                                    Text(
                                        text = "+1 ⭐",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFFB45309)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // --- Bottom Navigation Buttons (Previous / Next Page) ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    if (currentPageIndex > 0) {
                        currentPageIndex--
                    }
                },
                enabled = currentPageIndex > 0,
                colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary),
                shape = RoundedCornerShape(14.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Anterior", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            Surface(
                shape = CircleShape,
                color = SunshineYellow,
                shadowElevation = 2.dp
            ) {
                Box(
                    modifier = Modifier.padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Button(
                onClick = {
                    if (currentPageIndex < bookPages.size - 1) {
                        currentPageIndex++
                    } else {
                        onBack()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                shape = RoundedCornerShape(14.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = if (currentPageIndex < bookPages.size - 1) "Seguinte" else "Concluir 🏆",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = if (currentPageIndex < bookPages.size - 1) Icons.AutoMirrored.Filled.ArrowForward else Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
