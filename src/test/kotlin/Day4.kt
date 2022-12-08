import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.nio.charset.Charset

class Day4 {

    private val input = javaClass.getResourceAsStream("day4.txt")!!.readBytes().toString(Charset.defaultCharset())

    @Test
    fun `part one`() {
        val result = input.split("\n")
            .map { it.split(",") }
            .map { (first, second) -> first.toRange() to second.toRange() }
            .count { it.first.containsAll(it.second) || it.second.containsAll(it.first) }
        assertThat(result).isEqualTo(567)
    }

    @Test
    fun `part two`() {
        val result = input.split("\n")
            .map { it.split(",") }
            .map { (first, second) -> first.toRange() to second.toRange() }
            .count { it.first.intersect(it.second).isNotEmpty() || it.second.intersect(it.first).isNotEmpty() }
        assertThat(result).isEqualTo(907)
    }
}

fun String.toRange() = this
    .split("-")
    .let { (first, second) -> first.toInt()..second.toInt() }
    .toSet()