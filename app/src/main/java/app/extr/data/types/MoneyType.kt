package app.extr.data.types

import androidx.room.Entity
import androidx.room.PrimaryKey
import app.extr.utils.helpers.Constants

//enum class MoneyTypeRes(
//    @DrawableRes override val iconId: Int,
//    @ColorRes override val colorId: Int,
//    override val storedId: Int
//) : DatabaseMapperEnum {
//
//    Card(R.drawable.card_icon, R.color.mt_card_color, 1),
//    Cash(R.drawable.card_icon, R.color.mt_card_color, 2);
//
//}

@Entity(tableName = "money_types")
data class MoneyType(
    @PrimaryKey
    val moneyTypeId: Int = Constants.DefaultMoneyTypeId,
    val name: String = "",
    val iconId: Int = Constants.DefaultIconId,
    val colorId: Int = Constants.DefaultColorId
)


