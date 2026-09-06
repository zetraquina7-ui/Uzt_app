package com.example.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ui.components.FundoApp
import com.example.viewmodel.MainViewModel
import org.json.JSONArray
import java.text.NumberFormat
import java.util.Locale

data class CountryData(
    val codigo: String,
    val name: String,
    val flag: String,
    val capital: String,
    val populacao: Long,
    val continent: String,
    val lingua: String,
    val moeda: String,
    val fact: String,
    val color: Long
)

data class ContinentInfo(
    val name: String,
    val emoji: String,
    val description: String,
    val color: Color
)

fun flagEmojiFromCode(countryCode: String): String {
    val code = countryCode.uppercase(Locale.US)
    return try {
        val firstChar = Character.codePointAt(code, 0) - 0x41 + 0x1F1E6
        val secondChar = Character.codePointAt(code, 1) - 0x41 + 0x1F1E6
        String(Character.toChars(firstChar)) + String(Character.toChars(secondChar))
    } catch (e: Exception) {
        "🏳️"
    }
}

fun formatPopulationPt(pop: Long): String {
    return when {
        pop >= 1_000_000_000 -> {
            val num = pop / 1_000_000_000.0
            String.format(java.util.Locale.forLanguageTag("pt-PT"), "%.2f mil milhões", num)
        }
        pop >= 1_000_000 -> {
            val num = pop / 1_000_000.0
            String.format(java.util.Locale.forLanguageTag("pt-PT"), "%.1f milhões", num)
        }
        pop >= 1_000 -> {
            val num = pop / 1_000.0
            String.format(java.util.Locale.forLanguageTag("pt-PT"), "%.0f mil", num)
        }
        else -> {
            NumberFormat.getInstance(java.util.Locale.forLanguageTag("pt-PT")).format(pop)
        }
    }
}

