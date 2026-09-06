package com.example.util

import android.content.Context
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * ZeAvatarCacheManager
 * Pre-downloads and caches Zé Traquina's live avatar video loops locally on the device.
 * This eliminates network latency, buffering delays, and initial load stalls when opening
 * the Zé AI screen or switching between Idle, Listening, and Talking states.
 */
object ZeAvatarCacheManager {

    private const val TAG = "ZeAvatarCacheManager"

    const val VIDEO_URL_IDLE = "https://files.catbox.moe/q9fduq.mp4"
    const val VIDEO_URL_LISTENING = "https://files.catbox.moe/b8tryz.mp4"
    const val VIDEO_URL_TALKING = "https://files.catbox.moe/51a3tn.mp4"
    const val VIDEO_URL_HOME = "https://files.catbox.moe/cxlun5.mp4"

    private val ALL_VIDEOS = listOf(
        VIDEO_URL_IDLE,
        VIDEO_URL_LISTENING,
        VIDEO_URL_TALKING,
        VIDEO_URL_HOME
    )

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    /**
     * Maps a remote URL to a local cache file.
     */
    fun getCacheFile(context: Context, url: String): File {
        val cacheDir = File(context.cacheDir, "ze_avatar_cache")
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
        val fileName = when (url) {
            VIDEO_URL_IDLE -> "avatar_idle.mp4"
            VIDEO_URL_LISTENING -> "avatar_listening.mp4"
            VIDEO_URL_TALKING -> "avatar_talking.mp4"
            VIDEO_URL_HOME -> "home_main_video.mp4"
            else -> "video_${url.hashCode()}.mp4"
        }
        return File(cacheDir, fileName)
    }

    /**
     * Checks if a file is a valid MP4 video by validating the File Type Box (ftyp) header at offset 4.
     */
    fun isValidMp4(file: File): Boolean {
        if (!file.exists() || file.length() < 8) return false
        try {
            file.inputStream().use { input ->
                val header = ByteArray(8)
                val read = input.read(header)
                if (read < 8) return false
                // Bytes at offset 4-7 must be 'f', 't', 'y', 'p' (0x66, 0x74, 0x79, 0x70)
                return header[4] == 'f'.toByte() &&
                       header[5] == 't'.toByte() &&
                       header[6] == 'y'.toByte() &&
                       header[7] == 'p'.toByte()
            }
        } catch (_: Throwable) {
            return false
        }
    }

    /**
     * Checks if a video is already cached and valid on local storage.
     * Auto-deletes the cached file if it is found to be corrupted or invalid.
     */
    fun isCached(context: Context, url: String): Boolean {
        val file = getCacheFile(context, url)
        if (file.exists()) {
            if (file.length() > 5000 && isValidMp4(file)) {
                return true
            } else {
                Log.w(TAG, "Deleting invalid/corrupted cache file: ${file.absolutePath}")
                try {
                    file.delete()
                } catch (_: Throwable) {}
            }
        }
        return false
    }

    /**
     * Returns a local file Uri if cached. If not cached, downloads synchronously and returns local Uri (or remote Uri as fallback).
     */
    fun getOrDownloadMediaUri(context: Context, url: String): Uri {
        val file = getCacheFile(context, url)
        if (isCached(context, url)) {
            return Uri.fromFile(file)
        }
        preloadVideoInternal(context, url)
        if (isCached(context, url)) {
            return Uri.fromFile(file)
        }
        return Uri.parse(url)
    }

    /**
     * Returns a local file Uri if cached, or the remote network Uri if not yet cached.
     * Also triggers a background cache attempt if missing.
     */
    fun getMediaUri(context: Context, url: String): Uri {
        val file = getCacheFile(context, url)
        if (isCached(context, url)) {
            return Uri.fromFile(file)
        }
        // Trigger background preload if missing
        preloadVideo(context, url)
        return Uri.parse(url)
    }

    /**
     * Preloads all live avatar videos in background.
     * Can be invoked on Application startup or MainActivity creation.
     */
    fun preloadAll(context: Context) {
        scope.launch {
            ALL_VIDEOS.forEach { url ->
                preloadVideoInternal(context, url)
            }
        }
    }

    /**
     * Preloads a specific video URL asynchronously.
     */
    fun preloadVideo(context: Context, url: String) {
        scope.launch {
            preloadVideoInternal(context, url)
        }
    }

    private fun preloadVideoInternal(context: Context, url: String) {
        val targetFile = getCacheFile(context, url)
        if (isCached(context, url)) {
            return // Already cached and valid
        }

        Log.d(TAG, "Starting fast preload of avatar video: $url")
        val tempFile = File(targetFile.parentFile, "${targetFile.name}.tmp")
        try {
            if (tempFile.exists()) {
                tempFile.delete()
            }
            
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.connectTimeout = 10000
            connection.readTimeout = 20000
            connection.requestMethod = "GET"
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 13)")
            connection.connect()

            if (connection.responseCode in 200..299) {
                val contentType = connection.contentType ?: ""
                if (contentType.contains("text/html") || contentType.contains("text/plain") || contentType.contains("application/json")) {
                    Log.w(TAG, "Preload returned invalid content-type '$contentType' for $url")
                    return
                }
                val expectedLength = connection.contentLength
                connection.inputStream.use { input ->
                    FileOutputStream(tempFile).use { output ->
                        input.copyTo(output, bufferSize = 32 * 1024)
                    }
                }
                val finalFileLength = tempFile.length()
                val isSizeValid = if (expectedLength > 0) {
                    finalFileLength == expectedLength.toLong()
                } else {
                    finalFileLength > 5000
                }
                if (tempFile.exists() && isSizeValid && isValidMp4(tempFile)) {
                    if (targetFile.exists()) targetFile.delete()
                    tempFile.renameTo(targetFile)
                    Log.d(TAG, "Successfully cached avatar video to ${targetFile.absolutePath} (${targetFile.length()} bytes)")
                } else {
                    Log.w(TAG, "Discarding incomplete or invalid download. Expected: $expectedLength, got: $finalFileLength")
                    if (tempFile.exists()) {
                        tempFile.delete()
                    }
                }
            } else {
                Log.w(TAG, "Preload failed with HTTP code: ${connection.responseCode} for $url")
            }
        } catch (e: Exception) {
            Log.w(TAG, "Error preloading avatar video $url: ${e.message}")
        } finally {
            try {
                if (tempFile.exists()) {
                    tempFile.delete()
                }
            } catch (_: Throwable) {}
        }
    }
}
