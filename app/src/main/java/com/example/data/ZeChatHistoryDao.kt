package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ZeChatHistoryDao {
    @Query("SELECT * FROM ze_chat_history ORDER BY timestamp DESC")
    fun getAllHistory(): Flow<List<ZeChatHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(item: ZeChatHistoryEntity): Long

    @Query("DELETE FROM ze_chat_history WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM ze_chat_history")
    suspend fun deleteAll()
}
