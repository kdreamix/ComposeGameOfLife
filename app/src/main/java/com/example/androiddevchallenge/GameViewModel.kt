package com.example.androiddevchallenge

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

const val boardSize = 10

data class GameState(
    val isRunning: Boolean = false,
    val cells: List<Cell> = List(boardSize * boardSize) { Cell() },
)

class GameViewModel() : ViewModel() {
    private val gameUniverse: Universe = Universe(boardSize)
    private val initialState = GameState()
    val setupState = initialState.copy(cells = gameUniverse.setupNeighborsList(initialState.cells))

    val _gameStateflow = MutableStateFlow(setupState)

    val gameStateflow: StateFlow<GameState> = _gameStateflow

    init {
    }

    fun start() {
        viewModelScope.launch {
            while (true) {
                Log.v("Game", "evolve")
                delay(400)
                evolve()
            }
        }
    }

    fun stop() {

    }

    fun reset() {

    }

    fun toggle(index: Int) {
        Log.v("Game", "Toggle $index")
        val cells = _gameStateflow.value.cells.copy {
            this[index] = this[index].toggle()
        }
        val updatedNeighborList = gameUniverse.setupNeighborsList(cells)

        viewModelScope.launch {
            _gameStateflow.emit(_gameStateflow.value.copy(cells = updatedNeighborList))
        }

    }

    private fun evolve() {
        viewModelScope.launch {
            val cells = _gameStateflow.value.cells
            val evolvedCells = gameUniverse.evolve(cells)
            val updatedNeighborList = gameUniverse.setupNeighborsList(evolvedCells)

            _gameStateflow.emit(_gameStateflow.value.copy(cells = updatedNeighborList))
        }
    }

    inline fun <T> List<T>.copy(mutatorBlock: MutableList<T>.() -> Unit): List<T> {
        return toMutableList().apply(mutatorBlock)
    }
}