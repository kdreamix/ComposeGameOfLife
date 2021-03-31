package com.example.androiddevchallenge

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.data.Pattern
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

const val INIT_BOARD_SIZE = 10

data class GameState(
    val boardSize: Int = INIT_BOARD_SIZE,
    val isRunning: Boolean = false,
    val cells: List<Cell> = List(INIT_BOARD_SIZE * INIT_BOARD_SIZE) { Cell() },
    val frameRate: Long = 400,
)

class GameViewModel : ViewModel() {
    private var gameUniverse: Universe = Universe(INIT_BOARD_SIZE)
    private val initialState = GameState()
    private val _gameStateflow = MutableStateFlow(initialState)

    val gameStateflow: StateFlow<GameState> = _gameStateflow

    private fun newRepeatJob() = viewModelScope.launch {
        while (true) {
            if (_gameStateflow.value.cells.isEmpty()) {
                reset()
            }
            Log.v("Game", "evolve")
            delay(_gameStateflow.value.frameRate)
            evolve()
        }
    }

    private lateinit var repeatJob: Job

    fun start() {
        if (_gameStateflow.value.isRunning) return
        if (_gameStateflow.value.cells.isEmpty()) return
        repeatJob = newRepeatJob()
        repeatJob.start()
    }

    fun pauseResume() {
        val gameState = _gameStateflow.value
        if (gameState.isRunning) {
            repeatJob.cancel()
        } else {
            repeatJob = newRepeatJob()
            repeatJob.start()
        }
        viewModelScope.launch {
            _gameStateflow.emit(gameState.copy(isRunning = !gameState.isRunning))
        }
    }

    fun reset() {
        repeatJob.cancel()
        viewModelScope.launch {
            val updatedNeighborList = gameUniverse.setupNeighborsList(initialState.cells)
            _gameStateflow.emit(initialState.copy(cells = updatedNeighborList))
        }
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

    fun onFrameRateChange(newFrameRate: Long) {
        if (newFrameRate == 0L) return
        viewModelScope.launch {
            _gameStateflow.emit(_gameStateflow.value.copy(frameRate = newFrameRate))
        }
    }

    fun setPattern(patterns: Pattern) {
        Log.v("Game", "Update pattern")
        viewModelScope.launch {
            gameUniverse = Universe(patterns.boardSize)
            Log.v("Game","Universe size: ${gameUniverse.size}")
            val cells = patterns.data.map { Cell(isAlive = it == 1) }
            val updatedNeighborList = gameUniverse.setupNeighborsList(cells)
            _gameStateflow.emit(
                _gameStateflow.value.copy(
                    cells = updatedNeighborList,
                    boardSize = patterns.boardSize
                )
            )
        }
    }

    private fun evolve() {
        viewModelScope.launch {
            val cells = _gameStateflow.value.cells
            val evolvedCells = gameUniverse.evolve(cells)
            val updatedNeighborList = gameUniverse.setupNeighborsList(evolvedCells)

            // TODO make evolve encapsulate setup neighbors logic
            _gameStateflow.emit(
                _gameStateflow.value.copy(
                    cells = updatedNeighborList,
                    isRunning = true
                )
            )
        }
    }

    inline fun <T> List<T>.copy(mutatorBlock: MutableList<T>.() -> Unit): List<T> {
        return toMutableList().apply(mutatorBlock)
    }
}