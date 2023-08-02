package miv.dev.wheelchair.friendly.data.remote

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.ktor.client.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import kotlinx.coroutines.tasks.await
import miv.dev.wheelchair.friendly.domain.entities.Credentials
import javax.inject.Inject

class AuthenticationDataSource @Inject constructor(
	private val client: HttpClient,
) {
	
	private val auth: FirebaseAuth by lazy {
		Firebase.auth
	}
	
	fun isLogged(): Boolean {
		
		
		return auth.currentUser != null
	}
	
	
	
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
	
	fun logout() {
		auth.signOut()
	}
	
	suspend fun createWithEmailAndPassword(credentials: Credentials.EmailAndPassword): Result<FirebaseUser> {
		return try {
			val result = auth
				.createUserWithEmailAndPassword(credentials.email, credentials.password)
				.await()
			Result.success(result.user ?: throw RuntimeException("User is null"))
		} catch (e: Exception) {
			Result.failure(e)
		}
	}
	
}
