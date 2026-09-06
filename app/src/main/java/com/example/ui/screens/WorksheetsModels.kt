package com.example.ui.screens

import androidx.compose.ui.graphics.Color

enum class ExerciseType {
    // Old enum values for backwards compatibility (e.g. with WorksheetPrintManager.kt)
    MULTIPLE_CHOICE,     // Escolha múltipla
    TRUE_FALSE,          // Verdadeiro/Falso
    FILL_IN_BLANKS,      // Preencher espaços
    MATCHING,            // Associar/Liga correspondente
    ORDERING,            // Ordenar crescente/decrescente
    NUMERICAL_RESPONSE,  // Resposta numérica
    TEXT_RESPONSE,       // Resposta de texto
    ASSOCIATION,         // Associação
    DRAG_AND_DROP,       // Arrastar e soltar
    DRAWING,             // Desenho

    // New specific enum values for the streamlined interactive system
    CHOOSE_IMAGE,        // Escolher imagem (mostrar várias opções de imagens/emojis)
    COUNTING,            // Contagem (mostrar itens e pedir para contar)
    COMPLETING,          // Completar (ex: "1, 2, __, 4, 5")
    LETTERS_WORDS        // Letras e palavras (ex: "Qual é a primeira letra de...")
}

enum class WorksheetDifficulty {
    EASY,
    MEDIUM,
    CHALLENGE
}

data class WorksheetExercise(
    val id: String,
    val type: ExerciseType,
    val question: String,
    val options: List<String> = emptyList(), // Opções de texto ou emojis
    val correctAnswer: String = "",         // Resposta correta principal
    val correctAnswers: List<String> = emptyList(), // Para ordenação ou respostas múltiplas
    val pairs: Map<String, String> = emptyMap(), // Para associação: esquerda -> direita
    val image: String? = null,              // Ilustração opcional
    val countItems: String? = null,         // Emojis a serem exibidos na contagem (ex: "🍎🍎🍎🍎")
    val explanation: String? = null,        // Explicação simples para "Ver Solução"
    val hint: String? = null
)

data class Worksheet(
    val id: String,
    val numero: Int,
    val title: String,
    val description: String,
    val difficulty: WorksheetDifficulty = WorksheetDifficulty.EASY,
    val exercises: List<WorksheetExercise>,
    val imageEmoji: String = "📝" // Ilustração/emoji pequena para o cartão
)

data class WorksheetDiscipline(
    val id: String,
    val title: String,
    val emoji: String,
    val color: Color,
    val worksheets: List<Worksheet> = emptyList() // Diretamente associadas à disciplina
)

data class WorksheetYear(
    val id: Int,
    val title: String,
    val disciplines: List<WorksheetDiscipline>
)
