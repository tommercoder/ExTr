package app.extr.data.types

import androidx.room.Entity
import androidx.room.PrimaryKey
import app.extr.utils.helpers.Constants

@Entity(tableName = "money_types")
data class MoneyType(
    @PrimaryKey
    val moneyTypeId: Int,
    val name: String,
    val iconId: Int,
    val colorId: Int
)


