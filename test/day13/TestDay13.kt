package day13

import org.junit.jupiter.api.Test
import readInput
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class TestDay13 {

    @Test
    fun `it should read map`() {
        val map = MirrorMap.fromOne(listOf(
            "#.##..##.",
            "..#.##.#.",
            "##......#",
            "##......#",
            "..#.##.#.",
            "..##..##.",
            "#.#.##.#."
        ))
        expectThat(map.rows).isEqualTo(listOf(
            "#.##..##.",
            "..#.##.#.",
            "##......#",
            "##......#",
            "..#.##.#.",
            "..##..##.",
            "#.#.##.#."
        ))
        expectThat(map.columns).isEqualTo(listOf(
            "#.##..#",
            "..##...",
            "##..###",
            "#....#.",
            ".#..#.#",
            ".#..#.#",
            "#....#.",
            "##..###",
            "..##..."
        ))
    }

    @Test
    fun `it should be able to find vertical mirroring`() {
        val map = MirrorMap.fromOne(listOf(
            "#.##..##.",
            "..#.##.#.",
            "##......#",
            "##......#",
            "..#.##.#.",
            "..##..##.",
            "#.#.##.#."
        ))
        expectThat(map.vertical()).isEqualTo(5)
    }

    @Test
    fun `it should find horizontal mirroring`() {
        val map = MirrorMap.fromOne(listOf(
            "#...##..#",
            "#....#..#",
            "..##..###",
            "#####.##.",
            "#####.##.",
            "..##..###",
            "#....#..#"
        ))
        expectThat(map.horizontal()).isEqualTo(400)
    }

    @Test
    fun `it should find summarize`() {
        val result = part1(listOf(
            "#.##..##.",
            "..#.##.#.",
            "##......#",
            "##......#",
            "..#.##.#.",
            "..##..##.",
            "#.#.##.#.",
            "",
            "#...##..#",
            "#....#..#",
            "..##..###",
            "#####.##.",
            "#####.##.",
            "..##..###",
            "#....#..#",
        ))
        expectThat(result).isEqualTo(405)
    }

    @Test
    fun `it should find summarize2`() {
        val result = part1(listOf(
            "#####.##.",
            "....#....",
            "....#####",
            ".##..####",
            "####.####",
            "#####....",
            "#####.##.",
            ".##..####",
            ".##......",
            ".##.#.##.",
            "#..##.##.",
            "....#.##.",
            "#..#.##.#",
            "#..#..##.",
            "#..#..##.",
            "####.....",
            "#####.##."
        ))
        expectThat(result).isEqualTo(2)
    }

    @Test
    fun `should solve part 1`() {
        val result = part1(readInput("Day13"))
        expectThat(result).isEqualTo(33780)
    }

    @Test
    fun `should clean mirror`() {
        val polished = MirrorMap.polish(
            listOf(
                "#.#",
                ".#.",
                "#.#"
            )
        )
        expectThat(polished).isEqualTo(
            listOf(
                listOf(
                    "..#",
                    ".#.",
                    "#.#"
                ),
                listOf(
                    "###",
                    ".#.",
                    "#.#"
                ),
                listOf(
                    "#..",
                    ".#.",
                    "#.#"
                ),
                listOf(
                    "#.#",
                    "##.",
                    "#.#"
                ),
                listOf(
                    "#.#",
                    "...",
                    "#.#"
                ),
                listOf(
                    "#.#",
                    ".##",
                    "#.#"
                ),
                listOf(
                    "#.#",
                    ".#.",
                    "..#"
                ),
                listOf(
                    "#.#",
                    ".#.",
                    "###"
                ),
                listOf(
                    "#.#",
                    ".#.",
                    "#.."
                )
            )
        )
    }

    @Test
    fun `it should find summarize part 2`() {
        val result = part2(listOf(
            "#.##..##.",
            "..#.##.#.",
            "##......#",
            "##......#",
            "..#.##.#.",
            "..##..##.",
            "#.#.##.#.",
            "",
            "#...##..#",
            "#....#..#",
            "..##..###",
            "#####.##.",
            "#####.##.",
            "..##..###",
            "#....#..#",
        ))
        expectThat(result).isEqualTo(400)
    }

}