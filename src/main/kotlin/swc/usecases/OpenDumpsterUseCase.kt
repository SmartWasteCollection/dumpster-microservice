package swc.usecases

import swc.entities.Dumpster

class OpenDumpsterUseCase(
    private val id: String,
    private val timeout: Long = Dumpster.TIMEOUT_MS,
) : UseCase<Dumpster> {
    override fun execute(): Dumpster {
        val dumpster = manager().getDumpsterById(id)
        if (dumpster.isAvailable()) manager().openDumpster(dumpster.id)
        manager().closeAfterTimeout(dumpster.id, timeout)
        return manager().getDumpsterById(id)
    }
}
