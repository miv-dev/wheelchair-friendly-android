package miv.dev.wheelchair.friendly.data.remote

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import miv.dev.wheelchair.friendly.domain.entities.Credentials
import javax.inject.Inject

class AuthenticationDataSource @Inject constructor() {
	
	private val auth: FirebaseAuth by lazy {
		Firebase.auth
	}
	
	fun isLogged(): Boolean = auth.currentUser != null
	
	
	suspend fun loginWithEmailAndPassword(
		credentials: Credentials.EmailAndPassword,
	): Result<FirebaseUser> {
		return try {
			val result = auth
				.signInWithEmailAndPassword(credentials.email, credentials.password)
				.await()
			Result.success(result.user ?: throw RuntimeException("User is null"))
		} catch (e: Exception) {
			Result.failure(e)
		}
		
	}
	
	suspend fun loginWithCredentials(
		authCredential: AuthCredential
	): Result<FirebaseUser> {
		return try {
			val authResult = auth.signInWithCredential(authCredential).await()
			Result.success(authResult.user ?: throw RuntimeException("User is null"))
		} catch (e: Exception) {
			Result.failure(e)
		}
		
	}
	
}
