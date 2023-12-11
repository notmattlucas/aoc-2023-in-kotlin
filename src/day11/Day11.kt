package day11

import println
import readInput

fun main() {

    check(part1(readInput("Day11_test")) == 374L)

    part1(readInput("Day11")).println()
    part2(readInput("Day11")).println()

}

fun part1(input: List<String>):Long = Space.parse(input, 2).totalDistance()

fun part2(input: List<String>):Long = Space.parse(input, 1000000).totalDistance()

enum class Segment {
    SPACE, GALAXY
}

typealias Coordinate = Pair<Int, Int>

data class Space(val map:List<List<Segment>>, val xdistance:List<Long>, val ydistance:List<Long>) {

    fun galaxies(): List<Coordinate> = map.flatMapIndexed { y, row ->
        row.mapIndexed { x, segment ->
            if (segment == Segment.GALAXY) Coordinate(x, y) else null
        }.filterNotNull()
    }.sortedBy { it.first }

    fun totalDistance(): Long = distances().sumOf { it.second }

    private fun distances(): List<Pair<Pair<Coordinate, Coordinate>, Long>> {
        val galaxies = galaxies()
        return galaxies.flatMapIndexed { idx, first -> galaxies.subList(idx, galaxies.size).map { second -> first to second } }
            .filter { (a, b) -> a != b }
            .distinct()
            .map { (a, b) -> (a to b) to distance(a.first, b.first, xdistance) + distance(a.second, b.second, ydistance) }
    }

    private fun distance(first:Int, second:Int, distances: List<Long>):Long {
        return if (first > second) distances.subList(second, first).sum()  else distances.subList(first, second).sum()
    }

    companion object {

        fun parse(input: List<String>, expansion:Long): Space {
            val grid = input.map { line ->
                line.map { char ->
                    when (char) {
                        '.' -> Segment.SPACE
                        '#' -> Segment.GALAXY
                        else -> throw IllegalArgumentException("Unknown char $char")
                    }
                }
            }.reversed()

            val ydistance = grid.map { row -> if (row.all { it == Segment.SPACE } ) expansion else 1L }
            val xdistance = grid[0].indices.map { x -> if (grid.all { it[x] == Segment.SPACE } ) expansion else 1L }

            return Space(grid, xdistance, ydistance)
        }

    }

}