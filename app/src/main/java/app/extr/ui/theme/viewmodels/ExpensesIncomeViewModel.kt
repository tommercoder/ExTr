package app.extr.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.extr.R
import app.extr.data.repositories.BalancesRepository
import app.extr.data.repositories.ExpensesIncomeRepository
import app.extr.data.types.BalanceWithDetails
import app.extr.data.types.Expense
import app.extr.data.types.Income
import app.extr.data.types.Transaction
import app.extr.data.types.TransactionType
import app.extr.data.types.TransactionWithDetails
import app.extr.utils.helpers.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

data class CombinedExpensesIncomeState(
    val expensesState: UiState<List<TransactionWithDetails>>,
    val incomeState: UiState<List<TransactionWithDetails>>,
    val expensesByCategoriesState: Map<TransactionType, Double>,
    val incomeByCategoriesState: Map<TransactionType, Double>
)

class ExpensesIncomeViewModel(
    private val ExpensesIncomeRepository: ExpensesIncomeRepository,
    private val balancesRepository: BalancesRepository
) : ViewModel() {
    private val _expenses = MutableStateFlow<UiState<List<TransactionWithDetails>>>(UiState.Loading)
    private val _income = MutableStateFlow<UiState<List<TransactionWithDetails>>>(UiState.Loading)
    private val _expensesByCategories = MutableStateFlow<Map<TransactionType, Double>>(emptyMap())
    private val _incomeByCategories = MutableStateFlow<Map<TransactionType, Double>>(emptyMap())

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
    }.stateIn(viewModelScope, SharingStarted.Lazily, CombinedExpensesIncomeState(UiState.Loading, UiState.Loading, emptyMap(), emptyMap()))

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
                delay(200)
                ExpensesIncomeRepository.getExpensesForCurrentCurrency(month, year).distinctUntilChanged().collect {
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
                ExpensesIncomeRepository.getIncomesForCurrentCurrency(month, year).distinctUntilChanged().collect {
                    val newIt = UiState.Success(it)
                    _income.value = newIt

                    _incomeByCategories.value = sortTransactionsByTypes(newIt.data)
                }
            }
        } catch (e: Exception) {
            _income.value = UiState.Error(R.string.error_income_couldnt_load)
        }
    }

    private fun sortTransactionsByTypes(list: List<TransactionWithDetails>): Map<TransactionType, Double> {
        val sumByType = list.groupBy { it.transactionType }
            .mapValues { (_, transactions) ->
                transactions.sumOf { it.transaction.transactionAmount.toDouble() }
            }

        return sumByType
    }

    fun insertTransaction(transaction: Transaction) {
        viewModelScope.launch {
            when (transaction) {
                is Expense -> {
                    ExpensesIncomeRepository.insertExpense(transaction)
                }

                is Income -> {
                    ExpensesIncomeRepository.insertIncome(transaction)
                }
            }
        }
    }
}