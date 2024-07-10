package com.ssajudn.expensetracker.presentation.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssajudn.expensetracker.data.chat.Chat
import com.ssajudn.expensetracker.data.chat.ChatData
import com.ssajudn.expensetracker.presentation.chat_screen.ChatSession
import com.ssajudn.expensetracker.presentation.chat_screen.ChatState
import com.ssajudn.expensetracker.presentation.chat_screen.ChatUiEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val _chatState = MutableStateFlow(ChatState())
    val chatState = _chatState.asStateFlow()

    private val _chatSessions = MutableStateFlow<List<ChatSession>>(emptyList())
    val chatSession = _chatSessions.asStateFlow()

    private val _currentSessionId = MutableStateFlow<String?>(null)
    val currentSessionId = _currentSessionId.asStateFlow()

    fun createNewSession() {
        val newSession = ChatSession(
            name = "New Chat ${_chatSessions.value.size + 1}"
        )
        _chatSessions.update { it + newSession }
        _currentSessionId.update { newSession.id }
        _chatState.value = ChatState()
    }

    fun selectSession(sessionId: String) {
        _currentSessionId.update { sessionId }
        val session = _chatSessions.value.find { it.id == sessionId }
        _chatState.update {
            it.copy(
                chatList = session?.messages ?: emptyList(),
                prompt = session?.lastMessage ?: ""
            )
        }
    }

    fun onEvent(event: ChatUiEvent) {
        when (event) {
            is ChatUiEvent.SendPrompt -> {
                if (event.prompt.isNotEmpty()) {
                    addPrompt(event.prompt, event.bitmap)

                    if (event.bitmap != null) {
                        getResponseWithImage(event.prompt, event.bitmap)
                    } else {
                        getResponse(event.prompt)
                    }
                }
            }

            is ChatUiEvent.UpdatePrompt -> {
                _chatState.update {
                    it.copy(prompt = event.newPrompt)
                }
            }
        }
    }

    private fun addPrompt(prompt: String, bitmap: Bitmap?) {
        val newChat = Chat(prompt, bitmap, true)
        updateCurrentSession { session ->
            session.copy(
                messages = session.messages + newChat,
                lastUpdated = System.currentTimeMillis()
            )
        }
        _chatState.update {
            it.copy(
                chatList = it.chatList + newChat,
                prompt = "",
                bitmap = null
            )
        }
    }

    private fun getResponse(prompt: String) {
        viewModelScope.launch {
            val chat = ChatData.getResponse(prompt)
            _chatState.update {
                it.copy(
                    chatList = it.chatList.toMutableList().apply {
                        add(0, chat)
                    },
                )
            }
            updateCurrentSession { session ->
                session.copy(
                    messages = listOf(chat) + session.messages,
                    lastUpdated = System.currentTimeMillis()
                )
            }
        }
    }

    private fun getResponseWithImage(prompt: String, bitmap: Bitmap) {
        viewModelScope.launch {
            val chat = ChatData.getResponseWithImage(prompt, bitmap)
            _chatState.update {
                it.copy(
                    chatList = it.chatList.toMutableList().apply {
                        add(0, chat)
                    },
                )
            }

            updateCurrentSession { session ->
                session.copy(
                    messages = listOf(chat) + session.messages,
                    lastUpdated = System.currentTimeMillis()
                )
            }
        }
    }

    private fun updateCurrentSession(update: (ChatSession) -> ChatSession) {
        _chatSessions.update { sessions ->
            sessions.map { session ->
                if (session.id == _currentSessionId.value) {
                    val updatedSession = update(session)
                    updatedSession.copy(
                        lastMessage = updatedSession.messages.lastOrNull()?.prompt ?: ""
                    )
                } else session
            }
        }
    }
}