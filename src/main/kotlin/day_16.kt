import java.util.*

class Day16 : Day() {

    data class Input(val map: List<String>, val start: Position, val end: Position)

    private val transformedInput by lazy {
        Input(
            map = input,
            start = input.first { it.contains("S") }.indexOf('S') to input.indexOfFirst { it.contains("S") },
            end = input.first { it.contains("E") }.indexOf('E') to input.indexOfFirst { it.contains("E") }
        )
    }

    private data class ScoredPosition(
        val x: Int,
        val y: Int,
        val score: Long,
        val path: List<Position>,
        val direction: Int
    )

    private data class Result(
        val bestScore: Long,
        val bestPaths: List<List<Position>>
    )

    private val result by lazy {
        val (map, start, end) = transformedInput
        val directions = listOf(-1 to 0, 0 to -1, 1 to 0, 0 to 1)
        val bestScores = Array(140) { Array(140) { LongArray(4) { Long.MAX_VALUE } } }
        val queue = PriorityQueue(compareBy<ScoredPosition> { it.score })
        val optimalPaths = mutableListOf<List<Pair<Int, Int>>>()
        var minScore = Long.MAX_VALUE

        directions.forEachIndexed { dir, _ ->
            queue.add(ScoredPosition(start.x, start.y, if (dir == 2) 0 else 1000, listOf(start.x to start.y), dir))
            bestScores[start.x][start.y][dir] = if (dir == 2) 0 else 1000
        }

        while (queue.isNotEmpty()) {
            val (x, y, score, path, dir) = queue.poll()
            if (score > bestScores[x][y][dir]) continue

            when {
                x to y != end -> {}
                score < minScore -> { minScore = score; optimalPaths.clear(); optimalPaths.add(path) }
                score == minScore -> optimalPaths += path
            }

            directions.forEachIndexed { newDir, (dx, dy) ->
                val new = x + dx to y + dy
                if (map[new.y][new.x] == '#') return@forEachIndexed

                val newScore = score + if (newDir == dir) 1 else 1001
                if (newScore <= bestScores[new.x][new.y][newDir]) {
                    bestScores[new.x][new.y][newDir] = newScore
                    queue.add(ScoredPosition(new.x, new.y, newScore, path + (new.x to new.y), newDir))
                }
            }
        }
        Result(minScore, optimalPaths)
    }

    override fun partA() = result.bestScore.toString()

    override fun partB() = result.bestPaths.flatten().toSet().size.toString()

}
