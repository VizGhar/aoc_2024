import kotlin.math.absoluteValue

class Day1 : Day() {

    private val transformedInput by lazy {
        input.map { it.split("   ").map { it.toInt() }.let { it[0] to it[1] } }.unzip()
    }

    override fun partA() = transformedInput.let { (l1, l2) ->
        l1.sorted().zip(l2.sorted()).sumOf { (it.first - it.second).absoluteValue }
    }.toString()

    override fun partB() = transformedInput.let { (l1, l2) ->
        l1.sumOf { a -> a * l2.count { b -> a == b } }
    }.toString()

}