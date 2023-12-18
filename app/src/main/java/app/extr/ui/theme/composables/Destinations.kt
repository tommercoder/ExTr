package app.extr.ui.theme.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import app.extr.R

sealed class Screens(val route : String) {
    object Home : Screens("home_route")
    object RoundChart : Screens("round_chart_route")
    object Chart : Screens("chart_route")
    object Profile : Screens("profile_route")
}

data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
)

val bottomNavItems = listOf(
    BottomNavItem(
        route = Screens.Home.route,
        icon = Icons.Filled.Home,
    ),
    BottomNavItem(
        route = Screens.RoundChart.route,
        icon = Icons.Filled.AccountBox,
    ),
    BottomNavItem(
        route = Screens.Chart.route,
        icon = Icons.Filled.List,
    ),
    BottomNavItem(
        route = Screens.Profile.route,
        icon = Icons.Filled.Call,
    ),
)

//add new screens