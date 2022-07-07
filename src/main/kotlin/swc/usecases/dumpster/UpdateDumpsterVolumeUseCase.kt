package swc.usecases.dumpster

import swc.entities.Dumpster
import swc.usecases.UseCase

class UpdateDumpsterVolumeUseCase(
    private val id: String,
    private val newVolume: Double
) : UseCase<Dumpster> {
    override fun execute(): Dumpster {
        manager().updateVolume(id, newVolume)
        return manager().getDumpsterById(id)
    }
}
