import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import swc.entities.Dimension
import swc.entities.Dumpster
import swc.entities.DumpsterType
import swc.entities.Size
import swc.entities.TypeOfOrdinaryWaste
import swc.entities.Volume
import swc.entities.WasteColor
import swc.entities.WasteName

class EntitiesTest : DescribeSpec({
    describe("A Volume") {
        it("can be initialized with a specific value") {
            val value = 1450.0
            Volume(value).value shouldBe value
        }

        it("can be initialized with the empty() factory") {
            Volume.empty().value shouldBe 0.0
        }

        it("should throw IllegalArgumentException if initialized with a negative number") {
            val exception = shouldThrow<IllegalArgumentException> {
                Volume(-175.5)
            }
            exception.message shouldBe "A Volume value can not be a negative number"
        }

        it("should return the right occupied percentage") {
            val value = 1450.0
            val capacity = 2000.0
            Volume(value).getOccupiedPercentage(capacity) shouldBe (value * 100) / capacity
        }
    }

    describe("TypeOfOrdinaryWaste") {
        it("should be correctly initialized specifying its WasteName") {
            val paperType = TypeOfOrdinaryWaste.from(WasteName.PAPER)
            paperType.wasteColor shouldBe WasteColor.BLUE
            paperType.wasteName shouldBe WasteName.PAPER

            val organicType = TypeOfOrdinaryWaste.from(WasteName.ORGANIC)
            organicType.wasteColor shouldBe WasteColor.BROWN
            organicType.wasteName shouldBe WasteName.ORGANIC

            val unsortedType = TypeOfOrdinaryWaste.from(WasteName.UNSORTED)
            unsortedType.wasteColor shouldBe WasteColor.GRAY
            unsortedType.wasteName shouldBe WasteName.UNSORTED

            val plasticType = TypeOfOrdinaryWaste.from(WasteName.PLASTIC_ALUMINIUM)
            plasticType.wasteColor shouldBe WasteColor.YELLOW
            plasticType.wasteName shouldBe WasteName.PLASTIC_ALUMINIUM

            val glassType = TypeOfOrdinaryWaste.from(WasteName.GLASS)
            glassType.wasteColor shouldBe WasteColor.GREEN
            glassType.wasteName shouldBe WasteName.GLASS
        }
    }

    describe("A Size") {
        it("should be initialized specifying a capacity") {
            val smallValue = 200.0
            val bigValue = 800.0

            val smallSize = Size.from(smallValue)
            smallSize.capacity shouldBe smallValue
            smallSize.dimension shouldBe Dimension.SMALL

            val largeSize = Size.from(bigValue)
            largeSize.capacity shouldBe bigValue
            largeSize.dimension shouldBe Dimension.LARGE
        }

        it("should not be initialized with empty capacity") {
            val exception = shouldThrow<java.lang.IllegalArgumentException> {
                Size.from(0.0)
            }
            exception.message shouldBe "A Size capacity must be a positive value"
        }

        it("should not be initialized with a negative capacity") {
            val exception = shouldThrow<java.lang.IllegalArgumentException> {
                Size.from(-800.0)
            }
            exception.message shouldBe "A Size capacity must be a positive value"
        }
    }

    describe("A DumpsterType") {
        it("should be initialized specifying a capacity and a WasteName") {
            val value = 500.0
            val type = DumpsterType.from(value, WasteName.PAPER)

            type.size.capacity shouldBe value
            type.size.dimension shouldBe Dimension.LARGE
            type.typeOfOrdinaryWaste.wasteColor shouldBe WasteColor.BLUE
            type.typeOfOrdinaryWaste.wasteName shouldBe WasteName.PAPER
        }
    }

    describe("A Dumpster") {
        it("should be initialized specifying a capacity and a WasteName") {
            val value = 1500.0
            val dumpster = Dumpster.from(value, WasteName.GLASS)

            dumpster.isOpen shouldBe false
            dumpster.isWorking shouldBe true
            dumpster.type shouldBe DumpsterType.from(value, WasteName.GLASS)
            dumpster.occupiedVolume shouldBe Volume.empty()
        }
    }
})