@Composable
fun WorldMapScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    // Load full 51 countries from JSON asset or parsed list
    val countries = remember {
        listOf(
            // Europa
            CountryData("pt", "Portugal", "🇵🇹", "Lisboa", 10300000L, "Europa", "Português", "Euro", "Portugal tem a livraria mais antiga do mundo e o famoso galo de Barcelos!", 0xFF388E3C),
            CountryData("es", "Espanha", "🇪🇸", "Madrid", 47500000L, "Europa", "Espanhol", "Euro", "A Espanha é o maior produtor de azeite e terra do flamenco!", 0xFFFDD835),
            CountryData("fr", "França", "🇫🇷", "Paris", 68000000L, "Europa", "Francês", "Euro", "A França tem a famosa Torre Eiffel e o Museu do Louvre!", 0xFF1E88E5),
            CountryData("it", "Itália", "🇮🇹", "Roma", 59000000L, "Europa", "Italiano", "Euro", "A Itália tem a Torre de Pisa, o Coliseu e a melhor pizza do mundo!", 0xFF4CAF50),
            CountryData("de", "Alemanha", "🇩🇪", "Berlim", 84000000L, "Europa", "Alemão", "Euro", "A Alemanha tem castelos de contos de fadas e a Floresta Negra!", 0xFFFFB300),
            CountryData("gb", "Reino Unido", "🇬🇧", "Londres", 68000000L, "Europa", "Inglês", "Libra Esterlina", "O Reino Unido tem o relógio Big Ben e o Palácio de Buckingham!", 0xFF3F51B5),
            CountryData("nl", "Países Baixos", "🇳🇱", "Amesterdão", 17800000L, "Europa", "Neerlandês", "Euro", "Os Países Baixos são famosos pelos moinhos de vento e campos de túlipas!", 0xFFE64A19),
            CountryData("be", "Bélgica", "🇧🇪", "Bruxelas", 11700000L, "Europa", "Neerlandês, Francês, Alemão", "Euro", "A Bélgica faz os chocolates e waffles mais deliciosos da Europa!", 0xFF795548),
            CountryData("ch", "Suíça", "🇨🇭", "Berna", 8800000L, "Europa", "Alemão, Francês, Italiano", "Franco Suíço", "A Suíça tem montanhas com neve eterna nos Alpes e relógios precisos!", 0xFFD32F2F),
            CountryData("at", "Áustria", "🇦🇹", "Viena", 9100000L, "Europa", "Alemão", "Euro", "A Áustria é o lar histórico da música clássica e de Mozart!", 0xFFB71C1C),
            CountryData("ie", "Irlanda", "🇮🇪", "Dublin", 5100000L, "Europa", "Inglês, Irlandês", "Euro", "A Irlanda é conhecida como a Ilha Esmeralda pelas suas colinas verdes!", 0xFF2E7D32),
            CountryData("se", "Suécia", "🇸🇪", "Estocolmo", 10500000L, "Europa", "Sueco", "Coroa Sueca", "A Suécia tem milhares de ilhas e o hotel feito inteiramente de gelo!", 0xFF1976D2),
            CountryData("no", "Noruega", "🇳🇴", "Oslo", 5500000L, "Europa", "Norueguês", "Coroa Norueguesa", "A Noruega tem fiordes deslumbrantes e a dança da aurora boreal!", 0xFFC62828),
            CountryData("dk", "Dinamarca", "🇩🇰", "Copenhaga", 5900000L, "Europa", "Dinamarquês", "Coroa Dinamarquesa", "A Dinamarca é a terra dos blocos coloridos da LEGO e dos Vikings!", 0xFFD32F2F),
            CountryData("pl", "Polónia", "🇵🇱", "Varsóvia", 37500000L, "Europa", "Polaco", "Zloti", "A Polónia tem florestas antigas onde ainda vivem bisontes selvagens!", 0xFFB71C1C),
            CountryData("gr", "Grécia", "🇬🇷", "Atenas", 10400000L, "Europa", "Grego", "Euro", "A Grécia é o berço da democracia e dos Jogos Olímpicos!", 0xFF0288D1),
            CountryData("ua", "Ucrânia", "🇺🇦", "Kiev", 37000000L, "Europa", "Ucraniano", "Grívnia", "A Ucrânia tem os maiores campos dourados de girassóis da Europa!", 0xFFFFD600),

            // América
            CountryData("br", "Brasil", "🇧🇷", "Brasília", 216000000L, "América", "Português", "Real", "O Brasil tem a maior floresta tropical do mundo, a Amazónia!", 0xFF43A047),
            CountryData("us", "Estados Unidos", "🇺🇸", "Washington D.C.", 335000000L, "América", "Inglês", "Dólar Americano", "Os Estados Unidos têm 50 estados e parques com desfiladeiros gigantes!", 0xFFC62828),
            CountryData("ca", "Canadá", "🇨🇦", "Otava", 39000000L, "América", "Inglês, Francês", "Dólar Canadiano", "O Canadá é famoso pelo xarope de ácer e pelos lagos cristalinos!", 0xFFD32F2F),
            CountryData("mx", "México", "🇲🇽", "Cidade do México", 128000000L, "América", "Espanhol", "Peso Mexicano", "O México foi o pioneiro a cultivar cacau e a dar o chocolate ao mundo!", 0xFF2E7D32),
            CountryData("ar", "Argentina", "🇦🇷", "Buenos Aires", 46000000L, "América", "Espanhol", "Peso Argentino", "A Argentina tem pinguins na Patagónia e a cordilheira dos Andes!", 0xFF039BE5),
            CountryData("cl", "Chile", "🇨🇱", "Santiago", 19500000L, "América", "Espanhol", "Peso Chileno", "O Chile é o país mais comprido do mundo, com desertos e glaciares!", 0xFFC62828),
            CountryData("co", "Colômbia", "🇨🇴", "Bogotá", 52000000L, "América", "Espanhol", "Peso Colombiano", "A Colômbia tem mais espécies de pássaros do que qualquer outro país!", 0xFFFFD600),
            CountryData("pe", "Peru", "🇵🇪", "Lima", 34000000L, "América", "Espanhol", "Sol Peruano", "No Peru fica Machu Picchu, a lendária cidade nas nuvens dos Incas!", 0xFFD32F2F),
            CountryData("cu", "Cuba", "🇨🇺", "Havana", 11000000L, "América", "Espanhol", "Peso Cubano", "Cuba é famosa pela sua música alegre, carros clássicos e praias de água morna!", 0xFFB71C1C),
            CountryData("ve", "Venezuela", "🇻🇪", "Caracas", 28000000L, "América", "Espanhol", "Bolívar", "A Venezuela tem o Salto Ángel, a cascata mais alta de todo o planeta!", 0xFFFFB300),
            CountryData("ec", "Equador", "🇪🇨", "Quito", 18000000L, "América", "Espanhol", "Dólar Americano", "O Equador tem as Ilhas Galápagos onde vivem tartarugas gigantes!", 0xFF1976D2),

            // África
            CountryData("ao", "Angola", "🇦🇴", "Luanda", 36000000L, "África", "Português", "Kwanza", "Angola tem a Welwitschia Mirabilis, uma planta rara que vive mais de 1000 anos!", 0xFFB71C1C),
            CountryData("mz", "Moçambique", "🇲🇿", "Maputo", 34000000L, "África", "Português", "Metical", "Moçambique tem águas azul-turquesa com golfinhos e tubarões-baleia!", 0xFF00796B),
            CountryData("cv", "Cabo Verde", "🇨🇻", "Praia", 600000L, "África", "Português", "Escudo Cabo-verdiano", "Cabo Verde é um arquipélago alegre com 10 ilhas vulcânicas no Atlântico!", 0xFF1976D2),
            CountryData("gw", "Guiné-Bissau", "🇬🇼", "Bissau", 2200000L, "África", "Português", "Franco CFA", "A Guiné-Bissau tem as ilhas Bijagós com hipopótamos de água salgada!", 0xFFFFD600),
            CountryData("st", "São Tomé e Príncipe", "🇸🇹", "São Tomé", 230000L, "África", "Português", "Dobra", "São Tomé e Príncipe é conhecida como a doce Ilha do Cacau e do Café!", 0xFF2E7D32),
            CountryData("eg", "Egito", "🇪🇬", "Cairo", 112000000L, "África", "Árabe", "Libra Egípcia", "O Egito tem as famosas Pirâmides de Gizé, a Esfinge e o rio Nilo!", 0xFF795548),
            CountryData("za", "África do Sul", "🇿🇦", "Pretória", 60000000L, "África", "Zulu, Africânder, Inglês", "Rand", "A África do Sul tem pinguins na praia e leões e elefantes nas savanas!", 0xFF2E7D32),
            CountryData("ma", "Marrocos", "🇲🇦", "Rabat", 38000000L, "África", "Árabe, Amazigh", "Dirham Marroquino", "Marrocos tem mercados coloridos cheios de especiarias e tapetes!", 0xFFC62828),
            CountryData("ng", "Nigéria", "🇳🇬", "Abuja", 227000000L, "África", "Inglês", "Naira", "A Nigéria é o país mais populoso de África e cheio de energia cultural!", 0xFF1B5E20),
            CountryData("ke", "Quénia", "🇰🇪", "Nairóbi", 55000000L, "África", "Suaíli, Inglês", "Xelim Queniano", "No Quénia podes ver a grande migração de zebras e gnus no Masai Mara!", 0xFF000000),

            // Ásia
            CountryData("cn", "China", "🇨🇳", "Pequim", 1410000000L, "Ásia", "Mandarim", "Yuan", "A China construiu a Grande Muralha e é o lar dos ursos panda!", 0xFFD50000),
            CountryData("jp", "Japão", "🇯🇵", "Tóquio", 124000000L, "Ásia", "Japonês", "Iene", "O Japão tem comboios-bala Shinkansen velozes e cerejeiras Sakura!", 0xFFE91E63),
            CountryData("in", "Índia", "🇮🇳", "Nova Deli", 1428000000L, "Ásia", "Hindi, Inglês", "Rupia Indiana", "A Índia tem o Taj Mahal e inventou o jogo de xadrez!", 0xFFFB8C00),
            CountryData("kr", "Coreia do Sul", "🇰🇷", "Seul", 51700000L, "Ásia", "Coreano", "Won Sul-coreano", "A Coreia do Sul é famosa pelos robôs, tecnologia e música K-Pop!", 0xFF1976D2),
            CountryData("id", "Indonésia", "🇮🇩", "Jacarta", 278000000L, "Ásia", "Indonésio", "Rupia Indonésia", "A Indonésia é composta por mais de 17.000 ilhas tropicais!", 0xFFB71C1C),
            CountryData("th", "Tailândia", "🇹🇭", "Banguecoque", 71000000L, "Ásia", "Tailandês", "Baht", "A Tailândia é a terra dos elefantes sagrados e templos dourados!", 0xFFC62828),
            CountryData("vn", "Vietname", "🇻🇳", "Hanói", 99000000L, "Ásia", "Vietnamita", "Dong", "O Vietname tem a baía de Ha Long com milhares de ilhéus de pedra calcária!", 0xFFD32F2F),
            CountryData("sa", "Arábia Saudita", "🇸🇦", "Riade", 36000000L, "Ásia", "Árabe", "Rial Saudita", "A Arábia Saudita tem o deserto de Rub' al-Khali com dunas imensas!", 0xFF2E7D32),
            CountryData("ae", "Emirados Árabes Unidos", "🇦🇪", "Abu Dhabi", 9900000L, "Ásia", "Árabe", "Dirham dos EAU", "Os Emirados têm o Burj Khalifa, o edifício mais alto do mundo!", 0xFF00796B),
            CountryData("il", "Israel", "🇮🇱", "Jerusalém", 9800000L, "Ásia", "Hebraico, Árabe", "Novo Shekel", "Em Israel fica o Mar Morto, onde se flutua na água sem esforço!", 0xFF1976D2),
            CountryData("tr", "Turquia", "🇹🇷", "Ancara", 85000000L, "Ásia", "Turco", "Lira Turca", "A Turquia une a Europa e a Ásia com a bela cidade de Istambul!", 0xFFD32F2F),

            // Oceania
            CountryData("au", "Austrália", "🇦🇺", "Camberra", 26600000L, "Oceania", "Inglês", "Dólar Australiano", "A Austrália tem cangurus aos saltos, coalas e a Grande Barreira de Coral!", 0xFF1A237E),
            CountryData("nz", "Nova Zelândia", "🇳🇿", "Wellington", 5200000L, "Oceania", "Inglês, Maori", "Dólar Neozelandês", "A Nova Zelândia é o lar do pássaro kiwi e de montanhas mágicas!", 0xFF00247D)
        )
    }

    val continents = remember {
        listOf(
            ContinentInfo("Todos", "🌐", "Todos os países da lista", Color(0xFF1976D2)),
            ContinentInfo("Europa", "🏰", "Castelos, história e monumentos", Color(0xFF388E3C)),
            ContinentInfo("América", "🗽", "Florestas tropicais e grandes montanhas", Color(0xFFE64A19)),
            ContinentInfo("África", "🦁", "Savanas, animais selvagens e sol", Color(0xFFF57C00)),
            ContinentInfo("Ásia", "🐼", "O maior continente, pandas e templos", Color(0xFF7B1FA2)),
            ContinentInfo("Oceania", "🦘", "Recifes de coral, ilhas e cangurus", Color(0xFF0097A7))
        )
    }

    var selectedContinentName by remember { mutableStateOf("Todos") }
    var selectedCountry by remember { mutableStateOf<CountryData?>(null) }
    var currentTab by remember { mutableStateOf(0) } // 0 = Mapa Interativo, 1 = Bandeiras, 2 = Quiz
    var searchQuery by remember { mutableStateOf("") }

    // Quiz Mode State
    var quizCountry by remember { mutableStateOf<CountryData?>(null) }
    var quizOptions by remember { mutableStateOf<List<String>>(emptyList()) }
    var quizScore by remember { mutableStateOf(0) }
    var quizAnswered by remember { mutableStateOf<Boolean?>(null) }
    var selectedAnswer by remember { mutableStateOf("") }

    var targetCountryCodeForMap by remember { mutableStateOf<String?>(null) }

    fun startNewQuizQuestion() {
        val randomCountry = countries.random()
        quizCountry = randomCountry
        val correctCapital = randomCountry.capital.trim()
        val otherCapitals = countries
            .map { it.capital.trim() }
            .filter { it.isNotBlank() && !it.equals(correctCapital, ignoreCase = true) }
            .distinct()
            .shuffled()
            .take(2)
        quizOptions = (otherCapitals + correctCapital).filter { it.isNotBlank() }.shuffled()
        quizAnswered = null
        selectedAnswer = ""
    }

    LaunchedEffect(currentTab) {
        if (currentTab == 2 && quizCountry == null) {
            startNewQuizQuestion()
            viewModel.speak("Vamos jogar ao Quiz do Mundo! Adivinha a capital correta.")
        }
    }

    val userProgress by viewModel.userProgress.collectAsState()
    val filteredCountries = remember(selectedContinentName, searchQuery) {
        countries.filter { country ->
            (selectedContinentName == "Todos" || country.continent.equals(selectedContinentName, ignoreCase = true) ||
             (selectedContinentName == "Oceania" && country.continent.contains("Ocean", ignoreCase = true))) &&
            (searchQuery.isBlank() || country.name.contains(searchQuery, ignoreCase = true) || country.capital.contains(searchQuery, ignoreCase = true))
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
            // Header Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.background(Color.White.copy(alpha = 0.9f), CircleShape)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color(0xFF1976D2))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Atlas do Zé Traquina",
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White,
                                shadow = androidx.compose.ui.graphics.Shadow(
                                    color = Color.Black.copy(alpha = 0.70f),
                                    offset = androidx.compose.ui.geometry.Offset(2f, 2f),
                                    blurRadius = 6f
                                )
                            )
                        )
                        Text(
                            text = "Explora o Planeta Terra 🌍",
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                shadow = androidx.compose.ui.graphics.Shadow(
                                    color = Color.Black.copy(alpha = 0.70f),
                                    offset = androidx.compose.ui.geometry.Offset(1.5f, 1.5f),
                                    blurRadius = 4f
                                )
                            )
                        )
                    }
                }

                // Star points indicator
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFFFD54F),
                    shadowElevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFE65100), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${userProgress.starsCount}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = Color(0xFFE65100)
                        )
                    }
                }
            }

            // Mode Navigation Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TabButton(
                    title = "Mapa Interativo",
                    icon = Icons.Default.Public,
                    isSelected = currentTab == 0,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        currentTab = 0
                        viewModel.speak("Mapa mundo aberto! Toca num país colorido para explorar.")
                    }
                )
                TabButton(
                    title = "Bandeiras (${countries.size})",
                    icon = Icons.Default.Flag,
                    isSelected = currentTab == 1,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        currentTab = 1
                        viewModel.speak("Aqui estão as ${countries.size} bandeiras dos países do mundo!")
                    }
                )
                TabButton(
                    title = "Quiz 🎯",
                    icon = Icons.Default.EmojiEvents,
                    isSelected = currentTab == 2,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        currentTab = 2
                        startNewQuizQuestion()
                    }
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            when (currentTab) {
                0 -> {
                    // ==========================================
                    // TAB 0: REAL GEOGRAPHIC INTERACTIVE HTML SVG MAP
                    // ==========================================
                    InteractiveSvgMapWebView(
                        viewModel = viewModel,
                        countries = countries,
                        initialCountryCode = targetCountryCodeForMap,
                        onInitialCountryHandled = { targetCountryCodeForMap = null }
                    )
                }
                1 -> {
                    // ==========================================
                    // TAB 1: ALL FLAGS & SEARCHABLE DIRECTORY
                    // ==========================================
                    Column(modifier = Modifier.fillMaxSize()) {
                        // Search bar
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp),
                            placeholder = { Text("Pesquisar país ou capital...", fontSize = 14.sp) },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFF1976D2)) },
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(onClick = { searchQuery = "" }) {
                                        Icon(Icons.Default.Clear, contentDescription = "Limpar")
                                    }
                                }
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White.copy(alpha = 0.95f),
                                focusedBorderColor = Color(0xFF1976D2),
                                unfocusedBorderColor = Color.Transparent
                            ),
                            singleLine = true
                        )

                        // Continent Pills
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(continents) { continent ->
                                val isSelected = selectedContinentName == continent.name
                                Surface(
                                    onClick = {
                                        selectedContinentName = continent.name
                                        viewModel.speak("A mostrar países de ${continent.name}")
                                    },
                                    shape = RoundedCornerShape(14.dp),
                                    color = if (isSelected) Color(0xFF1976D2) else Color.White.copy(alpha = 0.9f),
                                    contentColor = if (isSelected) Color.White else Color.Black,
                                    border = if (isSelected) null else BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.4f)),
                                    shadowElevation = if (isSelected) 3.dp else 1.dp
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(continent.emoji, fontSize = 14.sp)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = continent.name,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp
                                        )
                                    }
                                }
                            }
                        }

                        // Grid of Countries
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            items(filteredCountries) { country ->
                                CountryCard(
                                    country = country,
                                    onClick = {
                                        selectedCountry = country
                                        val popPt = formatPopulationPt(country.populacao)
                                        viewModel.speak("${country.name}. A capital é ${country.capital}. Fica na ${country.continent}, com $popPt de habitantes.")
                                        viewModel.addStars(2)
                                    }
                                )
                            }
                        }
                    }
                }
                2 -> {
                    // ==========================================
                    // TAB 2: FUN WORLD QUIZ GAME
                    // ==========================================
                    WorldQuizSection(
                        quizCountry = quizCountry,
                        quizOptions = quizOptions,
                        quizAnswered = quizAnswered,
                        selectedAnswer = selectedAnswer,
                        quizScore = quizScore,
                        onSelectAnswer = { option ->
                            if (quizAnswered == null && quizCountry != null) {
                                selectedAnswer = option
                                val isCorrect = option == quizCountry!!.capital
                                quizAnswered = isCorrect
                                if (isCorrect) {
                                    quizScore++
                                    viewModel.addStars(5)
                                    viewModel.speak("Correto! A capital de ${quizCountry!!.name} é ${quizCountry!!.capital}! Ganhaste 5 estrelas!")
                                } else {
                                    viewModel.speak("Quase! A capital de ${quizCountry!!.name} é ${quizCountry!!.capital}.")
                                }
                            }
                        },
                        onNext = {
                            startNewQuizQuestion()
                        }
                    )
                }
            }
        }

        // ==========================================
        // COUNTRY DETAIL MODAL DIALOG
        // ==========================================
        if (selectedCountry != null) {
            val country = selectedCountry!!
            AlertDialog(
                onDismissRequest = { selectedCountry = null },
                confirmButton = {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = {
                                val targetCode = country.codigo
                                selectedCountry = null
                                targetCountryCodeForMap = targetCode
                                currentTab = 0
                                viewModel.speak("A localizar ${country.name} no mapa!")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Ver no Mapa 🗺️", fontWeight = FontWeight.Bold)
                        }
                        Button(
                            onClick = { selectedCountry = null },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Fechar", fontWeight = FontWeight.Bold)
                        }
                    }
                },
                dismissButton = {
                    OutlinedButton(
                        onClick = {
                            val popPt = formatPopulationPt(country.populacao)
                            viewModel.speak("${country.name}. A capital é ${country.capital}. Língua: ${country.lingua}. Moeda: ${country.moeda}. ${country.fact}")
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Ouvir")
                    }
                },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(country.flag, fontSize = 36.sp)
                        Column {
                            Text(country.name, fontWeight = FontWeight.Black, fontSize = 20.sp)
                            Text(country.continent, fontSize = 12.sp, color = Color(0xFF1976D2), fontWeight = FontWeight.Bold)
                        }
                    }
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F9FF)),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                DetailRow(Icons.Default.LocationCity, "Capital", country.capital)
                                DetailRow(Icons.Default.Groups, "População", formatPopulationPt(country.populacao))
                                DetailRow(Icons.Default.RecordVoiceOver, "Língua", country.lingua)
                                DetailRow(Icons.Default.MonetizationOn, "Moeda", country.moeda)
                            }
                        }

                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
                            shape = RoundedCornerShape(14.dp),
                            border = BorderStroke(1.dp, Color(0xFFFFD54F))
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text("💡", fontSize = 20.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text("Sabias que?", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFFE65100))
                                    Text(
                                        text = country.fact,
                                        fontSize = 13.sp,
                                        lineHeight = 18.sp,
                                        color = Color(0xFF3E2723)
                                    )
                                }
                            }
                        }
                    }
                },
                shape = RoundedCornerShape(24.dp),
                containerColor = Color.White
            )
        }
    }

