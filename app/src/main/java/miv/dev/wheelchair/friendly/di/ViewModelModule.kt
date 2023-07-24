package miv.dev.wheelchair.friendly.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import miv.dev.wheelchair.friendly.presentation.MainViewModel

@Module
interface ViewModelModule {
	@IntoMap
	@ViewModelKey(MainViewModel::class)
	@Binds
	fun bindMainViewModel(
		viewModel: MainViewModel
	): ViewModel
	
}
