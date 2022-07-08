package swc.adapters

import com.google.gson.JsonObject
import swc.controllers.AzureConstants
import swc.entities.CollectionPoint
import swc.entities.Position

object CollectionPointsSerialization {
    fun Position.toJson(): JsonObject {
        val obj = JsonObject()
        obj.addProperty("latitude", this.latitude)
        obj.addProperty("longitude", this.longitude)
        return obj
    }

    fun CollectionPoint.toJson(): JsonObject {
        val metadata = JsonObject()
        metadata.addProperty("${'$'}model", AzureConstants.COLLECTION_POINT_DT_MODEL_ID)

        val obj = JsonObject()
        obj.addProperty("${'$'}dtId", this.id)
        obj.add("${'$'}metadata", metadata)
        obj.add("position", this.position.toJson())
        return obj
    }
}

object CollectionPointsDeserialization {
    fun JsonObject.toCollectionPoint() = CollectionPoint(
        this["${'$'}dtId"].asString,
        this.getAsJsonObject("position").toPosition(),
    )

    private fun JsonObject.toPosition() = Position(
        this.getAsJsonPrimitive("latitude").asLong,
        this.getAsJsonPrimitive("longitude").asLong,
    )
}
