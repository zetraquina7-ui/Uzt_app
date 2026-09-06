package com.example.util

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Precision
import com.example.R
import kotlinx.coroutines.launch

/**
 * Centralized Coil ImageLoader helper object that standardizes loading of
 * mascot assets, headers, and media across the app.
 *
 * Ensures all image requests use crossfade, memory & disk caching, and optimized
 * thread execution to prevent UI thread blocking during preview or rendering.
 */
object AppImageLoader {

    @Volatile
    private var instance: ImageLoader? = null

    /**
     * Gets or creates a singleton ImageLoader with disk & memory cache enabled.
     */
    fun get(context: Context): ImageLoader {
        return instance ?: synchronized(this) {
            instance ?: ImageLoader.Builder(context.applicationContext)
                .allowHardware(true)
                .memoryCache {
                    MemoryCache.Builder(context.applicationContext)
                        .maxSizePercent(0.25) // High memory cache for rapid instant navigation
                        .strongReferencesEnabled(true)
                        .build()
                }
                .diskCache {
                    val cacheBase = context.applicationContext.cacheDir ?: context.cacheDir
                    DiskCache.Builder()
                        .directory(cacheBase.resolve("image_cache_webp"))
                        .maxSizePercent(0.20) // 20% disk cache for persistent offline instant display
                        .build()
                }
                .crossfade(true)
                .respectCacheHeaders(false) // Prioritize local cache
                .components {
                    add(RetryInterceptor(maxRetries = 2, baseDelayMs = 400))
                }
                .build()
                .also { instance = it }
        }
    }

