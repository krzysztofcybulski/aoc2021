package me.kcybulski.aoc2021
import me.kcybulski.aoc2021.utils.lines
import me.kcybulski.aoc2021.utils.print

class HydrothermalVenture() {
}

fun main() {
    val wires = lines("05HydrothermalVenture")
        .map { it.split(" -> ") }
        .mapNotNull { Wire.of(it[0].toPosition(), it[1].toPosition()) }

    val intersections = wires
        .flatMap { it.positions }
        .groupingBy { it }
        .eachCount()
        .filterValues { it > 1 }

    intersections.size.print()
}

private data class Wire(
    val positions: Set<Position>
) {
    companion object {

        fun of(start: Position, end: Position): Wire? {
            if(start.x == end.x && start.y < end.y)
                return Wire((start.y..end.y).map { Position(start.x, it) }.toSet())
            else if(start.x == end.x && start.y > end.y)
                return Wire((end.y..start.y).map { Position(start.x, it) }.toSet())
            else if(start.y == end.y && start.x < end.x)
                return Wire((start.x..end.x).map { Position(it, start.y) }.toSet())
            else if(start.y == end.y && start.x > end.x)
                return Wire((end.x..start.x).map { Position(it, start.y) }.toSet())
            else
                return null
        }

    }

}

data class Position(val x: Int, val y: Int)

private fun String.toPosition() = split(",").map(String::toInt).let { Position(it[0], it[1]) }
