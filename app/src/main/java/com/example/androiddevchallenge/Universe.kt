package com.example.androiddevchallenge

class Universe(val size: Int) {

    fun evolve(cells: List<Cell>): List<Cell> {
        val livingNeighborCounts = cells
            .setupNeighborsList()
            .map { cell -> cell.neighbors.count { it.isAlive } }
        return cells.mapIndexed { i, cell -> cell.evolve(livingNeighborCounts[i]) }
    }

    private fun List<Cell>.setupNeighborsList(): List<Cell> {
        return mapIndexed { index, cell ->
            cell.copy(neighbors = setupNeighbors(index, this))
        }
    }

    private fun setupNeighbors(index: Int, allCells: List<Cell>): List<Cell> {
        return neighborCoordinatesOf(index.toX(), index.toY())
            .filter { it.isInBounds() }
            .map { it.toIndex() }
            .map { allCells[it] }

    }

    private fun neighborCoordinatesOf(x: Int, y: Int) = arrayOf(
        x - 1 to y - 1, x to y - 1, x + 1 to y - 1, x - 1 to y,
        x + 1 to y, x - 1 to y + 1, x to y + 1, x + 1 to y + 1
    )

    private fun Pair<Int, Int>.isInBounds() =
        !((first < 0).or(first >= size).or(second < 0).or(second >= size))

    private fun Pair<Int, Int>.toIndex() = second * size + first
    private fun Int.toX() = this % size
    private fun Int.toY() = this / size
}

//fun Universe.cellAt(x: Int, y: Int): Cell {
//    return cells[Pair(x, y).toIndex()]
//}