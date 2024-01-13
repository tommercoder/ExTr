package app.extr.ui.theme.composables.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.extr.R
import app.extr.data.types.Balance
import app.extr.data.types.BalanceWithDetails
import app.extr.ui.theme.composables.BalanceBottomSheet
import app.extr.ui.theme.composables.reusablecomponents.PlusButton
import app.extr.ui.theme.composables.reusablecomponents.RoundedCard
import app.extr.ui.theme.mt_card_color
import app.extr.utils.helpers.UiState
import app.extr.utils.helpers.resproviders.MoneyTypesRes

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: UiState<List<BalanceWithDetails>>,
    onAddBalanceClicked: () -> Unit,
    onDeleteBalanceClicked: (Balance) -> Unit
) {
    when (uiState) {
        is UiState.Loading -> {

        }

        is UiState.Success -> {
            var showDeleteDialog by remember { mutableStateOf(false) }
            var longPressedBalance by remember { mutableStateOf<Balance?>(null) }
            val data by rememberUpdatedState(uiState.data)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(13.dp),
                verticalArrangement = Arrangement.spacedBy(7.dp),
                horizontalArrangement = Arrangement.spacedBy(7.dp),

                ) {
                val elementSize = 180.dp
                items(data.size) { i ->
                    RoundedCard(
                        icon = MoneyTypesRes.getRes(data[i].moneyType.iconId).icon,
                        text = data[i].moneyType.name,
                        secondaryText = data[i].balance.customName,
                        color = MoneyTypesRes.getRes(data[i].moneyType.colorId).color,
                        currencySymbol = data[i].currency.symbol,
                        number = data[i].balance.amount,
                        modifier = Modifier.size(elementSize),
                        onLongPress = {
                            longPressedBalance = data[i].balance
                            showDeleteDialog = true
                        }
                    )
                }
                item {
                    PlusButton(onClick = { onAddBalanceClicked() }, size = elementSize)
                }
            }

            if (showDeleteDialog) {
                DeleteBalanceDialog(
                    onDeleteClicked = {
                        onDeleteBalanceClicked(longPressedBalance!!)
                    },
                    onDismissed = { showDeleteDialog = false }
                )
            }
        }

        is UiState.Error -> {

        }
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