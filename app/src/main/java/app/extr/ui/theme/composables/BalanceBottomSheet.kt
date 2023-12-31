package app.extr.ui.theme.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import app.extr.data.types.Currency
import app.extr.data.types.MoneyType
import app.extr.ui.theme.AppPadding
import app.extr.ui.theme.composables.reusablecomponents.CurrenciesDropDownMenu
import app.extr.ui.theme.composables.reusablecomponents.CustomKeyboard
import app.extr.ui.theme.composables.reusablecomponents.ReusableDropdownMenu
import app.extr.ui.theme.mappers.toDropdownItem
import app.extr.utils.helpers.UiState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalanceBottomSheet(
    modifier: Modifier = Modifier,
    currenciesUiState: UiState<List<Currency>>,
    moneyTypeUiState: UiState<List<MoneyType>>,
    onSaveClicked: () -> Unit,
    onDismissed: () -> Unit
) {
    //dropdown for currencies --> //dropdown for money types
    //label "balance"
    //currency symbol + inputted number
    //input name
    //keyboard
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = { onDismissed() },
        //modifier = Modifier.height(LocalConfiguration.current.screenHeightDp.dp * 0.7f),
        sheetState = sheetState,
        content = {
            Column(
                //verticalArrangement = Arr
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppPadding.Medium),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (currenciesUiState is UiState.Success) {
                        val currencies = currenciesUiState.data
                        if (currencies.isNotEmpty()) {
                            CurrenciesDropDownMenu(
                                modifier = Modifier.weight(1f).padding(end = AppPadding.ExtraSmall),
                                items = currencies,
                                onItemSelected = {

                                },
                                borderShown = true
                            )
                        }
                    }
                    if (moneyTypeUiState is UiState.Success) {
                        val moneyTypes = moneyTypeUiState.data
                        if (moneyTypes.isNotEmpty()) {
                            ReusableDropdownMenu(
                                modifier = Modifier.weight(1f),
                                items = moneyTypes.map { it.toDropdownItem() },
                                onItemSelected = {},
                            )
                        }
                    }
                }
                var inputValue by remember { mutableStateOf("") }
                var nameValue by remember { mutableStateOf("") }
                val currencySymbol = "$"
                CustomKeyboard(
                    currencySymbol = currencySymbol,
                    onValueChange = { inputValue += it },
                    inputValue = inputValue,
                    onNameChange = { nameValue = it },
                    nameValue = nameValue,
                    showCalendar = true, // Control the visibility of the calendar button
                    onEraseClick = { inputValue = inputValue.dropLast(1) },
                    onCalendarClick = { /* Handle calendar click */ },
                    onAcceptClick = { /* Handle accept click */ }
                )
            }
        }
    )
}
