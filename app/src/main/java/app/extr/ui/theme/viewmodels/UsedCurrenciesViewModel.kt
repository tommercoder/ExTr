package app.extr.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.extr.data.repositories.UsedCurrenciesRepository
import app.extr.data.types.UsedCurrencyDetails
import app.extr.utils.helpers.UiState
import app.extr.utils.helpers.UsedCurrencyDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class UsedCurrenciesState(
    val usedCurrencies: UsedCurrencyDetailsState,
    val currentlySelectedCurrency: UsedCurrencyDetails?
)

class UsedCurrenciesViewModel(
    private val usedCurrenciesRepository: UsedCurrenciesRepository
) : ViewModel() {

    private var _usedCurrencies = MutableStateFlow<UsedCurrencyDetailsState>(UiState.Loading)
    private val _currentlySelectedCurrency = MutableStateFlow<UsedCurrencyDetails?>(null)

    val combinedUiState: StateFlow<UsedCurrenciesState> = combine(
        _usedCurrencies,
        _currentlySelectedCurrency,
    ) { usedCurrencies, currentlySelectedCurrency ->
        UsedCurrenciesState(
            usedCurrencies = usedCurrencies,
            currentlySelectedCurrency = currentlySelectedCurrency
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, UsedCurrenciesState(UiState.Loading, null))

    init {
        loadData()
        loadCurrentlySelectedCurrency()
    }

    private fun loadData() {
        viewModelScope.launch {
            _usedCurrencies.value = UiState.Loading
            try {
                usedCurrenciesRepository.getUsedCurrencies().collectLatest {
                    _usedCurrencies.value =
                        UiState.Success(it)
                }
            } catch (_: Exception) {

            }
        }
    }

    private fun loadCurrentlySelectedCurrency() {
        viewModelScope.launch {
            usedCurrenciesRepository.getCurrentlySelectedUsedCurrency().collectLatest { selected ->
                _currentlySelectedCurrency.value = selected
            }
        }
    }

    fun selectCurrency(currencyId: Int) {
        viewModelScope.launch {
            usedCurrenciesRepository.selectCurrency(currencyId)
        }
    }
}