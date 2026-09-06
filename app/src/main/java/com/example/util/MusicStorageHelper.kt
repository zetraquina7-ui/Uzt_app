package com.example.util

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import com.example.service.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

/**
 * Helper to save, load and manage custom uploaded MP3 tracks in persistent app internal storage.
 * Ensures ExoPlayer running in background MusicService can always access the files without URI permission issues.
 */
object MusicStorageHelper {
    private const val TAG = "MusicStorageHelper"
    private const val PREFS_NAME = "custom_music_prefs"
    private const val KEY_TRACKS_JSON = "saved_custom_tracks"
    private const val AUDIO_DIR_NAME = "user_audio"

    private fun getAudioDirectory(context: Context): File {
        val dir = File(context.filesDir, AUDIO_DIR_NAME)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return dir
    }

    /**
     * Extracts readable display name from a Content URI
     */
    fun extractFileName(context: Context, uri: Uri): String {
        var name: String? = null
        try {
            if (uri.scheme == "content") {
                val cursor = context.contentResolver.query(uri, null, null, null, null)
                cursor?.use {
                    if (it.moveToFirst()) {
                        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        if (nameIndex != -1) {
                            name = it.getString(nameIndex)
                        }
                    }
                }
            }
            if (name.isNullOrBlank()) {
                name = uri.lastPathSegment
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error extracting file name", e)
        }

        val finalName = name ?: "Minha Música MP3"
        return finalName.removeSuffix(".mp3").removeSuffix(".MP3").removeSuffix(".m4a").removeSuffix(".wav")
    }

    /**
     * Copies selected audio Uri into the internal app storage and returns the new Track object.
     */
    suspend fun saveUploadedTrack(context: Context, sourceUri: Uri, customTitle: String? = null): Track? = withContext(Dispatchers.IO) {
        try {
            val audioDir = getAudioDirectory(context)
            val trackId = "custom_${System.currentTimeMillis()}_${UUID.randomUUID().toString().take(6)}"
            val destFile = File(audioDir, "$trackId.mp3")

            val inputStream = context.contentResolver.openInputStream(sourceUri)
                ?: return@withContext null

            FileOutputStream(destFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
            inputStream.close()

            val title = if (!customTitle.isNullOrBlank()) {
                customTitle.trim()
            } else {
                extractFileName(context, sourceUri)
            }

            val track = Track(
                id = trackId,
                title = title,
                artist = "Música Carregada",
                uri = Uri.fromFile(destFile),
                isCustom = true
            )

            // Save to persistent SharedPreferences
            persistTrack(context, track)

            track
        } catch (e: Exception) {
            Log.e(TAG, "Failed to save uploaded audio track", e)
            null
        }
    }

    /**
     * Loads all saved custom tracks from persistent storage
     */
    fun loadPersistedCustomTracks(context: Context): List<Track> {
        val tracks = mutableListOf<Track>()
        try {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val jsonString = prefs.getString(KEY_TRACKS_JSON, null) ?: return emptyList()
            val array = JSONArray(jsonString)

            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                val id = obj.getString("id")
                val title = obj.getString("title")
                val artist = obj.optString("artist", "Música Carregada")
                val filePath = obj.optString("filePath", "")

                val file = File(filePath)
                if (file.exists()) {
                    tracks.add(
                        Track(
                            id = id,
                            title = title,
                            artist = artist,
                            uri = Uri.fromFile(file),
                            isCustom = true
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error loading persisted tracks", e)
        }
        return tracks
    }

    private fun persistTrack(context: Context, track: Track) {
        try {
            val currentTracks = loadPersistedCustomTracks(context).toMutableList()
            // Remove duplicate if exists
            currentTracks.removeAll { it.id == track.id }
            currentTracks.add(track)

            saveTracksList(context, currentTracks)
        } catch (e: Exception) {
            Log.e(TAG, "Error persisting track", e)
        }
    }

    /**
     * Deletes a custom uploaded track from disk and storage
     */
    fun deleteCustomTrack(context: Context, trackId: String): Boolean {
        try {
            val tracks = loadPersistedCustomTracks(context).toMutableList()
            val toRemove = tracks.find { it.id == trackId }
            if (toRemove != null) {
                toRemove.uri?.path?.let { path ->
                    val file = File(path)
                    if (file.exists()) {
                        file.delete()
                    }
                }
                tracks.remove(toRemove)
                saveTracksList(context, tracks)
                return true
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting track $trackId", e)
        }
        return false
    }

    private fun saveTracksList(context: Context, tracks: List<Track>) {
        val array = JSONArray()
        for (t in tracks) {
            val obj = JSONObject()
            obj.put("id", t.id)
            obj.put("title", t.title)
            obj.put("artist", t.artist)
            obj.put("filePath", t.uri?.path ?: "")
            array.put(obj)
        }

        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_TRACKS_JSON, array.toString())
            .apply()
    }
}
