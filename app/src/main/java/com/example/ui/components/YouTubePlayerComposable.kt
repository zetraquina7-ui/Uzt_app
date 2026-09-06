package com.example.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * YouTubePlayerComposable delegates directly to the robust VideoPlayer implementation
 * ensuring 100% in-app playback with the YouTube IFrame Player API.
 */
@Composable
fun YouTubePlayerComposable(
    youtubeId: String? = null,
    playlistId: String? = null,
    videoUrl: String? = null,
    modifier: Modifier = Modifier
) {
    VideoPlayer(
        youtubeId = youtubeId,
        playlistId = playlistId,
        videoUrl = videoUrl,
        modifier = modifier.fillMaxSize()
    )
}
