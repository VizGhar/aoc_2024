import kotlin.math.absoluteValue

class Day2 : Day() {

    private val transformedInput by lazy { input.map { it.split(" ").map { it.toInt() } } }

    private fun List<Int>.isOk() = (sorted() == this || sortedDescending() == this) && windowed(2).all { (it[0] - it[1]).absoluteValue in 1..3 }

    override fun partA() = transformedInput.count { it.isOk() }.toString()

    override fun partB() = transformedInput.count { list ->
        list.indices.any { index ->
            list.toMutableList().let {
                it.removeAt(index)
                it.isOk()
            }
        }
    }.toString()
}