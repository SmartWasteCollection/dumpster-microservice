package swc.controllers

import swc.entities.Dumpster

interface Manager {
    fun getDumpsters(): List<Dumpster>

    fun getDumpsterById(id: String): Dumpster
}
