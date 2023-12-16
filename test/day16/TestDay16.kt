package day16

import day16.Cell.Companion.north
import day16.Cell.Companion.west
import day16.Cell.Companion.east
import day16.Cell.Companion.south
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class TestDay16 {

    @Test
    fun `empty cell should maintain direction`() {
        val cell = Cell.of('.', 5 to 5)
        expectThat(cell.next(north)).isEqualTo(listOf(Pair(5 to 6, north)))
        expectThat(cell.next(east)).isEqualTo(listOf(Pair(6 to 5, east)))
        expectThat(cell.next(south)).isEqualTo(listOf(Pair(5 to 4, south)))
        expectThat(cell.next(west)).isEqualTo(listOf(Pair(4 to 5, west)))
    }

    @Test
    fun `should handle forward right angle`() {
        val cell = Cell.of('/', 5 to 5)
        expectThat(cell.next(north)).isEqualTo(listOf(Pair(6 to 5, east)))
        expectThat(cell.next(east)).isEqualTo(listOf(Pair(5 to 6, north)))
        expectThat(cell.next(south)).isEqualTo(listOf(Pair(4 to 5, west)))
        expectThat(cell.next(west)).isEqualTo(listOf(Pair(5 to 4, south)))
    }

    @Test
    fun `should handle backwards right angle`() {
        val cell = Cell.of('\\', 5 to 5)
        expectThat(cell.next(north)).isEqualTo(listOf(Pair(4 to 5, west)))
        expectThat(cell.next(east)).isEqualTo(listOf(Pair(5 to 4, south)))
        expectThat(cell.next(south)).isEqualTo(listOf(Pair(6 to 5, east)))
        expectThat(cell.next(west)).isEqualTo(listOf(Pair(5 to 6, north)))
    }

    @Test
    fun `should handle horizontal splitter`() {
        val cell = Cell.of('-', 5 to 5)
        expectThat(cell.next(north)).isEqualTo(listOf(Pair(4 to 5, west), Pair(6 to 5, east)))
        expectThat(cell.next(east)).isEqualTo(listOf(Pair(6 to 5, east)))
        expectThat(cell.next(south)).isEqualTo(listOf(Pair(4 to 5, west), Pair(6 to 5, east)))
        expectThat(cell.next(west)).isEqualTo(listOf(Pair(4 to 5, west)))
    }

    @Test
    fun `should handle vertical splitter`() {
        val cell = Cell.of('|', 5 to 5)
        expectThat(cell.next(north)).isEqualTo(listOf(Pair(5 to 6, north)))
        expectThat(cell.next(east)).isEqualTo(listOf(Pair(5 to 6, north), Pair(5 to 4, south)))
        expectThat(cell.next(south)).isEqualTo(listOf(Pair(5 to 4, south)))
        expectThat(cell.next(west)).isEqualTo(listOf(Pair(5 to 6, north), Pair(5 to 4, south)))
    }

    @Test
    fun `it should calculate energy`() {
        val grid = Grid.from(listOf(
            ".|...\\....",
            "|.-.\\.....",
            ".....|-...",
            "........|.",
            "..........",
            ".........\\",
            "..../.\\\\..",
            ".-.-/..|..",
            ".|....-|.\\",
            "..//.|...."
        ))
        expectThat(grid.energized()).isEqualTo(46)
    }

    @Test
    fun `it should calculate best energy`() {
        val grid = Grid.from(listOf(
            ".|...\\....",
            "|.-.\\.....",
            ".....|-...",
            "........|.",
            "..........",
            ".........\\",
            "..../.\\\\..",
            ".-.-/..|..",
            ".|....-|.\\",
            "..//.|...."
        ))
        expectThat(grid.best()).isEqualTo(51)
    }

}