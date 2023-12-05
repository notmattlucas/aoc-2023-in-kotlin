import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestDay05 {

    @Test
    fun `it should parse seeds`() {
        val almanac = Almanac.from(listOf("seeds: 79 14 55 13"))
        assertEquals(listOf(79L, 14L, 55L, 13L), almanac.seeds)
    }

    @Test
    fun `it should parse range maps`() {
        val almanac = Almanac.from(listOf(
            "seeds: 79 14 55 13",
            "",
            "seed-to-soil map:",
            "50 98 2",
            "52 50 48",
            "",
            "soil-to-fertilizer map:",
            "0 15 37",
            "37 52 2",
            "39 0 15"
        ))
        assertEquals(81, almanac.lookup("seed-to-soil", 79))
        assertEquals(14, almanac.lookup("seed-to-soil", 14))
        assertEquals(57, almanac.lookup("seed-to-soil", 55))
        assertEquals(13, almanac.lookup("seed-to-soil", 13))
    }

    @Test
    fun `it should give ranges of seed`() {
        val almanac = Almanac.from(listOf(
            "seeds: 79 14 55 13",
            "",
            "seed-to-soil map:",
            "50 98 2",
            "52 50 48",
        ))
        assertEquals(listOf(10L .. 20L), almanac.lookup("seed-to-soil", 10L..20L))
        assertEquals(listOf(52L .. 53L), almanac.lookup("seed-to-soil", 50L..51L))
        assertEquals(listOf(10L .. 49L, 52L .. 53L), almanac.lookup("seed-to-soil", 10..51L))
        assertEquals(listOf(0L .. 49L, 52L .. 59L), almanac.lookup("seed-to-soil", 0..57L))
    }

}