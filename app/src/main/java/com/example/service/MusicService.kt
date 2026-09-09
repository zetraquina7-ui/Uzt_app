package com.example.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Binder
import android.util.Log
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import com.example.MainActivity
import com.example.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class RepeatMode {
    OFF,
    REPEAT_ALL,
    REPEAT_ONE
}

data class Track(
    val id: String,
    val title: String,
    val artist: String = "Cantinho PT",
    val uri: Uri? = null,
    val rawResId: Int? = null,
    val isCustom: Boolean = false
)

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
class MusicService : Service() {

    private var exoPlayer: ExoPlayer? = null
    private var mediaSession: MediaSessionCompat? = null
    private val binder = MusicBinder()

    private val _currentTrack = MutableStateFlow<Track?>(null)
    val currentTrack = _currentTrack.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val _playlist = MutableStateFlow<List<Track>>(emptyList())
    val playlist = _playlist.asStateFlow()

    private val _currentPositionMs = MutableStateFlow(0)
    val currentPositionMs = _currentPositionMs.asStateFlow()

    private val _durationMs = MutableStateFlow(1)
    val durationMs = _durationMs.asStateFlow()

    private val _volume = MutableStateFlow(1.0f)
    val volume = _volume.asStateFlow()

    private val _isShuffle = MutableStateFlow(false)
    val isShuffle = _isShuffle.asStateFlow()

    private val failedTracks = mutableSetOf<String>()

    private val _repeatMode = MutableStateFlow(RepeatMode.REPEAT_ALL)
    val repeatMode = _repeatMode.asStateFlow()

    private var currentTrackIndex = -1
    private val NOTIFICATION_ID = 101
    private val CHANNEL_ID = "music_channel"

    private val progressHandler = Handler(Looper.getMainLooper())
    private val progressRunnable = object : Runnable {
        override fun run() {
            exoPlayer?.let { player ->
                if (player.isPlaying) {
                    _currentPositionMs.value = player.currentPosition.toInt().coerceAtLeast(0)
                    val dur = player.duration.toInt()
                    if (dur > 0) {
                        _durationMs.value = dur
                    }
                }
            }
            progressHandler.postDelayed(this, 300L)
        }
    }

    inner class MusicBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        mediaSession = MediaSessionCompat(this, "MusicService")

        // Initial playlist with default track and any persisted user uploaded MP3 tracks
        val defaultTrack = Track("default", "Cantinho PT É Fixe", rawResId = R.raw.ze_traquina_e_fixe)
        val customTracks = com.example.util.MusicStorageHelper.loadPersistedCustomTracks(this)
        val fullList = listOf(defaultTrack) + customTracks
        _playlist.value = fullList
        _currentTrack.value = defaultTrack
        currentTrackIndex = 0

        startForegroundSafely(defaultTrack, false)
        progressHandler.post(progressRunnable)
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val currentTrackOrFallback = _currentTrack.value ?: _playlist.value.firstOrNull() ?: Track("default", "Cantinho PT É Fixe")
        startForegroundSafely(currentTrackOrFallback, _isPlaying.value)

