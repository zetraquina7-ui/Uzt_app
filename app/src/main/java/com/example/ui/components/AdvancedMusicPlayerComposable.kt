package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode as AnimRepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeDown
import androidx.compose.material.icons.automirrored.filled.VolumeMute
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.MusicOff
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.RepeatOne
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.service.RepeatMode
import com.example.service.Track
import com.example.ui.theme.KidStarOrange
import com.example.ui.theme.SkyBluePrimary
import com.example.ui.theme.SunshineYellow
import com.example.viewmodel.MediaViewModel

@Composable
fun AdvancedMusicPlayerComposable(
    mediaViewModel: MediaViewModel,
    onAddTrack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isPlaying by mediaViewModel.musicIsPlaying.collectAsState()
    val currentTrack by mediaViewModel.currentMusicTrack.collectAsState()
    val playlist by mediaViewModel.playlist.collectAsState()
    val currentPositionMs by mediaViewModel.currentPositionMs.collectAsState()
    val durationMs by mediaViewModel.durationMs.collectAsState()
    val volume by mediaViewModel.volume.collectAsState()
    val isShuffle by mediaViewModel.isShuffle.collectAsState()
    val repeatMode by mediaViewModel.repeatMode.collectAsState()
    val isUploading by mediaViewModel.isUploading.collectAsState()

    // Smooth seeking state
    var isDraggingSlider by remember { mutableStateOf(false) }
    var dragSliderPosition by remember { mutableFloatStateOf(0f) }

    // Vinyl spinning animation when music is actively playing
    val infiniteTransition = rememberInfiniteTransition(label = "vinyl_rotation")
    val vinylAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 6000, easing = LinearEasing),
            repeatMode = AnimRepeatMode.Restart
        ),
        label = "vinyl_angle"
    )

    val currentRotation = if (isPlaying) vinylAngle else 0f

    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(10.dp, RoundedCornerShape(24.dp))
            .testTag("advanced_music_player_card"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.5.dp, Color(0xFFBAE6FD))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF0F9FF),
                            Color(0xFFE0F2FE),
                            Color.White
                        )
                    )
                )
                .padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- TOP STATUS BAR: Background Playback Indicator ---
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (isPlaying) Color(0xFFDCFCE7) else Color(0xFFF1F5F9),
                border = BorderStroke(1.dp, if (isPlaying) Color(0xFF22C55E) else Color(0xFFCBD5E1)),
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = if (isPlaying) "🟢 Reprodução Ativa em 2º Plano" else "⏸️ Leitor de Música Pronto",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isPlaying) Color(0xFF15803D) else Color(0xFF64748B)
                    )
                }
            }

            // --- ALBUM ART: Vinyl Record with Mascot Center ---
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(140.dp)
                    .shadow(12.dp, CircleShape)
            ) {
                // Vinyl Disc
                Surface(
                    shape = CircleShape,
                    color = Color(0xFF1E293B),
                    border = BorderStroke(4.dp, Color(0xFF0F172A)),
                    modifier = Modifier
                        .fillMaxSize()
                        .rotate(currentRotation)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        // Vinyl Grooves
                        Box(
                            modifier = Modifier
                                .size(110.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF334155))
                                .border(1.5.dp, Color(0xFF475569), CircleShape)
                        )
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF1E293B))
                                .border(1.dp, Color(0xFF64748B), CircleShape)
                        )

                        // Center Mascot / Music Note Circle
                        Surface(
                            shape = CircleShape,
                            color = SkyBluePrimary,
                            border = BorderStroke(3.dp, SunshineYellow),
                            modifier = Modifier.size(54.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (isPlaying) Icons.Default.GraphicEq else Icons.Default.MusicNote,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- TRACK TITLE & ARTIST ---
            Text(
                text = currentTrack?.title ?: "Cantinho PT É Fixe",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF0F172A),
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (currentTrack?.isCustom == true) Color(0xFFFEF3C7) else Color(0xFFE0F2FE),
                    border = BorderStroke(1.dp, if (currentTrack?.isCustom == true) SunshineYellow else SkyBluePrimary)
                ) {
                    Text(
                        text = if (currentTrack?.isCustom == true) "📁 MP3 do Utilizador" else "👦🏻 Cantiga do Zé Traquina",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (currentTrack?.isCustom == true) Color(0xFFB45309) else Color(0xFF0369A1),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // --- SEEKABLE PROGRESS BAR ---
            val activeProgress = if (isDraggingSlider) {
                dragSliderPosition
            } else {
                currentPositionMs.toFloat()
            }
            val maxProgress = durationMs.toFloat().coerceAtLeast(1000f)

            Slider(
                value = activeProgress.coerceIn(0f, maxProgress),
                onValueChange = {
                    isDraggingSlider = true
                    dragSliderPosition = it
                },
                onValueChangeFinished = {
                    isDraggingSlider = false
                    mediaViewModel.seekTo(dragSliderPosition.toInt())
                },
                valueRange = 0f..maxProgress,
                colors = SliderDefaults.colors(
                    thumbColor = KidStarOrange,
                    activeTrackColor = SkyBluePrimary,
                    inactiveTrackColor = SkyBluePrimary.copy(alpha = 0.25f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("music_progress_slider")
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = formatTime(if (isDraggingSlider) dragSliderPosition.toInt() else currentPositionMs),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF64748B)
                )
                Text(
                    text = formatTime(durationMs),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF64748B)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // --- MAIN PLAYBACK CONTROLS (Shuffle, Prev, Play/Pause, Next, Repeat) ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Shuffle Button
                IconButton(
                    onClick = { mediaViewModel.toggleShuffle() },
                    modifier = Modifier.testTag("btn_music_shuffle")
                ) {
                    Surface(
                        shape = CircleShape,
                        color = if (isShuffle) KidStarOrange.copy(alpha = 0.2f) else Color.Transparent,
                        modifier = Modifier.size(38.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Shuffle,
                                contentDescription = "Modo Aleatório",
                                tint = if (isShuffle) KidStarOrange else Color(0xFF94A3B8),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }

                // Previous Button
                IconButton(
                    onClick = { mediaViewModel.previous() },
                    modifier = Modifier
                        .size(48.dp)
                        .testTag("btn_music_previous")
                ) {
                    Icon(
                        imageVector = Icons.Default.SkipPrevious,
                        contentDescription = "Música Anterior",
                        tint = SkyBluePrimary,
                        modifier = Modifier.size(36.dp)
                    )
                }

                // Giant Colorful Play / Pause Button
                Surface(
                    shape = CircleShape,
                    color = if (isPlaying) SunshineYellow else SkyBluePrimary,
                    shadowElevation = 8.dp,
                    border = BorderStroke(3.dp, Color.White),
                    modifier = Modifier
                        .size(68.dp)
                        .clip(CircleShape)
                        .clickable { mediaViewModel.togglePlayPause() }
                        .testTag("btn_music_play_pause")
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isPlaying) "Pausar Música" else "Tocar Música",
                            tint = if (isPlaying) Color(0xFF0F172A) else Color.White,
                            modifier = Modifier.size(38.dp)
                        )
                    }
                }

                // Next Button
                IconButton(
                    onClick = { mediaViewModel.next() },
                    modifier = Modifier
                        .size(48.dp)
                        .testTag("btn_music_next")
                ) {
                    Icon(
                        imageVector = Icons.Default.SkipNext,
                        contentDescription = "Próxima Música",
                        tint = SkyBluePrimary,
                        modifier = Modifier.size(36.dp)
                    )
                }

                // Repeat Mode Button (Off -> Repeat All -> Repeat One)
                IconButton(
                    onClick = { mediaViewModel.toggleRepeat() },
                    modifier = Modifier.testTag("btn_music_repeat")
                ) {
                    Surface(
                        shape = CircleShape,
                        color = if (repeatMode != RepeatMode.OFF) SkyBluePrimary.copy(alpha = 0.2f) else Color.Transparent,
                        modifier = Modifier.size(38.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = if (repeatMode == RepeatMode.REPEAT_ONE) Icons.Default.RepeatOne else Icons.Default.Repeat,
                                contentDescription = "Modo de Repetição",
                                tint = if (repeatMode != RepeatMode.OFF) SkyBluePrimary else Color(0xFF94A3B8),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- VOLUME SLIDER WITH MUTE / MAX TOGGLES ---
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                IconButton(
                    onClick = { mediaViewModel.setVolume(if (volume > 0f) 0f else 0.8f) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (volume == 0f) Icons.AutoMirrored.Filled.VolumeMute else Icons.AutoMirrored.Filled.VolumeDown,
                        contentDescription = "Mudo / Volume",
                        tint = if (volume == 0f) Color(0xFFEF4444) else Color(0xFF64748B),
                        modifier = Modifier.size(20.dp)
                    )
                }

                Slider(
                    value = volume,
                    onValueChange = { mediaViewModel.setVolume(it) },
                    valueRange = 0f..1f,
                    colors = SliderDefaults.colors(
                        thumbColor = SkyBluePrimary,
                        activeTrackColor = SkyBluePrimary,
                        inactiveTrackColor = Color(0xFFE2E8F0)
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .testTag("music_volume_slider")
                )

                IconButton(
                    onClick = { mediaViewModel.setVolume(1.0f) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = "Volume Máximo",
                        tint = SkyBluePrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Text(
                    text = "${(volume * 100).toInt()}%",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF64748B),
                    modifier = Modifier.width(36.dp),
                    textAlign = TextAlign.End
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // --- UPLOAD MP3 BUTTON ---
            Button(
                onClick = onAddTrack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("btn_upload_mp3"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SkyBluePrimary),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                if (isUploading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("A carregar áudio MP3...", fontWeight = FontWeight.Bold)
                } else {
                    Icon(imageVector = Icons.Default.FileUpload, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Carregar Ficheiro MP3 do Dispositivo", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- PLAYLIST / TRACK LIST SECTION ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🎶 Lista de Músicas (${playlist.size})",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF1E293B)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                playlist.forEachIndexed { index, track ->
                    val isCurrent = currentTrack?.id == track.id

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { mediaViewModel.playTrack(index) }
                            .testTag("track_item_${track.id}"),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isCurrent) Color(0xFFE0F2FE) else Color(0xFFF8FAFC)
                        ),
                        border = BorderStroke(
                            width = if (isCurrent) 1.8.dp else 1.dp,
                            color = if (isCurrent) SkyBluePrimary else Color(0xFFE2E8F0)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = if (isCurrent) 3.dp else 0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = if (isCurrent) SkyBluePrimary else Color(0xFFCBD5E1),
                                    modifier = Modifier.size(34.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = if (isCurrent && isPlaying) Icons.Default.GraphicEq else Icons.Default.MusicNote,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = track.title,
                                        fontSize = 13.sp,
                                        fontWeight = if (isCurrent) FontWeight.ExtraBold else FontWeight.Bold,
                                        color = if (isCurrent) Color(0xFF0369A1) else Color(0xFF1E293B),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = if (track.isCustom) "Ficheiro MP3 Carregado" else track.artist,
                                        fontSize = 11.sp,
                                        color = Color(0xFF64748B)
                                    )
                                }
                            }

                            // Delete button for custom uploaded tracks
                            if (track.isCustom) {
                                IconButton(
                                    onClick = { mediaViewModel.removeTrack(track.id) },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.DeleteOutline,
                                        contentDescription = "Remover Música",
                                        tint = Color(0xFFEF4444),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun formatTime(ms: Int): String {
    val totalSeconds = (ms / 1000).coerceAtLeast(0)
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}
