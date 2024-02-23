package app.extr.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.extr.R
import app.extr.data.repositories.BalancesRepository
import app.extr.data.repositories.ExpensesIncomeRepository
import app.extr.data.types.Balance
import app.extr.data.types.Currency
import app.extr.data.types.Expense
import app.extr.data.types.Income
import app.extr.data.types.MoneyType
import app.extr.data.types.Transaction
import app.extr.data.types.TransactionType
import app.extr.data.types.TransactionWithDetails
import app.extr.utils.helpers.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

data class CombinedExpensesIncomeState(
    val expensesState: UiState<List<TransactionWithDetails>>,
    val incomeState: UiState<List<TransactionWithDetails>>,
    val expensesByCategoriesState: List<TransactionByType>,
    val incomeByCategoriesState: List<TransactionByType>
)
data class TransactionByType(
    val transactionType: TransactionType,
    val totalAmount: Double,
    val currency: Currency,
    val moneyType: MoneyType,
    val balances: List<Balance>
)

class ExpensesIncomeViewModel(
    private val expensesIncomeRepository: ExpensesIncomeRepository,
    private val balancesRepository: BalancesRepository
) : ViewModel() {
    private val _expenses = MutableStateFlow<UiState<List<TransactionWithDetails>>>(UiState.Loading)
    private val _income = MutableStateFlow<UiState<List<TransactionWithDetails>>>(UiState.Loading)
    private val _expensesByCategories = MutableStateFlow<List<TransactionByType>>(emptyList())
    private val _incomeByCategories = MutableStateFlow<List<TransactionByType>>(emptyList())

    val combinedUiState: StateFlow<CombinedExpensesIncomeState> = combine(
        _expenses,
        _income,
        _expensesByCategories,
        _incomeByCategories
    ) { expenses, income, expensesByCategories, incomeByCategories ->
        CombinedExpensesIncomeState(
            expensesState = expenses,
            incomeState = income,
            expensesByCategoriesState = expensesByCategories,
            incomeByCategoriesState = incomeByCategories
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, CombinedExpensesIncomeState(UiState.Loading, UiState.Loading, emptyList(), emptyList()))

    init {
        val calendar = Calendar.getInstance()
        val month =
            calendar.get(Calendar.MONTH)// + 1 // Calendar.MONTH is zero-based; January is 0.
        val year = calendar.get(Calendar.YEAR)

        loadTransactions(Date(month, year))
    }

    fun loadTransactions(date: Date) {
        loadExpenses(date.month, date.year)
        loadIncome(date.month, date.year)
    }

    private fun loadExpenses(month: Int, year: Int) {
        try {
            viewModelScope.launch {
                _expenses.value = UiState.Loading
                //delay(200)
                expensesIncomeRepository.getExpensesForCurrentCurrency(month, year).distinctUntilChanged().collect {
                    val newIt = UiState.Success(it)
                    _expenses.value = newIt

                    _expensesByCategories.value = sortTransactionsByTypes(newIt.data)
                }
            }
        } catch (e: Exception) {
            _expenses.value = UiState.Error(R.string.error_expenses_couldnt_load)
        }
    }

    private fun loadIncome(month: Int, year: Int) {
        try {
            viewModelScope.launch {
                _income.value = UiState.Loading
                expensesIncomeRepository.getIncomesForCurrentCurrency(month, year).distinctUntilChanged().collect {
                    val newIt = UiState.Success(it)
                    _income.value = newIt

                    _incomeByCategories.value = sortTransactionsByTypes(newIt.data)
                }
            }
        } catch (e: Exception) {
            _income.value = UiState.Error(R.string.error_income_couldnt_load)
        }
    }

    private fun sortTransactionsByTypes(transactions: List<TransactionWithDetails>): List<TransactionByType> {
        return transactions
            .groupBy { it.transactionType } // Group by transaction type ID
            .map { (type, groupedTransactions) ->
                TransactionByType(
                    transactionType = groupedTransactions.first().transactionType,
                    totalAmount = groupedTransactions.sumOf { it.transaction.transactionAmount.toDouble() },
                    currency = groupedTransactions.first().currency,
                    moneyType = groupedTransactions.first().moneyType,
                    balances = groupedTransactions.map { it.balance }.distinctBy { it.balanceId }
                )
            }
    }

    fun insertTransaction(transaction: Transaction) {
        viewModelScope.launch {
            when (transaction) {
                is Expense -> {
                    expensesIncomeRepository.insertExpense(transaction)
                }

                is Income -> {
                    expensesIncomeRepository.insertIncome(transaction)
                }
            }
        }
    }
}