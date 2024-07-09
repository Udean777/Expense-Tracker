package com.ssajudn.expensetracker.presentation.chat_screen

import android.graphics.Bitmap
import com.ssajudn.expensetracker.data.chat.Chat

data class ChatState (
    val chatList: MutableList<Chat> = mutableListOf(),
    val prompt: String = "",
    val bitmap: Bitmap? = null
)