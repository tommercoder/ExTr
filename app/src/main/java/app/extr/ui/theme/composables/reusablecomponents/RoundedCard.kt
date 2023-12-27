package app.extr.ui.theme.composables.reusablecomponents

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
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
import app.extr.ui.theme.mt_card_color
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.extr.ui.theme.AppPadding

@Composable
fun RoundedCard(
    @DrawableRes icon: Int,
    text: String,
    secondaryText: String?,
    @ColorRes color: Color,
    currencySymbol: String, //Todo: change to CHAR
    number: Float,
    modifier: Modifier = Modifier
) {

    var padding by remember { mutableStateOf(0.dp) }
    var numberSize by remember{ mutableStateOf(0.sp) }
    Card(
        modifier = modifier
            .onSizeChanged { size->
            padding = (size.width * 0.01f).dp
        }
            //.padding(padding),
                ,
        colors = CardDefaults.cardColors(color),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Column(
            modifier = modifier.padding(padding)
        ) {
            Row(
                modifier = Modifier.weight(2f).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = Color.White)
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
                Text(
                    text = text,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = padding*2))
            }

            Row(
                modifier = Modifier.weight(2f)
                    .fillMaxWidth()
                    .onSizeChanged { size->
                    numberSize = (size.height * 0.15f).sp
                },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = currencySymbol,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = numberSize/2),
                    color = Color.Gray
                )
                Text(
                    text = number.toString(),
                    style = MaterialTheme.typography.titleLarge
                        .copy(fontSize = numberSize, fontWeight = FontWeight.Bold)
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
        secondaryText = null,
        color = mt_card_color,
        currencySymbol = "$",
        number = 25.123f,
        modifier = Modifier.size(180.dp, 180.dp)
    )
}