package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.ui.components.AdvancedMusicPlayerComposable
import com.example.ui.components.FundoApp
import com.example.ui.components.ScreenHeader
import com.example.viewmodel.MediaViewModel

@Composable
fun MusicScreen(
    mediaViewModel: MediaViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Request POST_NOTIFICATIONS permission for Android 13+ so background playback media notification shows
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { _ -> }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissionCheck = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            )
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    val musicLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { audioUri ->
            Toast.makeText(context, "A processar ficheiro de áudio...", Toast.LENGTH_SHORT).show()
            mediaViewModel.uploadAndAddAudio(context, audioUri) { savedTrack ->
                if (savedTrack != null) {
                    Toast.makeText(context, "Música adicionada: ${savedTrack.title} 🎵", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Erro ao carregar o ficheiro de áudio.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        mediaViewModel.bindService(context)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .verticalScroll(rememberScrollState())
            .padding(start = 14.dp, end = 14.dp, top = 8.dp, bottom = 16.dp)
            .testTag("music_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
            ScreenHeader(
                title = "Músicas e Cantigas",
                subtitle = "Ouve em direto e em segundo plano",
                icon = "🎵",
                gradientColors = listOf(Color(0xFF9C27B0), Color(0xFF7B1FA2))
            )

            AdvancedMusicPlayerComposable(
                mediaViewModel = mediaViewModel,
                onAddTrack = {
                    musicLauncher.launch("audio/*")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
}
