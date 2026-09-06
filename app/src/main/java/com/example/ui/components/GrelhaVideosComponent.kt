package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.request.ImageRequest
import com.example.ui.screens.YouTubeVideoTrack

/**
 * 1. GrelhaVideosComponent (LAYOUT VERTICAL PADRÃO PARA O ECRÃ NORMAL)
 * Grelha de 3 colunas (3 cartões por linha) com scroll vertical para cima/baixo.
 */
@Composable
fun GrelhaVideosComponent(
    videos: List<YouTubeVideoTrack>,
    selectedVideoId: String?,
    isLoading: Boolean,
    accentColor: Color,
    onVideoSelected: (YouTubeVideoTrack) -> Unit,
    onDeleteVideo: ((YouTubeVideoTrack) -> Unit)? = null,
    modifier: Modifier = Modifier,
    gridState: LazyGridState = rememberLazyGridState(),
    errorMessage: String? = null,
    onRetry: (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 4.dp, vertical = 4.dp)
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("grelha_videos_vertical_grid")
    ) {
        if (videos.isEmpty() && !isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "🎬 ${errorMessage ?: "Nenhum vídeo disponível nesta categoria."}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF64748B),
                    textAlign = TextAlign.Center
                )
                if (onRetry != null) {
                    Spacer(modifier = Modifier.height(10.dp))
                    IconButton(
                        onClick = onRetry,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("grelha_btn_retry")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Tentar Novamente",
                            tint = accentColor,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                state = gridState,
                contentPadding = contentPadding,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                if (isLoading && videos.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = accentColor,
                                strokeWidth = 2.5.dp,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }

                itemsIndexed(
                    items = videos,
                    key = { index, track -> "${track.videoId ?: track.id}_$index" }
                ) { index, track ->
                    val isSelected = (selectedVideoId == track.videoId)
                    GrelhaVideoVerticalCardItem(
                        track = track,
                        index = index + 1,
                        isSelected = isSelected,
                        accentColor = accentColor,
                        onClick = { onVideoSelected(track) },
                        onDelete = if (onDeleteVideo != null) { { onDeleteVideo(track) } } else null
                    )
                }
            }
        }
    }
}

/**
 * 2. GrelhaVideosHorizontalCarousel (LAYOUT HORIZONTAL PARA MODO FULLSCREEN LANDSCAPE)
 * Carrossel em linha única (LazyRow) com scroll lateral e cartões compactos.
 */
@Composable
fun GrelhaVideosHorizontalCarousel(
    videos: List<YouTubeVideoTrack>,
    selectedVideoId: String?,
    isLoading: Boolean,
    accentColor: Color,
    onVideoSelected: (YouTubeVideoTrack) -> Unit,
    onDeleteVideo: ((YouTubeVideoTrack) -> Unit)? = null,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    errorMessage: String? = null,
    onRetry: (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 6.dp, vertical = 2.dp)
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .testTag("grelha_videos_horizontal_carousel")
    ) {
        if (videos.isEmpty() && !isLoading) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🎬 ${errorMessage ?: "Nenhum vídeo disponível"}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                )
                if (onRetry != null) {
                    Spacer(modifier = Modifier.width(6.dp))
                    IconButton(
                        onClick = onRetry,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Tentar Novamente",
                            tint = accentColor,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        } else {
            LazyRow(
                state = listState,
                contentPadding = contentPadding,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading && videos.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .width(96.dp)
                                .height(74.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = accentColor,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                itemsIndexed(
                    items = videos,
                    key = { index, track -> "land_${track.videoId ?: track.id}_$index" }
                ) { index, track ->
                    val isSelected = (selectedVideoId == track.videoId)
                    GrelhaVideoHorizontalCardItem(
                        track = track,
                        index = index + 1,
                        isSelected = isSelected,
                        accentColor = accentColor,
                        onClick = { onVideoSelected(track) },
                        onDelete = if (onDeleteVideo != null) { { onDeleteVideo(track) } } else null
                    )
                }
            }
        }
    }
}

/**
 * Cartão Vertical para o Ecrã Normal (Grelha de 3 colunas)
 */
