package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.R

/**
 * ScreenHeader: Cabeçalho com o design estético de cápsula de vidro (Glass Capsule Dock)
 * idêntico à barra de menus inferior (UniversoBottomNavBar), mantendo as cores e gradientes de cada menu.
 */
@Composable
fun ScreenHeader(
    title: String,
    subtitle: String,
    icon: String,
    gradientColors: List<Color>,
    modifier: Modifier = Modifier
) {
    val topColor = gradientColors.firstOrNull() ?: Color(0xFF004A58)
    val bottomColor = gradientColors.lastOrNull() ?: Color(0xFF001F28)

    // Borda luminosa com as cores do próprio cabeçalho
    val borderColors = listOf(
        topColor.copy(alpha = 0.95f),
        Color.White.copy(alpha = 0.85f),
        bottomColor.copy(alpha = 0.75f)
    )

    val headerShape = RoundedCornerShape(18.dp)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 12.dp,
                    shape = headerShape,
                    ambientColor = topColor.copy(alpha = 0.45f),
                    spotColor = topColor.copy(alpha = 0.45f)
                )
                .clip(headerShape)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            topColor.copy(alpha = 0.70f),
                            bottomColor.copy(alpha = 0.85f)
                        )
                    )
                )
                .border(
                    width = 1.2.dp,
                    brush = Brush.verticalGradient(colors = borderColors),
                    shape = headerShape
                )
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Imagem à esquerda do cabeçalho com cantos ligeiramente arredondados
                val imageShape = RoundedCornerShape(8.dp)
                Surface(
                    shape = imageShape,
                    color = Color.White.copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(
                        0.8.dp,
                        Color.White.copy(alpha = 0.5f)
                    ),
                    shadowElevation = 2.dp,
                    modifier = Modifier.size(36.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://i.imgur.com/O2uwUho.png")
                            .crossfade(true)
                            .build(),
                        contentDescription = "Logo Cabeçalho",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(imageShape)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                // Título e Subtítulo
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Text(
                        text = subtitle,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White.copy(alpha = 0.92f)
                    )
                }

                // Divisória vertical subtil estilo dock
                Box(
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .height(20.dp)
                        .width(1.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0f),
                                    Color.White.copy(alpha = 0.65f),
                                    Color.White.copy(alpha = 0f)
                                )
                            )
                        )
                )

                // Ícone / Emoji em cápsula circular de vidro luminoso
                Surface(
                    shape = CircleShape,
                    color = Color.White.copy(alpha = 0.22f),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        Color.White.copy(alpha = 0.6f)
                    ),
                    modifier = Modifier.size(30.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = icon, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

