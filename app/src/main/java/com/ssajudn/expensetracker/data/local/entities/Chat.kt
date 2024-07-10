package com.ssajudn.expensetracker.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatSessionEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val lastUpdated: Long,
    val lastMessage: String
)

@Entity
data class ChatEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val sessionId: String,
    val prompt: String,
    val isFromUser: Boolean,
    val timestamp: Long
)