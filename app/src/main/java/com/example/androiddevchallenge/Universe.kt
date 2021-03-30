package com.example.androiddevchallenge

class Universe(val size: Int) {

//    init {
//        cells.forEachIndexed { i, cell -> cell.setupNeighbors(i) }
//    }


    fun evolve(cells: List<Cell>): List<Cell> {
        val livingNeighborCounts = cells.map { cell -> cell.neighbors.count { it.isAlive } }
        return cells.mapIndexed { i, cell -> cell.evolve(livingNeighborCounts[i]) }
    }

    fun setupNeighborsList(list: List<Cell>) {
        list.forEachIndexed { index, cell ->
            cell.setupNeighbors(index, list)
        }
    }

    private fun Cell.setupNeighbors(index: Int, allCells: List<Cell>) {
        neighborCoordinatesOf(index.toX(), index.toY())
            .filter { it.isInBounds() }
            .map { it.toIndex() }
            .mapTo(neighbors) { allCells[it] }

    }

    private fun neighborCoordinatesOf(x: Int, y: Int) = arrayOf(
        Pair(x - 1, y - 1), Pair(x, y - 1), Pair(x + 1, y - 1), Pair(x - 1, y),
        Pair(x + 1, y), Pair(x - 1, y + 1), Pair(x, y + 1), Pair(x + 1, y + 1)
    )

    private fun Pair<Int, Int>.isInBounds() =
        !((first < 0).or(first >= size).or(second < 0).or(second >= size))

    fun Pair<Int, Int>.toIndex() = second * size + first
    fun Int.toX() = this % size
    fun Int.toY() = this / size
}

//fun Universe.cellAt(x: Int, y: Int): Cell {
//    return cells[Pair(x, y).toIndex()]
//}