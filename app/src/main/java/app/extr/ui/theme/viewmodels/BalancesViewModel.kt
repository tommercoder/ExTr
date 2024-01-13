package app.extr.ui.theme.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.extr.data.repositories.BalancesRepository
import app.extr.data.types.Balance
import app.extr.data.types.BalanceWithDetails
import app.extr.data.types.Currency
import app.extr.data.types.MoneyType
import app.extr.data.types.User
import app.extr.utils.helpers.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BalancesViewModel(
    private val balancesRepository: BalancesRepository
) : ViewModel() {

    private var _balances = MutableStateFlow<UiState<List<BalanceWithDetails>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<BalanceWithDetails>>> = _balances.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _balances.value = UiState.Loading
            try {
                balancesRepository.getBalancesForCurrentCurrency().collect {
                    _balances.value =
                        UiState.Success(it)
                }
            } catch (e: Exception) {
                Log.e("YourViewModel", "Error fetching balances", e)

            }
        }
    }

    fun addBalance(balance: Balance){
        viewModelScope.launch {
            balancesRepository.insert(balance)
        }
    }

    fun deleteBalance(balance: Balance){
        viewModelScope.launch {
            balancesRepository.delete(balance)
        }
    }
}