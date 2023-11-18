package presentation.screens.thread

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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

        Surface(modifier = Modifier.clip(RoundedCornerShape(12.dp)), color = MaterialTheme.colors.background) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                val text = threadId ?: "OI"
                Text(text)
            }
        }
    }

}