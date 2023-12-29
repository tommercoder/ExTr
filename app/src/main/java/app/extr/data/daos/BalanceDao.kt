package app.extr.data.daos

import androidx.room.Dao
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

    @Transaction
    @Query("SELECT * FROM balances")
    fun getBalancesForCurrentCurrency() : Flow<List<BalanceWithDetails>>
}