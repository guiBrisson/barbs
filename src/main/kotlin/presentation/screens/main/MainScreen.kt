package presentation.screens.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import presentation.designsystem.components.ThemeSwitch
import presentation.designsystem.theme.mainBackgroundColor
import presentation.utils.loadSvgPainter

class MainScreen(
    private val modifier: Modifier = Modifier,
) : Screen {

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<MainScreenModel>()
        val threadsUiState by screenModel.threadsUiState.collectAsState()
        val newThreadUiState by screenModel.newThreadUiState.collectAsState()

        LaunchedEffect(threadsUiState) {
            if (threadsUiState == ThreadsUiState.Init) {
                screenModel.fetchThreadList()
            }
        }

        Screen(
            modifier = modifier,
            threadsUiState = threadsUiState,
            newThreadUiState = newThreadUiState,
            onAddChat = screenModel::newThread,
        )
    }

    @Composable
    private fun Screen(
        modifier: Modifier = Modifier,
        threadsUiState: ThreadsUiState,
        newThreadUiState: NewThreadUiState,
        onAddChat: () -> Unit,
    ) {
        val mainSpace = 12.dp

        Row(
            modifier = modifier.background(mainBackgroundColor).padding(mainSpace),
            horizontalArrangement = Arrangement.spacedBy(mainSpace),
        ) {
            Sidebar(
                threadsUiState = threadsUiState,
                newThreadUiState = newThreadUiState,
                onAddChat = onAddChat,
                onThread = { /*TODO: navigate to thread screen*/ },
            )

            // Todo: move this to it's own screen
            Surface(modifier = Modifier.clip(RoundedCornerShape(12.dp)), color = MaterialTheme.colors.background) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(text = "OI")
                }
            }

        }
    }

    @Composable
    private fun Sidebar(
        modifier: Modifier = Modifier,
        threadsUiState: ThreadsUiState,
        newThreadUiState: NewThreadUiState,
        onAddChat: () -> Unit,
        onThread: (treadId: String) -> Unit,
    ) {
        var expanded by remember { mutableStateOf(true) }
        val columnWidth by animateDpAsState(if (expanded) 272.dp else 44.dp)
        val toggleExpand = { expanded = !expanded }

        Column(modifier = modifier.width(columnWidth)) {
            TopTitleComponent(expanded = expanded, toggleExpand = toggleExpand)

            if (threadsUiState.isLoading()) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth().height(8.dp).padding(vertical = 12.dp))
            }

            if (threadsUiState.isSuccess()) {
                ThreadList(
                    modifier = Modifier.weight(1f).padding(top = 24.dp),
                    expanded = expanded,
                    threadsUiState = threadsUiState as ThreadsUiState.Success,
                    onThread = onThread,
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }

            AddChatButton(
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                newThreadUiState = newThreadUiState,
                expanded = expanded,
                onAdd = onAddChat,
            )

            ThemeSwitch(modifier = Modifier.fillMaxWidth(), isExpanded = expanded)
        }
    }

    @Composable
    private fun TopTitleComponent(
        modifier: Modifier = Modifier,
        expanded: Boolean,
        toggleExpand: () -> Unit
    ) {
        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            val actionIcon =
                if (expanded) loadSvgPainter("icons/ic_sidebar_close.svg")
                else loadSvgPainter("icons/ic_sidebar_open.svg")

            AnimatedVisibility(
                visible = expanded,
                exit = ExitTransition.None,
                enter = fadeIn(),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = loadSvgPainter("icons/ic_ai_model.svg"),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary,
                    )
                    Text(
                        text = "Lorem Ipsum",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                }
            }

            if (expanded) Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = toggleExpand) {
                Icon(
                    painter = actionIcon,
                    contentDescription = null,
                    tint = Color(0xFF737373),
                )
            }
        }
    }

    @Composable
    private fun ThreadList(
        modifier: Modifier = Modifier,
        expanded: Boolean,
        threadsUiState: ThreadsUiState.Success,
        onThread: (treadId: String) -> Unit,
    ) {
        LazyColumn(modifier = modifier) {
            val list = threadsUiState.list

            item {
                val titleText = if (expanded) "Chat list" else "-"

                Text(
                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                    text = titleText,
                    textAlign = if (expanded) TextAlign.Start else TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color(0xFF737373),
                )
            }

            items(list) { thread ->
                Text(
                    modifier = Modifier.fillMaxWidth().clickable { onThread(thread.id) }.padding(12.dp),
                    text = thread.id,
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }

    @Composable
    private fun AddChatButton(
        modifier: Modifier = Modifier,
        newThreadUiState: NewThreadUiState,
        expanded: Boolean,
        onAdd: () -> Unit,
    ) {
        TextButton(
            modifier = modifier.heightIn(max = 40.dp),
            onClick = onAdd,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
            ),
            enabled = !newThreadUiState.isLoading(),
        ) {
            if (newThreadUiState.isLoading()) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeCap = StrokeCap.Round,
                    color = MaterialTheme.colors.onPrimary,
                    backgroundColor = MaterialTheme.colors.onPrimary.copy(alpha = 0.4f),
                )
            } else {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }

            AnimatedVisibility(
                visible = expanded,
                exit = ExitTransition.None,
                enter = fadeIn(),
            ) {
                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = "New Chat",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                )
            }
        }
    }

}
