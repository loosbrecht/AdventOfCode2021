package day8

import util.Days
import util.solveDay

fun main() {
    solveDay(Day8())
}


class Day8 : Days {
    private val zeroSignalCount = 6
    private val oneSignalCount = 2
    private val twoSignalCount = 5
    private val threeSignalCount = 5
    private val fourSignalCount = 4
    private val fiveSignalCount = 5
    private val sixSignalCount = 6
    private val sevenSignalCount = 3
    private val eightSignalCount = 7
    private val nineSignalCount = 6
    override fun solvePart1(): String {

        val possibleLengths = arrayOf(eightSignalCount, oneSignalCount, fourSignalCount, sevenSignalCount)
        val outputValues = readInputForDay().map { it.split("|")[1] }
        val allOutputValues = outputValues.map { it.split(" ") }.flatten()
        val counter = allOutputValues.count { it.length in possibleLengths }
        return counter.toString()
    }

    override fun solvePart2(): String {
        val input = readInputForDay()
        var sum = 0
        for (inp in input) {
            val split = inp.split(" | ")
            val decoder = createDigitOtherWay(split[0])
            sum += decodeNumber(decoder, split[1])
        }
        return sum.toString()
    }

    private fun decodeNumber(decoder: Map<Int, java.util.HashSet<Char>>, s: String): Int {
        val input = s.split(" ").filter { it != "" }
        var s = ""
        for (i in input) {
            val set = i.toHashSet()
            for (kv in decoder) {
                if (set == kv.value) {
                    s += kv.key
                    break
                }
            }
        }
        return Integer.parseInt(s)
    }

    private fun createDigitOtherWay(input: String): Map<Int, HashSet<Char>> {
        val parsed = input.split(" ").filterNot { it == "" }.map { it.toCharArray() }.toMutableList()
        val one = parsed.first { it.size == oneSignalCount }
        parsed.remove(one)
        val four = parsed.first { it.size == fourSignalCount }
        parsed.remove(four)
        val seven = parsed.first { it.size == sevenSignalCount }
        parsed.remove(seven)
        val eight = parsed.first { it.size == eightSignalCount }
        parsed.remove(eight)
        val six = parsed.filter { it.size == sixSignalCount }.first { one.toList().subtract(it.toSet()).size == 1 }
        parsed.remove(six)
        val nine =
            parsed.filter { it.size == zeroSignalCount }.first { it.subtract(seven.union(four.toSet())).size == 1 }
        parsed.remove(nine)
        val zero = parsed.first { it.size == nineSignalCount }
        parsed.remove(zero)
        val five = parsed.filter { it.size == fiveSignalCount }.first { six.subtract(it.toSet()).size == 1 }
        parsed.remove(five)
        val three = parsed.filter { it.size == threeSignalCount }.first { nine.subtract(it.toSet()).size == 1 }
        parsed.remove(three)
        val two = parsed.first()

        return mapOf<Int, HashSet<Char>>(
            1 to one.toHashSet(),
            2 to two.toHashSet(),
            3 to three.toHashSet(),
            4 to four.toHashSet(),
            5 to five.toHashSet(),
            6 to six.toHashSet(),
            7 to seven.toHashSet(),
            8 to eight.toHashSet(),
            9 to nine.toHashSet(),
            0 to zero.toHashSet()
        )
    }


}