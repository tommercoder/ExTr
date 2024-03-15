package app.extr.data.repositories

import app.extr.data.daos.UsedCurrencyDao
import app.extr.data.types.UsedCurrency
import app.extr.data.types.UsedCurrencyDetails
import kotlinx.coroutines.flow.Flow

interface UsedCurrenciesRepository {
    suspend fun insertIfNotExists(usedCurrency: UsedCurrency) //OnConflictStrategy.REPLACE
    suspend fun delete(usedCurrencyId: Int)
    suspend fun selectCurrency(currencyId: Int)
    fun getUsedCurrencies(): Flow<List<UsedCurrencyDetails>>
    fun getCurrentlySelectedUsedCurrency(): Flow<UsedCurrencyDetails>
}

class UsedCurrenciesRepositoryImpl(private val usedCurrencyDao: UsedCurrencyDao) :
    UsedCurrenciesRepository {
    override suspend fun insertIfNotExists(usedCurrency: UsedCurrency) {
        usedCurrencyDao.insert(usedCurrency)
    }

    override suspend fun delete(usedCurrencyId: Int) {
        usedCurrencyDao.delete(usedCurrencyId)
    }

    override fun getUsedCurrencies(): Flow<List<UsedCurrencyDetails>> {
        return usedCurrencyDao.getUsedCurrencies()
    }

    override suspend fun selectCurrency(currencyId: Int) {
        usedCurrencyDao.updateIndices(currencyId)
    }

    override fun getCurrentlySelectedUsedCurrency(): Flow<UsedCurrencyDetails> {
        return usedCurrencyDao.getCurrentlySelectedUsedCurrency()
    }
}