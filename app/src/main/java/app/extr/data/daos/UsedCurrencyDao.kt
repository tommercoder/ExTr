package app.extr.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import app.extr.data.types.UsedCurrency
import app.extr.data.types.UsedCurrencyDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface UsedCurrencyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(usedCurrency: UsedCurrency)

    @Query("DELETE FROM used_currencies WHERE currencyId = :currencyId")
    suspend fun delete(currencyId: Int)

    @Query("SELECT MAX(selectionIndex) FROM used_currencies")
    suspend fun getMaxSelectionIndex() : Int?

    @Transaction
    @Query("SELECT * FROM used_currencies")
    fun getUsedCurrencies() : Flow<List<UsedCurrencyDetails>>
}