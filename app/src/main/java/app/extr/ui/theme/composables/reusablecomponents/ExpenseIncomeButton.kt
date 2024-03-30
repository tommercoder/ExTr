package app.extr.ui.theme.composables.reusablecomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import app.extr.R
import app.extr.ui.theme.AppPadding
import app.extr.ui.theme.ExTrTheme
import app.extr.ui.theme.shapeScheme
import app.extr.ui.theme.viewmodels.Date
import app.extr.utils.helpers.Constants

enum class SelectedTransactionType {
    EXPENSES, INCOME
}

@Composable
fun ExpenseIncomeDateRow(
    modifier: Modifier = Modifier,
    onSelected: (SelectedTransactionType) -> Unit,
    onDateClicked: () -> Unit,
    date: Date,
    selectedType: SelectedTransactionType
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapeScheme.extraRoundedCorners)
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
                .padding(horizontal = 4.dp)
                .weight(0.6f),
            contentAlignment = Alignment.Center
        ) {
            Row {
                Button(
                    onClick = {
                        onSelected(SelectedTransactionType.EXPENSES)
                    },
                    modifier = Modifier
                        .weight(1f)
                        //.height(48.dp)
                        .zIndex(if (selectedType == SelectedTransactionType.EXPENSES) 1f else 0f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =  if (selectedType == SelectedTransactionType.EXPENSES) MaterialTheme.colorScheme.primary else Color.Transparent
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.button_expenses),
                        style = MaterialTheme.typography.bodySmall,
                        color = if (selectedType == SelectedTransactionType.EXPENSES) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                    )
                }

                Button(
                    onClick = {
                        onSelected(SelectedTransactionType.INCOME)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .zIndex(if (selectedType == SelectedTransactionType.EXPENSES) 1f else 0f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedType == SelectedTransactionType.INCOME) MaterialTheme.colorScheme.primary else Color.Transparent
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.button_income),
                        style = MaterialTheme.typography.bodySmall,
                        color = if (selectedType == SelectedTransactionType.INCOME) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(4.dp))
        Button(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .weight(0.4f),
            onClick = {
                onDateClicked()
            }
        ) {
            Text(
                text = stringResource(Constants.months[date.month]) + " " + date.year.toString()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OverlappingSegmentedControlPreview() {
    ExTrTheme {
            ExpenseIncomeDateRow(
                selectedType = SelectedTransactionType.EXPENSES,
                modifier = Modifier,
                onSelected = {},
                onDateClicked = {},
                date = Date(2, 2024)
            )
    }
}