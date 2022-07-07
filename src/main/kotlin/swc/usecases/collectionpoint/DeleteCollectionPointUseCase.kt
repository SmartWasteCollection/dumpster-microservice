package swc.usecases.collectionpoint

import swc.usecases.UseCase

class DeleteCollectionPointUseCase(private val id: String) : UseCase<Unit> {
    override fun execute() = manager().deleteCollectionPoint(id)
}
