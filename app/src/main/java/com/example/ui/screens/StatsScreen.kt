package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CompletedItem
import com.example.ui.theme.KidStarOrange
import com.example.ui.theme.SkyBluePrimary
import com.example.ui.theme.SunshineYellow
import com.example.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun StatsSection(
    mainViewModel: MainViewModel
) {
    val userProgress by mainViewModel.userProgress.collectAsState()
    val completedItems by mainViewModel.completedItems.collectAsState()

    var selectedTimePeriod by remember { mutableStateOf("Esta Semana") }
    var selectedCategoryFilter by remember { mutableStateOf("Todos") }
    var selectedChartIndex by remember { mutableStateOf<Int?>(3) } // Default select middle day

    val dateFormat = remember { SimpleDateFormat("dd/MM HH:mm", Locale.getDefault()) }

    // Chart mock dataset dynamically aggregated
    val daysLabels = remember { listOf("Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom") }
    val lineChartData = remember { listOf(15f, 30f, 45f, 75f, 60f, 90f, 110f) }
    val barChartData = remember {
        listOf(
            listOf(2, 3, 1, 1),
            listOf(4, 2, 2, 0),
            listOf(3, 5, 1, 2),
            listOf(6, 4, 3, 2),
            listOf(5, 3, 2, 1),
            listOf(8, 6, 4, 3),
            listOf(7, 5, 3, 2)
        )
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        // --- Header Section ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "O Meu Progresso 📈",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF0F172A)
                )
                Text(
                    text = "Acompanha o teu crescimento e conquistas!",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }
            Surface(
                shape = CircleShape,
                color = SunshineYellow,
                modifier = Modifier.size(44.dp),
                shadowElevation = 2.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = "🏆", fontSize = 22.sp)
                }
            }
        }

        // --- Summary Metric Cards Row ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MetricCard(
                title = "Total Estrelas",
                value = "${userProgress.starsCount} ⭐",
                subtitle = "+15 esta semana",
                cardColor = Color(0xFFFFF8E1),
                accentColor = KidStarOrange,
                modifier = Modifier.weight(1f)
            )
            MetricCard(
                title = "Jogos & Aulas",
                value = "${completedItems.size}",
                subtitle = "concluídos",
                cardColor = Color(0xFFE3F2FD),
                accentColor = SkyBluePrimary,
                modifier = Modifier.weight(1f)
            )
            MetricCard(
                title = "Sequência",
                value = "${userProgress.streakDays} dias 🔥",
                subtitle = "Ativo hoje",
                cardColor = Color(0xFFFBE9E7),
                accentColor = Color(0xFFFF5722),
                modifier = Modifier.weight(1f)
            )
        }

        // --- Gemini AI Performance Analysis & Adaptive Difficulty Card ---
        GeminiAdaptiveAnalysisCard(mainViewModel = mainViewModel)

        // --- Recharts Line/Area Chart: Stars & Learning Curve ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Timeline,
                            contentDescription = "Evolução",
                            tint = SkyBluePrimary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Evolução do Aprendizado (Recharts Line)",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 14.sp
                        )
                    }

                    Text(
                        text = "Média: +18 pts/dia",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                RechartsAreaChart(
                    labels = daysLabels,
                    data = lineChartData,
                    selectedIndex = selectedChartIndex,
                    onSelectIndex = { selectedChartIndex = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                )

                selectedChartIndex?.let { idx ->
                    if (idx in daysLabels.indices) {
                        Surface(
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFF1F5F9)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "📅 ${daysLabels[idx]}: ${lineChartData[idx].toInt()} Pontos de Aprendizagem",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = Color(0xFF1E293B)
                                )
                                Text(
                                    text = "+${(lineChartData[idx] / 5).toInt()} ⭐",
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 13.sp,
                                    color = KidStarOrange
                                )
                            }
                        }
                    }
                }
            }
        }

        // --- Recharts Grouped Bar Chart: Category Breakdown Per Day ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Atividades Diárias por Categoria (Recharts Bar)",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ChartLegendItem(color = SkyBluePrimary, label = "Jogos 🎮")
                    ChartLegendItem(color = SunshineYellow, label = "Aprender 📚")
                    ChartLegendItem(color = KidStarOrange, label = "Vídeos 🎵")
                    ChartLegendItem(color = Color(0xFF8E24AA), label = "ZéAI 🤖")
                }

                Spacer(modifier = Modifier.height(16.dp))

                RechartsBarChart(
                    labels = daysLabels,
                    data = barChartData,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )
            }
        }

        // --- Recharts Donut/Pie Chart & Skill Mastery ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Distribuição de Interações (Recharts Donut)",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RechartsDonutChart(
                        categoriesData = listOf(
                            "Jogos" to 38f,
                            "Aprender" to 32f,
                            "Vídeos" to 18f,
                            "Chat ZéAI" to 12f
                        ),
                        colors = listOf(
                            SkyBluePrimary,
                            SunshineYellow,
                            KidStarOrange,
                            Color(0xFF8E24AA)
                        ),
                        modifier = Modifier.size(120.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        CategoryRowDetail("🎮 Jogos Educativos", "38%", SkyBluePrimary)
                        CategoryRowDetail("📚 Leitura & Números", "32%", SunshineYellow)
                        CategoryRowDetail("🎵 Vídeos & Músicas", "18%", KidStarOrange)
                        CategoryRowDetail("🤖 Conversas ZéAI", "12%", Color(0xFF8E24AA))
                    }
                }
            }
        }

        // --- Skill Mastery Radar Progress ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "🎯 Habilidades Desenvolvidas",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp
                )

                SkillProgressItem(label = "Raciocínio Lógico & Matemática", progress = 0.85f, color = SkyBluePrimary)
                SkillProgressItem(label = "Vocabulário & Alfabetização", progress = 0.90f, color = SunshineYellow)
                SkillProgressItem(label = "Coordenação Motora & Agilidade", progress = 0.78f, color = KidStarOrange)
                SkillProgressItem(label = "Foco & Concentração", progress = 0.92f, color = Color(0xFF4CAF50))
                SkillProgressItem(label = "Criatividade & Expressão", progress = 0.84f, color = Color(0xFF8E24AA))
            }
        }

        // --- Activity History Timeline ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "📜 Histórico de Atividades",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp
                    )

                    TextButton(
                        onClick = { mainViewModel.clearActivityHistory() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteSweep,
                            contentDescription = "Limpar",
                            tint = Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Limpar", fontSize = 12.sp, color = Color.Gray)
                    }
                }

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    items(listOf("Todos", "Jogos", "Aprender", "Vídeos", "Chat")) { cat ->
                        val isSel = selectedCategoryFilter == cat
                        FilterChip(
                            selected = isSel,
                            onClick = { selectedCategoryFilter = cat },
                            label = { Text(cat, fontSize = 12.sp) },
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                val filteredItems = if (selectedCategoryFilter == "Todos") {
                    completedItems
                } else {
                    completedItems.filter { it.category.contains(selectedCategoryFilter, ignoreCase = true) }
                }

                if (filteredItems.isEmpty()) {
                    Text(
                        text = "Nenhuma atividade registada nesta categoria.",
                        fontSize = 13.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        filteredItems.take(10).forEach { item ->
                            ActivityHistoryRow(item = item, dateFormat = dateFormat)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatsScreen(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .testTag("stats_screen"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            StatsSection(mainViewModel = mainViewModel)
        }
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    subtitle: String,
    cardColor: Color,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontSize = 10.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = accentColor)
            Text(text = subtitle, fontSize = 9.sp, color = Color.Gray)
        }
    }
}

@Composable
private fun RechartsAreaChart(
    labels: List<String>,
    data: List<Float>,
    selectedIndex: Int?,
    onSelectIndex: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier.pointerInput(Unit) {
            detectTapGestures { offset ->
                val width = size.width
                val step = width / (data.size - 1)
                val clickedIdx = (offset.x / step).toInt().coerceIn(0, data.size - 1)
                onSelectIndex(clickedIdx)
            }
        }
    ) {
        val width = size.width
        val height = size.height - 30.dp.toPx()
        val maxVal = (data.maxOrNull() ?: 100f).coerceAtLeast(100f)

        val points = data.mapIndexed { idx, valF ->
            val x = idx * (width / (data.size - 1))
            val y = height - (valF / maxVal * height)
            Offset(x, y)
        }

        // Horizontal Grid Lines
        val gridLines = 4
        for (i in 0..gridLines) {
            val y = height * (i.toFloat() / gridLines)
            drawLine(
                color = Color(0xFFE2E8F0),
                start = Offset(0f, y),
                end = Offset(width, y),
                strokeWidth = 1.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )
        }

        // Curved Path & Area Fill
        val path = Path().apply {
            moveTo(points.first().x, points.first().y)
            for (i in 0 until points.size - 1) {
                val p1 = points[i]
                val p2 = points[i + 1]
                val cx = (p1.x + p2.x) / 2
                cubicTo(cx, p1.y, cx, p2.y, p2.x, p2.y)
            }
        }

        val fillPath = Path().apply {
            addPath(path)
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(SkyBluePrimary.copy(alpha = 0.45f), Color.Transparent)
            )
        )

        drawPath(
            path = path,
            color = SkyBluePrimary,
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )

        // Draw Data Points
        points.forEachIndexed { idx, pt ->
            val isSelected = idx == selectedIndex
            drawCircle(
                color = if (isSelected) SunshineYellow else SkyBluePrimary,
                radius = if (isSelected) 7.dp.toPx() else 4.dp.toPx(),
                center = pt
            )
            drawCircle(
                color = Color.White,
                radius = if (isSelected) 3.5.dp.toPx() else 2.dp.toPx(),
                center = pt
            )
        }
    }
}

