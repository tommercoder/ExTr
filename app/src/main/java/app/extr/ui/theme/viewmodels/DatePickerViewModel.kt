package app.extr.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.extr.data.types.BalanceWithDetails
import app.extr.data.types.TransactionType
import app.extr.utils.helpers.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar

data class DateState(
    val date: Date,
    val datePickerVisible: Boolean
)

data class Date(
    val month: Int,
    val year: Int
)

class DatePickerViewModel : ViewModel() {

    private val calendar = Calendar.getInstance()
    private val _selectedDate = MutableStateFlow<Date>(
        Date(
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.YEAR)
        )
    )

    private val _datePickerVisible = MutableStateFlow(false)

    val dateState: StateFlow<DateState> = combine(
        _selectedDate,
        _datePickerVisible
    ) { selectedDate, datePickerVisible ->
        DateState(
            date = selectedDate,
            datePickerVisible = datePickerVisible
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        DateState(
            Date(
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR)
            ),
            false
        )
    )

    fun toggleDatePicker(show: Boolean) {
        _datePickerVisible.value = show
    }

    fun setDate(date: Date) {
        _selectedDate.value = date
    }
}