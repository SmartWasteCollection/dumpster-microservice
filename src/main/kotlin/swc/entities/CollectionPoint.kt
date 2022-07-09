package swc.entities

import java.util.UUID

data class CollectionPoint(
    val id: String = "CollectionPoint-${UUID.randomUUID()}",
    val position: Position,
)

data class Position(
    val latitude: Double,
    val longitude: Double,
)
