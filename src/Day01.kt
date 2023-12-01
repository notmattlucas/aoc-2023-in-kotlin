fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { extractNumber(it) }
    }

    fun part2(input: List<String>): Int {
        return input.map { translateNumber(it) }.sumOf { extractNumber(it) }
    }

    // test if implementation meets criteria from the description, like:
    check(part1(readInput("Day01_part1_test")) == 142)
    check(part2(readInput("Day01_part2_test")) == 281)

    part1(readInput("Day01")).println()
    part2(readInput("Day01")).println()
}

private val NUMBERS = mapOf(
    "zero" to "0",
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9",
    "0" to "0",
    "1" to "1",
    "2" to "2",
    "3" to "3",
    "4" to "4",
    "5" to "5",
    "6" to "6",
    "7" to "7",
    "8" to "8",
    "9" to "9"
)

internal fun translateNumber(input:String): String {
    val prefixes = sequence {
        for (i in input.indices) {
            yield(input.substring(i, input.length))
        }
    }.toList()
    val digits = prefixes.flatMap {
        for ((key, value) in NUMBERS.entries) {
            if (it.startsWith(key)) {
                return@flatMap listOf(value)
            }
        }
        return@flatMap emptyList<String>()
    }
    return digits.joinToString("")
}

internal fun extractNumber(input:String): Int {
    val digits = input.toCharArray().filter { it.isDigit() }.toList()
    val pair = "%s%s".format(digits.first(), digits.last())
    return pair.toInt()
}
