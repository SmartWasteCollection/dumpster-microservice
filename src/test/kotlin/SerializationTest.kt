import com.google.gson.JsonObject
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import swc.adapters.CollectionPointsDeserialization.toCollectionPoint
import swc.adapters.CollectionPointsSerialization.toJson
import swc.adapters.DumpsterDeserialization.toDumpster
import swc.adapters.DumpsterSerialization.toJson
import swc.entities.CollectionPoint
import swc.entities.Dumpster
import swc.entities.Position
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
            typeAfter.dumpsterType shouldBe typeBefore.dumpsterType
            typeAfter.isOpen shouldBe typeBefore.isOpen
            typeAfter.isWorking shouldBe typeBefore.isWorking
            typeAfter.occupiedVolume shouldBe typeBefore.occupiedVolume
        }
    }

    describe("A CollectionPoint") {
        it("should be serialized and deserialized correctly") {
            val typeBefore = CollectionPoint(position = Position(0L, 0L))

            val typeSerialized = typeBefore.toJson()
            typeSerialized.shouldBeInstanceOf<JsonObject>()

            val typeAfter = typeSerialized.toCollectionPoint()
            typeAfter.shouldBeInstanceOf<CollectionPoint>()
            typeAfter.id shouldBe typeBefore.id
            typeAfter.position shouldBe typeBefore.position
        }
    }
})
