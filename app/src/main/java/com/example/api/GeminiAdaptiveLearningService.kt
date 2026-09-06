package com.example.api

import com.example.BuildConfig
import com.example.data.CompletedItem
import com.example.data.UserProgress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

data class AdaptiveAnalysisResult(
    val recommendedDifficulty: String, // "Fácil", "Médio", "Desafiante", "Avançado"
    val difficultyExplanation: String,
    val suggestedTopics: List<String>,
    val suggestedNextActivity: String,
    val encouragingMessage: String,
    val analyzedAt: Long = System.currentTimeMillis()
)

class GeminiAdaptiveLearningService {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    suspend fun analyzePerformance(
        userProgress: UserProgress,
        completedItems: List<CompletedItem>
    ): AdaptiveAnalysisResult = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.MY_GEMINI_KEY

        try {
            val rootJson = JSONObject()

            val systemInstr = JSONObject().apply {
                put("parts", JSONArray().apply {
                    put(JSONObject().apply {
                        put("text", """
                            Tu és um assistente pedagógico perito em desenvolvimento infantil e gamificação para a aplicação 'Universo do Zé Traquina'.
                            A tua missão é analisar as métricas de desempenho da criança (jogos concluídos, estrelas, faixa etária) e retornar obrigatoriamente um JSON válido no seguinte formato:
                            {
                              "recommendedDifficulty": "Fácil" | "Médio" | "Desafiante" | "Avançado",
                              "difficultyExplanation": "uma explicação curta em português de Portugal do porquê do ajuste",
                              "suggestedTopics": ["Tópico 1", "Tópico 2", "Tópico 3"],
                              "suggestedNextActivity": "Nome exato de um jogo ou atividade sugerida (ex: Jogo da Memória, Sopa de Letras, Quebra-Cabeça, Corrida Colorida, Desafio dos Números)",
                              "encouragingMessage": "Mensagem entusiasta e calorosa do Zé Traquina para a criança em português de Portugal"
                            }
                        """.trimIndent())
                    })
                })
            }
            rootJson.put("systemInstruction", systemInstr)

            val recentActivitiesStr = if (completedItems.isEmpty()) {
                "Nenhuma atividade concluída ainda."
            } else {
                completedItems.take(15).joinToString(", ") { "${it.title} (${it.category})" }
            }

            val promptText = """
                Nome da Criança: ${userProgress.childName}
                Faixa Etária: ${userProgress.ageGroup} anos
                Total de Estrelas: ${userProgress.starsCount}
                Total de Jogos Jogados: ${userProgress.totalGamesPlayed}
                Sequência de Dias Ativos: ${userProgress.streakDays}
                Atividades Concluídas Recentes: $recentActivitiesStr
                
                Analisa o nível de maestria e engajamento da criança, determina se a dificuldade deve ser aumentada ou ajustada, e sugere 3 tópicos pedagógicos cativantes para as próximas atividades.
            """.trimIndent()

            val contentsArray = JSONArray().apply {
                put(JSONObject().apply {
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply { put("text", promptText) })
                    })
                })
            }
            rootJson.put("contents", contentsArray)

            val genConfig = JSONObject().apply {
                put("responseMimeType", "application/json")
                put("temperature", 0.4)
            }
            rootJson.put("generationConfig", genConfig)

            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = rootJson.toString().toRequestBody(mediaType)

            val apiKey = BuildConfig.GEMINI_API_KEY.ifBlank { BuildConfig.MY_GEMINI_KEY }
            val models = listOf("gemini-2.0-flash", "gemini-1.5-flash", "gemini-2.5-flash", "gemini-1.5-pro")
            var bodyStr = ""
            var success = false

            for (model in models) {
                val url = "https://generativelanguage.googleapis.com/v1beta/models/$model:generateContent?key=$apiKey"
                val request = Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build()

                try {
                    client.newCall(request).execute().use { response ->
                        if (response.isSuccessful) {
                            bodyStr = response.body?.string() ?: ""
                            if (bodyStr.isNotBlank()) {
                                success = true
                            }
                        }
                    }
                } catch (_: Exception) {}

                if (success) break
            }

            if (!success) {
                return@withContext generateFallbackAnalysis(userProgress, completedItems)
            }
                val resObj = JSONObject(bodyStr)
                val candidates = resObj.optJSONArray("candidates")
                if (candidates != null && candidates.length() > 0) {
                    val firstCandidate = candidates.getJSONObject(0)
                    val contentObj = firstCandidate.optJSONObject("content")
                    val parts = contentObj?.optJSONArray("parts")
                    if (parts != null && parts.length() > 0) {
                        val jsonText = parts.getJSONObject(0).optString("text")
                        if (jsonText.isNotBlank()) {
                            val parsed = JSONObject(jsonText)
                            val difficulty = parsed.optString("recommendedDifficulty", "Desafiante")
                            val explanation = parsed.optString("difficultyExplanation", "Com base na tua excelente precisão, ajustámos o nível de desafio!")
                            val topicsArray = parsed.optJSONArray("suggestedTopics")
                            val topics = mutableListOf<String>()
                            if (topicsArray != null) {
                                for (i in 0 until topicsArray.length()) {
                                    topics.add(topicsArray.getString(i))
                                }
                            }
                            if (topics.isEmpty()) {
                                topics.addAll(listOf("Exploração Espacial 🚀", "Mundo dos Animais 🦁", "Desafios de Matemática 🧮"))
                            }
                            val nextAct = parsed.optString("suggestedNextActivity", "Jogo da Memória dos Animais")
                            val msg = parsed.optString("encouragingMessage", "Uau, estás a ir super bem! Continua assim, campeão! ⭐")

                            return@withContext AdaptiveAnalysisResult(
                                recommendedDifficulty = difficulty,
                                difficultyExplanation = explanation,
                                suggestedTopics = topics,
                                suggestedNextActivity = nextAct,
                                encouragingMessage = msg
                            )
                        }
                    }
                }
            generateFallbackAnalysis(userProgress, completedItems)
        } catch (e: Exception) {
            generateFallbackAnalysis(userProgress, completedItems)
        }
    }

    private fun generateFallbackAnalysis(
        userProgress: UserProgress,
        completedItems: List<CompletedItem>
    ): AdaptiveAnalysisResult {
        val totalCount = completedItems.size + userProgress.totalGamesPlayed
        val (difficulty, explanation) = when {
            totalCount > 15 -> "Avançado" to "Atingiste um ritmo espetacular! Os jogos agora têm mais elementos e novos desafios."
            totalCount > 6 -> "Desafiante" to "Demonstraste ótima concentração! Aumentámos ligeiramente o nível dos puzzles."
            totalCount > 2 -> "Médio" to "Já ganhaste bastante prática! O nível médio é ideal para continuares a evoluir."
            else -> "Fácil" to "Início perfeito! Começamos com ritmos tranquilos para explorares à tua vontade."
        }

        val name = if (userProgress.childName.isNotBlank() && userProgress.childName != "Amiguinhos") userProgress.childName else "amiguinho"

        return AdaptiveAnalysisResult(
            recommendedDifficulty = difficulty,
            difficultyExplanation = explanation,
            suggestedTopics = listOf("Mundo Animal 🦁", "Raciocínio Lógico 🧩", "Cores e Formas 🎨", "Contar & Somar 🧮"),
            suggestedNextActivity = "Jogo da Memória dos Animais",
            encouragingMessage = "Olá $name! Tens feito um trabalho fantástico no Universo do Zé Traquina. Vamos continuar a aprender juntos!"
        )
    }
}
