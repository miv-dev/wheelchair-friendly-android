package miv.dev.wheelchair.friendly.data.remote.services

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.ktor.client.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.http.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class ApiDataService @Inject constructor(
	private val client: HttpClient,
	private val sp: SharedPreferences
) {
	
	private val auth: FirebaseAuth by lazy {
		Firebase.auth
	}
	
	
	init {
		client.config {
			install(Auth) {
				
				bearer {
					loadTokens {
						getToken()?.let {
							BearerTokens(it, it)
						}
					}
					sendWithoutRequest { true }
					refreshTokens {
						getToken()?.let {
							BearerTokens(it, it)
						}
					}
				}
			}
		}
	}
	
	suspend fun request(block: suspend HttpClient.() -> Unit) {
		block(client)
	}
	
	
	suspend fun getToken(): String? {
		return auth.currentUser?.let { user ->
			val result = user.getIdToken(true).await()
			sp.edit {
				putString("access_token", result.token)
			}
			return result.token
			
		}
	}
	
	companion object {
		const val HOST = "miv-dev.ru"
	}
}
