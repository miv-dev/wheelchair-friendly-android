package miv.dev.wheelchair.friendly.presentation.screens.places.add

import miv.dev.wheelchair.friendly.domain.entities.Coordinates

data class AddPlaceScreenState(
	val address: String = "",
	val addressError: String = "",
	
	val name: String = "",
	val nameError: String = "",
	
	val coordinates: Coordinates = Coordinates(.0,.0),
	val coordinatesError: String = "",
	
	val isLoading: Boolean = false,
	val sendError: String = "",
	val sendIsSuccess: Boolean? = null
)
