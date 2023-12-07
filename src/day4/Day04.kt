package day4

import println
import readInput
import kotlin.math.pow

fun main() {

    fun part1(input: List<String>):Int = Card.parse(input).sumOf { it.score() }

    fun part2(input: List<String>):Int {
        val cards = Card.parse(input)
        cards.forEach { it.cascade() }
        return cards.sumOf { it.value }
    }

    // test if implementation meets criteria from the description, like:
    check(part1(readInput("Day04_test")) == 13)
    check(part2(readInput("Day04_test")) == 30)

    part1(readInput("Day04")).println()
    part2(readInput("Day04")).println()
}

data class Card(val winning:List<Int>, val numbers:List<Int>, val following:List<Card>, var value:Int = 1) {

    fun score():Int = 2.0.pow(matched() - 1).toInt()

    fun won():List<Card> = following.take(matched())

    fun cascade() {
        won().forEach { it.update(value) }
    }

    private fun update(incr:Int = 0) {
        value += incr
    }

    private fun matched():Int = winning.intersect(numbers.toSet()).size

    companion object {
        fun parse(input: List<String>): List<Card> {
            var cards = listOf<Card>()
            return input.reversed().map { line ->
                val (_, all) = line.split(":")
                val (winning, numbers) = all.split("|")
                val card = Card(asNumList(winning), asNumList(numbers), cards.reversed())
                cards = cards + card
                card
            }.reversed()
        }

        private fun asNumList(numbers:String): List<Int> = numbers
            .trim()
            .split(" ")
            .filter { it.isNotBlank() }
            .map { it.toInt()
        }
    }

}