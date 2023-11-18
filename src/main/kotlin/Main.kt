import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import cafe.adriel.voyager.navigator.Navigator
import data.di.dataModule
import org.koin.core.context.startKoin
import presentation.designsystem.theme.AppTheme
import presentation.di.viewModelModule
import presentation.screens.splash.SplashScreen

@Composable
fun App() {
    AppTheme {
        Navigator(SplashScreen(modifier = Modifier.fillMaxSize()))
    }
}

fun main() = application {
    val windowState = rememberWindowState(size = DpSize(width = 1280.dp, height = 832.dp))

    startKoin {
        modules(*dataModule, viewModelModule)
    }

    Window(onCloseRequest = ::exitApplication, state = windowState) {
        App()
    }
}
