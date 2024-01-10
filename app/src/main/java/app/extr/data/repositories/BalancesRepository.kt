package app.extr.data.repositories

import app.extr.data.daos.BalanceDao
import app.extr.data.types.Balance
import app.extr.data.types.BalanceWithDetails
import kotlinx.coroutines.flow.Flow

interface BalancesRepository {
    suspend fun insert(balance: Balance)
    suspend fun delete(balanceId: Int)
    fun getBalancesForCurrentCurrency() : Flow<List<BalanceWithDetails>>
}

class BalancesRepositoryImpl(private val balanceDao: BalanceDao) : BalancesRepository {
    override suspend fun insert(balance: Balance) {
        balanceDao.insert(balance)
    }

    override suspend fun delete(balanceId: Int) {
        balanceDao.delete(balanceId)
    }

    override fun getBalancesForCurrentCurrency(): Flow<List<BalanceWithDetails>> {
        return balanceDao.getBalancesForCurrentCurrency()
    }
}