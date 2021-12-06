package me.kcybulski.aoc2021

import me.kcybulski.aoc2021.utils.lines
import me.kcybulski.aoc2021.utils.print

fun main() {
    lines("06Lanternfish")
        .first()
        .split(",")
        .map(String::toInt)
        .map(::Lanternfish)
        .let { days(it.groupingBy(Lanternfish::age).eachCount()
            .mapValues { it.value.toLong() }
            .mapKeys { Lanternfish(it.key) }, 256) }
        .print()
}

private tailrec fun days(fishes: Map<Lanternfish, Long>, days: Int): Long {
    if (days == 0) {
        println(fishes)
        return fishes.values.sum()
    }
    val fishesToAdd = fishes.map { (k, v) -> k.next().mapValues { (a, b) -> b * v } }
        .reduce { acc, map -> acc.add(map) }
        .filterValues { it != 0L }
    return days(fishesToAdd, days - 1)
}

@JvmInline
value class Lanternfish(val age: Int) {

    fun next(): Map<Lanternfish, Int> = when (age) {
        0 -> mapOf(Lanternfish(6) to 1, Lanternfish(8) to 1)
        else -> mapOf(Lanternfish(age - 1) to 1)
    }

}

private fun Map<Lanternfish, Long>.add(other: Map<Lanternfish, Long>) =
    (0..9).associate { i ->
        val lanternfish = Lanternfish(i)
        lanternfish to ((this[lanternfish] ?: 0) + (other[lanternfish] ?: 0))
    }
