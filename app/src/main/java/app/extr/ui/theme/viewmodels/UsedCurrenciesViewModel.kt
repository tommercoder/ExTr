package app.extr.ui.theme.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.extr.data.repositories.UsedCurrenciesRepository
import app.extr.data.types.Currency
import app.extr.data.types.UsedCurrency
import app.extr.data.types.UsedCurrencyDetails
import app.extr.utils.helpers.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsedCurrenciesViewModel(
    private val usedCurrenciesRepository: UsedCurrenciesRepository
) : ViewModel() {

    private var _usedCurrencies =
        MutableStateFlow<UiState<List<UsedCurrencyDetails>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<UsedCurrencyDetails>>> = _usedCurrencies.asStateFlow()

    private val _currentlySelectedCurrency = MutableStateFlow<UsedCurrencyDetails?>(null)
    val currentlySelectedCurrency: StateFlow<UsedCurrencyDetails?> =
        _currentlySelectedCurrency.asStateFlow()

    init {
        loadData()
        loadCurrentlySelectedCurrency()
    }

    private fun loadData() {
        viewModelScope.launch {
            _usedCurrencies.value = UiState.Loading
            try {
                usedCurrenciesRepository.getUsedCurrencies().collect {
                    _usedCurrencies.value =
                        UiState.Success(it)
                }
            } catch (e: Exception) {
                Log.e("YourViewModel", "Error fetching balances", e)
            }
        }
    }

    private fun loadCurrentlySelectedCurrency() {
        viewModelScope.launch {
            usedCurrenciesRepository.getCurrentlySelectedUsedCurrency().collect { selected ->
                _currentlySelectedCurrency.value = selected
            }
        }
    }

//    fun getSelectedCurrency(): Currency? {
//        var currency: Currency? = null
//        val state = _usedCurrencies.value
//        if (state is UiState.Success) {
//            if (state.data.isNotEmpty()) {
//                val currentUsedCurrency = state.data.maxBy { it.usedCurrency.selectionIndex }
//                currency = currentUsedCurrency.currency
//            }
//        }
//
//        return currency
//    }

    fun selectCurrency(currencyId: Int) {
        viewModelScope.launch {
            usedCurrenciesRepository.selectCurrency(currencyId)
        }
    }
}