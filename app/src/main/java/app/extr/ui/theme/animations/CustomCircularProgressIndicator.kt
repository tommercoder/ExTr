package app.extr.ui.theme.animations

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.extr.R
import app.extr.ui.theme.LocalCustomColorsPalette
import kotlinx.coroutines.delay

@Composable
fun CustomCircularProgressIndicator(
    modifier: Modifier = Modifier,
    iconSize: Dp = 50.dp
) {
    val icons = listOf(
        R.drawable.card_icon,
        R.drawable.cash_icon,
        R.drawable.savings_icon,
        R.drawable.ewallet_icon
    )
    val colors = listOf(
        LocalCustomColorsPalette.current.balanceCardColor,
        LocalCustomColorsPalette.current.balanceCashColor,
        LocalCustomColorsPalette.current.balanceEWalletColor,
        LocalCustomColorsPalette.current.balanceSavingsColor,
    )
    var currentIndex by remember { mutableStateOf(0) }

    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    LaunchedEffect(rotation) {
        if (rotation >= 352f) {
            currentIndex = (currentIndex + 1) % icons.size
        }
    }

    Box(modifier = modifier) {
        Icon(
            painter = painterResource(icons[currentIndex]),
            contentDescription = null,
            modifier = Modifier
                .rotate(rotation)
                .size(iconSize),
            tint = colors[currentIndex]
        )
    }
}