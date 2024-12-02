import kotlin.math.absoluteValue

class Day1 : Day() {

    private val transformedInput by lazy {
        val l = input.map { it.split(" ").filter { it.isNotEmpty() }.map { it.toInt() } }
        val l1 = l.map { it[0] }
        val l2 = l.map { it[1] }
        l1 to l2
    }

    override fun partA() = transformedInput.let { (l1, l2) ->
        l1.sorted().zip(l2.sorted()).sumOf { (it.first - it.second).absoluteValue }
    }.toString()

    override fun partB() = transformedInput.let { (l1, l2) ->
        l1.sumOf { a -> a * l2.count { b -> a == b } }
    }.toString()

}