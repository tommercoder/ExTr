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
import app.extr.ui.theme.animations.CustomCircularProgressIndicator
import app.extr.ui.theme.composables.reusablecomponents.ErrorScreen
import app.extr.ui.theme.composables.reusablecomponents.NoDataYet
import app.extr.ui.theme.composables.reusablecomponents.ReusableButton
import app.extr.utils.helpers.resproviders.MoneyTypesRes

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    uiState: UiState<User?>,
    onEvent: (UserUiEvent) -> Unit
) {
    when (uiState) {
        is UiState.Success -> {
            val user = uiState.data

            if (user == null) {
                var showAddUserDialog by remember { mutableStateOf(false) }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(AppPadding.Small),
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
                    SettingsSection()

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
fun SettingsSection() {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(id = R.string.label_settings),
            style = MaterialTheme.typography.titleLarge
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
            //todo: add the checkboxes here
        },
        confirmButton = {
            Button(onClick = {
                onConfirm(
                    User(
                        name = name
                    ))
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

