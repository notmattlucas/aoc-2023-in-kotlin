import kotlin.math.floor
import kotlin.math.sqrt

fun main() {

    // test if implementation meets criteria from the description, like:
    check(solve(readInput("Day06_test")) == 288)
    check(solve(readInput("Day06_part2_test")) == 71503)
    check(solve(readInput("Day06")) == 275724)

    solve(readInput("Day06")).println()
    solve(readInput("Day06_part2")).println()

}

fun solve(input: List<String>):Int = Race.parse(input)
    .map { it.winningStrategies() }
    .reduce(Int::times)

data class Race(val time:Long, val record:Long) {
    fun winningStrategies(): Int {
        /**
         * Can be represented as a quadratic equation:
         *
         * We want to satisfy the following:
         *
         *    hold * (time - hold) > record
         *
         *    h * (t - h) > r
         *    ht - h^2 > r
         *    -h^2 - th - r > 0
         *
         * we can solve for roots
         *
         *    h = (-t +- sqrt(t^2 - 4*-1*-r)) / 2*-1
         *
         */
        val t = time.toDouble()
        val r = -1 * record.toDouble()
        val lower = ((-1 * t) + sqrt((t * t) - (4 * -1 * r))) / -2
        val upper = ((-1 * t) - sqrt((t * t) - (4 * -1 * r))) / -2
        // bump upper and lower limits marginally to avoid fencepost conditions (where the root is an integer)
        return floor(upper - 0.000001).toInt() - floor(lower + 0.000001).toInt()
    }

    companion object {
        fun parse(input:List<String>): List<Race> {
            val times = input[0].listOfNums()
            val records = input[1].listOfNums()
            return times.zip(records).map { (time, record) -> Race(time, record) }
        }

        private fun String.listOfNums() = this.split(" ").filter { it.isNotBlank() }.drop(1).map { it.toLong() }
    }

}