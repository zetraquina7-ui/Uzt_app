package com.example.util

import android.os.Build
import android.webkit.WebView
import android.webkit.WebSettings

object EmulatorUtils {
    val hasDriRenderNode: Boolean by lazy {
        try {
            java.io.File("/dev/dri/renderD128").exists() || java.io.File("/dev/dri/card0").exists()
        } catch (_: Throwable) {
            false
        }
    }

    val isEmulator: Boolean by lazy { 
        if (!hasDriRenderNode) return@lazy true
        val finger = (Build.FINGERPRINT ?: "").lowercase()
        val model = (Build.MODEL ?: "").lowercase()
        val prod = (Build.PRODUCT ?: "").lowercase()
        val hardware = (Build.HARDWARE ?: "").lowercase()
        val brand = (Build.BRAND ?: "").lowercase()
        val device = (Build.DEVICE ?: "").lowercase()
        val manufacturer = (Build.MANUFACTURER ?: "").lowercase()
        val board = (Build.BOARD ?: "").lowercase()
        val host = (Build.HOST ?: "").lowercase()

        finger.contains("generic") || finger.contains("redroid") || finger.contains("aosp") ||
                hardware.contains("gce") || model.contains("gce") || prod.contains("gce") ||
                model.contains("aosp") || hardware.contains("aosp") || prod.contains("aosp") ||
                model.contains("redroid") || hardware.contains("redroid") || manufacturer.contains("redroid") ||
                finger.contains("sdk") ||
                model.contains("google_sdk") ||
                model.contains("emulator") ||
                model.contains("android sdk built for x86") ||
                prod.contains("sdk_gphone") ||
                prod.contains("sdk") ||
                prod.contains("emulator") ||
                hardware.contains("goldfish") ||
                hardware.contains("ranchu") ||
                hardware.contains("cutf") ||
                hardware.contains("vbox") ||
                brand.startsWith("generic") ||
                device.startsWith("generic") ||
                board.contains("goldfish") ||
                board.contains("cutf") ||
                host.contains("android-build") ||
                manufacturer.contains("genymotion") ||
                manufacturer.contains("google") ||
                hardware.contains("qemu") ||
                hardware.contains("kvm") ||
                hardware.contains("x86")
    }

    fun initWebViewEnvironment(context: android.content.Context) {
        try {
            val cacheDir = context.cacheDir
            val jsCodeCache = java.io.File(cacheDir, "WebView/Default/HTTP Cache/Code Cache/js")
            if (!jsCodeCache.exists()) {
                jsCodeCache.mkdirs()
            }
            val wasmCodeCache = java.io.File(cacheDir, "WebView/Default/HTTP Cache/Code Cache/wasm")
            if (!wasmCodeCache.exists()) {
                wasmCodeCache.mkdirs()
            }
            val defaultHttpCache = java.io.File(cacheDir, "WebView/Default/HTTP Cache")
            if (!defaultHttpCache.exists()) {
                defaultHttpCache.mkdirs()
            }
            val gpuCache = java.io.File(cacheDir, "WebView/Default/GPUCache")
            if (!gpuCache.exists()) {
                gpuCache.mkdirs()
            }
            val appWebviewDir = java.io.File(context.filesDir.parentFile, "app_webview")
            if (!appWebviewDir.exists()) {
                appWebviewDir.mkdirs()
            }
        } catch (_: Throwable) {}
    }

    fun optimizeWebViewForEmulator(webView: WebView) {
        try {
            // If in headless/emulator without DRI render node, adjust rendering to avoid MESA driver errors
            if (!hasDriRenderNode) {
                // On systems without DRI render node, standard hardware layer might emit MESA rendernode warnings
                // We keep layer type compatible without forcing failing GPU calls
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                webView.setLayerType(android.view.View.LAYER_TYPE_SOFTWARE, null)
            }
            
            webView.settings.apply {
                // Ensure proper JavaScript and DOM storage
                javaScriptEnabled = true
                domStorageEnabled = true
                databaseEnabled = true
                mediaPlaybackRequiresUserGesture = false
                
                // Allow safe file and content access to support Chromium sandboxed system component interface queries smoothly
                allowFileAccess = true
                allowContentAccess = true
                setGeolocationEnabled(false)
                
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    safeBrowsingEnabled = false
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                }
                cacheMode = WebSettings.LOAD_DEFAULT
                
                // Optimization for chromium sandboxing
                blockNetworkImage = false
                loadWithOverviewMode = true
                useWideViewPort = true
            }
        } catch (_: Throwable) {}
    }
}


