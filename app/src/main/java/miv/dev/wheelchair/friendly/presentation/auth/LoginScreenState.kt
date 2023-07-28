package miv.dev.wheelchair.friendly.presentation.auth


data class LoginScreenUiState(
	val email: String = "",
	val emailError: String = "",
	
	val password: String = "",
	val passwordError: String = "",
	
	val isLoading: Boolean = false,
	val loginError: String = "",
	val loginIsSuccess: Boolean? = null
)
