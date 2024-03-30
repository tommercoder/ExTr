package app.extr.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.extr.data.repositories.CurrenciesRepository
import app.extr.utils.helpers.CurrencyState
import app.extr.utils.helpers.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CurrenciesViewModel(
    private val currenciesRepository: CurrenciesRepository
) : ViewModel() {
    private val _currencies = MutableStateFlow<CurrencyState>(UiState.Loading)
    val currencies: StateFlow<CurrencyState> = _currencies.asStateFlow()

    init {
        viewModelScope.launch {
            _currencies.value = UiState.Loading
            try {
                currenciesRepository.getAllCurrencies().collectLatest {
                    _currencies.value = UiState.Success(it)
                }
            } catch (_: Exception) {

            }
        }
    }
}