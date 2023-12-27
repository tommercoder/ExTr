package app.extr.data.repositories

import app.extr.data.daos.MoneyTypeDao
import app.extr.data.types.MoneyType

interface MoneyTypesRepository{
    suspend fun getAllMoneyTypes() : List<MoneyType>
}

class MoneyTypesRepositoryImpl(private val moneyTypeDao : MoneyTypeDao) : MoneyTypesRepository  {
    override suspend fun getAllMoneyTypes(): List<MoneyType> {
        return moneyTypeDao.getAll()
    }
}