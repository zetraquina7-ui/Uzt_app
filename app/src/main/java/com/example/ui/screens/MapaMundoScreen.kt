package com.example.ui.screens

import androidx.compose.runtime.Composable
import com.example.viewmodel.MainViewModel

/**
 * Wrapper for Mapa Mundo / Atlas Screen
 */
@Composable
fun MapaMundoScreen(
    viewModel: MainViewModel,
    onBack: () -> Unit
) {
    WorldMapScreen(viewModel = viewModel, onBack = onBack)
}
