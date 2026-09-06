package com.example.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("inicio", "Início", Icons.Default.Home)
    object Educar : Screen("educar", "Educar", Icons.Default.School)
    object Learn : Screen("aprender", "Aprender", Icons.Default.School) // Kept for sub-screen
    object Games : Screen("jogos", "Jogos", Icons.Default.SportsEsports) // Kept for sub-screen
    object Stories : Screen("historias", "Histórias", Icons.Default.MenuBook) // New main tab
    object Media : Screen("videos", "Vídeos", Icons.Default.OndemandVideo)
    object ParentalControl : Screen("parental", "Pais", Icons.Default.Security)
    object Chat : Screen("chat", "ZéAI", Icons.Default.Face)
    object Stats : Screen("stats", "Dados", Icons.Default.AutoAwesome)
    object Atualidade : Screen("atualidade", "Atual", Icons.Default.Newspaper)
    object More : Screen("mais", "Mais", Icons.Default.MoreHoriz)
    object Music : Screen("music", "Música", Icons.Default.MusicNote)
}

val mainNavScreens = listOf(
    Screen.Home,
    Screen.Educar,
    Screen.Chat,
    Screen.Media,
    Screen.More
)
