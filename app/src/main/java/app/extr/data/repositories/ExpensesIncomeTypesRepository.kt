package app.extr.data.repositories

import app.extr.data.daos.ExpenseIncomeTypesDao
import app.extr.data.types.TransactionType
import kotlinx.coroutines.flow.Flow

interface ExpensesIncomeTypesRepository{
    fun getAllExpenseTypes() : Flow<List<TransactionType>>
    fun getAllIncomeTypes() : Flow<List<TransactionType>>
}

class ExpensesIncomeTypesRepositoryImpl(private val expenseIncomeTypesDao: ExpenseIncomeTypesDao) : ExpensesIncomeTypesRepository {
    override fun getAllExpenseTypes(): Flow<List<TransactionType>> {
        return expenseIncomeTypesDao.getAllExpenseTypes()
    }

    override fun getAllIncomeTypes(): Flow<List<TransactionType>> {
        return expenseIncomeTypesDao.getAllIncomeTypes()
    }
}