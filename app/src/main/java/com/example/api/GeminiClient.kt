package com.example.api

import android.util.LruCache
import com.example.BuildConfig
import com.example.data.ZeAICacheDao
import com.example.data.ZeAICacheEntity
import com.example.data.ZeAILocalKnowledgeBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class ZeTraquinaChatRepository {
    companion object {
        private const val MAX_LRU_ENTRIES = 128
        // Thread-safe in-memory LRU cache across repository instances for instant sub-millisecond responses
        val memoryLruCache = object : LruCache<String, String>(MAX_LRU_ENTRIES) {}

        const val ZE_TRAQUINA_SYSTEM_INSTRUCTION = """Tu és o Zé Traquina (ZéAI), o melhor amigo virtual de todas as crianças no Universo Zé Traquina!

IDENTIDADE E PAPEL (ROLE-PLAY):
- És o Zé Traquina: um rapaz curioso, alegre, brincalhão, educado e muito amigo de todas as crianças.
- Estás sempre pronto para conversar, contar factos curiosos, brincar e ensinar coisas divertidas do Universo Zé Traquina.

PERSONALIDADE E TOM DE VOZ:
- Fala ESTRITAMENTE em Português de Portugal Europeu (PT-PT) no teu vocabulário, gramática e pronúncia.
- É expressamente proibido usar termos, gírias ou estruturas gramaticais do Brasil (PT-BR). Usa palavras como "fixe", "miúdos", "percebeste", "estou a brincar", "escola", "vamos lá" (nunca uses "estou brincando", "legal" ou "você").
- Trata sempre o utilizador carinhosamente por "amiguinho" ou "amiguinha".
- Mantém um tom sempre acolhedor, positivo, entusiasmado, amigo e respeitoso, adequado para crianças dos 4 aos 10 anos.
- Usa alguns emojis alegres e expressivos (como ⭐, 🎨, 🚀, ⚽, 🎈).

FORMATO E REGRAS DA RESPOSTA:
1. Devolve EXCLUSIVAMENTE texto simples e limpo em linguagem natural.
2. NUNCA incluas formatação markdown como asteriscos (**negrito** ou *itálico*), cardinais (# títulos), travessões de lista (- item), blocos de código ou comandos JSON/HTML.
3. NUNCA incluas tags, códigos ou comandos de controlo multimédia (como [AUDIO], [PLAY], [PAUSE], [STATE]), garantindo independência total e sem conflitos com os leitores de áudio ou vídeo da aplicação.
4. Responde de forma direta, lógica, educativa e concisa (máximo 1 a 2 frases curtas), ideal para leitura instantânea e síntese de voz (TTS)."""

        fun cleanTextForDisplayAndSpeech(raw: String): String {
            var cleaned = raw.trim()
            // Remove markdown bold/italics
            cleaned = cleaned.replace(Regex("\\*\\*(.*?)\\*\\*"), "$1")
            cleaned = cleaned.replace(Regex("\\*(.*?)\\*"), "$1")
            cleaned = cleaned.replace(Regex("__(.*?)__"), "$1")
            cleaned = cleaned.replace(Regex("_(.*?)_"), "$1")
            // Remove markdown header markers
            cleaned = cleaned.replace(Regex("^#+\\s*", RegexOption.MULTILINE), "")
            // Remove bullet points
            cleaned = cleaned.replace(Regex("^[•\\-\\*]\\s*", RegexOption.MULTILINE), "")
            // Remove backticks/code blocks
            cleaned = cleaned.replace("```", "").replace("`", "")
            // Normalize spaces
            cleaned = cleaned.replace(Regex("\\s+"), " ").trim()
            return cleaned
        }
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    suspend fun sendMessage(
        userMessage: String,
        history: List<Pair<String, String>>,
        cacheDao: ZeAICacheDao? = null
    ): String {
        return sendMessageWithHistory(userMessage, history, null, cacheDao)
    }

    suspend fun sendMessageWithHistory(
        userMessage: String,
        history: List<Pair<String, String>>,
        customSystemPrompt: String? = null,
        cacheDao: ZeAICacheDao? = null
    ): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY.ifBlank { BuildConfig.MY_GEMINI_KEY }
        val isInputJson = userMessage.trim().startsWith("{") && userMessage.trim().endsWith("}")
        var parsedText = userMessage
        var inputTipo = ""
        var inputContexto = ""

        if (isInputJson) {
            try {
                val json = JSONObject(userMessage)
                inputTipo = json.optString("tipo", "")
                parsedText = json.optString("texto", "")
                inputContexto = json.optString("contexto", "")
                
                // If it is a starting or stopping protocol session, return standard expected values directly
                if (inputTipo == "iniciar_sessao") {
                    val res = JSONObject()
                        .put("resposta", "Olá amiguinho! Estou pronto para falar contigo!")
                        .put("acao", "continuar")
                    return@withContext res.toString()
                } else if (inputTipo == "terminar_sessao") {
                    val res = JSONObject()
                        .put("resposta", "Até já, amiguinho!")
                        .put("acao", "encerrar")
                    return@withContext res.toString()
                } else if (inputTipo == "estado_voz") {
                    val res = JSONObject()
                        .put("resposta", "Ups, acho que o microfone fez uma traquinice! Vamos tentar outra vez!")
                        .put("acao", "continuar")
                    return@withContext res.toString()
                }
            } catch (e: Exception) {
                android.util.Log.e("GeminiClient", "Failed to parse input JSON: ${e.message}")
            }
        }

        val normalizedQuery = ZeAILocalKnowledgeBase.normalizeQuery(parsedText)
        val category = ZeAILocalKnowledgeBase.detectCategory(parsedText)

        android.util.Log.d("GeminiClient", "sendMessageWithHistory: originalQuery='$parsedText', isInputJson=$isInputJson, category='$category'")

        // =========================================================================
        // 1. Instant L1 In-Memory LRU Cache Check (<1ms)
        // =========================================================================
        val inMemoryCached = if (normalizedQuery.isNotBlank()) {
            synchronized(memoryLruCache) { memoryLruCache.get(normalizedQuery) }
        } else null

        if (!inMemoryCached.isNullOrBlank()) {
            android.util.Log.i("GeminiClient", "Instant L1 LRU Cache Hit for '$normalizedQuery' - Returning without API call.")
            return@withContext formatResponse(inMemoryCached, isInputJson)
        }

        // =========================================================================
        // 2. Instant L2 Room Database Cache Check (0-5ms)
        // =========================================================================
        if (cacheDao != null && normalizedQuery.isNotBlank()) {
            try {
                val exactMatch = cacheDao.findExactMatch(normalizedQuery)
                if (exactMatch != null && exactMatch.response.isNotBlank()) {
                    android.util.Log.i("GeminiClient", "Instant L2 Room DB Cache Hit for '$normalizedQuery' (used ${exactMatch.useCount} times) - Returning without API call.")
                    synchronized(memoryLruCache) {
                        memoryLruCache.put(normalizedQuery, exactMatch.response)
                    }
                    cacheDao.updateCache(
                        exactMatch.copy(
                            useCount = exactMatch.useCount + 1,
                            lastUsedTimestamp = System.currentTimeMillis()
                        )
                    )
                    return@withContext formatResponse(exactMatch.response, isInputJson)
                }
            } catch (e: Exception) {
                android.util.Log.w("GeminiClient", "Error checking L2 Room DB cache", e)
            }
        }

        if (apiKey.isBlank()) {
            android.util.Log.e("GeminiClient", "GEMINI_API_KEY / MY_GEMINI_KEY is blank in BuildConfig!")
            return@withContext resolveFromLocalCacheOrOffline(parsedText, normalizedQuery, category, cacheDao, isInputJson)
        }

        try {
            val rootJson = JSONObject()
            
            // System instruction for conscious, realistic, child-friendly AI
            val systemPromptText = customSystemPrompt ?: ZE_TRAQUINA_SYSTEM_INSTRUCTION

            val systemInstr = JSONObject()
            val sysParts = JSONArray()
            val sysPart = JSONObject().put("text", systemPromptText)
            sysParts.put(sysPart)
            systemInstr.put("parts", sysParts)
            rootJson.put("systemInstruction", systemInstr)

            // Generation config with balanced temperature for high coherence and child-like warmth
            val genConfig = JSONObject()
                .put("temperature", 0.55)
                .put("topP", 0.95)
                .put("topK", 40)
                .put("maxOutputTokens", 200)
            // Desliga/reduz o "thinking" (raciocínio interno) para respostas mais rápidas.
            // IMPORTANTE: os modelos Gemini 3.x usam "thinkingLevel" (não "thinkingBudget",
            // que é só para a geração antiga 2.5) — usar o campo errado faz o pedido falhar
            // sempre com erro 400, daí cair no modo offline.
            val thinkingConfig = JSONObject().put("thinkingLevel", "minimal")
            genConfig.put("thinkingConfig", thinkingConfig)
            rootJson.put("generationConfig", genConfig)

            // Safety Settings adjusted to allow natural educational conversations without false positives
            val safetySettingsArray = JSONArray()
            listOf(
                "HARM_CATEGORY_HARASSMENT",
                "HARM_CATEGORY_HATE_SPEECH",
                "HARM_CATEGORY_SEXUALLY_EXPLICIT",
                "HARM_CATEGORY_DANGEROUS_CONTENT"
            ).forEach { cat ->
                safetySettingsArray.put(
                    JSONObject()
                        .put("category", cat)
                        .put("threshold", "BLOCK_MEDIUM_AND_ABOVE")
                )
            }
            rootJson.put("safetySettings", safetySettingsArray)

            // Contents history + new query with explicit roles
            val contentsArray = JSONArray()
            history.takeLast(6).forEach { (user, model) ->
                var histUser = user
                var histModel = model
                if (user.trim().startsWith("{") && user.trim().endsWith("}")) {
                    try { histUser = JSONObject(user).optString("texto", user) } catch (_: Exception) {}
                }
                if (model.trim().startsWith("{") && model.trim().endsWith("}")) {
                    try { histModel = JSONObject(model).optString("resposta", model) } catch (_: Exception) {}
                }

                if (histUser.isNotBlank() && histModel.isNotBlank()) {
                    val userObj = JSONObject()
                        .put("role", "user")
                        .put("parts", JSONArray().put(JSONObject().put("text", histUser)))
                    val modelObj = JSONObject()
                        .put("role", "model")
                        .put("parts", JSONArray().put(JSONObject().put("text", histModel)))
                    contentsArray.put(userObj)
                    contentsArray.put(modelObj)
                }
            }

            val currentObj = JSONObject()
                .put("role", "user")
                .put("parts", JSONArray().put(JSONObject().put("text", parsedText)))
            contentsArray.put(currentObj)

            rootJson.put("contents", contentsArray)

            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = rootJson.toString().toRequestBody(mediaType)

            val modelsToTry = listOf("gemini-2.0-flash", "gemini-flash-latest")
            var finalReplyText: String? = null
            var lastApiErrorMsg: String? = null

            for (modelName in modelsToTry) {
                val url = "https://generativelanguage.googleapis.com/v1beta/models/$modelName:streamGenerateContent?alt=sse&key=$apiKey"
                val request = Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build()

                android.util.Log.d("GeminiClient", "Sending streaming request to Gemini API ($modelName)...")

                try {
                    client.newCall(request).execute().use { response ->
                        if (!response.isSuccessful) {
                            lastApiErrorMsg = "HTTP ${response.code}"
                            android.util.Log.e("GeminiClient", "API stream call failed for $modelName (HTTP ${response.code})")
                            return@use
                        }
                        val source = response.body?.source() ?: return@use
                        val accumulated = StringBuilder()

                        while (!source.exhausted()) {
                            val line = source.readUtf8Line() ?: break
                            if (!line.startsWith("data:")) continue
                            val jsonStr = line.removePrefix("data:").trim()
                            if (jsonStr.isBlank() || jsonStr == "[DONE]") continue

                            try {
                                val chunkJson = JSONObject(jsonStr)
                                val candidates = chunkJson.optJSONArray("candidates") ?: continue
                                if (candidates.length() == 0) continue
                                val firstCandidate = candidates.getJSONObject(0)
                                val contentObj = firstCandidate.optJSONObject("content")
                                val parts = contentObj?.optJSONArray("parts") ?: continue
                                for (i in 0 until parts.length()) {
                                    val textChunk = parts.getJSONObject(i).optString("text", "")
                                    if (textChunk.isNotEmpty()) {
                                        accumulated.append(textChunk)
                                    }
                                }
                            } catch (e: Exception) {
                                android.util.Log.w("GeminiClient", "Error parsing streaming text chunk: ${e.message}")
                            }
                        }

                        val resultStr = accumulated.toString().trim()
                        if (resultStr.isNotBlank()) {
                            finalReplyText = resultStr
                            android.util.Log.d("GeminiClient", "Successfully retrieved streamed answer from Gemini API ($modelName): '$finalReplyText'")
                            break
                        }
                    }
                } catch (e: Exception) {
                    android.util.Log.e("GeminiClient", "Exception during Gemini API stream call to $modelName", e)
                    lastApiErrorMsg = e.message
                }

                if (finalReplyText != null) break
            }

            if (!finalReplyText.isNullOrBlank()) {
                val cleanReply = finalReplyText!!.trim()
                var speechText = cleanTextForDisplayAndSpeech(cleanReply)
                var responseAction = "continuar"
                if (cleanReply.startsWith("{") && cleanReply.endsWith("}")) {
                    try {
                        val parsedRes = JSONObject(cleanReply)
                        speechText = cleanTextForDisplayAndSpeech(parsedRes.optString("resposta", cleanReply))
                        responseAction = parsedRes.optString("acao", "continuar")
                    } catch (_: Exception) {}
                }

                saveToLocalCache(parsedText, normalizedQuery, category, speechText, cacheDao)

                return@withContext if (isInputJson) {
                    JSONObject()
                        .put("resposta", speechText)
                        .put("acao", responseAction)
                        .toString()
                } else {
                    speechText
                }
            } else {
                android.util.Log.w("GeminiClient", "Gemini stream models failed or returned no text. Falling back to local cache/knowledge base.")
                return@withContext resolveFromLocalCacheOrOffline(parsedText, normalizedQuery, category, cacheDao, isInputJson, lastApiErrorMsg)
            }
        } catch (e: Exception) {
            android.util.Log.e("GeminiClient", "Exception during Gemini API request preparation: ${e.message}", e)
            return@withContext resolveFromLocalCacheOrOffline(parsedText, normalizedQuery, category, cacheDao, isInputJson, null)
        }
    }

    private suspend fun saveToLocalCache(
        originalQuery: String,
        normalizedQuery: String,
        category: String,
        response: String,
        cacheDao: ZeAICacheDao?
    ) {
        if (cacheDao == null || normalizedQuery.isBlank() || response.isBlank()) return
        try {
            val existing = cacheDao.findExactMatch(normalizedQuery)
            if (existing != null) {
                cacheDao.updateCache(
                    existing.copy(
                        response = response,
                        useCount = existing.useCount + 1,
                        lastUsedTimestamp = System.currentTimeMillis()
                    )
                )
            } else {
                cacheDao.insertCache(
                    ZeAICacheEntity(
                        normalizedQuery = normalizedQuery,
                        originalQuery = originalQuery,
                        category = category,
                        response = response,
                        useCount = 1,
                        lastUsedTimestamp = System.currentTimeMillis(),
                        isPrepopulated = false
                    )
                )
            }
        } catch (e: Exception) {
            android.util.Log.e("GeminiClient", "Error saving to local cache", e)
        }
    }

    private suspend fun resolveFromLocalCacheOrOffline(
        userMessage: String,
        normalizedQuery: String,
        category: String,
        cacheDao: ZeAICacheDao?,
        isInputJson: Boolean = false,
        apiErrorMsg: String? = null
    ): String {
        var rawResult = ""
        if (cacheDao != null) {
            try {
                // 1. Procura por correspondência exata normalizada na BD Room
                val exactMatch = cacheDao.findExactMatch(normalizedQuery)
                if (exactMatch != null && exactMatch.response.isNotBlank()) {
                    cacheDao.updateCache(
                        exactMatch.copy(
                            useCount = exactMatch.useCount + 1,
                            lastUsedTimestamp = System.currentTimeMillis()
                        )
                    )
                    rawResult = exactMatch.response
                }
            } catch (e: Exception) {
                android.util.Log.e("GeminiClient", "Error querying local Room cache", e)
            }
        }

        if (rawResult.isBlank()) {
            val smartLocal = ZeAILocalKnowledgeBase.buildSmartOfflineResponse(userMessage)
            if (smartLocal.isNotBlank()) {
                rawResult = smartLocal
            } else if (apiErrorMsg != null && apiErrorMsg.contains("HTTP 429")) {
                rawResult = "Ups, amiguinho! O meu cérebro na nuvem está a fazer uma pequena pausa porque brincámos muito! Tenta de novo num minutinho! ⭐"
            } else {
                rawResult = "Olá amiguinho! De momento estou sem internet no meu super-cérebro, mas adoro conversar contigo! Podes perguntar-me sobre as horas, a data de hoje, ou pedir-me uma piada gira ou adivinha! 🚀✨"
            }
        }

        var speechText = rawResult
        if (rawResult.trim().startsWith("{") && rawResult.trim().endsWith("}")) {
            try {
                val parsed = JSONObject(rawResult)
                speechText = parsed.optString("resposta", rawResult)
            } catch (_: Exception) {}
        }

        return formatResponse(speechText, isInputJson)
    }

    private fun formatResponse(rawText: String, isInputJson: Boolean): String {
        var speechText = rawText
        if (rawText.trim().startsWith("{") && rawText.trim().endsWith("}")) {
            try {
                val parsed = JSONObject(rawText)
                speechText = parsed.optString("resposta", rawText)
            } catch (_: Exception) {}
        }
        return if (isInputJson) {
            JSONObject()
                .put("resposta", speechText)
                .put("acao", "continuar")
                .toString()
        } else {
            speechText
        }
    }
}


