
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import swc.adapters.DumpsterDeserialization
import swc.adapters.DumpsterDeserialization.toDumpster
import swc.controllers.AzureAuthentication
import swc.controllers.AzureQueries
import swc.controllers.errors.DumpsterNotFoundException
import swc.entities.Dumpster
import swc.entities.Volume
import swc.entities.WasteName
import swc.usecases.CreateDumpsterUseCase
import swc.usecases.GetDumpsterByIdUseCase
import swc.usecases.GetDumpstersUseCase
import swc.usecases.OpenDumpsterUseCase

class UseCasesTest : DescribeSpec({
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
            DumpsterDeserialization.parse(res).toDumpster() shouldBe dumpster

            val remoteDumpster = GetDumpsterByIdUseCase(dumpster.id).execute()
            remoteDumpster shouldBe dumpster
        }
    }

    describe("A OpenDumpsterUseCase") {
        it("should modify the Open property of an available dumpster on Azure Platform") {
            val dumpster = Dumpster.from(500.0, WasteName.ORGANIC)
            val res = CreateDumpsterUseCase(dumpster).execute()
            DumpsterDeserialization.parse(res).toDumpster().isOpen shouldBe false

            OpenDumpsterUseCase(dumpster.id).execute()
            val remoteDumpster = GetDumpsterByIdUseCase(dumpster.id).execute()
            remoteDumpster.isOpen shouldBe true
        }

        it("should not modify the Open property of a non-available dumpster on Azure Platform") {
            val dumpster = Dumpster.from(500.0, WasteName.ORGANIC)
            dumpster.occupiedVolume = Volume(499.0)
            CreateDumpsterUseCase(dumpster).execute()

            OpenDumpsterUseCase(dumpster.id).execute()
            val remoteDumpster = GetDumpsterByIdUseCase(dumpster.id).execute()
            remoteDumpster.isOpen shouldBe false
        }
    }

    describe("GetDumpstersUseCase") {
        it("should return the same number of instances in Azure Platform") {
            val dumpsters = GetDumpstersUseCase().execute()
            val count = AzureAuthentication.authClient.query(AzureQueries.GET_DUMPSTERS_COUNT, String::class.java).first()

            dumpsters.size shouldBe DumpsterDeserialization.parse(count)["COUNT"].asInt
        }
    }
})
