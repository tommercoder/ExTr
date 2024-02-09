package app.extr.data.repositories

import app.extr.data.daos.BalanceDao
import app.extr.data.daos.UsedCurrencyDao
import app.extr.data.types.Balance
import app.extr.data.types.BalanceWithDetails
import app.extr.data.types.UsedCurrency
import kotlinx.coroutines.flow.Flow

interface BalancesRepository {
    suspend fun insert(balance: Balance)
    suspend fun delete(balance: Balance)
    fun getBalancesForCurrentCurrency(): Flow<List<BalanceWithDetails>>
    suspend fun getBalancesForIds(ids: List<Int>): List<BalanceWithDetails>
}

class BalancesRepositoryImpl(
    private val balanceDao: BalanceDao,
    private val usedCurrenciesDao: UsedCurrencyDao
) : BalancesRepository {
    override suspend fun insert(balance: Balance) {
        balanceDao.insert(balance)

        //if exists already will ignore
        //todo: Think of the case when you add a balance for a currency that is not currently selected, what shall happen?
        val maxIndex = usedCurrenciesDao.getMaxSelectionIndex() ?: 0
        usedCurrenciesDao.insert(
            UsedCurrency(
                currencyId = balance.currencyId,
                selectionIndex = maxIndex + 1
            )
        )
    }

    override suspend fun delete(balance: Balance) {
        val countOfRemainingBalancesInThisCurrency =
            balanceDao.getBalanceCountForCurrentCurrency(balance.currencyId) // delete could only be called for the current currency

        if (countOfRemainingBalancesInThisCurrency == 1) { // this is the last element now -> delete it from used_currencies
            usedCurrenciesDao.delete(balance.currencyId)
        }

        balanceDao.delete(balance)
    }

    override fun getBalancesForCurrentCurrency(): Flow<List<BalanceWithDetails>> {
        return balanceDao.getBalancesForCurrentCurrency()
    }

    override suspend fun getBalancesForIds(ids: List<Int>): List<BalanceWithDetails> {
        return balanceDao.getBalancesForIds(ids)
    }
}