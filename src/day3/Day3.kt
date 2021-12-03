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
        val data = readInputForDay()
        val oxygenGeneratorRatingValue = calculateOxygenGeneratorRatingValue(data)
        val cO2ScrubberRatingValue = calculateCO2ScrubberRatingValue(data)
        return (oxygenGeneratorRatingValue * cO2ScrubberRatingValue).toString()
    }

    private fun gammaRate(binNums: List<String>): String = (binNums[0].indices).map { i ->
        binNums.map { it[i] }.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
    }.joinToString("")


    private fun epsilonRate(binNums: List<String>): String = (binNums[0].indices).map { i ->
        binNums.map { it[i] }.groupingBy { it }.eachCount().minByOrNull { it.value }?.key
    }.joinToString("")

    private fun calculateOxygenGeneratorRatingValue(binNums: List<String>): Int {
        var workingBinNums = binNums.toMutableList()
        for (i in binNums[0].indices) {
            workingBinNums = filterMostCommonBitPosition(i, workingBinNums) as MutableList<String>
            if (workingBinNums.size == 1) {
                return Integer.parseInt(workingBinNums[0], 2)
            }
        }
        return -1
    }

    private fun calculateCO2ScrubberRatingValue(binNums: List<String>): Int {
        var workingBinNums = binNums.toMutableList()
        for (i in binNums[0].indices) {
            workingBinNums = filterLeastCommonBitPosition(i, workingBinNums) as MutableList<String>
            if (workingBinNums.size == 1) {
                return Integer.parseInt(workingBinNums[0], 2)
            }
        }
        return -1
    }

    private fun filterMostCommonBitPosition(pos: Int, binNums: List<String>): List<String> {
        val eachCount = binNums.map { it[pos] }.groupingBy { it }.eachCount()
        var mostCommon = eachCount.maxByOrNull { it.value }?.key!!
        if (eachCount['1'] == eachCount['0']) {
            mostCommon = '1'
        }
        return binNums.filter { it[pos] == mostCommon }
    }

    private fun filterLeastCommonBitPosition(pos: Int, binNums: List<String>): List<String> {
        val eachCount = binNums.map { it[pos] }.groupingBy { it }.eachCount()
        var leastCommon = eachCount.minByOrNull { it.value }?.key!!
        if (eachCount['1'] == eachCount['0']) {
            leastCommon = '0'
        }
        return binNums.filter { it[pos] == leastCommon }
    }
}