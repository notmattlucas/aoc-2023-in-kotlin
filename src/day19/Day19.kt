package day19

import println
import readInput
import java.math.BigInteger
import kotlin.math.max
import kotlin.math.min

fun main() {

    part1(readInput("Day19")).println()

    part2(readInput("Day19")).println()

}

fun part1(input: List<String>):Int {
    val workflow = Workflow.workflow(input)
    val parts = Workflow.parts(input)
    val accepted = workflow.accept(parts)
    return accepted.flatMap { it.values }.sum()
}

fun part2(input: List<String>):Long = Workflow.workflow(input).bounds().combinations()

typealias Part = Map<String, Int>

typealias Bound = Map<String, IntRange>

fun List<Bound>.combinations():Long = this.sumOf { it.combinations() }

fun Bound.combinations():Long = this.values.map { (it.last - it.first + 1).toLong() }.reduce { acc, i -> acc * i }

class Workflow(private val rules:List<Rule>) {

    private val directory = rules.associateBy { it.name }

    fun accept(parts:List<Part>) = parts.filter { accept(it) }

    private fun accept(part:Part):Boolean {
        var next = "in"
        while (!listOf("A", "R").contains(next)) {
            val rule = directory[next]!!
            next = rule.evaluate(part)
        }
        return next == "A"
    }

    fun bounds():List<Bound> {
        val init = "xmas".map { it to 1..4000 }.associate { it.first.toString() to it.second }
        val todo = mutableListOf("in" to init)
        val accepted = mutableListOf<Bound>()
        while (todo.isNotEmpty()) {
            val (name, range) = todo.removeAt(0)
            val bounds = directory[name]!!.bounds(range)
            val (acceptedNext, todoNext) = bounds.partition { it.first == "A" }
            accepted.addAll(acceptedNext.map { it.second })
            todo.addAll(todoNext.filter { it.first != "R" })
        }
        return accepted
    }

    companion object {

        fun workflow(input: List<String>) = input
            .takeWhile { it.isNotBlank() }
            .map { Rule.parse(it) }
            .let { Workflow(it) }

        fun parts(input:List<String>) = input
            .filter { it.firstOrNull() == '{' }
            .map { part(it) }

        fun part(input:String):Part = input
                .replace("{", "")
                .replace("}", "")
                .split(",")
                .map { it.split("=") }
                .map { (k, v) -> k to v.toInt() }
                .associate { it }

    }

}

class Rule(val name:String, private val initial:Condition) {

    fun evaluate(input:Map<String, Int>): String = initial.evaluate(input)

    fun bounds(input:Bound):List<Pair<String, Bound>> = initial.bounds(input)

    class Condition(private val predicate:(Map<String, Int>) -> Boolean, private val bound:(Bound) -> Pair<Bound, Bound>, private val workflow:String, private val next:Condition?=null) {

        fun evaluate(input:Map<String, Int>): String {
            if (predicate(input)) {
                return workflow
            }
            return next!!.evaluate(input)
        }

        fun bounds(input:Bound, acc:List<Pair<String, Bound>> = emptyList()):List<Pair<String, Bound>> {
            val (bound, alternate) = bound(input)
            val expanded = acc + (workflow to bound)
            return next?.bounds(alternate, expanded) ?: expanded
        }

    }

    companion object {
        fun parse(input: String): Rule {
            Regex("(\\w+)\\{(.*)\\}").matchEntire(input)?.let {
                val (name, rule) = it.destructured
                val parts = rule.split(",")
                val default = Condition({ true }, { range -> Pair(range, range) }, parts.last())
                val initial = parts
                    .dropLast(1)
                    .reversed()
                    .fold(default) { next, condition -> createCond(condition, next) }
                return Rule(name, initial)
            }
            throw IllegalArgumentException("Invalid rule: $input")
        }

        private fun createCond(input: String, next:Condition): Condition {
            Regex("(\\w+)([<>=])(\\d+):(.*)").matchEntire(input)?.let {
                val (name, operator, value, workflow) = it.destructured
                val (predicate, bound) = when (operator) {
                    "<" -> Pair({ input:Map<String, Int> -> (input[name] ?: Int.MAX_VALUE) < value.toInt() }, { input:Bound ->
                        val range = input[name]!!
                        val sep = value.toInt()
                        val bound = input + (name to range.first ..sep - 1)
                        val alternate = input + (name to sep  .. range.last)
                        Pair(bound, alternate)
                    })
                    ">" -> Pair({ input:Map<String, Int> -> (input[name] ?: Int.MIN_VALUE) > value.toInt() }, { input:Bound ->
                        val range = input[name]!!
                        val sep = value.toInt()
                        val bound = input + (name to sep + 1.. range.last)
                        val alternate = input + (name to range.first .. sep)
                        Pair(bound, alternate)
                    })
                    else -> throw IllegalArgumentException("Invalid operator: $operator")
                }
                return Condition(predicate, bound, workflow, next)
            }
            throw IllegalArgumentException("Invalid condition: $input")
        }

    }

}