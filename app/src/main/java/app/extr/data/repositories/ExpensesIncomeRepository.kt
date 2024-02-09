package app.extr.data.repositories

import app.extr.data.daos.BalanceDao
import app.extr.data.daos.ExpenseIncomeDao
import app.extr.data.types.Balance
import app.extr.data.types.Expense
import app.extr.data.types.ExpenseWithDetails
import app.extr.data.types.Income
import app.extr.data.types.IncomeWithDetails
import kotlinx.coroutines.flow.Flow

interface ExpensesIncomeRepository {
    suspend fun insertExpense(expense: Expense)
    suspend fun insertIncome(income: Income)
    fun getExpensesForCurrentCurrency(month: Int, year: Int): Flow<List<ExpenseWithDetails>>
    fun getIncomesForCurrentCurrency(month: Int, year: Int): Flow<List<IncomeWithDetails>>
}

class ExpensesIncomeRepositoryImpl(
    private val balanceDao: BalanceDao,
    private val expenseIncomeDao: ExpenseIncomeDao
) : ExpensesIncomeRepository {

    private suspend fun getBalanceById(balanceId: Int): Balance {
        return balanceDao.getBalanceById(balanceId)
    }

    override suspend fun insertExpense(expense: Expense) {
        expenseIncomeDao.insertExpense(expense)

        var balance = getBalanceById(expense.balanceId)
        if(balance != null) {
            val newAmount = balance.amount - expense.transactionAmount
            balance = balance.copy(
                amount = newAmount
            )
            balanceDao.update(balance)
        }
    }

    override suspend fun insertIncome(income: Income) {
        expenseIncomeDao.insertIncome(income)

        var balance = getBalanceById(income.balanceId)
        if(balance != null) {
            val newAmount = balance.amount + income.transactionAmount
            balance = balance.copy(
                amount = newAmount
            )
            balanceDao.update(balance)
        }
    }

    override fun getExpensesForCurrentCurrency(month: Int, year: Int): Flow<List<ExpenseWithDetails>> {
        return expenseIncomeDao.getExpensesForCurrentCurrency(month, year)
    }

    override fun getIncomesForCurrentCurrency(month: Int, year: Int): Flow<List<IncomeWithDetails>> {
        return expenseIncomeDao.getIncomesForCurrentCurrency(month, year)
    }

}