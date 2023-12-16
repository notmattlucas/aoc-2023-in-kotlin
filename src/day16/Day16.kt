package day16

import println
import readInput

fun main() {

    part1(readInput("Day16")).println()

    part2(readInput("Day16")).println()

}

fun part1(input: List<String>):Int = Grid.from(input).energized()

fun part2(input: List<String>):Int = Grid.from(input).best()

typealias Point = Pair<Int, Int>

typealias Vector = Pair<Int, Int>

class Cell(val type: Char, val location: Point, private val translation: Map<Vector, List<Vector>>) {

    fun next(input: Vector): List<Pair<Point, Vector>> {
        val (x, y) = location
        val vectors = translation[input] ?: listOf()
        return vectors.map {
            val (dx, dy) = it
            (x + dx to y + dy) to it
        }
    }

    override fun toString(): String {
        return type.toString()
    }

    companion object {

        val north = 0 to 1

        val east = 1 to 0

        val south = 0 to -1

        val west = -1 to 0

        private val translations = mapOf(
            '.' to mapOf(north to listOf(north), east to listOf(east), south to listOf(south), west to listOf(west)),
            '/' to mapOf(north to listOf(east), east to listOf(north), south to listOf(west), west to listOf(south)),
            '\\' to mapOf(north to listOf(west), east to listOf(south), south to listOf(east), west to listOf(north)),
            '-' to mapOf(north to listOf(west, east), east to listOf(east), south to listOf(west, east), west to listOf(west)),
            '|' to mapOf(north to listOf(north), east to listOf(north, south), south to listOf(south), west to listOf(north, south)),
        )

        fun of(type: Char, location: Point): Cell = Cell(type, location, translations[type] ?: mapOf())

    }

}

class Grid(private val cells: List<List<Cell>>) {

    fun energized(start:Pair<Point, Vector> = Pair(0 to cells.size - 1, Cell.east)): Int {
        val next = mutableListOf(start)
        val visited = mutableListOf(start)
        while (next.isNotEmpty()) {
            val (point, vector) = next.removeAt(0)
            visited.add(point to vector)
            val cell = cells[point.second][point.first]
            val new = cell.next(vector).filter { move ->
                val (x, y) = move.first
                y in cells.indices && x in cells[0].indices && move !in visited
            }
            next.addAll(new)
        }
        return visited.map { it.first }.distinct().size
    }

    fun best(): Int {
        val y = cells.size - 1
        val x = cells[0].size - 1
        val init = (0 .. x).map { (it to 0) to Cell.north } +
                (0 .. y).map { (x to it) to Cell.west } +
                (0 .. x).map { (it to y) to Cell.south } +
                (0 .. y).map { (0 to it) to Cell.east }
        return init.maxOfOrNull { energized(it) } ?: 0
    }

    companion object {

        fun from(input: List<String>): Grid {
            val cells = input.reversed().mapIndexed { y, line ->
                line.mapIndexed { x, char ->
                    Cell.of(char, x to y)
                }
            }
            return Grid(cells)
        }

    }

}