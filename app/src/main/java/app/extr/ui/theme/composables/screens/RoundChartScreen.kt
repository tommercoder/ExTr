package app.extr.ui.theme.composables.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.extr.data.types.TransactionWithDetails
import app.extr.ui.theme.AppPadding
import app.extr.ui.theme.composables.reusablecomponents.ExpenseIncomeDateRow
import app.extr.utils.helpers.UiState

@Composable
fun RoundChartScreen(
    modifier: Modifier = Modifier,
    uiState: UiState<List<TransactionWithDetails>>
) {
    Column(
        modifier = modifier
    ) {
        if(uiState is UiState.Success && uiState.data.isNotEmpty()){
            val data = uiState.data
            LazyColumn(content = {
                items(count = data.size){
                    Text(data[it].transaction.description)
                }
            })
        }
    }
}
