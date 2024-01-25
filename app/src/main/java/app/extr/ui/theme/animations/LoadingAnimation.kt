package app.extr.ui.theme.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.extr.R
import app.extr.ui.theme.CustomColorsPalette
import app.extr.ui.theme.LocalCustomColorsPalette
import kotlinx.coroutines.delay

@Composable
fun LoadingAnimation(
    modifier: Modifier = Modifier
) {
    // State for the current visible icon index
    var currentVisibleIconIndex by remember { mutableStateOf(0) }
    val icons = listOf(
        R.drawable.card_icon,
        R.drawable.cash_icon,
        R.drawable.savings_icon,
        R.drawable.ewallet_icon
    )
    val iconColors = listOf(
        LocalCustomColorsPalette.current.balanceCardColor,
        LocalCustomColorsPalette.current.balanceCashColor,
        LocalCustomColorsPalette.current.balanceEWalletColor,
        LocalCustomColorsPalette.current.balanceSavingsColor,
    )
    var shuffledColors by remember { mutableStateOf(iconColors.shuffled()) }
    LaunchedEffect(key1 = "animation") {
        while (true) {
            // Incrementally show each icon
            for (index in 0 until 4) {
                currentVisibleIconIndex = index
                delay(500) // Delay before the next icon appears
            }
            delay(1000) // Duration for which all icons are visible
            currentVisibleIconIndex = -1 // Reset (no icon visible)

            shuffledColors = iconColors.shuffled()
            delay(500) // Duration for which icons are invisible
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Row {


            icons.forEachIndexed { index, icon ->
                AnimatedVisibility(
                    visible = index <= currentVisibleIconIndex,
                    enter = fadeIn() + expandHorizontally(),
                    exit = fadeOut() + slideOutHorizontally()
                ) {
                    Icon(
                        modifier = Modifier.size(48.dp),
                        contentDescription = null,
                        painter = painterResource(id = icon),
                        tint = shuffledColors[index]
                    )
                }
            }
        }
    }
}