package app.extr.ui.theme.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import app.extr.R
import app.extr.data.types.Currency
import app.extr.ui.theme.AppPadding
import app.extr.ui.theme.composables.reusablecomponents.CurrenciesDropDownMenu
import app.extr.ui.theme.composables.reusablecomponents.CustomKeyboard
import app.extr.ui.theme.composables.reusablecomponents.ReusableDropdownMenu
import app.extr.ui.theme.mappers.DropdownItemUi
import app.extr.utils.helpers.BottomSheetAcceptType

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun BalanceBottomSheet(
    modifier: Modifier = Modifier,
    currencies: List<Currency>,
    moneyTypes: List<DropdownItemUi>,
    initialCurrency: Currency,
    initialMoneyType: DropdownItemUi,
    onSaveClicked: (BottomSheetAcceptType) -> Unit,
    onDismissed: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var inputValue by remember { mutableStateOf("") }
    var nameValue by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = { onDismissed() },
        sheetState = sheetState
    ) {

        var selectedCurrency by remember { mutableStateOf(initialCurrency) }
        var selectedMoneyType by remember { mutableStateOf(initialMoneyType) }

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppPadding.Medium),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                CurrenciesDropDownMenu(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = AppPadding.Small),
                    items = currencies,
                    onItemSelected = {
                        selectedCurrency = it
                    },
                    borderShown = true
                )

                ReusableDropdownMenu(
                    modifier = Modifier.weight(1f),
                    items = moneyTypes,
                    onItemSelected = {
                        selectedMoneyType = it
                    },
                )
            }

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = AppPadding.Small, bottom = AppPadding.Small),
                text = stringResource(id = R.string.label_balance),
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.secondary)
            )

            CustomKeyboard(
                currencySymbol = selectedCurrency.symbol,
                onValueChange = { inputValue += it },
                inputValue = inputValue,
                onNameChange = { nameValue = it },
                nameValue = nameValue,
                onEraseClick = { inputValue = inputValue.dropLast(1) },
                showCalendar = false,
                onCalendarClick = { },
                onAcceptClick = {
                    val balance = BottomSheetAcceptType(
                        currencyId = selectedCurrency.currencyId,
                        moneyTypeId = selectedMoneyType.id,
                        amount = inputValue.toDouble(),
                        name = nameValue
                    )

                    onSaveClicked(balance)
                },
                isNameRequired = true
            )
        }
    }
}
