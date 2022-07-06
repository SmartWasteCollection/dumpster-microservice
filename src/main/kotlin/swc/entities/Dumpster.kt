package swc.entities

import java.util.UUID

data class Dumpster(
    val id: String = UUID.randomUUID().toString(),
    val type: DumpsterType,
    var isOpen: Boolean = false,
    var isWorking: Boolean = true,
    var occupiedVolume: Volume = Volume(),
) {
    companion object {
        const val CAPACITY_THRESHOLD = 95
        const val TIMEOUT = 30

        fun from(capacity: Double, wasteName: WasteName) = Dumpster(type = DumpsterType.from(capacity, wasteName))
        fun from(id: String, capacity: Double, wasteName: WasteName) = Dumpster(id = id, type = DumpsterType.from(capacity, wasteName))
    }

    fun isAvailable(): Boolean = isWorking && occupiedVolume.getOccupiedPercentage(type.size.capacity) < CAPACITY_THRESHOLD
}
