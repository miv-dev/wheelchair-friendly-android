package miv.dev.wheelchair.friendly.presentation.screens.places

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import miv.dev.wheelchair.friendly.domain.usecases.FetchPlaceListUseCase
import javax.inject.Inject

class PlacesScreenViewModel @Inject constructor(
	private val fetchPlaceListUseCase: FetchPlaceListUseCase
) : ViewModel() {
	val scope = viewModelScope
	
	
	private val _screenState = MutableSharedFlow<PlaceScreenState>()
	val screenState: SharedFlow<PlaceScreenState>
		get() = _screenState
	
	
	
	fun fetchPlaces() {
		
		scope.launch {
			_screenState.emit(PlaceScreenState.Loading)
			
			fetchPlaceListUseCase()
				.onSuccess {
					_screenState.emit(PlaceScreenState.Places(it))
				}
				.onFailure {
					_screenState.emit(PlaceScreenState.Error(it.localizedMessage ?: "Unhandled Error"))
				}
		}
	}
	
	
	fun onSearch(text: String) {
	
	}
	
}
