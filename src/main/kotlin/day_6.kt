class Day6 : Day() {

    // Left = 0; Top = 1; Right = 2; Bottom = 3
    private val directions = listOf(-1 to 0, 0 to -1, 1 to 0, 0 to 1)

    private data class Input(val map: List<String>, val startX: Int, val startY: Int, val direction: Int = 1)

    private val transformedInput by lazy {
        Input(
            map = input,
            startX = input.first { it.contains('^') }.indexOf('^'),
            startY = input.indexOfFirst { it.contains('^') }
        )
    }

    data class OrientedPosition(val x: Int, val y: Int, val dir: Int)

    private fun visits(obstacle: Pair<Int, Int> = -1 to -1): List<Pair<Int, Int>> {
        var (map, x, y, direction) = transformedInput
        val memo = mutableSetOf<OrientedPosition>()
        val visited = mutableListOf(x to y)
        while (y + directions[direction].second in map.indices && x + directions[direction].first in map[0].indices) {
            while (
                map[y + directions[direction].second][x + directions[direction].first] == '#' ||
                (x + directions[direction].first == obstacle.first) && (y + directions[direction].second == obstacle.second)
            ) {
                if (!memo.add(OrientedPosition(x, y, direction))) return emptyList()
                direction = (direction + 1) % 4
            }
            y += directions[direction].second
            x += directions[direction].first
            visited += x to y
        }
        return visited.distinct()
    }

    override fun partA() = visits().size.toString()

    override fun partB() =
        (visits() - (transformedInput.startX to transformedInput.startY))
            .asyncMap { visits(it).isEmpty() }
            .count { it }
            .toString()

}
