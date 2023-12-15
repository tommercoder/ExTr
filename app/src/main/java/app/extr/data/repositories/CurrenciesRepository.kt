package app.extr.data.repositories

import app.extr.data.daos.CurrencyDao
import app.extr.data.daos.MoneyTypeDao
import app.extr.data.types.Currency
import app.extr.data.types.MoneyType

interface CurrenciesRepository{
    suspend fun getAllCurrencies() : List<Currency>
}

class CurrenciesRepositoryImpl(private val currencyDao : CurrencyDao) : CurrenciesRepository  {
    override suspend fun getAllCurrencies(): List<Currency> {
        return currencyDao.getAll()
    }
}