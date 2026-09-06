package com.example.ui.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.api.YouTubeRepository
import com.example.ui.components.AnimatedRainbowBar
import com.example.ui.components.FundoApp
import com.example.ui.components.SafeAsyncImage
import com.example.ui.components.ScreenHeader
import com.example.ui.components.VideoPlayer
import com.example.viewmodel.MainViewModel
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.ExperimentalMaterial3Api
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Modelo de dados para Histórias
 */
data class Historia(
    val titulo: String,
    val sinopse: String,
    val imagemCapa: String,
    val youtubeId: String
)

data class HistoriaSubCategory(
    val key: String,
    val name: String,
    val emoji: String,
    val color: Color
)

val historiaSubCategories = listOf(
    HistoriaSubCategory("all", "Todas", "📚", Color(0xFF7C3AED)),
    HistoriaSubCategory("aventuras", "Aventuras", "🌟", Color(0xFF2563EB)),
    HistoriaSubCategory("natureza", "Natureza", "🌱", Color(0xFF059669)),
    HistoriaSubCategory("aprender", "Aprender", "🧠", Color(0xFFD97706)),
    HistoriaSubCategory("musica", "Músicas", "🎵", Color(0xFFDB2777))
)

/**
 * Lista de reserva de Histórias do Zé Traquina (Fallback offline)
 */
val defaultHistoriasList = listOf(
    Historia(
        titulo = "Zé & Luna: Missão Verde",
        sinopse = "O Zé Traquina e a Luna Brilhante descobrem que o rio da sua aldeia está a ser poluído. Juntos, vão em busca do responsável e aprendem a importância de cuidar do ambiente.",
        imagemCapa = "capa_missao_verde",
        youtubeId = "LBxCHE24CXw"
    )
)

const val HISTORIAS_PLAYLIST_ID = "PLZjPDfJ2Av4c"

/**
 * Carrega dinamicamente a lista de histórias da playlist do YouTube com ID PLZjPDfJ2Av4c
 */
