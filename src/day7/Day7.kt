package day7

import util.Days
import util.solveDay
import kotlin.math.abs

fun main() {
    solveDay(Day7())
}

class Day7 : Days {
    override fun solvePart1(): String {
        val sortedCrabList = readInputForDay()[0].split(",").map { it.toInt() }.sorted()
        val middle = sortedCrabList.size / 2
        var median = 0
        median = if (middle % 2 == 0) {
            (sortedCrabList[middle - 1] + sortedCrabList[middle + 1]) / 2
        } else {
            sortedCrabList[middle]
        }
        val totalFuel =sortedCrabList.sumOf { abs(it - median) }
        return totalFuel.toString()
    }



    override fun solvePart2(): String {
        TODO("Not yet implemented")
    }
}