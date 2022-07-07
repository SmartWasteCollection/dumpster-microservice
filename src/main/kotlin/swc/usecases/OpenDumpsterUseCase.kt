package swc.usecases

import swc.controllers.DumpsterManager
import swc.entities.Dumpster

class OpenDumpsterUseCase(
    private val id: String,
    private val timeout: Long = Dumpster.TIMEOUT_MS,
) : UseCase<Unit> {
    override fun execute() {
        val dumpster = DumpsterManager.getDumpsterById(id)
        if (dumpster.isAvailable()) DumpsterManager.openDumpster(dumpster)

        println("BEFORE: ${DumpsterManager.getDumpsterById(dumpster.id)}")

        DumpsterManager.closeAfterTimeout(dumpster, timeout)
    }
}
