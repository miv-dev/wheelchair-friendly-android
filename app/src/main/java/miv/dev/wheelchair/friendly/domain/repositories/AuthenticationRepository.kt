package miv.dev.wheelchair.friendly.domain.repositories

import kotlinx.coroutines.flow.StateFlow
import miv.dev.wheelchair.friendly.domain.entities.AuthState
import miv.dev.wheelchair.friendly.domain.entities.Credentials

interface AuthenticationRepository {
	
	val authState: StateFlow<AuthState>
	
	suspend fun login(credentials: Credentials): Result<Unit>
	
	fun logout()
	suspend fun checkAuthState()
}


