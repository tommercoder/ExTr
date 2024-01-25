package app.extr.ui.theme.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class RefreshBaseViewModel : ViewModel() {
    var isRefreshing by mutableStateOf(false)
        private set

    fun refreshData(refreshLogic: suspend () -> Unit) {
        viewModelScope.launch {
            isRefreshing = true
            try {
                refreshLogic()
            } finally {
                isRefreshing = false
            }
        }
    }

}