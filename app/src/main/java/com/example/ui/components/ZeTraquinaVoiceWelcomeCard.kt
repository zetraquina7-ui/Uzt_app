package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.ZeTraquinaPuckTTSService
import com.example.viewmodel.MainViewModel

@Composable
fun ZeTraquinaVoiceWelcomeCard(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isSpeaking by ZeTraquinaPuckTTSService.isSpeaking.collectAsState()
    var isExpanded by remember { mutableStateOf(true) }

    val monologueText = "Olá, amigo! Eu sou o Zé Traquina, e este é o meu cantinho de aprender! Aqui dentro há tanta coisa à nossa espera... números para contar, letras para descobrir, e histórias cheias de aventuras! Vem daí, escolhe um cartão, e vamos aprender juntos, com calma e com muita brincadeira. Prontos? Então vamos a isto!"

    val infiniteTransition = rememberInfiniteTransition(label = "pulse_avatar")
    val avatarScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isSpeaking) 1.08f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "avatar_scale"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("ze_traquina_welcome_card"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(2.dp, if (isSpeaking) Color(0xFFF59E0B) else Color(0xFFFFB74D)),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSpeaking) 6.dp else 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFFBEB),
                            Color(0xFFFFFFFF),
                            Color(0xFFFEF3C7)
                        )
                    )
                )
                .padding(14.dp)
        ) {
            // Header: Zé Traquina Avatar + Title + Expand/Collapse Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFFFE082),
                        border = BorderStroke(2.dp, Color(0xFFFFA000)),
                        shadowElevation = 4.dp,
                        modifier = Modifier
                            .size(50.dp)
                            .scale(avatarScale)
                    ) {
                        val req = remember(context) {
                            com.example.util.AppImageLoader.buildMascotRequest(
                                context = context,
                                data = "https://i.imgur.com/O2uwUho.png",
                                placeholderRes = com.example.R.drawable.img_ze_traquina_original
                            )
                        }
                        SafeAsyncImage(
                            model = req,
                            contentDescription = "Zé Traquina",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Cantinho do Zé Traquina",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF78350F)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFFFDE047),
                                border = BorderStroke(1.dp, Color(0xFFCA8A04))
                            ) {
                                Text(
                                    text = "VOZ PUCK 🎙️",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF713F12),
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = "Voz Oficial Ativa em Toda a Aplicação (PT-PT)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFB45309)
                        )
                    }
                }

                IconButton(
                    onClick = { isExpanded = !isExpanded },
                    modifier = Modifier.size(34.dp)
                ) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (isExpanded) "Recolher" else "Expandir",
                        tint = Color(0xFF78350F)
                    )
                }
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(10.dp))

                    // Monologue Balloon
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        color = Color(0xFFFEF3C7).copy(alpha = 0.85f),
                        border = BorderStroke(1.5.dp, Color(0xFFFCD34D))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.FormatQuote,
                                    contentDescription = null,
                                    tint = Color(0xFFD97706),
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Mensagem do Zé Traquina:",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF92400E)
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "\"$monologueText\"",
                                fontSize = 13.sp,
                                lineHeight = 19.sp,
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF451A03)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Big Action Button: Play / Stop Puck Voice
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (isSpeaking) {
                                    ZeTraquinaPuckTTSService.stop()
                                } else {
                                    ZeTraquinaPuckTTSService.speak(context, monologueText)
                                }
                            },
                        shape = RoundedCornerShape(16.dp),
                        color = if (isSpeaking) Color(0xFFFEF3C7) else Color(0xFF4338CA),
                        border = BorderStroke(2.dp, if (isSpeaking) Color(0xFFF59E0B) else Color(0xFF3730A3)),
                        shadowElevation = 3.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            if (isSpeaking) {
                                Icon(
                                    imageVector = Icons.Default.StopCircle,
                                    contentDescription = "Parar Áudio",
                                    tint = Color(0xFFD97706),
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "A Ouvir o Zé Traquina (Voz Puck)...",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color(0xFF92400E)
                                    )
                                    Text(
                                        text = "Toca para parar a reprodução",
                                        fontSize = 10.sp,
                                        color = Color(0xFFB45309),
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            } else {
                                Icon(
                                    imageVector = Icons.Default.SpatialAudio,
                                    contentDescription = "Ouvir o Zé Traquina",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "Ouvir o Zé Traquina (Voz Puck Oficial)",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "Voz jovem e alegre de rapaz em Português de Portugal",
                                        fontSize = 10.sp,
                                        color = Color(0xFFC7D2FE),
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Info footer: Confirmation of Global Puck Voice
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFECFDF5),
                        border = BorderStroke(1.dp, Color(0xFF10B981).copy(alpha = 0.5f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "✨", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "A voz Puck é agora a narradora oficial em todos os cartões, jogos de soletrar, divisões silábicas e quizzes da aplicação.",
                                fontSize = 11.sp,
                                lineHeight = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF065F46)
                            )
                        }
                    }
                }
            }
        }
    }
}
