package swc.entities

import java.util.UUID

data class Dumpster(
    val id: String = UUID.randomUUID().toString(),
    val type: DumpsterType,
    val isOpen: Boolean = false,
    val isWorking: Boolean = true,
    val occupiedVolume: Volume = Volume(),
) {
    companion object {
        fun from(capacity: Double, wasteName: WasteName) = Dumpster(type = DumpsterType.from(capacity, wasteName))
        fun from(id: String, capacity: Double, wasteName: WasteName) = Dumpster(id = id, type = DumpsterType.from(capacity, wasteName))
    }
}
