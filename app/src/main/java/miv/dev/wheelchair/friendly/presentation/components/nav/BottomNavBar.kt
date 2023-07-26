package miv.dev.wheelchair.friendly.presentation.components.nav

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import miv.dev.wheelchair.friendly.presentation.navigation.NavigationItem
import miv.dev.wheelchair.friendly.presentation.navigation.TOP_LEVEL_DESTINATIONS

@Composable
fun AppBottomNavigationBar(
	selectedDestination: String,
	navigateToTopLevelDestination: (NavigationItem) -> Unit

) {
	NavigationBar(modifier = Modifier.fillMaxWidth()) {
		TOP_LEVEL_DESTINATIONS.forEach { item ->
			
			NavigationBarItem(
				selected = selectedDestination == item.screen.route,
				onClick = { navigateToTopLevelDestination(item) },
				icon = { Icon(item.icon, contentDescription = null) },
				label = { Text(text = stringResource(item.titleResId)) },
			)
		}
		
		
	}
}
