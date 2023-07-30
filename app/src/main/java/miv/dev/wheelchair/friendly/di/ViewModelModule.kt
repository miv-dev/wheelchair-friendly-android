package miv.dev.wheelchair.friendly.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import miv.dev.wheelchair.friendly.presentation.AppViewModel
import miv.dev.wheelchair.friendly.presentation.auth.login.LoginScreenViewModel
import miv.dev.wheelchair.friendly.presentation.auth.register.RegisterScreenViewModel
import miv.dev.wheelchair.friendly.presentation.screens.profile.ProfileScreenViewModel

@Module
interface ViewModelModule {
	@IntoMap
	@ViewModelKey(AppViewModel::class)
	@Binds
	fun bindAppViewModel(
		viewModel: AppViewModel
	): ViewModel
	
	@IntoMap
	@ViewModelKey(LoginScreenViewModel::class)
	@Binds
	fun bindLoginScreenViewModel(
		viewModel: LoginScreenViewModel
	): ViewModel
	
	@IntoMap
	@ViewModelKey(ProfileScreenViewModel::class)
	@Binds
	fun bindProfileScreenViewModel(
		viewModel: ProfileScreenViewModel
	): ViewModel
	@IntoMap
	@ViewModelKey(RegisterScreenViewModel::class)
	@Binds
	fun bindRegisterScreenViewModel(
		viewModel: RegisterScreenViewModel
	): ViewModel
	
}
