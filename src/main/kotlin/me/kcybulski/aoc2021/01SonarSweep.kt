package me.kcybulski.aoc2021

import me.kcybulski.aoc2021.utils.lines

fun main() {
    val input = lines("01SonarSweep")
        .map(String::toInt)

    println("Part1: ${input.howManyIncreases()}")

    input
        .windowed(3, 1)
        .map(List<Int>::sum)
        .run { println("Part2: ${howManyIncreases()}") }
}

private fun List<Int>.howManyIncreases() = windowed(2, 1).count { it[1] > it[0] }
