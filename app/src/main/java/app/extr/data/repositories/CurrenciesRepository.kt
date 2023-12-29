package app.extr.data.repositories

import app.extr.data.daos.CurrencyDao
import app.extr.data.daos.MoneyTypeDao
import app.extr.data.types.Currency
import app.extr.data.types.MoneyType
import kotlinx.coroutines.flow.Flow

interface CurrenciesRepository {
    fun getAllCurrencies(): Flow<List<Currency>>
}

class CurrenciesRepositoryImpl(private val currencyDao: CurrencyDao) : CurrenciesRepository {
    override fun getAllCurrencies(): Flow<List<Currency>> {
        return currencyDao.getAll()
    }
}