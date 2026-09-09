package com.example.ui.screens

import android.Manifest
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ScreenRotation
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.layout.heightIn
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.content.res.Configuration
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.ui.layout.ContentScale
import com.example.ui.components.SafeAsyncImage
import com.example.BuildConfig
import com.example.api.YouTubeRepository
import com.example.data.VideoCacheManager
import com.example.ui.components.FundoApp
import com.example.ui.components.GrelhaVideosComponent
import com.example.ui.components.GrelhaVideosHorizontalCarousel
import com.example.ui.components.ScreenHeader
import com.example.ui.components.ShortsGrid
import com.example.ui.components.VideoPlayer
import com.example.ui.components.parseYouTubePlaylistId
import com.example.ui.components.parseYouTubeVideoId
import com.example.util.AudioPermissionHelper
import com.example.util.CustomVideoStorageHelper
import com.example.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.ui.input.pointer.PointerEventPass

const val YOUTUBE_API_KEY = "AIzaSyBP6gYBEy9W2p0FIVObmRKScBkIJRqgTUE"

data class YouTubeVideoTrack(
    val id: String,
    val title: String,
    val videoId: String? = null,
    val thumbnailUrl: String? = null,
    val duration: String = "Vídeo",
    val emoji: String = "🎵",
    val publishedDate: String = "",
    val isShort: Boolean = false,
    val channelTitle: String = "Zé Traquina",
    val remoteThumbnailUrl: String? = null
) {
    val cleanVideoId: String
        get() {
            if (!videoId.isNullOrBlank() && !videoId.contains(":") && videoId.length in 8..15) {
                return videoId
            }
            if (!id.contains(":") && id.length in 8..15) {
                return id
            }
            if (!thumbnailUrl.isNullOrBlank() && !thumbnailUrl.startsWith("http") && !thumbnailUrl.contains(":") && thumbnailUrl.length in 8..15) {
                return thumbnailUrl
            }
            return videoId ?: id
        }

    val cleanThumbnailUrl: String
        get() {
            val remote = remoteThumbnailUrl?.trim()?.takeIf { it.startsWith("http") }
            if (!remote.isNullOrEmpty()) return remote
            val thumb = thumbnailUrl?.trim()?.takeIf { it.startsWith("http") }
            if (!thumb.isNullOrEmpty()) return thumb
            val vid = cleanVideoId
            return if (vid.isNotBlank() && !vid.contains(":")) {
                "https://i.ytimg.com/vi/$vid/hqdefault.jpg"
            } else {
                "https://i.ytimg.com/vi/wOnvZxQ-Iio/hqdefault.jpg"
            }
        }

    val cleanDuration: String
        get() {
            if (duration.isNotBlank() && duration != "Vídeo" && duration != "Short") {
                return duration
            }
            if (!videoId.isNullOrBlank() && videoId.contains(":")) {
                return videoId
            }
            return if (isShort) "Short" else "Vídeo"
        }

    val cleanTitle: String
        get() {
            return title
                .replace("&amp;", "&")
                .replace("&quot;", "\"")
                .replace("&#39;", "'")
                .replace("&apos;", "'")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .trim()
        }
}

data class YouTubeSubCategory(
    val key: String,
    val name: String,
    val emoji: String,
    val accentColor: Color,
    val playlistId: String? = null,
    val playlistList: List<Pair<String, String>> = emptyList(),
    val isCustom: Boolean = false
)

