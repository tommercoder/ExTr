package app.extr.ui.theme.composables.reusablecomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.extr.R
import app.extr.ui.theme.AppPadding
import app.extr.ui.theme.ExTrTheme
import app.extr.ui.theme.shapeScheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

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
    maxTextFieldCharacters: Int = 8
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.align(
                Alignment.CenterHorizontally
            )
        ) {
            Text(
                modifier = Modifier
                    .padding(end = AppPadding.ExtraSmall),
                text = currencySymbol.toString(),
                style = MaterialTheme.typography.displaySmall.copy(color = MaterialTheme.colorScheme.secondary)
            )

            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = inputValue.ifBlank { "0.00" },
                style = if (inputValue.isNotBlank())
                    MaterialTheme.typography.displayLarge
                else
                    MaterialTheme.typography.displayLarge.copy(color = MaterialTheme.colorScheme.secondary)
            )
        }

        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppPadding.Medium),
            value = nameValue,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                //cursorColor = Color.Transparent,
            ),
            textStyle = TextStyle(textAlign = TextAlign.Center),
            placeholder = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = textFieldDefaultText,
                            style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center)
                        )
                        Text(
                            text = stringResource(id = R.string.label_required),
                            style = MaterialTheme.typography.bodySmall.copy(textAlign = TextAlign.Center)
                        )
                    }
                }
            },
            onValueChange = {
                if (it.length <= maxTextFieldCharacters) {
                    onNameChange(it)
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
                focusManager.clearFocus(force = true)
            }),
        )

        // Keyboard grid
        val configuration = LocalConfiguration.current
        val screenWidth = remember(configuration) { configuration.screenWidthDp.dp }
        val squareSize = remember(configuration) { screenWidth / 4 }
        //Log.d("TAG", squareSize.toString()) //102.75.dp
        val dotSign = "."
        val keys = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "", "0", dotSign)

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            NonLazyGrid(
                columns = 3,
                itemCount = keys.size,
                modifier = Modifier
                    .padding(AppPadding.ExtraSmall)
                    .weight(3f)
            ) { it ->
                if (keys[it].isNotBlank()) {
                    SquareButtonText(
                        key = keys[it],
                        onClicked = {
                            if (isValidInput(inputValue + it)) {
                                onValueChange(it)
                            }
                        },
                        modifier = Modifier
                            .size(squareSize)
                            .padding(AppPadding.ExtraSmall),
                        isEnabled = if (keys[it] == dotSign)
                            inputValue.isNotBlank() && !inputValue.contains(dotSign)
                        else
                            isValidInput(inputValue + keys[it])
                    )
                }
            }

            // Function buttons
            Column(
                Modifier
                    .padding(AppPadding.ExtraSmall)
                    .weight(1f)
            ) {
                SquareButtonIcon(
                    modifier = Modifier
                        .size(squareSize)
                        .padding(AppPadding.ExtraSmall),
                    icon = Icons.Rounded.Clear,
                    onClicked = { onEraseClick() }
                )
                if (showCalendar) {
                    SquareButtonIcon(
                        modifier = Modifier
                            .size(squareSize)
                            .padding(AppPadding.ExtraSmall),
                        icon = Icons.Rounded.Email,
                        onClicked = { onCalendarClick() }
                    )
                }
                SquareButtonIcon(
                    modifier = Modifier
                        .size(width = squareSize, height = squareSize * 2)
                        .padding(AppPadding.ExtraSmall),
                    icon = Icons.Rounded.Check,
                    onClicked = { onAcceptClick() },
                    isEnabled = inputValue.isNotBlank() && nameValue.isNotBlank()
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
        modifier = modifier,//.aspectRatio(1f),
        enabled = isEnabled,
        shape = MaterialTheme.shapeScheme.extraLarge,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
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
    Button(
        onClick = onClicked,
        modifier = modifier,
        enabled = isEnabled,
        shape = MaterialTheme.shapeScheme.extraLarge,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Icon(imageVector = icon, contentDescription = null)
    }
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

fun isValidInput(currentValue: String): Boolean {
    val parts = currentValue.split(".")
    return when {
        parts.size > 2 -> false
        parts.size == 2 -> parts[0].length <= 5 && parts[1].length <= 2
        else -> currentValue.length <= 5
    }
}