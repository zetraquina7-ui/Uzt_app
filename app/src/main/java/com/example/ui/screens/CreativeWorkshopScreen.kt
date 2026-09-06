package com.example.ui.screens

import com.example.ui.components.FundoApp

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.example.data.UserProgress
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.viewmodel.MainViewModel
import kotlinx.coroutines.delay

enum class WorkshopStep {
    SHAPE,
    COLOR,
    FACE,
    FINISH
}

enum class CharacterShape(val emoji: String) {
    CIRCLE("🔵"),
    SQUARE("🟩"),
    TRIANGLE("🔼"),
    HEART("❤️"),
    STAR("⭐")
}

data class CustomCharacter(
    val name: String = "Novo Amigo",
    val shape: CharacterShape = CharacterShape.CIRCLE,
    val color: Color = SkyBluePrimary,
    val faceEmoji: String = "😊"
)

@Composable
fun CreativeWorkshopScreen(
    mainViewModel: MainViewModel? = null,
    onBack: () -> Unit
) {
    val userProgress by mainViewModel?.userProgress?.collectAsState() ?: remember { mutableStateOf(UserProgress()) }
    
    var currentStep by remember { mutableStateOf(WorkshopStep.SHAPE) }
    
    // Initial state from persisted progress if available
    var character by remember(userProgress) { 
        mutableStateOf(
            CustomCharacter(
                name = userProgress.customCharName,
                shape = try { CharacterShape.valueOf(userProgress.customCharShape) } catch(e: Exception) { CharacterShape.CIRCLE },
                color = Color(userProgress.customCharColor),
                faceEmoji = userProgress.customCharFace
            )
        )
    }
    
    var characterName by remember(userProgress.customCharName) { mutableStateOf(userProgress.customCharName) }
    
    val steps = WorkshopStep.values()
    val progress = (currentStep.ordinal + 1).toFloat() / steps.size.toFloat()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(androidx.compose.ui.graphics.Color.White)
            .testTag("creative_workshop_screen")
    ) {
        // --- Header ---
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = SkyBluePrimary,
            shadowElevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                    Text(
                        text = "Oficina Criativa 🎨✨",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.weight(1f)
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Progress Bar
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(CircleShape),
                    color = SunshineYellow,
                    trackColor = Color.White.copy(alpha = 0.3f),
                )
            }
        }

        // --- Preview Area ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(Color.White, Color(0xFFE2E8F0))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            CharacterPreview(character, currentStep == WorkshopStep.FINISH)
        }

        // --- Step Content ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = when(currentStep) {
                    WorkshopStep.SHAPE -> "1. Escolhe a Forma do teu Amigo! 📐"
                    WorkshopStep.COLOR -> "2. Que cor queres que ele tenha? 🌈"
                    WorkshopStep.FACE -> "3. Escolhe uma carinha divertida! 😄"
                    WorkshopStep.FINISH -> "4. Dá um nome ao teu novo Amigo! ✍️"
                },
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF1E293B),
                modifier = Modifier.padding(vertical = 6.dp)
            )

            Box(modifier = Modifier.weight(1f)) {
                when(currentStep) {
                    WorkshopStep.SHAPE -> ShapeSelection(character) { character = it }
                    WorkshopStep.COLOR -> ColorSelection(character) { character = it }
                    WorkshopStep.FACE -> FaceSelection(character) { character = it }
                    WorkshopStep.FINISH -> FinishStep(characterName) { characterName = it }
                }
            }

            // --- Navigation Buttons ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (currentStep != WorkshopStep.SHAPE) {
                    Button(
                        onClick = { currentStep = steps[currentStep.ordinal - 1] },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Anterior", color = Color.DarkGray, fontWeight = FontWeight.Bold)
                    }
                } else {
                    Spacer(modifier = Modifier.width(1.dp))
                }

                Button(
                    onClick = {
                        if (currentStep == WorkshopStep.FINISH) {
                            val nameToSave = characterName.ifEmpty { "Amiguinho" }
                            mainViewModel?.saveCustomCharacter(
                                name = nameToSave,
                                shape = character.shape.name,
                                color = character.color.toArgb().toLong(),
                                face = character.faceEmoji
                            )
                            mainViewModel?.addStars(20)
                            onBack()
                        } else {
                            currentStep = steps[currentStep.ordinal + 1]
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = if (currentStep == WorkshopStep.FINISH) MintGreen else SkyBluePrimary),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.height(48.dp)
                ) {
                    Text(
                        text = if (currentStep == WorkshopStep.FINISH) "Finalizar! 🚀" else "Próximo ✨",
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun CharacterPreview(character: CustomCharacter, isFinished: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "preview")
    
    val bounce by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = if (isFinished) -20f else -5f,
        animationSpec = infiniteRepeatable(
            animation = tween(if (isFinished) 400 else 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bounce"
    )

    val rotation by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotation"
    )

    Box(
        modifier = Modifier
            .offset(y = bounce.dp)
            .rotate(rotation)
            .size(140.dp)
            .clip(
                when(character.shape) {
                    CharacterShape.CIRCLE -> CircleShape
                    CharacterShape.SQUARE -> RoundedCornerShape(24.dp)
                    else -> RoundedCornerShape(20.dp) // Simplified for now
                }
            )
            .background(character.color)
            .border(
                4.dp, 
                Color.White.copy(alpha = 0.5f),
                when(character.shape) {
                    CharacterShape.CIRCLE -> CircleShape
                    CharacterShape.SQUARE -> RoundedCornerShape(24.dp)
                    else -> RoundedCornerShape(20.dp)
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = character.faceEmoji,
                fontSize = 60.sp
            )
            if (isFinished) {
                Text(
                    text = "✨",
                    fontSize = 20.sp,
                    modifier = Modifier.offset(x = 40.dp, y = (-50).dp)
                )
            }
        }
        
        // Shape specific overlays
        if (character.shape == CharacterShape.STAR) {
            Text("⭐", fontSize = 160.sp, color = character.color.copy(alpha = 0.3f))
        }
    }
}

@Composable
fun ShapeSelection(current: CustomCharacter, onUpdate: (CustomCharacter) -> Unit) {
    val shapes = CharacterShape.values()
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(shapes) { shape ->
            val isSelected = current.shape == shape
            Card(
                onClick = { onUpdate(current.copy(shape = shape)) },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) SkyBluePrimary.copy(alpha = 0.1f) else Color.White
                ),
                border = BorderStroke(if (isSelected) 3.dp else 1.dp, if (isSelected) SkyBluePrimary else Color.LightGray)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = shape.emoji, fontSize = 48.sp)
                }
            }
        }
    }
}

