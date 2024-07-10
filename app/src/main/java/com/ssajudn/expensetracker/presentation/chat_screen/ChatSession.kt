package com.ssajudn.expensetracker.presentation.chat_screen

import com.ssajudn.expensetracker.data.chat.Chat
import java.util.UUID

data class ChatSession(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val messages: List<Chat> = emptyList(),
    val lastUpdated: Long = System.currentTimeMillis(),
    val lastMessage: String = ""
)
