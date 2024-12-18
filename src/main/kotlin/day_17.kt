class Day17 : Day() {

    private data class Input(
        var registerA: Long,
        var registerB: Long,
        var registerC: Long,
        val program: List<Int>,
        var pointer: Int = 0
    )

    private val transformedInput by lazy {
        Input(
            registerA = input[0].substringAfter(": ").toLong(),
            registerB = input[1].substringAfter(": ").toLong(),
            registerC = input[2].substringAfter(": ").toLong(),
            program = input[4].substringAfter(": ").split(",").map { it.toInt() }
        )
    }

    private fun getComboOperand(input: Input, literalOperand: Int): Long = when (literalOperand) {
        4 -> input.registerA
        5 -> input.registerB
        6 -> input.registerC
        else -> literalOperand.toLong()
    }

    private fun runOver(input: Input, drop: Boolean): List<Int> {
        val result = mutableListOf<Int>()
        while (input.pointer < input.program.size) {
            val literalOperand = input.program[input.pointer + 1]
            val comboOperand = getComboOperand(input, literalOperand)
            when (input.program[input.pointer]) {
                0 -> input.registerA /= 1L shl minOf(64, comboOperand).toInt()
                1 -> input.registerB = input.registerB xor literalOperand.toLong()
                2 -> input.registerB = comboOperand and 0b111
                3 -> if (input.registerA != 0L) input.pointer = literalOperand - 2
                4 -> input.registerB = input.registerB xor input.registerC
                5 -> {
                    result += (comboOperand and 0b111).toInt()
                    if (drop) {
                        var matching = true
                        for (i in 0..<result.size) {
                            if (i >= transformedInput.program.size || result[i] != transformedInput.program[i]) matching =
                                false
                        }
                        if (!matching) return listOf(-1)
                    }
                }
                6 -> input.registerB = input.registerA / (1L shl minOf(64, comboOperand).toInt())
                7 -> input.registerC = input.registerA / (1L shl minOf(64, comboOperand).toInt())
            }
            input.pointer += 2
        }
        return result
    }

    override fun partA() = run {
        runOver(transformedInput.copy(), false).joinToString(",")
    }.toString()

    private data class Pattern(var text: String, var iteration: Int)

    override fun partB() = run {
        var patterns = mutableListOf(Pattern("", 0))
        while (true) {
            val newPatterns = mutableListOf<Pattern>()
            for (p in patterns) {
                val iteration = "${p.iteration.toString(8)}${p.text}".toLong(8)
                val out = runOver(transformedInput.copy(registerA = iteration), true)

                var matching = true
                for (a in out.indices) {
                    if (a >= transformedInput.program.size || out[a] != transformedInput.program[a]) matching = false
                }
                if (out == transformedInput.program) return@run iteration

                if (matching) {
                    val newPattern = iteration.toString(8).drop(1)
                    val newI = iteration.toString(8).take(1)
                    newPatterns += Pattern(newPattern, newI.toInt(8))
                }
            }
            if (newPatterns.isNotEmpty()) {
                patterns.addAll(newPatterns)
                val longestPattern = patterns.maxOf { it.text.length }
                patterns.removeIf { it.text.length < longestPattern - 1 }
                patterns = patterns.distinct().toMutableList()
            }
            for (p in patterns) {
                p.iteration++
            }
        }
    }.toString()

}
