package app.extr.ui.theme.composables.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.extr.R
import app.extr.data.types.TransactionWithDetails
import app.extr.ui.theme.AppPadding
import app.extr.ui.theme.ExTrTheme
import app.extr.ui.theme.animations.CustomCircularProgressIndicator
import app.extr.ui.theme.composables.reusablecomponents.CategoryCard
import app.extr.ui.theme.composables.reusablecomponents.NoDataYet
import app.extr.ui.theme.composables.reusablecomponents.SelectedTransactionType
import app.extr.ui.theme.composables.reusablecomponents.TimePeriodCard
import app.extr.ui.theme.viewmodels.TimePeriodAmount
import app.extr.ui.theme.viewmodels.TransactionByType
import app.extr.utils.helpers.UiState
import app.extr.utils.helpers.resproviders.ResProvider

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChartScreen(
    uiState: UiState<List<TransactionWithDetails>>,
    transactionsByType: List<TransactionByType>,
    timePeriodAmount: TimePeriodAmount,
    selectedType: SelectedTransactionType,
    resProvider: ResProvider,
    modifier: Modifier = Modifier,
) {

    when (uiState) {
        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CustomCircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
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
                val currencySymbol =
                    remember(transactionsByType) { transactionsByType[0].currency.symbol }
                var itemCount by remember(selectedType) { mutableIntStateOf(4) }
                val showMore = itemCount < transactionsByType.size
                val showLess = itemCount > 4
                Column(
                    modifier = modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Chart(
                        modifier = Modifier.padding(
                            top = AppPadding.Small,
                            bottom = AppPadding.Small
                        ),
                        data = transactionsByType,
                        resProvider = resProvider
                    )

                    TimePeriodRow(
                        timePeriodAmount = timePeriodAmount,
                        currencySymbol = currencySymbol
                    )

                    LazyColumn(
                        modifier = Modifier,
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(
                            items = transactionsByType.take(itemCount),
                            key = { it -> it.transactionType.id }
                        ) {
                            CategoryCard(
                                modifier = Modifier.fillMaxWidth(),
                                transactionByType = it,
                                resProvider = resProvider
                            )
                        }
                        item {
                            when {
                                showMore -> MoreButton(modifier = Modifier.fillMaxWidth()) { itemCount += 4 }
                                showLess -> LessButton(modifier = Modifier.fillMaxWidth()) {
                                    itemCount = 4
                                }
                            }
                        }
                    }

                }
            }
        }

        is UiState.Error -> {

        }
    }
}

@Composable
fun TimePeriodRow(
    timePeriodAmount: TimePeriodAmount,
    currencySymbol: Char
) {
    val elementHeight = 90.dp
    val elementWidth = 130.dp
    Row(
        modifier = Modifier.padding(8.dp)
    ) {
        TimePeriodCard(
            labelId = R.string.label_day,
            amount = timePeriodAmount.byDay,
            currencySign = currencySymbol,
            modifier = Modifier
                .padding(end = 4.dp)
                .size(height = elementHeight, width = elementWidth)
        )
        TimePeriodCard(
            labelId = R.string.label_week,
            amount = timePeriodAmount.byWeek,
            currencySign = currencySymbol,
            modifier = Modifier
                .padding(end = 4.dp)
                .size(height = elementHeight, width = elementWidth)
        )
        TimePeriodCard(
            labelId = R.string.label_month,
            amount = timePeriodAmount.byMonth,
            currencySign = currencySymbol,
            modifier = Modifier.size(height = elementHeight, width = elementWidth)
        )
    }
}

@Composable
fun MoreButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier.clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row {
            Icon(
                painter = painterResource(id = R.drawable.expand_more_icon),
                contentDescription = null
            )
            Text(text = stringResource(id = R.string.btn_show_more), fontWeight = FontWeight.Bold)

        }
    }
}

@Composable
fun LessButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier.clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row {
            Icon(
                painter = painterResource(id = R.drawable.expand_less_icon),
                contentDescription = null
            )
            Text(
                text = stringResource(id = R.string.btn_show_collapse),
                fontWeight = FontWeight.Bold
            )

        }
    }
}

fun calculateBarHeights(transactions: List<TransactionByType>, maxHeight: Float): List<Double> {
    val maxAmount = transactions.maxOf { it.totalAmount }
    return transactions.map { (it.totalAmount / maxAmount) * maxHeight }
}

@Composable
fun Chart(
    // chat gpt
    data: List<TransactionByType>,
    resProvider: ResProvider,
    modifier: Modifier = Modifier,
) {
    val barWidth = 50f
    val barSpacing = 40f
    val canvasHeight = 400.dp
    val cornerRadius = CornerRadius(20f)

    // This will be the animated progress for the bar heights
    var animatedProgress by remember { mutableFloatStateOf(0f) }
    val animationProgress = animateFloatAsState(
        targetValue = animatedProgress,
        animationSpec = TweenSpec(durationMillis = 600), label = ""
    )

    // Trigger the animation when the composable enters the Composition
    LaunchedEffect(Unit) {
        animatedProgress = 1f
    }

    val barHeights = calculateBarHeights(data, canvasHeight.value).map { it * animationProgress.value }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        Canvas(
            modifier = modifier
                .fillMaxWidth()
                .height(160.dp)
        ) {
            val canvasWidth = size.width
            val numberOfBars = data.size
            val totalBarWidth = numberOfBars * barWidth
            val totalSpacingWidth = (numberOfBars - 1) * barSpacing
            val startingPoint = (canvasWidth - (totalBarWidth + totalSpacingWidth)) / 2

            data.forEachIndexed { index, type ->

                val left = startingPoint + (barWidth + barSpacing) * index
                val top = size.height - barHeights[index]
                val right = left + barWidth
                val bottom = size.height

                drawRoundRect(
                    color = resProvider.getRes(type.transactionType.id).color,
                    topLeft = Offset(left, top.toFloat()),
                    size = androidx.compose.ui.geometry.Size(
                        right - left,
                        (bottom - top).toFloat()
                    ),
                    cornerRadius = cornerRadius
                )
            }
        }
    }
}