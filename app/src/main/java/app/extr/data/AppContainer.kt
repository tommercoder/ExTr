package app.extr.data

import android.content.Context
import app.extr.data.repositories.CurrenciesRepository
import app.extr.data.repositories.CurrenciesRepositoryImpl
import app.extr.data.repositories.MoneyTypeRepository
import app.extr.data.repositories.MoneyTypeRepositoryImpl
import app.extr.data.repositories.UserCurrenciesRepository
import app.extr.data.repositories.UserCurrenciesRepositoryImpl
import app.extr.data.repositories.UserRepository
import app.extr.data.repositories.UserRepositoryImpl
import app.extr.data.types.MoneyType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface AppContainer {
    val moneyTypeRepository: MoneyTypeRepository
    val currenciesRepository: CurrenciesRepository
    val UserRepository: UserRepository
    val userCurrenciesRepository: UserCurrenciesRepository
}

class AppContainerImpl(private val context: Context) : AppContainer {

    override val moneyTypeRepository: MoneyTypeRepository by lazy {
        MoneyTypeRepositoryImpl(ExTrDatabase.getDatabase(context).moneyTypeDao())
    }

    override val currenciesRepository: CurrenciesRepository by lazy {
        CurrenciesRepositoryImpl(ExTrDatabase.getDatabase(context).currencyDao())
    }

    override val UserRepository: UserRepository by lazy {
        UserRepositoryImpl(ExTrDatabase.getDatabase(context).userDao())
    }

    override val userCurrenciesRepository: UserCurrenciesRepository by lazy {
        UserCurrenciesRepositoryImpl(ExTrDatabase.getDatabase(context).userCurrencyDao())
    }
}