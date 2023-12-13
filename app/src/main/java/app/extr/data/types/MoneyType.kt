package app.extr.data.types

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import app.extr.R

//move later
//enum class IconFromId(
//    @DrawableRes val resId: Int,
//    val storedId: Int
//) {
//    CardIcon(R.drawable.card_icon, 1),
//    CashIcon(R.drawable.card_icon, 2);
//
//    companion object {
//        @DrawableRes
//        fun asRes(stored: Int): Int = values().first() { it.storedId == stored }.resId
//    }
//}

@Entity(tableName = "tbl_moneyTypes")
data class MoneyType(
    @PrimaryKey
    val id: Int,
    val name: String,
    val colorName: String,
    //val iconId: Int
val iconName : String
)


