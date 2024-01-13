package app.extr.ui.theme.composables.reusablecomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.extr.R
import app.extr.ui.theme.AppPadding
import app.extr.ui.theme.ExTrTheme
import app.extr.ui.theme.shapeScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomKeyboard(
    currencySymbol: Char,
    onValueChange: (String) -> Unit,
    inputValue: String,
    onNameChange: (String) -> Unit,
    nameValue: String,
    showCalendar: Boolean,
    onEraseClick: () -> Unit,
    onAcceptClick: () -> Unit,
    onCalendarClick: () -> Unit = {}, // Optional calendar click handler
    textFieldDefaultText: String = stringResource(id = R.string.label_add_custom_name),
    maxTextFieldCharacters: Int = 5
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.align(
                Alignment.CenterHorizontally)
        ) {
            Text(
                text = currencySymbol.toString(),
                style = MaterialTheme.typography.displaySmall
            )

            Text(
                text = inputValue,
                style = MaterialTheme.typography.displayLarge
            )
        }


        TextField(
            value = nameValue,
            //textStyle = TextStyle(textAlign = TextAlign.Center),
            placeholder = { Text(text = textFieldDefaultText, textAlign = TextAlign.Center) },
            onValueChange = {
                if (it.length <= maxTextFieldCharacters) {
                    onNameChange(it)
                }
            },
            //label = { Text(textFieldLabel) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppPadding.Medium, bottom = AppPadding.Medium)
        )

        // Keyboard grid
        val keys = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "", "0")
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            //todo: move buttons modifier to a const
            val buttonTempSize = 100.dp
            // Numeric buttons
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.weight(2f),
                contentPadding = PaddingValues(AppPadding.Small)
            ) {
                items(keys.size) { index ->
                    val key = keys[index]
                    if (key.isNotBlank()) {
                        SquareButtonText(
                            key = key,
                            onClicked = { onValueChange(it) },
                            modifier = Modifier
                                .size(buttonTempSize)
                                .padding(AppPadding.ExtraSmall)//.weight(1f).aspectRatio(1f)
                        )
                    }
                }
                item {
                    SquareButtonText(
                        key = ".",
                        onClicked = { onValueChange(it) },
                        modifier = Modifier
                            .size(buttonTempSize)
                            .padding(AppPadding.ExtraSmall)//.weight(1f).aspectRatio(1f),
                        , isEnabled = inputValue.isNotBlank() && !inputValue.contains('.')
                    )
                }
            }


            // Function buttons
            Column(
                Modifier.padding(AppPadding.Small)
            ) {
                SquareButtonIcon(
                    modifier = Modifier
                        .size(buttonTempSize)
                        .padding(AppPadding.ExtraSmall)//.weight(1f).aspectRatio(1f),
                    , icon = Icons.Rounded.Clear,
                    onClicked = { onEraseClick() }
                )
                if (showCalendar) {
                    SquareButtonIcon(
                        modifier = Modifier
                            .size(buttonTempSize)
                            .padding(AppPadding.ExtraSmall)//.weight(1f).aspectRatio(1f),
                        , icon = Icons.Rounded.Email,
                        onClicked = { onCalendarClick() }
                    )
                } else {
                    //Spacer(modifier = Modifier.weight(1f).aspectRatio(1f)) // Empty space
                }
                SquareButtonIcon(
                    modifier = Modifier
                        .size(buttonTempSize)
                        .fillMaxHeight()
                        .padding(AppPadding.ExtraSmall)//.weight(1f).aspectRatio(1f),
                    , icon = Icons.Rounded.Check,
                    onClicked = { onAcceptClick() },
                    isEnabled = inputValue.isNotBlank()
                )
            }
        }
    }
}

@Composable
fun SquareButtonText(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    key: String,
    onClicked: (String) -> Unit
) {
    Button(
        onClick = { if (key.isNotBlank()) onClicked(key) },
        modifier = modifier,
        enabled = isEnabled,
        shape = MaterialTheme.shapeScheme.extraLarge,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.inversePrimary
        )
    ) {
        Text(text = key, style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
fun SquareButtonIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onClicked: () -> Unit,
    isEnabled: Boolean = true
) {
    IconButton(
        onClick = onClicked,
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapeScheme.extraLarge
            ),
        content = {
            Icon(imageVector = icon, contentDescription = null)
        },
        enabled = isEnabled
    )
}

@Composable
@Preview
fun KeyboardPreview() {
    ExTrTheme() {
        Surface() {
            CustomKeyboard(
                currencySymbol = '$',
                onValueChange = {},
                inputValue = "10.304",
                onNameChange = {},
                nameValue = "",
                showCalendar = true,
                onEraseClick = { /*TODO*/ },
                onAcceptClick = { /*TODO*/ })
        }
    }
}