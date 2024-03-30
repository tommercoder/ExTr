package app.extr.utils.helpers

import app.extr.data.types.Balance

data class BottomSheetAcceptType(
    val currencyId: Int,
    val moneyTypeId: Int,
    val amount: Double,
    val name: String = ""
)

fun BottomSheetAcceptType.toBalance(): Balance {
    return Balance(
        currencyId = this.currencyId,
        moneyTypeId = this.moneyTypeId,
        amount = this.amount,
        customName = this.name
    )
}

data class BottomSheetAcceptTypeTransaction(
    val balanceId: Int,
    val transactionTypeId: Int,
    val amount: String,
    val name: String = "",
    val month: Int,
    val year: Int
)