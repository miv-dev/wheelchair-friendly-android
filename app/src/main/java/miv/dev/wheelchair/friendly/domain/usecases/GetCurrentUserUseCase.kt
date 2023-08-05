package miv.dev.wheelchair.friendly.domain.usecases

import miv.dev.wheelchair.friendly.domain.entities.User
import miv.dev.wheelchair.friendly.domain.repositories.UserRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
	private val repository: UserRepository
) {
	suspend operator fun invoke(): Result<User> = repository.getCurrentUser()
}
