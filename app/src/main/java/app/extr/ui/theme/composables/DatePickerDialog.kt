package app.extr.ui.theme.composables

import android.widget.Space
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.lifecycle.viewmodel.compose.viewModel
import app.extr.R
import app.extr.ui.theme.ExTrTheme
import app.extr.ui.theme.shapeScheme
import app.extr.ui.theme.viewmodels.Date
import app.extr.ui.theme.viewmodels.DatePickerViewModel
import app.extr.ui.theme.viewmodels.ExpensesIncomeViewModel
import app.extr.ui.theme.viewmodels.ViewModelsProvider
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
    var month by remember { mutableStateOf(selectedMonth) }
    var year by remember { mutableStateOf(selectedYear) }
    val interactionSource = remember { MutableInteractionSource() }
    val calendar = Calendar.getInstance()
    val currentMonth = calendar.get(Calendar.MONTH)
    AlertDialog(
        onDismissRequest = { },
        //title = { Text(stringResource(id = R.string.label_delete_balance)) },
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
                            .clickable(
                                indication = null,
                                interactionSource = interactionSource,
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
                            .clickable(
                                indication = null,
                                interactionSource = interactionSource,
                                onClick = {
                                    year++
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
                    months.forEachIndexed { index, it ->
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
                                .clickable { month = index }
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
                datePickerViewModel.setDate(it)
                datePickerViewModel.toggleDatePicker(false)
                expensesIncomeViewModel.loadTransactions(it) // .copy(month = it.month + 1)
            },
            onCancelClicked = { datePickerViewModel.toggleDatePicker(false) }
        )
    }
}