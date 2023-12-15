package day15

import println
import readInput

fun main() {

    part1(readInput("Day15")).println()

    part2(readInput("Day15")).println()

}

fun part1(input: List<String>):Int = HashMap().hash(input.first().split(","))

fun part2(input: List<String>):Int = HashMap().applyAll(input.first().split(",")).power()

typealias Entry = Pair<String, Int>

class HashMap(private val size: Int = 256) {

    private val commandRegex = Regex("""(\w+)([=-])(\d+)?""")

    internal val map = Array(size) { mutableListOf<Entry>() }

    fun applyAll(commands: List<String>): HashMap {
        commands.forEach { apply(it) }
        return this
    }

    fun apply(command:String) {
        val (key, op, value) = commandRegex.find(command)!!.destructured
        when (op) {
            "=" -> set(key, value)
            "-" -> remove(key)
        }
    }

    fun power() = map.flatMapIndexed { box, bucket ->
        bucket
            .mapIndexed { slot, (_, value) -> (slot + 1) * value }
            .map { value -> (box + 1) * value }
    }.sum()

    fun hash(input: List<String>) = input.sumOf { hash(it) }

    fun hash(input: String) =  input.fold(0) { acc, c -> ((acc + c.code) * 17 % size) }

    private fun set(key: String, value: String) {
        val bucket = map[hash(key)]
        val idx = bucket.indexOfFirst { it.first == key }
        if (idx >= 0) {
            bucket[idx] = key to value.toInt()
            return
        }
        bucket.add(key to value.toInt())
    }

    private fun remove(key: String) {
        map[hash(key)].removeIf { it.first == key }
    }

}