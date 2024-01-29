package app.extr.utils.helpers

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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