        when (intent?.action) {
            ACTION_PLAY -> play()
            ACTION_PAUSE -> pause()
            ACTION_NEXT -> next()
            ACTION_PREVIOUS -> previous()
            ACTION_STOP -> stop()
        }
        return START_NOT_STICKY
    }

    private fun startForegroundSafely(track: Track, isPlaying: Boolean) {
        try {
            val notification = getNotification(track, isPlaying)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Ensure context is valid before starting foreground
                if (Looper.myLooper() != Looper.getMainLooper()) {
                    Handler(Looper.getMainLooper()).post {
                        startForeground(
                            NOTIFICATION_ID,
                            notification,
                            android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
                        )
                    }
                } else {
                    startForeground(
                        NOTIFICATION_ID,
                        notification,
                        android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
                    )
                }
            } else {
                startForeground(NOTIFICATION_ID, notification)
            }
        } catch (e: Throwable) {
            android.util.Log.e("MusicService", "CRITICAL ERROR in startForegroundSafely: ${e.message}", e)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Música do Cantinho PT",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun getNotification(track: Track, isPlaying: Boolean): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            flags
        )

        val playPauseAction = if (isPlaying) {
            NotificationCompat.Action(
                android.R.drawable.ic_media_pause, "Pausar",
                getServiceIntent(ACTION_PAUSE)
            )
        } else {
            NotificationCompat.Action(
                android.R.drawable.ic_media_play, "Tocar",
                getServiceIntent(ACTION_PLAY)
            )
        }

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(track.title)
            .setContentText(track.artist)
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentIntent(pendingIntent)
            .addAction(NotificationCompat.Action(android.R.drawable.ic_media_previous, "Anterior", getServiceIntent(ACTION_PREVIOUS)))
            .addAction(playPauseAction)
            .addAction(NotificationCompat.Action(android.R.drawable.ic_media_next, "Próximo", getServiceIntent(ACTION_NEXT)))
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                .setMediaSession(mediaSession?.sessionToken)
                .setShowActionsInCompactView(0, 1, 2))
            .build()
    }

    private fun getServiceIntent(action: String): PendingIntent {
        val intent = Intent(this, MusicService::class.java).apply { this.action = action }
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        return PendingIntent.getService(this, 0, intent, flags)
    }

    fun setPlaylist(tracks: List<Track>) {
        _playlist.value = tracks
        currentTrackIndex = if (tracks.isNotEmpty()) 0 else -1
        if (currentTrackIndex != -1) {
            loadTrack(tracks[currentTrackIndex])
        }
    }

    fun addTrack(track: Track) {
        val updated = _playlist.value.toMutableList()
        updated.add(track)
        _playlist.value = updated
        if (currentTrackIndex == -1) {
            currentTrackIndex = 0
            loadTrack(track)
        }
    }

    fun removeTrack(trackId: String) {
        val list = _playlist.value.toMutableList()
        val removeIndex = list.indexOfFirst { it.id == trackId }
        if (removeIndex != -1) {
            val wasPlayingThis = (removeIndex == currentTrackIndex)
            list.removeAt(removeIndex)
            _playlist.value = list
            com.example.util.MusicStorageHelper.deleteCustomTrack(this, trackId)
            if (list.isEmpty()) {
                currentTrackIndex = -1
                stop()
            } else if (wasPlayingThis) {
                currentTrackIndex = removeIndex.coerceAtMost(list.size - 1)
                loadTrack(list[currentTrackIndex])
                play()
            } else if (removeIndex < currentTrackIndex) {
                currentTrackIndex--
            }
        }
    }

    fun playTrack(index: Int) {
        val list = _playlist.value
        if (index in list.indices) {
            currentTrackIndex = index
            loadTrack(list[index])
            play()
        }
    }

    private fun getOrCreatePlayer(): ExoPlayer {
        exoPlayer?.let { return it }
        val renderersFactory = com.example.util.ExoPlayerHelper.createRenderersFactory(
            this,
            DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF,
            isAudioOnly = true
        )
        val player = ExoPlayer.Builder(this, renderersFactory)
            .setMediaSourceFactory(com.example.util.ExoPlayerHelper.createMediaSourceFactory(this))
            .setLoadControl(com.example.util.ExoPlayerHelper.createLoadControl())
            .build()

        player.addListener(object : Player.Listener {
            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                val index = player.currentMediaItemIndex
                if (index in _playlist.value.indices) {
                    currentTrackIndex = index
                    val track = _playlist.value[index]
                    _currentTrack.value = track
                    startForegroundSafely(track, _isPlaying.value)
                }
            }

            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    when (_repeatMode.value) {
                        RepeatMode.REPEAT_ONE -> {
                            player.seekTo(0)
                            player.play()
                        }
                        RepeatMode.REPEAT_ALL -> {
                            if (player.currentMediaItemIndex >= _playlist.value.size - 1) {
                                player.seekTo(0, 0L)
                                player.play()
                            }
                        }
                        RepeatMode.OFF -> {
                            if (player.currentMediaItemIndex >= _playlist.value.size - 1) {
                                pause()
                                player.seekTo(0, 0L)
                            }
                        }
                    }
                }
            }

            override fun onIsPlayingChanged(playing: Boolean) {
                _isPlaying.value = playing
            }

            override fun onPlayerError(error: androidx.media3.common.PlaybackException) {
                val track = _currentTrack.value
                Log.e("MusicService", "ExoPlayer playback error on track '${track?.title}': ${error.message}", error)
                if (track != null) {
                    com.example.util.MediaFailureTracker.addFailedUrl(track.uri?.toString() ?: "raw:${track.rawResId ?: ""}")
                }
                _isPlaying.value = false
                if (_playlist.value.size > 1 && currentTrackIndex < _playlist.value.size - 1) {
                    player.seekToNextMediaItem()
                    player.play()
                }
            }
        })

        exoPlayer = player
        return player
    }

    private fun loadTrack(track: Track) {
        val player = getOrCreatePlayer()
        val list = _playlist.value
        val startIndex = list.indexOfFirst { it.id == track.id }.takeIf { it != -1 } ?: currentTrackIndex.coerceAtLeast(0)
        currentTrackIndex = startIndex

        val mediaItems = list.mapNotNull { t ->
            val mediaItem = if (t.rawResId != null) {
                try {
                    val rawUri = com.example.util.ExoPlayerHelper.getRawResourceAsFileUri(this, t.rawResId, "track_${t.id}.mp3")
                    MediaItem.Builder()
                        .setUri(rawUri)
                        .setMimeType(androidx.media3.common.MimeTypes.AUDIO_MPEG)
                        .build()
                } catch (_: Throwable) {
                    if (t.uri != null) MediaItem.Builder().setUri(t.uri).build() else null
                }
            } else if (t.uri != null) {
                MediaItem.Builder().setUri(t.uri).build()
            } else null
            mediaItem
        }

        if (mediaItems.isNotEmpty()) {
            player.stop()
            player.clearMediaItems()
            player.setMediaItems(mediaItems, startIndex, 0L)
            player.prepare()
            if (_isPlaying.value) {
                player.play()
            }
        }

        player.volume = _volume.value
        _currentTrack.value = track
    }

    fun play() {
        val list = _playlist.value
        if (exoPlayer == null && list.isNotEmpty() && currentTrackIndex != -1) {
            loadTrack(list[currentTrackIndex])
        }
        exoPlayer?.play()
        _isPlaying.value = true
        _currentTrack.value?.let {
            startForegroundSafely(it, true)
        }
    }

    fun pause() {
        exoPlayer?.pause()
        _isPlaying.value = false
        _currentTrack.value?.let {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(NOTIFICATION_ID, getNotification(it, false))
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_DETACH)
        } else {
            @Suppress("DEPRECATION")
            stopForeground(false)
        }
    }

    fun togglePlayPause() {
        if (_isPlaying.value) pause() else play()
    }

    fun next() {
        val list = _playlist.value
        if (list.isEmpty()) return
        currentTrackIndex = if (_isShuffle.value) {
            (0 until list.size).random()
        } else {
            (currentTrackIndex + 1) % list.size
        }
        loadTrack(list[currentTrackIndex])
        play()
    }

    fun previous() {
        val list = _playlist.value
        if (list.isEmpty()) return
        currentTrackIndex = if (currentTrackIndex <= 0) list.size - 1 else currentTrackIndex - 1
        loadTrack(list[currentTrackIndex])
        play()
    }

    fun toggleShuffle() {
        _isShuffle.value = !_isShuffle.value
    }

    fun toggleRepeat() {
        _repeatMode.value = when (_repeatMode.value) {
            RepeatMode.OFF -> RepeatMode.REPEAT_ALL
            RepeatMode.REPEAT_ALL -> RepeatMode.REPEAT_ONE
            RepeatMode.REPEAT_ONE -> RepeatMode.OFF
        }
    }

    fun stop() {
        exoPlayer?.stop()
        exoPlayer?.release()
        exoPlayer = null
        _isPlaying.value = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_REMOVE)
        } else {
            @Suppress("DEPRECATION")
            stopForeground(true)
        }
        stopSelf()
    }

    fun seekTo(posMs: Int) {
        exoPlayer?.seekTo(posMs.toLong())
        _currentPositionMs.value = posMs
    }

    fun setVolume(vol: Float) {
        val clamped = vol.coerceIn(0f, 1f)
        _volume.value = clamped
        exoPlayer?.volume = clamped
    }

    fun getDuration(): Int = exoPlayer?.duration?.toInt()?.coerceAtLeast(0) ?: 0
    fun getCurrentPosition(): Int = exoPlayer?.currentPosition?.toInt()?.coerceAtLeast(0) ?: 0

    override fun onDestroy() {
        super.onDestroy()
        progressHandler.removeCallbacks(progressRunnable)
        exoPlayer?.stop()
        exoPlayer?.release()
        exoPlayer = null
        mediaSession?.release()
    }

    companion object {
        const val ACTION_PLAY = "com.example.ACTION_PLAY"
        const val ACTION_PAUSE = "com.example.ACTION_PAUSE"
        const val ACTION_NEXT = "com.example.ACTION_NEXT"
        const val ACTION_PREVIOUS = "com.example.ACTION_PREVIOUS"
        const val ACTION_STOP = "com.example.ACTION_STOP"
    }
}
