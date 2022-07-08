package entities

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import swc.entities.CollectionPoint
import swc.entities.Position

class CollectionPointsTest : DescribeSpec({
    describe("A CollectionPoint") {
        it("should hold correctly initialization values") {
            val position = Position(0L, 0L)
            val cpId = "random-id"
            val cp = CollectionPoint(cpId, position)

            cp.position.latitude shouldBe 0L
            cp.position.longitude shouldBe 0L
            cp.id shouldBe cpId
        }
    }
})
