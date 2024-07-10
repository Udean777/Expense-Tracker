package com.ssajudn.expensetracker.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssajudn.expensetracker.presentation.chat_screen.ChatSession

@Composable
fun ChatDrawer(
    chatSessions: List<ChatSession>,
    onSessionSelected: (String) -> Unit,
    onNewChatClicked: () -> Unit,
) {
    Column {
        Text(
            text = "Chat History",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.titleLarge
        )

        LazyColumn {
            items(chatSessions.sortedByDescending { it.lastUpdated }) { session ->
                Text(text = session.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSessionSelected(session.id) }
                        .padding(16.dp)
                )
            }
        }

        Button(
            onClick = onNewChatClicked, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "New Chat")
        }
    }
}