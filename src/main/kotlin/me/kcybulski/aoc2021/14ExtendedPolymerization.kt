package me.kcybulski.aoc2021

import me.kcybulski.aoc2021.utils.lines
import me.kcybulski.aoc2021.utils.print

fun main() {
    val lines = lines("14ExtendedPolymerization")
    val template: String = lines.first()
    val rules: Map<String, String> = lines.drop(1)
        .map { it.split(" -> ") }
        .associate { it[0] to it[1] }


    val d20 = rules
        .mapValues { (k, _) -> k.insert(rules, 21) }
        .mapValues { (_, v) -> v.countLetters() }

    val elements = template.insert(rules, 17)
        .windowed(2, 1)
        .mapNotNull { d20[it]?.subtract(it.last()) }
        .reduce(Map<Char, Long>::add)
        .add(mapOf(template.last() to 1L))
        .map(Map.Entry<Char, Long>::value)

    ((elements.maxOrNull() ?: 0) - (elements.minOrNull() ?: 0)).print()
}

private fun Map<Char, Long>.add(second: Map<Char, Long>) =
    mapValues { (key, value) -> value + (second[key] ?: 0L) } +
            second.mapValues { (key, value) -> value + (this[key] ?: 0L) }

private fun Map<Char, Long>.subtract(l: Char) =
    mapValues { (key, value) -> if (key == l) value - 1 else value }

private fun String.insert(rules: Map<String, String>, depth: Int) =
    (0..depth).fold(this) { e, _ -> e.insert(rules) }

private fun String.insert(rules: Map<String, String>): String =
    windowed(2, 1)
        .joinToString("") { it[0] + (rules[it] ?: "") } + last()

private fun String.countLetters() =
    groupingBy { it }
        .eachCount()
        .mapValues { it.value.toLong() }
