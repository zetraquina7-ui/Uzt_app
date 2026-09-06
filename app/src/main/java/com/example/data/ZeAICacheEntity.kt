package com.example.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entidade para cache local persistente das frases e respostas do ZéAI.
 * Permite que a personagem interaja com inteligência e simpatia mesmo sem ligação à Internet.
 */
@Entity(
    tableName = "ze_ai_cache",
    indices = [
        Index(value = ["normalizedQuery"]),
        Index(value = ["category"])
    ]
)
data class ZeAICacheEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val normalizedQuery: String,
    val originalQuery: String,
    val category: String, // "saudacao", "ciencia", "matematica", "animais", "espaco", "escola", "historia", "curiosidade", "adivinha", "piada", "emocoes", "geral"
    val response: String,
    val useCount: Int = 1,
    val lastUsedTimestamp: Long = System.currentTimeMillis(),
    val isPrepopulated: Boolean = false
)
