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
        obj.add("size", this.size.toJson())
        obj.add("typeOfOrdinaryWaste", this.typeOfOrdinaryWaste.toJson())
        return obj
    }

    fun Size.toJson(): JsonObject {
        val obj = JsonObject()
        obj.addProperty("dimension", this.dimension.toString())
        obj.addProperty("capacity", this.capacity)
        return obj
    }

    fun TypeOfOrdinaryWaste.toJson(): JsonObject {
        val obj = JsonObject()
        obj.addProperty("wasteName", this.wasteName.toString())
        obj.addProperty("color", this.wasteColor.toString())
        return obj
    }

    fun Dumpster.toJson(): JsonObject {
        val metadata = JsonObject()
        metadata.addProperty("${'$'}model", AzureConstants.DUMPSTER_DT_MODEL_ID)

        val obj = JsonObject()
        obj.addProperty("${'$'}dtId", this.id)
        obj.add("${'$'}metadata", metadata)
        obj.add("dumpsterType", this.type.toJson())
        obj.addProperty("open", this.isOpen)
        obj.addProperty("working", this.isWorking)
        obj.addProperty("occupiedVolume", this.occupiedVolume.value)
        return obj
    }
}

object DumpsterDeserialization {
    fun JsonObject.toDumpster() = Dumpster(
        this["${'$'}dtId"].asString,
        this.getAsJsonObject("dumpsterType").toDumpsterType(),
        this["open"].asBoolean,
        this["working"].asBoolean,
        this.toVolume()
    )

    fun parse(json: String): JsonObject = JsonParser().parse(json).asJsonObject

    private fun JsonObject.toSize() = Size.from(
        this.getAsJsonPrimitive("capacity")
            .asDouble
    )

    private fun JsonObject.toWasteName() = WasteName.valueOf(
        this.getAsJsonObject("typeOfOrdinaryWaste")
            .getAsJsonPrimitive("wasteName")
            .asString
    )

    private fun JsonObject.toVolume() = Volume(this["occupiedVolume"].asDouble)

    private fun JsonObject.toDumpsterType() = DumpsterType.from(
        this.getAsJsonObject("size").toSize().capacity,
        this.toWasteName()
    )
}
