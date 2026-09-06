package com.example.ui.screens

import androidx.compose.runtime.Composable
import com.example.viewmodel.MainViewModel

@Composable
fun BubbleTraquinaClassicGameScreen(
    mainViewModel: MainViewModel? = null,
    onBack: () -> Unit = {}
) {
    BubbleShooterScreen(
        mainViewModel = mainViewModel,
        onBack = onBack
    )
}
