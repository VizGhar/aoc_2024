typealias Position = Pair<Int, Int>
typealias LPosition<T> = Pair<T, T>

val Position.x get() = first
val Position.y get() = second

val Position.neighbors get() = listOf(
    Position(x - 1, y),
    Position(x, y - 1),
    Position(x + 1, y),
    Position(x, y + 1)
)

val <T> LPosition<T>.x get() = first
val <T> LPosition<T>.y get() = second