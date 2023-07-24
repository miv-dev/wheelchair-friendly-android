package miv.dev.wheelchair.friendly.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import miv.dev.wheelchair.friendly.presentation.ViewModelFactory

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun getViewModelFactory(): ViewModelFactory


    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

}
