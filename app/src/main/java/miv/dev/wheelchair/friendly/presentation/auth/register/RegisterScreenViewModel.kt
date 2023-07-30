package miv.dev.wheelchair.friendly.presentation.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import miv.dev.wheelchair.friendly.domain.entities.Credentials
import miv.dev.wheelchair.friendly.domain.usecases.LoginUseCase
import miv.dev.wheelchair.friendly.domain.usecases.RegisterUseCase
import javax.inject.Inject


class RegisterScreenViewModel @Inject constructor(
	private val registerUseCase: RegisterUseCase
) : ViewModel() {
	
	
	private val scope = viewModelScope
	
	private val _screenState = MutableStateFlow(RegisterScreenState())
	val screenState: StateFlow<RegisterScreenState>
		get() = _screenState
	
	fun updateEmail(email: String) {
		_screenState.value = _screenState.value.copy(email = email)
	}
	
	fun updatePassword(password: String) {
		_screenState.value = _screenState.value.copy(password = password)
	}
	
	fun registerWithEmailAndPassword() {
		val credentials = Credentials.EmailAndPassword(
			email = _screenState.value.email,
			password = _screenState.value.password
		)
		_screenState.value = _screenState.value.copy(
			isLoading = true,
			loginIsSuccess = null,
			
		)
		scope.launch {
			registerUseCase(credentials)
				.fold(
					onSuccess = {
						_screenState.value = _screenState.value.copy(
							isLoading = false,
							loginIsSuccess = true,
						)
					},
					onFailure = {
						_screenState.value = _screenState.value.copy(
							isLoading = false,
							loginIsSuccess = false,
							loginError = it.localizedMessage ?: UNHANDLED_ERROR
						)
					}
				)
		}
	}
	
	fun clearState() {
		_screenState.value = RegisterScreenState()
	}
	
	companion object{
		
		const val UNHANDLED_ERROR = "Unhandled Error"
	}
}
