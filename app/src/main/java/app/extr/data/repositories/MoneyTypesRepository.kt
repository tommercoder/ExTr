package app.extr.data.repositories

import app.extr.data.daos.MoneyTypeDao
import app.extr.data.types.MoneyType
import kotlinx.coroutines.flow.Flow

interface MoneyTypesRepository{
    fun getAllMoneyTypes() : Flow<List<MoneyType>>
}

class MoneyTypesRepositoryImpl(private val moneyTypeDao : MoneyTypeDao) : MoneyTypesRepository  {
    override fun getAllMoneyTypes(): Flow<List<MoneyType>> {
        return moneyTypeDao.getAll()
    }
}