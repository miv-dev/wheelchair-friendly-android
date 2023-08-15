package miv.dev.wheelchair.friendly.presentation.screens.places.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import miv.dev.wheelchair.friendly.domain.entities.Coordinates
import miv.dev.wheelchair.friendly.domain.entities.Place
import miv.dev.wheelchair.friendly.domain.usecases.AddPlaceUseCase
import javax.inject.Inject

class AddPlaceScreenViewModel @Inject constructor(
	private val addPlaceUseCase: AddPlaceUseCase
) : ViewModel() {
	
	
	private val scope = viewModelScope
	
	private val _screenState = MutableStateFlow(AddPlaceScreenState())
	val screenState: StateFlow<AddPlaceScreenState>
		get() = _screenState
	
	
	fun updateName(name: String) {
		_screenState.value = _screenState.value.copy(name = name)
	}
	
	fun updateAddress(address: String) {
		_screenState.value = _screenState.value.copy(address = address)
	}
	
	fun updateCoordinates(coordinates: Coordinates) {
		_screenState.value = _screenState.value.copy(coordinates = coordinates)
	}
	
	fun submit() {
		
		scope.launch {
			_screenState.value = _screenState.value.copy(
				sendIsSuccess = null,
				sendError = "",
				isLoading = true
			)
			val place = with(screenState.value) {
				Place(
					"",
					name = name,
					address = address,
					coordinates = coordinates,
					rating = 0.0,
					lastUpdate = Clock.System.now().toLocalDateTime(TimeZone.UTC)
				)
			}
			
			
			val result = addPlaceUseCase(place)
			result
				.onSuccess {
					_screenState.value = _screenState.value.copy(
						sendIsSuccess = true,
						isLoading = false
					)
				}
				.onFailure {
					_screenState.value = _screenState.value.copy(
						sendIsSuccess = false,
						sendError = it.localizedMessage ?: "Unhandled Error",
						isLoading = false
					)
				}
		}
		
	}
}
