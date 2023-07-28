package miv.dev.wheelchair.friendly.domain.usecases

import miv.dev.wheelchair.friendly.domain.repositories.AuthenticationRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
	private val repository: AuthenticationRepository
) {
	operator fun invoke() {
		repository.logout()
	}
	
}
