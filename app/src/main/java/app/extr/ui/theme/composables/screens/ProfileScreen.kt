package app.extr.ui.theme.composables.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.extr.data.types.User
import app.extr.utils.helpers.UiState

@Composable
fun ProfileScreen(
    modifier: Modifier,
    uiState: UiState<List<User>>
) {
    when (uiState) {
        is UiState.Success -> {
            if(uiState.data.isNotEmpty()) {
                DropdownMenu(expanded = true, onDismissRequest = { /*TODO*/ }) {
                    uiState.data.forEach {
                        DropdownMenuItem(text = { it.name }, onClick = { /*TODO*/ })
                    }
                }
            }
            else{
                Text("NO USERS ADDED YET")
            }
        }
        is UiState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
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