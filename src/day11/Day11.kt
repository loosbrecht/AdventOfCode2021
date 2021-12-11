package day11

import util.Days
import util.solveDay

fun main() {
    solveDay(Day11())
}

class Day11 : Days {
    override fun solvePart1(): String {
        val octopuses = readInputForDay().map { l -> l.split("").filterNot { it == "" }.map { Octopus(it.toInt()) } }
        fillInNeighbours(octopuses)
        return goOverOctopuses(octopuses.flatten()).toString()
    }

    override fun solvePart2(): String {
        val octopuses = readInputForDay().map { l -> l.split("").filterNot { it == "" }.map { Octopus(it.toInt()) } }
        fillInNeighbours(octopuses)
        return goOverOctopusesTillAllFlash(octopuses.flatten()).toString()
    }

    private fun goOverOctopuses(octopus: List<Octopus>): Int {
        var flashes = 0
        val maxSteps = 100
        for (s in (1..maxSteps)) {
            octopus.forEach { it.addEnergyAndPossiblyFlashToAllNeighbours() }
            flashes += octopus.count { it.isFlashing() }
            octopus.filter { it.isFlashing() }.forEach { it.loseAllEnergy() }
        }
        return flashes
    }

    private fun goOverOctopusesTillAllFlash(octopus: List<Octopus>): Int {
        var step = 0
        while (true) {
            step += 1
            octopus.forEach { it.addEnergyAndPossiblyFlashToAllNeighbours() }
            if (octopus.all { it.isFlashing() }) {
                break
            }
            octopus.filter { it.isFlashing() }.forEach { it.loseAllEnergy() }
        }
        return step
    }

    private fun fillInNeighbours(octopus: List<List<Octopus>>) {
        octopus.forEachIndexed { i, line ->
            line.forEachIndexed { j, octo ->
                val neigh = getAllNeighbours(i, j, octopus)
                octo.neighbours = neigh
            }
        }
    }

    private fun getAllNeighbours(i: Int, j: Int, octopus: List<List<Octopus>>): List<Octopus> {
        return mutableListOf<Octopus?>(
            octopus.getOrNull(i - 1)?.getOrNull(j - 1),
            octopus.getOrNull(i)?.getOrNull(j - 1),
            octopus.getOrNull(i + 1)?.getOrNull(j - 1),
            octopus.getOrNull(i - 1)?.getOrNull(j),
            octopus.getOrNull(i + 1)?.getOrNull(j),
            octopus.getOrNull(i - 1)?.getOrNull(j + 1),
            octopus.getOrNull(i)?.getOrNull(j + 1),
            octopus.getOrNull(i + 1)?.getOrNull(j + 1),
        ).filterNotNull()
    }


    data class Octopus(var light: Int, var neighbours: List<Octopus> = emptyList(), var hasFlashed: Boolean = false) {

        fun addEnergyAndPossiblyFlashToAllNeighbours() {
            light++
            if (isFlashing() && !hasFlashed) {
                hasFlashed = true
                neighbours.forEach { it.addEnergyAndPossiblyFlashToAllNeighbours() }
            }
        }

        fun isFlashing(): Boolean {
            return light > 9
        }

        fun loseAllEnergy() {
            light = 0
            hasFlashed = false
        }
    }
}