package com.example.data

import android.content.Context
import android.util.Log
import android.util.LruCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repositório para gerir o cache local de frases do ZéAI (L1 Memória LRU + L2 Room SQLite).
 * Permite respostas offline imediatas e resposta a perguntas repetidas com 0ms de latência e sem chamadas redundantes de API.
 */
class ZeAICacheRepository(context: Context) {
    private val TAG = "ZeAICacheRepository"
    private val database = AppDatabase.getDatabase(context.applicationContext)
    private val cacheDao = database.zeAICacheDao()

    companion object {
        private const val MAX_LRU_ENTRIES = 128
        // Thread-safe in-memory LRU cache across repository instances
        val memoryLruCache = object : LruCache<String, String>(MAX_LRU_ENTRIES) {}
    }

    suspend fun initializeIfNeeded() = withContext(Dispatchers.IO) {
        try {
            val count = cacheDao.getCacheCount()
            if (count == 0) {
                val seedData = ZeAILocalKnowledgeBase.getInitialSeedData()
                cacheDao.insertAll(seedData)
                Log.d(TAG, "Successfully seeded ZéAI local cache with ${seedData.size} phrases")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize ZéAI local cache", e)
        }
    }

    /**
     * Procura primeiro na cache L1 (Memória LRU) e depois na L2 (Room DB) por uma resposta exata à pergunta.
     * Retorna a resposta instantaneamente se já tiver sido respondida anteriormente, poupando chamadas de API.
     */
    suspend fun findInstantCachedResponse(userQuery: String): String? = withContext(Dispatchers.IO) {
        val normalized = ZeAILocalKnowledgeBase.normalizeQuery(userQuery)
        if (normalized.isBlank()) return@withContext null

        // 1. L1: In-Memory LRU Cache (<1ms)
        val inMemory = synchronized(memoryLruCache) { memoryLruCache.get(normalized) }
        if (!inMemory.isNullOrBlank()) {
            Log.d(TAG, "Instant L1 LRU memory cache hit for '$normalized'")
            return@withContext inMemory
        }

        // 2. L2: Persistent Room Database exact match
        try {
            val exactMatch = cacheDao.findExactMatch(normalized)
            if (exactMatch != null && exactMatch.response.isNotBlank()) {
                Log.d(TAG, "Instant L2 Room DB cache hit for '$normalized'")
                synchronized(memoryLruCache) {
                    memoryLruCache.put(normalized, exactMatch.response)
                }
                cacheDao.updateCache(
                    exactMatch.copy(
                        useCount = exactMatch.useCount + 1,
                        lastUsedTimestamp = System.currentTimeMillis()
                    )
                )
                return@withContext exactMatch.response
            }
        } catch (e: Exception) {
            Log.w(TAG, "Error looking up instant cache in Room DB", e)
        }

        return@withContext null
    }

    suspend fun getOfflineResponse(userQuery: String): String = withContext(Dispatchers.IO) {
        val normalized = ZeAILocalKnowledgeBase.normalizeQuery(userQuery)

        // 1. Tentar correspondência instantânea primeiro (exata)
        val instant = findInstantCachedResponse(userQuery)
        if (!instant.isNullOrBlank()) {
            return@withContext instant
        }

        // 2. Resposta contextual offline inteligente
        return@withContext ZeAILocalKnowledgeBase.buildSmartOfflineResponse(userQuery)
    }

    suspend fun cacheResponse(query: String, response: String) = withContext(Dispatchers.IO) {
        if (query.isBlank() || response.isBlank()) return@withContext
        val normalized = ZeAILocalKnowledgeBase.normalizeQuery(query)
        val category = ZeAILocalKnowledgeBase.detectCategory(query)

        // Store in L1 LRU Cache
        synchronized(memoryLruCache) {
            memoryLruCache.put(normalized, response.trim())
        }

        // Store in L2 Room DB
        try {
            val existing = cacheDao.findExactMatch(normalized)
            if (existing != null) {
                cacheDao.updateCache(
                    existing.copy(
                        response = response.trim(),
                        useCount = existing.useCount + 1,
                        lastUsedTimestamp = System.currentTimeMillis()
                    )
                )
            } else {
                cacheDao.insertCache(
                    ZeAICacheEntity(
                        normalizedQuery = normalized,
                        originalQuery = query.trim(),
                        category = category,
                        response = response.trim(),
                        useCount = 1,
                        lastUsedTimestamp = System.currentTimeMillis(),
                        isPrepopulated = false
                    )
                )
                try {
                    cacheDao.pruneOldCache(200)
                } catch (_: Exception) {}
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error saving response to cache", e)
        }
    }
}
