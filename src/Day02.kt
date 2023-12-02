import java.util.*

fun main() {

    fun part1(input: List<String>): Int {
        val games = parse(input)
        val criteria = mapOf(Color.GREEN to 13, Color.RED to 12, Color.BLUE to 14)
        return games.filter { it.allowed(criteria) }.sumOf { it.number }
    }

    fun part2(input: List<String>): Int {
        val games = parse(input)
        return games.sumOf { it.minCube() }
    }

    // test if implementation meets criteria from the description, like:
    check(part1(readInput("Day02_part1_test")) == 8)

    part1(readInput("Day02")).println()
    part2(readInput("Day02")).println()
}

data class Game(val number: Int, val rounds:List<Round>) {

    fun allowed(max:Map<Color, Int>): Boolean {
        return rounds.all { it.allowed(max) }
    }

    fun fewest(): Map<Color, Int> {
        val base = mapOf(Color.RED to 0, Color.BLUE to 0, Color.GREEN to 0)
        return rounds.fold(base) { max, round ->
            max.entries.associate { (key, current) ->
                key to maxOf(current, round.colors.getOrDefault(key, 0))
            }
        }
    }

    fun minCube(): Int {
        return fewest().values.reduce(Int::times)
    }

}

data class Round(val colors:Map<Color, Int>) {

    fun allowed(max:Map<Color, Int>): Boolean {
        return colors.entries
            .all { (color, count) ->
                max.getOrDefault(color, 0) >= count
            }
    }

}

enum class Color {
    RED, GREEN, BLUE
}

private val gameNumberRegex = Regex("Game (\\d+):")

internal fun parse(input: List<String>): List<Game> {
    return input.map { roundLine ->
        val number = gameNumberRegex.find(roundLine)?.groupValues?.get(1)?.toInt() ?: 0
        val roundText = roundLine.split(":")[1].trim().split(";")
        val rounds = roundText.map { roundSegment ->
            val colorText = roundSegment.split(",")
            val colors = colorText.associate {
                val pair = it.trim().split(" ")
                Color.valueOf(pair[1].uppercase(Locale.getDefault())) to pair[0].toInt()
            }
            Round(colors)
        }
        Game(number, rounds)
    }
}
