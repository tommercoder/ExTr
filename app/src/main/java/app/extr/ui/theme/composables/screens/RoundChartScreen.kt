package app.extr.ui.theme.composables.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.extr.R
import app.extr.data.types.TransactionType
import app.extr.data.types.TransactionWithDetails
import app.extr.ui.theme.LocalCustomColorsPalette
import app.extr.ui.theme.animations.CustomCircularProgressIndicator
import app.extr.ui.theme.composables.reusablecomponents.ErrorScreen
import app.extr.ui.theme.composables.reusablecomponents.RoundedCard
import app.extr.utils.helpers.UiState
import app.extr.utils.helpers.resproviders.ResProvider

@Composable
fun RoundChartScreen(
    modifier: Modifier = Modifier,
    uiState: UiState<List<TransactionWithDetails>>,
    transactionsByTypes: Map<TransactionType, Double>,
    resProvider: ResProvider,
    onCardClicked: (TransactionType) -> Unit,
    onRefresh: () -> Unit
) {
    when (uiState) {

        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CustomCircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is UiState.Success -> {
            Column(
                modifier = modifier
            ) {
                val transactions = remember(uiState) { uiState.data }
                val elementWidth = 120.dp
                val elementHeight = elementWidth + 30.dp
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(13.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(transactionsByTypes.size) { index ->
                        val pair = transactionsByTypes.entries.elementAt(index)
                        val type = pair.key
                        val sum = pair.value
                        RoundedCard(
                            icon = resProvider.getRes(type.id).icon,
                            secondaryText = type.name,
                            color = resProvider.getRes(type.id).color,
                            currencySymbol = transactions[0].currency.symbol,
                            number = sum.toFloat(),
                            modifier = Modifier
                                .size(width = elementWidth, height = elementHeight),
                            onClick = { onCardClicked(transactions[index].transactionType) }
                        )
                    }
                }
            }
        }

        is UiState.Error -> {
            ErrorScreen(
                onRefresh = { onRefresh() },
                resourceId = uiState.resourceId
            )
        }
    }
}

//@Composable
//fun ProportionalCircularChart(numbers: List<Float>, colors: List<Color>) {
//    val total = numbers.sum()
//    val sweepAngles = numbers.map { (it / total) * 360f }
//
//    Box(contentAlignment = Alignment.Center) {
//        Canvas(modifier = Modifier.size(200.dp)) {
//            val strokeWidth = 40f
//            val radius = size.minDimension / 2 - strokeWidth / 2
//            val center = Offset(size.width / 2, size.height / 2)
//            var startAngle = -90f // Start from the top (12 o'clock)
//
//            sweepAngles.forEachIndexed { index, sweepAngle ->
//                drawArc(
//                    color = colors[index % colors.size],
//                    startAngle = startAngle,
//                    sweepAngle = sweepAngle,
//                    useCenter = false,
//                    topLeft = Offset(center.x - radius, center.y - radius),
//                    size = Size(radius * 2, radius * 2),
//                    style = Stroke(width = strokeWidth)
//                )
//                startAngle += sweepAngle
//            }
//        }
//        // Draw the amount text
//        Text(text = "$${"%.2f".format(total)}", style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold))
//    }
//}
//
//@Preview
//@Composable
//fun MyScreen() {
//    val numbers = listOf(500f, 300f, 200f, 604.49f)
//    val colors = listOf(Color.Magenta, Color.Yellow, Color.Cyan, Color.Green)
//
//    ProportionalCircularChart(numbers = numbers, colors = colors)
//}
