package day9

import util.Days
import util.solveDay

fun main() {
    solveDay(Day9())
}

class Day9 : Days {
    override fun solvePart1(): String {
        val heightMap = parseInput(readInputForDay())
        val lowRiskPoints = findLowPoints(heightMap).map { it + 1 }

        return lowRiskPoints.sum().toString()
    }

    private fun findLowPoints(heightMap: List<List<Int>>): List<Int> {
        val lowPoints = mutableListOf<Int>()
        val neighbours =
            listOf(::pointIsLowerThanTop, ::pointIsLowerThanBottom, ::pointIsLowerThanLeft, ::pointIsLowerThanRight)
        heightMap.forEachIndexed { i, line ->
            line.forEachIndexed { j, _ ->
                val isLower = neighbours.mapNotNull { it(i, j, heightMap) }.all { it }
                if (isLower) {
                    lowPoints.add(heightMap[i][j])
                }
            }
        }
        return lowPoints
    }

    private fun pointIsLowerThanTop(i: Int, j: Int, heightMap: List<List<Int>>): Boolean? {
        val current = heightMap[i][j]
        val top = heightMap.getOrNull(i - 1)?.get(j) ?: return null
        return current < top
    }

    private fun pointIsLowerThanBottom(i: Int, j: Int, heightMap: List<List<Int>>): Boolean? {
        val current = heightMap[i][j]
        val top = heightMap.getOrNull(i + 1)?.get(j) ?: return null
        return current < top
    }

    private fun pointIsLowerThanLeft(i: Int, j: Int, heightMap: List<List<Int>>): Boolean? {
        val current = heightMap[i][j]
        val top = heightMap[i].getOrNull(j - 1) ?: return null
        return current < top
    }

    private fun pointIsLowerThanRight(i: Int, j: Int, heightMap: List<List<Int>>): Boolean? {
        val current = heightMap[i][j]
        val top = heightMap[i].getOrNull(j + 1) ?: return null
        return current < top
    }


    override fun solvePart2(): String {
        TODO("Not yet implemented")
    }

    private fun parseInput(input: List<String>): List<List<Int>> {
        return input.map { l -> l.split("").filterNot { it == "" }.map { it.toInt() } }
    }
}