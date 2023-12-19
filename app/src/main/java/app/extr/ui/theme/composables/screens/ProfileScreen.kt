package app.extr.ui.theme.composables.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import app.extr.utils.helpers.resproviders.MoneyTypesRes

@Composable
fun ProfileScreen(
    modifier: Modifier,
    uiState: UiState<List<User>>,
    onEvent: (UserUiEvent) -> Unit
) {
    when (uiState) {
        is UiState.Success -> {
            if (uiState.data.isNotEmpty()) {
                var expanded by remember { mutableStateOf(true/*change to false later*/) }
                val selectedItem by remember(uiState.data) {
                    mutableStateOf(uiState.data.firstOrNull { it.lastSelected })
                }

//                Column(
//                    modifier = modifier,
//                    horizontalAlignment = Alignment.Start
//                ) {
//                    Text(
//                        text = selectedItem?.name?: "",//stringResource(R.string.label_users),
//                        style = MaterialTheme.typography.titleLarge
//                    )

                    Box(
                        modifier = Modifier
                            .clickable { expanded = true }
                            .clip(MaterialTheme.shapes.medium)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            val selectedItemText = selectedItem?.name
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown Arrow"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            when {
                                selectedItemText != null -> Text(text = selectedItemText)
                                else -> {}
                            }
                        }

                        }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        uiState.data.forEach {
                            DropdownMenuItem(
                                text = { Text(text = it.name) },
                                onClick = {
                                    onEvent(UserUiEvent.UserSelected(it.id))
                                })
                        }
                    }
               // }
            } else {
                Column(
                    modifier = modifier,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(id = R.string.no_users_added_yet))
                }
            }
        }

        is UiState.Loading -> {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }

        }

        is UiState.Error -> {

        }
    }

}

@Preview
@Composable
fun profileScreenPreview() {
    val previewList = listOf(User(0, "name1", false), User(1, "name2", true))
    val uiState = UiState.Success(previewList)

    ExTrTheme {
        Surface {
            ProfileScreen(modifier = Modifier.fillMaxSize(),
                uiState = uiState,
                onEvent = {}
            )
        }
    }
}