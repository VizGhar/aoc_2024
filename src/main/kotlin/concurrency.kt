import kotlinx.coroutines.*

inline fun <T, R> Iterable<T>.asyncMap(crossinline transform: (T) -> R): List<R> {
    return runBlocking {
        withContext(Dispatchers.Default) {
            map { async { transform(it) } }.awaitAll()
        }
    }
}