class WebAppInterface(private val viewModel: MainViewModel) {
    @JavascriptInterface
    fun speak(text: String) {
        viewModel.speak(text)
    }

    @JavascriptInterface
    fun addStars(stars: Int) {
        viewModel.addStars(stars)
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun InteractiveSvgMapWebView(
    viewModel: MainViewModel,
    countries: List<CountryData>,
    initialCountryCode: String? = null,
    onInitialCountryHandled: () -> Unit = {}
) {
    var webViewRef by remember { mutableStateOf<WebView?>(null) }
    var mapSearchQuery by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(initialCountryCode, webViewRef) {
        if (initialCountryCode != null && webViewRef != null) {
            val code = initialCountryCode.lowercase(Locale.getDefault())
            val target = countries.find { it.codigo.lowercase(Locale.getDefault()) == code }
            target?.let {
                delay(300)
                webViewRef?.evaluateJavascript("zoomToCountry('${it.codigo}')", null)
                onInitialCountryHandled()
            }
        }
    }

    val searchResults by remember(mapSearchQuery, countries) {
        derivedStateOf {
            if (mapSearchQuery.isBlank()) {
                emptyList()
            } else {
                val q = mapSearchQuery.trim().lowercase(Locale.getDefault())
                countries.filter { country ->
                    country.name.lowercase(Locale.getDefault()).contains(q) ||
                    country.capital.lowercase(Locale.getDefault()).contains(q) ||
                    country.continent.lowercase(Locale.getDefault()).contains(q) ||
                    country.codigo.lowercase(Locale.getDefault()) == q
                }.take(5)
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            webViewRef?.stopLoading()
            webViewRef?.destroy()
            webViewRef = null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 2.dp)
    ) {
        // Search bar card with suggestions
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            border = BorderStroke(1.5.dp, Color(0xFF1976D2).copy(alpha = 0.4f))
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = mapSearchQuery,
                    onValueChange = { mapSearchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            "Procurar país para dar zoom (ex: Portugal, Japão...)",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Pesquisar País",
                            tint = Color(0xFF1976D2)
                        )
                    },
                    trailingIcon = {
                        if (mapSearchQuery.isNotEmpty()) {
                            IconButton(onClick = {
                                mapSearchQuery = ""
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            }) {
                                Icon(Icons.Default.Clear, contentDescription = "Limpar", tint = Color.Gray)
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        if (searchResults.isNotEmpty()) {
                            val target = searchResults.first()
                            webViewRef?.evaluateJavascript("zoomToCountry('${target.codigo}')", null)
                            viewModel.speak("A aproximar o mapa de ${target.name}!")
                            mapSearchQuery = ""
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        }
                    })
                )

                // Dropdown suggestions when typing
                if (searchResults.isNotEmpty()) {
                    HorizontalDivider(color = Color(0xFFE0E0E0))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 200.dp)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        items(searchResults) { country ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        webViewRef?.evaluateJavascript("zoomToCountry('${country.codigo}')", null)
                                        viewModel.speak("A aproximar o mapa de ${country.name}!")
                                        mapSearchQuery = ""
                                        focusManager.clearFocus()
                                        keyboardController?.hide()
                                    }
                                    .padding(vertical = 8.dp, horizontal = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(country.flag, fontSize = 22.sp)
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = country.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = Color(0xFF1A237E)
                                    )
                                    Text(
                                        text = "${country.continent} • Capital: ${country.capital}",
                                        fontSize = 11.sp,
                                        color = Color.Gray
                                    )
                                }
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFFE3F2FD)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            Icons.Default.ZoomIn,
                                            contentDescription = null,
                                            tint = Color(0xFF1976D2),
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            "Zoom",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF1976D2)
                                        )
                                    }
                                }
                            }
                            HorizontalDivider(color = Color(0xFFF5F5F5), thickness = 0.5.dp)
                        }
                    }
                }
            }
        }

        // Quick Country Shortcuts row
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF1976D2),
                    modifier = Modifier.clickable {
                        webViewRef?.evaluateJavascript("resetMapZoom()", null)
                        viewModel.speak("A mostrar o mapa do mundo inteiro!")
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🌐", fontSize = 13.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Mundo Todo", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            val quickCodes = listOf("pt", "es", "fr", "de", "it", "gb", "br", "ao", "mz", "cv", "gw", "st", "jp", "us")
            val quickCountries = quickCodes.mapNotNull { code -> countries.find { it.codigo == code } }
            items(quickCountries) { country ->
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, Color(0xFFBBDEFB)),
                    modifier = Modifier.clickable {
                        webViewRef?.evaluateJavascript("zoomToCountry('${country.codigo}')", null)
                        viewModel.speak("A aproximar de ${country.name}!")
                    }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(country.flag, fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(country.name, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF37474F))
                    }
                }
            }
        }

        // WebView Map Container
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F4FD)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            border = BorderStroke(2.dp, Color(0xFF90CAF9))
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    WebView(ctx).apply { com.example.util.EmulatorUtils.optimizeWebViewForEmulator(this); 
                        webViewRef = this
                        layoutParams = android.view.ViewGroup.LayoutParams(
                            android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                            android.view.ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        setBackgroundColor(0)
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        settings.allowFileAccess = true
                        settings.allowContentAccess = true
                        settings.loadWithOverviewMode = true
                        settings.useWideViewPort = true
                        settings.cacheMode = WebSettings.LOAD_DEFAULT
                        webChromeClient = object : WebChromeClient() {
                            override fun onConsoleMessage(consoleMessage: android.webkit.ConsoleMessage?): Boolean {
                                android.util.Log.d(
                                    "MapWebView",
                                    "${consoleMessage?.message()} -- line ${consoleMessage?.lineNumber()} of ${consoleMessage?.sourceId()}"
                                )
                                return true
                            }
                        }
                        webViewClient = object : WebViewClient() {
                            override fun onRenderProcessGone(
                                view: WebView?,
                                detail: android.webkit.RenderProcessGoneDetail?
                            ): Boolean {
                                try {
                                    (view?.parent as? android.view.ViewGroup)?.removeView(view)
                                    view?.destroy()
                                } catch (e: Exception) {
                                    android.util.Log.e("MapWebView", "Error onRenderProcessGone", e)
                                }
                                return true
                            }
                        }

                        addJavascriptInterface(WebAppInterface(viewModel), "Android")
                        loadUrl("file:///android_asset/mapa_mundo/mapa_mundo.html")
                    }
                },
                onRelease = { view ->
                    view.stopLoading()
                    view.destroy()
                }
            )
        }
    }
}

