package app.extr.ui.theme.composables.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.extr.R
import app.extr.ui.theme.AppPadding
import app.extr.ui.theme.ExTrTheme
import app.extr.ui.theme.composables.reusablecomponents.CategoryCard
import app.extr.ui.theme.composables.reusablecomponents.SelectedTransactionType
import app.extr.ui.theme.viewmodels.TransactionByType
import app.extr.utils.helpers.resproviders.ResProvider

@Composable
fun ChartScreen(
    selectedType: SelectedTransactionType,
    transactionsByType: List<TransactionByType>,
    resProvider: ResProvider
) {
    var itemCount by remember(selectedType) { mutableIntStateOf(4) }
    val showMore = itemCount < transactionsByType.size
    val showLess = itemCount > 4

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = AppPadding.Small, end = AppPadding.Small)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(bottom = AppPadding.ExtraSmall)
        ) {
            items(
                items = transactionsByType.take(itemCount),
                key = { it -> it.transactionType.id }
            ) {
                CategoryCard(
                    modifier = Modifier.fillMaxWidth(),
                    transactionByType = it,
                    resProvider = resProvider
                )
            }
            item {
                // Show more or less buttons as needed
                when {
                    showMore -> MoreButton(modifier = Modifier.fillMaxWidth()) { itemCount += 4 }
                    showLess -> LessButton(modifier = Modifier.fillMaxWidth()) { itemCount = 4 }
                }
            }
        }

    }
}

@Composable
fun MoreButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier.clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row {
            Icon(painter = painterResource(id = R.drawable.expand_more_icon), contentDescription = null)
            Text(text = stringResource(id = R.string.btn_show_more), fontWeight = FontWeight.Bold)

        }
    }
}

@Preview
@Composable
fun ButtonPreview() {
    ExTrTheme {
        MoreButton(modifier = Modifier.fillMaxWidth(), onClick = {})
    }
}

@Composable
fun LessButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier.clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row {
            Icon(painter = painterResource(id = R.drawable.expand_less_icon), contentDescription = null)
            Text(text = stringResource(id = R.string.btn_show_less), fontWeight = FontWeight.Bold)

        }
    }
}