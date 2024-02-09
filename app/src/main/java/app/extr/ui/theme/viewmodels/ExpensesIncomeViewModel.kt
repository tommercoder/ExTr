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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar


class ExpensesIncomeViewModel(
    private val ExpensesIncomeRepository: ExpensesIncomeRepository,
    private val balancesRepository: BalancesRepository
) : ViewModel() {
    
    private var _expenses = MutableStateFlow<UiState<List<TransactionWithDetails>>>(UiState.Loading)
    val uiStateExpenses: StateFlow<UiState<List<TransactionWithDetails>>> = _expenses.asStateFlow()

    private var _income = MutableStateFlow<UiState<List<TransactionWithDetails>>>(UiState.Loading)
    val uiStateIncome: StateFlow<UiState<List<TransactionWithDetails>>> = _income.asStateFlow()

    private var _expensesByCategories = MutableStateFlow<Map<TransactionType, Double>>(emptyMap())
    val expensesByCategories: StateFlow<Map<TransactionType, Double>> = _expensesByCategories.asStateFlow()

    private var _incomeByCategories = MutableStateFlow<Map<TransactionType, Double>>(emptyMap())
    val incomeByCategories: StateFlow<Map<TransactionType, Double>> = _incomeByCategories.asStateFlow()

    init {
        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH) + 1 // Calendar.MONTH is zero-based; January is 0.
        val year = calendar.get(Calendar.YEAR)

        loadExpenses(month, year)
        loadIncome(month, year)
    }

    fun loadExpenses(month: Int, year: Int) {
        try {
            viewModelScope.launch {
                _expenses.value = UiState.Loading
                ExpensesIncomeRepository.getExpensesForCurrentCurrency(month, year).collect {

                    _expenses.value = UiState.Success(it)
                    
                    _expensesByCategories.value = sortTransactionsByTypes(it)
                }
            }
        } catch (e: Exception) {
            _expenses.value = UiState.Error(R.string.error_expenses_couldnt_load)
        }
    }

    fun loadIncome(month: Int, year: Int) {
        try {
            viewModelScope.launch {
                _income.value = UiState.Loading
                ExpensesIncomeRepository.getIncomesForCurrentCurrency(month, year).collect {
                    _income.value = UiState.Success(it)

                    _incomeByCategories.value = sortTransactionsByTypes(it)
                }
            }
        } catch (e: Exception) {
            _income.value = UiState.Error(R.string.error_income_couldnt_load)
        }
    }

    private fun sortTransactionsByTypes(list: List<TransactionWithDetails>) : Map<TransactionType, Double> {
        val sumByType = list.groupBy { it.transactionType }
            .mapValues { (_, transactions) ->
                transactions.sumOf { it.transaction.transactionAmount.toDouble() }
            }
        
        return sumByType
    }

    fun insertTransaction(transaction: Transaction){
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