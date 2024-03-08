package app.extr.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.extr.R
import app.extr.data.repositories.BalancesRepository
import app.extr.data.repositories.ExpensesIncomeRepository
import app.extr.data.types.Balance
import app.extr.data.types.Currency
import app.extr.data.types.Expense
import app.extr.data.types.Income
import app.extr.data.types.MoneyType
import app.extr.data.types.Transaction
import app.extr.data.types.TransactionType
import app.extr.data.types.TransactionWithDetails
import app.extr.utils.helpers.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.YearMonth
import java.util.Calendar
import kotlin.time.Duration.Companion.days

data class CombinedExpensesIncomeState(
    val expensesState: UiState<List<TransactionWithDetails>>,
    val incomeState: UiState<List<TransactionWithDetails>>,
    val expensesByCategoriesState: List<TransactionByType>,
    val incomeByCategoriesState: List<TransactionByType>,
    val timePeriodAmountExpensesState: TimePeriodAmount,
    val timePeriodAmountIncomeState: TimePeriodAmount,
)

data class TransactionByType(
    val transactionType: TransactionType,
    val totalAmount: Double,
    val currency: Currency,
    val moneyType: MoneyType,
    val balances: List<Balance>
)

data class TimePeriodAmount(
    val byDay: Int = 0,
    val byWeek: Int = 0,
    val byMonth: Int = 0
)

class ExpensesIncomeViewModel(
    private val expensesIncomeRepository: ExpensesIncomeRepository,
    private val balancesRepository: BalancesRepository
) : ViewModel() {
    private val _expenses = MutableStateFlow<UiState<List<TransactionWithDetails>>>(UiState.Loading)
    private val _income = MutableStateFlow<UiState<List<TransactionWithDetails>>>(UiState.Loading)
    private val _expensesByCategories = MutableStateFlow<List<TransactionByType>>(emptyList())
    private val _incomeByCategories = MutableStateFlow<List<TransactionByType>>(emptyList())
    private val _timePeriodAmountExpenses = MutableStateFlow<TimePeriodAmount>(TimePeriodAmount())
    private val _timePeriodAmountIncome = MutableStateFlow<TimePeriodAmount>(TimePeriodAmount())

    val combinedUiState: StateFlow<CombinedExpensesIncomeState> = combine(
        _expenses,
        _income,
        _expensesByCategories,
        _incomeByCategories,
        _timePeriodAmountExpenses,
        _timePeriodAmountIncome
    ) { array: Array<Any> ->
        CombinedExpensesIncomeState(
            expensesState = array[0] as UiState<List<TransactionWithDetails>>,
            incomeState = array[1] as UiState<List<TransactionWithDetails>>,
            expensesByCategoriesState = array[2] as List<TransactionByType>,
            incomeByCategoriesState = array[3] as List<TransactionByType>,
            timePeriodAmountExpensesState = array[4] as TimePeriodAmount,
            timePeriodAmountIncomeState = array[5] as TimePeriodAmount
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        CombinedExpensesIncomeState(
            UiState.Loading,
            UiState.Loading,
            emptyList(),
            emptyList(),
            TimePeriodAmount(),
            TimePeriodAmount()
        )
    )

    init {
        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        loadTransactions(Date(month, year))
    }

    private fun loadExpenses(date: Date) {
        try {
            viewModelScope.launch {
                _expenses.value = UiState.Loading
                //delay(200)
                expensesIncomeRepository.getExpensesForCurrentCurrency(date).collectLatest {
                    val newIt = UiState.Success(it)
                    _expenses.value = newIt

                    val transactionsByType = sortTransactionsByTypes(newIt.data)
                    _expensesByCategories.value = transactionsByType
                    _timePeriodAmountExpenses.value = getTimePeriodAmount(transactionsByType, date)
                }
            }
        } catch (e: Exception) {
            _expenses.value = UiState.Error(R.string.error_expenses_couldnt_load)
        }
    }

    private fun loadIncome(date: Date) {
        try {
            viewModelScope.launch {
                _income.value = UiState.Loading
                expensesIncomeRepository.getIncomesForCurrentCurrency(date)
                    .distinctUntilChanged().collect {
                    val newIt = UiState.Success(it)
                    _income.value = newIt

                    val transactionsByType = sortTransactionsByTypes(newIt.data)
                    _incomeByCategories.value = transactionsByType
                    _timePeriodAmountIncome.value = getTimePeriodAmount(transactionsByType, date)
                }
            }
        } catch (e: Exception) {
            _income.value = UiState.Error(R.string.error_income_couldnt_load)
        }
    }

    private fun sortTransactionsByTypes(transactions: List<TransactionWithDetails>): List<TransactionByType> {
        return transactions
            .groupBy { it.transactionType } // Group by transaction type ID
            .map { (type, groupedTransactions) ->
                TransactionByType(
                    transactionType = groupedTransactions.first().transactionType,
                    totalAmount = groupedTransactions.sumOf { it.transaction.transactionAmount.toDouble() },
                    currency = groupedTransactions.first().currency,
                    moneyType = groupedTransactions.first().moneyType,
                    balances = groupedTransactions.map { it.balance }.distinctBy { it.balanceId }
                )
            }
    }

    private fun getTimePeriodAmount(
        byType: List<TransactionByType>,
        date: Date
    ): TimePeriodAmount {
        val byMonth = byType.sumOf { it.totalAmount }.toInt()
        val byWeek = byMonth / 4

        val yearMonth = YearMonth.of(date.year, date.month + 1)
        val byDay = byMonth / yearMonth.lengthOfMonth()

        return TimePeriodAmount(byDay, byWeek, byMonth)
    }

    fun insertTransaction(transaction: Transaction) {
        viewModelScope.launch {
            when (transaction) {
                is Expense -> {
                    expensesIncomeRepository.insertExpense(transaction)
                }

                is Income -> {
                    expensesIncomeRepository.insertIncome(transaction)
                }
            }
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            when (transaction) {
                is Expense -> {
                    expensesIncomeRepository.deleteExpense(transaction)
                }

                is Income -> {
                    expensesIncomeRepository.deleteIncome(transaction)
                }
            }
        }
    }

    fun loadTransactions(date: Date) {
        loadExpenses(date)
        loadIncome(date)
    }
}