@Composable
fun GrelhaVideoVerticalCardItem(
    track: YouTubeVideoTrack,
    index: Int,
    isSelected: Boolean,
    accentColor: Color,
    onClick: () -> Unit,
    onDelete: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale = if (isPressed) 0.97f else 1.0f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .shadow(
                elevation = if (isSelected) 4.dp else 1.5.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = if (isSelected) accentColor.copy(alpha = 0.4f) else Color.Black.copy(alpha = 0.1f)
            )
            .testTag("grelha_video_card_${track.videoId ?: index}"),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.98f)),
        border = BorderStroke(
            width = if (isSelected) 2.dp else 0.6.dp,
            color = if (isSelected) accentColor else Color(0xFFE2E8F0)
        )
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            // Thumbnail 16:9
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFF0F172A)),
                contentAlignment = Alignment.Center
            ) {
                val thumbnailUrl = track.thumbnailUrl ?: "https://img.youtube.com/vi/${track.videoId}/hqdefault.jpg"
                val imgReq = remember(thumbnailUrl) {
                    ImageRequest.Builder(context)
                        .data(thumbnailUrl)
                        .crossfade(true)
                        .build()
                }
                SafeAsyncImage(
                    model = imgReq,
                    contentDescription = track.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.45f))
                            )
                        )
                )

                // Play Button
                Surface(
                    shape = CircleShape,
                    color = if (isSelected) accentColor else Color.Black.copy(alpha = 0.65f),
                    shadowElevation = 1.5.dp,
                    modifier = Modifier.size(20.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Reproduzir",
                            tint = Color.White,
                            modifier = Modifier.size(13.dp)
                        )
                    }
                }

                if (track.isShort) {
                    Surface(
                        shape = RoundedCornerShape(3.dp),
                        color = Color(0xFFE11D48),
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(2.dp)
                    ) {
                        Text(
                            text = "⚡",
                            fontSize = 6.5.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 2.dp, vertical = 0.5.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "$index. ${track.title}",
                fontSize = 9.sp,
                lineHeight = 11.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) accentColor else Color(0xFF1E293B),
                maxLines = 2,
                minLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(2.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (track.publishedDate.isNotBlank()) track.publishedDate else track.duration,
                    fontSize = 7.5.sp,
                    color = Color(0xFF64748B),
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                if (onDelete != null) {
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(18.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Remover",
                            tint = Color(0xFFEF4444),
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Cartão Compacto Horizontal para Modo Fullscreen Landscape
 */
@Composable
fun GrelhaVideoHorizontalCardItem(
    track: YouTubeVideoTrack,
    index: Int,
    isSelected: Boolean,
    accentColor: Color,
    onClick: () -> Unit,
    onDelete: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale = if (isPressed) 0.96f else 1.0f

    Card(
        modifier = Modifier
            .width(96.dp)
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .shadow(
                elevation = if (isSelected) 3.dp else 1.dp,
                shape = RoundedCornerShape(8.dp),
                ambientColor = if (isSelected) accentColor.copy(alpha = 0.35f) else Color.Black.copy(alpha = 0.1f)
            ),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.96f)),
        border = BorderStroke(
            width = if (isSelected) 1.8.dp else 0.6.dp,
            color = if (isSelected) accentColor else Color(0xFFE2E8F0)
        )
    ) {
        Column(modifier = Modifier.padding(3.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFF0F172A)),
                contentAlignment = Alignment.Center
            ) {
                val thumbnailUrl = track.thumbnailUrl ?: "https://img.youtube.com/vi/${track.videoId}/hqdefault.jpg"
                val imgReq = remember(thumbnailUrl) {
                    ImageRequest.Builder(context)
                        .data(thumbnailUrl)
                        .crossfade(true)
                        .build()
                }
                SafeAsyncImage(
                    model = imgReq,
                    contentDescription = track.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.45f))
                            )
                        )
                )

                Surface(
                    shape = CircleShape,
                    color = if (isSelected) accentColor else Color.Black.copy(alpha = 0.65f),
                    modifier = Modifier.size(15.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(10.dp)
                        )
                    }
                }

                if (track.isShort) {
                    Surface(
                        shape = RoundedCornerShape(2.dp),
                        color = Color(0xFFE11D48),
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(1.5.dp)
                    ) {
                        Text(
                            text = "⚡",
                            fontSize = 5.5.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 1.5.dp, vertical = 0.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(1.5.dp))

            Text(
                text = "$index. ${track.title}",
                fontSize = 7.5.sp,
                lineHeight = 9.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) accentColor else Color(0xFF1E293B),
                maxLines = 2,
                minLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(1.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (track.publishedDate.isNotBlank()) track.publishedDate else track.duration,
                    fontSize = 6.sp,
                    color = Color(0xFF64748B),
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                if (onDelete != null) {
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(14.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Remover",
                            tint = Color(0xFFEF4444),
                            modifier = Modifier.size(9.dp)
                        )
                    }
                } else {
                    Surface(
                        shape = RoundedCornerShape(2.dp),
                        color = accentColor.copy(alpha = 0.15f),
                        modifier = Modifier.padding(start = 1.dp)
                    ) {
                        Text(
                            text = "▶",
                            fontSize = 6.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = accentColor,
                            modifier = Modifier.padding(horizontal = 1.5.dp, vertical = 0.dp)
                        )
                    }
                }
            }
        }
    }
}
