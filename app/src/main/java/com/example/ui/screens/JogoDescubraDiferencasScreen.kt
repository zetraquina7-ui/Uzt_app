package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import coil.request.ImageRequest
import com.example.ui.components.SafeAsyncImage
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.MascotEmotion
import com.example.ui.components.ZeTraquinaMascot
import com.example.ui.theme.MintGreen
import com.example.ui.theme.PreviewAppTheme
import com.example.ui.theme.SkyBluePrimary
import com.example.ui.theme.SunshineYellow
import com.example.viewmodel.MainViewModel
import kotlin.math.pow
import kotlin.math.sqrt

enum class DifferenceType {
    CIRCLE, STAR, TRIANGLE, APPLE, FLOWER, SUN, HEART, MOON, CLOUD, DIAMOND, CROWN, BUTTERFLY, FISH, MUSIC_NOTE
}

data class Difference(
    val id: Int,
    val offset: Offset,
    val type: DifferenceType,
    var isFound: Boolean = false,
    val label: String = ""
)

data class SpotDifferenceLevel(
    val id: Int,
    val name: String,
    val subtitle: String,
    val emoji: String,
    val bgGradient: List<Color>,
    val imageResId: Int?,
    val differences: List<Difference>
)

@Composable
fun JogoDescubraDiferencasScreen(
    mainViewModel: MainViewModel? = null,
    onBack: () -> Unit
) {
    val isPreview = LocalInspectionMode.current

    val levels = remember {
        listOf(
            SpotDifferenceLevel(
                id = 1,
                name = "Jardim dos Sonhos",
                subtitle = "Encontra as 4 diferenças no jardim mágico!",
                emoji = "🌸",
                bgGradient = listOf(Color(0xFFE8F5E9), Color(0xFFC8E6C9)),
                imageResId = R.drawable.img_diff_garden,
                differences = listOf(
                    Difference(0, Offset(0.22f, 0.28f), DifferenceType.SUN, label = "Sol Brilhante"),
                    Difference(1, Offset(0.76f, 0.32f), DifferenceType.FLOWER, label = "Flor Vermelha"),
                    Difference(2, Offset(0.32f, 0.68f), DifferenceType.APPLE, label = "Maçã Madura"),
                    Difference(3, Offset(0.80f, 0.74f), DifferenceType.BUTTERFLY, label = "Borboleta Azul")
                )
            ),
            SpotDifferenceLevel(
                id = 2,
                name = "Aventura Espacial",
                subtitle = "Encontra as 5 diferenças nas galáxias!",
                emoji = "🚀",
                bgGradient = listOf(Color(0xFF1A237E), Color(0xFF0D47A1)),
                imageResId = R.drawable.img_diff_space,
                differences = listOf(
                    Difference(0, Offset(0.25f, 0.22f), DifferenceType.MOON, label = "Super Lua"),
                    Difference(1, Offset(0.75f, 0.25f), DifferenceType.STAR, label = "Estrela Cadente"),
                    Difference(2, Offset(0.20f, 0.58f), DifferenceType.DIAMOND, label = "Cristal Cósmico"),
                    Difference(3, Offset(0.68f, 0.65f), DifferenceType.CIRCLE, label = "Planeta Anel"),
                    Difference(4, Offset(0.48f, 0.82f), DifferenceType.HEART, label = "Coração Espacial")
                )
            ),
            SpotDifferenceLevel(
                id = 3,
                name = "Quinta Alegre",
                subtitle = "Encontra as 5 diferenças no celeiro!",
                emoji = "🚜",
                bgGradient = listOf(Color(0xFFFFF8E1), Color(0xFFFFECB3)),
                imageResId = R.drawable.img_diff_farm,
                differences = listOf(
                    Difference(0, Offset(0.18f, 0.30f), DifferenceType.SUN, label = "Sol Radiante"),
                    Difference(1, Offset(0.70f, 0.22f), DifferenceType.CLOUD, label = "Nuvem Mágica"),
                    Difference(2, Offset(0.35f, 0.55f), DifferenceType.FLOWER, label = "Florzinhas"),
                    Difference(3, Offset(0.82f, 0.62f), DifferenceType.APPLE, label = "Maçã Vermelha"),
                    Difference(4, Offset(0.40f, 0.80f), DifferenceType.HEART, label = "Coração Amigo")
                )
            ),
            SpotDifferenceLevel(
                id = 4,
                name = "Castelo Encantado",
                subtitle = "Encontra as 5 diferenças no castelo real!",
                emoji = "👑",
                bgGradient = listOf(Color(0xFFEDE7F6), Color(0xFFD1C4E9)),
                imageResId = R.drawable.img_diff_castle,
                differences = listOf(
                    Difference(0, Offset(0.20f, 0.20f), DifferenceType.CROWN, label = "Coroa Real"),
                    Difference(1, Offset(0.80f, 0.24f), DifferenceType.MOON, label = "Lua Mágica"),
                    Difference(2, Offset(0.26f, 0.58f), DifferenceType.DIAMOND, label = "Gema Preciosa"),
                    Difference(3, Offset(0.74f, 0.62f), DifferenceType.STAR, label = "Estrela Real"),
                    Difference(4, Offset(0.50f, 0.82f), DifferenceType.HEART, label = "Coração do Rei")
                )
            ),
            SpotDifferenceLevel(
                id = 5,
                name = "Oceano Divertido",
                subtitle = "Encontra as 5 diferenças no fundo do mar!",
                emoji = "🐠",
                bgGradient = listOf(Color(0xFFE0F7FA), Color(0xFFB2EBF2)),
                imageResId = R.drawable.img_diff_ocean,
                differences = listOf(
                    Difference(0, Offset(0.22f, 0.25f), DifferenceType.FISH, label = "Peixinho Dourado"),
                    Difference(1, Offset(0.78f, 0.28f), DifferenceType.STAR, label = "Estrela-do-Mar"),
                    Difference(2, Offset(0.30f, 0.55f), DifferenceType.CIRCLE, label = "Bolha Mágica"),
                    Difference(3, Offset(0.72f, 0.68f), DifferenceType.DIAMOND, label = "Pérola Brilhante"),
                    Difference(4, Offset(0.48f, 0.80f), DifferenceType.HEART, label = "Coração Aquático")
                )
            ),
            SpotDifferenceLevel(
                id = 6,
                name = "Super Zé Traquina",
                subtitle = "Encontra as 5 diferenças no mundo do Zé!",
                emoji = "⭐",
                bgGradient = listOf(Color(0xFFFFF3E0), Color(0xFFFFE0B2)),
                imageResId = R.drawable.img_diff_garden,
                differences = listOf(
                    Difference(0, Offset(0.20f, 0.24f), DifferenceType.MUSIC_NOTE, label = "Nota Musical"),
                    Difference(1, Offset(0.80f, 0.22f), DifferenceType.STAR, label = "Super Estrela"),
                    Difference(2, Offset(0.28f, 0.58f), DifferenceType.HEART, label = "Coração Traquina"),
                    Difference(3, Offset(0.72f, 0.64f), DifferenceType.CROWN, label = "Coroa Especial"),
                    Difference(4, Offset(0.50f, 0.82f), DifferenceType.SUN, label = "Sorriso do Zé")
                )
            ),
            SpotDifferenceLevel(
                id = 7,
                name = "Festa de Aniversário",
                subtitle = "Encontra as 5 diferenças na festa de aniversário!",
                emoji = "🎂",
                bgGradient = listOf(Color(0xFFFCE4EC), Color(0xFFF8BBD0)),
                imageResId = R.drawable.img_diff_farm,
                differences = listOf(
                    Difference(0, Offset(0.24f, 0.22f), DifferenceType.SUN, label = "Sol Brilhante"),
                    Difference(1, Offset(0.72f, 0.26f), DifferenceType.STAR, label = "Estrela Festiva"),
                    Difference(2, Offset(0.30f, 0.60f), DifferenceType.CROWN, label = "Chapéu de Festa"),
                    Difference(3, Offset(0.80f, 0.66f), DifferenceType.HEART, label = "Balão de Coração"),
                    Difference(4, Offset(0.48f, 0.82f), DifferenceType.APPLE, label = "Doce de Maçã")
                )
            ),
            SpotDifferenceLevel(
                id = 8,
                name = "Parque de Diversões",
                subtitle = "Encontra as 5 diferenças na feira popular!",
                emoji = "🎡",
                bgGradient = listOf(Color(0xFFE0F2F1), Color(0xFFB2DFDB)),
                imageResId = R.drawable.img_diff_castle,
                differences = listOf(
                    Difference(0, Offset(0.18f, 0.22f), DifferenceType.MOON, label = "Lua de Algodão"),
                    Difference(1, Offset(0.82f, 0.28f), DifferenceType.STAR, label = "Estrela de Néon"),
                    Difference(2, Offset(0.28f, 0.54f), DifferenceType.DIAMOND, label = "Cristal do Carrossel"),
                    Difference(3, Offset(0.72f, 0.62f), DifferenceType.CROWN, label = "Coroa do Castelo"),
                    Difference(4, Offset(0.50f, 0.80f), DifferenceType.HEART, label = "Coração Doce")
                )
            ),
            SpotDifferenceLevel(
                id = 9,
                name = "Floresta Secreta",
                subtitle = "Encontra as 5 diferenças sob a copa das árvores!",
                emoji = "🌲",
                bgGradient = listOf(Color(0xFFE1F5FE), Color(0xFFB3E5FC)),
                imageResId = R.drawable.img_diff_space,
                differences = listOf(
                    Difference(0, Offset(0.25f, 0.18f), DifferenceType.SUN, label = "Sol Oculto"),
                    Difference(1, Offset(0.76f, 0.24f), DifferenceType.CLOUD, label = "Nuvem Passageira"),
                    Difference(2, Offset(0.22f, 0.60f), DifferenceType.FLOWER, label = "Flor Rara"),
                    Difference(3, Offset(0.80f, 0.68f), DifferenceType.BUTTERFLY, label = "Asas de Fada"),
                    Difference(4, Offset(0.46f, 0.84f), DifferenceType.HEART, label = "Folha de Coração")
                )
            ),
            SpotDifferenceLevel(
                id = 10,
                name = "Inverno Gelado",
                subtitle = "Encontra as 5 diferenças na neve fofa!",
                emoji = "⛄",
                bgGradient = listOf(Color(0xFFFFF9C4), Color(0xFFFFF59D)),
                imageResId = R.drawable.img_diff_ocean,
                differences = listOf(
                    Difference(0, Offset(0.20f, 0.20f), DifferenceType.SUN, label = "Sol de Inverno"),
                    Difference(1, Offset(0.80f, 0.25f), DifferenceType.STAR, label = "Cristal de Gelo"),
                    Difference(2, Offset(0.26f, 0.55f), DifferenceType.CROWN, label = "Coroa de Neve"),
                    Difference(3, Offset(0.74f, 0.65f), DifferenceType.DIAMOND, label = "Diamante Brilhante"),
                    Difference(4, Offset(0.48f, 0.82f), DifferenceType.HEART, label = "Coração Quente")
                )
            ),
            SpotDifferenceLevel(
                id = 11,
                name = "Planeta Alien",
                subtitle = "Encontra as 5 diferenças galácticas!",
                emoji = "🪐",
                bgGradient = listOf(Color(0xFFEDE7F6), Color(0xFFD1C4E9)),
                imageResId = R.drawable.img_diff_space,
                differences = listOf(
                    Difference(0, Offset(0.28f, 0.22f), DifferenceType.MOON, label = "Lua Violeta"),
                    Difference(1, Offset(0.72f, 0.18f), DifferenceType.STAR, label = "Estrela Cadente"),
                    Difference(2, Offset(0.35f, 0.58f), DifferenceType.DIAMOND, label = "Cristal Cósmico"),
                    Difference(3, Offset(0.82f, 0.62f), DifferenceType.CROWN, label = "Coroa Alien"),
                    Difference(4, Offset(0.50f, 0.85f), DifferenceType.MUSIC_NOTE, label = "Sinal Rádio")
                )
            ),
            SpotDifferenceLevel(
                id = 12,
                name = "Castelo das Fadas",
                subtitle = "Encontra as 5 diferenças encantadas!",
                emoji = "🏰",
                bgGradient = listOf(Color(0xFFFCE4EC), Color(0xFFF8BBD0)),
                imageResId = R.drawable.img_diff_castle,
                differences = listOf(
                    Difference(0, Offset(0.18f, 0.15f), DifferenceType.SUN, label = "Sol Dourado"),
                    Difference(1, Offset(0.85f, 0.20f), DifferenceType.BUTTERFLY, label = "Fada Alada"),
                    Difference(2, Offset(0.25f, 0.65f), DifferenceType.HEART, label = "Amor Mágico"),
                    Difference(3, Offset(0.78f, 0.70f), DifferenceType.FLOWER, label = "Rosa Encantada"),
                    Difference(4, Offset(0.52f, 0.80f), DifferenceType.CROWN, label = "Tiara Real")
                )
            )
        )
    }

    var currentLevelIndex by remember { mutableIntStateOf(0) }
    val safeLevelIndex = currentLevelIndex.coerceIn(0, levels.lastIndex)
    val currentLevel = levels[safeLevelIndex]

    val activeDifferences = remember(safeLevelIndex) {
        mutableStateListOf(*currentLevel.differences.map { it.copy() }.toTypedArray())
    }

    val foundCount by remember { derivedStateOf { activeDifferences.count { it.isFound } } }
    val totalDifferences = activeDifferences.size
    val isLevelCompleted = foundCount == totalDifferences && totalDifferences > 0
    var showHintFlash by remember { mutableStateOf(false) }

    LaunchedEffect(isLevelCompleted, safeLevelIndex) {
        if (isLevelCompleted) {
            if (!isPreview) {
                mainViewModel?.playVictorySound()
                mainViewModel?.addStars(10)
            }
        }
    }

    val onDifferenceFound: (Int) -> Unit = { index ->
        if (index in activeDifferences.indices && !activeDifferences[index].isFound) {
            activeDifferences[index] = activeDifferences[index].copy(isFound = true)
            if (!isPreview) {
                mainViewModel?.playStarSound()
                mainViewModel?.addStars(2)
            }
        }
    }

    val onMissTap: (Offset) -> Unit = { _ ->
        if (!isPreview) {
            mainViewModel?.playClickSound()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Header Bar ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        mainViewModel?.playClickSound()
                        onBack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SunshineYellow),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.Black,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Jogos",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFFFFF9C4),
                    border = BorderStroke(1.5.dp, Color(0xFFFFB300))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Estrelas",
                            tint = Color(0xFFFF8F00),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$foundCount / $totalDifferences Encontradas",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF3E2723)
                        )
                    }
                }
            }

            // --- Level Selector Tabs ---
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                itemsIndexed(levels) { index, level ->
                    val isSelected = index == safeLevelIndex
                    val isPassed = index < safeLevelIndex

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = when {
                            isSelected -> SkyBluePrimary
                            isPassed -> MintGreen
                            else -> Color(0xFFE2E8F0)
                        },
                        border = if (isSelected) BorderStroke(2.dp, Color(0xFF0288D1)) else null,
                        modifier = Modifier.clickable {
                            mainViewModel?.playClickSound()
                            currentLevelIndex = index
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${level.emoji} Nível ${level.id}",
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold,
                                color = if (isSelected || isPassed) Color.White else Color(0xFF334155)
                            )
                            if (isPassed) {
                                Text(
                                    text = " ✓",
                                    fontSize = 10.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // --- Level Title & Tools Banner ---
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${currentLevel.emoji} Nível ${currentLevel.id}: ${currentLevel.name}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF0F172A)
                        )
                        Text(
                            text = currentLevel.subtitle,
                            fontSize = 10.sp,
                            color = Color(0xFF64748B)
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Button(
                            onClick = {
                                val unfoundIndex = activeDifferences.indexOfFirst { !it.isFound }
                                if (unfoundIndex != -1) {
                                    activeDifferences[unfoundIndex] = activeDifferences[unfoundIndex].copy(isFound = true)
                                    if (!isPreview) {
                                        mainViewModel?.playStarSound()
                                        mainViewModel?.addStars(2)
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SunshineYellow),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                            modifier = Modifier.padding(end = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = "Dica",
                                tint = Color.Black,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "Dica",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }

                        IconButton(
                            onClick = {
                                mainViewModel?.playClickSound()
                                activeDifferences.indices.forEach { i ->
                                    activeDifferences[i] = activeDifferences[i].copy(isFound = false)
                                }
                            },
                            modifier = Modifier.size(30.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Reiniciar",
                                tint = SkyBluePrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            // --- Found Items Checklist Chips ---
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                itemsIndexed(activeDifferences) { _, diff ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (diff.isFound) Color(0xFFDCFCE7) else Color(0xFFF1F5F9),
                        border = BorderStroke(1.dp, if (diff.isFound) Color(0xFF22C55E) else Color(0xFFCBD5E1))
                    ) {
                        Text(
                            text = if (diff.isFound) "✓ ${diff.label}" else "🔎 ${diff.label}",
                            fontSize = 10.sp,
                            fontWeight = if (diff.isFound) FontWeight.Bold else FontWeight.Normal,
                            color = if (diff.isFound) Color(0xFF15803D) else Color(0xFF64748B),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            // --- Two Interactive Scenes (Both respond to touches) ---
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                // Top Reference Image Box
                SpotDifferenceImageBox(
                    title = "1. Imagem Original 🖼️ (Toca aqui!)",
                    isTopImage = true,
                    level = currentLevel,
                    differences = activeDifferences,
                    onDifferenceFound = onDifferenceFound,
                    onMissTap = onMissTap,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Bottom Differences Play Image Box
                SpotDifferenceImageBox(
                    title = "2. Imagem com Diferenças 🔎 (Toca aqui!)",
                    isTopImage = false,
                    level = currentLevel,
                    differences = activeDifferences,
                    onDifferenceFound = onDifferenceFound,
                    onMissTap = onMissTap,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )
            }

            // --- Level Completion Card Banner ---
            AnimatedVisibility(
                visible = isLevelCompleted,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MintGreen),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ZeTraquinaMascot(
                            size = 46.dp,
                            showSpeechBubble = false,
                            emotion = MascotEmotion.CELEBRATING,
                            triggerEmotionKey = safeLevelIndex,
                            onInteract = {}
                        )

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp)
                        ) {
                            Text(
                                text = "PARABÉNS! 🎉 Nível ${currentLevel.id} Completo!",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF0F172A)
                            )
                            Text(
                                text = "Descobriste tudo! Ganhaste +10 ⭐ Estrelas!",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF14532D)
                            )
                        }

                        Button(
                            onClick = {
                                mainViewModel?.playClickSound()
                                if (safeLevelIndex < levels.lastIndex) {
                                    currentLevelIndex = safeLevelIndex + 1
                                } else {
                                    currentLevelIndex = 0
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SunshineYellow),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = if (safeLevelIndex < levels.lastIndex) "Seguinte ➔" else "Reiniciar 🔄",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }

        if (isLevelCompleted) {
            ConfettiEffect()
        }
    }
}

@Composable
private fun SpotDifferenceImageBox(
    title: String,
    isTopImage: Boolean,
    level: SpotDifferenceLevel,
    differences: List<Difference>,
    onDifferenceFound: (Int) -> Unit,
    onMissTap: (Offset) -> Unit,
    modifier: Modifier = Modifier
) {
    var missOffset by remember { mutableStateOf<Offset?>(null) }
    var missTrigger by remember { mutableIntStateOf(0) }

    LaunchedEffect(missTrigger) {
        if (missOffset != null) {
            delay(450)
            missOffset = null
        }
    }

    val latestDifferences = rememberUpdatedState(differences)

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(2.dp, SkyBluePrimary),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(level.bgGradient))
        ) {
            // Background illustration scene
            var imageFailed by remember(level.id) { mutableStateOf(false) }
            val context = LocalContext.current

            if (level.imageResId != null) {
                Image(
                    painter = painterResource(id = level.imageResId),
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawDefaultSceneArt(level.id, size)
                }
            }

            // Interactive touch detection surface
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(level.id) {
                        detectTapGestures { tapOffset ->
                            if (size.width > 0 && size.height > 0) {
                                val boxW = size.width.toFloat()
                                val boxH = size.height.toFloat()
                                val currentDiffs = latestDifferences.value

                                // Precise pixel hit threshold (minimum ~65px or 18% of smaller dimension)
                                val hitThresholdPx = maxOf(65f, minOf(boxW, boxH) * 0.18f)

                                var closestIndex = -1
                                var minDistance = Float.MAX_VALUE

                                currentDiffs.forEachIndexed { index, diff ->
                                    if (!diff.isFound) {
                                        val targetPxX = diff.offset.x * boxW
                                        val targetPxY = diff.offset.y * boxH
                                        val distPx = sqrt(
                                            (tapOffset.x - targetPxX).pow(2) +
                                                    (tapOffset.y - targetPxY).pow(2)
                                        )
                                        if (distPx <= hitThresholdPx && distPx < minDistance) {
                                            minDistance = distPx
                                            closestIndex = index
                                        }
                                    }
                                }

                                if (closestIndex != -1) {
                                    onDifferenceFound(closestIndex)
                                } else {
                                    missOffset = tapOffset
                                    missTrigger++
                                    onMissTap(tapOffset)
                                }
                            }
                        }
                    }
            )

            // Differences & Discovery indicators overlay
            Canvas(modifier = Modifier.fillMaxSize()) {
                if (size.width <= 0f || size.height <= 0f) return@Canvas

                differences.forEach { diff ->
                    val center = Offset(diff.offset.x * size.width, diff.offset.y * size.height)

                    if (diff.isFound) {
                        // Found difference element drawn on both images
                        drawDifferenceElement(diff, center)

                        // Discovery glowing ring on both images
                        drawCircle(
                            color = Color(0xFF00E676),
                            center = center,
                            radius = 34f,
                            style = Stroke(width = 5f)
                        )
                        drawCircle(
                            color = Color(0xFFFFEB3B),
                            center = center,
                            radius = 42f,
                            style = Stroke(width = 2.5f)
                        )
                    } else if (!isTopImage) {
                        // Unfound difference element present only on bottom image
                        drawDifferenceElement(diff, center)
                    }
                }

                // Miss tap feedback indicator (expanding red ring + cross)
                missOffset?.let { mOffset ->
                    drawCircle(
                        color = Color(0xFFFF1744).copy(alpha = 0.75f),
                        center = mOffset,
                        radius = 28f,
                        style = Stroke(width = 4f)
                    )
                    drawLine(
                        color = Color(0xFFFF1744),
                        start = Offset(mOffset.x - 10f, mOffset.y - 10f),
                        end = Offset(mOffset.x + 10f, mOffset.y + 10f),
                        strokeWidth = 3.5f
                    )
                    drawLine(
                        color = Color(0xFFFF1744),
                        start = Offset(mOffset.x + 10f, mOffset.y - 10f),
                        end = Offset(mOffset.x - 10f, mOffset.y + 10f),
                        strokeWidth = 3.5f
                    )
                }
            }

            // Title Header Tag
            Surface(
                color = Color(0xFF0288D1).copy(alpha = 0.90f),
                shape = RoundedCornerShape(bottomEnd = 10.dp),
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.5.dp)
                )
            }
        }
    }
}

