package swc.usecases.collectionpoint

import swc.entities.CollectionPoint
import swc.usecases.UseCase

class GetCollectionPointsUseCase : UseCase<List<CollectionPoint>> {
    override fun execute() = manager().getCollectionPoints()
}
