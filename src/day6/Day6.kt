package day6

import util.Days
import util.solveDay

fun main() {
    solveDay(Day6())
}

class Day6 : Days {
    override fun solvePart1(): String {
        // val input = "3,4,3,1,2".split(",").map { Fish(it.toInt()) }
        val input = readInputForDay()[0].split(",").map { it.toInt() }
        return holdLanternFishesInBuckets(input, 80).toString()
    }

    override fun solvePart2(): String {
        val input = readInputForDay()[0].split(",").map { it.toInt() }
        return holdLanternFishesInBuckets(input, 256).toString()
    }

    data class Fish(var fish: Int)

    private fun simulateLanternFishes(fishesInput: List<Fish>, totalDays: Int): Long {
        val fishes = fishesInput.toMutableList()
//        print("Day\t0 ")
//        println(fishes.map { it.fish })
        for (i in 1..totalDays) {
            val newBorns = fishes.count { it.fish == 0 }
            fishes.filter { it.fish == 0 }.forEach { it.fish = 7 }
            fishes.forEach { it.fish-- }
            repeat(newBorns) { fishes.add(Fish(8)) }
//            print("Day\t$i ")
//            println(fishes.map { it.fish })
        }
        return fishes.size.toLong()
    }

    private fun holdLanternFishesInBuckets(fishesInput: List<Int>, totalDays: Int): Long {
        var day0: Long = 0L
        var day1: Long = fishesInput.count { it == 1 }.toLong()
        var day2: Long = fishesInput.count { it == 2 }.toLong()
        var day3: Long = fishesInput.count { it == 3 }.toLong()
        var day4: Long = fishesInput.count { it == 4 }.toLong()
        var day5: Long = fishesInput.count { it == 5 }.toLong()
        var day6: Long = fishesInput.count { it == 6 }.toLong()
        var day7: Long = fishesInput.count { it == 7 }.toLong()
        var day8: Long = 0L
        for (i in 1..totalDays) {
            val newborn = day0
            day0 = day1
            day1 = day2
            day2 = day3
            day3 = day4
            day4 = day5
            day5 = day6
            day6 = day7 + newborn
            day7 = day8
            day8 = newborn
        }
        return day0 + day1 + day2 + day3 + day4 + day5 + day6 + day7 + day8
    }

}