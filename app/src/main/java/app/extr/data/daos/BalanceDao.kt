package app.extr.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import app.extr.data.types.Balance
import app.extr.data.types.BalanceWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface BalanceDao {

    @Insert
    suspend fun insert(balance: Balance)

    @Query("DELETE FROM balances WHERE balanceId = :balanceId") //todo: add used_currencies logic here
    suspend fun delete(balanceId: Int)

    @Transaction
    @Query("SELECT * FROM balances")
    fun getBalancesForCurrentCurrency() : Flow<List<BalanceWithDetails>>
}