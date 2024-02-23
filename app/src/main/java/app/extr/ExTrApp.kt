package app.extr

import android.widget.Toast
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import app.extr.data.types.BalanceWithDetails
import app.extr.data.types.Currency
import app.extr.data.types.Expense
import app.extr.data.types.MoneyType
import app.extr.data.types.TransactionType
import app.extr.ui.theme.AppPadding
import app.extr.ui.theme.LocalCustomColorsPalette
import app.extr.ui.theme.composables.BalanceBottomSheet
import app.extr.ui.theme.composables.DatePickerDialogCaller
import app.extr.ui.theme.composables.ExpenseIncomeBottomSheetCaller
import app.extr.ui.theme.composables.ExpensesIncomeBottomSheet
import app.extr.ui.theme.composables.reusablecomponents.ExpenseIncomeDateRow
import app.extr.ui.theme.composables.reusablecomponents.SelectedTransactionType
import app.extr.ui.theme.mappers.DropdownItemUi
import app.extr.ui.theme.mappers.toDropdownItem
import app.extr.ui.theme.viewmodels.BalancesViewModel
import app.extr.ui.theme.viewmodels.CurrenciesViewModel
import app.extr.ui.theme.viewmodels.DatePickerViewModel
import app.extr.ui.theme.viewmodels.ExpensesIncomeBottomSheetViewModel
import app.extr.ui.theme.viewmodels.ExpensesIncomeTypesViewModel
import app.extr.ui.theme.viewmodels.ExpensesIncomeViewModel
import app.extr.ui.theme.viewmodels.MoneyTypesViewModel
import app.extr.ui.theme.viewmodels.UsedCurrenciesViewModel
import app.extr.ui.theme.viewmodels.UserViewModel
import app.extr.utils.helpers.UiState
import app.extr.utils.helpers.resproviders.ExpenseTypesRes
import app.extr.utils.helpers.resproviders.IncomeTypesRes
import app.extr.utils.helpers.resproviders.MoneyTypesRes
import app.extr.utils.helpers.resproviders.ResProvider


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExTrApp(
    navController: NavHostController = rememberNavController()
) {
    //val scrollBehavior =
      //  TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    val navBackStackEntry =
        navController.currentBackStackEntryAsState().value?.destination?.route

    var selectedType by remember { mutableStateOf(SelectedTransactionType.EXPENSES) }
    val expensesIncomeBottomSheetViewModel: ExpensesIncomeBottomSheetViewModel =
        viewModel(factory = ViewModelsProvider.Factory)
    val datePickerViewModel: DatePickerViewModel = viewModel(factory = ViewModelsProvider.Factory)
    val expenseIncomeViewModel: ExpensesIncomeViewModel = viewModel(factory = ViewModelsProvider.Factory)

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            val viewModel: UsedCurrenciesViewModel = viewModel(factory = ViewModelsProvider.Factory)
            val dateState by datePickerViewModel.dateState.collectAsStateWithLifecycle()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val currentlySelectedCurrency by viewModel.currentlySelectedCurrency.collectAsStateWithLifecycle()


            val chartButtonsShown = navBackStackEntry == Screens.RoundChart.route
                    || navBackStackEntry == Screens.Chart.route
            val isDropdownVisible = navBackStackEntry != Screens.Profile.route
            val context = LocalContext.current
            val toastText = stringResource(id = R.string.label_balances_are_empty)
            Column {
                TopBar(
                    uiState = uiState,
                    onItemSelected = { currency ->
                        viewModel.selectCurrency(currencyId = currency.currencyId)
                    },
                    onAddClicked = {
                        val opened = expensesIncomeBottomSheetViewModel.toggleBottomSheet(true)
                        if (!opened) {
                            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
                        }
                    },
                    //scrollBehavior = scrollBehavior,
                    isAddButtonVisible = navBackStackEntry == Screens.RoundChart.route
                            || navBackStackEntry == Screens.Chart.route,
                    isDropdownVisible = isDropdownVisible,
                    selectedCurrency = currentlySelectedCurrency?.currency
                )
                if (chartButtonsShown) {
                    ExpenseIncomeDateRow(
                        modifier = Modifier
                            .fillMaxWidth().padding(start = AppPadding.Small, end = AppPadding.Small),
                        onSelected = {
                            selectedType = it
                        },
                        onDateClicked = {
                            datePickerViewModel.toggleDatePicker(true)
                        },
                        date = dateState.date
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
            startDestination = Screens.RoundChart.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }
        ) {
            composable(Screens.Home.route) {
                HomeScreenRoute()
            }
            composable(Screens.RoundChart.route) {
                RoundChartScreenRoute(
                    selectedType = selectedType,
                    onCardClicked = {
                        expensesIncomeBottomSheetViewModel.toggleBottomSheet(true, it.id)
                    },
                    expenseIncomeViewModel = expenseIncomeViewModel
                )
            }
            composable(Screens.Chart.route) {
                ChartScreenRoute(
                    selectedType = selectedType,
                    expenseIncomeViewModel = expenseIncomeViewModel
                )
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

    DatePickerDialogCaller(datePickerViewModel = datePickerViewModel, expensesIncomeViewModel = expenseIncomeViewModel)
    ExpenseIncomeBottomSheetCaller(
        expensesIncomeBottomSheetViewModel = expensesIncomeBottomSheetViewModel,
        expensesIncomeViewModel = expenseIncomeViewModel,
        datePickerViewModel = datePickerViewModel,
        selectedTransactionType = selectedType
    )
}

@Composable
fun ChartScreenRoute(
    selectedType: SelectedTransactionType,
    expenseIncomeViewModel: ExpensesIncomeViewModel
){
    val uiState by expenseIncomeViewModel.combinedUiState.collectAsStateWithLifecycle()

    val expenseTypesRes = ExpenseTypesRes(LocalCustomColorsPalette.current)
    val incomeTypesRes = IncomeTypesRes(LocalCustomColorsPalette.current)

    ChartScreen(
        selectedType = selectedType,
        transactionsByType =  if (selectedType == SelectedTransactionType.EXPENSES) uiState.expensesByCategoriesState else uiState.incomeByCategoriesState,
        resProvider = if (selectedType == SelectedTransactionType.EXPENSES) expenseTypesRes else incomeTypesRes
    )
}

@Composable
fun RoundChartScreenRoute(
    selectedType: SelectedTransactionType,
    onCardClicked: (TransactionType) -> Unit,
    expenseIncomeViewModel: ExpensesIncomeViewModel
) {
    val uiState by expenseIncomeViewModel.combinedUiState.collectAsStateWithLifecycle()

    val expenseTypesRes = ExpenseTypesRes(LocalCustomColorsPalette.current)
    val incomeTypesRes = IncomeTypesRes(LocalCustomColorsPalette.current)
    RoundChartScreen(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = AppPadding.ExtraSmall),
        uiState = if (selectedType == SelectedTransactionType.EXPENSES) uiState.expensesState else uiState.incomeState,
        transactionsByTypes = if (selectedType == SelectedTransactionType.EXPENSES) uiState.expensesByCategoriesState else uiState.incomeByCategoriesState,
        resProvider = if (selectedType == SelectedTransactionType.EXPENSES) expenseTypesRes else incomeTypesRes,
        selectedType = selectedType,
        onCardClicked = {
            onCardClicked(it)
        },
        onRefresh = {

        }
    )
}

//todo: pass only ui states and callback here
@Composable
fun HomeScreenRoute() {
    val viewModel: BalancesViewModel = viewModel(factory = ViewModelsProvider.Factory)
    val currenciesViewModel: CurrenciesViewModel = viewModel(factory = ViewModelsProvider.Factory)
    val moneyTypesViewModel: MoneyTypesViewModel = viewModel(factory = ViewModelsProvider.Factory)
    val palette = LocalCustomColorsPalette.current
    val moneyTypesRes = remember { MoneyTypesRes(palette) }


    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currenciesUiState by currenciesViewModel.currencies.collectAsStateWithLifecycle()
    val moneyTypesUiState by moneyTypesViewModel.moneyTypes.collectAsStateWithLifecycle()
    val totalBalance by viewModel.totalBalance.collectAsStateWithLifecycle()
    val isBottomSheetDataValid = remember(currenciesUiState, moneyTypesUiState) {
        currenciesUiState is UiState.Success && (currenciesUiState as UiState.Success<List<Currency>>).data.isNotEmpty()
                && moneyTypesUiState is UiState.Success && (moneyTypesUiState as UiState.Success<List<MoneyType>>).data.isNotEmpty()
    }

    val context = LocalContext.current
    val toastTextBalanceExists = stringResource(id = R.string.label_toast_couldnt_create_balance)
    val toastTextCantAddBalance =
        stringResource(id = R.string.label_toast_cant_add_balance_due_to_empty_data)
    var isAddBalanceSheetShown by remember { mutableStateOf(false) }

    HomeScreen(
        modifier = Modifier.fillMaxSize(),
        uiState = uiState,
        totalBalance = totalBalance,
        moneyTypesRes = moneyTypesRes,
        isAddBalanceEnabled = isBottomSheetDataValid,
        onAddBalanceClicked = {
            if (isBottomSheetDataValid) {
                isAddBalanceSheetShown = true
            } else {
                Toast.makeText(context, toastTextCantAddBalance, Toast.LENGTH_SHORT).show()
            }
        },
        onDeleteBalanceClicked = { balanceId ->
            viewModel.deleteBalance(balanceId)
        },
        onRefresh = {
            viewModel.refreshData()
        },

        )

    if (isAddBalanceSheetShown && isBottomSheetDataValid) {
        //todo: remember seems to be not needed
        val currencies = remember(currenciesUiState) {
            (currenciesUiState as UiState.Success<List<Currency>>).data
        }
        val dropdownItems = remember(moneyTypesUiState) {
            (moneyTypesUiState as UiState.Success<List<MoneyType>>).data.map {
                it.toDropdownItem(
                    moneyTypesRes
                )
            }
        }

        BalanceBottomSheet(
            currencies = currencies,
            moneyTypes = dropdownItems,
            initialCurrency = currencies.first(),
            initialMoneyType = dropdownItems.first(),
            onSaveClicked = { balance ->
                if (viewModel.doesBalanceExist(balance)) {
                    Toast.makeText(context, toastTextBalanceExists, Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.addBalance(balance)
                    isAddBalanceSheetShown = false
                }
            },
            onDismissed = { isAddBalanceSheetShown = false },

            )
    }
}

