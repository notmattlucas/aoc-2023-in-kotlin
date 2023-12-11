package day10

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class TestDay10 {

    @Test
    fun `it should parse the map`() {
        val expected = listOf(
            listOf(Tile.SOUTH_WEST, Tile.HORIZONTAL, Tile.SOUTH_EAST, Tile.SOUTH_WEST, Tile.HORIZONTAL),
            listOf(Tile.GROUND, Tile.SOUTH_EAST, Tile.NORTH_WEST, Tile.VERTICAL, Tile.SOUTH_WEST),
            listOf(Tile.START, Tile.NORTH_WEST, Tile.NORTH_EAST, Tile.NORTH_EAST, Tile.SOUTH_WEST),
            listOf(Tile.VERTICAL, Tile.SOUTH_EAST, Tile.HORIZONTAL, Tile.HORIZONTAL, Tile.NORTH_WEST),
            listOf(Tile.NORTH_EAST, Tile.NORTH_WEST, Tile.GROUND, Tile.NORTH_EAST, Tile.NORTH_WEST)
        ).reversed()
        val actual = PipeMap.parse(listOf(
            "7-F7-",
            ".FJ|7",
            "SJLL7",
            "|F--J",
            "LJ.LJ"
        ))
        expectThat(actual.grid).isEqualTo(expected)
        expectThat(actual.start).isEqualTo(0 to 2)
    }

    @Test
    fun `it should identify start`() {
        val actual = PipeMap.parse(listOf(
            "7-F7-",
            ".FJ|7",
            ".JLS7",
            "LJ.LJ"
        ))
        expectThat(actual.start).isEqualTo(3 to 1)
    }

    @Test
    fun `tile should translate vector`() {
        expectThat(Tile.VERTICAL.translate(Vector(0, 1))).isEqualTo(0 to 1)
        expectThat(Tile.VERTICAL.translate(Vector(0, -1))).isEqualTo(0 to -1)
        expectThat(Tile.HORIZONTAL.translate(Vector(1, 0))).isEqualTo(1 to 0)
        expectThat(Tile.HORIZONTAL.translate(Vector(-1, 0))).isEqualTo(-1 to 0)
        expectThat(Tile.GROUND.translate(Vector(1, 0))).isEqualTo(0 to 0)
        expectThat(Tile.GROUND.translate(Vector(-1, 0))).isEqualTo(0 to 0)
        expectThat(Tile.START.translate(Vector(1, 0))).isEqualTo(1 to 0)
        expectThat(Tile.START.translate(Vector(-1, 0))).isEqualTo(-1 to 0)
        expectThat(Tile.NORTH_EAST.translate(Vector(0, 1))).isEqualTo(-1 to 0)
        expectThat(Tile.NORTH_EAST.translate(Vector(1, 0))).isEqualTo(0 to -1)
        expectThat(Tile.NORTH_WEST.translate(Vector(0, 1))).isEqualTo(1 to 0)
        expectThat(Tile.NORTH_WEST.translate(Vector(-1, 0))).isEqualTo(0 to -1)
        expectThat(Tile.SOUTH_EAST.translate(Vector(0, -1))).isEqualTo(-1 to 0)
        expectThat(Tile.SOUTH_EAST.translate(Vector(1, 0))).isEqualTo(0 to 1)
        expectThat(Tile.SOUTH_WEST.translate(Vector(0, -1))).isEqualTo(1 to 0)
        expectThat(Tile.SOUTH_WEST.translate(Vector(-1, 0))).isEqualTo(0 to 1)
    }

    @Test
    fun `tile should translate point`() {
        expectThat(Tile.VERTICAL.next(Vector(0, 1), 2 to 2)).isEqualTo((2 to 3))
        expectThat(Tile.VERTICAL.next(Vector(0, -1), 2 to 2)).isEqualTo(2 to 1)
        expectThat(Tile.HORIZONTAL.next(Vector(1, 0), 2 to 2)).isEqualTo(3 to 2)
        expectThat(Tile.HORIZONTAL.next(Vector(-1, 0), 2 to 2)).isEqualTo(1 to 2)
        expectThat(Tile.GROUND.next(Vector(1, 0), 2 to 2)).isEqualTo(2 to 2)
        expectThat(Tile.GROUND.next(Vector(-1, 0), 2 to 2)).isEqualTo(2 to 2)
        expectThat(Tile.START.next(Vector(1, 0), 2 to 2)).isEqualTo(3 to 2)
        expectThat(Tile.START.next(Vector(-1, 0), 2 to 2)).isEqualTo(1 to 2)
        expectThat(Tile.NORTH_EAST.next(Vector(0, 1), 2 to 2)).isEqualTo(1 to 2)
        expectThat(Tile.NORTH_EAST.next(Vector(1, 0), 2 to 2)).isEqualTo(2 to 1)
        expectThat(Tile.NORTH_WEST.next(Vector(0, 1),2 to 2)).isEqualTo(3 to 2)
        expectThat(Tile.NORTH_WEST.next(Vector(-1, 0), 2 to 2)).isEqualTo(2 to 1)
        expectThat(Tile.SOUTH_EAST.next(Vector(0, -1), 2 to 2)).isEqualTo(1 to 2)
        expectThat(Tile.SOUTH_EAST.next(Vector(1, 0), 2 to 2)).isEqualTo(2 to 3)
        expectThat(Tile.SOUTH_WEST.next(Vector(0, -1), 2 to 2)).isEqualTo(3 to 2)
        expectThat(Tile.SOUTH_WEST.next(Vector(-1, 0), 2 to 2)).isEqualTo(2 to 3)
    }

    @Test
    fun `should take steps`() {
        val map = PipeMap.parse(listOf(
            "7-F7-",
            ".FJ|7",
            "SJLL7",
            "|F--J",
            "LJ.LJ"
        ))
        val route = Route(map, 1 to 0)
        route.step()
        route.step()
        route.step()
        expectThat(route.route).isEqualTo(
            listOf(
                0 to 2,
                1 to 2,
                1 to 3,
                2 to 3
            ).toMutableList()
        )
    }

    @Test
    fun `it should find longest path simple`() {
        val map = PipeMap.parse(listOf(
            ".....",
            ".S-7.",
            ".|.|.",
            ".L-J.",
            "....."
        ))
        expectThat(map.longestPath().size()).isEqualTo(8)
    }

    @Test
    fun `it should find longest path`() {
        val map = PipeMap.parse(listOf(
            "7-F7-",
            ".FJ|7",
            "SJLL7",
            "|F--J",
            "LJ.LJ"
        ))
        expectThat(map.longestPath().size()).isEqualTo(16)
    }

    @Test
    fun `it should find enclosing space 2`() {
        val map = PipeMap.parse(listOf(
            "...........",
            ".S-------7.",
            ".|F-----7|.",
            ".||.....||.",
            ".||.....||.",
            ".|L-7.F-J|.",
            ".|..|.|..|.",
            ".L--J.L--J.",
            "...........",
        ))
        expectThat(map.longestPath().enclosed()).isEqualTo(4)
    }
    
}