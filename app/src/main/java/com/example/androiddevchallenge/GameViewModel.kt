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
    val _gameStateflow = MutableStateFlow(GameState())

    val gameStateflow: StateFlow<GameState> = _gameStateflow

    init {
        gameUniverse.setupNeighborsList(_gameStateflow.value.cells)
    }

    fun start() {
        viewModelScope.launch {
            while (true) {
                Log.v("Game", "evolve")
                delay(3000)
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
        viewModelScope.launch {
            _gameStateflow.emit(_gameStateflow.value.copy(cells = cells))
        }

    }

    private fun evolve() {
        viewModelScope.launch {
            val cells = _gameStateflow.value.cells
            val evolvedCells = gameUniverse.evolve(cells)
            _gameStateflow.emit(_gameStateflow.value.copy(cells = evolvedCells))
        }
    }

    inline fun <T> List<T>.copy(mutatorBlock: MutableList<T>.() -> Unit): List<T> {
        return toMutableList().apply(mutatorBlock)
    }
}