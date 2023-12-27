package app.extr.ui.theme.composables.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.extr.R
import app.extr.data.types.Balance
import app.extr.ui.theme.composables.reusablecomponents.PlusButton
import app.extr.ui.theme.composables.reusablecomponents.RoundedCard
import app.extr.ui.theme.mt_card_color
import app.extr.utils.helpers.UiState
import app.extr.utils.helpers.resproviders.MoneyTypesRes

@Composable
fun HomeScreen(
    uiState: UiState<List<Balance>>
) {
    when(uiState){
        is UiState.Loading -> {

        }
        is UiState.Success -> {
            val data = uiState.data
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(13.dp),
                verticalArrangement = Arrangement.spacedBy(7.dp),
                horizontalArrangement = Arrangement.spacedBy(7.dp),

                ) {
                val elementSize = 180.dp
                items(data.size) { i ->
                    RoundedCard(
                        icon = MoneyTypesRes.getRes(data[i].moneyType.iconId).icon,
                        text = data[i].moneyType.name,
                        secondaryText = null,
                        color = MoneyTypesRes.getRes(data[i].moneyType.colorId).color, // todo: what color?
                        currencySymbol = data[i].currency.symbol,
                        number = data[i].amount,
                        modifier = Modifier.size(elementSize)
                    )
                }
                item {
                    PlusButton(onClick = { /*TODO*/ }, size = elementSize)
                }
            }
        }
        is UiState.Error -> {

        }
    }
}