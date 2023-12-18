package app.extr.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import app.extr.data.daos.CurrencyDao
import app.extr.data.daos.MoneyTypeDao
import app.extr.data.daos.UserDao
import app.extr.data.types.Currency
import app.extr.utils.helpers.json.JsonParsers
import app.extr.data.types.MoneyType
import app.extr.data.types.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [MoneyType::class, Currency::class, User::class], version = 8)
//@TypeConverters(Converters::class)
abstract class ExTrDatabase : RoomDatabase() {

    abstract fun moneyTypeDao(): MoneyTypeDao
    abstract fun currencyDao(): CurrencyDao
    abstract fun userDao(): UserDao

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

            val curencies = JsonParsers.parseCurrencies(context)
            curencies?.let {
                database?.currencyDao()?.insertAllFromJson(it)
            }
        }
    }
}