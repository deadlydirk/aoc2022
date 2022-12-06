import Day2.Result.*
import Day2.RockPaperScissors.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.nio.charset.Charset.defaultCharset

class Day2 {

    private val input = javaClass.getResourceAsStream("day2.txt")!!.readBytes().toString(defaultCharset())

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

        abstract fun play(other: RockPaperScissors): Result

        companion object {
            fun from(input: String) = RockPaperScissors.values().first { it.values.contains(input) }
        }
    }

    enum class Result(val score: Int) {
        WINS(6), LOSES(0), DRAW(3);

        fun toNew(other: RockPaperScissors) = when (this) {
            LOSES -> when (other) {
                ROCK -> SCISSORS
                PAPER -> ROCK
                SCISSORS -> PAPER
            }

            DRAW -> other
            WINS -> when (other) {
                ROCK -> PAPER
                PAPER -> SCISSORS
                SCISSORS -> ROCK
            }
        }

        companion object {
            fun from(input: String) = when (input) {
                "X" -> LOSES
                "Y" -> DRAW
                "Z" -> WINS
                else -> throw IllegalArgumentException("wrong")
            }
        }
    }

    @Test
    fun `rock paper scissors part one`() {
        val result = input.split("\n")
            .map { it.split(" ") }
            .map { RockPaperScissors.from(it[0]) to RockPaperScissors.from(it[1]) }
            .sumOf { (opponent, me) -> me.score + me.play(opponent).score }
        assertThat(result).isEqualTo(12855)
    }

    @Test
    fun `rock paper scissors part two`() {
        val result = input.split("\n")
            .map { it.split(" ") }
            .map { RockPaperScissors.from(it[0]) to Result.from(it[1]).toNew(RockPaperScissors.from(it[0])) }
            .sumOf { (opponent, me) -> me.score + me.play(opponent).score }
        assertThat(result).isEqualTo(13726)
    }
}