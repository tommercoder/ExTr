package app.extr.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.extr.data.repositories.ExpensesIncomeTypesRepository
import app.extr.utils.helpers.TransactionTypeState
import app.extr.utils.helpers.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ExpensesIncomeTypesViewModel(
    private val ExpensesIncomeTypesRepository: ExpensesIncomeTypesRepository
) : ViewModel() {
    private val _expenseTypes = MutableStateFlow<TransactionTypeState>(UiState.Loading)
    private val _incomeTypes = MutableStateFlow<TransactionTypeState>(UiState.Loading)

    init {
        loadExpenseTypes()
        loadIncomeTypes()
    }

    private fun loadExpenseTypes() {
        viewModelScope.launch {
            _expenseTypes.value = UiState.Loading
            try {
                ExpensesIncomeTypesRepository.getAllExpenseTypes().collectLatest {
                    _expenseTypes.value = UiState.Success(it)
                }
            } catch (_: Exception) {

            }
        }
    }

    private fun loadIncomeTypes() {
        viewModelScope.launch {
            _incomeTypes.value = UiState.Loading
            try {
                ExpensesIncomeTypesRepository.getAllIncomeTypes().collectLatest {
                    _incomeTypes.value = UiState.Success(it)
                }
            } catch (e: Exception) {

            }
        }
    }
}