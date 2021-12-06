package me.kcybulski.aoc2021
import me.kcybulski.aoc2021.utils.lines
import me.kcybulski.aoc2021.utils.print

fun main() {
    lines("06Lanternfish")
        .first()
        .split(",")
        .map(String::toInt)
        .map(::Lanternfish)
        .let { days(it, 80) }
        .print()
}

private fun days(fishes: List<Lanternfish>, days: Int): Int {
    if(days == 0) {
        return fishes.size
    }
    return days(fishes.flatMap { it.next() }, days - 1)
}

data class Lanternfish(val age: Int) {

    fun next(): List<Lanternfish> = when(age) {
        0 -> listOf(Lanternfish(6), Lanternfish(8))
        else -> listOf(Lanternfish(age - 1))
    }

}
