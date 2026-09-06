package com.example.util

import java.util.Collections

object MediaFailureTracker {
    private val failedUrls = Collections.synchronizedSet(mutableSetOf<String>())

    fun isUrlFailed(url: String): Boolean {
        if (url.startsWith("raw:") || url.contains("android.resource") || url.startsWith("file:")) {
            return false
        }
        return failedUrls.contains(url)
    }

    fun addFailedUrl(url: String) {
        if (url.startsWith("raw:") || url.contains("android.resource") || url.startsWith("file:")) {
            return
        }
        failedUrls.add(url)
    }

    fun clear() {
        failedUrls.clear()
    }
}
