package app.extr.ui.theme.composables.reusablecomponents

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.extr.ui.theme.mappers.DropdownItemUi
import app.extr.data.types.Currency
import app.extr.ui.theme.shapeScheme

@Composable
fun CurrenciesDropDownMenu(
    modifier: Modifier = Modifier,
    items: List<Currency>,
    onItemSelected: (Currency) -> Unit,
    borderShown: Boolean
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(items[0]) }

    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapeScheme.large)
            .then(
                if (borderShown)
                    Modifier.border(
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.inverseSurface),
                        shape = MaterialTheme.shapeScheme.large)
                else Modifier
            )
            .wrapContentSize(Alignment.TopStart)
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = { expanded = !expanded })
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(text = selectedItem.symbol + " " + selectedItem.shortName)
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown Arrow"
            )
        }
    }
    DropdownMenu(
        modifier = modifier,
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        items.forEach { item ->
            DropdownMenuItem(
                text = { Text(item.symbol + item.shortName) },
                onClick = {
                    onItemSelected(item)
                    selectedItem = item
                    expanded = false
                }
            )
        }
    }
}

@Preview
@Composable
fun CurrenciesDropDownPreview() {
    CurrenciesDropDownMenu(
        modifier = Modifier,
        items = listOf(Currency(0, "Bla", "Bla", "$")),
        onItemSelected = {
        },
        borderShown = true
    )
}

//todo: display icon in an item, and cleanup
@Composable
fun ReusableDropdownMenu(
    items: List<DropdownItemUi>,
    modifier: Modifier = Modifier,
    selectedItem: DropdownItemUi? = null,
    onItemSelected: (DropdownItemUi) -> Unit,
    isLoading: Boolean,
    dropdownContentColor: Color = LocalContentColor.current,
    dropdownBackgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant
) {
    val selected by remember(items) {
        mutableStateOf(items.firstOrNull())
    }
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Row(
            modifier = modifier
                .clip(MaterialTheme.shapeScheme.extraRoundedCorners)
                .clickable(onClick = { expanded = !isLoading && !expanded })
                .background((selected?.color) ?: dropdownBackgroundColor)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = dropdownContentColor,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(16.dp)
                )
            } else {
                val symbol = selected?.symbol
                val icon = selected?.icon

                when {
                    symbol != null -> Text(text = symbol, color = dropdownContentColor)
                    icon != null -> Icon(
                        painterResource(id = icon),
                        contentDescription = null,
                        tint = dropdownContentColor
                    )

                    else -> {} // Do nothing if no symbol or icon is provided
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = selected?.name ?: "",
                    color = dropdownContentColor
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Arrow",
                    tint = dropdownContentColor
                )
            }
        }

        DropdownMenu(
            expanded = expanded && !isLoading,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.Transparent)
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    },
                    text = {
                        val symbol = selected?.symbol
                        val icon = selected?.icon

                        if (symbol != null) {
                            Text(text = symbol, color = item.color ?: dropdownContentColor)
                        } else {
                            Text(
                                text = item.name
                            )
                        }
                    },
                    modifier = Modifier.background(
                        color = item.color ?: dropdownContentColor,
                        //shape = menuShape
                    )
                )
            }

        }
    }
}