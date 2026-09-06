package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.ripple.rememberRipple
import com.example.viewmodel.MainViewModel

data class DictionaryItem(
    val word: String,
    val emoji: String,
    val color: Color
)

@Composable
fun VisualDictionaryScreen(
    mainViewModel: MainViewModel,
    onBack: () -> Unit
) {
    val items = remember {
        listOf(
            DictionaryItem("Abelha", "🐝", Color(0xFFFFD54F)),
            DictionaryItem("Balão", "🎈", Color(0xFFF48FB1)),
            DictionaryItem("Carro", "🚗", Color(0xFF81D4FA)),
            DictionaryItem("Dinossauro", "🦖", Color(0xFFA5D6A7)),
            DictionaryItem("Elefante", "🐘", Color(0xFFB0BEC5)),
            DictionaryItem("Foguete", "🚀", Color(0xFFCE93D8)),
            DictionaryItem("Gato", "🐱", Color(0xFFFFCC80)),
            DictionaryItem("Hipopótamo", "🦛", Color(0xFFCFD8DC)),
            DictionaryItem("Íman", "🧲", Color(0xFFEF9A9A)),
            DictionaryItem("Joaninha", "🐞", Color(0xFFE57373)),
            DictionaryItem("Kiwi", "🥝", Color(0xFFC5E1A5)),
            DictionaryItem("Leão", "🦁", Color(0xFFFFB74D)),
            DictionaryItem("Maçã", "🍎", Color(0xFFE57373)),
            DictionaryItem("Nuvem", "☁️", Color(0xFF90CAF9)),
            DictionaryItem("Óculos", "👓", Color(0xFFFFE082)),
            DictionaryItem("Pássaro", "🐦", Color(0xFF80DEEA)),
            DictionaryItem("Queijo", "🧀", Color(0xFFFFF59D)),
            DictionaryItem("Relógio", "⌚", Color(0xFFBCAAA4)),
            DictionaryItem("Sol", "☀️", Color(0xFFFFF176)),
            DictionaryItem("Tartaruga", "🐢", Color(0xFFA5D6A7)),
            DictionaryItem("Urso", "🐻", Color(0xFFD7CCC8)),
            DictionaryItem("Vulcão", "🌋", Color(0xFFFFAB91)),
            DictionaryItem("Xícara", "☕", Color(0xFFF48FB1)),
            DictionaryItem("Zebra", "🦓", Color(0xFFEEEEEE))
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Header
        Surface(
            color = Color(0xFF4CAF50),
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    mainViewModel.playClickSound()
                    onBack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Dicionário Visual",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(items) { item ->
                DictionaryCard(item = item, mainViewModel = mainViewModel)
            }
        }
    }
}

@Composable
fun DictionaryCard(
    item: DictionaryItem,
    mainViewModel: MainViewModel
) {
    var isTapped by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(targetValue = if (isTapped) 1.05f else 1.0f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .scale(scale)
            .clickable {
                isTapped = true
                mainViewModel.playClickSound()
                android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({ isTapped = false }, 300)
            },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = item.color),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = item.emoji,
                fontSize = 54.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Surface(
                color = Color.White.copy(alpha = 0.9f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.word,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .clickable {
                                mainViewModel.playClickSound()
                                mainViewModel.speak(item.word)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Ouvir",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}
