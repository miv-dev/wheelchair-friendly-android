package miv.dev.wheelchair.friendly

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import miv.dev.wheelchair.friendly.di.ApplicationComponent
import miv.dev.wheelchair.friendly.di.DaggerApplicationComponent

class App : Application() {
    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(
            this
        )

    }
}

@Composable
fun getApplicationComponent(): ApplicationComponent {
    return (LocalContext.current.applicationContext as App).component
}
