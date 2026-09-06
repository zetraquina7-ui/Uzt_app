package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import com.example.ui.theme.SunshineYellow
import com.example.ui.theme.PreviewAppTheme
import com.example.viewmodel.MainViewModel
import com.example.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

data class WordGuessMockData(val level: Int, val word: String, val hintEmoji: String)

class WordGuessPreviewParameterProvider : PreviewParameterProvider<WordGuessMockData> {
    override val values: Sequence<WordGuessMockData> = sequenceOf(
        WordGuessMockData(1, "GATO", "🐱"),
        WordGuessMockData(2, "CASA", "🏠")
    )
}

@Composable
fun ConfettiEffect() {
    val isPreview = LocalInspectionMode.current
    val particles = remember { List(50) { RandomParticle() } }

    if (isPreview) {
        // Static frame for previews to prevent IDE freezing
        Canvas(modifier = Modifier.fillMaxSize()) {
            if (size.width <= 0f || size.height <= 0f) return@Canvas
            particles.forEach { p ->
                drawCircle(color = p.color, radius = p.radius, center = androidx.compose.ui.geometry.Offset(p.startX % size.width, (p.startY + 200f) % size.height))
            }
        }
        return
    }

    val progress = 0f

    Canvas(modifier = Modifier.fillMaxSize()) {
        if (size.width <= 0f || size.height <= 0f) return@Canvas
        particles.forEach { p ->
            val y = p.startY + (size.height * progress * p.speed)
            val x = p.startX + (Math.sin((progress * 10 + p.angle).toDouble()) * 50.0).toFloat()
            val currentY = (y % size.height + size.height) % size.height
            drawCircle(color = p.color, radius = p.radius, center = androidx.compose.ui.geometry.Offset(x, currentY))
        }
    }
}

data class RandomParticle(
    val startX: Float = Random.nextFloat() * 1000,
    val startY: Float = Random.nextFloat() * -500,
    val speed: Float = Random.nextFloat() * 2 + 0.5f,
    val angle: Float = Random.nextFloat() * 360,
    val color: Color = Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)),
    val radius: Float = Random.nextFloat() * 10 + 5
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JogoAdivinhaPalavraScreen(
    mainViewModel: MainViewModel? = null,
    onBack: () -> Unit
) {
    val isPreview = LocalInspectionMode.current
    var level by remember { mutableIntStateOf(1) }
    val words = listOf(
        "CASA", "BOLA", "GATO", "LIVRO", 
        "ESCOLA", "AMIGO", "SABÃO", "SOL", 
        "PALHAÇO", "GIRAFA", "PIANO", "FRUTA", 
        "ESTRELA", "COMBOIO", "JANELA", "PARQUE"
    )
    
    // Derived current word based on level
    val currentWord = words[(level - 1) % words.size]
    
    // Game logic for blanks based on level
    val blankCount = minOf(level, currentWord.length - 1)
    val displayWord = remember(currentWord, blankCount) {
        val chars = currentWord.toCharArray()
        val indicesToBlank = (0 until chars.size).shuffled().take(blankCount)
        chars.mapIndexed { index, c -> if (index in indicesToBlank) '_' else c }
    }
    
    // Use mutableStateListOf for better reactivity
    val userGuess = remember(level) {
        mutableStateListOf(*displayWord.map { if (it == '_') "" else it.toString() }.toTypedArray())
    }
    
    val isComplete by remember(userGuess, currentWord) {
        derivedStateOf { !userGuess.contains("") && userGuess.joinToString("") == currentWord }
    }
    
    LaunchedEffect(isComplete) {
        if (isComplete) {
            if (!isPreview) {
                mainViewModel?.playSound(R.raw.ze_traquina_e_fixe)
            }
            kotlinx.coroutines.delay(2000)
            level++
        }
    }
    
    // Generate options: correct letters (all letters of the word) + more random letters
    val options = remember(currentWord) {
        val wordLetters = currentWord.toSet()
        val allLetters = ('A'..'Z').toList()
        val distractors = allLetters.filter { it !in wordLetters }.shuffled().take(8)
        (wordLetters + distractors).shuffled()
    }

    val wordImages = mapOf(
        "CASA" to "🏠",
        "BOLA" to "⚽",
        "GATO" to "🐱",
        "LIVRO" to "📚"
    )

    var errorMessage by remember { mutableStateOf<String?>(null) }
    
    // Animation for scale
    val scale by animateFloatAsState(
        targetValue = if (isComplete) 1.5f else 1.0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy), label = "scale"
    )

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header with Back and Hint button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onBack,
                    colors = ButtonDefaults.buttonColors(containerColor = SunshineYellow),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.Black,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Voltar", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }

                Text("Nível $level: Adivinha a Palavra!", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)

                Button(
                    onClick = {
                        val firstBlankIndex = userGuess.indexOf("")
                        if (firstBlankIndex != -1 && firstBlankIndex in currentWord.indices) {
                            userGuess[firstBlankIndex] = currentWord[firstBlankIndex].toString()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SunshineYellow),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("💡 Dica", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black)
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Display word
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                displayWord.forEachIndexed { index, char ->
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (char == '_') (userGuess[index] ?: "_") else char.toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = Color.Red,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Letter panel
            if (!isComplete) {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    options.forEach { letter ->
                        Button(onClick = {
                            errorMessage = null
                            val firstBlank = userGuess.indexOf("")
                            if (firstBlank != -1) {
                                if (!isPreview) {
                                    mainViewModel?.playSound(R.raw.ze_traquina_e_fixe)
                                }
                                userGuess[firstBlank] = letter.toString()
                                
                                // Check if complete
                                if (!userGuess.contains("")) {
                                    if (userGuess.joinToString("") != currentWord) {
                                        if (!isPreview) {
                                            mainViewModel?.playSound(R.raw.ze_traquina_e_fixe)
                                        }
                                        errorMessage = "Ops! Essa não é a palavra. Tenta de novo!"
                                        // Reset userGuess
                                        userGuess.clear()
                                        userGuess.addAll(displayWord.map { if (it == '_') "" else it.toString() })
                                    }
                                }
                            }
                        }) {
                            Text(letter.toString())
                        }
                    }
                }
            } else {
                // Reward
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = wordImages[currentWord] ?: "✨",
                        fontSize = 120.sp,
                        modifier = Modifier.scale(scale)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "${currentWord} REVELADA! 🎉", 
                        fontSize = 32.sp, 
                        fontWeight = FontWeight.ExtraBold, 
                        color = Color(0xFFFF9800)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        level++
                        userGuess.clear()
                        userGuess.addAll(displayWord.map { if (it == '_') "" else it.toString() })
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SunshineYellow),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("🚀 PRÓXIMO NÍVEL (NÍVEL ${level + 1} - MAIS DIFÍCIL!)", fontWeight = FontWeight.ExtraBold, color = Color.Black)
                }
            }
        }
        
        if (isComplete) {
            ConfettiEffect()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JogoAdivinhaPalavraPreview(
    @PreviewParameter(WordGuessPreviewParameterProvider::class) mockData: WordGuessMockData
) {
    PreviewAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Nível ${mockData.level}: Adivinha a Palavra!", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(20.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    mockData.word.forEach { char ->
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = char.toString(), fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = mockData.hintEmoji,
                    fontSize = 100.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "${mockData.word} REVELADA! 🎉",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFFFF9800)
                )
            }
        }
    }
}
