package util

import java.io.File


interface Days {

    fun solvePart1(): String
    fun solvePart2(): String
    fun dayNum(): String {
        val name = javaClass.simpleName
        return name.replace("Day", "")
    }

    fun readInput(name: String) = File("src/input", "$name.txt").readLines()
    fun readInputForDay() = readInput("day${dayNum()}")
    fun readInputForDayToIntList() = readInput("day${dayNum()}").map { it.toInt() }
}

fun solveDay(d: Days) {
    println("Solution for day ${d.dayNum()}")
    println("Part 1: ${d.solvePart1()}")
    println("Part 2: ${d.solvePart2()}")
}


fun main() {
    val day = 13
    val f = File("src/day$day")
    f.mkdir()
    File("src/day$day/Day$day.kt").createNewFile()
    File("src/input/day$day.txt").createNewFile()
}