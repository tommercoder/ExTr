package app.extr.data.repositories

import androidx.room.Query
import androidx.room.Transaction
import app.extr.data.daos.ExpenseIncomeTypesDao
import app.extr.data.types.ExpenseType
import app.extr.data.types.IncomeType
import app.extr.data.types.TransactionType
import kotlinx.coroutines.flow.Flow

interface ExpenseIncomeTypesRepository{
    fun getAllExpenseTypes() : Flow<List<TransactionType>>
    fun getAllIncomeTypes() : Flow<List<TransactionType>>
}

class ExpenseIncomeTypesRepositoryImpl(private val expenseIncomeTypesDao: ExpenseIncomeTypesDao) : ExpenseIncomeTypesRepository {
    override fun getAllExpenseTypes(): Flow<List<TransactionType>> {
        return expenseIncomeTypesDao.getAllExpenseTypes()
    }

    override fun getAllIncomeTypes(): Flow<List<TransactionType>> {
        return expenseIncomeTypesDao.getAllIncomeTypes()
    }
}