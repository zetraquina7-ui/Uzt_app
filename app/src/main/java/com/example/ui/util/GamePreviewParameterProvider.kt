package com.example.ui.util

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

data class GamePreviewMockData(
    val gameId: String = "game_1",
    val title: String = "Jogo do Zé Traquina",
    val level: Int = 1,
    val score: Int = 100,
    val stars: Int = 5,
    val isFinished: Boolean = false,
    val playerName: String = "Zé Traquina",
    val statusMessage: String = "Jogo Pronto!"
)

class GamePreviewParameterProvider : PreviewParameterProvider<GamePreviewMockData> {
    override val values: Sequence<GamePreviewMockData> = sequenceOf(
        GamePreviewMockData(
            gameId = "galo",
            title = "Jogo do Galo",
            level = 1,
            score = 50,
            stars = 3,
            statusMessage = "Turno do Jogador X"
        ),
        GamePreviewMockData(
            gameId = "sopa",
            title = "Sopa de Letras",
            level = 2,
            score = 120,
            stars = 5,
            statusMessage = "Encontra a palavra GATO"
        )
    )
}
