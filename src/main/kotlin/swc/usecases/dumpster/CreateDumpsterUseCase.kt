package swc.usecases.dumpster

import swc.entities.Dumpster
import swc.usecases.UseCase

class CreateDumpsterUseCase(
    private val dumpster: Dumpster,
    private val collectionPointId: String,
) : UseCase<Dumpster> {
    override fun execute(): Dumpster = manager().createDumpster(dumpster, collectionPointId)
}
