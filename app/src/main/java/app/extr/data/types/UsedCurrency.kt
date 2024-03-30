package app.extr.data.types

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
@Entity(tableName = "used_currencies")
data class UsedCurrency(
    @PrimaryKey
    val currencyId: Int,
    val selectionIndex: Int
)

data class UsedCurrencyDetails(
    @Embedded val usedCurrency: UsedCurrency,
    @Relation(
        parentColumn = "currencyId",
        entityColumn = "currencyId"
    )
    val currency: Currency
)