@Composable
private fun RechartsBarChart(
    labels: List<String>,
    data: List<List<Int>>,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height - 20.dp.toPx()
        val numDays = labels.size
        val colWidth = width / numDays
        val maxTotal = 15f

        val categoryColors = listOf(
            SkyBluePrimary,
            SunshineYellow,
            KidStarOrange,
            Color(0xFF8E24AA)
        )

        data.forEachIndexed { dayIdx, categories ->
            val startX = dayIdx * colWidth + (colWidth * 0.15f)
            val barW = colWidth * 0.7f
            var currentY = height

            categories.forEachIndexed { catIdx, valCount ->
                val barH = (valCount / maxTotal) * height
                val topY = currentY - barH

                drawRoundRect(
                    color = categoryColors[catIdx % categoryColors.size],
                    topLeft = Offset(startX, topY),
                    size = Size(barW, barH),
                    cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                )

                currentY = topY
            }
        }
    }
}

@Composable
private fun RechartsDonutChart(
    categoriesData: List<Pair<String, Float>>,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val total = categoriesData.sumOf { it.second.toDouble() }.toFloat()
        var startAngle = -90f
        val strokeW = 20.dp.toPx()

        categoriesData.forEachIndexed { idx, (_, valF) ->
            val sweepAngle = (valF / total) * 360f

            drawArc(
                color = colors[idx % colors.size],
                startAngle = startAngle,
                sweepAngle = sweepAngle - 3f, // Gap between slices
                useCenter = false,
                style = Stroke(width = strokeW, cap = StrokeCap.Round),
                size = Size(size.width - strokeW, size.height - strokeW),
                topLeft = Offset(strokeW / 2, strokeW / 2)
            )

            startAngle += sweepAngle
        }
    }
}

