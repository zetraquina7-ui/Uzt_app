package com.example.ui.screens

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

data class LearningCard(
    val id: String,
    val category: String,
    val title: String,
    val question: String,
    val speech: String,
    val options: List<String>,
    val correctAnswer: String,
    val feedbackCorrect: String,
    val feedbackWrong: String
)

object LearningRepository {
    fun getCardsForCategory(category: String): List<LearningCard> {
        if (category == "Números") {
            return listOf(
                LearningCard(
                    id = "num_1",
                    category = "Números",
                    title = "Matemática Divertida",
                    question = "Qual é o número que vem depois do 7?",
                    speech = "Qual é o número que vem depois do sete?",
                    options = listOf("6", "8", "9"),
                    correctAnswer = "8",
                    feedbackCorrect = "Muito bem!",
                    feedbackWrong = "Quase! Tenta outra vez"
                ),
                LearningCard(
                    id = "num_2",
                    category = "Números",
                    title = "Corpo Humano",
                    question = "Quantos dedos tens numa mão?",
                    speech = "Quantos dedos tens numa mão?",
                    options = listOf("3", "5", "7"),
                    correctAnswer = "5",
                    feedbackCorrect = "Muito bem!",
                    feedbackWrong = "Quase! Tenta outra vez"
                ),
                LearningCard(
                    id = "num_3",
                    category = "Números",
                    title = "O Maior",
                    question = "Qual destes números é o maior?",
                    speech = "Qual destes números é o maior?",
                    options = listOf("2", "9", "4"),
                    correctAnswer = "9",
                    feedbackCorrect = "Muito bem!",
                    feedbackWrong = "Quase! Tenta outra vez"
                ),
                LearningCard(
                    id = "num_4",
                    category = "Números",
                    title = "Antes do 10",
                    question = "Qual é o número que vem antes do 10?",
                    speech = "Qual é o número que vem antes do dez?",
                    options = listOf("9", "11", "8"),
                    correctAnswer = "9",
                    feedbackCorrect = "Muito bem!",
                    feedbackWrong = "Quase! Tenta outra vez"
                )
            )
        }
        return emptyList()
    }
}

class LearningTts(context: Context) {
    private val ttsManager = com.example.audio.TTSManager(context)

    fun speak(text: String) {
        ttsManager.speak(text)
    }

    fun shutdown() {
        ttsManager.shutdown()
    }
}

class LearningViewModel : ViewModel() {
    private val _cards = MutableStateFlow<List<LearningCard>>(emptyList())
    private val _currentIndex = MutableStateFlow(0)
    private val _score = MutableStateFlow(0)
    private val _isFinished = MutableStateFlow(false)
    private val _answeredState = MutableStateFlow<AnswerState>(AnswerState.Idle)

    val cards: StateFlow<List<LearningCard>> = _cards.asStateFlow()
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()
    val score: StateFlow<Int> = _score.asStateFlow()
    val isFinished: StateFlow<Boolean> = _isFinished.asStateFlow()
    val answeredState: StateFlow<AnswerState> = _answeredState.asStateFlow()

    fun loadCategory(category: String) {
        _cards.value = LearningRepository.getCardsForCategory(category)
        _currentIndex.value = 0
        _score.value = 0
        _isFinished.value = false
        _answeredState.value = AnswerState.Idle
    }

    fun submitAnswer(answer: String) {
        if (_answeredState.value is AnswerState.Correct) return // already answered correctly

        val currentCard = _cards.value.getOrNull(_currentIndex.value) ?: return
        if (answer == currentCard.correctAnswer) {
            _score.value += 10
            _answeredState.value = AnswerState.Correct(currentCard.feedbackCorrect)
        } else {
            _answeredState.value = AnswerState.Wrong(currentCard.feedbackWrong)
        }
    }

    fun nextCard() {
        if (_currentIndex.value < _cards.value.size - 1) {
            _currentIndex.value += 1
            _answeredState.value = AnswerState.Idle
        } else {
            _isFinished.value = true
        }
    }
}

sealed class AnswerState {
    object Idle : AnswerState()
    data class Correct(val feedback: String) : AnswerState()
    data class Wrong(val feedback: String) : AnswerState()
}

@Composable
fun LearningCardScreen(category: String, onBack: () -> Unit) {
    val viewModel: LearningViewModel = viewModel()
    val context = LocalContext.current
    val tts = remember { LearningTts(context) }

    LaunchedEffect(category) {
        viewModel.loadCategory(category)
    }

    DisposableEffect(Unit) {
        onDispose {
            tts.shutdown()
        }
    }

    val cards by viewModel.cards.collectAsState()
    val currentIndex by viewModel.currentIndex.collectAsState()
    val score by viewModel.score.collectAsState()
    val isFinished by viewModel.isFinished.collectAsState()
    val answeredState by viewModel.answeredState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F4F6))
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF6366F1))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color.White)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Cartões Didáticos",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "⭐ $score",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

        if (cards.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Sem cartões disponíveis")
            }
            return
        }

        if (isFinished) {
            // End Screen
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("🎉", fontSize = 80.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Parabéns!",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Completaste os cartões.",
                    fontSize = 18.sp,
                    color = Color(0xFF4B5563)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Pontuação Final", fontSize = 16.sp, color = Color.Gray)
                        Text("$score", fontSize = 48.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFFF59E0B))
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Voltar ao Menu", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        } else {
            val card = cards[currentIndex]

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Progress Bar
                LinearProgressIndicator(
                    progress = { (currentIndex + 1).toFloat() / cards.size },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    color = Color(0xFF10B981),
                    trackColor = Color(0xFFE5E7EB)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Cartão ${currentIndex + 1} de ${cards.size}",
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                
                Spacer(modifier = Modifier.height(24.dp))

                // Card content
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = card.title,
                            color = Color(0xFF6366F1),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = card.question,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1F2937),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            onClick = { tts.speak(card.speech) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3F4F6), contentColor = Color.Black),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("🔊 Ouvir", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Options
                card.options.forEach { option ->
                    val isCorrect = answeredState is AnswerState.Correct && option == card.correctAnswer
                    val isWrongOption = answeredState is AnswerState.Wrong && (answeredState as? AnswerState.Wrong)?.feedback != "" // A bit of a hack, let's just make button red if wrong state. 
                    // Better approach: we don't know exactly WHICH button they pressed that was wrong unless we store it.
                    // Instead, let's just use standard style, and show feedback below.
                    
                    Button(
                        onClick = { viewModel.submitAnswer(option) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .height(64.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isCorrect) Color(0xFF10B981) else Color.White,
                            contentColor = if (isCorrect) Color.White else Color(0xFF1F2937)
                        ),
                        shape = RoundedCornerShape(16.dp),
                        elevation = ButtonDefaults.buttonElevation(4.dp)
                    ) {
                        Text(option, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Feedback
                AnimatedVisibility(visible = answeredState !is AnswerState.Idle) {
                    when (answeredState) {
                        is AnswerState.Correct -> {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("🎉 ${(answeredState as AnswerState.Correct).feedback}", color = Color(0xFF10B981), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = { viewModel.nextCard() },
                                    modifier = Modifier.height(56.dp).fillMaxWidth(0.7f),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6366F1))
                                ) {
                                    Text("Continuar", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        is AnswerState.Wrong -> {
                            Text("❌ ${(answeredState as AnswerState.Wrong).feedback}", color = Color(0xFFEF4444), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}
