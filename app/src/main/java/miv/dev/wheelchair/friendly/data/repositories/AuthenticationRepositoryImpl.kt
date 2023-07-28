package miv.dev.wheelchair.friendly.data.repositories

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import miv.dev.wheelchair.friendly.data.remote.AuthenticationDataSource
import miv.dev.wheelchair.friendly.domain.entities.AuthState
import miv.dev.wheelchair.friendly.domain.entities.Credentials
import miv.dev.wheelchair.friendly.domain.repositories.AuthenticationRepository
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
	private val authenticationDataSource: AuthenticationDataSource
) : AuthenticationRepository {
	
	
	private val scope = CoroutineScope(IO)
	
	
	private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
	
	override val authState: StateFlow<AuthState>
		get() = _authState
	
	
	override suspend fun checkAuthState() {
		val state = if (authenticationDataSource.isLogged()) {
			AuthState.Authorized
		} else {
			AuthState.NonAuthorized
		}
		_authState.emit(state)
	}
	
	override suspend fun login(credentials: Credentials): Result<Unit> {
		val result = when (credentials) {
			is Credentials.EmailAndPassword -> {
				authenticationDataSource.loginWithEmailAndPassword(credentials)
			}
			
			is Credentials.Google -> {
				authenticationDataSource.loginWithCredentials(credentials.credential)
			}
		}
		result.onFailure {
			_authState.emit(AuthState.NonAuthorized)
			return Result.failure(it)
		}.onSuccess {
			_authState.emit(AuthState.Authorized)
			return Result.success(Unit)
		}
		
		return Result.failure(RuntimeException())
	}
	
	override fun logout() {
		authenticationDataSource.logout()
		scope.launch {
			_authState.emit(AuthState.NonAuthorized)
		}
	}
	
}
