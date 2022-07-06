package swc.controllers

import com.azure.digitaltwins.core.implementation.models.ErrorResponseException
import swc.adapters.DumpsterDeserialization
import swc.adapters.DumpsterDeserialization.toDumpster
import swc.adapters.DumpsterSerialization.toJson
import swc.controllers.errors.DumpsterNotFoundException
import swc.entities.Dumpster

object DumpsterManager : Manager {

    override fun getDumpsters(): List<Dumpster> {
        TODO("Not yet implemented")
    }
    @Throws(NoSuchElementException::class)
    override fun getDumpsterById(id: String): Dumpster {
        val query = "SELECT * FROM digitaltwins WHERE ${'$'}dtId='$id' "
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

    override fun createDumpster(dumpster: Dumpster): String = AzureAuthentication.authClient
        .createOrReplaceDigitalTwin(dumpster.id, dumpster.toJson().toString(), String::class.java)
}
