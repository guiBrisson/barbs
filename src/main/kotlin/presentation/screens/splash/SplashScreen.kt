package presentation.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import presentation.designsystem.theme.mainBackgroundColor
import presentation.screens.main.MainScreen
import presentation.utils.loadSvgPainter

class SplashScreen(
    private val modifier: Modifier = Modifier,
) : Screen {

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<SplashScreenModel>()
        val assistantUiState by screenModel.assistantUiState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(assistantUiState) {
            if (assistantUiState == AssistantUiState.Init) { // Fetching assistants when it's on init state
                screenModel.fetchAssistants()
            } else if (assistantUiState.isSuccess()) {       // Navigating to MainScreen when it's successful
                navigator.push(MainScreen(modifier = Modifier.fillMaxSize()))
            }
        }

        Screen(
            modifier = modifier,
            assistantUiState = assistantUiState,
            fetchAssistant = screenModel::fetchAssistants
        )
    }

    @Composable
    private fun Screen(
        modifier: Modifier = Modifier,
        assistantUiState: AssistantUiState,
        fetchAssistant: () -> Unit,
    ) {

        Box(modifier = modifier.fillMaxSize().background(mainBackgroundColor), contentAlignment = Alignment.Center) {
            Column(
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        painter = loadSvgPainter("icons/ic_ai_model.svg"),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary,
                    )
                    Text(
                        text = "Lorem Ipsum",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                }

                if (assistantUiState.isLoading()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        LinearProgressIndicator(
                            modifier = Modifier.width(400.dp).height(8.dp),
                            strokeCap = StrokeCap.Round,
                        )
                        Text(text = "Fetching assistant", fontSize = 12.sp, color = Color(0xFF737373))
                    }
                } else if (assistantUiState.hasError()) {
                    val errorMessage = (assistantUiState as AssistantUiState.Error).message
                    println("ERROR: Fetching assistant: $errorMessage")
                    //Todo: error component
                }

            }
        }

    }
}