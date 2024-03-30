package app.extr.ui.theme.composables.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.extr.R
import app.extr.data.types.Balance
import app.extr.ui.theme.animations.CustomCircularProgressIndicator
import app.extr.ui.theme.composables.reusablecomponents.ErrorScreen
import app.extr.ui.theme.composables.reusablecomponents.NoDataYet
import app.extr.ui.theme.composables.reusablecomponents.PlusButton
import app.extr.ui.theme.composables.reusablecomponents.RoundedCard
import app.extr.ui.theme.viewmodels.BalanceUiEvent
import app.extr.utils.helpers.AnimatedTextWithSign
import app.extr.utils.helpers.BalanceWithDetailsState
import app.extr.utils.helpers.UiState
import app.extr.utils.helpers.resproviders.MoneyTypesRes

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: BalanceWithDetailsState,
    totalBalance: Double,
    isAddBalanceEnabled: Boolean,
    moneyTypesRes: MoneyTypesRes,
    onEvent: (BalanceUiEvent) -> Unit,
    onAddBalanceClicked: () -> Unit
) {
    when (uiState) {
        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CustomCircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is UiState.Success -> {
            var showDeleteDialog by remember { mutableStateOf(false) }
            var longPressedBalance by remember { mutableStateOf<Balance?>(null) }
            val data by rememberUpdatedState(uiState.data)

            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                if (data.isEmpty()) {
                    NoDataYet(
                        modifier = Modifier,
                        headerId = R.string.label_no_balances,
                        labelId = R.string.label_create_balance_hint
                    )
                } else {
                    TotalBalance(
                        totalBalance = totalBalance,
                        currencySign = data.firstOrNull()?.currency?.symbol ?: ' '
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                }

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
                        RoundedCard(
                            icon = moneyTypesRes.getRes(data[i].moneyType.iconId).icon,
                            text = data[i].moneyType.name,
                            secondaryText = data[i].balance.customName,
                            color = moneyTypesRes.getRes(data[i].moneyType.iconId).color,
                            currencySymbol = data[i].currency.symbol,
                            number = data[i].balance.amount,
                            modifier = Modifier
                                .size(elementSize),
                            onLongPress = {
                                longPressedBalance = data[i].balance
                                showDeleteDialog = true
                            }
                        )
                    }
                    item {
                        PlusButton(
                            onClick = { onAddBalanceClicked() },
                            size = elementSize,
                            isEnabled = isAddBalanceEnabled
                        )
                    }
                }

                if (showDeleteDialog) {
                    DeleteBalanceDialog(
                        onDeleteClicked = {
                            onEvent(BalanceUiEvent.Delete(longPressedBalance!!))
                        },
                        onDismissed = { showDeleteDialog = false }
                    )
                }
            }
        }


        is UiState.Error -> {
            ErrorScreen(
                onRefresh = { onEvent(BalanceUiEvent.Refresh)  },
                resourceId = uiState.resourceId
            )
        }
    }
}

@Composable
fun TotalBalance(
    modifier: Modifier = Modifier,
    totalBalance: Double,
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

        AnimatedTextWithSign(
            totalValue = totalBalance,
            currencySign = currencySign
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