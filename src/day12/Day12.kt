package day12

import kotlin.math.min

fun main() {

//    check(part1(readInput("Day12_test")) == 0)
//    check(part1(readInput("Day12_test")) == 0)
//
//    part1(readInput("Day12")).println()
//    part2(readInput("Day12")).println()

}

fun part1(input: List<String>):Int = 0

fun part2(input: List<String>):Int = 0

data class Search(val initRow: String, val initGroups:List<Int>) {

    fun search(): List<String> = search(initRow, initGroups)

    private fun search(row: String, groups: List<Int>): List<String> {
        if (groups.isEmpty()) {
            return listOf(row.replace("?", "."))
        }

        val group = groups.first()
        val rest = groups.drop(1)

        val regex = Regex("([?#]{$group})[?.]|([?#]{$group})$")
        val matches = regex.findAll(row).toList()

        return matches.flatMap { match ->
            match.groups.drop(1)
                .filterNotNull()
                .flatMap { matchGroup ->
                    val range = matchGroup.range
                    val before = row.substring(0, range.first).replace("?", ".")
                    val after = row.substring(min(row.length, range.last + 2))
                    search(after, rest).map {
                        before + "#".repeat(group) + "." + it
                }
            }

        }

    }

    companion object {

        fun from(input: String): Search {
            val (row, groups) = input.split(" ")
            return Search(row, groups.split(",").map { it.toInt() })
        }

    }

}