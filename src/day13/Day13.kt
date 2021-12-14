package day13

import util.Days
import util.solveDay


fun main() {
    solveDay(Day13())
}

class Day13 : Days {
    override fun solvePart1(): String {
        val (paper, folds) = parseInput(readInputForDay())
        return foldPaper(paper.toMutableMap(), folds.subList(0, 1)).toString()

    }

    override fun solvePart2(): String {
        val (paper, folds) = parseInput(readInputForDay())
        val paperMut = paper.toMutableMap()
        foldPaper(paperMut, folds)
        printPaper(paperMut)

        return "BCZRCEAB"
    }

    private fun printPaper(paperMut: Map<Pair<Int, Int>, Char>) {
        val maxX = paperMut.maxOf { it.key.first }
        val maxY = paperMut.maxOf { it.key.second }
        for (y in 0..maxY) {
            for (x in 0..maxX) {
                val res = paperMut[Pair(x, y)]
                print(res)
            }
            println()
        }
    }


    private fun foldPaper(paper: MutableMap<Pair<Int, Int>, Char>, folds: List<String>): Int {
        for (fold in folds) {
            val lineCoord = fold.substringAfter("=").toInt()
            val foldFunc = getFoldFunc(fold)!!
            foldFunc(paper, lineCoord)
        }
        return paper.count { it.value == '#' }
    }

    private fun getFoldFunc(fold: String): ((MutableMap<Pair<Int, Int>, Char>, Int) -> Unit)? {
        return if (fold.contains("x")) {
            ::foldAlongXAxis
        } else if (fold.contains("y")) {
            ::foldAlongYAxis
        } else {
            null
        }
    }

    private fun foldAlongYAxis(paper: MutableMap<Pair<Int, Int>, Char>, lineCoord: Int) {
        paper.filter { it.key.second == lineCoord }.forEach { paper.remove(it.key) }
        val onSecondFold = paper.filter { it.key.second > lineCoord }
        for (f in onSecondFold) {
            paper.remove(f.key)
            val yCoord = f.key.second
            val diff = yCoord - lineCoord
            val yNew = lineCoord - diff
            if (paper[Pair(f.key.first, yNew)] != '#') {
                paper[Pair(f.key.first, yNew)] = f.value
            }
        }
    }

    private fun foldAlongXAxis(paper: MutableMap<Pair<Int, Int>, Char>, lineCoord: Int) {
        paper.filter { it.key.first == lineCoord }.forEach { paper.remove(it.key) }
        val onSecondFold = paper.filter { it.key.first > lineCoord }
        for (f in onSecondFold) {
            paper.remove(f.key)
            val xCoord = f.key.first
            val diff = xCoord - lineCoord
            val xNew = lineCoord - diff
            if (paper[Pair(xNew, f.key.second)] != '#') {
                paper[Pair(xNew, f.key.second)] = f.value
            }
        }
    }

    private fun parseInput(input: List<String>): Pair<Map<Pair<Int, Int>, Char>, List<String>> {
        val paper = mutableMapOf<Pair<Int, Int>, Char>()
        val emptyLine = input.indexOf("")
        for (co in input.subList(0, emptyLine)) {
            val split = co.split(",").map { it.toInt() }
            paper[Pair(split[0], split[1])] = '#'
        }
        val maxX = paper.maxOf { it.key.first }
        val maxY = paper.maxOf { it.key.second }

        for (y in 0..maxY) {
            for (x in 0..maxX) {
                if (!paper.contains(Pair(x, y))) {
                    paper[Pair(x, y)] = '.'
                }
            }
        }

        return Pair(paper, input.subList(emptyLine + 1, input.size))
    }


}