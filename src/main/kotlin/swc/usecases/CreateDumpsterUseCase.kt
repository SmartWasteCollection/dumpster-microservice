package swc.usecases

import swc.entities.Dumpster

class CreateDumpsterUseCase(private val dumpster: Dumpster) : UseCase<String> {
    override fun execute(): String =
        manager().createDumpster(dumpster)
}
