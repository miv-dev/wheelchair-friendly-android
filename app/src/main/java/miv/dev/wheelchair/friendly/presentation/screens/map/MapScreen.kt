package miv.dev.wheelchair.friendly.presentation.screens.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView

@Composable
fun MapScreen() {
	MapScreenContent()
}

@Composable
fun MapScreenContent() {
	Scaffold(
	
	
	) {
		Column(Modifier.padding(it)) {
			MapContainer()
		}
	}
}

@Composable
fun MapContainer() {
	AndroidView(
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
