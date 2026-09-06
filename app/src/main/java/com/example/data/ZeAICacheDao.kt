package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ZeAICacheDao {
    @Query("SELECT * FROM ze_ai_cache WHERE normalizedQuery = :query LIMIT 1")
    suspend fun findExactMatch(query: String): ZeAICacheEntity?

    @Query("SELECT * FROM ze_ai_cache WHERE normalizedQuery LIKE '%' || :keyword || '%' ORDER BY useCount DESC, RANDOM() LIMIT 1")
    suspend fun findKeywordMatch(keyword: String): ZeAICacheEntity?

    @Query("SELECT * FROM ze_ai_cache WHERE category = :category ORDER BY RANDOM() LIMIT 1")
    suspend fun findRandomByCategory(category: String): ZeAICacheEntity?

    @Query("SELECT * FROM ze_ai_cache ORDER BY RANDOM() LIMIT 1")
    suspend fun findRandomFallback(): ZeAICacheEntity?

    @Query("SELECT COUNT(*) FROM ze_ai_cache")
    suspend fun getCacheCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCache(item: ZeAICacheEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(items: List<ZeAICacheEntity>)

    @Update
    suspend fun updateCache(item: ZeAICacheEntity)

    @Query("DELETE FROM ze_ai_cache WHERE isPrepopulated = 0 AND id NOT IN (SELECT id FROM ze_ai_cache WHERE isPrepopulated = 0 ORDER BY lastUsedTimestamp DESC LIMIT :keepCount)")
    suspend fun pruneOldCache(keepCount: Int = 200)

    @Query("SELECT * FROM ze_ai_cache ORDER BY lastUsedTimestamp DESC LIMIT :limit")
    fun getRecentCachedResponses(limit: Int = 50): Flow<List<ZeAICacheEntity>>
}