suspend fun fetchYouTubePlaylistHistorias(playlistId: String = HISTORIAS_PLAYLIST_ID): Pair<List<Historia>, String?> = withContext(Dispatchers.IO) {
    val apiKey = YouTubeRepository().effectiveApiKey
    val resultList = mutableListOf<Historia>()
    var errorMsg: String? = null

    // 1. YouTube Data API v3 playlistItems endpoint
    try {
        val urlString = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet,status&maxResults=50&playlistId=$playlistId&key=$apiKey"
        val url = java.net.URL(urlString)
        val connection = url.openConnection() as java.net.HttpURLConnection
        connection.requestMethod = "GET"
        connection.connectTimeout = 4000
        connection.readTimeout = 4000

        if (connection.responseCode == 200) {
            val jsonString = connection.inputStream.bufferedReader().use { it.readText() }
            val root = org.json.JSONObject(jsonString)
            val items = root.optJSONArray("items") ?: org.json.JSONArray()

            val unavailableTitles = setOf(
                "private video",
                "deleted video",
                "this video is unavailable",
                "this video is private",
                "video unavailable",
                "vídeo indisponível",
                "vídeo privado",
                "vídeo eliminado",
                "vídeo removido"
            )

            for (i in 0 until items.length()) {
                val item = items.optJSONObject(i) ?: continue

                // Check privacy status
                val status = item.optJSONObject("status")
                if (status != null) {
                    val privacyStatus = status.optString("privacyStatus", "").trim()
                    if (privacyStatus.isNotEmpty() && !privacyStatus.equals("public", ignoreCase = true)) continue
                    val uploadStatus = status.optString("uploadStatus", "").trim()
                    if (uploadStatus.equals("deleted", ignoreCase = true) ||
                        uploadStatus.equals("failed", ignoreCase = true) ||
                        uploadStatus.equals("rejected", ignoreCase = true)) continue
                }

                val snippet = item.optJSONObject("snippet") ?: continue

                val title = snippet.optString("title", "").trim()
                if (title.isBlank() || title.equals("null", ignoreCase = true) || unavailableTitles.contains(title.lowercase())) continue

                val resourceId = snippet.optJSONObject("resourceId")
                val videoId = resourceId?.optString("videoId", "")?.trim() ?: ""
                if (videoId.isBlank() || videoId.equals("null", ignoreCase = true)) continue

                val thumbnails = snippet.optJSONObject("thumbnails")
                if (thumbnails == null || thumbnails.length() == 0) continue

                val thumbnailUrl = thumbnails.optJSONObject("high")?.optString("url")
                    ?: thumbnails.optJSONObject("medium")?.optString("url")
                    ?: thumbnails.optJSONObject("standard")?.optString("url")
                    ?: thumbnails.optJSONObject("default")?.optString("url")
                    ?: ""
                if (thumbnailUrl.isBlank()) continue

                val description = snippet.optString("description", "").trim()
                val sinopseFinal = if (description.isNotBlank()) description else "Acompanha esta incrível história e aventura do Zé Traquina!"

                resultList.add(
                    Historia(
                        titulo = title,
                        sinopse = sinopseFinal,
                        imagemCapa = thumbnailUrl,
                        youtubeId = videoId
                    )
                )
            }
        } else {
            val errorBody = connection.errorStream?.bufferedReader()?.use { it.readText() } ?: ""
            Log.e("HistoriasScreen", "HTTP ${connection.responseCode}: $errorBody")
            errorMsg = "Erro ao carregar do YouTube (${connection.responseCode})"
        }
    } catch (e: Exception) {
        Log.e("HistoriasScreen", "Exception fetching playlistItems for $playlistId", e)
        errorMsg = "Sem ligação à rede"
    }

    // 2. RSS Feed Fallback if API returned empty/failed
    if (resultList.isEmpty()) {
        try {
            val rssUrlString = "https://www.youtube.com/feeds/videos.xml?playlist_id=$playlistId"
            val rssUrl = java.net.URL(rssUrlString)
            val rssConn = rssUrl.openConnection() as java.net.HttpURLConnection
            rssConn.requestMethod = "GET"
            rssConn.connectTimeout = 4000
            rssConn.readTimeout = 4000

            if (rssConn.responseCode == 200) {
                val xmlString = rssConn.inputStream.bufferedReader().use { it.readText() }
                val entryBlocks = xmlString.split("<entry>")
                for (i in 1 until entryBlocks.size) {
                    val block = entryBlocks[i]
                    val vId = block.substringAfter("<yt:videoId>", "").substringBefore("</yt:videoId>", "").trim()
                    val vTitle = block.substringAfter("<title>", "").substringBefore("</title>", "").trim()
                    val vDesc = block.substringAfter("<media:description>", "").substringBefore("</media:description>", "").trim()
                    val vThumb = block.substringAfter("<media:thumbnail url=\"", "").substringBefore("\"", "").trim()

                    if (vId.isNotEmpty() && vTitle.isNotEmpty()) {
                        val thumbUrl = if (vThumb.isNotEmpty()) vThumb else "https://img.youtube.com/vi/$vId/hqdefault.jpg"
                        val sinopse = if (vDesc.isNotEmpty()) vDesc else "Acompanha esta incrível história e aventura do Zé Traquina!"
                        resultList.add(
                            Historia(
                                titulo = vTitle,
                                sinopse = sinopse,
                                imagemCapa = thumbUrl,
                                youtubeId = vId
                            )
                        )
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("HistoriasScreen", "Error parsing RSS for $playlistId", e)
        }
    }

    // 3. Fallback to default static list if still empty
    if (resultList.isEmpty()) {
        resultList.addAll(defaultHistoriasList)
    }

    Pair(resultList, errorMsg)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoriasScreen(
    viewModel: MainViewModel? = null,
    showHeaderAndBackground: Boolean = true,
    showEmbeddedPlayer: Boolean = true,
    onHistoriaSelected: ((String) -> Unit)? = null,
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var historias by remember { mutableStateOf<List<Historia>>(emptyList()) }
    var selectedHistoria by remember { mutableStateOf<Historia?>(null) }
    var selectedCategoryKey by remember { mutableStateOf("all") }
    var isLoading by remember { mutableStateOf(true) }
    var isRefreshing by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var refreshTrigger by remember { mutableStateOf(0) }
    val gridState = rememberLazyGridState()

    val filteredHistorias = remember(historias, selectedCategoryKey) {
        if (selectedCategoryKey == "all") {
            historias
        } else {
            historias.filter { story ->
                when (selectedCategoryKey) {
                    "aventuras" -> story.titulo.contains("Aventura", ignoreCase = true) || story.sinopse.contains("aventura", ignoreCase = true) || story.sinopse.contains("missao", ignoreCase = true) || story.sinopse.contains("busca", ignoreCase = true)
                    "natureza" -> story.titulo.contains("Verde", ignoreCase = true) || story.sinopse.contains("rio", ignoreCase = true) || story.sinopse.contains("ambiente", ignoreCase = true) || story.sinopse.contains("árvore", ignoreCase = true) || story.sinopse.contains("floresta", ignoreCase = true)
                    "aprender" -> story.titulo.contains("Aprende", ignoreCase = true) || story.sinopse.contains("apren", ignoreCase = true) || story.sinopse.contains("escola", ignoreCase = true) || story.sinopse.contains("importância", ignoreCase = true)
                    "musica" -> story.titulo.contains("Música", ignoreCase = true) || story.titulo.contains("Canção", ignoreCase = true) || story.sinopse.contains("música", ignoreCase = true) || story.sinopse.contains("cantar", ignoreCase = true) || story.sinopse.contains("som", ignoreCase = true)
                    else -> true
                }
            }.ifEmpty { historias }
        }
    }

    // Carregamento dinâmico da playlist do YouTube
    LaunchedEffect(refreshTrigger) {
        isLoading = true
        errorMessage = null
        val (fetchedList, error) = fetchYouTubePlaylistHistorias(HISTORIAS_PLAYLIST_ID)
        historias = fetchedList
        if (fetchedList.isNotEmpty()) {
            selectedHistoria = fetchedList.first()
        }
        errorMessage = error
        isLoading = false
    }

    val onPullToRefresh: () -> Unit = {
        coroutineScope.launch {
            isRefreshing = true
            val (fetchedList, error) = fetchYouTubePlaylistHistorias(HISTORIAS_PLAYLIST_ID)
            if (fetchedList.isNotEmpty()) {
                historias = fetchedList
                val currentSelectedExists = fetchedList.any { it.youtubeId == selectedHistoria?.youtubeId }
                if (!currentSelectedExists) {
                    selectedHistoria = fetchedList.first()
                }
            }
            errorMessage = error
            isRefreshing = false
        }
    }

    val content = @Composable {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .testTag("historias_screen")
        ) {
        // --- 1. CABEÇALHO COM O MESMO ESTILO VISUAL DOS OUTROS MENUS ---
        if (showHeaderAndBackground) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 14.dp, end = 14.dp, top = 8.dp, bottom = 4.dp)
            ) {
                ScreenHeader(
                    title = "Histórias",
                    subtitle = "Contos e Aventuras do Zé Traquina",
                    icon = "📖",
                    gradientColors = listOf(Color(0xFF7C3AED), Color(0xFFDB2777))
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (isLoading) {
                // Indicador de Carregamento
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color(0xFF7C3AED),
                            modifier = Modifier.size(44.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "A carregar histórias do YouTube...",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF64748B)
                        )
                    }
                }
            } else if (historias.isEmpty()) {
                // Mensagem de Erro / Lista Vazia
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "📖", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = errorMessage ?: "Não foi possível carregar a playlist de histórias.",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF64748B),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = { refreshTrigger++ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Tentar Novamente", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            } else {
                val currentHistoria = selectedHistoria ?: historias.first()

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // --- 2. PLAYER DE VÍDEO FIXO NO TOPO ---
                    if (showEmbeddedPlayer) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                                .shadow(8.dp, RoundedCornerShape(18.dp)),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
                            border = BorderStroke(
                                width = 2.dp,
                                brush = Brush.horizontalGradient(
                                    listOf(Color(0xFF7C3AED), Color(0xFFDB2777), Color(0xFF3B82F6))
                                )
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(16f / 9f)
                                    .testTag("historias_player_fixed_container")
                            ) {
                                VideoPlayer(
                                    youtubeId = currentHistoria.youtubeId,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }

                    // --- 3. TÍTULO E SINOPSE DA HISTÓRIA SELECIONADA ---
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 4.dp)
                            .shadow(2.dp, RoundedCornerShape(14.dp)),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color(0xFF7C3AED).copy(alpha = 0.12f)
                                ) {
                                    Text(
                                        text = "EM REPRODUÇÃO 🎬",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFF7C3AED),
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }

                                Text(
                                    text = currentHistoria.titulo,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF1E293B),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = currentHistoria.sinopse,
                                fontSize = 12.sp,
                                lineHeight = 16.sp,
                                color = Color(0xFF475569),
                                fontWeight = FontWeight.Normal,
                                maxLines = 4,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    // --- 4. LISTA VERTICAL ROLÁVEL COM PULL-TO-REFRESH ---
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.White.copy(alpha = 0.95f),
                            border = BorderStroke(1.dp, Color(0xFF7C3AED).copy(alpha = 0.3f)),
                            shadowElevation = 2.dp
                        ) {
                            Text(
                                text = "📚 Histórias (${historias.size})",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF7C3AED),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color.White.copy(alpha = 0.9f),
                            shadowElevation = 1.dp
                        ) {
                            Text(
                                text = "Puxa p/ atualizar 🔄",
                                fontSize = 10.sp,
                                color = Color(0xFF64748B),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    PullToRefreshBox(
                        isRefreshing = isRefreshing,
                        onRefresh = { onPullToRefresh() },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            state = rememberLazyGridState(),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 4.dp),
                            contentPadding = PaddingValues(top = 4.dp, bottom = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            itemsIndexed(
                                items = historias,
                                key = { index, item -> "${item.youtubeId}_$index" }
                            ) { index, historia ->
                                val isSelected = historia.youtubeId == currentHistoria.youtubeId

                                HistoriaCardGridItem(
                                    historia = historia,
                                    index = index + 1,
                                    isSelected = isSelected,
                                    onClick = {
                                        selectedHistoria = historia
                                        onHistoriaSelected?.invoke(historia.youtubeId)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    } // closes content block

    content()
} // closes HistoriasScreen

/**
 * Componente de cartão para cada história na grelha (3 em linha)
 */
@Composable
private fun HistoriaCardGridItem(
    historia: Historia,
    index: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale = if (isPressed) 0.97f else 1.0f

    val imageModel: Any = remember(historia.imagemCapa, historia.youtubeId) {
        if (!historia.imagemCapa.startsWith("http")) {
            val resId = context.resources.getIdentifier(historia.imagemCapa, "drawable", context.packageName)
            if (resId != 0) resId else "https://img.youtube.com/vi/${historia.youtubeId}/hqdefault.jpg"
        } else {
            historia.imagemCapa
        }
    }

    val accentColor = Color(0xFF7C3AED)

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
                ambientColor = if (isSelected) accentColor.copy(alpha = 0.3f) else Color.Black.copy(alpha = 0.1f)
            )
            .testTag("historia_card_${historia.youtubeId}"),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFFAF5FF) else Color.White
        ),
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) accentColor else Color(0xFFE2E8F0)
        )
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            // Miniatura de capa 16:9
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFF0F172A)),
                contentAlignment = Alignment.Center
            ) {
                SafeAsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(imageModel)
                        .crossfade(true)
                        .build(),
                    contentDescription = historia.titulo,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Overlay Gradiente
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.35f)
                                )
                            )
                        )
                )

                // Ícone de Play / Selecionado
                Surface(
                    shape = CircleShape,
                    color = if (isSelected) accentColor else Color.Black.copy(alpha = 0.65f),
                    shadowElevation = 3.dp,
                    modifier = Modifier.size(22.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(3.dp))

            // Título
            Text(
                text = historia.titulo,
                fontSize = 9.sp,
                lineHeight = 11.5.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) accentColor else Color(0xFF1E293B),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(2.dp))

            // Etiqueta da História
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(3.dp),
                    color = if (isSelected) accentColor else accentColor.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = if (isSelected) "▶ A tocar" else "📚 História",
                        fontSize = 7.5.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (isSelected) Color.White else accentColor,
                        modifier = Modifier.padding(horizontal = 3.dp, vertical = 1.dp)
                    )
                }
            }
        }
    }
}
