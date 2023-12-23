package app.extr.data.repositories

import app.extr.data.daos.UserCurrencyDao
import app.extr.data.types.CurrencyLastSelected
import app.extr.data.types.UserCurrencyCrossRef
import app.extr.data.types.UserWithCurrencies
import kotlinx.coroutines.flow.Flow

interface UserCurrenciesRepository {
    suspend fun insert(userCurrency: UserCurrencyCrossRef)
    suspend fun delete(userCurrency: UserCurrencyCrossRef)

    fun getCurrenciesForUser(userId: Int): Flow<List<CurrencyLastSelected>>
    suspend fun selectCurrency(userId: Int, currencyId: Int)

}

class UserCurrenciesRepositoryImpl(
    private val userCurrencyDao: UserCurrencyDao
) : UserCurrenciesRepository {
    override suspend fun insert(userCurrency: UserCurrencyCrossRef) {
        userCurrencyDao.insert(userCurrency)
    }

    override suspend fun delete(userCurrency: UserCurrencyCrossRef) {
        userCurrencyDao.delete(userCurrency)
    }

    override fun getCurrenciesForUser(userId: Int): Flow<List<CurrencyLastSelected>> {
        return userCurrencyDao.getCurrenciesForUser(userId)
    }

    override suspend fun selectCurrency(userId: Int, currencyId: Int) {
        userCurrencyDao.selectCurrency(userId, currencyId)
    }


}