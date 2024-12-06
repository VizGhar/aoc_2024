typealias Position = Pair<Int, Int>

class Day8 : Day() {

    private data class Antenna(val x: Int, val y: Int, val c: Char)

    private data class Input(
        val width: Int,
        val height: Int,
        val antennas: Map<Char, List<Antenna>>
    )

    private val transformedInput by lazy {
        Input(input[0].length, input.size, input.mapIndexed { y, s ->
            s.mapIndexedNotNull { x, c -> Antenna(x, y, c).takeIf { it.c != '.' } }
        }.flatten().groupBy { it.c })
    }

    private inline fun countAntinodes(anitinodes: (a: Antenna, b: Antenna, dx: Int, dy: Int) -> List<Position>) =
        transformedInput.antennas.map { (_, antennas) ->
            (0..<antennas.size - 1).map { i ->
                (i + 1..<antennas.size).map { j ->
                    anitinodes(antennas[i], antennas[j], antennas[i].x - antennas[j].x, antennas[i].y - antennas[j].y)
                }
            }.flatten().flatten().filter {
                it.first in 0..<transformedInput.width && it.second in 0..<transformedInput.height
            }
        }.flatten().distinct().size

    override fun partA() =
        countAntinodes { a, b, dx, dy -> listOf(a.x + dx to a.y + dy, b.x - dx to b.y - dy) }.toString()

    override fun partB() =
        countAntinodes { a, b, dx, dy ->
            mutableListOf<Pair<Int, Int>>().apply {
                var (x1, y1) = a
                while (x1 in 0..<transformedInput.width && y1 in 0..<transformedInput.height) {
                    add(x1 to y1); x1 += dx; y1 += dy
                }
                var (x2, y2) = b
                while (x2 in 0..<transformedInput.width && y2 in 0..<transformedInput.height) {
                    add(x2 to y2); x2 -= dx; y2 -= dy
                }
            }
        }.toString()

}
