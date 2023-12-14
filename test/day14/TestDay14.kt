package day14

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class TestDay14 {

    @Test
    fun `it should parse the platform`() {
        val input = listOf(
            "O....#....",
            "O.OO#....#",
            ".....##...",
            "OO.#O....O",
            ".O.....O#.",
            "O.#..O.#.#",
            "..O..#O..O",
            ".......O..",
            "#....###..",
            "#OO..#...."
        )
        val platform = Platform.from(input)
        expectThat(platform.columns).isEqualTo(listOf(
            listOf("#", "#", ".", ".", "O", ".", "O", ".", "O", "O"),
            listOf("O", ".", ".", ".", ".", "O", "O", ".", ".", "."),
            listOf("O", ".", ".", "O", "#", ".", ".", ".", "O", "."),
            listOf(".", ".", ".", ".", ".", ".", "#", ".", "O", "."),
            listOf(".", ".", ".", ".", ".", ".", "O", ".", "#", "."),
            listOf("#", "#", ".", "#", "O", ".", ".", "#", ".", "#"),
            listOf(".", "#", ".", "O", ".", ".", ".", "#", ".", "."),
            listOf(".", "#", "O", ".", "#", "O", ".", ".", ".", "."),
            listOf(".", ".", ".", ".", ".", "#", ".", ".", ".", "."),
            listOf(".", ".", ".", "O", "#", ".", "O", ".", "#", "."))
        )
    }

    @Test
    fun `it should roll stones`() {
        val platform = Platform(listOf(
            listOf("O", ".", ".", "O", "#", ".", ".", ".", "O", "."),
            listOf("#", "#", ".", ".", "O", ".", "O", ".", "O", "O")
        ))
        expectThat(platform.tilt().columns).isEqualTo(listOf(
            listOf(".", ".", "O", "O", "#", ".", ".", ".", ".", "O"),
            listOf("#", "#", ".", ".", ".", ".", "O", "O", "O", "O"),
        ))
    }

    @Test
    fun `it should calculate load after tilt`() {
        val input = listOf(
            "O....#....",
            "O.OO#....#",
            ".....##...",
            "OO.#O....O",
            ".O.....O#.",
            "O.#..O.#.#",
            "..O..#O..O",
            ".......O..",
            "#....###..",
            "#OO..#...."
        )
        val platform = Platform.from(input).tilt()
        platform.print()
        expectThat(platform.load()).isEqualTo(136)
    }

    @Test
    fun `it should rotate the grid`() {
        val input = listOf(
            "12",
            "34"
        )
        val original = Platform.from(input)
        expectThat(original.columns).isEqualTo(listOf(
            listOf("3", "1"),
            listOf("4", "2")
        ))

        var rotated = original.rotate()
        expectThat(rotated.columns).isEqualTo(listOf(
            listOf("4", "3"),
            listOf("2", "1")
        ))

        rotated = original.rotate()
        expectThat(rotated.columns).isEqualTo(listOf(
            listOf("2", "4"),
            listOf("1", "3")
        ))

        rotated = original.rotate()
        expectThat(rotated.columns).isEqualTo(listOf(
            listOf("1", "2"),
            listOf("3", "4")
        ))
    }

    @Test
    fun `it should spin the grid`() {
        val input = listOf(
            "O....#....",
            "O.OO#....#",
            ".....##...",
            "OO.#O....O",
            ".O.....O#.",
            "O.#..O.#.#",
            "..O..#O..O",
            ".......O..",
            "#....###..",
            "#OO..#...."
        )
        val platform = Platform.from(input).spin().spin().spin()
        platform.print()
    }

}