package miv.dev.wheelchair.friendly.presentation.components.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.MapType
import com.yandex.mapkit.mapview.MapView

@Composable
fun Map(
	modifier: Modifier = Modifier,
) {
	val mapView = rememberMapViewWithLifecycle()
	
	AndroidView(
		modifier = modifier,
		factory = { mapView },
		onRelease = {
			it.onStart()
			it.map.mapType = MapType.VECTOR_MAP
			it.map.mapObjects.addPlacemark(Point(55.751225, 37.629540))
		}
	)
}


@Composable
fun rememberMapViewWithLifecycle(): MapView {
	val context = LocalContext.current
	val mapView = remember {
		MapKitFactory.initialize(context)
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
				Lifecycle.Event.ON_CREATE -> mapView.onStart()
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
