package com.example.ui.screens

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.FundoApp
import com.example.ui.components.ScreenHeader
import com.example.ui.components.ZeChatHistoryView
import com.example.ui.components.ZeLiveAvatarComponent
import com.example.util.PreviewConfig
import com.example.viewmodel.ChatViewModel
import com.example.viewmodel.MainViewModel
import com.example.viewmodel.ZeAITab

@androidx.media3.common.util.UnstableApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZeAIScreen(
    mainViewModel: MainViewModel,
    chatViewModel: ChatViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isPreviewMode = androidx.compose.ui.platform.LocalInspectionMode.current || PreviewConfig.isInPreview()
    val selectedTab by chatViewModel.selectedTab.collectAsState()

    DisposableEffect(Unit) {
        Log.d("ZeAIScreen", "ZeAIScreen initialized")
        onDispose {
            mainViewModel.stopSpeaking()
            mainViewModel.stopListening()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(horizontal = 8.dp, vertical = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
            // ==========================================
            // 1. Header Superior
            // ==========================================
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ScreenHeader(
                    title = "Universo Zé Traquina",
                    subtitle = "ZéAI Amigo Virtual",
                    icon = "🤖",
                    gradientColors = listOf(Color(0xFFDB2777), Color(0xFF9333EA)),
                    modifier = Modifier.weight(1f)
                )
                
                // Remover indicador artificial de Offline/Online baseado em API Key
                // O estado da conexão agora depende puramente da resposta de rede do Gemini.
            }

            Spacer(modifier = Modifier.height(6.dp))

            // ==========================================
            // 2. Seletor de Abas ZéAI: Conversar / Histórico
            // ==========================================
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .height(46.dp)
                    .shadow(4.dp, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                color = Color.White.copy(alpha = 0.95f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Aba 1: Conversar
                    val isConversar = selectedTab == ZeAITab.CONVERSAR
                    Surface(
                        onClick = {
                            mainViewModel.playClickSound()
                            chatViewModel.selectTab(ZeAITab.CONVERSAR)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .testTag("tab_zeai_conversar"),
                        shape = RoundedCornerShape(20.dp),
                        color = if (isConversar) Color(0xFF9333EA) else Color.Transparent
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Mic,
                                contentDescription = null,
                                tint = if (isConversar) Color.White else Color(0xFF6B7280),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Conversar",
                                color = if (isConversar) Color.White else Color(0xFF4B5563),
                                fontSize = 14.sp,
                                fontWeight = if (isConversar) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    // Aba 2: Histórico
                    val isHistorico = selectedTab == ZeAITab.HISTORICO
                    Surface(
                        onClick = {
                            mainViewModel.playClickSound()
                            chatViewModel.selectTab(ZeAITab.HISTORICO)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .testTag("tab_zeai_historico"),
                        shape = RoundedCornerShape(20.dp),
                        color = if (isHistorico) Color(0xFF9333EA) else Color.Transparent
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.History,
                                contentDescription = null,
                                tint = if (isHistorico) Color.White else Color(0xFF6B7280),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Histórico",
                                color = if (isHistorico) Color.White else Color(0xFF4B5563),
                                fontSize = 14.sp,
                                fontWeight = if (isHistorico) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ==========================================
            // 3. Conteúdo da Aba Ativa
            // ==========================================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                AnimatedContent(
                    targetState = selectedTab,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "zeai_tab_content",
                    modifier = Modifier.fillMaxSize()
                ) { tab ->
                    when (tab) {
                        ZeAITab.CONVERSAR -> {
                            ZeLiveAvatarComponent(
                                mainViewModel = mainViewModel,
                                chatViewModel = chatViewModel,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        ZeAITab.HISTORICO -> {
                            ZeChatHistoryView(
                                mainViewModel = mainViewModel,
                                chatViewModel = chatViewModel,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
}
