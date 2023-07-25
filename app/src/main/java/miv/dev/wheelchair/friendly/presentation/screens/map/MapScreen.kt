package miv.dev.wheelchair.friendly.presentation.screens.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import miv.dev.wheelchair.friendly.presentation.components.AppDockedSearchBar
import miv.dev.wheelchair.friendly.utils.AppContentType
import miv.dev.wheelchair.friendly.utils.AppNavigationType

@Composable
fun MapScreen(contentType: AppContentType, navigationType: AppNavigationType, displayFeatures: List<DisplayFeature>) {
	
	val placeLazyListState = rememberLazyListState()
	
	
	if (contentType == AppContentType.DUAL_PANE) {
		TwoPane(
			first = {
				Column() {
					AppDockedSearchBar(
						modifier = Modifier
							.fillMaxWidth()
							.padding(16.dp)
					)
					PlaceList(places = listOf(), placeLazyListState = placeLazyListState, navigateToDetail = { _, _ -> })
					
				}
			},
			second = {
				MapContainer()
			},
			strategy = HorizontalTwoPaneStrategy(0.5f, gapWidth = 16.dp),
			displayFeatures = displayFeatures
		)
	} else {
		SinglePaneContent(places = listOf(), placeLazyListState = placeLazyListState, navigateToDetail = { _, _ -> })
	}
	
}


@OptIn(ExperimentalMaterial3Api::class)
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
		MapContainer()
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

@Composable
fun MapContainer() {
	AndroidView(
		modifier = Modifier
			.padding(16.dp)
			.clip(MaterialTheme.shapes.medium),
		factory = { context ->
			MapKitFactory.initialize(context.applicationContext)
			val view = MapView(context)
			view.map.move(
				CameraPosition(Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
				Animation(Animation.Type.SMOOTH, 0F),
				null
			)
			return@AndroidView view
		},
		onRelease = {
			it.onStart()
		}
	)
}
