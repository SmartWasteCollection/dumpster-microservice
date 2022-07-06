package swc.entities

import java.util.UUID

class Dumpster private constructor(
    val id: String,
    val name: String,
    val type: DumpsterType,
    val isOpen: Boolean = false,
    val isWorking: Boolean = true,
    val occupiedVolume: Volume = Volume(),
) {
    companion object {
        fun from(id: String, name: String, capacity: Double, wasteName: WasteName) =
            Dumpster(
                id = id,
                name = name,
                type = DumpsterType.from(capacity, wasteName),
            )

        fun from(capacity: Double, wasteName: WasteName) =
            from(UUID.randomUUID().toString(), "Dumpster", capacity, wasteName)
    }

    override fun toString(): String =
        """
        Dumpster(
            id = $id
            name = $name
            type = $type
            isOpen = $isOpen
            isWorking = $isWorking
            occupiedVolume = $occupiedVolume
        )
        """.trimIndent()

    override fun equals(other: Any?) = (other is Dumpster) &&
        id == other.id && name == other.name && type == other.type && isOpen == other.isOpen &&
        isWorking == other.isWorking && occupiedVolume == other.occupiedVolume

    override fun hashCode(): Int {
        var result = 31 * id.hashCode() + name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + isOpen.hashCode()
        result = 31 * result + isWorking.hashCode()
        result = 31 * result + occupiedVolume.hashCode()
        return result
    }
}
