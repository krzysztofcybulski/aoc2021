package me.kcybulski.aoc2021
import me.kcybulski.aoc2021.utils.lines
import me.kcybulski.aoc2021.utils.print

class ExtendedPolymerization() {
}

fun main() {
    val lines = lines("14ExtendedPolymerization")
    val template: String = lines.first()
    val rules: Map<String, String> = lines.drop(1)
        .map { it.split(" -> ") }
        .associate { it[0] to it[1] }

    val elements = (0..9).fold(template) { temp, _ -> temp.insert(rules) }
        .groupingBy { it }
        .eachCount()
        .map { it.value }

    ((elements.maxOrNull() ?: 0) - (elements.minOrNull() ?: 0)).print()
}

private fun String.insert(rules: Map<String, String>) =
    windowed(2, 1)
        .joinToString("") { it[0] + (rules[it] ?: "") } + last()
