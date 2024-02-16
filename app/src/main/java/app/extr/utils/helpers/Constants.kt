package app.extr.utils.helpers

import androidx.compose.runtime.remember
import androidx.compose.ui.platform.isDebugInspectorInfoEnabled
import androidx.room.PrimaryKey
import app.extr.R
import app.extr.data.types.Balance
import app.extr.data.types.BalanceWithDetails
import app.extr.data.types.Currency
import app.extr.data.types.MoneyType
import app.extr.data.types.TransactionType

object Constants {
    //val BalanceUiState = UiState<List<BalanceWithDetails>>
    //todo: Move all ui states here

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

//
//
//    val EmptyCurrency: Currency = Currency(
//        currencyId = 0,
//        shortName = "",
//        fullName = "",
//        symbol = ' '
//    )
//
//    val EmptyMoneyType: MoneyType = MoneyType(
//        moneyTypeId = 0,
//        name = "",
//        iconId = 0,
//        colorId = 0
//    )
//
//    val EmptyBalance: Balance = Balance(
//        balanceId = 0,
//        currencyId = 0,
//        moneyTypeId = 0,
//        amount = 0f,
//        customName = ""
//    )
//
//    val DefaultTransactionType : TransactionType() {
//        override val id: Int = 0 // Default id
//        override val name: String = "Default Name" // Default name
//        override val iconId: Int = 0 // Default iconId
//        override val colorId: Int = 0 // Default colorId
//        override val isCustom: Boolean = false // Default isCustom
//    }
}