private fun DrawScope.drawDefaultSceneArt(levelId: Int, size: Size) {
    val w = size.width
    val h = size.height

    when (levelId % 3) {
        0 -> { // Undersea Scene
            drawCircle(Color(0xFF81D4FA).copy(alpha = 0.4f), radius = w * 0.4f, center = Offset(w * 0.5f, h * 0.8f))
            drawCircle(Color(0xFFE0F7FA).copy(alpha = 0.6f), radius = 24f, center = Offset(w * 0.15f, h * 0.4f))
            drawCircle(Color(0xFFE0F7FA).copy(alpha = 0.6f), radius = 16f, center = Offset(w * 0.85f, h * 0.3f))
        }
        1 -> { // Garden Scene
            drawRect(Color(0xFF81C784), topLeft = Offset(0f, h * 0.65f), size = Size(w, h * 0.35f))
            drawCircle(Color(0xFFFFD54F), radius = w * 0.18f, center = Offset(w * 0.85f, h * 0.2f))
        }
        else -> { // Castle / Cosmic Scene
            drawCircle(Color(0xFFFFEE58).copy(alpha = 0.3f), radius = w * 0.3f, center = Offset(w * 0.2f, h * 0.3f))
            drawRect(Color(0xFF90CAF9).copy(alpha = 0.5f), topLeft = Offset(0f, h * 0.7f), size = Size(w, h * 0.3f))
        }
    }
}

