package swc.usecases.dumpster

import swc.entities.Dumpster
import swc.usecases.UseCase

class CloseDumpsterUseCase(private val id: String) : UseCase<Dumpster> {
    override fun execute(): Dumpster {
        manager().closeDumpster(id)
        return manager().getDumpsterById(id)
    }
}
