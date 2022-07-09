package swc.usecases.dumpster

import swc.entities.CollectionPoint
import swc.usecases.UseCase
import swc.usecases.collectionpoint.GetCollectionPointByIdUseCase

class GetCollectionPointFromDumpsterIdUseCase(private val id: String) : UseCase<CollectionPoint> {
    override fun execute(): CollectionPoint {
        val cpId = manager().getCollectionPointFromDumpsterId(id)
        return GetCollectionPointByIdUseCase(cpId).execute()
    }
}
