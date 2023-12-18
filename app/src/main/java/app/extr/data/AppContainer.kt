package app.extr.data

import android.content.Context
import app.extr.data.repositories.CurrenciesRepository
import app.extr.data.repositories.CurrenciesRepositoryImpl
import app.extr.data.repositories.MoneyTypeRepository
import app.extr.data.repositories.MoneyTypeRepositoryImpl
import app.extr.data.repositories.UsersRepository
import app.extr.data.repositories.UsersRepositoryImpl
import app.extr.data.types.MoneyType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface AppContainer {
    val moneyTypeRepository: MoneyTypeRepository
    val currenciesRepository: CurrenciesRepository
    val usersRepository: UsersRepository
}

class AppContainerImpl(private val context: Context) : AppContainer {

    override val moneyTypeRepository: MoneyTypeRepository by lazy {
        MoneyTypeRepositoryImpl(ExTrDatabase.getDatabase(context).moneyTypeDao())
    }

    override val currenciesRepository: CurrenciesRepository by lazy {
        CurrenciesRepositoryImpl(ExTrDatabase.getDatabase(context).currencyDao())
    }

    override val usersRepository: UsersRepository by lazy {
        UsersRepositoryImpl(ExTrDatabase.getDatabase(context).userDao())
    }
}