package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import com.example.ui.navigation.Screen
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.KidStarOrange
import com.example.ui.theme.SkyBluePrimary
import com.example.viewmodel.MainViewModel
import androidx.compose.ui.platform.LocalContext

@Composable
fun MoreScreen(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("ze_traquina_prefs", android.content.Context.MODE_PRIVATE) }
    val userProgress by mainViewModel.userProgress.collectAsState()


    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .testTag("more_screen"),
        contentPadding = PaddingValues(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // --- Progress Summary & Full Statistics Data (Always Visible) ---
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "📊 Relatório de Progresso & Estatísticas do Aluno",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "⭐ Estrelas", fontSize = 12.sp, color = Color.Gray)
                            Text(text = "${userProgress.starsCount}", fontWeight = FontWeight.ExtraBold, fontSize = 24.sp, color = KidStarOrange)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "🎮 Jogos", fontSize = 12.sp, color = Color.Gray)
                            Text(text = "${userProgress.totalGamesPlayed}", fontWeight = FontWeight.ExtraBold, fontSize = 24.sp, color = SkyBluePrimary)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "🔥 Sequência", fontSize = 12.sp, color = Color.Gray)
                            Text(text = "${userProgress.streakDays} dias", fontWeight = FontWeight.ExtraBold, fontSize = 24.sp, color = Color(0xFFFF5722))
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Embedded full charts section
                    StatsSection(mainViewModel = mainViewModel)
                }
            }
        }

        // --- Community Wall Quick Access Card ---
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        mainViewModel.navigateTo(Screen.Atualidade)
                    },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2F1)),
                border = BorderStroke(1.5.dp, Color(0xFF00897B))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("💬", fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Mural da Comunidade 💬", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = Color(0xFF004D40))
                        Text("Partilha avisos, eventos e mensagens com outros pais", fontSize = 12.sp, color = Color(0xFF00695C))
                    }
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color(0xFF00897B))
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Info, contentDescription = "Sobre", tint = SkyBluePrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Sobre o Universo Zé Traquina",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Aplicação 100% segura para crianças. Desenvolvida para estimular a cognição, coordenação motora, linguagem, raciocínio lógico e criatividade na educação infantil.",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Versão 1.0.0 • Livre de anúncios • Ambientes Protegidos",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SkyBluePrimary
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "📧 Contacto e Suporte: zetraquina7@gmail.com",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )


                }
            }
        }
    }


}
