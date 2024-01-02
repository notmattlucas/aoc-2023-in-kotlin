package day20

import println
import readInput
import java.math.BigInteger
import java.util.concurrent.LinkedBlockingDeque
import kotlin.math.max
import kotlin.math.min

fun main() {

    part1(readInput("Day20")).println()

    part2(readInput("Day20")).println()

}

fun part1(input: List<String>):Int {
    val engine = Engine.parse(input)
    (1..1000).forEach() { _ -> engine.press() }
    return engine.value()
}

fun part2(input: List<String>):Long {
    val halt = { commands:List<Command> ->
        commands
            .filter { it.first.name == "rx" && it.third == 0 }
            .count() > 0
    }
    val engine = Engine.parse(input, halt)
    var presses = 0L
    while (!engine.halt()) {
        presses++
        engine.press()
    }
    return presses
}

typealias Pulse = Int
typealias Source = String
typealias Command = Triple<Module, Source, Pulse>

abstract class Module {

    abstract val name: String

    internal val downstream: MutableList<Module> = mutableListOf()

    internal val upstream: MutableList<Module> = mutableListOf()

    fun connectTo(other: Module) {
        downstream.add(other)
        other.backlink(this)
    }

    fun backlink(other: Module) {
        upstream.add(other)
    }

    abstract fun tick(source:String, pulse:Int): List<Command>

    companion object {

        fun create(input:String) = when {
            input == "broadcaster" -> Broadcast(input)
            input.startsWith("&") -> Conjunction(name(input))
            input.startsWith("%") -> FlipFlop(name(input))
            else -> throw IllegalArgumentException("Unknown module type: $input")
        }

        fun name(input:String) = when {
            input.startsWith("&") -> input.replace("&", "")
            input.startsWith("%") -> input.replace("%", "")
            else -> input
        }

    }

}

class Dummy(override val name: String): Module() {

    override fun tick(source:String, pulse: Int): List<Command> = emptyList()

}

class FlipFlop(override val name: String): Module() {

    var on: Boolean = false

    override fun tick(source:String, pulse: Int): List<Command> {
        if (pulse == 1) {
            return emptyList()
        }
        on = !on
        return downstream.map { Command(it, name, if (on) 1 else 0) }
    }

}

class Broadcast(override val name: String): Module() {

    override fun tick(source:String, pulse: Int): List<Command> {
        return downstream.map { Command(it, name, pulse) }
    }

}

class Conjunction(override val name: String): Module() {

    private var state: MutableMap<String, Int>? = null

    override fun tick(source:String, pulse: Int): List<Command> {
        if (state == null) {
            state = upstream.associate { it.name to 0 }.toMutableMap()
        }
        state!![source] = pulse
        val next = if (state!!.values.all { it == 1 }) 0 else 1
        return downstream.map { Command(it, name, next) }
    }

}

class Engine(private val input:Module, private val halt:(List<Command>) -> Boolean = { false }) {

    private val commands = LinkedBlockingDeque<Command>()

    private val stage = mutableListOf<Command>()

    private var high = 0

    private var low = 0

    fun press() {
        stage.clear()
        add(Command(input, "", 0))
        while (commands.isNotEmpty()) {
            val (module, source, pulse) = commands.remove()
            module.tick(source, pulse).forEach { add(it) }
        }
    }

    fun halt() = halt(stage)

    fun value() = low * high

    private fun add(cmd:Command) {
        when (cmd.third) {
            0 -> low++
            1 -> high++
        }
        commands.add(cmd)
        stage.add(cmd)
    }

    companion object {

        fun parse(input:List<String>, halt:(List<Command>) -> Boolean = { false }): Engine {
            val parts = input.map { it.split(" ").first() }
                .map { Module.create(it.trim()) }.associateBy { it.name }
            val links = input.map { it.split("->") }.flatMap { (from, commalist) ->
                commalist.split(",").map { to -> Module.name(from.trim()) to Module.name(to.trim()) }
            }
            for ((from, to) in links) {
                parts[from]!!.connectTo(parts[to] ?: Dummy(to))
            }
            return Engine(parts["broadcaster"]!!, halt)
        }

    }

}
