package com.example.util

/**
 * Model representing a voice recognition and AI response pair.
 */
data class VoiceResponse(
    val transcription: String,
    val responseText: String,
    val audioUrl: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)
