package app.extr.data.types

import androidx.room.Entity
import androidx.room.PrimaryKey
import app.extr.utils.helpers.Constants

@Entity(tableName = "currencies")
data class Currency(
    @PrimaryKey
    val currencyId : Int = Constants.DefaultCurrencyId,
    val shortName : String = "",
    val fullName : String = "",
    val symbol : Char = ' '
)
