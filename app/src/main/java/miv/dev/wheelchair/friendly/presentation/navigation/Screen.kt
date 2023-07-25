package miv.dev.wheelchair.friendly.presentation.navigation

sealed class Screen(val route: String) {
	
	object Home : Screen(ROUTE_HOME)
	object Main : Screen(ROUTE_MAIN)
	object Places : Screen(ROUTE_PLACES)
	object Settings : Screen(ROUTE_SETTINGS)
	
	
	companion object {
		
		const val ROUTE_MAIN = "main"
		const val ROUTE_HOME = "home"
		const val ROUTE_PLACES = "places"
		const val ROUTE_SETTINGS = "settings"
		
	}
}
