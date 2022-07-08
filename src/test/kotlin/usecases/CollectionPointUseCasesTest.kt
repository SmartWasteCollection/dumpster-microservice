package usecases

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import swc.controllers.errors.CollectionPointNotFoundException
import swc.entities.CollectionPoint
import swc.entities.Dumpster
import swc.entities.Position
import swc.entities.WasteName
import swc.usecases.collectionpoint.CreateCollectionPointUseCase
import swc.usecases.collectionpoint.DeleteCollectionPointUseCase
import swc.usecases.collectionpoint.GetCollectionPointByIdUseCase
import swc.usecases.collectionpoint.GetDumpstersInCollectionPointUseCase
import swc.usecases.dumpster.CreateDumpsterUseCase
import swc.usecases.dumpster.DeleteDumpsterUseCase

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

    describe("GetDumpstersInCollectionPointUseCase") {
        it("should return the correct list of dumpster") {
            val dumpster1 = Dumpster.from(1450.0, WasteName.PAPER)
            val dumpster2 = Dumpster.from(1450.0, WasteName.PAPER)
            val cp = CollectionPoint(position = Position(0L, 0L))

            CreateCollectionPointUseCase(cp).execute()
            CreateDumpsterUseCase(dumpster1, cp).execute()
            CreateDumpsterUseCase(dumpster2, cp).execute()

            val list = GetDumpstersInCollectionPointUseCase(cp.id).execute()
            val resList = listOf(dumpster1, dumpster2)
            list[0] shouldBeIn resList
            list[1] shouldBeIn resList

            DeleteDumpsterUseCase(dumpster1.id).execute()
            DeleteDumpsterUseCase(dumpster2.id).execute()
            DeleteCollectionPointUseCase(cp.id).execute()
        }
    }
})
