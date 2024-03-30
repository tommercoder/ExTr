package app.extr.data

import android.content.Context
import app.extr.data.repositories.BalancesRepository
import app.extr.data.repositories.BalancesRepositoryImpl
import app.extr.data.repositories.CurrenciesRepository
import app.extr.data.repositories.CurrenciesRepositoryImpl
import app.extr.data.repositories.ExpensesIncomeRepository
import app.extr.data.repositories.ExpensesIncomeRepositoryImpl
import app.extr.data.repositories.ExpensesIncomeTypesRepository
import app.extr.data.repositories.ExpensesIncomeTypesRepositoryImpl
import app.extr.data.repositories.MoneyTypesRepository
import app.extr.data.repositories.MoneyTypesRepositoryImpl
import app.extr.data.repositories.UsedCurrenciesRepository
import app.extr.data.repositories.UsedCurrenciesRepositoryImpl
import app.extr.data.repositories.UserRepository
import app.extr.data.repositories.UserRepositoryImpl
import app.extr.data.types.MoneyType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface AppContainer {
    val moneyTypesRepository: MoneyTypesRepository
    val currenciesRepository: CurrenciesRepository
    val userRepository: UserRepository
    val balancesRepository: BalancesRepository
    val usedCurrenciesRepository: UsedCurrenciesRepository
    val expensesIncomeTypesRepository: ExpensesIncomeTypesRepository
    val expensesIncomeRepository: ExpensesIncomeRepository
}

class AppContainerImpl(private val context: Context) : AppContainer {

    override val moneyTypesRepository: MoneyTypesRepository by lazy {
        MoneyTypesRepositoryImpl(ExTrDatabase.getDatabase(context).moneyTypeDao())
    }

    override val currenciesRepository: CurrenciesRepository by lazy {
        CurrenciesRepositoryImpl(ExTrDatabase.getDatabase(context).currencyDao())
    }

    override val userRepository: UserRepository by lazy {
        UserRepositoryImpl(ExTrDatabase.getDatabase(context).userDao())
    }

    override val balancesRepository: BalancesRepository by lazy {
        val database = ExTrDatabase.getDatabase(context)
        BalancesRepositoryImpl(
            database.balanceDao(),
            database.usedCurrencyDao()
        )
    }

    override val usedCurrenciesRepository: UsedCurrenciesRepository by lazy {
        UsedCurrenciesRepositoryImpl(ExTrDatabase.getDatabase(context).usedCurrencyDao())
    }

    override val expensesIncomeTypesRepository: ExpensesIncomeTypesRepository by lazy {
        ExpensesIncomeTypesRepositoryImpl(ExTrDatabase.getDatabase(context).expenseIncomeTypesDao())
    }

    override val expensesIncomeRepository: ExpensesIncomeRepository by lazy {
        val database = ExTrDatabase.getDatabase(context)
        ExpensesIncomeRepositoryImpl(
            database.balanceDao(),
            database.expenseIncomeDao()
        )
    }
}