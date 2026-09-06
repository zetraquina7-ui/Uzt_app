package com.example.ui.screens

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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.FundoApp
import com.example.viewmodel.MainViewModel

data class AnimalData(
    val id: String,
    val name: String,
    val emoji: String,
    val group: String, // Mamífero, Ave, Réptil, Peixe, etc.
    val habitat: String,
    val diet: String,
    val sound: String,
    val speed: String,
    val fact: String,
    val color: Long
)

data class AnimalGroupInfo(
    val name: String,
    val emoji: String,
    val description: String,
    val color: Color
)

@Composable
fun AnimalsScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit
) {
    // 21 Animals data matching the rich dataset of Atlas
    val animals = remember {
        listOf(
            AnimalData("leao", "Leão", "🦁", "Mamíferos", "Savana", "Carnívoro", "Rugido potente", "80 km/h", "O leão é conhecido como o Rei da Selva e vive em grandes famílias!", 0xFFFFA000),
            AnimalData("elefante", "Elefante", "🐘", "Mamíferos", "Savana e Floresta", "Herbívoro", "Bramido com tromba", "40 km/h", "O elefante é o maior animal terrestre e tem uma memória fantástica!", 0xFF78909C),
            AnimalData("girafa", "Girafa", "🦒", "Mamíferos", "Savana", "Herbívoro", "Sons suaves e assobios", "60 km/h", "A girafa é o animal mais alto do planeta e tem uma língua azul gigante!", 0xFFFFB300),
            AnimalData("tigre", "Tigre", "🐯", "Mamíferos", "Florestas e Selvas", "Carnívoro", "Rugido forte", "65 km/h", "As riscas de cada tigre são únicas, tal como as impressões digitais!", 0xFFFF6F00),
            AnimalData("macaco", "Macaco", "🐒", "Mamíferos", "Árvores e Florestas", "Omnívoro", "Gritos alegres", "55 km/h", "Os macacos são muito inteligentes, usam ferramentas e adoram brincar!", 0xFF8D6E63),
            AnimalData("golfinho", "Golfinho", "🐬", "Mamíferos", "Oceanos e Mares", "Piscívoro", "Cliques e assobios", "60 km/h", "Os golfinhos comunicam por ultrassons e são super amigáveis com humanos!", 0xFF0288D1),
            AnimalData("baleia", "Baleia Azul", "🐋", "Mamíferos", "Oceanos profundos", "Planctívoro", "Cantos subaquáticos", "50 km/h", "A baleia azul é o maior ser vivo que alguma vez existiu na Terra!", 0xFF1565C0),
            AnimalData("aguia", "Águia", "🦅", "Aves", "Montanhas e Céu", "Carnívoro", "Grito agudo", "160 km/h (mergulho)", "A águia tem uma visão 8 vezes mais nítida do que a dos humanos!", 0xFF6D4C41),
            AnimalData("coruja", "Coruja", "🦉", "Aves", "Florestas noturnas", "Carnívoro", "Pio noturno suave", "80 km/h", "A coruja consegue rodar a cabeça quase 360 graus e voa em silêncio absoluto!", 0xFF5D4037),
            AnimalData("papagaio", "Papagaio", "🦜", "Aves", "Florestas tropicais", "Frugívoro", "Imitação de vozes", "50 km/h", "Os papagaios são capazes de aprender palavras e frases inteiras!", 0xFF43A047),
            AnimalData("pinguim", "Pinguim", "🐧", "Aves", "Gelo e Mar Antártico", "Piscívoro", "Grasnido social", "36 km/h (a nadar)", "Os pinguins não voam no ar, mas são nadadores incrivelmente rápidos no mar!", 0xFF37474F),
            AnimalData("tartaruga", "Tartaruga Marinha", "🐢", "Répteis", "Oceanos e Praias", "Herbívoro", "Respiração suave", "35 km/h", "Algumas tartarugas marinhas podem viver mais de 100 anos!", 0xFF2E7D32),
            AnimalData("crocodilo", "Crocodilo", "🐊", "Répteis", "Rios e Lagos tropicais", "Carnívoro", "Rugido gutural", "30 km/h", "Os crocodilos existem desde o tempo dos dinossauros e têm dentes fortíssimos!", 0xFF33691E),
            AnimalData("camaleao", "Camaleão", "🦎", "Répteis", "Florestas e Ramos", "Insetívoro", "Silencioso", "5 km/h", "O camaleão muda de cor para expressar emoções e mexer cada olho sozinho!", 0xFF558B2F),
            AnimalData("tubarao", "Tubarão Branco", "🦈", "Peixes", "Oceanos", "Carnívoro", "Silencioso na água", "56 km/h", "Os tubarões conseguem detetar uma única gota de cheiro a quilómetros de distância!", 0xFF455A64),
            AnimalData("palhaco", "Peixe-Palhaço", "🐠", "Peixes", "Recifes de Coral", "Omnívoro", "Cliques pequenos", "15 km/h", "O peixe-palhaço vive protegido dentro dos tentáculos das anémonas!", 0xFFFF7043),
            AnimalData("cavalo_marinho", "Cavalo-Marinho", "🐡", "Peixes", "Fundos Marinhos", "Planctívoro", "Estalidos suaves", "2 km/h", "É o pai cavalo-marinho que carrega os ovinhos na bolsa até nascerem os bebés!", 0xFFFFB74D),
            AnimalData("urso", "Urso Polar", "🐻‍❄️", "Mamíferos", "Ártico e Neve", "Carnívoro", "Rosnado forte", "40 km/h", "A pele do urso polar por baixo do pelo branco é preta para absorver o calor do sol!", 0xFF90A4AE),
            AnimalData("panda", "Urso Panda", "🐼", "Mamíferos", "Florestas de Bambu", "Herbívoro", "Balidos fofos", "32 km/h", "Um panda passa até 12 horas por dia a mastigar o seu bambu favorito!", 0xFF212121),
            AnimalData("coelho", "Coelho", "🐰", "Mamíferos", "Prados e Tocas", "Herbívoro", "Ronronar suave", "45 km/h", "Os coelhos mexem as orelhas compridas em todas as direções para ouvir tudo!", 0xFFE0E0E0),
            AnimalData("lobo", "Lobo Ibérico", "🐺", "Mamíferos", "Serras de Portugal", "Carnívoro", "Uivo melódico", "60 km/h", "O lobo ibérico vive nas serras de Portugal e protege o equilíbrio da natureza!", 0xFF546E7A)
        )
    }

    val groups = remember {
        listOf(
            AnimalGroupInfo("Todos", "🐾", "Todos os animais do reino animal", Color(0xFF1976D2)),
            AnimalGroupInfo("Mamíferos", "🦁", "Têm pelo e bebem leite da mãe", Color(0xFFFFA000)),
            AnimalGroupInfo("Aves", "🦅", "Têm penas, asas e põem ovos", Color(0xFF43A047)),
            AnimalGroupInfo("Répteis", "🐊", "Têm escamas e sangue frio", Color(0xFF2E7D32)),
            AnimalGroupInfo("Peixes", "🐟", "Vivem debaixo de água e têm guelras", Color(0xFF0288D1))
        )
    }

    var currentTab by remember { mutableStateOf(0) } // 0 = Animais (Grid), 1 = Espécies, 2 = Quiz
    var searchQuery by remember { mutableStateOf("") }
    var selectedGroupName by remember { mutableStateOf("Todos") }
    var selectedAnimal by remember { mutableStateOf<AnimalData?>(null) }

    // Quiz Mode State (EXACT REPLICA OF ATLAS QUIZ)
    var quizAnimal by remember { mutableStateOf<AnimalData?>(null) }
    var quizOptions by remember { mutableStateOf<List<String>>(emptyList()) }
    var quizAnswered by remember { mutableStateOf<Boolean?>(null) }
    var selectedAnswer by remember { mutableStateOf("") }
    var quizScore by remember { mutableStateOf(0) }

    fun startNewQuizQuestion() {
        val randomAnimal = animals.random()
        quizAnimal = randomAnimal
        val correctGroup = randomAnimal.group.trim()
        val otherGroups = listOf("Mamíferos", "Aves", "Répteis", "Peixes")
            .filter { !it.equals(correctGroup, ignoreCase = true) }
            .shuffled()
            .take(2)
        quizOptions = (otherGroups + correctGroup).shuffled()
        quizAnswered = null
        selectedAnswer = ""
    }

    LaunchedEffect(currentTab) {
        if (currentTab == 2 && quizAnimal == null) {
            startNewQuizQuestion()
            viewModel.speak("Vamos jogar ao Quiz dos Animais! Adivinha a que grupo pertence cada animal.")
        }
    }

    val userProgress by viewModel.userProgress.collectAsState()
    val filteredAnimals = remember(selectedGroupName, searchQuery) {
        animals.filter { animal ->
            (selectedGroupName == "Todos" || animal.group.equals(selectedGroupName, ignoreCase = true)) &&
            (searchQuery.isBlank() || animal.name.contains(searchQuery, ignoreCase = true) || animal.habitat.contains(searchQuery, ignoreCase = true) || animal.group.contains(searchQuery, ignoreCase = true))
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
            // ==========================================
            // HEADER TOP BAR (IDENTICAL TO ATLAS)
            // ==========================================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .size(38.dp)
                            .shadow(4.dp, CircleShape)
                            .background(Color.White.copy(alpha = 0.95f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color(0xFF1976D2),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Animais do Zé Traquina",
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 18.sp,
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
                            text = "Explora o Reino Animal 🐾",
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

                // Star points indicator (IDENTICAL TO ATLAS)
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

            // ==========================================
            // MODE NAVIGATION TABS (IDENTICAL TO ATLAS TABBUTTONS)
            // ==========================================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TabButton(
                    title = "Animais (${animals.size})",
                    icon = Icons.Default.Pets,
                    isSelected = currentTab == 0,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        currentTab = 0
                        viewModel.speak("Aqui estão os ${animals.size} animais fascinantes do Zé Traquina!")
                    }
                )
                TabButton(
                    title = "Espécies",
                    icon = Icons.Default.Category,
                    isSelected = currentTab == 1,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        currentTab = 1
                        viewModel.speak("Explora as espécies e grupos de animais!")
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
                    // TAB 0: ALL ANIMALS & SEARCHABLE DIRECTORY
                    // ==========================================
                    Column(modifier = Modifier.fillMaxSize()) {
                        // Search bar (IDENTICAL TO ATLAS)
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp),
                            placeholder = { Text("Pesquisar animal ou habitat...", fontSize = 14.sp) },
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

                        // Group / Category Pills (IDENTICAL TO ATLAS CONTINENT PILLS)
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(groups) { group ->
                                val isSelected = selectedGroupName == group.name
                                Surface(
                                    onClick = {
                                        selectedGroupName = group.name
                                        viewModel.speak("A mostrar ${group.name}")
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
                                        Text(group.emoji, fontSize = 14.sp)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = group.name,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp
                                        )
                                    }
                                }
                            }
                        }

                        // Grid of Animals (IDENTICAL TO ATLAS GRID)
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            items(filteredAnimals) { animal ->
                                AnimalCard(
                                    animal = animal,
                                    onClick = {
                                        selectedAnimal = animal
                                        viewModel.speak("${animal.name}. Vive no habitat: ${animal.habitat}. É um ${animal.group}. ${animal.fact}")
                                        viewModel.addStars(2)
                                    }
                                )
                            }
                        }
                    }
                }
                1 -> {
                    // ==========================================
                    // TAB 1: ANIMAL SPECIES / GROUPS DIRECTORY
                    // ==========================================
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(groups.filter { it.name != "Todos" }) { group ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedGroupName = group.name
                                        searchQuery = ""
                                        currentTab = 0
                                        viewModel.speak("A mostrar todos os ${group.name}!")
                                    },
                                shape = RoundedCornerShape(18.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                                border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.25f))
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Surface(
                                            shape = CircleShape,
                                            color = group.color.copy(alpha = 0.15f),
                                            modifier = Modifier.size(54.dp)
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Text(group.emoji, fontSize = 28.sp)
                                            }
                                        }
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column {
                                            Text(
                                                text = group.name,
                                                fontWeight = FontWeight.Black,
                                                fontSize = 17.sp,
                                                color = Color(0xFF1E293B)
                                            )
                                            Text(
                                                text = group.description,
                                                fontSize = 12.sp,
                                                color = Color.Gray,
                                                lineHeight = 16.sp
                                            )
                                        }
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = Color(0xFF1976D2),
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
                2 -> {
                    // ==========================================
                    // TAB 2: FUN ANIMALS QUIZ GAME (IDENTICAL TO ATLAS QUIZ)
                    // ==========================================
                    AnimalQuizSection(
                        quizAnimal = quizAnimal,
                        quizOptions = quizOptions,
                        quizAnswered = quizAnswered,
                        selectedAnswer = selectedAnswer,
                        quizScore = quizScore,
                        onSelectAnswer = { option ->
                            if (quizAnswered == null && quizAnimal != null) {
                                selectedAnswer = option
                                val isCorrect = option.equals(quizAnimal!!.group, ignoreCase = true)
                                quizAnswered = isCorrect
                                if (isCorrect) {
                                    quizScore++
                                    viewModel.addStars(5)
                                    viewModel.speak("Correto! O ${quizAnimal!!.name} é um ${quizAnimal!!.group}! Ganhaste 5 estrelas!")
                                } else {
                                    viewModel.speak("Quase! O ${quizAnimal!!.name} é um ${quizAnimal!!.group}.")
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
        // ANIMAL DETAIL MODAL DIALOG (IDENTICAL TO ATLAS COUNTRY DETAIL DIALOG)
        // ==========================================
        if (selectedAnimal != null) {
            val animal = selectedAnimal!!
            AlertDialog(
                onDismissRequest = { selectedAnimal = null },
                confirmButton = {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = {
                                viewModel.speak("${animal.name}. Som característico: ${animal.sound}. ${animal.fact}")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Ouvir Zé 🔊", fontWeight = FontWeight.Bold)
                        }
                        Button(
                            onClick = { selectedAnimal = null },
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
                            viewModel.speak("${animal.name}. Vive no habitat: ${animal.habitat}. Alimentação: ${animal.diet}. Velocidade: ${animal.speed}. ${animal.fact}")
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
                        Text(animal.emoji, fontSize = 36.sp)
                        Column {
                            Text(animal.name, fontWeight = FontWeight.Black, fontSize = 20.sp)
                            Text(animal.group, fontSize = 12.sp, color = Color(0xFF1976D2), fontWeight = FontWeight.Bold)
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
                                DetailRow(Icons.Default.Nature, "Habitat", animal.habitat)
                                DetailRow(Icons.Default.Restaurant, "Alimentação", animal.diet)
                                DetailRow(Icons.Default.Speed, "Velocidade", animal.speed)
                                DetailRow(Icons.Default.MusicNote, "Som", animal.sound)
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
                                        text = animal.fact,
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

@Composable
fun AnimalCard(animal: AnimalData, onClick: () -> Unit) {
    com.example.ui.components.EducationalItemCard(
        title = animal.name,
        subtitle = animal.habitat,
        emoji = animal.emoji,
        colorHex = animal.color,
        badge = animal.group,
        countText = null,
        onClick = onClick,
        subtitleMaxLines = 1
    )
}

@Composable
fun AnimalQuizSection(
    quizAnimal: AnimalData?,
    quizOptions: List<String>,
    quizAnswered: Boolean?,
    selectedAnswer: String,
    quizScore: Int,
    onSelectAnswer: (String) -> Unit,
    onNext: () -> Unit
) {
    if (quizAnimal == null) return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Score Header Card (IDENTICAL TO ATLAS)
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
                            color = Color(0xFF1976D2)
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

        // Question Card (IDENTICAL TO ATLAS)
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
                Text(quizAnimal.emoji, fontSize = 72.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "A que grupo pertence este animal?",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = quizAnimal.name,
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
                        text = "Habitat: ${quizAnimal.habitat}",
                        fontSize = 12.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // Options List (IDENTICAL TO ATLAS)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            quizOptions.forEach { option ->
                val isSelected = selectedAnswer == option
                val isCorrect = option.equals(quizAnimal.group, ignoreCase = true)
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

        // Next Question Button (IDENTICAL TO ATLAS)
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
