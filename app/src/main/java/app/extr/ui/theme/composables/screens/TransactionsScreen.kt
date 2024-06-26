package app.extr.ui.theme.composables.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.extr.R
import app.extr.ui.theme.AppPadding
import app.extr.ui.theme.animations.CustomCircularProgressIndicator
import app.extr.ui.theme.composables.reusablecomponents.ErrorScreen
import app.extr.ui.theme.composables.reusablecomponents.NoDataYet
import app.extr.ui.theme.composables.reusablecomponents.SelectedTransactionType
import app.extr.ui.theme.composables.reusablecomponents.TransactionCard
import app.extr.ui.theme.viewmodels.TransactionUiEvent
import app.extr.utils.helpers.TransactionWithDetailsState
import app.extr.utils.helpers.UiState
import app.extr.utils.helpers.resproviders.ResProvider

@Composable
fun TransactionsScreen(
    uiState: TransactionWithDetailsState,
    selectedType: SelectedTransactionType,
    resProvider: ResProvider,
    modifier: Modifier = Modifier,
    onEvent: (TransactionUiEvent) -> Unit,
) {
    when (uiState) {
        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CustomCircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        is UiState.Success -> {
            val transactions = remember(uiState.data) { uiState.data.reversed() }
            if (transactions.isEmpty()) {
                NoDataYet(
                    modifier = Modifier.fillMaxSize(),
                    headerId = if (selectedType == SelectedTransactionType.EXPENSES) R.string.label_no_expenses else R.string.label_no_income,
                    labelId = if (selectedType == SelectedTransactionType.EXPENSES) R.string.label_create_expense_hint else R.string.label_create_income_hint
                )
            } else {

                LazyColumn(
                    modifier = modifier,
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(
                        items = transactions,
                        key = {
                            it.transaction.id
                        }
                    ) { transaction ->
                        TransactionCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = AppPadding.ExtraSmall),
                            transaction = transaction,
                            resProvider = resProvider,
                            onDelete = { onEvent(TransactionUiEvent.Delete(it)) }
                        )
                    }
                }
            }
        }

        is UiState.Error -> {
            ErrorScreen(
                onRefresh = { onEvent(TransactionUiEvent.Refresh) },
                resourceId = uiState.resourceId
            )
        }
    }
}