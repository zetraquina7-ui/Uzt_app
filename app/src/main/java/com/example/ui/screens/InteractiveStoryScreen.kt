package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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

data class StoryChoice(
    val id: String,
    val text: String,
    val emoji: String,
    val nextNodeId: String,
    val educationalValue: String? = null,
    val starsReward: Int = 0
)

data class StoryNode(
    val id: String,
    val title: String,
    val text: String,
    val mascotEmotion: MascotEmotion = MascotEmotion.HAPPY,
    val speechAudio: String,
    val choices: List<StoryChoice>,
    val isEnding: Boolean = false,
    val endingRewardStars: Int = 0,
    val endingTitle: String? = null,
    val endingMessage: String? = null,
    val bgGradientHexes: List<Long> = listOf(0xFFF0F9FF, 0xFFE0F2FE)
)

data class InteractiveStory(
    val id: String,
    val title: String,
    val subtitle: String,
    val theme: String,
    val coverEmoji: String,
    val accentColorHex: Long,
    val cardBgHex: Long,
    val estimatedMinutes: String,
    val startNodeId: String,
    val nodes: Map<String, StoryNode>
)

object StoryRepository {
    val stories = listOf(
        InteractiveStory(
            id = "story_cores_floresta",
            title = "A Floresta Amiga e as Cores",
            subtitle = "Ajuda os animais a reencontrar a cesta de frutas coloridas!",
            theme = "Cores, Ecologia & Gentileza 🌲🎨",
            coverEmoji = "🌲",
            accentColorHex = 0xFF2E7D32,
            cardBgHex = 0xFFE8F5E9,
            estimatedMinutes = "3-4 min",
            startNodeId = "start",
            nodes = mapOf(
                "start" to StoryNode(
                    id = "start",
                    title = "O Mistério na Floresta 🌲",
                    text = "O Zé Traquina encontrou a Esquilo Melina muito triste. A sua cesta com frutas vermelhas 🔴, amarelas 🟡 e azuis 🔵 desapareceu! 'Onde devemos procurar primeiro, amiguinho?'",
                    mascotEmotion = MascotEmotion.THINKING,
                    speechAudio = "O Zé Traquina e a Esquilo Melina precisam de ajuda! Onde procuramos a cesta de frutas primeiro?",
                    choices = listOf(
                        StoryChoice("c1", "Explorar o bosque das árvores altas 🌳", "🌳", "bosque"),
                        StoryChoice("c2", "Procurar junto ao lago cristalino 🌊", "🌊", "lago")
                    ),
                    bgGradientHexes = listOf(0xFFE8F5E9, 0xFFC8E6C9)
                ),
                "bosque" to StoryNode(
                    id = "bosque",
                    title = "No Bosque Verde 🌳",
                    text = "Encontrámos morangos vermelhos 🔴 e limões amarelos 🟡 espalhados! Mas lá no alto está um papagaio brincalhão a balançar a cesta. Como pedimos a cesta de volta?",
                    mascotEmotion = MascotEmotion.EXCITED,
                    speechAudio = "Encontrámos o papagaio com a cesta no bosque! Como lhe pedimos a cesta de volta?",
                    choices = listOf(
                        StoryChoice("c3", "Pedir com educação: 'Por favor, Papagaio!' 😊", "😊", "papagaio_amigo", educationalValue = "Gentileza ⭐", starsReward = 5),
                        StoryChoice("c4", "Correr atrás dele a fazer barulho! 🏃", "🏃", "correr_papagaio")
                    ),
                    bgGradientHexes = listOf(0xFFDCEDC8, 0xFFAED581)
                ),
                "lago" to StoryNode(
                    id = "lago",
                    title = "Junto ao Lago Azul 🌊",
                    text = "Junto ao lago vemos mirtilos azuis 🔵! Mas há garrafas de plástico a flutuar na água que impedem os peixinhos de nadar. O que fazemos?",
                    mascotEmotion = MascotEmotion.THINKING,
                    speechAudio = "Há plástico no lago a atrapalhar os peixinhos. O que fazemos?",
                    choices = listOf(
                        StoryChoice("c5", "Limpar o lago e reciclar o plástico! ♻️", "♻️", "lago_limpo", educationalValue = "Cuidar da Natureza 🌿", starsReward = 10),
                        StoryChoice("c6", "Continuar a procurar sem limpar 🍃", "🍃", "lago_direto")
                    ),
                    bgGradientHexes = listOf(0xFFE0F7FA, 0xFF80DEEA)
                ),
                "papagaio_amigo" to StoryNode(
                    id = "papagaio_amigo",
                    title = "Amizade Mágica! 🦜✨",
                    text = "O papagaio abriu as asas radiante! 'Ninguém me tinha pedido com tanta gentileza!' Ele desceu e entregou a cesta cheia de frutos coloridos!",
                    mascotEmotion = MascotEmotion.CELEBRATING,
                    speechAudio = "A gentileza funciona sempre! O papagaio devolveu todas as frutas coloridas!",
                    choices = listOf(
                        StoryChoice("c7", "Fazer um piquenique de cores com todos os animais! 🍎🍌🫐", "🎉", "final_festa_cores")
                    ),
                    bgGradientHexes = listOf(0xFFFFF9C4, 0xFFFFF176)
                ),
                "correr_papagaio" to StoryNode(
                    id = "correr_papagaio",
                    title = "Aprender com carinho 💭",
                    text = "O papagaio voou para outro galho. O Zé lembra suavemente: 'Quando usamos as palavras mágicas como Por Favor, os amigos ficam felizes em ajudar!'",
                    mascotEmotion = MascotEmotion.THINKING,
                    speechAudio = "Vamos usar as palavras mágicas de gentileza!",
                    choices = listOf(
                        StoryChoice("c8", "Dizer carinhosamente: 'Por favor, amigo Papagaio!' 🦜", "🦜", "papagaio_amigo")
                    ),
                    bgGradientHexes = listOf(0xFFFFF3E0, 0xFFFFB74D)
                ),
                "lago_limpo" to StoryNode(
                    id = "lago_limpo",
                    title = "Herói do Planeta! 🌊♻️",
                    text = "Os peixinhos deram saltos de alegria no lago limpo! O sapinho encontrou a cesta da Melina que tinha flutuado em segurança e entregou-nos com um sorriso gigante!",
                    mascotEmotion = MascotEmotion.PROUD,
                    speechAudio = "Boa! Limpaste o lago e salvo os peixinhos! O sapinho devolveu a cesta!",
                    choices = listOf(
                        StoryChoice("c9", "Organizar as frutas por cores com o Zé! 🔴🟡🔵", "🎨", "final_heroi_ecologico")
                    ),
                    bgGradientHexes = listOf(0xFFB2EBF2, 0xFF4DD0E1)
                ),
                "lago_direto" to StoryNode(
                    id = "lago_direto",
                    title = "O Conselho do Sapinho 🐸",
                    text = "O sapinho coaxou: 'Se limpares a nossa casa de água, eu digo-te onde está a cesta de frutas!'",
                    mascotEmotion = MascotEmotion.HAPPY,
                    speechAudio = "O sapinho precisa da nossa ajuda para limpar o lago!",
                    choices = listOf(
                        StoryChoice("c10", "Ajudar o sapinho a reciclar o plástico! 🌊", "🌊", "lago_limpo")
                    ),
                    bgGradientHexes = listOf(0xFFE0F2F1, 0xFF80CBC4)
                ),
                "final_festa_cores" to StoryNode(
                    id = "final_festa_cores",
                    title = "Piquenique das Cores! 🎨🎉",
                    text = "A Melina, o Papagaio e o Zé Traquina comeram morangos vermelhos, bananas amarelas e mirtilos azuis. Aprendeste que a gentileza torna tudo mágico!",
                    mascotEmotion = MascotEmotion.CELEBRATING,
                    speechAudio = "Parabéns! Completaste a história da Floresta Amiga e aprendeste sobre gentileza e cores!",
                    choices = emptyList(),
                    isEnding = true,
                    endingRewardStars = 15,
                    endingTitle = "Guardião da Gentileza ⭐",
                    endingMessage = "Parabéns! Descobriste que tratar os outros com respeito e gentileza abre todas as portas!",
                    bgGradientHexes = listOf(0xFFE8F5E9, 0xFFA5D6A7)
                ),
                "final_heroi_ecologico" to StoryNode(
                    id = "final_heroi_ecologico",
                    title = "Protetor do Planeta! 🌿⭐",
                    text = "Separaste o lixo, salvaste os animais do lago e organizaste as frutas por cores! O Zé Traquina entrega-te o distintivo de Super Herói da Natureza!",
                    mascotEmotion = MascotEmotion.PROUD,
                    speechAudio = "Incrível! És um verdadeiro Protetor do Planeta e Mestre das Cores!",
                    choices = emptyList(),
                    isEnding = true,
                    endingRewardStars = 15,
                    endingTitle = "Protetor da Natureza ♻️",
                    endingMessage = "Parabéns! Aprendeste a cuidar do meio ambiente, reciclar e identificar todas as cores!",
                    bgGradientHexes = listOf(0xFFB2DFDB, 0xFF80CBC4)
                )
            )
        ),

        InteractiveStory(
            id = "story_numeros_castelo",
            title = "O Castelo dos Números Mágicos",
            subtitle = "Conta de 1 a 5 e ajuda os amigos a chegar à festa no castelo!",
            theme = "Matemática, Contagem & Partilha 🔢🏰",
            coverEmoji = "🏰",
            accentColorHex = 0xFFFF8F00,
            cardBgHex = 0xFFFFF8E1,
            estimatedMinutes = "3-4 min",
            startNodeId = "start",
            nodes = mapOf(
                "start" to StoryNode(
                    id = "start",
                    title = "A Caminho do Castelo 🏰",
                    text = "A Ursa Pipa quer ir à festa no Castelo do Tigre Tomás, mas há um riacho no caminho. Precisamos de construir uma ponte com 5 troncos de madeira!",
                    mascotEmotion = MascotEmotion.HAPPY,
                    speechAudio = "Precisamos de 5 troncos para a ponte do castelo! Vamos contar juntos?",
                    choices = listOf(
                        StoryChoice("c1", "Contar 5 troncos com o Zé Traquina (1, 2, 3, 4, 5)! 🪵", "🪵", "ponte_construida", educationalValue = "Contar até 5 ⭐", starsReward = 5),
                        StoryChoice("c2", "Pedir ajuda ao Castor Engenheiro 🦫", "🦫", "ajuda_castor")
                    ),
                    bgGradientHexes = listOf(0xFFFFF8E1, 0xFFFFE082)
                ),
                "ponte_construida" to StoryNode(
                    id = "ponte_construida",
                    title = "Ponte Forte e Segura! 🌉",
                    text = "Colocámos 1, 2, 3, 4, 5 troncos! A ponte ficou super firme! Chegámos à porta do castelo, mas está trancada com um mistério: 'Qual é o número que vem a seguir ao 2?'",
                    mascotEmotion = MascotEmotion.EXCITED,
                    speechAudio = "A ponte está pronta! Qual é o número que vem a seguir ao número 2?",
                    choices = listOf(
                        StoryChoice("c3", "É o número 3! 3️⃣", "3️⃣", "porta_aberta", educationalValue = "Sequência Numérica 🔢", starsReward = 5),
                        StoryChoice("c4", "É o número 1! 1️⃣", "1️⃣", "dica_numero")
                    ),
                    bgGradientHexes = listOf(0xFFFFF3E0, 0xFFFFCC80)
                ),
                "ajuda_castor" to StoryNode(
                    id = "ajuda_castor",
                    title = "O Castor Contador 🦫",
                    text = "O Castor bate as palmas: 'Com prazer! Vamos pegar em 1, 2, 3, 4, 5 troncos bem fortes!'",
                    mascotEmotion = MascotEmotion.HAPPY,
                    speechAudio = "O Castor ajudou a contar os 5 troncos para a ponte!",
                    choices = listOf(
                        StoryChoice("c5", "Atravessar a ponte para o castelo! 🌉", "🌉", "ponte_construida")
                    ),
                    bgGradientHexes = listOf(0xFFEFEBE9, 0xFFD7CCC8)
                ),
                "dica_numero" to StoryNode(
                    id = "dica_numero",
                    title = "Dica do Zé Traquina 💡",
                    text = "O Zé sorri e faz uma contagem no ar com os dedos: '1... 2... e a seguir vem o três!'",
                    mascotEmotion = MascotEmotion.THINKING,
                    speechAudio = "Vamos contar juntos: um, dois, três!",
                    choices = listOf(
                        StoryChoice("c6", "Escolher a magia do número 3! 3️⃣", "3️⃣", "porta_aberta")
                    ),
                    bgGradientHexes = listOf(0xFFFFF8E1, 0xFFFFD54F)
                ),
                "porta_aberta" to StoryNode(
                    id = "porta_aberta",
                    title = "A Grande Festa do Bolo! 🍰🎉",
                    text = "A porta do castelo abre-se com luzes brilhantes! Na mesa há um bolo delicioso cortado em 4 fatias. Há 4 amigos à mesa (Pipa, Tomás, Zé e Tu). Como dividimos o bolo?",
                    mascotEmotion = MascotEmotion.CELEBRATING,
                    speechAudio = "Há 4 fatias de bolo para 4 amigos! Como dividimos?",
                    choices = listOf(
                        StoryChoice("c7", "Dar 1 fatia a cada amigo para todos ficarem felizes! 🍰", "🍰", "final_castelo_partilha", educationalValue = "Partilha & Igualdade ❤️", starsReward = 10)
                    ),
                    bgGradientHexes = listOf(0xFFFFEBEE, 0xFFFFCDD2)
                ),
                "final_castelo_partilha" to StoryNode(
                    id = "final_castelo_partilha",
                    title = "Rei da Contagem e Partilha! 👑🎂",
                    text = "Todos comeram 1 fatia de bolo, contaram até 5 e dançaram no castelo! Aprendeste que a matemática é divertida e partilhar traz muita alegria!",
                    mascotEmotion = MascotEmotion.PROUD,
                    speechAudio = "Parabéns! Sabes contar até 5 e partilhar com os teus amigos!",
                    choices = emptyList(),
                    isEnding = true,
                    endingRewardStars = 15,
                    endingTitle = "Mestre dos Números 🔢⭐",
                    endingMessage = "Fantástico! Sabes contar perfeitamente de 1 a 5 e descobriste que partilhar é o melhor ingrediente da amizade!",
                    bgGradientHexes = listOf(0xFFFFF8E1, 0xFFFFD54F)
                )
            )
        ),

        InteractiveStory(
            id = "story_emocoes_escola",
            title = "O Dia das Emoções na Escola",
            subtitle = "Aprende sobre empatia, abraços e gerir sentimentos com o Zé!",
            theme = "Inteligência Emocional & Afeto ❤️🎒",
            coverEmoji = "❤️",
            accentColorHex = 0xFFD81B60,
            cardBgHex = 0xFFFCE4EC,
            estimatedMinutes = "3-4 min",
            startNodeId = "start",
            nodes = mapOf(
                "start" to StoryNode(
                    id = "start",
                    title = "Manhã na Escola do Bosque 🎒",
                    text = "É o primeiro dia de aulas. O Coelho Simão está num cantinho com lágrima no olho porque está com saudades de casa. O que pode o Zé Traquina fazer?",
                    mascotEmotion = MascotEmotion.THINKING,
                    speechAudio = "O Coelho Simão está triste no cantinho da sala. Como podemos acolher o nosso amigo?",
                    choices = listOf(
                        StoryChoice("c1", "Oferecer um abraço quentinho e dizer 'Estou aqui contigo' 🤗", "🤗", "abracar_simao", educationalValue = "Empatia e Afeto ❤️", starsReward = 5),
                        StoryChoice("c2", "Convidar o Simão para jogar à bola colorida ⚽", "⚽", "jogar_simao")
                    ),
                    bgGradientHexes = listOf(0xFFFCE4EC, 0xFFF8BBD0)
                ),
                "abracar_simao" to StoryNode(
                    id = "abracar_simao",
                    title = "O Poder do Abraço 🤗✨",
                    text = "O Simão limpa a lágrima e dá um sorriso brilhante! 'Obrigado! O teu abraço fez o meu coração ficar quentinho!' Que atividade fazemos juntos agora?",
                    mascotEmotion = MascotEmotion.CELEBRATING,
                    speechAudio = "Um abraço amigo cura qualquer tristeza! O que queremos brincar juntos?",
                    choices = listOf(
                        StoryChoice("c3", "Construir uma torre alta de blocos de madeira 🧱", "🧱", "torre_blocos"),
                        StoryChoice("c4", "Fazer um desenho cheio de corações e soles 🎨", "🎨", "desenho_coracoes", educationalValue = "Expressão Artística 🎨", starsReward = 5)
                    ),
                    bgGradientHexes = listOf(0xFFFFF3E0, 0xFFFFE0B2)
                ),
                "jogar_simao" to StoryNode(
                    id = "jogar_simao",
                    title = "O Jogo da Bola ⚽",
                    text = "O Simão alinha no jogo! Mas sem querer a bola bateu numa torre de blocos que caiu no chão. O Simão ficou assustado pensando que ficavas zangado. O que dizes?",
                    mascotEmotion = MascotEmotion.THINKING,
                    speechAudio = "A torre caiu sem querer! O que dizes ao Simão?",
                    choices = listOf(
                        StoryChoice("c5", "Dizer com carinho: 'Não faz mal! Construímos outra ainda melhor!' 🧱", "🧱", "torre_blocos", educationalValue = "Resiliência ⭐", starsReward = 5),
                        StoryChoice("c6", "Fazer a respiração da borboleta para acalmar 🦋", "🦋", "respirar_borboleta")
                    ),
                    bgGradientHexes = listOf(0xFFE8EAF6, 0xFFC5CAE9)
                ),
                "respirar_borboleta" to StoryNode(
                    id = "respirar_borboleta",
                    title = "Respiração da Borboleta 🦋🧘",
                    text = "O Zé ensina: 'Cruza as mãos no peito, inspira o ar pelo nariz... 1, 2, 3... e expira suavemente!' O Simão acalmou-se e sorriu!",
                    mascotEmotion = MascotEmotion.PROUD,
                    speechAudio = "Inspirar e expirar acalma o coração e traz paz!",
                    choices = listOf(
                        StoryChoice("c7", "Agora vamos construir a torre mágica juntos! 🧱", "🧱", "torre_blocos")
                    ),
                    bgGradientHexes = listOf(0xFFE0F7FA, 0xFFB2EBF2)
                ),
                "torre_blocos" to StoryNode(
                    id = "torre_blocos",
                    title = "A Torre da Amizade 🏰✨",
                    text = "O Zé e o Simão colocaram cada bloco com cuidado. A torre ficou tão alta que parecia tocar no céu! O Professor Coruja deu a medalha da Amizade aos dois!",
                    mascotEmotion = MascotEmotion.PROUD,
                    speechAudio = "Trabalharam em equipa e construíram a maior torre da escola!",
                    choices = listOf(
                        StoryChoice("c8", "Celebrar com a Dança da Amizade! 🕺🎉", "🎉", "final_campeao_empatia")
                    ),
                    bgGradientHexes = listOf(0xFFE8F5E9, 0xFFC8E6C9)
                ),
                "desenho_coracoes" to StoryNode(
                    id = "desenho_coracoes",
                    title = "Arte do Coração 🎨❤️",
                    text = "Desenháram a escola, o Zé, a família e muitas estrelas douradas. O Simão guardou o desenho na mochila com muito orgulho!",
                    mascotEmotion = MascotEmotion.HAPPY,
                    speechAudio = "Desenhar ajuda a transformar sentimentos em arte bonita!",
                    choices = listOf(
                        StoryChoice("c9", "Mostrar o desenho a toda a turma com alegria! 🌟", "🌟", "final_campeao_empatia")
                    ),
                    bgGradientHexes = listOf(0xFFFFF8E1, 0xFFFFECB3)
                ),
                "final_campeao_empatia" to StoryNode(
                    id = "final_campeao_empatia",
                    title = "Campeão das Emoções! ❤️👑",
                    text = "Aprendeste a acolher um amigo, dar abraços, respirar fundo nas frustrações e espalhar alegria! A escola é um lugar mágico para crescer!",
                    mascotEmotion = MascotEmotion.CELEBRATING,
                    speechAudio = "Parabéns! És um verdadeiro Campeão da Empatia e das Emoções!",
                    choices = emptyList(),
                    isEnding = true,
                    endingRewardStars = 15,
                    endingTitle = "Embaixador do Afeto ❤️⭐",
                    endingMessage = "Parabéns! Descobriste o superpoder da empatia: cuidar dos sentimentos dos teus amigos torna o mundo um lugar muito mais feliz!",
                    bgGradientHexes = listOf(0xFFFCE4EC, 0xFFF8BBD0)
                )
            )
        )
    )
}

