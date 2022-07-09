package swc.usecases.collectionpoint

import swc.entities.Dumpster
import swc.usecases.UseCase

class GetDumpstersInCollectionPointUseCase(private val id: String) : UseCase<List<Dumpster>> {
    override fun execute() = manager().getDumpstersInCollectionPoint(id)
}
