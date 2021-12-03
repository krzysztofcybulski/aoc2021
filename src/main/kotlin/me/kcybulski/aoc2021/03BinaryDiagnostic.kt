package me.kcybulski.aoc2021

import me.kcybulski.aoc2021.utils.lines
import me.kcybulski.aoc2021.utils.print

//@formatter:off
fun main() {
    val lines = lines("03BinaryDiagnostic")

    (
            lines.mostCommon().fromBinary()
                    *
            lines.leastCommon().fromBinary()
    ).print()

    (
            find(lines, List<String>::mostCommon)[0].fromBinary()
                    *
            find(lines, List<String>::leastCommon)[0].fromBinary()
    ).print()
}
//@formatter:on

private fun find(raport: List<String>, selector: (List<String>) -> String, index: Int = 0): List<String> =
    if (raport.size == 1) raport else {
        val mostCommon = selector(raport)
        find(raport.filter { it[index] == mostCommon[index] }, selector, index + 1)
    }

private fun List<String>.mostCommon() = common { entries ->
    entries
        .sortedByDescending { it.key }
        .maxByOrNull { it.value }
        ?.key
}

private fun List<String>.leastCommon() = common { entries ->
    entries
        .sortedBy { it.key }
        .minByOrNull { it.value }
        ?.key
}

private fun List<String>.common(handler: (List<Map.Entry<Char, Int>>) -> Char?) = (0 until first().length)
    .map { index ->
        map { it[index] }
            .groupingBy { it }
            .eachCount()
            .entries
            .sortedBy() { it.key }
            .let(handler)
    }
    .joinToString("")

private fun String.fromBinary() = toInt(2)
