package day1

import readInputForDay


fun main() {
   Day1().solvePart1()
}


class Day1 {

    fun solvePart1() {
        val lines = readInputForDay(1)
        val depths = lines.map { it.toInt() }
        var currentDepth = depths[0]
        var gotDeeper = 0
        for (d in depths.drop(1)) {
            if (d > currentDepth) {
                gotDeeper += 1
            }
            currentDepth = d

        }
        println(gotDeeper)
    }

}