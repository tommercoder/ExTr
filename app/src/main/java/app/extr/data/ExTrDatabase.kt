package app.extr.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import app.extr.data.daos.BalanceDao
import app.extr.data.daos.CurrencyDao
import app.extr.data.daos.ExpenseIncomeTypesDao
import app.extr.data.daos.MoneyTypeDao
import app.extr.data.daos.UsedCurrencyDao
import app.extr.data.daos.UserDao
import app.extr.data.types.Balance
import app.extr.data.types.Currency
import app.extr.data.types.ExpenseType
import app.extr.data.types.IncomeType
import app.extr.utils.helpers.json.JsonParsers
import app.extr.data.types.MoneyType
import app.extr.data.types.UsedCurrency
import app.extr.data.types.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [MoneyType::class, Currency::class, User::class, Balance::class, UsedCurrency::class, ExpenseType::class, IncomeType::class],
    version = 10,
    exportSchema = true
)
//@TypeConverters(Converters::class)
abstract class ExTrDatabase : RoomDatabase() {

    abstract fun moneyTypeDao(): MoneyTypeDao
    abstract fun currencyDao(): CurrencyDao
    abstract fun userDao(): UserDao
    abstract fun balanceDao(): BalanceDao
    abstract fun usedCurrencyDao(): UsedCurrencyDao
    abstract fun expenseIncomeTypesDao(): ExpenseIncomeTypesDao

    companion object {
        @Volatile //thread safety
        private var Instance: ExTrDatabase? = null

        fun getDatabase(context: Context): ExTrDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ExTrDatabase::class.java, "extr_database")
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            CoroutineScope(Dispatchers.IO).launch {
                                populateDatabase(context, Instance)
                            }
                        }
                    })
                    .fallbackToDestructiveMigration() // remove after testing!!!
                    .build()
                    .also { Instance = it }
            }
        }

        suspend fun populateDatabase(context: Context, database: ExTrDatabase?) {
            val moneyTypes = JsonParsers.parseMoneyTypes(context)
            moneyTypes?.let {
                database?.moneyTypeDao()?.insertAllFromJson(it)
            }

            val currencies = JsonParsers.parseCurrencies(context)
            currencies?.let {
                database?.currencyDao()?.insertAllFromJson(it)
            }

            val expenseTypes = JsonParsers.parseExpenseTypes(context)
            expenseTypes?.let {
                database?.expenseIncomeTypesDao()?.insertAllExpenseTypesFromJson(it)
            }

            val incomeTypes = JsonParsers.parseIncomeTypes(context)
            incomeTypes?.let {
                database?.expenseIncomeTypesDao()?.insertAllIncomeTypesFromJson(it)
            }
        }
    }
}