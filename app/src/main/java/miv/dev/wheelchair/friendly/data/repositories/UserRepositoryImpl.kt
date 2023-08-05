package miv.dev.wheelchair.friendly.data.repositories

import miv.dev.wheelchair.friendly.data.remote.services.UserDataService
import miv.dev.wheelchair.friendly.domain.entities.User
import miv.dev.wheelchair.friendly.domain.repositories.UserRepository
import java.util.*
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
	private val userDataService: UserDataService
): UserRepository {
	override suspend fun getCurrentUser(): Result<User> {
		runCatching {
			userDataService.getCurrentUser()
		}.onSuccess {
			return Result.success(it)
		}.onFailure {
			return Result.failure(it)
		}
		return Result.failure(RuntimeException("Unhandled Error"))
	}
	
	override suspend fun getUserById(uuid: UUID): Result<User> {
		TODO("Not yet implemented")
	}
}
