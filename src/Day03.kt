import kotlin.math.abs

fun main() {

    fun part1(input: List<String>) = Schematic.from(input).sum()

    fun part2(input: List<String>) = Schematic.from(input).sumRatios()

    // test if implementation meets criteria from the description, like:
    check(part1(readInput("Day03_test")) == 4361)
    check(part2(readInput("Day03_test")) == 467835)

    part1(readInput("Day03")).println()
    part2(readInput("Day03")).println()
}

class Schematic(val parts: List<Part>, val gears: List<Gear>) {

    fun sum() = parts.sumOf { it.number }

    fun sumRatios() = gears.sumOf { it.ratio() }

    companion object {

        private val digits = Regex("(\\d+)")

        private val symbols = Regex("([^\\d.]+)")

        fun from(lines:List<String>) : Schematic {
            val numbers = numbers(lines)
            val symbols = symbols(lines)
            val parts = buildParts(symbols, numbers)
            val gears = buildGears(symbols, numbers)
            return Schematic(parts, gears)
        }

        private fun buildGears(symbols: List<Symbol>, numbers: List<Number>) = symbols
            .filter { it.isGear() }
            .map { symbol ->
                numbers.filter { it.associates(symbol) }.map { Part(symbol.value, it.number) }
            }
            .filter { it.size == 2 }
            .map { Gear(it[0], it[1]) }

        private fun buildParts(symbols: List<Symbol>, numbers: List<Number>) = symbols
            .flatMap { symbol ->
                numbers.filter { it.associates(symbol) }
                    .map { Part(symbol.value, it.number) }
            }

        fun numbers(lines:List<String>) = extract(digits, lines) { group, y -> Number(group.value.toInt(), group.range, y) }

        fun symbols(lines:List<String>) = extract(symbols, lines) { group, y -> Symbol(group.value, group.range.first, y) }

        private fun <T> extract(regex:Regex, lines:List<String>, transform:(MatchGroup, Int) -> T): List<T> {
            return lines.withIndex()
                .flatMap { (y, line) ->
                    regex.findAll(line)
                        .map { it.groups.first() }
                        .map { transform(it!!, y) }
                }
        }

    }

}

data class Number(val number:Int, val xrange: IntRange, val y: Int) {

    fun associates(symbol: Symbol) = IntRange(xrange.first - 1, xrange.last + 1).contains(symbol.x) && abs(y - symbol.y) <= 1

}

data class Symbol(val value:String, val x: Int, val y: Int) {

    fun isGear() = value == "*"

}

data class Part(val symbol: String, val number: Int)

data class Gear(val left: Part, val right: Part) {

    fun ratio() = left.number * right.number

}