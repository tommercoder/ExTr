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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(usedCurrency: UsedCurrency)

    @Query("DELETE FROM used_currencies WHERE currencyId = :currencyId")
    suspend fun delete(currencyId: Int)

    @Query("SELECT MAX(selectionIndex) FROM used_currencies")
    suspend fun getMaxSelectionIndex() : Int?

    @Transaction
    @Query("SELECT * FROM used_currencies WHERE selectionIndex = (SELECT MAX(selectionIndex) FROM used_currencies)")
    fun getCurrentlySelectedUsedCurrency() : Flow<UsedCurrencyDetails>

    @Transaction
    @Query("SELECT * FROM used_currencies")
    fun getUsedCurrencies() : Flow<List<UsedCurrencyDetails>>

    @Query("UPDATE used_currencies SET selectionIndex = :newIndex WHERE currencyId = :selectedCurrencyId")
    suspend fun incrementSelectedEntryIndex(selectedCurrencyId: Int, newIndex: Int)

    @Query("UPDATE used_currencies SET selectionIndex = selectionIndex - 1 WHERE selectionIndex > :originalIndex")
    suspend fun decrementHigherEntriesIndex(originalIndex: Int)

    @Transaction
    suspend fun updateIndices(selectedId: Int) {
        val originalIndex = getIndexOfEntry(selectedId)
        val maxIndex = getMaxSelectionIndex() ?: 0

        incrementSelectedEntryIndex(
            selectedCurrencyId = selectedId,
            newIndex = maxIndex + 1)

        decrementHigherEntriesIndex(originalIndex)
    }

    @Query("SELECT selectionIndex FROM used_currencies WHERE currencyId = :currencyId")
    suspend fun getIndexOfEntry(currencyId: Int): Int
}