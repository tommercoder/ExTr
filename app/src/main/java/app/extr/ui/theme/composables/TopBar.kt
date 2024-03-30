package app.extr.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.extr.data.types.Currency
import app.extr.data.types.UsedCurrencyDetails
import app.extr.ui.theme.animations.CustomCircularProgressIndicator
import app.extr.ui.theme.composables.reusablecomponents.CurrenciesDropDownMenu
import app.extr.utils.helpers.UiState
import app.extr.utils.helpers.UsedCurrencyDetailsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    uiState: UsedCurrencyDetailsState,
    onAddClicked: () -> Unit,
    onItemSelected: (Currency) -> Unit,
    isAddButtonVisible: Boolean,
    isDropdownVisible: Boolean,
    titleText: String,
    selectedCurrency: Currency? = null
) {
    TopAppBar(
        title = {
            Text(text = titleText, textAlign = TextAlign.Start)
        },
        navigationIcon = {
            if (isAddButtonVisible) {
                IconButton(onClick = { onAddClicked() }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null
                    )
                }
            }
        },
        actions = {
            when (uiState) {
                is UiState.Loading -> {
                    Box(modifier = Modifier) {
                        CustomCircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            iconSize = 20.dp
                        )
                    }
                }

                is UiState.Success -> {
                    if (isDropdownVisible && uiState.data.isNotEmpty()) {
                        val data by rememberUpdatedState(uiState.data)

                        Row(
                            horizontalArrangement = Arrangement.End
                        ) {
                            CurrenciesDropDownMenu(
                                modifier = Modifier.fillMaxWidth(0.5f),
                                items = data.map { it.currency },
                                onItemSelected = { currency ->
                                    onItemSelected(currency)
                                },
                                borderShown = false,
                                selectedPassed = selectedCurrency
                            )
                        }
                    }
                }

                is UiState.Error -> {}
            }
        }
    )
}