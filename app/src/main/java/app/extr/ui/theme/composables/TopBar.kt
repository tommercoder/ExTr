package app.extr.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.extr.R
import app.extr.data.types.Currency
import app.extr.data.types.CurrencyLastSelected
import app.extr.data.types.MoneyType
import app.extr.data.types.UsedCurrency
import app.extr.data.types.UsedCurrencyDetails
import app.extr.ui.theme.animations.CustomCircularProgressIndicator
import app.extr.ui.theme.animations.LoadingAnimation
import app.extr.ui.theme.composables.reusablecomponents.CurrenciesDropDownMenu
import app.extr.utils.helpers.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    uiState: UiState<List<UsedCurrencyDetails>>,
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
                                items = data.map { it.currency }, // extract a list of currencies only // todo: move to viewmodel
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