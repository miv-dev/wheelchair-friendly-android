package miv.dev.wheelchair.friendly.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import miv.dev.wheelchair.friendly.presentation.navigation.NavigationItem
import miv.dev.wheelchair.friendly.presentation.navigation.TOP_LEVEL_DESTINATIONS
import miv.dev.wheelchair.friendly.utils.AppContentPosition

@Composable
fun AppNavigationRail(
	selectedDestination: String,
	navigationContentPosition: AppContentPosition,
	navigateToTopLevelDestination: (NavigationItem) -> Unit,
	) {
	NavigationRail(containerColor = MaterialTheme.colorScheme.inverseOnSurface) {
		Column(
			modifier = Modifier.layoutId(LayoutType.CONTENT),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.spacedBy(4.dp)
		) {
			TOP_LEVEL_DESTINATIONS.forEach { item ->
				
				NavigationRailItem(
					selected = selectedDestination == item.screen.route,
					onClick = { navigateToTopLevelDestination(item) },
					icon = { Icon(item.icon, contentDescription = null) },
					label = { Text(text = stringResource(item.titleResId)) },
				)
			}
		}
	}
}


fun navigationMeasurePolicy(
	navigationContentPosition: AppContentPosition,
): MeasurePolicy {
	return MeasurePolicy { measurables, constraints ->
		lateinit var headerMeasurable: Measurable
		lateinit var contentMeasurable: Measurable
		measurables.forEach {
			when (it.layoutId) {
				LayoutType.HEADER -> headerMeasurable = it
				LayoutType.CONTENT -> contentMeasurable = it
				else -> error("Unknown layoutId encountered!")
			}
		}
		
		val headerPlaceable = headerMeasurable.measure(constraints)
		val contentPlaceable = contentMeasurable.measure(
			constraints.offset(vertical = -headerPlaceable.height)
		)
		layout(constraints.maxWidth, constraints.maxHeight) {
			// Place the header, this goes at the top
			headerPlaceable.placeRelative(0, 0)
			
			// Determine how much space is not taken up by the content
			val nonContentVerticalSpace = constraints.maxHeight - contentPlaceable.height
			
			val contentPlaceableY = when (navigationContentPosition) {
				// Figure out the place we want to place the content, with respect to the
				// parent (ignoring the header for now)
				AppContentPosition.TOP -> 0
				AppContentPosition.CENTER -> nonContentVerticalSpace / 2
			}
				// And finally, make sure we don't overlap with the header.
				.coerceAtLeast(headerPlaceable.height)
			
			contentPlaceable.placeRelative(0, contentPlaceableY)
		}
	}
}

enum class LayoutType {
	HEADER, CONTENT
}
