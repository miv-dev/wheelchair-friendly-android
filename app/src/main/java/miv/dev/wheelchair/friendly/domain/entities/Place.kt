package miv.dev.wheelchair.friendly.domain.entities

import kotlinx.datetime.*
import kotlinx.serialization.Serializable
import miv.dev.wheelchair.friendly.di.DataModule

@Serializable
data class Place(
	var id: String,
	var name: String,
	var address: String,
	var coordinates: Coordinates,
	var rating: Double,
	var lastUpdate: LocalDateTime,
	var pictures: List<Picture> = emptyList(),
	var pictureBaseUrl: String = "",
	var reviewsQuantity: Int = 0
//	var tags: List<Tag> = emptyList()
){
	
	fun getPictureUrl(picture: Picture): String {
		return "${DataModule.BASE_URL}${pictureBaseUrl}${picture.picture}"
	}
}

@Serializable
data class Picture (
	val id: Int,
	val picture: String,
	val preview: String
){

}
@Serializable
data class Coordinates(
	var latitude: Double,
	var longitude: Double
)
