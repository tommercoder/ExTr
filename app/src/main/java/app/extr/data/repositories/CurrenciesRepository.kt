package app.extr.data.repositories

import app.extr.data.daos.MoneyTypeDao
import app.extr.data.types.MoneyType

//interface CurrenciesRepository{
//    suspend fun getAllMoneyTypes() : List<MoneyType>
//}
//
//class CurrenciesRepositoryImpl(private val moneyTypeDao : MoneyTypeDao) : MoneyTypeRepository  {
//    override suspend fun getAllMoneyTypes(): List<MoneyType> {
//        return moneyTypeDao.getAll()
//    }
//}