import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.nio.charset.Charset.defaultCharset

class Day1 {

    private val input = javaClass.getResourceAsStream("day1.txt")!!.readAllBytes().toString(defaultCharset())

    @Test
    fun `day one part one - total calories carried by the strongest elf`() {
        val maxCalories = input
            .split("\n\n")
            .maxOfOrNull { it.split("\n").sumOf(String::toInt) }
        assertThat(maxCalories).isEqualTo(69912)
    }

    @Test
    fun `day one part two - total calories carried by the top three strongest elves`() {
        val topThreeCaloriesTotal = input
            .split("\n\n")
            .map { it.split("\n").sumOf(String::toInt) }
            .sortedDescending()
            .take(3)
            .sum()
        assertThat(topThreeCaloriesTotal).isEqualTo(208180)
    }
}