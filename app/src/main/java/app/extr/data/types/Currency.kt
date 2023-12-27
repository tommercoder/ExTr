package app.extr.data.types

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class Currency(
    @PrimaryKey
    val currencyId : Int,
    val shortName : String,
    val fullName : String,
    val symbol : String
)
