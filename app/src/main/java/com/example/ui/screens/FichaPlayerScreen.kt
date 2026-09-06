package com.example.ui.screens

import com.example.Ficha
import com.example.FichaBloco
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R

import androidx.compose.material.icons.filled.Print
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.text.TextStyle

import com.example.util.WorksheetPrintManager
import com.example.util.WorksheetImageLoader
import kotlinx.coroutines.launch

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.material.icons.filled.Delete

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FichaPlayerScreen(
    fichas: List<Ficha>,
    initialIndex: Int = 0,
    onBack: () -> Unit,
    onNavigateToPdf: (String) -> Unit
) {
    var currentIndex by remember { mutableIntStateOf(initialIndex) }
    val ficha = fichas[currentIndex]
    val themeColor = Color(android.graphics.Color.parseColor(ficha.corTemaHex))
    
    // State for user inputs: Map<"fichaIndex_blocoIndex_subIndex", value>
    val userInputs = remember { mutableStateMapOf<String, String>() }
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(ficha.titulo, fontSize = 16.sp, fontWeight = FontWeight.Black)
                        Text("Ficha ${ficha.numero} de ${fichas.size}", fontSize = 12.sp, color = Color.Gray)
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("back_button")
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            WorksheetPrintManager.printFicha(context, ficha, userInputs, currentIndex)
                        },
                        modifier = Modifier.testTag("print_button")
                    ) {
                        Icon(Icons.Default.Print, contentDescription = "Imprimir", tint = themeColor)
                    }

                    TextButton(
                        onClick = { 
                            // Clear only inputs for current ficha
                            val keysToRemove = userInputs.keys.filter { it.startsWith("${currentIndex}_") }
                            keysToRemove.forEach { userInputs.remove(it) }
                        }
                    ) {
                        Text("Limpar", color = Color.Red, fontWeight = FontWeight.Bold)
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = themeColor.copy(alpha = 0.1f),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(
                            ficha.area,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            color = themeColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            )
        },
        bottomBar = {
            // Navigation Footer
            Surface(
                modifier = Modifier.fillMaxWidth(),
                tonalElevation = 8.dp,
                shadowElevation = 8.dp,
                color = Color.White
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { if (currentIndex > 0) currentIndex-- },
                            enabled = currentIndex > 0,
                            colors = ButtonDefaults.buttonColors(containerColor = themeColor),
                            modifier = Modifier.testTag("prev_ficha_button")
                        ) {
                            Icon(Icons.Default.ChevronLeft, contentDescription = null)
                            Text("Anterior")
                        }

                        Button(
                            onClick = { 
                                scope.launch {
                                    snackbarHostState.showSnackbar("Progresso guardado com sucesso!")
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Guardar")
                        }

                        Button(
                            onClick = { if (currentIndex < fichas.size - 1) currentIndex++ },
                            enabled = currentIndex < fichas.size - 1,
                            colors = ButtonDefaults.buttonColors(containerColor = themeColor),
                            modifier = Modifier.testTag("next_ficha_button")
                        ) {
                            Text("Próxima")
                            Icon(Icons.Default.ChevronRight, contentDescription = null)
                        }
                    }
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ficha.blocos.forEachIndexed { blocoIndex, bloco ->
                    FichaBlocoRenderer(
                        bloco = bloco, 
                        themeColor = themeColor,
                        inputs = userInputs,
                        fichaIndex = currentIndex,
                        blocoIndex = blocoIndex,
                        onNavigateToPdf = onNavigateToPdf
                    )
                }
                
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun FichaBlocoRenderer(
    bloco: FichaBloco, 
    themeColor: Color,
    inputs: MutableMap<String, String>,
    fichaIndex: Int,
    blocoIndex: Int,
    onNavigateToPdf: (String) -> Unit
) {
    when (bloco) {
        is FichaBloco.Link -> {
            Button(
                onClick = { onNavigateToPdf(bloco.url) },
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                Text(bloco.texto)
            }
        }
        is FichaBloco.Instrucao -> {
            Text(
                text = bloco.texto,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B),
                modifier = Modifier.fillMaxWidth()
            )
        }
        is FichaBloco.LetrasGrandes -> {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                bloco.letras.forEachIndexed { subIndex, letra ->
                    val key = "${fichaIndex}_${blocoIndex}_${subIndex}"
                    val value = inputs[key] ?: ""
                    
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = themeColor.copy(alpha = 0.05f)),
                            border = BorderStroke(2.dp, themeColor.copy(alpha = 0.2f))
                        ) {
                            Text(
                                text = letra,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Black,
                                color = themeColor.copy(alpha = 0.3f),
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                        
                        // Fillable box below the target letter
                        OutlinedTextField(
                            value = value,
                            onValueChange = { if (it.length <= 1) inputs[key] = it },
                            modifier = Modifier.width(60.dp).padding(top = 8.dp).testTag("letter_input_$key"),
                            textStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center),
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = themeColor,
                                unfocusedBorderColor = themeColor.copy(alpha = 0.3f)
                            )
                        )
                    }
                }
            }
        }
        is FichaBloco.CaixaPalavras -> {
            FlowRow(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                bloco.palavras.forEachIndexed { subIndex, palavra ->
                    val key = "${fichaIndex}_${blocoIndex}_${subIndex}"
                    val isSelected = inputs[key] == "selected"
                    
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(2.dp, if (isSelected) themeColor else Color.LightGray),
                        color = if (isSelected) themeColor.copy(alpha = 0.1f) else Color.White,
                        modifier = Modifier.clickable { 
                            inputs[key] = if (isSelected) "" else "selected"
                        }
                    ) {
                        Text(
                            text = palavra,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            fontSize = 20.sp,
                            fontWeight = if (isSelected) FontWeight.Black else FontWeight.Medium,
                            color = if (isSelected) themeColor else Color.Unspecified
                        )
                    }
                }
            }
        }
        is FichaBloco.LinhasEscrita -> {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                repeat(bloco.quantidade) { subIndex ->
                    val key = "${fichaIndex}_${blocoIndex}_${subIndex}"
                    val value = inputs[key] ?: ""
                    
                    TextField(
                        value = value,
                        onValueChange = { inputs[key] = it },
                        modifier = Modifier.fillMaxWidth().height(56.dp).testTag("line_input_$key"),
                        placeholder = { Text("Escreve aqui...", color = Color.LightGray) },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color(0xFFF8FAFC),
                            focusedContainerColor = Color.White,
                            unfocusedIndicatorColor = Color.LightGray,
                            focusedIndicatorColor = themeColor
                        ),
                        textStyle = TextStyle(fontSize = 18.sp)
                    )
                }
            }
        }
        is FichaBloco.TabelaDuasColunas -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
            ) {
                bloco.esquerda.zip(bloco.direita).forEachIndexed { index, (esq, dir) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(if (index % 2 == 0) Color.Transparent else Color(0xFFF8FAFC))
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            if (esq.startsWith("mascote_")) {
                                ImageRenderer(esq)
                            } else {
                                Text(esq, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(themeColor.copy(alpha = 0.1f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("→", color = themeColor, fontWeight = FontWeight.Bold)
                        }
                        
                        // Interactive right side
                        Box(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
                            val key = "${fichaIndex}_${blocoIndex}_${index}"
                            val value = inputs[key] ?: ""
                            
                            BasicTextField(
                                value = value,
                                onValueChange = { inputs[key] = it },
                                modifier = Modifier.fillMaxWidth().border(1.dp, Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(4.dp)).padding(8.dp).testTag("table_input_$key"),
                                textStyle = TextStyle(fontSize = 16.sp, color = themeColor, fontWeight = FontWeight.Medium),
                                decorationBox = { innerTextField ->
                                    if (value.isEmpty() && !dir.startsWith("mascote_")) {
                                        Text(dir, color = Color.LightGray, fontSize = 16.sp)
                                    }
                                    innerTextField()
                                }
                            )
                        }
                    }
                }
            }
        }
        is FichaBloco.CaixinhasCompletar -> {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                bloco.itens.forEachIndexed { itemIndex, item ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        var underscoreCount = 0
                        item.split("_").forEachIndexed { partIndex, part ->
                            if (part.isNotEmpty()) {
                                Text(part, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            }
                            
                            // Check if there was an underscore between parts or at the end
                            if (partIndex < item.split("_").size - 1 || item.endsWith("_")) {
                                val key = "${fichaIndex}_${blocoIndex}_${itemIndex}_${underscoreCount}"
                                val value = inputs[key] ?: ""
                                
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .border(2.dp, themeColor, RoundedCornerShape(6.dp))
                                        .padding(2.dp)
                                        .testTag("completion_input_$key"),
                                    contentAlignment = Alignment.Center
                                ) {
                                    BasicTextField(
                                        value = value,
                                        onValueChange = { if (it.length <= 1) inputs[key] = it },
                                        modifier = Modifier.fillMaxWidth(),
                                        textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Black, textAlign = TextAlign.Center, color = themeColor),
                                        singleLine = true
                                    )
                                }
                                underscoreCount++
                            }
                        }
                    }
                }
            }
        }
        is FichaBloco.TextoLeitura -> {
            Surface(
                color = Color(0xFFFFFBEB),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color(0xFFFDE68A)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = bloco.texto,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 20.sp,
                    lineHeight = 28.sp,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    textAlign = TextAlign.Center
                )
            }
        }
        is FichaBloco.Imagem -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                ImageRenderer(bloco.nomeRecurso)
                Text(
                    text = bloco.legenda,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
        is FichaBloco.EspacoDesenho -> {
            val linesState = remember { mutableStateListOf<List<Offset>>() }
            val currentLine = remember { mutableStateOf<List<Offset>>(emptyList()) }
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(bloco.alturaDp.dp + 60.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFFDFDFD))
                    .border(2.dp, themeColor.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(themeColor.copy(alpha = 0.05f))
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val label = when (bloco.tipoGrelha) {
                        "pontos" -> "Papel Ponteado (Usa o dedo para desenhar/ligar)"
                        "quadrados" -> "Papel Quadriculado (Usa o dedo para desenhar)"
                        "autorretrato" -> "Moldura do teu Autorretrato (Desenha a tua cara na moldura)"
                        "familia" -> "Árvore Genealógica (Completa ou pinta as silhuetas)"
                        "casa_porta" -> "Planta da Casa (Desenha uma bicicleta à frente da porta)"
                        "roda_crianças" -> "Desenha-te no espaço em branco para fechar a roda de crianças"
                        "lavar_maos" -> "Depois de usar a casa de banho: o que fazemos? (Desenha a ação)"
                        "antes_sumo" -> "O que aconteceu antes de fazer o sumo? (Desenha a ação)"
                        "profissao" -> "A minha Profissão de Sonho (Desenha o que queres ser)"
                        "labirinto_pascoa" -> "Labirinto dos Ovos da Páscoa (Desenha o caminho correto)"
                        else -> "Espaço de Escrita/Desenho Livre"
                    }
                    Text(
                        text = label,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                    IconButton(
                        onClick = {
                            linesState.clear()
                            currentLine.value = emptyList()
                        },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Limpar desenho",
                            tint = Color.Red,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    currentLine.value = listOf(offset)
                                },
                                onDrag = { change, dragAmount ->
                                    change.consume()
                                    currentLine.value = currentLine.value + change.position
                                },
                                onDragEnd = {
                                    if (currentLine.value.isNotEmpty()) {
                                        linesState.add(currentLine.value)
                                        currentLine.value = emptyList()
                                    }
                                }
                            )
                        }
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        // Draw custom visual backgrounds based on type
                        when (bloco.tipoGrelha) {
                            "pontos" -> {
                                val spacing = 24.dp.toPx()
                                val dotRadius = 3.dp.toPx()
                                var x = spacing
                                while (x < size.width) {
                                    var y = spacing
                                    while (y < size.height) {
                                        drawCircle(
                                            color = Color.LightGray.copy(alpha = 0.8f),
                                            radius = dotRadius,
                                            center = Offset(x, y)
                                        )
                                        y += spacing
                                    }
                                    x += spacing
                                }
                            }
                            "quadrados" -> {
                                val spacing = 24.dp.toPx()
                                var x = spacing
                                while (x < size.width) {
                                    drawLine(
                                        color = Color.LightGray.copy(alpha = 0.4f),
                                        start = Offset(x, 0f),
                                        end = Offset(x, size.height),
                                        strokeWidth = 1.dp.toPx()
                                    )
                                    x += spacing
                                }
                                var y = spacing
                                while (y < size.height) {
                                    drawLine(
                                        color = Color.LightGray.copy(alpha = 0.4f),
                                        start = Offset(0f, y),
                                        end = Offset(size.width, y),
                                        strokeWidth = 1.dp.toPx()
                                    )
                                    y += spacing
                                }
                            }
                            "autorretrato" -> {
                                val centerX = size.width / 2f
                                val centerY = size.height / 2f - 20.dp.toPx()
                                val radiusX = 60.dp.toPx()
                                val radiusY = 75.dp.toPx()
                                
                                drawCircle(
                                    color = Color(0xFFFFCC99).copy(alpha = 0.3f),
                                    radius = radiusX,
                                    center = Offset(centerX, centerY)
                                )
                                drawCircle(
                                    color = Color.Gray,
                                    radius = radiusX,
                                    center = Offset(centerX, centerY),
                                    style = androidx.compose.ui.graphics.drawscope.Stroke(
                                        width = 2.dp.toPx(),
                                        pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                                    )
                                )
                                drawLine(
                                    color = Color.Gray,
                                    start = Offset(centerX - 15.dp.toPx(), centerY + radiusY - 15.dp.toPx()),
                                    end = Offset(centerX - 15.dp.toPx(), centerY + radiusY + 15.dp.toPx()),
                                    strokeWidth = 2.dp.toPx()
                                )
                                drawLine(
                                    color = Color.Gray,
                                    start = Offset(centerX + 15.dp.toPx(), centerY + radiusY - 15.dp.toPx()),
                                    end = Offset(centerX + 15.dp.toPx(), centerY + radiusY + 15.dp.toPx()),
                                    strokeWidth = 2.dp.toPx()
                                )
                                drawLine(
                                    color = Color.Gray,
                                    start = Offset(centerX - 60.dp.toPx(), centerY + radiusY + 15.dp.toPx()),
                                    end = Offset(centerX + 60.dp.toPx(), centerY + radiusY + 15.dp.toPx()),
                                    strokeWidth = 2.dp.toPx()
                                )
                            }
                            "familia" -> {
                                val spacing = size.width / 6f
                                val groundY = size.height - 20.dp.toPx()
                                drawLine(color = Color.LightGray, start = Offset(10f, groundY), end = Offset(size.width - 10f, groundY), strokeWidth = 2.dp.toPx())
                                
                                for (i in 0 until 5) {
                                    val x = spacing * (i + 0.8f)
                                    val headY = size.height / 2f - (if (i < 2) 10.dp.toPx() else 20.dp.toPx())
                                    val headRadius = if (i == 4) 10.dp.toPx() else 15.dp.toPx()
                                    val bodyLength = if (i == 4) 25.dp.toPx() else 40.dp.toPx()
                                    
                                    drawCircle(color = Color.Gray.copy(alpha = 0.5f), radius = headRadius, center = Offset(x, headY), style = androidx.compose.ui.graphics.drawscope.Stroke(2.dp.toPx()))
                                    drawLine(color = Color.Gray.copy(alpha = 0.5f), start = Offset(x, headY + headRadius), end = Offset(x, headY + headRadius + bodyLength), strokeWidth = 2.dp.toPx())
                                    drawLine(color = Color.Gray.copy(alpha = 0.5f), start = Offset(x - 10.dp.toPx(), headY + headRadius + 10.dp.toPx()), end = Offset(x + 10.dp.toPx(), headY + headRadius + 10.dp.toPx()), strokeWidth = 2.dp.toPx())
                                    drawLine(color = Color.Gray.copy(alpha = 0.5f), start = Offset(x, headY + headRadius + bodyLength), end = Offset(x - 8.dp.toPx(), headY + headRadius + bodyLength + 15.dp.toPx()), strokeWidth = 2.dp.toPx())
                                    drawLine(color = Color.Gray.copy(alpha = 0.5f), start = Offset(x, headY + headRadius + bodyLength), end = Offset(x + 8.dp.toPx(), headY + headRadius + bodyLength + 15.dp.toPx()), strokeWidth = 2.dp.toPx())
                                }
                            }
                            "casa_porta" -> {
                                val w = size.width
                                val h = size.height
                                drawRect(color = Color.Gray, topLeft = Offset(20f, 20f), size = androidx.compose.ui.geometry.Size(w - 40f, h - 40f), style = androidx.compose.ui.graphics.drawscope.Stroke(2.dp.toPx()))
                                drawLine(color = Color.Gray, start = Offset(20f, 20f), end = Offset(w/2f, 2f), strokeWidth = 2.dp.toPx())
                                drawLine(color = Color.Gray, start = Offset(w - 20f, 20f), end = Offset(w/2f, 2f), strokeWidth = 2.dp.toPx())
                                drawLine(color = Color.Gray, start = Offset(20f, h / 2f), end = Offset(w - 20f, h / 2f), strokeWidth = 2.dp.toPx())
                                drawLine(color = Color.Gray, start = Offset(w / 2f, 20f), end = Offset(w / 2f, h / 2f), strokeWidth = 2.dp.toPx())
                                drawLine(color = Color.Gray, start = Offset(w / 3f, h / 2f), end = Offset(w / 3f, h - 20f), strokeWidth = 2.dp.toPx())
                                drawLine(color = Color.Gray, start = Offset(2f * w / 3f, h / 2f), end = Offset(2f * w / 3f, h - 20f), strokeWidth = 2.dp.toPx())
                            }
                            "roda_crianças" -> {
                                val cx = size.width / 2f
                                val cy = size.height / 2f
                                val r = 50.dp.toPx()
                                val angles = listOf(0.0, 120.0, 240.0)
                                angles.forEachIndexed { idx, angle ->
                                    val rad = Math.toRadians(angle)
                                    val kx = (cx + r * Math.cos(rad)).toFloat()
                                    val ky = (cy + r * Math.sin(rad)).toFloat()
                                    drawCircle(color = Color.Gray.copy(alpha = 0.5f), radius = 8.dp.toPx(), center = Offset(kx, ky), style = androidx.compose.ui.graphics.drawscope.Stroke(2.dp.toPx()))
                                    drawLine(color = Color.Gray.copy(alpha = 0.5f), start = Offset(kx, ky + 8.dp.toPx()), end = Offset(kx, ky + 25.dp.toPx()), strokeWidth = 2.dp.toPx())
                                    drawLine(color = Color.Gray.copy(alpha = 0.5f), start = Offset(kx - 6.dp.toPx(), ky + 12.dp.toPx()), end = Offset(kx + 6.dp.toPx(), ky + 12.dp.toPx()), strokeWidth = 2.dp.toPx())
                                }
                            }
                            "lavar_maos" -> {
                                val cx = size.width / 2f
                                val cy = size.height / 2f
                                drawCircle(color = Color.LightGray.copy(alpha = 0.3f), radius = 40.dp.toPx(), center = Offset(cx, cy))
                            }
                            "antes_sumo" -> {
                                val cx = size.width / 2f
                                val cy = size.height / 2f
                                drawCircle(color = Color.Green.copy(alpha = 0.1f), radius = 50.dp.toPx(), center = Offset(cx, cy))
                                drawCircle(color = Color.Green.copy(alpha = 0.2f), radius = 50.dp.toPx(), center = Offset(cx, cy), style = androidx.compose.ui.graphics.drawscope.Stroke(2.dp.toPx()))
                                drawCircle(color = Color(0xFFFF9900), radius = 8.dp.toPx(), center = Offset(cx - 20.dp.toPx(), cy - 10.dp.toPx()))
                                drawCircle(color = Color(0xFFFF9900), radius = 8.dp.toPx(), center = Offset(cx + 20.dp.toPx(), cy + 10.dp.toPx()))
                                drawCircle(color = Color(0xFFFF9900), radius = 8.dp.toPx(), center = Offset(cx, cy - 30.dp.toPx()))
                            }
                            "profissao" -> {
                                val cx = size.width / 2f
                                val cy = size.height / 2f
                                drawCircle(color = Color(0xFFE0F2FE), radius = 60.dp.toPx(), center = Offset(cx, cy))
                            }
                            "labirinto_pascoa" -> {
                                val cx = size.width / 2f
                                val cy = size.height / 2f
                                drawCircle(color = Color.LightGray, radius = 20.dp.toPx(), center = Offset(cx, cy), style = androidx.compose.ui.graphics.drawscope.Stroke(2.dp.toPx()))
                                drawCircle(color = Color.LightGray, radius = 45.dp.toPx(), center = Offset(cx, cy), style = androidx.compose.ui.graphics.drawscope.Stroke(2.dp.toPx()))
                                drawCircle(color = Color.LightGray, radius = 70.dp.toPx(), center = Offset(cx, cy), style = androidx.compose.ui.graphics.drawscope.Stroke(2.dp.toPx()))
                                drawCircle(color = Color.White, radius = 8.dp.toPx(), center = Offset(cx, cy - 20.dp.toPx()))
                                drawCircle(color = Color.White, radius = 8.dp.toPx(), center = Offset(cx - 45.dp.toPx(), cy))
                                drawCircle(color = Color.White, radius = 8.dp.toPx(), center = Offset(cx + 70.dp.toPx(), cy))
                                drawCircle(color = Color(0xFFFCD34D), radius = 6.dp.toPx(), center = Offset(cx - 4.dp.toPx(), cy - 4.dp.toPx()))
                                drawCircle(color = Color(0xFFF472B6), radius = 6.dp.toPx(), center = Offset(cx + 4.dp.toPx(), cy + 4.dp.toPx()))
                            }
                        }
                        
                        // Draw all finished lines
                        linesState.forEach { line ->
                            for (i in 0 until line.size - 1) {
                                drawLine(
                                    color = themeColor,
                                    start = line[i],
                                    end = line[i + 1],
                                    strokeWidth = 4.dp.toPx(),
                                    cap = StrokeCap.Round
                                )
                            }
                        }
                        
                        // Draw current active line
                        val current = currentLine.value
                        if (current.isNotEmpty()) {
                            for (i in 0 until current.size - 1) {
                                drawLine(
                                    color = themeColor,
                                    start = current[i],
                                    end = current[i + 1],
                                    strokeWidth = 4.dp.toPx(),
                                    cap = StrokeCap.Round
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable () -> Unit
) {
    androidx.compose.foundation.layout.FlowRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement
    ) {
        content()
    }
}

@Composable
fun ImageRenderer(resourceName: String) {
    val context = LocalContext.current
    val resourceId = remember(resourceName) {
        WorksheetImageLoader.getDrawableId(context, resourceName)
    }
    
    if (resourceId != 0) {
        Image(
            painter = painterResource(id = resourceId),
            contentDescription = null,
            modifier = Modifier
                .size(140.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Fit
        )
    } else if (WorksheetImageLoader.isRemoteUrl(resourceName)) {
        AsyncImage(
            model = resourceName,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Fit
        )
    } else {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color(0xFFF1F5F9), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = WorksheetImageLoader.getFallbackDrawable()),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(48.dp)
                )
                Text("Zé Traquina", color = Color.LightGray, fontSize = 10.sp)
            }
        }
    }
}
