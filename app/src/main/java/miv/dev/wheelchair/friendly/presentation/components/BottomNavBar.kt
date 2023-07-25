package miv.dev.wheelchair.friendly.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import miv.dev.wheelchair.friendly.presentation.navigation.NavigationItem
import miv.dev.wheelchair.friendly.presentation.navigation.NavigationState
import miv.dev.wheelchair.friendly.presentation.navigation.equalRoute

@Composable
fun BottomNavBar(navigationState: NavigationState) {
    NavigationBar {
        val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
        val items = listOf(
            NavigationItem.Home,
            NavigationItem.Map,
            NavigationItem.Settings,
        )
        items.forEach { item ->
            val selected = navBackStackEntry?.equalRoute(item.screen.route) ?: false

            NavigationBarItem(
                selected = selected,
                onClick = { if (!selected) navigationState.navigateTo(item.screen.route) },
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(text = stringResource(item.titleResId)) },
            )
        }


    }
}
