package com.ssajudn.expensetracker.data.repository

import com.ssajudn.expensetracker.data.chat.Chat
import com.ssajudn.expensetracker.data.local.ChatDao
import com.ssajudn.expensetracker.data.local.entities.ChatEntity
import com.ssajudn.expensetracker.data.local.entities.ChatSessionEntity
import com.ssajudn.expensetracker.domain.repository.ChatRepository
import com.ssajudn.expensetracker.presentation.chat_screen.ChatSession
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatDao: ChatDao
) : ChatRepository{
    override suspend fun getAllSessions(): List<ChatSession> {
        return chatDao.getAllSession().map { entity ->
            ChatSession(
                id = entity.id,
                name = entity.name,
                lastUpdated = entity.lastUpdated,
                lastMessage = entity.lastMessage
            )
        }
    }

    override suspend fun getChatsForSession(sessionId: String): List<Chat> {
        return chatDao.getChatsForSession(sessionId).map { entity ->
            Chat(
                prompt = entity.prompt,
                isFromUser = entity.isFromUser,
                bitmap = null
            )
        }
    }

    override suspend fun insertSession(session: ChatSession) {
        chatDao.insertSession(
            ChatSessionEntity(
                id = session.id,
                name = session.name,
                lastUpdated = session.lastUpdated,
                lastMessage = session.lastMessage
            )
        )
    }

    override suspend fun insertChat(sessionId: String,chat: Chat) {
        chatDao.insertChat(
            ChatEntity(
                sessionId = sessionId,
                prompt = chat.prompt,
                isFromUser = chat.isFromUser,
                timestamp = System.currentTimeMillis()
            )
        )
    }

    override suspend fun deleteSession(sessionId: String) {
        chatDao.deleteSession(sessionId)
        chatDao.deleteChatsForSession(sessionId)
    }

    override suspend fun updateSession(session: ChatSession) {
        chatDao.insertSession(
            ChatSessionEntity(
                id = session.id,
                name = session.name,
                lastUpdated = session.lastUpdated,
                lastMessage = session.lastMessage
            )
        )
    }
}