package miv.dev.wheelchair.friendly.domain.repositories

import miv.dev.wheelchair.friendly.domain.entities.Place

interface PlaceRepository {
	suspend fun addPlace(place: Place): Result<Unit>
	
	suspend fun fetchPlaces(): Result<List<Place>>
	
}
