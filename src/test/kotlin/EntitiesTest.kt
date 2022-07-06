import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
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

        it("can be initialized with an empty value by default") {
            Volume().value shouldBe 0.0
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

        it("should be equals to another TypeOfOrdinaryWaste with the same values") {
            val type1 = TypeOfOrdinaryWaste.from(WasteName.PAPER)
            val type2 = TypeOfOrdinaryWaste.from(WasteName.PAPER)

            type1 shouldBe type2
        }

        it("should not be equals to another TypeOfOrdinaryWaste with different values") {
            val type1 = TypeOfOrdinaryWaste.from(WasteName.PAPER)
            val type2 = TypeOfOrdinaryWaste.from(WasteName.GLASS)

            type1 shouldNotBe type2
        }
    }

    describe("A Size") {
        val smallValue = 200.0
        val bigValue = 800.0

        it("should be initialized specifying a capacity") {
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

        it("should be equals to another Size with the same value") {
            val size1 = Size.from(smallValue)
            val size2 = Size.from(smallValue)

            size1 shouldBe size2
        }

        it("should not be equals to another Size with a different value") {
            val size1 = Size.from(smallValue)
            val size2 = Size.from(bigValue)

            size1 shouldNotBe size2
        }
    }

    describe("A DumpsterType") {
        val value = 500.0

        it("should be initialized specifying a capacity and a WasteName") {
            val type = DumpsterType.from(value, WasteName.PAPER)

            type.size.capacity shouldBe value
            type.size.dimension shouldBe Dimension.LARGE
            type.typeOfOrdinaryWaste.wasteColor shouldBe WasteColor.BLUE
            type.typeOfOrdinaryWaste.wasteName shouldBe WasteName.PAPER
        }

        it("should be equals to another DumpsterType with same values") {
            val type1 = DumpsterType.from(value, WasteName.PAPER)
            val type2 = DumpsterType.from(value, WasteName.PAPER)

            type1 shouldBe type2
        }

        it("should not be equals to another DumpsterType with different values") {
            val type1 = DumpsterType.from(value, WasteName.PAPER)
            val type2 = DumpsterType.from(value * 2, WasteName.PAPER)

            type1 shouldNotBe type2
        }
    }

    describe("A Dumpster") {
        val value = 1500.0
        val customId = "custom-id"

        it("should be initialized specifying a capacity and a WasteName") {
            val dumpster = Dumpster.from(value, WasteName.GLASS)

            dumpster.isOpen shouldBe false
            dumpster.isWorking shouldBe true
            dumpster.type shouldBe DumpsterType.from(value, WasteName.GLASS)
            dumpster.occupiedVolume shouldBe Volume()
        }

        it("should be initialized providing also id and name") {
            val dumpster = Dumpster.from(customId, value, WasteName.GLASS)

            dumpster.id shouldBe customId
        }

        it("should be equal to another dumpster with the same id") {
            val dumpster1 = Dumpster.from(customId, value, WasteName.GLASS)
            val dumpster2 = Dumpster.from(customId, value, WasteName.GLASS)

            dumpster1 shouldBe dumpster2
        }

        it("shouldn't be equal to another dumpster with a different id") {
            val dumpster1 = Dumpster.from(value, WasteName.GLASS)
            val dumpster2 = Dumpster.from(value, WasteName.GLASS)

            dumpster1 shouldNotBe dumpster2
        }

        it("should be properly available depending on its occupiedVolume") {
            val dumpster = Dumpster.from(value, WasteName.UNSORTED)
            dumpster.isAvailable() shouldBe true
            dumpster.isWorking = false
            dumpster.isAvailable() shouldBe false
            dumpster.isWorking = true
            dumpster.occupiedVolume = Volume(value)
            dumpster.isAvailable() shouldBe false
        }
    }
})
