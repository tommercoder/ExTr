package app.extr.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.extr.data.repositories.MoneyTypesRepository
import app.extr.data.types.MoneyType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

//todo: remove if not used afterall
data class MoneyTypeUiState(
    val isLoading: Boolean = false,
    val list: List<MoneyType> = emptyList(),
    //val errorMessage: String? = null
)

class MoneyTypesViewModel(
    private val moneyTypeRepository: MoneyTypesRepository
) : ViewModel() {
    private val _moneyTypes = MutableStateFlow(MoneyTypeUiState())
    val uiState: StateFlow<MoneyTypeUiState> = _moneyTypes.asStateFlow()

    init {
        loadMoneyTypes()
    }

    private fun loadMoneyTypes() {
        viewModelScope.launch {
            try {
                _moneyTypes.value = MoneyTypeUiState(isLoading = true)
                val list = moneyTypeRepository.getAllMoneyTypes()
                _moneyTypes.value = MoneyTypeUiState(list = list)
            } catch (e: Exception) {
                //_moneyTypes.value = MoneyTypeUiState(errorMessage = "error")
//                    MoneyTypeUiState(errorMessage = getString(R.string.money_type_error))
            }
        }
    }
}