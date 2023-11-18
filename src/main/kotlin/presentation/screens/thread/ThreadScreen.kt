package presentation.screens.thread

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel

class ThreadScreen(
    private val threadId: String?,
) : Screen {

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<ThreadScreenModel>()
        var prompt by remember { mutableStateOf("") }

        LaunchedEffect(threadId) {
            screenModel.threadId = threadId
        }

        Surface(modifier = Modifier.clip(RoundedCornerShape(12.dp)), color = MaterialTheme.colors.background) {
            Column(modifier = Modifier.fillMaxSize()) {
                val text = threadId ?: "OI"
                Text(text)

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.padding(20.dp).fillMaxWidth().widthIn(max = 820.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    OutlinedTextField(
                        value = prompt,
                        onValueChange = { prompt = it },
                    )

                    IconButton(onClick = {
                        if (prompt.isNotEmpty()){
                            screenModel.addMessage(prompt)
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