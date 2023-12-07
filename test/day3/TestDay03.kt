package day3

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class TestDay03 {

    @Test
    fun shouldParseEmptySchematic() {
        val schematic = Schematic.from(listOf())
        assertEquals(listOf<String>(), schematic.parts)
    }

    @Test
    fun shouldParseNumbers() {
        val numbers = Schematic.numbers(
            listOf(
                "467..114.."
            )
        )
        assertEquals(listOf(
            Number(467, IntRange(0, 2), 0),
            Number(114, IntRange(5, 7), 0)
        ), numbers)
    }

    @Test
    fun shouldParseSymbols() {
        val symbols = Schematic.symbols(
            listOf(
                ".....+.58."
            )
        )
        assertEquals(listOf(
            Symbol("+", 5, 0)
        ), symbols)
    }

    @Test
    fun numberShouldAssociateWhenSymbolIsOnTheRight() {
        val number = Number(467, IntRange(0, 2), 0)
        val symbol = Symbol("+", 3, 0)
        assertTrue(number.associates(symbol))
    }

    @Test
    fun numberShouldNotAssociateWhenSymbolIsFarOnTheRight() {
        val number = Number(467, IntRange(0, 2), 0)
        val symbol = Symbol("+", 4, 0)
        assertFalse(number.associates(symbol))
    }

    @Test
    fun numberShouldAssociateWhenSymbolIsOnTheLeft() {
        val number = Number(467, IntRange(1, 3), 0)
        val symbol = Symbol("+", 0, 0)
        assertTrue(number.associates(symbol))
    }

    @Test
    fun numberShouldNotAssociateWhenSymbolIsFarOnTheLeft() {
        val number = Number(467, IntRange(2, 4), 0)
        val symbol = Symbol("+", 0, 0)
        assertFalse(number.associates(symbol))
    }

    @Test
    fun numberShouldAssociateWhenAbove() {
        val number = Number(467, IntRange(0, 2), 0)
        val symbol = Symbol("+", 1, 1)
        assertTrue(number.associates(symbol))
    }

    @Test
    fun numberShouldNotAssociateWhenFarAbove() {
        val number = Number(467, IntRange(0, 2), 0)
        val symbol = Symbol("+", 1, 2)
        assertFalse(number.associates(symbol))
    }

    @Test
    fun numberShouldAssociateWhenBelow() {
        val number = Number(467, IntRange(0, 2), 1)
        val symbol = Symbol("+", 1, 0)
        assertTrue(number.associates(symbol))
    }

    @Test
    fun numberShouldNotAssociateWhenFarBelow() {
        val number = Number(467, IntRange(0, 2), 2)
        val symbol = Symbol("+", 1, 0)
        assertFalse(number.associates(symbol))
    }

    @Test
    fun numberShouldAssociateWhenDiagonal() {
        val number = Number(467, IntRange(0, 2), 1)
        val symbol = Symbol("+", 3, 0)
        assertTrue(number.associates(symbol))
    }

    @Test
    fun shouldCreateParts() {
        val schematic = Schematic.from(
            listOf(
                ".....+58."
            )
        )
        assertEquals(listOf(
            Part("+", 58)
        ), schematic.parts)
    }

    @Test
    fun shouldSumParts() {
        val schematic = Schematic.from(
            listOf(
                ".....+58."
            )
        )
        assertEquals(listOf(
            Part("+", 58)
        ), schematic.parts)
    }

    @Test
    fun shouldCreateGearWhenTwoPartsConnectedToSymbol() {
        val schematic = Schematic.from(
            listOf(
                ".....100.",
                ".......*.",
                "......50."
            )
        )
        assertEquals(listOf(Gear(Part("*", 100), Part("*", 50))), schematic.gears)
        assertEquals(5000, schematic.gears.first().ratio())
    }

}