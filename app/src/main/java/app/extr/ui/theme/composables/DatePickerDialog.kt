package app.extr.ui.theme.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.extr.R
import app.extr.ui.theme.ExTrTheme
import app.extr.ui.theme.shapeScheme
import app.extr.ui.theme.viewmodels.Date
import app.extr.ui.theme.viewmodels.DatePickerViewModel
import app.extr.ui.theme.viewmodels.ExpensesIncomeViewModel
import app.extr.utils.helpers.Constants
import java.util.Calendar

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DatePickerDialog(
    selectedMonth: Int,
    selectedYear: Int,
    onOkClicked: (Date) -> Unit,
    onCancelClicked: () -> Unit
) {
    val months = Constants.months
    var month by remember { mutableIntStateOf(selectedMonth) }
    var year by remember { mutableIntStateOf(selectedYear) }
    val calendar = Calendar.getInstance()
    val currentMonth = calendar.get(Calendar.MONTH)
    val currentYear = calendar.get(Calendar.YEAR)
    AlertDialog(
        onDismissRequest = { onCancelClicked() },
        text = {
            Column {
                //year picker row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .clip(MaterialTheme.shapeScheme.extraLarge)
                            .clickable(
                                onClick = {
                                    year--
                                }
                            ),
                        painter = painterResource(id = R.drawable.left_icon),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                        text = year.toString(),
                        style = MaterialTheme.typography.labelLarge
                    )
                    Icon(
                        modifier = Modifier
                            .clip(MaterialTheme.shapeScheme.extraLarge)
                            .clickable(
                                enabled = year != currentYear,
                                onClick = {
                                    year++
                                    if(year == currentYear && month > currentMonth) month = currentMonth //reset to the actual month for the current year
                                }
                            ),
                        painter = painterResource(id = R.drawable.right_icon),
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))
                //month picker row
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    maxItemsInEachRow = 3,
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.Center
                ) {
                    months.take(if (year == currentYear) currentMonth+1/*current month is zero indexed*/ else months.size)
                        .forEachIndexed { index, it ->

                            val isSelected = index == month
                            val backgroundColor by animateColorAsState(
                                targetValue = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                                animationSpec = tween(
                                    durationMillis = 500,
                                    easing = FastOutSlowInEasing
                                ), label = ""
                            )
                            Box(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .size(60.dp)
                                    .clip(MaterialTheme.shapeScheme.large)
                                    .clickable(
                                        //enabled = if (year == currentYear) index <= currentMonth else true,
                                        onClick = { month = index })
                                    .background(backgroundColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(it),
                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onOkClicked(Date(month, year))
                }
            ) {
                Text(stringResource(id = R.string.button_ok))
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onCancelClicked()
                }
            ) {
                Text(stringResource(id = R.string.button_cancel))
            }
        }
    )

}

@Preview
@Composable
fun preview() {
    ExTrTheme {
        DatePickerDialog(
            selectedMonth = 2,
            selectedYear = 2024,
            onCancelClicked = {},
            onOkClicked = {}
        )
    }
}

@Composable
fun DatePickerDialogCaller(
    datePickerViewModel: DatePickerViewModel,
    expensesIncomeViewModel: ExpensesIncomeViewModel
) {
    val state by datePickerViewModel.dateState.collectAsStateWithLifecycle()

    if (state.datePickerVisible) {
        DatePickerDialog(
            selectedMonth = state.date.month,
            selectedYear = state.date.year,
            onOkClicked = {
                if(state.date != it) { // only update when something changed
                    datePickerViewModel.setDate(it)
                    datePickerViewModel.toggleDatePicker(false)
                    expensesIncomeViewModel.loadTransactions(it)
                }
            },
            onCancelClicked = { datePickerViewModel.toggleDatePicker(false) }
        )
    }
}