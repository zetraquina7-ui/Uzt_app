package com.example.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Precision
import com.example.R

/**
 * Utility object for Compose Preview inspection, EditMode detection,
 * and mocking image loading operations with local static drawable fallbacks.
 *
 * Prevents network-based Coil operations or slow disk operations from stalling
 * the Compose compiler, preview renderer, or UI preview thread.
 */
object PreviewConfig {

    /**
     * Default static fallback drawable resource for general images/mascots in preview or edit mode.
     */
    val defaultMascotFallback: Int = R.drawable.img_ze_traquina_original

    /**
     * Default static fallback drawable resource for headers/banners in preview or edit mode.
     */
    val defaultHeaderFallback: Int = R.drawable.img_header_universo

    /**
     * Returns true if Compose is currently rendering inside a Preview or Inspection mode.
     */
    @Composable
    fun isInPreview(): Boolean {
        // LocalInspectionMode is true for Android Studio Compose Previews
        // forceSafeMode can be used for explicit safe mode
        return LocalInspectionMode.current || forceSafeMode
    }

    // Force Safe Mode for Preview (No Shaders, No heavy effects)
    private var forceSafeMode: Boolean = false

    fun setForceSafeMode(enabled: Boolean) {
        forceSafeMode = enabled
    }

    /**
     * Validates if a resource ID exists and can be loaded as a drawable in context resources, otherwise returns fallbackRes.
     * Guaranteed to return a non-zero ID if fallbackRes is valid or if a system fallback is needed.
     */
    fun getSafeDrawableId(context: Context, resId: Int, fallbackRes: Int = 0): Int {
        if (isValidDrawable(context, resId)) return resId
        if (fallbackRes != 0 && isValidDrawable(context, fallbackRes)) return fallbackRes
        
        // Log the failure if we had an intention to load something but it's invalid
        if (resId != 0 || fallbackRes != 0) {
            android.util.Log.e("PreviewConfig", "CRITICAL: Resource validation failed for resId $resId and fallbackRes $fallbackRes.")
        }
        
        // If even the requested fallback is invalid, use a guaranteed system fallback
        if (fallbackRes != 0) {
            return android.R.drawable.ic_menu_report_image
        }
        
        return 0
    }

    /**
     * Internal check for drawable validity.
     * Uses ContextCompat.getDrawable to ensure it's a valid drawable resource.
     */
    private fun isValidDrawable(context: Context?, resId: Int): Boolean {
        if (context == null || resId <= 0) return false
        return try {
            val drawable = androidx.core.content.ContextCompat.getDrawable(context, resId)
            drawable != null
        } catch (e: Throwable) {
            false
        }
    }

    /**
     * Sanitizes data input for Coil image requests when in Preview or EditMode.
     * If [isPreviewMode] is true or [data] is null or an invalid resource ID,
     * it substitutes a static local drawable fallback.
     */
    fun getPreviewMockData(
        context: Context?,
        data: Any?,
        fallbackRes: Int = defaultMascotFallback,
        isPreviewMode: Boolean = false
    ): Any? {
        val safeFallback = if (fallbackRes != 0) fallbackRes else null
        
        if (isPreviewMode || data == null) {
            return safeFallback
        }
        if (data is Int) {
            if (context != null) {
                val safeId = getSafeDrawableId(context, data, fallbackRes)
                return if (safeId != 0) safeId else null
            }
            return if (data != 0) data else null
        }
        if (data is String && (data.startsWith("http://") || data.startsWith("https://"))) {
            if (isPreviewMode) {
                return safeFallback
            }
        }
        return data
    }

    /**
     * Legacy overload for backwards compatibility.
     */
    fun getPreviewMockData(
        data: Any?,
        fallbackRes: Int = defaultMascotFallback,
        isPreviewMode: Boolean = false
    ): Any? {
        if (isPreviewMode || data == null) {
            return if (fallbackRes != 0) fallbackRes else null
        }
        return if (data is Int && data == 0) null else data
    }

    /**
     * Builds a safe Coil [ImageRequest] configured specifically for Preview or Runtime.
     * In preview or edit mode, it avoids network/disk operations and uses local static drawables.
     */
    fun buildSafeRequest(
        context: Context,
        data: Any?,
        placeholderRes: Int = defaultMascotFallback,
        errorRes: Int = defaultMascotFallback,
        isPreviewMode: Boolean = false
    ): ImageRequest {
        val safePlaceholder = getSafeDrawableId(context, placeholderRes, defaultMascotFallback)
        val safeError = getSafeDrawableId(context, errorRes, defaultMascotFallback)
        val safeData = getPreviewMockData(context, data, safePlaceholder, isPreviewMode)

        val builder = ImageRequest.Builder(context)
            .data(safeData)
            .crossfade(!isPreviewMode)
            .allowHardware(!isPreviewMode)
            .precision(Precision.INEXACT)

        
        

        if (isPreviewMode) {
            builder.memoryCachePolicy(CachePolicy.DISABLED)
                .diskCachePolicy(CachePolicy.DISABLED)
                .networkCachePolicy(CachePolicy.DISABLED)
        } else {
            builder.memoryCachePolicy(CachePolicy.ENABLED)
                .diskCachePolicy(CachePolicy.ENABLED)
        }

        return builder.build()
    }
}
