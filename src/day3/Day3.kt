package day3

import util.Days
import util.solveDay

fun main() {
    solveDay(Day3())
}


class Day3 : Days {
    override fun solvePart1(): String {
        val data = readInputForDay()
        val gamma = gammaRate(data)
        val epsilon = epsilonRate(data)
        val powerConsumption = Integer.valueOf(gamma, 2) * Integer.valueOf(epsilon, 2)
        return powerConsumption.toString()
    }

    override fun solvePart2(): String {
        TODO("Not yet implemented")
    }

    private fun gammaRate(binNums: List<String>): String = (binNums[0].indices).map { i ->
        binNums.map { it[i] }.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
    }.joinToString("")


    private fun epsilonRate(binNums: List<String>): String = (binNums[0].indices).map { i ->
        binNums.map { it[i] }.groupingBy { it }.eachCount().minByOrNull { it.value }?.key
    }.joinToString("")


}