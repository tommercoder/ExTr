package app.extr.ui.theme.composables.screens

import androidx.compose.ui.geometry.Size
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
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.extr.R
import app.extr.data.types.TransactionType
import app.extr.data.types.TransactionWithDetails
import app.extr.ui.theme.AppPadding
import app.extr.ui.theme.LocalCustomColorsPalette
import app.extr.ui.theme.animations.CustomCircularProgressIndicator
import app.extr.ui.theme.composables.reusablecomponents.ErrorScreen
import app.extr.ui.theme.composables.reusablecomponents.NoDataYet
import app.extr.ui.theme.composables.reusablecomponents.RoundedCard
import app.extr.ui.theme.composables.reusablecomponents.SelectedTransactionType
import app.extr.utils.helpers.AnimatedText
import app.extr.utils.helpers.AnimatedTextWithSign
import app.extr.utils.helpers.Constants
import app.extr.utils.helpers.UiState
import app.extr.utils.helpers.resproviders.ResProvider

@Composable
fun RoundChartScreen(
    modifier: Modifier = Modifier,
    uiState: UiState<List<TransactionWithDetails>>,
    transactionsByTypes: Map<TransactionType, Double>,
    resProvider: ResProvider,
    selectedType: SelectedTransactionType,
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
            val transactions = uiState.data

            if (transactions.isEmpty()) {
                NoDataYet(
                    modifier = Modifier.fillMaxSize(),
                    headerId = if (selectedType == SelectedTransactionType.EXPENSES) R.string.label_no_expenses else R.string.label_no_income,
                    labelId = if (selectedType == SelectedTransactionType.EXPENSES) R.string.label_create_expense_hint else R.string.label_create_income_hint
                )
            } else {
                val currencySymbol = remember(transactions) { transactions[0].currency.symbol }
                Column(
                    modifier = modifier
                ) {

                    PercentageCircleChart(
                        modifier = Modifier.padding(top = AppPadding.Medium, bottom = AppPadding.Medium),
                        data = transactionsByTypes,
                        currencySymbol = currencySymbol,
                        total = transactionsByTypes.entries.sumOf { it.value },
                        resProvider = resProvider
                    )

                    val elementWidth = 120.dp
                    val elementHeight = elementWidth + 30.dp
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        items(
                            count = transactionsByTypes.size,
                            key = { it }
                        ) { index ->
                            val pair = transactionsByTypes.entries.elementAt(index)
                            val type = pair.key
                            val sum = pair.value
                            RoundedCard(
                                icon = resProvider.getRes(type.id).icon,
                                secondaryText = type.name,
                                color = resProvider.getRes(type.id).color,
                                currencySymbol = currencySymbol,
                                precision = Constants.precisionZero,
                                number = sum.toFloat(),
                                modifier = Modifier
                                    .size(width = elementWidth, height = elementHeight),
                                onClick = { onCardClicked(transactions[index].transactionType) }
                            )
                        }
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

@Composable
fun PercentageCircleChart(
    data: Map<TransactionType, Double>,
    total: Double,
    currencySymbol: Char,
    modifier: Modifier = Modifier,
    resProvider: ResProvider
) {
    val strokeWidthPx = with(LocalDensity.current) { 9.dp.toPx() }
    val gapAngle = if(data.size == 1) 0f else 10f

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        Canvas(modifier = Modifier.size(250.dp).align(Alignment.Center)) {
            val diameter = size.minDimension - strokeWidthPx
            val radius = diameter / 2
            val topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2)
            val size = Size(radius * 2, radius * 2)
            var startAngle = -90f + gapAngle / 2

            val totalSweepAngle = 360f - data.size * gapAngle

            data.forEach { (type, value) ->
                val sweepAngle = (value / total) * totalSweepAngle
                drawArc(
                    color = resProvider.getRes(type.id).color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle.toFloat(),
                    useCenter = false,
                    topLeft = topLeft,
                    size = size,
                    style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
                )
                startAngle += sweepAngle.toFloat() + gapAngle
            }
        }

        val targetTotal = Constants.precisionTwo.format(total).toFloat()
        AnimatedTextWithSign(
            totalValue = targetTotal,
            currencySign = currencySymbol,
            signStyle = MaterialTheme.typography.headlineMedium.copy(color = MaterialTheme.colorScheme.secondary),
            valueStyle = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.ExtraBold)
        )
    }
}