@Composable
fun ColorSelection(current: CustomCharacter, onUpdate: (CustomCharacter) -> Unit) {
    val colors = listOf(
        SkyBluePrimary, Color(0xFFFF4081), SunshineYellow, MintGreen, 
        KidStarOrange, Color(0xFF7E57C2), Color(0xFF00BCD4), Color(0xFFFF5252),
        Color(0xFF4CAF50), Color(0xFF3F51B5)
    )
    
    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(colors) { color ->
            val isSelected = current.color == color
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .background(color)
                    .border(if (isSelected) 4.dp else 0.dp, Color.Black.copy(alpha = 0.3f), CircleShape)
                    .clickable { onUpdate(current.copy(color = color)) }
            )
        }
    }
}

@Composable
fun FaceSelection(current: CustomCharacter, onUpdate: (CustomCharacter) -> Unit) {
    val faces = listOf("😊", "😎", "🤩", "😜", "😇", "😴", "🤔", "😮", "🤠", "🥳")
    
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(faces) { face ->
            val isSelected = current.faceEmoji == face
            Card(
                onClick = { onUpdate(current.copy(faceEmoji = face)) },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) SunshineYellow.copy(alpha = 0.1f) else Color.White
                ),
                border = BorderStroke(if (isSelected) 3.dp else 1.dp, if (isSelected) SunshineYellow else Color.LightGray)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = face, fontSize = 48.sp)
                }
            }
        }
    }
}

@Composable
fun FinishStep(name: String, onNameChange: (String) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(
            "Como se vai chamar o teu Amigo?",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            placeholder = { Text("Ex: Super Zé, Bolinha, Estrela...") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SkyBluePrimary,
                unfocusedBorderColor = Color.LightGray
            )
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            "✨ Estás quase a terminar! ✨",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MintGreen
        )
    }
}
