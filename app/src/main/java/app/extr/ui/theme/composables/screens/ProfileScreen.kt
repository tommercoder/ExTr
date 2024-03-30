package app.extr.ui.theme.composables.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import app.extr.R
import app.extr.data.types.UiMode
import app.extr.data.types.User
import app.extr.ui.theme.AppPadding
import app.extr.ui.theme.animations.CustomCircularProgressIndicator
import app.extr.ui.theme.composables.reusablecomponents.ErrorScreen
import app.extr.ui.theme.composables.reusablecomponents.NoDataYet
import app.extr.ui.theme.composables.reusablecomponents.ReusableButton
import app.extr.ui.theme.mappers.toResourceString
import app.extr.ui.theme.viewmodels.UserUiEvent
import app.extr.utils.helpers.UiState
import app.extr.utils.helpers.UserState

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    uiState: UserState,
    onEvent: (UserUiEvent) -> Unit
) {
    when (uiState) {
        is UiState.Success -> {
            val user = uiState.data

            if (user == null) {
                var showAddUserDialog by remember { mutableStateOf(false) }
                Column(
                    modifier = modifier,
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    NoDataYet(
                        headerId = R.string.label_no_profile,
                        labelId = R.string.label_create_profile
                    )
                    ReusableButton(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = {
                            showAddUserDialog = true
                        },
                        textId = R.string.btn_add_user
                    )
                }

                if (showAddUserDialog) {
                    CreateProfileDialog(
                        onConfirm = {
                            onEvent(UserUiEvent.UserCreated(it))
                        },
                        onDismiss = { showAddUserDialog = false }
                    )
                }
            } else {
                var showChangeUserNameDialog by remember { mutableStateOf(false) }
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
                            text = stringResource(id = R.string.label_user, user.name),
                            style = MaterialTheme.typography.titleMedium
                        )
                        IconButton(onClick = {
                            showChangeUserNameDialog = true
                        }) {
                            Icon(Icons.Filled.Edit, contentDescription = null)
                        }
                        if (showChangeUserNameDialog) {
                            ChangeNameDialog(
                                currentName = user.name,
                                onNameChange = { onEvent(UserUiEvent.UserNameChanged(it)) },
                                onDismiss = { showChangeUserNameDialog = false }
                            )
                        }
                    }
                    HorizontalDivider(thickness = 1.dp)
                    Spacer(modifier = Modifier.size(25.dp))
                    SettingsSection(
                        currentlySelectedMode = user.uiMode,
                        onModeSelected = { onEvent(UserUiEvent.UiModeChanged(it)) }
                    )

                }
            }
        }

        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CustomCircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is UiState.Error -> {
            ErrorScreen(onRefresh = { /*TODO*/ }, resourceId = R.string.error_profile_couldnt_load)
        }
    }

}

@Composable
fun SettingsSection(
    currentlySelectedMode: UiMode,
    onModeSelected: (UiMode) -> Unit,
) {
    var changeUiModeShown by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(id = R.string.label_settings),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(10.dp))

        ReusableButton(
            modifier = Modifier
                .align(Alignment.Start)
                .fillMaxWidth(),
            onClick = { changeUiModeShown = true },
            textId = R.string.btn_change_ui_mode,
            colors =  ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onBackground,
                contentColor = MaterialTheme.colorScheme.background)
        )

        if (changeUiModeShown) {
            UiModeSelection(
                currentlySelectedMode = currentlySelectedMode,
                onModeSelected = {
                    onModeSelected(it)
                },
                onDismiss = { changeUiModeShown = false }
            )
        }

    }
}

@Composable
fun UiModeSelection(
    currentlySelectedMode: UiMode,
    onModeSelected: (UiMode) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            UiMode.values().forEach { mode ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            onModeSelected(mode)
                            onDismiss()
                        })
                        //.padding(8.dp)
                ) {
                    RadioButton(
                        selected = mode == currentlySelectedMode,
                        onClick = {
                            onModeSelected(mode)
                            onDismiss()
                        }
                    )
                    Text(
                        text = stringResource(mode.toResourceString()),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
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
        title = { Text(stringResource(id = R.string.label_username)) },
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

@Composable
fun CreateProfileDialog(
    onConfirm: (User) -> Unit,
    onDismiss: () -> Unit
) {
    val user by remember { mutableStateOf(User()) }
    var name by remember { mutableStateOf(user.name) }
    AlertDialog(
        onDismissRequest = {},
        title = { Text(stringResource(id = R.string.btn_add_user)) },
        text = {
            TextField(
                value = name,
                onValueChange = {
                    name = it
                },
                label = { Text(stringResource(id = R.string.username)) }
            )
        },
        confirmButton = {
            Button(onClick = {
                onConfirm(
                    User(
                        name = name
                    )
                )
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

