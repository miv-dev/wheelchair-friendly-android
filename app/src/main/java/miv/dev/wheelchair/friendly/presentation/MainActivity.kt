package miv.dev.wheelchair.friendly.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.yandex.mapkit.MapKitFactory
import miv.dev.wheelchair.friendly.ui.theme.WheelchairFriendlyTheme
import miv.dev.wheelchair.friendly.utils.*

class MainActivity : ComponentActivity() {
	@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		setContent {
			WheelchairFriendlyTheme {
				val windowSize = calculateWindowSizeClass(this)
				val displayFeatures = calculateDisplayFeatures(this)
				WheelchairFriendlyApp(windowSize = windowSize, displayFeatures = displayFeatures)
			}
		}
	}
	
	
	
}

@Composable
fun WheelchairFriendlyApp(windowSize: WindowSizeClass, displayFeatures: List<DisplayFeature>) {
	val navigationType: AppNavigationType
	val contentType: AppContentType
	
	val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()
	
	val foldingDevicePosture = when {
		isBookPosture(foldingFeature) ->
			DevicePosture.BookPosture(foldingFeature.bounds)
		
		isSeparating(foldingFeature) ->
			DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)
		
		else -> DevicePosture.NormalPosture
	}
	
	when (windowSize.widthSizeClass) {
		WindowWidthSizeClass.Compact -> {
			navigationType = if (foldingDevicePosture is DevicePosture.BookPosture) {
				AppNavigationType.NAVIGATION_RAIL
			} else {
				AppNavigationType.BOTTOM_NAVIGATION
			}
			contentType = AppContentType.SINGLE_PANE
		}
		
		WindowWidthSizeClass.Medium -> {
			navigationType = AppNavigationType.NAVIGATION_RAIL
			contentType = if (foldingDevicePosture != DevicePosture.NormalPosture) {
				AppContentType.DUAL_PANE
			} else {
				AppContentType.SINGLE_PANE
			}
		}
		
		WindowWidthSizeClass.Expanded -> {
			navigationType = AppNavigationType.NAVIGATION_RAIL
			contentType = AppContentType.DUAL_PANE
		}
		
		else -> {
			navigationType = AppNavigationType.BOTTOM_NAVIGATION
			contentType = AppContentType.SINGLE_PANE
		}
	}
	
	val navigationContentPosition = when (windowSize.heightSizeClass) {
		WindowHeightSizeClass.Compact -> {
			AppContentPosition.TOP
		}
		
		WindowHeightSizeClass.Medium,
		WindowHeightSizeClass.Expanded -> {
			AppContentPosition.CENTER
		}
		
		else -> {
			AppContentPosition.TOP
		}
	}
	WheelchairWrapper(
		navigationType = navigationType,
		contentType = contentType,
		displayFeatures = displayFeatures,
		navigationContentPosition = navigationContentPosition
	)
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true)
@Composable
fun WheelchairFriendlyAppPreview() {
	WheelchairFriendlyTheme {
		WheelchairFriendlyApp(
			windowSize = WindowSizeClass.calculateFromSize(DpSize(400.dp, 900.dp)),
			emptyList()
		
		)
	}
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 700, heightDp = 500)
@Composable
fun WheelchairFriendlyAppPreviewTablet() {
	WheelchairFriendlyTheme {
		WheelchairFriendlyApp(
			windowSize = WindowSizeClass.calculateFromSize(DpSize(700.dp, 500.dp)),
			emptyList()
		
		)
	}
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 500, heightDp = 700)
@Composable
fun WheelchairFriendlyAppPreviewTabletPortrait() {
	WheelchairFriendlyTheme {
		WheelchairFriendlyApp(
			windowSize = WindowSizeClass.calculateFromSize(DpSize(500.dp, 700.dp)),
			emptyList()
		)
	}
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 1100, heightDp = 600)
@Composable
fun WheelchairFriendlyAppPreviewDesktop() {
	WheelchairFriendlyTheme {
		WheelchairFriendlyApp(
			windowSize = WindowSizeClass.calculateFromSize(DpSize(1100.dp, 600.dp)),
			emptyList()
		
		)
	}
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, widthDp = 600, heightDp = 1100)
@Composable
fun WheelchairFriendlyAppPreviewDesktopPortrait() {
	WheelchairFriendlyTheme {
		WheelchairFriendlyApp(
			windowSize = WindowSizeClass.calculateFromSize(DpSize(600.dp, 1100.dp)),
			emptyList()
		
		)
	}
}
