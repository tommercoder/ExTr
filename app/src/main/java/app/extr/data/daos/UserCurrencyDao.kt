package app.extr.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import app.extr.data.types.UserCurrency
import kotlinx.coroutines.flow.Flow

@Dao
interface UserCurrencyDao {

    @Insert
    suspend fun insert(userCurrency: UserCurrency)

    @Delete
    suspend fun delete(userCurrency: UserCurrency)

    @Query(
        """
        SELECT user_currencies.*, currencies.fullName
        FROM user_currencies
        JOIN currencies ON user_currencies.currencyId = currencies.id
        WHERE userId = :userId
        """
    )
    fun getCurrenciesForUser(userId: Int): Flow<List<UserCurrency>>

    @Query("""
        UPDATE user_currencies 
        SET lastSelected = CASE WHEN currencyId = :currencyId THEN 1 ELSE 0 END
        WHERE userId = :userId
        """)
    suspend fun selectCurrency(userId: Int, currencyId: Int)
}