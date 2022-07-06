package swc.usecases

import swc.controllers.DumpsterManager
import swc.entities.Dumpster

class CreateDumpsterUseCase(private val dumpster: Dumpster) : UseCase<String> {
    override fun execute(): String =
        DumpsterManager.createDumpster(dumpster)
}
