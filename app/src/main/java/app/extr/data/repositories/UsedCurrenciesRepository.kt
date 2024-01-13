package app.extr.data.repositories

import app.extr.data.daos.UsedCurrencyDao
import app.extr.data.types.UsedCurrency
import app.extr.data.types.UsedCurrencyDetails
import kotlinx.coroutines.flow.Flow

interface UsedCurrenciesRepository{
    suspend fun insertIfNotExists(usedCurrency: UsedCurrency) //OnConflictStrategy.IGNORE
    suspend fun delete(usedCurrencyId: Int)
    fun getUsedCurrencies() : Flow<List<UsedCurrencyDetails>>
}

class UsedCurrenciesRepositoryImpl(private val usedCurrencyDao: UsedCurrencyDao) : UsedCurrenciesRepository {
    override suspend fun insertIfNotExists(usedCurrency: UsedCurrency) {
        usedCurrencyDao.insert(usedCurrency)
    }

    override suspend fun delete(usedCurrencyId: Int) {
        usedCurrencyDao.delete(usedCurrencyId)
    }

    override fun getUsedCurrencies(): Flow<List<UsedCurrencyDetails>> {
         return usedCurrencyDao.getUsedCurrencies()
    }
}