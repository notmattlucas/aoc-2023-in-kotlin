import kotlin.math.max
import kotlin.math.min

fun main() {

    fun part1(input: List<String>):Int = Almanac.from(input).lowestLocation().toInt()

    fun part2(input: List<String>):Int = Almanac.from(input).lowestLocationRanges().toInt()

    // test if implementation meets criteria from the description, like:
    check(part1(readInput("Day05_test")) == 35)
    check(part2(readInput("Day05_test")) == 46)

    part1(readInput("Day05")).println()
    part2(readInput("Day05")).println()

}

class Almanac(val seeds:List<Long>, val sections:Map<String, Section>) {

    private val chain = listOf("seed-to-soil", "soil-to-fertilizer", "fertilizer-to-water", "water-to-light", "light-to-temperature", "temperature-to-humidity", "humidity-to-location")

    fun lowestLocation():Long = chain
        .fold(seeds) { acc, section -> acc.map { lookup(section, it) } }
        .min()

    fun lowestLocationRanges():Long {
        val seedRanges = seeds.windowed(2, 2) { (a, b) -> a .. (a + b) }
        val fold = chain.fold(seedRanges) { acc, section -> acc.flatMap { lookup(section, it) } }
        return fold
            .map { it.first }
            .min()
    }

    fun lookup(section:String, seed:Long):Long = sections[section]?.lookup(seed) ?: seed

    fun lookup(section:String, range:LongRange):List<LongRange> = sections[section]?.lookupRange(range) ?: listOf()

    companion object {
        fun from(input: List<String>): Almanac {
            val seeds = parseSeeds(input)
            val lookups = parseLookups(input)
            return Almanac(seeds, lookups)
        }

        private fun parseLookups(input: List<String>): Map<String, Section> {
            if (input.size < 3) {
                return mapOf()
            }
            return input.drop(2)
                .fold(listOf(listOf<String>())) { acc, line ->
                    if (line.isEmpty()) {
                        acc + listOf(listOf())
                    } else {
                        acc.dropLast(1) + listOf(acc.last() + listOf(line))
                    }
                }
                .map(::parseSection).associateBy { it.name }
        }

        private fun parseSection(group: List<String>): Section {
            val name = group.first().split(" ").first()
            val rows = group.drop(1)
            val mappings = rows
                .map { row -> row.split(" ").map { it.toLong() } }
                .map { (dest, source, length) ->
                    val diff = dest - source
                    MapRange(source,(source + length)) { it + diff }
                }
                .sortedBy { range -> range.lower }
                .windowed(2) {(a, b) ->
                    if (a.upper < b.lower - 1) {
                        listOf(a, MapRange(a.upper + 1, b.lower), b)
                    } else {
                        listOf(a, b)
                    }
                }
                .flatten()
                .distinctBy { range -> range.lower..range.upper }
            val leastBound = mappings.first().lower - 1
            val lower = MapRange(min(0, leastBound), leastBound)
            val upper = MapRange(mappings.last().upper + 1, Long.MAX_VALUE)
            return Section(name, listOf(lower) + mappings + listOf(upper))
        }

        private fun parseSeeds(input: List<String>) = input
            .first()
            .split(" ")
            .drop(1)
            .map { it.toLong() }

    }

}

data class MapRange(val lower:Long, val upper:Long, val transform:(Long) -> Long = { x -> x }) {

    fun contains(value:Long):Boolean = value in lower..upper

    fun overlaps(other:LongRange) = other.first in lower..upper || other.last in lower..upper

    fun transform(other:LongRange):LongRange = transform(max(lower, other.first)) .. transform(min(upper, other.last))

}

data class Section(val name:String, private val mappings:List<MapRange>) {

    fun lookup(seed:Long):Long = mappings
        .firstOrNull { it.contains(seed) }
        ?.let { it.transform(seed) }
        ?: seed

    fun lookupRange(range: LongRange) = mappings
        .filter { it.overlaps(range) }
        .map { it.transform(range) }

}