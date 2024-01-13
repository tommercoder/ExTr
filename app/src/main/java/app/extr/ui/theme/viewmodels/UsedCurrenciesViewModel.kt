package app.extr.ui.theme.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.extr.data.repositories.UsedCurrenciesRepository
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

    private var _usedCurrencies = MutableStateFlow<UiState<List<UsedCurrencyDetails>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<UsedCurrencyDetails>>> = _usedCurrencies.asStateFlow()

    init {
        loadData()
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
}