@Composable
fun InteractiveStoryScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val userProgress by viewModel.userProgress.collectAsState()
    var selectedStory by remember { mutableStateOf<InteractiveStory?>(null) }
    var currentNodeId by remember { mutableStateOf<String?>(null) }
    var userPath by remember { mutableStateOf<List<String>>(emptyList()) }
    var hasClaimedEndingStars by remember { mutableStateOf(false) }

    val currentStory = selectedStory
    val currentNode = if (currentStory != null && currentNodeId != null) {
        currentStory.nodes[currentNodeId]
    } else null

    LaunchedEffect(currentNodeId) {
        currentNode?.let { node ->
            viewModel.speak(node.speechAudio)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
    ) {
        // --- Top Bar ---
        Surface(
            color = Color.White,
            shadowElevation = 3.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = {
                            if (selectedStory != null) {
                                // Exit current story back to story list
                                selectedStory = null
                                currentNodeId = null
                                userPath = emptyList()
                                hasClaimedEndingStars = false
                                viewModel.speak("A voltar à biblioteca de histórias!")
                            } else {
                                onBack()
                            }
                        },
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color(0xFFF1F5F9), CircleShape)
                            .testTag("story_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color(0xFF0F172A),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = if (selectedStory != null) selectedStory!!.title else "Histórias Interativas 📖",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF0F172A)
                        )
                        Text(
                            text = if (selectedStory != null) selectedStory!!.theme else "Escolhe uma aventura e decide o caminho!",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = SunshineYellow,
                    border = BorderStroke(1.dp, KidStarOrange)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${userProgress.starsCount} ⭐",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.Black
                        )
                    }
                }
            }
        }

        // --- Content View ---
        if (selectedStory == null) {
            // --- STORY CATALOG LIST VIEW ---
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    // Header Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = SkyBluePrimary)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = SunshineYellow
                                ) {
                                    Text(
                                        text = "PRIMEIRA INFÂNCIA 🎒",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color.Black,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Histórias com Decisões! 🌟",
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                                Text(
                                    text = "Lê com o Zé Traquina, faz escolhas e descobre múltiplos finais educativos!",
                                    fontSize = 11.sp,
                                    color = Color.White.copy(alpha = 0.9f)
                                )
                            }

                            ZeTraquinaMascot(
                                emotion = MascotEmotion.EXCITED,
                                size = 65.dp,
                                showSpeechBubble = false,
                                onInteract = {
                                    viewModel.speak("Qual é a história interativa que queres ler hoje?")
                                }
                            )
                        }
                    }
                }

                item {
                    Text(
                        text = "Aventuras Disponíveis 📚",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF0F172A)
                    )
                }

                items(StoryRepository.stories) { story ->
                    Card(
                        onClick = {
                            selectedStory = story
                            currentNodeId = story.startNodeId
                            userPath = listOf(story.startNodeId)
                            hasClaimedEndingStars = false
                            viewModel.speak("Vamos começar a história ${story.title}!")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("story_card_${story.id}"),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(story.cardBgHex)),
                        border = BorderStroke(2.dp, Color(story.accentColorHex).copy(alpha = 0.7f)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = Color.White,
                                    shadowElevation = 2.dp,
                                    modifier = Modifier.size(46.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(text = story.coverEmoji, fontSize = 24.sp)
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(story.accentColorHex)
                                ) {
                                    Text(
                                        text = story.estimatedMinutes,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = story.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.Black
                            )

                            Text(
                                text = story.subtitle,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black.copy(alpha = 0.7f)
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color.White.copy(alpha = 0.8f)
                                ) {
                                    Text(
                                        text = story.theme,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(story.accentColorHex),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                    )
                                }

                                Button(
                                    onClick = {
                                        selectedStory = story
                                        currentNodeId = story.startNodeId
                                        userPath = listOf(story.startNodeId)
                                        hasClaimedEndingStars = false
                                        viewModel.speak("Vamos ler ${story.title}!")
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(story.accentColorHex)),
                                    shape = RoundedCornerShape(12.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("LER AGORA 🚀", fontSize = 11.sp, fontWeight = FontWeight.Black)
                                }
                            }
                        }
                    }
                }
            }
        } else if (currentNode != null) {
            // --- ACTIVE STORY NODE SCENE ---
            val bgGradient = remember(currentNode) {
                Brush.verticalGradient(
                    colors = currentNode.bgGradientHexes.map { Color(it) }
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(bgGradient)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Top Progress & Node Scene Header
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color.White.copy(alpha = 0.85f)
                            ) {
                                Text(
                                    text = "Passo ${userPath.size} de ~4 📖",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF0F172A),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }

                            IconButton(
                                onClick = {
                                    viewModel.speak(currentNode.speechAudio)
                                },
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(Color.White, CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                    contentDescription = "OuvirNarração",
                                    tint = Color(selectedStory!!.accentColorHex),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Mascot & Speech Dialog Panel
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(22.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    ZeTraquinaMascot(
                                        emotion = currentNode.mascotEmotion,
                                        size = 70.dp,
                                        showSpeechBubble = false,
                                        onInteract = {
                                            viewModel.speak(currentNode.speechAudio)
                                        }
                                    )

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = currentNode.title,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Black,
                                            color = Color(0xFF0F172A)
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = currentNode.text,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = Color(0xFF334155),
                                            lineHeight = 17.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Bottom Area: Choices or Story Ending Rewards
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                    ) {
                        if (!currentNode.isEnding) {
                            Text(
                                text = "O que fazemos agora? 🤔",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF0F172A),
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            currentNode.choices.forEach { choice ->
                                Card(
                                    onClick = {
                                        if (choice.starsReward > 0) {
                                            viewModel.addStars(choice.starsReward)
                                        }
                                        userPath = userPath + choice.nextNodeId
                                        currentNodeId = choice.nextNodeId
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .testTag("choice_btn_${choice.id}"),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                    border = BorderStroke(2.dp, Color(selectedStory!!.accentColorHex).copy(alpha = 0.5f)),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(text = choice.emoji, fontSize = 20.sp)
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Column {
                                                Text(
                                                    text = choice.text,
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF0F172A)
                                                )
                                                if (choice.educationalValue != null) {
                                                    Text(
                                                        text = choice.educationalValue,
                                                        fontSize = 10.sp,
                                                        fontWeight = FontWeight.ExtraBold,
                                                        color = Color(selectedStory!!.accentColorHex)
                                                    )
                                                }
                                            }
                                        }

                                        if (choice.starsReward > 0) {
                                            Surface(
                                                shape = RoundedCornerShape(8.dp),
                                                color = SunshineYellow
                                            ) {
                                                Text(
                                                    text = "+${choice.starsReward} ⭐",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Black,
                                                    color = Color.Black,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            // ENDING CELEBRATION CARD
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(22.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = null,
                                        tint = SunshineYellow,
                                        modifier = Modifier.size(36.dp)
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = currentNode.endingTitle ?: "História Concluída! 🎉",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color(0xFF0F172A)
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = currentNode.endingMessage ?: "Fizeste ótimas escolhas e aprendeste uma valiosa lição!",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.Gray,
                                        textAlign = TextAlign.Center
                                    )

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = SunshineYellow,
                                        border = BorderStroke(1.5.dp, KidStarOrange)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "Recompensa Final: +${currentNode.endingRewardStars} Estrelas Mágicas! ⭐",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Black,
                                                color = Color.Black
                                            )
                                        }
                                    }

                                    LaunchedEffect(Unit) {
                                        if (!hasClaimedEndingStars) {
                                            hasClaimedEndingStars = true
                                            viewModel.addStars(currentNode.endingRewardStars)
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(14.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Button(
                                            onClick = {
                                                currentNodeId = selectedStory!!.startNodeId
                                                userPath = listOf(selectedStory!!.startNodeId)
                                                hasClaimedEndingStars = false
                                                viewModel.speak("Vamos ler de novo!")
                                            },
                                            modifier = Modifier.weight(1f),
                                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF1F5F9)),
                                            shape = RoundedCornerShape(14.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Refresh,
                                                contentDescription = null,
                                                tint = Color.Black,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Repetir 🔄", fontSize = 11.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                                        }

                                        Button(
                                            onClick = {
                                                selectedStory = null
                                                currentNodeId = null
                                                userPath = emptyList()
                                                hasClaimedEndingStars = false
                                                viewModel.speak("Escolhe outra história!")
                                            },
                                            modifier = Modifier.weight(1f),
                                            colors = ButtonDefaults.buttonColors(containerColor = Color(selectedStory!!.accentColorHex)),
                                            shape = RoundedCornerShape(14.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Book,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Outras Histórias 📖", fontSize = 11.sp, color = Color.White, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
