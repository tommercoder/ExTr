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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.extr.R
import app.extr.ui.theme.mappers.DropdownItemUi
import app.extr.data.types.Currency
import app.extr.ui.theme.shapeScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrenciesDropDownMenu(
    modifier: Modifier = Modifier,
    items: List<Currency>, //passed list must be not empty
    onItemSelected: (Currency) -> Unit,
    borderShown: Boolean,
    selectedPassed: Currency? = null
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember(selectedPassed) { mutableStateOf(selectedPassed ?: items.first()) }

    Box(
        modifier = modifier
            .clip(MaterialTheme.shapeScheme.large)
            .then(
                if (borderShown)
                    Modifier.border(
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.inverseSurface),
                        shape = MaterialTheme.shapeScheme.large
                    )
                else Modifier
            ),
//        expanded = expanded,
//        onExpandedChange = { expanded = !expanded }
        //.wrapContentSize(Alignment.TopStart)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { expanded = !expanded })
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(text = selectedItem.symbol + " " + selectedItem.shortName)
            Icon(
                imageVector = if (expanded)
                    ImageVector.vectorResource(id = R.drawable.expand_less_icon)
                else
                    ImageVector.vectorResource(
                        id = R.drawable.expand_more_icon
                    ),
                contentDescription = "Dropdown Arrow"
            )
        }

        DropdownMenu(
            modifier = Modifier,
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    modifier = Modifier.clip(MaterialTheme.shapeScheme.large),
                    text = { Text(item.symbol + " " + item.shortName) },
                    onClick = {
                        onItemSelected(item)
                        selectedItem = item
                        expanded = false
                    }
                )
            }
        }
    }

}

@Preview
@Composable
fun CurrenciesDropDownPreview() {
    CurrenciesDropDownMenu(
        modifier = Modifier,
        items = listOf(Currency(0, "Bla", "Bla", '$')),
        onItemSelected = {
        },
        borderShown = true
    )
}

//todo: display icon in an item, and cleanup
//todo: can I make sure the items are not null?
@Composable
fun ReusableDropdownMenu(
    modifier: Modifier = Modifier,
    items: List<DropdownItemUi>,
    onItemSelected: (DropdownItemUi) -> Unit,
    dropdownContentColor: Color = LocalContentColor.current,
    dropdownBackgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    selectedItem: DropdownItemUi? = null
) {
    var selected by remember {
        mutableStateOf(selectedItem ?: items.firstOrNull())
    }
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .clip(MaterialTheme.shapeScheme.large)
            .background((selected?.color) ?: dropdownBackgroundColor)
            .wrapContentSize(Alignment.TopStart),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { expanded = !expanded })
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val icon = selected?.icon
            if (icon != null) {
                Icon(
                    painterResource(id = icon),
                    contentDescription = null,
                    tint = dropdownContentColor
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = selected?.name ?: "",
                color = dropdownContentColor
            )

            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = if (expanded)
                    ImageVector.vectorResource(id = R.drawable.expand_less_icon)
                else
                    ImageVector.vectorResource(
                        id = R.drawable.expand_more_icon
                    ),
                contentDescription = "Dropdown Arrow",
                tint = dropdownContentColor
            )
        }


        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.Transparent)
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onItemSelected(item)
                        selected = item
                        expanded = false
                    },
                    text = {
                        //todo: add icon support here?
                        val icon = selected?.icon
                        Text(
                            text = item.name,
                            color = item.color ?: dropdownContentColor
                        )

                    },
//                    modifier = Modifier.background(
//                        color = item.color
//                            ?: dropdownContentColor, //todo: text should be colored I think not the back
//                        //shape = menuShape
//                    )
                )
            }

        }
    }

}
