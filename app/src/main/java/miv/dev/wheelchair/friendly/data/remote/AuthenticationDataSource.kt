package miv.dev.wheelchair.friendly.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable
import miv.dev.wheelchair.friendly.data.local.TokenPair
import miv.dev.wheelchair.friendly.data.local.TokenService
import miv.dev.wheelchair.friendly.domain.entities.Credentials
import javax.inject.Inject

@Serializable
data class Response<T>(
	val success: Boolean,
	val error: String? = null,
	val data: T? = null
)

class AuthenticationDataSource @Inject constructor(
	private val client: HttpClient,
	private val tokenService: TokenService
) {
	
	
	suspend fun loginWithEmailAndPassword(
		credentials: Credentials.EmailAndPassword,
	): Response<TokenPair> = client.post {
		url("auth/login")
		setBody(credentials)
	}.body()
	
	fun setTokenPair(tokenPair: TokenPair) {
		tokenService.saveBearerToken(tokenPair.accessToken)
		tokenService.saveRefreshToken(tokenPair.refreshToken)
	}
	
	fun clearTokenPair() {
		tokenService.saveBearerToken("")
		tokenService.saveRefreshToken("")
	}


//	suspend fun loginWithCredentials(
//		authCredential: AuthCredential
//	): Result<FirebaseUser> {
//		return try {
////			val authResult = auth.signInWithCredential(authCredential).await()
//			Result.success(authResult.user ?: throw RuntimeException("User is null"))
//		} catch (e: Exception) {
//			Result.failure(e)
//		}
//
//	}


//	suspend fun createWithEmailAndPassword(credentials: Credentials.EmailAndPassword): Result<FirebaseUser> {
//		return try {
//			val result = auth
//				.createUserWithEmailAndPassword(credentials.email, credentials.password)
//				.await()
//			Result.success(result.user ?: throw RuntimeException("User is null"))
//		} catch (e: Exception) {
//			Result.failure(e)
//		}
//	}

}
