import java.io.File
import kotlin.system.measureTimeMillis

abstract class Day {
    protected val input by lazy { File("input/aoc_2024_${this::class.simpleName!!.takeLastWhile { it.isDigit() }}.txt").readLines() }
    protected abstract fun partA(): String
    protected abstract fun partB(): String
    fun solve() {
        val timeInMillis = measureTimeMillis {
            runCatching { println(partA()) }.exceptionOrNull()?.printStackTrace()
            runCatching { println(partB()) }.exceptionOrNull()?.printStackTrace()
        }
        println("Day ${this::class.simpleName!!.takeLastWhile { it.isDigit() }} solved in $timeInMillis ms")
    }
}

fun runAll() {
    val timeInMillis = measureTimeMillis {
        Day1().solve()
        Day2().solve()
        Day3().solve()
        Day4().solve()
        Day5().solve()
        Day6().solve()
        Day7().solve()
        Day8().solve()
        Day9().solve()
        Day10().solve()
    }
    println("All solved in $timeInMillis ms")
}

fun main() {
    runAll()
}