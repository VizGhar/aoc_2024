class Day7 : Day() {

    private data class Equation(val result: Long, val operands: List<Long>)

    private val transformedInput by lazy {
        input.map {
            Equation(
                it.substringBefore(":").toLong(),
                it.substringAfter(": ").split(" ").map { it.toLong() }
            )
        }
    }

    private fun isViable(
        result: Long,
        current: Long,
        operands: List<Long>,
        availableOperators: List<(Long, Long) -> Long>
    ): Boolean {
        if (current == result && operands.isEmpty()) return true
        if (current > result || operands.isEmpty()) return false
        return availableOperators.any { operator ->
            isViable(result, operator(current, operands.first()), operands.drop(1), availableOperators)
        }
    }

    override fun partA() = run {
        transformedInput.filter {
            isViable(
                it.result, 0, it.operands, listOf(
                    { a, b -> a + b },
                    { a, b -> a * b }
                )
            )
        }.sumOf { it.result.toBigDecimal() }
    }.toString()

    override fun partB() = run {
        transformedInput.filter {
            isViable(
                it.result, 0, it.operands, listOf(
                    { a, b -> a + b },
                    { a, b -> a * b },
                    { a, b -> (a.toString() + b.toString()).toLong() }
                )
            )
        }.sumOf { it.result.toBigDecimal() }
    }.toString()

}
