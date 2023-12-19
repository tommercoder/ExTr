package app.extr

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.extr.ui.theme.TopBar
import app.extr.ui.theme.composables.HomeScreen
import app.extr.ui.theme.composables.Screens
import app.extr.ui.theme.composables.screens.ChartScreen
import app.extr.ui.theme.composables.screens.ProfileScreen
import app.extr.ui.theme.composables.screens.RoundChartScreen
import app.extr.ui.theme.viewmodels.BottomBar
import app.extr.ui.theme.viewmodels.MoneyTypesViewModel
import app.extr.ui.theme.viewmodels.ViewModelsProvider

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import app.extr.ui.theme.viewmodels.UsersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExTrApp(
    navController: NavHostController = rememberNavController()
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val navBackStackEntry =
        navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            val MoneyTypesViewModel: MoneyTypesViewModel =
                viewModel(factory = ViewModelsProvider.Factory)
            val uiState by MoneyTypesViewModel.uiState.collectAsState()
            TopBar(
                uiState = uiState,
                onItemSelected = { /*update last selected of UserCurrencies*/ },
                scrollBehavior = scrollBehavior,
                isAddButtonVisible = navBackStackEntry == Screens.RoundChart.route
                        || navBackStackEntry == Screens.Chart.route
            )
        },
        bottomBar = {
            BottomBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(Screens.Home.route) {
                HomeScreen()
            }
            composable(Screens.RoundChart.route) {
                RoundChartScreen()
            }
            composable(Screens.Chart.route) {
                ChartScreen()
            }
            composable(Screens.Profile.route) {
                val viewModel: UsersViewModel = viewModel(factory = ViewModelsProvider.Factory)
                val uiState by viewModel.uiState.collectAsState()
                ProfileScreen(
                    modifier = Modifier.fillMaxSize(),
                    uiState = uiState,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}

