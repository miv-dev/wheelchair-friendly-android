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
	profileScreenContent: @Composable () -> Unit,
	homeScreenContent: @Composable () -> Unit,
	addPlaceScreenContent: @Composable () -> Unit
) {
	NavHost(navController = navHostController, startDestination = Screen.Home.route, modifier = modifier) {
		composable(Screen.Places.route) { mapScreenContent() }
		composable(Screen.AddPlace.route) { addPlaceScreenContent() }
		composable(Screen.Profile.route) { profileScreenContent() }
		composable(Screen.Home.route) { homeScreenContent() }
	}
}
