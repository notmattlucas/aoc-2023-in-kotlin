import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestDay04 {

    @Test
    fun `it should contain following cards`() {
        val cards = Card.parse(listOf(
            "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
            "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
            "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1"
        ))
        assertEquals(cards[0].following, listOf(
            Card(listOf(13, 32, 20, 16, 61), listOf(61, 30, 68, 82, 17, 32, 24, 19),
                listOf(Card(listOf( 1, 21, 53, 59, 44), listOf(69, 82, 63, 72, 16, 21, 14,  1), listOf()))),
            Card(listOf( 1, 21, 53, 59, 44), listOf(69, 82, 63, 72, 16, 21, 14,  1), listOf())
        ))
    }

    @Test
    fun `it should parse cards`() {
        val cards = Card.parse(listOf(
            "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
            "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19"
        ))
        assertEquals(cards, listOf(
            Card(listOf(41, 48, 83, 86, 17), listOf(83, 86,  6, 31, 17,  9, 48, 53), listOf(
                Card(listOf(13, 32, 20, 16, 61), listOf(61, 30, 68, 82, 17, 32, 24, 19), listOf())
            )),
            Card(listOf(13, 32, 20, 16, 61), listOf(61, 30, 68, 82, 17, 32, 24, 19), listOf())
        ))
    }

    @Test
    fun `it should score cards`() {
        val card = Card(listOf(1, 2, 3), listOf(2, 3, 4), listOf())
        assertEquals(2, card.score())
    }

    @Test
    fun `it should score cards with no matches`() {
        val card = Card(listOf(1, 2, 3), listOf(4, 5, 6), listOf())
        assertEquals(0, card.score())
    }

    @Test
    fun `score should be power of two`() {
        val card = Card(listOf(2, 3, 4), listOf(2, 3, 4), listOf())
        assertEquals(4, card.score())
    }

    @Test
    fun `it should output as many cards as matches`() {
        val cards = Card.parse(listOf(
            "Card 1: 1 2 3 4 5 | 5 6 7 8 9",
            "Card 2: 2 2 2 2 2 | 3 3 3 3 3",
            "Card 3: 3 3 3 3 3 | 4 4 4 4 4"
        ))
        assertEquals(1, cards[0].won().size)
        assertEquals(0, cards[1].won().size)
    }

}