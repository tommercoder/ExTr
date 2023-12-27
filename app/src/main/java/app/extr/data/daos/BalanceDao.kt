package app.extr.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import app.extr.data.types.Balance
import kotlinx.coroutines.flow.Flow

@Dao
interface BalanceDao {

    @Insert
    suspend fun insert(balance: Balance)

    @Query("SELECT * FROM balances")
    fun getBalancesForCurrentCurrency() : Flow<List<Balance>>
}