package swc.usecases.collectionpoint

import swc.entities.CollectionPoint
import swc.usecases.UseCase

class GetCollectionPointByIdUseCase(private val id: String) : UseCase<CollectionPoint> {
    override fun execute(): CollectionPoint = manager().getCollectionPointById(id)
}
