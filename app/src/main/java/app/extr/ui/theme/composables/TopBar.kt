package app.extr.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import app.extr.R
import app.extr.data.types.CurrencyLastSelected
import app.extr.data.types.MoneyType
import app.extr.data.types.UserWithCurrencies
import app.extr.ui.theme.composables.reusablecomponents.ReusableDropdownMenu
import app.extr.ui.theme.mappers.toDropdownItem
import app.extr.ui.theme.viewmodels.MoneyTypeUiState
import app.extr.utils.helpers.UiState
import app.extr.utils.helpers.resproviders.MoneyTypesRes
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    uiState: UiState<List<CurrencyLastSelected>>,
    onItemSelected: (MoneyType) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    isAddButtonVisible: Boolean
) {

    when (uiState) {
        is UiState.Loading -> {
//            CircularProgressIndicator(
//                strokeWidth = 2.dp,
//                modifier = Modifier.size(16.dp)
//            )
        }

        is UiState.Success -> {
            var expanded by remember { mutableStateOf(false) }
            val selectedItem by remember(uiState.data) {
                mutableStateOf(uiState.data.firstOrNull { it.lastSelected })
            }

            LargeTopAppBar(
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
                    Box(
                        modifier = Modifier
                            .clickable { expanded = true }
                            .clip(MaterialTheme.shapes.medium)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(16.dp)
                        ) {

                            val selectedItemText = selectedItem?.currency?.fullName
                            //todo: replace with symbol
                            val selectedItemSymbol = selectedItem?.currency?.symbol

                            Icon(
                                imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = "Dropdown Arrow"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            if(selectedItemText != null && selectedItemSymbol != null){
                                Text(text = selectedItemSymbol + selectedItemText)
                            }
                            else {//error
                             }
                        }
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        uiState.data.forEach {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        it.currency.fullName,
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                },
                                onClick = { //todo
                                     },

                            )
                        }
                    }
                }
            )
        }

        is UiState.Error -> {}
    }

}