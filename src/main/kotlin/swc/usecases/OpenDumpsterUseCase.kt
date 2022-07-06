package swc.usecases

import swc.controllers.DumpsterManager

class OpenDumpsterUseCase(private val id: String) : UseCase<Unit> {
    override fun execute() {
        val dumpster = DumpsterManager.getDumpsterById(id)
        if (dumpster.isAvailable()) DumpsterManager.openDumpster(dumpster)
    }
}
