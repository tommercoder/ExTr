package app.extr.data.types

import androidx.room.Entity
import androidx.room.PrimaryKey

abstract class TransactionType {
    abstract val id: Int
    abstract val name: String
    abstract val iconId: Int
    abstract val colorId: Int
    abstract val isCustom: Boolean
}

@Entity(tableName = "expense_types")
data class ExpenseType(
    @PrimaryKey
    override val id: Int,
    override val name: String,
    override val iconId: Int,
    override val colorId: Int,
    override val isCustom: Boolean
) : TransactionType()

@Entity(tableName = "income_types")
data class IncomeType(
    @PrimaryKey
    override val id: Int,
    override val name: String,
    override val iconId: Int,
    override val colorId: Int,
    override val isCustom: Boolean
) : TransactionType()


