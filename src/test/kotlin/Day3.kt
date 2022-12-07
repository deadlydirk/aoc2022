import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.Test
import java.nio.charset.Charset
import kotlin.text.CharCategory.LOWERCASE_LETTER
import kotlin.text.CharCategory.UPPERCASE_LETTER

class Day3 {

    private val input = javaClass.getResourceAsStream("day3.txt")!!.readBytes().toString(Charset.defaultCharset())

    class Rucksack(input: String) {
        val compartment1: String = input.substring(0, input.length / 2)
        val compartment2: String = input.substring(input.length / 2)

        fun calculateCommonPriority() = compartment1.toCharArray().toSet()
            .intersect(compartment2.toCharArray().toSet())
            .first()
            .let {
                when (it.category) {
                    UPPERCASE_LETTER -> it.code - 38
                    LOWERCASE_LETTER -> it.code - 96
                    else -> 0
                }
            }

    }

    val example = """
        vJrwpWtwJgWrhcsFMMfFFhFp
        jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        PmmdzqPrVvPwwTWBwg
        wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        ttgJtRGJQctTZtZT
        CrZsJsPPZsGzwwsLwLmpwMDw
    """.trimIndent()

    @Test
    fun example() {
        val result = example
            .split("\n")
            .map { Rucksack(it) }

        assertThat(result).extracting({ it.compartment1 }, { it.compartment2 })
            .contains(
                tuple("vJrwpWtwJgWr", "hcsFMMfFFhFp"),
                tuple("jqHRNqRjqzjGDLGL", "rsFMfFZSrLrFZsSL"),
                tuple("PmmdzqPrV", "vPwwTWBwg"),
            )
        assertThat(result)
            .flatExtracting({ it.calculateCommonPriority() })
            .containsExactlyInAnyOrder(16, 38, 42, 22, 20, 19)
        assertThat(result.sumOf { it.calculateCommonPriority() }).isEqualTo(157)
    }

    @Test
    fun `part one - calculate common priorities`() {
        val result = input
            .split("\n")
            .sumOf { Rucksack(it).calculateCommonPriority() }
        assertThat(result).isEqualTo(8123)
    }
}