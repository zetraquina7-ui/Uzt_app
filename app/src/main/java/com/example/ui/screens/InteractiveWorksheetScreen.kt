package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

import com.example.data.FichaConteudo
import com.example.data.FichaQuestao

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InteractiveWorksheetScreen(ficha: FichaConteudo, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(ficha.titulo) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(Modifier.padding(padding).padding(16.dp)) {
            items(ficha.questoes) { questao ->
                Card(Modifier.padding(vertical = 8.dp).fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text(when (questao) {
                            is FichaQuestao.Escrever -> "✏️ ${questao.enunciado}"
                            is FichaQuestao.Rodeia -> "⭕ ${questao.enunciado}"
                            is FichaQuestao.Completa -> "📝 ${questao.enunciado}"
                            is FichaQuestao.Ilustrar -> "🎨 ${questao.enunciado}"
                            is FichaQuestao.Frase -> "🗣️ ${questao.enunciado}"
                        }, style = MaterialTheme.typography.titleMedium)
                        
                        when (questao) {
                            is FichaQuestao.Escrever -> TextField(value = "", onValueChange = {}, label = { Text("Escreve aqui") }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp))
                            is FichaQuestao.Rodeia -> {
                                Text(questao.lista.joinToString(", "), style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(top = 8.dp))
                            }
                            is FichaQuestao.Completa -> {
                                TextField(value = "", onValueChange = {}, label = { Text("Completa aqui") }, modifier = Modifier.fillMaxWidth().padding(top = 8.dp))
                            }
                            is FichaQuestao.Ilustrar -> {
                                Box(Modifier.fillMaxWidth().height(150.dp).padding(top = 8.dp)) {
                                    var path by remember { mutableStateOf(Path()) }
                                    Canvas(modifier = Modifier.fillMaxSize().pointerInput(Unit) {
                                        detectDragGestures { change, dragAmount ->
                                            path.moveTo(change.position.x - dragAmount.x, change.position.y - dragAmount.y)
                                            path.lineTo(change.position.x, change.position.y)
                                        }
                                    }) {
                                        drawPath(path, Color.Black, style = Stroke(width = 5f))
                                    }
                                }
                            }
                            is FichaQuestao.Frase -> {}
                        }
                    }
                }
            }
        }
    }
}
