package day2

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class TestDay02 {

    @Test
    fun shouldParseGameNumber() {
        val input = listOf("Game 7: 1 green")
        val games = parse(input)
        assertEquals(7, games[0].number)
    }

    @Test
    fun shouldParseRounds() {
        val input = listOf("Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red")
        val games = parse(input)
        assertEquals(
            Game(
                4, listOf(
                    Round(mapOf(Color.GREEN to 1, Color.RED to 3, Color.BLUE to 6)),
                    Round(mapOf(Color.GREEN to 3, Color.RED to 6)),
                    Round(mapOf(Color.GREEN to 3, Color.RED to 14, Color.BLUE to 15)),
                )
            ), games[0])
    }

    @Test
    fun shouldAllowBagGreaterThanTotal() {
        val input = listOf("Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red")
        val game = parse(input)[0]
        assertTrue(game.allowed(mapOf(Color.GREEN to 100, Color.RED to 100, Color.BLUE to 100)))
    }

    @Test
    fun shouldNotAllowBagLessThanTotal() {
        val input = listOf("Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red")
        val game = parse(input)[0]
        assertFalse(game.allowed(mapOf(Color.GREEN to 7, Color.RED to 3, Color.BLUE to 6)))
    }

    @Test
    fun gameShouldReportFewestBlocksRequired() {
        val input = listOf(
            "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
            "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
            "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
            "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
            "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"
        )
        val games = parse(input)
        assertEquals(mapOf(Color.RED to 4, Color.GREEN to 2, Color.BLUE to 6), games[0].fewest())
        assertEquals(mapOf(Color.RED to 1, Color.GREEN to 3, Color.BLUE to 4), games[1].fewest())
        assertEquals(mapOf(Color.RED to 20, Color.GREEN to 13, Color.BLUE to 6), games[2].fewest())
        assertEquals(mapOf(Color.RED to 14, Color.GREEN to 3, Color.BLUE to 15), games[3].fewest())
        assertEquals(mapOf(Color.RED to 6, Color.GREEN to 3, Color.BLUE to 2), games[4].fewest())
    }

    @Test
    fun gameShouldProduceMinCubeValue() {
        val input = listOf(
            "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
        )
        val game = parse(input)[0]
        assertEquals(48, game.minCube())
    }

    @Test
    fun gameShouldProduceMinCubeValueSum() {
        val input = listOf(
            "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
            "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
            "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
            "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
            "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"
        )
        val games = parse(input)
        assertEquals(2286, games.sumOf { it.minCube() })
    }

}