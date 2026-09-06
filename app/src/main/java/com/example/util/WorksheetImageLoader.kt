package com.example.util

import android.content.Context
import androidx.annotation.DrawableRes
import com.example.R

/**
 * Utility to load worksheet resources dynamically.
 */
object WorksheetImageLoader {

    /**
     * Finds a drawable resource ID by its name.
     * Returns 0 if not found.
     */
    @DrawableRes
    fun getDrawableId(context: Context, resourceName: String?): Int {
        if (resourceName.isNullOrBlank()) return 0
        
        // Remove extension if present (e.g. .png, .jpg)
        val cleanName = resourceName.substringBeforeLast(".")
        
        return context.resources.getIdentifier(
            cleanName,
            "drawable",
            context.packageName
        )
    }

    /**
     * Determines if the string is a remote URL.
     */
    fun isRemoteUrl(path: String?): Boolean {
        if (path.isNullOrBlank()) return false
        return path.startsWith("http://", ignoreCase = true) || 
               path.startsWith("https://", ignoreCase = true)
    }

    /**
     * Returns a fallback image resource if the requested one is missing.
     */
    @DrawableRes
    fun getFallbackDrawable(): Int {
        // Using app_logo or ic_ze_traquina_launcher as fallback
        return R.drawable.ic_ze_traquina_launcher
    }
}
