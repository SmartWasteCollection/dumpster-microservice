package swc.usecases.dumpster

import swc.entities.Dumpster
import swc.usecases.UseCase

class UpdateDumpsterWorkingUseCase(
    private val id: String,
    private val working: Boolean,
) : UseCase<Dumpster> {
    override fun execute(): Dumpster {
        manager().updateDumpsterWorking(id, working)
        return manager().getDumpsterById(id)
    }
}
