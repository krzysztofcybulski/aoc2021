package me.kcybulski.aoc2021

import me.kcybulski.aoc2021.utils.lines
import me.kcybulski.aoc2021.utils.print

class Paper(
    val positions: Set<Pair<Int, Int>>
) {

    fun foldHorizontal(line: Int) = Paper(
        positions.filter { (x, _) -> x < line }.toSet() +
                positions.filter { (x, _) -> x > line }
                    .map { (x, y) -> line - (x - line) to y }
                    .toSet()
    )

    fun foldVertical(line: Int) = Paper(
        positions.filter { (_, y) -> y < line }.toSet() +
                positions.filter { (_, y) -> y > line }
                    .map { (x, y) -> x to line - (y - line) }
                    .toSet()
    )

    fun print() {
        val width = positions.maxOf { it.first }
        val height = positions.maxOf { it.second }
        (0..height).joinToString("\n") { row ->
            (0..width).map { column ->
                if (column to row in positions) '#' else ' '
            }.joinToString("")
        }
            .print()
    }
}

fun main() {
    val lines: List<String> = lines("13TransparentOrigami")

    val paper = lines.takeWhile { !it.startsWith("fold") }
        .map { it.split(",") }
        .map { it[0].toInt() to it[1].toInt() }
        .toSet()
        .let(::Paper)

    val folds = lines
        .filter { it.startsWith("fold") }
        .map { it.dropWhile { it !in listOf('x', 'y') } }

    folds.take(1).fold(paper).positions.size.print()
    folds.fold(paper).print()
}

private fun List<String>.fold(paper: Paper) = fold(paper) { p, fold ->
    val number = fold.drop(2).toInt()
    when (fold.first()) {
        'x' -> p.foldHorizontal(number)
        else -> p.foldVertical(number)
    }
}
