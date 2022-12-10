import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.nio.charset.Charset.defaultCharset

class Day6 {

    private val input = javaClass.getResourceAsStream("day6.txt")!!.readBytes().toString(defaultCharset())

    @CsvSource(
        value = [
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb,7,19",
            "bvwbjplbgvbhsrlpgdmjqwftvncz,5,23",
            "nppdvjthqldpwncqszvftbrmjlhg,6,23",
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg,10,29",
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw,11,26",
        ]
    )
    @ParameterizedTest
    fun `example `(input: String, partOneMarker: Int, partTwoMarker: Int) {
        val resultPartOne = firstMarkerWith4ConsecutiveDistinctCharactersFor(input, 4)
        assertThat(resultPartOne).isEqualTo(partOneMarker)

        val resultPartTwo = firstMarkerWith4ConsecutiveDistinctCharactersFor(input, 14)
        assertThat(resultPartTwo).isEqualTo(partTwoMarker)
    }

    @Test
    fun `part one`() {
        assertThat(firstMarkerWith4ConsecutiveDistinctCharactersFor(input, 4)).isEqualTo(1155)
    }

    @Test
    fun `part two`() {
        assertThat(firstMarkerWith4ConsecutiveDistinctCharactersFor(input, 14)).isEqualTo(2789)
    }

    private fun firstMarkerWith4ConsecutiveDistinctCharactersFor(
        input: String,
        numberOfDistinctCharacters: Int
    ) = numberOfDistinctCharacters + input.toCharArray()
        .toList()
        .windowed(numberOfDistinctCharacters)
        .indexOfFirst { it.distinct().size == numberOfDistinctCharacters }

}