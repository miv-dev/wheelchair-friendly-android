package miv.dev.wheelchair.friendly.domain.usecases

import miv.dev.wheelchair.friendly.data.repositories.PlaceRepositoryImpl
import miv.dev.wheelchair.friendly.domain.entities.Place
import miv.dev.wheelchair.friendly.domain.repositories.PlaceRepository
import javax.inject.Inject

class FetchPlaceListUseCase @Inject constructor(
	private val repository: PlaceRepository
) {
	suspend operator fun invoke(): Result<List<Place>> = repository.fetchPlaces()
	
}
