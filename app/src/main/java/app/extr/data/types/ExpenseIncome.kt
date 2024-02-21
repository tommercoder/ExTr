package app.extr.data.types

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

abstract class Transaction {
    abstract val id: Int
    abstract val typeId: Int
    abstract val balanceId: Int
    abstract val currencyId: Int
    abstract val moneyTypeId: Int
    abstract val description: String
    abstract val transactionAmount: Double
    abstract val month: Int
    abstract val year: Int
}

@Entity(
    tableName = "expenses",
    foreignKeys = [
        ForeignKey(
            entity = Balance::class,
            parentColumns = ["balanceId"],
            childColumns = ["balanceId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ExpenseType::class,
            parentColumns = ["id"],
            childColumns = ["typeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["balanceId"]) // This line adds an index for balanceId
    ]
)
data class Expense(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    override val typeId: Int,
    override val balanceId: Int,
    override val currencyId: Int,
    override val moneyTypeId: Int,
    override val description: String,
    override val transactionAmount: Double,
    override val month: Int,
    override val year: Int
) : Transaction()

@Entity(
    tableName = "income",
    foreignKeys = [
        ForeignKey(
            entity = Balance::class,
            parentColumns = ["balanceId"],
            childColumns = ["balanceId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = IncomeType::class,
            parentColumns = ["id"],
            childColumns = ["typeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["balanceId"]) // This line adds an index for balanceId
    ])
data class Income(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    override val typeId: Int,
    override val balanceId: Int,
    override val currencyId: Int,
    override val moneyTypeId: Int,
    override val description: String,
    override val transactionAmount: Double,
    override val month: Int,
    override val year: Int
) : Transaction()

///JOIN
interface TransactionWithDetails {
    val transaction: Transaction
    val transactionType: TransactionType
    val balance: Balance
    val currency: Currency
    val moneyType: MoneyType
}

data class ExpenseWithDetails(
    @Embedded val expense: Expense,
    @Relation(
        parentColumn = "typeId",
        entityColumn = "id"
    )
    val expenseType: ExpenseType,
    @Relation(
        parentColumn = "balanceId",
        entityColumn = "balanceId"
    )
    val balance_: Balance,
    @Relation(
        parentColumn = "currencyId",
        entityColumn = "currencyId"
    )
    val currency_: Currency,
    @Relation(
        parentColumn = "moneyTypeId",
        entityColumn = "moneyTypeId"
    )
    val moneyType_: MoneyType

) : TransactionWithDetails {
    override val transaction: Transaction get() = expense
    override val transactionType: TransactionType get() = expenseType
    override val balance: Balance get() = balance_
    override val currency: Currency get() = currency_
    override val moneyType: MoneyType get() = moneyType_
}

data class IncomeWithDetails(
    @Embedded val income: Income,
    @Relation(
        parentColumn = "typeId",
        entityColumn = "id"
    )
    val incomeType: IncomeType,
    @Relation(
        parentColumn = "balanceId",
        entityColumn = "balanceId"
    )
    val balance_: Balance,
    @Relation(
        parentColumn = "currencyId",
        entityColumn = "currencyId"
    )
    val currency_: Currency,
    @Relation(
        parentColumn = "moneyTypeId",
        entityColumn = "moneyTypeId"
    )
    val moneyType_: MoneyType

) : TransactionWithDetails {
    override val transaction: Transaction get() = income
    override val transactionType: TransactionType get() = incomeType
    override val balance: Balance get() = balance_
    override val currency: Currency get() = currency_
    override val moneyType: MoneyType get() = moneyType_
}