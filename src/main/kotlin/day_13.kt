class Day13 : Day() {

    data class Input(val buttonA: Position, val buttonB: Position, val prize: LPosition<Long>)

    private val transformedInput by lazy {
        input.filter { it != "" }
            .chunked(3)
            .map { (a, b, p) ->
                Input(
                    buttonA = a.substringAfter("+").substringBefore(",").toInt() to a.substringAfterLast("+").toInt(),
                    buttonB = b.substringAfter("+").substringBefore(",").toInt() to b.substringAfterLast("+").toInt(),
                    prize = p.substringAfter("=").substringBefore(",").toLong() to p.substringAfterLast("=").toLong()
                )
            }
    }

    private fun solveE(input: Input): Long {
        val (a, b, p) = input
        val d1 = (a.x * p.y - a.y * p.x)
        val d2 = (b.y * p.x - b.x * p.y)
        val d3 = (a.x * b.y - a.y * b.x)
        return if (d1 % d3 != 0L || d2 % d3 != 0L) 0L
        else (d2 / d3) * 3 + d1 / d3
    }

    override fun partA() =
        transformedInput.sumOf(::solveE).toString()

    override fun partB() =
        transformedInput.map { it.copy(prize = it.prize.x + 10000000000000L to it.prize.y + 10000000000000L) }
            .sumOf(::solveE).toString()

}
