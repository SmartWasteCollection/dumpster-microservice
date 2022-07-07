package swc.adapters

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import swc.controllers.AzureConstants
import swc.entities.Dumpster
import swc.entities.DumpsterType
import swc.entities.Size
import swc.entities.TypeOfOrdinaryWaste
import swc.entities.Volume
import swc.entities.WasteName

object DumpsterSerialization {
    fun DumpsterType.toJson(): JsonObject {
        val obj = JsonObject()
        obj.add("Size", this.size.toJson())
        obj.add("TypeOfOrdinaryWaste", this.typeOfOrdinaryWaste.toJson())
        return obj
    }

    fun Size.toJson(): JsonObject {
        val obj = JsonObject()
        obj.addProperty("Dimension", this.dimension.toString())
        obj.addProperty("Capacity", this.capacity)
        return obj
    }

    fun TypeOfOrdinaryWaste.toJson(): JsonObject {
        val obj = JsonObject()
        obj.addProperty("WasteName", this.wasteName.toString())
        obj.addProperty("Color", this.wasteColor.toString())
        return obj
    }

    fun Dumpster.toJson(): JsonObject {
        val metadata = JsonObject()
        metadata.addProperty("${'$'}model", AzureConstants.DUMPSTER_DT_MODEL_ID)

        val obj = JsonObject()
        obj.addProperty("${'$'}dtId", this.id)
        obj.add("${'$'}metadata", metadata)
        obj.add("DumpsterType", this.type.toJson())
        obj.addProperty("Open", this.isOpen)
        obj.addProperty("Working", this.isWorking)
        obj.addProperty("OccupiedVolume", this.occupiedVolume.value)
        return obj
    }
}

object DumpsterDeserialization {
    fun JsonObject.toDumpster() = Dumpster(
        this["${'$'}dtId"].asString,
        this.toDumpsterType(),
        this["Open"].asBoolean,
        this["Working"].asBoolean,
        this.toVolume()
    )

    fun parse(json: String): JsonObject = JsonParser().parse(json).asJsonObject

    private fun JsonObject.toSize() = Size.from(
        this.getAsJsonObject("DumpsterType")
            .getAsJsonObject("Size")
            .getAsJsonPrimitive("Capacity")
            .asDouble
    )

    private fun JsonObject.toWasteName() = WasteName.valueOf(
        this.getAsJsonObject("DumpsterType")
            .getAsJsonObject("TypeOfOrdinaryWaste")
            .getAsJsonPrimitive("WasteName")
            .asString
    )

    private fun JsonObject.toVolume() = Volume(this["OccupiedVolume"].asDouble)

    private fun JsonObject.toDumpsterType() = DumpsterType.from(this.toSize().capacity, this.toWasteName())
}