    /**
     * Prefetches all educational and curriculum visual assets in background to ensure zero loading delay.
     */
    fun prefetchLearningImages(context: Context) {
        val urls = listOf(
            "https://files.catbox.moe/mp6m9e.jpg", // Animais
            "https://files.catbox.moe/82lzbj.jpg", // Corpo
            "https://files.catbox.moe/zf4c71.png", // Cores
            "https://files.catbox.moe/mxbe80.jpg", // Dinossauros
            "https://files.catbox.moe/jgkz7i.png", // Matemática
            "https://files.catbox.moe/6smgux.jpg", // Frutas
            "https://files.catbox.moe/a8ahhg.png", // Saúde e Higiene
            "https://files.catbox.moe/pxnyml.jpg", // Profissões
            "https://files.catbox.moe/il2z4u.jpg", // Transportes
            "https://files.catbox.moe/9rbtlx.jpg", // Instrumentos
            "https://files.catbox.moe/m9s9i5.jpg", // Emoções
            "https://files.catbox.moe/cqm5mc.jpg", // Dicionário
            "https://files.catbox.moe/so9awi.png", // Números
            "https://files.catbox.moe/m2nqfm.jpg", // Vogais
            "https://files.catbox.moe/2dpgp6.jpg", // Dias da Semana
            "https://files.catbox.moe/jjnzkb.jpg", // Meses do Ano
            "https://files.catbox.moe/sxxmx4.webp", // Cores e Formas
            "https://files.catbox.moe/xf507z.webp"  // Natureza
        )
        val loader = get(context)
        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
            urls.forEach { url ->
                val request = ImageRequest.Builder(context.applicationContext)
                    .data(url)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .build()
                loader.enqueue(request)
            }
        }
    }


    /**
     * Diagnostic logging helper to verify resource existence and accessibility.
     */
    private fun logResourceAccess(context: Context, data: Any?, tag: String) {
        if (data is Int && data > 0) {
            try {
                val resName = context.resources.getResourceEntryName(data)
                val resType = context.resources.getResourceTypeName(data)
                android.util.Log.d("AppImageLoader", "[$tag] Resource: $resName ($resType, ID: $data)")
            } catch (e: Exception) {
                android.util.Log.e("AppImageLoader", "[$tag] Resource ID $data NOT FOUND!", e)
            }
        } else if (data is String) {
            android.util.Log.d("AppImageLoader", "[$tag] Loading from URL/Path: $data")
        }
    }

    /**
     * Builds a standardized ImageRequest for mascot assets with crossfade,
     * memory/disk caching, error handling, and inexact precision.
     */
    fun buildMascotRequest(
        context: Context,
        data: Any?,
        placeholderRes: Int = R.drawable.img_ze_traquina_original,
        errorRes: Int = R.drawable.img_ze_traquina_original,
        isPreviewMode: Boolean = false
    ): ImageRequest {
        logResourceAccess(context, data, "Mascot")
        val mascotDefaultRes = R.drawable.img_ze_traquina_original
        
        val safePlaceholder = PreviewConfig.getSafeDrawableId(context, placeholderRes, mascotDefaultRes)
        val safeError = PreviewConfig.getSafeDrawableId(context, errorRes, mascotDefaultRes)

        val safeData = if (isPreviewMode) {
            if (data is Int && data != 0) PreviewConfig.getSafeDrawableId(context, data, safePlaceholder) else safePlaceholder
        } else {
            if (data is Int && data != 0) PreviewConfig.getSafeDrawableId(context, data, safePlaceholder) else (data ?: mascotDefaultRes)
        }

        val builder = ImageRequest.Builder(context)
            .data(safeData)
            .crossfade(!isPreviewMode)
            .precision(Precision.INEXACT)
            .allowHardware(false)
            .memoryCachePolicy(if (isPreviewMode) CachePolicy.DISABLED else CachePolicy.ENABLED)
            .diskCachePolicy(if (isPreviewMode) CachePolicy.DISABLED else CachePolicy.ENABLED)

        if (safePlaceholder != 0) builder.placeholder(safePlaceholder)
        if (safeError != 0) {
            builder.error(safeError)
            builder.fallback(safeError)
        }

        return builder.build()
    }

    /**
     * Builds a standardized ImageRequest for app headers or background banners.
     */
    fun buildHeaderRequest(
        context: Context,
        data: Any?,
        placeholderRes: Int? = null,
        errorRes: Int? = null,
        isPreviewMode: Boolean = false
    ): ImageRequest {
        logResourceAccess(context, data, "Header")
        val defaultHeader = PreviewConfig.defaultHeaderFallback
        val safePlaceholder = PreviewConfig.getSafeDrawableId(context, placeholderRes ?: defaultHeader, defaultHeader)
        val safeError = PreviewConfig.getSafeDrawableId(context, errorRes ?: defaultHeader, defaultHeader)
        var safeData = PreviewConfig.getPreviewMockData(context, data, safePlaceholder, isPreviewMode)
        
        // Final guard against null data
        if (safeData == null) {
            safeData = if (safePlaceholder != 0) safePlaceholder else defaultHeader
        }
        
        val builder = ImageRequest.Builder(context)
            .data(safeData!!)
            .crossfade(!isPreviewMode)
            .precision(Precision.INEXACT)
            .allowHardware(false)
            .memoryCachePolicy(if (isPreviewMode) CachePolicy.DISABLED else CachePolicy.ENABLED)
            .diskCachePolicy(if (isPreviewMode) CachePolicy.DISABLED else CachePolicy.ENABLED)
            
        if (safePlaceholder != 0) builder.placeholder(safePlaceholder)
        if (safeError != 0) {
            builder.error(safeError)
            builder.fallback(safeError)
        }

        return builder.build()
    }

    /**
     * Builds a general purpose standardized ImageRequest.
     */
    fun buildImageRequest(
        context: Context,
        data: Any?,
        placeholderRes: Int = PreviewConfig.defaultMascotFallback,
        errorRes: Int = PreviewConfig.defaultMascotFallback,
        crossfade: Boolean = true,
        transformations: List<coil.transform.Transformation> = emptyList(),
        isPreviewMode: Boolean = false
    ): ImageRequest {
        logResourceAccess(context, data, "General")
        val safePlaceholder = PreviewConfig.getSafeDrawableId(context, placeholderRes, PreviewConfig.defaultMascotFallback)
        val safeError = PreviewConfig.getSafeDrawableId(context, errorRes, PreviewConfig.defaultMascotFallback)
        var safeData = PreviewConfig.getPreviewMockData(context, data, safePlaceholder, isPreviewMode)
        
        // Final guard against null data
        if (safeData == null) {
            safeData = if (safePlaceholder != 0) safePlaceholder else PreviewConfig.defaultMascotFallback
        }
        
        val builder = ImageRequest.Builder(context)
            .data(safeData!!)
            .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 10; Mobile) AppleWebKit/537.36")
            .addHeader("Referer", "https://imgur.com/")
            .crossfade(if (isPreviewMode) false else crossfade)
            .precision(Precision.INEXACT)
            .allowHardware(false)
            .memoryCachePolicy(if (isPreviewMode) CachePolicy.DISABLED else CachePolicy.ENABLED)
            .diskCachePolicy(if (isPreviewMode) CachePolicy.DISABLED else CachePolicy.ENABLED)
            
        if (safePlaceholder != 0) builder.placeholder(safePlaceholder)
        if (safeError != 0) {
            builder.error(safeError)
            builder.fallback(safeError)
        }

        if (transformations.isNotEmpty()) {
            builder.transformations(transformations)
        }
            
        return builder.build()
    }
}

class RemoveBlackBackgroundTransformation : coil.transform.Transformation {
    override val cacheKey: String = "RemoveBlackBackgroundTransformation"

    override suspend fun transform(input: android.graphics.Bitmap, size: coil.size.Size): android.graphics.Bitmap {
        val width = input.width
        val height = input.height
        val config = input.config ?: android.graphics.Bitmap.Config.ARGB_8888
        val output = input.copy(config, true)
        val pixels = IntArray(width * height)
        output.getPixels(pixels, 0, width, 0, 0, width, height)
        for (i in pixels.indices) {
            val p = pixels[i]
            val r = (p shr 16) and 0xff
            val g = (p shr 8) and 0xff
            val b = p and 0xff
            if (r < 30 && g < 30 && b < 30) {
                pixels[i] = 0x00000000 // Fully transparent
            }
        }
        output.setPixels(pixels, 0, width, 0, 0, width, height)
        return output
    }
}
