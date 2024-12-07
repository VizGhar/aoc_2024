import kotlinx.coroutines.*

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
        index: Int,
        availableOperators: List<(Long, Long) -> Long>
    ): Boolean {
        if (current == result && index == operands.size) return true
        if (current > result || index == operands.size) return false
        if (index == 0) return isViable(result, operands[0], operands, 1, availableOperators)
        return availableOperators.any { operator ->
            isViable(result, operator(current, operands[index]), operands, index + 1, availableOperators)
        }
    }

    private fun solution(operators: List<(Long, Long) -> Long>) = runBlocking {
        withContext(Dispatchers.Default) {
            transformedInput
                .asyncMap { if(isViable(it.result, 0, it.operands, 0, operators)) it.result else 0L }
                .sumOf { it }
                .toString()
        }
    }

    private val operatorsA: List<(Long, Long) -> Long> = listOf(
        { a, b -> a + b },
        { a, b -> a * b }
    )

    private val operatorsB = operatorsA + { a, b -> (a.toString() + b.toString()).toLong() }

    override fun partA() = solution(operatorsA)
    override fun partB() = solution(operatorsB)
}
