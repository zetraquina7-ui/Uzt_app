package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.screens.EducationalCategoryFilter
import com.example.viewmodel.MainViewModel

/**
 * Navigation tab definition replicating the Atlas tab structure.
 */
data class AtlasNavigationTab(
    val title: String,
    val icon: ImageVector,
    val count: Int? = null
)

/**
 * EducationalAtlasTemplate is the EXACT visual and functional replica of the "Atlas do Zé Traquina".
 * It provides:
 * 1. Top Header with circular Back button, large title ("X do Zé Traquina"), subtitle, and Star indicator
 * 2. Mode Navigation Tabs row (same dimensions, TabButton style, colors, and shadows as Atlas)
 * 3. Search bar (same RoundedCornerShape(16.dp), colors, and icons as Atlas)
 * 4. Category filter pills (same RoundedCornerShape(14.dp), colors, and layout as Atlas)
 * 5. Flexible central content area
 */
@Composable
fun EducationalAtlasTemplate(
    title: String,
    subtitle: String,
    viewModel: MainViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    accentColor: Color = Color(0xFF1976D2),
    tabs: List<AtlasNavigationTab> = emptyList(),
    selectedTabIndex: Int = 0,
    onTabSelected: (Int) -> Unit = {},
    searchQuery: String? = null,
    onSearchQueryChange: ((String) -> Unit)? = null,
    searchPlaceholder: String = "Pesquisar...",
    categoryFilters: List<EducationalCategoryFilter> = emptyList(),
    selectedCategoryName: String = "Todos",
    onCategorySelected: (String) -> Unit = {},
    showSearchAndFilters: Boolean = true,
    content: @Composable BoxScope.() -> Unit
) {
    val userProgress by viewModel.userProgress.collectAsState()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        // =========================================================================
        // 1. TOP HEADER (Transparent with Text Shadow, Back button, Title, Subtitle, Voice & Stars)
        // =========================================================================
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
                        .testTag("atlas_template_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = accentColor,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    if (title.isNotBlank()) {
                        Text(
                            text = title,
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = Color.White,
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.70f),
                                    offset = Offset(2f, 2f),
                                    blurRadius = 6f
                                )
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    if (subtitle.isNotBlank()) {
                        Text(
                            text = subtitle,
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.70f),
                                    offset = Offset(1.5f, 1.5f),
                                    blurRadius = 4f
                                )
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(6.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Botão para Ativar / Desativar a Voz do Zé Traquina
                val soundEnabled = userProgress.soundEnabled
                IconButton(
                    onClick = { viewModel.toggleSound() },
                    modifier = Modifier
                        .size(36.dp)
                        .shadow(4.dp, CircleShape)
                        .background(
                            if (soundEnabled) Color.White.copy(alpha = 0.95f) else Color(0xFFFFEBEE),
                            CircleShape
                        )
                        .testTag("atlas_template_voice_toggle")
                ) {
                    Icon(
                        imageVector = if (soundEnabled) Icons.Default.VolumeUp else Icons.Default.VolumeOff,
                        contentDescription = if (soundEnabled) "Desativar Voz do Zé" else "Ativar Voz do Zé",
                        tint = if (soundEnabled) accentColor else Color(0xFFDC2626),
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Star points indicator
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFFFFD54F),
                    shadowElevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFE65100),
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "${userProgress.starsCount}",
                            fontWeight = FontWeight.Black,
                            fontSize = 12.sp,
                            color = Color(0xFFE65100)
                        )
                    }
                }
            }
        }

            // =========================================================================
            // 2. MODE NAVIGATION TABS (IDENTICAL TO ATLAS TABBUTTONS)
            // =========================================================================
            if (tabs.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    tabs.forEachIndexed { index, tab ->
                        val isSelected = selectedTabIndex == index
                        val label = if (tab.count != null) "${tab.title} (${tab.count})" else tab.title

                        AtlasTabButton(
                            title = label,
                            icon = tab.icon,
                            isSelected = isSelected,
                            modifier = Modifier.weight(1f),
                            onClick = { onTabSelected(index) }
                        )
                    }
                }
            }

            if (showSearchAndFilters) {
                Spacer(modifier = Modifier.height(6.dp))
                // Search Bar
                if (onSearchQueryChange != null && searchQuery != null) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = onSearchQueryChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .testTag("atlas_template_search_field"),
                        placeholder = { Text(searchPlaceholder, fontSize = 14.sp) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = accentColor
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { onSearchQueryChange("") }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Limpar"
                                    )
                                }
                            }
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White.copy(alpha = 0.95f),
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            }
                        )
                    )
                }

                // Category Pills (IDENTICAL TO ATLAS CONTINENT PILLS)
                if (categoryFilters.isNotEmpty()) {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(categoryFilters) { category ->
                            val isSelected = selectedCategoryName.equals(category.name, ignoreCase = true)
                            val pillColor = if (isSelected) accentColor else Color.White.copy(alpha = 0.9f)

                            Surface(
                                onClick = { onCategorySelected(category.name) },
                                shape = RoundedCornerShape(14.dp),
                                color = pillColor,
                                contentColor = if (isSelected) Color.White else Color.Black,
                                border = if (isSelected) null else BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.4f)),
                                shadowElevation = if (isSelected) 3.dp else 1.dp
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(category.emoji, fontSize = 14.sp)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = category.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // =========================================================================
            // 4. CENTRAL CONTENT AREA
            // =========================================================================
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                content = content
            )
        }
}

/**
 * TabButton component identical to the Atlas TabButton in WorldMapScreen.kt
 */
@Composable
fun AtlasTabButton(
    title: String,
    icon: ImageVector,
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
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = if (isSelected) Color(0xFFE65100) else Color.Gray
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}
