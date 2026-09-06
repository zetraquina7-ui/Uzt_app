package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class UserProgress(
    @PrimaryKey val id: Int = 1,
    val starsCount: Int = 25,
    val totalGamesPlayed: Int = 0,
    val streakDays: Int = 1,
    val timeLimitMinutes: Int = 30, // 0 = unlimited, 15, 30, 45, 60
    val ageGroup: String = "4-5", // "2-3", "4-5", "6+"
    val soundEnabled: Boolean = true,
    val childName: String = "Amiguinho",
    // Legacy shape fields
    val customCharShape: String = "CIRCLE",
    val customCharColor: Long = 0xFF0288D1,
    val customCharFace: String = "😊",
    val customCharName: String = "Zé Traquina Jr.",

    // Enhanced Customizable Avatar System for "Universo Zé Traquina"
    val avatarSkinTone: String = "skin_fair",      // skin_light, skin_fair, skin_warm, skin_tan, skin_deep
    val avatarHairStyle: String = "hair_short",     // hair_short, hair_spiky, hair_curly, hair_ponytail, hair_wavy, hair_braids
    val avatarHairColor: Long = 0xFF4E342E,         // Brown (Castanho Zé)
    val avatarClothingStyle: String = "polo_green_vintage", // polo_green_vintage (Polo Verde Clássico)
    val avatarClothingColor: Long = 0xFF2E7D32,     // Green (Verde Clássico)
    val avatarAccessory: String = "cap_vintage_beret", // cap_vintage_beret (Boina Castanha Vintage)
    val avatarExpression: String = "smile_freckles",   // smile_freckles (Sorriso com Sardas)
    val avatarBackground: String = "street_calcada"    // street_calcada (Rua de Calçada Antiga)
)

@Entity(tableName = "completed_items")
data class CompletedItem(
    @PrimaryKey val id: String, // e.g. "alphabet_A", "game_memory_easy"
    val category: String,
    val title: String,
    val timestamp: Long = System.currentTimeMillis()
)
