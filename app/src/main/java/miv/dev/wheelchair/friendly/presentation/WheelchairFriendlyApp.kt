package miv.dev.wheelchair.friendly.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.window.layout.DisplayFeature
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import miv.dev.wheelchair.friendly.domain.entities.AuthState
import miv.dev.wheelchair.friendly.getApplicationComponent
import miv.dev.wheelchair.friendly.presentation.auth.Keyboard
import miv.dev.wheelchair.friendly.presentation.auth.LoginScreen
import miv.dev.wheelchair.friendly.presentation.auth.keyboardAsState
import miv.dev.wheelchair.friendly.presentation.components.nav.AppBottomNavigationBar
import miv.dev.wheelchair.friendly.presentation.components.nav.AppNavigationRail
import miv.dev.wheelchair.friendly.presentation.navigation.NavigationItem
import miv.dev.wheelchair.friendly.presentation.navigation.Screen
import miv.dev.wheelchair.friendly.presentation.navigation.graph.MainNavGraph
import miv.dev.wheelchair.friendly.presentation.navigation.rememberNavigationState
import miv.dev.wheelchair.friendly.presentation.screens.places.PlacesScreen
import miv.dev.wheelchair.friendly.presentation.screens.profile.ProfileScreen
import miv.dev.wheelchair.friendly.utils.AppContentPosition
import miv.dev.wheelchair.friendly.utils.AppContentType
import miv.dev.wheelchair.friendly.utils.AppNavigationType

@Composable
fun WheelchairWrapper(
	navigationType: AppNavigationType,
	contentType: AppContentType,
	displayFeatures: List<DisplayFeature>,
	navigationContentPosition: AppContentPosition,
	
	) {
	val navigationState = rememberNavigationState()
	val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
	val selectedDestination = navBackStackEntry?.destination?.route ?: Screen.Home.route
	
	WheelchairAppContent(
		navigationType = navigationType,
		contentType = contentType,
		displayFeatures = displayFeatures,
		navHostController = navigationState.navHostController,
		navigateToTopLevelDestination = navigationState::navigateToTopLevelDestination,
		selectedDestination = selectedDestination,
		navigationContentPosition = navigationContentPosition
	)
}

@Composable
fun WheelchairAppContent(
	modifier: Modifier = Modifier,
	navHostController: NavHostController,
	navigationType: AppNavigationType,
	selectedDestination: String,
	contentType: AppContentType,
	displayFeatures: List<DisplayFeature>,
	navigateToTopLevelDestination: (NavigationItem) -> Unit,
	navigationContentPosition: AppContentPosition,
) {
	
	val component = getApplicationComponent()
	val vm: AppViewModel = viewModel(factory = component.getViewModelFactory())
	val authState = vm.authState.collectAsState(AuthState.Initial)
	val keyboardState by keyboardAsState()
	val theme = MaterialTheme.colorScheme
	
	
	val systemUiController = rememberSystemUiController()
	LaunchedEffect(Unit) {
		systemUiController.setStatusBarColor(
			color = theme.background
		)
		systemUiController.setNavigationBarColor(
			color = theme.background
		)
	}
	
	when (authState.value) {
		AuthState.Initial -> {
		
		}
		
		AuthState.Authorized -> {
			Row(modifier = modifier.fillMaxSize()) {
				AnimatedVisibility(visible = navigationType == AppNavigationType.NAVIGATION_RAIL) {
					AppNavigationRail(
						selectedDestination = selectedDestination,
						navigationContentPosition = navigationContentPosition,
						navigateToTopLevelDestination = navigateToTopLevelDestination
					)
				}
				Column(
					modifier = Modifier
						.fillMaxSize()
						.background(MaterialTheme.colorScheme.inverseOnSurface)
				) {
					MainNavGraph(
						modifier = Modifier.weight(1f),
						navHostController = navHostController,
						mapScreenContent = {
							PlacesScreen(
								contentType = contentType,
								navigationType = navigationType,
								displayFeatures = displayFeatures
							)
						},
						profileScreenContent = {
							ProfileScreen()
						},
						homeScreenContent = {
						
						},
					)
					AnimatedVisibility(visible = navigationType == AppNavigationType.BOTTOM_NAVIGATION && keyboardState == Keyboard.Closed) {
						AppBottomNavigationBar(
							selectedDestination = selectedDestination,
							navigateToTopLevelDestination = navigateToTopLevelDestination
						)
					}
				}
			}
		}
		
		AuthState.NonAuthorized -> {
			LoginScreen(
				contentType = contentType,
				navigationType = navigationType,
				displayFeatures = displayFeatures
			)
		}
	}
	
	
}
