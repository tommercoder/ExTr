package app.extr.ui.theme.composables

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import app.extr.R

sealed class Screens(val route : String) {
    object Home : Screens("home_route")
    object Transactions : Screens("transaction_route")
    object RoundChart : Screens("round_chart_route")
    object Chart : Screens("chart_route")
    object Profile : Screens("profile_route")
}

data class BottomNavItem(
    val route: String,
    val iconId: Int
)

val bottomNavItems = listOf(
    BottomNavItem(
        route = Screens.Home.route,
        iconId = R.drawable.home_icon,
    ),
    BottomNavItem(
        route = Screens.Transactions.route,
        iconId = R.drawable.list_icon,
    ),
    BottomNavItem(
        route = Screens.RoundChart.route,
        iconId = R.drawable.round_chart_icon,
    ),
    BottomNavItem(
        route = Screens.Chart.route,
        iconId = R.drawable.chart_icon,
    ),
    BottomNavItem(
        route = Screens.Profile.route,
        iconId = R.drawable.profile_icon
    ),
)

fun getTitleByRoute(context: Context, route:String): String {
    return when (route) {
        Screens.Home.route -> context.getString(R.string.title_balances)
        Screens.Profile.route -> context.getString(R.string.title_profile)
        else -> ""
    }
}
//add new screens