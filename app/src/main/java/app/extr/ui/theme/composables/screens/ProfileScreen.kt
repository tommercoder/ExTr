package app.extr.ui.theme.composables.screens

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import app.extr.R
import app.extr.data.types.User
import app.extr.ui.theme.ExTrTheme
import app.extr.ui.theme.viewmodels.UserUiEvent
import app.extr.utils.helpers.UiState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import app.extr.ui.theme.AppPadding
import app.extr.utils.helpers.resproviders.MoneyTypesRes

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    uiState: UiState<User>,
    onEvent: (UserUiEvent) -> Unit
) {
    when (uiState) {
        is UiState.Success -> {
            var showChangeUserNameDialog by remember { mutableStateOf(false) }
            val data = uiState.data
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(AppPadding.Small),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.label_user, data.name),
                        style = MaterialTheme.typography.titleMedium
                    )
                    IconButton(onClick = {
                        showChangeUserNameDialog = true
                    }) {
                        Icon(Icons.Filled.Edit, contentDescription = null)
                    }
                    if (showChangeUserNameDialog) {
                        ChangeNameDialog(
                            currentName = data.name,
                            onNameChange = { onEvent(UserUiEvent.UserNameChanged(it)) },
                            onDismiss = { showChangeUserNameDialog = false }
                        )
                    }
                }
                HorizontalDivider(thickness = 1.dp)
                Spacer(modifier = Modifier.size(25.dp))
                SettingsSection()

            }

        }

        is UiState.Loading -> {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                CircularProgressIndicator()
            }

        }

        is UiState.Error -> {

        }
    }

}
object ThemePreference {
    private const val THEME_PREF = "theme_preferences"
    private const val THEME_KEY = "theme_dark"

    fun isDarkTheme(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(THEME_PREF, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(THEME_KEY, false)
    }

    fun setDarkTheme(context: Context, isDark: Boolean) {
        val sharedPreferences = context.getSharedPreferences(THEME_PREF, Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean(THEME_KEY, isDark).apply()
    }
}
@Composable
fun SettingsSection() {
    val context = LocalContext.current
    val isDarkTheme = remember { mutableStateOf(ThemePreference.isDarkTheme(context)) }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(id = R.string.label_settings),
            style = MaterialTheme.typography.titleLarge
        )

        Switch(
            checked = isDarkTheme.value,
            onCheckedChange = { isChecked ->
                isDarkTheme.value = isChecked
                ThemePreference.setDarkTheme(context, isChecked)
                // Trigger UI/theme refresh if necessary
            }
        )
    }
}

@Composable
fun ChangeNameDialog(
    currentName: String,
    onNameChange: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var text by remember { mutableStateOf(currentName) }
    AlertDialog(
        onDismissRequest = {},
        title = { Text(stringResource(id = R.string.change_username)) },
        text = {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(stringResource(id = R.string.username)) }
            )
        },
        confirmButton = {
            Button(onClick = {
                onNameChange(text)
                onDismiss()
            }
            ) {
                Text(stringResource(id = R.string.button_ok))
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(stringResource(id = R.string.button_cancel))
            }
        }
    )
}

@Preview
@Composable
fun profileScreenPreview() {
    val user = User(0, "name1")
    val uiState = UiState.Success(user)

    ExTrTheme {
        Surface {
//            ProfileScreen(modifier = Modifier.fillMaxSize(),
//                uiState = uiState,
//                onEvent = {}
//            )
            ChangeNameDialog("Serhii", {}, {})
        }
    }
}