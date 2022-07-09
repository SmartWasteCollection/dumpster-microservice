package swc.usecases.collectionpoint

import swc.entities.CollectionPoint
import swc.usecases.UseCase

class DeleteCollectionPointUseCase(private val id: String) : UseCase<CollectionPoint> {
    override fun execute(): CollectionPoint {
        val oldCP = manager().getCollectionPointById(id)
        manager().deleteCollectionPoint(id)
        return oldCP
    }
}
