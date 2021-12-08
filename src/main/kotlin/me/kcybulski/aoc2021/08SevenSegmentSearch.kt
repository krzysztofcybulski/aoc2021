package me.kcybulski.aoc2021
import me.kcybulski.aoc2021.utils.lines
import me.kcybulski.aoc2021.utils.print

class SevenSegmentSearch() {
}

fun main() {
    val lines = lines("08SevenSegmentSearch")
        .map { it.split("|")[1] }
        .map { it.split(" ") }
        .flatMap { it.map { it.length } }
        .count { it in listOf(2, 3, 4, 7) }
        .print()
}
