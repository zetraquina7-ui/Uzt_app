package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.R
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SafeAsyncImage
import com.example.ui.components.MascotFaceCircle
import com.example.util.AppImageLoader
import com.example.util.PreviewConfig
import com.example.api.RssArticle
import com.example.api.RssFeedParser
import com.example.ui.theme.KidStarOrange
import com.example.ui.theme.SkyBluePrimary
import com.example.ui.theme.SunshineYellow
import com.example.viewmodel.MainViewModel
import com.example.BuildConfig
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import kotlinx.coroutines.launch

@Composable
fun AtualidadeScreen(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("Escola & Família 🏫", "Conquistas 🏆", "🏫 A minha Escola / Grupo")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .testTag("atualidade_screen")
    ) {
        // --- Enhanced Interactive Top Bar Header ---
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Escola e Família",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF1E293B)
                    )
                    Text(
                        text = "Notícias infantis e conquistas",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF64748B)
                    )
                }

                Surface(
                    shape = CircleShape,
                    color = SunshineYellow,
                    modifier = Modifier.size(40.dp),
                    shadowElevation = 2.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = "🏫", fontSize = 22.sp)
                    }
                }
            }
        }

        // --- Sub-menu Tabs (Fixed Equal Width Row) ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            tabTitles.forEachIndexed { index, title ->
                val isSelected = selectedTabIndex == index
                Surface(
                    onClick = {
                        mainViewModel.playClickSound()
                        selectedTabIndex = index
                    },
                    shape = RoundedCornerShape(14.dp),
                    color = if (isSelected) SunshineYellow else Color.White,
                    modifier = Modifier.weight(1f),
                    shadowElevation = if (isSelected) 4.dp else 1.dp
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp, horizontal = 2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title,
                            fontSize = 10.sp,
                            lineHeight = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                            color = if (isSelected) Color(0xFF3E2723) else Color(0xFF263238),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Visible
                        )
                    }
                }
            }
        }

        // --- Tab Content ---
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            when (selectedTabIndex) {
                0 -> EscolaFamiliaTab()
                1 -> DiarioConquistasTab(mainViewModel = mainViewModel)
                2 -> CommunityWallTab(mainViewModel = mainViewModel)
            }
        }
    }
}

