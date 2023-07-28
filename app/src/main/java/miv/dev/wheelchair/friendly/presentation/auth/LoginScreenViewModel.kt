package miv.dev.wheelchair.friendly.presentation.auth

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import miv.dev.wheelchair.friendly.R
import miv.dev.wheelchair.friendly.domain.entities.Credentials
import miv.dev.wheelchair.friendly.domain.usecases.LoginUseCase
import javax.inject.Inject


class LoginScreenViewModel @Inject constructor(
	private val loginUseCase: LoginUseCase
) : ViewModel() {
	
	
	private val scope = viewModelScope
	
	private val _screenState = MutableStateFlow(LoginScreenUiState())
	val screenState: StateFlow<LoginScreenUiState>
		get() = _screenState
	
	fun updateEmail(email: String) {
		_screenState.value = _screenState.value.copy(email = email)
	}
	
	fun updatePassword(password: String) {
		_screenState.value = _screenState.value.copy(password = password)
	}
	
	fun loginWithEmailAndPassword() {
		val credentials = Credentials.EmailAndPassword(
			email = _screenState.value.email,
			password = _screenState.value.password
		)
		scope.launch {
			loginUseCase(credentials)
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
	
	companion object{
		
		const val UNHANDLED_ERROR = "Unhandled Error"
	}
}
