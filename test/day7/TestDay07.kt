package day7

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import readInput

class TestDay07 {

    @Test
    fun `it should compare Card`() {
        assertEquals(Card.A, Card.A)
        assertTrue(Card.A > Card.K)
        assertFalse(Card.A < Card.K)
        assertTrue(Card.A > Card.`2`)
        assertFalse(Card.A < Card.`2`)
    }

    @Test
    fun `it should parse hands`() {
        val hands = Hand.parse(listOf("32T3K 765", "T55J5 684"))
        assertEquals(2, hands.size)
        assertEquals(Hand(listOf(Card.`3`, Card.`2`, Card.T, Card.`3`, Card.K), 765, Type.ONE_PAIR), hands[0])
        assertEquals(Hand(listOf(Card.T, Card.`5`, Card.`5`, Card.J, Card.`5`), 684, Type.THREE_OF_A_KIND), hands[1])
    }

    @Test
    fun `it should parse types`() {
        val hand = Hand.parse(listOf("AAAAA 1", "AA8AA 2", "23332 3", "TTT98 4", "23432 5", "A23A4 6", "23456 7"))
        assertEquals(Type.FIVE_OF_A_KIND, hand[0].type)
        assertEquals(Type.FOUR_OF_A_KIND, hand[1].type)
        assertEquals(Type.FULL_HOUSE, hand[2].type)
        assertEquals(Type.THREE_OF_A_KIND, hand[3].type)
        assertEquals(Type.TWO_PAIR, hand[4].type)
        assertEquals(Type.ONE_PAIR, hand[5].type)
        assertEquals(Type.HIGH, hand[6].type)
    }

    @Test
    fun `it should solve part1`() {
        val actual = part1(readInput("Day07_test"))
        assertEquals(6440, actual)
    }

    @Test
    fun `it should solve part2`() {
        val actual = part2(readInput("Day07_test"))
        assertEquals(5905, actual)
    }

//    @Test
//    fun `it should solve part2 real`() {
//        val actual = part2(readInput("Day07"))
//        assertEquals(254412181, actual)
//    }

}