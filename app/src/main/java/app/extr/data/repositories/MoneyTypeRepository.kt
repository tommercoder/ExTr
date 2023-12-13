package app.extr.data.repositories

import app.extr.data.daos.MoneyTypeDao
import app.extr.data.types.MoneyType
import kotlinx.coroutines.flow.Flow

interface MoneyTypeRepository{
    suspend fun getAllMoneyTypes() : List<MoneyType>
}

class MoneyTypeRepositoryImpl(private val moneyTypeDao : MoneyTypeDao) : MoneyTypeRepository  {
    override suspend fun getAllMoneyTypes(): List<MoneyType> {
        return moneyTypeDao.getAll()
    }
}