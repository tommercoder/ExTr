package app.extr.data.types

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    tableName = "balances"
)
data class Balance(
    @PrimaryKey(autoGenerate = true)
    val balanceId: Int = 0,
    val currencyId: Int,
    val moneyTypeId: Int,
    val amount: Double,
    val customName: String
)

data class BalanceWithDetails(
    @Embedded val balance: Balance,
    @Relation(
        parentColumn = "currencyId",
        entityColumn = "currencyId"
    )
    val currency: Currency,
    @Relation(
        parentColumn = "moneyTypeId",
        entityColumn = "moneyTypeId"
    )
    val moneyType: MoneyType
)