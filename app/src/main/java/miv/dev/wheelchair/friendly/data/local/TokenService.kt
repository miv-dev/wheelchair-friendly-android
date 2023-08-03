package miv.dev.wheelchair.friendly.data.local

import android.content.Context
import androidx.core.content.edit
import kotlinx.serialization.Serializable
import javax.inject.Inject

class TokenService @Inject constructor(
	private val context: Context
) {
	private val sp = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
	
	fun saveBearerToken(bearerToken: String) {
		sp.edit {
			putString(BEARER_TOKEN_KEY, bearerToken)
			commit()
		}
	}
	
	fun saveRefreshToken(refreshToken: String) {
		sp.edit {
			putString(REFRESH_TOKEN_KEY, refreshToken)
			commit()
		}
	}
	
	fun getBearerToken(): String? {
		return sp.getString(BEARER_TOKEN_KEY, null)
	}
	
	fun getRefreshToken(): String? {
		return sp.getString(REFRESH_TOKEN_KEY, null)
	}
	
	companion object{
		const val BEARER_TOKEN_KEY = "bearer_token"
		const val REFRESH_TOKEN_KEY ="refresh_token"
	}
}

@Serializable
data class TokenPair(val accessToken: String, val refreshToken: String)
