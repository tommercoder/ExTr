package app.extr.ui.theme.mappers

import androidx.compose.ui.graphics.Color
import app.extr.data.types.MoneyType
import app.extr.ui.theme.CustomColorsPalette
import app.extr.utils.helpers.resproviders.MoneyTypesRes

interface DropdownItemUi {
    val id: Int
    val name: String
    val icon: Int
    val color: Color
}

fun MoneyType.toDropdownItem(customColorsPalette: CustomColorsPalette) =
    object : DropdownItemUi {
        val moneyTypesRes = MoneyTypesRes(customColorsPalette)

        override val id: Int = this@toDropdownItem.moneyTypeId
        override val name: String = this@toDropdownItem.name
        override val icon: Int = moneyTypesRes.getRes(this@toDropdownItem.iconId).icon
        override val color: Color = moneyTypesRes.getRes(this@toDropdownItem.colorId).color
    }

fun DropdownItemUi.toMoneyType(): MoneyType {
    return MoneyType(
        moneyTypeId = this.id,
        name = this.name,
        iconId = this.id, // ids must match
        colorId = this.id, // ids must match
    )
}