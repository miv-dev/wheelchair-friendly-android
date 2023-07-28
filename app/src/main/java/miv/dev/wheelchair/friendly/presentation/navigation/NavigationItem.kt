package miv.dev.wheelchair.friendly.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Place
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
        icon = Icons.Outlined.Home
    )

    object Places: NavigationItem(
        screen = Screen.Places,
        titleResId = R.string.navigation_item_places,
        icon = Icons.Outlined.Place
    )

    object Profile: NavigationItem(
        screen = Screen.Profile,
        titleResId = R.string.navigation_item_profile,
        icon = Icons.Outlined.AccountCircle
    )
    
}

val TOP_LEVEL_DESTINATIONS = listOf(
    NavigationItem.Home,
    NavigationItem.Places,
    NavigationItem.Profile,
)
