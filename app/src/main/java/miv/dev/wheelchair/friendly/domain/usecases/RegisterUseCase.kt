package miv.dev.wheelchair.friendly.domain.usecases

import miv.dev.wheelchair.friendly.domain.entities.Credentials
import miv.dev.wheelchair.friendly.domain.repositories.AuthenticationRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
	private val authenticationRepository: AuthenticationRepository
) {
	suspend operator fun invoke(credentials: Credentials): Result<Unit> {
		return authenticationRepository.register(credentials)
	}
}
