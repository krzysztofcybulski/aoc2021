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

    val (winningBoard, wonWith) = getWinning(winning, boards)
    val unmarked = winningBoard.numbers.flatten().filter { it !in wonWith }
    winning.print()
    unmarked.print()
    unmarked.sum().print()
    wonWith.print()
    (unmarked.sum() * wonWith.last()).print()
}

private fun getWinning(winning: List<Int>, boards: List<Board>, current: Int = 1): Pair<Board, List<Int>> {
    val winningBoard = boards.find { it.isWinning(winning.take(current)) }
    println(winningBoard)
    return if(winningBoard == null)
        getWinning(winning, boards, current + 1)
    else
        winningBoard to winning.take(current)
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

    private fun rowWinning(random: List<Int>) = numbers
        .any { row -> row.all { it in random } }

    private fun columnWinning(random: List<Int>) = (0 until numbers[0].size)
        .map { c -> (numbers.indices).map { r -> numbers[r][c] } }
        .any { c -> c.all { it in random } }


}
