package day4

import util.Days
import util.solveDay

fun main() {
    solveDay(Day4())
}

class Day4 : Days {
    override fun solvePart1(): String {
        return solve(::playBingoToWin)
    }

    override fun solvePart2(): String {
        return solve(::playBingoToLose)
    }

    private fun solve(playMethod: (List<Int>, List<BingoBoard>) -> Int): String {
        val input = readInputForDay()
        val bingoNumbers = input[0].split(",").map { Integer.parseInt(it) }
        val bingoBoards = createBingoBoards(input)

        val score = playMethod(bingoNumbers, bingoBoards)
        return score.toString()
    }

    private fun playBingoToWin(bingoNumbers: List<Int>, bingoBoards: List<BingoBoard>): Int {
        for (bingo in bingoNumbers) {
            for (board in bingoBoards) {
                board.playBingo(bingo)
                if (board.bingo()) {
                    return board.calculateScore() * bingo
                }
            }
        }
        return -1
    }

    private fun playBingoToLose(bingoNumbers: List<Int>, bingoBoards: List<BingoBoard>): Int {
        for (bingo in bingoNumbers) {
            val boards = bingoBoards.filter { !it.bingoStatus() }
            for (board in boards) {
                board.playBingo(bingo)
                if (board.bingo() && boards.size == 1) {
                    return board.calculateScore() * bingo
                }
            }
        }
        return -1
    }

    private fun createBingoBoards(input: List<String>): List<BingoBoard> {
        val bingoBoards = mutableListOf<BingoBoard>()
        var boardInput = mutableListOf<String>()
        for (l in input.drop(2)) {
            if (l == "") {
                bingoBoards.add(BingoBoard(boardInput))
                boardInput = mutableListOf()
                continue
            }
            boardInput.add(l)
        }
        //create last board
        bingoBoards.add(BingoBoard(boardInput))
        return bingoBoards
    }

}

class BingoBoard() {
    data class Field(val value: Int, var taken: Boolean)

    private var inputBoard = arrayOf<Array<Field>>()
    private var bingo = false

    constructor(boardInput: List<String>) : this() {
        for (inputLine in boardInput) {
            var line = arrayOf<Field>()
            for (field in inputLine.split(" ")) {
                if (field == "") {
                    continue
                }
                line += Field(field.toInt(), false)
            }
            inputBoard += line
        }
    }

    fun playBingo(bingoValue: Int) {
        this.inputBoard.flatten().filter { it.value == bingoValue }.forEach { it.taken = true }
    }

    fun bingoStatus(): Boolean {
        return this.bingo
    }

    fun bingo(): Boolean {
        this.inputBoard.forEach { horizontal ->
            if (horizontal.count { it.taken } == horizontal.size) {
                bingo = true
                return true
            }
        }
        for (i in this.inputBoard.indices) {
            if (this.inputBoard.map { it[i] }.count { it.taken } == 5) {
                bingo = true
                return true
            }
        }
        return false
    }

    fun calculateScore(): Int {
        return this.inputBoard.flatten().filter { !it.taken }.sumOf { it.value }
    }
}