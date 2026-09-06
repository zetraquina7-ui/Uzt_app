package com.example.data

data class YouTubeVideoItem(
    val id: String,
    val title: String,
    val videoId: String,
    val thumbnailUrl: String,
    val publishedAt: String = "",
    val duration: String = "",
    val isShort: Boolean = false,
    val channelTitle: String = "Zé Traquina",
    val emoji: String = "🎬"
)
