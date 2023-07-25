package miv.dev.wheelchair.friendly.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import miv.dev.wheelchair.friendly.presentation.components.BottomNavBar
import miv.dev.wheelchair.friendly.presentation.navigation.NavigationItem
import miv.dev.wheelchair.friendly.presentation.navigation.equalRoute
import miv.dev.wheelchair.friendly.presentation.navigation.graph.MainNavGraph
import miv.dev.wheelchair.friendly.presentation.navigation.rememberNavigationState
import miv.dev.wheelchair.friendly.presentation.screens.map.MapScreen

@Composable
fun ShallScreen() {
	val navigationState = rememberNavigationState()
	LaunchedEffect(Unit) {
	
	}
	BoxWithConstraints {
		this.let { box ->
			Scaffold(
				containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
				bottomBar = {
					AnimatedVisibility(visible = box.maxWidth < 640.dp) {
						BottomNavBar(navigationState = navigationState)
					}
				}
			) {
				Row(Modifier.padding(it)) {
					AnimatedVisibility(box.maxWidth > 640.dp) {
						
						NavigationRail(containerColor = MaterialTheme.colorScheme.surfaceContainer) {
							val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
							val items = listOf(
								NavigationItem.Home,
								NavigationItem.Map,
								NavigationItem.Settings,
							)
							Spacer(modifier = Modifier.weight(1f))
							items.forEach { item ->
								val selected = navBackStackEntry?.equalRoute(item.screen.route) ?: false
								
								NavigationRailItem(
									selected = selected,
									onClick = { if (!selected) navigationState.navigateTo(item.screen.route) },
									icon = { Icon(item.icon, contentDescription = null) },
									label = { Text(text = stringResource(item.titleResId)) },
								)
							}
							Spacer(modifier = Modifier.weight(1f))
							Box(modifier = Modifier.padding(16.dp)) {
							
							}
						}
					}
					
					MainNavGraph(
						navHostController = navigationState.navHostController,
						mapScreenContent = {
							MapScreen()
						},
						settingsScreenContent = { /*TODO*/ },
						homeScreenContent = {
							/*TODO*/
						})
					
				}
				
				
			}
		}
		
	}
	
}
