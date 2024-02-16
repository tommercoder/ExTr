package app.extr.ui.theme.viewmodels

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
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
            ExpensesIncomeTypesViewModel(ExTrApplication.container.ExpensesIncomeTypesRepository) //todo: probably not needed, remove if so
        }
        initializer {
            ExpensesIncomeViewModel(
                ExTrApplication.container.ExpensesIncomeRepository,
                ExTrApplication.container.balancesRepository)
        }
        initializer {
            ExpensesIncomeBottomSheetViewModel(
                ExTrApplication.container.balancesRepository,
                ExTrApplication.container.ExpensesIncomeTypesRepository
            )
        }
        initializer {
            DatePickerViewModel()
        }
    }
}