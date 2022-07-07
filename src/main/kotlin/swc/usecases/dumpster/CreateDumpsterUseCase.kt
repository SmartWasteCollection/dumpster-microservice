package swc.usecases.dumpster

import swc.entities.CollectionPoint
import swc.entities.Dumpster
import swc.usecases.UseCase

class CreateDumpsterUseCase(
    private val dumpster: Dumpster,
    private val collectionPoint: CollectionPoint,
) : UseCase<Dumpster> {
    override fun execute(): Dumpster = manager().createDumpster(dumpster, collectionPoint)
}
