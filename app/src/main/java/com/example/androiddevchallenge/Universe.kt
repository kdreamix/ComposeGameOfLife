package com.example.androiddevchallenge

class Universe(val size: Int) {

    fun evolve(cells: List<Cell>): List<Cell> {
        val livingNeighborCounts = cells.map { cell -> cell.neighbors.count { it.isAlive } }
        return cells.mapIndexed { i, cell -> cell.evolve(livingNeighborCounts[i]) }
    }

    fun setupNeighborsList(list: List<Cell>): List<Cell> {
        return list.mapIndexed { index, cell ->
            cell.copy(neighbors = setupNeighbors(index, list))
        }
    }

    private fun setupNeighbors(index: Int, allCells: List<Cell>): List<Cell> {
        return neighborCoordinatesOf(index.toX(), index.toY())
            .filter { it.isInBounds() }
            .map { it.toIndex() }
            .map { allCells[it] }

    }

    private fun neighborCoordinatesOf(x: Int, y: Int) = arrayOf(
        Pair(x - 1, y - 1), Pair(x, y - 1), Pair(x + 1, y - 1), Pair(x - 1, y),
        Pair(x + 1, y), Pair(x - 1, y + 1), Pair(x, y + 1), Pair(x + 1, y + 1)
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