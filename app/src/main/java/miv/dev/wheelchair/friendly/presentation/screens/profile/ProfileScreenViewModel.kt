package miv.dev.wheelchair.friendly.presentation.screens.profile

import androidx.lifecycle.ViewModel
import miv.dev.wheelchair.friendly.domain.usecases.LogoutUseCase
import javax.inject.Inject

class ProfileScreenViewModel @Inject constructor(
	private val logoutUseCase: LogoutUseCase
) : ViewModel() {
	
	fun logout() {
		logoutUseCase()
	}
	
}
