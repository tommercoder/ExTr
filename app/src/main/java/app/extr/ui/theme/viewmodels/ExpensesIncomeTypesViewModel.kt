package app.extr.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.extr.data.repositories.ExpenseIncomeTypesRepository
import app.extr.data.types.TransactionType
import app.extr.utils.helpers.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExpensesIncomeTypesViewModel(
    private val expenseIncomeTypesRepository: ExpenseIncomeTypesRepository
) : ViewModel() {
    private val _expenseTypes = MutableStateFlow<UiState<List<TransactionType>>>(UiState.Loading)
    val expenseTypes: StateFlow<UiState<List<TransactionType>>> = _expenseTypes.asStateFlow()

    private val _incomeTypes = MutableStateFlow<UiState<List<TransactionType>>>(UiState.Loading)
    val incomeTypes: StateFlow<UiState<List<TransactionType>>> = _incomeTypes.asStateFlow()

    init {
        loadTypes()
    }

    private fun loadTypes() {
        viewModelScope.launch {
            _expenseTypes.value = UiState.Loading
            _incomeTypes.value = UiState.Loading
            try {
                expenseIncomeTypesRepository.getAllExpenseTypes().collect {
                    _expenseTypes.value = UiState.Success(it)
                }

                expenseIncomeTypesRepository.getAllIncomeTypes().collect {
                    _incomeTypes.value = UiState.Success(it)
                }
            } catch (e: Exception) {

            }
        }
    }
}