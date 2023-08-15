package miv.dev.wheelchair.friendly.domain.usecases

import miv.dev.wheelchair.friendly.domain.entities.Place
import miv.dev.wheelchair.friendly.domain.repositories.PlaceRepository
import javax.inject.Inject

class AddPlaceUseCase @Inject constructor(
	private val repository: PlaceRepository
) {
	suspend operator fun invoke(place: Place): Result<Unit>{
		return repository.addPlace(place)
	}
	
}
