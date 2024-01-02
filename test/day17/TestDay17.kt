package day17

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class TestDay17 {

    @Test
    fun `it should parse grid`() {
        val city = City.from(
            listOf(
                "789",
                "456",
                "123"
            )
        )
    }

    @Test
    fun `it should minimize heat loss`() {
        val input = listOf(
            "2413432311323",
            "3215453535623",
            "3255245654254",
            "3446585845452",
            "4546657867536",
            "1438598798454",
            "4457876987766",
            "3637877979653",
            "4654967986887",
            "4564679986453",
            "1224686865563",
            "2546548887735",
            "4322674655533"
        )
        expectThat(part1(input)).isEqualTo(102)
    }

}