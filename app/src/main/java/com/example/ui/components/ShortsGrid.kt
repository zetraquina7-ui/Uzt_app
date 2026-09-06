package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.ui.screens.YouTubeVideoTrack
import com.example.util.AppImageLoader

/**
 * ShortsGrid (LAYOUT VERTICAL PARA ECRÃ NORMAL)
 * Grelha de 3 colunas com scroll vertical.
 */
@Composable
fun ShortsGrid(
    shorts: List<YouTubeVideoTrack>,
    selectedVideoId: String?,
    isLoading: Boolean,
    onShortSelected: (YouTubeVideoTrack) -> Unit,
    modifier: Modifier = Modifier
) {
    val accentColor = Color(0xFFEC4899)

    Box(modifier = modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            if (isLoading && shorts.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = accentColor,
                            modifier = Modifier.size(28.dp),
                            strokeWidth = 2.5.dp
                        )
                    }
                }
            }

            itemsIndexed(
                items = shorts,
                key = { index, item -> "${item.videoId ?: item.id}_$index" }
            ) { index, track ->
                ShortCardItem(
                    track = track,
                    index = index + 1,
                    isSelected = (selectedVideoId != null && selectedVideoId == track.videoId),
                    accentColor = accentColor,
                    onClick = { onShortSelected(track) }
                )
            }
        }
    }
}

/**
 * ShortsHorizontalCarousel (LAYOUT HORIZONTAL PARA MODO LANDSCAPE/FULLSCREEN)
 */
@Composable
fun ShortsHorizontalCarousel(
    shorts: List<YouTubeVideoTrack>,
    selectedVideoId: String?,
    isLoading: Boolean,
    onShortSelected: (YouTubeVideoTrack) -> Unit,
    modifier: Modifier = Modifier
) {
    val accentColor = Color(0xFFEC4899)

    Box(modifier = modifier.fillMaxWidth()) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading && shorts.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .width(95.dp)
                            .height(125.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = accentColor,
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    }
                }
            }

            itemsIndexed(
                items = shorts,
                key = { index, item -> "short_land_${item.videoId ?: item.id}_$index" }
            ) { index, track ->
                ShortCardItem(
                    track = track,
                    index = index + 1,
                    isSelected = (selectedVideoId != null && selectedVideoId == track.videoId),
                    accentColor = accentColor,
                    onClick = { onShortSelected(track) }
                )
            }
        }
    }
}

@Composable
fun ShortCardItem(
    track: YouTubeVideoTrack,
    index: Int,
    isSelected: Boolean,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val imageRequest = remember(track.thumbnailUrl, track.videoId) {
        val rawUrl = track.thumbnailUrl
            ?: (if (!track.videoId.isNullOrBlank()) "https://img.youtube.com/vi/${track.videoId}/hqdefault.jpg" else "")
            
        ImageRequest.Builder(context)
            .data(rawUrl)
            .crossfade(250)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCacheKey("short_thumb_${track.videoId ?: track.id}")
            .diskCacheKey("short_thumb_${track.videoId ?: track.id}")
            .build()
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.96f)
        ),
        border = BorderStroke(
            width = if (isSelected) 2.dp else 0.5.dp,
            color = if (isSelected) accentColor else Color.LightGray.copy(alpha = 0.45f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 3.dp else 1.5.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(9f / 13f)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFF0F172A)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = imageRequest,
                    imageLoader = AppImageLoader.get(context),
                    contentDescription = track.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.6f)
                                )
                            )
                        )
                )

                Surface(
                    shape = CircleShape,
                    color = if (isSelected) accentColor else Color.Black.copy(alpha = 0.6f),
                    modifier = Modifier.size(20.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(13.dp)
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(3.dp),
                    color = Color(0xFFE11D48),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(2.dp)
                ) {
                    Text(
                        text = "⚡",
                        fontSize = 7.sp,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 2.dp, vertical = 0.5.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = track.title,
                fontSize = 8.5.sp,
                lineHeight = 10.5.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) accentColor else Color(0xFF1E293B),
                maxLines = 2,
                minLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
