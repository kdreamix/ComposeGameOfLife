package com.example.androiddevchallenge.data


data class Pattern(
    val title: String,
    val data: List<Int>,
    val boardSize: Int,
)

val blinker = Pattern(
    title= "blinker",
    data = listOf(
        0, 0, 0, 0, 0,
        0, 0, 1, 0, 0,
        0, 0, 1, 0, 0,
        0, 0, 1, 0, 0,
        0, 0, 0, 0, 0,
    ), boardSize = 5
)

val toad = Pattern(
    title= "toad",
    data = listOf(
        0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0,
        0, 0, 1, 1, 1, 0,
        0, 1, 1, 1, 0, 0,
        0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0,
    ), boardSize = 6
)
