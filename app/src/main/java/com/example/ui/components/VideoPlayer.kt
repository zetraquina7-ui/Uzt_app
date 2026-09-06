package com.example.ui.components

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.JavascriptInterface
import android.webkit.RenderProcessGoneDetail
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.KidStarOrange
import com.example.ui.theme.SkyBluePrimary
import com.example.ui.theme.SunshineYellow
import java.util.regex.Pattern

/**
 * Extracts a YouTube Video ID from any input format (Full URL, Shorts, youtu.be, Embed, or 11-char ID)
 */
fun parseYouTubeVideoId(rawInput: String?): String? {
    if (rawInput.isNullOrBlank()) return null
    val clean = rawInput.trim()

    // 1. Direct 11-character ID
    if (clean.matches(Regex("^[a-zA-Z0-9_-]{11}$"))) {
        return clean
    }

    // 2. YouTube Shorts URL: youtube.com/shorts/{id}
    if (clean.contains("/shorts/")) {
        val candidate = clean.substringAfter("/shorts/")
            .substringBefore("?")
            .substringBefore("&")
            .substringBefore("/")
            .substringBefore("#")
            .trim()
        if (candidate.matches(Regex("^[a-zA-Z0-9_-]{11}$"))) return candidate
        if (candidate.length >= 11) return candidate.take(11)
    }

    // 3. youtu.be/{id}
    if (clean.contains("youtu.be/")) {
        val candidate = clean.substringAfter("youtu.be/")
            .substringBefore("?")
            .substringBefore("&")
            .substringBefore("/")
            .substringBefore("#")
            .trim()
        if (candidate.matches(Regex("^[a-zA-Z0-9_-]{11}$"))) return candidate
        if (candidate.length >= 11) return candidate.take(11)
    }

    // 4. Standard watch?v={id}
    if (clean.contains("v=")) {
        val candidate = clean.substringAfter("v=")
            .substringBefore("&")
            .substringBefore("?")
            .substringBefore("#")
            .trim()
        if (candidate.matches(Regex("^[a-zA-Z0-9_-]{11}$"))) return candidate
        if (candidate.length >= 11) return candidate.take(11)
    }

    // 5. Embed URL: /embed/{id}
    if (clean.contains("/embed/")) {
        val candidate = clean.substringAfter("/embed/")
            .substringBefore("?")
            .substringBefore("&")
            .substringBefore("/")
            .substringBefore("#")
            .trim()
        if (candidate != "videoseries" && candidate.isNotBlank()) {
            if (candidate.matches(Regex("^[a-zA-Z0-9_-]{11}$"))) return candidate
            if (candidate.length >= 11) return candidate.take(11)
        }
    }

    // 6. Regex Pattern fallback
    val pattern = Pattern.compile("(?:v=|/shorts/|youtu\\.be/|/embed/|/v/)([a-zA-Z0-9_-]{11})")
    val matcher = pattern.matcher(clean)
    if (matcher.find()) {
        return matcher.group(1)
    }

    return null
}

/**
 * Extracts Playlist ID from list= query parameter or input
 */
fun parseYouTubePlaylistId(rawInput: String?): String? {
    if (rawInput.isNullOrBlank()) return null
    val clean = rawInput.trim()
    if (clean.contains("list=")) {
        val pid = clean.substringAfter("list=")
            .substringBefore("&")
            .substringBefore("?")
            .substringBefore("#")
            .trim()
        if (pid.isNotBlank()) return pid
    }
    if (clean.startsWith("PL") || clean.startsWith("UU") || clean.startsWith("FL") || clean.startsWith("RD")) {
        return clean
    }
    return null
}

/**
 * JavaScript Interface bridge for YouTube IFrame Player API
 */
class YouTubeIFrameBridge(
    private val onReadyCallback: () -> Unit,
    private val onEndedCallback: () -> Unit,
    private val onErrorCallback: (Int) -> Unit
) {
    @JavascriptInterface
    fun onPlayerReady() {
        onReadyCallback()
    }

    @JavascriptInterface
    fun onPlayerEnded() {
        onEndedCallback()
    }

    @JavascriptInterface
    fun onPlayerError(errorCode: Int) {
        onErrorCallback(errorCode)
    }
}

