class Day14 : Day() {

    private val width = 101
    private val height = 103

    data class Input(
        var position: Position,
        val velocity: Position
    )

    fun List<Input>.next() = map {
        it.copy(position = (it.position.x + it.velocity.x + width) % width to (it.position.y + it.velocity.y + height) % height)
    }

    private val transformedInput by lazy {
        input.map {
            Input(
                position = it.substringAfter("=").substringBefore(",").toInt() to it.substringAfter(",").substringBefore(" ").toInt(),
                velocity = it.substringAfterLast("=").substringBefore(",").toInt() to it.substringAfterLast(",").substringBefore(" ").toInt(),
            )
        }
    }

    override fun partA() = run {
        var state = transformedInput
        repeat(100) { state = state.next() }
        state.count { it.position.x < width / 2 && it.position.y < height / 2 } *
        state.count { it.position.x < width / 2 && it.position.y > height / 2 } *
        state.count { it.position.x > width / 2 && it.position.y < height / 2 } *
        state.count { it.position.x > width / 2 && it.position.y > height / 2 }
    }.toString()

    override fun partB(): String {
        var state = transformedInput
        for (i in 1..Int.MAX_VALUE) {
            state = state.next()
            val positions = state.map { it.position }
            if (positions.count { it.neighbors.any { n -> n in positions } } > state.size / 2) return i.toString()
        }
        return ""
    }
}
