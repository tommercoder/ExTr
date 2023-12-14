package app.extr.utils.helpers.resproviders

import app.extr.R
import app.extr.ui.theme.Purple80
import app.extr.ui.theme.mt_card_color

class MoneyTypeRes : CategoryResAbstract() {
    override fun getPredefinedAttributes(id: Int): CategoryAttributes? {
        return predefinedAttributes[id]
    }

    override fun defaultAttributes(): CategoryAttributes {
        return CategoryAttributes(R.drawable.ic_launcher_background, Purple80)
    }

    companion object {
        private val predefinedAttributes = mapOf(
            1 to CategoryAttributes(R.drawable.card_icon, mt_card_color),
            2 to CategoryAttributes(R.drawable.card_icon, mt_card_color),
        )
    }
}