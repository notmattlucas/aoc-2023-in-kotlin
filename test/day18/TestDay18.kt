package day18

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class TestDay18 {

    @Test
    fun `it should parse instructions`() {
        val input = listOf(
            "R 6 (#70c710)",
            "D 5 (#0dc571)"
        )
        val expected = listOf(
            Instruction(Direction.R, 6, "#70c710"),
            Instruction(Direction.D, 5, "#0dc571")
        )
        expectThat(Instruction.parse(input)).isEqualTo(expected)
    }

    @Test
    fun `it should dig grid `() {
        val input = listOf(
            "R 6 (#70c710)",
            "D 5 (#0dc571)",
            "L 2 (#5713f0)",
            "D 2 (#d2c081)",
            "R 2 (#59c680)",
            "D 2 (#411b91)",
            "L 5 (#8ceee2)",
            "U 2 (#caa173)",
            "L 1 (#1b58a2)",
            "U 2 (#caa171)",
            "R 2 (#7807d2)",
            "U 3 (#a77fa3)",
            "L 2 (#015232)",
            "U 2 (#7a21e3)"
        )
        val grid = Grid.dig(Instruction.parse(input))
        expectThat(grid.vertices.take(3)).isEqualTo(
            listOf(
                Triple(6, 9, "#70c710"),
                Triple(6, 4, "#0dc571"),
                Triple(4, 4, "#5713f0")
            )
        )
        expectThat(grid.vertices.last()).isEqualTo(
            Triple(0, 9, "#7a21e3")
        )
    }

    @Test
    fun `it should calculate area within polygon simple`() {
        val grid = Grid(
            listOf(
                Triple(1, 6, "#70c710"),
                Triple(3, 1, "#0dc571"),
                Triple(7, 2, "#5713f0"),
                Triple(4, 4, "#d2c081"),
                Triple(8, 5, "#d2c081")
            ), 0
        )
        expectThat(grid.area()).isEqualTo(16.5)
    }

    @Test
    fun `it should calculate area within polygon `() {
        val input = listOf(
            "R 6 (#70c710)",
            "D 5 (#0dc571)",
            "L 2 (#5713f0)",
            "D 2 (#d2c081)",
            "R 2 (#59c680)",
            "D 2 (#411b91)",
            "L 5 (#8ceee2)",
            "U 2 (#caa173)",
            "L 1 (#1b58a2)",
            "U 2 (#caa171)",
            "R 2 (#7807d2)",
            "U 3 (#a77fa3)",
            "L 2 (#015232)",
            "U 2 (#7a21e3)"
        )
        val grid = Grid.dig(Instruction.parse(input))
        expectThat(grid.area()).isEqualTo(42.0)
        expectThat(grid.inner()).isEqualTo(24.0)
        expectThat(grid.points()).isEqualTo(62.0)
    }

    @Test
    fun `it should hex parse`() {
        val input = listOf(
            "R 6 (#70c710)",
            "D 5 (#0dc571)",
            "L 2 (#5713f0)",
            "D 2 (#d2c081)",
            "R 2 (#59c680)",
            "D 2 (#411b91)",
            "L 5 (#8ceee2)",
            "U 2 (#caa173)",
            "L 1 (#1b58a2)",
            "U 2 (#caa171)",
            "R 2 (#7807d2)",
            "U 3 (#a77fa3)",
            "L 2 (#015232)",
            "U 2 (#7a21e3)"
        )
        val instructions = Instruction.parse(input).map { it.fromHex() }
        expectThat(instructions.take(3)).isEqualTo(
            listOf(
                Instruction(Direction.R, 461937, "#70c710"),
                Instruction(Direction.D, 56407, "#0dc571"),
                Instruction(Direction.R, 356671, "#5713f0")
            )
        )
        expectThat(Grid.dig(instructions).points()).isEqualTo(952408144115.0)
    }

}