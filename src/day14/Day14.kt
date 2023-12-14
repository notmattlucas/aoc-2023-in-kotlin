package day14

import println
import readInput
import kotlin.math.absoluteValue
import kotlin.math.min

fun main() {

    part1(readInput("Day14")).println()
    part2(readInput("Day14")).println()

}

fun part1(input: List<String>):Int = Platform.from(input).tilt().load()

fun part2(input: List<String>):Int {
    // Once the sequence converges, there are only 9 possibilities. Just print them and try them all :D (lazy)
    println(Platform.from(input).multiSpin().drop(100000).take(100).toList().distinct())
    return 0
}

data class Platform(val columns: List<List<String>>) {

    fun tilt(): Platform = Platform(columns.map { it.tilt() })

    fun load(): Int {
        return columns.sumOf { column ->
            column.mapIndexed { y, tile -> if (tile == "O") y + 1 else 0 }
                .sum()
        }
    }

    fun rotate(): Platform {
        val columns = columns.mapIndexed { x, column ->
            List(column.size) { y -> this.columns[columns.size - 1 - y][x] }
        }
        return Platform(columns)
    }

    fun spin(): Platform {
        var platform = this
        for (i in 0..3) {
            platform = platform.tilt().rotate()
        }
        return platform
    }

    fun multiSpin(): Sequence<Int> = generateSequence(this) { it.spin() }.map { it.load() }

    fun print() {
        this.rotate().rotate().rotate().columns.forEach { column ->
            column.forEach { tile ->
                kotlin.io.print(tile)
            }
            kotlin.io.println()
        }
    }

    companion object {

        fun from(input: List<String>): Platform {
            val columns = input[0].indices.map { index -> input.map { it[index].toString() }.reversed() }
            return Platform(columns)
        }

        fun List<String>.tilt(): List<String> {
            val edit = this.toMutableList()
            val stones = this.mapIndexed { idx, tile -> Pair(idx, tile) }
                .filter { (_, tile) -> tile == "O" }
                .map { (idx, _) -> idx }
                .reversed()
            for (stone in stones) {
                var pos = stone
                while (pos < edit.size - 1 && edit[pos + 1] == ".") {
                    edit[pos] = "."
                    edit[pos + 1] = "O"
                    pos++
                }
            }
            return edit.toList()
        }

    }

}