package app.extr.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Transaction
import app.extr.data.types.Expense
import app.extr.data.types.ExpenseWithDetails
import app.extr.data.types.Income
import app.extr.data.types.IncomeWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseIncomeDao {

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insertExpense(expense: Expense)

    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insertIncome(income: Income)

    @RewriteQueriesToDropUnusedColumns
    @Transaction
    @Query(
        """
    SELECT * FROM expenses
        JOIN balances ON expenses.balanceId = balances.balanceId
    WHERE balances.currencyId = (
        SELECT currencyId FROM used_currencies 
        ORDER BY selectionIndex DESC
        LIMIT 1
    ) AND expenses.month = :month AND expenses.year = :year
    """
    )
    fun getExpensesForCurrentCurrency(month: Int, year: Int): Flow<List<ExpenseWithDetails>>

    @RewriteQueriesToDropUnusedColumns
    @Transaction
    @Query(
        """
    SELECT * FROM income
        JOIN balances ON income.balanceId = balances.balanceId
    WHERE balances.currencyId = (
        SELECT currencyId FROM used_currencies 
        ORDER BY selectionIndex DESC
        LIMIT 1
    ) AND income.month = :month AND income.year = :year
    """
    )
    fun getIncomesForCurrentCurrency(month: Int, year: Int): Flow<List<IncomeWithDetails>>

}