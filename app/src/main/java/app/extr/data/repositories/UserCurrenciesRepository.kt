package app.extr.data.repositories

import app.extr.data.daos.UserCurrencyDao

interface UserCurrenciesRepository {

}

class UserCurrenciesRepositoryImpl(
    private val userCurrencyDao: UserCurrencyDao
) : UserCurrenciesRepository {


}