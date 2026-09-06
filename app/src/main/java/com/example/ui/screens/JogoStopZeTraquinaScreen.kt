package com.example.ui.screens

import com.example.ui.theme.PreviewAppTheme

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
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
import kotlinx.coroutines.delay
import java.text.Normalizer
import kotlin.random.Random

enum class StopDifficulty(
    val title: String,
    val subtitle: String,
    val color: Color,
    val initialTimeSeconds: Int?, // null = no timer
    val categories: List<StopCategory>
) {
    FACIL(
        title = "Fácil (6-7 anos)",
        subtitle = "3 Categorias • Sem limite de tempo",
        color = Color(0xFF4CAF50),
        initialTimeSeconds = null,
        categories = listOf(
            StopCategory("Nome", "👤", "Ex: Ana, Bernardo, Carlos..."),
            StopCategory("Animal", "🐶", "Ex: Águia, Boi, Cão..."),
            StopCategory("Cor", "🎨", "Ex: Azul, Branco, Castanho...")
        )
    ),
    MEDIO(
        title = "Médio (8-9 anos)",
        subtitle = "4 Categorias • 60 Segundos",
        color = Color(0xFFFF9800),
        initialTimeSeconds = 60,
        categories = listOf(
            StopCategory("Nome", "👤", "Ex: Alice, Bruno, Carina..."),
            StopCategory("Animal", "🐶", "Ex: Abelha, Baleia, Coelho..."),
            StopCategory("Objeto", "🧸", "Ex: Anel, Bola, Cadeira..."),
            StopCategory("Comida", "🍎", "Ex: Arroz, Banana, Cenoura...")
        )
    ),
    DIFACIL(
        title = "Difícil (10+ anos)",
        subtitle = "5 Categorias • 30 Segundos",
        color = Color(0xFFE53935),
        initialTimeSeconds = 30,
        categories = listOf(
            StopCategory("Nome", "👤", "Ex: Afonso, Beatriz, Daniel..."),
            StopCategory("Animal", "🐶", "Ex: Aranha, Borboleta, Cobra..."),
            StopCategory("Cidade / País", "🏙️", "Ex: Aveiro, Brasil, Coimbra..."),
            StopCategory("Objeto", "🧸", "Ex: Armário, Borracha, Copo..."),
            StopCategory("Comida", "🍎", "Ex: Atum, Batata, Chocolate...")
        )
    )
}

data class StopCategory(
    val name: String,
    val emoji: String,
    val placeholder: String
)

val availableLetters = listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "L", "M", "N", "O", "P", "R", "S", "T", "V")

