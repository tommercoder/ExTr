package app.extr.utils.helpers

import app.extr.R
import app.extr.data.types.BalanceWithDetails
import app.extr.data.types.Currency
import app.extr.data.types.MoneyType
import app.extr.data.types.TransactionType
import app.extr.data.types.TransactionWithDetails
import app.extr.data.types.UsedCurrencyDetails
import app.extr.data.types.User

object Constants {
    const val precisionTwo = "%.2f"
    const val precisionZero = "%.0f"

    val months = listOf(
        R.string.month_jan,
        R.string.month_feb,
        R.string.month_mar,
        R.string.month_apr,
        R.string.month_may,
        R.string.month_jun,
        R.string.month_jul,
        R.string.month_aug,
        R.string.month_sep,
        R.string.month_oct,
        R.string.month_nov,
        R.string.month_dec
    )
}

typealias BalanceWithDetailsState = UiState<List<BalanceWithDetails>>
typealias TransactionTypeState = UiState<List<TransactionType>>
typealias CurrencyState = UiState<List<Currency>>
typealias TransactionWithDetailsState = UiState<List<TransactionWithDetails>>
typealias MoneyTypeState = UiState<List<MoneyType>>
typealias UsedCurrencyDetailsState = UiState<List<UsedCurrencyDetails>>
typealias UserState = UiState<User?>