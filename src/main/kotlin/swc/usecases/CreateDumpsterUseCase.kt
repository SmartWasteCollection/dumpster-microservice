package swc.usecases

import swc.entities.Dumpster

class CreateDumpsterUseCase(private val dumpster: Dumpster) : UseCase<Dumpster> {
    override fun execute(): Dumpster = manager().createDumpster(dumpster)
}
