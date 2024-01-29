package app.extr.ui.theme.composables.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.extr.R
import app.extr.data.types.Balance
import app.extr.data.types.BalanceWithDetails
import app.extr.ui.theme.AppPadding
import app.extr.ui.theme.*
import app.extr.ui.theme.animations.CustomCircularProgressIndicator
import app.extr.ui.theme.animations.LoadingAnimation
import app.extr.ui.theme.composables.BalanceBottomSheet
import app.extr.ui.theme.composables.reusablecomponents.ErrorScreen
import app.extr.ui.theme.composables.reusablecomponents.PlusButton
import app.extr.ui.theme.composables.reusablecomponents.RoundedCard
import app.extr.utils.helpers.AnimatedText
import app.extr.utils.helpers.UiState
import app.extr.utils.helpers.resproviders.MoneyTypesRes
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: UiState<List<BalanceWithDetails>>,
    totalBalance: Float,
    onAddBalanceClicked: () -> Unit,
    onDeleteBalanceClicked: (Balance) -> Unit,
    onRefresh: () -> Unit
) {
    when (uiState) {
        is UiState.Loading -> {
            //LoadingAnimation(modifier = Modifier.fillMaxSize())
            Box(modifier = Modifier.fillMaxSize()) {
                CustomCircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is UiState.Success -> {
            var showDeleteDialog by remember { mutableStateOf(false) }
            var longPressedBalance by remember { mutableStateOf<Balance?>(null) }
            val data by rememberUpdatedState(uiState.data)
            val currentPalette = LocalCustomColorsPalette.current
            val moneyTypesRes = remember { MoneyTypesRes(currentPalette) }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                if (totalBalance == 0.0f) {
                    NoBalancesYet()
                } else {
                    TotalBalance(
                        totalBalance = totalBalance,
                        currencySign = data.firstOrNull()?.currency?.symbol ?: ' '
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                }

//                AnimatedVisibility(
//                    visible = true,
//                    enter = fadeIn() + expandVertically(),
//                    exit = fadeOut() + shrinkVertically()
//                ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(13.dp),
                    verticalArrangement = Arrangement.spacedBy(7.dp),
                    horizontalArrangement = Arrangement.spacedBy(7.dp),
                ) {
                    val elementSize = 180.dp
                    items(
                        count = data.size,
                        key = { it }
                    ) { i ->
                        // Initial scale set to 0 to grow from nothing
//                        var scale by remember { mutableStateOf(0f) }
//
//                        // Coroutine to delay the animation slightly for each item
//                        LaunchedEffect(Unit) {
//                            delay(i * 100L) // Delay based on item index to stagger the animations
//                            scale = 1f // Animate to full scale
//                        }
                            RoundedCard(
                                icon = moneyTypesRes.getRes(data[i].moneyType.iconId).icon,
                                text = data[i].moneyType.name,
                                secondaryText = data[i].balance.customName,
                                color = moneyTypesRes.getRes(data[i].moneyType.iconId).color,
                                currencySymbol = data[i].currency.symbol,
                                number = data[i].balance.amount,
                                modifier = Modifier
                                    .size(elementSize)
                                    .animateItemPlacement(

                                    ),
                                onLongPress = {
                                    longPressedBalance = data[i].balance
                                    showDeleteDialog = true
                                },
                                onClick = {
                                    Log.d("TAG", "Clicked " + data[i].balance.amount)
                                }
                            )
                        }
                    item {
                        PlusButton(onClick = { onAddBalanceClicked() }, size = elementSize)
                    }
                }
                // }

                if (showDeleteDialog) {
                    DeleteBalanceDialog(
                        onDeleteClicked = {
                            onDeleteBalanceClicked(longPressedBalance!!)
                        },
                        onDismissed = { showDeleteDialog = false }
                    )
                }
            }
        }


        is UiState.Error -> {
            ErrorScreen(
                onRefresh = { /*TODO*/ },
                resourceId = uiState.resourceId
            )
        }
    }
}

@Composable
fun TotalBalance(
    modifier: Modifier = Modifier,
    totalBalance: Float,
    currencySign: Char
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.header_total_balance),
            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.secondary)
        )
        Row(
            modifier = Modifier
                .padding(top = AppPadding.Medium)
                .align(
                    Alignment.CenterHorizontally
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(end = AppPadding.Small),
                text = currencySign.toString(),
                style = MaterialTheme.typography.headlineLarge.copy(color = MaterialTheme.colorScheme.secondary)
            )
            AnimatedText(
                targetValue = totalBalance,
                textStyle = MaterialTheme.typography.displayLarge.copy(fontWeight = FontWeight.ExtraBold),
                durationMillis = 700
            )
        }
    }
}

@Composable
fun NoBalancesYet(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val iconSize = 60.dp
            Icon(
                modifier = Modifier.size(iconSize),
                painter = painterResource(id = R.drawable.card_icon),
                contentDescription = null,
                tint = LocalCustomColorsPalette.current.balanceCardColor
            )
            Icon(
                modifier = Modifier.size(iconSize),
                painter = painterResource(id = R.drawable.ewallet_icon),
                contentDescription = null,
                tint = LocalCustomColorsPalette.current.balanceCashColor
            )
            Icon(
                modifier = Modifier.size(iconSize),
                painter = painterResource(id = R.drawable.savings_icon),
                contentDescription = null,
                tint = LocalCustomColorsPalette.current.balanceSavingsColor
            )
            Icon(
                modifier = Modifier.size(iconSize),
                painter = painterResource(id = R.drawable.cash_icon),
                contentDescription = null,
                tint = LocalCustomColorsPalette.current.balanceEWalletColor
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(id = R.string.label_no_balances),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = stringResource(id = R.string.label_create_balance_hint),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun DeleteBalanceDialog(
    onDeleteClicked: () -> Unit,
    onDismissed: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismissed() },
        title = { Text(stringResource(id = R.string.label_delete_balance)) },
        text = { Text(stringResource(id = R.string.label_delete_balance_confirmation)) },
        confirmButton = {
            Button(
                onClick = {
                    onDeleteClicked()
                    onDismissed()
                }
            ) {
                Text(stringResource(id = R.string.button_ok))
            }
        },
        dismissButton = {
            Button(onClick = { onDismissed() }) {
                Text(stringResource(id = R.string.button_cancel))
            }
        }
    )
}