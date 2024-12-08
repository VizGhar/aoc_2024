class Day5 : Day() {

    private data class Input(
        val orders: List<Pair<Int, Int>>,
        val updates: List<List<Int>>
    )

    private val transformedInput by lazy {
        Input(
            orders = input.takeWhile { it.contains("|") }.map { val (a, b) = it.split("|").map { it.toInt() }; a to b },
            updates = input.takeLastWhile { it.contains(",") }.map { it.split(",").toList().map { it.toInt() } }
        )
    }

    override fun partA() = transformedInput.updates.filter { update ->
        transformedInput.orders
            .filter { it.first in update }
            .all { order ->
                val (a, b) = order
                update.indexOf(a) < (update.indexOf(b).takeIf { it != -1 } ?: Int.MAX_VALUE)
            }
    }.sumOf { it[it.size / 2] }.toString()

    override fun partB() = transformedInput.updates.sumOf { update ->
        val mutable = update.toMutableList()
        while (true) {
            val (a, b) = transformedInput.orders.firstOrNull { order ->
                mutable.indexOf(order.first) > (mutable.indexOf(order.second).takeIf { it != -1 } ?: Int.MAX_VALUE)
            } ?: break
            val index1 = mutable.indexOf(a)
            val index2 = mutable.indexOf(b)
            mutable[index2] = a
            mutable[index1] = b
        }
        mutable.takeIf { it != update }?.let { it[it.size / 2] } ?: 0
    }.toString()
}
