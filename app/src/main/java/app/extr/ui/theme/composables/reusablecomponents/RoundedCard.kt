package app.extr.ui.theme.composables.reusablecomponents

import android.view.MotionEvent
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import app.extr.R
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.extr.ui.theme.AppPadding
import com.example.compose.md_theme_light_primary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun RoundedCard(
    @DrawableRes icon: Int,
    text: String,
    @ColorRes color: Color,
    currencySymbol: Char,
    number: Float,
    modifier: Modifier = Modifier,
    secondaryText: String = "",
    onClick: (() -> Unit)? = null,
    onLongPress: (() -> Unit)? = null
) {
    var padding by remember { mutableStateOf(0.dp) }
    var numberSize by remember { mutableStateOf(0.sp) }
    var isLongPressed by remember { mutableStateOf(false) }
    var isLongPressDetected by remember { mutableStateOf(false) }
    val coroutineScope =  rememberCoroutineScope()

    val backgroundColor by animateColorAsState(
        targetValue = if (isLongPressed) Color.LightGray else color, //todo: works for dark mode?
        animationSpec = tween(durationMillis = 100)
    )

    val combinedModifier = modifier
        .onSizeChanged { size ->
            padding = (size.width * 0.01f).dp
        }
        .let {
            if (onClick != null) {
                it.clickable {
                    onClick() }
            } else it
        }
        .let {
            if (onLongPress != null) {
                it.pointerInteropFilter { motionEvent ->
                    when (motionEvent.action) {
                        MotionEvent.ACTION_DOWN -> {
                            isLongPressed = true
                            coroutineScope.launch {
                                delay(500) // Long Press Threshold
                                if (isLongPressed) {
                                    onLongPress?.invoke()
                                }
                            }
                        }
                        MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                            isLongPressed = false
                        }
                    }
                    true
                }
            } else it
        }
    //.background(backgroundColor)

    Card(
        colors = CardDefaults.cardColors(backgroundColor),
        shape = MaterialTheme.shapes.extraLarge,
        modifier = combinedModifier,
    ) {
        Column(
            modifier = modifier.padding(padding)
        ) {
            Row(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.onPrimary)
                        .aspectRatio(1f)
                        .padding(padding),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        modifier = Modifier.aspectRatio(0.5f)
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    //horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = text,
                        modifier = Modifier
                            //.align(Alignment.CenterVertically)
                            .padding(start = padding * 2)
                    )

                    Text(
                        text = secondaryText,
                        modifier = Modifier
                            //.align(Alignment.CenterVertically)
                            .padding(start = padding * 2)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()
                    .onSizeChanged { size ->
                        numberSize = (size.height * 0.11f).sp
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(end = AppPadding.ExtraSmall),
                    text = currencySymbol.toString(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = numberSize / 2,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                )
                Text(
                    text = number.toString(),
                    style = MaterialTheme.typography.titleLarge
                        .copy(fontSize = numberSize, fontWeight = FontWeight.Medium)
                )
            }
        }
    }
}

@Preview
@Composable
fun RoundedCardPreview() {
    RoundedCard(
        icon = R.drawable.card_icon,
        text = "Card",
        secondaryText = "blabla",
        color = md_theme_light_primary,
        currencySymbol = '$',
        number = 25.123f,
        modifier = Modifier.size(180.dp, 180.dp)
    )
}