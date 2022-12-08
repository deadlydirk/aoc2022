import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.Test
import java.nio.charset.Charset.defaultCharset
import kotlin.text.CharCategory.LOWERCASE_LETTER
import kotlin.text.CharCategory.UPPERCASE_LETTER

class Day3 {

    private val input = javaClass.getResourceAsStream("day3.txt")!!.readBytes().toString(defaultCharset())

    val example = """
        vJrwpWtwJgWrhcsFMMfFFhFp
        jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        PmmdzqPrVvPwwTWBwg
        wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        ttgJtRGJQctTZtZT
        CrZsJsPPZsGzwwsLwLmpwMDw
    """.trimIndent()

    @Test
    fun `part one - example`() {
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
        val result = input.split("\n")
            .sumOf { Rucksack(it).calculateCommonPriority() }
        assertThat(result).isEqualTo(8123)
    }

    @Test
    fun `part two`() {
        val result = input.split("\n")
            .map { it.toCharArray().toSet() }
            .chunked(3)
            .sumOf { (first, second, third) -> first.intersect(second).intersect(third).first().toPriority() }
        assertThat(result).isEqualTo(2620)
    }
}

class Rucksack(input: String) {
    val compartment1: String = input.substring(0, input.length / 2)
    val compartment2: String = input.substring(input.length / 2)

    fun calculateCommonPriority() = compartment1.toCharArray().toSet()
        .intersect(compartment2.toCharArray().toSet())
        .first()
        .toPriority()
}

fun Char.toPriority() = when (category) {
    UPPERCASE_LETTER -> code - 38
    LOWERCASE_LETTER -> code - 96
    else -> 0
}
