import Day2.Result.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.nio.charset.Charset

class Day2 {

    private val input = javaClass.getResourceAsStream("day2.txt")!!.readAllBytes().toString(Charset.defaultCharset())

    enum class RockPaperScissors(private val values: Array<String>, val score: Int) {
        ROCK(arrayOf("A", "X"), 1) {
            override fun play(other: RockPaperScissors) = when (other) {
                ROCK -> DRAW
                PAPER -> LOSES
                SCISSORS -> WINS
            }
        },
        PAPER(arrayOf("B", "Y"), 2) {
            override fun play(other: RockPaperScissors) = when (other) {
                ROCK -> WINS
                PAPER -> DRAW
                SCISSORS -> LOSES
            }
        },
        SCISSORS(arrayOf("C", "Z"), 3) {
            override fun play(other: RockPaperScissors) = when (other) {
                ROCK -> LOSES
                PAPER -> WINS
                SCISSORS -> DRAW
            }
        };

        abstract fun play(rockPaperScissors: RockPaperScissors): Result

        companion object {
            fun from(input: String) = RockPaperScissors.values().first { it.values.contains(input) }
        }
    }

    enum class Result(val score: Int) {
        WINS(6), LOSES(0), DRAW(3)
    }

    @Test
    fun `rock paper scissors example`() {
        val total = listOf(
            "A" to "Y", // Rock -> Paper        -> win  -> 2 + 6 = 8
            "B" to "X", // Paper -> Rock        -> loss -> 1 + 0 = 1
            "C" to "Z", // Scissors -> Scissors -> draw -> 3 + 3 = 6 -> total: 15
        )
            .map { RockPaperScissors.from(it.first) to RockPaperScissors.from(it.second) }
            .sumOf { (opponent, me) -> me.play(opponent).score + me.score }
        assertThat(total).isEqualTo(15)
    }

    @Test
    fun `rock paper scissors part one`() {
        val total = input.split("\n")
            .map { it.split(" ") }
            .map { RockPaperScissors.from(it[0]) to RockPaperScissors.from(it[1]) }
            .sumOf { (opponent, me) -> me.play(opponent).score + me.score }
        assertThat(total).isEqualTo(12855)
    }


    @Test
    fun `rock paper scissors part two`() {
        TODO()
    }
}