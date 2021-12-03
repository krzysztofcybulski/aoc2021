package me.kcybulski.aoc2021

import me.kcybulski.aoc2021.utils.lines
import me.kcybulski.aoc2021.utils.print

fun main() {
    val lines = lines("03BinaryDiagnostic")

    val mostCommon = (0 until lines[0].length)
        .map { index ->
            lines
                .map { it[index] }
                .groupingBy { it }
                .eachCount()
                .maxByOrNull { it.value }
                ?.key
        }
        .joinToString("")

    (mostCommon.fromBinary() * mostCommon.inverse().fromBinary()).print()
}

private fun String.inverse() = map { if (it == '0') '1' else '0' }.joinToString("")

private fun String.fromBinary() = toInt(2)
