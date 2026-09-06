package com.example.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.viewmodel.MainViewModel

/**
 * 🔤 Abecedário / Alfabeto Screen - Alinhado a 100% com o modelo funcional do ecrã Números
 * Utiliza o EducationalTopicScreen com a barra de sub-menus (Início, ABC, Categorias, Quiz),
 * sem fundos brancos sobrantes e com a imagem do Início a ocupar a totalidade do ecrã vertical.
 */
@Composable
fun AbecedarioScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val topicData = LearningTopicRepository.getTopicById("alfabeto")
        ?: LearningTopicRepository.allTopics.first { it.id == "alfabeto" }

    EducationalTopicScreen(
        topicData = topicData,
        viewModel = viewModel,
        onBack = onBack,
        modifier = modifier
    )
}
