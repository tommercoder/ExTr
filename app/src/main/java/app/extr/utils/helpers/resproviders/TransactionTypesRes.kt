package app.extr.utils.helpers.resproviders

import app.extr.R
import app.extr.ui.theme.CustomColorsPalette

class ExpenseTypesRes(customColorsPalette: CustomColorsPalette) : ResProvider() {
    override val predefinedAttributes by lazy {
        mapOf(
            0 to ResIconColor(R.drawable.card_icon, customColorsPalette.balanceCardColor),
            1 to ResIconColor(R.drawable.cash_icon, customColorsPalette.balanceCashColor),
            2 to ResIconColor(R.drawable.savings_icon, customColorsPalette.balanceSavingsColor),
            3 to ResIconColor(R.drawable.ewallet_icon, customColorsPalette.balanceEWalletColor),
            4 to ResIconColor(R.drawable.ewallet_icon, customColorsPalette.balanceEWalletColor),
            5 to ResIconColor(R.drawable.ewallet_icon, customColorsPalette.balanceEWalletColor),
            6 to ResIconColor(R.drawable.ewallet_icon, customColorsPalette.balanceEWalletColor),
        )
    }
}

class IncomeTypesRes(customColorsPalette: CustomColorsPalette) : ResProvider() {
    override val predefinedAttributes by lazy {
        mapOf(
            0 to ResIconColor(R.drawable.card_icon, customColorsPalette.balanceCardColor),
            1 to ResIconColor(R.drawable.cash_icon, customColorsPalette.balanceCashColor),
            2 to ResIconColor(R.drawable.savings_icon, customColorsPalette.balanceSavingsColor),
            3 to ResIconColor(R.drawable.ewallet_icon, customColorsPalette.balanceEWalletColor),
            4 to ResIconColor(R.drawable.ewallet_icon, customColorsPalette.balanceEWalletColor),
            5 to ResIconColor(R.drawable.ewallet_icon, customColorsPalette.balanceEWalletColor),
            6 to ResIconColor(R.drawable.ewallet_icon, customColorsPalette.balanceEWalletColor),
        )
    }
}