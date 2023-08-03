package miv.dev.wheelchair.friendly.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import miv.dev.wheelchair.friendly.domain.usecases.GetCurrentUserUseCase
import miv.dev.wheelchair.friendly.domain.usecases.LogoutUseCase
import javax.inject.Inject

class ProfileScreenViewModel @Inject constructor(
	private val logoutUseCase: LogoutUseCase,
	private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {
	
	private val scope = viewModelScope
	
	private val _screenState = MutableSharedFlow<ProfileScreenState>()
	val screenState: SharedFlow<ProfileScreenState>
		get() = _screenState
	
	fun fetchUser(){
		scope.launch {
			_screenState.emit(ProfileScreenState.Loading)
			val result = getCurrentUserUseCase()
			result.fold(
				{
					_screenState.emit(ProfileScreenState.Profile(it))
				},
				{
					_screenState.emit(ProfileScreenState.Error(it.localizedMessage ?: "Unhandled Error"))
				}
			)
		}
	}
	fun logout() {
		logoutUseCase()
	}
	
}
