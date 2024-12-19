class Day19 : Day() {

    private data class Input(
        val availablePatterns: List<String>,
        val required: List<String>
    )

    private val transformedInput by lazy {
        Input(
            availablePatterns = input[0].split(", "),
            required = input.drop(2)
        )
    }

    private val memo = mutableMapOf<String, Long>()

    private fun countPossibilities(
        patterns: List<String>,
        remaining: String,
        used: List<String>
    ): Long {
        memo[remaining]?.let { return it }          // memoization
        if (remaining.isEmpty()) return 1           // stop condition
        return patterns.sumOf { pattern ->          // recursion
            if (remaining.startsWith(pattern)) {
                countPossibilities(
                    patterns,
                    remaining.drop(pattern.length),
                    used + pattern
                )
            } else 0
        }.also { memo[remaining] = it }
    }

    private val possibilities by lazy {
        transformedInput.required.map {
            memo.clear()
            countPossibilities(transformedInput.availablePatterns, it, emptyList())
        }
    }

    override fun partA() = possibilities.count { it > 0 }.toString()

    override fun partB() = possibilities.sumOf { it }.toString()

}
