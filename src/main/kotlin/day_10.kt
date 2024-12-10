class Day10 : Day() {

    private data class Input(
        val startingPositions: List<Position>,
        val map: List<String>
    )

    private val transformedInput by lazy {
        Input(
            startingPositions = input.mapIndexed { y, line ->
                line.mapIndexedNotNull { x, c -> (x to y).takeIf { c == '0' } }
            }.flatten(),
            map = input
        )
    }

    private val allTrails by lazy { transformedInput.startingPositions.map { findTrails(it) } }

    private data class ScoredPosition(val x: Int, val y: Int, val score: Int)

    private fun findTrails(start: Position): List<Position> {
        val positions = mutableListOf(ScoredPosition(start.x, start.y, -1))
        val results = mutableListOf<Position>()
        while (positions.isNotEmpty()) {
            val (x, y, s) = positions.removeFirst()
            if (x < 0 || y < 0 || y >= transformedInput.map.size || x >= transformedInput.map[0].length) continue
            val value = transformedInput.map[y][x].digitToInt()
            if (value != s + 1) continue
            if (value == 9) { results += x to y; continue }
            positions += ScoredPosition(x + 1, y, value)
            positions += ScoredPosition(x - 1, y, value)
            positions += ScoredPosition(x, y + 1, value)
            positions += ScoredPosition(x, y - 1, value)
        }
        return results
    }

    override fun partA() = allTrails.sumOf { it.distinct().size }.toString()

    override fun partB() = allTrails.sumOf { it.size }.toString()

}
