package app.extr.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.extr.data.repositories.ExpensesIncomeTypesRepository
import app.extr.data.types.TransactionType
import app.extr.utils.helpers.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExpensesIncomeTypesViewModel(
    private val ExpensesIncomeTypesRepository: ExpensesIncomeTypesRepository
) : ViewModel() {
    private val _expenseTypes = MutableStateFlow<UiState<List<TransactionType>>>(UiState.Loading)
    val expenseTypes: StateFlow<UiState<List<TransactionType>>> = _expenseTypes.asStateFlow()

    private val _incomeTypes = MutableStateFlow<UiState<List<TransactionType>>>(UiState.Loading)
    val incomeTypes: StateFlow<UiState<List<TransactionType>>> = _incomeTypes.asStateFlow()

    init {
        loadExpenseTypes()
        loadIncomeTypes()
    }

    private fun loadExpenseTypes() {
        viewModelScope.launch {
            _expenseTypes.value = UiState.Loading
            try {
                ExpensesIncomeTypesRepository.getAllExpenseTypes().collect {
                    _expenseTypes.value = UiState.Success(it)
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun loadIncomeTypes() {
        viewModelScope.launch {
            _incomeTypes.value = UiState.Loading
            try {
                ExpensesIncomeTypesRepository.getAllIncomeTypes().collect {
                    _incomeTypes.value = UiState.Success(it)
                }
            } catch (e: Exception) {

            }
        }
    }
}