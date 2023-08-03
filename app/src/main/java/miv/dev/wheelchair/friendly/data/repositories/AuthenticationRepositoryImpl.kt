package miv.dev.wheelchair.friendly.data.repositories

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import miv.dev.wheelchair.friendly.data.remote.AuthenticationDataSource
import miv.dev.wheelchair.friendly.data.remote.services.UserDataService
import miv.dev.wheelchair.friendly.domain.entities.AuthState
import miv.dev.wheelchair.friendly.domain.entities.Credentials
import miv.dev.wheelchair.friendly.domain.repositories.AuthenticationRepository
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
	private val authenticationDataSource: AuthenticationDataSource,
	private val userDataService: UserDataService,
) : AuthenticationRepository {
	
	
	private val scope = CoroutineScope(IO)
	
	
	private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
	
	override val authState: StateFlow<AuthState>
		get() = _authState
	
	
	override suspend fun checkAuthState() {
		scope.runCatching {
			userDataService.getCurrentUser()
		}.onSuccess {
			_authState.emit(AuthState.Authorized)
		}.onFailure {
			_authState.emit(AuthState.NonAuthorized)
		}
		
	}
	
	override suspend fun login(credentials: Credentials): Result<Unit> {
//		val result = when (credentials) {
//			is Credentials.EmailAndPassword -> {
//
//			}
//
//			is Credentials.Google -> {
//				// TODO: Implement
////				authenticationDataSource.loginWithCredentials(credentials.credential)
//			}
//		}
		
		if (credentials is Credentials.EmailAndPassword) {
			val result = authenticationDataSource.loginWithEmailAndPassword(credentials)
			authenticationDataSource.setTokenPair(result)
		}
		
		return Result.failure(RuntimeException())
	}
	
	override suspend fun register(credentials: Credentials): Result<Unit> {
//		val result = when (credentials) {
//			is Credentials.EmailAndPassword -> {
//				authenticationDataSource.createWithEmailAndPassword(credentials)
//			}
//
//			is Credentials.Google -> {
//				authenticationDataSource.loginWithCredentials(credentials.credential)
//			}
//		}
//		result.onFailure {
//			_authState.emit(AuthState.NonAuthorized)
//			return Result.failure(it)
//		}.onSuccess {
//			_authState.emit(AuthState.Authorized)
//			return Result.success(Unit)
//		}
//
//		return Result.failure(RuntimeException())
		return Result.failure(Throwable("Not implement"))
	}
	
	override fun logout() {
		scope.launch {
			_authState.emit(AuthState.NonAuthorized)
		}
	}
	
}