// ==========================================
// SEPARADOR 2: ESCOLA & FAMÍLIA
// ==========================================
@Composable
private fun EscolaFamiliaTab() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("escola_familia_list"),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --- Calendário Escolar Portugal ---
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = SkyBluePrimary.copy(alpha = 0.15f),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = "Calendário",
                                tint = SkyBluePrimary,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Calendário Escolar (Portugal)",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Text(
                                text = "Datas importantes de pausas letivas e avaliações",
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        CalendarEventItem(
                            icon = "🎄",
                            title = "Pausa de Natal",
                            dateRange = "18 de Dezembro a 2 de Janeiro",
                            badgeColor = Color(0xFFE8F5E9),
                            textColor = Color(0xFF2E7D32)
                        )
                        CalendarEventItem(
                            icon = "🎭",
                            title = "Pausa de Carnaval",
                            dateRange = "3 a 5 de Março",
                            badgeColor = Color(0xFFFFF3E0),
                            textColor = Color(0xFFE65100)
                        )
                        CalendarEventItem(
                            icon = "🐣",
                            title = "Pausa de Páscoa",
                            dateRange = "14 a 21 de Abril",
                            badgeColor = Color(0xFFE1F5FE),
                            textColor = Color(0xFF0277BD)
                        )
                        CalendarEventItem(
                            icon = "☀️",
                            title = "Férias de Verão (Início)",
                            dateRange = "13 de Junho (Pré-Escolar e 1º Ciclo) / 20 de Junho",
                            badgeColor = Color(0xFFFFF8E1),
                            textColor = Color(0xFFF57F17)
                        )
                        CalendarEventItem(
                            icon = "📝",
                            title = "Época de Provas de Aferição",
                            dateRange = "Maio e Junho (2º, 5º e 8º Anos)",
                            badgeColor = Color(0xFFF3E5F5),
                            textColor = Color(0xFF7B1FA2)
                        )
                    }
                }
            }
        }

        // --- Dicas para Pais & Hábitos de Estudo ---
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = SunshineYellow.copy(alpha = 0.25f),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.TipsAndUpdates,
                                contentDescription = "Dicas",
                                tint = Color(0xFFE65100),
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Dicas para Pais & Hábitos Saudáveis 💡",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        ParentTipCard(
                            emoji = "📖",
                            title = "Incentivo à Leitura Diária",
                            description = "Ler 15 minutos em conjunto antes de dormir expande o vocabulário, fortalece os laços afetivos e melhora a compreensão escolar."
                        )
                        ParentTipCard(
                            emoji = "⏰",
                            title = "Rotinas de Sono e Foco",
                            description = "Manter horários consistentes de sono (9 a 11 horas) é fundamental para a consolidação da memória e a concentração durante o dia escolar."
                        )
                        ParentTipCard(
                            emoji = "📱",
                            title = "Uso Equilibrado da Tecnologia",
                            description = "Defina horários de ecrã com pausas para brincar ao ar livre. Alterne o uso de conteúdos educativos com o jogo livre e a criatividade."
                        )
                        ParentTipCard(
                            emoji = "🎨",
                            title = "Elogiar o Esforço e a Persistência",
                            description = "Em vez de focar apenas nas notas, elogie a dedicação e o processo de aprendizagem para desenvolver uma mentalidade de crescimento."
                        )
                    }
                }
            }
        }

        // --- Comunidade Educativa & Destaques ---
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = KidStarOrange.copy(alpha = 0.2f),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Groups,
                                contentDescription = "Comunidade",
                                tint = KidStarOrange,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Comunidade Educativa 🏫",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "O envolvimento da família com a escola cria uma rede de apoio segura que potencia a autoconfiança e a felicidade das crianças.",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFFF1F5F9),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("🌳", fontSize = 22.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Projetos Escolares de Sustentabilidade",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Atividades de hortas pedagógicas e reciclagem criativa em família.",
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CalendarEventItem(
    icon: String,
    title: String,
    dateRange: String,
    badgeColor: Color,
    textColor: Color
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        border = BorderStroke(1.dp, badgeColor),
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(icon, fontSize = 20.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = textColor
                )
                Text(
                    text = dateRange,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = textColor.copy(alpha = 0.85f)
                )
            }
        }
    }
}

