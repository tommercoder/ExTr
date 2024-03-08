package app.extr.utils.helpers.resproviders

import app.extr.R
import app.extr.ui.theme.*

class MoneyTypesRes(customColorsPalette: CustomColorsPalette) : ResProvider() {
    override val predefinedAttributes by lazy {
        mapOf(
            0 to ResIconColor(R.drawable.card_icon, customColorsPalette.balanceCardColor),
            1 to ResIconColor(R.drawable.cash_icon, customColorsPalette.balanceCashColor),
            2 to ResIconColor(R.drawable.savings_icon, customColorsPalette.balanceSavingsColor),
            3 to ResIconColor(R.drawable.ewallet_icon, customColorsPalette.balanceEWalletColor),
        )
    }
}