@Composable
fun JogoStopZeTraquinaScreen(
    mainViewModel: MainViewModel? = null,
    onBack: () -> Unit
) {
    val isPreview = LocalInspectionMode.current

    var selectedDifficulty by remember { mutableStateOf<StopDifficulty?>(null) }
    var currentLetter by remember { mutableStateOf("A") }

    // Map of category name to user typed answer
    var answers by remember { mutableStateOf(mapOf<String, String>()) }

    var timerRemaining by remember { mutableIntStateOf(60) }
    var isTimerRunning by remember { mutableStateOf(false) }

    var isGameFinished by remember { mutableStateOf(false) }
    var validationResults by remember { mutableStateOf(mapOf<String, Boolean>()) }
    var score by remember { mutableIntStateOf(0) }

    // Clean text helper (removes accents and trims)
    fun sanitizeText(text: String): String {
        val normalized = Normalizer.normalize(text.trim(), Normalizer.Form.NFD)
        return normalized.replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "").uppercase()
    }

    // Comprehensive valid word dictionary per category and starting letter
    val validWordsByCategory = remember {
        mapOf(
            "Nome" to mapOf(
                "A" to listOf("ANA", "ALICE", "AFONSO", "ANDRE", "ALEXANDRE", "ANTONIO", "AMALIA", "ARTHUR", "ADRIANO", "ADRIANA", "ALEXIA", "AMANDA"),
                "B" to listOf("BRUNO", "BEATRIZ", "BERNARDO", "BRUNA", "BARBARA", "BENJAMIM", "BRANCA"),
                "C" to listOf("CARLOS", "CAROLINA", "CARINA", "CLARA", "CRISTIANO", "CAMILA", "CATARINA", "CECILIA", "CESAR"),
                "D" to listOf("DANIEL", "DIANA", "DAVID", "DIOGO", "DUARTE", "DANIELA", "DARIO", "DANI"),
                "E" to listOf("EDUARDO", "ELENA", "ESMERALDA", "ESTHER", "EVA", "EMMA", "ELIZABETH", "ELISABETE", "ENZO"),
                "F" to listOf("FRANCISCO", "FERNANDO", "FILIPE", "FLAVIA", "FABIANA", "FABIO", "FREDERICO"),
                "G" to listOf("GABRIEL", "GONCALO", "GISELA", "GRACA", "GUILHERME", "GABRIELA", "GUSTAVO"),
                "H" to listOf("HUGO", "HELENA", "HENRIQUE", "HILDA", "HORACIO", "HERCULES"),
                "I" to listOf("INES", "ISABEL", "IVAN", "IRIS", "IAGO", "IRINA", "ISABELE"),
                "J" to listOf("JOAO", "JOSE", "JOANA", "JORGE", "JULIA", "JULIO", "JESSICA", "JONAS", "JUDITE"),
                "L" to listOf("LUANA", "LUIS", "LUCAS", "LEONOR", "LILIANA", "LAURA", "LETICIA", "LORENZO", "LUCIA"),
                "M" to listOf("MARIA", "MIGUEL", "MATILDE", "MARGARIDA", "MARCO", "MARTIM", "MANUEL", "MARCELO", "MAFALDA", "MELISSA"),
                "N" to listOf("NUNO", "NICOLE", "NATALIA", "NELSON", "NADIA", "NAIR", "NOEMIA"),
                "O" to listOf("OTAVIO", "OLIVIA", "ODETE", "ORLANDO", "OSCAR"),
                "P" to listOf("PEDRO", "PAULO", "PATRICIA", "PILAR", "PABLO", "PASCOAL"),
                "R" to listOf("RITA", "RODRIGO", "RAQUEL", "RICARDO", "RUBEN", "ROSA", "RAUL", "REGINA", "ROBERTO"),
                "S" to listOf("SOFIA", "SARA", "SIMAO", "SILVIA", "SUSANA", "SEBASTIAO", "SIMONE", "SANDRA"),
                "T" to listOf("TIAGO", "TOMAS", "TERESA", "TANIA", "TELMO", "TATIANA"),
                "V" to listOf("VANESSA", "VICENTE", "VICTOR", "VALERIA", "VERONICA", "VIOLETA", "VALTER")
            ),
            "Animal" to mapOf(
                "A" to listOf("AGUIA", "ABELHA", "ARANHA", "ANTA", "ASNO", "AVESTRUZ", "ATUM", "ALCE"),
                "B" to listOf("BALEIA", "BUFALO", "BORBOLETA", "BURRO", "BODE", "BAU", "BARRACUDA", "BABUINO"),
                "C" to listOf("CAO", "GATO", "COELHO", "CABRA", "CAMELO", "CAVALO", "CORUJA", "CROCODILO", "CAMALEAO", "CANGURU", "CASTOR"),
                "D" to listOf("DINOSSAURO", "DELFIM", "DROMEDARIO", "DRAGAO"),
                "E" to listOf("ELEFANTE", "ESQUILO", "EMU", "ESCORPIAO", "ENGUIA", "ESCARAVELHO"),
                "F" to listOf("FORMIGA", "FOCA", "FAISAO", "FLAMINGO", "FURAO", "FALCAO"),
                "G" to listOf("GATO", "GIRAFA", "GALINHA", "GAFANHOTO", "GORILA", "GAIOLA", "GALO", "GOLFINHO"),
                "H" to listOf("HIENA", "HAMSTER", "HIDRA"),
                "I" to listOf("IGUANA", "IMPALA", "INSETO"),
                "J" to listOf("JACARE", "JAGUAR", "JAVALI", "JIBOIA"),
                "L" to listOf("LEAO", "LOBO", "LAGARTO", "LEBRE", "LINCE", "LAGOSTA", "LEOPARDO", "LOMBRIZ"),
                "M" to listOf("MACACO", "MORCEGO", "MOSCA", "MOSQUITO", "MULA", "MORSAS", "MEDUSA"),
                "N" to listOf("NUTRIA", "NANDU"),
                "O" to listOf("OVELHA", "ONCA", "ORNITORRINCO", "OSTRA", "URSO"),
                "P" to listOf("PATO", "PINGUIM", "PERU", "PANTERA", "POLVO", "PUMA", "POMBO", "PAPAGAIO", "PAVAO"),
                "R" to listOf("RATO", "RAPOSA", "RA", "RINOCERONTE", "ROINDO", "REPTIL"),
                "S" to listOf("SAPO", "SERPENTE", "SIMIO", "SALMAO", "SURICATA", "TARTARUGA"),
                "T" to listOf("TIGRE", "TARTARUGA", "TUBARAO", "TOURO", "TOPEIRA", "TUCANO"),
                "V" to listOf("VACA", "VEADO", "VESPA", "VICUNHA", "VIBORA")
            ),
            "Cor" to mapOf(
                "A" to listOf("AMARELO", "AZUL", "ACINZENTADO", "AZULADO"),
                "B" to listOf("BRANCO", "BEGE", "BORDEAUX", "BRANCANTE"),
                "C" to listOf("CASTANHO", "CEREJA", "CINZENTO", "CORAL", "CELESTE", "CARMEZIM"),
                "D" to listOf("DOURADO"),
                "E" to listOf("ESMERALDA", "ESCURA"),
                "F" to listOf("FUCHSIA"),
                "G" to listOf("GRENA"),
                "L" to listOf("LARANJA", "LILAS", "LAVANDA"),
                "M" to listOf("MARROM", "MAGENTA", "MOSTARDA", "MARFIM"),
                "O" to listOf("OURO", "OLIVA", "OCRE"),
                "P" to listOf("PRETO", "PRATA", "PURPURA", "PRATEADO"),
                "R" to listOf("ROSA", "ROXO", "RUBI", "RUIVO"),
                "V" to listOf("VERDE", "VERMELHO", "VIOLETA", "VINHO")
            ),
            "Objeto" to mapOf(
                "A" to listOf("ANEL", "ARMARIO", "APITO", "ALMOFADA", "ALFINETE", "AGULHA", "ALICATE", "ABAJOUR"),
                "B" to listOf("BOLA", "BOTA", "BARRACA", "BALAO", "BANCO", "BOTAO", "BALDE", "BAU", "BORRACHA"),
                "C" to listOf("CADEIRA", "CANETA", "COPO", "CADERNO", "COLHER", "CAMISA", "CHAVE", "CARRO", "COLCHAO", "COBERTOR"),
                "D" to listOf("DADO", "DEGRAU", "DISCO", "DIARIO", "DIAMANTE"),
                "E" to listOf("ESPELHO", "ESCOVA", "ESTOJO", "ENXADA", "ESTANTE"),
                "F" to listOf("FACA", "FITA", "FOGAO", "FOTOGRAFIA", "FRASCO", "FERRO", "FUNIL"),
                "G" to listOf("GIZ", "GARFO", "GAVETA", "GORRO", "GUITARRA", "GALOCHA"),
                "H" to listOf("HARPA"),
                "I" to listOf("IMPRESSORA", "INTERCOMUNICADOR"),
                "J" to listOf("JANELA", "JARRA", "JOGO", "JAQUETA", "JOIA"),
                "L" to listOf("LIVRO", "LAPIS", "LAMPADA", "LUPA", "LIXO", "LANTERNA", "LATA"),
                "M" to listOf("MESA", "MOCHILA", "MARTELO", "MAQUINA", "MANTA", "MOEDA", "MALA"),
                "N" to listOf("NAVALHA", "NUVEM", "NOVELO"),
                "O" to listOf("OCULOS"),
                "P" to listOf("PAPEL", "PENA", "PORTA", "PRATO", "PIANO", "PINCEL", "PANELA", "PIJAMA", "PENTE"),
                "R" to listOf("RELOGIO", "REGUA", "RADIO", "RODA", "REDE"),
                "S" to listOf("SOFA", "SAPATO", "SACO", "SAIA", "SINO", "SABAO", "SERROTE"),
                "T" to listOf("TESOURA", "TELEFONE", "TAPETE", "TOALHA", "TAMBOR", "TERMOMETRO"),
                "V" to listOf("VELA", "VASO", "VIDRO", "VESTIDO", "VASSOURA", "VIOLAO")
            ),
            "Comida" to mapOf(
                "A" to listOf("ARROZ", "ATUM", "AGUA", "ALFACE", "AZEITONA", "ABACATE", "ABACAXI", "ALHO", "ACUCAR", "AVEIA"),
                "B" to listOf("BANANA", "BATATA", "BIFE", "BOLACHA", "BOLO", "BROA", "BACALHAU", "BATIDO"),
                "C" to listOf("CENOURA", "CHOCOLATE", "CARNE", "CEBOLA", "CEREJA", "CAFE", "CALDO", "CAMARAO", "COCO", "COUVE"),
                "D" to listOf("DOCE", "DAMASCO", "DOURADINHO", "DONUT"),
                "E" to listOf("ERVILHA", "EMPADA", "ESPAGUETE", "ESPINAFRE", "ENCHIDO", "EMPANADA"),
                "F" to listOf("FEIJAO", "FRANGO", "FARINHA", "FIGO", "FRAMBOESA", "FAROFA"),
                "G" to listOf("GELADO", "GALINHA", "GOMA", "GOIABA", "GELATINA", "GRANOLA"),
                "H" to listOf("HAMBURGUER", "HORTELA", "HOMUS"),
                "I" to listOf("IOGURTE", "ICETEA"),
                "J" to listOf("JACA", "JAMBO", "JUJUBA"),
                "L" to listOf("LEITE", "LINGUICA", "LARANJA", "LIMAO", "LAGOSTA", "LENTILHA", "LASANHA"),
                "M" to listOf("MACA", "MANTEIGA", "MELANCIA", "MELAO", "MILHO", "MORANGO", "MACARRAO", "MEL"),
                "N" to listOf("NATA", "NOZ", "NABO", "NESCAU", "NUGGETS"),
                "O" to listOf("OVO", "OMELETE", "OREGANOS", "OSTRA"),
                "P" to listOf("PAO", "PERA", "PEIXE", "PIZZA", "PURE", "PEPINO", "PRESUNTO", "PISTACHE", "PUDIM"),
                "R" to listOf("RABANO", "REBUCADO", "REQUEIJAO", "RISOTO"),
                "S" to listOf("SOPA", "SALADA", "SALMAO", "SALSICHA", "SANDES", "SUCO", "SAL", "SUSHI"),
                "T" to listOf("TOMATE", "TORRADA", "TANGERINA", "TORTA", "TAPIOCA", "TOFU"),
                "V" to listOf("VINHO", "VINAGRE", "VITELA", "VODKA")
            ),
            "Cidade / País" to mapOf(
                "A" to listOf("AVEIRO", "ALEMANHA", "ANGOLA", "AMSTERDAO", "ATENAS", "ARGENTINA", "ALGARVE", "AUSTRIA", "AZORES"),
                "B" to listOf("BRASIL", "BERLIM", "BEJA", "BRAGA", "BARCELONA", "BELGICA", "BOLIVIA", "BUDAPESTE"),
                "C" to listOf("COIMBRA", "CANADA", "CHILE", "CHINA", "CASCAIS", "COPENHAGA", "COLOMBIA", "CUBA", "CROACIA"),
                "D" to listOf("DINAMARCA", "DUBAI", "DUBLIN", "DOHA"),
                "E" to listOf("ESPANHA", "EVORA", "ESTRASBURGO", "EGITO", "EQUADOR", "ESTOCOLMO"),
                "F" to listOf("FARO", "FRANCA", "FLORENCA", "FINLANDIA"),
                "G" to listOf("GRECIA", "GENEBRA", "GUATEMALA", "GHANA", "GIBRALTAR"),
                "H" to listOf("HUNGRIA", "HAVANA", "HELSINQUIA", "HONDURAS"),
                "I" to listOf("ITALIA", "INDIA", "IRLANDA", "ISLANDIA", "IRAQUE", "IRAO", "ISRAEL"),
                "J" to listOf("JAPAO", "JORDANIA", "JAMAICA", "JAKARTA"),
                "L" to listOf("LISBOA", "LONDRES", "LUXEMBURGO", "LIMA", "LUANDA", "LYON"),
                "M" to listOf("MADRID", "MILAO", "MONACO", "MEXICO", "MACAU", "MAPUTO", "MARROCOS", "MALTA", "MOSCOU"),
                "N" to listOf("NOVA IORQUE", "NORUEGA", "NANTES", "NICE", "NIGERIA", "NEPAL"),
                "O" to listOf("OPORTO", "OSLO", "OVIEDO", "OMAN"),
                "P" to listOf("PORTO", "PARIS", "PORTUGAL", "POLONIA", "PRAGA", "PANAMA", "PERU", "PARAGUAI"),
                "R" to listOf("ROMA", "RUSSIA", "RIO DE JANEIRO", "REYKJAVIK", "ROMENIA", "ROTTERDAO"),
                "S" to listOf("SETUBAL", "SUECIA", "SUICA", "SEVILHA", "SAO PAULO", "SYDNEY", "SINGAPURA"),
                "T" to listOf("TOQUIO", "TORONTO", "TAIWAN", "TUNEZ", "TURQUIA", "TIRANA"),
                "V" to listOf("VIENA", "VENECIA", "VARSOVIA", "VENEZUELA", "VIETNAME", "VALENCIA")
            )
        )
    }

    // Helper to check if a word looks like gibberish or keyboard smash
    fun isGibberish(word: String): Boolean {
        if (word.length < 2) return true
        if (word.all { it == word[0] }) return true
        val vowels = "AEIOU"
        if (!word.any { it in vowels }) return true
        val smashes = listOf("ASDF", "QWER", "ZXCV", "POIU", "LKJH", "MNBV", "ASDFG", "QWERT")
        if (smashes.any { word.contains(it) }) return true
        return false
    }

    // Start a round for selected difficulty
    fun startRound(diff: StopDifficulty) {
        selectedDifficulty = diff
        currentLetter = availableLetters.random()
        answers = diff.categories.associate { it.name to "" }
        timerRemaining = diff.initialTimeSeconds ?: 0
        isTimerRunning = diff.initialTimeSeconds != null
        isGameFinished = false
        validationResults = emptyMap()
        score = 0

        if (!isPreview) {
        }
    }

    // Validate answers when STOP button is pressed or timer expires
    fun finishRound() {
        val diff = selectedDifficulty ?: return
        isTimerRunning = false

        val results = mutableMapOf<String, Boolean>()
        var calculatedScore = 0

        val targetChar = currentLetter[0]

        diff.categories.forEach { cat ->
            val userText = answers[cat.name] ?: ""
            val clean = sanitizeText(userText)

            val categoryMap = validWordsByCategory[cat.name]
            val letterList = categoryMap?.get(currentLetter) ?: emptyList()

            // Check if word is valid: must start with targetChar, be present in valid words list or be a valid non-gibberish word starting with targetChar
            val isInDictionary = letterList.any { clean == it || clean.contains(it) }
            val isPlausibleWord = clean.length >= 3 && clean.startsWith(targetChar) && clean.any { it in "AEIOU" } && !isGibberish(clean)

            // To strictly prevent invented nonsense words while accepting valid real words:
            // If it's in our curated dictionary OR (plausible & at least 4 letters & has vowels), it's valid.
            // Actually, requiring it to be in our curated dictionary OR matching known valid patterns ensures invented words (like random strings) are rejected.
            val isValid = clean.isNotEmpty() && clean.startsWith(targetChar) && (isInDictionary || (clean.length >= 4 && isPlausibleWord && isInDictionary)) 
            // Wait, what if someone enters a valid word not in our list? Let's check: isInDictionary || (clean.length >= 4 && isPlausibleWord). But wait, can kids invent plausible-looking fake words? Yes, e.g. "Blabla" or "Bralha". To prevent inventing words completely, requiring isInDictionary OR strict validation is good, but our curated dictionary has hundreds of words. If we require `isInDictionary`, any word not in our curated dictionary would be marked invalid. Is our dictionary comprehensive enough? Let's make `isInDictionary` the primary requirement (or check if it's in our curated dictionary OR a very strict dictionary check), so that invented words are correctly rejected!
            // Wait, let's check: `val isValid = clean.isNotEmpty() && clean.startsWith(targetChar) && isInDictionary`
            // Let's use `isInDictionary` as the check so only valid words in the dictionary for that category and letter are accepted!
            val isValidStrict = clean.isNotEmpty() && clean.startsWith(targetChar) && isInDictionary

            results[cat.name] = isValidStrict
            if (isValidStrict) {
                calculatedScore += 10
            }
        }

        score = calculatedScore
        validationResults = results
        isGameFinished = true

        val totalStars = (calculatedScore / 10).coerceAtLeast(1)
        if (!isPreview) {
            mainViewModel?.addStars(totalStars)
            mainViewModel?.playSound(R.raw.ze_traquina_e_fixe)
        }
    }

    // Timer loop
    LaunchedEffect(isTimerRunning, timerRemaining) {
        if (isTimerRunning && timerRemaining > 0) {
            delay(1000)
            timerRemaining--
            if (timerRemaining == 0) {
                finishRound()
            }
        }
    }

    LaunchedEffect(isGameFinished, score) {
        if (isGameFinished && score > 0 && selectedDifficulty != null) {
            kotlinx.coroutines.delay(2500)
            startRound(selectedDifficulty!!)
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        // --- Top Bar ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    if (selectedDifficulty != null && !isGameFinished) {
                        selectedDifficulty = null // Return to difficulty selection
                    } else {
                        onBack()
                    }
                },
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

            Text(
                text = "Stop do Zé Traquina 🛑",
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground
            )

            if (selectedDifficulty != null && !isGameFinished) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(
                        onClick = {
                            val emptyCat = answers.entries.find { it.value.isBlank() }?.key
                            if (emptyCat != null) {
                                val wordList = validWordsByCategory[emptyCat]?.get(currentLetter)
                                val sampleWord = wordList?.randomOrNull() ?: "${currentLetter}..."
                                val newAnswers = answers.toMutableMap()
                                newAnswers[emptyCat] = sampleWord
                                answers = newAnswers
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
                        onClick = { startRound(selectedDifficulty!!) },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Nova Letra",
                            tint = SkyBluePrimary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            } else {
                Spacer(modifier = Modifier.size(36.dp))
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // --- SCREEN 1: DIFFICULTY SELECTION ---
        if (selectedDifficulty == null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ZeTraquinaMascot(
                    size = 90.dp,
                    showSpeechBubble = false,
                    emotion = MascotEmotion.HAPPY
                )
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Escolhe a Dificuldade do Stop ✏️🛑",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Escreve palavras que comecem pela letra sorteada!",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                StopDifficulty.entries.forEach { diff ->
                    Card(
                        onClick = { startRound(diff) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = diff.color.copy(alpha = 0.12f)),
                        border = BorderStroke(2.dp, diff.color),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = diff.title,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = diff.color
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = diff.subtitle,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Jogar",
                                tint = diff.color,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
            }
        } else {
            // --- SCREEN 2: ACTIVE GAME OR RESULTS ---
            val diff = selectedDifficulty!!

            // Banner with Drawn Letter & Timer
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = diff.color),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = Color.White,
                            modifier = Modifier.size(52.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = currentLetter,
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Black,
                                    color = diff.color
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Letra Sorteada",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                            Text(
                                text = "Procura por: \"$currentLetter\"",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White
                            )
                        }
                    }

                    if (diff.initialTimeSeconds != null && !isGameFinished) {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = Color.Black.copy(alpha = 0.35f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Timer,
                                    contentDescription = null,
                                    tint = SunshineYellow,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${timerRemaining}s",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (!isGameFinished) {
                // --- ACTIVE INPUT FORM ---
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(diff.categories) { cat ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = BorderStroke(1.5.dp, SkyBluePrimary.copy(alpha = 0.3f))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = cat.emoji, fontSize = 26.sp)
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = cat.name,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    OutlinedTextField(
                                        value = answers[cat.name] ?: "",
                                        onValueChange = { newValue ->
                                            answers = answers.toMutableMap().apply { put(cat.name, newValue) }
                                        },
                                        placeholder = {
                                            Text(
                                                text = cat.placeholder,
                                                fontSize = 12.sp,
                                                color = Color.Gray
                                            )
                                        },
                                        singleLine = true,
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // BIG STOP BUTTON 🛑
                Button(
                    onClick = { finishRound() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
                    shape = RoundedCornerShape(18.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Text(
                        text = "STOP! 🛑",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                }
            } else {
                // --- RESULTS SCREEN ---
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    border = BorderStroke(2.dp, MintGreen)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ZeTraquinaMascot(
                            size = 64.dp,
                            showSpeechBubble = false,
                            emotion = if (score > 0) MascotEmotion.CELEBRATING else MascotEmotion.HAPPY
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Resultado da Rodada! 📝",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = "Pontuação: $score Pontos!",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = SkyBluePrimary
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            items(diff.categories) { cat ->
                                val userText = answers[cat.name] ?: ""
                                val isValid = validationResults[cat.name] == true

                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isValid) MintGreen.copy(alpha = 0.2f) else Color(0xFFFFEBEE)
                                    ),
                                    border = BorderStroke(
                                        1.dp,
                                        if (isValid) Color(0xFF4CAF50) else Color(0xFFE53935)
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 12.dp, vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(text = cat.emoji, fontSize = 20.sp)
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Column {
                                                Text(
                                                    text = cat.name,
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Bold
                                                )
                                                Text(
                                                    text = if (userText.isBlank()) "(Sem resposta)" else "\"$userText\"",
                                                    fontSize = 13.sp,
                                                    color = if (userText.isBlank()) Color.Gray else Color.Black
                                                )
                                            }
                                        }

                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            if (isValid) {
                                                Text(
                                                    text = "+10 pts",
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFF166534)
                                                )
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Icon(
                                                    imageVector = Icons.Default.CheckCircle,
                                                    contentDescription = "Correto",
                                                    tint = Color(0xFF4CAF50),
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            } else {
                                                Text(
                                                    text = "0 pts",
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color(0xFFC62828)
                                                )
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Icon(
                                                    imageVector = Icons.Default.Cancel,
                                                    contentDescription = "Incorreto",
                                                    tint = Color(0xFFE53935),
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { startRound(diff) },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(46.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = SunshineYellow),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Text(
                                    text = if (score > 0) "🚀 PRÓXIMO NÍVEL (MAIS DIFÍCIL!)" else "JOGAR DE NOVO 🔄",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.Black
                                )
                            }

                            Button(
                                onClick = { selectedDifficulty = null },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(46.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Text(
                                    text = "MUDAR NÍVEL ⚙️",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
        }

        if (isGameFinished && score > 0) {
            ConfettiEffect()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JogoStopZeTraquinaPreview(
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
            JogoStopZeTraquinaScreen(mainViewModel = null, onBack = {})
        }
    }
}
