import kotlin.math.abs

class Day20 : Day() {

    data class Input(val map: List<String>, val start: Position, val end: Position)

    private val transformedInput by lazy {
        Input(
            map = input,
            start = input.first { it.contains("S") }.indexOf('S') to input.indexOfFirst { it.contains("S") },
            end = input.first { it.contains("E") }.indexOf('E') to input.indexOfFirst { it.contains("E") }
        )
    }

    private fun findPath(ignore: Position = -1 to -1): List<Position> {
        val directions = listOf(-1 to 0, 0 to -1, 1 to 0, 0 to 1)
        val expand = mutableListOf(listOf(transformedInput.start))
        val visited = mutableListOf<Position>()
        while (expand.isNotEmpty()) {
            val expandingPath = expand.removeFirst()
            val expanding = expandingPath.last()
            if (expanding in visited) continue
            visited += expanding
            if (expanding == transformedInput.end) {
                return expandingPath
            }
            directions.map { (dx, dy) ->
                if (expanding.x + dx !in 0..<transformedInput.map[0].length || expanding.y + dy !in 0..transformedInput.map.size) return@map
                if (transformedInput.map[expanding.y + dy][expanding.x + dx] == '#' && expanding.x + dx to expanding.y + dy != ignore) return@map
                expand += expandingPath + (expanding.x + dx to expanding.y + dy)
            }
        }
        return emptyList()
    }

    private val path = findPath()

    private fun calc(allowedShortCutLength: Int, requiredSave: Int = 100) =
        path.sumOf { start ->
            val i = path.indexOf(start)
            path
                .drop(i + 1)
                .count { target ->
                    if (abs(target.x - start.x) + abs(target.y - start.y) > allowedShortCutLength) false
                    else path.size - (i + path.size - path.indexOfFirst{ it == target } + abs(target.x - start.x) + abs(target.y - start.y)) >= requiredSave
                }
        }

    override fun partA() = calc(2).toString()

    override fun partB() = calc(20, 100).toString()

}
