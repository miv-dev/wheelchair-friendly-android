package miv.dev.wheelchair.friendly.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import miv.dev.wheelchair.friendly.presentation.navigation.Screen

@Composable
fun AuthNavGraph(
	navHostController: NavHostController,
	loginScreenContent: @Composable () -> Unit,
	registerScreenContent: @Composable () -> Unit,
	welcomeScreenContent: @Composable () -> Unit
) {
	NavHost(navController = navHostController, startDestination = Screen.Welcome.route) {
		composable(Screen.Welcome.route) { welcomeScreenContent() }
		composable(Screen.Login.route) { loginScreenContent() }
		composable(Screen.Register.route) { registerScreenContent() }
	}
}
