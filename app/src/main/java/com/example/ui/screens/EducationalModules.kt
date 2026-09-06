package com.example.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.viewmodel.MainViewModel

/**
 * Componente do módulo educativo: Animais do Zé Traquina
 */
@Composable
fun AnimaisScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val topicData = LearningTopicRepository.getTopicById("animais")
        ?: LearningTopicRepository.allTopics.first { it.id == "animais" }
    EducationalTopicScreen(
        topicData = topicData,
        viewModel = viewModel,
        onBack = onBack,
        modifier = modifier
    )
}

/**
 * Componente do módulo educativo: Sistema Solar do Zé Traquina
 */
@Composable
fun SistemaSolarScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val topicData = LearningTopicRepository.getTopicById("sistema_solar")
        ?: LearningTopicRepository.allTopics.first { it.id == "sistema_solar" }
    EducationalTopicScreen(
        topicData = topicData,
        viewModel = viewModel,
        onBack = onBack,
        modifier = modifier
    )
}

/**
 * Componente do módulo educativo: Corpo Humano do Zé Traquina
 */
@Composable
fun CorpoHumanoScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val topicData = LearningTopicRepository.getTopicById("corpo_humano")
        ?: LearningTopicRepository.allTopics.first { it.id == "corpo_humano" }
    EducationalTopicScreen(
        topicData = topicData,
        viewModel = viewModel,
        onBack = onBack,
        modifier = modifier
    )
}

/**
 * Componente do módulo educativo: Matemática do Zé Traquina
 */
@Composable
fun MatematicaScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val topicData = LearningTopicRepository.getTopicById("matematica")
        ?: LearningTopicRepository.allTopics.first { it.id == "matematica" }
    EducationalTopicScreen(
        topicData = topicData,
        viewModel = viewModel,
        onBack = onBack,
        modifier = modifier
    )
}

/**
 * Componente do módulo educativo: Portugal do Zé Traquina
 */
@Composable
fun PortugalScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val topicData = LearningTopicRepository.getTopicById("portugal")
        ?: LearningTopicRepository.allTopics.first { it.id == "portugal" }
    EducationalTopicScreen(
        topicData = topicData,
        viewModel = viewModel,
        onBack = onBack,
        modifier = modifier
    )
}
