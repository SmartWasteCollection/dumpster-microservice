package usecases

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.timing.eventually
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import swc.controllers.errors.DumpsterNotFoundException
import swc.entities.Dumpster
import swc.entities.Volume
import swc.entities.WasteName
import swc.usecases.dumpster.CloseDumpsterUseCase
import swc.usecases.dumpster.CreateDumpsterUseCase
import swc.usecases.dumpster.DeleteDumpsterUseCase
import swc.usecases.dumpster.GetDumpsterByIdUseCase
import swc.usecases.dumpster.GetDumpstersUseCase
import swc.usecases.dumpster.OpenDumpsterUseCase
import swc.usecases.dumpster.UpdateDumpsterVolumeUseCase
import kotlin.time.Duration.Companion.milliseconds

class DumpsterUseCasesTest : DescribeSpec({

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

            val res = CreateDumpsterUseCase(dumpster).execute()
            res shouldBe dumpster

            DeleteDumpsterUseCase(dumpster.id).execute()
        }
    }

    describe("A OpenDumpsterUseCase") {
        it("should modify the Open property of an available dumpster on Azure Platform") {
            val dumpster = Dumpster.from(500.0, WasteName.ORGANIC)
            val res = CreateDumpsterUseCase(dumpster).execute()
            res.isOpen shouldBe false

            OpenDumpsterUseCase(dumpster.id).execute()
            val remoteDumpster = GetDumpsterByIdUseCase(dumpster.id).execute()
            remoteDumpster.isOpen shouldBe true

            DeleteDumpsterUseCase(dumpster.id).execute()
        }

        it("should not modify the Open property of a non-available dumpster on Azure Platform") {
            val dumpster = Dumpster.from(500.0, WasteName.ORGANIC)
            dumpster.occupiedVolume = Volume(499.0)
            CreateDumpsterUseCase(dumpster).execute()

            OpenDumpsterUseCase(dumpster.id).execute()
            val remoteDumpster = GetDumpsterByIdUseCase(dumpster.id).execute()
            remoteDumpster.isOpen shouldBe false

            DeleteDumpsterUseCase(dumpster.id).execute()
        }

        it("should close the dumpster after timeout") {
            val timeout: Long = 5000
            val dumpster = Dumpster.from(1450.0, WasteName.PAPER)
            CreateDumpsterUseCase(dumpster).execute()
            OpenDumpsterUseCase(dumpster.id, timeout).execute()
            GetDumpsterByIdUseCase(dumpster.id).execute().isOpen shouldBe true

            eventually((timeout + 1000).milliseconds) {
                GetDumpsterByIdUseCase(dumpster.id).execute().isOpen shouldBe false
                DeleteDumpsterUseCase(dumpster.id).execute()
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
            CreateDumpsterUseCase(dumpster).execute()
            val res = GetDumpsterByIdUseCase(dumpster.id).execute()
            res shouldBe dumpster

            DeleteDumpsterUseCase(dumpster.id).execute()
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
            CreateDumpsterUseCase(dumpster).execute()
            CloseDumpsterUseCase(dumpster.id).execute()

            GetDumpsterByIdUseCase(dumpster.id).execute().isOpen shouldBe false

            DeleteDumpsterUseCase(dumpster.id).execute()
        }
    }

    describe("UpdateDumpsterVolumeUseCase") {
        it("should update dumpster's volume") {
            val newVolume = 500.0
            val dumpster = Dumpster.from(1450.0, WasteName.PAPER)
            CreateDumpsterUseCase(dumpster).execute()
            UpdateDumpsterVolumeUseCase(dumpster.id, newVolume).execute()

            GetDumpsterByIdUseCase(dumpster.id).execute().occupiedVolume.value shouldBe newVolume

            DeleteDumpsterUseCase(dumpster.id).execute()
        }
    }
})
