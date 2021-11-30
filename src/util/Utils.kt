import java.io.File

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src/input", "$name.txt").readLines()

fun readInputForDay(day: Int) = File("src/input", "day$day.txt").readLines()


fun main() {
    val day = 2
    val f = File("src/day$day")
    f.mkdir()
    File("src/day$day/Day$day.kt").createNewFile()
    File("src/input/day$day.txt").createNewFile()
}