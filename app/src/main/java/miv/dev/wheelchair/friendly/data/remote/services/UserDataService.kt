package miv.dev.wheelchair.friendly.data.remote.services

import android.util.Log
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import miv.dev.wheelchair.friendly.data.local.TokenService
import miv.dev.wheelchair.friendly.domain.entities.User
import javax.inject.Inject

class UserDataService @Inject constructor(
	private val client: HttpClient,
	private val tokenService: TokenService
) {
	
	
	suspend fun getCurrentUser(): User {
		
		val body: User = client.get {
			url("api/users/current")
		}.body()
		Log.d(TAG, "getCurrentUser: $body")
		return body
	}
	
	companion object {
		const val TAG = "UserDataService"
	}
	
}
