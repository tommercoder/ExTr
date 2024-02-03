package app.extr

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import app.extr.data.types.Expense
import app.extr.ui.theme.AppPadding
import app.extr.ui.theme.composables.BalanceBottomSheet
import app.extr.ui.theme.composables.reusablecomponents.ExpenseIncomeDateRow
import app.extr.ui.theme.composables.reusablecomponents.SelectedButton
import app.extr.ui.theme.viewmodels.BalancesViewModel
import app.extr.ui.theme.viewmodels.CurrenciesViewModel
import app.extr.ui.theme.viewmodels.ExpensesIncomeTypesViewModel
import app.extr.ui.theme.viewmodels.ExpensesIncomeViewModel
import app.extr.ui.theme.viewmodels.MoneyTypesViewModel
import app.extr.ui.theme.viewmodels.UsedCurrenciesViewModel
import app.extr.ui.theme.viewmodels.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExTrApp(
    navController: NavHostController = rememberNavController(),
    currenciesViewModel: CurrenciesViewModel = viewModel(factory = ViewModelsProvider.Factory),
    moneyTypesViewModel: MoneyTypesViewModel = viewModel(factory = ViewModelsProvider.Factory),
    expensesIncomeTypesViewModel: ExpensesIncomeTypesViewModel = viewModel(factory = ViewModelsProvider.Factory),
    expenseIncomeViewModel: ExpensesIncomeViewModel = viewModel(factory = ViewModelsProvider.Factory)
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val navBackStackEntry =
        navController.currentBackStackEntryAsState().value?.destination?.route

    val currenciesUiState by currenciesViewModel.currencies.collectAsStateWithLifecycle()
    val moneyTypesUiState by moneyTypesViewModel.moneyTypes.collectAsStateWithLifecycle()

    val expenseTypesUiState by expensesIncomeTypesViewModel.expenseTypes.collectAsStateWithLifecycle()
    val incomeTypesUiState by expensesIncomeTypesViewModel.incomeTypes.collectAsStateWithLifecycle()

    val expensesUiState by expenseIncomeViewModel.uiStateExpenses.collectAsStateWithLifecycle()
    val incomeUiState by expenseIncomeViewModel.uiStateIncome.collectAsStateWithLifecycle()

    var selectedButton by remember { mutableStateOf(SelectedButton.EXPENSES) }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }, //todo: remove
        topBar = {
            val viewModel: UsedCurrenciesViewModel = viewModel(factory = ViewModelsProvider.Factory)
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val currentlySelectedCurrency by viewModel.currentlySelectedCurrency.collectAsStateWithLifecycle()
            val chartButtonsShown = navBackStackEntry == Screens.RoundChart.route
                    || navBackStackEntry == Screens.Chart.route
            Column {
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
                if (chartButtonsShown) {
                    ExpenseIncomeDateRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onSelected = {
                            selectedButton = it
                        },
                        onDateClicked = {
                            val expense = Expense(
                                typeId = 0,
                                balanceId = 1,
                                description = "blabla",
                                amount = 0.3f,
                                month = 2,
                                year = 2024
                            )
                            expenseIncomeViewModel.insertExpense(expense)
                        }
                    )
                }
            }
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
                var isAddBalanceSheetShown by remember { mutableStateOf(false) }

                HomeScreen(
                    modifier = Modifier.fillMaxSize(),
                    uiState = uiState,
                    totalBalance = totalBalance,
                    onAddBalanceClicked = {
                        isAddBalanceSheetShown = true
                    },
                    onDeleteBalanceClicked = { balanceId ->
                        viewModel.deleteBalance(balanceId)
                    },
                    onRefresh = {
                        viewModel.refreshData()
                    }
                )

                val toastText = stringResource(id = R.string.label_snackbar_couldnt_create_balance)
                var showToast by remember { mutableStateOf(false) }
                if (showToast) {
                    Toast.makeText(LocalContext.current, toastText, Toast.LENGTH_SHORT).show()
                    showToast = false
                }
                if (isAddBalanceSheetShown) {
                    BalanceBottomSheet(
                        currenciesUiState = currenciesUiState,
                        moneyTypeUiState = moneyTypesUiState,
                        onSaveClicked = { balance ->
                            if (viewModel.doesBalanceExist(balance)) {
                                showToast = true //todo: move showing to a separate composable
                            } else {
                                viewModel.addBalance(balance)
                                isAddBalanceSheetShown = false
                            }
                        },
                        onDismissed = { isAddBalanceSheetShown = false }
                    )
                }
            }
            composable(Screens.RoundChart.route) {
                RoundChartScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = AppPadding.Small),
                    uiState = if (selectedButton == SelectedButton.EXPENSES) expensesUiState else incomeUiState
                )
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

