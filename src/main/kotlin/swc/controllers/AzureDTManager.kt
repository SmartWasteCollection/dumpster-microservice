package swc.controllers

import com.azure.core.models.JsonPatchDocument
import com.azure.digitaltwins.core.implementation.models.ErrorResponseException
import swc.adapters.CollectionPointsDeserialization.toCollectionPoint
import swc.adapters.CollectionPointsSerialization.toJson
import swc.adapters.DumpsterDeserialization.parse
import swc.adapters.DumpsterDeserialization.toDumpster
import swc.adapters.DumpsterSerialization.toJson
import swc.controllers.errors.DumpsterNotFoundException
import swc.entities.CollectionPoint
import swc.entities.Dumpster
import java.util.concurrent.Executors

object AzureDTManager : Manager {

    override fun getDumpsters() = AzureAuthentication.authClient
        .query(AzureQueries.GET_ALL_DUMPSTERS_QUERY, String::class.java)
        .map { parse(it).toDumpster() }

    @Throws(NoSuchElementException::class)
    override fun getDumpsterById(id: String): Dumpster {
        val response: String
        try {
            response = AzureAuthentication.authClient.getDigitalTwin(id, String::class.java)
        } catch (e: ErrorResponseException) {
            when (e.response.statusCode) {
                404 -> throw DumpsterNotFoundException("Dumpster with id $id not found")
                else -> error { e.printStackTrace() }
            }
        }
        return parse(response).toDumpster()
    }

    override fun createDumpster(dumpster: Dumpster) =
        parse(createDigitalTwin(dumpster.id, dumpster.toJson().toString())).toDumpster()

    override fun createCollectionPoint(collectionPoint: CollectionPoint) =
        parse(createDigitalTwin(collectionPoint.id, collectionPoint.toJson().toString())).toCollectionPoint()

    override fun openDumpster(id: String) = updateDigitalTwin(id, "/Open", true)

    override fun closeDumpster(id: String) = updateDigitalTwin(id, "/Open", false)

    override fun deleteDumpster(id: String) = AzureAuthentication.authClient.deleteDigitalTwin(id)

    override fun closeAfterTimeout(id: String, timeout: Long) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            Thread.sleep(timeout)
            closeDumpster(id)
        }
    }

    override fun updateVolume(id: String, newVolume: Double) = updateDigitalTwin(id, "/OccupiedVolume", newVolume)

    private fun updateDigitalTwin(id: String, path: String, newValue: Any) =
        AzureAuthentication.authClient.updateDigitalTwin(id, JsonPatchDocument().appendReplace(path, newValue))

    private fun createDigitalTwin(id: String, dtDescription: String) =
        AzureAuthentication.authClient.createOrReplaceDigitalTwin(id, dtDescription, String::class.java)
}
