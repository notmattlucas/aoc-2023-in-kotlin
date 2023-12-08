package day8

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import readInput

class TestDay08 {

    @Test
    fun `it should parse node`() {
        val node = Node.parse("AAA = (BBB, CCC)")
        assertEquals(Node("AAA", Pair("BBB", "CCC")), node)
    }

    @Test
    fun `it should parse tree`() {
        val tree = Tree.singular(listOf("AAA = (BBB, BBB)", "BBB = (AAA, ZZZ)", "ZZZ = (ZZZ, ZZZ)"))
        val aaa = tree.roots[0]
        val aleft = aaa.left!!
        val aright = aaa.right!!
        val bbb = aleft
        val bleft = bbb.left!!
        val zzz = bbb.right!!
        assertEquals("AAA", aaa.name)
        assertEquals("BBB", aleft.name)
        assertEquals("BBB", aright.name)
        assertEquals("BBB", bbb.name)
        assertEquals("AAA", bleft.name)
        assertEquals("ZZZ", zzz.name)
        assertEquals("ZZZ", zzz.left!!.name)
        assertEquals("ZZZ", zzz.right!!.name)
    }

    @Test
    fun `it should find node`() {
        val tree = Tree.singular(listOf("AAA = (BBB, BBB)", "BBB = (AAA, ZZZ)", "ZZZ = (ZZZ, ZZZ)"))
        val steps = tree.search( "LLR")
        assertEquals(6, steps)
    }

    @Test
    fun `it should complete part 1`() {
        assertEquals(13019, part1(readInput("Day08")))
    }

    @Test
    fun `it should find parallel node`() {
        val spec = """
            LR

            11A = (11B, XXX)
            11B = (XXX, 11Z)
            11Z = (11B, XXX)
            22A = (22B, XXX)
            22B = (22C, 22C)
            22C = (22Z, 22Z)
            22Z = (22B, 22B)
            XXX = (XXX, XXX)
        """.trimIndent().split("\n")
        assertEquals(6, part2(spec))
    }

    @Test
    fun `it should complete part 2`() {
        assertEquals(13524038372771, part2(readInput("Day08")))
    }

}