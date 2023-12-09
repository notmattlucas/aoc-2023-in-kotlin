package day9

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import readInput

class TestDay09 {

    @Test
    fun `a seq should provide next`() {
        assertEquals(9, Seq.from("0 3 6").next())
    }

    @Test
    fun `a seq should provide deep next`() {
        assertEquals(68, Seq.from("10  13  16  21  30  45").next())
    }

    @Test
    fun `it should solve part 1`() {
        assertEquals(1884768153, part1(readInput("Day09")))
    }

    @Test
    fun `it should solve part 2`() {
        assertEquals(1031, part2(readInput("Day09")))
    }

}