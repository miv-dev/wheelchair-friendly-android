package miv.dev.wheelchair.friendly.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector
import miv.dev.wheelchair.friendly.R

sealed class NavigationItem(
    val screen: Screen,
    val titleResId: Int,
    val icon: ImageVector,
){
    object Home: NavigationItem(
        screen = Screen.Home,
        titleResId = R.string.navigation_item_home,
        icon = Icons.Rounded.Home
    )

    object Map: NavigationItem(
        screen = Screen.Map,
        titleResId = R.string.navigation_item_map,
        icon = Icons.Rounded.PinDrop
    )

    object Settings: NavigationItem(
        screen = Screen.Settings,
        titleResId = R.string.navigation_item_settings,
        icon = Icons.Rounded.Settings
    )
    
}
