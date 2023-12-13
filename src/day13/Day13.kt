package day13

import println
import readInput
import kotlin.math.min

fun main() {

    part1(readInput("Day13")).println()
    part2(readInput("Day13")).println()

}

fun part1(input: List<String>):Int = MirrorMap
    .from(input)
    .map { MirrorMap.fromOne(it) }
    .sumOf { it.summarize() }

fun part2(input: List<String>):Int  {
    val mirrors = MirrorMap
        .from(input)
        .map { MirrorMap.fromOne(it) }
        .flatMap {
            val polished = it.polish()
            if (polished.isNotEmpty()) {
                listOf(polished.first())
            } else {
                emptyList()
            }
        }
    val summarize = mirrors.map { it.summarize() }
    return summarize.sum()
}

class MirrorMap(val rows:List<String>, val columns:List<String>) {

    fun vertical() = mirrored(columns)

    fun horizontal() = mirrored(rows) * 100

    fun summarize():Int {
        val h = horizontal()
        if (h == 0) {
            return vertical()
        }
        return h
    }

    fun polish(): List<MirrorMap> {
        val hidx = mirrored(rows)
        val vidx = mirrored(columns)
        return polish(rows)
            .map { fromOne(it) }
            .filter { other ->
                val ohidx = other.mirrored(other.rows)
                val ovidx = other.mirrored(other.columns)
                (ohidx != hidx && ohidx > 0) || (ovidx != vidx && ovidx > 0)
            }
    }

    private fun mirrored(map:List<String>):Int {
        val idx = findMirrorIndex(map)
        return if (isMirrored(map, idx)) {
            idx!!
        } else {
            0
        }
    }

    private fun isMirrored(map:List<String>, idx:Int?):Boolean {
        if (idx == null) {
            return false
        }
        val size = min(idx, map.size - idx)
        val left = map.subList(idx - size, idx)
        val right = map.subList(idx, idx + size)
        return left.zip(right.reversed())
            .all { (l, r) -> l == r  }
    }

    private fun findMirrorIndex(map:List<String>):Int? = map.asSequence()
        .windowed(2, 1)
        .withIndex()
        .filter { (_, pair) -> pair[0] == pair[1] }
        .toList()
        .map { (idx, _) -> idx + 1 }
        .filter { idx -> isMirrored(map, idx) }
        .maxOrNull()

    companion object {

        fun from(input:List<String>):List<List<String>> {
            val buffer = mutableListOf<String>()
            val maps = mutableListOf<List<String>>()
            for (line in input) {
                if (line.isBlank()) {
                    maps.add(buffer.toList())
                    buffer.clear()
                } else {
                    buffer.add(line)
                }
            }
            maps.add(buffer.toList())
            buffer.clear()
            return maps
        }

        fun fromOne(input:List<String>):MirrorMap {
            val rows = input
            val columns = rows[0].indices.map { index -> rows.map { it[index] }.joinToString("") }
            return MirrorMap(rows, columns)
        }

        fun polish(input:List<String>): List<List<String>> {
            return sequence {
                for (y in input.indices) {
                    for (x in input[0].indices) {
                        val ps = input.mapIndexed { ys, line ->
                            line.mapIndexed() { xs, c ->
                                if (xs == x && ys == y) {
                                    if (c == '#') '.' else '#'
                                } else {
                                    c
                                }
                            }.joinToString("")
                        }
                        yield(ps)
                    }
                }
            }
            .toList()
        }

    }

}