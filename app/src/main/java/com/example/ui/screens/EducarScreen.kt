package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.FundoApp
import com.example.ui.components.ScreenHeader
import com.example.viewmodel.MainViewModel

private data class EducarSubCategory(
    val title: String,
    val emoji: String,
    val gradient: List<Color>
)

private val educarSubCategories = listOf(
    EducarSubCategory("Aprender", "📚", listOf(Color(0xFF4F46E5), Color(0xFF9333EA))),
    EducarSubCategory("Jogos", "🎮", listOf(Color(0xFF16A34A), Color(0xFFEAB308))),
    EducarSubCategory("Fichas", "📝", listOf(Color(0xFFE11D48), Color(0xFFF43F5E)))
)

@Composable
fun EducarScreen(
    viewModel: MainViewModel
) {
    // Submenu tab state: 0 = Aprender (selected by default), 1 = Jogos, 2 = Fichas
    var selectedTab by remember { mutableStateOf(0) }
    var isTopicOpen by remember { mutableStateOf(false) }
    var isGameOpen by remember { mutableStateOf(false) }
    var isWorksheetOpen by remember { mutableStateOf(false) }
    var selectedYear by remember { mutableStateOf<Int?>(1) }

    val isChildScreenOpen = when (selectedTab) {
        0 -> isTopicOpen
        1 -> isGameOpen
        2 -> isWorksheetOpen
        else -> false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .testTag("educar_screen")
    ) {
            if (!isChildScreenOpen) {
                // --- 1. CABEÇALHO EM CIMA (VISÍVEL NO HUB PRINCIPAL) ---
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 4.dp, bottom = 2.dp)
                ) {
                    when (selectedTab) {
                        0 -> ScreenHeader(
                            title = "Educar • Aprender",
                            subtitle = "Escola Mágica do Zé Traquina",
                            icon = "📚",
                            gradientColors = listOf(Color(0xFF4F46E5), Color(0xFF9333EA))
                        )
                        1 -> ScreenHeader(
                            title = "Educar • Jogos",
                            subtitle = "Parquinho dos Jogos com o Zé!",
                            icon = "🎮",
                            gradientColors = listOf(Color(0xFF16A34A), Color(0xFFEAB308))
                        )
                        2 -> ScreenHeader(
                            title = "Educar - Fichas",
                            subtitle = "Fichas de trabalho",
                            icon = "📝",
                            gradientColors = listOf(Color(0xFFE11D48), Color(0xFFF43F5E))
                        )
                    }
                }

                // --- 2. SUBMENUS DEBAIXO DO CABEÇALHO + BOTÃO VOZ DO ZÉ ---
                val userProgress by viewModel.userProgress.collectAsState()
                val soundEnabled = userProgress.soundEnabled

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    EducarSubmenuBar(
                        selectedTab = selectedTab,
                        onTabSelected = { selectedTab = it },
                        modifier = Modifier.weight(1f)
                    )

                    Surface(
                        onClick = { viewModel.toggleSound() },
                        shape = RoundedCornerShape(8.dp),
                        color = if (soundEnabled) Color(0xFFE0E7FF) else Color(0xFFFFEBEE),
                        border = BorderStroke(1.dp, if (soundEnabled) Color(0xFF6366F1) else Color(0xFFEF4444)),
                        shadowElevation = 0.5.dp,
                        modifier = Modifier
                            .height(30.dp)
                            .testTag("btn_toggle_ze_voice_educar")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            Icon(
                                imageVector = if (soundEnabled) Icons.Default.VolumeUp else Icons.Default.VolumeOff,
                                contentDescription = if (soundEnabled) "Desativar Voz do Zé" else "Ativar Voz do Zé",
                                tint = if (soundEnabled) Color(0xFF4F46E5) else Color(0xFFDC2626),
                                modifier = Modifier.size(13.dp)
                            )
                            Text(
                                text = if (soundEnabled) "Voz ON" else "Voz OFF",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (soundEnabled) Color(0xFF312E81) else Color(0xFF991B1B)
                            )
                        }
                    }
                }

                if (selectedTab == 2) {
                    // --- 3. SELETOR DE ANO GLOBAL (UNIFICADO) ---
                    YearSelectorBar(
                        selectedYear = selectedYear,
                        onYearSelected = { selectedYear = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 2.dp)
                    )
                }
            }

            // --- 4. CONTEÚDO NO CENTRO ---
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                when (selectedTab) {
                    0 -> LearnScreen(
                        viewModel = viewModel,
                        showHeader = false,
                        selectedYear = null,
                        onTopicStateChanged = { isOpen -> isTopicOpen = isOpen }
                    )
                    1 -> GamesScreen(
                        mainViewModel = viewModel, 
                        showHeader = false,
                        selectedYear = null,
                        onGameStateChanged = { isOpen -> isGameOpen = isOpen }
                    )
                    2 -> WorksheetsScreen(
                        viewModel = viewModel, 
                        showHeader = false,
                        initialYearId = selectedYear,
                        onWorksheetStateChanged = { isOpen -> isWorksheetOpen = isOpen }
                    )
                }
            }
        }
    }

@Composable
private fun YearSelectorBar(
    selectedYear: Int?,
    onYearSelected: (Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        val years = listOf(1, 2, 3, 4)
        
        years.forEach { year ->
            val isSelected = selectedYear == year
            val bgColor = if (isSelected) Color(0xFF2563EB) else Color.White.copy(alpha = 0.95f)
            val textColor = if (isSelected) Color.White else Color(0xFF2563EB)
            val borderColor = if (isSelected) Color(0xFF1D4ED8) else Color(0xFFE2E8F0)
            
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(30.dp)
                    .clickable { 
                        onYearSelected(year) 
                    }
                    .testTag("year_selector_button_$year"),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = bgColor),
                border = BorderStroke(if (isSelected) 1.5.dp else 1.dp, borderColor),
                elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 2.dp else 1.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$year.º Ano",
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                        color = textColor,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun EducarSubmenuBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        educarSubCategories.forEachIndexed { index, subCategory ->
            val isSelected = selectedTab == index
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
                    .clickable { onTabSelected(index) }
                    .testTag(
                        when (index) {
                            0 -> "submenu_tab_aprender"
                            1 -> "submenu_tab_jogos"
                            else -> "submenu_tab_fichas"
                        }
                    ),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) Color.Transparent else Color.White.copy(alpha = 0.95f)
                ),
                border = BorderStroke(
                    width = if (isSelected) 1.5.dp else 1.dp,
                    color = if (isSelected) subCategory.gradient.first() else Color.LightGray.copy(alpha = 0.4f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 3.dp else 1.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .then(
                            if (isSelected) Modifier.background(
                                Brush.horizontalGradient(subCategory.gradient)
                            ) else Modifier
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(horizontal = 6.dp)
                    ) {
                        Text(
                            text = subCategory.emoji,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                        Text(
                            text = subCategory.title,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                            color = if (isSelected) Color.White else Color(0xFF334155)
                        )
                    }
                }
            }
        }
    }
}
