package app.extr

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.extr.ui.theme.TopBar
import app.extr.ui.theme.composables.screens.HomeScreen
import app.extr.ui.theme.composables.Screens
import app.extr.ui.theme.composables.screens.ChartScreen
import app.extr.ui.theme.composables.screens.ProfileScreen
import app.extr.ui.theme.composables.screens.RoundChartScreen
import app.extr.ui.theme.viewmodels.BottomBar
import app.extr.ui.theme.viewmodels.ViewModelsProvider

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import app.extr.ui.theme.composables.BalanceBottomSheet
import app.extr.ui.theme.viewmodels.BalancesViewModel
import app.extr.ui.theme.viewmodels.CurrenciesViewModel
import app.extr.ui.theme.viewmodels.MoneyTypesViewModel
import app.extr.ui.theme.viewmodels.UsedCurrenciesViewModel
import app.extr.ui.theme.viewmodels.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExTrApp(
    navController: NavHostController = rememberNavController(),
    currenciesViewModel: CurrenciesViewModel = viewModel(factory = ViewModelsProvider.Factory),
    moneyTypesViewModel: MoneyTypesViewModel = viewModel(factory = ViewModelsProvider.Factory)
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val navBackStackEntry =
        navController.currentBackStackEntryAsState().value?.destination?.route

    val currenciesUiState by currenciesViewModel.currencies.collectAsStateWithLifecycle()
    val moneyTypesUiState by moneyTypesViewModel.moneyTypes.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
        //.nestedScroll(scrollBehavior.nestedScrollConnection),
        ,
        topBar = {
            val viewModel: UsedCurrenciesViewModel = viewModel(factory = ViewModelsProvider.Factory)
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val currentlySelectedCurrency by viewModel.currentlySelectedCurrency.collectAsStateWithLifecycle()
            TopBar(
                uiState = uiState,
                onItemSelected = { currency ->
                    viewModel.selectCurrency(currencyId = currency.currencyId)
                },
                scrollBehavior = scrollBehavior,
                isAddButtonVisible = navBackStackEntry == Screens.RoundChart.route
                        || navBackStackEntry == Screens.Chart.route,
                selectedCurrency = currentlySelectedCurrency?.currency
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
                val viewModel: BalancesViewModel = viewModel(factory = ViewModelsProvider.Factory)
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val totalBalance by viewModel.totalBalance.collectAsStateWithLifecycle()
                var isAddBalanceSheetShown by remember {
                    mutableStateOf(false)
                }

                HomeScreen(
                    modifier = Modifier.fillMaxSize(),
                    uiState = uiState,
                    totalBalance = totalBalance,
                    onAddBalanceClicked = {
                        isAddBalanceSheetShown = true
                    },
                    onDeleteBalanceClicked = { balanceId ->
                        viewModel.deleteBalance(balanceId)
                    }
                )

                if (isAddBalanceSheetShown) {
                    BalanceBottomSheet(
                        currenciesUiState = currenciesUiState,
                        moneyTypeUiState = moneyTypesUiState,
                        onSaveClicked = { balance ->
                            viewModel.addBalance(balance)
                        },
                        onDismissed = { isAddBalanceSheetShown = false }
                    )
                }
            }
            composable(Screens.RoundChart.route) {
                RoundChartScreen()
            }
            composable(Screens.Chart.route) {
                ChartScreen()
            }
            composable(Screens.Profile.route) {
                val viewModel: UserViewModel = viewModel(factory = ViewModelsProvider.Factory)
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                ProfileScreen(
                    modifier = Modifier.fillMaxSize(),
                    uiState = uiState,
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}

