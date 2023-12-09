package day9

import println
import readInput

fun main() {

    check(part1(readInput("Day09_test")) == 114)
    check(part2(readInput("Day09_test")) == 2)

    part1(readInput("Day09")).println()
    part2(readInput("Day09")).println()

}

fun part1(input: List<String>):Int = Seq.from(input).sumOf { it.next() }

fun part2(input: List<String>):Int = Seq.from(input).sumOf { it.previous() }

data class Seq(private val seed:List<Int>) {

    fun next() = seed.last() + increment(seed)

    fun previous() = seed.first() - decrement(seed)

    companion object {
        fun from(input:List<String>):List<Seq> = input.map { from(it) }

        fun from(input:String):Seq = input.split(" ").filter { it.isNotBlank() }.map { it.trim().toInt() }.let { Seq(it) }

        fun increment(input:List<Int>):Int {
            val diffs = input.zipWithNext { a, b -> b - a }
            if (diffs.all { it == 0 }) return 0
            return diffs.last() + increment(diffs)
        }

        fun decrement(input:List<Int>):Int {
            val diffs = input.zipWithNext { a, b -> b - a }
            if (diffs.all { it == 0 }) return 0
            return diffs.first() - decrement(diffs)
        }

    }

}