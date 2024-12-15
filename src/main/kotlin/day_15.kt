class Day15 : Day() {

    private enum class Direction(val c: Char, val dx: Int, val dy: Int) {
        LEFT('<', -1, 0),
        TOP('^', 0, -1),
        RIGHT('>', 1, 0),
        BOTTOM('v', 0, 1)
    }

    private val Char.direction get() = Direction.entries.first { it.c == this }

    private data class Input(
        val startPos: Position,
        val walls: List<Position>,
        val boxes: List<Position>,
        val moves: List<Direction>,
        val boxWidth: Int
    )

    private operator fun Position.plus(direction: Direction) = x + direction.dx to y + direction.dy

    private val input1 by lazy {
        Input(
            startPos = input.first { it.contains("@") }.indexOf("@") to input.indexOfFirst { it.contains("@") },
            walls = input.takeWhile { it.isNotEmpty() }.mapIndexed { y, s -> s.mapIndexedNotNull { x, c -> if (c == '#') x to y else null } }.flatten(),
            boxes = input.takeWhile { it.isNotEmpty() }.mapIndexed { y, s -> s.mapIndexedNotNull { x, c -> if (c == 'O') x to y else null } }.flatten(),
            moves = input.takeLastWhile { it.isNotEmpty() }.joinToString("").map { it.direction },
            boxWidth = 1
        )
    }

    private val input2 by lazy {
        Input(
            startPos = input1.startPos.x * 2 to input1.startPos.y,
            walls = input1.walls.map { listOf(it.x * 2 to it.y, it.x * 2 + 1 to it.y) }.flatten(),
            boxes = input1.boxes.map { it.x * 2 to it.y },
            moves = input1.moves,
            boxWidth = 2
        )
    }

    private fun tryMoveBoxes(boxes: List<Position>, walls: List<Position>, direction: Direction, position: Position, width: Int): Pair<List<Position>, Boolean> {
        var positions = listOf(position + direction)
        val move = mutableListOf<Position>()
        do {
            if (positions.any { it in walls }) return boxes to false
            val boxesToMove = boxes.filter { box -> box !in move && positions.any { position -> position.x in (box.x..<box.x+width) && position.y == box.y } }
            if (boxesToMove.isEmpty()) { break }
            move += boxesToMove
            positions = boxesToMove.map { box -> (0..<width).map { box.x + direction.dx + Direction.RIGHT.dx * it to box.y + direction.dy } }.flatten()
        } while (true)
        return boxes.map { if (it in move) it + direction else it } to true
    }

    private fun solver(input: Input) = run {
        val walls = input.walls
        var boxes = input.boxes

        var pos = input.startPos
        for (move in input.moves) {
            val (movedBoxes, moved) = tryMoveBoxes(boxes, walls, move, pos, input.boxWidth)
            if (moved) {
                boxes = movedBoxes
                pos += move
            }
        }
        boxes.sumOf { it.x + 100 * it.y }
    }

    override fun partA() = solver(input1).toString()

    override fun partB() = solver(input2).toString()

}
