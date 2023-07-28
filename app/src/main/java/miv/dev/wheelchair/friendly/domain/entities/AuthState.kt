package miv.dev.wheelchair.friendly.domain.entities

sealed class AuthState {
	object Initial : AuthState()
	object NonAuthorized : AuthState()
	object Authorized : AuthState()
}
