package com.ssajudn.expensetracker.presentation.chat_screen

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.AddPhotoAlternate
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.ssajudn.expensetracker.presentation.components.ChatDrawer
import com.ssajudn.expensetracker.presentation.viewmodel.ChatViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen() {
    val uriState = remember { MutableStateFlow<Uri?>(null) }
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uriState.value = uri
        }
    )
    val chatViewModel = viewModel<ChatViewModel>()
    val chatState by chatViewModel.chatState.collectAsState()
    val chatSessions by chatViewModel.chatSession.collectAsState()
    val currentSessionId by chatViewModel.currentSessionId.collectAsState()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val bitmap = getBitmap(uriState.collectAsState().value)

    val focusRequester = remember {
        FocusRequester()
    }
    val textFieldValue = remember {
        mutableStateOf(TextFieldValue(chatState.prompt))
    }

    LaunchedEffect(key1 = currentSessionId) {
        textFieldValue.value = TextFieldValue(chatState.prompt, TextRange(chatState.prompt.length))
        focusRequester.requestFocus()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ChatDrawer(
                chatSessions = chatSessions,
                onSessionSelected = { sessionId ->
                    chatViewModel.selectSession(sessionId)
                    scope.launch { drawerState.close() }
                },
                onNewChatClicked = {
                    chatViewModel.createNewSession()
                    scope.launch { drawerState.open() }
                }
            )
        },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Chat")
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Drawer Menu"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { chatViewModel.createNewSession() }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add new chat"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        reverseLayout = true
                    ) {
                        itemsIndexed(chatState.chatList) { index, chat ->
                            if (chat.isFromUser) {
                                UserChatItem(
                                    prompt = chat.prompt, bitmap = chat.bitmap
                                )
                            } else {
                                ModelChatItem(response = chat.prompt)
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp, start = 4.dp, end = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column {
                            bitmap?.let {
                                Image(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .padding(bottom = 2.dp)
                                        .clip(RoundedCornerShape(6.dp)),
                                    contentDescription = "picked image",
                                    contentScale = ContentScale.Crop,
                                    bitmap = it.asImageBitmap()
                                )
                            }

                            Icon(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clickable {
                                        imagePicker.launch(
                                            PickVisualMediaRequest
                                                .Builder()
                                                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                                .build()
                                        )
                                    },
                                imageVector = Icons.Rounded.AddPhotoAlternate,
                                contentDescription = "Add Photo",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        TextField(
                            modifier = Modifier
                                .weight(1f)
                                .focusRequester(focusRequester),
                            value = textFieldValue.value,
                            onValueChange = {
                                textFieldValue.value = it
                                chatViewModel.onEvent(ChatUiEvent.UpdatePrompt(it.text))
                            },
                            placeholder = {
                                Text(text = "Type a prompt")
                            }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Icon(
                            modifier = Modifier
                                .size(40.dp)
                                .clickable {
                                    chatViewModel.onEvent(
                                        ChatUiEvent.SendPrompt(
                                            chatState.prompt,
                                            bitmap
                                        )
                                    )
                                    uriState.update { null }
                                },
                            imageVector = Icons.Rounded.Send,
                            contentDescription = "Send prompt",
                            tint = MaterialTheme.colorScheme.primary
                        )

                    }

                }
            }
        }
    }
}

@Composable
fun UserChatItem(prompt: String, bitmap: Bitmap?) {
    Column(
        modifier = Modifier.padding(start = 100.dp, bottom = 16.dp)
    ) {

        bitmap?.let {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .padding(bottom = 2.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                bitmap = it.asImageBitmap()
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp),
            text = prompt,
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )

    }
}

@Composable
fun ModelChatItem(response: String) {
    Column(
        modifier = Modifier.padding(end = 100.dp, bottom = 16.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.secondary)
                .padding(16.dp),
            text = response,
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )

    }
}

@Composable
private fun getBitmap(uri: Uri?): Bitmap? {
    val context = LocalContext.current
    val imageState: AsyncImagePainter.State = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(uri)
            .size(Size.ORIGINAL)
            .build(),
    ).state

    if (imageState is AsyncImagePainter.State.Success) {
        return imageState.result.drawable.toBitmap()
    }

    return null
}
