package miv.dev.wheelchair.friendly.presentation.navigation

sealed class Screen(val route: String) {
	
	object Home : Screen(ROUTE_HOME)
	object Main : Screen(ROUTE_MAIN)
	object Places : Screen(ROUTE_PLACES)
	object Profile : Screen(ROUTE_PROFILE)
	object Settings : Screen(ROUTE_SETTINGS)
	
	object Login : Screen(ROUTE_LOGIN)
	object Register : Screen(ROUTE_REGISTER)
	object Welcome : Screen(ROUTE_WELCOME)
	
	object AddPlace: Screen(ROUTE_ADD_PLACE)
	
	companion object {
		
		
		const val ROUTE_MAIN = "main"
		const val ROUTE_ADD_PLACE = "add_place"
		const val ROUTE_PROFILE = "profile"
		const val ROUTE_LOGIN = "login"
		const val ROUTE_REGISTER = "register"
		const val ROUTE_WELCOME = "welcome"
		const val ROUTE_HOME = "home"
		const val ROUTE_PLACES = "places"
		const val ROUTE_SETTINGS = "settings"
		
	}
}
