package app.extr.ui.theme.mappers

import androidx.compose.ui.graphics.Color
import app.extr.data.types.MoneyType
import app.extr.utils.helpers.resproviders.MoneyTypesRes

interface DropdownItemUi {
    val id: Int
    val name: String
    val icon: Int?
    val color: Color?
    val symbol: String?
}

fun MoneyType.toDropdownItem() =
    object : DropdownItemUi {
        override val id: Int = this@toDropdownItem.moneyTypeId
        override val name: String = this@toDropdownItem.name
        override val icon: Int = MoneyTypesRes.getRes(this@toDropdownItem.iconId).icon
        override val color: Color = MoneyTypesRes.getRes(this@toDropdownItem.colorId).color
        override val symbol: String? = null
    }

