import com.google.gson.JsonObject
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import swc.adapters.DumpsterDeserialization.toDumpster
import swc.adapters.DumpsterSerialization.toJson
import swc.entities.Dumpster
import swc.entities.WasteName

class SerializationTest : DescribeSpec({
    describe("A Dumpster") {
        it("should be serialized and deserialized correctly") {
            val typeBefore = Dumpster.from(500.0, WasteName.PAPER)

            val typeSerialized = typeBefore.toJson()
            typeSerialized.shouldBeInstanceOf<JsonObject>()

            val typeAfter = typeSerialized.toDumpster()
            typeAfter.shouldBeInstanceOf<Dumpster>()
            typeAfter.id shouldBe typeBefore.id
            typeAfter.type shouldBe typeBefore.type
            typeAfter.isOpen shouldBe typeBefore.isOpen
            typeAfter.isWorking shouldBe typeBefore.isWorking
            typeAfter.occupiedVolume shouldBe typeBefore.occupiedVolume
        }
    }
})