@Composable
private fun CategoryRowDetail(title: String, percentage: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.width(160.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(color)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = title, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
        }
        Text(text = percentage, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = color)
    }
}

@Composable
private fun ChartLegendItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = label, fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun SkillProgressItem(label: String, progress: Float, color: Color) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(text = "${(progress * 100).toInt()}%", fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = color)
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = color,
            trackColor = Color(0xFFF1F5F9)
        )
    }
}

@Composable
private fun ActivityHistoryRow(item: CompletedItem, dateFormat: SimpleDateFormat) {
    val categoryIcon = when (item.category.lowercase()) {
        "jogos" -> Icons.Default.SportsEsports
        "aprender" -> Icons.Default.School
        "vídeos", "videos" -> Icons.Default.OndemandVideo
        "chat" -> Icons.Default.Face
        else -> Icons.Default.AutoAwesome
    }

    val categoryColor = when (item.category.lowercase()) {
        "jogos" -> SkyBluePrimary
        "aprender" -> SunshineYellow
        "vídeos", "videos" -> KidStarOrange
        "chat" -> Color(0xFF8E24AA)
        else -> SkyBluePrimary
    }

    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color(0xFFF8FAFC),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(categoryColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = categoryIcon,
                        contentDescription = item.category,
                        tint = categoryColor,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = item.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = Color(0xFF0F172A)
                    )
                    Text(
                        text = "${item.category} • ${dateFormat.format(Date(item.timestamp))}",
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = KidStarOrange.copy(alpha = 0.15f)
            ) {
                Text(
                    text = "+5 ⭐",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 11.sp,
                    color = KidStarOrange,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun GeminiAdaptiveAnalysisCard(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val adaptiveAnalysis by mainViewModel.adaptiveAnalysisState.collectAsState()
    val isAnalyzing by mainViewModel.isAnalyzingPerformance.collectAsState()

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.5.dp, Brush.horizontalGradient(listOf(Color(0xFF00E5FF), Color(0xFF9C27B0)))),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFF00E5FF), Color(0xFF7C4DFF))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "Gemini AI",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Análise Adaptativa Gemini AI 🤖✨",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp,
                            color = Color(0xFF0F172A)
                        )
                        Text(
                            text = "Ajuste dinâmico de dificuldade e tópicos",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }

                Button(
                    onClick = { mainViewModel.analyzePerformanceWithGemini() },
                    enabled = !isAnalyzing,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SkyBluePrimary,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    if (isAnalyzing) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Reanalisar",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Analisar", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            val analysis = adaptiveAnalysis
            if (analysis != null) {
                // Difficulty Badge Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Nível Recomendado:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )

                    val diffColor = when (analysis.recommendedDifficulty.lowercase()) {
                        "fácil", "facil" -> Color(0xFF4CAF50)
                        "médio", "medio" -> SkyBluePrimary
                        "desafiante" -> KidStarOrange
                        "avançado", "avancado" -> Color(0xFF9C27B0)
                        else -> SkyBluePrimary
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = diffColor.copy(alpha = 0.15f),
                        border = BorderStroke(1.dp, diffColor)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🎯 ${analysis.recommendedDifficulty.uppercase()}",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 12.sp,
                                color = diffColor
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Difficulty Explanation
                Text(
                    text = analysis.difficultyExplanation,
                    fontSize = 12.sp,
                    color = Color(0xFF334155),
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Suggested Topics
                Text(
                    text = "💡 Tópicos de Interesse Sugeridos:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E293B)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    analysis.suggestedTopics.forEach { topic ->
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = Color(0xFFF1F5F9),
                            border = BorderStroke(1.dp, Color(0xFFCBD5E1))
                        ) {
                            Text(
                                text = topic,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF475569),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Encouraging Message Box
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFFFFBEB),
                    border = BorderStroke(1.dp, Color(0xFFFDE68A)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🐸",
                            fontSize = 24.sp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Mensagem do Zé Traquina:",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFB45309)
                            )
                            Text(
                                text = analysis.encouragingMessage,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF78350F)
                            )
                        }
                    }
                }
            } else {
                Text(
                    text = "A carregar análise adaptativa de inteligência artificial...",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }
        }
    }
}
