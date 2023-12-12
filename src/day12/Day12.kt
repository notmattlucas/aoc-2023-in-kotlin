package day12

import println
import readInput

fun main() {

    part1(readInput("Day12")).println()
    part2(readInput("Day12")).println()

}

fun part1(input: List<String>):Int = Search.from(input).sumOf { it.search().size }

fun part2(input: List<String>):Int {
    val searches = Search.fromFolded(input)
    var count = 1
    var sum = 0
    for (search in searches) {
        "..$count".println()
        sum += search.search().size
        count++
    }
    return sum
}

data class Search(val initRow: String, val initGroups:List<Int>) {

    private val cache = mutableMapOf<Pair<String, List<Int>>, List<String>>()

    private val matchCache = mutableMapOf<Pair<String, Int>, List<Pair<IntRange, Boolean>>>()

    fun search(): List<String> = search(initRow, initGroups)

    private fun search(row: String, groups: List<Int>): List<String> {
        val key = (row to groups)
        if (key in cache) {
            return cache[key]!!
        }

        if (groups.isEmpty()) {
            return listOf(row.replace("?", "."))
        }

        val group = groups.first()
        val rest = groups.drop(1)

        val matches = match(row, group)

        val result = matches
            .flatMap { (range, unknown) ->
                val border = if (unknown) "." else ""
                val before = row.substring(0, range.first).replace("?", ".")
                val after = row.substring(range.last + 1)
                search(after, rest).map {before + "#".repeat(group) + border + it }
            }
            .filter { candidate ->
                candidate.split(".").filter { it.isNotBlank() }.map { it.length } == groups
            }

        cache[key] = result

        return result
    }

    private fun match(row:String, groupSize:Int): List<Pair<IntRange, Boolean>> {
        val key = row to groupSize
        if (key in matchCache) {
            return matchCache[key]!!
        }
        val result = row.indices
            .map { it to row.substring(it) }
            .flatMap { (offset, sub) ->
                val bordering = Regex("([?#]{$groupSize})[.]|([?#]{$groupSize})$").findAll(sub).map { Pair(it, false) }
                val unknown = Regex("([?#]{$groupSize}[?])").findAll(sub).map { Pair(it, true) }
                val matches = (bordering + unknown).toList()
                matches.flatMap { (match, unknown) ->
                    match.groups.drop(1)
                        .filterNotNull()
                        .map { offset + it.range.first..offset + it.range.last to unknown }
                }
            }
            .distinct()
        matchCache[key] = result
        return result
    }

    companion object {

        fun from(input: String): Search {
            val (row, groups) = input.split(" ")
            return Search(row, groups.split(",").map { it.toInt() })
        }

        fun from(input: List<String>): List<Search> = input.map { from(it) }

        fun fromFolded(input:String):Search {
            val (row, groups) = input.split(" ")
            val unfoldedChart = (0 ..< 5).map { row }.joinToString("?")
            val unfoldedGroups = (0 ..< 5).map { groups }.joinToString(",")
            return from("$unfoldedChart $unfoldedGroups")
        }

        fun fromFolded(input:List<String>):List<Search> = input.map { fromFolded(it) }

    }

}