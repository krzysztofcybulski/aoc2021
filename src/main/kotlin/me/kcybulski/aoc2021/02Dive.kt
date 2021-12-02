package me.kcybulski.aoc2021

import me.kcybulski.aoc2021.utils.lines

fun main() {
    lines("02Dive")
        .fold(Ship(), Ship::parse)
        .run { println(result) }
}

private data class Ship(
    private val horizontal: Int = 0,
    private val depth: Int = 0
) {

    val result = depth * horizontal

    fun parse(input: String) =
        when (input.command) {
            "forward" -> copy(horizontal = horizontal + input.argument)
            "up" -> copy(depth = depth - input.argument)
            "down" -> copy(depth = depth + input.argument)
            else -> this
        }
}

private val String.command get() = split(" ")[0]

private val String.argument get() = split(" ")[1].toInt()
