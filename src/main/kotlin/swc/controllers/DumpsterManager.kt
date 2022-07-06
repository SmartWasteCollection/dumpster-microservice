package swc.controllers

import swc.controllers.errors.DumpsterNotFoundException
import swc.entities.Dumpster
import swc.entities.WasteName

object DumpsterManager : Manager {

    override fun getDumpsters(): List<Dumpster> {
        TODO("Not yet implemented")
    }

    override fun getDumpsterById(id: String): Dumpster {
        val query = "SELECT * FROM digitaltwins WHERE ${'$'}dtId='$id' "
        try {
            AzureAuthentication.authClient.query(query, String::class.java).first()
        } catch (e: NoSuchElementException) {
            throw DumpsterNotFoundException("Dumpster with id $id not found")
        }
        return Dumpster.from(1500.0, WasteName.GLASS)
    }
}
