package me.kcybulski.aoc2021

import me.kcybulski.aoc2021.utils.lines
import me.kcybulski.aoc2021.utils.print
import java.util.Stack

class SyntaxScoring() {
}

fun main() {
    val scores = lines("10SyntaxScoring")
        .mapNotNull(::complete)
        .map(String::completeScore)

    // 5
    scores.size.print()
    scores.sorted().print()

    scores
        .sorted()[scores.size / 2]
        .print()
}

private fun complete(line: String): String? {
    val stack = Stack<Int>()
    for (char in line.chars()) {
        if (char in starting)
            stack.push(char)
        else if (startingToClosing[stack.pop().toChar()] != char.toChar())
            return null
    }
    return stack.map { startingToClosing[it.toChar()] }
        .joinToString("")
        .reversed()
}

private fun String.completeScore(): Long = fold(0) { acc, c -> acc * 5 + c.points() }

private fun Char.points() = when (this) {
    ')' -> 1L
    ']' -> 2L
    '}' -> 3L
    '>' -> 4L
    else -> 0L
}

private val startingToClosing = mapOf(
    '(' to ')',
    '[' to ']',
    '<' to '>',
    '{' to '}'
)

private val starting = setOf('(', '[', '<', '{').map(Char::code)
