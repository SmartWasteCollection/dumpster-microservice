package swc.controllers

import swc.entities.Dumpster

interface Manager {
    fun getDumpsters(): List<Dumpster>
    fun getDumpsterById(id: String): Dumpster
    fun createDumpster(dumpster: Dumpster): String
    fun openDumpster(id: String)
    fun closeDumpster(id: String)
    fun deleteDumpster(id: String)
    fun closeAfterTimeout(id: String, timeout: Long)
    fun updateVolume(id: String, newVolume: Double)
}
