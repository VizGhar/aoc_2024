class Day9 : Day() {

    private data class Block(
        val id: Int,
        val index: Int,
        val size: Int
    )

    private data class Input(
        val gaps: List<Block>,
        val blocks: List<Block>
    )

    private val transformedInput by lazy {
        val gaps = mutableListOf<Block>()
        val blocks = mutableListOf<Block>()
        input[0].forEachIndexed { index, c ->
            Block(
                id = index / 2,
                index = input[0].substring(0, index).sumOf { it.digitToInt() },
                size = c.digitToInt()
            ).let {
                if (index % 2 == 1) gaps.add(it) else blocks.add(it)
            }
        }
        Input(gaps, blocks)
    }

    override fun partA() = run {
        val gaps = transformedInput.gaps.toMutableList()
        val blocks = transformedInput.blocks.toMutableList()
        for (blockId in transformedInput.blocks.size - 1 downTo 0) {
            val block = transformedInput.blocks[blockId]
            if (gaps[0].index > block.index) break
            blocks.remove(block)
            gaps.add(block.copy(id = 0))
            gaps.sortBy { it.index }
            var toMove = block.size
            while (toMove != 0) {
                val gap = gaps.removeFirst()
                blocks.add(Block(block.id, gap.index, minOf(toMove, gap.size)))
                if (gap.size > toMove) {
                    gaps.add(0, Block(0, gap.index + toMove, gap.size - toMove))
                    toMove = 0
                } else {
                    toMove -= gap.size
                }
            }
        }

        blocks.sumOf { block ->
            (block.index ..< block.index + block.size).sumOf { it.toLong() * block.id }
        }
    }.toString()

    override fun partB() = run {
        val gaps = transformedInput.gaps.toMutableList()
        val blocks = transformedInput.blocks.toMutableList()

        for (blockId in transformedInput.blocks.size - 1 downTo 0) {
            val block = transformedInput.blocks[blockId]
            if (gaps.none { it.size >= block.size && it.index < block.index }) continue
            val gapIndex = gaps.indexOfFirst { it.size >= block.size }
            val gap = gaps[gapIndex]
            // decrease gap size
            if (gap.size == block.size)
                gaps.removeAt(gapIndex)
            else
                gaps[gapIndex] = gap.copy(size = gap.size - block.size, index = gap.index + block.size)
            // move block
            blocks.remove(block)
            blocks.add(block.copy(index = gap.index))
        }
        blocks.sumOf { block ->
            (block.index ..< block.index + block.size).sumOf { it.toLong() * block.id }
        }
    }.toString()
}