/**
 * Embedded YouTube Player implementing the official YouTube IFrame Player API.
 * - Runs 100% inside the app without opening external links or YouTube application.
 * - Does not require users to sign in.
 * - Supports fullscreen, playlists, autoplay, and inline playback.
 * - If embedding is unavailable, displays a friendly in-app error UI with retry action.
 */
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun VideoPlayer(
    youtubeId: String? = null,
    playlistId: String? = null,
    videoUrl: String? = null,
    modifier: Modifier = Modifier,
    onVideoEnded: () -> Unit = {}
) {
    val isPreview = androidx.compose.ui.platform.LocalInspectionMode.current || com.example.util.PreviewConfig.isInPreview()
    if (isPreview) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0xFF0F172A)),
            contentAlignment = Alignment.Center
        ) {
            Text("YouTube IFrame Player (Preview)", color = Color.White, fontSize = 13.sp)
        }
        return
    }

    val context = LocalContext.current

    // Resolve exact Video ID and Playlist ID
    val parsedVideoId = remember(youtubeId, videoUrl) {
        parseYouTubeVideoId(youtubeId) ?: parseYouTubeVideoId(videoUrl)
    }

    val parsedPlaylistId = remember(playlistId, videoUrl) {
        parseYouTubePlaylistId(playlistId) ?: parseYouTubePlaylistId(videoUrl)
    }

    // Build the direct YouTube Embed URL
    val resolvedEmbedSrc = remember(parsedVideoId, parsedPlaylistId) {
        val params = "autoplay=1&playsinline=1&enablejsapi=1&rel=0&modestbranding=1&controls=1&fs=1&cc_load_policy=0&origin=https://www.youtube.com"
        when {
            parsedVideoId != null && parsedPlaylistId != null ->
                "https://www.youtube.com/embed/$parsedVideoId?list=$parsedPlaylistId&$params"
            parsedVideoId != null ->
                "https://www.youtube.com/embed/$parsedVideoId?$params"
            parsedPlaylistId != null ->
                "https://www.youtube.com/embed/videoseries?list=$parsedPlaylistId&$params"
            else ->
                "https://www.youtube.com/embed/videoseries?list=PLHz1Xt0IaQWM&$params"
        }
    }

    val htmlContent = remember(parsedVideoId, parsedPlaylistId) {
        buildYouTubeIFrameHtml(parsedVideoId, parsedPlaylistId)
    }

    var isLoading by remember(resolvedEmbedSrc) { mutableStateOf(true) }
    var hasError by remember(resolvedEmbedSrc) { mutableStateOf(false) }
    var errorCode by remember(resolvedEmbedSrc) { mutableIntStateOf(0) }
    var reloadKey by remember { mutableIntStateOf(0) }
    var retryCount by remember(resolvedEmbedSrc) { mutableIntStateOf(0) }

    // Fullscreen custom view support from WebChromeClient
    var customFullscreenView by remember { mutableStateOf<View?>(null) }
    var customViewCallback by remember { mutableStateOf<WebChromeClient.CustomViewCallback?>(null) }

    // Handle Android back button when in fullscreen
    if (customFullscreenView != null) {
        BackHandler {
            customViewCallback?.onCustomViewHidden()
            customFullscreenView = null
            customViewCallback = null
        }
    }

    // Fullscreen Dialog overlay
    if (customFullscreenView != null) {
        Dialog(
            onDismissRequest = {
                customViewCallback?.onCustomViewHidden()
                customFullscreenView = null
                customViewCallback = null
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                decorFitsSystemWindows = false
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                AndroidView(
                    factory = { _ ->
                        FrameLayout(context).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            customFullscreenView?.let { cv ->
                                (cv.parent as? ViewGroup)?.removeView(cv)
                                addView(cv)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )

                // Close Fullscreen Button
                IconButton(
                    onClick = {
                        customViewCallback?.onCustomViewHidden()
                        customFullscreenView = null
                        customViewCallback = null
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.FullscreenExit,
                        contentDescription = "Sair do Ecrã Inteiro",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }

    Box(
        modifier = modifier
            .testTag("embedded_youtube_player_box")
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        if (hasError) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF0F172A))
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFEF4444).copy(alpha = 0.2f),
                            modifier = Modifier.size(54.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.ErrorOutline,
                                    contentDescription = null,
                                    tint = Color(0xFFEF4444),
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = if (errorCode == 152 || errorCode == 150 || errorCode == 101) {
                                "Vídeo com Restrição de Reprodução (Erro $errorCode)"
                            } else {
                                "Ops! Ocorreu um problema ao carregar o vídeo (Erro $errorCode)"
                            },
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = if (errorCode == 152 || errorCode == 150 || errorCode == 101) {
                                "O autor ou detentor de direitos autorais deste vídeo no YouTube não permite a sua reprodução em leitores externos de aplicações."
                            } else {
                                "Verifica a tua ligação à Internet ou tenta recarregar o vídeo."
                            },
                            fontSize = 11.sp,
                            color = Color(0xFF94A3B8),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = {
                                    hasError = false
                                    errorCode = 0
                                    isLoading = true
                                    reloadKey++
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Recarregar no APK", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                } else {
                    // Active Embedded YouTube Player inside App
                    androidx.compose.runtime.key(reloadKey, resolvedEmbedSrc) {
                        AndroidView(
                            modifier = Modifier.fillMaxSize(),
                            factory = { ctx ->
                                WebView(ctx).apply {
                                    com.example.util.EmulatorUtils.optimizeWebViewForEmulator(this)
                                    layoutParams = ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT
                                    )
                                    setBackgroundColor(android.graphics.Color.BLACK)

                                    try {
                                        CookieManager.getInstance().setAcceptCookie(true)
                                        CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
                                    } catch (_: Throwable) {}
                                    
                                    settings.apply {
                                        javaScriptEnabled = true
                                        domStorageEnabled = true
                                        mediaPlaybackRequiresUserGesture = false
                                        javaScriptCanOpenWindowsAutomatically = false
                                        loadWithOverviewMode = true
                                        useWideViewPort = true
                                        allowFileAccess = true
                                        allowContentAccess = true
                                        cacheMode = WebSettings.LOAD_DEFAULT
                                        mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                                    }

                                    // Attach Android Bridge
                                    addJavascriptInterface(
                                        YouTubeIFrameBridge(
                                            onReadyCallback = {
                                                isLoading = false
                                            },
                                            onEndedCallback = {
                                                onVideoEnded()
                                            },
                                            onErrorCallback = { errCode ->
                                                Log.e("YouTubeIFrame", "YouTube player error code $errCode")
                                                errorCode = errCode
                                                hasError = true
                                                isLoading = false

                                                // If YouTube embedding is restricted by video owner/copyright (152, 150, 101, 100), auto-open in YouTube

                                            }
                                        ),
                                        "AndroidBridge"
                                    )

                                    webChromeClient = object : WebChromeClient() {
                                        override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                                            customFullscreenView = view
                                            customViewCallback = callback
                                        }

                                        override fun onHideCustomView() {
                                            customViewCallback?.onCustomViewHidden()
                                            customFullscreenView = null
                                            customViewCallback = null
                                        }
                                    }

                                    webViewClient = object : WebViewClient() {
                                        override fun onPageFinished(view: WebView?, url: String?) {
                                            super.onPageFinished(view, url)
                                            isLoading = false
                                        }

                                        override fun onReceivedError(
                                            view: WebView?,
                                            request: WebResourceRequest?,
                                            error: WebResourceError?
                                        ) {
                                            super.onReceivedError(view, request, error)
                                            if (request?.isForMainFrame == true) {
                                                Log.e("VideoPlayer", "MainFrame Error: ${error?.description}")
                                                hasError = true
                                                isLoading = false
                                            }
                                        }

                                        override fun onRenderProcessGone(view: WebView?, detail: RenderProcessGoneDetail?): Boolean {
                                            val didCrash = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                                detail?.didCrash() == true
                                            } else {
                                                false
                                            }
                                            Log.e("VideoPlayer", "Render process gone (didCrash=$didCrash), retryCount=$retryCount")
                                            try {
                                                view?.stopLoading()
                                                (view?.parent as? ViewGroup)?.removeView(view)
                                                view?.destroy()
                                            } catch (_: Exception) {}
                                            
                                            if (retryCount < 2) {
                                                retryCount++
                                                reloadKey++
                                            } else {
                                                hasError = true
                                                isLoading = false
                                            }
                                            return true
                                        }

                                        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                                            val targetUrl = request?.url?.toString() ?: return false
                                            if (targetUrl.contains("youtube.com") || targetUrl.contains("youtube-nocookie.com") || targetUrl.contains("googlevideo.com") || targetUrl.contains("ytimg.com")) {
                                                return false
                                            }
                                            val extractedId = parseYouTubeVideoId(targetUrl)
                                            val extractedPlaylist = parseYouTubePlaylistId(targetUrl)
                                            if (extractedId != null) {
                                                val newHtml = buildYouTubeIFrameHtml(extractedId, extractedPlaylist)
                                                view?.loadDataWithBaseURL("https://www.youtube-nocookie.com", newHtml, "text/html", "UTF-8", null)
                                                return true
                                            }
                                            return true
                                        }
                                    }

                                    tag = resolvedEmbedSrc
                                    loadDataWithBaseURL("https://www.youtube-nocookie.com", htmlContent, "text/html", "UTF-8", null)
                                }
                            },
                            update = { webView ->
                                val currentSrc = webView.tag as? String
                                if (currentSrc != resolvedEmbedSrc) {
                                    webView.tag = resolvedEmbedSrc
                                    webView.stopLoading()
                                    webView.loadDataWithBaseURL("https://www.youtube-nocookie.com", htmlContent, "text/html", "UTF-8", null)
                                }
                            },
                            onRelease = { webView ->
                                try {
                                    webView.stopLoading()
                                    webView.loadUrl("about:blank")
                                    (webView.parent as? ViewGroup)?.removeView(webView)
                                    webView.destroy()
                                } catch (_: Exception) {}
                            }
                        )
                    }

                    if (isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.5f)),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = SkyBluePrimary,
                                strokeWidth = 3.dp,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }

                // Glass Shine Accent Overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = 0.08f),
                                    Color.Transparent,
                                    Color.Transparent
                                )
                            )
                        )
                )
    }
}

