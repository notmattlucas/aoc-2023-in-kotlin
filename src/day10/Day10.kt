package day10

import println
import readInput

fun main() {

//    check(part1(readInput("Day10_test")) == 8)
//    check(part2(readInput("Day10_test")) == 0)
//
//    part1(readInput("Day10")).println()
    part2(readInput("Day10")).println()

}

fun part1(input: List<String>):Int = PipeMap.parse(input).longestPath().size() / 2

fun part2(input: List<String>):Int {
    val map = PipeMap.parse(input)
    val longest = map.longestPath()
    return longest.enclosed()
}

enum class Tile(val char: Char, private val tx:(Vector) -> Vector) {

    VERTICAL('|', { x -> x }),
    HORIZONTAL('-', { x -> x }),
    NORTH_EAST('L', { (x, y) -> (-1 * y) to (-1 * x) }),
    NORTH_WEST('J', { (x, y) -> y to x }),
    SOUTH_EAST('F', { (x, y) -> y to x }),
    SOUTH_WEST('7', { (x, y) -> (-1 * y) to (-1 * x) }),
    GROUND('.', { 0 to 0 }),
    START('S', { x -> x });

    fun translate(vec: Vector) = tx(vec)

    fun next(vec: Vector, point:Point) = translate(vec).let { (x, y) -> (point.first + x to point.second + y) }

}

typealias Vector = Pair<Int, Int>

typealias Point = Pair<Int, Int>

class PipeMap(internal val grid: List<List<Tile>>, internal val start: Point) {

    fun tile(point: Point):Tile? {
        return grid.getOrNull(point.second)?.getOrNull(point.first)
    }

    fun longestPath():Route {
        val routes = listOf(0 to 1, 1 to 0, 0 to -1, -1 to 0)
            .map { Route(this, it) }
        while (routes.any { !it.complete() }) {
            routes.forEach { it.step() }
        }
        return routes.maxBy { it.size() }
    }

    companion object {
        internal fun parse(input: List<String>): PipeMap {
            val grid = input.map { line ->
                line.map { char ->
                    Tile.entries.find { it.char == char } ?: error("Unknown tile $char")
                }
            }.reversed()
            return PipeMap(grid, findStart(grid))
        }

        private fun findStart(grid: List<List<Tile>>): Point {
            val y = grid.indexOfFirst { it.contains(Tile.START) }
            val x = grid[y].indexOf(Tile.START)
            return x to y
        }
    }

}

class Route(private val map: PipeMap, private val initial: Vector) {

    internal val route = mutableListOf(map.start)

    private var vector = initial

    private var complete = false

    fun step() {
        val last = route.last()
        val tile = map.tile(last)
        if (tile != null) {
            tile.next(vector, last).let { route.add(it) }
            vector = tile.translate(vector)
        }
        complete = (route.last() == map.start) || (tile == null) || (vector == 0 to 0)
    }

    fun complete(): Boolean {
        return route.size > 1 && complete
    }

    fun size(): Int {
        return route.distinct().size
    }

    fun view(): List<MutableList<Char>> {
        val grid = map.grid.map { it.map { Tile.GROUND.char }.toMutableList() }
        route.forEachIndexed { index, point ->
            grid[point.second][point.first] = map.tile(point)!!.char
        }
        return grid.reversed()
    }

    fun enclosed(): Int {
        val grid = map.grid.map { it.map { Tile.GROUND.char }.toMutableList() }
        route.forEachIndexed { index, point ->
            grid[point.second][point.first] = '|'
        }

        val visited = mutableSetOf(0 to 0)
        val todo = ArrayDeque(listOf(0 to 0))
        while (todo.isNotEmpty()) {
            val point = todo.removeAt(0)
            grid[point.second][point.first] = 'X'
            neighbours(point)
                .filter { !visited.contains(it) }
                .filter { grid[it.second][it.first] == '.' }
                .forEach {
                    todo.add(it)
                    visited.add(it)
                }
        }
        grid.reversed().forEach { println(it.joinToString("")) }
        return grid.flatten().count { it == '.' }
    }

    private fun neighbours(point: Point): List<Point> {
        return listOf(0 to 1, 1 to 0, 0 to -1, -1 to 0)
            .map { (x, y) -> point.first + x to point.second + y }
            .filter { map.tile(it) != null }
    }

}