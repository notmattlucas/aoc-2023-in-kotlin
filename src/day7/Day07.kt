package day7

import println
import readInput

fun main() {

    // test if implementation meets criteria from the description, like:
    check(part1(readInput("Day07_test")) == 6440)
    check(part2(readInput("Day07_test")) == 5905)

    part1(readInput("Day07")).println()
    part2(readInput("Day07")).println()

}

fun part1(input: List<String>):Int = Hand.parse(input)
    .sorted()
    .withIndex()
    .sumOf { (idx, hand) -> hand.bid * (idx + 1) }

fun part2(input: List<String>):Int = Hand.parse(input, true)
    .sorted()
    .withIndex()
    .sumOf { (idx, hand) -> hand.bid * (idx + 1) }

enum class Card {
    // joker represented as lowercase j (not in original input)
    j, `2`, `3`, `4`, `5`, `6`, `7`, `8`, `9`, T, J, Q, K, A
}

enum class Type {
    HIGH,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    FIVE_OF_A_KIND
}

data class Hand(val cards: List<Card>, val bid:Int, val type: Type) : Comparable<Hand> {

    override fun compareTo(other: Hand): Int {
        if (this.type == other.type) {
            return this.cards.zip(other.cards).map { (a, b) -> a.compareTo(b) }.first { it != 0 }
        }
        return this.type.compareTo(other.type)
    }

    companion object {
        fun parse(input: List<String>, jokers:Boolean = false): List<Hand> {
            return input.map { line ->
                val (labels, bid) = line.split(" ")
                val cards = labels.chunked(1).map { Card.valueOf(it) }
                if (jokers) {
                    val remapped = cards.map { if (it == Card.J) Card.j else it }
                    val x = listOf(remapped) + unjoker(remapped)
                    val type = x
                        .map { typeOfHand(it) }
                        .maxOf { it }
                    Hand(cards, bid.toInt(), type)
                } else {
                    Hand(cards, bid.toInt(), typeOfHand(cards))
                }
            }
        }

        private fun unjoker(cards:List<Card>): List<List<Card>> {
            val indexes = cards.withIndex()
                .filter { it.value == Card.j }
                .map { it.index }
            return unjoker(cards, indexes)
        }

        private fun unjoker(cards:List<Card>, indexes:List<Int>): List<List<Card>> {
            if (indexes.isEmpty()) {
                return listOf()
            }
            val index = indexes.first()
            val unjokered = sequence {
                for (card in cards) {
                    yield(cards.mapIndexed { i, c -> if (i == index) card else c })
                }
            }.toList()
            return unjokered + unjokered.flatMap { unjoker(it, indexes.drop(1)) }
        }

        private fun typeOfHand(cards:List<Card>): Type {
            val distinct = cards.distinct()
            if (distinct.size == 1) {
                return Type.FIVE_OF_A_KIND
            }
            if (distinct.size == 2) {
                return if (cards.groupBy { it }.values.map { it.size }.sorted() == listOf(2, 3)) {
                    Type.FULL_HOUSE
                } else {
                    Type.FOUR_OF_A_KIND
                }
            }
            if (distinct.size == 3) {
                return if (cards.groupBy { it }.values.map { it.size }.sorted() == listOf(1, 2, 2)) {
                    Type.TWO_PAIR
                } else {
                    Type.THREE_OF_A_KIND
                }
            }
            if (distinct.size == 4) {
                return Type.ONE_PAIR
            }
            return Type.HIGH
        }
    }

}
