package app.extr.data.repositories

import app.extr.data.daos.BalanceDao
import app.extr.data.types.Balance
import kotlinx.coroutines.flow.Flow

interface BalancesRepository {
    suspend fun insert(balance: Balance)
    fun getBalancesForCurrentCurrency() : Flow<List<Balance>>
}

class BalancesRepositoryImpl(private val balanceDao: BalanceDao) : BalancesRepository {
    override suspend fun insert(balance: Balance) {
        balanceDao.insert(balance)
    }

    override fun getBalancesForCurrentCurrency(): Flow<List<Balance>> {
        return balanceDao.getBalancesForCurrentCurrency()
    }
}