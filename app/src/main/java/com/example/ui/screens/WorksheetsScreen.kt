package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R
import com.example.ui.components.FundoApp
import com.example.viewmodel.MainViewModel
import com.example.viewmodel.WorksheetsViewModel

sealed class WorksheetsNavState {
    object ChooseYear : WorksheetsNavState()
    data class ChooseDiscipline(val year: WorksheetYear) : WorksheetsNavState()
    data class WorksheetList(val year: WorksheetYear, val discipline: WorksheetDiscipline) : WorksheetsNavState()
    data class Player(val year: WorksheetYear, val discipline: WorksheetDiscipline, val worksheet: Worksheet) : WorksheetsNavState()
    data class Result(val year: WorksheetYear, val discipline: WorksheetDiscipline, val worksheet: Worksheet, val score: Int, val total: Int) : WorksheetsNavState()
}

@Composable
fun WorksheetsScreen(
    viewModel: MainViewModel,
    showHeader: Boolean = true,
    initialYearId: Int? = null,
    worksheetsViewModel: WorksheetsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onWorksheetStateChanged: ((Boolean) -> Unit)? = null
) {
    val context = LocalContext.current

    // Pre-warm data asynchronously on IO
    LaunchedEffect(Unit) {
        worksheetsViewModel.loadYearsAsync(context)
    }

    val currentYearId = initialYearId ?: 1
    val currentYear = remember(currentYearId) { 
        WorksheetsRepository.getYear(currentYearId) ?: WorksheetsYear1.year 
    }
    
    var selectedDiscipline by remember { mutableStateOf<WorksheetDiscipline?>(null) }
    var activeWorksheet by remember { mutableStateOf<Worksheet?>(null) }
    var activeResult by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    var showPrintableList by remember { mutableStateOf(false) }
    var activePrintablePdf by remember { mutableStateOf<Pair<String, String>?>(null) } // Pair(title, url)

    LaunchedEffect(selectedDiscipline, activeWorksheet, activeResult, showPrintableList, activePrintablePdf) {
        onWorksheetStateChanged?.invoke(selectedDiscipline != null || activeWorksheet != null || activeResult != null || showPrintableList || activePrintablePdf != null)
    }

    // Reset navigation states if year changes externally
    LaunchedEffect(currentYearId) {
        selectedDiscipline = null
        activeWorksheet = null
        activeResult = null
        showPrintableList = false
        activePrintablePdf = null
    }

    Box(modifier = Modifier.fillMaxSize()) {
        val discipline = selectedDiscipline
        val worksheet = activeWorksheet
        val result = activeResult
        val printablePdf = activePrintablePdf

            when {
                printablePdf != null -> {
                    PdfEditorScreen(
                        pdfUrl = printablePdf.second,
                        onBack = { activePrintablePdf = null }
                    )
                }
                showPrintableList -> {
                    PrintableLinksScreen(
                        year = currentYear,
                        onPdfSelected = { title, url ->
                            activePrintablePdf = Pair(title, url)
                        },
                        onBack = {
                            showPrintableList = false
                        }
                    )
                }
                discipline != null && worksheet != null && result != null -> {
                    InteractiveWorksheetResultScreen(
                        year = currentYear,
                        discipline = discipline,
                        worksheet = worksheet,
                        score = result.first,
                        total = result.second,
                        viewModel = viewModel,
                        onRetry = {
                            worksheetsViewModel.clearWorksheetState()
                            activeResult = null
                        },
                        onFinish = {
                            worksheetsViewModel.clearWorksheetState()
                            activeWorksheet = null
                            activeResult = null
                        }
                    )
                }
                discipline != null && worksheet != null -> {
                    InteractiveWorksheetPlayerScreen(
                        year = currentYear,
                        discipline = discipline,
                        worksheet = worksheet,
                        viewModel = viewModel,
                        worksheetsViewModel = worksheetsViewModel,
                        onFinished = { score, total ->
                            activeResult = Pair(score, total)
                        },
                        onBack = {
                            worksheetsViewModel.clearWorksheetState()
                            activeWorksheet = null
                            activeResult = null
                        }
                    )
                }
                discipline != null -> {
                    WorksheetListScreen(
                        year = currentYear,
                        discipline = discipline,
                        viewModel = viewModel,
                        onWorksheetSelected = { ws ->
                            activeWorksheet = ws
                            activeResult = null
                        },
                        onBack = {
                            selectedDiscipline = null
                        }
                    )
                }
                else -> {
                    DisciplineSelectionScreen(
                        year = currentYear,
                        viewModel = viewModel,
                        onDisciplineSelected = { disc ->
                            selectedDiscipline = disc
                        },
                        onOpenPrintableList = {
                            showPrintableList = true
                        }
                    )
                }
            }
        }
    }

// --- 1. CHOOSE YEAR SCREEN ---
@Composable
fun ChooseYearScreen(
    worksheetsViewModel: WorksheetsViewModel? = null,
    onYearSelected: (WorksheetYear) -> Unit
) {
    val asyncYears by (worksheetsViewModel?.years?.collectAsState() ?: remember { mutableStateOf(emptyList()) })
    val isLoading by (worksheetsViewModel?.isLoading?.collectAsState() ?: remember { mutableStateOf(false) })
    val years = if (asyncYears.isNotEmpty()) asyncYears else WorksheetsRepository.years

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Escolher o ano",
            fontSize = 24.sp,
            fontWeight = FontWeight.Black,
            color = Color(0xFF1E293B),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Seleciona o teu ano escolar para ver as fichas!",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF64748B),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
        )

        if (isLoading && years.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color(0xFFE15B17),
                    strokeWidth = 3.dp
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(years) { year ->
                    val interactionSource = remember { MutableInteractionSource() }
                    val isPressed by interactionSource.collectIsPressedAsState()
                    val scale by animateFloatAsState(if (isPressed) 0.95f else 1f, label = "year_card_scale")

                    val cardColor = when (year.id) {
                        1 -> Color(0xFF4F46E5)
                        2 -> Color(0xFF10B981)
                        3 -> Color(0xFFF59E0B)
                        else -> Color(0xFFEC4899)
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(0.95f)
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                            }
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null,
                                onClick = { onYearSelected(year) }
                            ),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        border = BorderStroke(2.dp, cardColor.copy(alpha = 0.3f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .background(cardColor.copy(alpha = 0.1f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = when (year.id) {
                                        1 -> "🌱"
                                        2 -> "🪁"
                                        3 -> "🚀"
                                        else -> "🎓"
                                    },
                                    fontSize = 36.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = year.title,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF1E293B)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Fichas divertidas",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = cardColor
                            )
                        }
                    }
                }
            }
        }
    }
}

