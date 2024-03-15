package app.extr.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.extr.data.repositories.MoneyTypesRepository
import app.extr.data.types.MoneyType
import app.extr.utils.helpers.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MoneyTypesViewModel(
    private val moneyTypeRepository: MoneyTypesRepository
) : ViewModel() {
    private val _moneyTypes = MutableStateFlow<UiState<List<MoneyType>>>(UiState.Loading)
    val moneyTypes: StateFlow<UiState<List<MoneyType>>> = _moneyTypes.asStateFlow()

    init {
        loadMoneyTypes()
    }

    private fun loadMoneyTypes() {
        viewModelScope.launch {
            _moneyTypes.value = UiState.Loading
            try {
                moneyTypeRepository.getAllMoneyTypes().collectLatest {
                    _moneyTypes.value = UiState.Success(it)
                }
            } catch (_: Exception) {

            }
        }
    }
}