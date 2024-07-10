package com.ssajudn.expensetracker.domain.repository

import com.ssajudn.expensetracker.data.chat.Chat
import com.ssajudn.expensetracker.presentation.chat_screen.ChatSession

interface ChatRepository {
    suspend fun getAllSessions(): List<ChatSession>
    suspend fun getChatsForSession(sessionId: String): List<Chat>
    suspend fun insertSession(session: ChatSession)
    suspend fun insertChat(sessionId: String, chat: Chat)
    suspend fun deleteSession(sessionId: String)
    suspend fun updateSession(session: ChatSession)
}