package miv.dev.wheelchair.friendly.data.remote.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import miv.dev.wheelchair.friendly.domain.entities.Place
import javax.inject.Inject

class PlaceDataService @Inject constructor(
	private val client: HttpClient,
) {
	
	suspend fun addPlace(place: Place): Boolean {
		val response = client.post {
			url("api/place")
			setBody(
				place
			)
		}
		if (response.status != HttpStatusCode.OK) {
			throw ResponseException(response, response.status.toString())
		}
		return true
	}
	
	suspend fun fetchPlaces(): List<Place>{
		return client.get {
			url("api/place")
		}.body()
	}
}
