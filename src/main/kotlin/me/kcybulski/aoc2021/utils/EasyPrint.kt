package me.kcybulski.aoc2021.utils

inline fun <reified T> T.print(): T = this.also(::println)

inline fun <reified T, reified O> T.print(get: (T) -> O): O = get(this).also(::println)

inline fun <reified T, reified O> T.alsoPrint(get: (T) -> O = { x -> x as O }): T = this.also { println(get(it)) }
