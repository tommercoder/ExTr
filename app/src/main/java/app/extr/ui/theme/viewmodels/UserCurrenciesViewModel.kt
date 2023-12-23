package app.extr.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.extr.data.CommonDataHolder
import app.extr.data.repositories.UserCurrenciesRepository
import app.extr.data.types.CurrencyLastSelected
import app.extr.data.types.User
import app.extr.data.types.UserCurrencyCrossRef
import app.extr.data.types.UserWithCurrencies
import app.extr.utils.helpers.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UserCurrencyUiEvent {
    data class CurrencySelected(val id: Int) : UserCurrencyUiEvent()
    data class CurrencyDeleted(val currency: UserCurrencyCrossRef) : UserCurrencyUiEvent()
}

class UserCurrenciesViewModel(
    private val userCurrenciesRepository: UserCurrenciesRepository
) : ViewModel() {

    private val _userCurrencies =
        MutableStateFlow<UiState<List<CurrencyLastSelected>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<CurrencyLastSelected>>> = _userCurrencies.asStateFlow()

    init {
//        val userCurrencyCrossRef: UserCurrencyCrossRef = UserCurrencyCrossRef(0, 0, true)
//        val userCurrencyCrossRef1: UserCurrencyCrossRef = UserCurrencyCrossRef(1, 0, true)
//        //val userCurrencyCrossRef2: UserCurrencyCrossRef = UserCurrencyCrossRef(1, 1, false)
//
//        addCurrencyForCurrentUser(userCurrencyCrossRef)
//        addCurrencyForCurrentUser(userCurrencyCrossRef1)
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _userCurrencies.value = UiState.Loading
            try {
                CommonDataHolder.currentUserId?.let {
                userCurrenciesRepository.getCurrenciesForUser(it).collect { values ->
                    _userCurrencies.value = UiState.Success(values)
                }
                }
            } catch (e: Exception) {
                //
            }
        }
    }

    fun addCurrencyForCurrentUser(userCurrencyCrossRef: UserCurrencyCrossRef) {
        viewModelScope.launch {
            userCurrenciesRepository.insert(userCurrencyCrossRef)
            userCurrenciesRepository.selectCurrency(userCurrencyCrossRef.userId, userCurrencyCrossRef.currencyId)
        }
    }


}