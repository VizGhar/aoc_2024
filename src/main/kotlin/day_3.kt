class Day3 : Day() {

    private val transformedInput by lazy { "do()${input.joinToString("")}do()" }

    private val multiplications by lazy { "mul\\((\\d+),(\\d+)\\)".toRegex().findAll(transformedInput) }

    private fun multiply(filter: Sequence<MatchResult>): Long = filter.sumOf {
        it.groupValues[1].toLong() * it.groupValues[2].toLong()
    }

    override fun partA() = multiply(multiplications).toString()

    override fun partB() = "do\\(\\)|don't\\(\\)".toRegex().findAll(transformedInput)
        .zipWithNext()
        .mapNotNull { (prev, curr) -> (prev.range.first..curr.range.first).takeIf { prev.value == "do()" } }
        .run { multiply(multiplications.filter { mul -> this.any { it.contains(mul.range.first) } }) }
        .toString()

}