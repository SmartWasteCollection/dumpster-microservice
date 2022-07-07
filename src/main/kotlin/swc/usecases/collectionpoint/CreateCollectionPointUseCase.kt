package swc.usecases.collectionpoint

import swc.entities.CollectionPoint
import swc.usecases.UseCase

class CreateCollectionPointUseCase(private val collectionPoint: CollectionPoint) : UseCase<CollectionPoint> {
    override fun execute() = manager().createCollectionPoint(collectionPoint)
}
