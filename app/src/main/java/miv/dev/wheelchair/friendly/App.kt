package miv.dev.wheelchair.friendly

import android.app.Application
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.yandex.mapkit.MapKitFactory
import miv.dev.wheelchair.friendly.di.ApplicationComponent
import miv.dev.wheelchair.friendly.di.DaggerApplicationComponent

class App : Application() {
	val component: ApplicationComponent by lazy {
		DaggerApplicationComponent.factory().create(
			this
		)
		
	}
	
	init {
		Log.d(TAG, "Set Places Api Key ")
		MapKitFactory.setApiKey(MAPKIT_API_KEY);
	}
	
	companion object {
		const val TAG = "App"
		const val MAPKIT_API_KEY = "993162c2-cefd-4075-b062-febcced606da"
	}
}

@Composable
fun getApplicationComponent(): ApplicationComponent {
	return (LocalContext.current.applicationContext as App).component
}
