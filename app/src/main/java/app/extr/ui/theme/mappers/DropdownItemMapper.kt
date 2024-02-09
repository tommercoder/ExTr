package app.extr.ui.theme.mappers

import androidx.compose.ui.graphics.Color
import app.extr.data.types.Balance
import app.extr.data.types.BalanceWithDetails
import app.extr.data.types.MoneyType
import app.extr.data.types.TransactionType
import app.extr.data.types.TransactionWithDetails
import app.extr.utils.helpers.resproviders.ExpenseTypesRes
import app.extr.utils.helpers.resproviders.IncomeTypesRes
import app.extr.utils.helpers.resproviders.MoneyTypesRes

interface DropdownItemUi {
    val id: Int
    val name: String
    val icon: Int
    val color: Color
}

fun MoneyType.toDropdownItem(
    moneyTypesRes: MoneyTypesRes
) = object : DropdownItemUi {
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

fun BalanceWithDetails.toDropdownItem(moneyTypesRes: MoneyTypesRes) =
    object: DropdownItemUi {
        override val id: Int = this@toDropdownItem.balance.balanceId
        override val name: String = this@toDropdownItem.balance.customName
        override val icon: Int = moneyTypesRes.getRes(this@toDropdownItem.moneyType.iconId).icon
        override val color: Color = moneyTypesRes.getRes(this@toDropdownItem.moneyType.colorId).color
    }

fun TransactionType.toDropdownItem(
    expenseTypesRes: ExpenseTypesRes
) = object: DropdownItemUi {
        override val id: Int = this@toDropdownItem.id
        override val name: String = this@toDropdownItem.name
        override val icon: Int = expenseTypesRes.getRes(this@toDropdownItem.iconId).icon
        override val color: Color = expenseTypesRes.getRes(this@toDropdownItem.colorId).color
    }

fun TransactionType.toDropdownItem(
    incomeTypesRes: IncomeTypesRes
) = object: DropdownItemUi {
    override val id: Int = this@toDropdownItem.id
    override val name: String = this@toDropdownItem.name
    override val icon: Int = incomeTypesRes.getRes(this@toDropdownItem.iconId).icon
    override val color: Color = incomeTypesRes.getRes(this@toDropdownItem.colorId).color
}