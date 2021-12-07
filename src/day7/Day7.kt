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
        val fuel = calculatePositionWithMinimumFuel(sortedCrabList, ::calculateFuelOneCostForDistance)
        return fuel.toString()
    }

    override fun solvePart2(): String {
        val sortedCrabList = readInputForDay()[0].split(",").map { it.toInt() }.sorted()
        val fuel = calculatePositionWithMinimumFuel(sortedCrabList, ::calculateFuelGreaterDistanceGreaterCost)
        return fuel.toString()
    }

    private fun calculatePositionWithMinimumFuel(sortedCrabList: List<Int>, fuelCost: (Int) -> Int): Int {
        var totalFuel = Int.MAX_VALUE
        val minHorizontal = sortedCrabList.first()
        val maxHorizontal = sortedCrabList.last()
        for (alignPos in (minHorizontal..maxHorizontal)) {
            val newTotal = sortedCrabList.sumOf { fuelCost(abs(it - alignPos)) }
            if (newTotal < totalFuel) {
                totalFuel = newTotal
            }
        }
        return totalFuel
    }

    private fun calculateFuelGreaterDistanceGreaterCost(distance: Int): Int {
        return (0..distance).sum()
    }

    private fun calculateFuelOneCostForDistance(distance: Int): Int {
        return distance
    }
}