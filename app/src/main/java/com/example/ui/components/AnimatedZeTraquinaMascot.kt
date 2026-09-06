package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun AnimatedZeTraquinaMascot(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val scale = remember { Animatable(1f) }

    val react = {
        coroutineScope.launch {
            // Animation: Jump and scale reaction
            scale.animateTo(1.2f, spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow))
            scale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow))
        }
        viewModel.playClickSound()
        // Simple reactive response
        val responses = listOf(
            "Olá! Adoro brincar contigo! hihi!",
            "Cuidado com as cócegas!",
            "Vamos aprender coisas novas hoje?",
            "Estou tão feliz por estares aqui!"
        )
        viewModel.speak(responses.random())
    }

    Box(
        modifier = modifier
            .size(300.dp) // Maintain a good size for interaction
            .scale(scale.value)
            .clickable { react() }
            .clip(RoundedCornerShape(32.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_ze_traquina_original),
            contentDescription = "Zé Traquina Animado",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
