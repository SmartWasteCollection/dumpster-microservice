package swc.usecases

import swc.entities.Dumpster

class CloseDumpsterUseCase(private val id: String) : UseCase<Dumpster> {
    override fun execute(): Dumpster {
        manager().closeDumpster(id)
        return manager().getDumpsterById(id)
    }
}
