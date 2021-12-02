package me.kcybulski.aoc2021.utils

class Data

fun lines(name: String, splitter: String = "\n") = Data::class.java
    .getResource("/${name}.txt")
    .readText()
    .split(splitter)
    .filter { it.isNotBlank() }
