package miv.dev.wheelchair.friendly.domain.usecases

import miv.dev.wheelchair.friendly.domain.entities.User
import miv.dev.wheelchair.friendly.domain.repositories.UserRepository
import java.util.*
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
	private val repository: UserRepository
) {
	suspend operator fun invoke(uuid: UUID): Result<User> = repository.getUserById(uuid)
}
