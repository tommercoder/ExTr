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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import app.extr.ui.theme.AppPadding

@Composable
fun AnimatedTextWithSign(
    totalValue: Double,
    currencySign: Char,
    format: String = Constants.precisionTwo,
    valueStyle: TextStyle = MaterialTheme.typography.displayLarge.copy(fontWeight = FontWeight.ExtraBold)
) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(end = AppPadding.ExtraSmall),
            text = currencySign.toString(),
            style = TextStyle(
                color = MaterialTheme.colorScheme.secondary,
                fontSize = valueStyle.fontSize / 1.7,
                baselineShift = BaselineShift.Subscript // bottom alignment
            )
        )
        AnimatedText(
            targetValue = totalValue,
            textStyle = valueStyle,
            format = format,
            durationMillis = 700
        )
    }
}

@Composable
fun AnimatedText(
    targetValue: Double,
    textStyle: TextStyle,
    format: String = Constants.precisionTwo,
    durationMillis: Int = 500
) {
    val animatedValue = remember { Animatable(0f) }
    LaunchedEffect(targetValue) {
        animatedValue.animateTo(
            targetValue.toFloat(),
            animationSpec = TweenSpec(
                durationMillis = durationMillis,
                easing = FastOutSlowInEasing
            )
        )
    }

    Text(
        text = format.format(animatedValue.value),
        style = textStyle
    )
}