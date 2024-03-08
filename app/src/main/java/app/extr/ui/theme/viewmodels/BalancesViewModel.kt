package app.extr.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.extr.R
import app.extr.data.repositories.BalancesRepository
import app.extr.data.types.Balance
import app.extr.data.types.BalanceWithDetails
import app.extr.utils.helpers.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BalancesViewModel(
    private val balancesRepository: BalancesRepository
) : ViewModel() {

    private var _balances = MutableStateFlow<UiState<List<BalanceWithDetails>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<BalanceWithDetails>>> = _balances.asStateFlow()

    private var _totalBalance = MutableStateFlow(0.0)
    val totalBalance: StateFlow<Double> = _totalBalance.asStateFlow()

    init {
        loadData()
    }

    fun refreshData() {
        loadData()
    }

    fun doesBalanceExist(balance: Balance): Boolean {
        return when (val balances = _balances.value) {
            is UiState.Success -> balances.data.any {
                it.balance.currencyId == balance.currencyId
                        && it.balance.moneyTypeId == balance.moneyTypeId
                        && it.balance.customName == balance.customName
            }

            else -> false
        }
    }

    fun addBalance(balance: Balance) {
        viewModelScope.launch {
            balancesRepository.insert(balance)
        }
    }

    fun deleteBalance(balance: Balance) {
        viewModelScope.launch {
            balancesRepository.delete(balance)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _balances.value = UiState.Loading
            try {
                balancesRepository.getBalancesForCurrentCurrency().collect { balances ->
                    _balances.value =
                        UiState.Success(balances)

                    updateTotalBalance(balances)
                }
            } catch (e: Exception) {
                _balances.value = UiState.Error(R.string.error_balances_couldnt_load)
            }
        }
    }

    private fun updateTotalBalance(balances: List<BalanceWithDetails>) {
        var total = 0.0
        for (balance in balances) {
            total += balance.balance.amount
        }
        _totalBalance.value = total
    }

}