/**
 * Builds reliable HTML embedding official YouTube embed URL directly via iframe.
 */
private fun buildYouTubeIFrameHtml(videoId: String?, playlistId: String?): String {
    val cleanVideoId = videoId?.trim()?.takeIf { it.isNotBlank() } ?: "wOnvZxQ-Iio"
    val cleanPlaylistId = playlistId?.trim()?.takeIf { it.isNotBlank() } ?: ""

    val embedUrl = when {
        cleanVideoId.isNotBlank() && cleanPlaylistId.isNotBlank() ->
            "https://www.youtube.com/embed/$cleanVideoId?list=$cleanPlaylistId&autoplay=1&playsinline=1&rel=0&modestbranding=1&controls=1&fs=1&cc_load_policy=0"
        cleanVideoId.isNotBlank() ->
            "https://www.youtube.com/embed/$cleanVideoId?autoplay=1&playsinline=1&rel=0&modestbranding=1&controls=1&fs=1&cc_load_policy=0"
        cleanPlaylistId.isNotBlank() ->
            "https://www.youtube.com/embed/videoseries?list=$cleanPlaylistId&autoplay=1&playsinline=1&rel=0&modestbranding=1&controls=1&fs=1&cc_load_policy=0"
        else ->
            "https://www.youtube.com/embed/wOnvZxQ-Iio?autoplay=1&playsinline=1&rel=0&modestbranding=1&controls=1&fs=1&cc_load_policy=0"
    }

    return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
            <style>
                * { margin: 0; padding: 0; box-sizing: border-box; }
                html, body { width: 100%; height: 100%; background-color: #000000; overflow: hidden; position: fixed; top: 0; left: 0; right: 0; bottom: 0; }
                iframe { width: 100%; height: 100%; border: none; }
            </style>
        </head>
        <body>
            <iframe src="$embedUrl" allow="autoplay; encrypted-media; fullscreen; picture-in-picture" allowfullscreen></iframe>
        </body>
        </html>
    """.trimIndent()
}
