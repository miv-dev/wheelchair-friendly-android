package miv.dev.wheelchair.friendly.data.remote

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.tasks.await
import miv.dev.wheelchair.friendly.data.local.TokenPair
import miv.dev.wheelchair.friendly.data.local.TokenService
import miv.dev.wheelchair.friendly.domain.entities.Credentials
import javax.inject.Inject

class AuthenticationDataSource @Inject constructor(
	private val client: HttpClient,
	private val tokenService: TokenService
) {
	
	
	
	suspend fun loginWithEmailAndPassword(
		credentials: Credentials.EmailAndPassword,
	): TokenPair =
		client.post {
			url("auth/login")
			setBody(credentials)
		}.body()
	
	fun setTokenPair(tokenPair: TokenPair) {
		tokenService.saveBearerToken(tokenPair.accessToken)
		tokenService.saveRefreshToken(tokenPair.refreshToken)
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
