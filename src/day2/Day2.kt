package day2

import util.Days
import util.solveDay

fun main() {
    solveDay(Day2())
}

class Day2 : Days {
    override fun solvePart1(): String {
        val dirs = createDirections(readInputForDay())
        val (horizon, depth) = processDirHorizontalAndDepth(dirs)
        return (horizon * depth).toString()
    }

    override fun solvePart2(): String {
        val dirs = createDirections(readInputForDay())
        val (horizon, depth) = processDirHorizontalDepthAndAim(dirs)
        return (horizon * depth).toString()
    }

    data class Direction(val direction: String, val amount: Int)

    private fun createDirections(input: List<String>): List<Direction> {
        return input.map { it.split(" ") }.map { Direction(it[0], it[1].toInt()) }
    }

    private fun processDirHorizontalAndDepth(dirs: List<Direction>): Pair<Int, Int> {
        var horizontal = 0
        var depth = 0
        dirs.forEach {
            when (it.direction) {
                "forward" -> horizontal += it.amount
                "down" -> depth += it.amount
                "up" -> depth -= it.amount
            }
        }
        return Pair(horizontal, depth)
    }

    private fun processDirHorizontalDepthAndAim(dirs: List<Direction>): Pair<Int, Int> {
        var horizontal = 0
        var depth = 0
        var aim = 0
        dirs.forEach {
            when (it.direction) {
                "forward" -> {
                    horizontal += it.amount
                    depth += aim * it.amount
                }
                "down" -> aim += it.amount
                "up" -> aim -= it.amount
            }
        }
        return Pair(horizontal, depth)
    }
}