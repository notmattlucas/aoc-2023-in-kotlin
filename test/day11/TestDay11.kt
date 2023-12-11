package day11

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class TestDay11 {

    @Test
    fun `it should parse space`() {
        val space = Space.parse(
            listOf(
                "....#........",
                ".........#...",
                "#............",
                ".............",
                ".............",
                "........#....",
                ".#...........",
                "............#",
                ".............",
                ".............",
                ".........#...",
                "#....#......."
            ), 2
        )
        val expected = listOf(
            List(4) { Segment.SPACE } + listOf(Segment.GALAXY) + List(8) { Segment.SPACE },
            List(9) { Segment.SPACE } + listOf(Segment.GALAXY) + List(3) { Segment.SPACE },
            listOf(Segment.GALAXY) + List(12) { Segment.SPACE },
            List(13) { Segment.SPACE },
            List(13) { Segment.SPACE },
            List(8) { Segment.SPACE } + listOf(Segment.GALAXY) + List(4) { Segment.SPACE },
            listOf(Segment.SPACE) + listOf(Segment.GALAXY) + List(11) { Segment.SPACE },
            List(12) { Segment.SPACE } + listOf(Segment.GALAXY),
            List(13) { Segment.SPACE },
            List(13) { Segment.SPACE },
            List(9) { Segment.SPACE } + listOf(Segment.GALAXY) + List(3) { Segment.SPACE },
            listOf(Segment.GALAXY) + List(4) { Segment.SPACE } + listOf(Segment.GALAXY) + List(7) { Segment.SPACE }
        ).reversed()

        expectThat(space.map).isEqualTo(expected)
        expectThat(space.xdistance).isEqualTo(listOf(1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 2, 2, 1))
        expectThat(space.ydistance).isEqualTo(listOf(1, 1, 2, 2, 1, 1, 1, 2, 2, 1, 1, 1))
    }

    @Test
    fun `it should identify galaxies`() {
        val space = Space.parse(
            listOf(
                "....#........",
                ".........#...",
                "#............",
                ".............",
                ".............",
                "........#....",
                ".#...........",
                "............#",
                ".............",
                ".............",
                ".........#...",
                "#....#......."
            ), 2
        )
        expectThat(space.galaxies()).isEqualTo(listOf(
            0 to 0,
            0 to 9,
            1 to 5,
            4 to 11,
            5 to 0,
            8 to 6,
            9 to 1,
            9 to 10,
            12 to 4
        ))
    }

    @Test
    fun `it should calculate distance`() {
        val space = Space.parse(
            listOf(
                "...#......",
                ".......#..",
                "#.........",
                "..........",
                "......#...",
                ".#........",
                ".........#",
                "..........",
                ".......#..",
                "#...#....."
            ), 2
        )
        expectThat(space.totalDistance()).isEqualTo(374)
    }

    @Test
    fun `it should calculate distance with expansion 10`() {
        val space = Space.parse(
            listOf(
                "...#......",
                ".......#..",
                "#.........",
                "..........",
                "......#...",
                ".#........",
                ".........#",
                "..........",
                ".......#..",
                "#...#....."
            ), 10
        )
        expectThat(space.totalDistance()).isEqualTo(1030)
    }

    @Test
    fun `it should calculate distance with expansion 100`() {
        val space = Space.parse(
            listOf(
                "...#......",
                ".......#..",
                "#.........",
                "..........",
                "......#...",
                ".#........",
                ".........#",
                "..........",
                ".......#..",
                "#...#....."
            ), 100
        )
        expectThat(space.totalDistance()).isEqualTo(8410)
    }

}