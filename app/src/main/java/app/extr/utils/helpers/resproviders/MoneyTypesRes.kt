package app.extr.utils.helpers.resproviders

import app.extr.R
import com.example.compose.*

object MoneyTypesRes : ResProvider() {
    override val predefinedAttributes = mapOf(
        0 to ResIconColor(R.drawable.card_icon, balance_light_card_color),
        1 to ResIconColor(R.drawable.cash_icon, balance_light_cash_color),
        2 to ResIconColor(R.drawable.savings_icon, balance_light_savings_color),
        3 to ResIconColor(R.drawable.ewallet_icon, balance_light_electronic_wallet_color),
    )
}