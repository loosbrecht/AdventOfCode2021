package day5

import util.Days
import util.solveDay


fun main() {
    solveDay(Day5())
}

class Day5 : Days {
    override fun solvePart1(): String {
        val streams = parseInput(readInputForDay())
        val field = buildFieldHorizontalOrVertical(streams)
        return field.groupBy { it }.count { it.value.size >= 2 }.toString()
    }

    override fun solvePart2(): String {
        val streams = parseInput(readInputForDay())
        val field = buildFieldAllDirections(streams)
        return field.groupBy { it }.count { it.value.size >= 2 }.toString()
    }

    private fun buildFieldAllDirections(streams: List<Pair<Point, Point>>): List<Point> {
        val streamPoints = mutableListOf<Point>()
        for (s in streams) {
            var start = s.first
            var end = s.second
            if (start.x > end.x || start.y > end.y) {
                val tmp = start
                start = end
                end = tmp
            }
            if (start.x == end.x) {
                for (y in start.y..end.y) {
                    streamPoints.add(Point(start.x, y))
                }
            } else if (start.y == end.y) {
                for (x in start.x..end.x) {
                    streamPoints.add(Point(x, start.y))
                }
            } else {
                val indexesX = getAllValues(start.x, end.x)
                val indexesY = getAllValues(start.y, end.y)
                for (i in indexesX.indices) {
                    streamPoints.add(Point(indexesX[i], indexesY[i]))
                }
            }
        }
        return streamPoints
    }

    private fun getAllValues(start: Int, end: Int): List<Int> {
        if (start < end) {
            return (start..end).toList()
        } else {
            return (end..start).toList().reversed()
        }
    }

    private fun buildFieldHorizontalOrVertical(streams: List<Pair<Point, Point>>): List<Point> {
        val filtered = streams.filter { it.first.x == it.second.x || it.first.y == it.second.y }
        val streamPoints = mutableListOf<Point>()
        for (s in filtered) {
            var start = s.first
            var end = s.second
            if (start.x > end.x || start.y > end.y) {
                val tmp = start
                start = end
                end = tmp
            }
            if (start.x == end.x) {
                for (y in start.y..end.y) {
                    streamPoints.add(Point(start.x, y))
                }
            } else if (start.y == end.y) {
                for (x in start.x..end.x) {
                    streamPoints.add(Point(x, start.y))
                }
            }
        }
        return streamPoints
    }


    data class Point(var x: Int, var y: Int)

    private fun parseInput(streams: List<String>): List<Pair<Point, Point>> {
        return streams.map { l ->
            val points = l.split(" -> ")
            val first = points[0].split(",").map { it.toInt() }
            val second = points[1].split(",").map { it.toInt() }
            Pair(Point(first[0], first[1]), Point(second[0], second[1]))
        }
    }
}

