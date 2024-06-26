package app.extr.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.extr.R
import app.extr.data.repositories.BalancesRepository
import app.extr.data.types.Balance
import app.extr.data.types.BalanceWithDetails
import app.extr.utils.helpers.BalanceWithDetailsState
import app.extr.utils.helpers.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class BalanceUiEvent {
    object Refresh : BalanceUiEvent()
    data class Delete(val balance: Balance) : BalanceUiEvent()
    data class Add(val balance: Balance) : BalanceUiEvent()
}

data class BalancesState(
    val balances: BalanceWithDetailsState,
    val totalBalance: Double
)

class BalancesViewModel(
    private val balancesRepository: BalancesRepository
) : ViewModel() {

    private var _balances = MutableStateFlow<BalanceWithDetailsState>(UiState.Loading)
    private var _totalBalance = MutableStateFlow(0.0)

    val combinedUiState: StateFlow<BalancesState> = combine(
        _balances,
        _totalBalance,
    ) { balances, totalBalance ->
        BalancesState(
            balances = balances,
            totalBalance = totalBalance
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, BalancesState(UiState.Loading,0.0))

    init {
        loadData()
    }

    private fun refreshData() {
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

    fun onEvent(event: BalanceUiEvent) {
        when (event) {
            is BalanceUiEvent.Refresh -> {
                refreshData()
            }

            is BalanceUiEvent.Delete -> {
                deleteBalance(event.balance)
            }

            is BalanceUiEvent.Add -> {
                addBalance(event.balance)
            }
        }
    }

    private fun addBalance(balance: Balance) {
        viewModelScope.launch {
            balancesRepository.insert(balance)
        }
    }

    private fun deleteBalance(balance: Balance) {
        viewModelScope.launch {
            balancesRepository.delete(balance)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _balances.value = UiState.Loading
            try {
                balancesRepository.getBalancesForCurrentCurrency().collectLatest { balances ->
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