data class PrintableLink(
    val title: String,
    val subtitle: String,
    val url: String,
    val emoji: String = "📄"
)

fun getPrintableLinksForYear(yearId: Int): List<PrintableLink> {
    return when (yearId) {
        1 -> listOf(
            PrintableLink(
                title = "Português",
                subtitle = "Fichas de trabalho de Português - 1.º Ano",
                url = "https://files.catbox.moe/m5jekf.pdf",
                emoji = "📖"
            ),
            PrintableLink(
                title = "Estudo do Meio",
                subtitle = "Fichas de trabalho de Estudo do Meio - 1.º Ano",
                url = "https://files.catbox.moe/4hv9fw.pdf",
                emoji = "🌍"
            ),
            PrintableLink(
                title = "Matemática",
                subtitle = "Fichas de trabalho de Matemática - 1.º Ano",
                url = "https://files.catbox.moe/dt6whv.pdf",
                emoji = "🔢"
            )
        )
        2 -> listOf(
            PrintableLink(
                title = "Português",
                subtitle = "Fichas de trabalho de Português - 2.º Ano",
                url = "https://files.catbox.moe/qw2b1b.pdf",
                emoji = "📖"
            ),
            PrintableLink(
                title = "Estudo do Meio",
                subtitle = "Fichas de trabalho de Estudo do Meio - 2.º Ano",
                url = "https://files.catbox.moe/0sysf6.pdf",
                emoji = "🌍"
            ),
            PrintableLink(
                title = "Matemática",
                subtitle = "Fichas de trabalho de Matemática - 2.º Ano",
                url = "https://files.catbox.moe/ct1ad5.pdf",
                emoji = "🔢"
            )
        )
        3 -> listOf(
            PrintableLink(
                title = "Português",
                subtitle = "Fichas de trabalho de Português - 3.º Ano",
                url = "https://files.catbox.moe/dhgz8l.pdf",
                emoji = "📖"
            ),
            PrintableLink(
                title = "Estudo do Meio",
                subtitle = "Fichas de trabalho de Estudo do Meio - 3.º Ano",
                url = "https://files.catbox.moe/bqzg0c.pdf",
                emoji = "🌍"
            ),
            PrintableLink(
                title = "Matemática",
                subtitle = "Fichas de trabalho de Matemática - 3.º Ano",
                url = "https://files.catbox.moe/hn0w0r.pdf",
                emoji = "🔢"
            )
        )
        4 -> listOf(
            PrintableLink(
                title = "Português",
                subtitle = "Fichas de trabalho de Português - 4.º Ano",
                url = "https://files.catbox.moe/pqxynj.pdf",
                emoji = "📖"
            ),
            PrintableLink(
                title = "Estudo do Meio",
                subtitle = "Fichas de trabalho de Estudo do Meio - 4.º Ano",
                url = "https://files.catbox.moe/bu2krg.pdf",
                emoji = "🌍"
            ),
            PrintableLink(
                title = "Matemática",
                subtitle = "Fichas de trabalho de Matemática - 4.º Ano",
                url = "https://files.catbox.moe/931l9l.pdf",
                emoji = "🔢"
            )
        )
        else -> listOf(
            PrintableLink(
                title = "Todas as áreas",
                subtitle = "Fichas de trabalho para imprimir",
                url = "https://www.aveordemsantiago.pt/pdfs_on/1ciclo/1ano/var/1.pdf",
                emoji = "📚"
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrintableLinksScreen(
    year: WorksheetYear,
    onPdfSelected: (String, String) -> Unit,
    onBack: () -> Unit
) {
    val links = remember(year.id) { getPrintableLinksForYear(year.id) }
    val printColor = Color(0xFF8B5CF6)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(44.dp)
                    .background(Color(0xFFEDE9FE), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar",
                    tint = printColor
                )
            }
            Column {
                Text(
                    text = "Fichas para Imprimir • ${year.title}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF1E293B)
                )
                Text(
                    text = "Documentos oficiais prontos a descarregar e imprimir",
                    fontSize = 13.sp,
                    color = Color(0xFF64748B)
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(links) { link ->
                val interaction = remember { MutableInteractionSource() }
                val pressed by interaction.collectIsPressedAsState()
                val scale by animateFloatAsState(if (pressed) 0.98f else 1f, label = "link_scale")

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        }
                        .clickable(
                            interactionSource = interaction,
                            indication = null,
                            onClick = { onPdfSelected(link.title, link.url) }
                        ),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    border = BorderStroke(1.5.dp, printColor.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .background(
                                    brush = Brush.linearGradient(listOf(Color(0xFF8B5CF6), Color(0xFF6D28D9))),
                                    shape = RoundedCornerShape(16.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = link.emoji, fontSize = 26.sp)
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = link.title,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF1E293B)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = link.subtitle,
                                fontSize = 13.sp,
                                color = Color(0xFF475569)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(printColor.copy(alpha = 0.12f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🖨️", fontSize = 18.sp)
                        }
                    }
                }
            }
        }
    }
}

// --- 2. DISCIPLINE SELECTION SCREEN (CLEAN VIEW: ONLY 3 LARGE CARDS) ---
@Composable
fun DisciplineSelectionScreen(
    year: WorksheetYear,
    viewModel: MainViewModel,
    onDisciplineSelected: (WorksheetDiscipline) -> Unit,
    onOpenPrintableList: () -> Unit
) {
    val completedItemsState by viewModel.completedItems.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 28.dp)
    ) {
        items(year.disciplines, key = { it.id }) { disc ->
            val total = disc.worksheets.size
            val completedCount = disc.worksheets.count { ws ->
                completedItemsState.any { item ->
                    item.category == "Fichas" && item.title.contains(ws.id)
                }
            }
            val progress = if (total > 0) completedCount.toFloat() / total else 0f

            // Dynamic vibrant gradient for the icon container matching Portuguese flag / math / study
            val badgeGradient = when {
                disc.id.startsWith("pt") || disc.title.contains("Português", ignoreCase = true) -> listOf(Color(0xFF3B82F6), Color(0xFF1D4ED8))
                disc.id.startsWith("mat") || disc.title.contains("Matemática", ignoreCase = true) -> listOf(Color(0xFF10B981), Color(0xFF059669))
                else -> listOf(Color(0xFFF59E0B), Color(0xFFD97706))
            }

            val interactionSource = remember { MutableInteractionSource() }
            val isPressed by interactionSource.collectIsPressedAsState()
            val scale by animateFloatAsState(if (isPressed) 0.98f else 1f, label = "disc_scale_${disc.id}")

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = { onDisciplineSelected(disc) }
                    )
                    .testTag("discipline_card_${disc.id}"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                border = BorderStroke(
                    width = 1.5.dp,
                    color = disc.color.copy(alpha = 0.25f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Large vibrant colorful badge
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                brush = Brush.linearGradient(badgeGradient),
                                shape = RoundedCornerShape(14.dp)
                            )
                            .border(1.5.dp, Color.White.copy(alpha = 0.6f), RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = disc.emoji,
                            fontSize = 24.sp
                        )
                    }

                    // Title, subtitle and progress bar
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = disc.title,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF1E293B)
                        )
                        Spacer(modifier = Modifier.height(1.dp))
                        Text(
                            text = "$total fichas interativas",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = disc.color
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            LinearProgressIndicator(
                                progress = { progress },
                                color = disc.color,
                                trackColor = disc.color.copy(alpha = 0.15f),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(6.dp)
                                    .clip(CircleShape)
                            )
                            Text(
                                text = "${(progress * 100).toInt()}%",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF475569)
                            )
                        }
                    }

                    // Action arrow indicator
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(disc.color.copy(alpha = 0.12f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "👉",
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }

        item {
            val printInteraction = remember { MutableInteractionSource() }
            val printPressed by printInteraction.collectIsPressedAsState()
            val printScale by animateFloatAsState(if (printPressed) 0.98f else 1f, label = "print_scale")
            val printColor = Color(0xFF8B5CF6)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        scaleX = printScale
                        scaleY = printScale
                    }
                    .clickable(
                        interactionSource = printInteraction,
                        indication = null,
                        onClick = onOpenPrintableList
                    )
                    .testTag("discipline_card_printable"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                border = BorderStroke(
                    width = 1.5.dp,
                    color = printColor.copy(alpha = 0.25f)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                brush = Brush.linearGradient(listOf(Color(0xFF8B5CF6), Color(0xFF6D28D9))),
                                shape = RoundedCornerShape(14.dp)
                            )
                            .border(1.5.dp, Color.White.copy(alpha = 0.6f), RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "🖨️",
                            fontSize = 24.sp
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "fichas de trabalho para imprimir",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF1E293B),
                            maxLines = 1,
                            softWrap = false,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(1.dp))
                        Text(
                            text = "PDFs para descarregar e praticar",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = printColor
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(printColor.copy(alpha = 0.12f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "📥",
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

// --- 3. WORKSHEET LIST SCREEN (DEDICATED PAGE PER DISCIPLINE) ---
@Composable
fun WorksheetListScreen(
    year: WorksheetYear,
    discipline: WorksheetDiscipline,
    viewModel: MainViewModel,
    onWorksheetSelected: (Worksheet) -> Unit,
    onBack: () -> Unit
) {
    val completedItemsState by viewModel.completedItems.collectAsState()

    val badgeGradient = when {
        discipline.id.startsWith("pt") || discipline.title.contains("Português", ignoreCase = true) -> listOf(Color(0xFF3B82F6), Color(0xFF1D4ED8))
        discipline.id.startsWith("mat") || discipline.title.contains("Matemática", ignoreCase = true) -> listOf(Color(0xFF10B981), Color(0xFF059669))
        else -> listOf(Color(0xFFF59E0B), Color(0xFFD97706))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        // Back Header
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .background(Color.White, CircleShape)
                        .size(42.dp)
                        .border(1.dp, Color(0xFFE2E8F0), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color(0xFF1E293B)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .background(
                            brush = Brush.linearGradient(badgeGradient),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = discipline.emoji, fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = discipline.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF1E293B)
                    )
                    Text(
                        text = "${discipline.worksheets.size} fichas disponíveis",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = discipline.color
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // List of Worksheets
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(bottom = 28.dp, top = 4.dp)
        ) {
            items(discipline.worksheets, key = { it.id }) { worksheet ->
                val isCompleted = completedItemsState.any { item ->
                    item.category == "Fichas" && item.title.contains(worksheet.id)
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onWorksheetSelected(worksheet) }
                        .testTag("worksheet_card_${worksheet.id}"),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    border = BorderStroke(1.5.dp, discipline.color.copy(alpha = 0.2f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(discipline.color.copy(alpha = 0.12f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = worksheet.imageEmoji, fontSize = 24.sp)
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = "Ficha ${worksheet.numero}",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Black,
                                        color = discipline.color
                                    )
                                    if (isCompleted) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Concluído",
                                                tint = Color(0xFF10B981),
                                                modifier = Modifier.size(12.dp)
                                            )
                                            Text(
                                                text = "CONCLUÍDA",
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Black,
                                                color = Color(0xFF10B981)
                                            )
                                        }
                                    }
                                }
                                Text(
                                    text = worksheet.title,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF1E293B)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = worksheet.description,
                            fontSize = 13.sp,
                            color = Color(0xFF64748B),
                            lineHeight = 17.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))
                        Button(
                            onClick = { onWorksheetSelected(worksheet) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isCompleted) Color(0xFF0F172A) else discipline.color
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = if (isCompleted) "REPETIR" else "COMEÇAR",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 13.sp,
                                    color = Color.White
                                )
                                Text(text = if (isCompleted) "🔄" else "🚀", fontSize = 15.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- 4. INTERACTIVE WORKSHEET PLAYER SCREEN ---
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InteractiveWorksheetPlayerScreen(
    year: WorksheetYear,
    discipline: WorksheetDiscipline,
    worksheet: Worksheet,
    viewModel: MainViewModel,
    worksheetsViewModel: WorksheetsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onFinished: (Int, Int) -> Unit,
    onBack: () -> Unit
) {
    // Sync active worksheet and reset its state if it's a new worksheet
    if (worksheetsViewModel.activeWorksheet != worksheet) {
        worksheetsViewModel.startWorksheet(worksheet)
    }

    var currentExerciseIndex by worksheetsViewModel::currentExerciseIndex
    var score by worksheetsViewModel::score
    val currentExercise = worksheet.exercises.getOrNull(currentExerciseIndex)

    // Clean up active sound or TTS services AND clear worksheet state on exit
    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopSpeaking()
            worksheetsViewModel.clearWorksheetState()
        }
    }

    // Answer states delegated to WorksheetsViewModel (linked to Compose lifecycle)
    var selectedAnswer by worksheetsViewModel::selectedAnswer
    var selectedImageAnswer by worksheetsViewModel::selectedImageAnswer
    var inputAnswer by worksheetsViewModel::inputAnswer
    var orderingList by worksheetsViewModel::orderingList
    var matchingLeftSelected by worksheetsViewModel::matchingLeftSelected
    var matchingRightSelected by worksheetsViewModel::matchingRightSelected
    var matchedPairs by worksheetsViewModel::matchedPairs

    // Enhanced states for tactile Ordering delegated to WorksheetsViewModel
    var selectedOrderList by worksheetsViewModel::selectedOrderList
    var availableOrderList by worksheetsViewModel::availableOrderList

    var hasAnswered by worksheetsViewModel::hasAnswered
    var isAnswerCorrect by worksheetsViewModel::isAnswerCorrect
    var showSolutionModal by worksheetsViewModel::showSolutionModal

    val configuration = androidx.compose.ui.platform.LocalConfiguration.current
    val isLandscape = configuration.screenWidthDp > configuration.screenHeightDp
    val isSmallScreen = configuration.screenHeightDp < 700 || isLandscape
    val cardSpacerHeight = if (isSmallScreen) 8.dp else 14.dp
    val imageMaxHeight = if (isSmallScreen) 115.dp else 175.dp
    val bottomSpacerHeight = if (isSmallScreen) 8.dp else 14.dp
    val optionVerticalPadding = if (isSmallScreen) 10.dp else 14.dp
    val optionTextSize = if (isSmallScreen) 14.sp else 16.sp

    // Reset answer states when question index changes to keep UI fully in sync
    LaunchedEffect(currentExerciseIndex) {
        worksheetsViewModel.resetAnswerStatesForCurrentExercise()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 680.dp)
                .padding(horizontal = if (isSmallScreen) 12.dp else 16.dp, vertical = if (isSmallScreen) 8.dp else 14.dp)
        ) {
            // Toolbar
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                worksheetsViewModel.clearWorksheetState()
                                onBack()
                            },
                            modifier = Modifier
                                .background(Color.White, CircleShape)
                                .size(40.dp)
                                .border(1.dp, Color(0xFFE2E8F0), CircleShape)
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color(0xFF1E293B))
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Ficha ${worksheet.numero}: ${worksheet.title}",
                                fontSize = if (isSmallScreen) 14.sp else 16.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF1E293B),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "Pergunta ${currentExerciseIndex + 1} de ${worksheet.exercises.size}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = discipline.color
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Progress Bar
                    LinearProgressIndicator(
                        progress = { (currentExerciseIndex + 1).toFloat() / worksheet.exercises.size },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape),
                        color = discipline.color,
                        trackColor = discipline.color.copy(alpha = 0.15f)
                    )
                }
            }

            if (currentExercise != null) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Mascot Row (Mascot guide)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data("https://i.imgur.com/O2uwUho.png")
                                    .placeholder(R.drawable.img_ze_face_beret_alt)
                                    .error(R.drawable.img_ze_face_beret_alt)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Zé Traquina",
                                modifier = Modifier
                                    .size(if (isSmallScreen) 48.dp else 56.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Column {
                                Text(
                                    text = "O Zé Traquina diz:",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFE15B17)
                                )
                                Text(
                                    text = currentExercise.question,
                                    fontSize = if (isSmallScreen) 14.sp else 15.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF1E293B),
                                    lineHeight = if (isSmallScreen) 18.sp else 20.sp
                                )
                            }
                        }

                        // Optional illustration / graphic (Dynamic support for drawables and URLs)
                        if (!currentExercise.image.isNullOrBlank()) {
                            val context = LocalContext.current
                            val imageModel = remember(currentExercise.image) {
                                if (currentExercise.image.startsWith("http")) {
                                    currentExercise.image
                                } else {
                                    val resId = context.resources.getIdentifier(
                                        currentExercise.image,
                                        "drawable",
                                        context.packageName
                                    )
                                    if (resId != 0) resId else R.drawable.img_ze_face_beret_alt
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = if (isSmallScreen) 80.dp else 110.dp, max = imageMaxHeight)
                                    .clip(RoundedCornerShape(16.dp))
                                    .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp))
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                AsyncImage(
                                    model = imageModel,
                                    contentDescription = "Ilustração do Exercício",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }

                        // Optional graphic count items (e.g. 🍎🍎🍎)
                        if (currentExercise.countItems != null) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .background(Color(0xFFF1F5F9), RoundedCornerShape(16.dp))
                                    .padding(horizontal = 20.dp, vertical = if (isSmallScreen) 8.dp else 12.dp)
                            ) {
                                Text(
                                    text = currentExercise.countItems,
                                    fontSize = if (isSmallScreen) 24.sp else 30.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        // --- Render Exercise Options ---
                        when (currentExercise.type) {
                        ExerciseType.MULTIPLE_CHOICE, ExerciseType.LETTERS_WORDS -> {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(if (isSmallScreen) 8.dp else 10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                currentExercise.options.forEach { option ->
                                    val isSelected = selectedAnswer == option
                                    val borderCol = if (hasAnswered && option == currentExercise.correctAnswer) {
                                        Color(0xFF16A34A)
                                    } else if (hasAnswered && isSelected && !isAnswerCorrect) {
                                        Color(0xFFDC2626)
                                    } else if (isSelected) {
                                        discipline.color
                                    } else {
                                        Color(0xFFE2E8F0)
                                    }

                                    val fillCol = if (hasAnswered && option == currentExercise.correctAnswer) {
                                        Color(0xFFDCFCE7)
                                    } else if (hasAnswered && isSelected && !isAnswerCorrect) {
                                        Color(0xFFFEE2E2)
                                    } else if (isSelected) {
                                        discipline.color.copy(alpha = 0.12f)
                                    } else {
                                        Color.White
                                    }

                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable(enabled = !hasAnswered) { selectedAnswer = option },
                                        shape = RoundedCornerShape(16.dp),
                                        colors = CardDefaults.cardColors(containerColor = fillCol),
                                        border = BorderStroke(2.dp, borderCol),
                                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 16.dp, vertical = optionVerticalPadding),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = option,
                                                fontSize = optionTextSize,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF1E293B),
                                                modifier = Modifier.weight(1f)
                                            )
                                            if (hasAnswered && option == currentExercise.correctAnswer) {
                                                Text(text = "✅", fontSize = 18.sp)
                                            } else if (hasAnswered && isSelected && !isAnswerCorrect) {
                                                Text(text = "❌", fontSize = 18.sp)
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        ExerciseType.TRUE_FALSE -> {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(if (isSmallScreen) 10.dp else 14.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                listOf("true" to "Verdadeiro ✅", "false" to "Falso ❌").forEach { (valStr, label) ->
                                    val isSelected = selectedAnswer == valStr
                                    val isCorrectOption = valStr == currentExercise.correctAnswer

                                    val borderCol = if (hasAnswered && isCorrectOption) {
                                        Color(0xFF16A34A)
                                    } else if (hasAnswered && isSelected && !isAnswerCorrect) {
                                        Color(0xFFDC2626)
                                    } else if (isSelected) {
                                        discipline.color
                                    } else {
                                        Color(0xFFE2E8F0)
                                    }

                                    val fillCol = if (hasAnswered && isCorrectOption) {
                                        Color(0xFFDCFCE7)
                                    } else if (hasAnswered && isSelected && !isAnswerCorrect) {
                                        Color(0xFFFEE2E2)
                                    } else if (isSelected) {
                                        discipline.color.copy(alpha = 0.12f)
                                    } else {
                                        Color.White
                                    }

                                    Card(
                                        modifier = Modifier
                                            .weight(1f)
                                            .heightIn(min = if (isSmallScreen) 58.dp else 72.dp)
                                            .clickable(enabled = !hasAnswered) { selectedAnswer = valStr },
                                        shape = RoundedCornerShape(16.dp),
                                        colors = CardDefaults.cardColors(containerColor = fillCol),
                                        border = BorderStroke(2.dp, borderCol),
                                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier.fillMaxSize().padding(if (isSmallScreen) 8.dp else 12.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = label,
                                                fontSize = optionTextSize,
                                                fontWeight = FontWeight.Black,
                                                color = Color(0xFF1E293B),
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        ExerciseType.CHOOSE_IMAGE -> {
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(if (isSmallScreen) 8.dp else 12.dp, Alignment.CenterHorizontally),
                                verticalArrangement = Arrangement.spacedBy(if (isSmallScreen) 8.dp else 12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                currentExercise.options.forEach { option ->
                                    val isSelected = selectedImageAnswer == option
                                    val isCorrectOption = option == currentExercise.correctAnswer

                                    val borderCol = if (hasAnswered && isCorrectOption) {
                                        Color(0xFF16A34A)
                                    } else if (hasAnswered && isSelected && !isAnswerCorrect) {
                                        Color(0xFFDC2626)
                                    } else if (isSelected) {
                                        discipline.color
                                    } else {
                                        Color(0xFFE2E8F0)
                                    }

                                    val fillCol = if (hasAnswered && isCorrectOption) {
                                        Color(0xFFDCFCE7)
                                    } else if (hasAnswered && isSelected && !isAnswerCorrect) {
                                        Color(0xFFFEE2E2)
                                    } else if (isSelected) {
                                        discipline.color.copy(alpha = 0.15f)
                                    } else {
                                        Color.White
                                    }

                                    Card(
                                        modifier = Modifier
                                            .heightIn(min = if (isSmallScreen) 58.dp else 70.dp)
                                            .clickable(enabled = !hasAnswered) { selectedImageAnswer = option },
                                        shape = RoundedCornerShape(16.dp),
                                        colors = CardDefaults.cardColors(containerColor = fillCol),
                                        border = BorderStroke(2.dp, borderCol),
                                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier.padding(horizontal = 18.dp, vertical = if (isSmallScreen) 10.dp else 14.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = option,
                                                fontSize = if (isSmallScreen) 16.sp else 20.sp,
                                                fontWeight = FontWeight.Black,
                                                textAlign = TextAlign.Center,
                                                color = Color(0xFF1E293B)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        ExerciseType.COUNTING, ExerciseType.COMPLETING -> {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(if (isSmallScreen) 8.dp else 10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                currentExercise.options.forEach { option ->
                                    val isSelected = selectedAnswer == option
                                    val borderCol = if (hasAnswered && option == currentExercise.correctAnswer) {
                                        Color(0xFF16A34A)
                                    } else if (hasAnswered && isSelected && !isAnswerCorrect) {
                                        Color(0xFFDC2626)
                                    } else if (isSelected) {
                                        discipline.color
                                    } else {
                                        Color(0xFFE2E8F0)
                                    }

                                    val fillCol = if (hasAnswered && option == currentExercise.correctAnswer) {
                                        Color(0xFFDCFCE7)
                                    } else if (hasAnswered && isSelected && !isAnswerCorrect) {
                                        Color(0xFFFEE2E2)
                                    } else if (isSelected) {
                                        discipline.color.copy(alpha = 0.12f)
                                    } else {
                                        Color.White
                                    }

                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable(enabled = !hasAnswered) { selectedAnswer = option },
                                        shape = RoundedCornerShape(16.dp),
                                        colors = CardDefaults.cardColors(containerColor = fillCol),
                                        border = BorderStroke(2.dp, borderCol),
                                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 16.dp, vertical = optionVerticalPadding),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = option,
                                                fontSize = optionTextSize,
                                                fontWeight = FontWeight.Black,
                                                color = Color(0xFF1E293B),
                                                modifier = Modifier.weight(1f)
                                            )
                                            if (hasAnswered && option == currentExercise.correctAnswer) {
                                                Text(text = "✅", fontSize = 18.sp)
                                            } else if (hasAnswered && isSelected && !isAnswerCorrect) {
                                                Text(text = "❌", fontSize = 18.sp)
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        ExerciseType.MATCHING -> {
                            // Stabilize shuffle across recompositions using remember
                            val leftKeys = remember(currentExerciseIndex) { currentExercise.pairs.keys.toList().shuffled() }
                            val rightValues = remember(currentExerciseIndex) { currentExercise.pairs.values.toList().shuffled() }

                            Column(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Seleciona um item de cada lado para associar:",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF64748B),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    // Left Column
                                    Column(
                                        modifier = Modifier.weight(1f),
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        leftKeys.forEach { key ->
                                            val isSelected = matchingLeftSelected == key
                                            val isMatched = matchedPairs.containsKey(key)

                                            val bg = if (isMatched) Color(0xFFDCFCE7) else if (isSelected) discipline.color.copy(alpha = 0.15f) else Color.White
                                            val border = if (isMatched) Color(0xFF16A34A) else if (isSelected) discipline.color else Color(0xFFE2E8F0)

                                            Card(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .heightIn(min = 54.dp)
                                                    .clickable(enabled = !hasAnswered && !isMatched) {
                                                        matchingLeftSelected = key
                                                        if (matchingRightSelected != null) {
                                                            val correctVal = currentExercise.pairs[key]
                                                            if (correctVal == matchingRightSelected) {
                                                                matchedPairs = matchedPairs + (key to matchingRightSelected!!)
                                                                viewModel.speak("Par correto! Boa!")
                                                            } else {
                                                                viewModel.speak("Tenta outro par!")
                                                            }
                                                            matchingLeftSelected = null
                                                            matchingRightSelected = null
                                                        }
                                                    },
                                                shape = RoundedCornerShape(14.dp),
                                                colors = CardDefaults.cardColors(containerColor = bg),
                                                border = BorderStroke(2.dp, border),
                                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                                            ) {
                                                Box(modifier = Modifier.fillMaxSize().padding(8.dp), contentAlignment = Alignment.Center) {
                                                    Text(
                                                        text = key,
                                                        fontWeight = FontWeight.Black,
                                                        fontSize = 13.sp,
                                                        color = Color(0xFF1E293B),
                                                        textAlign = TextAlign.Center
                                                    )
                                                }
                                            }
                                        }
                                    }

                                    // Right Column
                                    Column(
                                        modifier = Modifier.weight(1f),
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        rightValues.forEach { value ->
                                            val isSelected = matchingRightSelected == value
                                            val isMatched = matchedPairs.containsValue(value)

                                            val bg = if (isMatched) Color(0xFFDCFCE7) else if (isSelected) discipline.color.copy(alpha = 0.15f) else Color.White
                                            val border = if (isMatched) Color(0xFF16A34A) else if (isSelected) discipline.color else Color(0xFFE2E8F0)

                                            Card(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .heightIn(min = 54.dp)
                                                    .clickable(enabled = !hasAnswered && !isMatched) {
                                                        matchingRightSelected = value
                                                        if (matchingLeftSelected != null) {
                                                            val correctVal = currentExercise.pairs[matchingLeftSelected!!]
                                                            if (correctVal == value) {
                                                                matchedPairs = matchedPairs + (matchingLeftSelected!! to value)
                                                                viewModel.speak("Par correto! Boa!")
                                                            } else {
                                                                viewModel.speak("Tenta outro par!")
                                                            }
                                                            matchingLeftSelected = null
                                                            matchingRightSelected = null
                                                        }
                                                    },
                                                shape = RoundedCornerShape(14.dp),
                                                colors = CardDefaults.cardColors(containerColor = bg),
                                                border = BorderStroke(2.dp, border),
                                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                                            ) {
                                                Box(modifier = Modifier.fillMaxSize().padding(8.dp), contentAlignment = Alignment.Center) {
                                                    Text(
                                                        text = value,
                                                        fontWeight = FontWeight.Black,
                                                        fontSize = 13.sp,
                                                        color = Color(0xFF1E293B),
                                                        textAlign = TextAlign.Center
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }

                                // Auto progress if all are matched
                                LaunchedEffect(matchedPairs) {
                                    if (matchedPairs.size == leftKeys.size && leftKeys.isNotEmpty()) {
                                        isAnswerCorrect = true
                                        hasAnswered = true
                                    }
                                }
                            }
                        }

                        ExerciseType.ORDERING -> {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Ordena os elementos tocando neles para mover:",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF64748B),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )

                                // 1. Target Container (The Answer)
                                Card(
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.6f)),
                                    border = BorderStroke(2.dp, discipline.color.copy(alpha = 0.3f)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text(
                                            text = "A tua resposta:",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = discipline.color
                                        )

                                        if (selectedOrderList.isEmpty()) {
                                            Text(
                                                text = "Toca nas opções abaixo para ordenar",
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = Color(0xFF94A3B8),
                                                modifier = Modifier.padding(vertical = 10.dp)
                                            )
                                        } else {
                                            FlowRow(
                                                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                                modifier = Modifier.fillMaxWidth()
                                            ) {
                                                selectedOrderList.forEach { value ->
                                                    val borderCol = if (hasAnswered) {
                                                        if (isAnswerCorrect) Color(0xFF16A34A) else Color(0xFFDC2626)
                                                    } else {
                                                        discipline.color
                                                    }

                                                    Card(
                                                        modifier = Modifier
                                                            .clickable(enabled = !hasAnswered) {
                                                                selectedOrderList = selectedOrderList - value
                                                                availableOrderList = availableOrderList + value
                                                            },
                                                        shape = RoundedCornerShape(12.dp),
                                                        colors = CardDefaults.cardColors(containerColor = Color.White),
                                                        border = BorderStroke(2.dp, borderCol),
                                                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                                                    ) {
                                                        Text(
                                                            text = value,
                                                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                                            fontWeight = FontWeight.Black,
                                                            fontSize = 14.sp,
                                                            color = Color(0xFF1E293B)
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                // 2. Source Container (Available Options)
                                if (availableOrderList.isNotEmpty()) {
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "Opções disponíveis:",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF64748B),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    FlowRow(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                                        verticalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        availableOrderList.forEach { value ->
                                            Card(
                                                modifier = Modifier
                                                    .clickable(enabled = !hasAnswered) {
                                                        availableOrderList = availableOrderList - value
                                                        selectedOrderList = selectedOrderList + value
                                                    },
                                                shape = RoundedCornerShape(12.dp),
                                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                                border = BorderStroke(1.5.dp, Color(0xFFE2E8F0)),
                                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                                            ) {
                                                Text(
                                                    text = value,
                                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                                    fontWeight = FontWeight.Black,
                                                    fontSize = 14.sp,
                                                    color = Color(0xFF1E293B)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        else -> {
                            Box(modifier = Modifier.padding(16.dp)) {
                                Text("Exercício não suportado neste modo.")
                            }
                        }
                    }
                }
            }

                // --- BOTTOM BAR WITH VER SOLUÇÃO & CONFIRM / NEXT BUTTONS ---
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // "VER SOLUÇÃO" button
                        Button(
                            onClick = { showSolutionModal = true },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFECE5)),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp),
                            border = BorderStroke(1.5.dp, Color(0xFFE15B17).copy(alpha = 0.4f))
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(text = "💡", fontSize = 16.sp)
                                Text(
                                    text = "VER SOLUÇÃO",
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFFE15B17),
                                    fontSize = 12.sp
                                )
                            }
                        }

                        // Confirm / Continue Button
                        val canConfirm = when (currentExercise.type) {
                            ExerciseType.MULTIPLE_CHOICE, ExerciseType.LETTERS_WORDS, ExerciseType.TRUE_FALSE, ExerciseType.COUNTING, ExerciseType.COMPLETING -> {
                                selectedAnswer != null
                            }
                            ExerciseType.CHOOSE_IMAGE -> {
                                selectedImageAnswer != null
                            }
                            ExerciseType.ORDERING -> {
                                selectedOrderList.isNotEmpty() && availableOrderList.isEmpty()
                            }
                            ExerciseType.MATCHING -> {
                                matchedPairs.size == currentExercise.pairs.size && currentExercise.pairs.isNotEmpty()
                            }
                            else -> false
                        }

                        Button(
                            onClick = {
                                if (!hasAnswered) {
                                    // Evaluate answer
                                    when (currentExercise.type) {
                                        ExerciseType.MULTIPLE_CHOICE, ExerciseType.LETTERS_WORDS, ExerciseType.TRUE_FALSE, ExerciseType.COUNTING, ExerciseType.COMPLETING -> {
                                            isAnswerCorrect = selectedAnswer == currentExercise.correctAnswer
                                        }
                                        ExerciseType.CHOOSE_IMAGE -> {
                                            isAnswerCorrect = selectedImageAnswer == currentExercise.correctAnswer
                                        }
                                        ExerciseType.ORDERING -> {
                                            isAnswerCorrect = selectedOrderList == currentExercise.correctAnswers
                                        }
                                        ExerciseType.MATCHING -> {
                                            isAnswerCorrect = matchedPairs == currentExercise.pairs
                                        }
                                        else -> {
                                            isAnswerCorrect = false
                                        }
                                    }

                                    hasAnswered = true
                                    if (isAnswerCorrect) {
                                        score++
                                        viewModel.speak("Excelente! Acertaste!")
                                    } else {
                                        viewModel.speak("Não faz mal! Vamos continuar!")
                                    }
                                } else {
                                    // Go next
                                    if (currentExerciseIndex < worksheet.exercises.size - 1) {
                                        currentExerciseIndex++
                                    } else {
                                        onFinished(score, worksheet.exercises.size)
                                    }
                                }
                            },
                            enabled = canConfirm || hasAnswered,
                            colors = ButtonDefaults.buttonColors(containerColor = if (hasAnswered) Color(0xFF1E293B) else discipline.color),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .weight(1.2f)
                                .height(52.dp)
                        ) {
                            Text(
                                text = if (!hasAnswered) "VERIFICAR" else "CONTINUAR 👉",
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        // --- VER SOLUÇÃO MODAL / DIALOG ---
        if (showSolutionModal && currentExercise != null) {
            AlertDialog(
                onDismissRequest = { showSolutionModal = false },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(text = "💡 Solução e Explicação", fontWeight = FontWeight.Black, fontSize = 18.sp, color = Color(0xFFE15B17))
                    }
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        // Correct response view
                        val printableSolution = when (currentExercise.type) {
                            ExerciseType.MULTIPLE_CHOICE, ExerciseType.LETTERS_WORDS, ExerciseType.CHOOSE_IMAGE, ExerciseType.COUNTING, ExerciseType.COMPLETING -> {
                                currentExercise.correctAnswer
                            }
                            ExerciseType.TRUE_FALSE -> {
                                if (currentExercise.correctAnswer == "true") "Verdadeiro ✅" else "Falso ❌"
                            }
                            ExerciseType.ORDERING -> {
                                currentExercise.correctAnswers.joinToString(" ➡️ ")
                            }
                            ExerciseType.MATCHING -> {
                                currentExercise.pairs.entries.joinToString("\n") { "• ${it.key} liga com ${it.value}" }
                            }
                            else -> {
                                "Nenhuma solução disponível"
                            }
                        }

                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFDCFCE7)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp).fillMaxWidth()) {
                                Text(
                                    text = "Resposta correta:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = Color(0xFF15803D)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = printableSolution,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 15.sp,
                                    color = Color(0xFF166534)
                                )
                            }
                        }

                        // Explanation from mascot
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data("https://i.imgur.com/O2uwUho.png")
                                    .placeholder(R.drawable.img_ze_face_beret_alt)
                                    .error(R.drawable.img_ze_face_beret_alt)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Mascote",
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                            )
                            Column {
                                Text(text = "O Zé Traquina explica:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFE15B17))
                                Text(
                                    text = currentExercise.explanation ?: "Tenta responder com atenção para completar toda a ficha escolar!",
                                    fontSize = 13.sp,
                                    lineHeight = 17.sp,
                                    color = Color(0xFF1E293B)
                                )
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = { showSolutionModal = false }
                    ) {
                        Text(text = "OK", fontWeight = FontWeight.Black, color = Color(0xFFE15B17))
                    }
                },
                shape = RoundedCornerShape(24.dp),
                containerColor = Color.White
            )
        }
    }
}

data class ConfettiParticle(
    var x: Float,
    var y: Float,
    var vx: Float,
    var vy: Float,
    val color: Color,
    val size: Float,
    val shapeIsCircle: Boolean,
    var rotation: Float,
    var rotationSpeed: Float,
    var alpha: Float = 1f
)

@Composable
fun ConfettiShower(modifier: Modifier = Modifier) {
    val particles = remember {
        val colors = listOf(
            Color(0xFFFF5252), // Red
            Color(0xFFFFEB3B), // Yellow
            Color(0xFF2196F3), // Blue
            Color(0xFF4CAF50), // Green
            Color(0xFF9C27B0), // Purple
            Color(0xFFFF9800), // Orange
            Color(0xFF00BCD4), // Cyan
            Color(0xFFE91E63)  // Pink
        )
        List(100) {
            ConfettiParticle(
                x = (0..1000).random().toFloat(),
                y = -50f - (0..400).random().toFloat(),
                vx = (-5..5).random().toFloat(),
                vy = (5..15).random().toFloat(),
                color = colors.random(),
                size = (8..24).random().toFloat(),
                shapeIsCircle = (0..1).random() == 0,
                rotation = (0..360).random().toFloat(),
                rotationSpeed = (-10..10).random().toFloat()
            )
        }
    }

    var frameCount by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            withFrameMillis {
                frameCount++
            }
        }
    }

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        particles.forEach { particle ->
            // Re-initialize or wrap x bounds to fit canvas width
            if (particle.x > width || particle.x < 0) {
                particle.x = (0..width.toInt().coerceAtLeast(1)).random().toFloat()
            }

            // Physics update
            particle.x += particle.vx + kotlin.math.sin(frameCount * 0.04f + particle.size) * 0.6f
            particle.y += particle.vy
            particle.rotation += particle.rotationSpeed

            // Fade out when approaching bottom of screen
            if (particle.y > height * 0.75f) {
                particle.alpha = ((height - particle.y) / (height * 0.25f)).coerceIn(0f, 1f)
            }

            // Loop back to top to keep animation going
            if (particle.y > height) {
                particle.y = -50f - (0..100).random().toFloat()
                particle.x = (0..width.toInt().coerceAtLeast(1)).random().toFloat()
                particle.alpha = 1f
            }

            // Draw rotated particle
            rotate(degrees = particle.rotation, pivot = Offset(particle.x, particle.y)) {
                if (particle.shapeIsCircle) {
                    drawCircle(
                        color = particle.color.copy(alpha = particle.alpha),
                        radius = particle.size / 2,
                        center = Offset(particle.x, particle.y)
                    )
                } else {
                    drawRect(
                        color = particle.color.copy(alpha = particle.alpha),
                        topLeft = Offset(particle.x - particle.size / 2, particle.y - particle.size / 4),
                        size = Size(particle.size, particle.size / 2)
                    )
                }
            }
        }
    }
}

// --- 5. INTERACTIVE WORKSHEET RESULT SCREEN ---
@Composable
fun InteractiveWorksheetResultScreen(
    year: WorksheetYear,
    discipline: WorksheetDiscipline,
    worksheet: Worksheet,
    score: Int,
    total: Int,
    viewModel: MainViewModel,
    onRetry: () -> Unit,
    onFinish: () -> Unit
) {
    val passed = score.toFloat() / total >= 0.70

    // Clean up active sound or TTS services when exiting the Result screen
    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopSpeaking()
        }
    }

    // Voice feedback and progress tracking on launch (executed exactly once)
    LaunchedEffect(Unit) {
        viewModel.recordActivityCompletion(
            category = "Fichas",
            title = "Ficha_${worksheet.id}"
        )
        val speechText = if (passed) {
            "Muito bem! Parabéns! Estás no caminho certo para seres um campeão! Acertaste $score de $total perguntas!"
        } else {
            "Bom esforço! Nenhum mestre aprendeu sem errar! Tenta outra vez, eu estou contigo! Acertaste $score de $total perguntas."
        }
        viewModel.speak(speechText)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .background(discipline.color.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = if (passed) "🎉" else "💪", fontSize = 72.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = if (passed) "MUITO BEM! 🥳" else "Continua a tentar! 😊",
                fontSize = 26.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF1E293B),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (passed) {
                    "Incrível! Acertaste $score de $total perguntas!"
                } else {
                    "Bom esforço! Acertaste $score de $total perguntas. Com mais prática vais conseguir!"
                },
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF64748B),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Mascot dialog banner
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.5.dp, discipline.color.copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://i.imgur.com/O2uwUho.png")
                            .placeholder(R.drawable.img_ze_face_beret_alt)
                            .error(R.drawable.img_ze_face_beret_alt)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Zé Traquina",
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Column {
                        Text(
                            text = "Zé Traquina diz:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE15B17)
                        )
                        Text(
                            text = if (passed) {
                                "Parabéns! Estás no caminho certo para seres um campeão!"
                            } else {
                                "Nenhum mestre aprendeu sem errar! Tenta outra vez, eu estou contigo!"
                            },
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF1E293B),
                            lineHeight = 17.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Button actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onRetry,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    border = BorderStroke(2.dp, discipline.color),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp)
                ) {
                    Text(
                        text = "REPETIR 🔄",
                        fontWeight = FontWeight.Black,
                        color = discipline.color,
                        fontSize = 14.sp
                    )
                }

                Button(
                    onClick = onFinish,
                    colors = ButtonDefaults.buttonColors(containerColor = discipline.color),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp)
                ) {
                    Text(
                        text = "CONCLUIR 🏁",
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }
        } // missing Card closing brace

        if (passed) {
            ConfettiShower(modifier = Modifier.fillMaxSize())
        }
    }
}

