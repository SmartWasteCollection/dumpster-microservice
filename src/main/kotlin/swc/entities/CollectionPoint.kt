package swc.entities

import java.util.UUID

data class CollectionPoint(
    val id: String = UUID.randomUUID().toString(),
    val position: Position,
)

data class Position(
    val latitude: Double,
    val longitude: Double,
)
