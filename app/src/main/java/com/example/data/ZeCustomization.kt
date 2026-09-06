package com.example.data

import android.content.Context
import androidx.compose.ui.graphics.Color

enum class HatOption(val id: String, val title: String, val emoji: String, val description: String) {
    BERET("beret", "Boina Clássica", "👨‍🎨", "A boina verde tradicional do Zé"),
    NONE("none", "Sem Chapéu", "👤", "Sem chapéu na cabeça"),
    PARTY("party", "Chapéu de Festa", "🥳", "Chapéu cónico com pompom alegre"),
    WIZARD("wizard", "Chapéu Mágico", "🧙‍♂️", "Chapéu de feiticeiro com estrelas"),
    CROWN("crown", "Coroa Real", "👑", "Coroa dourada com joias radiantes"),
    CAP("cap", "Boné Desportivo", "🧢", "Boné azul para grandes aventuras"),
    BUNNY("bunny", "Orelhas Fofas", "🐰", "Orelhas de coelhinho muito divertidas"),
    PIRATE("pirate", "Chapéu Pirata", "🏴‍☠️", "Chapéu tricornio de grande explorador")
}

enum class GlassesOption(val id: String, val title: String, val emoji: String, val description: String) {
    NONE("none", "Sem Óculos", "👀", "Sem óculos"),
    READING("reading", "Óculos Inteligentes", "👓", "Óculos redondos de grande sábio"),
    SUNGLASSES("sunglasses", "Óculos de Sol", "🕶️", "Óculos escuros super estilosos"),
    STAR("star", "Óculos Estrela", "🌟", "Óculos amarelos em forma de estrela"),
    HERO_MASK("hero_mask", "Máscara Herói", "🦸‍♂️", "Máscara misteriosa de super-herói"),
    GLASSES_3D("glasses_3d", "Óculos 3D", "🕶️✨", "Óculos 3D vermelho e azul")
}

enum class OutfitOption(val id: String, val title: String, val emoji: String, val description: String) {
    POLO_GREEN("polo_green", "Polo Verde Clássico", "👕", "Polo verde da sorte do Zé Traquina"),
    ASTRONAUT("astronaut", "Traje Espacial", "👨‍🚀", "Fato espacial com insignia e estrelas"),
    WIZARD("wizard", "Manto Mágico", "🧙‍♂️", "Túnica roxa de grande mago"),
    SUPERHERO("superhero", "Capa de Herói", "🦸‍♂️", "Capa vermelha com emblema reluzente"),
    EXPLORER("explorer", "Colete Aventura", "🏕️", "Colete de explorador com bússola"),
    PAINTER("painter", "Avental de Artista", "🎨", "Avental com salpicos de tintas")
}

enum class AuraColorOption(val id: String, val title: String, val color: Color) {
    CYAN("cyan", "Azul Néon", Color(0xFF00E5FF)),
    GOLD("gold", "Amarelo Ouro", Color(0xFFFFD54F)),
    PURPLE("purple", "Roxo Mágico", Color(0xFFAB47BC)),
    GREEN("green", "Verde Esmeralda", Color(0xFF66BB6A)),
    RED("red", "Vermelho Traquina", Color(0xFFFF5252)),
    PINK("pink", "Rosa Festivo", Color(0xFFEC407A)),
    ORANGE("orange", "Laranja Sol", Color(0xFFFF9800))
}

data class ZeCustomization(
    val hat: HatOption = HatOption.BERET,
    val glasses: GlassesOption = GlassesOption.NONE,
    val outfit: OutfitOption = OutfitOption.POLO_GREEN,
    val aura: AuraColorOption = AuraColorOption.CYAN
) {
    fun saveToPrefs(context: Context) {
        val prefs = context.getSharedPreferences("ze_customization_prefs", Context.MODE_PRIVATE)
        prefs.edit()
            .putString("hat", hat.id)
            .putString("glasses", glasses.id)
            .putString("outfit", outfit.id)
            .putString("aura", aura.id)
            .apply()
    }

    companion object {
        fun loadFromPrefs(context: Context): ZeCustomization {
            val prefs = context.getSharedPreferences("ze_customization_prefs", Context.MODE_PRIVATE)
            val hatId = prefs.getString("hat", HatOption.BERET.id) ?: HatOption.BERET.id
            val glassesId = prefs.getString("glasses", GlassesOption.NONE.id) ?: GlassesOption.NONE.id
            val outfitId = prefs.getString("outfit", OutfitOption.POLO_GREEN.id) ?: OutfitOption.POLO_GREEN.id
            val auraId = prefs.getString("aura", AuraColorOption.CYAN.id) ?: AuraColorOption.CYAN.id

            return ZeCustomization(
                hat = HatOption.values().find { it.id == hatId } ?: HatOption.BERET,
                glasses = GlassesOption.values().find { it.id == glassesId } ?: GlassesOption.NONE,
                outfit = OutfitOption.values().find { it.id == outfitId } ?: OutfitOption.POLO_GREEN,
                aura = AuraColorOption.values().find { it.id == auraId } ?: AuraColorOption.CYAN
            )
        }
    }
}
