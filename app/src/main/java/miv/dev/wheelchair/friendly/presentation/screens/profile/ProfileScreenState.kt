package miv.dev.wheelchair.friendly.presentation.screens.profile

import miv.dev.wheelchair.friendly.domain.entities.User

sealed class ProfileScreenState {
	
	object Initial : ProfileScreenState()
	object Loading : ProfileScreenState()
	data class Error(val msg: String) : ProfileScreenState()
	
	data class Profile(
		val user: User
	) : ProfileScreenState()
	
}
