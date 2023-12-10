fun main() {

    check(part1(readInput("Day10_test")) == 8)
    check(part2(readInput("Day10_test")) == 0)

    part1(readInput("Day10")).println()
    part2(readInput("Day10")).println()

}

fun part1(input: List<String>):Int = PipeMap.parse(input).longestPath().size() / 2

fun part2(input: List<String>):Int {
    val map = PipeMap.parse(input)
    val longest = map.longestPath()
    longest.view().forEach { println(it.joinToString("").replace(".", "X")) }
    return 0
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
        return 0
    }

}