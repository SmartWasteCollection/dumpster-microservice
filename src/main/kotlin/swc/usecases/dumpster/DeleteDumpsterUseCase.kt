package swc.usecases.dumpster

import swc.entities.Dumpster
import swc.usecases.UseCase

class DeleteDumpsterUseCase(private val id: String) : UseCase<Dumpster> {
    override fun execute(): Dumpster {
        val oldDumpster = manager().getDumpsterById(id)
        manager().deleteDumpster(id)
        return oldDumpster
    }
}
