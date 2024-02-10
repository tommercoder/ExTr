package app.extr.utils.helpers

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import app.extr.ui.theme.AppPadding

@Composable
fun AnimatedText(
    targetValue: Float,
    textStyle: TextStyle,
    durationMillis: Int = 500
) {
    // State to hold the current animated value
    val animatedValue = remember { Animatable(0f) }

    // Launching a coroutine to animate the value
    LaunchedEffect(targetValue) {
        animatedValue.animateTo(
            targetValue,
            animationSpec = TweenSpec(
                durationMillis = durationMillis,
                easing = FastOutSlowInEasing // Using an easing function for a smoother animation
            )
        )
    }

    // Displaying the animated value in a Text composable with the given textStyle
    Text(
        text = "%.2f".format(animatedValue.value),
        style = textStyle
    )
}

@Composable
fun AnimatedTextWithSign(
    totalValue: Float,
    currencySign: Char,
    signStyle: TextStyle = MaterialTheme.typography.headlineLarge.copy(color = MaterialTheme.colorScheme.secondary),
    valueStyle: TextStyle = MaterialTheme.typography.displayLarge.copy(fontWeight = FontWeight.ExtraBold)
) {
    Row(
        modifier = Modifier
            .padding(top = AppPadding.Medium),
            //.align(Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(end = AppPadding.Small),
            text = currencySign.toString(),
            style = signStyle
        )
        AnimatedText(
            targetValue = totalValue,
            textStyle = valueStyle,
            durationMillis = 700
        )
    }
}