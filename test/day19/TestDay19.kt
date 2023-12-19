package day19

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class TestDay19 {

    @Test
    fun `it should parse rule`() {
        val rule = Rule.parse("px{a<2006:qkq,m>2090:A,rfg}")
        expectThat(rule.name).isEqualTo("px")
        expectThat(rule.evaluate(mapOf("a" to 2005, "m" to 2091))).isEqualTo("qkq")
        expectThat(rule.evaluate(mapOf("a" to 2006, "m" to 2091))).isEqualTo("A")
        expectThat(rule.evaluate(mapOf("a" to 2006, "m" to 2090))).isEqualTo("rfg")
    }

    @Test
    fun `it should parse part`() {
        val part = Workflow.part("{x=2461,m=1339,a=466,s=291}")
        expectThat(part).isEqualTo(
            mapOf("x" to 2461, "m" to 1339, "a" to 466, "s" to 291)
        )
    }

    @Test
    fun `it should accept parts`() {
        val input = listOf(
            "px{a<2006:qkq,m>2090:A,rfg}",
            "pv{a>1716:R,A}",
            "lnx{m>1548:A,A}",
            "rfg{s<537:gd,x>2440:R,A}",
            "qs{s>3448:A,lnx}",
            "qkq{x<1416:A,crn}",
            "crn{x>2662:A,R}",
            "in{s<1351:px,qqz}",
            "qqz{s>2770:qs,m<1801:hdj,R}",
            "gd{a>3333:R,R}",
            "hdj{m>838:A,pv}",
            "",
            "{x=787,m=2655,a=1222,s=2876}",
            "{x=1679,m=44,a=2067,s=496}",
            "{x=2036,m=264,a=79,s=2244}",
            "{x=2461,m=1339,a=466,s=291}",
            "{x=2127,m=1623,a=2188,s=1013}"
        )
        val workflow = Workflow.workflow(input)
        val parts = Workflow.parts(input)
        val accepted = workflow.accept(parts)
        expectThat(accepted.size).isEqualTo(3)
        expectThat(part1(input)).isEqualTo(19114)
    }

    @Test
    fun `it should calculate bounds`() {
        val input = listOf(
            "px{a<2006:qkq,m>2090:A,rfg}",
            "pv{a>1716:R,A}",
            "lnx{m>1548:A,A}",
            "rfg{s<537:gd,x>2440:R,A}",
            "qs{s>3448:A,lnx}",
            "qkq{x<1416:A,crn}",
            "crn{x>2662:A,R}",
            "in{s<1351:px,qqz}",
            "qqz{s>2770:qs,m<1801:hdj,R}",
            "gd{a>3333:R,R}",
            "hdj{m>838:A,pv}",
            "",
            "{x=787,m=2655,a=1222,s=2876}",
            "{x=1679,m=44,a=2067,s=496}",
            "{x=2036,m=264,a=79,s=2244}",
            "{x=2461,m=1339,a=466,s=291}",
            "{x=2127,m=1623,a=2188,s=1013}"
        )
        val workflow = Workflow.workflow(input)
        val accepted = workflow.bounds().toSet()
        val expected = setOf(
            mapOf("x" to 1..1415, "m" to 1..4000, "a" to 1..2005, "s" to 1..1350),
            mapOf("x" to 2663..4000, "m" to 1..4000, "a" to 1..2005, "s" to 1..1350),
            mapOf("x" to 1..4000, "m" to 2091..4000, "a" to 2006..4000, "s" to 1..1350),
            mapOf("x" to 1..2440, "m" to 1..2090, "a" to 2006..4000, "s" to 537..1350),
            mapOf("x" to 1..4000, "m" to 1..4000, "a" to 1..4000, "s" to 3449..4000),
            mapOf("x" to 1..4000, "m" to 1549..4000, "a" to 1..4000, "s" to 2771..3448),
            mapOf("x" to 1..4000, "m" to 1..1548, "a" to 1..4000, "s" to 2771..3448),
            mapOf("x" to 1..4000, "m" to 839..1800, "a" to 1..4000, "s" to 1351..2770),
            mapOf("x" to 1..4000, "m" to 1..838, "a" to 1..1716, "s" to 1351..2770)
        )
        expectThat(accepted).isEqualTo(expected)
        expectThat(accepted.toList().combinations()).isEqualTo(167409079868000L)
    }

}