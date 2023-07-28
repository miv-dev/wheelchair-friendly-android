package miv.dev.wheelchair.friendly.presentation.screens.places

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import miv.dev.wheelchair.friendly.presentation.components.map.Map
import miv.dev.wheelchair.friendly.presentation.components.nav.AppDockedSearchBar
import miv.dev.wheelchair.friendly.utils.AppContentType
import miv.dev.wheelchair.friendly.utils.AppNavigationType

@Composable
fun PlacesScreen(contentType: AppContentType, navigationType: AppNavigationType, displayFeatures: List<DisplayFeature>) {
	
	val placeLazyListState = rememberLazyListState()
	
	
	if (contentType == AppContentType.DUAL_PANE) {
		TwoPane(
			first = {
				Column {
					AppDockedSearchBar(
						modifier = Modifier
							.fillMaxWidth()
							.padding(16.dp)
					)
					PlaceList(places = listOf(), placeLazyListState = placeLazyListState, navigateToDetail = { _, _ -> })
					
				}
			},
			second = {
				Map(
					modifier = Modifier
						.padding(16.dp)
						.clip(MaterialTheme.shapes.medium),
				)
			},
			strategy = HorizontalTwoPaneStrategy(0.5f, gapWidth = 16.dp),
			displayFeatures = displayFeatures
		)
	} else {
		SinglePaneContent(places = listOf(), placeLazyListState = placeLazyListState, navigateToDetail = { _, _ -> })
	}
	
}


@Composable
fun SinglePaneContent(
	places: List<String>,
	modifier: Modifier = Modifier,
	placeLazyListState: LazyListState,
	navigateToDetail: (String, AppContentType) -> Unit
) {
	
	Column {
		AppDockedSearchBar(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp)
		)
		Map(
			modifier = Modifier
				.padding(16.dp)
				.clip(MaterialTheme.shapes.medium),
		)
		PlaceList(
			places = places,
			placeLazyListState = placeLazyListState,
			navigateToDetail = navigateToDetail
		)
		
	}
}

@Composable
fun PlaceList(
	places: List<String>,
	modifier: Modifier = Modifier,
	placeLazyListState: LazyListState,
	navigateToDetail: (String, AppContentType) -> Unit
) {
	Box(modifier = modifier) {
		LazyColumn(
			modifier = Modifier
				.fillMaxWidth()
				.padding(top = 80.dp),
			state = placeLazyListState
		) {
			items(places) {
				Text(text = it)
			}
		}
	}
}
