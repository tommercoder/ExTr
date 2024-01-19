package app.extr.utils.helpers
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.TweenSpec
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

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
            animationSpec = TweenSpec(durationMillis)
        )
    }

    // Displaying the animated value in a Text composable with the given textStyle
    Text(
        text = "%.2f".format(animatedValue.value),
        style = textStyle
    )
}