package swc.adapters

import com.google.gson.JsonObject
import swc.controllers.AzureConstants
import swc.entities.CollectionPoint
import swc.entities.Position

object CollectionPointsSerialization {
    fun Position.toJson(): JsonObject {
        val obj = JsonObject()
        obj.addProperty(CollectionPointsPropertyNames.LATITUDE, this.latitude)
        obj.addProperty(CollectionPointsPropertyNames.LONGITUDE, this.longitude)
        return obj
    }

    fun CollectionPoint.toJson(): JsonObject {
        val metadata = JsonObject()
        metadata.addProperty(DigitalTwinPropertyNames.MODEL, AzureConstants.COLLECTION_POINT_DT_MODEL_ID)

        val obj = JsonObject()
        obj.addProperty(DigitalTwinPropertyNames.DTID, this.id)
        obj.add(DigitalTwinPropertyNames.METADATA, metadata)
        obj.add(CollectionPointsPropertyNames.POSITION, this.position.toJson())
        return obj
    }
}

object CollectionPointsDeserialization {
    fun JsonObject.toCollectionPoint() = CollectionPoint(
        (this["id"] ?: this[DigitalTwinPropertyNames.DTID]).asString,
        this.getAsJsonObject(CollectionPointsPropertyNames.POSITION).toPosition(),
    )

    private fun JsonObject.toPosition() = Position(
        this.getAsJsonPrimitive(CollectionPointsPropertyNames.LATITUDE).asLong,
        this.getAsJsonPrimitive(CollectionPointsPropertyNames.LONGITUDE).asLong,
    )
}
