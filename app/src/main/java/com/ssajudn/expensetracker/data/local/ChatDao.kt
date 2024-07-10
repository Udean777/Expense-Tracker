package com.ssajudn.expensetracker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssajudn.expensetracker.data.local.entities.ChatEntity
import com.ssajudn.expensetracker.data.local.entities.ChatSessionEntity

@Dao
interface ChatDao {
    @Query("SELECT * FROM ChatSessionEntity ORDER BY lastUpdated DESC")
    suspend fun getAllSession(): List<ChatSessionEntity>

    @Query("SELECT * FROM ChatEntity WHERE sessionId = :sessionId ORDER BY timestamp ASC")
    suspend fun getChatsForSession(sessionId: String): List<ChatEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: ChatSessionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chat: ChatEntity)

    @Query("DELETE FROM ChatSessionEntity WHERE id = :sessionId")
    suspend fun deleteSession(sessionId: String)

    @Query("DELETE FROM ChatEntity WHERE sessionId = :sessionId")
    suspend fun deleteChatsForSession(sessionId: String)
}