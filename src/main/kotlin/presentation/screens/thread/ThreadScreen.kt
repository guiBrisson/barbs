package presentation.screens.thread

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import kotlinx.coroutines.launch
import model.message.Message
import presentation.designsystem.components.DotsTyping
import presentation.designsystem.theme.ThemeState
import presentation.designsystem.theme.dark_stroke
import presentation.designsystem.theme.light_stroke
import presentation.utils.isScrolledToEnd
import presentation.utils.loadSvgPainter
import presentation.utils.scrollToTheBottom

class ThreadScreen(
    private val threadId: String?,
) : Screen {

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<ThreadScreenModel>()
        val messagesUiState by screenModel.messagesUiState.collectAsState()
        val completionUiState by screenModel.completionUiState.collectAsState()

        LaunchedEffect(threadId) {
            screenModel.threadId = threadId

            threadId?.let { id ->
                screenModel.fetchMessages(id)
            }
        }

        Surface(modifier = Modifier.clip(RoundedCornerShape(12.dp)), color = MaterialTheme.colors.background) {
            Screen(
                modifier = Modifier.fillMaxSize(),
                threadId = threadId,
                messagesUiState = messagesUiState,
                completionUiState = completionUiState,
                onSendMessage = screenModel::addMessage,
            )
        }
    }

    @Composable
    private fun Screen(
        modifier: Modifier = Modifier,
        threadId: String?,
        messagesUiState: MessagesUiState,
        completionUiState: CompletionUiState,
        onSendMessage: (prompt: String) -> Unit,
    ) {
        //Todo: Add scroll bar
        var prompt by remember { mutableStateOf("") }

        val scrollState = rememberLazyListState()
        val endOfListReached by remember { derivedStateOf { scrollState.isScrolledToEnd() } }

        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            LaunchedEffect(messagesUiState) {
                scrollToTheBottom(messagesUiState.messages.size, scrollState)
            }

            if (!threadId.isNullOrEmpty()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                        Text(text = threadId, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    }
                    val strokeColor = if (ThemeState.isDark) dark_stroke else light_stroke

                    if (messagesUiState.loading) {
                        LinearProgressIndicator(modifier = Modifier.height(1.dp).fillMaxWidth())
                    } else {
                        Box(modifier = Modifier.height(1.dp).background(strokeColor).fillMaxWidth())
                    }
                }
            }

            if (!threadId.isNullOrEmpty()) {
                Box(
                    modifier = Modifier.padding(horizontal = 20.dp).weight(1f).widthIn(max = 820.dp),
                ) {

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        state = scrollState,
                        verticalArrangement = Arrangement.Bottom,
                    ) {

                        if (messagesUiState.messages.isNotEmpty()) {
                            items(messagesUiState.messages.reversed()) { message ->
                                MessageItem(modifier = Modifier.padding(vertical = 16.dp), message = message)
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.padding(bottom = 20.dp))
                        }
                    }

                    if (!endOfListReached && !messagesUiState.loading) {
                        ScrollDownFloatingButton(
                            modifier = Modifier.align(Alignment.BottomEnd),
                            messagesUiState = messagesUiState,
                            scrollState = scrollState,
                        )
                    }

                }
            }

            if (!threadId.isNullOrEmpty()) {
                Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp).widthIn(max = 820.dp)) {
                    if (completionUiState.isInProgress()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.Bottom,
                        ) {
                            val progressMessage = (completionUiState as CompletionUiState.InProgress).message

                            DotsTyping(dotSize = 8.dp)

                            Text(
                                modifier = Modifier.padding(start = 12.dp),
                                text = progressMessage,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colors.onBackground.copy(alpha = 0.4f)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        val strokeColor = if (ThemeState.isDark) dark_stroke else light_stroke

                        OutlinedTextField(
                            modifier = Modifier.weight(1f),
                            value = prompt,
                            onValueChange = { prompt = it },
                            label = {
                                Text(text = "Ask me something...", fontSize = 14.sp)
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = strokeColor
                            ),
                            shape = RoundedCornerShape(8.dp),
                            textStyle = TextStyle(fontSize = 14.sp),
                        )

                        IconButton(onClick = {
                            if (prompt.isNotEmpty() && !completionUiState.isInProgress()) {
                                onSendMessage(prompt)
                                prompt = ""
                            }
                        }) {
                            Icon(imageVector = Icons.Default.Send, contentDescription = null)
                        }
                    }

                }
            }

        }
    }

    @Composable
    private fun ScrollDownFloatingButton(
        modifier: Modifier = Modifier,
        messagesUiState: MessagesUiState,
        scrollState: LazyListState
    ) {
        val scope = rememberCoroutineScope()

        Box(
            modifier = modifier
                .clip(RoundedCornerShape(8.dp))
                .size(36.dp)
                .clickable {
                    scope.launch {
                        scrollToTheBottom(itemListSize = messagesUiState.messages.size, scrollState = scrollState)
                    }
                }
                .background(MaterialTheme.colors.primary),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = MaterialTheme.colors.onPrimary,
            )
        }
    }


    @Composable
    private fun MessageItem(modifier: Modifier = Modifier, message: Message) {
        SelectionContainer {
            Column(modifier = modifier.fillMaxWidth()) {
                val isAssistant = message.role.contains("assistant")

                Row(
                    modifier = Modifier.padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    if (isAssistant) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(MaterialTheme.colors.primary),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                modifier = Modifier.padding(4.dp).size(16.dp),
                                painter = loadSvgPainter("icons/ic_ai_model.svg"),
                                contentDescription = null,
                                tint = MaterialTheme.colors.onPrimary
                            )
                        }
                    }

                    Text(
                        text = if (isAssistant) "Assistant" else "You",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }
                val joinedMessages = message.content.joinToString("/n") { it.text.value }

                Text(text = joinedMessages, fontSize = 14.sp)
            }
        }
    }


}