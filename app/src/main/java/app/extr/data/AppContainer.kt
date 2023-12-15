package app.extr.data

import android.content.Context
import app.extr.data.repositories.CurrenciesRepository
import app.extr.data.repositories.CurrenciesRepositoryImpl
import app.extr.data.repositories.MoneyTypeRepository
import app.extr.data.repositories.MoneyTypeRepositoryImpl
import app.extr.data.types.MoneyType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface AppContainer{
    val moneyTypeRepository : MoneyTypeRepository
    val currenciesRepository : CurrenciesRepository
}

class AppContainerImpl(private val context: Context) : AppContainer {
    //Implementation for [MoneyTypeRepository]
    override val moneyTypeRepository: MoneyTypeRepository by lazy {
        MoneyTypeRepositoryImpl(ExTrDatabase.getDatabase(context).moneyTypeDao())
    }
    //Implementation for [CurrenciesRepository]
    override val currenciesRepository: CurrenciesRepository by lazy {
        CurrenciesRepositoryImpl(ExTrDatabase.getDatabase(context).currencyDao())
    }
}