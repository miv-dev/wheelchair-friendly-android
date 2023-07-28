package miv.dev.wheelchair.friendly.domain.usecases

import kotlinx.coroutines.flow.StateFlow
import miv.dev.wheelchair.friendly.domain.entities.AuthState
import miv.dev.wheelchair.friendly.domain.repositories.AuthenticationRepository
import javax.inject.Inject

class GetAuthStateUseCase @Inject constructor(
	private val authenticationRepository: AuthenticationRepository
) {
	operator fun invoke(): StateFlow<AuthState> {
		return authenticationRepository.authState
	}
}
