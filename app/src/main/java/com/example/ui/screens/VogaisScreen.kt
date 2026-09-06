package com.example.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.viewmodel.MainViewModel

/**
 * 🔠 Vogais Screen - Alinhado a 100% com o modelo funcional do ecrã Números
 * Utiliza o EducationalTopicScreen com a barra de sub-menus (Início, Vogais, Categorias, Quiz),
 * sem fundos brancos sobrantes e com a imagem do Início a ocupar a totalidade do ecrã vertical.
 */
@Composable
fun VogaisScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val topicData = LearningTopicRepository.getTopicById("vogais")
        ?: LearningTopicRepository.allTopics.first { it.id == "vogais" }

    EducationalTopicScreen(
        topicData = topicData,
        viewModel = viewModel,
        onBack = onBack,
        modifier = modifier
    )
}
