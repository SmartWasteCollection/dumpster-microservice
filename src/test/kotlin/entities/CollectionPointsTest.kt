package entities

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import swc.entities.CollectionPoint
import swc.entities.Position

class CollectionPointsTest : DescribeSpec({
    describe("A CollectionPoint") {
        it("should hold correctly initialization values") {
            val position = Position(12.56, 17.34)
            val cpId = "random-id"
            val cp = CollectionPoint(cpId, position)

            cp.position.latitude shouldBe 12.56
            cp.position.longitude shouldBe 17.34
            cp.id shouldBe cpId
        }
    }
})
