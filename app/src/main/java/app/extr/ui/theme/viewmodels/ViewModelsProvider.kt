package app.extr.ui.theme.viewmodels

import android.text.Editable.Factory
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import app.extr.ExTrApp
import app.extr.ExTrApplication

object ViewModelsProvider {
    val Factory = viewModelFactory {
        initializer {
            MoneyTypeViewModel(ExTrApplication.container.moneyTypeRepository)
        }
        initializer {
            CurrenciesViewModel(ExTrApplication.container.currenciesRepository)
        }
    }
}