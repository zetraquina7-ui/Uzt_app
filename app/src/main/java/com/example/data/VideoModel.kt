package com.example.data

import androidx.annotation.Keep

@Keep
data class CanalRecomendado(
    val nomeCanal: String,
    val channelId: String,
    val categoria: String // "Músicas", "Educativo", "Diversão"
)

val canaisRecomendados = listOf(
    CanalRecomendado("Panda e os Caricas", "UCrRCYfAdzBO3ViaOseB9dJw", "Músicas"),
    CanalRecomendado("Xana Toc Toc", "UCF8C_W8v0_n6VdCLjmHsOSA", "Músicas"),
    CanalRecomendado("Rádio Recreio", "UCRLLJsy8MHv-dIa7J36jZnQ", "Músicas"),
    CanalRecomendado("RTP Zig Zag", "UCdFnXETzOn-BQT_ZtQE1nTw", "Educativo")
)

@Keep
data class VideoModel(
    val id: String,
    val titulo: String,
    val nomeCanal: String = "Cantinho PT",
    val urlThumbnail: String,
    val categoria: String
)
