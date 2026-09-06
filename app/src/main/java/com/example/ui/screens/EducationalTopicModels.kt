package com.example.ui.screens

import androidx.compose.ui.graphics.Color

/**
 * Educational item model for any learning topic.
 */
data class EducationalItem(
    val id: String,
    val name: String,
    val category: String,
    val emoji: String,
    val subtitle: String,
    val fact: String,
    val details: Map<String, String> = emptyMap(),
    val speechText: String = "",
    val syllables: String = "",
    val badge: String = "",
    val colorHex: Long = 0xFF1976D2
)

/**
 * Quiz question for a specific learning topic.
 */
data class EducationalQuizQuestion(
    val id: String,
    val question: String,
    val options: List<String>,
    val correctAnswer: String,
    val explanation: String,
    val emoji: String = "🎯"
)

/**
 * Filter category for a topic.
 */
data class EducationalCategoryFilter(
    val name: String,
    val emoji: String,
    val color: Color
)

/**
 * Complete data definition for an educational learning topic.
 */
data class EducationalTopicData(
    val id: String,
    val title: String,
    val subtitle: String,
    val iconEmoji: String,
    val accentColor: Color,
    val headerGradient: List<Color>,
    val categories: List<EducationalCategoryFilter>,
    val items: List<EducationalItem>,
    val quizQuestions: List<EducationalQuizQuestion>,
    val searchPlaceholder: String = "Pesquisar...",
    val enableSyllables: Boolean = false,
    val customTabName: String = "Explorar"
)
