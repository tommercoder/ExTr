package app.extr.data.types

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.Junction
import androidx.room.Relation

@Entity(
    tableName = "user_currencies",
    primaryKeys = ["userId", "currencyId"],
    foreignKeys = [
        ForeignKey(
            entity = Currency::class,
            parentColumns = ["id"],
            childColumns = ["currencyId"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = CASCADE
        )
    ],
    indices = [Index(value = ["currencyId"])]
)
data class UserCurrencyCrossRef(
    val userId: Int,
    val currencyId: Int,
    val lastSelected: Boolean
)

data class CurrencyLastSelected(
    @Embedded val currency: Currency,
    val lastSelected: Boolean
)

data class UserWithCurrencies(
    @Embedded val user: User,
    val currencies: List<CurrencyLastSelected>,
)

//
//data class UserWithCurrencies(
//    @Embedded val user: User,
//    @Relation(
//        parentColumn = "id", // users
//        entityColumn = "id", // currencies
//        associateBy = Junction(
//            UserCurrencyCrossRef::class
//        )
//    )
//    val currencies: List<CurrencyLastSelected>
//)