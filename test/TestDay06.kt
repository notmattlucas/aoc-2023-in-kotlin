import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestDay06 {

    @Test
    fun `should parse races`() {
        val races = Race.parse(listOf(
            "Time:      7  15   30",
            "Distance:  9  40  200")
        )
        assertEquals(listOf(
            Race(7, 9),
            Race(15, 40),
            Race(30, 200)
        ), races)
    }

    @Test
    fun `should supply winning solutions`() {
        assertEquals(4, Race(7, 9).winningStrategies())
    }

    @Test
    fun `should solve part 1 test`() {
        assertEquals(288, solve(readInput("Day06_test")))
    }

    @Test
    fun `should solve part 1`() {
        assertEquals(275724, solve(readInput("Day06")))
    }

    @Test
    fun `should solve part 2 test`() {
        assertEquals(71503, solve(readInput("Day06_part2_test")))
    }

}