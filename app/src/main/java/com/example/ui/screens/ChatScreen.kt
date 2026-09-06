package com.example.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viewmodel.ChatViewModel
import com.example.viewmodel.MainViewModel

@androidx.media3.common.util.UnstableApi
@Composable
fun ChatScreen(
    mainViewModel: MainViewModel,
    chatViewModel: ChatViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    ZeAIScreen(
        mainViewModel = mainViewModel,
        chatViewModel = chatViewModel,
        modifier = modifier
    )
}
