package presentation.screens.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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

        Screen(
            modifier = modifier,
            onAddChat = { /*TODO*/ },
        )
    }

    @Composable
    private fun Screen(
        modifier: Modifier = Modifier,
        onAddChat: () -> Unit,
    ) {
        val mainSpace = 12.dp

        Row(
            modifier = modifier.background(mainBackgroundColor).padding(mainSpace),
            horizontalArrangement = Arrangement.spacedBy(mainSpace),
        ) {
            Sidebar(onAddChat = onAddChat)

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
        onAddChat: () -> Unit,
    ) {
        var expanded by remember { mutableStateOf(true) }
        val columnWidth by animateDpAsState(if (expanded) 272.dp else 44.dp)
        val toggleExpand = { expanded = !expanded }

        Column(modifier = modifier.width(columnWidth)) {
            TopTitleComponent(expanded = expanded, toggleExpand = toggleExpand)

            Spacer(modifier = Modifier.weight(1f))

            AddChatButton(
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                expanded = expanded,
                onAdd = onAddChat,
            )

            ThemeSwitch(modifier = Modifier.fillMaxWidth(), isExpanded = expanded)
        }
    }

    @Composable
    private fun TopTitleComponent(expanded: Boolean, toggleExpand: () -> Unit) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
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
    private fun AddChatButton(
        modifier: Modifier = Modifier,
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
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)

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
