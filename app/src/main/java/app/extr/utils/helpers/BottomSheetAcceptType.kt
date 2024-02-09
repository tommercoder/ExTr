package app.extr.utils.helpers

data class BottomSheetAcceptType(
    val currencyId: Int,
    val moneyTypeId: Int,
    val amount: String,
    val name: String = ""
)

data class BottomSheetAcceptTypeTransaction(
    val balanceId: Int,
    val transactionTypeId: Int,
    val amount: String,
    val name: String = "",
    val month: Int,
    val year: Int
)