package miv.dev.wheelchair.friendly.domain.usecases

import miv.dev.wheelchair.friendly.domain.entities.User
import miv.dev.wheelchair.friendly.domain.repositories.UserRepository

class GetCurrentUserUseCase(
	private val repository: UserRepository
) {
	suspend operator fun invoke(): Result<User> = repository.getCurrentUser()
}
