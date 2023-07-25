package miv.dev.wheelchair.friendly.presentation.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy

fun NavBackStackEntry?.equalRoute(route: String): Boolean =
    this?.destination?.hierarchy?.any { it.route == route } ?: false