@Composable
fun TabButton(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) Color(0xFFFFD54F) else Color.White.copy(alpha = 0.85f),
        contentColor = if (isSelected) Color(0xFF3E2723) else Color.DarkGray,
        shadowElevation = if (isSelected) 3.dp else 1.dp,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 6.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp), tint = if (isSelected) Color(0xFFE65100) else Color.Gray)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun WorldQuizSection(
    quizCountry: CountryData?,
    quizOptions: List<String>,
    quizAnswered: Boolean?,
    selectedAnswer: String,
    quizScore: Int,
    onSelectAnswer: (String) -> Unit,
    onNext: () -> Unit
) {
    if (quizCountry == null) return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Score Header Card
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
                        Text("$quizScore Acertos", fontSize = 16.sp, fontWeight = FontWeight.Black, color = Color(0xFF1976D2))
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

        // Question Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(quizCountry.flag, fontSize = 72.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Qual é a capital deste país?",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = quizCountry.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF1976D2)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFF0F4F8)
                ) {
                    Text(
                        text = "Continente: ${quizCountry.continent}",
                        fontSize = 12.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // Options List
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            quizOptions.forEach { option ->
                val isSelected = selectedAnswer == option
                val isCorrect = option == quizCountry.capital
                val buttonColor = when {
                    quizAnswered == null -> Color.White
                    isCorrect -> Color(0xFFC8E6C9)
                    isSelected && !isCorrect -> Color(0xFFFFCDD2)
                    else -> Color.White
                }
                val borderColor = when {
                    quizAnswered == null -> Color(0xFF1976D2).copy(alpha = 0.3f)
                    isCorrect -> Color(0xFF2E7D32)
                    isSelected && !isCorrect -> Color(0xFFC62828)
                    else -> Color.LightGray.copy(alpha = 0.4f)
                }

                Surface(
                    onClick = { onSelectAnswer(option) },
                    shape = RoundedCornerShape(16.dp),
                    color = buttonColor,
                    border = BorderStroke(2.dp, borderColor),
                    shadowElevation = 3.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = option,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF212121)
                        )
                        if (quizAnswered != null) {
                            if (isCorrect) {
                                Icon(Icons.Default.CheckCircle, contentDescription = "Correto", tint = Color(0xFF2E7D32))
                            } else if (isSelected) {
                                Icon(Icons.Default.Cancel, contentDescription = "Incorreto", tint = Color(0xFFC62828))
                            }
                        }
                    }
                }
            }
        }

        // Next Question Button
        if (quizAnswered != null) {
            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD54F))
            ) {
                Text(
                    text = "Próxima Pergunta ➡️",
                    fontWeight = FontWeight.Black,
                    fontSize = 16.sp,
                    color = Color(0xFF3E2723)
                )
            }
        }
    }
}

@Composable
fun CountryCard(country: CountryData, onClick: () -> Unit) {
    com.example.ui.components.EducationalItemCard(
        title = country.name,
        subtitle = country.capital,
        emoji = country.flag,
        colorHex = country.color,
        badge = country.continent,
        countText = null,
        onClick = onClick,
        subtitleMaxLines = 1
    )
}

@Composable
fun DetailRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color(0xFF1976D2))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "$label: ", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.Gray)
        Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = Color.Black)
    }
}
