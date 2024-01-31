package app.extr.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.extr.data.types.ExpenseType
import app.extr.data.types.IncomeType
import app.extr.data.types.TransactionType
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseIncomeTypesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllExpenseTypesFromJson(items: List<ExpenseType>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllIncomeTypesFromJson(items: List<IncomeType>)

    @Query("SELECT * FROM expense_types")
    fun getAllExpenseTypes() : Flow<List<ExpenseType>>

    @Query("SELECT * FROM income_types")
    fun getAllIncomeTypes() : Flow<List<IncomeType>>
}