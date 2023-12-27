package app.extr.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.extr.data.repositories.BalancesRepository
import app.extr.data.types.Balance
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

    private var _balances = MutableStateFlow<UiState<List<Balance>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Balance>>> = _balances.asStateFlow()

    init {

        val currency = Currency(0, "USD", "DOllar", "$")
        val currency2 = Currency(9, "SEK", "DOllar", "kr")
        val moneyType = MoneyType(1, "Card", 1, 2)
        val moneyType2 = MoneyType(2, "Cash", 1, 2)

        val balance = Balance(currency = currency, moneyType = moneyType, amount = 13.123f, customName = "blabla")
        val balance2 = Balance(currency = currency2, moneyType = moneyType2, amount = 12323.3125f, customName = "blabla")

        addBalance(balance)
        addBalance(balance2)
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _balances.value = UiState.Loading
            try {
                balancesRepository.getBalancesForCurrentCurrency().collect {
                    _balances.value =
                        UiState.Success(it)
                }
            } catch (e: Exception) {

            }
        }
    }

    fun addBalance(balance: Balance){
        viewModelScope.launch {
            balancesRepository.insert(balance)
        }
    }
}