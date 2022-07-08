package swc.usecases.collectionpoint

import swc.entities.CollectionPoint
import swc.usecases.UseCase

class GetCollectionPointFromDumpsterIdUseCase(private val id: String) : UseCase<CollectionPoint> {
    override fun execute(): CollectionPoint {
        val cpId = manager().getCollectionPointFromDumspterId(id)
        return GetCollectionPointByIdUseCase(cpId).execute()
    }
}
