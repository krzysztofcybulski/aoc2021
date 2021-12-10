package me.kcybulski.aoc2021

import me.kcybulski.aoc2021.utils.lines
import me.kcybulski.aoc2021.utils.print

class SmokeBasin() {
}

fun main() {
    val lines = lines("09SmokeBasin")
        .map { line -> line.map { it.digitToInt() }.toMutableList() }

    val result = lines.indices.flatMap { y ->
        lines[y].indices
            .filter { x -> lines.getOrNull(y - 1)?.getOrNull(x).isNineOrNull() && lines.getOrNull(y)?.getOrNull(x - 1).isNineOrNull()}
            .mapNotNull { x ->
                if(lines[y][x] != -1) {
                    basin(x, y, lines)
                } else null
            }
    }
        .filter { it.isNotEmpty() }
        .toSet()


    result
        .map { it.size }
        .sortedByDescending { it }
        .take(3)
        .reduce { a, b -> a * b }
        .print()

}

private fun Int?.isNineOrNull() = this == 9 || this == null

private fun basin(x: Int, y: Int, map: List<MutableList<Int>>, basin: Set<Pair<Int, Int>> = emptySet()): Set<Pair<Int, Int>> {
    if (y >= map.size || y < 0 || x >= map[y].size || x < 0 || map[y][x] == 9 || map[y][x] == -1) {
        return basin
    }
    map[y][x] = -1
    return basin(x + 1, y, map, basin + (x to y)) +
            basin(x, y + 1, map, basin + (x to y)) +
            basin(x - 1, y, map, basin + (x to y)) +
            basin(x, y - 1, map, basin + (x to y))
}
