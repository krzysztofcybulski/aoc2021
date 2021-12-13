package me.kcybulski.aoc2021

import me.kcybulski.aoc2021.utils.lines
import me.kcybulski.aoc2021.utils.print

sealed class Cave(
    open val name: String,
    open val connected: MutableSet<String>
) {

    fun print() = "${name}: ${connected.map { it }}"

}

data class BigCave(
    override val name: String,
    override val connected: MutableSet<String> = mutableSetOf()
) : Cave(name, connected)

data class SmallCave(
    override val name: String,
    override val connected: MutableSet<String> = mutableSetOf()
) : Cave(name, connected)

fun main() {
    val caves = lines("12PassagePathing")
        .flatMap { it.split("-") }
        .toSet()
        .map { it.toCave() }
        .toSet()

    lines("12PassagePathing")
        .map {
            val connected = it.split("-").map { n -> caves.find { it.name == n }!! }
            connected[0].connected += connected[1].name
            connected[1].connected += connected[0].name
        }

    caves.forEach { it.connected -= "start" }

    val paths = paths(caves)
    paths.map { it.map(Cave::name).joinToString(",").print() }
    paths.size.print()
}

private fun paths(caves: Set<Cave>): Set<List<Cave>> {
    return allPaths(caves, caves.find { it.name.lowercase() == "start" }!!)
}

private fun allPaths(
    caves: Set<Cave>,
    current: Cave,
    path: List<Cave> = listOf(current),
    visited: List<Cave> = listOf(current),
    allPaths: Set<List<Cave>> = emptySet()
): Set<List<Cave>> {
    if(path.isEmpty()) {
        return allPaths
    }
    if (current.name.lowercase() == "end") {
        return allPaths(caves, current, emptyList(), emptyList(), allPaths.plusElement(path))
    }
    val wasSmallVisitedTwice = visited
        .filterIsInstance<SmallCave>()
        .groupingBy { it.name }
        .eachCount()
        .containsValue(2)

    return current.connected
        .filter { it.uppercase() == it || !wasSmallVisitedTwice || it !in visited.map(Cave::name) }
        .flatMap {
            val cave = caves.find { c -> c.name == it }!!
            allPaths(
                caves,
                cave,
                path + cave,
                visited + cave,
                allPaths
            )
        }
        .toSet()
}

private fun String.toCave() = if (lowercase() == this)
    SmallCave(this)
else
    BigCave(this)
