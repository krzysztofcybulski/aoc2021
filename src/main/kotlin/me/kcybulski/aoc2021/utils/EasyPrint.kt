package me.kcybulski.aoc2021.utils

fun <T> T.print(get: (T) -> Any = { x -> x as Any }) = println(get(this))
