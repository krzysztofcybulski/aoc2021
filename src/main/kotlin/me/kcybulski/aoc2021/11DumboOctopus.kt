package me.kcybulski.aoc2021

import me.kcybulski.aoc2021.utils.lines
import me.kcybulski.aoc2021.utils.print

fun main() {
    val cavern = lines("11DumboOctopus")
        .map { row -> row.map { DumboOctopus(it.digitToInt()) } }
        .let { Cavern(it) }
    (0..99).fold(cavern) { c, _ -> c.increase().flashAll() }
        .flashed
        .print()
}

data class Step(val map: Cavern, val flashes: Int)

data class DumboOctopus(val energy: Int) {

    fun increaseEnergy() = copy(energy + 1)

    fun zero() = copy(0)

    fun reset() = copy(Int.MIN_VALUE)

}

data class Cavern(val map: List<List<DumboOctopus>>, val flashed: Int = 0) {

    fun increase(): Cavern = copy(
        map = map.map { row -> row.map(DumboOctopus::increaseEnergy) }
    )

    fun flashAll(): Cavern {
        val positions = getFlashesPositions()
        val newCavern = onPositions(positions) { it.reset() }
        val flashes = positions
            .flatMap(Pair<Int, Int>::getAdjacent)
        return if (flashes.isEmpty())
            newCavern.clean()
        else
            newCavern.flash(flashes).flashAll()
    }

    private fun clean(): Cavern {
        val positions = map
            .indices
            .map { y ->
                map[y].indices.filter { x -> map[y][x].energy < 0 }.map { x -> x to y }
            }
            .filter { it.isNotEmpty() }
            .flatten()
        return onPositions(positions) { it.zero() }
            .copy(flashed = flashed + positions.size)
    }

    private fun flash(positions: List<Pair<Int, Int>>): Cavern =
        onPositions(positions) { it.increaseEnergy() }

    private fun onPositions(positions: List<Pair<Int, Int>>, handle: (DumboOctopus) -> DumboOctopus) = copy(
        map = positions.fold(map) { old, p ->
            old
                .mapIndexed { y, row ->
                    row
                        .mapIndexed { x, octopus ->
                            if (x to y == p) handle(octopus) else octopus
                        }
                }
        },
    )

    private fun getFlashesPositions() = map
        .indices
        .map { y ->
            map[y].indices.filter { x -> map[y][x].energy > 9 }.map { x -> x to y }
        }
        .filter { it.isNotEmpty() }
        .flatten()

    fun print(): Cavern {
        map.forEach {
            println(it.joinToString("") { it.energy.toString() })
        }
        return this
    }

}

private fun Pair<Int, Int>.getAdjacent() = (first - 1..first + 1)
    .flatMap { x -> (second - 1..second + 1).map { y -> x to y } }
    .filterNot { it == this }
