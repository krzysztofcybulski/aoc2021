package me.kcybulski.aoc2021

import me.kcybulski.aoc2021.utils.lines
import me.kcybulski.aoc2021.utils.print

fun main() {
    val input = lines("01SonarSweep")
        .map(String::toInt)

    input.howManyIncreases().print()

    input
        .windowed(3, 1)
        .map(List<Int>::sum)
        .print(List<Int>::howManyIncreases)
}

private fun List<Int>.howManyIncreases() = windowed(2, 1).count { it[1] > it[0] }
