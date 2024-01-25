package app.extr.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import app.extr.data.types.Currency
import app.extr.data.types.CurrencyLastSelected
import app.extr.data.types.MoneyType
import app.extr.data.types.UsedCurrency
import app.extr.data.types.UsedCurrencyDetails
import app.extr.ui.theme.animations.LoadingAnimation
import app.extr.ui.theme.composables.reusablecomponents.CurrenciesDropDownMenu
import app.extr.utils.helpers.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    uiState: UiState<List<UsedCurrencyDetails>>,
    onItemSelected: (Currency) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    isAddButtonVisible: Boolean,
    selectedCurrency: Currency? = null
) {
    when (uiState) {
        is UiState.Loading -> {

        }

        is UiState.Success -> {
           // LargeTopAppBar(
            TopAppBar (
                title = {},
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    if (isAddButtonVisible) {
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Navigation icon"
                            )
                        }
                    }
                },
                actions = {
                    if (uiState.data.isEmpty()) {
                        return@TopAppBar
                    }
                    val data by rememberUpdatedState(uiState.data)

                    Row(
                        //modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        CurrenciesDropDownMenu(
                            modifier = Modifier.fillMaxWidth(0.5f),
                            items = data.map { it.currency }, // extract a list of currencies only // todo: move to viewmodel
                            onItemSelected = { currency ->
                                onItemSelected(currency)
                            },
                            borderShown = false,
                            selectedPassed = selectedCurrency
                        )
                    }
                }
            )
        }

        is UiState.Error -> {}
    }

}