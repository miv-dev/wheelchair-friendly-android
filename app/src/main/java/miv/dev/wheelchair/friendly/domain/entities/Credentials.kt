package miv.dev.wheelchair.friendly.domain.entities

import com.google.firebase.auth.AuthCredential

sealed class Credentials {
	data class EmailAndPassword(val email: String, val password: String) : Credentials()
	
	data class Google(val credential: AuthCredential): Credentials()
}
