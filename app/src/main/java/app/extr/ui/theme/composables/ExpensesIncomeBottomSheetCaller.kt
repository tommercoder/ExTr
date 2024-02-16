package app.extr.ui.theme.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.extr.R
import app.extr.data.types.Balance
import app.extr.data.types.BalanceWithDetails
import app.extr.data.types.Expense
import app.extr.data.types.Income
import app.extr.data.types.Transaction
import app.extr.data.types.TransactionType
import app.extr.ui.theme.LocalCustomColorsPalette
import app.extr.ui.theme.composables.reusablecomponents.SelectedTransactionType
import app.extr.ui.theme.mappers.DropdownItemUi
import app.extr.ui.theme.mappers.toDropdownItem
import app.extr.ui.theme.viewmodels.DatePickerViewModel
import app.extr.ui.theme.viewmodels.ExpensesIncomeBottomSheetViewModel
import app.extr.ui.theme.viewmodels.ExpensesIncomeViewModel
import app.extr.utils.helpers.UiState
import app.extr.utils.helpers.resproviders.ExpenseTypesRes
import app.extr.utils.helpers.resproviders.IncomeTypesRes
import app.extr.utils.helpers.resproviders.MoneyTypesRes

@Composable
fun ExpenseIncomeBottomSheetCaller(
    expensesIncomeBottomSheetViewModel: ExpensesIncomeBottomSheetViewModel,
    expensesIncomeViewModel: ExpensesIncomeViewModel,
    datePickerViewModel: DatePickerViewModel,
    selectedTransactionType: SelectedTransactionType
) {
    val combinedUiState by expensesIncomeBottomSheetViewModel.combinedUiState.collectAsStateWithLifecycle()
    val dateState by datePickerViewModel.dateState.collectAsStateWithLifecycle()

    val palette = LocalCustomColorsPalette.current
    val expenseTypesRes = remember { ExpenseTypesRes(palette) }
    val incomeTypesRes = remember { IncomeTypesRes(palette) }
    val moneyTypeRes = remember { MoneyTypesRes(palette) }

    if (combinedUiState.bottomSheetVisible) {
        when {
            combinedUiState.balancesState is UiState.Success
                    && combinedUiState.expenseTypesState is UiState.Success
                    && combinedUiState.incomeTypesState is UiState.Success ->
            {
                val balances =
                    (combinedUiState.balancesState as UiState.Success<List<BalanceWithDetails>>).data
                val balancesDropdownItems = remember(combinedUiState.balancesState) {
                    balances.map { it.toDropdownItem(moneyTypeRes) }
                }
                val transactionTypesDropdownItems = remember(
                    combinedUiState.expenseTypesState,
                    combinedUiState.incomeTypesState,
                    selectedTransactionType
                ) {
                    if (selectedTransactionType == SelectedTransactionType.EXPENSES) {
                        (combinedUiState.expenseTypesState as UiState.Success).data.map {
                            it.toDropdownItem(
                                expenseTypesRes
                            )
                        }
                    } else {
                        (combinedUiState.incomeTypesState as UiState.Success).data.map {
                            it.toDropdownItem(
                                incomeTypesRes
                            )
                        }
                    }
                }

                val preselectedTypeUi= transactionTypesDropdownItems.firstOrNull { it.id == combinedUiState.preSelectedTransactionTypeId }
                ExpensesIncomeBottomSheet(
                    balances = balancesDropdownItems,
                    transactionTypes = transactionTypesDropdownItems,
                    preselectedTransactionType = preselectedTypeUi,
                    onSaveClicked = {
                        val balance = expensesIncomeBottomSheetViewModel.getBalanceById(
                            balances,
                            it.balanceId
                        )
                        val transaction =
                            if (selectedTransactionType == SelectedTransactionType.EXPENSES)
                                Expense(
                                    typeId = it.transactionTypeId,
                                    balanceId = it.balanceId,
                                    currencyId = balance.balanceId,
                                    moneyTypeId = balance.moneyTypeId,
                                    description = it.name,
                                    transactionAmount = it.amount.toFloat(),
                                    month = it.month,
                                    year = it.year
                                )
                            else Income(
                                typeId = it.transactionTypeId,
                                balanceId = it.balanceId,
                                currencyId = balance.balanceId,
                                moneyTypeId = balance.moneyTypeId,
                                description = it.name,
                                transactionAmount = it.amount.toFloat(),
                                month = it.month,
                                year = it.year
                            )

                        expensesIncomeViewModel.insertTransaction(transaction)
                        expensesIncomeBottomSheetViewModel.toggleBottomSheet(false)
                    },
                    onCalendarClick = {
                        datePickerViewModel.toggleDatePicker(true)
                    },
                    onDismissed = { expensesIncomeBottomSheetViewModel.toggleBottomSheet(false) },
                    currencySymbol = (combinedUiState.balancesState as UiState.Success<List<BalanceWithDetails>>).data[0].currency.symbol,
                    initialBalance = balancesDropdownItems.first(),
                    initialTransactionType = transactionTypesDropdownItems.first(),
                    label = stringResource(
                        id = if (selectedTransactionType == SelectedTransactionType.EXPENSES) R.string.label_expense else R.string.label_income
                    ),
                    date = dateState.date
                )
            }
        }
    }
}