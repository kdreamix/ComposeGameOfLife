package com.example.androiddevchallenge.data


data class Pattern(
    val data: List<Int>,
    val boardSize: Int,
)

val blinker = Pattern(
    data = listOf(
        0, 0, 0, 0, 0,
        0, 0, 1, 0, 0,
        0, 0, 1, 0, 0,
        0, 0, 1, 0, 0,
        0, 0, 0, 0, 0,
    ), boardSize = 5
)

val toad = Pattern(
    data = listOf(
        0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0,
        0, 0, 1, 1, 1, 0,
        0, 1, 1, 1, 0, 0,
        0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0,
    ), boardSize = 6
)
