package miv.dev.wheelchair.friendly.presentation.screens.places

import miv.dev.wheelchair.friendly.domain.entities.Place

sealed class PlaceScreenState {
	object Initial : PlaceScreenState()
	object Loading : PlaceScreenState()
	
	data class Places(
		val list: List<Place>
	) : PlaceScreenState()
	
	data class Error(
		val msg: String
	) : PlaceScreenState()
}
