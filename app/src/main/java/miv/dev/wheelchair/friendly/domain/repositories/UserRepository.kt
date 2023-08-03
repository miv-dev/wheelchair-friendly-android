package miv.dev.wheelchair.friendly.domain.repositories

import miv.dev.wheelchair.friendly.domain.entities.User
import java.util.UUID

interface UserRepository {
	suspend fun getCurrentUser(): Result<User>
	
	suspend fun getUserById(uuid: UUID): Result<User>
}
