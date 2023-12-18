package app.extr.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.extr.data.types.Currency
import app.extr.data.types.MoneyType

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFromJson(items: List<Currency>)

    @Query("SELECT * FROM currencies")
    suspend fun getAll() : List<Currency>
}