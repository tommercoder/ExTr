package app.extr.utils.helpers.resproviders

import app.extr.R
import app.extr.ui.theme.mt_card_color

object MoneyTypesRes : ResProvider() {
    override val predefinedAttributes = mapOf(
        1 to ResIconColor(R.drawable.card_icon, mt_card_color),
        2 to ResIconColor(R.drawable.card_icon, mt_card_color),
    )
}