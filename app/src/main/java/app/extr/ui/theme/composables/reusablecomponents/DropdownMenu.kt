package app.extr.ui.theme.composables.reusablecomponents

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.extr.R
import app.extr.ui.theme.mappers.DropdownItemUi
import app.extr.data.types.Currency
import app.extr.ui.theme.shapeScheme
import app.extr.utils.helpers.crop


//todo: move textfield to common composable
//todo: Create common item?
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

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
            .clip(MaterialTheme.shapeScheme.large)
            .then(
                if (borderShown)
                    Modifier.border(
                        border = BorderStroke(
                            1.dp,
                            MaterialTheme.colorScheme.inverseSurface
                        ),
                        shape = MaterialTheme.shapeScheme.large
                    )
                else Modifier
            )
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor(),
            value = selectedItem.symbol + " " + selectedItem.shortName,
            onValueChange = {},
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            textStyle = MaterialTheme.typography.bodyMedium,
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = if (expanded)
                        ImageVector.vectorResource(id = R.drawable.expand_less_icon)
                    else
                        ImageVector.vectorResource(
                            id = R.drawable.expand_more_icon
                        ),
                    contentDescription = null
                )
            },
        )

        ExposedDropdownMenu(
            modifier = Modifier
                .crop(vertical = 8.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    modifier = Modifier
                        .clip(MaterialTheme.shapeScheme.large),
                    text = {
                        Text(
                            text = item.symbol + " " + item.shortName,
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    onClick = {
                        if(item != selectedItem) {
                            onItemSelected(item)
                        }
                        expanded = false

                        if (selectedPassed == null) {
                            selectedItem =
                                item // for those dropdowns where a user should immediately see selected value without additional logic
                        }
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReusableDropdownMenu(
    modifier: Modifier = Modifier,
    items: List<DropdownItemUi>,
    onItemSelected: (DropdownItemUi) -> Unit,
    selectedItem: DropdownItemUi? = null
) {
    var selected by remember {
        mutableStateOf(selectedItem ?: items.first())
    }
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
            .clip(MaterialTheme.shapeScheme.large)
            .background(selected.color)
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor(),
            value = selected.name,
            onValueChange = {},
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            textStyle = MaterialTheme.typography.bodyMedium,//  .copy(color = MaterialTheme.colorScheme.inversePrimary),
            readOnly = true,
            leadingIcon = {
                Icon(
                    painterResource(id = selected.icon),
                    contentDescription = null,
                    //tint = MaterialTheme.colorScheme.onPrimary
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = if (expanded)
                        ImageVector.vectorResource(id = R.drawable.expand_less_icon)
                    else
                        ImageVector.vectorResource(id = R.drawable.expand_more_icon),
                    contentDescription = null,
                    //tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .crop(vertical = 1.dp)
                //.background(color = selected.color),
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    modifier = Modifier
                        .clip(MaterialTheme.shapeScheme.large),
                    onClick = {
                        onItemSelected(item)
                        selected = item
                        expanded = false
                    },
                    text = {
                        Text(
                            text = item.name,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = null
                        )
                    }
                )
            }

        }
    }

}
