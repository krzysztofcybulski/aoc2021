package me.kcybulski.aoc2021

import me.kcybulski.aoc2021.utils.lines
import me.kcybulski.aoc2021.utils.print

private val SEGMENTS = mapOf(
    0 to listOf(0, 1, 2, 4, 5, 6),
    1 to listOf(2, 5),
    2 to listOf(0, 2, 3, 4, 6),
    3 to listOf(0, 2, 3, 5, 6),
    4 to listOf(1, 2, 3, 5),
    5 to listOf(0, 1, 3, 5, 6),
    6 to listOf(0, 1, 3, 4, 5, 6),
    7 to listOf(0, 2, 5),
    8 to listOf(0, 1, 2, 3, 4, 5, 6),
    9 to listOf(0, 1, 2, 3, 5, 6)
)

fun main() {
    lines("08SevenSegmentSearch")
        .map { it.split(" | ") }
        .map { Line(it[0].split(" ").map { Digit(it) }, it[1].split(" ").map { Digit(it) }) }
        .sumOf(::solve)
        .print()
}

private fun solve(line: Line): Int {
    val inputs = line.inputs.map { it.segments }

    val counts = inputs.flatMap { it.toList() }
        .groupingBy { it }
        .eachCount()

    val a = uncommon(inputs, 2, 3).first()
    val b = counts.segmentExactly(6)
    val e = counts.segmentExactly(4)
    val f = counts.segmentExactly(9)
    val c = inputs.numberWithNSegmentsWithout(2, f)
    val d = inputs.numberWithNSegmentsWithout(4, b, c, f)
    val g = lastOne(a, b, c, d, e, f)

    val results = listOf(a, b, c, d, e, f, g)

    fun findDigit(digit: String): Int = digit
        .map { results.indexOf(it) }.sortedBy { it }
        .let { SEGMENTS.filter { s -> s.value == it }.keys.first() }

    return line.outputs
        .map { digit -> findDigit(digit.segments) }
        .joinToString("")
        .toInt()
}

private fun lastOne(vararg chars: Char) = ("abcdefg".toSet() - chars.toSet()).first()

private fun Map<Char, Int>.segmentExactly(times: Int) = filterValues { it == times }.keys.first()

private fun List<String>.numberWithNSegmentsWithout(n: Int, vararg without: Char) =
    (find { it.length == 4 }!!.toSet() - without.toSet()).first()

private fun uncommon(inputs: List<String>, aSize: Int, bSize: Int): List<Char> {
    val a = inputs.find { it.length == aSize }
    val b = inputs.find { it.length == bSize }
    return uncommon(a ?: "", b ?: "")
}

private fun uncommon(a: String, b: String): List<Char> =
    (b.toSet() - a.toSet()).toList() + (a.toSet() - b.toSet()).toList()

class Digit(segments: String) {
    val segments = segments.toList().sorted().joinToString("")
}

class Line(
    val inputs: List<Digit>,
    val outputs: List<Digit>
)
