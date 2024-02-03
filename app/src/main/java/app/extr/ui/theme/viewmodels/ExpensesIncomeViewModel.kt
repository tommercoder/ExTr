package app.extr.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.extr.R
import app.extr.data.repositories.ExpenseIncomeRepository
import app.extr.data.types.Expense
import app.extr.data.types.Income
import app.extr.data.types.TransactionWithDetails
import app.extr.utils.helpers.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class ExpensesIncomeViewModel(
    private val expenseIncomeRepository: ExpenseIncomeRepository
) : ViewModel() {

    private var _expenses = MutableStateFlow<UiState<List<TransactionWithDetails>>>(UiState.Loading)
    val uiStateExpenses: StateFlow<UiState<List<TransactionWithDetails>>> = _expenses.asStateFlow()

    private var _income = MutableStateFlow<UiState<List<TransactionWithDetails>>>(UiState.Loading)
    val uiStateIncome: StateFlow<UiState<List<TransactionWithDetails>>> = _income.asStateFlow()

    init {
        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH) + 1 // Calendar.MONTH is zero-based; January is 0.
        val year = calendar.get(Calendar.YEAR)

//        val expense = Expense(
//            typeId = 0,
//            balanceId = 0,
//            description = "blabla",
//            amount = 0.3f,
//            month = 2,
//            year = 2024
//        )
//
//        val income = Income(
//            typeId = 0,
//            balanceId = 0,
//            description = "blabla2",
//            amount = 0.4f,
//            month = 2,
//            year = 2024
//        )
//        viewModelScope.launch {
//            expenseIncomeRepository.insertExpense(expense)
//            expenseIncomeRepository.insertIncome(income)
//        }
        loadExpenses(month, year)
        loadIncome(month, year)
    }

    fun loadExpenses(month: Int, year: Int) {
        try {
            viewModelScope.launch {
                _expenses.value = UiState.Loading
                expenseIncomeRepository.getExpensesForCurrentCurrency(month, year).collect {
                    _expenses.value = UiState.Success(it)
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
                expenseIncomeRepository.getIncomesForCurrentCurrency(month, year).collect {
                    _income.value = UiState.Success(it)
                }
            }
        } catch (e: Exception) {
            _income.value = UiState.Error(R.string.error_income_couldnt_load)
        }
    }

    fun insertExpense(expense: Expense){
        viewModelScope.launch {
            expenseIncomeRepository.insertExpense(expense)
        }
    }
}