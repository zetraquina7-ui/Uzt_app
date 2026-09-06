package com.example.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

/**
 * Helper object providing explicit checks and management for RECORD_AUDIO permissions
 * for the ZéAI voice recognition flow.
 */
object AudioPermissionHelper {

    /**
     * Explicitly checks whether the app currently has RECORD_AUDIO permission granted.
     */
    fun hasAudioPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Helper function that evaluates the permission status:
     * - If granted, immediately invokes [onPermissionGranted]
     * - If not granted, invokes [onShowRationale] to display the friendly explanation dialog
     */
    fun checkAndRequestAudioPermission(
        context: Context,
        onPermissionGranted: () -> Unit,
        onShowRationale: () -> Unit
    ) {
        if (hasAudioPermission(context)) {
            onPermissionGranted()
        } else {
            onShowRationale()
        }
    }
}
