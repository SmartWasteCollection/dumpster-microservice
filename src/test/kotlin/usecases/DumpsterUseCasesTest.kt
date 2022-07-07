package usecases

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.timing.eventually
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import swc.controllers.errors.DumpsterNotFoundException
import swc.entities.CollectionPoint
import swc.entities.Dumpster
import swc.entities.Position
import swc.entities.Volume
import swc.entities.WasteName
import swc.usecases.collectionpoint.CreateCollectionPointUseCase
import swc.usecases.collectionpoint.DeleteCollectionPointUseCase
import swc.usecases.dumpster.CloseDumpsterUseCase
import swc.usecases.dumpster.CreateDumpsterUseCase
import swc.usecases.dumpster.DeleteDumpsterUseCase
import swc.usecases.dumpster.GetDumpsterByIdUseCase
import swc.usecases.dumpster.GetDumpstersUseCase
import swc.usecases.dumpster.OpenDumpsterUseCase
import swc.usecases.dumpster.UpdateDumpsterVolumeUseCase
import kotlin.time.Duration.Companion.milliseconds

class DumpsterUseCasesTest : DescribeSpec({

    fun deleteInstances(dumpster: Dumpster, collectionPoint: CollectionPoint) {
        DeleteDumpsterUseCase(dumpster.id).execute()
        DeleteCollectionPointUseCase(collectionPoint.id).execute()
    }

    describe("A GetDumpsterByIdUseCase") {
        it("should return DumpsterNotFoundException if the dumpster is not in Azure Platform") {
            val exception = shouldThrow<DumpsterNotFoundException> {
                GetDumpsterByIdUseCase("non-existent-id").execute()
            }
            exception.message shouldBe "Dumpster with id non-existent-id not found"
        }
    }

    describe("A CreateDumpsterUseCase") {
        it("should create a new dumpster digital twin") {
            val dumpster = Dumpster.from(500.0, WasteName.ORGANIC)
            val cp = CollectionPoint(position = Position(0L, 0L))

            CreateCollectionPointUseCase(cp).execute()
            CreateDumpsterUseCase(dumpster, cp).execute() shouldBe dumpster

            deleteInstances(dumpster, cp)
        }
    }

    describe("A OpenDumpsterUseCase") {
        it("should modify the Open property of an available dumpster on Azure Platform") {
            val dumpster = Dumpster.from(500.0, WasteName.ORGANIC)
            val cp = CollectionPoint(position = Position(0L, 0L))

            CreateCollectionPointUseCase(cp).execute()
            val res = CreateDumpsterUseCase(dumpster, cp).execute()
            res.isOpen shouldBe false

            OpenDumpsterUseCase(dumpster.id).execute().isOpen shouldBe true

            deleteInstances(dumpster, cp)
        }

        it("should not modify the Open property of a non-available dumpster on Azure Platform") {
            val dumpster = Dumpster.from(500.0, WasteName.ORGANIC)
            dumpster.occupiedVolume = Volume(499.0)
            val cp = CollectionPoint(position = Position(0L, 0L))

            CreateCollectionPointUseCase(cp).execute()
            CreateDumpsterUseCase(dumpster, cp).execute()

            OpenDumpsterUseCase(dumpster.id).execute().isOpen shouldBe false

            deleteInstances(dumpster, cp)
        }

        it("should close the dumpster after timeout") {
            val timeout: Long = 5000
            val dumpster = Dumpster.from(1450.0, WasteName.PAPER)
            val cp = CollectionPoint(position = Position(0L, 0L))

            CreateCollectionPointUseCase(cp).execute()
            CreateDumpsterUseCase(dumpster, cp).execute()
            OpenDumpsterUseCase(dumpster.id, timeout).execute()
            GetDumpsterByIdUseCase(dumpster.id).execute().isOpen shouldBe true

            eventually((timeout + 1000).milliseconds) {
                GetDumpsterByIdUseCase(dumpster.id).execute().isOpen shouldBe false
                deleteInstances(dumpster, cp)
            }
        }
    }

    describe("GetDumpstersUseCase") {
        it("should return the same number of instances in Azure Platform") {
            val dumpsters = GetDumpstersUseCase().execute()

            dumpsters.shouldBeInstanceOf<List<Dumpster>>()
        }
    }

    describe("DeleteDumpsterUseCase") {
        it("should delete the desired digital twin from Azure Platform") {
            val dumpster = Dumpster.from(500.0, WasteName.ORGANIC)
            val cp = CollectionPoint(position = Position(0L, 0L))

            CreateCollectionPointUseCase(cp).execute()
            CreateDumpsterUseCase(dumpster, cp).execute() shouldBe dumpster

            deleteInstances(dumpster, cp)
            val exception = shouldThrow<DumpsterNotFoundException> {
                GetDumpsterByIdUseCase(dumpster.id).execute()
            }
            exception.message shouldBe "Dumpster with id ${dumpster.id} not found"
        }
    }

    describe("CloseDumpsterUseCase") {
        it("should close the dumpster") {
            val dumpster = Dumpster.from(1450.0, WasteName.PAPER)
            dumpster.isOpen = true
            val cp = CollectionPoint(position = Position(0L, 0L))

            CreateCollectionPointUseCase(cp).execute()
            CreateDumpsterUseCase(dumpster, cp).execute()
            CloseDumpsterUseCase(dumpster.id).execute()

            GetDumpsterByIdUseCase(dumpster.id).execute().isOpen shouldBe false

            deleteInstances(dumpster, cp)
        }
    }

    describe("UpdateDumpsterVolumeUseCase") {
        it("should update dumpster's volume") {
            val newVolume = 500.0
            val dumpster = Dumpster.from(1450.0, WasteName.PAPER)
            val cp = CollectionPoint(position = Position(0L, 0L))

            CreateCollectionPointUseCase(cp).execute()
            CreateDumpsterUseCase(dumpster, cp).execute()
            UpdateDumpsterVolumeUseCase(dumpster.id, newVolume).execute()

            GetDumpsterByIdUseCase(dumpster.id).execute().occupiedVolume.value shouldBe newVolume

            deleteInstances(dumpster, cp)
        }
    }
})
