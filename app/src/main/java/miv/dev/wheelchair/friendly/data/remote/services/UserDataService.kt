package miv.dev.wheelchair.friendly.data.remote.services

import android.util.Log
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.json.JSONObject
import javax.inject.Inject

class UserDataService @Inject constructor(
	private val api: ApiDataService
) {
	
	suspend fun getCurrentUser() {
		val token = api.getToken()
		Log.d(TAG, "getCurrentUser: $token")
		api.request {
			val body = get {
				url {
					port = 8080
					protocol = URLProtocol.HTTP
					host = "192.168.0.36"
					path("api/users/current")
					
				}
				
				headers {
					append(HttpHeaders.Authorization, token ?: "")
				}
			}.bodyAsText()
			Log.d(TAG, "getCurrentUser: $body")
		}
	}
	companion object{
		const val TAG = "UserDataService"
	}
	
}
