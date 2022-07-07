package swc.adapters

import com.google.gson.JsonObject
import swc.controllers.AzureConstants
import swc.entities.CollectionPoint
import swc.entities.Position

object CollectionPointsSerialization {
    fun Position.toJson(): JsonObject {
        val obj = JsonObject()
        obj.addProperty("Latitude", this.latitude)
        obj.addProperty("Longitude", this.longitude)
        return obj
    }

    fun CollectionPoint.toJson(): JsonObject {
        val metadata = JsonObject()
        metadata.addProperty("${'$'}model", AzureConstants.COLLECTION_POINT_DT_MODEL_ID)

        val obj = JsonObject()
        obj.addProperty("${'$'}dtId", this.id)
        obj.add("${'$'}metadata", metadata)
        obj.add("Position", this.position.toJson())
        return obj
    }
}

object CollectionPointsDeserialization {
    fun JsonObject.toCollectionPoint() = CollectionPoint(
        this["${'$'}dtId"].asString,
        this.getAsJsonObject("Position").toPosition(),
    )

    private fun JsonObject.toPosition() = Position(
        this.getAsJsonPrimitive("Latitude").asLong,
        this.getAsJsonPrimitive("Longitude").asLong,
    )
}
