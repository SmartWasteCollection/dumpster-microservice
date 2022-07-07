package usecases

import io.kotest.core.spec.style.DescribeSpec
import swc.adapters.CollectionPointsSerialization.toJson
import swc.entities.CollectionPoint
import swc.entities.Position
import swc.usecases.collectionpoint.CreateCollectionPointUseCase

class CollectionPointUseCasesTest : DescribeSpec({
    describe("CreateCollectionPointUseCase") {
        it("should create the digital twin") {
            val cp = CollectionPoint("CollectionPoint0", Position(0L, 0L))
            println(cp.toJson())
            CreateCollectionPointUseCase(cp).execute()
        }
    }
})
