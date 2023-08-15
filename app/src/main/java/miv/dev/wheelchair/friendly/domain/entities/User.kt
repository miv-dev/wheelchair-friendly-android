package miv.dev.wheelchair.friendly.domain.entities

import kotlinx.serialization.Serializable
import miv.dev.wheelchair.friendly.serializers.UUIDSerializer
import java.util.*

@Serializable
data class User(
	@Serializable(with = UUIDSerializer::class)
	val uuid: UUID,
	val email: String,
	val username: String,
	val places: List<Place>
)


