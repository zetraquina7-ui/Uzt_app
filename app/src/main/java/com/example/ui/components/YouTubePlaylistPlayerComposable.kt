package com.example.ui.components

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.RenderProcessGoneDetail
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ui.theme.SunshineYellow
import kotlinx.coroutines.delay

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun YouTubePlaylistPlayerComposable(
    playlistId: String? = null,
    embedUrl: String? = null,
    modifier: Modifier = Modifier
) {
    val isLocalPreview = androidx.compose.ui.platform.LocalInspectionMode.current || com.example.util.PreviewConfig.isInPreview()
    if (isLocalPreview) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF0F172A)),
            contentAlignment = Alignment.Center
        ) {
            Text("YouTube Playlist (Preview)", color = Color.White, fontSize = 12.sp)
        }
        return
    }

    val isEmulatorPreview = com.example.util.PreviewConfig.isInPreview()

    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }
    var hasError by remember { mutableStateOf(false) }

    val resolvedEmbedSrc = remember(embedUrl, playlistId) {
        val playerParams = "playsinline=1&autoplay=1&enablejsapi=1&rel=0&modestbranding=1&controls=1&fs=1&origin=https://www.youtube-nocookie.com&widget_referrer=https://www.youtube-nocookie.com"
        if (!embedUrl.isNullOrBlank()) {
            val separator = if (embedUrl.contains("?")) "&" else "?"
            val base = embedUrl.replace("youtube.com", "youtube-nocookie.com")
            if (!base.contains("enablejsapi=")) {
                "$base$separator$playerParams"
            } else base
        } else {
            when (playlistId) {
                "PLT7ZV5QsDKA4" -> "https://www.youtube-nocookie.com/embed/videoseries?list=PLT7ZV5QsDKA4&$playerParams"
                "PLHXyMYX6Yxxc" -> "https://www.youtube-nocookie.com/embed/videoseries?list=PLHXyMYX6Yxxc&$playerParams"
                else -> "https://www.youtube-nocookie.com/embed/videoseries?list=PLHz1Xt0IaQWM&$playerParams"
            }
        }
    }

    val htmlData = remember(playlistId, embedUrl) {
        val pId = playlistId ?: "PLHz1Xt0IaQWM"
        val embedPlaylistUrl = "https://www.youtube-nocookie.com/embed/videoseries?list=$pId&autoplay=1&playsinline=1&enablejsapi=1&rel=0&modestbranding=1&controls=1&fs=1"
        """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
            <style>
                * { margin: 0; padding: 0; box-sizing: border-box; }
                html, body { width: 100%; height: 100%; background-color: #000000; overflow: hidden; position: fixed; top: 0; left: 0; right: 0; bottom: 0; }
                iframe { width: 100%; height: 100%; border: none; display: block; position: absolute; top: 0; left: 0; }
            </style>
        </head>
        <body>
            <iframe id="player"
                src="$embedPlaylistUrl"
                width="100%"
                height="100%"
                frameborder="0"
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
                allowfullscreen>
            </iframe>
            <script src="https://www.youtube-nocookie.com/iframe_api"></script>
            <script>
                var player;
                function onYouTubeIframeAPIReady() {
                    player = new YT.Player('player', {
                        events: {
                            'onReady': function(event) {
                                try { event.target.playVideo(); } catch(e) {}
                            },
                            'onError': function(event) {
                                if (window.AndroidBridge && window.AndroidBridge.onPlayerError) {
                                    window.AndroidBridge.onPlayerError(event.data);
                                }
                            }
                        }
                    });
                }
            </script>
        </body>
        </html>
        """.trimIndent()
    }

    var errorCode by remember(resolvedEmbedSrc) { androidx.compose.runtime.mutableIntStateOf(0) }
    var reloadKey by remember { androidx.compose.runtime.mutableIntStateOf(0) }
    var retryCount by remember(resolvedEmbedSrc) { androidx.compose.runtime.mutableIntStateOf(0) }

    LaunchedEffect(resolvedEmbedSrc) {
        isLoading = true
        delay(100)
        isLoading = false
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(420.dp)
            .padding(horizontal = 4.dp, vertical = 2.dp)
            .testTag("youtube_playlist_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
        if (!hasError) {
            androidx.compose.runtime.key(reloadKey, resolvedEmbedSrc) {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { ctx ->
                        WebView(ctx).apply { 
                            com.example.util.EmulatorUtils.optimizeWebViewForEmulator(this)
                            resumeTimers()
                            onResume()
                            
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            setBackgroundColor(android.graphics.Color.BLACK)
                            requestFocus()
                            try {
                                CookieManager.getInstance().setAcceptCookie(true)
                                CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
                            } catch (_: Throwable) {}

                            settings.apply {
                                javaScriptEnabled = true
                                domStorageEnabled = true
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    safeBrowsingEnabled = false
                                }
                                mediaPlaybackRequiresUserGesture = false
                                javaScriptCanOpenWindowsAutomatically = false
                                loadWithOverviewMode = true
                                useWideViewPort = true
                                allowFileAccess = true
                                allowContentAccess = true
                                cacheMode = WebSettings.LOAD_DEFAULT
                                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                            }
                            
                            addJavascriptInterface(
                                YouTubeIFrameBridge(
                                    onReadyCallback = {
                                        isLoading = false
                                    },
                                    onEndedCallback = {
                                        
                                    },
                                    onErrorCallback = { errCode ->
                                        Log.e("YouTubeIFrame", "YouTube playlist player error code $errCode")
                                        errorCode = errCode
                                        hasError = true
                                        isLoading = false


                                    }
                                ),
                                "AndroidBridge"
                            )
                            
                            // Use default rendering without forcing explicit hardware layer type
                            webChromeClient = object : WebChromeClient() {
                            override fun onConsoleMessage(consoleMessage: android.webkit.ConsoleMessage?): Boolean {
                                Log.d("YouTubeWebView", "${consoleMessage?.message()} -- From line ${consoleMessage?.lineNumber()} of ${consoleMessage?.sourceId()}")
                                return true
                            }
                            override fun onShowCustomView(view: android.view.View?, callback: android.webkit.WebChromeClient.CustomViewCallback?) {
                                super.onShowCustomView(view, callback)
                            }
                            override fun onHideCustomView() {
                                super.onHideCustomView()
                            }
                        }
                        webViewClient = object : WebViewClient() {
                            override fun onRenderProcessGone(view: WebView?, detail: RenderProcessGoneDetail?): Boolean {
                                val didCrash = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    detail?.didCrash() == true
                                } else {
                                    false
                                }
                                Log.e("YouTube", "Render process gone (didCrash=$didCrash), retryCount=$retryCount")
                                try {
                                    (view?.parent as? ViewGroup)?.removeView(view)
                                    view?.destroy()
                                } catch (e: Exception) {
                                    Log.e("YouTube", "Error destroying webview on render process gone", e)
                                }
                                if (retryCount < 2) {
                                    retryCount++
                                    reloadKey++
                                } else {
                                    hasError = true
                                }
                                return true
                            }

                            override fun shouldOverrideUrlLoading(
                                view: WebView?,
                                request: WebResourceRequest?
                            ): Boolean {
                                val url = request?.url?.toString() ?: return false
                                if (url.contains("youtube.com") || url.contains("youtube-nocookie.com") || url.contains("googlevideo.com") || url.contains("ytimg.com")) {
                                    return false
                                }
                                return true
                            }
                        }

                        tag = resolvedEmbedSrc
                        loadDataWithBaseURL("https://www.youtube-nocookie.com", htmlData, "text/html", "UTF-8", null)
                    }
                },
                update = { webView ->
                    webView.resumeTimers()
                    webView.onResume()
                    val currentSrc = webView.tag as? String
                    if (currentSrc != resolvedEmbedSrc) {
                        webView.tag = resolvedEmbedSrc
                        webView.stopLoading()
                        webView.loadDataWithBaseURL("https://www.youtube-nocookie.com", htmlData, "text/html", "UTF-8", null)
                    }
                },
                onRelease = { webView ->
                    try {
                        webView.onPause()
                        webView.stopLoading()
                        webView.clearHistory()
                        webView.clearCache(true)
                        webView.clearFormData()
                        webView.clearSslPreferences()
                        webView.loadUrl("about:blank")
                        webView.removeAllViews()
                        (webView.parent as? ViewGroup)?.removeView(webView)
                        webView.destroy()
                    } catch (e: Exception) {
                        Log.e("YouTubePlaylist", "Error releasing webview", e)
                    }
                }
            )
            } // Close key block
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(12.dp)
            ) {
                Text("▶️", fontSize = 32.sp)
                Spacer(modifier = Modifier.height(6.dp))
                if (errorCode != 0) {
                    Text(
                        text = "Ops! Ocorreu um problema ao carregar a playlist (Erro $errorCode)\nO autor deste vídeo no YouTube restringiu a sua reprodução em leitores externos.",
                        color = Color(0xFFFF5252),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                } else {
                    Text(
                        text = "Recarregar playlist de vídeos",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { 
                        hasError = false 
                        errorCode = 0
                        isLoading = true
                        reloadKey++
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SunshineYellow)
                ) {
                    Text("Tentar Novamente", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

            if (isLoading) {
                CircularProgressIndicator(
                    color = SunshineYellow,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}
