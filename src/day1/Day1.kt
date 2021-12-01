package day1

import util.Days
import util.solveDay


fun main() {
    solveDay(Day1())
}


class Day1 : Days {
    override fun solvePart1(): String {
        return findNumDepthIncrease(readInputForDayToIntList()).toString()
    }

    override fun solvePart2(): String {
        val depths = readInputForDayToIntList()
        val rollingWindow = rollingWindowThreeLong(depths)
        return findNumDepthIncrease(rollingWindow).toString()
    }


    private fun findNumDepthIncrease(depths: List<Int>): Int {
        var currentDepth = depths[0]
        var gotDeeper = 0
        for (d in depths.drop(1)) {
            if (d > currentDepth) {
                gotDeeper += 1
            }
            currentDepth = d
        }
        return gotDeeper
    }


    private fun rollingWindowThreeLong(depths: List<Int>): List<Int> {
        val rollingWindow = mutableListOf<Int>()
        for (i in 0..depths.size) {
            rollingWindow.add(depths.subList(i, i + 3).sum())
            if (i + 3 >= depths.size) {
                break
            }
        }
        return rollingWindow.toList()
    }

}