import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.nio.charset.Charset.defaultCharset

class Day5 {

    private val input = javaClass.getResourceAsStream("day5.txt")!!.readBytes().toString(defaultCharset())

    @Test
    fun `part one`() {
        with(
            CrateMover(input.substringBefore("\n\n").split("\n"))
        ) {
            move(
                actionLines = input.substringAfter("\n\n").split("\n")
            )
            assertThat(result).isEqualTo("NTWZZWHFV")
        }
    }

    @Test
    fun `part two`() {
        with(
            CrateMover(
                crateLines = input.substringBefore("\n\n").split("\n"),
                version9001 = true
            )
        ) {
            move(
                actionLines = input.substringAfter("\n\n").split("\n")
            )
            assertThat(result).isEqualTo("BRZGFVBTJ")
        }
    }
}

class CrateMover(
    crateLines: List<String>,
    private val version9001: Boolean = false
) {
    private val stackIndexes: Map<Int, Char> = crateLines.last()
        .toIndexedCharacterIf { it.isDigit() }
        .associate { it.first to it.second }

    private val stacks: MutableMap<String, List<Char>> = crateLines.take(crateLines.size - 1)
        .map { line -> line.toIndexedCharacterIf { it.isUpperCase() }.map { stackIndexes[it.first]!! to it.second } }
        .flatten()
        .groupBy({ it.first.toString() }, { it.second })
        .toMutableMap()

    private fun String.toIndexedCharacterIf(predicate: (Char) -> Boolean) = toCharArray()
        .mapIndexed { index, c -> index to c }
        .filter { predicate(it.second) }

    val result: String
        get() = stacks.entries
            .sortedBy { it.key }
            .map { it.value.first() }
            .joinToString("")

    fun move(actionLines: List<String>) {
        actionLines.forEach { line ->
            val (numberOfCrates, from, to) = line.replace("move ", "").replace(Regex(" from | to "), ",").split(",")
            val stacksFrom = stacks[from]!!.toMutableList()
            stacks[to] = stacksFrom.take(numberOfCrates.toInt()).let { if (!version9001) it.reversed() else it } + stacks[to]!!
            repeat(numberOfCrates.toInt()) { stacksFrom.removeAt(0) }
            stacks[from] = stacksFrom
        }
    }
}
