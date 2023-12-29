package app.extr.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.extr.data.types.MoneyType
import kotlinx.coroutines.flow.Flow

@Dao
interface MoneyTypeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFromJson(items: List<MoneyType>)

    @Query("SELECT * FROM money_types")
    fun getAll() : Flow<List<MoneyType>>
}