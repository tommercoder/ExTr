package app.extr.data.types

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "balances")
data class Balance(
    @PrimaryKey(autoGenerate = true)
    val balanceId: Int = 0,
    @Embedded val currency: Currency,
    @Embedded val moneyType: MoneyType,
    val amount: Float,
    val customName: String
)

//data class BalanceInfo(
//    @Embedded val balance: Balance,
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "currencyId"
//    )
//    val currency: Currency
//
//)