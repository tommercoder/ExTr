package app.extr.utils.helpers.resproviders

import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import app.extr.R
import app.extr.ui.theme.*
import com.example.compose.*

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