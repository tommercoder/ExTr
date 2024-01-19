package app.extr.ui.theme.composables.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.extr.R
import app.extr.data.types.Balance
import app.extr.data.types.BalanceWithDetails
import app.extr.ui.theme.AppPadding
import app.extr.ui.theme.composables.BalanceBottomSheet
import app.extr.ui.theme.composables.reusablecomponents.PlusButton
import app.extr.ui.theme.composables.reusablecomponents.RoundedCard
import app.extr.utils.helpers.AnimatedText
import app.extr.utils.helpers.UiState
import app.extr.utils.helpers.resproviders.MoneyTypesRes
import com.example.compose.balance_light_card_color
import com.example.compose.balance_light_cash_color
import com.example.compose.balance_light_electronic_wallet_color
import com.example.compose.balance_light_savings_color

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: UiState<List<BalanceWithDetails>>,
    totalBalance: Float,
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

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                if(totalBalance == 0.0f){
                    NoBalancesYet()
                }
                else {
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
            text = stringResource(R.string.label_total_balance),
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
                tint = balance_light_card_color
            )
            Icon(
                modifier = Modifier.size(iconSize),
                painter = painterResource(id = R.drawable.ewallet_icon),
                contentDescription = null,
                tint = balance_light_electronic_wallet_color
            )
            Icon(
                modifier = Modifier.size(iconSize),
                painter = painterResource(id = R.drawable.savings_icon),
                contentDescription = null,
                tint = balance_light_savings_color
            )
            Icon(
                modifier = Modifier.size(iconSize),
                painter = painterResource(id = R.drawable.cash_icon),
                contentDescription = null,
                tint = balance_light_cash_color
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