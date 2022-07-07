package swc.controllers

import com.azure.core.models.JsonPatchDocument
import com.azure.digitaltwins.core.implementation.models.ErrorResponseException
import swc.adapters.DumpsterDeserialization
import swc.adapters.DumpsterDeserialization.toDumpster
import swc.adapters.DumpsterSerialization.toJson
import swc.controllers.errors.DumpsterNotFoundException
import swc.entities.Dumpster
import java.util.concurrent.Executors

object DumpsterManager : Manager {

    override fun getDumpsters(): List<Dumpster> = AzureAuthentication.authClient
        .query(AzureQueries.GET_ALL_DUMPSTERS_QUERY, String::class.java)
        .map { DumpsterDeserialization.parse(it).toDumpster() }

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
        return DumpsterDeserialization.parse(response).toDumpster()
    }

    override fun createDumpster(dumpster: Dumpster): String = AzureAuthentication.authClient.createOrReplaceDigitalTwin(
        dumpster.id,
        dumpster.toJson().toString(),
        String::class.java,
    )

    override fun openDumpster(id: String) = AzureAuthentication.authClient.updateDigitalTwin(
        id,
        JsonPatchDocument().appendReplace("/Open", true),
    )

    override fun closeDumpster(id: String) = AzureAuthentication.authClient.updateDigitalTwin(
        id,
        JsonPatchDocument().appendReplace("/Open", false),
    )

    override fun deleteDumpster(id: String) = AzureAuthentication.authClient.deleteDigitalTwin(id)

    override fun closeAfterTimeout(id: String, timeout: Long) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            Thread.sleep(timeout)
            closeDumpster(id)
        }
    }
}
