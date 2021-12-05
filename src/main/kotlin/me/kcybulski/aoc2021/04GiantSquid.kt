package me.kcybulski.aoc2021

import me.kcybulski.aoc2021.utils.lines
import me.kcybulski.aoc2021.utils.print

class GiantSquid() {
}

fun main() {
    val lines = lines("04GiantSquid")

    val winning = lines.first().split(",").map { it.toInt() }

    val boards = lines.drop(1)
        .map { loadBoard(it.split("\n")) }

    val (winningBoard, wonWith) = getWinning(winning, boards)!!
    winningBoard.result(wonWith).print()

    val (winningLastBoard, wonLastWith) = getWinningLast(winning, boards)
    winningLastBoard.result(wonLastWith).print()
}

private fun getWinning(winning: List<Int>, boards: List<Board>, current: Int = 1): Pair<Board, List<Int>>? {
    val winningBoard = boards.find { it.isWinning(winning.take(current)) }
    return if (winningBoard == null) {
        return if(current >= winning.size)
            null
        else
            getWinning(winning, boards, current + 1)
    }
    else
        winningBoard to winning.take(current)
}

private fun getWinningLast(
    winning: List<Int>,
    boards: List<Board>,
    lastWon: Pair<Board, List<Int>> = getWinning(winning, boards)!!
): Pair<Board, List<Int>> {
    val winningBoard = getWinning(winning, boards)
    return if (winningBoard == null)
        lastWon
    else
        getWinningLast(winning, boards - winningBoard.first, winningBoard)
}

private fun loadBoard(raw: List<String>): Board {
    val board = Board(raw.map { r ->
        r.split("\\s+".toRegex())
            .filterNot { it.isBlank() }
            .map { it.toInt() }
    }.filterNot { it.size == 0 })
    return board
}

data class Board(
    val numbers: List<List<Int>>
) {

    fun isWinning(random: List<Int>) = rowWinning(random) || columnWinning(random)

    fun result(wonWith: List<Int>): Int {
        val unmarked = numbers.flatten().filter { it !in wonWith }
        return unmarked.sum() * wonWith.last()
    }

    private fun rowWinning(random: List<Int>) = numbers
        .any { row -> row.all { it in random } }

    private fun columnWinning(random: List<Int>) = (0 until numbers[0].size)
        .map { c -> (numbers.indices).map { r -> numbers[r][c] } }
        .any { c -> c.all { it in random } }


}
