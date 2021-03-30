package com.example.androiddevchallenge


data class Cell(val isAlive: Boolean = false) {
    val neighbors: MutableList<Cell> = arrayListOf()

    fun evolve(livingNeighborsCount: Int = neighbors.count { it.isAlive }): Cell {
        return copy(
            isAlive = when (livingNeighborsCount) {
                0, 1 -> false
                2 -> isAlive
                3 -> true
                else -> false
            }
        )
    }

    fun toggle(): Cell {
        return copy(isAlive = !isAlive)
    }
}
