package me.kcybulski.aoc2021
import me.kcybulski.aoc2021.utils.lines
import me.kcybulski.aoc2021.utils.print
import kotlin.math.abs

fun main() {
    val lines: List<Int> = lines("07TheTreacheryofWhales")
        .first()
        .split(",")
        .map { it.toInt() }

    ((lines.minOrNull() ?: 0)..(lines.maxOrNull() ?: 0))
        .map { i ->
            lines.map { (1..abs(i - it)).sum() }.sum()
        }
        .minOrNull()
        .print()

}
