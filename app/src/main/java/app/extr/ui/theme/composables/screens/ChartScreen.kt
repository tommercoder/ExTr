package app.extr.ui.theme.composables.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.extr.ui.theme.AppPadding
import app.extr.ui.theme.composables.reusablecomponents.CategoryCard
import app.extr.ui.theme.viewmodels.TransactionByType
import app.extr.utils.helpers.resproviders.ResProvider

@Composable
fun ChartScreen(
    transactionsByType: List<TransactionByType>,
    resProvider: ResProvider
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(start = AppPadding.Small, end = AppPadding.Small)
    ){
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(bottom = AppPadding.ExtraSmall)
        ){
            items(
                items = transactionsByType,
                key = { it-> it.totalAmount }//todo: add a key
            ){
                CategoryCard(
                    modifier = Modifier.fillMaxWidth(),
                    transactionByType = it,
                    resProvider = resProvider)
            }
        }

    }
}