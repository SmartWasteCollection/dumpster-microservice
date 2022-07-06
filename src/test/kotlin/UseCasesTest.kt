import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import swc.adapters.DumpsterDeserialization
import swc.adapters.DumpsterDeserialization.toDumpster
import swc.controllers.errors.DumpsterNotFoundException
import swc.entities.Dumpster
import swc.entities.WasteName
import swc.usecases.CreateDumpsterUseCase
import swc.usecases.GetDumpsterByIdUseCase

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
})
