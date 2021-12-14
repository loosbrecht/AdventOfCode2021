package day12

import util.Days
import util.solveDay
import java.util.*

fun main() {
    solveDay(Day12())
}

class Day12 : Days {
    override fun solvePart1(): String {
        val allCaves = createCaveSystem(readInputForDay())
        findAllPaths(allCaves)
        return ""
    }

    override fun solvePart2(): String {
        TODO("Not yet implemented")
    }

    fun findAllPaths(caves: List<Cave>) {
        val start = caves.find { it.name == "start" }

        if (start != null) {
            val allPaths = goToNextCave(emptyList(), start)
            println(allPaths)
        }

    }

    fun goToNextCave(pathTillNow: List<Cave>, current: Cave): List<List<Cave>> {
        val pathWithCurrent = pathTillNow + current
        if (current.name == "end") {
            return listOf(pathTillNow)
        }
        if (pathTillNow.isNotEmpty() && current.name == "start") {
            return emptyList()
        }
        val count = pathWithCurrent.filter { !it.bigCave }.groupBy { it.name }.count { it.value.size > 1 }
        if (count > 1) {
            return listOf(pathWithCurrent)
        }
        val path = mutableListOf<List<Cave>>()
        for (next in current.connectedCaves) {
            val extraPath = goToNextCave(pathWithCurrent, next)
            for (ep in extraPath) {
                if (ep.isEmpty()){
                    continue
                }
                path.add(ep)
            }
        }
        return path
    }

    fun createCaveSystem(lines: List<String>): List<Cave> {
        val caves = mutableListOf<Cave>()
        for (l in lines) {
            val split = l.split("-")
            var c1 = caves.find { it.name == split[0] }
            var c2 = caves.find { it.name == split[1] }
            if (c1 == null) {
                c1 = Cave(split[0], mutableListOf(), isBigCave(split[0]))
                caves.add(c1)
            }
            if (c2 == null) {
                c2 = Cave(split[1], mutableListOf(), isBigCave(split[1]))
                caves.add(c2)
            }
            c1.connectedCaves.add(c2)
            c2.connectedCaves.add(c1)

        }
        return caves
    }

    fun isBigCave(c: String) = c.uppercase(Locale.getDefault()) == c

    class Cave(val name: String, var connectedCaves: MutableList<Cave>, var bigCave: Boolean) {
        override fun toString(): String {
            return name
        }
    }


}