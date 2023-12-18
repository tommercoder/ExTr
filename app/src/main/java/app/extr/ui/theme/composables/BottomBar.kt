package app.extr.ui.theme.viewmodels

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import app.extr.ui.theme.composables.bottomNavItems
import app.extr.utils.helpers.navigateSingleTopTo

@Composable
fun BottomBar(navController: NavHostController) {
    NavigationBar(
        //containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp) // fix color to match back
    ) {
        val navBackStackEntry =
            navController.currentBackStackEntryAsState().value?.destination?.route

        bottomNavItems.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = null
                    )
                },
                onClick = { navController.navigateSingleTopTo(screen.route) },
                selected = screen.route == navBackStackEntry, // handle parents later(so when some screen from screens open, what should happen?
                modifier = Modifier.background(Color.Transparent)
            )
        }
    }
}