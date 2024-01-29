package app.extr.ui.theme.composables.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.extr.ui.theme.AppPadding
import app.extr.ui.theme.composables.reusablecomponents.ExpenseIncomeButton

@Composable
fun RoundChartScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = AppPadding.ExtraSmall)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ExpenseIncomeButton(
                modifier = Modifier.weight(0.6f),
                onSelected = {}
            )
            Spacer(modifier = Modifier.width(4.dp))
            Button(
                modifier = Modifier.weight(0.35f),
                onClick = {}
            ){
                Text("Temp")
            }

        }


    }
}
