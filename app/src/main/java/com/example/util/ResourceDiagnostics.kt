package com.example.util

import android.content.Context
import android.util.Log
import com.example.R
import java.lang.reflect.Field

/**
 * Utility for diagnosing resource availability at runtime.
 * Helps identify if drawables are missing from the APK or inaccessible.
 */
object ResourceDiagnostics {
    private const val TAG = "ResourceDiagnostics"

    /**
     * Scans all fields in R.drawable and checks if they can be opened.
     */
    fun checkAllDrawables(context: Context) {
        Log.i(TAG, "=== Starting Comprehensive Resource Audit ===")
        
        val drawableClass = R.drawable::class.java
        val fields: Array<Field> = drawableClass.fields
        
        var totalCount = 0
        var successCount = 0
        var failureCount = 0

        for (field in fields) {
            try {
                val resourceId = field.getInt(null)
                val resourceName = field.name
                totalCount++

                // Attempt to open the resource to verify it's actually in the APK and valid
                try {
                    context.resources.openRawResource(resourceId).use { 
                        // Resource is accessible
                        successCount++
                        // We use VERBOSE for success to avoid flooding Logcat too much, 
                        // but you can see them if you filter for the tag.
                        Log.v(TAG, "Resource OK: $resourceName (ID: $resourceId)")
                    }
                } catch (e: Exception) {
                    failureCount++
                    Log.e(TAG, "!!! Resource FAILURE: $resourceName (ID: $resourceId) is inaccessible !!!", e)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error accessing field: ${field.name}", e)
            }
        }

        Log.i(TAG, "=== Resource Audit Complete ===")
        Log.i(TAG, "Total Drawables scanned: $totalCount")
        Log.i(TAG, "Success: $successCount")
        if (failureCount > 0) {
            Log.e(TAG, "Failures: $failureCount")
        } else {
            Log.i(TAG, "Failures: 0 (All resources accessible)")
        }
    }

    /**
     * Specific check for common "missing" patterns.
     */
    fun checkCriticalAssets(context: Context) {
        val criticals = listOf(
            "img_header_universo",
            "img_universo_bg",
            "img_ze_mascot_transp",
            "img_ze_ai_mascot_nobg",
            "img_universo_bg",
            "app_logo",
            "ic_launcher_foreground",
            "logo1",
            "ic_ze_traquina_launcher"
        )
        
        Log.i(TAG, "--- Checking Critical Assets ---")
        for (name in criticals) {
            val id = context.resources.getIdentifier(name, "drawable", context.packageName)
            if (id == 0) {
                Log.e(TAG, "CRITICAL MISSING: Drawable '$name' not found in identifier mapping!")
            } else {
                try {
                    context.resources.openRawResource(id).close()
                    Log.d(TAG, "CRITICAL OK: $name (ID: $id)")
                } catch (e: Exception) {
                    Log.e(TAG, "CRITICAL FAILURE: $name (ID: $id) exists but cannot be opened!", e)
                }
            }
        }
    }
}
