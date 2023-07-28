package miv.dev.wheelchair.friendly.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import miv.dev.wheelchair.friendly.domain.usecases.CheckAuthStateUseCase
import miv.dev.wheelchair.friendly.domain.usecases.GetAuthStateUseCase
import javax.inject.Inject

class AppViewModel @Inject constructor(
	private val getAuthStateUseCase: GetAuthStateUseCase,
	private val checkAuthStateUseCase: CheckAuthStateUseCase
) : ViewModel() {
	
	val authState = getAuthStateUseCase()
	
	init {
		performAuthResult()
	}
	private fun performAuthResult() {
		viewModelScope.launch {
			checkAuthStateUseCase()
		}
	}
}
