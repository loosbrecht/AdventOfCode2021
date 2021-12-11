package day10

import util.Days
import util.solveDay
import java.util.*

fun main() {
     solveDay(Day10())
}

class Day10 : Days {


    override fun solvePart1(): String {
        val scoreFunc = fun(s: String): Long {
            return when (s) {
                ")" -> 3L
                "]" -> 57L
                "}" -> 1197L
                ">" -> 25137L
                else -> 0
            }
        }
        val lines = readInputForDay()
        return lines.mapNotNull { walkOverChunkLine(it) }.sumOf { scoreFunc(it) }.toString()
    }

    override fun solvePart2(): String {

        val lines = readInputForDay()
        val sortedScores =
            lines.mapNotNull { walkOverLineAndCorrectEnd(it) }.map { calculateScoreOfCompletedLine(it) }.sorted()
        return sortedScores[(sortedScores.size / 2)].toString()
    }

    private val open = listOf("{", "<", "[", "(")
    private val close = listOf("}", ">", "]", ")")


    private fun validClose(close: String, open: String): Boolean {
        return when (close) {
            ")" -> open == "("
            "]" -> open == "["
            "}" -> open == "{"
            ">" -> open == "<"
            else -> false
        }
    }

    private fun getCloseForOpen(open: String): String {
        return when (open) {
            "(" -> ")"
            "[" -> "]"
            "{" -> "}"
            "<" -> ">"
            else -> ""
        }
    }

    private fun walkOverChunkLine(line: String): String? {
        val stack = Stack<String>()
        val it = line.splitToSequence("").filterNot { it == "" }.iterator()
        while (it.hasNext()) {
            val s = it.next()
            if (s in open) {
                stack.push(s)
            } else if (s in close) {
                val open = stack.pop()
                if (!validClose(s, open)) {
                    return s
                }
            }
        }
        return null
    }

    // return null when line is complete as is or when the line was corrupted
    private fun walkOverLineAndCorrectEnd(line: String): List<String>? {
        val completionString = mutableListOf<String>()
        val stack = Stack<String>()
        val it = line.splitToSequence("").filterNot { it == "" }.iterator()
        while (it.hasNext()) {
            val s = it.next()
            if (s in open) {
                stack.push(s)
            } else if (s in close) {
                val open = stack.pop()
                if (!validClose(s, open)) {
                    return null
                }
            }
        }
        if (stack.empty()) {
            return null
        }

        while (!stack.isEmpty()) {
            val open = stack.pop()
            completionString += getCloseForOpen(open)
        }
        return completionString
    }

    fun calculateScoreOfCompletedLine(lines: List<String>): Long {
        val scoreFunc = fun(s: String): Long {
            return when (s) {
                ")" -> 1L
                "]" -> 2L
                "}" -> 3L
                ">" -> 4L
                else -> 0
            }
        }
        var totalScore = 0L
        for (l in lines) {
            totalScore *= 5
            totalScore += scoreFunc(l)

        }
        return totalScore
    }
}