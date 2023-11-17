package presentation.designsystem.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.designsystem.theme.ThemeState
import presentation.designsystem.theme.dark_background
import presentation.designsystem.theme.mainBackgroundColor
import presentation.utils.loadSvgPainter

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ThemeSwitch(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
) {
    var isDarkMode by remember { mutableStateOf(ThemeState.isDark) }
    val setDarkMode: (Boolean) -> Unit = { isDark ->
        isDarkMode = isDark
        ThemeState.isDark = isDark
    }
    val innerModifier =
        modifier.clip(RoundedCornerShape(8.dp)).background(dark_background).padding(4.dp)

    AnimatedContent(isExpanded, ) { expanded ->
        if (expanded) {
            ExtendedThemeSwitch(
                modifier = innerModifier,
                isDarkMode = isDarkMode,
                setDarkMode = setDarkMode,
            )
        } else {
            CompactThemeSwitch(
                modifier = innerModifier,
                isDarkMode = isDarkMode,
                setDarkMode = setDarkMode,
            )
        }
    }
}

@Composable
private fun ExtendedThemeSwitch(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean,
    setDarkMode: (Boolean) -> Unit,
) {
    Row(
        modifier = modifier.background(dark_background).clip(RoundedCornerShape(8.dp)),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Light Button
        ThemeSwitchButton(
            modifier = Modifier.weight(1f),
            onClick = { setDarkMode(false) },
            selected = !isDarkMode,
        ) {
            val contentColor = if (isDarkMode) Color.White.copy(alpha = 0.4f) else Color.White

            Icon(
                modifier = Modifier.padding(end = 16.dp).size(20.dp),
                painter = loadSvgPainter("icons/ic_sun.svg"),
                contentDescription = null,
                tint = contentColor
            )

            Text(text = "Light", fontWeight = FontWeight.Medium, fontSize = 16.sp, color = contentColor)
        }

        // Dark Button
        ThemeSwitchButton(
            modifier = Modifier.weight(1f),
            onClick = { setDarkMode(true) },
            selected = isDarkMode,
        ) {
            val contentColor = if (isDarkMode) Color.White else Color.White.copy(alpha = 0.4f)

            Icon(
                modifier = Modifier.padding(end = 16.dp).size(20.dp),
                painter = loadSvgPainter("icons/ic_moon.svg"),
                contentDescription = null,
                tint = contentColor
            )

            Text(text = "Dark", fontWeight = FontWeight.Medium, fontSize = 16.sp, color = contentColor)
        }
    }
}

@Composable
fun CompactThemeSwitch(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean,
    setDarkMode: (Boolean) -> Unit,
) {

    val iconPainter = if (isDarkMode) {
        loadSvgPainter("icons/ic_moon.svg")
    } else {
        loadSvgPainter("icons/ic_sun.svg")
    }

    Box(
        modifier = modifier.background(dark_background).clip(RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center,
    ) {
        ThemeSwitchButton(
            onClick = { setDarkMode(!isDarkMode) },
            selected = false,
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = iconPainter,
                contentDescription = null,
                tint = Color.White,
            )
        }
    }
}

@Composable
private fun ThemeSwitchButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean,
    content: @Composable RowScope.() -> Unit
) {
    val backgroundColor = if (selected) mainBackgroundColor else Color.Unspecified

    Row(
        modifier = modifier
            .background(backgroundColor)
            .clickable { onClick() }
            .clip(RoundedCornerShape(8.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        content = content,
    )
}
