package usecases

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import swc.controllers.errors.CollectionPointNotFoundException
import swc.entities.CollectionPoint
import swc.entities.Position
import swc.usecases.collectionpoint.CreateCollectionPointUseCase
import swc.usecases.collectionpoint.DeleteCollectionPointUseCase
import swc.usecases.collectionpoint.GetCollectionPointByIdUseCase

class CollectionPointUseCasesTest : DescribeSpec({
    describe("CreateCollectionPointUseCase") {
        it("should create the digital twin inside Azure Platform") {
            val cp = CollectionPoint(position = Position(0L, 0L))
            CreateCollectionPointUseCase(cp).execute()
            GetCollectionPointByIdUseCase(cp.id).execute() shouldBe cp
            DeleteCollectionPointUseCase(cp.id).execute()
        }
    }

    describe("DeleteCollectionPointUseCase") {
        it("should delete the digital twin from Azure Platform") {
            val cp = CollectionPoint(position = Position(0L, 0L))
            CreateCollectionPointUseCase(cp).execute()
            DeleteCollectionPointUseCase(cp.id).execute()
            val exception = shouldThrow<CollectionPointNotFoundException> {
                GetCollectionPointByIdUseCase(cp.id).execute()
            }
            exception.message shouldBe "CollectionPoint with id ${cp.id} not found"
        }
    }
})
