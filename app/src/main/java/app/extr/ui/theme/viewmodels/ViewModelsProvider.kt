package app.extr.ui.theme.viewmodels

import android.text.Editable.Factory
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import app.extr.ExTrApp
import app.extr.ExTrApplication

object ViewModelsProvider {
    val Factory = viewModelFactory {
        initializer {
            MoneyTypesViewModel(ExTrApplication.container.moneyTypesRepository)
        }
        initializer {
            CurrenciesViewModel(ExTrApplication.container.currenciesRepository)
        }
        initializer {
            UserViewModel(ExTrApplication.container.userRepository)
        }
        initializer {
            BalancesViewModel(ExTrApplication.container.balancesRepository)
        }
        initializer {
            UsedCurrenciesViewModel(ExTrApplication.container.usedCurrenciesRepository)
        }
        initializer {
            ExpenseIncomeTypesViewModel(ExTrApplication.container.expenseIncomeTypesRepository)
        }
    }
}