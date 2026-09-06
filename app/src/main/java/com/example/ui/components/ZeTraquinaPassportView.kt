package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.MainViewModel

@Composable
fun ZeTraquinaPassportView(
    viewModel: MainViewModel,
    onNavigateToCustomizer: () -> Unit,
    modifier: Modifier = Modifier
) {
    val progress by viewModel.userProgress.collectAsState()

    val avatarName = progress.customCharName.ifBlank { "Zé Traquina" }
    val skinTone = progress.avatarSkinTone.ifBlank { "skin_fair" }
    val hairStyle = progress.avatarHairStyle.ifBlank { "hair_short" }
    val hairColor = if (progress.avatarHairColor != 0L) progress.avatarHairColor else 0xFF4E342E
    val clothingStyle = if (progress.avatarClothingStyle.isNotBlank() && progress.avatarClothingStyle != "tshirt_star") progress.avatarClothingStyle else "polo_green_vintage"
    val clothingColor = if (progress.avatarClothingColor != 0L && progress.avatarClothingColor != 0xFF0288D1) progress.avatarClothingColor else 0xFF2E7D32
    val accessory = if (progress.avatarAccessory.isNotBlank() && progress.avatarAccessory != "cap_ze") progress.avatarAccessory else "cap_vintage_beret"
    val expression = if (progress.avatarExpression.isNotBlank() && progress.avatarExpression != "smile") progress.avatarExpression else "smile_freckles"
    val background = if (progress.avatarBackground.isNotBlank() && progress.avatarBackground != "room_ze") progress.avatarBackground else "street_calcada"

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 6.dp)
            .testTag("ze_passport_card"),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(2.dp, Color(0xFFF59E0B)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFFFFFBEB),
                            Color(0xFFFFFFFF),
                            Color(0xFFFEF3C7)
                        )
                    )
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Passport Title Banner
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "🌟", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text(
                            text = "Passaporte Oficial Zé",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFF78350F)
                        )
                        Text(
                            text = "Universo Zé Traquina",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFB45309)
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFFDE047),
                    border = BorderStroke(1.dp, Color(0xFFCA8A04))
                ) {
                    Text(
                        text = "EXPLORADOR 🚀",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFF713F12),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Avatar Preview
            ZeTraquinaAvatarView(
                skinTone = skinTone,
                hairStyle = hairStyle,
                hairColor = hairColor,
                clothingStyle = clothingStyle,
                clothingColor = clothingColor,
                accessory = accessory,
                expression = expression,
                background = background,
                size = 170.dp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Name & Level
            Text(
                text = avatarName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF1E293B)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Stats Badges
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFFEFF6FF),
                    border = BorderStroke(1.dp, Color(0xFFBFDBFE))
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "⭐ ${progress.starsCount}", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color(0xFF1E40AF))
                        Text(text = "Estrelas", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))
                    }
                }

                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFFECFDF5),
                    border = BorderStroke(1.dp, Color(0xFFA7F3D0))
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "🔥 ${progress.streakDays} Dias", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color(0xFF065F46))
                        Text(text = "Sequência", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))
                    }
                }

                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFFFEF2F2),
                    border = BorderStroke(1.dp, Color(0xFFFECACA))
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "🎮 ${progress.totalGamesPlayed}", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color(0xFF991B1B))
                        Text(text = "Jogos", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action Buttons: Edit Avatar & Speak with Puck Voice
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        viewModel.playClickSound()
                        val msg = "Olá! Eu sou o $avatarName! Este é o meu passaporte no Universo Zé Traquina! Tenho ${progress.starsCount} estrelas!"
                        viewModel.speak(msg)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4338CA)),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                ) {
                    Icon(imageVector = Icons.Default.SpatialAudio, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Falar 🎙️", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = {
                        viewModel.playClickSound()
                        onNavigateToCustomizer()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B)),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Personalizar 🎨", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
