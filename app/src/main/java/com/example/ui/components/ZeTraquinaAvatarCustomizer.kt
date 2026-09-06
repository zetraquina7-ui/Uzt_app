package com.example.ui.components

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.MainViewModel
import kotlin.random.Random

data class AvatarOptionItem(
    val id: String,
    val title: String,
    val iconEmoji: String,
    val color: Color? = null,
    val colorHex: Long? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZeTraquinaAvatarCustomizer(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val progress by viewModel.userProgress.collectAsState()

    var avatarName by remember(progress) {
        mutableStateOf(progress.customCharName.ifBlank { "Zé Traquina" })
    }
    var skinTone by remember(progress) {
        mutableStateOf(progress.avatarSkinTone.ifBlank { "skin_fair" })
    }
    var hairStyle by remember(progress) {
        mutableStateOf(progress.avatarHairStyle.ifBlank { "hair_short" })
    }
    var hairColor by remember(progress) {
        mutableStateOf(if (progress.avatarHairColor != 0L) progress.avatarHairColor else 0xFF4E342E)
    }
    var clothingStyle by remember(progress) {
        mutableStateOf(if (progress.avatarClothingStyle.isNotBlank() && progress.avatarClothingStyle != "tshirt_star") progress.avatarClothingStyle else "polo_green_vintage")
    }
    var clothingColor by remember(progress) {
        mutableStateOf(if (progress.avatarClothingColor != 0L && progress.avatarClothingColor != 0xFF0288D1) progress.avatarClothingColor else 0xFF2E7D32)
    }
    var accessory by remember(progress) {
        mutableStateOf(if (progress.avatarAccessory.isNotBlank() && progress.avatarAccessory != "cap_ze") progress.avatarAccessory else "cap_vintage_beret")
    }
    var expression by remember(progress) {
        mutableStateOf(if (progress.avatarExpression.isNotBlank() && progress.avatarExpression != "smile") progress.avatarExpression else "smile_freckles")
    }
    var background by remember(progress) {
        mutableStateOf(if (progress.avatarBackground.isNotBlank() && progress.avatarBackground != "room_ze") progress.avatarBackground else "street_calcada")
    }

    var selectedTab by remember { mutableStateOf("roupa") }
    var showSavedMessage by remember { mutableStateOf(false) }
    var useRealPhoto3dModel by remember { mutableStateOf(false) }

    // Option Lists
    val skinTones = listOf(
        AvatarOptionItem("skin_fair", "Morena Clara (Sardas)", "👦", Color(0xFFFCD0B1)),
        AvatarOptionItem("skin_light", "Clara", "👶", Color(0xFFFFE0D2)),
        AvatarOptionItem("skin_warm", "Dourada", "🧒", Color(0xFFE8AB75)),
        AvatarOptionItem("skin_tan", "Morena Escura", "👦", Color(0xFFC68642)),
        AvatarOptionItem("skin_deep", "Negra / Profunda", "👧🏾", Color(0xFF8D5524)),
        AvatarOptionItem("skin_alien", "Alienígena Traquina", "👽", Color(0xFFA7F3D0))
    )

    val hairStyles = listOf(
        AvatarOptionItem("hair_short", "Curto com Franja (Clássico)", "👦"),
        AvatarOptionItem("hair_spiky", "Radical / Picos", "⚡"),
        AvatarOptionItem("hair_curly", "Afro / Caracóis", "🌀"),
        AvatarOptionItem("hair_ponytail", "Rabo de Cavalo", "👱‍♀️"),
        AvatarOptionItem("hair_wavy", "Ondulado Longo", "🌊"),
        AvatarOptionItem("hair_braids", "Trancinhas", "🎀")
    )

    val hairColors = listOf(
        AvatarOptionItem("c_brown", "Castanho Zé", "🟤", Color(0xFF4E342E), 0xFF4E342E),
        AvatarOptionItem("c_black", "Preto", "⚫", Color(0xFF212121), 0xFF212121),
        AvatarOptionItem("c_blonde", "Loiro Dourado", "🟡", Color(0xFFF59E0B), 0xFFF59E0B),
        AvatarOptionItem("c_red", "Ruivo", "🟠", Color(0xFFE65100), 0xFFE65100),
        AvatarOptionItem("c_blue", "Azul Cósmico", "🔵", Color(0xFF3B82F6), 0xFF3B82F6),
        AvatarOptionItem("c_pink", "Rosa Mágico", "🌸", Color(0xFFEC4899), 0xFFEC4899),
        AvatarOptionItem("c_purple", "Roxo Traquina", "🟣", Color(0xFF8B5CF6), 0xFF8B5CF6)
    )

    val clothingStyles = listOf(
        AvatarOptionItem("polo_green_vintage", "Polo Verde Clássico (Foto)", "👕"),
        AvatarOptionItem("tshirt_star", "T-Shirt Estrela", "⭐"),
        AvatarOptionItem("hoodie_ze", "Hoodie do Zé", "🧥"),
        AvatarOptionItem("overalls_traquina", "Jardineiras", "👖"),
        AvatarOptionItem("explorer_jacket", "Aventureiro", "🧭"),
        AvatarOptionItem("dress_sparkle", "Vestido Mágico", "👗")
    )

    val clothingColors = listOf(
        AvatarOptionItem("cl_green", "Verde Clássico", "💚", Color(0xFF2E7D32), 0xFF2E7D32),
        AvatarOptionItem("cl_blue", "Azul Zé", "💙", Color(0xFF0288D1), 0xFF0288D1),
        AvatarOptionItem("cl_red", "Vermelho Rubi", "❤️", Color(0xFFE53935), 0xFFE53935),
        AvatarOptionItem("cl_yellow", "Amarelo Sol", "💛", Color(0xFFFBBF24), 0xFFFBBF24),
        AvatarOptionItem("cl_purple", "Roxo Galáctico", "💜", Color(0xFF8B5CF6), 0xFF8B5CF6),
        AvatarOptionItem("cl_orange", "Laranja Foguetão", "🧡", Color(0xFFF97316), 0xFFF97316)
    )

    val accessories = listOf(
        AvatarOptionItem("cap_vintage_beret", "Boina Vintage Castanha (Foto)", "🧢"),
        AvatarOptionItem("cap_ze", "Boné Azul do Zé", "🧢"),
        AvatarOptionItem("glasses_cool", "Óculos Escuros", "😎"),
        AvatarOptionItem("glasses_smart", "Óculos Cientista", "🤓"),
        AvatarOptionItem("headphones", "Fones DJ", "🎧"),
        AvatarOptionItem("bow_ribbon", "Laço Mágico", "🎀"),
        AvatarOptionItem("star_badge", "Estrela Campeão", "⭐"),
        AvatarOptionItem("none", "Nenhum", "❌")
    )

    val expressions = listOf(
        AvatarOptionItem("smile_freckles", "Sorriso com Sardas (Foto)", "😄"),
        AvatarOptionItem("smile", "Sorriso Lindo", "😄"),
        AvatarOptionItem("wink", "Piscadela", "😉"),
        AvatarOptionItem("playful", "Traquina & Língua", "😜"),
        AvatarOptionItem("star_eyes", "Super Empolgado", "🤩"),
        AvatarOptionItem("happy", "Alegria Doce", "😊")
    )

    val backgrounds = listOf(
        AvatarOptionItem("street_calcada", "Rua de Calçada (Foto)", "🏘️"),
        AvatarOptionItem("room_ze", "Quarto do Zé", "🏠"),
        AvatarOptionItem("park", "Parque Divertido", "🌳"),
        AvatarOptionItem("space", "Espaço Sideral", "🚀"),
        AvatarOptionItem("pirate_island", "Ilha dos Piratas", "🏝️"),
        AvatarOptionItem("music_stage", "Palco Musical", "🎸")
    )

    val applyClassicVintagePreset = {
        viewModel.playClickSound()
        useRealPhoto3dModel = false
        avatarName = "Zé Traquina"
        skinTone = "skin_fair"
        hairStyle = "hair_short"
        hairColor = 0xFF4E342E
        clothingStyle = "polo_green_vintage"
        clothingColor = 0xFF2E7D32
        accessory = "cap_vintage_beret"
        expression = "smile_freckles"
        background = "street_calcada"
    }

    val randomizeAvatar = {
        viewModel.playClickSound()
        useRealPhoto3dModel = false
        skinTone = skinTones.random().id
        hairStyle = hairStyles.random().id
        hairColor = hairColors.random().colorHex ?: 0xFF5D4037
        clothingStyle = clothingStyles.random().id
        clothingColor = clothingColors.random().colorHex ?: 0xFF0288D1
        accessory = accessories.random().id
        expression = expressions.random().id
        background = backgrounds.random().id
    }

    val playAvatarVoice = {
        viewModel.playClickSound()
        val speech = "Olá! Eu sou o $avatarName do Universo Zé Traquina! Estou pronto para aprender e viver muitas aventuras contigo!"
        viewModel.speak(speech)
    }

    val saveAvatar = {
        viewModel.playStarSound()
        viewModel.saveCustomAvatar(
            name = avatarName,
            skinTone = skinTone,
            hairStyle = hairStyle,
            hairColor = hairColor,
            clothingStyle = clothingStyle,
            clothingColor = clothingColor,
            accessory = accessory,
            expression = expression,
            background = background
        )
        showSavedMessage = true
        Toast.makeText(context, "Avatar Guardado! +5 Estrelas! ⭐", Toast.LENGTH_SHORT).show()
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ==========================================
        // 1. Avatar Preview Stage (Reduced size)
        // ==========================================
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 6.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), // Reduced padding
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Character Name Input Field (Inline)
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Nome: ",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF334155)
                    )
                    OutlinedTextField(
                        value = avatarName,
                        onValueChange = { if (it.length <= 20) avatarName = it },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF4338CA),
                            unfocusedBorderColor = Color(0xFFCBD5E1),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp) // Slightly taller
                            .testTag("avatar_name_input")
                    )
                }

                // The Customizer Visual Canvas
                ZeTraquinaAvatarView(
                    skinTone = skinTone,
                    hairStyle = hairStyle,
                    hairColor = hairColor,
                    clothingStyle = clothingStyle,
                    clothingColor = clothingColor,
                    accessory = accessory,
                    expression = expression,
                    background = background,
                    useRealPhoto3dModel = useRealPhoto3dModel,
                    size = 130.dp // Reduced size for better visibility of options
                )

                // Action Buttons (Hear Voice Puck & Save)
                Row(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = playAvatarVoice,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4338CA)),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .testTag("listen_avatar_btn")
                    ) {
                        Text(text = "Ouvir 🎙️", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    Button(
                        onClick = saveAvatar,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .testTag("save_avatar_btn")
                    ) {
                        Text(text = "Guardar ⭐", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }

        // ==========================================
        // 2. Options Area (Weight based)
        // ==========================================
        Column(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(horizontal = 4.dp, vertical = 4.dp)
        ) {
            // Category Submenu Tabs
            val categories = listOf(
                Triple("pele", "Pele", "🎨"),
                Triple("cabelo", "Cabelo", "💇"),
                Triple("roupa", "Roupa", "👕"),
                Triple("acessorio", "Acessório", "🧢"),
                Triple("expressao", "Expressão", "😄"),
                Triple("cenario", "Cenário", "🏞️")
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                categories.forEach { (catId, catName, catIcon) ->
                    val isSelected = selectedTab == catId
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = if (isSelected) Color(0xFF4338CA) else Color.White,
                        border = BorderStroke(1.5.dp, if (isSelected) Color(0xFF3730A3) else Color(0xFFE2E8F0)),
                        shadowElevation = if (isSelected) 3.dp else 1.dp,
                        modifier = Modifier.clickable {
                            selectedTab = catId
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = catIcon, fontSize = 12.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = catName,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.SemiBold,
                                color = if (isSelected) Color.White else Color(0xFF334155)
                            )
                        }
                    }
                }
            }

            // Selection Panels for Active Category (Scrollable only here)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()) // Scroll only here
                        .padding(14.dp)
                ) {
                    when (selectedTab) {
                        "pele" -> {
                            Text("Escolhe a Cor da Pele:", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                skinTones.forEach { item ->
                                    OptionSelectRow(item, skinTone == item.id, { skinTone = item.id })
                                }
                            }
                        }
                        "cabelo" -> {
                            Text("1. Estilo do Cabelo:", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(hairStyles) { item -> OptionGridChip(item, hairStyle == item.id, { hairStyle = item.id }) }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text("2. Cor do Cabelo:", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(hairColors) { item -> OptionColorChip(item, hairColor == (item.colorHex ?: 0L), { hairColor = item.colorHex ?: 0xFF5D4037 }) }
                            }
                        }
                        "roupa" -> {
                            Text("1. Estilo de Roupa:", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(clothingStyles) { item -> OptionGridChip(item, clothingStyle == item.id, { clothingStyle = item.id }) }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text("2. Cor da Roupa:", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(clothingColors) { item -> OptionColorChip(item, clothingColor == (item.colorHex ?: 0L), { clothingColor = item.colorHex ?: 0xFF0288D1 }) }
                            }
                        }
                        "acessorio" -> {
                            Text("Escolhe um Acessório:", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                accessories.forEach { item -> OptionSelectRow(item, accessory == item.id, { accessory = item.id }) }
                            }
                        }
                        "expressao" -> {
                            Text("Expressão do Rosto:", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                expressions.forEach { item -> OptionSelectRow(item, expression == item.id, { expression = item.id }) }
                            }
                        }
                        "cenario" -> {
                            Text("Cenário de Fundo:", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                backgrounds.forEach { item -> OptionSelectRow(item, background == item.id, { background = item.id }) }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OptionSelectRow(
    item: AvatarOptionItem,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() },
        shape = RoundedCornerShape(14.dp),
        color = if (isSelected) Color(0xFFEEF2FF) else Color(0xFFF8FAFC),
        border = BorderStroke(1.5.dp, if (isSelected) Color(0xFF4338CA) else Color(0xFFE2E8F0))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (item.color != null) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(item.color)
                            .border(1.5.dp, Color.White, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                } else {
                    Text(text = item.iconEmoji, fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                }

                Text(
                    text = item.title,
                    fontSize = 13.sp,
                    fontWeight = if (isSelected) FontWeight.Black else FontWeight.SemiBold,
                    color = if (isSelected) Color(0xFF1E1B4B) else Color(0xFF334155)
                )
            }

            Icon(
                imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                contentDescription = null,
                tint = if (isSelected) Color(0xFF4338CA) else Color(0xFFCBD5E1),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun OptionGridChip(
    item: AvatarOptionItem,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Surface(
        modifier = Modifier
            .width(110.dp)
            .clickable { onSelect() },
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) Color(0xFFEEF2FF) else Color(0xFFF8FAFC),
        border = BorderStroke(1.5.dp, if (isSelected) Color(0xFF4338CA) else Color(0xFFE2E8F0))
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = item.iconEmoji, fontSize = 26.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.title,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Black else FontWeight.SemiBold,
                color = if (isSelected) Color(0xFF1E1B4B) else Color(0xFF334155),
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}

@Composable
private fun OptionColorChip(
    item: AvatarOptionItem,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Surface(
        modifier = Modifier
            .width(100.dp)
            .clickable { onSelect() },
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) Color(0xFFEEF2FF) else Color(0xFFF8FAFC),
        border = BorderStroke(1.5.dp, if (isSelected) Color(0xFF4338CA) else Color(0xFFE2E8F0))
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(item.color ?: Color.Gray)
                    .border(2.dp, if (isSelected) Color(0xFF4338CA) else Color.White, CircleShape)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.title,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Black else FontWeight.SemiBold,
                color = if (isSelected) Color(0xFF1E1B4B) else Color(0xFF334155),
                textAlign = TextAlign.Center
            )
        }
    }
}
