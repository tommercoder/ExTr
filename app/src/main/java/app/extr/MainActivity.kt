package app.extr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import app.extr.data.types.UiMode
import app.extr.ui.theme.ExTrTheme
import app.extr.ui.theme.viewmodels.UserViewModel
import app.extr.ui.theme.viewmodels.ViewModelsProvider
import app.extr.utils.helpers.UiState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val userViewModel: UserViewModel = viewModel(factory = ViewModelsProvider.Factory)
            val useDarkTheme = userViewModel.uiState.collectAsStateWithLifecycle().value.let { uiState ->
                when (uiState) {
                    is UiState.Success -> {
                        when (uiState.data?.uiMode) {
                            UiMode.DARK -> true
                            UiMode.LIGHT -> false
                            else -> isSystemInDarkTheme()
                        }
                    }
                    else -> isSystemInDarkTheme()
                }
            }

            ExTrTheme(useDarkTheme = useDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ExTrApp()
                }
            }
        }
    }
}

