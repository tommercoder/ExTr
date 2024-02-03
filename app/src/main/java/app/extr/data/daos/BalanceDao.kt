package app.extr.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import app.extr.data.types.Balance
import app.extr.data.types.BalanceWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface BalanceDao {

    @Insert
    suspend fun insert(balance: Balance)

    //@Query("DELETE FROM balances WHERE balanceId = :balanceId") //todo: add used_currencies logic here
    @Delete
    suspend fun delete(balance: Balance)

    @Update
    suspend fun update(balance: Balance)

    @Query("SELECT * FROM balances WHERE balanceId = :balanceId")
    suspend fun getBalanceById(balanceId: Int): Balance

    @Transaction //do I need it here?
    @Query(
        """
    SELECT * FROM balances 
        WHERE currencyId = (
            SELECT currencyId FROM used_currencies 
            ORDER BY selectionIndex DESC
            LIMIT 1
    )
    """
    )
    fun getBalancesForCurrentCurrency(): Flow<List<BalanceWithDetails>>

    @Query("SELECT COUNT(*) FROM balances WHERE currencyId = :currencyId")
    suspend fun getBalanceCountForCurrentCurrency(currencyId: Int): Int
}