package app.extr.ui.theme.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.extr.data.repositories.CurrenciesRepository
import app.extr.data.types.Currency
import app.extr.data.types.MoneyType
import kotlinx.coroutines.launch

class CurrenciesViewModel(
    private val currenciesRepository: CurrenciesRepository
) : ViewModel() {
    private val _currencies = MutableLiveData<List<Currency>>()
    val currencies : LiveData<List<Currency>> = _currencies

    init {
        viewModelScope.launch {
            _currencies.setValue(currenciesRepository.getAllCurrencies())
        }
    }
}