package day1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TestDay01 {

    @Test
    fun testExtractPlainNumber() {
        assertEquals(12, extractNumber("12"))
    }

    @Test
    fun testExtractMixedNumber() {
        assertEquals(45, extractNumber("4abc5"))
        assertEquals(45, extractNumber("abc4def5"))
        assertEquals(45, extractNumber("4abc5def"))
        assertEquals(45, extractNumber("abc4def5ghi"))
        assertEquals(45, extractNumber("abc4d7e8f5ghi"))
    }

    @Test
    fun testMapNumber() {
        assertEquals("0", translateNumber("zero"))
        assertEquals("1", translateNumber("one"))
        assertEquals("2", translateNumber("two"))
        assertEquals("3", translateNumber("three"))
        assertEquals("4", translateNumber("four"))
        assertEquals("5", translateNumber("five"))
        assertEquals("6", translateNumber("six"))
        assertEquals("7", translateNumber("seven"))
        assertEquals("8", translateNumber("eight"))
        assertEquals("9", translateNumber("nine"))
    }

    @Test
    fun shouldMapEmbeddedNumber() {
        assertEquals("219", translateNumber("two1nine"))
        assertEquals("823", translateNumber("eightwothree"))
        assertEquals("123", translateNumber("abcone2threexyz"))
        assertEquals("2134", translateNumber("xtwone3four"))
        assertEquals("49872", translateNumber("4nineeightseven2"))
        assertEquals("18234", translateNumber("zoneight234"))
        assertEquals("76", translateNumber("7pqrstsixteen"))
    }

}