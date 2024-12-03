class Day3 : Day() {

    private val transformedInput by lazy { input.joinToString("") }

    private val multiplications by lazy {
        "mul\\(([1-9][0-9]{0,2}),([1-9][0-9]{0,2})\\)".toRegex().findAll(transformedInput)
    }

    private fun multiply(filter: Sequence<MatchResult>): Long = filter.sumOf {
        val (_, a, b) = it.groupValues
        a.toLong() * b.toLong()
    }

    override fun partA() = multiply(multiplications).toString()

    override fun partB() = with((listOf(0 to true) + "do\\(\\)|don't\\(\\)".toRegex()
        .findAll(transformedInput)
        .map { it.range.first to (it.value == "do()") }
        .toList() + listOf(transformedInput.length to false))
        .zipWithNext()
        .mapNotNull { (prev, curr) -> (prev.first..curr.first).takeIf { prev.second } }
    ) {
        multiply(multiplications.filter { mul -> this.any { it.contains(mul.range.first) } }).toString()
    }
}