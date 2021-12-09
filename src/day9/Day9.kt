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

    override fun solvePart2(): String {
        val heightMap = readInputForDay().map { l -> l.split("").filterNot { it == "" }.map { Point(it, false) } }
        val allBasinSizes = createBasinsFromMap(heightMap)
        val size = allBasinSizes.sortedDescending().subList(0, 3).reduce { acc, i -> acc * i }
        return size.toString()
    }

    private fun createBasinsFromMap(heightMap: List<List<Point>>): List<Int> {
        val basinSize = mutableListOf<Int>()
        heightMap.flatten().filter { it.s == "9" }.forEach { it.inBasin = true }
        for (i in heightMap.indices) {
            for (j in heightMap[i].indices) {
                if (!heightMap[i][j].inBasin) {
                    heightMap[i][j].inBasin = true
                    basinSize += (1 + goAllDirs.sumOf { it(i, j, heightMap) })
                }
            }
        }
        return basinSize
    }

    private fun printMap(heightMap: List<List<Point>>) {
        for (i in heightMap.indices) {
            for (j in heightMap[i].indices) {
                if (heightMap[i][j].inBasin) {
                    print("s")
                } else {
                    print(heightMap[i][j].s)
                }
            }
            println()
        }
        println()
    }

    private val goAllDirs = listOf(::goNorth, ::goSouth, ::goLeft, ::goRight)

    private fun goNorth(i: Int, j: Int, heightMap: List<List<Point>>): Int {
        val iNew = i - 1
        val point = heightMap.getOrNull(iNew)?.getOrNull(j)
        return if (point == null) {
            0
        } else if (point.inBasin) {
            0
        } else {
            point.inBasin = true
            1 + goAllDirs.sumOf { it(iNew, j, heightMap) }
        }
    }

    private fun goSouth(i: Int, j: Int, heightMap: List<List<Point>>): Int {
        val iNew = i + 1
        val point = heightMap.getOrNull(iNew)?.getOrNull(j)
        return if (point == null) {
            0
        } else if (point.inBasin) {
            0
        } else {
            point.inBasin = true
            1 + goAllDirs.sumOf { it(iNew, j, heightMap) }
        }
    }

    private fun goLeft(i: Int, j: Int, heightMap: List<List<Point>>): Int {
        val jNew = j - 1
        val point = heightMap.getOrNull(i)?.getOrNull(jNew)
        return if (point == null) {
            0
        } else if (point.inBasin) {
            0
        } else {
            point.inBasin = true
            1 + goAllDirs.sumOf { it(i, jNew, heightMap) }
        }
    }

    private fun goRight(i: Int, j: Int, heightMap: List<List<Point>>): Int {
        val jNew = j + 1
        val point = heightMap.getOrNull(i)?.getOrNull(jNew)
        return if (point == null) {
            0
        } else if (point.inBasin) {
            0
        } else {
            point.inBasin = true
            1 + goAllDirs.sumOf { it(i, jNew, heightMap) }
        }
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


    private fun parseInput(input: List<String>): List<List<Int>> {
        return input.map { l -> l.split("").filterNot { it == "" }.map { it.toInt() } }
    }

    data class Point(val s: String, var inBasin: Boolean)
}