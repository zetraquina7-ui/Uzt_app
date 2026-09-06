package com.example.viewmodel

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.service.MusicService
import com.example.service.RepeatMode
import com.example.service.Track
import com.example.util.MusicStorageHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MediaViewModel : ViewModel() {
    private val TAG = "MediaViewModel"
    private var musicService: MusicService? = null
    private var isBound = false

    private val _serviceConnected = MutableStateFlow(false)
    val serviceConnected: StateFlow<Boolean> = _serviceConnected.asStateFlow()

    private val _musicIsPlaying = MutableStateFlow(false)
    val musicIsPlaying: StateFlow<Boolean> = _musicIsPlaying.asStateFlow()

    private val _currentMusicTrack = MutableStateFlow<Track?>(null)
    val currentMusicTrack: StateFlow<Track?> = _currentMusicTrack.asStateFlow()

    private val _playlist = MutableStateFlow<List<Track>>(emptyList())
    val playlist: StateFlow<List<Track>> = _playlist.asStateFlow()

    private val _currentPositionMs = MutableStateFlow(0)
    val currentPositionMs: StateFlow<Int> = _currentPositionMs.asStateFlow()

    private val _durationMs = MutableStateFlow(1)
    val durationMs: StateFlow<Int> = _durationMs.asStateFlow()

    private val _volume = MutableStateFlow(1.0f)
    val volume: StateFlow<Float> = _volume.asStateFlow()

    private val _isShuffle = MutableStateFlow(false)
    val isShuffle: StateFlow<Boolean> = _isShuffle.asStateFlow()

    private val _repeatMode = MutableStateFlow(RepeatMode.REPEAT_ALL)
    val repeatMode: StateFlow<RepeatMode> = _repeatMode.asStateFlow()

    private val _isUploading = MutableStateFlow(false)
    val isUploading: StateFlow<Boolean> = _isUploading.asStateFlow()

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "MusicService connected successfully")
            val binder = service as MusicService.MusicBinder
            val s = binder.getService()
            musicService = s
            isBound = true
            _serviceConnected.value = true

            viewModelScope.launch {
                s.isPlaying.collect { _musicIsPlaying.value = it }
            }
            viewModelScope.launch {
                s.currentTrack.collect { _currentMusicTrack.value = it }
            }
            viewModelScope.launch {
                s.playlist.collect { _playlist.value = it }
            }
            viewModelScope.launch {
                s.currentPositionMs.collect { _currentPositionMs.value = it }
            }
            viewModelScope.launch {
                s.durationMs.collect { _durationMs.value = it }
            }
            viewModelScope.launch {
                s.volume.collect { _volume.value = it }
            }
            viewModelScope.launch {
                s.isShuffle.collect { _isShuffle.value = it }
            }
            viewModelScope.launch {
                s.repeatMode.collect { _repeatMode.value = it }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "MusicService disconnected")
            musicService = null
            isBound = false
            _serviceConnected.value = false
        }
    }

    fun bindService(context: Context) {
        if (isBound) return
        try {
            Log.d(TAG, "Binding MusicService...")
            val appContext = context.applicationContext
            val intent = Intent(appContext, MusicService::class.java)
            try {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    appContext.startForegroundService(intent)
                } else {
                    appContext.startService(intent)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed startForegroundService, trying startService", e)
                try { appContext.startService(intent) } catch (_: Throwable) {}
            }
            appContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        } catch (e: Exception) {
            Log.e(TAG, "Error binding MusicService", e)
        }
    }

    fun unbindService(context: Context) {
        if (isBound) {
            try {
                context.applicationContext.unbindService(serviceConnection)
            } catch (e: Exception) {
                Log.e(TAG, "Error unbinding service", e)
            }
            isBound = false
        }
    }

    fun play() = musicService?.play()
    fun pause() = musicService?.pause()
    fun togglePlayPause() = musicService?.togglePlayPause()
    fun next() = musicService?.next()
    fun previous() = musicService?.previous()
    fun seekTo(posMs: Int) = musicService?.seekTo(posMs)
    fun setVolume(vol: Float) = musicService?.setVolume(vol)
    fun toggleShuffle() = musicService?.toggleShuffle()
    fun toggleRepeat() = musicService?.toggleRepeat()
    fun playTrack(index: Int) = musicService?.playTrack(index)
    fun removeTrack(trackId: String) = musicService?.removeTrack(trackId)

    fun addTrack(track: Track) {
        musicService?.addTrack(track)
    }

    /**
     * Uploads an MP3 file selected by the user, saves it permanently to internal storage,
     * and adds it directly to the active playlist and starts playing.
     */
    fun uploadAndAddAudio(
        context: Context,
        uri: Uri,
        customTitle: String? = null,
        onComplete: (Track?) -> Unit = {}
    ) {
        viewModelScope.launch {
            _isUploading.value = true
            try {
                val savedTrack = MusicStorageHelper.saveUploadedTrack(
                    context = context.applicationContext,
                    sourceUri = uri,
                    customTitle = customTitle
                )

                if (savedTrack != null) {
                    musicService?.addTrack(savedTrack)
                    // Auto-play the newly added track
                    val currentList = _playlist.value
                    val index = currentList.indexOfFirst { it.id == savedTrack.id }
                    if (index >= 0) {
                        musicService?.playTrack(index)
                    }
                }
                withContext(Dispatchers.Main) {
                    onComplete(savedTrack)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error saving uploaded track", e)
                withContext(Dispatchers.Main) {
                    onComplete(null)
                }
            } finally {
                _isUploading.value = false
            }
        }
    }

    fun getCurrentPosition(): Int = musicService?.getCurrentPosition() ?: _currentPositionMs.value
    fun getDuration(): Int = musicService?.getDuration() ?: _durationMs.value
}
