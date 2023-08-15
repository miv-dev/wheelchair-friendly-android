package miv.dev.wheelchair.friendly.data.repositories

import miv.dev.wheelchair.friendly.data.remote.services.PlaceDataService
import miv.dev.wheelchair.friendly.domain.entities.Place
import miv.dev.wheelchair.friendly.domain.repositories.PlaceRepository
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
	private val placeDataService: PlaceDataService
) : PlaceRepository {
	override suspend fun addPlace(place: Place): Result<Unit> {
		return runCatching {
			placeDataService.addPlace(place)
			Unit
		}
	}
	
	override suspend fun fetchPlaces(): Result<List<Place>> {
		return runCatching {
			placeDataService.fetchPlaces()
		}
	}
}
