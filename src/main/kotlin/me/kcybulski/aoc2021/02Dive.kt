package me.kcybulski.aoc2021

import me.kcybulski.aoc2021.utils.lines
import me.kcybulski.aoc2021.utils.print

fun main() {
    lines("02Dive")
        .fold(Ship(), Ship::parse)
        .print(Ship::result)
}

private data class Ship(
    private val horizontal: Int = 0,
    private val depth: Int = 0,
    private val aim: Int = 0
) {

    val result = depth * horizontal

    fun parse(input: String) =
        when (input.command) {
            "forward" -> copy(horizontal = horizontal + input.argument, depth = depth + aim * input.argument)
            "up" -> copy(aim = aim - input.argument)
            "down" -> copy(aim = aim + input.argument)
            else -> this
        }
}

private val String.command get() = split(" ")[0]

private val String.argument get() = split(" ")[1].toInt()
