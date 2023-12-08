package day8

import println
import readInput

fun main() {

    check(part1(readInput("Day08_test")) == 6L)
    check(part2(readInput("Day08_test")) == 6L)

    part1(readInput("Day08")).println()
    part2(readInput("Day08")).println()

}

fun part1(input: List<String>):Long = singular(input).let { (tree, instructions) -> tree.search(instructions) }

fun part2(input: List<String>):Long = parallel(input).let { (tree, instructions) -> tree.search(instructions) }

fun singular(input: List<String>): Pair<Tree, String> {
    val instructions = input.first()
    val tree = Tree.singular(input.drop(2))
    return Pair(tree, instructions)
}

fun parallel(input: List<String>): Pair<Tree, String> {
    val instructions = input.first()
    val tree = Tree.parallel(input.drop(2))
    return Pair(tree, instructions)
}

data class Node(val name: String, internal val ref: Pair<String, String>, var left:Node?=null, var right:Node?=null) {

    fun next(direction:Direction): Node = when (direction) {
        Direction.L -> left!!
        Direction.R -> right!!
    }

    companion object {
        fun parse(input: String): Node {
            val (name, ref) = input.split(" = ")
            val (left, right) = ref.replace("(", "").replace(")", "").split(", ")
            return Node(name.trim(), Pair(left.trim(), right.trim()))
        }
    }

}

enum class Direction {
    L, R
}

data class Search(val root: Node, private val path:String, private val satisfied: (Node) -> Boolean) {

    private val instructions = path.toCharArray().map { Direction.valueOf(it.toString())  }

    private var current = root

    private var instruction = 0

    fun solve(): Int {
        var steps = 0
        while (!satisfied(current)) {
            val direction = instructions[(instruction++ % instructions.size).toInt()]
            current = current.next(direction)
            steps++
        }
        return steps
    }

}

data class Tree(internal val roots: List<Node>, private val goal: (Node) -> Boolean) {

    fun search(instructions:String):Long {
        val solutions = roots
            .map { root -> Search(root, instructions, goal) }
            .map { search -> search.solve().toLong() }
        if (solutions.size == 1) {
            return solutions.first()
        }
        return solutions.reduce(::lcm)
    }

    private fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

    private fun lcm(a: Long, b: Long): Long = a * b / gcd(a, b)

    companion object {

        fun singular(input: List<String>): Tree {
            val nodes = createNodes(input)
            val root = nodes.first()
            return Tree(listOf(root)) { it.name == "ZZZ" }
        }

        fun parallel(input: List<String>): Tree {
            val nodes = createNodes(input)
            val roots = nodes.filter { it.name.endsWith("A") }
            return Tree(roots) { it.name.endsWith("Z") }
        }

        private fun createNodes(input: List<String>): List<Node> {
            val nodes = input.map { Node.parse(it) }
            val nodeMap = nodes.associateBy { it.name }
            nodes.forEach { node ->
                val (left, right) = node.ref
                node.left = nodeMap[left]
                node.right = nodeMap[right]
            }
            return nodes
        }
    }

}