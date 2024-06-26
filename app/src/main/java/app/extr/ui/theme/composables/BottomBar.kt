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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import app.extr.ui.theme.composables.bottomNavItems
import app.extr.utils.helpers.navigateSingleTopTo

@Composable
fun BottomBar(navController: NavHostController) {
    NavigationBar {
        val navBackStackEntry =
            navController.currentBackStackEntryAsState().value?.destination?.route

        bottomNavItems.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = screen.iconId),
                        contentDescription = null
                    )
                },
                onClick = { navController.navigateSingleTopTo(screen.route) },
                selected = screen.route == navBackStackEntry,
                modifier = Modifier.background(Color.Transparent)
            )
        }
    }
}