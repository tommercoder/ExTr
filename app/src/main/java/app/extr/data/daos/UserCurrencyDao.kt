package app.extr.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.extr.data.types.CurrencyLastSelected
import app.extr.data.types.UserCurrencyCrossRef
import app.extr.data.types.UserWithCurrencies
import kotlinx.coroutines.flow.Flow

@Dao
interface UserCurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userCurrency: UserCurrencyCrossRef)

    @Delete
    suspend fun delete(userCurrency: UserCurrencyCrossRef)

    @Query(
        """
        SELECT currencies.*, user_currencies.lastSelected 
        FROM user_currencies
        JOIN currencies ON user_currencies.currencyId = currencies.id
        WHERE userId = :userId
        """
    )
    fun getCurrenciesForUser(userId: Int): Flow<List<CurrencyLastSelected>>

    @Query("""
        UPDATE user_currencies 
        SET lastSelected = CASE WHEN currencyId = :currencyId THEN 1 ELSE 0 END
        WHERE userId = :userId
        """)
    suspend fun selectCurrency(userId: Int, currencyId: Int)
}