package app.extr.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import app.extr.data.daos.MoneyTypeDao
import app.extr.utils.helpers.json.JsonParsers
import app.extr.data.types.MoneyType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [MoneyType::class], version = 4)
//@TypeConverters(Converters::class)
abstract class ExTrDatabase : RoomDatabase() {

    abstract fun moneyTypeDao(): MoneyTypeDao

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
        }
    }
}