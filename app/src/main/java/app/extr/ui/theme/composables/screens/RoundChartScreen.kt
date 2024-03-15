package app.extr.ui.theme.composables.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.extr.R
import app.extr.data.types.TransactionType
import app.extr.data.types.TransactionWithDetails
import app.extr.ui.theme.AppPadding
import app.extr.ui.theme.animations.CustomCircularProgressIndicator
import app.extr.ui.theme.composables.reusablecomponents.ErrorScreen
import app.extr.ui.theme.composables.reusablecomponents.NoDataYet
import app.extr.ui.theme.composables.reusablecomponents.RoundedCard
import app.extr.ui.theme.composables.reusablecomponents.SelectedTransactionType
import app.extr.ui.theme.viewmodels.TransactionByType
import app.extr.ui.theme.viewmodels.TransactionUiEvent
import app.extr.utils.helpers.AnimatedTextWithSign
import app.extr.utils.helpers.Constants
import app.extr.utils.helpers.UiState
import app.extr.utils.helpers.resproviders.ResProvider
import kotlinx.coroutines.launch

@Composable
fun RoundChartScreen(
    modifier: Modifier = Modifier,
    uiState: UiState<List<TransactionWithDetails>>,
    transactionsByTypes: List<TransactionByType>,
    resProvider: ResProvider,
    selectedType: SelectedTransactionType,
    onCardClicked: (TransactionType) -> Unit,
    onEvent: (TransactionUiEvent) -> Unit,
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
                val currencySymbol = remember(transactionsByTypes) { transactionsByTypes[0].currency.symbol }
                Column(
                    modifier = modifier
                ) {

                    PercentageCircleChart(
                        modifier = Modifier.padding(
                            top = AppPadding.Medium,
                            bottom = AppPadding.Medium
                        ),
                        data = transactionsByTypes,
                        currencySymbol = currencySymbol,
                        total = transactionsByTypes.sumOf { it.totalAmount },
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
                            val transactionByType = transactionsByTypes[index]
                            RoundedCard(
                                icon = resProvider.getRes(transactionByType.transactionType.id).icon,
                                secondaryText = transactionByType.transactionType.name,
                                color = resProvider.getRes(transactionByType.transactionType.id).color,
                                currencySymbol = currencySymbol,
                                precision = Constants.precisionZero,
                                number = transactionByType.totalAmount,
                                modifier = Modifier
                                    .size(width = elementWidth, height = elementHeight),
                                onClick = { onCardClicked(transactionsByTypes[index].transactionType) }
                            )
                        }
                    }
                }
            }
        }

        is UiState.Error -> {
            ErrorScreen(
                onRefresh = { onEvent(TransactionUiEvent.Refresh) },
                resourceId = uiState.resourceId
            )
        }
    }
}

@Composable
fun PercentageCircleChart( // chat gpt
    data: List<TransactionByType>,
    total: Double,
    currencySymbol: Char,
    modifier: Modifier = Modifier,
    resProvider: ResProvider
) {
    val strokeWidthPx = with(LocalDensity.current) { 9.dp.toPx() }
    val gapAngle = if (data.size == 1) 0f else 10f

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        val animatedSweepAngles =
            remember { mutableStateListOf<Animatable<Float, AnimationVector1D>>() }

        // Prepare animatables for each data entry
        LaunchedEffect(data) {
            animatedSweepAngles.clear()
            data.forEach { _ ->
                animatedSweepAngles.add(Animatable(0f))
            }

            val totalSweepAngle = 360f - data.size * gapAngle
            data.forEachIndexed { index, value ->
                val targetAngle = (value.totalAmount / total) * totalSweepAngle
                launch {
                    animatedSweepAngles[index].animateTo(
                        targetValue = targetAngle.toFloat(),
                        animationSpec = TweenSpec(durationMillis = 1000)
                    )
                }
            }
        }

        Canvas(modifier = Modifier
            .size(250.dp)
            .align(Alignment.Center)) {
            val diameter = size.minDimension - strokeWidthPx
            val radius = diameter / 2
            val topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2)
            val size = Size(radius * 2, radius * 2)
            var startAngle = -90f + gapAngle / 2

            data.forEachIndexed { index, type ->
                val sweepAngle = animatedSweepAngles.getOrNull(index)?.value ?: 0f
                drawArc(
                    color = resProvider.getRes(type.transactionType.id).color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = topLeft,
                    size = size,
                    style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
                )
                startAngle += sweepAngle + gapAngle
            }
        }

        //val targetTotalStr = Constants.precisionTwo.format(total)
            //val targetTotal = targetTotalStr.toDouble()
        AnimatedTextWithSign(
            totalValue = total,
            format = Constants.precisionTwo,
            currencySign = currencySymbol,
            valueStyle = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.ExtraBold)
        )
    }
}


