package me.kcybulski.aoc2021

import me.kcybulski.aoc2021.utils.lines
import me.kcybulski.aoc2021.utils.print
import java.util.Stack

class SyntaxScoring() {
}

fun main() {
    lines("10SyntaxScoring")
        .mapNotNull { firstIllegal(it) }
        .map { it.points() }
        .sum()
        .print()
}

private fun firstIllegal(line: String): Char? {
    val stack = Stack<Int>()
    for (char in line.chars()) {
        if (char in starting)
            stack.push(char)
        else if (!stack.pop().isOpeningTo(char))
            return char.toChar()
    }
    return null
}

private fun Int.isOpeningTo(closing: Int) = this == closing - 1 || this == closing - 2

private fun Char.points() = when (this) {
    ')' -> 3
    ']' -> 57
    '}' -> 1197
    '>' -> 25137
    else -> 0
}

private val starting = setOf('(', '[', '<', '{').map(Char::code)
