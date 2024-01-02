package day17

import println
import readInput

fun main() {

    part1(readInput("Day17")).println()

    part2(readInput("Day17")).println()

}

fun part1(input: List<String>):Int = City.from(input).search().last().cumulative

fun part2(input: List<String>):Int = 0

typealias Coordinate = Pair<Int, Int>

data class Block(val location: Coordinate, val neighbours: MutableList<Block>, var path: List<Block>, val heat:Int, var cumulative:Int) {

    override fun toString(): String = "Block($location, $heat, $cumulative)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Block

        return location == other.location
    }

    override fun hashCode(): Int {
        return location.hashCode()
    }


}

class City(private val start:Block, private val end:Block) {

    fun search():List<Block> {
        val settled = mutableSetOf<Block>()
        val unsettled = mutableSetOf(start)
        while (unsettled.isNotEmpty()) {
            val current = unsettled.minBy { it.cumulative }
            unsettled.remove(current)
            val neighbours = current.neighbours.filter { neighbour ->
                val points = current.path.takeLast(2).map { it.location } + current.location + neighbour.location
                val vert = points.distinctBy { it.second }.count() > 1
                val horiz = points.distinctBy { it.first }.count() > 1
                points.count() != 4 || (vert && horiz)
            }
            neighbours.forEach { neighbour ->
                if (!settled.contains(neighbour)) {
                    calculateMinimumDistance(neighbour, current)
                    unsettled.add(neighbour)
                }
            }
            settled.add(current)
        }
        println(end.path)
        printHeat(settled.toList())
        println(" ")
        printCumulative(settled.toList())
        println(" ")
        printCumulative(end.path)
        return end.path
    }

    private fun printCumulative(settled:List<Block>) {
        val ymax = start.location.second
        val xmax = end.location.first
        val grid = (0..ymax).map { y ->
            (0..xmax).map { x ->
                val block = settled.find { it.location == x to y }
                if (block != null) {
                    block.cumulative.toString().padStart(4, ' ')
                } else {
                    ".".padStart(4, ' ')
                }
            }
        }.reversed()
        println(grid.joinToString("\n") { it.joinToString("") })
    }

    private fun printHeat(settled:List<Block>) {
        val ymax = start.location.second
        val xmax = end.location.first
        val grid = (0..ymax).map { y ->
            (0..xmax).map { x ->
                val block = settled.find { it.location == x to y }
                if (block != null) {
                    block.heat.toString().padStart(4, ' ')
                } else {
                    "."
                }
            }
        }.reversed()
        println(grid.joinToString("\n") { it.joinToString("") })
    }



    private fun calculateMinimumDistance(neighbour: Block, current: Block) {
        val distance = current.cumulative + neighbour.heat
        if (distance < neighbour.cumulative) {
            neighbour.cumulative = distance
            neighbour.path = current.path + current
        }
    }

    companion object {
        fun from(input:List<String>):City {
            val grid = input
                .map { row -> row.map { it.toString().toInt() } }
                .toXY()
                .mapIndexed { x, col -> col.mapIndexed { y, heat -> Block(x to y, mutableListOf(), mutableListOf(), heat, Int.MAX_VALUE) } }
            val nodes = grid.flatten()
            nodes.forEach {node ->
                val (x, y) = node.location
                val neighbours = listOf(
                    (x - 1 to y),
                    (x + 1 to y),
                    (x to y - 1),
                    (x to y + 1)
                ).filter { (x, y) -> x >= 0 && y >= 0 && x < grid.size && y < grid.size }
                node.neighbours.addAll(neighbours.map { (x, y) -> grid[x][y] })
            }
            val start = grid[0][grid.size - 1]
            start.cumulative = 0
            val end = grid[grid.size - 1][0]
            return City(start, end)
        }

        private fun <T> List<List<T>>.toXY(): List<List<T>> = this[0].indices.map { col -> this.map { row -> row[col] }.reversed() }

    }

}
