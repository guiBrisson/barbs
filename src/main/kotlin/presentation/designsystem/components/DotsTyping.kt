package presentation.designsystem.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DotsTyping(
    modifier: Modifier = Modifier,
    dotSize: Dp = 24.dp,
    delayUnit: Int = 300,
    maxOffset: Float = 10f,
) {
    val infiniteTransition = rememberInfiniteTransition()


    val offset1 by animateOffsetWithDelay(0, delayUnit, maxOffset, infiniteTransition)
    val offset2 by animateOffsetWithDelay(delayUnit, delayUnit, maxOffset, infiniteTransition)
    val offset3 by animateOffsetWithDelay(delayUnit * 2, delayUnit, maxOffset, infiniteTransition)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.padding(top = maxOffset.dp)
    ) {
        val spaceSize = 2.dp
        Dot(offset1, dotSize)
        Spacer(Modifier.width(spaceSize))
        Dot(offset2, dotSize)
        Spacer(Modifier.width(spaceSize))
        Dot(offset3, dotSize)
    }
}

@Composable
private fun Dot(offset: Float, dotSize: Dp) = Spacer(
    Modifier
        .size(dotSize)
        .offset(y = -offset.dp)
        .background(
            color = MaterialTheme.colors.primary,
            shape = CircleShape,
        )
)

@Composable
fun animateOffsetWithDelay(
    delay: Int,
    delayUnit: Int,
    maxOffset: Float,
    infiniteTransition: InfiniteTransition
) = infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 0f,
    animationSpec = infiniteRepeatable(
        animation = keyframes {
            durationMillis = delayUnit * 4
            0f at delay with LinearEasing
            maxOffset at delay + delayUnit with LinearEasing
            0f at delay + delayUnit * 2
        }
    )
)