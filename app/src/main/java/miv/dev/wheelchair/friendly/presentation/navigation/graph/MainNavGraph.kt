package miv.dev.wheelchair.friendly.presentation.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import miv.dev.wheelchair.friendly.presentation.navigation.Screen

@Composable
fun MainNavGraph(
	modifier: Modifier = Modifier,
	navHostController: NavHostController,
	mapScreenContent: @Composable () -> Unit,
	settingsScreenContent: @Composable () -> Unit,
	homeScreenContent: @Composable () -> Unit,
	
	) {
	NavHost(navController = navHostController, startDestination = Screen.Home.route, modifier = modifier) {
		composable(Screen.Map.route) { mapScreenContent() }
		composable(Screen.Settings.route) { settingsScreenContent() }
		composable(Screen.Home.route) { homeScreenContent() }
	}
}
