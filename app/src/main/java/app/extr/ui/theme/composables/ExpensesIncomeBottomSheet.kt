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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import app.extr.ui.theme.AppPadding
import app.extr.ui.theme.composables.reusablecomponents.CustomKeyboard
import app.extr.ui.theme.composables.reusablecomponents.ReusableDropdownMenu
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import app.extr.R
import app.extr.ui.theme.mappers.DropdownItemUi
import app.extr.utils.helpers.BottomSheetAcceptType
import app.extr.utils.helpers.BottomSheetAcceptTypeTransaction

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ExpensesIncomeBottomSheet(
    modifier: Modifier = Modifier,
    balances: List<DropdownItemUi>,
    transactionTypes: List<DropdownItemUi>,
    initialBalance: DropdownItemUi,
    initialTransactionType: DropdownItemUi,
    preselectedTransactionType: DropdownItemUi? = null,
    currencySymbol: Char,
    onSaveClicked: (BottomSheetAcceptTypeTransaction) -> Unit,
    onDismissed: () -> Unit,
    label: String = ""
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var inputValue by remember { mutableStateOf("") }
    var nameValue by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = { onDismissed() },
        sheetState = sheetState
    ) {

        var selectedBalance by remember { mutableStateOf(initialBalance) }
        var selectedTransactionType by remember { mutableStateOf(preselectedTransactionType ?: initialTransactionType) }

        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppPadding.Medium),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                ReusableDropdownMenu(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = AppPadding.Small),
                    items = balances,
                    onItemSelected = {
                        selectedBalance = it
                    },
                )

                ReusableDropdownMenu(
                    modifier = Modifier.weight(1f),
                    items = transactionTypes,
                    selectedItem = selectedTransactionType,
                    onItemSelected = {
                        selectedTransactionType = it
                    },
                )
            }

            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = AppPadding.Small, bottom = AppPadding.Small),
                text = label,
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.secondary)
            )

            CustomKeyboard(
                currencySymbol = currencySymbol,
                onValueChange = { inputValue += it },
                inputValue = inputValue,
                onNameChange = { nameValue = it },
                nameValue = nameValue,
                showCalendar = true,
                onEraseClick = { inputValue = inputValue.dropLast(1) },
                onCalendarClick = { },
                onAcceptClick = {
                    val bottomSheetAcceptType = BottomSheetAcceptTypeTransaction(
                        balanceId = selectedBalance.id,
                        transactionTypeId = selectedTransactionType.id,
                        amount = inputValue,
                        name = nameValue,
                        month = 2, //todo: change
                        year = 2024 //todo: change

                    )

                    onSaveClicked(bottomSheetAcceptType)
                },
                isNameRequired = false,
                textFieldDefaultText = stringResource(id = R.string.label_add_description)
            )
        }
    }
}
