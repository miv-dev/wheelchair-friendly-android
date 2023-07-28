package miv.dev.wheelchair.friendly.domain.usecases

import miv.dev.wheelchair.friendly.domain.repositories.AuthenticationRepository
import javax.inject.Inject

class CheckAuthStateUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {

    suspend operator fun invoke() {
        repository.checkAuthState()
    }
}
