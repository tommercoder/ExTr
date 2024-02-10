package app.extr.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.extr.R
import app.extr.data.repositories.BalancesRepository
import app.extr.data.repositories.ExpensesIncomeTypesRepository
import app.extr.data.types.Balance
import app.extr.data.types.BalanceWithDetails
import app.extr.data.types.TransactionType
import app.extr.utils.helpers.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class CombinedUiState(
    val bottomSheetVisible: Boolean,
    val balancesState: UiState<List<BalanceWithDetails>>,
    val expenseTypesState: UiState<List<TransactionType>>,
    val incomeTypesState: UiState<List<TransactionType>>,
    val preSelectedTransactionTypeId: Int? = null
)

class ExpensesIncomeBottomSheetViewModel(
    private val balancesRepository: BalancesRepository,
    private val expensesIncomeTypesRepository: ExpensesIncomeTypesRepository
) : ViewModel() {

    private val _bottomSheetVisible = MutableStateFlow(false)
    private val _balancesData = MutableStateFlow<UiState<List<BalanceWithDetails>>>(UiState.Loading)
    private val _expenseTypes = MutableStateFlow<UiState<List<TransactionType>>>(UiState.Loading)
    private val _incomeTypes = MutableStateFlow<UiState<List<TransactionType>>>(UiState.Loading)
    private val _preSelectedTransactionTypeId = MutableStateFlow<Int?>(null)

    val combinedUiState: StateFlow<CombinedUiState> = combine(
        _bottomSheetVisible,
        _balancesData,
        _expenseTypes,
        _incomeTypes,
        _preSelectedTransactionTypeId
    ) { bottomSheetVisible, balancesData, expenseTypes, incomeTypes, preSelectedTransactionTypeId ->
        CombinedUiState(
            bottomSheetVisible = bottomSheetVisible,
            balancesState = balancesData,
            expenseTypesState = expenseTypes,
            incomeTypesState = incomeTypes,
            preSelectedTransactionTypeId = preSelectedTransactionTypeId
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, CombinedUiState(false, UiState.Loading, UiState.Loading, UiState.Loading, 0))

    init {
        loadBalances()
        loadExpenseTypes()
        loadIncomeTypes()
    }

    fun toggleBottomSheet(show: Boolean, preSelectedTypeId: Int? = null): Boolean {
        val balances = _balancesData.value
        if (balances is UiState.Loading || balances is UiState.Error) {
            return false
        }

        if(balances is UiState.Success && balances.data.isEmpty()){
            return false
        }

        _preSelectedTransactionTypeId.value = preSelectedTypeId
        _bottomSheetVisible.value = show
        return true
    }

    fun getBalanceById(balances: List<BalanceWithDetails>, id: Int): Balance {
        return balances.first { b -> b.balance.balanceId == id }.balance
    }

    private fun loadBalances() {
        viewModelScope.launch {
            _balancesData.value = UiState.Loading
            try {
                balancesRepository.getBalancesForCurrentCurrency().collect { balances ->
                    _balancesData.value =
                        UiState.Success(balances)
                }
            } catch (e: Exception) {
                _balancesData.value = UiState.Error(R.string.error_balances_couldnt_load)
            }
        }
    }

    private fun loadExpenseTypes() {
        viewModelScope.launch {
            _expenseTypes.value = UiState.Loading
            try {
                expensesIncomeTypesRepository.getAllExpenseTypes().collect {
                    _expenseTypes.value = UiState.Success(it)
                }
            } catch (e: Exception) {
                _expenseTypes.value = UiState.Error(R.string.error_transaction_types_couldnt_load)
            }
        }
    }

    private fun loadIncomeTypes() {
        viewModelScope.launch {
            _incomeTypes.value = UiState.Loading
            try {
                expensesIncomeTypesRepository.getAllIncomeTypes().collect {
                    _incomeTypes.value = UiState.Success(it)
                }
            } catch (e: Exception) {
                _incomeTypes.value = UiState.Error(R.string.error_transaction_types_couldnt_load)
            }
        }
    }
}