package swc.controllers

import swc.entities.CollectionPoint
import swc.entities.Dumpster

interface Manager {
    fun getDumpsters(): List<Dumpster>
    fun getDumpsterById(id: String): Dumpster
    fun createDumpster(dumpster: Dumpster, collectionPointId: String): Dumpster
    fun openDumpster(id: String)
    fun closeDumpster(id: String)
    fun deleteDumpster(id: String)
    fun closeAfterTimeout(id: String, timeout: Long)
    fun updateVolume(id: String, newVolume: Double)
    fun createCollectionPoint(collectionPoint: CollectionPoint): CollectionPoint
    fun deleteCollectionPoint(id: String)
    fun getCollectionPointById(id: String): CollectionPoint
    fun getDumpstersInCollectionPoint(id: String): List<Dumpster>
    fun getCollectionPointFromDumpsterId(id: String): String
    fun getCollectionPoints(): List<CollectionPoint>
    fun updateDumpsterWorking(id: String, working: Boolean)
}
