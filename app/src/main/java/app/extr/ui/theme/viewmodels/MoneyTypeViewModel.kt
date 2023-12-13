package app.extr.ui.theme.viewmodels

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.extr.data.repositories.MoneyTypeRepository
import app.extr.data.types.MoneyType
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoneyTypeViewModel(
    private val moneyTypeRepository: MoneyTypeRepository
) : ViewModel() {
    private val _moneyTypes = MutableLiveData<List<MoneyType>>()
    val moneyTypes : LiveData<List<MoneyType>> = _moneyTypes

    init {
        viewModelScope.launch {
            _moneyTypes.setValue(moneyTypeRepository.getAllMoneyTypes())
        }
    }
}