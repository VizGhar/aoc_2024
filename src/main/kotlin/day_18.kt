class Day18 : Day() {

    private val size: Int = 70
    private val initial: Int = 1024

    private val transformedInput by lazy {
        input.map { it.substringBefore(",").toInt() to it.substringAfter(",").toInt() }
    }

    private fun shortestPath(locations: List<Position>): List<Position> {
        val directions = listOf(-1 to 0, 0 to -1, 1 to 0, 0 to 1)
        val expand = mutableListOf(listOf(0 to 0))
        val visited = mutableListOf<Position>()
        while (expand.isNotEmpty()) {
            val expandingPath = expand.removeFirst()
            val expanding = expandingPath.last()
            if (expanding in visited) continue

            visited += expanding
            if (expanding == size to size) return expandingPath
            directions.map {  (dx, dy) ->
                if (expanding.x + dx !in 0..size || expanding.y + dy !in 0..size) return@map
                if (expanding.x + dx to expanding.y + dy in locations) return@map
                expand += expandingPath + (expanding.x + dx to expanding.y + dy)
            }
        }
        return emptyList()
    }

    override fun partA() = run {
        shortestPath(transformedInput.take(initial)).size - 1
    }.toString()

    override fun partB() = run {
        val obstacles = transformedInput.take(initial).toMutableList()
        var actualPath = shortestPath(obstacles)
        val additionalObstacles = transformedInput.drop(initial)
        for (additionalObstacle in additionalObstacles) {
            obstacles += additionalObstacle
            if (additionalObstacle in actualPath) {
                actualPath = shortestPath(obstacles)
                if (actualPath.isEmpty()) return@run "${additionalObstacle.x},${additionalObstacle.y}"
            }
        }
    }.toString()

}