enum class VideoMainSection(val title: String, val emoji: String, val gradient: List<Color>) {
    ZE_TRAQUINA("Zé Traquina", "👦🏻", listOf(Color(0xFFEA580C), Color(0xFFF59E0B))),
    CANTINHO_PT("Cantinho PT", "🇵🇹", listOf(Color(0xFF0284C7), Color(0xFF10B981)))
}
fun getFallbackPlaylistTracks(key: String): List<YouTubeVideoTrack> {
    val list = when (key) {
        "ze_musicas", "PLHz1Xt0IaQWM" -> listOf(
            YouTubeVideoTrack(id = "wOnvZxQ-Iio", title = "Sou de Viana", duration = "3:15", videoId = "wOnvZxQ-Iio", thumbnailUrl = "https://i.ytimg.com/vi/wOnvZxQ-Iio/hqdefault.jpg", channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/wOnvZxQ-Iio/hqdefault.jpg"),
            YouTubeVideoTrack(id = "FRnUep_E3x4", title = "Canção da alegria", duration = "3:02", videoId = "FRnUep_E3x4", thumbnailUrl = "https://i.ytimg.com/vi/FRnUep_E3x4/hqdefault.jpg", channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/FRnUep_E3x4/hqdefault.jpg"),
            YouTubeVideoTrack(id = "FunPMvy6He8", title = "Os melhores avós do mundo", duration = "3:20", videoId = "FunPMvy6He8", thumbnailUrl = "https://i.ytimg.com/vi/FunPMvy6He8/hqdefault.jpg", channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/FunPMvy6He8/hqdefault.jpg"),
            YouTubeVideoTrack(id = "jYYvwC3L2kI", title = "Férias de verão", duration = "2:55", videoId = "jYYvwC3L2kI", thumbnailUrl = "https://i.ytimg.com/vi/jYYvwC3L2kI/hqdefault.jpg", channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/jYYvwC3L2kI/hqdefault.jpg"),
            YouTubeVideoTrack(id = "GXDSVN0nfJo", title = "Marcha dos Santos populares", duration = "3:10", videoId = "GXDSVN0nfJo", thumbnailUrl = "https://i.ytimg.com/vi/GXDSVN0nfJo/hqdefault.jpg", channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/GXDSVN0nfJo/hqdefault.jpg"),
            YouTubeVideoTrack(id = "VbUX6EODgc0", title = "Um coração para ti - Dia da mãe", duration = "3:05", videoId = "VbUX6EODgc0", thumbnailUrl = "https://i.ytimg.com/vi/VbUX6EODgc0/hqdefault.jpg", channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/VbUX6EODgc0/hqdefault.jpg"),
            YouTubeVideoTrack(id = "Ce6QVBTkSUI", title = "Juntos somos o mundo", duration = "3:40", videoId = "Ce6QVBTkSUI", thumbnailUrl = "https://i.ytimg.com/vi/Ce6QVBTkSUI/hqdefault.jpg", channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/Ce6QVBTkSUI/hqdefault.jpg"),
            YouTubeVideoTrack(id = "npjny0rOVok", title = "A luz de Jesus venceu", duration = "3:12", videoId = "npjny0rOVok", thumbnailUrl = "https://i.ytimg.com/vi/npjny0rOVok/hqdefault.jpg", channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/npjny0rOVok/hqdefault.jpg"),
            YouTubeVideoTrack(id = "Cdys2zuYpVs", title = "É NATAL! - Zé Traquina", duration = "3:30", videoId = "Cdys2zuYpVs", thumbnailUrl = "https://i.ytimg.com/vi/Cdys2zuYpVs/hqdefault.jpg", channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/Cdys2zuYpVs/hqdefault.jpg"),
            YouTubeVideoTrack(id = "JT5dhPkaXLI", title = "É Halloween, que divertido!", duration = "2:45", videoId = "JT5dhPkaXLI", thumbnailUrl = "https://i.ytimg.com/vi/JT5dhPkaXLI/hqdefault.jpg", channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/JT5dhPkaXLI/hqdefault.jpg")
        )
        "ze_educativo", "PLT7ZV5QsDKA4" -> listOf(
            YouTubeVideoTrack(id = "pmMVHEF0zQg", title = "Animais do ABC", duration = "4:15", videoId = "pmMVHEF0zQg", thumbnailUrl = "https://i.ytimg.com/vi/pmMVHEF0zQg/hqdefault.jpg", channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/pmMVHEF0zQg/hqdefault.jpg"),
            YouTubeVideoTrack(id = "S5gzoD269E4", title = "Os planetas do sistema solar", duration = "4:50", videoId = "S5gzoD269E4", thumbnailUrl = "https://i.ytimg.com/vi/S5gzoD269E4/hqdefault.jpg", channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/S5gzoD269E4/hqdefault.jpg"),
            YouTubeVideoTrack(id = "wr8fKW8nlug", title = "O corpo humano 🇵🇹", duration = "3:55", videoId = "wr8fKW8nlug", thumbnailUrl = "https://i.ytimg.com/vi/wr8fKW8nlug/hqdefault.jpg", channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/wr8fKW8nlug/hqdefault.jpg"),
            YouTubeVideoTrack(id = "5U-cinIL2uY", title = "Reciclagem ♻️ 🇵🇹", duration = "3:40", videoId = "5U-cinIL2uY", thumbnailUrl = "https://i.ytimg.com/vi/5U-cinIL2uY/hqdefault.jpg", channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/5U-cinIL2uY/hqdefault.jpg"),
            YouTubeVideoTrack(id = "qd0DxrOngz4", title = "Volta ao mundo... PORTUGAL 🇵🇹", duration = "4:20", videoId = "qd0DxrOngz4", thumbnailUrl = "https://i.ytimg.com/vi/qd0DxrOngz4/hqdefault.jpg", channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/qd0DxrOngz4/hqdefault.jpg"),
            YouTubeVideoTrack(id = "_SVda7zfTx8", title = "Restauração da independência", duration = "3:35", videoId = "_SVda7zfTx8", thumbnailUrl = "https://i.ytimg.com/vi/_SVda7zfTx8/hqdefault.jpg", channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/_SVda7zfTx8/hqdefault.jpg"),
            YouTubeVideoTrack(id = "wnr1gdlgJQk", title = "FELIZ NATAL em várias línguas", duration = "3:10", videoId = "wnr1gdlgJQk", thumbnailUrl = "https://i.ytimg.com/vi/wnr1gdlgJQk/hqdefault.jpg", channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/wnr1gdlgJQk/hqdefault.jpg")
        )
        "ze_historias", "PLZjPDfJ2Av4c" -> listOf(
            YouTubeVideoTrack(id = "vK9PtdwQ1Wo", title = "O mistério das estrelas", duration = "5:10", videoId = "vK9PtdwQ1Wo", thumbnailUrl = "https://i.ytimg.com/vi/vK9PtdwQ1Wo/hqdefault.jpg", channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/vK9PtdwQ1Wo/hqdefault.jpg"),
            YouTubeVideoTrack(id = "4F1P2WDNfXk", title = "O nascimento de Jesus", duration = "4:30", videoId = "4F1P2WDNfXk", thumbnailUrl = "https://i.ytimg.com/vi/4F1P2WDNfXk/hqdefault.jpg", channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/4F1P2WDNfXk/hqdefault.jpg"),
            YouTubeVideoTrack(id = "DsaedkDd6KU", title = "Carta ao Pai Natal", duration = "4:05", videoId = "DsaedkDd6KU", thumbnailUrl = "https://i.ytimg.com/vi/DsaedkDd6KU/hqdefault.jpg", channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/DsaedkDd6KU/hqdefault.jpg"),
            YouTubeVideoTrack(id = "m4gFmngzMHE", title = "O pedido especial do Zé Traquina", duration = "4:45", videoId = "m4gFmngzMHE", thumbnailUrl = "https://i.ytimg.com/vi/m4gFmngzMHE/hqdefault.jpg", channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/m4gFmngzMHE/hqdefault.jpg"),
            YouTubeVideoTrack(id = "DhvP0v6uZEI", title = "Dia da família", duration = "3:50", videoId = "DhvP0v6uZEI", thumbnailUrl = "https://i.ytimg.com/vi/DhvP0v6uZEI/hqdefault.jpg", channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/DhvP0v6uZEI/hqdefault.jpg"),
            YouTubeVideoTrack(id = "Xkbtbam05_w", title = "Dia da criança - Magia no ar", duration = "3:40", videoId = "Xkbtbam05_w", thumbnailUrl = "https://i.ytimg.com/vi/Xkbtbam05_w/hqdefault.jpg", channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/Xkbtbam05_w/hqdefault.jpg")
        )
        "ze_diversao", "PLHXyMYX6Yxxc" -> listOf(
            YouTubeVideoTrack(id = "yXdbHR-h8KI", title = "O Sr. Guloso", duration = "3:15", videoId = "yXdbHR-h8KI", thumbnailUrl = "https://i.ytimg.com/vi/yXdbHR-h8KI/hqdefault.jpg", channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/yXdbHR-h8KI/hqdefault.jpg"),
            YouTubeVideoTrack(id = "OeuZJufOB9Y", title = "Missão Verde", duration = "3:30", videoId = "OeuZJufOB9Y", thumbnailUrl = "https://i.ytimg.com/vi/OeuZJufOB9Y/hqdefault.jpg", channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/OeuZJufOB9Y/hqdefault.jpg"),
            YouTubeVideoTrack(id = "hrpeW39DYTM", title = "Festa de Carnaval", duration = "3:05", videoId = "hrpeW39DYTM", thumbnailUrl = "https://i.ytimg.com/vi/hrpeW39DYTM/hqdefault.jpg", channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/hrpeW39DYTM/hqdefault.jpg"),
            YouTubeVideoTrack(id = "fe4HmhQRCUg", title = "Um mundo melhor", duration = "3:25", videoId = "fe4HmhQRCUg", thumbnailUrl = "https://i.ytimg.com/vi/fe4HmhQRCUg/hqdefault.jpg", channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/fe4HmhQRCUg/hqdefault.jpg")
        )
        "ze_shorts", "ze_shorts_auto" -> listOf(
            YouTubeVideoTrack(id = "-YnSfxwxw7s", title = "Canção da alegria", duration = "0:45", videoId = "-YnSfxwxw7s", thumbnailUrl = "https://i.ytimg.com/vi/-YnSfxwxw7s/hqdefault.jpg", isShort = true, channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/-YnSfxwxw7s/hqdefault.jpg"),
            YouTubeVideoTrack(id = "SlT77519BiU", title = "Os melhores avós do mundo", duration = "0:50", videoId = "SlT77519BiU", thumbnailUrl = "https://i.ytimg.com/vi/SlT77519BiU/hqdefault.jpg", isShort = true, channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/SlT77519BiU/hqdefault.jpg"),
            YouTubeVideoTrack(id = "eZ76ofmDfNI", title = "Férias de verão", duration = "0:40", videoId = "eZ76ofmDfNI", thumbnailUrl = "https://i.ytimg.com/vi/eZ76ofmDfNI/hqdefault.jpg", isShort = true, channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/eZ76ofmDfNI/hqdefault.jpg"),
            YouTubeVideoTrack(id = "R792le9BMGU", title = "Zé Traquina apoia a seleção", duration = "0:35", videoId = "R792le9BMGU", thumbnailUrl = "https://i.ytimg.com/vi/R792le9BMGU/hqdefault.jpg", isShort = true, channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/R792le9BMGU/hqdefault.jpg"),
            YouTubeVideoTrack(id = "wnxQMND0L0g", title = "Universo Zé Traquina", duration = "0:45", videoId = "wnxQMND0L0g", thumbnailUrl = "https://i.ytimg.com/vi/wnxQMND0L0g/hqdefault.jpg", isShort = true, channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/wnxQMND0L0g/hqdefault.jpg"),
            YouTubeVideoTrack(id = "AZ1GYo7pgMs", title = "Marcha dos Santos populares", duration = "0:55", videoId = "AZ1GYo7pgMs", thumbnailUrl = "https://i.ytimg.com/vi/AZ1GYo7pgMs/hqdefault.jpg", isShort = true, channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/AZ1GYo7pgMs/hqdefault.jpg"),
            YouTubeVideoTrack(id = "sltydmw3zYU", title = "Dia da criança - Magia no ar", duration = "0:40", videoId = "sltydmw3zYU", thumbnailUrl = "https://i.ytimg.com/vi/sltydmw3zYU/hqdefault.jpg", isShort = true, channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/sltydmw3zYU/hqdefault.jpg"),
            YouTubeVideoTrack(id = "793hLL96h1M", title = "Dia da família", duration = "0:45", videoId = "793hLL96h1M", thumbnailUrl = "https://i.ytimg.com/vi/793hLL96h1M/hqdefault.jpg", isShort = true, channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/793hLL96h1M/hqdefault.jpg"),
            YouTubeVideoTrack(id = "FFEla7pde4M", title = "Animais do ABC", duration = "0:50", videoId = "FFEla7pde4M", thumbnailUrl = "https://i.ytimg.com/vi/FFEla7pde4M/hqdefault.jpg", isShort = true, channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/FFEla7pde4M/hqdefault.jpg"),
            YouTubeVideoTrack(id = "fmx2m_1IbhQ", title = "Um coração para ti", duration = "0:42", videoId = "fmx2m_1IbhQ", thumbnailUrl = "https://i.ytimg.com/vi/fmx2m_1IbhQ/hqdefault.jpg", isShort = true, channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/fmx2m_1IbhQ/hqdefault.jpg"),
            YouTubeVideoTrack(id = "hVL5L3o1rIA", title = "Juntos somos o mundo", duration = "0:48", videoId = "hVL5L3o1rIA", thumbnailUrl = "https://i.ytimg.com/vi/hVL5L3o1rIA/hqdefault.jpg", isShort = true, channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/hVL5L3o1rIA/hqdefault.jpg"),
            YouTubeVideoTrack(id = "IGGV40rABS4", title = "A luz de Jesus venceu", duration = "0:50", videoId = "IGGV40rABS4", thumbnailUrl = "https://i.ytimg.com/vi/IGGV40rABS4/hqdefault.jpg", isShort = true, channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/IGGV40rABS4/hqdefault.jpg"),
            YouTubeVideoTrack(id = "QipCENBx8ik", title = "Sistema Solar 🌍✨", duration = "0:58", videoId = "QipCENBx8ik", thumbnailUrl = "https://i.ytimg.com/vi/QipCENBx8ik/hqdefault.jpg", isShort = true, channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/QipCENBx8ik/hqdefault.jpg"),
            YouTubeVideoTrack(id = "ARZSwAM8BnA", title = "Zé Traquina e D.A.M.A.", duration = "0:52", videoId = "ARZSwAM8BnA", thumbnailUrl = "https://i.ytimg.com/vi/ARZSwAM8BnA/hqdefault.jpg", isShort = true, channelTitle = "Zé Traquina", remoteThumbnailUrl = "https://i.ytimg.com/vi/ARZSwAM8BnA/hqdefault.jpg")
        )
        "pt_musicas", "PLSsrAc3exDx0" -> listOf(
            YouTubeVideoTrack(id = "ybN2N_hAf1M", title = "Cantinho PT - Músicas 1", duration = "2:45", videoId = "ybN2N_hAf1M", thumbnailUrl = "https://i.ytimg.com/vi/ybN2N_hAf1M/hqdefault.jpg", channelTitle = "Cantinho PT", remoteThumbnailUrl = "https://i.ytimg.com/vi/ybN2N_hAf1M/hqdefault.jpg"),
            YouTubeVideoTrack(id = "iTvK5uGSyZk", title = "Cantinho PT - Músicas 2", duration = "3:10", videoId = "iTvK5uGSyZk", thumbnailUrl = "https://i.ytimg.com/vi/iTvK5uGSyZk/hqdefault.jpg", channelTitle = "Cantinho PT", remoteThumbnailUrl = "https://i.ytimg.com/vi/iTvK5uGSyZk/hqdefault.jpg"),
            YouTubeVideoTrack(id = "pZ5NxMN88Jg", title = "Cantinho PT - Músicas 3", duration = "2:35", videoId = "pZ5NxMN88Jg", thumbnailUrl = "https://i.ytimg.com/vi/pZ5NxMN88Jg/hqdefault.jpg", channelTitle = "Cantinho PT", remoteThumbnailUrl = "https://i.ytimg.com/vi/pZ5NxMN88Jg/hqdefault.jpg")
        )
        "pt_aprender", "PLAHA0KA1fztw" -> listOf(
            YouTubeVideoTrack(id = "IoCjrOM_36s", title = "Cantinho PT - Aprender 1", duration = "3:15", videoId = "IoCjrOM_36s", thumbnailUrl = "https://i.ytimg.com/vi/IoCjrOM_36s/hqdefault.jpg", channelTitle = "Cantinho PT", remoteThumbnailUrl = "https://i.ytimg.com/vi/IoCjrOM_36s/hqdefault.jpg"),
            YouTubeVideoTrack(id = "pw-TbcdIi7E", title = "Cantinho PT - Aprender 2", duration = "2:50", videoId = "pw-TbcdIi7E", thumbnailUrl = "https://i.ytimg.com/vi/pw-TbcdIi7E/hqdefault.jpg", channelTitle = "Cantinho PT", remoteThumbnailUrl = "https://i.ytimg.com/vi/pw-TbcdIi7E/hqdefault.jpg")
        )
        "pt_brincar", "PLWywOKJSEg6E" -> listOf(
            YouTubeVideoTrack(id = "4HTBp8IEfUk", title = "Cantinho PT - Brincar 1", duration = "3:05", videoId = "4HTBp8IEfUk", thumbnailUrl = "https://i.ytimg.com/vi/4HTBp8IEfUk/hqdefault.jpg", channelTitle = "Cantinho PT", remoteThumbnailUrl = "https://i.ytimg.com/vi/4HTBp8IEfUk/hqdefault.jpg"),
            YouTubeVideoTrack(id = "56N-ycOW2Mk", title = "Cantinho PT - Brincar 2", duration = "3:30", videoId = "56N-ycOW2Mk", thumbnailUrl = "https://i.ytimg.com/vi/56N-ycOW2Mk/hqdefault.jpg", channelTitle = "Cantinho PT", remoteThumbnailUrl = "https://i.ytimg.com/vi/56N-ycOW2Mk/hqdefault.jpg")
        )
        "pt_historias", "PLTHYqcQWhhUI" -> listOf(
            YouTubeVideoTrack(id = "WvW6G9-K8kU", title = "Cantinho PT - Histórias 1", duration = "7:45", videoId = "WvW6G9-K8kU", thumbnailUrl = "https://i.ytimg.com/vi/WvW6G9-K8kU/hqdefault.jpg", channelTitle = "Cantinho PT", remoteThumbnailUrl = "https://i.ytimg.com/vi/WvW6G9-K8kU/hqdefault.jpg"),
            YouTubeVideoTrack(id = "SVBnWiU1ix8", title = "Cantinho PT - Histórias 2", duration = "5:50", videoId = "SVBnWiU1ix8", thumbnailUrl = "https://i.ytimg.com/vi/SVBnWiU1ix8/hqdefault.jpg", channelTitle = "Cantinho PT", remoteThumbnailUrl = "https://i.ytimg.com/vi/SVBnWiU1ix8/hqdefault.jpg")
        )
        "pt_amigos", "PLAXFI56mD-HQ" -> listOf(
            YouTubeVideoTrack(id = "4ZXQ2MyDG-g", title = "Cantinho PT - Amigos 1", duration = "5:20", videoId = "4ZXQ2MyDG-g", thumbnailUrl = "https://i.ytimg.com/vi/4ZXQ2MyDG-g/hqdefault.jpg", channelTitle = "Cantinho PT", remoteThumbnailUrl = "https://i.ytimg.com/vi/4ZXQ2MyDG-g/hqdefault.jpg"),
            YouTubeVideoTrack(id = "RbEVVSOE-6Y", title = "Cantinho PT - Amigos 2", duration = "6:15", videoId = "RbEVVSOE-6Y", thumbnailUrl = "https://i.ytimg.com/vi/RbEVVSOE-6Y/hqdefault.jpg", channelTitle = "Cantinho PT", remoteThumbnailUrl = "https://i.ytimg.com/vi/RbEVVSOE-6Y/hqdefault.jpg")
        )
        else -> emptyList()
    }
    return list.shuffled()
}

private var hasShownRotationHintInSession = false

suspend fun fetchYouTubePlaylistItems(
    playlistId: String,
    apiKey: String,
    categoryName: String = "Vídeo"
): List<YouTubeVideoTrack> = withContext(Dispatchers.IO) {
    val resultList = mutableListOf<YouTubeVideoTrack>()
    try {
        val repo = YouTubeRepository(apiKey)
        val fetchedVideos = repo.fetchPlaylistVideos(playlistId, maxResults = 30)
        if (fetchedVideos.isNotEmpty()) {
            resultList.addAll(fetchedVideos)
        }
    } catch (e: Exception) {
        Log.e("VideosScreen", "Error fetching YouTube playlist: $playlistId", e)
    }
    resultList.shuffled()
}

@Composable
fun VideosScreen(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = context as? android.app.Activity
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val scope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        activity?.requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_SENSOR
        
        onDispose {
            activity?.requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    // Dialog for adding YouTube URLs
        var showAddVideoDialog by remember { mutableStateOf(false) }
        var inputVideoUrl by remember { mutableStateOf("") }
        var inputVideoTitle by remember { mutableStateOf("") }

        var selectedSection by remember { mutableStateOf(VideoMainSection.ZE_TRAQUINA) }

        val zeTraquinaSubCategories = remember {
            listOf(
                YouTubeSubCategory("ze_musicas", "Músicas", "🎵", Color(0xFFE91E63), playlistId = "PLHz1Xt0IaQWM"),
                YouTubeSubCategory("ze_educativo", "Educativo", "📖", Color(0xFF10B981), playlistId = "PLT7ZV5QsDKA4"),
                YouTubeSubCategory("ze_historias", "Histórias", "📚", Color(0xFF7C3AED), playlistId = "PLZjPDfJ2Av4c"),
                YouTubeSubCategory("ze_diversao", "Diversão", "🪁", Color(0xFF06B6D4), playlistId = "PLHXyMYX6Yxxc"),
                YouTubeSubCategory("ze_shorts", "Shorts ⚡", "⚡", Color(0xFFEC4899), playlistId = "ze_shorts_auto")
            )
        }

        val cantinhoPtSubCategories = remember {
            listOf(
                YouTubeSubCategory(
                    key = "pt_musicas",
                    name = "Músicas",
                    emoji = "🎵",
                    accentColor = Color(0xFFE91E63),
                    playlistId = "PLSsrAc3exDx0"
                ),
                YouTubeSubCategory(
                    key = "pt_aprender",
                    name = "Aprender",
                    emoji = "🧠",
                    accentColor = Color(0xFFEA580C),
                    playlistId = "PLAHA0KA1fztw"
                ),
                YouTubeSubCategory(
                    key = "pt_brincar",
                    name = "Brincar",
                    emoji = "🪁",
                    accentColor = Color(0xFF06B6D4),
                    playlistId = "PLWywOKJSEg6E"
                ),
                YouTubeSubCategory(
                    key = "pt_historias",
                    name = "Histórias",
                    emoji = "📚",
                    accentColor = Color(0xFF7C3AED),
                    playlistId = "PLTHYqcQWhhUI"
                ),
                YouTubeSubCategory(
                    key = "pt_amigos",
                    name = "Amigos",
                    emoji = "👫",
                    accentColor = Color(0xFFFF7043),
                    playlistId = "PLAXFI56mD-HQ"
                )
            )
        }

        var selectedZeSub by remember { mutableStateOf(zeTraquinaSubCategories[0]) }
        var selectedPtSub by remember { mutableStateOf(cantinhoPtSubCategories[0]) }

        val activeSubCategory = if (selectedSection == VideoMainSection.ZE_TRAQUINA) selectedZeSub else selectedPtSub
        val activeKey = activeSubCategory.key

        var selectedVideoId by remember { mutableStateOf<String?>(null) }
        var refreshTrigger by remember { mutableIntStateOf(0) }

        var categoryTracksMap by remember {
            mutableStateOf<Map<String, List<YouTubeVideoTrack>>>(
                mapOf(
                    "ze_musicas" to getFallbackPlaylistTracks("ze_musicas"),
                    "ze_educativo" to getFallbackPlaylistTracks("ze_educativo"),
                    "ze_historias" to getFallbackPlaylistTracks("ze_historias"),
                    "ze_diversao" to getFallbackPlaylistTracks("ze_diversao"),
                    "ze_shorts" to getFallbackPlaylistTracks("ze_shorts"),
                    "pt_musicas" to getFallbackPlaylistTracks("pt_musicas"),
                    "pt_aprender" to getFallbackPlaylistTracks("pt_aprender"),
                    "pt_brincar" to getFallbackPlaylistTracks("pt_brincar"),
                    "pt_historias" to getFallbackPlaylistTracks("pt_historias"),
                    "pt_amigos" to getFallbackPlaylistTracks("pt_amigos")
                )
            )
        }
        var isLoadingTracks by remember { mutableStateOf(false) }
        var apiErrorMessage by remember { mutableStateOf<String?>(null) }

        LaunchedEffect(selectedSection, activeSubCategory, refreshTrigger) {
            if (activeSubCategory.isCustom || activeKey == "ze_meus_videos") {
                val customVideos = CustomVideoStorageHelper.loadCustomVideos(context)
                categoryTracksMap = categoryTracksMap + (activeKey to customVideos)
                isLoadingTracks = false
                return@LaunchedEffect
            }

            val existing = categoryTracksMap[activeKey] ?: emptyList()
            val roomList = VideoCacheManager.loadFromRoomDatabase(context, activeKey)
            val hasRealCache = roomList.isNotEmpty()
            if (hasRealCache) {
                categoryTracksMap = categoryTracksMap + (activeKey to roomList)
            } else if (existing.isEmpty()) {
                val fallbacks = getFallbackPlaylistTracks(activeKey)
                if (fallbacks.isNotEmpty()) {
                    categoryTracksMap = categoryTracksMap + (activeKey to fallbacks)
                }
            }

            val isExpired = VideoCacheManager.isCacheExpired(context, activeKey)
            val isForcedRefresh = refreshTrigger > 0

            if (hasRealCache && !isExpired && !isForcedRefresh) {
                isLoadingTracks = false
                return@LaunchedEffect
            }

            if (!hasRealCache && (categoryTracksMap[activeKey]?.isEmpty() == true)) {
                isLoadingTracks = true
            }

            try {
                var errorReport: String? = null
                val freshTracks = withContext(Dispatchers.IO) {
                    val apiKey = try {
                        val key = BuildConfig.YOUTUBE_API_KEY
                        if (key.isNotBlank() && key != "DEFAULT_KEY") key else YOUTUBE_API_KEY
                    } catch (e: Throwable) { YOUTUBE_API_KEY }

                    val repo = YouTubeRepository(apiKey)
                    when {
                        // 1. Zé Traquina Shorts dedicated channel fetch
                        activeKey == "ze_shorts" -> {
                            val (uploadsId, _) = repo.resolveUploadsPlaylistAndChannelId("zetraquina")
                            var videos = if (!uploadsId.isNullOrBlank()) {
                                repo.fetchPlaylistVideos(uploadsId, maxResults = 50)
                            } else emptyList()
                            if (videos.isEmpty()) {
                                videos = repo.searchVideos("Zé Traquina shorts", maxResults = 30)
                            }
                            videos.filter { it.isShort || it.title.contains("short", ignoreCase = true) }.ifEmpty { videos }
                        }
                        // 2. Specific YouTube Playlist ID (Músicas, Educativo, Histórias, Diversão)
                        activeSubCategory.playlistId != null && activeSubCategory.playlistId.isNotBlank() -> {
                            fetchYouTubePlaylistItems(activeSubCategory.playlistId, apiKey, activeSubCategory.name)
                        }
                        // 3. Multi-playlist subcategory
                        activeSubCategory.playlistList.isNotEmpty() -> {
                            coroutineScope {
                                val playlistDeferreds = activeSubCategory.playlistList.map { (pid, name) ->
                                    async(Dispatchers.IO) {
                                        try {
                                            fetchYouTubePlaylistItems(pid, apiKey, name)
                                        } catch (e: Exception) {
                                            emptyList()
                                        }
                                    }
                                }
                                val combined = mutableListOf<YouTubeVideoTrack>()
                                playlistDeferreds.awaitAll().forEach { list ->
                                    for (item in list) {
                                        if (!item.videoId.isNullOrBlank() && combined.none { it.videoId == item.videoId }) {
                                            combined.add(item)
                                        }
                                    }
                                }
                                combined.distinctBy { it.videoId }
                            }
                        }
                        else -> emptyList()
                    }
                }
                apiErrorMessage = errorReport
                if (freshTracks.isNotEmpty()) {
                    categoryTracksMap = categoryTracksMap + (activeKey to freshTracks)
                    VideoCacheManager.syncFreshTracks(context, activeKey, freshTracks)
                } else if (hasRealCache) {
                    // Keep real cached tracks
                } else {
                    val fallbacks = getFallbackPlaylistTracks(activeKey)
                    categoryTracksMap = categoryTracksMap + (activeKey to fallbacks)
                    if (fallbacks.isNotEmpty()) {
                        VideoCacheManager.syncFreshTracks(context, activeKey, fallbacks)
                    }
                }
            } catch (e: Exception) {
                Log.e("VideosScreen", "Error loading tracks for $activeKey", e)
                val fallbacks = getFallbackPlaylistTracks(activeKey)
                if (fallbacks.isNotEmpty() && categoryTracksMap[activeKey].isNullOrEmpty()) {
                    categoryTracksMap = categoryTracksMap + (activeKey to fallbacks)
                }
            } finally {
                isLoadingTracks = false
            }
        }

        val activeTracks = categoryTracksMap[activeKey] ?: emptyList()
        val activeVideoId = selectedVideoId ?: activeTracks.firstOrNull()?.cleanVideoId

        // --- DIALOG: ADICIONAR VÍDEO YOUTUBE ---
        if (showAddVideoDialog) {
            val detectedId = remember(inputVideoUrl) { parseYouTubeVideoId(inputVideoUrl) }
            val detectedPlaylistId = remember(inputVideoUrl) { parseYouTubePlaylistId(inputVideoUrl) }
            val isValid = detectedId != null || detectedPlaylistId != null

            AlertDialog(
                onDismissRequest = { showAddVideoDialog = false },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🔗 Adicionar Vídeo YouTube", fontWeight = FontWeight.Bold, fontSize = 17.sp)
                    }
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "Cola qualquer link do YouTube (Vídeo, Short, Playlist ou ID de 11 caracteres) para tocar na app:",
                            fontSize = 12.sp,
                            color = Color(0xFF475569)
                        )

                        OutlinedTextField(
                            value = inputVideoUrl,
                            onValueChange = { inputVideoUrl = it },
                            label = { Text("URL ou ID do YouTube") },
                            placeholder = { Text("Ex: https://youtu.be/wOnvZxQ-Iio") },
                            singleLine = true,
                            trailingIcon = {
                                if (inputVideoUrl.isNotEmpty()) {
                                    IconButton(onClick = { inputVideoUrl = "" }) {
                                        Icon(Icons.Default.Clear, contentDescription = "Limpar")
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = inputVideoTitle,
                            onValueChange = { inputVideoTitle = it },
                            label = { Text("Título do Vídeo (Opcional)") },
                            placeholder = { Text("Ex: Minha Música Favorita") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Status da Deteção
                        if (inputVideoUrl.isNotBlank()) {
                            if (detectedId != null) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFFDCFCE7),
                                    border = BorderStroke(1.dp, Color(0xFF22C55E))
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF16A34A), modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Vídeo reconhecido: ID $detectedId", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF15803D))
                                    }
                                }
                            } else if (detectedPlaylistId != null) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFFDCFCE7),
                                    border = BorderStroke(1.dp, Color(0xFF22C55E))
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF16A34A), modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Playlist reconhecida: ID $detectedPlaylistId", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF15803D))
                                    }
                                }
                            } else {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFFFEF3C7),
                                    border = BorderStroke(1.dp, Color(0xFFF59E0B))
                                ) {
                                    Text(
                                        text = "⚠️ Introduz um link válido do YouTube",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFFB45309),
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                    )
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            val vid = detectedId ?: detectedPlaylistId
                            if (vid != null) {
                                val isShort = inputVideoUrl.contains("/shorts/")
                                val savedTrack = CustomVideoStorageHelper.saveCustomVideo(
                                    context = context,
                                    videoId = vid,
                                    title = inputVideoTitle.ifBlank { "Vídeo YouTube ($vid)" },
                                    isShort = isShort
                                )

                                val updatedCustomList = CustomVideoStorageHelper.loadCustomVideos(context)
                                categoryTracksMap = categoryTracksMap + ("ze_meus_videos" to updatedCustomList)

                                // Select custom tab and play newly added video
                                selectedSection = VideoMainSection.ZE_TRAQUINA
                                selectedZeSub = zeTraquinaSubCategories.first { it.key == "ze_meus_videos" }
                                selectedVideoId = savedTrack.videoId

                                Toast.makeText(context, "Vídeo adicionado e a reproduzir! 🎬", Toast.LENGTH_SHORT).show()
                                showAddVideoDialog = false
                                inputVideoUrl = ""
                                inputVideoTitle = ""
                            }
                        },
                        enabled = isValid,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEA580C))
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Guardar & Reproduzir")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAddVideoDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        var showRotationHint by remember { mutableStateOf(!hasShownRotationHintInSession && !isLandscape) }

        LaunchedEffect(isLandscape) {
            if (isLandscape) {
                hasShownRotationHintInSession = true
                showRotationHint = false
            }
        }

        LaunchedEffect(showRotationHint) {
            if (showRotationHint) {
                delay(4500)
                showRotationHint = false
                hasShownRotationHintInSession = true
            }
        }

        if (isLandscape) {
            LandscapeImmersiveVideoPlayer(
                mainViewModel = mainViewModel,
                selectedSection = selectedSection,
                onSectionSelected = { newSection ->
                    if (selectedSection != newSection) {
                        selectedSection = newSection
                        selectedVideoId = null
                    }
                },
                zeTraquinaSubCategories = zeTraquinaSubCategories,
                cantinhoPtSubCategories = cantinhoPtSubCategories,
                categoryTracksMap = categoryTracksMap,
                activeVideoId = activeVideoId,
                onVideoSelected = { track ->
                    selectedVideoId = track.cleanVideoId
                }
            )
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                ) {
                // --- Header ---
                ScreenHeader(
                    title = if (selectedSection == VideoMainSection.ZE_TRAQUINA) "Vídeos e Músicas" else "Videos e músicas",
                    subtitle = if (selectedSection == VideoMainSection.ZE_TRAQUINA) "Canal Oficial @zetraquina" else "YouTube kids  🇵🇹",
                    icon = "🎬",
                    gradientColors = selectedSection.gradient,
                    modifier = Modifier.fillMaxWidth()
                )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                VideoMainSection.values().forEach { section ->
                    val isSelected = selectedSection == section
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(32.dp)
                            .clickable {
                                if (selectedSection != section) {
                                    selectedSection = section
                                    selectedVideoId = null
                                }
                            },
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) Color.Transparent else Color.White.copy(alpha = 0.85f)
                        ),
                        border = BorderStroke(
                            width = if (isSelected) 1.5.dp else 0.5.dp,
                            color = if (isSelected) section.gradient.first() else Color.LightGray.copy(alpha = 0.4f)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 3.dp else 1.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .then(
                                    if (isSelected) Modifier.background(
                                        Brush.horizontalGradient(section.gradient)
                                    ) else Modifier
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = section.emoji,
                                    fontSize = 13.sp,
                                    modifier = Modifier.padding(end = 4.dp)
                                )
                                Text(
                                    text = section.title,
                                    fontSize = 10.5.sp,
                                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold,
                                    color = if (isSelected) Color.White else Color(0xFF334155)
                                )
                            }
                        }
                    }
                }
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
                    .aspectRatio(16f / 10f)
            ) {
                VideoPlayer(
                    youtubeId = activeVideoId,
                    playlistId = null,
                    modifier = Modifier.fillMaxSize(),
                    onVideoEnded = {
                        // User requested to not start another video automatically when one ends.
                    }
                )

                // Visual Rotation Hint for children
                RotateDeviceHintOverlay(
                    visible = showRotationHint,
                    onDismiss = {
                        showRotationHint = false
                        hasShownRotationHintInSession = true
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 8.dp, bottom = 8.dp)
                )
            }

            // --- SUB-MENUS UNDER PLAYER ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val currentSubCategories = if (selectedSection == VideoMainSection.ZE_TRAQUINA) {
                    zeTraquinaSubCategories
                } else {
                    cantinhoPtSubCategories
                }

                LazyRow(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(currentSubCategories.size) { index ->
                        val subCategory = currentSubCategories[index]
                        val isSelected = activeSubCategory.key == subCategory.key

                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                if (selectedSection == VideoMainSection.ZE_TRAQUINA) {
                                    selectedZeSub = subCategory
                                } else {
                                    selectedPtSub = subCategory
                                }
                                selectedVideoId = null
                            },
                            label = {
                                Text(
                                    text = subCategory.emoji,
                                    fontWeight = if (isSelected) FontWeight.Black else FontWeight.Bold,
                                    fontSize = 13.sp,
                                    letterSpacing = (-0.2).sp,
                                    maxLines = 1
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = subCategory.accentColor,
                                selectedLabelColor = Color.White,
                                containerColor = Color.White.copy(alpha = 0.85f),
                                labelColor = Color(0xFF475569)
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = Color.Transparent,
                                selectedBorderColor = subCategory.accentColor,
                                borderWidth = 0.dp,
                                selectedBorderWidth = 0.dp
                            ),
                            shape = RoundedCornerShape(6.dp)
                        )
                    }
                }

                IconButton(
                    onClick = { refreshTrigger++ },
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Atualizar Vídeos",
                        tint = Color(0xFF475569),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // --- VIDEO CONTENT GRID ---
            if (activeKey == "ze_shorts") {
                ShortsGrid(
                    shorts = activeTracks,
                    selectedVideoId = activeVideoId,
                    isLoading = isLoadingTracks,
                    onShortSelected = { track ->
                        selectedVideoId = track.cleanVideoId
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            } else {
                GrelhaVideosComponent(
                    videos = activeTracks,
                    selectedVideoId = activeVideoId,
                    isLoading = isLoadingTracks,
                    accentColor = activeSubCategory.accentColor,
                    onVideoSelected = { track ->
                        selectedVideoId = track.cleanVideoId
                    },
                    onDeleteVideo = if (activeSubCategory.isCustom || activeKey == "ze_meus_videos") {
                        { track ->
                            val vid = track.cleanVideoId
                            CustomVideoStorageHelper.deleteCustomVideo(context, vid)
                            val updated = CustomVideoStorageHelper.loadCustomVideos(context)
                            categoryTracksMap = categoryTracksMap + (activeKey to updated)
                            if (selectedVideoId == vid) {
                                selectedVideoId = updated.firstOrNull()?.cleanVideoId
                            }
                        }
                    } else null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    errorMessage = apiErrorMessage,
                    onRetry = { refreshTrigger++ }
                )
            }
        }

        // Floating Action Button removed by user request
        /*
        if (activeKey != "ze_historias") {
            androidx.compose.material3.FloatingActionButton(
            onClick = { showAddVideoDialog = true },
            containerColor = Color(0xFFEA580C),
            contentColor = Color.White,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .size(56.dp)
                .testTag("add_video_button")
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Adicionar Vídeo",
                modifier = Modifier.size(28.dp)
            )
        }
    }
    */
}
}
}

@Composable
private fun LandscapeImmersiveVideoPlayer(
    mainViewModel: MainViewModel,
    selectedSection: VideoMainSection,
    onSectionSelected: (VideoMainSection) -> Unit,
    zeTraquinaSubCategories: List<YouTubeSubCategory>,
    cantinhoPtSubCategories: List<YouTubeSubCategory>,
    categoryTracksMap: Map<String, List<YouTubeVideoTrack>>,
    activeVideoId: String?,
    onVideoSelected: (YouTubeVideoTrack) -> Unit
) {
    val context = LocalContext.current
    var mostrarMenu by remember { mutableStateOf(false) }
    var resetTimerTrigger by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    val isListeningVoice by mainViewModel.isListening.collectAsStateWithLifecycle()

    val micPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            mainViewModel.startListening(onResult = { spoken ->
                searchQuery = spoken
                resetTimerTrigger++
            })
        }
    }

    fun startVoiceSearch() {
        AudioPermissionHelper.checkAndRequestAudioPermission(
            context = context,
            onPermissionGranted = {
                mainViewModel.startListening(onResult = { spoken ->
                    searchQuery = spoken
                    resetTimerTrigger++
                })
            },
            onShowRationale = {
                micPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }
        )
    }

    // Todos os vídeos de cada menu (juntando todas as sub-playlists), sem filtro de sub-categoria
    val zeCombined = remember(categoryTracksMap, zeTraquinaSubCategories) {
        zeTraquinaSubCategories.flatMap { categoryTracksMap[it.key] ?: emptyList() }
    }
    val ptCombined = remember(categoryTracksMap, cantinhoPtSubCategories) {
        cantinhoPtSubCategories.flatMap { categoryTracksMap[it.key] ?: emptyList() }
    }

    // Baralha cada menu uma vez quando a lista fica disponível (não a cada recomposição)
    var zeShuffled by remember { mutableStateOf<List<YouTubeVideoTrack>>(emptyList()) }
    var ptShuffled by remember { mutableStateOf<List<YouTubeVideoTrack>>(emptyList()) }
    LaunchedEffect(zeCombined.size) {
        if (zeCombined.isNotEmpty()) zeShuffled = zeCombined
    }
    LaunchedEffect(ptCombined.size) {
        if (ptCombined.isNotEmpty()) ptShuffled = ptCombined
    }

    // Control visibility of system bars in landscape
    val window = (context as? android.app.Activity)?.window
    val view = androidx.compose.ui.platform.LocalView.current
    
    DisposableEffect(Unit) {
        val windowInsetsController = window?.let {
            androidx.core.view.WindowCompat.getInsetsController(it, view)
        }
        windowInsetsController?.systemBarsBehavior = androidx.core.view.WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController?.hide(androidx.core.view.WindowInsetsCompat.Type.systemBars())
        
        onDispose {
            windowInsetsController?.show(androidx.core.view.WindowInsetsCompat.Type.systemBars())
        }
    }

    var isSearchFocused by remember { mutableStateOf(false) }
    
    // 5-second inactivity auto-hide
    LaunchedEffect(mostrarMenu, resetTimerTrigger, isSearchFocused) {
        if (mostrarMenu && !isSearchFocused) {
            delay(5000)
            mostrarMenu = false
        }
    }

    // Se há pesquisa: mostra resultados dos DOIS menus. Senão: menu ativo, baralhado.
    val currentTracks = if (searchQuery.isBlank()) {
        if (selectedSection == VideoMainSection.ZE_TRAQUINA) zeShuffled else ptShuffled
    } else {
        (zeCombined + ptCombined).filter { it.title.contains(searchQuery, ignoreCase = true) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F172A))
    ) {
        if (mostrarMenu) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    // Reset the inactivity timer when touching or interacting with the top menus
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                awaitPointerEvent(androidx.compose.ui.input.pointer.PointerEventPass.Initial)
                                resetTimerTrigger++
                            }
                        }
                    },
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Top: Section Toggles (Tabs Zé Traquina and Cantinho PT) + caixa de pesquisa
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    VideoMainSection.values().forEach { section ->
                        val isSelected = selectedSection == section
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) section.gradient.first() else Color.Black.copy(alpha = 0.65f),
                            border = BorderStroke(
                                width = if (isSelected) 1.5.dp else 0.8.dp,
                                color = if (isSelected) Color.White else Color.White.copy(alpha = 0.3f)
                            ),
                            modifier = Modifier
                                .weight(0.85f)
                                .height(32.dp)
                                .clickable { 
                                    onSectionSelected(section)
                                    resetTimerTrigger++
                                }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${section.emoji} ${section.title}",
                                    textAlign = TextAlign.Center,
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = {
                            searchQuery = it
                            resetTimerTrigger++
                        },
                        modifier = Modifier
                            .weight(1.1f)
                            .heightIn(min = 48.dp)
                            .onFocusChanged { focusState ->
                                isSearchFocused = focusState.isFocused
                                if (focusState.isFocused) resetTimerTrigger++
                            },
                        placeholder = { Text("Pesquisar...", fontSize = 11.sp) },
                        singleLine = true,
                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontSize = 12.sp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.4f),
                            cursorColor = Color.White,
                            focusedContainerColor = Color.Black.copy(alpha = 0.4f),
                            unfocusedContainerColor = Color.Black.copy(alpha = 0.4f)
                        ),
                        leadingIcon = {
                            IconButton(
                                onClick = {
                                    resetTimerTrigger++
                                    startVoiceSearch()
                                },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = if (isListeningVoice) Icons.Default.MicOff else Icons.Default.Mic,
                                    contentDescription = "Pesquisar por voz",
                                    tint = if (isListeningVoice) Color(0xFFEF4444) else Color.White
                                )
                            }
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }, modifier = Modifier.size(20.dp)) {
                                    Icon(Icons.Default.Clear, contentDescription = "Limpar", tint = Color.White)
                                }
                            }
                        }
                    )
                }
            }
        }

        // Player (adjusts size and style based on state)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(if (mostrarMenu) 1.2f else 1f)
                .padding(
                    horizontal = if (mostrarMenu) 48.dp else 0.dp,
                    vertical = if (mostrarMenu) 6.dp else 0.dp
                )
        ) {
            Surface(
                shape = if (mostrarMenu) RoundedCornerShape(12.dp) else RoundedCornerShape(0.dp),
                border = if (mostrarMenu) BorderStroke(2.dp, Color.White.copy(alpha = 0.3f)) else null,
                color = Color.Black,
                modifier = Modifier.fillMaxSize()
            ) {
                VideoPlayer(
                    youtubeId = activeVideoId,
                    playlistId = null,
                    modifier = Modifier.fillMaxSize(),
                    onVideoEnded = {
                        // User requested to not start another video automatically when one ends.
                    }
                )
            }

            // Clickable overlay on the player:
            // 1. If in fullscreen, clicking minimizes the player and displays menus.
            // 2. If already minimized, clicking the player returns it back to fullscreen.
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .clickable {
                        mostrarMenu = !mostrarMenu
                    }
            )
        }

        if (mostrarMenu) {
            // Bottom: Horizontal thumbnails carousel of active tracks for selected playlist
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    // Reset the inactivity timer when touching or scrolling the bottom cards list
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                awaitPointerEvent(androidx.compose.ui.input.pointer.PointerEventPass.Initial)
                                resetTimerTrigger++
                            }
                        }
                    }
            ) {
                GrelhaVideosHorizontalCarousel(
                    videos = currentTracks,
                    selectedVideoId = activeVideoId,
                    isLoading = false,
                    accentColor = selectedSection.gradient.first(),
                    onVideoSelected = { track ->
                        onVideoSelected(track)
                        mostrarMenu = false
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

/**
 * Pista visual discreta de rotação para crianças no ecrã de Vídeos
 */
@Composable
fun RotateDeviceHintOverlay(
    visible: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(400)) + scaleIn(initialScale = 0.85f),
        exit = fadeOut(animationSpec = tween(400)) + scaleOut(targetScale = 0.85f),
        modifier = modifier
    ) {
        val infiniteTransition = rememberInfiniteTransition(label = "rotation_hint_anim")
        val rotationAngle by infiniteTransition.animateFloat(
            initialValue = -12f,
            targetValue = 12f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 650, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "phone_tilt"
        )
        val pulseScale by infiniteTransition.animateFloat(
            initialValue = 0.97f,
            targetValue = 1.03f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 650, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "badge_pulse"
        )

        Surface(
            shape = RoundedCornerShape(18.dp),
            color = Color(0xEB0F172A),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.22f)),
            shadowElevation = 5.dp,
            modifier = Modifier
                .graphicsLayer {
                    scaleX = pulseScale
                    scaleY = pulseScale
                }
                .clickable { onDismiss() }
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ScreenRotation,
                    contentDescription = "Rodar para ecrã inteiro",
                    tint = Color(0xFFFBBF24),
                    modifier = Modifier
                        .size(16.dp)
                        .graphicsLayer {
                            rotationZ = rotationAngle
                        }
                )
                Text(
                    text = "Vira o ecrã 📱",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