@Composable
private fun ParentTipCard(
    emoji: String,
    title: String,
    description: String
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Color(0xFFE0E0E0)),
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(emoji, fontSize = 22.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

// ==========================================
// SEPARADOR 3: DIÁRIO DE CONQUISTAS (ANTIGO MENU PAIS)
// ==========================================
@Composable
private fun DiarioConquistasTab(
    mainViewModel: MainViewModel
) {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("ze_traquina_prefs", android.content.Context.MODE_PRIVATE) }
    val userProgress by mainViewModel.userProgress.collectAsState()

    var isUnlocked by remember { mutableStateOf(false) }
    var mathInput by remember { mutableStateOf("") }
    var mathError by remember { mutableStateOf(false) }

    var childNameState by remember { mutableStateOf(userProgress.childName) }
    var ageGroupState by remember { mutableStateOf(userProgress.ageGroup) }
    var timeLimitState by remember { mutableStateOf(userProgress.timeLimitMinutes) }
    var soundEnabledState by remember { mutableStateOf(userProgress.soundEnabled) }

    var miniMaxApiKey by remember { mutableStateOf(prefs.getString("minimax_api_key", "") ?: "") }
    var miniMaxGroupId by remember { mutableStateOf(prefs.getString("minimax_group_id", "412800306253115401")?.ifBlank { "412800306253115401" } ?: "412800306253115401") }
    var miniMaxVoiceId by remember { mutableStateOf(prefs.getString("minimax_voice_id", "moss_audio_042825f2-9a3c-11f0-a95b-4a1f35de8f62")?.ifBlank { "moss_audio_042825f2-9a3c-11f0-a95b-4a1f35de8f62" } ?: "moss_audio_042825f2-9a3c-11f0-a95b-4a1f35de8f62") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("diario_conquistas_list"),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --- Parental Gate Card ---
        if (!isUnlocked) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Bloqueio de Pais",
                            tint = SkyBluePrimary,
                            modifier = Modifier.size(44.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Portal dos Pais & Controlo Parental 🔒",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 17.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )

                        Text(
                            text = "Para aceder às configurações sensíveis, resolve o desafio:",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Quanto é 3 + 4 = ?",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 22.sp,
                            color = SkyBluePrimary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = mathInput,
                            onValueChange = {
                                mathInput = it
                                mathError = false
                            },
                            placeholder = { Text("Responda aqui") },
                            singleLine = true,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth(0.7f)
                        )

                        if (mathError) {
                            Text(
                                text = "Resposta incorreta! Tenta novamente.",
                                color = Color.Red,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                if (mathInput.trim() == "7") {
                                    isUnlocked = true
                                    mathError = false
                                } else {
                                    mathError = true
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)
                        ) {
                            Text(text = "Desbloquear Definições", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // --- Progress Summary & Full Statistics Charts (Always Visible) ---
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "📊 Relatório de Progresso & Conquistas de ${userProgress.childName}",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 17.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "⭐ Estrelas", fontSize = 11.sp, color = Color.Gray)
                            Text(text = "${userProgress.starsCount}", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, color = KidStarOrange)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "🎮 Jogos", fontSize = 11.sp, color = Color.Gray)
                            Text(text = "${userProgress.totalGamesPlayed}", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, color = SkyBluePrimary)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "🔥 Sequência", fontSize = 11.sp, color = Color.Gray)
                            Text(text = "${userProgress.streakDays} dias", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, color = Color(0xFFFF5722))
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Embedded full stats section with charts
                    StatsSection(mainViewModel = mainViewModel)
                }
            }
        }

        // --- Unlocked Settings ---
        if (isUnlocked) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.LockOpen, contentDescription = "Aberto", tint = Color(0xFF4CAF50))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Definições do Controlo Parental",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 17.sp
                            )
                        }

                        // Child Name Input
                        Column {
                            Text(text = "Nome da Criança:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            OutlinedTextField(
                                value = childNameState,
                                onValueChange = { childNameState = it },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp)
                            )
                        }

                        // Age Group Selection
                        Column {
                            Text(text = "Faixa Etária:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                listOf("2-3", "4-5", "6+").forEach { age ->
                                    val isSel = ageGroupState == age
                                    Button(
                                        onClick = { ageGroupState = age },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (isSel) SkyBluePrimary else Color.LightGray
                                        ),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(text = "$age Anos", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    }
                                }
                            }
                        }

                        // Screen Time Limit
                        Column {
                            Text(text = "Limite de Tempo de Ecrã (por sessão):", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                listOf(15, 30, 45, 0).forEach { mins ->
                                    val isSel = timeLimitState == mins
                                    val label = if (mins == 0) "Livre" else "${mins}m"
                                    Button(
                                        onClick = { timeLimitState = mins },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (isSel) SunshineYellow else Color.LightGray
                                        ),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(text = label, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    }
                                }
                            }
                        }

                        // Sound Toggle
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Sons e Narração Ativados", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Switch(
                                checked = soundEnabledState,
                                onCheckedChange = { soundEnabledState = it },
                                colors = SwitchDefaults.colors(checkedThumbColor = SkyBluePrimary)
                            )
                        }

                        // --- MiniMax.io Voice Cloning Integration ---
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "🎙️ Voz Clonada do Cantinho PT (MiniMax.io)",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Configura a API Key e o ID da Voz clonada no MiniMax.io para a mascote falar com a sua voz real!",
                                fontSize = 11.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(top = 2.dp, bottom = 8.dp)
                            )

                            Text(text = "MiniMax API Key:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            OutlinedTextField(
                                value = miniMaxApiKey,
                                onValueChange = { miniMaxApiKey = it },
                                placeholder = { Text("Cole a tua API Key do MiniMax") },
                                singleLine = true,
                                visualTransformation = PasswordVisualTransformation(),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(text = "MiniMax Group ID (Opcional):", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            OutlinedTextField(
                                value = miniMaxGroupId,
                                onValueChange = { miniMaxGroupId = it },
                                placeholder = { Text("Ex: 1812345678") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(text = "Voice ID (ID da Voz do Cantinho PT):", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            OutlinedTextField(
                                value = miniMaxVoiceId,
                                onValueChange = { miniMaxVoiceId = it },
                                placeholder = { Text("Ex: ze_traquina_voice_01") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = {
                                    val gId = miniMaxGroupId.trim().ifBlank { "412800306253115401" }
                                    val vId = miniMaxVoiceId.trim().ifBlank { "moss_audio_042825f2-9a3c-11f0-a95b-4a1f35de8f62" }
                                    prefs.edit()
                                        .putString("minimax_api_key", miniMaxApiKey.trim())
                                        .putString("minimax_group_id", gId)
                                        .putString("minimax_voice_id", vId)
                                        .apply()

                                    mainViewModel.speak("Olá! Teste de som do Cantinho PT com a voz do MiniMax!")
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = SunshineYellow),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Testar Voz do Cantinho PT 🎙️",
                                    color = Color.Black,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        // Save Button
                        Button(
                            onClick = {
                                mainViewModel.updateParentSettings(
                                    childName = childNameState,
                                    ageGroup = ageGroupState,
                                    timeLimitMinutes = timeLimitState,
                                    soundEnabled = soundEnabledState
                                )
                                val gId = miniMaxGroupId.trim().ifBlank { "412800306253115401" }
                                val vId = miniMaxVoiceId.trim().ifBlank { "moss_audio_042825f2-9a3c-11f0-a95b-4a1f35de8f62" }
                                prefs.edit()
                                    .putString("minimax_api_key", miniMaxApiKey.trim())
                                    .putString("minimax_group_id", gId)
                                    .putString("minimax_voice_id", vId)
                                    .apply()

                                mainViewModel.speak("Definições guardadas com sucesso!")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Guardar Alterações 💾", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

data class CommunityPost(
    val id: String,
    val title: String,
    val category: String,
    val textNotification: String,
    val messageOriginal: String,
    val author: String,
    val timestamp: Long
)

@Composable
private fun CommunityWallTab(mainViewModel: MainViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isUnlocked by remember { mutableStateOf(false) }
    var mathInput by remember { mutableStateOf("") }
    var mathError by remember { mutableStateOf(false) }

    val prefs = context.getSharedPreferences("community_wall_prefs", android.content.Context.MODE_PRIVATE)
    var selectedGroup by remember { mutableStateOf(prefs.getString("selected_group", null)) }
    var groups by remember { mutableStateOf<List<String>>(emptyList()) }
    var newGroupName by remember { mutableStateOf("") }

    var newMsgInput by remember { mutableStateOf("") }
    var isModerating by remember { mutableStateOf(false) }
    var rejectionReason by remember { mutableStateOf<String?>(null) }

    var posts by remember { mutableStateOf<List<CommunityPost>>(emptyList()) }

    DisposableEffect(Unit) {
        var groupListener: com.google.firebase.database.ValueEventListener? = null
        var groupRef: com.google.firebase.database.DatabaseReference? = null
        try {
            val db = com.google.firebase.database.FirebaseDatabase.getInstance("https://ze-traquina-app-default-rtdb.europe-west1.firebasedatabase.app")
            groupRef = db.getReference("community_groups")
            groupListener = object : com.google.firebase.database.ValueEventListener {
                override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                    val groupList = snapshot.children.mapNotNull { it.key }
                    groups = groupList
                }
                override fun onCancelled(error: com.google.firebase.database.DatabaseError) {}
            }
            groupRef.addValueEventListener(groupListener)
        } catch (e: Exception) {}
        onDispose { groupListener?.let { groupRef?.removeEventListener(it) } }
    }

    DisposableEffect(selectedGroup) {
        var valueEventListener: com.google.firebase.database.ValueEventListener? = null
        var databaseReference: com.google.firebase.database.DatabaseReference? = null
        if (selectedGroup != null) {
            try {
                val db = com.google.firebase.database.FirebaseDatabase.getInstance("https://ze-traquina-app-default-rtdb.europe-west1.firebasedatabase.app")
                databaseReference = db.getReference("community_wall_posts").child(selectedGroup!!)
                
                valueEventListener = object : com.google.firebase.database.ValueEventListener {
                    override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                        if (!snapshot.exists() || snapshot.childrenCount == 0L) {
                            val postMap = hashMapOf(
                                "title" to "Bem-vindo ao grupo $selectedGroup! 🎉",
                                "category" to "Comunidade",
                                "textNotification" to "Uma nova mensagem de boas-vindas foi publicada no grupo $selectedGroup.",
                                "messageOriginal" to "Olá! Este é o mural do grupo $selectedGroup. Aqui podemos partilhar novidades e discutir assuntos importantes.",
                                "author" to "Equipa Cantinho PT",
                                "timestamp" to System.currentTimeMillis()
                            )
                            databaseReference?.push()?.setValue(postMap)
                        }

                        val newPosts = snapshot.children.mapNotNull { doc ->
                            try {
                                CommunityPost(
                                    id = doc.key ?: "",
                                    title = doc.child("title").value?.toString() ?: "",
                                    category = doc.child("category").value?.toString() ?: "",
                                    textNotification = doc.child("textNotification").value?.toString() ?: "",
                                    messageOriginal = doc.child("messageOriginal").value?.toString() ?: "",
                                    author = doc.child("author").value?.toString() ?: "Pai/Mãe",
                                    timestamp = (doc.child("timestamp").value as? Number)?.toLong() ?: 0L
                                )
                            } catch (e: Exception) {
                                null
                            }
                        }.sortedByDescending { it.timestamp }
                        posts = newPosts
                    }

                    override fun onCancelled(error: com.google.firebase.database.DatabaseError) {
                        android.util.Log.e("AtualidadeScreen", "Database error: ${error.message}")
                    }
                }
                databaseReference.addValueEventListener(valueEventListener)
            } catch (e: Exception) {
                // Fallback to local storage if Firebase is not initialized
                try {
                    val jsonStr = prefs.getString("posts_json_$selectedGroup", "[]") ?: "[]"
                    val jsonArray = org.json.JSONArray(jsonStr)
                    val list = mutableListOf<CommunityPost>()
                    for (i in 0 until jsonArray.length()) {
                        val obj = jsonArray.getJSONObject(i)
                        list.add(
                            CommunityPost(
                                id = obj.optString("id", ""),
                                title = obj.optString("title", ""),
                                category = obj.optString("category", ""),
                                textNotification = obj.optString("textNotification", ""),
                                messageOriginal = obj.optString("messageOriginal", ""),
                                author = obj.optString("author", "Pai/Mãe"),
                                timestamp = obj.optLong("timestamp", System.currentTimeMillis())
                            )
                        )
                    }
                    posts = list
                } catch (e: Exception) {
                    posts = emptyList()
                }
            }
        }
        onDispose {
            valueEventListener?.let { databaseReference?.removeEventListener(it) }
        }
    }

    val savePosts: (List<CommunityPost>) -> Unit = { newPosts ->
        posts = newPosts
        if (selectedGroup != null) {
            try {
                val jsonArray = org.json.JSONArray()
                for (p in newPosts) {
                    val obj = org.json.JSONObject()
                    obj.put("id", p.id)
                    obj.put("title", p.title)
                    obj.put("category", p.category)
                    obj.put("textNotification", p.textNotification)
                    obj.put("messageOriginal", p.messageOriginal)
                    obj.put("author", p.author)
                    obj.put("timestamp", p.timestamp)
                    jsonArray.put(obj)
                }
                prefs.edit().putString("posts_json_$selectedGroup", jsonArray.toString()).apply()
            } catch (e: Exception) {
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (selectedGroup == null) {
            item {
                Text("Selecione a sua Escola / Grupo", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp, top = 8.dp))
            }
            items(groups.size) { index ->
                val group = groups[index]
                Card(
                    modifier = Modifier.fillMaxWidth().clickable {
                        selectedGroup = group
                        prefs.edit().putString("selected_group", group).apply()
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Text(
                        text = group,
                        modifier = Modifier.padding(16.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = newGroupName,
                    onValueChange = { newGroupName = it },
                    placeholder = { Text("Nome da Nova Escola/Grupo") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        if (newGroupName.isNotBlank()) {
                            try {
                                val db = com.google.firebase.database.FirebaseDatabase.getInstance("https://ze-traquina-app-default-rtdb.europe-west1.firebasedatabase.app")
                                db.getReference("community_groups").child(newGroupName.trim()).setValue(true)
                                selectedGroup = newGroupName.trim()
                                prefs.edit().putString("selected_group", selectedGroup).apply()
                                newGroupName = ""
                            } catch (e: Exception) { }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("+ Criar Nova Escola/Grupo", fontWeight = FontWeight.Bold)
                }
            }
        } else {
            item {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
                    IconButton(onClick = { 
                        selectedGroup = null
                        prefs.edit().remove("selected_group").apply()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                    Text(text = "Grupo: $selectedGroup", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }
        if (!isUnlocked) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Bloqueio de Pais",
                            tint = SkyBluePrimary,
                            modifier = Modifier.size(44.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Acesso Restrito a Adultos 🔒",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 17.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )

                        Text(
                            text = "Resolve a operação para aceder ao Mural da Comunidade:",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Quanto é 3 + 4 = ?",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 22.sp,
                            color = SkyBluePrimary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = mathInput,
                            onValueChange = {
                                mathInput = it
                                mathError = false
                            },
                            placeholder = { Text("Responda aqui") },
                            singleLine = true,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth(0.7f)
                        )

                        if (mathError) {
                            Text(
                                text = "Resposta incorreta! Tenta novamente.",
                                color = Color.Red,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                if (mathInput.trim() == "7") {
                                    isUnlocked = true
                                    mathError = false
                                } else {
                                    mathError = true
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary)
                        ) {
                            Text(text = "Desbloquear Mural", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        if (isUnlocked) {
            item {
                Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                border = BorderStroke(1.5.dp, Color(0xFF00897B))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("💬", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Mural da Comunidade de Pais",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 16.sp,
                            color = Color(0xFF004D40)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Escreve uma mensagem para a comunidade. O moderador inteligente analisará e publicará se adequada.",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = newMsgInput,
                        onValueChange = { 
                            newMsgInput = it
                            rejectionReason = null
                        },
                        placeholder = { Text("Ex: AVISO: Reunião de pais amanhã às 18h...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp),
                        shape = RoundedCornerShape(14.dp)
                    )

                    if (rejectionReason != null) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "❌ Mensagem rejeitada pelo moderador: $rejectionReason",
                            color = Color.Red,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            if (newMsgInput.isBlank()) {
                                mainViewModel.speak("Escreve primeiro uma mensagem para publicar!")
                                return@Button
                            }
                            mainViewModel.playClickSound()
                            isModerating = true
                            coroutineScope.launch {
                                val resultJson = moderateMessageWithGemini(newMsgInput)
                                isModerating = false
                                try {
                                    val obj = org.json.JSONObject(resultJson)
                                    val approved = obj.optBoolean("aprovado", false)
                                    if (approved) {
                                        val title = obj.optString("titulo", "Partilha da Comunidade")
                                        val cat = obj.optString("categoria", "Geral")
                                        val notification = obj.optString("texto_notificacao", "Nova mensagem no mural")
                                        val original = obj.optString("mensagem_original", newMsgInput)
                                        
                                        val newPost = CommunityPost(
                                            id = "post_${System.currentTimeMillis()}",
                                            title = title,
                                            category = cat,
                                            textNotification = notification,
                                            messageOriginal = original,
                                            author = "Pai/Mãe",
                                            timestamp = System.currentTimeMillis()
                                        )
                                        // Update locally first for immediate feedback
                                        posts = listOf(newPost) + posts
                                        try {
                                            val db = com.google.firebase.database.FirebaseDatabase.getInstance("https://ze-traquina-app-default-rtdb.europe-west1.firebasedatabase.app")
                                            val postMap = hashMapOf(
                                                "title" to title,
                                                "category" to cat,
                                                "textNotification" to notification,
                                                "messageOriginal" to original,
                                                "author" to "Pai/Mãe",
                                                "timestamp" to System.currentTimeMillis()
                                            )
                                            db.getReference("community_wall_posts").push().setValue(postMap)
                                        } catch (e: Exception) {
                                            savePosts(posts)
                                        }
                                        simulatePushNotification(context, title, notification)
                                        newMsgInput = ""
                                        rejectionReason = null
                                        mainViewModel.speak("Mensagem aprovada e publicada no Mural da Comunidade! 🌟")
                                    } else {
                                        val motivo = obj.optString("motivo_rejeicao", "Conteúdo inadequado para a comunidade.")
                                        rejectionReason = motivo
                                        mainViewModel.speak("Mensagem rejeitada. $motivo")
                                    }
                                } catch (e: Exception) {
                                    val fallbackPost = CommunityPost(
                                        id = "post_${System.currentTimeMillis()}",
                                        title = "Partilha de Pai/Mãe",
                                        category = "Geral",
                                        textNotification = "Nova partilha na comunidade",
                                        messageOriginal = newMsgInput,
                                        author = "Pai/Mãe",
                                        timestamp = System.currentTimeMillis()
                                    )
                                    posts = listOf(fallbackPost) + posts
                                    try {
                                        val db = com.google.firebase.database.FirebaseDatabase.getInstance("https://ze-traquina-app-default-rtdb.europe-west1.firebasedatabase.app")
                                        val postMap = hashMapOf(
                                            "title" to fallbackPost.title,
                                            "category" to fallbackPost.category,
                                            "textNotification" to fallbackPost.textNotification,
                                            "messageOriginal" to fallbackPost.messageOriginal,
                                            "author" to fallbackPost.author,
                                            "timestamp" to fallbackPost.timestamp
                                        )
                                        db.getReference("community_wall_posts").child(selectedGroup!!).push().setValue(postMap)
                                    } catch (e: Exception) {
                                        savePosts(posts)
                                    }
                                    simulatePushNotification(context, fallbackPost.title, fallbackPost.textNotification)
                                    newMsgInput = ""
                                    mainViewModel.speak("Mensagem publicada com sucesso!")
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isModerating,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00897B)),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        if (isModerating) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("A moderar mensagem (IA)...", fontWeight = FontWeight.Bold, color = Color.White)
                        } else {
                            Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = null, tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Publicar Mensagem 🚀", fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📢 Mensagens Publicadas (${posts.size})",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp,
                    color = Color(0xFF37474F)
                )
            }
        }

        items(posts, key = { it.id }) { post ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = when(post.category) {
                                "Eventos & Lazer" -> Color(0xFFFFE082)
                                "Avisos da Escola/Comunidade" -> Color(0xFFB3E5FC)
                                else -> Color(0xFFC8E6C9)
                            }
                        ) {
                            Text(
                                text = post.category,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF263238)
                            )
                        }
                        Text(
                            text = post.author,
                            fontSize = 11.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = post.title,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp,
                        color = Color(0xFF1F2937)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = post.messageOriginal,
                        fontSize = 13.sp,
                        color = Color(0xFF4B5563),
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🔔 Notificação: \"${post.textNotification}\"",
                            fontSize = 11.sp,
                            color = Color(0xFF00796B),
                            fontWeight = FontWeight.SemiBold
                        )

                        IconButton(
                            onClick = {
                                mainViewModel.speak("${post.title}. ${post.messageOriginal}")
                            },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "Ouvir", tint = Color(0xFF00897B), modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }
        }
        }
    }
}
        }

suspend fun moderateMessageWithGemini(userText: String): String = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
    val apiKey = BuildConfig.MY_GEMINI_KEY
    val systemPrompt = """
        És o assistente e moderador do "Mural da Comunidade", uma secção no menu atual.
        A tua tarefa é receber o texto escrito por um pai ou mãe, analisá-lo e devolver uma resposta estritamente em formato JSON.

        Regras de processamento:
        1. MODERAÇÃO DE CONTEÚDO:
           - Verifica se a mensagem é adequada para uma comunidade de pais e famílias.
           - Rejeita conteúdos com linguagem ofensiva, insultos, spam ou vendas comerciais inadequadas.

        2. FORMATO DA RESPOSTA (JSON):
           - Se for adequada: define "aprovado": true, cria um título curto (máx. 5 palavras), gera uma notificação curta para os telemóveis (máx. 10 palavras) e atribui uma categoria ("Eventos & Lazer", "Avisos da Escola/Comunidade" ou "Geral").
           - Se for inadequada: define "aprovado": false e indica o motivo em "motivo_rejeicao".

        Devolve a resposta APENAS nesta estrutura JSON:
        {
          "aprovado": true,
          "titulo": "Título curto",
          "categoria": "Categoria",
          "texto_notificacao": "Texto curto para a notificação no telemóvel",
          "mensagem_original": "Texto completo enviado pelo pai",
          "motivo_rejeicao": null
        }
    """.trimIndent()

    val lower = userText.lowercase()
    val hasBadWords = lower.contains("idiota") || lower.contains("estúpido") || lower.contains("odeio") || 
                      lower.contains("droga") || lower.contains("porra") || lower.contains("merda") ||
                      lower.contains("casino") || lower.contains("venda de armas") || lower.contains("spam")

    if (hasBadWords) {
        val json = org.json.JSONObject()
        json.put("aprovado", false)
        json.put("titulo", org.json.JSONObject.NULL)
        json.put("categoria", org.json.JSONObject.NULL)
        json.put("texto_notificacao", org.json.JSONObject.NULL)
        json.put("mensagem_original", userText)
        json.put("motivo_rejeicao", "A mensagem contém linguagem inadequada ou ofensiva para a comunidade de famílias.")
        return@withContext json.toString()
    }

    // Removed artificial connection check. Direct Gemini call.

    try {
        val client = okhttp3.OkHttpClient.Builder().connectTimeout(15, java.util.concurrent.TimeUnit.SECONDS).build()
        val rootJson = org.json.JSONObject()
        val systemInstr = org.json.JSONObject()
        val sysParts = org.json.JSONArray().put(org.json.JSONObject().put("text", systemPrompt))
        systemInstr.put("parts", sysParts)
        rootJson.put("systemInstruction", systemInstr)

        val contentsArray = org.json.JSONArray().put(
            org.json.JSONObject().put("parts", org.json.JSONArray().put(org.json.JSONObject().put("text", "Analisa e modera esta mensagem de um pai/mãe: $userText")))
        )
        rootJson.put("contents", contentsArray)

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = rootJson.toString().toRequestBody(mediaType)
        val apiKey = BuildConfig.GEMINI_API_KEY.ifBlank { BuildConfig.MY_GEMINI_KEY }
        val models = listOf("gemini-2.0-flash", "gemini-1.5-flash", "gemini-2.5-flash", "gemini-1.5-pro")
        var responseBody: String? = null

        for (model in models) {
            val url = "https://generativelanguage.googleapis.com/v1beta/models/$model:generateContent?key=$apiKey"
            val request = okhttp3.Request.Builder().url(url).post(requestBody).build()
            try {
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        responseBody = response.body?.string()
                    }
                }
            } catch (_: Exception) {}
            if (responseBody != null) break
        }

        if (responseBody != null) {
            val resObj = org.json.JSONObject(responseBody!!)
            val candidates = resObj.optJSONArray("candidates")
            if (candidates != null && candidates.length() > 0) {
                val parts = candidates.getJSONObject(0).optJSONObject("content")?.optJSONArray("parts")
                if (parts != null && parts.length() > 0) {
                    var text = parts.getJSONObject(0).optString("text", "")
                    text = text.replace("```json", "").replace("```", "").trim()
                    if (text.startsWith("{") && text.endsWith("}")) {
                        return@withContext text
                    }
                }
            }
        }
    } catch (e: Exception) {
        // Fallback
    }

    val words = userText.split(" ")
    val title = words.take(4).joinToString(" ").let { if (it.isBlank()) "Partilha Familiar" else it }
    val json = org.json.JSONObject()
    json.put("aprovado", true)
    json.put("titulo", title)
    json.put("categoria", "Geral")
    json.put("texto_notificacao", "Nova mensagem no mural")
    json.put("mensagem_original", userText)
    json.put("motivo_rejeicao", org.json.JSONObject.NULL)
    json.toString()
}

private fun simulatePushNotification(context: android.content.Context, title: String, messageBody: String) {
    val intent = android.content.Intent(context, com.example.MainActivity::class.java)
    intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP)
    val flags = android.app.PendingIntent.FLAG_ONE_SHOT or (if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) android.app.PendingIntent.FLAG_IMMUTABLE else 0)
    val pendingIntent = android.app.PendingIntent.getActivity(
        context, 0, intent,
        flags
    )

    val channelId = "community_wall_channel"
    val notificationBuilder = androidx.core.app.NotificationCompat.Builder(context, channelId)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle("Mural: $title")
        .setContentText(messageBody)
        .setAutoCancel(true)
        .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(pendingIntent)

    val notificationManager = context.getSystemService(android.content.Context.NOTIFICATION_SERVICE) as android.app.NotificationManager

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        val channel = android.app.NotificationChannel(
            channelId,
            "Mural da Comunidade",
            android.app.NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
    }

    notificationManager.notify(kotlin.random.Random.nextInt(), notificationBuilder.build())
}
