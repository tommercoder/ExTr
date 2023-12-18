package app.extr.ui.theme.composables.reusablecomponents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import app.extr.ui.theme.mappers.DropdownItemUi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Shape

@Composable
fun ReusableDropdownMenu(
    items: List<DropdownItemUi>,
    modifier: Modifier = Modifier,
    selectedItem: DropdownItemUi? = null,
    onItemSelected: (DropdownItemUi) -> Unit,
    isLoading: Boolean,
    dropdownContentColor: Color = LocalContentColor.current,
    dropdownBackgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    menuShape: Shape = RoundedCornerShape(50)
) {
    val selected by remember(items) {
        mutableStateOf(items.firstOrNull())
    }
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.wrapContentSize(Alignment.TopStart)) {
        Row(
            modifier = Modifier
                .clip(menuShape)
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
                            shape = menuShape
                        )
                    )
                }

        }
    }
}