package swc.usecases.dumpster

import swc.entities.Dumpster
import swc.usecases.UseCase

class CreateDumpsterUseCase(private val dumpster: Dumpster) : UseCase<Dumpster> {
    override fun execute(): Dumpster = manager().createDumpster(dumpster)
}
