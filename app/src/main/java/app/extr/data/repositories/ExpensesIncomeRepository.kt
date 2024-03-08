package app.extr.data.repositories

import app.extr.data.daos.BalanceDao
import app.extr.data.daos.ExpenseIncomeDao
import app.extr.data.types.Balance
import app.extr.data.types.Expense
import app.extr.data.types.ExpenseWithDetails
import app.extr.data.types.Income
import app.extr.data.types.IncomeWithDetails
import app.extr.ui.theme.viewmodels.Date
import kotlinx.coroutines.flow.Flow

interface ExpensesIncomeRepository {
    suspend fun insertExpense(expense: Expense)
    suspend fun insertIncome(income: Income)
    suspend fun deleteExpense(expense: Expense)
    suspend fun deleteIncome(income: Income)
    fun getExpensesForCurrentCurrency(date: Date): Flow<List<ExpenseWithDetails>>
    fun getIncomesForCurrentCurrency(date: Date): Flow<List<IncomeWithDetails>>
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
        if (balance != null) {
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
        if (balance != null) {
            val newAmount = balance.amount + income.transactionAmount
            balance = balance.copy(
                amount = newAmount
            )
            balanceDao.update(balance)
        }
    }

    override suspend fun deleteExpense(expense: Expense) {
        expenseIncomeDao.deleteExpense(expense)
    }

    override suspend fun deleteIncome(income: Income) {
        expenseIncomeDao.deleteIncome(income)
    }

    override fun getExpensesForCurrentCurrency(date: Date): Flow<List<ExpenseWithDetails>> {
        return expenseIncomeDao.getExpensesForCurrentCurrency(date.month, date.year)
    }

    override fun getIncomesForCurrentCurrency(date: Date): Flow<List<IncomeWithDetails>> {
        return expenseIncomeDao.getIncomesForCurrentCurrency(date.month, date.year)
    }

}