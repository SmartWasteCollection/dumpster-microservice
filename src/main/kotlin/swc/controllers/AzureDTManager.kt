package swc.controllers

import com.azure.core.models.JsonPatchDocument
import com.azure.digitaltwins.core.BasicRelationship
import com.azure.digitaltwins.core.implementation.models.ErrorResponseException
import swc.adapters.CollectionPointsDeserialization.toCollectionPoint
import swc.adapters.CollectionPointsSerialization.toJson
import swc.adapters.DumpsterDeserialization.parse
import swc.adapters.DumpsterDeserialization.toDumpster
import swc.adapters.DumpsterSerialization.toJson
import swc.controllers.errors.CollectionPointNotFoundException
import swc.controllers.errors.DumpsterNotFoundException
import swc.entities.CollectionPoint
import swc.entities.Dumpster
import java.util.concurrent.Executors

object AzureDTManager : Manager {

    override fun getDumpsters() = AzureAuthentication.authClient
        .query(AzureQueries.GET_ALL_DUMPSTERS_QUERY, String::class.java)
        .map { parse(it).toDumpster() }

    @Throws(DumpsterNotFoundException::class)
    override fun getDumpsterById(id: String) =
        parse(getDigitalTwinById(id, DumpsterNotFoundException("Dumpster with id $id not found"))).toDumpster()

    @Throws(CollectionPointNotFoundException::class)
    override fun getCollectionPointById(id: String) =
        parse(getDigitalTwinById(id, CollectionPointNotFoundException("CollectionPoint with id $id not found"))).toCollectionPoint()

    override fun createDumpster(dumpster: Dumpster, collectionPoint: CollectionPoint): Dumpster {
        val createdDumpster = createDigitalTwin(dumpster.id, dumpster.toJson().toString())
        val relationship = AzureDTRelationshipFactory.from(collectionPoint, dumpster)
        AzureAuthentication.authClient.createOrReplaceRelationship(
            collectionPoint.id,
            relationship.id,
            relationship,
            BasicRelationship::class.java
        )
        return parse(createdDumpster).toDumpster()
    }

    override fun createCollectionPoint(collectionPoint: CollectionPoint) =
        parse(createDigitalTwin(collectionPoint.id, collectionPoint.toJson().toString())).toCollectionPoint()

    override fun openDumpster(id: String) = updateDigitalTwin(id, "/open", true)

    override fun closeDumpster(id: String) = updateDigitalTwin(id, "/open", false)

    override fun deleteDumpster(id: String) {
        AzureAuthentication.authClient.listIncomingRelationships(id).forEach {
            AzureAuthentication.authClient.deleteRelationship(it.sourceId, it.relationshipId)
        }
        deleteDigitalTwin(id)
    }
    override fun deleteCollectionPoint(id: String) = deleteDigitalTwin(id)

    override fun closeAfterTimeout(id: String, timeout: Long) = Executors.newSingleThreadExecutor().execute {
        Thread.sleep(timeout)
        closeDumpster(id)
    }

    override fun updateVolume(id: String, newVolume: Double) = updateDigitalTwin(id, "/occupiedVolume", newVolume)

    override fun getDumpstersInCollectionPoint(id: String) =
        AzureAuthentication.authClient.listRelationships(id, BasicRelationship::class.java)
            .map { getDumpsterById(it.targetId) }

    override fun getCollectionPointFromDumpsterId(id: String): String =
        AzureAuthentication.authClient.listIncomingRelationships(id).first().sourceId

    override fun getCollectionPoints() = AzureAuthentication.authClient
        .query(AzureQueries.GET_ALL_CP_QUERY, String::class.java)
        .map { parse(it).toCollectionPoint() }

    private fun deleteDigitalTwin(id: String) = AzureAuthentication.authClient.deleteDigitalTwin(id)
    private fun updateDigitalTwin(id: String, path: String, newValue: Any) =
        AzureAuthentication.authClient.updateDigitalTwin(id, JsonPatchDocument().appendReplace(path, newValue))

    private fun createDigitalTwin(id: String, dtDescription: String) =
        AzureAuthentication.authClient.createOrReplaceDigitalTwin(id, dtDescription, String::class.java)

    private fun getDigitalTwinById(id: String, exception: Exception): String {
        val response: String
        try {
            response = AzureAuthentication.authClient.getDigitalTwin(id, String::class.java)
        } catch (e: ErrorResponseException) {
            when (e.response.statusCode) {
                404 -> throw exception
                else -> error { e.printStackTrace() }
            }
        }
        return response
    }
}
