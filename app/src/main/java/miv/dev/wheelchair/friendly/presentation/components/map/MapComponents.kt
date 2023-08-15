package miv.dev.wheelchair.friendly.presentation.components.map

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapType
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.map.TextStyle
import com.yandex.mapkit.mapview.MapView
import miv.dev.wheelchair.friendly.domain.entities.Place

@Composable
fun Map(
	modifier: Modifier = Modifier,
	places: List<Place> = emptyList(),
	selectedPlace:Place? = null,
) {
	val mapView = rememberMapViewWithLifecycle()
	val mapObjects = remember {
		mutableStateListOf<PlacemarkMapObject>()
	}
	
	LaunchedEffect(selectedPlace){
		selectedPlace?.let {
			mapView.map.move(
				CameraPosition(
					Point(selectedPlace.coordinates.latitude, selectedPlace.coordinates.longitude),
					/* zoom = */ 17.0f,
					/* azimuth = */ 150.0f,
					/* tilt = */0f
				)
			)
			mapView.onStart()
		}
	}
	
	LaunchedEffect(places) {
		places.forEach {
			mapView.map.mapObjects.addPlacemark(Point(it.coordinates.latitude, it.coordinates.longitude)).apply {
				setText(
					it.name,
					TextStyle().apply {
						size = 10f
						placement = TextStyle.Placement.RIGHT
						offset = 5f
						
					},
				)
			}
		}
		
		
		mapView.onStart()
		
	}
	
	AndroidView(
		modifier = modifier,
		factory = {
			MapKitFactory.initialize(it)
			MapKitFactory.getInstance().onStart()
			
			mapView
		},
		onRelease = {
			
			mapView.map.addTapListener {
				println(it.geoObject.descriptionText)
				true
			}
			mapView.onStart()
		}
	)
}


@Composable
fun rememberMapViewWithLifecycle(): MapView {
	val context = LocalContext.current
	val mapView = remember {
		
		MapView(context)
	}
	
	
	val lifecycleObserver = rememberMapLifecycleObserver(mapView = mapView)
	val lifecycle = LocalLifecycleOwner.current.lifecycle
	
	DisposableEffect(lifecycle) {
		lifecycle.addObserver(lifecycleObserver)
		onDispose {
			lifecycle.removeObserver(lifecycleObserver)
		}
	}
	
	return mapView
}

@Composable
fun rememberMapLifecycleObserver(mapView: MapView): LifecycleEventObserver =
	remember(mapView) {
		LifecycleEventObserver { _, event ->
			when (event) {
				Lifecycle.Event.ON_START -> mapView.onStart()
				Lifecycle.Event.ON_STOP -> mapView.onStop()
				Lifecycle.Event.ON_CREATE -> {
					
					mapView.map.mapType = MapType.VECTOR_MAP
					
					
				}
				
				Lifecycle.Event.ON_RESUME -> {
				
				}
				
				Lifecycle.Event.ON_PAUSE -> {
				
				}
				
				Lifecycle.Event.ON_DESTROY -> {}
				Lifecycle.Event.ON_ANY -> {
				
				}
			}
		}
	}
