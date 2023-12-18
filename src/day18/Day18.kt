package day18

import println
import readInput
import kotlin.math.abs
import kotlin.math.absoluteValue

fun main() {

    part1(readInput("Day18")).println()

    part2(readInput("Day18")).println()

}

fun part1(input: List<String>):Int = Grid.dig(Instruction.parse(input)).points().toInt()

fun part2(input: List<String>):Long = Grid.dig(Instruction.parse(input).map { it.fromHex() }).points().toLong()

enum class Direction { U, D, L, R }


class Grid(val vertices: List<Triple<Long, Long, String>>, private val outer: Long) {

    fun area(): Double {
        // shoelace theorem
        val pairs = vertices.windowed(2, 1) + listOf(listOf(vertices.last(), vertices.first()))
        return pairs
            .sumOf { (a, b) ->
                a.first * b.second - a.second * b.first
            }.absoluteValue / 2.0
    }

    // picks theorem: A - b/2 + 1 = i
    fun inner(): Double = area() + 1 - (outer / 2.0)

    fun points(): Double = inner() + outer

    companion object {

        fun dig(instructions: List<Instruction>): Grid {
            val vertices = instructions.fold(listOf(Triple(0L, 0L, instructions.first().color))) { steps, instruction ->
                val (x, y, _) = steps.last()
                when (instruction.direction) {
                    Direction.U -> steps + Triple(x, y + instruction.steps, instruction.color)
                    Direction.D -> steps + Triple(x, y - instruction.steps, instruction.color)
                    Direction.L -> steps + Triple(x - instruction.steps, y, instruction.color)
                    Direction.R -> steps + Triple(x + instruction.steps, y, instruction.color)
                }
            }
            val minX = abs(vertices.minBy { it.first }.first)
            val minY = abs(vertices.minBy { it.second }.second)
            val normalized = vertices.drop(1).map { (x, y, color) -> Triple(x + minX, y + minY, color) }
            return Grid(normalized, instructions.sumOf { it.steps })
        }

    }

}

data class Instruction(val direction: Direction, val steps: Long, val color: String) {

    fun fromHex(): Instruction {
        val hex = color.drop(1)
        val distance = hex.take(5).toLong(16)
        val direction = when (hex.last()) {
            '0' -> Direction.R
            '1' -> Direction.D
            '2' -> Direction.L
            '3' -> Direction.U
            else -> throw IllegalArgumentException("Unknown direction")
        }
        return Instruction(direction, distance, color)
    }

    companion object {

        fun parse(input:List<String>) = input.map { parse(it) }

        fun parse(input: String): Instruction {
            val (direction, steps, color) = input.split(" ")
            return Instruction(Direction.valueOf(direction), steps.toLong(), color.replace("(", "").replace(")", ""))
        }

    }

}