private fun DrawScope.drawDifferenceElement(diff: Difference, center: Offset) {
    when (diff.type) {
        DifferenceType.CIRCLE -> {
            drawCircle(Color(0xFFFF1744), radius = 20f, center = center)
            drawCircle(Color.White, radius = 9f, center = center)
        }
        DifferenceType.STAR -> {
            val path = Path().apply {
                moveTo(center.x, center.y - 20f)
                lineTo(center.x + 6f, center.y - 6f)
                lineTo(center.x + 20f, center.y - 6f)
                lineTo(center.x + 10f, center.y + 4f)
                lineTo(center.x + 14f, center.y + 16f)
                lineTo(center.x, center.y + 9f)
                lineTo(center.x - 14f, center.y + 16f)
                lineTo(center.x - 10f, center.y + 4f)
                lineTo(center.x - 20f, center.y - 6f)
                lineTo(center.x - 6f, center.y - 6f)
                close()
            }
            drawPath(path, Color(0xFFFFD600))
        }
        DifferenceType.TRIANGLE -> {
            val path = Path().apply {
                moveTo(center.x, center.y - 20f)
                lineTo(center.x + 18f, center.y + 15f)
                lineTo(center.x - 18f, center.y + 15f)
                close()
            }
            drawPath(path, Color(0xFF00E5FF))
        }
        DifferenceType.APPLE -> {
            drawCircle(Color(0xFFE91E63), radius = 18f, center = center)
            drawCircle(Color(0xFF81C784), radius = 7f, center = Offset(center.x + 9f, center.y - 11f))
        }
        DifferenceType.FLOWER -> {
            val petalRadius = 9f
            val distance = 11f
            for (i in 0 until 5) {
                val angle = Math.toRadians(i * 72.0)
                val px = center.x + (distance * Math.cos(angle)).toFloat()
                val py = center.y + (distance * Math.sin(angle)).toFloat()
                drawCircle(Color(0xFFFF4081), radius = petalRadius, center = Offset(px, py))
            }
            drawCircle(Color(0xFFFFEB3B), radius = 7f, center = center)
        }
        DifferenceType.SUN -> {
            drawCircle(Color(0xFFFF9800), radius = 15f, center = center)
            for (i in 0 until 8) {
                val angle = Math.toRadians(i * 45.0)
                val startX = center.x + (16f * Math.cos(angle)).toFloat()
                val startY = center.y + (16f * Math.sin(angle)).toFloat()
                val endX = center.x + (24f * Math.cos(angle)).toFloat()
                val endY = center.y + (24f * Math.sin(angle)).toFloat()
                drawLine(
                    color = Color(0xFFFFC107),
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = 3.5f
                )
            }
        }
        DifferenceType.HEART -> {
            val path = Path().apply {
                moveTo(center.x, center.y + 15f)
                cubicTo(center.x - 22f, center.y - 4f, center.x - 13f, center.y - 20f, center.x, center.y - 7f)
                cubicTo(center.x + 13f, center.y - 20f, center.x + 22f, center.y - 4f, center.x, center.y + 15f)
                close()
            }
            drawPath(path, Color(0xFFFF1744))
        }
        DifferenceType.MOON -> {
            drawCircle(Color(0xFFFFD54F), radius = 18f, center = center)
            drawCircle(Color(0xFF1E293B), radius = 14f, center = Offset(center.x + 7f, center.y - 5f))
        }
        DifferenceType.CLOUD -> {
            drawCircle(Color(0xFFE0F2FE), radius = 12f, center = Offset(center.x - 10f, center.y))
            drawCircle(Color.White, radius = 16f, center = Offset(center.x, center.y - 5f))
            drawCircle(Color(0xFFE0F2FE), radius = 12f, center = Offset(center.x + 10f, center.y))
        }
        DifferenceType.DIAMOND -> {
            val path = Path().apply {
                moveTo(center.x, center.y - 20f)
                lineTo(center.x + 16f, center.y)
                lineTo(center.x, center.y + 20f)
                lineTo(center.x - 16f, center.y)
                close()
            }
            drawPath(path, Color(0xFF00E676))
        }
        DifferenceType.CROWN -> {
            val path = Path().apply {
                moveTo(center.x - 18f, center.y - 8f)
                lineTo(center.x - 9f, center.y + 9f)
                lineTo(center.x, center.y - 13f)
                lineTo(center.x + 9f, center.y + 9f)
                lineTo(center.x + 18f, center.y - 8f)
                lineTo(center.x + 14f, center.y + 14f)
                lineTo(center.x - 14f, center.y + 14f)
                close()
            }
            drawPath(path, Color(0xFFFFD700))
        }
        DifferenceType.BUTTERFLY -> {
            drawCircle(Color(0xFF42A5F5), radius = 11f, center = Offset(center.x - 10f, center.y - 6f))
            drawCircle(Color(0xFF42A5F5), radius = 11f, center = Offset(center.x + 10f, center.y - 6f))
            drawCircle(Color(0xFF90CAF9), radius = 8f, center = Offset(center.x - 7f, center.y + 8f))
            drawCircle(Color(0xFF90CAF9), radius = 8f, center = Offset(center.x + 7f, center.y + 8f))
            drawLine(Color(0xFF0D47A1), Offset(center.x, center.y - 12f), Offset(center.x, center.y + 12f), strokeWidth = 3f)
        }
        DifferenceType.FISH -> {
            val path = Path().apply {
                moveTo(center.x - 18f, center.y)
                quadraticTo(center.x, center.y - 14f, center.x + 14f, center.y)
                lineTo(center.x + 22f, center.y - 10f)
                lineTo(center.x + 22f, center.y + 10f)
                lineTo(center.x + 14f, center.y)
                quadraticTo(center.x, center.y + 14f, center.x - 18f, center.y)
                close()
            }
            drawPath(path, Color(0xFFFF9800))
            drawCircle(Color.White, radius = 3f, center = Offset(center.x - 10f, center.y - 2f))
        }
        DifferenceType.MUSIC_NOTE -> {
            drawCircle(Color(0xFFAB47BC), radius = 8f, center = Offset(center.x - 6f, center.y + 8f))
            drawCircle(Color(0xFFAB47BC), radius = 8f, center = Offset(center.x + 10f, center.y + 4f))
            drawLine(Color(0xFF6A1B9A), Offset(center.x - 1f, center.y + 8f), Offset(center.x - 1f, center.y - 12f), strokeWidth = 3f)
            drawLine(Color(0xFF6A1B9A), Offset(center.x + 15f, center.y + 4f), Offset(center.x + 15f, center.y - 16f), strokeWidth = 3f)
            drawLine(Color(0xFF6A1B9A), Offset(center.x - 1f, center.y - 12f), Offset(center.x + 15f, center.y - 16f), strokeWidth = 4f)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JogoDescubraDiferencasPreview() {
    PreviewAppTheme {
        JogoDescubraDiferencasScreen(mainViewModel = null, onBack = {})
    }
}
