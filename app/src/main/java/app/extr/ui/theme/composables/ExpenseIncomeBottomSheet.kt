//package app.extr.ui.theme.composables
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.ExperimentalLayoutApi
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.ModalBottomSheet
//import androidx.compose.material3.Text
//import androidx.compose.material3.rememberModalBottomSheetState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import app.extr.data.types.Currency
//import app.extr.data.types.MoneyType
//import app.extr.ui.theme.AppPadding
//import app.extr.ui.theme.composables.reusablecomponents.CurrenciesDropDownMenu
//import app.extr.ui.theme.composables.reusablecomponents.CustomKeyboard
//import app.extr.ui.theme.composables.reusablecomponents.ReusableDropdownMenu
//import app.extr.ui.theme.mappers.toDropdownItem
//import app.extr.utils.helpers.UiState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.res.stringResource
//import app.extr.R
//import app.extr.data.types.Balance
//import app.extr.ui.theme.LocalCustomColorsPalette
//import app.extr.ui.theme.animations.CustomCircularProgressIndicator
//import app.extr.ui.theme.mappers.toMoneyType
//import app.extr.utils.helpers.Constants
//
//@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
//@Composable
//fun BalanceBottomSheet(
//    modifier: Modifier = Modifier,
//    currenciesUiState: UiState<List<Currency>>,
//    moneyTypeUiState: UiState<List<MoneyType>>,
//    onSaveClicked: (Balance) -> Unit,
//    onDismissed: () -> Unit
//    //todo: add notification on + press if loading was not complete?
//) {
//    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
//
//    val allDataLoaded = remember(currenciesUiState, moneyTypeUiState) {
//        currenciesUiState is UiState.Success && currenciesUiState.data.isNotEmpty()
//                && moneyTypeUiState is UiState.Success && moneyTypeUiState.data.isNotEmpty()
//    }
//    var inputValue by remember { mutableStateOf("") }
//    var nameValue by remember { mutableStateOf("") }
//
//    ModalBottomSheet(
//        onDismissRequest = { onDismissed() },
//        sheetState = sheetState
//    ) {
//
//        if (allDataLoaded) {
//            val palette = LocalCustomColorsPalette.current
//            val currencies = remember(currenciesUiState) {
//                (currenciesUiState as UiState.Success<List<Currency>>).data
//            }
//
//            val dropdownItems = remember(moneyTypeUiState) {
//                (moneyTypeUiState as UiState.Success<List<MoneyType>>).data.map {
//                    it.toDropdownItem(
//                        palette
//                    )
//                }
//            }
//            var selectedCurrency by remember { mutableStateOf(currencies.first()) }
//            var selectedMoneyType by remember { mutableStateOf(dropdownItems.first()) }
//
//            Column {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(AppPadding.Medium),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//
//                    CurrenciesDropDownMenu(
//                        modifier = Modifier
//                            .weight(1f)
//                            .padding(end = AppPadding.Small),
//                        items = currencies,
//                        onItemSelected = {
//                            selectedCurrency = it
//                        },
//                        borderShown = true
//                    )
//
//                    ReusableDropdownMenu(
//                        modifier = Modifier.weight(1f),
//                        items = dropdownItems,
//                        onItemSelected = {
//                            selectedMoneyType = it
//                        },
//                    )
//                }
//
//                Text(
//                    modifier = Modifier
//                        .align(Alignment.CenterHorizontally)
//                        .padding(top = AppPadding.Small, bottom = AppPadding.Small),
//                    text = stringResource(id = R.string.label_balance),
//                    style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.secondary)
//                )
//
//                CustomKeyboard(
//                    currencySymbol = selectedCurrency.symbol,
//                    onValueChange = { inputValue += it },
//                    inputValue = inputValue,
//                    onNameChange = { nameValue = it },
//                    nameValue = nameValue,
//                    showCalendar = false,
//                    onEraseClick = { inputValue = inputValue.dropLast(1) },
//                    onCalendarClick = { },
//                    onAcceptClick = {
//                        val balance = Balance(
//                            currencyId = selectedCurrency.currencyId,
//                            moneyTypeId = selectedMoneyType.toMoneyType().moneyTypeId,
//                            amount = inputValue.toFloat(),
//                            customName = nameValue
//                        )
//
//                        onSaveClicked(balance)
//                    }
//                )
//            }
//        } else {
//            CustomCircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
//        }
//
//    }
//}
